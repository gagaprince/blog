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
/***/ function(module, exports, __webpack_require__) {

	var wxBridge = __webpack_require__(2);
	var index = {
	    init:function(){
	        //wxBridge.setTitle("自定义title");
	        //wxBridge.refresh();
	    }
	};
	$(document).ready(function(){
	    index.init();
	});


/***/ },
/* 2 */
/***/ function(module, exports) {

	var wxBridge = (function(wx,$){
	    var wxShareConfig = {};

	    var shareObj = {
	        "title":document.title,
	        "link":self.location.href+"",
	        "desc":document.title,
	        "imgUrl":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2117727038,2641018931&fm=116&gp=0.jpg"
	    }

	    var debugFlag = (window.location.href+"").indexOf("debug")!=-1;

	    function _api(url,data,callback){
	        $.ajax({
	            url:url,
	            data:data,
	            type : 'POST',
	            timeout : 3e4,
	            success:function(res){
	                if(callback){
	                    callback(res);
	                }
	            }
	        });
	    }

	    function initConfig(){
	        _api("/blog/wx/getShareBisic",{
	            location:window.location.href
	        },function(res){
	            console.log(res);
	            //alert(res);
	            wxShareConfig = JSON.parse(res);
	            wx.config({
	                debug: debugFlag, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	                appId: wxShareConfig.appId, // 必填，公众号的唯一标识
	                timestamp: wxShareConfig.timestamp, // 必填，生成签名的时间戳
	                nonceStr: wxShareConfig.nonceStr, // 必填，生成签名的随机串
	                signature: wxShareConfig.signature,// 必填，签名，见附录1
	                jsApiList: [
	                    "onMenuShareTimeline",//分享到朋友圈
	                    "onMenuShareAppMessage"//分享到朋友
	                ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	            });
	            wx.ready(function(){
	                onWXReady();
	                //alert("ready");
	                //alert(wx.onMenuShareTimeline);
	            });
	            wx.error(function(res){
	                //alert("失败");
	                // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
	            });
	        });
	    }

	    function onWXReady(){
	        wx.checkJsApi({
	            jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
	            success: function(res) {
	                var result = res.checkResult;
	                if(result["onMenuShareTimeline"]){
	                    initMenuShareTimelineListener();
	                }
	                if(result["onMenuShareAppMessage"]){
	                    initMenuShareAppListener();
	                }
	                // 以键值对的形式返回，可用的api值true，不可用为false
	                // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
	            }
	        });
	    }

	    var shareTimelineObj ={
	        success: function () {
	            //alert("分享朋友圈成功");
	        },
	        cancel: function () {
	        }
	    };
	    var shareAppMessageObj = {
	        success: function () {
	            //alert("分享朋友成功");
	        },
	        cancel: function () {
	        }
	    }

	    function initMenuShareTimelineListener(){
	        shareTimelineObj = $.extend(shareTimelineObj,shareObj);
	        //alert(JSON.stringify(shareTimelineObj));
	        wx.onMenuShareTimeline(shareTimelineObj);
	    }

	    function initMenuShareAppListener(){
	        shareAppMessageObj = $.extend(shareAppMessageObj,shareObj);
	        //alert(JSON.stringify(shareAppMessageObj));
	        wx.onMenuShareAppMessage(shareAppMessageObj);
	    }


	    //out function
	    function setTitle(title){
	        shareObj["title"] = title||shareObj["title"];
	    }
	    function setDesc(desc){
	        shareObj["desc"] = desc||shareObj["desc"];
	    }
	    function setLink(link){
	        shareObj["link"] = link||shareObj["link"];
	    }
	    function setImgUrl(imgUrl){
	        shareObj["imgUrl"] = imgUrl||shareObj["imgUrl"];
	    }
	    function setShareObj(shareObjSet){
	        shareObj = shareObjSet;
	    }
	    function refresh(){
	        initMenuShareTimelineListener();
	        initMenuShareAppListener();
	    }


	    $(document).ready(function(){
	        initConfig();
	    });

	    return {
	        setTitle:setTitle,
	        setDesc:setDesc,
	        setLink:setLink,
	        setImgUrl:setImgUrl,
	        setShareObj:setShareObj,
	        refresh:refresh
	    }

	})(wx,$);
	module.exports = wxBridge;


/***/ }
/******/ ]);