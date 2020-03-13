(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["main"],{

/***/ "./src/$$_lazy_route_resource lazy recursive":
/*!**********************************************************!*\
  !*** ./src/$$_lazy_route_resource lazy namespace object ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncaught exception popping up in devtools
	return Promise.resolve().then(function() {
		var e = new Error("Cannot find module '" + req + "'");
		e.code = 'MODULE_NOT_FOUND';
		throw e;
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "./src/$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "./src/app/app.component.css":
/*!***********************************!*\
  !*** ./src/app/app.component.css ***!
  \***********************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJhcHAvYXBwLmNvbXBvbmVudC5jc3MifQ== */"

/***/ }),

/***/ "./src/app/app.component.html":
/*!************************************!*\
  !*** ./src/app/app.component.html ***!
  \************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<app-interface></app-interface>\n"

/***/ }),

/***/ "./src/app/app.component.ts":
/*!**********************************!*\
  !*** ./src/app/app.component.ts ***!
  \**********************************/
/*! exports provided: AppComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppComponent", function() { return AppComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");


var AppComponent = /** @class */ (function () {
    function AppComponent() {
        this.title = 'data-curation-tool';
    }
    AppComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-root',
            template: __webpack_require__(/*! ./app.component.html */ "./src/app/app.component.html"),
            styles: [__webpack_require__(/*! ./app.component.css */ "./src/app/app.component.css")]
        })
    ], AppComponent);
    return AppComponent;
}());



/***/ }),

/***/ "./src/app/app.module.ts":
/*!*******************************!*\
  !*** ./src/app/app.module.ts ***!
  \*******************************/
/*! exports provided: createTranslateLoader, HttpLoaderFactory, createMyMatPaginatorIntl, AppModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "createTranslateLoader", function() { return createTranslateLoader; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "HttpLoaderFactory", function() { return HttpLoaderFactory; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "createMyMatPaginatorIntl", function() { return createMyMatPaginatorIntl; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppModule", function() { return AppModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser */ "./node_modules/@angular/platform-browser/fesm5/platform-browser.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _angular_common_locales_fr_CA__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/common/locales/fr-CA */ "./node_modules/@angular/common/locales/fr-CA.js");
/* harmony import */ var _angular_common_locales_fr_CA__WEBPACK_IMPORTED_MODULE_5___default = /*#__PURE__*/__webpack_require__.n(_angular_common_locales_fr_CA__WEBPACK_IMPORTED_MODULE_5__);
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _ngx_translate_core__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @ngx-translate/core */ "./node_modules/@ngx-translate/core/fesm5/ngx-translate-core.js");
/* harmony import */ var _ngx_translate_http_loader__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @ngx-translate/http-loader */ "./node_modules/@ngx-translate/http-loader/fesm5/ngx-translate-http-loader.js");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm5/material.es5.js");
/* harmony import */ var _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @angular/platform-browser/animations */ "./node_modules/@angular/platform-browser/fesm5/animations.js");
/* harmony import */ var _app_component__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! ./app.component */ "./src/app/app.component.ts");
/* harmony import */ var _ddi_service__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ./ddi.service */ "./src/app/ddi.service.ts");
/* harmony import */ var _interface_interface_component__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! ./interface/interface.component */ "./src/app/interface/interface.component.ts");
/* harmony import */ var _var_group_var_group_component__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! ./var-group/var-group.component */ "./src/app/var-group/var-group.component.ts");
/* harmony import */ var _var_var_component__WEBPACK_IMPORTED_MODULE_15__ = __webpack_require__(/*! ./var/var.component */ "./src/app/var/var.component.ts");
/* harmony import */ var _var_dialog_var_dialog_component__WEBPACK_IMPORTED_MODULE_16__ = __webpack_require__(/*! ./var-dialog/var-dialog.component */ "./src/app/var-dialog/var-dialog.component.ts");
/* harmony import */ var _var_stat_dialog_var_stat_dialog_component__WEBPACK_IMPORTED_MODULE_17__ = __webpack_require__(/*! ./var-stat-dialog/var-stat-dialog.component */ "./src/app/var-stat-dialog/var-stat-dialog.component.ts");
/* harmony import */ var _chart_chart_component__WEBPACK_IMPORTED_MODULE_18__ = __webpack_require__(/*! ./chart/chart.component */ "./src/app/chart/chart.component.ts");
/* harmony import */ var _mat_paginator_intl__WEBPACK_IMPORTED_MODULE_19__ = __webpack_require__(/*! ./mat-paginator-intl */ "./src/app/mat-paginator-intl.ts");











// the second parameter 'fr' is optional
Object(_angular_common__WEBPACK_IMPORTED_MODULE_4__["registerLocaleData"])(_angular_common_locales_fr_CA__WEBPACK_IMPORTED_MODULE_5___default.a, 'fr-CA');












function createTranslateLoader(http) {
    return new _ngx_translate_http_loader__WEBPACK_IMPORTED_MODULE_8__["TranslateHttpLoader"](http);
}
function HttpLoaderFactory(httpClient) {
    return new _ngx_translate_http_loader__WEBPACK_IMPORTED_MODULE_8__["TranslateHttpLoader"](httpClient, './assets/i18n/', '.json');
}
function createMyMatPaginatorIntl(translateService, translateParser) { return new _mat_paginator_intl__WEBPACK_IMPORTED_MODULE_19__["MyMatPaginatorIntl"](translateService, translateParser); }
var AppModule = /** @class */ (function () {
    function AppModule() {
    }
    AppModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_2__["NgModule"])({
            declarations: [
                _app_component__WEBPACK_IMPORTED_MODULE_11__["AppComponent"],
                _interface_interface_component__WEBPACK_IMPORTED_MODULE_13__["InterfaceComponent"],
                _var_group_var_group_component__WEBPACK_IMPORTED_MODULE_14__["VarGroupComponent"],
                _var_var_component__WEBPACK_IMPORTED_MODULE_15__["VarComponent"],
                _var_dialog_var_dialog_component__WEBPACK_IMPORTED_MODULE_16__["VarDialogComponent"],
                _var_stat_dialog_var_stat_dialog_component__WEBPACK_IMPORTED_MODULE_17__["VarStatDialogComponent"],
                _chart_chart_component__WEBPACK_IMPORTED_MODULE_18__["ChartComponent"]
            ],
            imports: [
                _angular_platform_browser__WEBPACK_IMPORTED_MODULE_1__["BrowserModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormsModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_3__["ReactiveFormsModule"],
                _angular_common_http__WEBPACK_IMPORTED_MODULE_6__["HttpClientModule"],
                _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_10__["BrowserAnimationsModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatButtonModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatTabsModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatIconModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatToolbarModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatTableModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatSortModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatProgressSpinnerModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatPaginatorModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatFormFieldModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatInputModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatDialogModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatSidenavModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatListModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatCheckboxModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatSelectModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatGridListModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatChipsModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatSnackBarModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatTooltipModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatAutocompleteModule"],
                _ngx_translate_core__WEBPACK_IMPORTED_MODULE_7__["TranslateModule"].forRoot({ loader: {
                        provide: _ngx_translate_core__WEBPACK_IMPORTED_MODULE_7__["TranslateLoader"],
                        useFactory: HttpLoaderFactory,
                        deps: [_angular_common_http__WEBPACK_IMPORTED_MODULE_6__["HttpClient"]]
                    }
                })
            ],
            exports: [],
            entryComponents: [_var_dialog_var_dialog_component__WEBPACK_IMPORTED_MODULE_16__["VarDialogComponent"], _var_stat_dialog_var_stat_dialog_component__WEBPACK_IMPORTED_MODULE_17__["VarStatDialogComponent"]],
            providers: [_ddi_service__WEBPACK_IMPORTED_MODULE_12__["DdiService"], _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatPaginatorIntl"], {
                    provide: _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatPaginatorIntl"],
                    deps: [_ngx_translate_core__WEBPACK_IMPORTED_MODULE_7__["TranslateService"], _ngx_translate_core__WEBPACK_IMPORTED_MODULE_7__["TranslateParser"]],
                    useFactory: createMyMatPaginatorIntl
                }],
            bootstrap: [_app_component__WEBPACK_IMPORTED_MODULE_11__["AppComponent"]]
        })
    ], AppModule);
    return AppModule;
}());



/***/ }),

/***/ "./src/app/chart/chart.component.css":
/*!*******************************************!*\
  !*** ./src/app/chart/chart.component.css ***!
  \*******************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJhcHAvY2hhcnQvY2hhcnQuY29tcG9uZW50LmNzcyJ9 */"

/***/ }),

/***/ "./src/app/chart/chart.component.html":
/*!********************************************!*\
  !*** ./src/app/chart/chart.component.html ***!
  \********************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div #chart ></div>\n"

/***/ }),

/***/ "./src/app/chart/chart.component.ts":
/*!******************************************!*\
  !*** ./src/app/chart/chart.component.ts ***!
  \******************************************/
/*! exports provided: ChartComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ChartComponent", function() { return ChartComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var d3__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! d3 */ "./node_modules/d3/index.js");



var ChartComponent = /** @class */ (function () {
    function ChartComponent() {
        this.colorArray = [
            '#3366cc',
            '#dc3912',
            '#ff9900',
            '#109618',
            '#990099',
            '#0099c6',
            '#dd4477',
            '#66aa00',
            '#b82e2e',
            '#316395',
            '#994499',
            '#22aa99',
            '#aaaa11',
            '#6633cc',
            '#e67300',
            '#8b0707',
            '#651067',
            '#329262',
            '#5574a6',
            '#3b3eac',
            '#b77322',
            '#16d620',
            '#b91383',
            '#f4359e',
            '#9c5935',
            '#a9c413',
            '#2a778d',
            '#668d1c',
            '#bea413',
            '#0c5922',
            '#743411'
        ];
        this.maxStringLength = 13;
    }
    ChartComponent.prototype.ngOnInit = function () {
        this.createChart(this.data);
    };
    ChartComponent.prototype.createChart = function (_data) {
        var obj = this;
        var data = this.data;
        data = [];
        for (var i = 0; i < _data.length; i++) {
            var freq = null;
            var freqWeight = null;
            if (typeof _data[i].catStat !== 'undefined') {
                for (var j = 0; j < _data[i].catStat.length; j++) {
                    var subObj = _data[i].catStat[j];
                    if (subObj['@type'] === 'freq' && !subObj['@wgtd']) {
                        freq = subObj['#text'];
                    }
                    else {
                        if (subObj['@type'] === 'freq' && subObj['@wgtd'] && subObj['#text'] !== '') {
                            freqWeight = subObj['#text'];
                        }
                    }
                }
                // dataverse ddi exception
                if (!freq) {
                    freq = _data[i].catStat['#text'];
                }
            }
            var shortName = _data[i].labl['#text'];
            if (shortName.length > this.maxStringLength) {
                shortName = shortName.substring(0, this.maxStringLength) + '...';
            }
            shortName = shortName;
            // switching to weighted frequencies
            if (freqWeight != null) {
                freq = freqWeight;
            }
            data.push({
                name: shortName,
                freq: freq
            });
        }
        var maxHeight = (data.length + 1) * 25;
        // sort based on catStat
        data = data.sort(function (a, b) {
            return a.catStat - b.catStat;
        });
        // set the dimensions and margins of the graph
        var margin = { top: 0, right: 20, bottom: 30, left: 90 };
        var width = 500 - margin.left - margin.right;
        var height = maxHeight - margin.top - margin.bottom;
        // set the ranges
        var y = d3__WEBPACK_IMPORTED_MODULE_2__["scaleBand"]()
            .range([height, 0])
            .padding(0.3);
        var x = d3__WEBPACK_IMPORTED_MODULE_2__["scaleLinear"]().range([0, width]);
        var element = this.chartContainer.nativeElement;
        var svg = d3__WEBPACK_IMPORTED_MODULE_2__["select"](element)
            .append('svg')
            .attr('width', width + margin.left + margin.right)
            .attr('height', height + margin.top + margin.bottom)
            .append('g')
            .attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');
        // format the data
        data.forEach(function (d) {
            d.freq = +d.freq;
        });
        // Scale the range of the data in the domains
        x.domain([
            0,
            d3__WEBPACK_IMPORTED_MODULE_2__["max"](data, function (d) {
                return d.freq;
            })
        ]);
        y.domain(data.map(function (d) {
            return d.name;
        }));
        // append the rectangles for the bar chart
        var count = 0;
        svg
            .selectAll('.bar')
            .data(data)
            .enter()
            .append('rect')
            .attr('class', 'bar')
            .attr('width', function (d) {
            return x(d.freq);
        })
            .attr('y', function (d) {
            return y(d.name);
        })
            .attr('fill', function (d) {
            count += 1;
            return obj.getColor(count);
        })
            .attr('height', y.bandwidth());
        // add the x Axis
        svg
            .append('g')
            .attr('transform', 'translate(0,' + height + ')')
            .call(d3__WEBPACK_IMPORTED_MODULE_2__["axisBottom"](x));
        // add the y Axis
        svg.append('g').call(d3__WEBPACK_IMPORTED_MODULE_2__["axisLeft"](y));
    };
    ChartComponent.prototype.getColor = function (num) {
        var color = '';
        if (num < this.colorArray.length) {
            color = this.colorArray[num];
        }
        else {
            color = this.getRandomColor();
        }
        return color;
    };
    ChartComponent.prototype.getRandomColor = function () {
        var letters = '0123456789ABCDEF'.split('');
        var color = '#';
        for (var i = 0; i < 6; i++) {
            color += letters[Math.floor(Math.random() * 16)];
        }
        return color;
    };
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ViewChild"])('chart'),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_core__WEBPACK_IMPORTED_MODULE_1__["ElementRef"])
    ], ChartComponent.prototype, "chartContainer", void 0);
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])(),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", Array)
    ], ChartComponent.prototype, "data", void 0);
    ChartComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-chart',
            template: __webpack_require__(/*! ./chart.component.html */ "./src/app/chart/chart.component.html"),
            styles: [__webpack_require__(/*! ./chart.component.css */ "./src/app/chart/chart.component.css")]
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [])
    ], ChartComponent);
    return ChartComponent;
}());



/***/ }),

/***/ "./src/app/ddi.service.ts":
/*!********************************!*\
  !*** ./src/app/ddi.service.ts ***!
  \********************************/
/*! exports provided: DdiService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DdiService", function() { return DdiService; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var rxjs__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! rxjs */ "./node_modules/rxjs/_esm5/index.js");





var DdiService = /** @class */ (function () {
    function DdiService(http) {
        this.http = http;
        this.searchInput = new rxjs__WEBPACK_IMPORTED_MODULE_3__["BehaviorSubject"]('');
        this.currentSearchInput = this.searchInput.asObservable();
    }
    DdiService.prototype.getDDI = function (url) {
        return this.http.get(url, { responseType: 'text' });
    };
    DdiService.prototype.putDDI = function (url, body, key) {
        var httpOptions = {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                'Content-Type': 'application/xml',
                'X-Dataverse-key': key
            })
        };
        return this.http.put(url, body, httpOptions);
        // return this.http.post(url,body, httpOptions);
    };
    DdiService.prototype.getParameterByName = function (name) {
        var url = window.location.href;
        name = name.replace(/[\[\]]/g, '\\$&');
        var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)');
        var results = regex.exec(url);
        if (!results) {
            return null;
        }
        if (!results[2]) {
            return '';
        }
        return decodeURIComponent(results[2].replace(/\+/g, ' '));
    };
    DdiService.prototype.getBaseUrl = function () {
        var protocol = window.location.protocol;
        var host = window.location.hostname;
        var port = window.location.port;
        /*  if (port === '4200') {
            port = '8080';
          }*/
        var baseUrl = protocol + '//' + host;
        if (port != null || typeof port !== 'undefined') {
            baseUrl = baseUrl + ':' + port;
        }
        return baseUrl;
    };
    DdiService.prototype.clearSearch = function () {
        this.searchInput.next('');
    };
    DdiService = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Injectable"])(),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"]])
    ], DdiService);
    return DdiService;
}());



