!function(n){function t(i){if(e[i])return e[i].exports;var o=e[i]={exports:{},id:i,loaded:!1};return n[i].call(o.exports,o,o.exports,t),o.loaded=!0,o.exports}var e={};return t.m=n,t.c=e,t.p="",t(0)}([function(n,t,e){e(6)},,function(n,t){"use strict";var e="/blog/pl/nv/novelListAll",i="/blog/pl/nv/novelListPage",o="/blog/pl/nv/getNovelContent",r="/blog/pl/nv/novelIndexListPage",l="/blog/pl/nv/novelRandomBooks",s="blog_touch_novel_history",a={novelHistory:null,_api:function(n,t,e){$.ajax({url:n,data:t,type:"POST",dataType:"json",timeout:3e4,success:function(n){"string"==typeof n&&(n=JSON.parse(n)),console.log(n);var t=n.status;e&&e(t.code,t.des,n.data,n)}})},giveMeNovelListAll:function(n){this._api(e,{},function(t,e,i,o){0==t&&n&&n(i)})},giveMeNovelListPage:function(n,t){this._api(i,{pno:n,psize:20},function(n,e,i,o){0==n&&t&&t(i)})},giveMeNovelIndexListPage:function(n,t,e){this._api(r,{novelId:n,pno:t,psize:20},function(n,t,i,o){0==n&&e&&e(i)})},giveMeNovelByIdAndChapter:function(n,t,e){a._api(o,{novelId:n,chapter:t},function(n,t,i,o){0==n&&e&&e(i)})},giveMeRandomBooks:function(n,t){a._api(l,{num:n},function(n,e,i,o){0==n&&t&&t(i)})},getQueryString:function(n,t){var e=new RegExp("(^|&)"+n+"=([^&]*)(&|$)","i"),i=t||window.location.search.substr(1),o=i.match(e);return null!=o?decodeURIComponent(o[2]):null},initLocal:function(){this.novelHistory||(this.novelHistory=localStorage.getItem(s),this.novelHistory?this.novelHistory=JSON.parse(this.novelHistory):this.novelHistory={})},saveLocal:function(n,t,e){this.initLocal(),this.novelHistory[n]=[t,e],localStorage.setItem(s,JSON.stringify(this.novelHistory))},getLocal:function(n){return this.initLocal(),this.novelHistory[n]}};n.exports=a},,,,function(n,t,e){"use strict";var i=e(2),o=e(7),r=null,l={currentPage:0,init:function(){this.initPage(),this.initListener()},initPage:function(){$("#novelIndexList").html(""),this.renderPage()},renderPage:function(){var n=this,t=i.getQueryString("novelId"),e=this.currentPage;i.giveMeNovelIndexListPage(t,e,function(t){var e=t.indexList,i=o(e);$("#novelIndexList").append(i),n.currentPage++})},initListener:function(){var n=this;$("body").on("click",".novel-item",function(){var n=i.getQueryString("novelId"),t=$(this).attr("chapter");window.location.href="read.html?novelId="+n+"&chapter="+t}),$(window).scroll(function(){$(window).scrollTop()>=$(document).height()-$(window).height()&&(r&&(clearTimeout(r),r=null),r=setTimeout(function(){n.renderPage()},300))})}};$(document).ready(function(){l.init()})},function(n,t){n.exports=function(n){var t="",e=n,i=e;if(i)for(var o,r=-1,l=i.length-1;r<l;)o=i[r+=1],t+='<div class="item h-l novel-item" chapter="'+o.chapter+'" onclick=""> <div class="novel-name">'+(o.name||"")+'</div> <div class="arrow-frame"> <div class="arrow"> <div class="bg-frame"></div> <div class="front-frame"></div> </div> </div></div>';return t}}]);