'use strict';
angular.module('odesiApp').controller('detailsCtrl', function($scope,$cookies, $http, $modal, $location, searchParams,filterService, variableQuery,variableClick,sharedVariableStore){
    $scope.showPaging =true;
    $scope.chartTemplatePath = 'templates/chart.html';
    var detailsURL = $location.search();//get the url params
    $scope.detailsURL = detailsURL;
    console.log($scope.detailsURL)
    $scope.currentTablePage = [];//stored with each survey file
    $scope.tablePageSize = 10;
    $scope.loadingDetails = true;
    $scope.showVariables = true;//original default was false
    $scope.active = {abstract: true};
    $scope._variables;
    $scope.variableClick = variableClick
    $scope.variableQuery = variableQuery;
    $scope.selectedVariable;
    $scope.view="chart";
    $scope.searchParams = searchParams;
    $scope._variableData;
    $scope._filtered_num=0;
    $scope.sortReverse=true;
    $scope.has_no_selection=true;
    $scope.sortReverse=true;
    //
    $scope.sectionModel = {}
    //
    $scope.citation="";

    $scope.weight_on=false;
    $scope.has_weights=false;//temp

    //
    if($scope.variableClick.params == true) {
        $scope.active = {matches: true};
    } else {
        $scope.active = {abstract: true};
    };
    //
    $(".nav-tabs").append("<span id='deselect_x' aria-label='"+angular.element($('#main')).scope().lang.detailsPage.close+"' class='sortHandle glyphicon glyphicon-remove' style='cursor:pointer;'></span>");
    $( "#deselect_x" ).click(function() {
        var temp_array=sharedVariableStore.getVariableCompare();
        for (var i = 0; i < temp_array.length; i++){
            for(var j=0;j<sharedVariableStore.getVariableStore().length;j++){
                if(sharedVariableStore.getVariableStore()[j].vid==temp_array[i].id){
                    //store the metadata with the object
                    sharedVariableStore.getVariableStore()[j].selected=false
                    sharedVariableStore.getVariableStore()[j].type="";
                    break;
                }
            }
        }
        sharedVariableStore.setVariableCompare([])
        $scope.updateURLParams([]);
        unsplitInterface();
    });
    var selectVariables = function(variables){
        if(!variables){
            return;
        }else if(variables.length>0){
            //allow subset download
            $scope.has_no_selection = false;
        }
        for (var i = 0; i < variables.length; i++){
            for(var j=0;j<sharedVariableStore.getVariableStore().length;j++){
                if(sharedVariableStore.getVariableStore()[j].vid==variables[i].id){
                    //store the metadata with the object
                    sharedVariableStore.getVariableStore()[j].selected=true
                    sharedVariableStore.getVariableStore()[j].type=variables[i].type
                    break;
                }
            }
        }

        sharedVariableStore.setVariableCompare(variables);
        $scope.selectedVariable=URLON.stringify(variables);
        if(getParameterByName("view")=="table"){
            //show tabular view
            setTimeout(function(){  $('.nav-tabs a:eq(1)').trigger("click") }, 5);
        }
    }
    $scope.updateURLParams = function(selection,view,weight) {
        //toggle subset download
        if (selection && selection.length > 0 ) {
            $scope.has_no_selection = false;
        }else if(view){
            //the view has changed do not alter the subset download
        } else {
            $scope.has_no_selection = true;
        }
        //
        if(selection){
            $scope.selectedVariable=URLON.stringify(selection);
            sharedVariableStore.setVariableCompare(selection);
        }
        if(view){
            $scope.view=view
        }
        if(weight){
            detailsURL.weight =weight
        }
        //make sure to retain existing params
        var obj={};
        for(var i in detailsURL){
            if(["uri","fileId","siteUrl","key","locale"].indexOf(i)==-1)
                obj[i]=detailsURL[i];
        }
        obj.selected=$scope.selectedVariable;
        obj.view=$scope.view
        $location.search(obj);
    }
    //
    var populateVariables = function() {
        //create the citation
        var id_node=$scope.details.stdydscr.citation.titlstmt.idno;
        createCitation($scope.details.stdydscr.citation.biblcit["#text"], id_node.agency,id_node["#text"]);
        //create a reference to a specific link for dataverse
        $scope.surveyVariables = [];
        if ($scope.details.datadscr ){
            var counter=0
            for (var i = 0; i < $scope.details.datadscr['var'].length; i++){
                counter++


                //join the variable data
                $scope.details.datadscr['var'][i].variable_data=$scope._variableData[$scope.details.datadscr['var'][i].name]
                //
                if(!$scope.details.datadscr['var'][i].variable_data && typeof($scope._variableData.variables)!="undefined"){
                    //it might be nested in "variables"
                    $scope.details.datadscr['var'][i].variable_data=$scope._variableData.variables[$scope.details.datadscr['var'][i].name]

                }
                //check if the last  variable is a weight
                /*if(i == $scope.details.datadscr['var'].length-1){
                    var name=$scope.details.datadscr['var'][i].name.toLowerCase()
                    if(name.indexOf("weight")>-1 || name.indexOf("wgt")>-1){
                        sharedVariableStore.addWeights($scope.details.datadscr['var'][i]);
                        $scope.has_weights=true;
                    }
                }*/
                var chartable=false;

                if(typeof($scope.details.datadscr['var'][i].variable_data)!="undefined" && typeof($scope.details.datadscr['var'][i].variable_data.plotvalues)!="undefined" && typeof($scope.details.datadscr['var'][i].catgry)=="undefined"){
                    //artificially create data obj - we likely have value and freq
                    var temp_data=[]
                    for (var j in $scope.details.datadscr['var'][i].variable_data.plotvalues){
                        temp_data.push({labl:{"#text":j}, catvalu:{"#text":j},freq:$scope.details.datadscr['var'][i].variable_data.plotvalues[j]})
                    }
                    //update the catgry for reuse
                    $scope.details.datadscr['var'][i].catgry=temp_data;
                }

                //exception for dataverse
                var labl=""
                if ($scope.details.datadscr['var'][i].labl && $scope.details.datadscr['var'][i].labl["#text"]) {
                    labl= $scope.details.datadscr['var'][i].labl["#text"]
                }else if($scope.details.datadscr['var'][i].labl) {
                    labl= $scope.details.datadscr['var'][i].labl
                }
                // Todo need strong determinate for chartability
                //if(typeof($scope.details.datadscr['var'][i].catgry)!="undefined"){
                chartable=true
                //}
                $scope.details.datadscr['var'][i].labl=labl;
                $scope.surveyVariables.push({
                    id : parseFloat($scope.details.datadscr['var'][i].id.substring(1)),
                    vid : $scope.details.datadscr['var'][i].id,
                    label : labl,
                    chartable : chartable,
                    name : $scope.details.datadscr['var'][i].name,
                    fullData : $scope.details.datadscr['var'][i]
                });
                //If a location element exists - need to catagorize the questions by files
                if(typeof($scope.details.datadscr['var'][i].location)!="undefined" && $scope.details.datadscr['var'][i].location.fileid){
                    //get the file description
                    var file_id=$scope.details.datadscr['var'][i].location.fileid;

                    var uri,file_note;
                    //create an object array for the files (if it doesn't alreay exist)
                    if(typeof($scope.survey_files)=="undefined"){
                        $scope.survey_files=[]
                    }
                    //check if the file is in the array
                    var file_array_pos=-1;
                    for( var j = 0; j <$scope.survey_files.length; j++ ) {
                        //console.log("files", $scope.survey_files[j],file_id)
                        if( $scope.survey_files[j][0] === file_id ) {
                            file_array_pos=j;
                            break;
                        }
                    }
                    //console.log($scope.survey_files)
                    //if the file is not in the location array
                    if(file_array_pos==-1)	{
                        //console.log($scope.details.filedscr)
                        for(var j=0;j<$scope.details.filedscr.length;j++){
                            //console.log(file_id,$scope.details.filedscr[j].id)
                            if(file_id==$scope.details.filedscr[j].id){
                                var file_obj=$scope.details.filedscr[j]
                                //take the new url for better linking - note this simply downloads the data in the case of dataverse
                                uri=file_obj.URI[0]
                                //console.log(file_obj)
                                //create a new variable "vl.file_note" for file title display
                                for(var k=0;k<file_obj.notes.length;k++){
                                    if(file_obj.notes[k].type=="vdc:category"){
                                        file_note=file_obj.notes[k]._
                                        break;
                                    }
                                }
                                break
                            }
                        }
                        $scope.survey_files.push([file_id,{uri:uri,file_note:file_note,file_num:$scope.survey_files.length},[]])
                        file_array_pos=$scope.survey_files.length-1
                        $scope.currentTablePage.push(0);//all individual file page managmented
                    }
                    //store the survey item within the surveys files array
                    var var_obj=$scope.surveyVariables[$scope.surveyVariables.length-1]
                    //console.log($scope.surveyVariables.length,var_obj)
                    $scope.survey_files[file_array_pos][2].push(var_obj);
                    //keep a reference to the parent
                    var_obj.file_obj=$scope.survey_files[$scope.survey_files.length-1]
                    //
                    if(typeof($scope.filterBy)!="undefined"){
                        if(typeof($scope.survey_files_filtered)=="undefined"){
                            $scope.survey_files_filtered=[];//create container if it doesn't already exist
                        }
                        if($scope.filterBy.indexOf(var_obj.id)>-1){
                            //make sure there is a file object container
                            var file_array_pos_filtered=-1
                            for(var j=0;j<$scope.survey_files_filtered.length;j++){
                                if(file_id==$scope.survey_files_filtered[j][0]){
                                    file_array_pos_filtered=j;
                                    break;
                                }
                            }
                            if(file_array_pos_filtered==-1){
                                //create the container
                                $scope.survey_files_filtered.push([file_id,{uri:uri,file_note:file_note,file_num:$scope.survey_files.length},[]])
                                file_array_pos_filtered=$scope.survey_files_filtered.length-1
                            }
                            $scope.survey_files_filtered[file_array_pos_filtered][2].push(var_obj);
                        }
                    }
                    //
                    var index = $scope.surveyVariables.length - 1;
                    //exception for joining prep with details

                    if(typeof($scope.surveyVariables[index].sumstat) =="undefined"){
                        //expose the variables to the top level
                        for(j in $scope.surveyVariables[index].fullData.variable_data){
                            $scope.surveyVariables[index][j]=$scope.surveyVariables[index].fullData.variable_data[j]
                        }

                    }
                    //since DLIMF does not have a sumstat - check if it exists first before looping
                    if(typeof($scope.details.datadscr['var'][i].sumstat) !="undefined"){

                        for (var j = 0; j < $scope.details.datadscr['var'][i].sumstat.length; j++){
                            if (!$scope.details.datadscr['var'][i].sumstat[j].wgtd){
                                if ($scope.details.datadscr['var'][i].sumstat[j].type == 'vald'){
                                    $scope.surveyVariables[index].valid = Math.round($scope.details.datadscr['var'][i].sumstat[j]["#text"]);
                                }else if ($scope.details.datadscr['var'][i].sumstat[j].type == 'invd'){
                                    $scope.surveyVariables[index].missing = Math.round($scope.details.datadscr['var'][i].sumstat[j]["#text"]);
                                }else if ($scope.details.datadscr['var'][i].sumstat[j].type == 'min'){
                                    $scope.surveyVariables[index].min = Math.round($scope.details.datadscr['var'][i].sumstat[j]["#text"]);
                                }else if ($scope.details.datadscr['var'][i].sumstat[j].type == 'max'){
                                    $scope.surveyVariables[index].max = Math.round($scope.details.datadscr['var'][i].sumstat[j]["#text"]);
                                }
                            }
                        }

                    }else{

                        if(typeof($scope.details.datadscr['var'][i].valrng) !="undefined") {
                            var obj=$scope.details.datadscr['var'][i].valrng[0].range[0];
                            $scope.surveyVariables[index].min = Math.round(obj.min);
                            $scope.surveyVariables[index].max = Math.round(obj.max);
                        }
                    }
                }
            }
        }
        $scope.numberOfTablePages = function(_vars){
            return Math.ceil(_vars/$scope.tablePageSize);
        }
        //Allow filtering search results
        $scope.updateFilter = function(value){
            filterService.setFilter(value)
            $scope.currentTablePage[0]=0
        }
        $scope.filterResults = function(vl) {
            return vl.label.toLowerCase().indexOf(filterService.getFilter().toLowerCase()) !== -1 || vl.name.toLowerCase().indexOf(filterService.getFilter().toLowerCase()) !== -1 || vl.vid.toLowerCase().indexOf(filterService.getFilter().toLowerCase()) !== -1
        };

        //if only one file - treat it as if there were none by removing file reference
        if(typeof($scope.survey_files)!="undefined" && $scope.survey_files.length==1){
            $scope.survey_files=null;
        }
        //set variables for display
        if(!$scope.survey_files){
            $scope._variables=$scope.surveyVariables;//set a common variable to handle the sharing of the survey-variables page
            $scope._file_num=0;//to reference the first slot in the currentTablePage array
            $scope.currentTablePage[0]=0
            //loop through the array of $scope.filterBy and add them to the _variables_filtered array
            if(typeof($scope.filterBy)!="undefined"){
                $scope._variables_filtered=[];
                for(var i = 0;i<$scope._variables.length;i++){
                    if($scope.filterBy.indexOf($scope._variables[i].id)>-1){
                        $scope._variables_filtered.push($scope._variables[i])
                    }
                }
            }
        }
        sharedVariableStore.setVariableStore($scope.surveyVariables);//so that they are accessible to the chart for comparison
    }
    $scope.gotoPage = function(num,pg){
        $scope.currentTablePage[num] = pg;
    }
    $scope.resetCurrentPage = function(limit){
        $scope.tablePageSize=limit
        for(var i=0;i<$scope.currentTablePage.length;i++){
            $scope.currentTablePage[i]=0;
        }
    }
    $scope.viewVariableKeyPress = function(e,vl) {
        if ((e && e.keyCode === 13) || typeof e === 'undefined') {
            $scope.viewVariable(vl);
        }
    };
    $scope.viewVariable = function (vl,type) {
        //assign default display categories
        var id=vl.vid;
        //---
        var temp_array=sharedVariableStore.getVariableCompare();
        //make sure to keep selected if only the type has changed
        if(type && vl.type && vl.type!=type){
            //keep it selected - just update the chart
            vl.type=type;
            angular.element($('#combineModal')).scope().updateDataType(vl);
            //also need to modify the temp array
            var slot=temp_array.findIndex(x => x.id==id);
            temp_array.splice( slot,1);
            if(type=="row"){
                //put at begining - note the reverse of the table view ordering
                temp_array.unshift({"id":id, "type":type});
            }else{
                //put at end
                temp_array.push({"id":id, "type":type});
            }
            $scope.updateURLParams(temp_array)

            return
        }
        if(!type){
            type="row";
        }else{
            //show tabular view
            setTimeout(function(){  $('.nav-tabs a:eq(1)').trigger("click") }, 5);
        }
        var result=$.grep(temp_array, function(e){ return e.id == id; });
        if(result.length!=0){
            //remove the item from the array
            temp_array.splice( temp_array.findIndex(x => x.id==id),1)
        }else{
            temp_array.unshift({"id":id, "type":type});

        }
        //toggle the selection
        vl.selected=!vl.selected;

        if(vl.selected){
            vl.type=type;//store the type for Table View (either row or column)
        }else{
            delete vl.type;
        }
        //update the state
        $scope.updateURLParams(temp_array)
    };
    $scope.my_option = 0;
    $scope.downloadData = function (my_option) {
        var url=base_url+file_id//api/access/datafile/$id
        switch(Number(my_option)){
            case 5:
                var temp_array=sharedVariableStore.getVariableCompare()
                var temp_array2=[]
                for(var i = 0; i < temp_array.length; i++){
                    temp_array2.push(temp_array[i].id);
                };
                url=base_url+file_id+"?key="+detailsURL.key+"&variables="+temp_array2.join(",");
                break;
            case 1:
                url+="?format=original"
                break;
            case 2:
                //add nothing to download the tab file
                url+="?"
                break;
            case 3:
                url+="?format=RData"
                break;
            case 4:
                //need to prep the url a bit - should look like //https://sand9.scholarsportal.info/api/meta/datafile/15
                var base_url_api=base_url.substring(0,base_url.indexOf("/api/"));
                url=base_url_api+"/api/meta/datafile/"+file_id+"?"
                break;
            default:
                return
        }
        //add the key
        url+="&key="+detailsURL.key
        window.location.assign(url);
        $scope.reset();
    };
    $scope.goToTwoRavens =function (){
        var base_url_api=base_url.substring(0,base_url.indexOf("/api/"));
        var	url=base_url_api+"/dataexplore/gui.html?dfId="+file_id+"&key="+detailsURL.key

        window.open(url, "new");
    }
    $scope.reset = function() {
        $('#download').val( 0 );
        setTimeout(function(){ $scope.$apply();
            $('#download').trigger('change');
        });
    };
    $scope.selectFilter=function(){
        $(".search_field").select()
    }
    $scope.clearField=function(){
        $(".search_field").val("")
        $(".search_field").trigger( "change" );
    }


    // this traverses $scope.details object.
    var traverse = function(o,func) {
        for (var i in o) {
            func.apply(this,[i,o[i]]);
            if (o[i] !== null && typeof(o[i])=="object") {
                //going on step down in the object tree!!
                traverse(o[i],func);
            }
        }
    }
    var createCitation = function(citation,agency,id){
        var link;
        if(agency=="hdl"){
            link="dx.doi.org"
        }else{
            link="hdl.handle.net"
        }
        link="http://"+link+"/"+id
        //inject the url into the citation
        var re = new RegExp(agency+"([^,]+)", "g")
        var match = re.exec(citation);

        citation=citation.replace(match[0], "<a href='"+link+"' target='_new'>"+match[0]+"</a>");
        $("#citation").html(citation);
    }


    ///////////////////////
    /*
    Entry point of application
    */
    //////////////////////
    detailsURL.uri=getParameterByName("uri");
    detailsURL.siteUrl=getParameterByName("siteUrl");
    detailsURL.fileId=getParameterByName("fileId");
    detailsURL.key=getParameterByName("key");
    detailsURL.locale=getParameterByName("locale")
    detailsURL.selected=getParameterByName("selected");
    detailsURL.weight=getParameterByName("weight");
    //
    var file_id = "";
    var base_url = null;
    if (detailsURL.uri) {
        //In the future the uri will not be needed but remains to support existing implementations
        file_id = detailsURL.uri.match("datafile\/(.*)\/metadata")[1];
        base_url = detailsURL.uri.substr(0, detailsURL.uri.indexOf("datafile/") + 9);
    }
    //the fileId and siteUrl may be provided
    if(detailsURL.fileId){
        file_id=detailsURL.fileId;
    }
    if(!detailsURL.siteUrl && !detailsURL.uri){
        detailsURL.siteUrl = window.location.hostname;
    }
    //
    if(file_id=="") {
        //error - try using the dfId without a detailsURL.siteUrl
        detailsURL.fileId = getParameterByName("dfId");
        file_id = detailsURL.fileId;
        detailsURL.siteUrl = location.protocol+'//'+location.hostname;
        if (location.port != "") {
            detailsURL.siteUrl+=":"+location.port
        }
    }
    //
    if(detailsURL.siteUrl!=null){
        base_url=detailsURL.siteUrl+"/api/access/datafile/";
        detailsURL.uri=base_url+file_id+"/metadata/ddi";
    }
    //
    if(detailsURL.selected){
        detailsURL.selected = URLON.parse(detailsURL.selected);
    }
    if(detailsURL.weight=='on'){
        $scope.weight_on=true;
        sharedVariableStore.setWeightOn(true);//temp
    }
    $http({
        url: detailsURL.uri,
        method: "GET",
        //params: {requestURL: detailsURL.uri}
    }).success(function(data, status, headers, config){
        var xml = $.parseXML(data);
        $scope.details = xmlToJson(xml).codebook;
        connectVariablesAndData();
    });



    //create a url for loading the data
    //var tab_data_url=base_url.substring(0,base_url.indexOf("/api/"))+"/ReadFile?url="+base_url+file_id+"&key="+detailsURL.key+"&variables=";
    var tab_data_url=base_url+file_id+"?variables=";
    sharedVariableStore.setVariableStoreURL(tab_data_url);
    //
    $http({
        url: base_url+file_id+"?format=prep&key="+detailsURL.key,
        method: "GET",
        //params: {requestURL: detailsURL.uri}
    }).success(function(data, status, headers, config){
        $scope._variableData=data;
        connectVariablesAndData();
    }).error(function(){
        $scope._variableData={};
        connectVariablesAndData();
    });
    console.log(base_url+file_id+"?format=prep&key="+detailsURL.key);
    var loadcount=0
    function connectVariablesAndData(){
        //wait till both variables and data are loaded
        loadcount++
        if(loadcount>1){
            populateVariables();
            $scope.loadingDetails = false;
            $(".overlay").fadeOut();
            //try to show a previous selection if exists
            selectVariables(detailsURL.selected);
        }
    }

}).controller('AccordionCtrl', function($scope){
    $scope.show = {};
    $scope.toggle = function(index) {
        $scope.show[index] = !$scope.show[index];
    };
})
    .controller('PagerCtrl', function($scope){
        $scope.showPaging =true;
    })
    .directive('tabs',  function() {
        return {
            restrict: 'E',
            transclude: true,
            scope: {},
            controller: [ "$scope", function($scope) {
                var panes = $scope.panes = [];

                $scope.select = function(pane) {
                    angular.forEach(panes, function(pane) {
                        pane.selected = false;
                    });
                    pane.selected = true;
                    var view = 'chart';
                    if(pane.label.toLowerCase().indexOf("table")>-1){
                        view='table';
                    }
                    if(angular.element($('#details-page')).scope().view !=view){
                        angular.element($('#details-page')).scope().updateURLParams(null,view)
                    }
                }

                this.addPane = function(pane) {
                    if (panes.length == 0) $scope.select(pane);
                    panes.push(pane);
                }
            }],
            template:
            '<div class="tabbable">' +
            '<ul class="nav nav-tabs">' +
            '<li ng-repeat="pane in panes" ng-class="{active:pane.selected}">'+
            '<a href="" ng-click="select(pane)">{{pane.label}}</a>' +
            '</li>' +
            '</ul>' +
            '<div class="tab-content" ng-transclude></div>' +
            '</div>',
            replace: true
        };
    })
    .directive('pane', function() {
        return {
            require: '^tabs',
            restrict: 'E',
            transclude: true,
            scope: { label: '@' },
            link: function(scope, element, attrs, tabsCtrl) {
                tabsCtrl.addPane(scope);
            },
            template:
            '<div class="tab-pane" ng-class="{active: selected}" ng-transclude>' +
            '</div>',
            replace: true
        };
    }).controller('weightCtrl', function($scope,sharedVariableStore) {
    $('#weight_but').click(function(e) {
        e.preventDefault();
        $scope.weight_on=!$scope.weight_on;
        sharedVariableStore.setWeightOn($scope.weight_on);
        var url_val='on'
        if(!$scope.weight_on){
            url_val='off'
        }
        $scope.updateURLParams(null,null,url_val)
        if(sharedVariableStore.getWeights()>1){
            //TODO if there are more then 1 weight variables all the user to determine which one to add
            console.log("show prompt allow user to add their own weights")
        }
        for(var j=0;j<sharedVariableStore.getVariableStore().length;j++){
            //add the specified slot to the
            sharedVariableStore.getVariableStore()[j].weight_id=sharedVariableStore.getWeights()[0].id
        }
        //if there are slected variables - make sure to update the charts/tables
        if($scope.detailsURL.selected!=""){
            angular.element($('#combineModal')).scope().run_it();
        }
    })
});
////////////////////////
// Changes XML to JSON
function xmlToJson(xml) {
    // Create the return object
    var obj = {};
    if (xml.nodeType == 1) { // element
        // do attributes
        if (xml.attributes.length > 0) {
            for (var j = 0; j < xml.attributes.length; j++) {
                var attribute = xml.attributes.item(j);
                obj[attribute.nodeName.toLowerCase()] = attribute.nodeValue;
            }
        }
    } else if (xml.nodeType == 3) { // text
        obj = xml.nodeValue;
    }
    // do children
    if (xml.hasChildNodes()) {
        for(var i = 0; i < xml.childNodes.length; i++) {
            var item = xml.childNodes.item(i);
            var nodeName = item.nodeName.toLowerCase();;
            if (typeof(obj[nodeName]) == "undefined") {
                obj[nodeName] = xmlToJson(item);
            } else {
                if (typeof(obj[nodeName].push) == "undefined") {
                    var old = obj[nodeName];
                    obj[nodeName] = [];
                    obj[nodeName].push(old);
                }
                obj[nodeName].push(xmlToJson(item));
            }
        }
    }
    return obj;
};
//adjust the interface size
$(function() {
    $( window ).resize(function() {
        var details_height=$( window ).height()-$("#details-content").position().top-170
        $('.tab_views').css({height:details_height});
        $('#variables_table_container').css({height:details_height});
        $('#right-half').css({top:$("#details-content").offset().top+1, width:$("#details-content").width()/2-10})
        if($('#right-half').is(":visible")){
            splitInterface();
        }else{
            unsplitInterface()
        }
    });

});

function splitInterface(){
    $('#right-half').show();
    var content_width=$("#details-content").width()
    $('#right-half').finish().animate({left: content_width/2+30}, 700);
    $('#variables_table').css({width: content_width});
    $('#left-half').finish().animate({width: (content_width/2+15)}, 700);
}
function unsplitInterface(){
    var content_width=$("#details-content").width()
    $('#variables_table').css({width: content_width});
    $('#left-half').finish().animate({width: content_width});
    $('#right-half').finish().animate({left: content_width}, 700);
    $('#right-half').hide();
    angular.element($('#combineModal')).scope().combineHTML="";
    angular.element($('#combineModal')).scope().showing=false;
}