/***/ }),

/***/ "./src/app/interface/interface.component.css":
/*!***************************************************!*\
  !*** ./src/app/interface/interface.component.css ***!
  \***************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".interface-header {\n  padding-top: 0.8em;\n  border-bottom: solid 1px rgba(0, 0, 0, .12);\n}\n\n.mat-form-field.lang-select {\n  font-size: 0.6em;\n  width: 7em;\n}\n\n.mat-toolbar-row,\n.mat-toolbar-single-row {\n  min-height: 40px;\n  height: auto;\n}\n\n.header-text {\n  height: auto;\n  font-size: 0.7em;\n  font-weight: 400;\n  white-space: normal;\n  padding-bottom: 10px;\n  line-height: 1.1em;\n}\n\n.header-text.title {\n  font-size: 1.1em;\n  font-weight: 500;\n}\n\n.action-buttons {\n  float: right;\n  font-size: 0.7em;\n}\n\n.button-label {\n  padding: 0.3em;\n}\n\n.groups-toggle {\n  font-size: 0.7em;\n}\n\n.group-container {\n  min-height: 100vh;\n}\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbImFwcC9pbnRlcmZhY2UvaW50ZXJmYWNlLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7RUFDRSxrQkFBa0I7RUFDbEIsMkNBQTJDO0FBQzdDOztBQUVBO0VBQ0UsZ0JBQWdCO0VBQ2hCLFVBQVU7QUFDWjs7QUFFQTs7RUFFRSxnQkFBZ0I7RUFDaEIsWUFBWTtBQUNkOztBQUVBO0VBQ0UsWUFBWTtFQUNaLGdCQUFnQjtFQUNoQixnQkFBZ0I7RUFDaEIsbUJBQW1CO0VBQ25CLG9CQUFvQjtFQUNwQixrQkFBa0I7QUFDcEI7O0FBRUE7RUFDRSxnQkFBZ0I7RUFDaEIsZ0JBQWdCO0FBQ2xCOztBQUVBO0VBQ0UsWUFBWTtFQUNaLGdCQUFnQjtBQUNsQjs7QUFFQTtFQUNFLGNBQWM7QUFDaEI7O0FBRUE7RUFDRSxnQkFBZ0I7QUFDbEI7O0FBRUE7RUFDRSxpQkFBaUI7QUFDbkIiLCJmaWxlIjoiYXBwL2ludGVyZmFjZS9pbnRlcmZhY2UuY29tcG9uZW50LmNzcyIsInNvdXJjZXNDb250ZW50IjpbIi5pbnRlcmZhY2UtaGVhZGVyIHtcbiAgcGFkZGluZy10b3A6IDAuOGVtO1xuICBib3JkZXItYm90dG9tOiBzb2xpZCAxcHggcmdiYSgwLCAwLCAwLCAuMTIpO1xufVxuXG4ubWF0LWZvcm0tZmllbGQubGFuZy1zZWxlY3Qge1xuICBmb250LXNpemU6IDAuNmVtO1xuICB3aWR0aDogN2VtO1xufVxuXG4ubWF0LXRvb2xiYXItcm93LFxuLm1hdC10b29sYmFyLXNpbmdsZS1yb3cge1xuICBtaW4taGVpZ2h0OiA0MHB4O1xuICBoZWlnaHQ6IGF1dG87XG59XG5cbi5oZWFkZXItdGV4dCB7XG4gIGhlaWdodDogYXV0bztcbiAgZm9udC1zaXplOiAwLjdlbTtcbiAgZm9udC13ZWlnaHQ6IDQwMDtcbiAgd2hpdGUtc3BhY2U6IG5vcm1hbDtcbiAgcGFkZGluZy1ib3R0b206IDEwcHg7XG4gIGxpbmUtaGVpZ2h0OiAxLjFlbTtcbn1cblxuLmhlYWRlci10ZXh0LnRpdGxlIHtcbiAgZm9udC1zaXplOiAxLjFlbTtcbiAgZm9udC13ZWlnaHQ6IDUwMDtcbn1cblxuLmFjdGlvbi1idXR0b25zIHtcbiAgZmxvYXQ6IHJpZ2h0O1xuICBmb250LXNpemU6IDAuN2VtO1xufVxuXG4uYnV0dG9uLWxhYmVsIHtcbiAgcGFkZGluZzogMC4zZW07XG59XG5cbi5ncm91cHMtdG9nZ2xlIHtcbiAgZm9udC1zaXplOiAwLjdlbTtcbn1cblxuLmdyb3VwLWNvbnRhaW5lciB7XG4gIG1pbi1oZWlnaHQ6IDEwMHZoO1xufVxuIl19 */"

/***/ }),

/***/ "./src/app/interface/interface.component.html":
/*!****************************************************!*\
  !*** ./src/app/interface/interface.component.html ***!
  \****************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div id=\"loading-details\" *ngIf=\"!ddiLoaded\" class=\"row content-area\">\n  <mat-progress-spinner mode=\"indeterminate\"></mat-progress-spinner>\n</div>\n\n<ng-container class=\"interface-container\">\n  <mat-toolbar class=\"interface-header\">\n    <mat-toolbar-row>\n      <h1 class=\"header-text title\">{{title}}</h1>\n      <span class=\"fill-space\"></span>\n      <mat-form-field class=\"lang-select\">\n        <select matNativeControl title=\"Language\" #langSelect (change)=\"translate.use(langSelect.value)\">\n          <option *ngFor=\"let lang of translate.getLangs()\" [value]=\"lang\" [selected]=\"lang === translate.currentLang\">{{lang}}</option>\n        </select>\n      </mat-form-field>\n    </mat-toolbar-row>\n    <mat-toolbar-row class=\"header-text\">\n      {{filename}}\n    </mat-toolbar-row>\n    <mat-toolbar-row class=\"header-text citation\">\n      <span>{{firstCitat}}<a href=\"{{doi}}\">{{doi}}</a>{{secondCitat}}</span>\n    </mat-toolbar-row>\n    <mat-toolbar-row>\n      <span class=\"groups-toggle\">\n        <ng-container *ngIf=\"sidenav.opened; else showGroups\">\n          <button mat-button (click)=\"sidenav.toggle()\">\n            <mat-icon color=\"accent\" [attr.aria-label]=\"'GROUPS.HIDE' | translate\">keyboard_arrow_left</mat-icon>\n            <span class=\"button-label\">{{'GROUPS.HIDE' | translate }}</span>\n          </button>\n        </ng-container>\n        <ng-template #showGroups>\n          <button mat-button (click)=\"sidenav.toggle()\">\n            <mat-icon color=\"accent\" [attr.aria-label]=\"'GROUPS.SHOW' | translate\">keyboard_arrow_right</mat-icon>\n            <span class=\"button-label\">{{'GROUPS.SHOW' | translate }}</span>\n          </button>\n        </ng-template>\n      </span>\n      <span class=\"fill-space\"></span>\n      <span class=\"action-buttons\">\n        <button mat-button (click)=\"onSave()\">\n          <mat-icon color=\"accent\" [attr.aria-label]=\"'SAVE.DOWNLOAD' | translate\" [attr.matTooltip]=\"'SAVE.DOWNLOAD' | translate\">get_app</mat-icon>\n          <span class=\"button-label\">{{'SAVE.DOWNLOAD' | translate}}</span>\n        </button>\n      </span>\n      <span class=\"action-buttons\">\n        <button mat-button (click)=\"sendToDV()\">\n            <mat-icon color=\"accent\" [attr.aria-label]=\"'SAVE.SAVE' | translate\" [attr.matTooltip]=\"'SAVE.SAVE' | translate\">save</mat-icon>\n            <span class=\"button-label\">{{'SAVE.SAVE' | translate}}</span>\n        </button>\n      </span>\n    </mat-toolbar-row>\n  </mat-toolbar>\n\n  <mat-sidenav-container class=\"group-container\">\n    <mat-sidenav mode=\"side\" opened class=\"side_nav\" #sidenav #scrollMe>\n      <app-var-group [variableGroups]=\"variableGroups\" (subSetRows)=\"broadcastSubSetRows($event)\" (selectGroup)=\"broadcastSelect($event)\" (disableSelectGroup)=\"broadcastDeselectGroup()\"\n      (parentScrollNav)=\"scrollNav()\"\n      ></app-var-group>\n    </mat-sidenav>\n    <mat-sidenav-content>\n      <app-var [variableGroups]=\"variableGroups\"></app-var>\n    </mat-sidenav-content>\n  </mat-sidenav-container>\n\n</ng-container>\n"

/***/ }),

/***/ "./src/app/interface/interface.component.ts":
/*!**************************************************!*\
  !*** ./src/app/interface/interface.component.ts ***!
  \**************************************************/
/*! exports provided: InterfaceComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "InterfaceComponent", function() { return InterfaceComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _ddi_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../ddi.service */ "./src/app/ddi.service.ts");
/* harmony import */ var _assets_js_xml2json__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../assets/js/xml2json */ "./src/assets/js/xml2json.js");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm5/material.es5.js");
/* harmony import */ var _var_var_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ../var/var.component */ "./src/app/var/var.component.ts");
/* harmony import */ var file_saver__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! file-saver */ "./node_modules/file-saver/dist/FileSaver.min.js");
/* harmony import */ var file_saver__WEBPACK_IMPORTED_MODULE_6___default = /*#__PURE__*/__webpack_require__.n(file_saver__WEBPACK_IMPORTED_MODULE_6__);
/* harmony import */ var xml_writer__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! xml-writer */ "./node_modules/xml-writer/index.js");
/* harmony import */ var xml_writer__WEBPACK_IMPORTED_MODULE_7___default = /*#__PURE__*/__webpack_require__.n(xml_writer__WEBPACK_IMPORTED_MODULE_7__);
/* harmony import */ var _ngx_translate_core__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @ngx-translate/core */ "./node_modules/@ngx-translate/core/fesm5/ngx-translate-core.js");
/* harmony import */ var _var_group_var_group_component__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ../var-group/var-group.component */ "./src/app/var-group/var-group.component.ts");











