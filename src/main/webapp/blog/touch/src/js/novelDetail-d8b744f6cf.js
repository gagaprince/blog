!function(i){function n(t){if(e[t])return e[t].exports;var o=e[t]={exports:{},id:t,loaded:!1};return i[t].call(o.exports,o,o.exports,n),o.loaded=!0,o.exports}var e={};return n.m=i,n.c=e,n.p="",n(0)}([function(i,n,e){e(8)},,function(i,n){"use strict";var e="/blog/pl/nv/novelListAll",t="/blog/pl/nv/novelListPage",o="/blog/pl/nv/getNovelContent",l="/blog/pl/nv/novelIndexListPage",s="/blog/pl/nv/novelRandomBooks",v="/blog/pl/nv/novelById",a="blog_touch_novel_history",d={novelHistory:null,_api:function(i,n,e){$.ajax({url:i,data:n,type:"POST",dataType:"json",timeout:3e4,success:function(i){"string"==typeof i&&(i=JSON.parse(i)),console.log(i);var n=i.status;e&&e(n.code,n.des,i.data,i)}})},giveMeNovelListAll:function(i){this._api(e,{},function(n,e,t,o){0==n&&i&&i(t)})},giveMeNovelListPage:function(i,n){this._api(t,{pno:i,psize:20},function(i,e,t,o){0==i&&n&&n(t)})},giveMeNovelIndexListPage:function(i,n,e){this._api(l,{novelId:i,pno:n,psize:20},function(i,n,t,o){0==i&&e&&e(t)})},giveMeNovelByIdAndChapter:function(i,n,e){d._api(o,{novelId:i,chapter:n},function(i,n,t,o){0==i&&e&&e(t)})},giveMeRandomBooks:function(i,n){d._api(s,{num:i},function(i,e,t,o){0==i&&n&&n(t)})},giveMeNovelById:function(i,n){n&&d._api(v,{id:i},function(i,e,t,o){0==i&&n(t)})},giveMeNovelAllById:function(i,n){n&&d._api(v,{id:i,needAll:1},function(i,e,t,o){0==i&&n(t)})},getQueryString:function(i,n){var e=new RegExp("(^|&)"+i+"=([^&]*)(&|$)","i"),t=n||window.location.search.substr(1),o=t.match(e);return null!=o?decodeURIComponent(o[2]):null},initLocal:function(){this.novelHistory||(this.novelHistory=localStorage.getItem(a),this.novelHistory?this.novelHistory=JSON.parse(this.novelHistory):this.novelHistory={})},saveLocal:function(i,n,e){this.initLocal(),this.novelHistory[i]=[n,e],localStorage.setItem(a,JSON.stringify(this.novelHistory))},getLocal:function(i){return this.initLocal(),this.novelHistory[i]},clone:function(i){var n={};for(var e in i){var t=i[e];"object"==typeof t&&(t=this.clone(t)),n[e]=t}return n}};i.exports=d},,,,,,function(i,n,e){var t=e(2),o=e(9),l={novelId:"",novelData:null,init:function(){this.initPage(),this.initListener()},initPage:function(){var i=this;this.initData(function(n){i.renderPage(n)})},initData:function(i){if(i){var n=this.novelId=t.getQueryString("novelId"),e=this;t.giveMeNovelAllById(n,function(n){e.novelData=n,i(n)})}},renderPage:function(i){console.log(i);var n=o(i);$("body").html(n)},initListener:function(){var i=this;$("body").on("click","#readBtn",function(){window.location.href="read.html?novelId="+i.novelId}),$("body").on("click",".novel-index-item",function(){var n=$(this).attr("chapter");window.location.href="read.html?novelId="+i.novelId+"&chapter="+n})}};$(document).ready(function(){l.init()})},function(i,n){i.exports=function(i){var n="",e=i,t=e.chapters;n+='<div class="header h-c"> <div class="backFrame"> <div class="back"> <div class="bg-frame"></div> <div class="front-frame"></div> </div> </div> <div class="title">'+e.name+'</div></div><div class="container"> <div class="info h-u"> <div class="left v-u"> <div class="imgframe"> <img src="http://www.37zw.com'+e.cover+'" alt=""/> </div> </div> <div class="right"> <div class="name">'+e.name+'</div> <div class="info-item h-l-u"> <div class="lb">作者：</div> <div class="lbv">'+e.author+'</div> </div> <div class="info-item h-l-u"> <div class="lb">分类：</div> <div class="lbv">'+e.cate+'</div> </div> <div class="info-item h-l-u"> <div class="lb">更新：</div> <div class="lbv">'+e.updateTime.split(" ")[0]+'</div> </div> <div class="info-item h-l-u"> <div class="lb">最新：</div> <div class="lbv">'+t[0].name+'</div> </div> </div> </div> <div class="read-btn-frame h-c"> <div class="read-btn h-c" id="readBtn" onclick="">开始阅读</div> </div> <div class="desc-frame"> <div class="desc-t h-c"> <div class="desc-title h-l">小说简介</div> </div> <div class="desc-info h-c"> <div class="desc"> '+e.descripe+' </div> </div> </div> <div class="novel-index-frame"> <div class="novel-index-t h-c"> <div class="novel-index-title h-l"> 目录 </div> </div> <div class="novel-index-list"> ';var o=t;if(o)for(var l,s=-1,v=o.length-1;s<v;)l=o[s+=1],n+=' <div class="novel-index-item h-c" onclick="" chapter="'+l.chapter+'"> <div class="novel-index-item-info h-l">'+l.name+"</div> </div> ";return n+=" </div> </div></div>"}}]);