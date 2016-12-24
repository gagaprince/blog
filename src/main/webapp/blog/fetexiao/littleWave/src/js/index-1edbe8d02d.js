/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};

/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {

/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;

/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			exports: {},
/******/ 			id: moduleId,
/******/ 			loaded: false
/******/ 		};

/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);

/******/ 		// Flag the module as loaded
/******/ 		module.loaded = true;

/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}


/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;

/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;

/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";

/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(1);

/***/ },
/* 1 */
/***/ function(module, exports) {

	"use strict";

	var main = {
	    init:function(){
	        this.initListener();
	    },
	    _api:function(url,data,callback){
	        $.ajax({
	            url:url,
	            data:JSON.stringify(data),
	            contentType:"application/json;charset=UTF-8",
	            type : 'POST',
	            dataType : 'json',
	            timeout : 3e4,
	            success:function(res){
	                if(typeof res == "string"){
	                    res = JSON.parse(res);
	                }
	                if(callback){
	                    callback(res);
	                }
	            }
	        });
	    },
	    beginOp:function(){
	        var shareCode = $("#shareCode").val();
	        var waveSwing = $("#waveSwing").val();
	        var startDate = $("#startDate").val();
	        var endDate = $("#endDate").val();
	        var initMoney = $("#initMoney").val();
	        var waveShareNum = $("#waveShareNum").val();
	        var data = {
	            shareCode:shareCode,
	            waveSwing:waveSwing,
	            startDate:startDate,
	            endDate:endDate,
	            initMoney:initMoney,
	            waveShareNum:waveShareNum
	        };
	        var url="/shares/littleWave/waveBegin";
	        var _this = this;
	        this._api(url,data,function(res){
	            //显示执行结果
	            console.log(res);
	            _this.renderAllMoneyChart(data,res);
	            _this.renderPriceChart(data,res);
	        });
	    },
	    initListener:function(){
	        var _this = this;
	        $("#waveBtn").on("click",function(){
	            _this.beginOp();
	        });
	    },
	    _findMinMax:function(array){
	        var max = -10000;
	        var min = 10000;
	        for(var i=0;i<array.length;i++){
	            var item = array[i];
	            if(item>max){
	                max=item;
	            }
	            if(item<min){
	                min = item;
	            }
	        }
	        return {
	            max:max,
	            min:min,
	            cha:max-min
	        }
	    },
	    renderAllMoneyChart:function(options,waveData){
	        var bigTitle = options.shareCode+"代码"+options.startDate+"到"+options.endDate+"资金波动";

	        var flow=[];
	        var initMoneyFlow = [];
	        var labels = [];
	        var len = waveData.length;
	        for(var i=0;i<len;i++){
	            var waveItem = waveData[i];
	            flow.push(waveItem.allMoney/1000);
	            labels.push(i);
	            initMoneyFlow.push(options.initMoney/1000);
	        }

	        var mObj = this._findMinMax(flow);
	        console.log(mObj);
	        console.log(Math.floor(mObj.min))


	        var data = [
	            {
	                name : '总资金',
	                value:flow,
	                color:'#ec4646',
	                line_width:2
	            },
	            {
	                name:"成本资金",
	                value:initMoneyFlow,
	                color:'#4646ec',
	                line_width:2
	            }
	        ];

	        var chart = new iChart.LineBasic2D({
	            render : 'waveChart',
	            data: data,
	            align:'center',
	            title : {
	                text:bigTitle,
	                font : '微软雅黑',
	                fontsize:24,
	                color:'#b4b4b4'
	            },
	            subtitle : {
	                text:'',
	                font : '微软雅黑',
	                color:'#b4b4b4'
	            },
	            footnote : {
	                text:'小幅度波动分析',
	                font : '微软雅黑',
	                fontsize:11,
	                fontweight:600,
	                padding:'0 28',
	                color:'#b4b4b4'
	            },
	            width : 800,
	            height : 400,
	            shadow:true,
	            shadow_color : '#ff0000',
	            shadow_blur : 8,
	            shadow_offsetx : 0,
	            shadow_offsety : 0,
	            background_color:'#2e2e2e',
	            animation : true,//开启过渡动画
	            animation_duration:600,//600ms完成动画
	            tip:{
	                enable:true,
	                shadow:true,
	                listeners:{
	                    //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
	                    parseText:function(tip,name,value,text,i){
	                        if(name=="总资金"){
	                            return "<span style='color:#005268;font-size:12px;'>"+waveData[i].today+":日资金:<br/>"+
	                                "</span><span style='color:#005268;font-size:20px;'>"+value+"千元</span>";
	                        }else{
	                            return "<span style='color:#005268;font-size:12px;'>"+waveData[i].today+":日成本资金:<br/>"+
	                                "</span><span style='color:#005268;font-size:20px;'>"+value+"千元</span>";
	                        }
	                    }

	                }
	            },
	            crosshair:{
	                enable:true,
	                line_color:'#ec4646'
	            },
	            sub_option : {
	                smooth : true,
	                label:false,
	                hollow:false,
	                hollow_inside:false,
	                point_size:8
	            },
	            coordinate:{
	                width:640,
	                height:260,
	                striped_factor : 0.18,
	                grid_color:'#4e4e4e',
	                axis:{
	                    color:'#252525',
	                    width:[0,0,4,4]
	                },
	                scale:[{
	                    position:'left',
	                    start_scale:Math.floor(mObj.min),
	                    end_scale:Math.ceil(mObj.max),
	                    scale_space:0,
	                    scale_size:2,
	                    scale_enable : false,
	                    label : {color:'#9d987a',font : '微软雅黑',fontsize:11,fontweight:600},
	                    scale_color:'#9f9f9f'
	                },{
	                    position:'bottom',
	                    label : {color:'#9d987a',font : '微软雅黑',fontsize:11,fontweight:600},
	                    scale_enable : false,
	                    labels:labels
	                }]
	            }
	        });
	        //利用自定义组件构造左侧说明文本
	        chart.plugin(new iChart.Custom({
	            drawFn:function(){
	                //计算位置
	                var coo = chart.getCoordinate(),
	                    x = coo.get('originx'),
	                    y = coo.get('originy'),
	                    w = coo.width,
	                    h = coo.height;
	                //在左上侧的位置，渲染一个单位的文字
	                chart.target.textAlign('start')
	                    .textBaseline('bottom')
	                    .textFont('600 11px 微软雅黑')
	                    .fillText('资金(千元)',x-40,y-12,false,'#9d987a')
	                    .textBaseline('top')
	                    .fillText('(时间)',x+w+12,y+h+10,false,'#9d987a');

	            }
	        }));
	        //开始画图
	        chart.draw();
	    },
	    renderPriceChart:function(options,waveData){
	        var bigTitle = options.shareCode+"代码"+options.startDate+"到"+options.endDate+"价格波动";

	        var sharePrice=[];
	        var cbPrice = [];
	        var labels = [];
	        var len = waveData.length;
	        for(var i=0;i<len;i++){
	            var waveItem = waveData[i];
	            sharePrice.push(waveItem.sharePrice);
	            labels.push(i);
	            cbPrice.push(waveItem.cbPrice);
	        }

	        var mObj = this._findMinMax([].concat(sharePrice,cbPrice));


	        var data = [
	            {
	                name : '当前股价',
	                value:sharePrice,
	                color:'#ec4646',
	                line_width:2
	            },
	            {
	                name:"成本价",
	                value:cbPrice,
	                color:'#4646ec',
	                line_width:2
	            }
	        ];

	        var chart = new iChart.LineBasic2D({
	            render : 'wavePriceChart',
	            data: data,
	            align:'center',
	            title : {
	                text:bigTitle,
	                font : '微软雅黑',
	                fontsize:24,
	                color:'#b4b4b4'
	            },
	            subtitle : {
	                text:'',
	                font : '微软雅黑',
	                color:'#b4b4b4'
	            },
	            footnote : {
	                text:'小幅度价格波动分析',
	                font : '微软雅黑',
	                fontsize:11,
	                fontweight:600,
	                padding:'0 28',
	                color:'#b4b4b4'
	            },
	            width : 800,
	            height : 400,
	            shadow:true,
	            shadow_color : '#ff0000',
	            shadow_blur : 8,
	            shadow_offsetx : 0,
	            shadow_offsety : 0,
	            background_color:'#2e2e2e',
	            animation : true,//开启过渡动画
	            animation_duration:600,//600ms完成动画
	            tip:{
	                enable:true,
	                shadow:true,
	                listeners:{
	                    //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
	                    parseText:function(tip,name,value,text,i){
	                        if(name=="当前股价"){
	                            return "<span style='color:#005268;font-size:12px;'>"+waveData[i].today+":日当前价:<br/>"+
	                                "</span><span style='color:#005268;font-size:20px;'>"+value+"元</span>";
	                        }else{
	                            return "<span style='color:#005268;font-size:12px;'>"+waveData[i].today+":日成本价:<br/>"+
	                                "</span><span style='color:#005268;font-size:20px;'>"+value+"元</span>";
	                        }
	                    }

	                }
	            },
	            crosshair:{
	                enable:true,
	                line_color:'#ec4646'
	            },
	            sub_option : {
	                smooth : true,
	                label:false,
	                hollow:false,
	                hollow_inside:false,
	                point_size:8
	            },
	            coordinate:{
	                width:640,
	                height:260,
	                striped_factor : 0.18,
	                grid_color:'#4e4e4e',
	                axis:{
	                    color:'#252525',
	                    width:[0,0,4,4]
	                },
	                scale:[{
	                    position:'left',
	                    start_scale:Math.floor(mObj.min),
	                    end_scale:Math.ceil(mObj.max),
	                    scale_space:0,
	                    scale_size:2,
	                    scale_enable : false,
	                    label : {color:'#9d987a',font : '微软雅黑',fontsize:11,fontweight:600},
	                    scale_color:'#9f9f9f'
	                },{
	                    position:'bottom',
	                    label : {color:'#9d987a',font : '微软雅黑',fontsize:11,fontweight:600},
	                    scale_enable : false,
	                    labels:labels
	                }]
	            }
	        });
	        //利用自定义组件构造左侧说明文本
	        chart.plugin(new iChart.Custom({
	            drawFn:function(){
	                //计算位置
	                var coo = chart.getCoordinate(),
	                    x = coo.get('originx'),
	                    y = coo.get('originy'),
	                    w = coo.width,
	                    h = coo.height;
	                //在左上侧的位置，渲染一个单位的文字
	                chart.target.textAlign('start')
	                    .textBaseline('bottom')
	                    .textFont('600 11px 微软雅黑')
	                    .fillText('资金(千元)',x-40,y-12,false,'#9d987a')
	                    .textBaseline('top')
	                    .fillText('(时间)',x+w+12,y+h+10,false,'#9d987a');

	            }
	        }));
	        //开始画图
	        chart.draw();
	    }
	};


	$(document).ready(function(){
	    main.init();
	});

/***/ }
/******/ ]);