var InterfaceComponent = /** @class */ (function () {
    function InterfaceComponent(ddiService, snackBar, translatePar) {
        this.ddiService = ddiService;
        this.snackBar = snackBar;
        this.translatePar = translatePar;
        this.data = null; // store the xml
        this.ddiLoaded = false; // show the loading
        this.variableGroups = []; // store the variables in an array display
        this._variables = []; // store the variables to be broadcast to child
        this._id = null; // file id
        this.metaId = null;
        this.baseUrl = null;
        this.siteUrl = null;
        this.dvLocale = null;
        this.translate = translatePar;
        this.translate.addLangs(['English', 'Français']);
        this.translate.setDefaultLang('English');
        var browserLang = this.translate.getBrowserLang();
        this.translate.use(browserLang.match(/English|Français/) ? browserLang : 'English');
    }
    InterfaceComponent.prototype.doSomething = function ($event) {
        console.log(this.child.varChange + " " + this.childGroups.groupChange);
        if ((this.child.varChange === true || this.childGroups.groupChange === true)) {
            $event.returnValue = 'You have unsaved changes - are you sure you want to exit?';
            return $event.returnValue;
        }
    };
    InterfaceComponent.prototype.ngOnInit = function () {
        var uri = null;
        this.siteUrl = this.ddiService.getParameterByName('siteUrl');
        this.baseUrl = this.ddiService.getBaseUrl();
        this._id = this.ddiService.getParameterByName('dfId');
        this.metaId = this.ddiService.getParameterByName('fileMetadataId');
        this.dvLocale = this.ddiService.getParameterByName('dvLocale');
        if (this.dvLocale != null) {
            if (this.dvLocale === 'en') {
                this.translate.use('English');
            }
            else if (this.dvLocale === 'fr') {
                this.translate.use('Français');
            }
            else {
                var browserLang = this.translate.getBrowserLang();
                this.translate.use(browserLang.match(/English|Français/) ? browserLang : 'English');
            }
        }
        else {
            var browserLang = this.translate.getBrowserLang();
            this.translate.use(browserLang.match(/English|Français/) ? browserLang : 'English');
        }
        if (!this.siteUrl && this._id != null) {
            uri = this.baseUrl + '/api/access/datafile/' + this._id + '/metadata/ddi';
            if (this.metaId != null) {
                uri = uri + '?fileMetadataId=' + this.metaId;
            }
        }
        else {
            if (this.siteUrl) {
                uri = this.siteUrl + '/api/access/datafile/' + this._id + '/metadata/ddi';
                if (this.metaId != null) {
                    uri = uri + '?fileMetadataId=' + this.metaId;
                }
            }
            else {
                // Just for testing purposes
                // uri = this.baseUrl + '/assets/FOCN_SPSS_20150525_FORMATTED-ddi.xml';
                if (!this._id) {
                    uri = window.location.href;
                    uri = uri + '/assets/test_groups.xml';
                    // uri = this.baseUrl + '/assets/arg-drones-E-2014-can.xml';
                }
                else {
                    uri = this.baseUrl + '/api/access/datafile/' + this._id + '/metadata/ddi';
                    if (this.metaId != null) {
                        uri = uri + '?fileMetadataId=' + this.metaId;
                    }
                }
            }
        }
        this.getDDI(uri);
        // this.translate.getLangs();
    };
    InterfaceComponent.prototype.getDDI = function (_uri) {
        var _this = this;
        var url = _uri;
        this.ddiService
            .getDDI(url)
            .subscribe(function (data) { return _this.processDDI(data); }, function (error) { return console.log(error); }, function () { return _this.completeDDI(); });
    };
    InterfaceComponent.prototype.scrollNav = function () {
        var elm = this.myScrollContainer['_elementRef'].nativeElement;
        elm.scrollTop = elm.scrollHeight;
    };
    InterfaceComponent.prototype.processDDI = function (data) {
        var parser = new DOMParser();
        this.data = parser.parseFromString(data, 'text/xml');
    };
    InterfaceComponent.prototype.completeDDI = function () {
        this.showVarsGroups();
        this.showVars();
        this.title = this.data
            .getElementsByTagName('stdyDscr')[0]
            .getElementsByTagName('titl')[0].textContent;
        var citation = this.data
            .getElementsByTagName('stdyDscr')[0]
            .getElementsByTagName('biblCit')[0].textContent;
        var start = citation.indexOf('http');
        var temp = citation.substr(start);
        var end = temp.indexOf(',');
        this.doi = temp.substr(0, end);
        this.firstCitat = citation.substr(0, start);
        this.firstCitat = this.firstCitat;
        this.secondCitat = temp.substr(end);
        this.secondCitat = this.secondCitat;
        this.filename = this.data
            .getElementsByTagName('fileDscr')[0]
            .getElementsByTagName('fileName')[0].textContent;
        this.showDDI();
        var agency = this.data.getElementsByTagName('IDNo')[0];
        var obj = JSON.parse(Object(_assets_js_xml2json__WEBPACK_IMPORTED_MODULE_3__["xml2json"])(agency, ''));
    };
    InterfaceComponent.prototype.showVarsGroups = function () {
        var elm = this.data.getElementsByTagName('varGrp');
        for (var _i = 0, elm_1 = elm; _i < elm_1.length; _i++) {
            var elmIn = elm_1[_i];
            var obj = JSON.parse(Object(_assets_js_xml2json__WEBPACK_IMPORTED_MODULE_3__["xml2json"])(elmIn, ''));
            if (typeof obj.varGrp['@var'] === 'undefined') {
                obj.varGrp['@var'] = '';
            }
            this.variableGroups.push(obj);
        }
    };
    InterfaceComponent.prototype.showVars = function () {
        var variables = [];
        var elm = this.data.getElementsByTagName('var');
        for (var _i = 0, elm_2 = elm; _i < elm_2.length; _i++) {
            var elm_in = elm_2[_i];
            variables.push(JSON.parse(Object(_assets_js_xml2json__WEBPACK_IMPORTED_MODULE_3__["xml2json"])(elm_in, '')));
        }
        // flatten the table structure so it can be sorted/filtered appropriately
        var flat_array = [];
        for (var i = 0; i < variables.length; i++) {
            var obj = variables[i];
            // make equivalent variable to allow sorting
            for (var j in obj.var) {
                if (j.indexOf('@') === 0) {
                    obj.var[j.substring(1).toLowerCase()] = obj.var[j];
                }
            }
            if (typeof obj.var.catgry !== 'undefined') {
                if (typeof obj.var.catgry.length === 'undefined') {
                    // If there is only one category
                    obj.var.catgry = [obj.var.catgry];
                }
                var sumCount = 0;
                for (var k = 0; k < obj.var.catgry.length; k++) {
                    if (typeof obj.var.catgry[k].catStat !== 'undefined') {
                        if (typeof obj.var.catgry[k].catStat.length === 'undefined') {
                            obj.var.catgry[k].catStat = [obj.var.catgry[k].catStat];
                        }
                        // tslint:disable-next-line:radix
                        sumCount = sumCount + parseInt(obj.var.catgry[k].catStat[0]['#text']);
                    }
                }
                for (var k = 0; k < obj.var.catgry.length; k++) {
                    if (typeof obj.var.catgry[k].catStat !== 'undefined') {
                        // tslint:disable-next-line:radix
                        obj.var.catgry[k].countPerc = parseInt(obj.var.catgry[k].catStat[0]['#text']) * 100 / sumCount;
                    }
                }
                obj.var.sumCount = sumCount;
            }
            if (typeof obj.var.universe !== 'undefined') {
                if (typeof obj.var.universe.size === 'undefined') {
                    obj.var.universe = { '#text': obj.var.universe };
                }
            }
            if (typeof obj.var.notes !== 'undefined') {
                if (typeof obj.var.notes.length !== undefined && obj.var.notes.length === 2) {
                    obj.var.notes = { '#cdata': obj.var.notes[1]['#cdata'],
                        '#text': obj.var.notes[0]['#text'],
                        '@level': obj.var.notes[0]['@level'],
                        '@subject': obj.var.notes[0]['@subject'],
                        '@type': obj.var.notes[0]['@type'] };
                }
            }
            flat_array.push(obj.var);
        }
        this._variables = flat_array;
        this.child.onUpdateVars(this._variables);
    };
    // pass the selected ids to the var table for display
    InterfaceComponent.prototype.broadcastSubSetRows = function (ids) {
        this.child.onSubset(ids);
    };
    InterfaceComponent.prototype.broadcastSelect = function (_id) {
        // set the var table header to show the selection
        this.child.selectGroup(_id);
    };
    InterfaceComponent.prototype.broadcastDeselectGroup = function () {
        this.child.disableSelectGroup();
    };
    InterfaceComponent.prototype.showDDI = function () {
        this.ddiLoaded = true;
    };
    // Create the XML File
    InterfaceComponent.prototype.makeXML = function (dv) {
        var doc = new xml_writer__WEBPACK_IMPORTED_MODULE_7__();
        doc.startDocument();
        doc.startElement('codeBook');
        var codeBook = this.data.getElementsByTagName('codeBook')[0];
        var obj = JSON.parse(Object(_assets_js_xml2json__WEBPACK_IMPORTED_MODULE_3__["xml2json"])(codeBook, ''));
        doc.writeAttribute('xmlns', obj.codeBook['@xmlns']);
        doc.writeAttribute('version', obj.codeBook['@version']);
        if (dv === false) {
            this.addStdyDscr(doc);
            this.addFileDscr(doc);
        }
        doc.startElement('dataDscr');
        // add groups
        for (var _i = 0, _a = this.variableGroups; _i < _a.length; _i++) {
            var group = _a[_i];
            if (group.varGrp.labl !== null && group.varGrp.labl.trim() !== '') {
                doc.startElement('varGrp');
                doc.writeAttribute('ID', group.varGrp['@ID']);
                doc.writeAttribute('var', group.varGrp['@var']);
                doc.startElement('labl');
                doc.text(group.varGrp.labl);
                doc.endElement();
                doc.endElement();
            }
        }
        // add variables
        for (var i = 0; i < this._variables.length; i++) {
            // start variable (var)
            doc.startElement('var');
            doc.writeAttribute('ID', this._variables[i]['@ID']);
            doc.writeAttribute('name', this._variables[i]['@name']);
            if (typeof this._variables[i]['@intrvl'] !== 'undefined') {
                doc.writeAttribute('intrvl', this._variables[i]['@intrvl']);
            }
            if (typeof this._variables[i]['@wgt'] !== 'undefined' && this._variables[i]['@wgt'] !== '') {
                doc.writeAttribute('wgt', this._variables[i]['@wgt']);
            }
            if (typeof this._variables[i]['@wgt-var'] !== 'undefined' && this._variables[i]['@wgt-var'] !== '') {
                doc.writeAttribute('wgt-var', this._variables[i]['@wgt-var']);
            }
            // start location
            if (typeof this._variables[i].location !== 'undefined') {
                doc.startElement('location').writeAttribute('fileid', this._variables[i].location['@fileid']);
                doc.endElement();
            }
            // end location
            // start labl
            if (typeof this._variables[i].labl !== 'undefined') {
                doc.startElement('labl');
                doc.writeAttribute('level', this._variables[i].labl['@level']);
                doc.text(this._variables[i].labl['#text']);
                doc.endElement();
            }
            // end labl
            // start sumStat
            if (typeof this._variables[i].sumStat !== 'undefined') {
                if (typeof this._variables[i].sumStat.length !== 'undefined') {
                    for (var j = 0; j < this._variables[i].sumStat.length; j++) {
                        doc.startElement('sumStat');
                        doc.writeAttribute('type', this._variables[i].sumStat[j]['@type']);
                        doc.text(this._variables[i].sumStat[j]['#text']);
                        doc.endElement();
                    }
                }
            }
            // end sumStat
            // start catgry
            if (typeof this._variables[i].catgry !== 'undefined') {
                if (typeof this._variables[i].catgry.length !== 'undefined') {
                    for (var j = 0; j < this._variables[i].catgry.length; j++) {
                        doc.startElement('catgry');
                        if (typeof this._variables[i].catgry[j].catValu !== 'undefined') {
                            doc.startElement('catValu').text(this._variables[i].catgry[j].catValu);
                            doc.endElement();
                        }
                        if (typeof this._variables[i].catgry[j].labl !== 'undefined') {
                            doc.startElement('labl');
                            doc.writeAttribute('level', this._variables[i].catgry[j].labl['@level']);
                            doc.text(this._variables[i].catgry[j].labl['#text']);
                            doc.endElement();
                        }
                        if (typeof this._variables[i].catgry[j].catStat !== 'undefined') {
                            // frequency
                            if (typeof this._variables[i].catgry[j].catStat.length !== 'undefined') {
                                doc.startElement('catStat');
                                doc.writeAttribute('type', this._variables[i].catgry[j].catStat[0]['@type']);
                                doc.text(this._variables[i].catgry[j].catStat[0]['#text']);
                                doc.endElement();
                                // weighted frequency
                                if (this._variables[i].catgry[j].catStat.length > 1) {
                                    doc.startElement('catStat');
                                    doc.writeAttribute('wgtd', this._variables[i].catgry[j].catStat[1]['@wgtd']);
                                    doc.writeAttribute('type', this._variables[i].catgry[j].catStat[1]['@type']);
                                    doc.text(this._variables[i].catgry[j].catStat[1]['#text']);
                                    doc.endElement();
                                }
                            }
                        }
                        doc.endElement();
                    }
                }
            }
            // end catgry
            // start qstn
            if (typeof this._variables[i].qstn !== 'undefined' &&
                ((typeof this._variables[i].qstn.qstnLit !== 'undefined' && this._variables[i].qstn.qstnLit !== '') ||
                    (typeof this._variables[i].qstn.ivuInstr !== 'undefined' && this._variables[i].qstn.ivuInstr !== '') ||
                    (typeof this._variables[i].qstn.postQTxt !== 'undefined' && this._variables[i].qstn.postQTxt !== ''))) {
                doc.startElement('qstn');
                if (typeof this._variables[i].qstn.qstnLit !== 'undefined') {
                    doc.startElement('qstnLit').text(this._variables[i].qstn.qstnLit);
                    doc.endElement();
                }
                if (typeof this._variables[i].qstn.ivuInstr !== 'undefined') {
                    doc.startElement('ivuInstr').text(this._variables[i].qstn.ivuInstr);
                    doc.endElement();
                }
                if (typeof this._variables[i].qstn.postQTxt !== 'undefined') {
                    doc.startElement('postQTxt').text(this._variables[i].qstn.postQTxt);
                    doc.endElement();
                }
                doc.endElement();
            }
            // end qstn
            // start varFormat
            if (typeof this._variables[i].varFormat !== 'undefined') {
                doc.startElement('varFormat');
                doc.writeAttribute('type', this._variables[i].varFormat['@type']);
                doc.endElement();
            }
            // end varFormat
            // start notes
            if (typeof this._variables[i].notes !== 'undefined') {
                // start notes cdata
                if (typeof this._variables[i].notes['#cdata'] !== 'undefined' && this._variables[i].notes['#cdata'] !== '') {
                    doc.startElement('notes');
                    doc.startCData();
                    doc.writeCData(this._variables[i].notes['#cdata']);
                    doc.endCData();
                    doc.endElement();
                }
                // end notes cdata
                doc.startElement('notes');
                doc.writeAttribute('subject', this._variables[i].notes['@subject']);
                doc.writeAttribute('level', this._variables[i].notes['@level']);
                doc.writeAttribute('type', this._variables[i].notes['@type']);
                doc.text(this._variables[i].notes['#text']);
                doc.endElement();
            }
            // end notes
            // start universe
            if (typeof this._variables[i].universe !== 'undefined' && this._variables[i].universe['#text'] !== '') {
                doc.startElement('universe');
                doc.text(this._variables[i].universe['#text']);
                doc.endElement();
            }
            // end universe
            // end variable (var)
            doc.endElement();
        }
        doc.endElement();
        doc.endDocument();
        return doc;
    };
    InterfaceComponent.prototype.addStdyDscr = function (doc) {
        doc.startElement('stdyDscr');
        doc.startElement('citation');
        doc.startElement('titlStmt');
        var titl = this.data.getElementsByTagName('titl')[0].textContent;
        doc.startElement('titl').text(titl);
        doc.endElement(); // end titl
        doc.startElement('IDNo');
        var agency = this.data.getElementsByTagName('IDNo')[0];
        var obj = JSON.parse(Object(_assets_js_xml2json__WEBPACK_IMPORTED_MODULE_3__["xml2json"])(agency, ''));
        doc.writeAttribute('agency', obj.IDNo['@agency']).text(obj.IDNo['#text']);
        doc.endElement('IDNo'); // end IDNo
        doc.endElement(); // end titlStmt
        doc.startElement('rspStmt');
        var AuthEnty = this.data.getElementsByTagName('AuthEnty')[0].textContent;
        doc.startElement('AuthEnty').text(AuthEnty);
        doc.endElement(); // end AuthEnty
        doc.endElement(); // end rspStmt
        var biblCit = this.data.getElementsByTagName('biblCit')[0].textContent;
        doc.startElement('biblCit').text(biblCit);
        doc.endElement(); // biblCit
        doc.endElement(); // end citation
        doc.endElement(); // end stdyDscr
    };
    InterfaceComponent.prototype.addFileDscr = function (doc) {
        doc.startElement('fileDscr');
        var fileDscr = this.data.getElementsByTagName('fileDscr')[0];
        var obj = JSON.parse(Object(_assets_js_xml2json__WEBPACK_IMPORTED_MODULE_3__["xml2json"])(fileDscr, ''));
        doc.writeAttribute('ID', obj.fileDscr['@ID']);
        doc.startElement('fileTxt');
        var fileName = this.data.getElementsByTagName('fileName')[0].textContent;
        doc.startElement('fileName').text(fileName);
        doc.endElement(); // end fileName
        doc.startElement('dimensns');
        doc.startElement('caseQnty').text(obj.fileDscr.fileTxt.dimensns.caseQnty);
        doc.endElement(); // end caseQnty
        doc.startElement('varQnty').text(obj.fileDscr.fileTxt.dimensns.varQnty);
        doc.endElement(); // end varQnty
        doc.endElement(); // end dimensns
        doc.startElement('fileType').text(obj.fileDscr.fileTxt.fileType);
        doc.endElement(); // fileType
        doc.endElement(); // fileTxt
        var notes = fileDscr.getElementsByTagName('notes');
        console.log(notes);
        for (var _i = 0, notes_1 = notes; _i < notes_1.length; _i++) {
            var note = notes_1[_i];
            var newNote = JSON.parse(Object(_assets_js_xml2json__WEBPACK_IMPORTED_MODULE_3__["xml2json"])(note, ''));
            console.log(newNote);
            doc.startElement('notes');
            doc.writeAttribute('level', newNote.notes['@level']);
            doc.writeAttribute('type', newNote.notes['@type']);
            doc.writeAttribute('subject', newNote.notes['@subject']);
            doc.text(newNote.notes['#text']);
            doc.endElement(); // end notes
        }
        doc.endElement(); // end fileDscr
    };
    // Save the XML file locally
    InterfaceComponent.prototype.onSave = function () {
        var dv = false;
        var doc = this.makeXML(dv);
        var text = new Blob([doc.toString()], { type: 'application/xml' });
        var tl = this.title + '.xml';
        file_saver__WEBPACK_IMPORTED_MODULE_6__["saveAs"](text, 'dct.xml');
    };
    // Send the XML to Dataverse
    InterfaceComponent.prototype.sendToDV = function () {
        var _this = this;
        var key = this.ddiService.getParameterByName('key');
        var dv = true;
        var doc = this.makeXML(dv);
        var url = null;
        if (key !== null) {
            if (this.siteUrl !== null) {
                url = this.siteUrl + '/api/edit/' + this._id; // + "/" + this.metaId;
            }
            else {
                url = this.baseUrl + '/api/edit/' + this._id;
            }
            this.ddiService
                .putDDI(url, doc.toString(), key)
                .subscribe(function (data) {
                console.log('Data ');
                console.log(data);
            }, function (error) {
                _this.snackBar.open(_this.translate.instant('SAVE.CANNOT') + error, '', {
                    duration: 2000
                });
                console.log('Error');
                console.log(error);
            }, function () {
                // console.log('Ok');
                _this.snackBar.open(_this.translate.instant('SAVE.SAVED'), '', {
                    duration: 2000
                });
                _this.child.varChange = false;
                _this.childGroups.groupChange = false;
            });
        }
        else {
            this.snackBar.open(this.translate.instant('SAVE.MISSAPI'), '', {
                duration: 2000
            });
            console.log('API Key missing');
        }
    };
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ViewChild"])(_var_var_component__WEBPACK_IMPORTED_MODULE_5__["VarComponent"]),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", Object)
    ], InterfaceComponent.prototype, "child", void 0);
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ViewChild"])(_var_group_var_group_component__WEBPACK_IMPORTED_MODULE_9__["VarGroupComponent"]),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", Object)
    ], InterfaceComponent.prototype, "childGroups", void 0);
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ViewChild"])('scrollMe'),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_core__WEBPACK_IMPORTED_MODULE_1__["ElementRef"])
    ], InterfaceComponent.prototype, "myScrollContainer", void 0);
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["HostListener"])('window:beforeunload', ['$event']),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", Function),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [Object]),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:returntype", void 0)
    ], InterfaceComponent.prototype, "doSomething", null);
    InterfaceComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-interface',
            template: __webpack_require__(/*! ./interface.component.html */ "./src/app/interface/interface.component.html"),
            styles: [__webpack_require__(/*! ./interface.component.css */ "./src/app/interface/interface.component.css")]
        }),
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
            imports: [_angular_material__WEBPACK_IMPORTED_MODULE_4__["MatButtonModule"]],
            exports: [_angular_material__WEBPACK_IMPORTED_MODULE_4__["MatButtonModule"]]
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_ddi_service__WEBPACK_IMPORTED_MODULE_2__["DdiService"],
            _angular_material__WEBPACK_IMPORTED_MODULE_4__["MatSnackBar"],
            _ngx_translate_core__WEBPACK_IMPORTED_MODULE_8__["TranslateService"]])
    ], InterfaceComponent);
    return InterfaceComponent;
}());



