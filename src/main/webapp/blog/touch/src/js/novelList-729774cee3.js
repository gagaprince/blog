!function(e){function i(n){if(t[n])return t[n].exports;var o=t[n]={exports:{},id:n,loaded:!1};return e[n].call(o.exports,o,o.exports,i),o.loaded=!0,o.exports}var t={};return i.m=e,i.c=t,i.p="",i(0)}([function(e,i,t){t(14)},,function(e,i){"use strict";var t="/blog/pl/nv/novelListAll",n="/blog/pl/nv/novelListPage",o="/blog/pl/nv/novelCateListPage",s="/blog/pl/nv/getNovelContent",l="/blog/pl/nv/novelIndexListPage",a="/blog/pl/nv/novelRandomBooks",c="/blog/pl/nv/novelById",r="/blog/pl/nv/recommendPage",v="/blog/pl/nv/novelSearchListPage",d="blog_touch_novel_history",u={novelHistory:null,_api:function(e,i,t){$.ajax({url:e,data:i,type:"POST",dataType:"json",timeout:3e4,success:function(e){"string"==typeof e&&(e=JSON.parse(e)),console.log(e);var i=e.status;t&&t(i.code,i.des,e.data,e)}})},giveMeNovelListAll:function(e){this._api(t,{},function(i,t,n,o){0==i&&e&&e(n)})},giveMeNovelListPage:function(e,i){this._api(n,{pno:e,psize:20},function(e,t,n,o){0==e&&i&&i(n)})},giveMeNovelCateListPage:function(e,i,t){this._api(o,{pno:e,psize:20,cate:i},function(e,i,n,o){0==e&&t&&t(n)})},giveMeNovelIndexListPage:function(e,i,t){this._api(l,{novelId:e,pno:i,psize:20},function(e,i,n,o){0==e&&t&&t(n)})},giveMeNovelListByKey:function(e,i,t){t&&this._api(v,{pno:e,psize:20,key:i},function(e,i,n,o){0==e&&t&&t(n)})},giveMeNovelByIdAndChapter:function(e,i,t){u._api(s,{novelId:e,chapter:i},function(e,i,n,o){0==e&&t&&t(n)})},giveMeRandomBooks:function(e,i){u._api(a,{num:e},function(e,t,n,o){0==e&&i&&i(n)})},giveMeNovelById:function(e,i){i&&u._api(c,{id:e},function(e,t,n,o){0==e&&i(n)})},giveMeNovelAllById:function(e,i){i&&u._api(c,{id:e,needAll:1},function(e,t,n,o){0==e&&i(n)})},giveMeRecommendData:function(e){e&&u._api(r,{},function(i,t,n,o){0==i&&e(n)})},getQueryString:function(e,i){var t=new RegExp("(^|&)"+e+"=([^&]*)(&|$)","i"),n=i||window.location.search.substr(1),o=n.match(t);return null!=o?decodeURIComponent(o[2]):null},initLocal:function(){this.novelHistory||(this.novelHistory=localStorage.getItem(d),this.novelHistory?this.novelHistory=JSON.parse(this.novelHistory):this.novelHistory={})},saveLocal:function(e,i,t){this.initLocal(),this.novelHistory[e]=[i,t],localStorage.setItem(d,JSON.stringify(this.novelHistory))},getLocal:function(e){return this.initLocal(),this.novelHistory[e]},clone:function(e){var i={};for(var t in e){var n=e[t];"object"==typeof n&&(n=this.clone(n)),i[t]=n}return i}};e.exports=u},,,,,,,function(e,i,t){"use strict";var n=t(10),o={init:function(){var e=n({});$("body").append(e)},show:function(){$(".loading-frame").show()},hide:function(){$(".loading-frame").hide()}};e.exports=o},function(e,i){e.exports=function(e){var i='<div class="loading-frame h-c" style="display:none;"> <div class="search-load h-c"> <div class="my-dot"></div> <div class="my-dot"></div> <div class="my-dot"></div> </div></div>';return i}},,,,function(e,i,t){"use strict";var n=t(2),o=t(15),s=t(9),l=null,a={currentPage:0,cate:null,type:"cate",key:"",init:function(){s.init(),this.initPage(),this.initListener()},initPage:function(){this.type=n.getQueryString("type")||this.type,"search"==this.type?(this.key=n.getQueryString("key"),this.key&&($(".title").html(this.key),$("title").html(this.key),$("#novelList").html(""),this.renderPage())):(this.cate=n.getQueryString("cate")||"玄幻小说",$(".title").html(this.cate),$("title").html(this.cate),$("#novelList").html(""),this.renderPage())},renderPage:function(){s.show();var e=this,i=this.currentPage;if("search"==this.type){var t=this.key;n.giveMeNovelListByKey(i,t,function(i){var t=i.novelList,n=o(t);$("#novelList").append(n),e.currentPage++,s.hide()})}else{var l=this.cate;n.giveMeNovelCateListPage(i,l,function(i){var t=i.novelList,n=o(t);$("#novelList").append(n),e.currentPage++,s.hide()})}},initListener:function(){var e=this;$("body").on("click",".backFrame",function(){history.back()}),$("body").on("click",".novel-item",function(){var e=$(this).attr("novelId");window.location.href="detail.html?novelId="+e}),$(window).scroll(function(){$(window).scrollTop()>=$(document).height()-$(window).height()&&(l&&(clearTimeout(l),l=null),l=setTimeout(function(){e.renderPage()},300))})}};$(document).ready(function(){a.init()})},function(e,i){e.exports=function(e){var i="",t=e,n=t;if(n)for(var o,s=-1,l=n.length-1;s<l;){o=n[s+=1],i+='<div class="item h-l-u novel-item" novelId="'+o.id+'" link="'+o.sourceUrl+'" onclick=""> <div class="cover"> <img src="http://www.37zw.com'+o.cover+'" alt=""/> </div> <div class="desc-frame"> <div class="info h-l"> <div class="name h-c">'+o.name+'</div> <div class="author h-c">'+o.author+"</div> </div> ";var a=o.descripe.replace(/\w/g,"");a&&a.length>55&&(o.descripe=a.substr(0,55)+"..."),i+=' <div class="desc">'+(o.descripe||"")+'</div> </div> <div class="arrow-frame"> <div class="arrow"> <div class="bg-frame"></div> <div class="front-frame"></div> </div> </div></div>'}return i}}]);