!function(n){function i(e){if(t[e])return t[e].exports;var o=t[e]={exports:{},id:e,loaded:!1};return n[e].call(o.exports,o,o.exports,i),o.loaded=!0,o.exports}var t={};return i.m=n,i.c=t,i.p="",i(0)}({0:function(n,i,t){t(12)},2:function(n,i){"use strict";var t="/blog/pl/nv/novelListAll",e="/blog/pl/nv/novelListPage",o="/blog/pl/nv/getNovelContent",l="/blog/pl/nv/novelIndexListPage",r="/blog/pl/nv/novelRandomBooks",s="/blog/pl/nv/novelById",a="blog_touch_novel_history",c={novelHistory:null,_api:function(n,i,t){$.ajax({url:n,data:i,type:"POST",dataType:"json",timeout:3e4,success:function(n){"string"==typeof n&&(n=JSON.parse(n)),console.log(n);var i=n.status;t&&t(i.code,i.des,n.data,n)}})},giveMeNovelListAll:function(n){this._api(t,{},function(i,t,e,o){0==i&&n&&n(e)})},giveMeNovelListPage:function(n,i){this._api(e,{pno:n,psize:20},function(n,t,e,o){0==n&&i&&i(e)})},giveMeNovelIndexListPage:function(n,i,t){this._api(l,{novelId:n,pno:i,psize:20},function(n,i,e,o){0==n&&t&&t(e)})},giveMeNovelByIdAndChapter:function(n,i,t){c._api(o,{novelId:n,chapter:i},function(n,i,e,o){0==n&&t&&t(e)})},giveMeRandomBooks:function(n,i){c._api(r,{num:n},function(n,t,e,o){0==n&&i&&i(e)})},giveMeNovelById:function(n,i){i&&c._api(s,{id:n},function(n,t,e,o){0==n&&i(e)})},giveMeNovelAllById:function(n,i){i&&c._api(s,{id:n,needAll:1},function(n,t,e,o){0==n&&i(e)})},getQueryString:function(n,i){var t=new RegExp("(^|&)"+n+"=([^&]*)(&|$)","i"),e=i||window.location.search.substr(1),o=e.match(t);return null!=o?decodeURIComponent(o[2]):null},initLocal:function(){this.novelHistory||(this.novelHistory=localStorage.getItem(a),this.novelHistory?this.novelHistory=JSON.parse(this.novelHistory):this.novelHistory={})},saveLocal:function(n,i,t){this.initLocal(),this.novelHistory[n]=[i,t],localStorage.setItem(a,JSON.stringify(this.novelHistory))},getLocal:function(n){return this.initLocal(),this.novelHistory[n]},clone:function(n){var i={};for(var t in n){var e=n[t];"object"==typeof e&&(e=this.clone(e)),i[t]=e}return i}};n.exports=c},12:function(n,i,t){"use strict";var e=t(2),o=t(13),l=null,r={currentPage:0,init:function(){this.initPage(),this.initListener()},initPage:function(){$("#novelList").html(""),this.renderPage()},renderPage:function(){var n=this,i=this.currentPage;e.giveMeNovelListPage(i,function(i){var t=i.novelList,e=o(t);$("#novelList").append(e),n.currentPage++})},initListener:function(){var n=this;$("body").on("click",".novel-item",function(){var n=$(this).attr("novelId");window.location.href="indexlist.html?novelId="+n}),$(window).scroll(function(){$(window).scrollTop()>=$(document).height()-$(window).height()&&(l&&(clearTimeout(l),l=null),l=setTimeout(function(){n.renderPage()},300))})}};$(document).ready(function(){r.init()})},13:function(n,i){n.exports=function(n){var i="",t=n,e=t;if(e)for(var o,l=-1,r=e.length-1;l<r;)o=e[l+=1],i+='<div class="item h-l novel-item" novelId="'+o.id+'" link="'+o.sourceUrl+'" onclick=""> <div class="novel-name">'+(o.name||"")+'</div> <div class="arrow-frame"> <div class="arrow"> <div class="bg-frame"></div> <div class="front-frame"></div> </div> </div></div>';return i}}});