/***/ }),

/***/ "./src/app/mat-paginator-intl.ts":
/*!***************************************!*\
  !*** ./src/app/mat-paginator-intl.ts ***!
  \***************************************/
/*! exports provided: MyMatPaginatorIntl */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MyMatPaginatorIntl", function() { return MyMatPaginatorIntl; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm5/material.es5.js");


var MyMatPaginatorIntl = /** @class */ (function (_super) {
    tslib__WEBPACK_IMPORTED_MODULE_0__["__extends"](MyMatPaginatorIntl, _super);
    function MyMatPaginatorIntl(translateService, translateParser) {
        var _this = _super.call(this) || this;
        _this.translateService = translateService;
        _this.translateParser = translateParser;
        _this.getRangeLabel = function (page, pageSize, length) {
            length = Math.max(length, 0);
            var startIndex = (page) * pageSize + 1;
            var endIndex = startIndex <= length ?
                Math.min(startIndex + pageSize - 1, length) :
                startIndex + pageSize - 1;
            return _this.translateParser.interpolate(_this.rangeLabelIntl, { startIndex: startIndex, endIndex: endIndex, length: length });
        };
        _this.getTranslations();
        _this.translateService.onLangChange.subscribe(function () { return _this.getTranslations(); });
        return _this;
    }
    MyMatPaginatorIntl.prototype.getTranslations = function () {
        var _this = this;
        this.translateService.get([
            'PAGINATOR.ITEMS_PER_PAGE',
            'PAGINATOR.NEXT_PAGE',
            'PAGINATOR.PREVIOUS_PAGE',
            'PAGINATOR.RANGE'
        ])
            .subscribe(function (translation) {
            _this.itemsPerPageLabel = translation['PAGINATOR.ITEMS_PER_PAGE'];
            _this.nextPageLabel = translation['PAGINATOR.NEXT_PAGE'];
            _this.previousPageLabel = translation['PAGINATOR.PREVIOUS_PAGE'];
            _this.rangeLabelIntl = translation['PAGINATOR.RANGE'];
            _this.changes.next();
        });
    };
    return MyMatPaginatorIntl;
}(_angular_material__WEBPACK_IMPORTED_MODULE_1__["MatPaginatorIntl"]));



/***/ }),

/***/ "./src/app/var-dialog/var-dialog.component.css":
/*!*****************************************************!*\
  !*** ./src/app/var-dialog/var-dialog.component.css ***!
  \*****************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".text-inside-grid > div {\n  justify-content: flex-start !important;\n}\n\n.field_width {\n  width: 480px !important;\n}\n\n.textarea_height {\n  height: 80px;\n}\n\n.mat-dialog-content {\n  overflow: visible;\n}\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbImFwcC92YXItZGlhbG9nL3Zhci1kaWFsb2cuY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtFQUNFLHNDQUFzQztBQUN4Qzs7QUFFQTtFQUNFLHVCQUF1QjtBQUN6Qjs7QUFFQTtFQUNFLFlBQVk7QUFDZDs7QUFFQTtFQUNFLGlCQUFpQjtBQUNuQiIsImZpbGUiOiJhcHAvdmFyLWRpYWxvZy92YXItZGlhbG9nLmNvbXBvbmVudC5jc3MiLCJzb3VyY2VzQ29udGVudCI6WyIudGV4dC1pbnNpZGUtZ3JpZCA+IGRpdiB7XG4gIGp1c3RpZnktY29udGVudDogZmxleC1zdGFydCAhaW1wb3J0YW50O1xufVxuXG4uZmllbGRfd2lkdGgge1xuICB3aWR0aDogNDgwcHggIWltcG9ydGFudDtcbn1cblxuLnRleHRhcmVhX2hlaWdodCB7XG4gIGhlaWdodDogODBweDtcbn1cblxuLm1hdC1kaWFsb2ctY29udGVudCB7XG4gIG92ZXJmbG93OiB2aXNpYmxlO1xufVxuIl19 */"

/***/ }),

/***/ "./src/app/var-dialog/var-dialog.component.html":
/*!******************************************************!*\
  !*** ./src/app/var-dialog/var-dialog.component.html ***!
  \******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div mat-dialog-content >\n  <form [formGroup]=\"form\" (ngSubmit)=\"submit(form)\">\n    <h1 mat-dialog-title>{{'VARDIALOG.VARINFO' | translate}}</h1>\n\n    <mat-dialog-content>\n      <mat-grid-list class=\"table-controls\" cols=\"2\" rowHeight=\"62\">\n        <mat-grid-tile colspan=\"1\" rowspan=\"1\">\n          <mat-form-field>\n            <input matInput formControlName=\"id\" placeholder=\"{{'VAR.ID' | translate }}\" value=\"{{data['@ID']}}\"  >\n          </mat-form-field>\n        </mat-grid-tile>\n        <mat-grid-tile colspan=\"1\" rowspan=\"1\">\n          <mat-form-field>\n            <input matInput formControlName=\"name\" placeholder=\"{{'VAR.NAME' | translate}}\" value=\"{{data['@name']}}\" >\n          </mat-form-field>\n        </mat-grid-tile>\n\n        <mat-grid-tile colspan=\"2\" rowspan=\"1\">\n          <mat-form-field  class=\"field_width\">\n             <input matInput formControlName=\"labl\" placeholder=\"{{'VAR.LABEL' | translate}}\" value=\"{{data.labl['#text']}}\">\n          </mat-form-field>\n        </mat-grid-tile>\n        <mat-grid-tile colspan=\"2\" rowspan=\"1\">\n          <mat-form-field  class=\"field_width\">\n            <input matInput formControlName=\"qstnLit\" placeholder=\"{{'VARDIALOG.LITQ' | translate}}\" value=\"{{data.qstn.qstnLit}}\">\n          </mat-form-field>\n        </mat-grid-tile>\n\n        <mat-grid-tile colspan=\"2\" rowspan=\"1\">\n          <mat-form-field  class=\"field_width\">\n            <input matInput formControlName=\"ivuInstr\" placeholder=\"{{'VARDIALOG.INTINSTR' | translate}}\" value=\"{{data.qstn.ivuInstr}}\">\n          </mat-form-field>\n        </mat-grid-tile>\n        <mat-grid-tile colspan=\"2\" rowspan=\"1\">\n          <mat-form-field  class=\"field_width\">\n            <input matInput formControlName=\"postQTxt\" placeholder=\"{{'VARDIALOG.POSTQ' | translate}}\" value=\"{{data.qstn.postQTxt}}\">\n          </mat-form-field>\n        </mat-grid-tile>\n        <mat-grid-tile colspan=\"2\" rowspan=\"1\">\n          <mat-form-field  class=\"field_width\">\n            <input matInput formControlName=\"universe\" placeholder=\"{{'VARDIALOG.UNIVERSE' | translate}}\" value=\"{{data.universe['#text']}}\">\n          </mat-form-field>\n        </mat-grid-tile>\n        <mat-grid-tile colspan=\"2\" rowspan=\"2\">\n          <mat-form-field class=\"field_width\" >\n            <textarea matInput formControlName=\"notes\" class=\"textarea_height\" placeholder=\"{{'VARDIALOG.NOTES' | translate}}\" value=\"{{data.notes['#cdata']}}\" ></textarea>\n          </mat-form-field>\n        </mat-grid-tile>\n        <!--Group chip list-->\n        <mat-grid-tile colspan=\"2\" rowspan=\"1\" class=\"field_width\">\n          <mat-form-field class=\"field_width\">\n            <mat-select formControlName=\"_groups\" [(value)]=\"data['_groups']\"  placeholder=\"{{'VARDIALOG.GROUP' | translate}}\" multiple=\"true\">\n              <mat-option *ngFor=\"let g of variableGroups\" [value]=\"g.varGrp['@ID']\" >\n                {{ g.varGrp.labl}}\n              </mat-option>\n            </mat-select>\n          </mat-form-field>\n        </mat-grid-tile>\n\n        <mat-grid-tile colspan=\"1\" rowspan=\"1\">\n          <mat-form-field>\n            <mat-select formControlName=\"wgt_var\" [(value)]=\"data['@wgt-var']\" placeholder=\"{{'VARDIALOG.WEIGHTVAR' | translate}}\">\n              <mat-option *ngFor=\"let w of weights\" [value]=\"w['@ID']\">\n                {{ w['@name'] }}\n              </mat-option>\n              <mat-option>{{'VARDIALOG.UNWEIGHTED' | translate}}</mat-option>\n            </mat-select>\n          </mat-form-field>\n        </mat-grid-tile>\n        <mat-grid-tile colspan=\"1\" rowspan=\"1\">\n          <section class=\"example-section\">\n            <mat-checkbox formControlName=\"wgt\" align=\"end\" checked=\"{{data['@wgt']}}\">{{'VARDIALOG.ISWEIGHT' | translate}}</mat-checkbox>\n          </section>\n        </mat-grid-tile>\n        <mat-grid-tile colspan=\"1\" rowspan=\"1\">\n          <button mat-raised-button type=\"submit\">{{'VARDIALOG.UPDATE' | translate}}</button>\n        </mat-grid-tile>\n        <mat-grid-tile colspan=\"1\" rowspan=\"1\">\n          <button mat-raised-button type=\"button\" (click)=\"cancel()\">{{'VARDIALOG.CANCEL' | translate}}</button>\n        </mat-grid-tile>\n      </mat-grid-list>\n    </mat-dialog-content>\n  </form>\n</div>\n"

/***/ }),

/***/ "./src/app/var-dialog/var-dialog.component.ts":
/*!****************************************************!*\
  !*** ./src/app/var-dialog/var-dialog.component.ts ***!
  \****************************************************/
/*! exports provided: VarDialogComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "VarDialogComponent", function() { return VarDialogComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm5/material.es5.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _ddi_service__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../ddi.service */ "./src/app/ddi.service.ts");





