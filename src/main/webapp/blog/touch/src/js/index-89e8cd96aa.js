!function(n){function e(i){if(t[i])return t[i].exports;var o=t[i]={exports:{},id:i,loaded:!1};return n[i].call(o.exports,o,o.exports,e),o.loaded=!0,o.exports}var t={};return e.m=n,e.c=t,e.p="",e(0)}([function(n,e,t){t(1)},function(n,e,t){var i=(t(2),{init:function(){}});$(document).ready(function(){i.init()})},function(n,e){var t=function(n,e){function t(n,t,i){e.ajax({url:n,data:t,type:"POST",timeout:3e4,success:function(n){i&&i(n)}})}function i(){t("/blog/wx/getShareBisic",{location:window.location.href},function(e){console.log(e),d=JSON.parse(e),n.config({debug:m,appId:d.appId,timestamp:d.timestamp,nonceStr:d.nonceStr,signature:d.signature,jsApiList:["onMenuShareTimeline","onMenuShareAppMessage"]}),n.ready(function(){o()}),n.error(function(n){})})}function o(){n.checkJsApi({jsApiList:["onMenuShareTimeline","onMenuShareAppMessage"],success:function(n){var e=n.checkResult;e.onMenuShareTimeline&&c(),e.onMenuShareAppMessage&&u()}})}function c(){g=e.extend(g,h),n.onMenuShareTimeline(g)}function u(){S=e.extend(S,h),n.onMenuShareAppMessage(S)}function s(n){h.title=n||h.title}function r(n){h.desc=n||h.desc}function a(n){h.link=n||h.link}function f(n){h.imgUrl=n||h.imgUrl}function l(n){h=n}function p(){c(),u()}var d={},h={title:document.title,link:self.location.href+"",desc:document.title,imgUrl:"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2117727038,2641018931&fm=116&gp=0.jpg"},m=(window.location.href+"").indexOf("debug")!=-1,g={success:function(){},cancel:function(){}},S={success:function(){},cancel:function(){}};return e(document).ready(function(){i()}),{setTitle:s,setDesc:r,setLink:a,setImgUrl:f,setShareObj:l,refresh:p}}(wx,$);n.exports=t}]);