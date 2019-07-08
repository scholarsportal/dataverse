'use strict';
angular.module('odesiApp').controller('combineCtrl', function($scope, $cookies, $http,sharedVariableStore){	
	var mode="";//for testing
	$scope.loadingTabDetails=false;
	$scope.bucket_order;
	$scope.data;//
	$scope.sharedVariableStore=sharedVariableStore
	$scope.buckets;//
	$scope.showing=false;//
	$scope.pending_requests=0;
	$scope.box_bg_colors = [
    { pct: 0.0, color: { r: 0xff, g: 0xff, b: 0xff } },
    { pct: 1.0, color: { r: 49, g: 122, b: 185 } } ];
    $scope.box_bg_colors2 = [
    { pct: 0.0, color: { r: 0xff, g: 0xff, b: 0xff } },
    { pct: 1.0, color: { r: 197, g: 54, b: 54 } } ];
    //store the mouse position for use in testing drpping of variables
	$(window).mousemove(function(e){
      window.mouse_x = e.pageX;
      window.mouse_y = e.pageY;
   	}); 
	drawLegend();
	$scope.$watch ('selectedVariable', function(){
		$scope.run_it();
	//----------------------
	});
	$scope.run_it = function(){
		$scope.variableCompare=[];//all the selected variables
		var temp_array=sharedVariableStore.getVariableCompare();
		//
		if(temp_array.length==0){
			unsplitInterface();
			return;
		}
		if(!$scope.showing){
			$('#right-half').show();
			$scope.showing=true;
			$(window).trigger('resize');
		}
		
		$scope.loadingTabDetails=true;
		//----------------------
		//var url="temp_tab/tab.tab";//temp loading of tab file
		var temp_array=sharedVariableStore.getVariableCompare()
		var temp_array2=[]
		for(var i = 0; i < temp_array.length; i++){
			temp_array2.push(temp_array[i].id);
		};
		var url=sharedVariableStore.getVariableStoreURL()+temp_array2.reverse().join(",");
		$scope.pending_requests++;
		//
		if(sharedVariableStore.getWeightOn()){
			//get the weights of the selected variables
			for(var i =0;i<sharedVariableStore.getWeights().length;i++){
				url+=","+sharedVariableStore.getWeights()[i].id
			}
			
		}
		//url should look like this "https://dataverse.harvard.edu/api/access/datafile/2326305?variables=v13184189,v13183932,v13184076",
		$http({
			url:url,
			method: "GET",
			//params: {requestURL: detailsURL.uri}
		}).success(function(data, status, headers, config){
			$scope.pending_requests--
			if($scope.pending_requests==0){
				prepData(data);
			}
		});
	}
	var prepData = function(data){
		var old_data=$scope.data
		if(!old_data){
			old_data=[];
		}
		$scope.data=createTabObj(data);
		$scope.buckets=createBuckets($scope.data);
		//if there are weights - remove them from the data
		if(sharedVariableStore.getWeightOn()){
			$scope.data=$scope.data.slice(0,$scope.data.length-sharedVariableStore.getWeights().length)
		}
		$scope.bucket_order=[];
		for(var i = 0; i < $scope.data.length; i++){
			$scope.bucket_order.push($scope.data[i].name);
		}
		//first remove any unwantend variables which have been unselected
		for(var i = old_data.length-1; i >= 0; i--){
			var var_exists=false;
			for(var j= 0; j < $scope.data.length; j++){
				if(old_data[i].name==$scope.data[j].name){
					var_exists=true
					break;
				}
			}
			//if variable old not found
			if(!var_exists){
				old_data.splice(i,1)
			}
		}
		//determine if there are any new variables
		var new_data=[];
		for(var i = 0; i < $scope.data.length; i++){
			var var_exists=false;
			for(var j = 0; j < old_data.length; j++){
				if($scope.data[i].name==old_data[j].name){
					var_exists=true
					break;
				}
			}
			//if new variable not found
			if(!var_exists){
				new_data.push($scope.data[i])
			}
		}
		
		getVariableMetadata(new_data);//adds extra variable information
		//if we already have some data just append the new values and delete any that have been removed
		$scope.data=old_data.concat(new_data);
		$scope.data=$scope.groupVariableMetadata();
		$scope.combineHTML=$scope.getCombinedTable();//triggers watcher update which places html
		$scope.loadingTabDetails=false
	}
	var createTabObj = function(_data){
		var var_obj=[]
		var data = _data.split(/\r?\n/);
			//loop over lines
			for(var i = 0; i < data.length; i++){
				var vals=data[i].split('\t');
				//create containing objects
				for(var j=0;j<vals.length;j++){
					if(i==0){
						var_obj[j]={'name':vals[j],'vals':[]}
					}else{
						if(vals[j]!=""){
							//load the values to the appropriate location
							var_obj[j]['vals'].push(vals[j])
						}
					}
				}
			}
			 return var_obj;
	}
	//loop through the variables looking for unique enteries
	var createBuckets = function(_data){
		var buckets=[];
		//loop through the first row and check against others
		var rows=_data[0].vals.length;
		var weight_count=0
		if(sharedVariableStore.getWeightOn()){
				weight_count=sharedVariableStore.getWeights().length
		}
		for(var i = 0; i < rows; i++){
			var combined_val="";
			//combine with others and look for uniqueness
			var weight=1;
			for (var j = 0;j< _data.length-weight_count;j++){
				combined_val+="_"+_data[j].vals[i]
				if(sharedVariableStore.getWeightOn()){
				 weight=_data[_data.length-1].vals[i]
				}
			}
			if(buckets.indexOf(combined_val)==-1){
				buckets.push(combined_val)
				buckets[combined_val]=1*weight
			}else{
				buckets[combined_val]=buckets[combined_val]+(1*weight);
			}
			
		}
		return buckets;
	}
	
	var getVariableMetadata = function(_data){
		for(var i = 0; i < _data.length; i++){
			var name=_data[i].name;
			for(var j=0;j<sharedVariableStore.getVariableStore().length;j++){
				if(sharedVariableStore.getVariableStore()[j].name==name){
					//store the metadata with the object
					_data[i].type=sharedVariableStore.getVariableStore()[j].type;
					_data[i].label=sharedVariableStore.getVariableStore()[j].label;
					_data[i].catgry=sharedVariableStore.getVariableStore()[j].fullData.catgry;
					break;
				}
			}
			//lets also fix the order of the catagory values
			var temp_array=[];
			if(typeof(_data[i].catgry)!="undefined"){
				for(var j=0; j<_data[i].catgry.length;j++){
					temp_array.push(_data[i].catgry[j])
				}
			}
			temp_array.sort(compare);
			_data[i].catgry=temp_array;	
		}
	}
	$scope.updateVariableStoreType = function(){
		var _data=$scope.data;
		for(var i = 0; i < _data.length; i++){
			var name=_data[i].name;
			for(var j=0;j<sharedVariableStore.getVariableStore().length;j++){
				if(sharedVariableStore.getVariableStore()[j].name==name){
					//store the metadata with the object
					sharedVariableStore.getVariableStore()[j].type=_data[i].type;
					break;
				}
			}
			
		}
	}
	//called from details.js
	$scope.updateDataType = function(_obj){
		var data=$scope.data;
		var changed_name=_obj.name
		var changed_pos=0;
		//update the type
		for(var i = 0; i < data.length; i++){
			var name=data[i].name;
			if(name==changed_name){
				changed_pos=i;
			}
			for(var j=0;j<sharedVariableStore.getVariableStore().length;j++){
				if(sharedVariableStore.getVariableStore()[j].name==name){
					//update
					data[i].type=sharedVariableStore.getVariableStore()[j].type;
					break;
				}
			}
		}
		if(_obj.type=="col"){
			//if type col move to beginning of the list
			data.move(changed_pos,0)
		}else{
			data.move(changed_pos,data.length-1)
		}	
		//
		var _data= $scope.groupVariableMetadata();
		$scope.combineHTML=$scope.getCombinedTable();
	}
	//stacks an array with columns then rows
	$scope.groupVariableMetadata = function(_data){
		if(!_data){
			_data=$scope.data;
		}
		var cols=[];
		var rows=[];
		for(var i = 0; i < _data.length; i++){
			if(_data[i].type=="col"){
				cols.push(_data[i])
			}else{
				rows.push(_data[i])
			}
		}

		return cols.concat(rows);
	};
	$scope.getCombinedTable =function(){
		var _data=$scope.data;
		var _tot_responses=$scope.data[0].vals.length;
		var html="<table id='compare_table' class='table-bordered table-condensed'>";
		//add first row to span table allowing the dropping of variables
		//rules if there is 1 row - there will be 4 cols (label,code,%,N)
		//otherwise we can just calculate them based on the cols
		var var_count=0;
		var total_col_count=1
		var total_col_var_count=0;
		for(var i=0; i<_data.length;i++){
			if(_data[i].type=="col"){
				total_col_count*=_data[i].catgry.length;
			}
		}
		var total_row_count=1
		var total_row_var_count=0
		for(var i=0; i<_data.length;i++){
			if(_data[i].type=="row"){
				total_row_count*=_data[i].catgry.length;
				total_row_var_count+=1
			}
		}

		//add cols to total row count since each will occupy 1
		for(var i=0; i<_data.length;i++){
			if(_data[i].type=="col"){
				total_row_count+=1;
				total_col_var_count+=1;
			}
		}

		///
		var no_rows=true;
		var single_row=false;
		var single_col=false;
		//Now do the rows - if there is one, otherwise just add 1
		for(var i = 0; i < _data.length; i++){//
			if(_data[i].type=="row"){
				if(_data.length==1){
					single_row=true
				}
				no_rows=false;
				obj=_data[i]
			}
			if(_data[i].type=="col"){
				if(_data.length==1){
					single_col=true
				}
			}
			//break;
		}
		//fillout drop box by adding some extra cells to the count
		if(single_row){
			total_col_count+=3
			total_row_count+=3
		}else if(single_col){
			total_col_count+=4
			total_row_count+=4
		}else if(!single_col && !single_row){
			total_col_count+=4
			total_row_count+=4
		}
		//doppable row
		html+="<tr><td id='col_0' class='droppable' colspan='"+(total_col_count+total_row_var_count)+"'></td></tr>";
		//droppable column
		html+="<td id='row_0' class='droppable' rowspan='"+total_row_count+"'></td>";
		//
		//assume the first variable is a 'row' so all the variable labels are lists on the vertical axis
		//if it doesn't have a type assume it a 'col' - with all variable labels on horizontal axis
		for(var i = 0; i < _data.length; i++){
			if(_data[i].type=="col"){//do rows last
				var td_class=getCurrentClass(var_count++)

				//get the number of columns to span by looping forward
				var span_count=1
				for(var j=i+1; j<_data.length;j++){
					if(_data[j].type=="col"){
						span_count*=_data[j].catgry.length;
					}
				}
				//
				html+="<tr>"
				html+="<th id='"+_data[i].name+"' class='draggable droppable "+td_class+"' colspan='"+(total_row_var_count)+"'>"+_data[i].label+"</th>"
				//
				var has_other_col=false;
				for(var j=i+1; j<_data.length;j++){
					if(_data[j].type=="col"){
						has_other_col=true;
						break
					}
				}
	
				//get the number of columns already placed by looping back
				var repeat_count=1;
				for(var j=i-1; j>=0;j--){
					if(_data[j].type=="col"){
						repeat_count*=_data[j].catgry.length;
					}
				}
				

				//loop through the results - repeating as neccessary
				for(var k=0;k<repeat_count;k++){
					for(var j=0; j<_data[i].catgry.length;j++){
						var var_label=_data[i].catgry[j].labl['#text'];
						html+="<td class='"+td_class+" td_center' colspan='"+(span_count)+"'";
						if(single_col){
							html+=" rowspan='"+1+"'"
						}else if(!has_other_col){//set the row span of the last value to overlap the labels
							html+=" rowspan='"+2+"'"
						}
						html+=">"+var_label+"</td>";
					}
				}
				//and the totals and N col
				if(i==0){
					var count=total_col_var_count
					if(!no_rows){
						count+=1
					}
					html+="<th class='td_value td_center td_last' rowspan='"+count+"'>Total</th>"
					//
					html+="<th class='td_value td_center' rowspan='"+count+"'>N</th>"
				}

				html+="</tr>"
				//--------
				if(single_col){
					html+="<tr><th class='td_value td_center'>Code</th>"
					for(var j=0; j<_data[i].catgry.length;j++){
						var cat_val=_data[i].catgry[j].catvalu['#text']
						html+="<td class='td_value td_center'>"+cat_val+"</td>"
					}
					//plus and empty cell since no totals for codes
					html+="<td></td><td></td>"
					html+="</tr>"
				}
			}
		}
		//---------------
		var obj={};
		obj.num=0
		//
		var td_class=getCurrentClass(obj.num+var_count);
		html+="<tr>"
		//Add all the variable rows along one row
		//look ahead to see if there are other variables to add
		var temp_var_count=Number(var_count)
		for(var j=obj.num; j<_data.length;j++){
			if(_data[j].type=="row"){
				var td_class2=getCurrentClass(temp_var_count++);//cycle the classes for other rows
				//should be flipping the class
				html+="<th id='"+_data[j].name+"' class='draggable droppable "+td_class2+"'>"+_data[j].label+"</th>"
			}
		}
		//if this is the only one add two extra headers % and n=
		if(single_row){
			html+="<th class='td_value td_center'>Code</th>"
			html+="<th class='td_value td_center'>%</th>"
			html+="<th class='td_value td_center'>N</th>"
		}else{
			var has_no_cols_vals=true;
			for(var j=obj.num; j<_data.length;j++){
				if(_data[j].type=="col"){
					has_no_cols_vals=false;
				}
			}
			if(has_no_cols_vals){
				html+="<th class='td_value td_center'>%</th>"
			}
		}
		//
		html+="</tr>"
		//
		var totals=[[0]];
		//Need to alternate between adding full rows - and partial rows
		//at the lowest level how many rows are there
		var total_rows=1;
		for(var j=obj.num; j<_data.length;j++){//only work from first row;
			if(_data[j].type=="row"){
				total_rows*=_data[j].catgry.length;
			}
		}
		//--------------
		var row_var_array=[];
		//populate an array with all the rows, using array slots for each of the cells
		for(var j=0; j<total_rows;j++){
			row_var_array.push(getVariableLabel([],_data,j,_data.length-1,"row"));
		}
		if(typeof(row_var_array[0])=="undefined"){
			row_var_array[0]=[]
		}
		//
		var full_row_var_array=$.extend(true, [], row_var_array)
		//------------
		//get corresponding cols
		var total_cols=1;
		var col_var_array=[];
		for(var j=0; j<_data.length;j++){//only work from first row;
			if(_data[j].type=="col"){
				total_cols*=_data[j].catgry.length;
			}
		}
		for(var j=0; j<total_cols;j++){
			col_var_array.push(getVariableLabel([],_data,j,_data.length-1,"col"));
		}
		if(typeof(col_var_array[0])=="undefined"){
			col_var_array[0]=[]
		}
		//calculate the standard deviation
		//get the mean of the values as avg_bucket_val
		var bucket_val_total=0;
		var bucket_vals=[];
		for(var j=0; j<total_rows;j++){
			for(var k=0; k<col_var_array.length;k++){
				var bucket_id=getBucketID(col_var_array[k].concat(full_row_var_array[j]),_data,"catvalu")
				//add the values
				var val=getBucketValue(bucket_id)
				if(!isNaN(val)){
					bucket_vals.push(val);
				}else{
					bucket_vals.push(0);
				}
			}
		}
		var bucket_val_total=0;
		for (var j = 0; j < bucket_vals.length; j++) {
		    bucket_val_total += bucket_vals[j];
		}
		//console.log("bucket_val_total ",bucket_val_total)
		var avg_bucket_val=bucket_val_total/bucket_vals.length;
		//Now get the total distance of all the points from that number
		//console.log("avg_bucket_val",avg_bucket_val)
		var bucket_vals_dist=[];
		//get bucket_vals_dist to exp 2
		//console.log("bucket_vals",bucket_vals)
		for (var j = 0; j < bucket_vals.length; j++) {
		   bucket_vals_dist.push(Math.pow(Math.abs(bucket_vals[j]-avg_bucket_val),2))
		}
		//console.log("bucket_vals_dist "+bucket_vals_dist);
		var bucket_vals_dist_total=0;
		//sum bucket_vals_dist
		for (var j = 0; j < bucket_vals_dist.length; j++) {
		   bucket_vals_dist_total+=bucket_vals_dist[j];
		}
		var standard_dev=Math.sqrt(bucket_vals_dist_total/bucket_vals_dist.length);
		//console.log("standard_dev "+standard_dev)
		//-------
		var col_pre_totals=[];
		var col_response_totals=[];
		for(var j=0; j<total_rows;j++){
			html+="<tr>"
			html+=getTableData(row_var_array,j,var_count);//return the rows omiting duplicates
			if(single_row){
				var cat_val=_data[obj.num].catgry[j].catvalu['#text']
				html+="<td class='td_value td_center'>"+cat_val+"</td>"
			}
			if(no_rows){
				html+="<td class='td_value td_center'>%</td>"
			}
			//------------
			
			var row_pre_total=0;
			var row_response_total=0;
			for(var k=0; k<col_var_array.length;k++){
				var bucket_id=getBucketID(col_var_array[k].concat(full_row_var_array[j]),_data,"catvalu")

				//add the values
				var bucket_val=getBucketValue(bucket_id)
				var bucket_per=0;
				if(bucket_val){
					bucket_per=Math.round(bucket_val/_tot_responses*100*10)/10
				}
				//make sure there's a slot for the col totals
				if(typeof(col_pre_totals[k])=="undefined"){
					col_pre_totals[k]=0;
					col_response_totals[k]=0;
				}
				if(isNaN(bucket_val)){
					bucket_val=0;
				}
				row_response_total+=bucket_val;
				row_pre_total+=bucket_val/_tot_responses;
				col_pre_totals[k]+=bucket_val/_tot_responses;
				col_response_totals[k]+=bucket_val;

				//
				if(mode=="debug"){
					html+="<td class='td_value td_center'>"+col_var_array[k].concat(full_row_var_array[j])+"</td>"
				}else{
					
					//color the cell based relative to the average
					var z=(bucket_val-avg_bucket_val)/standard_dev;
					var cell_color;
					if(z>0){
						cell_color=getColorForPercentage($scope.box_bg_colors2,z/2)
					}else{
						cell_color=getColorForPercentage($scope.box_bg_colors,z/2*-1)
					}
					//change the text color depending on the percent for cell color visiblility
					var text_color="#000000";
					if(Math.abs(z)/2>.5){
						text_color="#ffffff";
					}
					//
					html+="<td class='td_value td_center' style='background-color:"+cell_color+"; color:"+text_color+"'>"
					if(bucket_per % 1 == 0){
						bucket_per=String(bucket_per)+".0";
					}
					html+=bucket_per;
					html+="</td>";
					

				}
			}
			//add the percent totals
			if(total_col_var_count!=0){
				html+="<td class='td_value td_center td_last'>"+getPercent(row_pre_total)+"</td>"
				html+="<td class='td_value td_center'>"+Math.round(row_response_total*10)/10+"</td>"
			}
			//-------
			if(single_row){
				html+="<td class='td_value td_center'>"+Math.round(bucket_val*10)/10+"</td>"
			}
			html+="</tr>"
			totals[0]=Number(totals[0])+Number(bucket_val)
		}
		if(single_col){
			html+="<tr><th class='td_value td_center'>N</th>"
			for(var j=0; j<_data[0].catgry.length;j++){
				try{
					var cat_val=_data[0].catgry[j].catstat['#text']
				}catch(e){
					cat_val="";
				}
				
				html+="<td class='td_value td_center'>"+cat_val+"</td>"
			}
			//add the response totals
			html+="<td class='td_value td_center'>"+Math.round(row_response_total*10)/10+"</td><td></td>"
			html+="</tr>"
		}else if(single_row){
			//add a row with the totals
			html+="<tr class='tr_last'>"
			html+="<th class='td_value'>Total</th>"
			html+="<td class='td_value'></td>"
			html+="<td class='td_value'></td>"
			html+="<td class='td_value'>"+Math.round(totals[0]*10)/10+"</td>"
			html+="</tr>"
		}else{
			html+="<tr class='tr_last'>"
			html+="<th colspan='"+total_row_var_count+"'>Total</th>"
			for(var k=0;k<col_pre_totals.length;k++){
				html+="<td class='td_value td_center'>"+getPercent(col_pre_totals[k])+"</td>"
			}
			if(total_col_var_count>0){
				html+="<td class='td_value td_center td_last'>"+getPercent(1)+"</td><td></td>";
			}
			html+="</tr>"
			//
			html+="<tr >"
			html+="<th colspan='"+total_row_var_count+"'>N</th>"
			var all_responses=0;
			for(var k=0;k<col_pre_totals.length;k++){
				all_responses+=col_response_totals[k]
				html+="<td class='td_value td_center'>"+Math.round(col_response_totals[k]*10)/10+"</td>"
			}
			if(total_col_var_count>0){
				html+="<td class='td_last'></td><td class='td_value td_center'>"+Math.round(all_responses*10)/10+"</td>";
			}
			html+="</tr>"
		}
			
		//
		return html+'</table>';
	}
	//sorting for multidimentional array
	function compare(a,b) {
	  if (a.catvalu['#text'] < b.catvalu['#text'])
	    return -1;
	  if (a.catvalu['#text'] > b.catvalu['#text'])
	    return 1;
	  return 0;
	}
	var td_classes=["td_class1","td_class2"];
	function getCurrentClass(curr_td_class){
		return td_classes[curr_td_class%td_classes.length];
	}
	//use the bottom level as a gauge for position using the exceedance value, work up the stack 
	function getTableData(_row_var_array,_num,_class_offset){
		var html="";
		var row_array=_row_var_array[_num]//for easy access 
		var row_span_array=[];//to store the rowspans	
		//look ahead to determine rowspan - setting all duplicates to "" 
		for(var i=0;i<row_array.length;i++){
			for(var j=_num+1;j<_row_var_array.length;j++){
				if(_row_var_array[j][i]==row_array[i] && row_array[i]!=""){
					_row_var_array[j][i]="";
					//increment val
					if(isNaN(row_span_array[i])){
						row_span_array[i]=1;
					}
					row_span_array[i]++;
				}else{
					break
				}
			}
		}
		//
		for(var i=0;i<row_array.length;i++){
			var cell_val=row_array[i];
			var td_class=getCurrentClass(i+_class_offset);
			if(cell_val!=""){
				html+="<td rowspan='"+row_span_array[i]+"' class='"+td_class+" td_center'>"+cell_val+"</td>";
			}
			
		}
		return html;
	}
	//looks at the loaded variables labels and returns the corresponding value 
	//Note that the _data maybe in a different order from the bucket order

	function getBucketID(_label_array,_data,_val){
		var bucket_array=[];
		for(var i =0; i<_label_array.length;i++){
			for(var j =0; j<_data[i].catgry.length;j++){
				if(_data[i].catgry[j].labl["#text"]==_label_array[i]){
					bucket_array.push(_data[i].catgry[j][_val]["#text"]);
					break;
				}
			}

		}
		var reordered_bucket_array=[];
		//rearrange variable to match reorder
		 for(var i =0; i<_data.length;i++){
		 	//find the proper slot
		 	for(var j =0; j<$scope.bucket_order.length;j++){
		 		if(_data[i].name==$scope.bucket_order[j]){
		 			reordered_bucket_array[j]=bucket_array[i];
		 		}
		 	}
		 }
		return "_"+reordered_bucket_array.join("_");
	}
	function getBucketValue(_id){
		return $scope.buckets[_id];
	}
	//makes a recursive call returing all the labels in a stack
	function getVariableLabel(_label_array,_data,_num,_level,type){
		for(var i=_level; i>=0;i--){
			if(_data[i].type==type){
				var exceeded_count=0;
				var position=0;
				var child_count=_data[i].catgry.length-1;
				for(var j=0;j<_num;j++){
					position++
					if(position>child_count){
						exceeded_count++
						position=0;
					}
				}
				_label_array.unshift(_data[i].catgry[position].labl['#text'])
				if(_data[i-1] && _data[i-1].type==type){
					return getVariableLabel(_label_array,_data,exceeded_count,i-1,type)
				}else{
					return _label_array
				}
				break
			}
		}
	}
//-----
function drawLegend(){
	var html="";
	html+="<table>";
	html+="<tr>"; 
	html+="<td>Z-Score&nbsp;</td>";
	html+="<td id='left_gradient' style='color:#fff; padding-left:5px;'>-2<</td>";
	html+="<td><0<</td>";
	html+="<td id='right_gradient' style='text-align:right; color:#fff; padding-right:5px;'><2</td>";
	html+="</tr>";
	html+="</table>";
	$("#legend").html(html);
	$("#left_gradient").css(getCSSGradient($scope.box_bg_colors[0].color,$scope.box_bg_colors[1].color,"right","left"));
	$("#right_gradient").css(getCSSGradient($scope.box_bg_colors2[0].color,$scope.box_bg_colors2[1].color,"left","right"));
}
function getCSSGradient(_start_color,_end_color,dir1,dir2){
	var start_color  = "rgb("+_start_color.r+", "+_start_color.g+", "+_start_color.b+")";
	var end_color  = "rgb("+_end_color.r+", "+_end_color.g+", "+_end_color.b+")";
	return {
		width:150,
		height:20,
		background: start_color,
		background: "-webkit-linear-gradient("+dir1+","+start_color+", "+start_color+", "+end_color+")",
		background: "-o-linear-gradient("+dir2+","+start_color+", "+end_color+")",
		background: "-moz-linear-gradient("+dir2+","+start_color+", "+end_color+")", 
		background: "linear-gradient(to "+dir2+","+start_color+", "+end_color+")"
	}
}

}).directive('dynamic', function ($compile) {
	return {
	    restrict: 'A',
	    replace: true,
	    link: function (scope, ele, attrs) {
		      scope.$watch("combineHTML", function(html) {
		      	var data=scope.data;
		        ele.html(html);
		        $compile(ele.contents())(scope);
		        //
		        $("#compare_table .draggable").prepend("<span class='sortHandle glyphicon glyphicon-move' style='cursor:pointer;'></span>")
		        //
		      	$("#compare_table .draggable").draggable({
		        	handle: '.sortHandle',
		        	opacity: 0.7,
		        	containment: "#compare_table",
		        	start: function(event, ui){
						$(event.target).css({'z-index': 1000});
					},
					 revert : function(event) {
					 	var rect = $("#compare_table")[0].getBoundingClientRect();

					 	if( window.mouse_y>=rect.top && window.mouse_y<=rect.bottom && window.mouse_x>=rect.left && window.mouse_x<=rect.right){
					 		try{
				            $(this).data("uiDraggable").originalPosition = {
				                top : 0,
				                left : 0
				            };
				            return !event;
			       		}catch(e){}

					 	}else{
							for(var i =0;i<scope.sharedVariableStore.getVariableStore().length;i++){
								var obj=scope.sharedVariableStore.getVariableStore()[i]
			            		if(obj.name==$(this).attr('id')){
			            			//deselect the item, thus removing it from the table
					 				//angular.element($('#details-page')).scope().viewVariable(obj)
					 				scope.$apply()
					 			}
					 		}
					 	}
					 	
			        },
					stop: function(event, ui){
						$(event.target).css({'z-index': 999});	
					}
		        }).disableSelection();
		        //
		        $('#compare_table .droppable').droppable({
       				accept: ".draggable",
		       		tolerance: "pointer",
		        	snap: ".draggable",
		        	classes: {
				        "ui-droppable-hover": "ui-state-hover"
				      },
			        drop: function (event, ui) {
			        	var moved_id=$(ui.draggable).attr('id');
			        	var dropped_id=$(this).attr('id');
			        	var curr_pos;
			        	var new_pos;
			        	var new_type; 
			        	var old_type;

			            //get the postion for 
			            if(dropped_id=="row_0"){
			            	new_pos=0;
			            	new_type="row";
			            }else if(dropped_id=="col_0"){
			            	new_pos=0;
			            	new_type="col";
			            }else{
			            	//find the position of the dropped var
			            	for(var i =0;i<data.length;i++){
				            	if(data[i].name==dropped_id){
									new_pos=i;
									new_type=data[i].type;
				            		break;
				            	}
				            }
			            }
			            //
						for(var i =0;i<data.length;i++){
			            	if(data[i].name==moved_id){
								curr_pos=i;
								//update the type
								old_type=data[i].type;
								data[i].type=new_type;
			            		break;
			            	}
			            }
			            if(curr_pos==new_pos && old_type==new_type){
			            	//revert
			            	$(ui.draggable).css({'top': 0, 'left' :0})
			            }else{
							data.move(curr_pos,new_pos)
							scope.data=scope.groupVariableMetadata(data);//need to resort since cols draw before rows
							scope.combineHTML= scope.getCombinedTable();
							scope.updateVariableStoreType();//triggers interface type (row,col) update
							
							//update the state - noting it's in reverse
							var temp_array=scope.sharedVariableStore.getVariableCompare().reverse();
							temp_array[curr_pos].type=new_type;
							temp_array.move(curr_pos,new_pos);
							angular.element($('#details-page')).scope().updateURLParams(temp_array.reverse())
							scope.$apply()
						}
			        }
			    });
		        //
		    });
		}
	};
});
var getPercent=function(_percent){
	var _percent=Math.round(_percent*100*10)/10;
	if(_percent % 1 == 0){
			_percent=String(_percent)+".0";
	}
	return _percent
}
//http://stackoverflow.com/questions/7128675/from-green-to-red-color-depend-on-percentage
var getColorForPercentage = function(colors,pct) {
    for (var i = 1; i < colors.length - 1; i++) {
        if (pct < colors[i].pct) {
            break;
        }
    }
    var lower = colors[i - 1];
    var upper = colors[i];
    var range = upper.pct - lower.pct;
    var rangePct = (pct - lower.pct) / range;
    var pctLower = 1 - rangePct;
    var pctUpper = rangePct;
    var color = {
        r: Math.floor(lower.color.r * pctLower + upper.color.r * pctUpper),
        g: Math.floor(lower.color.g * pctLower + upper.color.g * pctUpper),
        b: Math.floor(lower.color.b * pctLower + upper.color.b * pctUpper)
    };
    return 'rgb(' + [color.r, color.g, color.b].join(',') + ')';
    // or output as hex if preferred
}  
//http://stackoverflow.com/questions/5306680/move-an-array-element-from-one-array-position-to-another
Array.prototype.move = function (old_index, new_index) {
    if (new_index >= this.length) {
        var k = new_index - this.length;
        while ((k--) + 1) {
            this.push(undefined);
        }
    }
    this.splice(new_index, 0, this.splice(old_index, 1)[0]);
    return this; // for testing purposes
};