var VarDialogComponent = /** @class */ (function () {
    function VarDialogComponent(data, formBuilder, dialogRef, ddiService) {
        this.data = data;
        this.formBuilder = formBuilder;
        this.dialogRef = dialogRef;
        this.ddiService = ddiService;
        this.parentUpdateVar = new _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"]();
        this.editObjs = [];
    }
    VarDialogComponent.prototype.ngOnInit = function () {
        // Note: data is passed as any array to allow for multi editing
        if (this.data.length > 1) {
            var selectedIds = [];
            for (var j = 0; j < this.data.length; j++) {
                selectedIds.push(this.data[j]['@ID']);
            }
            var obj = {
                '@ID': selectedIds.join(','),
                _group_edit: true
            };
            this.editObjs = this.data;
            this.data = obj;
        }
        else {
            this.data = this.data[0];
        }
        // add the groups - create an id with all of them
        var groups = [];
        for (var i = 0; i < this.variableGroups.length; i++) {
            var groupVars = this.variableGroups[i].varGrp['@var'].split(' ');
            if (groupVars.indexOf(this.data['@ID']) > -1) {
                groups.push(this.variableGroups[i].varGrp['@ID']);
            }
        }
        this.data['_groups'] = [groups]; // groups;
        this.addOmittedProperties(this.data);
        this.form = this.formBuilder.group({
            id: [
                { value: this.data ? this.data['@ID'] : '', disabled: true },
                _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required
            ],
            name: [
                { value: this.data ? this.data['@name'] : '', disabled: true },
                _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required
            ],
            labl: [this.data ? this.data['labl']['#text'] : '', _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required],
            qstnLit: this.data ? this.data.qstn['qstnLit'] : '',
            universe: this.data ? this.data.universe['#text'] : '',
            ivuInstr: this.data ? this.data.qstn['ivuInstr'] : '',
            postQTxt: this.data ? this.data.qstn['postQTxt'] : '',
            notes: this.data ? this.data.notes['#cdata'] : '',
            wgt: this.data ? this.data['@wgt'] : '',
            wgt_var: this.data ? this.data['@wgt-var'] : '',
            _groups: this.data ? this.data['_groups'] : []
        });
        this.originalVarWeight = this.data['@wgt-var'];
    };
    VarDialogComponent.prototype.isSelected = function (_id) {
        return true;
    };
    VarDialogComponent.prototype.addOmittedProperties = function (_obj) {
        if (typeof _obj.qstn === 'undefined') {
            _obj.qstn = {};
        }
        if (typeof _obj.labl === 'undefined') {
            _obj.labl = { '#text': '' };
        }
        if (typeof _obj.universe === 'undefined') {
            _obj.universe = {
                '#text': '',
                '@clusion': ''
            };
        }
        if (typeof _obj.notes === 'undefined') {
            _obj.notes = {
                '#cdata': ''
            };
        }
        return _obj;
    };
    VarDialogComponent.prototype.updateObjValues = function (_obj, form) {
        // update label
        this.updateObjValue(_obj, 'labl.#text', form.controls.labl);
        // Literal Question - data.qstn.qstnLit
        this.updateObjValue(_obj, 'qstn.qstnLit', form.controls.qstnLit);
        // Interviewer Instructions - data.qstn.ivuInstr
        this.updateObjValue(_obj, 'qstn.ivuInstr', form.controls.ivuInstr);
        // Post Question - data.qstn.postQTxt
        this.updateObjValue(_obj, 'qstn.postQTxt', form.controls.postQTxt);
        // Universe - data.universe
        this.updateObjValue(_obj, 'universe.#text', form.controls.universe);
        // update notes if available data.notes
        // TODO surround notes with <![CDATA[ before saving back to xml
        this.updateObjValue(_obj, 'notes.#cdata', form.controls.notes);
        //
        this.updateObjValue(_obj, '@wgt-var', form.controls.wgt_var);
        //
        console.log(form.controls.wgt.value);
        if (form.controls.wgt.value === true || form.controls.wgt.value === 'wgt') {
            _obj['@wgt'] = 'wgt';
        }
        else {
            _obj['@wgt'] = '';
            // this.removeWeightedFrequencies(_obj);
        }
        // add (remove) variable to (from) group
        if (form.controls._groups.dirty === true) {
            this.removeVarFromGroups(_obj['@ID']);
            // add
            for (var i = 0; i < form.controls._groups.value.length; i++) {
                var group = this.getVariableGroup(form.controls._groups.value[i]);
                if (group['@var'] !== '') {
                    group['@var'] = group['@var'] + ' ' + _obj['@ID'];
                }
                else {
                    group['@var'] = _obj['@ID'];
                }
            }
        }
        return _obj;
    };
    VarDialogComponent.prototype.removeVarFromGroups = function (varId) {
        for (var _i = 0, _a = this.variableGroups; _i < _a.length; _i++) {
            var varGrp = _a[_i];
            console.log(varGrp.varGrp['@var']);
            if (varGrp.varGrp['@var'].indexOf(varId) === 0) {
                varGrp.varGrp['@var'] = varGrp.varGrp['@var'].replace(varId, '');
            }
            else if (varGrp.varGrp['@var'].indexOf(varId) !== -1) {
                varGrp.varGrp['@var'] = varGrp.varGrp['@var'].replace(' ' + varId, '');
            }
        }
    };
    VarDialogComponent.prototype.getVariableGroup = function (_id) {
        // loop though all the variables in the variable groups and create a complete list
        for (var i = 0; i < this.variableGroups.length; i++) {
            if (this.variableGroups[i].varGrp['@ID'] === _id) {
                return this.variableGroups[i].varGrp;
            }
        }
    };
    VarDialogComponent.prototype.updateObjValue = function (_obj, _var, _control) {
        if (_control.dirty === true) {
            var stack = _var.split('.');
            while (stack.length > 1) {
                _obj = _obj[stack.shift()];
            }
            _obj[stack.shift()] = _control.value;
        }
    };
    VarDialogComponent.prototype.submit = function (form) {
        if (this.editObjs.length > 0) {
            // take all the objects from the
            for (var _i = 0, _a = this.editObjs; _i < _a.length; _i++) {
                var i = _a[_i];
                this.data = i;
                this.addOmittedProperties(this.data);
                this.updateObjValues(this.data, form);
                this.parentUpdateVar.emit(this.data);
            }
        }
        else {
            this.updateObjValues(this.data, form);
            this.parentUpdateVar.emit(this.data);
        }
        if (typeof this.data['@wgt-var'] !== 'undefined') {
            this.calculateWeightedFrequencies();
        }
        else {
            // Removing weighted frequency
            if (typeof this.data.catgry !== 'undefined') {
                for (var k = 0; k < this.data.catgry.length; k++) {
                    if (typeof this.data.catgry[k].catStat !== 'undefined' &&
                        this.data.catgry[k].catStat.length > 1) {
                        this.data.catgry[k].catStat.splice(1, 1);
                    }
                }
            }
        }
        this.dialogRef.close("" + form);
    };
    VarDialogComponent.prototype.cancel = function () {
        this.dialogRef.close();
        this.data['@wgt-var'] = this.originalVarWeight;
    };
    VarDialogComponent.prototype.calculateWeightedFrequencies = function () {
        var _this = this;
        var weightVariable = this.data['@wgt-var'];
        var variable = this.data['@ID'];
        if (typeof weightVariable !== 'undefined') {
            var id = this.ddiService.getParameterByName('dfId');
            var baseUrl = this.ddiService.getBaseUrl();
            var key = this.ddiService.getParameterByName('key');
            var siteUrl = null;
            siteUrl = this.ddiService.getParameterByName('siteUrl');
            var detailUrl = null;
            console.log(siteUrl);
            if (!siteUrl) {
                detailUrl =
                    baseUrl +
                        '/api/access/datafile/' +
                        id +
                        '?format=subset&variables=' +
                        variable +
                        ',' +
                        weightVariable +
                        '&key=' +
                        key;
            }
            else {
                detailUrl =
                    siteUrl +
                        '/api/access/datafile/' +
                        id +
                        '?format=subset&variables=' +
                        variable +
                        ',' +
                        weightVariable +
                        '&key=' +
                        key;
            }
            this.ddiService
                .getDDI(detailUrl)
                .subscribe(function (data) { return _this.processVariable(data); }, function (error) { return console.log(error); }, function () { return _this.completeVariable(); });
            //  http://localhost:8080/api/access/datafile/41?variables=v885
        }
    };
    VarDialogComponent.prototype.processVariable = function (data) {
        this.weightsAndVariable = data.split('\n');
    };
    VarDialogComponent.prototype.completeVariable = function () {
        var map_wf = new Map();
        for (var i = 1; i < this.weightsAndVariable.length; i++) {
            var vr = this.weightsAndVariable[i].split('\t');
            if (map_wf.has(vr[0])) {
                var wt = map_wf.get(vr[0]);
                wt = parseFloat(wt) + parseFloat(vr[1]);
                map_wf.set(vr[0], wt);
            }
            else {
                map_wf.set(vr[0], vr[1]);
            }
        }
        for (var i = 0; i < this.data.catgry.length; i++) {
            if (!map_wf.has(this.data.catgry[i].catValu)) {
                map_wf.set(this.data.catgry[i].catValu, 0);
            }
            if (typeof this.data.catgry[i].catStat !== 'undefined') {
                if (typeof this.data.catgry[i].catStat.length !== 'undefined') {
                    if (this.data.catgry[i].catStat.length > 1) {
                        this.data.catgry[i].catStat[1] = {
                            '@wgtd': 'wgtd',
                            '@type': 'freq',
                            '#text': map_wf.get(this.data.catgry[i].catValu)
                        };
                    }
                    else {
                        this.data.catgry[i].catStat.push({
                            '@wgtd': 'wgtd',
                            '@type': 'freq',
                            '#text': map_wf.get(this.data.catgry[i].catValu)
                        });
                    }
                }
            }
        }
    };
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Output"])(),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"])
    ], VarDialogComponent.prototype, "parentUpdateVar", void 0);
    VarDialogComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-var-dialog',
            template: __webpack_require__(/*! ./var-dialog.component.html */ "./src/app/var-dialog/var-dialog.component.html"),
            styles: [__webpack_require__(/*! ./var-dialog.component.css */ "./src/app/var-dialog/var-dialog.component.css")]
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__param"](0, Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Inject"])(_angular_material__WEBPACK_IMPORTED_MODULE_2__["MAT_DIALOG_DATA"])),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [Object, _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormBuilder"],
            _angular_material__WEBPACK_IMPORTED_MODULE_2__["MatDialogRef"],
            _ddi_service__WEBPACK_IMPORTED_MODULE_4__["DdiService"]])
    ], VarDialogComponent);
    return VarDialogComponent;
}());



/***/ }),

/***/ "./src/app/var-group/var-group.component.css":
/*!***************************************************!*\
  !*** ./src/app/var-group/var-group.component.css ***!
  \***************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".groups-list {\n    padding-top: 0;\n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbImFwcC92YXItZ3JvdXAvdmFyLWdyb3VwLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7SUFDSSxjQUFjO0FBQ2xCIiwiZmlsZSI6ImFwcC92YXItZ3JvdXAvdmFyLWdyb3VwLmNvbXBvbmVudC5jc3MiLCJzb3VyY2VzQ29udGVudCI6WyIuZ3JvdXBzLWxpc3Qge1xuICAgIHBhZGRpbmctdG9wOiAwO1xufSJdfQ== */"

/***/ }),

/***/ "./src/app/var-group/var-group.component.html":
/*!****************************************************!*\
  !*** ./src/app/var-group/var-group.component.html ***!
  \****************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<mat-nav-list class=\"groups-list\">\n  <a mat-list-item (click)=\"addTab()\" class=\"button-header\">\n    <span>{{'GROUPS.ADD' | translate}}</span>\n    <span class=\"fill-space\"></span>\n    <mat-icon>add</mat-icon>\n  </a>\n  <mat-divider></mat-divider>\n  <a mat-list-item (click)=\"showAll()\" [ngClass]=\"{'active': allActive }\">{{'GROUPS.ALLVAR' | translate}}</a>\n  <mat-divider></mat-divider>\n  <a mat-list-item\n  id=\"{{tab.varGrp['@ID']}}\"\n  [disableRipple]=\"true\"\n  *ngFor=\"let tab of variableGroups\"\n  (click)=\"onGroupClick(tab)\"\n  (dblclick)=\"onGroupDblClick(tab)\"\n  [ngClass]=\"{'active': tab.active }\"\n  >\n    <span *ngIf=\"!tab.editable\">{{tab.varGrp.labl}}</span>\n    <mat-form-field\n    *ngIf=\"tab.editable\"\n    class=\"fixed_width\"\n    >\n      <input matInput\n      maxLength=\"50\"\n      value=\"{{tab.varGrp.labl}}\"\n      #titleInput\n      (keyup.enter)=\"renameGroupComplete(tab,titleInput.value)\"\n      >\n      <span button mat-icon-button matSuffix color=\"primary\">\n        <mat-icon [attr.aria-label]=\"'GROUPS.DONE | translate'\" matTooltip=\"{{'GROUPS.SAVE' | translate }}\" (click)=\"renameGroupComplete(tab,titleInput.value )\">done</mat-icon>\n      </span>\n      <span button mat-icon-button matSuffix color=\"primary\">\n        <mat-icon [attr.aria-label]=\"'GROUPS.CANCEL' | translate\" matTooltip=\"{{'GROUPS.CANCEL' | translate }}\" (click)=\"renameGroupCancel(tab)\">clear</mat-icon>\n      </span>\n      <span button mat-icon-button matSuffix color=\"primary\">\n        <mat-icon [attr.aria-label]=\"'GROUPS.DELETE' | translate\" matTooltip=\"{{'GROUPS.DELETE' | translate }}\" (click)=\"groupDelete(tab)\">delete</mat-icon>\n      </span>\n    </mat-form-field>\n    <span class=\"fill-space\"></span>\n    <button *ngIf=\"!tab.editable\" mat-icon-button color=\"primary\">\n      <mat-icon [attr.aria-label]=\"'GROUPS.EDIT' | translate\" matTooltip=\"{{'GROUPS.EDIT' | translate }}\" (click)=\"renameGroup(tab)\">edit</mat-icon>\n    </button>\n  </a>\n</mat-nav-list>\n"

/***/ }),

/***/ "./src/app/var-group/var-group.component.ts":
/*!**************************************************!*\
  !*** ./src/app/var-group/var-group.component.ts ***!
  \**************************************************/
/*! exports provided: VarGroupComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "VarGroupComponent", function() { return VarGroupComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _ddi_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../ddi.service */ "./src/app/ddi.service.ts");
/* harmony import */ var _ngx_translate_core__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @ngx-translate/core */ "./node_modules/@ngx-translate/core/fesm5/ngx-translate-core.js");




var VarGroupComponent = /** @class */ (function () {
    function VarGroupComponent(ddiService, translate) {
        this.ddiService = ddiService;
        this.translate = translate;
        this.allActive = true;
        this.subSetRows = new _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"]();
        this.parentScrollNav = new _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"]();
        this.selectGroup = new _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"]();
        this.disableSelectGroup = new _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"]();
        this.groupChange = false;
    }
    VarGroupComponent.prototype.ngOnInit = function () { };
    // Add a new group to the list and scroll to show it
    VarGroupComponent.prototype.addTab = function () {
        var numberOfGroups = this.variableGroups.length;
        if (numberOfGroups === 0 || (this.variableGroups[numberOfGroups - 1].varGrp.labl !== 'undefined' && this.variableGroups[numberOfGroups - 1].varGrp.labl.trim() !== '')) {
            // get the next id
            var ids = [];
            for (var _i = 0, _a = this.variableGroups; _i < _a.length; _i++) {
                var i = _a[_i];
                ids.push(Number(i.varGrp['@ID'].substring(2)));
            }
            ids.sort();
            var _id = 'VG';
            if (ids.length > 0) {
                _id += String(ids[ids.length - 1] + 1);
            }
            else {
                _id += '1';
            }
            var varGroup_1 = {};
            varGroup_1.varGrp = {
                labl: '',
                '@var': '',
                '@ID': _id
            };
            varGroup_1.varGrp['@var'] = '';
            this.variableGroups.push(varGroup_1);
            var obj_1 = this;
            obj_1.variableGroups[numberOfGroups].editable = true;
            this.groupChange = true;
            console.log("group Change " + this.groupChange);
            setTimeout(function () {
                obj_1.titleInput.nativeElement.focus();
                obj_1.parentScrollNav.emit();
                obj_1.onGroupClick(varGroup_1);
            }, 100);
        }
    };
    VarGroupComponent.prototype.onGroupClick = function (_obj) {
        this.ddiService.clearSearch();
        var obj = _obj;
        var vars = obj.varGrp['@var'].split(' ');
        this.subSetRows.emit(vars);
        this.showActive(obj.varGrp['@ID']);
        this.selectGroup.emit(obj.varGrp['@ID']);
    };
    VarGroupComponent.prototype.onGroupDblClick = function (_obj) {
        this.renameGroup(_obj);
    };
    VarGroupComponent.prototype.renameGroup = function (_obj) {
        _obj.editable = true;
    };
    VarGroupComponent.prototype.renameGroupComplete = function (_obj, _val) {
        if (_val !== null && _val.trim() !== '') {
            _obj.varGrp.labl = _val.trim();
            _obj.editable = false;
        }
        this.groupChange = true;
    };
    VarGroupComponent.prototype.renameGroupCancel = function (_obj) {
        if (_obj.varGrp.labl !== null && _obj.varGrp.labl.trim() !== '') {
            _obj.editable = false;
        }
    };
    VarGroupComponent.prototype.groupDelete = function (_obj) {
        //if (confirm('Are you sure you want to delete ' + _obj.varGrp['labl'] + '?')) {
        if (confirm(this.translate.instant('TS.CONFIRM') + _obj.varGrp['labl'] + '?')) {
            for (var i = 0; i < this.variableGroups.length; i++) {
                if (this.variableGroups[i].varGrp['@ID'] === _obj.varGrp['@ID']) {
                    this.variableGroups.splice(i, 1);
                }
            }
            this.showAll();
            this.groupChange = true;
        }
        else {
            _obj.editable = false;
        }
    };
    VarGroupComponent.prototype.showActive = function (_id) {
        this.allActive = false;
        // show it's active
        for (var _i = 0, _a = this.variableGroups; _i < _a.length; _i++) {
            var i = _a[_i];
            if (i.varGrp['@ID'] === _id) {
                i.active = true;
            }
            else {
                i.active = false;
            }
        }
    };
    VarGroupComponent.prototype.showAll = function () {
        this.ddiService.clearSearch();
        this.showActive();
        this.allActive = true;
        this.subSetRows.emit();
        this.disableSelectGroup.emit();
    };
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])(),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", Object)
    ], VarGroupComponent.prototype, "variableGroups", void 0);
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Output"])(),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"])
    ], VarGroupComponent.prototype, "subSetRows", void 0);
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Output"])(),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"])
    ], VarGroupComponent.prototype, "parentScrollNav", void 0);
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Output"])(),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"])
    ], VarGroupComponent.prototype, "selectGroup", void 0);
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Output"])(),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"])
    ], VarGroupComponent.prototype, "disableSelectGroup", void 0);
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ViewChild"])('titleInput'),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_core__WEBPACK_IMPORTED_MODULE_1__["ElementRef"])
    ], VarGroupComponent.prototype, "titleInput", void 0);
    VarGroupComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-var-group',
            template: __webpack_require__(/*! ./var-group.component.html */ "./src/app/var-group/var-group.component.html"),
            styles: [__webpack_require__(/*! ./var-group.component.css */ "./src/app/var-group/var-group.component.css")]
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_ddi_service__WEBPACK_IMPORTED_MODULE_2__["DdiService"],
            _ngx_translate_core__WEBPACK_IMPORTED_MODULE_3__["TranslateService"]])
    ], VarGroupComponent);
    return VarGroupComponent;
}());



/***/ }),

/***/ "./src/app/var-stat-dialog/var-stat-dialog.component.css":
/*!***************************************************************!*\
  !*** ./src/app/var-stat-dialog/var-stat-dialog.component.css ***!
  \***************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".table-bordered {\n  border: 1px solid #ddd;\n  width: 100%;\n  border-spacing: 0;\n  border-collapse: collapse;\n}\n\n.table-bordered tr > td,\n.table-bordered tr > th {\n  border: 1px solid #ddd;\n  padding: 5px;\n  text-align: left;\n}\n\n.close-dialog {\n  float: right;\n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbImFwcC92YXItc3RhdC1kaWFsb2cvdmFyLXN0YXQtZGlhbG9nLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7RUFDRSxzQkFBc0I7RUFDdEIsV0FBVztFQUNYLGlCQUFpQjtFQUNqQix5QkFBeUI7QUFDM0I7O0FBRUE7O0VBRUUsc0JBQXNCO0VBQ3RCLFlBQVk7RUFDWixnQkFBZ0I7QUFDbEI7O0FBRUE7RUFDRSxZQUFZO0FBQ2QiLCJmaWxlIjoiYXBwL3Zhci1zdGF0LWRpYWxvZy92YXItc3RhdC1kaWFsb2cuY29tcG9uZW50LmNzcyIsInNvdXJjZXNDb250ZW50IjpbIi50YWJsZS1ib3JkZXJlZCB7XG4gIGJvcmRlcjogMXB4IHNvbGlkICNkZGQ7XG4gIHdpZHRoOiAxMDAlO1xuICBib3JkZXItc3BhY2luZzogMDtcbiAgYm9yZGVyLWNvbGxhcHNlOiBjb2xsYXBzZTtcbn1cblxuLnRhYmxlLWJvcmRlcmVkIHRyID4gdGQsXG4udGFibGUtYm9yZGVyZWQgdHIgPiB0aCB7XG4gIGJvcmRlcjogMXB4IHNvbGlkICNkZGQ7XG4gIHBhZGRpbmc6IDVweDtcbiAgdGV4dC1hbGlnbjogbGVmdDtcbn1cblxuLmNsb3NlLWRpYWxvZyB7XG4gIGZsb2F0OiByaWdodDtcbn0iXX0= */"

/***/ }),

/***/ "./src/app/var-stat-dialog/var-stat-dialog.component.html":
/*!****************************************************************!*\
  !*** ./src/app/var-stat-dialog/var-stat-dialog.component.html ***!
  \****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div mat-dialog-content>\n\n  <button mat-icon-button mat-dialog-close matTooltip=\"{{'VARDIALSTAT.CLOSE' | translate}}\" class=\"close-dialog\">\n      <mat-icon  [attr.aria-label]=\"'VARDIALSTAT.CLOSE' | translate\">close</mat-icon>\n  </button>\n\n  <h1 mat-dialog-title>{{data[\"@name\"]}}: {{data[\"labl\"][\"#text\"]}}</h1>\n\n  <app-chart [data]=\"sortedCategories\"></app-chart>\n\n  <table class=\"table-bordered\">\n    <tr>\n        <th>{{'VARDIALSTAT.VALUES' | translate}}</th>\n        <th>{{'VARDIALSTAT.CATEGORIES' | translate}}</th>\n        <th>{{'VARDIALSTAT.COUNT' | translate}}</th>\n        <th>{{'VARDIALSTAT.COUNTPER' | translate}}</th>\n        <th>{{'VARDIALSTAT.WEIGHTCOUNT' | translate}}</th>\n    </tr>\n    <tr *ngFor=\"let row of sortedCategories\">\n      <td>{{row.catValu}}</td>\n      <td>{{row.labl[\"#text\"]}}</td>\n      <td *ngIf=\"isUndefined(row.catStat); else elseBlock\"></td>\n      <ng-template #elseBlock>\n        <td *ngIf=\"isUndefined(row.catStat.length); else elseBlock2\"></td>\n        <ng-template #elseBlock2>\n          <td>{{row.catStat[0][\"#text\"] | number}}</td>\n        </ng-template>\n      </ng-template>\n      <td>{{row.countPerc | number}}</td>\n      <td *ngIf=\"isUndefined(row.catStat); else elseBloc\"></td>\n      <ng-template #elseBloc>\n        <td *ngIf=\"doesExist(row.catStat.length); else elseBloc2\">{{row.catStat[1][\"#text\"] | number }}</td>\n        <ng-template #elseBloc2>\n          <td></td>\n        </ng-template>\n      </ng-template>\n    </tr>\n  </table>\n\n</div>\n"

/***/ }),

/***/ "./src/app/var-stat-dialog/var-stat-dialog.component.ts":
/*!**************************************************************!*\
  !*** ./src/app/var-stat-dialog/var-stat-dialog.component.ts ***!
  \**************************************************************/
/*! exports provided: VarStatDialogComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "VarStatDialogComponent", function() { return VarStatDialogComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm5/material.es5.js");



var VarStatDialogComponent = /** @class */ (function () {
    function VarStatDialogComponent(data) {
        this.data = data;
        this.sortedCategories = [];
    }
    VarStatDialogComponent.prototype.ngOnInit = function () {
        if (typeof this.data.catgry !== 'undefined') {
            if (typeof this.data.catgry.length === 'undefined') {
                this.sortedCategories.push(this.data.catgry);
            }
            else {
                for (var _i = 0, _a = this.data.catgry; _i < _a.length; _i++) {
                    var i = _a[_i];
                    this.sortedCategories.push(i);
                }
            }
            this.sortedCategories.sort(function (a, b) {
                return a.catValu - b.catValu;
            });
        }
    };
    VarStatDialogComponent.prototype.isUndefined = function (val) {
        return typeof val === 'undefined';
    };
    VarStatDialogComponent.prototype.doesExist = function (val) {
        return typeof val !== 'undefined' && val > 1;
    };
    VarStatDialogComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-var-stat-dialog',
            template: __webpack_require__(/*! ./var-stat-dialog.component.html */ "./src/app/var-stat-dialog/var-stat-dialog.component.html"),
            styles: [__webpack_require__(/*! ./var-stat-dialog.component.css */ "./src/app/var-stat-dialog/var-stat-dialog.component.css")]
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__param"](0, Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Inject"])(_angular_material__WEBPACK_IMPORTED_MODULE_2__["MAT_DIALOG_DATA"])),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [Object])
    ], VarStatDialogComponent);
    return VarStatDialogComponent;
}());



/***/ }),

/***/ "./src/app/var/var.component.css":
/*!***************************************!*\
  !*** ./src/app/var/var.component.css ***!
  \***************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".grey .mat-cell {\n  color: #767676;\n}\n\n.active {\n  background: #6d9cff;\n}\n\n.mat-header-cell {\n  color: rgba(0,0,0,.6);\n}\n\n.mat-column-select {\n  flex:  0 1 70px;\n}\n\n.mat-column-control {\n  flex:  0 1 50px;\n}\n\n.mat-column-id {\n  flex:  0 1 100px;\n}\n\n.mat-column-name {\n  flex:  1 1 100px;\n}\n\n.mat-column-labl {\n  flex:  1 1 600px;\n}\n\n.mat-column-wgt-var {\n  flex:  0 1 100px;\n}\n\n.mat-column-view {\n  flex:  0 1 75px;\n}\n\n.mat-column-action {\n  flex:  0 1 75px;\n}\n\n.unvis {\n  visibility: hidden;\n}\n\n\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbImFwcC92YXIvdmFyLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7RUFDRSxjQUFjO0FBQ2hCOztBQUVBO0VBQ0UsbUJBQW1CO0FBQ3JCOztBQUVBO0VBQ0UscUJBQXFCO0FBQ3ZCOztBQUVBO0VBQ0UsZUFBZTtBQUNqQjs7QUFFQTtFQUNFLGVBQWU7QUFDakI7O0FBRUE7RUFDRSxnQkFBZ0I7QUFDbEI7O0FBRUE7RUFDRSxnQkFBZ0I7QUFDbEI7O0FBRUE7RUFDRSxnQkFBZ0I7QUFDbEI7O0FBRUE7RUFDRSxnQkFBZ0I7QUFDbEI7O0FBRUE7RUFDRSxlQUFlO0FBQ2pCOztBQUVBO0VBQ0UsZUFBZTtBQUNqQjs7QUFFQTtFQUNFLGtCQUFrQjtBQUNwQiIsImZpbGUiOiJhcHAvdmFyL3Zhci5jb21wb25lbnQuY3NzIiwic291cmNlc0NvbnRlbnQiOlsiLmdyZXkgLm1hdC1jZWxsIHtcbiAgY29sb3I6ICM3Njc2NzY7XG59XG5cbi5hY3RpdmUge1xuICBiYWNrZ3JvdW5kOiAjNmQ5Y2ZmO1xufVxuXG4ubWF0LWhlYWRlci1jZWxsIHtcbiAgY29sb3I6IHJnYmEoMCwwLDAsLjYpO1xufVxuXG4ubWF0LWNvbHVtbi1zZWxlY3Qge1xuICBmbGV4OiAgMCAxIDcwcHg7XG59XG5cbi5tYXQtY29sdW1uLWNvbnRyb2wge1xuICBmbGV4OiAgMCAxIDUwcHg7XG59XG5cbi5tYXQtY29sdW1uLWlkIHtcbiAgZmxleDogIDAgMSAxMDBweDtcbn1cblxuLm1hdC1jb2x1bW4tbmFtZSB7XG4gIGZsZXg6ICAxIDEgMTAwcHg7XG59XG5cbi5tYXQtY29sdW1uLWxhYmwge1xuICBmbGV4OiAgMSAxIDYwMHB4O1xufVxuXG4ubWF0LWNvbHVtbi13Z3QtdmFyIHtcbiAgZmxleDogIDAgMSAxMDBweDtcbn1cblxuLm1hdC1jb2x1bW4tdmlldyB7XG4gIGZsZXg6ICAwIDEgNzVweDtcbn1cblxuLm1hdC1jb2x1bW4tYWN0aW9uIHtcbiAgZmxleDogIDAgMSA3NXB4O1xufVxuXG4udW52aXMge1xuICB2aXNpYmlsaXR5OiBoaWRkZW47XG59XG5cblxuIl19 */"

/***/ }),

/***/ "./src/app/var/var.component.html":
/*!****************************************!*\
  !*** ./src/app/var/var.component.html ***!
  \****************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<mat-grid-list class=\"table-controls\" cols=\"3\" rowHeight=\"64\">\n  <mat-grid-tile>\n    <mat-form-field>\n      <input matInput [formControl]=\"searchFilter\" placeholder=\"{{'VAR.SEARCH' | translate}}\" />\n      <span matSuffix><mat-icon>search</mat-icon></span>\n    </mat-form-field>\n  </mat-grid-tile>\n  <mat-paginator [pageSizeOptions]=\"getPageSizeOptions()\"></mat-paginator>\n  <mat-divider [vertical]=\"true\"></mat-divider>\n  <mat-grid-tile>\n    <mat-select\n      color=\"primary\"\n      (selectionChange)=\"addToGroup($event.value)\"\n      placeholder=\"{{'GROUPS.ADDSELECTED' | translate}}\"\n      #group_select\n      disabled=\"true\"\n      [hidden]=\"group_select.hidden\"\n    >\n      <mat-option *ngFor=\"let g of variableGroups\" [value]=\"g.varGrp['@ID']\">\n        {{ g.varGrp.labl }}\n      </mat-option>\n    </mat-select>\n  </mat-grid-tile>\n</mat-grid-list>\n\n<mat-table class=\"mat-elevation-z8\" [dataSource]=\"datasource\" matSort (matSortChange)=\"sortChange($event)\">\n\n  <!-- Checkbox Column in 'All Variables' view -->\n  <ng-container matColumnDef=\"select\">\n    <mat-header-cell *matHeaderCellDef>\n      <mat-checkbox\n        (change)=\"$event ? masterToggle() : null\"\n        [checked]=\"selection.hasValue() && isAllSelected()\"\n        [indeterminate]=\"selection.hasValue() && !isAllSelected()\"\n      >\n      </mat-checkbox>\n    </mat-header-cell>\n    <mat-cell *matCellDef=\"let row; let i=index\">\n      <mat-checkbox\n        (click)=\"$event.shiftKey ? multipleToggle(row,i, $event) : singleToggle(i,$event)\"\n        (change)=\"$event ? selection.toggle(row) : null; checkSelection()\"\n        [checked]=\"selection.isSelected(row)\"\n        matTooltip=\"{{'VAR.SELECT' | translate}}\"\n>\n      </mat-checkbox>\n    </mat-cell>\n  </ng-container>\n\n  <!-- MINUS PLUS Column in Group view -->\n  <!-- (click)=\"$event.shiftKey ? multipleToggleRemove(row,i, $event) : onRemove(row['@ID'])\" -->\n  <ng-container matColumnDef=\"control\">\n    <mat-header-cell *matHeaderCellDef>\n      <!--<div style=\"width:40px;\"></div> -->\n      <button\n              mat-icon-button\n              color=\"accent\"\n              (click)=\"onRemoveAll()\"\n              matTooltip=\"{{'GROUPS.REMOVEALL' | translate}}\"\n      >\n        <mat-icon  [attr.aria-label]=\"'GROUPS.REMOVE' | translate\">indeterminate_check_box</mat-icon>\n      </button>\n\n    </mat-header-cell>\n    <mat-cell *matCellDef=\"let row; let i = index\">\n      <button\n        *ngIf=\"row._show\"\n        mat-icon-button\n        color=\"accent\"\n        (click)=\"onRemove(row['@ID'])\"\n        matTooltip=\"{{'GROUPS.REMOVE' | translate}}\"\n      >\n        <mat-icon  [attr.aria-label]=\"'GROUPS.REMOVE' | translate\">indeterminate_check_box</mat-icon>\n      </button>\n      <button\n        *ngIf=\"!row._show\"\n        mat-icon-button\n        color=\"accent\"\n        (click)=\"onAdd(row['@ID'])\"\n        matTooltip=\"{{'GROUPS.ADDTO' | translate}}\"\n      >\n        <mat-icon [attr.aria-label]=\"'GROUPS.ADDTO' | translate\">add_box</mat-icon>\n      </button>\n    </mat-cell>\n  </ng-container>\n\n  <!-- ID Column -->\n  <ng-container matColumnDef=\"id\">\n    <mat-header-cell *matHeaderCellDef mat-sort-header>{{'VAR.ID' | translate}}</mat-header-cell>\n    <mat-cell *matCellDef=\"let row\"> {{ row['id'] }} </mat-cell>\n  </ng-container>\n\n  <ng-container matColumnDef=\"_order\">\n    <mat-header-cell *matHeaderCellDef mat-sort-header>order</mat-header-cell>\n    <mat-cell *matCellDef=\"let row\"> {{ row['_order'] }} </mat-cell>\n  </ng-container>\n\n  <!-- Name Column -->\n  <ng-container matColumnDef=\"name\">\n    <mat-header-cell *matHeaderCellDef mat-sort-header>{{'VAR.NAME' | translate}}</mat-header-cell>\n    <mat-cell *matCellDef=\"let row\">{{ (row['@name'].length > 30) ? (row['@name'] | slice:0:30) + '...' : (row['@name']) }}  </mat-cell>\n  </ng-container>\n\n  <!-- Label Column -->\n  <ng-container matColumnDef=\"labl\">\n    <mat-header-cell *matHeaderCellDef mat-sort-header>{{'VAR.LABEL' | translate}}</mat-header-cell>\n    <mat-cell *matCellDef=\"let row\"> {{ (row.labl['#text'].length > 150) ? (row.labl['#text'] | slice:0:150) + '...' : (row.labl['#text'])}} </mat-cell>\n  </ng-container>\n\n  <!-- Weight Column-->\n  <ng-container matColumnDef=\"wgt-var\">\n    <mat-header-cell *matHeaderCellDef mat-sort-header>{{'VAR.WEIGHT' | translate}}</mat-header-cell>\n    <mat-cell *matCellDef=\"let row\">\n      <span *ngIf=\"row['@wgt-var'] ; else elseBlock\">{{ row['@wgt-var'] }} </span>\n      <ng-template #elseBlock>\n        <span *ngIf=\"row['@wgt']\">{{ row['@wgt'] }}</span>\n      </ng-template>\n    </mat-cell>\n  </ng-container>\n\n  <!-- View Column-->\n  <ng-container matColumnDef=\"view\">\n    <mat-header-cell *matHeaderCellDef>{{'VAR.VIEW' | translate}}</mat-header-cell>\n    <mat-cell *matCellDef=\"let row; let i = index\">\n      <button\n        mat-icon-button\n        color=\"accent\"\n        (click)=\"onView(row['@ID'])\"\n        matTooltip=\"{{'VAR.VIEW' | translate}}\"\n      >\n        <mat-icon [attr.aria-label]=\"'VAR.VIEW' | translate\">visibility</mat-icon>\n      </button>\n    </mat-cell>\n  </ng-container>\n\n  <!-- Edit Column -->\n  <ng-container matColumnDef=\"action\">\n    <mat-header-cell *matHeaderCellDef>\n      <button\n        mat-icon-button\n        color=\"accent\"\n        (click)=\"onEditSelected()\"\n        matTooltip=\"{{'VAR.GROUPEDIT' | translate}}\"\n        #group_edit\n        disabled=\"true\"\n      >\n        <mat-icon [attr.aria-label]=\"'VAR.GROUPEDIT' | translate\">edit</mat-icon>\n      </button>\n    </mat-header-cell>\n\n    <mat-cell *matCellDef=\"let row; let i = index\">\n      <button\n        mat-icon-button\n        color=\"accent\"\n        (click)=\"onEdit(row['@ID'])\"\n        matTooltip=\"{{'VAR.EDIT' | translate}}\"\n      >\n        <mat-icon [attr.aria-label]=\"'VAR.EDIT' | translate\">edit</mat-icon>\n      </button>\n    </mat-cell>\n  </ng-container>\n\n  <mat-header-row *matHeaderRowDef=\"getDisplayedColumns()\"></mat-header-row>\n  <mat-row\n    *matRowDef=\"let row; columns: getDisplayedColumns()\"\n    [ngClass]=\"{ unvis: row._show != true , active: row._active == true }\"\n  >\n  </mat-row>\n</mat-table>\n"

/***/ }),

/***/ "./src/app/var/var.component.ts":
/*!**************************************!*\
  !*** ./src/app/var/var.component.ts ***!
  \**************************************/
/*! exports provided: VarComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "VarComponent", function() { return VarComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm5/material.es5.js");
/* harmony import */ var _var_dialog_var_dialog_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../var-dialog/var-dialog.component */ "./src/app/var-dialog/var-dialog.component.ts");
/* harmony import */ var _var_stat_dialog_var_stat_dialog_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../var-stat-dialog/var-stat-dialog.component */ "./src/app/var-stat-dialog/var-stat-dialog.component.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _angular_cdk_collections__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/cdk/collections */ "./node_modules/@angular/cdk/esm5/collections.es5.js");
/* harmony import */ var _ddi_service__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ../ddi.service */ "./src/app/ddi.service.ts");
/* harmony import */ var _ngx_translate_core__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @ngx-translate/core */ "./node_modules/@ngx-translate/core/fesm5/ngx-translate-core.js");









var VarComponent = /** @class */ (function () {
    function VarComponent(dialog, ref, snackBar, ddiService, translate) {
        this.dialog = dialog;
        this.ref = ref;
        this.snackBar = snackBar;
        this.ddiService = ddiService;
        this.translate = translate;
        this.searchFilter = new _angular_forms__WEBPACK_IMPORTED_MODULE_5__["FormControl"]();
        this.selection = new _angular_cdk_collections__WEBPACK_IMPORTED_MODULE_6__["SelectionModel"](true, []);
        this.filterValues = { search: '', _show: true };
        this.startSelection = null;
        this.endSelection = null;
        this.renderedData = null;
        this.startSelectionGroup = null;
        this.endSelectionGroup = null;
        this.mode = 'all';
        this.varChange = false;
        this.variableGroupsVars = [];
    }
    VarComponent.prototype.getDisplayedColumns = function () {
        var displayedColumns = []; // 'order_arrows'
        if (this.mode === 'all') {
            displayedColumns = [
                'select',
                'id',
                'name',
                'labl',
                'wgt-var',
                'view',
                'action'
            ];
        }
        else {
            displayedColumns = [
                'control',
                'id',
                'name',
                'labl',
                'wgt-var',
                'view',
                'action'
            ];
        }
        return displayedColumns;
    };
    /** Whether the number of selected elements matches the total number of rows. */
    VarComponent.prototype.isAllSelected = function () {
        var numSelected = this.selection.selected.length;
        var numRows = this.datasource.data.length;
        return numSelected === numRows;
    };
    /** Selects all rows if they are not all selected; otherwise clear selection. */
    VarComponent.prototype.masterToggle = function () {
        if (this.isAllSelected()) {
            this.selection.clear();
        }
        else {
            for (var i = 0; i < this.datasource.data.length; i++) {
                if (this.datasource.data[i]._show === true) {
                    this.selection.select(this.datasource.data[i]);
                }
            }
        }
        this.checkSelection();
    };
    VarComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.ddiService.currentSearchInput.subscribe(function (message) { return _this.searchFilter.patchValue(''); });
        this.searchFilter.valueChanges.subscribe(function (value) {
            _this.filterValues['search'] = value;
            _this.datasource.filter = JSON.stringify(_this.filterValues);
        });
        this.group_select['hidden'] = true;
    };
    VarComponent.prototype.getPageSizeOptions = function () {
        if (typeof this.datasource !== 'undefined') {
            if (this.datasource.paginator.length > 100) {
                return [25, 50, 100, this.datasource.paginator.length];
            }
            else if (this.datasource.paginator.length > 50 && this.datasource.paginator.length < 100) {
                return [25, 50, this.datasource.paginator.length];
            }
            else if (this.datasource.paginator.length > 25 && this.datasource.paginator.length < 50) {
                return [25, this.datasource.paginator.length];
            }
            else if (this.datasource.paginator.length >= 0 && this.datasource.paginator.length < 25) {
                return [this.datasource.paginator.length];
            }
            else {
                return [25, 50, 100];
            }
        }
        else {
            return [25];
        }
    };
    // Entry point - when data has been loaded
    VarComponent.prototype.onUpdateVars = function (data) {
        var _this = this;
        this._variables = data;
        // make sure all the data is set to show
        for (var i = 0; i < this._variables.length; i++) {
            this._variables[i]._show = true;
            // also make sure it has a label
            if (typeof this._variables[i].labl === 'undefined') {
                this._variables[i].labl = { '#text': '', '@level': 'variable' };
            }
        }
        // show if var is _in_group
        this.updateGroupsVars(true);
        this.datasource = new _angular_material__WEBPACK_IMPORTED_MODULE_2__["MatTableDataSource"](this._variables);
        this.datasource.sort = this.sort;
        this.datasource.paginator = this.paginator;
        // sorting
        this.datasource.sortingDataAccessor = function (datasort, property) {
            switch (property) {
                case 'id':
                    return +datasort['@ID'].replace(/\D/g, '');
                case 'name':
                    return datasort['@name'];
                case 'labl':
                    return datasort.labl['#text'];
                case '_order':
                    return datasort._order;
                case 'wgt-var':
                    if (datasort['@wgt'] === 'wgt') {
                        return datasort['@wgt'];
                    }
                    return datasort['@wgt-var'];
                default:
                    return '';
            }
        };
        // filter
        this.datasource.filterPredicate = this.createFilter();
        this.datasource.connect().subscribe(function (d) { return _this.renderedData = d; });
    };
    VarComponent.prototype.createFilter = function () {
        var filterFunction = function (data, filter) {
            var searchTerms = JSON.parse(filter);
            try {
                return (data['@ID']
                    .toString()
                    .toLowerCase()
                    .indexOf(searchTerms.search.toLowerCase()) !== -1 ||
                    data['@name']
                        .toString()
                        .toLowerCase()
                        .indexOf(searchTerms.search.toLowerCase()) !== -1 ||
                    data['labl']['#text']
                        .toString()
                        .toLowerCase()
                        .indexOf(searchTerms.search.toLowerCase()) !== -1);
            }
            catch (e) {
                return false;
            }
        };
        return filterFunction;
    };
    VarComponent.prototype.onEdit = function (_id) {
        this.id = _id;
        // get the data
        this.openDialog([this.getObjByID(_id, this._variables)]);
    };
    VarComponent.prototype.onSubset = function (_ids, sort) {
        if (_ids == null) {
            this.mode = 'all';
        }
        else {
            this.mode = 'group';
        }
        var data = [];
        var ungroupedCount = 0;
        var obj;
        for (var i = 0; i < this._variables.length; i++) {
            obj = this._variables[i];
            if (this.mode === 'group') {
                if (_ids.indexOf(obj['@ID']) !== -1) {
                    obj._order = _ids.indexOf(obj['@ID']);
                    obj._show = true;
                    data.push(obj);
                }
                else {
                    ungroupedCount += 1;
                    obj._order = 99999 + ungroupedCount;
                    obj._show = false;
                }
            }
            else if (this.mode === 'all') {
                obj._order = null;
                obj._show = true;
                data.push(obj);
            }
        }
        obj._active = false;
        this.filterValues['_show'] = true;
        this.datasource.filter = JSON.stringify(this.filterValues);
        // Showing all
        this.checkSelection(); // and enable group dropdown if applicable
        this.datasource.data = data;
        if (this.mode === 'group') {
            if (sort == null || sort) {
                this.sortByOrder();
                this.paginator.firstPage();
            }
        }
        else {
            if (sort == null || sort) {
                this.sort.sort({ id: '', start: 'asc', disableClear: false });
                this.paginator.firstPage();
            }
        }
    };
    // when a single row has been updated
    VarComponent.prototype.onUpdateVar = function () {
        this.varChange = true;
        this.removeWeightedFreq();
        this.ref.detectChanges();
    };
    VarComponent.prototype.removeWeightedFreq = function () {
        var weights = this.getWeights();
        var weightsSet = new Set(weights);
        for (var i = 0; i < this._variables.length; i++) {
            if (typeof this._variables[i]['@wgt-var'] !== 'undefined') {
                if (this._variables[i]['@wgt-var'] !== '') {
                    if (!weightsSet.has(this._variables[i]['@wgt-var'])) {
                        this._variables[i]['@wgt-var'] = '';
                        for (var k = 0; k < this._variables[i].catgry.length; k++) {
                            this._variables[i].catgry[k].catStat.splice(1, 1);
                        }
                    }
                }
            }
        }
    };
    // get the var
    VarComponent.prototype.getObjByID = function (_id, _data) {
        for (var _i = 0, _data_1 = _data; _i < _data_1.length; _i++) {
            var i = _data_1[_i];
            var obj = i;
            if (obj['@ID'] === _id) {
                return obj;
            }
        }
    };
    // get the group
    VarComponent.prototype.getObjByIDNested = function (_id, _data) {
        for (var _i = 0, _data_2 = _data; _i < _data_2.length; _i++) {
            var i = _data_2[_i];
            var obj = i;
            if (obj.varGrp['@ID'] === _id) {
                return obj;
            }
        }
    };
    VarComponent.prototype.getWeightsNames = function () {
        var weightsNames = [];
        for (var i = 0; i < this._variables.length; i++) {
            if (this._variables[i]['@wgt'] === 'wgt') {
                weightsNames.push(this._variables[i]);
            }
        }
        return weightsNames;
    };
    VarComponent.prototype.getWeights = function () {
        var weights = [];
        for (var i = 0; i < this._variables.length; i++) {
            if (this._variables[i]['@wgt'] === 'wgt') {
                weights.push(this._variables[i]['@ID']);
            }
        }
        return weights;
    };
    VarComponent.prototype.openDialog = function (data) {
        var _this = this;
        this.dialogRef = this.dialog.open(_var_dialog_var_dialog_component__WEBPACK_IMPORTED_MODULE_3__["VarDialogComponent"], {
            width: '35em',
            data: data,
            panelClass: 'field_width'
        });
        var weightsNames = this.getWeightsNames();
        this.dialogRef.componentInstance.weights = weightsNames;
        this.dialogRef.componentInstance.variableGroups = this.variableGroups;
        var sub = this.dialogRef.componentInstance.parentUpdateVar.subscribe(function () {
            _this.onUpdateVar();
            for (var i = 0; i < _this.variableGroups.length; i++) {
                if (_this.variableGroups[i].active) {
                    var vars = _this.variableGroups[i].varGrp['@var'].split(' ');
                    _this.onSubset(vars);
                    break;
                }
            }
        });
    };
    VarComponent.prototype.multipleToggle = function (row, i, event) {
        var _this = this;
        var selectFlag = false;
        if (this.selection.isSelected(row)) {
            selectFlag = true;
            this.selection.deselect(row);
        }
        else {
            selectFlag = false;
            this.selection.select(row);
        }
        if (this.startSelection == null) {
            this.startSelection = i;
        }
        else {
            this.endSelection = i;
            var currentIndex_1 = 0;
            this.renderedData.forEach(function (r) {
                if (_this.startSelection <= _this.endSelection) {
                    if (currentIndex_1 >= _this.startSelection && currentIndex_1 <= _this.endSelection) {
                        if (selectFlag) {
                            _this.selection.deselect(r);
                        }
                        else {
                            _this.selection.select(r);
                        }
                    }
                    currentIndex_1++;
                }
                else {
                    if (currentIndex_1 >= _this.endSelection && currentIndex_1 <= _this.startSelection) {
                        if (selectFlag) {
                            _this.selection.deselect(r);
                        }
                        else {
                            _this.selection.select(r);
                        }
                    }
                    currentIndex_1++;
                }
            });
            this.startSelection = null;
            this.endSelection = null;
        }
        this.checkSelection();
    };
    VarComponent.prototype.singleToggle = function (i, event) {
        this.startSelection = i;
        this.endSelection = null;
        event.stopPropagation();
    };
    VarComponent.prototype.checkSelection = function () {
        // when are in all view mode
        if (this.mode === 'all') {
            if (this.selection.selected.length > 0) {
                this.group_select['disabled'] = 'false';
                this.group_select['hidden'] = false;
            }
            else {
                this.group_select['disabled'] = 'true';
                this.group_select['hidden'] = true;
            }
        }
        if (this.selection.selected.length > 1) {
            this.group_edit['disabled'] = 'false';
        }
        else {
            this.group_edit['disabled'] = 'true';
        }
    };
    VarComponent.prototype.addToGroup = function (_id) {
        // first get the group
        var obj = this.getObjByIDNested(_id, this.variableGroups);
        var vars = obj.varGrp['@var'].split(' ');
        for (var _i = 0, _a = this.selection.selected; _i < _a.length; _i++) {
            var i = _a[_i];
            var selected = i;
            if (vars.indexOf(selected['@ID']) === -1) {
                vars.push(selected['@ID']);
            }
        }
        // reset vars to new selection
        obj.varGrp['@var'] = vars.join(' ');
        // reset the dropdown
        this.group_select['value'] = 0;
        //
        this.updateGroupsVars();
        this.showMSG(this.translate.instant('GROUPS.ADDEDTO') + obj.varGrp.labl);
        this.selection.clear();
    };
    VarComponent.prototype.selectGroup = function (_id) {
        this.group_select['value'] = _id;
        this.selection.clear();
    };
    VarComponent.prototype.disableSelectGroup = function () {
        this.group_select['value'] = 0;
    };
    VarComponent.prototype.updateGroupsVars = function (load) {
        this.getVariableGroupsVars();
        for (var i = 0; i < this._variables.length; i++) {
            if (this.variableGroupsVars.indexOf(this._variables[i]['@ID']) > -1) {
                this._variables[i]._in_group = true;
            }
            else {
                this._variables[i]._in_group = false;
            }
        }
        if (load == null || !load) {
            this.varChange = true;
        }
    };
    VarComponent.prototype.getVariableGroupsVars = function () {
        this.variableGroupsVars = [];
        // loop though all the variables in the varaible groups and create a complete list
        for (var i = 0; i < this.variableGroups.length; i++) {
            var obj = this.variableGroups[i];
            var vars = obj.varGrp['@var'].split(' ');
            for (var j = 0; j < vars.length; j++) {
                if (this.variableGroupsVars.indexOf(vars[j]) === -1) {
                    this.variableGroupsVars.push(vars[j]);
                }
            }
        }
    };
    VarComponent.prototype.sortByOrder = function () {
        this.sort.sort({ id: '', start: 'asc', disableClear: false });
        this.sort.sort({ id: '_order', start: 'asc', disableClear: false });
    };
    VarComponent.prototype.highlightRow = function (_row) {
        _row._active = true;
    };
    VarComponent.prototype.onAdd = function (_id) {
        var obj = this.updateGroupVars('add', _id);
        this.showMSG(this.translate.instant('GROUPS.ADDEDID', { _id: _id }) + obj.varGrp.labl);
    };
    VarComponent.prototype.onRemoveAll = function () {
        var obj = this.getObjByIDNested(this.group_select['value'], this.variableGroups);
        var vars = obj.varGrp['@var'].split(' ');
        vars.splice(0, vars.length); // empty array (remove all elements from group
        obj.varGrp['@var'] = vars.join(' ');
        this.updateGroupsVars();
        this.onSubset(vars);
    };
    VarComponent.prototype.multipleToggleRemove = function (row, i, event) {
        var _this = this;
        if (this.startSelectionGroup == null) {
            this.startSelectionGroup = i;
        }
        else {
            this.endSelectionGroup = i;
            console.log(this.datasource.data);
            var currentIndex_2 = 0;
            this.renderedData.forEach(function (r) {
                if (_this.startSelectionGroup <= _this.endSelectionGroup) {
                    if (currentIndex_2 >= _this.startSelectionGroup && currentIndex_2 <= _this.endSelectionGroup) {
                        var obj = _this.updateGroupVars('remove', r['@ID'], false);
                        _this.showMSG(_this.translate.instant('GROUPS.REMOVE'));
                    }
                    currentIndex_2++;
                }
                else {
                    if (currentIndex_2 >= _this.endSelectionGroup && currentIndex_2 <= _this.startSelectionGroup) {
                        var obj = _this.updateGroupVars('remove', r['@ID'], false);
                        _this.showMSG(_this.translate.instant('GROUPS.REMOVE'));
                    }
                    currentIndex_2++;
                }
            });
            this.startSelectionGroup = null;
            this.endSelectionGroup = null;
        }
        event.stopPropagation();
    };
    VarComponent.prototype.onRemove = function (_id) {
        var obj = this.updateGroupVars('remove', _id, false);
        this.showMSG(this.translate.instant('GROUPS.REMOVEDID', { _id: _id }) + obj.varGrp.labl);
    };
    VarComponent.prototype.updateGroupVars = function (_type, _id, sort) {
        var obj = this.getObjByIDNested(this.group_select['value'], this.variableGroups);
        var vars = obj.varGrp['@var'].split(' ');
        //
        if (_type === 'remove') {
            vars.splice(vars.indexOf(_id), 1); // remove from array
        }
        else if (_type === 'add') {
            vars.push(_id); // add to end of array
        }
        //
        obj.varGrp['@var'] = vars.join(' ');
        // reset the table
        this.updateGroupsVars();
        this.onSubset(vars, sort);
        return obj;
    };
    VarComponent.prototype.onEditSelected = function () {
        var selectedObjs = [];
        // show the popup but only allow certain fields be be updated
        for (var _i = 0, _a = this.selection.selected; _i < _a.length; _i++) {
            var i = _a[_i];
            var selected = i;
            selectedObjs.push(selected);
        }
        this.openDialog(selectedObjs);
    };
    VarComponent.prototype.onView = function (_id) {
        var data = this.getObjByID(_id, this._variables);
        // open a dialog showing the variables
        this.dialogStatRef = this.dialog.open(_var_stat_dialog_var_stat_dialog_component__WEBPACK_IMPORTED_MODULE_4__["VarStatDialogComponent"], {
            width: '35em',
            data: data,
            panelClass: 'field_width'
        });
    };
    VarComponent.prototype.showMSG = function (_msg) {
        this.snackBar.open(_msg, '', {
            duration: 1000
        });
    };
    VarComponent.prototype.sortChange = function (sort) {
        var _this = this;
        console.log("sortChange");
        console.log(sort);
        var vars = [];
        for (var i = 0; i < this._variables.length; i++) {
            if (this._variables[i]['_show']) {
                vars.push(this._variables[i]);
            }
        }
        this.datasource.data = vars;
        this.datasource.data.sort();
        this.datasource.connect().subscribe(function (d) { return _this.renderedData = d; });
    };
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])(),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", Object)
    ], VarComponent.prototype, "variableGroups", void 0);
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ViewChild"])('group_select'),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_core__WEBPACK_IMPORTED_MODULE_1__["ElementRef"])
    ], VarComponent.prototype, "group_select", void 0);
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ViewChild"])('group_edit'),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_core__WEBPACK_IMPORTED_MODULE_1__["ElementRef"])
    ], VarComponent.prototype, "group_edit", void 0);
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ViewChild"])(_angular_material__WEBPACK_IMPORTED_MODULE_2__["MatSort"]),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_material__WEBPACK_IMPORTED_MODULE_2__["MatSort"])
    ], VarComponent.prototype, "sort", void 0);
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ViewChild"])(_angular_material__WEBPACK_IMPORTED_MODULE_2__["MatPaginator"]),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_material__WEBPACK_IMPORTED_MODULE_2__["MatPaginator"])
    ], VarComponent.prototype, "paginator", void 0);
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["HostListener"])('matSortChange', ['$event']),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", Function),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [Object]),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:returntype", void 0)
    ], VarComponent.prototype, "sortChange", null);
    VarComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-var',
            template: __webpack_require__(/*! ./var.component.html */ "./src/app/var/var.component.html"),
            styles: [__webpack_require__(/*! ./var.component.css */ "./src/app/var/var.component.css")]
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_angular_material__WEBPACK_IMPORTED_MODULE_2__["MatDialog"],
            _angular_core__WEBPACK_IMPORTED_MODULE_1__["ChangeDetectorRef"],
            _angular_material__WEBPACK_IMPORTED_MODULE_2__["MatSnackBar"],
            _ddi_service__WEBPACK_IMPORTED_MODULE_7__["DdiService"],
            _ngx_translate_core__WEBPACK_IMPORTED_MODULE_8__["TranslateService"]])
    ], VarComponent);
    return VarComponent;
}());



/***/ }),

/***/ "./src/assets/js/xml2json.js":
/*!***********************************!*\
  !*** ./src/assets/js/xml2json.js ***!
  \***********************************/
/*! exports provided: xml2json */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "xml2json", function() { return xml2json; });
/*	This work is licensed under Creative Commons GNU LGPL License.

	License: http://creativecommons.org/licenses/LGPL/2.1/
   Version: 0.9
	Author:  Stefan Goessner/2006
	Web:     http://goessner.net/
*/
function xml2json(xml, tab) {
   var X = {
      toObj: function(xml) {
         var o = {};
         if (xml.nodeType==1) {   // element node ..
            if (xml.attributes.length)   // element with attributes  ..
               for (var i=0; i<xml.attributes.length; i++)
                  o["@"+xml.attributes[i].nodeName] = (xml.attributes[i].nodeValue||"").toString();
            if (xml.firstChild) { // element has child nodes ..
               var textChild=0, cdataChild=0, hasElementChild=false;
               for (var n=xml.firstChild; n; n=n.nextSibling) {
                  if (n.nodeType==1) hasElementChild = true;
                  else if (n.nodeType==3 && n.nodeValue.match(/[^ \f\n\r\t\v]/)) textChild++; // non-whitespace text
                  else if (n.nodeType==4) cdataChild++; // cdata section node
               }
               if (hasElementChild) {
                  if (textChild < 2 && cdataChild < 2) { // structured element with evtl. a single text or/and cdata node ..
                     X.removeWhite(xml);
                     for (var n=xml.firstChild; n; n=n.nextSibling) {
                        if (n.nodeType == 3)  // text node
                           o["#text"] = X.escape(n.nodeValue);
                        else if (n.nodeType == 4)  // cdata node
                           o["#cdata"] = X.escape(n.nodeValue);
                        else if (o[n.nodeName]) {  // multiple occurence of element ..
                           if (o[n.nodeName] instanceof Array)
                              o[n.nodeName][o[n.nodeName].length] = X.toObj(n);
                           else
                              o[n.nodeName] = [o[n.nodeName], X.toObj(n)];
                        }
                        else  // first occurence of element..
                           o[n.nodeName] = X.toObj(n);
                     }
                  }
                  else { // mixed content
                     if (!xml.attributes.length)
                        o = X.escape(X.innerXml(xml));
                     else
                        o["#text"] = X.escape(X.innerXml(xml));
                  }
               }
               else if (textChild) { // pure text
                  if (!xml.attributes.length)
                     o = X.escape(X.innerXml(xml));
                  else
                     o["#text"] = X.escape(X.innerXml(xml));
               }
               else if (cdataChild) { // cdata
                  if (cdataChild > 1)
                     o = X.escape(X.innerXml(xml));
                  else
                     for (var n=xml.firstChild; n; n=n.nextSibling)
                        o["#cdata"] = X.escape(n.nodeValue);
               }
            }
            if (!xml.attributes.length && !xml.firstChild) o = null;
         }
         else if (xml.nodeType==9) { // document.node
            o = X.toObj(xml.documentElement);
         }
         else
            alert("unhandled node type: " + xml.nodeType);
         return o;
      },
      toJson: function(o, name, ind) {
         var json = name ? ("\""+name+"\"") : "";
         if (o instanceof Array) {
            for (var i=0,n=o.length; i<n; i++)
               o[i] = X.toJson(o[i], "", ind+"\t");
            json += (name?":[":"[") + (o.length > 1 ? ("\n"+ind+"\t"+o.join(",\n"+ind+"\t")+"\n"+ind) : o.join("")) + "]";
         }
         else if (o == null)
            json += (name&&":") + "null";
         else if (typeof(o) == "object") {
            var arr = [];
            for (var m in o)
               arr[arr.length] = X.toJson(o[m], m, ind+"\t");
            json += (name?":{":"{") + (arr.length > 1 ? ("\n"+ind+"\t"+arr.join(",\n"+ind+"\t")+"\n"+ind) : arr.join("")) + "}";
         }
         else if (typeof(o) == "string")
            json += (name&&":") + "\"" + o.toString() + "\"";
         else
            json += (name&&":") + o.toString();
         return json;
      },
      innerXml: function(node) {
         var s = ""
         if ("innerHTML" in node)
            s = node.innerHTML;
         else {
            var asXml = function(n) {
               var s = "";
               if (n.nodeType == 1) {
                  s += "<" + n.nodeName;
                  for (var i=0; i<n.attributes.length;i++)
                     s += " " + n.attributes[i].nodeName + "=\"" + (n.attributes[i].nodeValue||"").toString() + "\"";
                  if (n.firstChild) {
                     s += ">";
                     for (var c=n.firstChild; c; c=c.nextSibling)
                        s += asXml(c);
                     s += "</"+n.nodeName+">";
                  }
                  else
                     s += "/>";
               }
               else if (n.nodeType == 3)
                  s += n.nodeValue;
               else if (n.nodeType == 4)
                  s += "<![CDATA[" + n.nodeValue + "]]>";
               return s;
            };
            for (var c=node.firstChild; c; c=c.nextSibling)
               s += asXml(c);
         }
         return s;
      },
      escape: function(txt) {

        return txt.replace(/^\s+|\s+$/g, '')
                   .replace(/[\\]/g, "\\\\")
                   .replace(/[\"]/g, '\\"')
                   .replace(/[\n]/g, '\\n')
                   .replace(/[\r]/g, '\\r')

      },
      removeWhite: function(e) {
         e.normalize();
         for (var n = e.firstChild; n; ) {
            if (n.nodeType == 3) {  // text node
               if (!n.nodeValue.match(/[^ \f\n\r\t\v]/)) { // pure whitespace text node
                  var nxt = n.nextSibling;
                  e.removeChild(n);
                  n = nxt;
               }
               else
                  n = n.nextSibling;
            }
            else if (n.nodeType == 1) {  // element node
               X.removeWhite(n);
               n = n.nextSibling;
            }
            else                      // any other node
               n = n.nextSibling;
         }
         return e;
      }
   };
   if (xml.nodeType == 9) // document node
      xml = xml.documentElement;
   var json = X.toJson(X.toObj(X.removeWhite(xml)), xml.nodeName, "\t");
   return "{\n" + tab + (tab ? json.replace(/\t/g, tab) : json.replace(/\t|\n/g, "")) + "\n}";
}


/***/ }),

/***/ "./src/environments/environment.ts":
/*!*****************************************!*\
  !*** ./src/environments/environment.ts ***!
  \*****************************************/
/*! exports provided: environment */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "environment", function() { return environment; });
// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
var environment = {
    production: false
};
/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.


/***/ }),

/***/ "./src/main.ts":
/*!*********************!*\
  !*** ./src/main.ts ***!
  \*********************/
/*! no exports provided */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser-dynamic */ "./node_modules/@angular/platform-browser-dynamic/fesm5/platform-browser-dynamic.js");
/* harmony import */ var _app_app_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./app/app.module */ "./src/app/app.module.ts");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./environments/environment */ "./src/environments/environment.ts");
/* harmony import */ var hammerjs__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! hammerjs */ "./node_modules/hammerjs/hammer.js");
/* harmony import */ var hammerjs__WEBPACK_IMPORTED_MODULE_4___default = /*#__PURE__*/__webpack_require__.n(hammerjs__WEBPACK_IMPORTED_MODULE_4__);





if (_environments_environment__WEBPACK_IMPORTED_MODULE_3__["environment"].production) {
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["enableProdMode"])();
}
Object(_angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__["platformBrowserDynamic"])().bootstrapModule(_app_app_module__WEBPACK_IMPORTED_MODULE_2__["AppModule"])
    .catch(function (err) { return console.error(err); });


/***/ }),

/***/ 0:
/*!***************************!*\
  !*** multi ./src/main.ts ***!
  \***************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! /home/lubitchv/work/Dataverse-Data-Curation-Tool-Test/Dataverse-Data-Curation-Tool/src/main.ts */"./src/main.ts");


/***/ })

},[[0,"runtime","vendor"]]]);
//# sourceMappingURL=main.js.map