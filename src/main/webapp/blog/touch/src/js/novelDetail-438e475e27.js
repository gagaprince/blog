!function(i){function t(e){if(o[e])return o[e].exports;var n=o[e]={exports:{},id:e,loaded:!1};return i[e].call(n.exports,n,n.exports,t),n.loaded=!0,n.exports}var o={};t.m=i,t.c=o,t.p="",t(0)}([function(i,t,o){o(15)},,,,function(i,t){"use strict";var o={novelHistory:null,_api:function(i,t,o){$.ajax({url:i,data:t,type:"POST",dataType:"json",timeout:3e4,success:function(i){"string"==typeof i&&(i=JSON.parse(i)),console.log(i);var t=i.status;o&&o(t.code,t.des,i.data,i)}})},giveMeNovelListAll:function(i){this._api("/blog/pl/nv/novelListAll",{},function(t,o,e,n){0==t&&i&&i(e)})},giveMeNovelListPage:function(i,t){this._api("/blog/pl/nv/novelListPage",{pno:i,psize:20},function(i,o,e,n){0==i&&t&&t(e)})},giveMeNovelCateListPage:function(i,t,o){this._api("/blog/pl/nv/novelCateListPage",{pno:i,psize:20,cate:t},function(i,t,e,n){0==i&&o&&o(e)})},giveMeNovelIndexListPage:function(i,t,o){this._api("/blog/pl/nv/novelIndexListPage",{novelId:i,pno:t,psize:20},function(i,t,e,n){0==i&&o&&o(e)})},giveMeNovelListByKey:function(i,t,o){o&&this._api("/blog/pl/nv/novelSearchListPage",{pno:i,psize:20,key:t},function(i,t,e,n){0==i&&o&&o(e)})},giveMeNovelByIdAndChapter:function(i,t,e){o._api("/blog/pl/nv/getNovelContent",{novelId:i,chapter:t},function(i,t,o,n){0==i&&e&&e(o)})},giveMeRandomBooks:function(i,t){o._api("/blog/pl/nv/novelRandomBooks",{num:i},function(i,o,e,n){0==i&&t&&t(e)})},giveMeNovelById:function(i,t){t&&o._api("/blog/pl/nv/novelById",{id:i},function(i,o,e,n){0==i&&t(e)})},giveMeNovelsByIds:function(i,t){t&&o._api("/blog/pl/nv/novelByIds",{ids:i},function(i,o,e,n){0==i&&t(e)})},giveMeNovelAllById:function(i,t){t&&o._api("/blog/pl/nv/novelById",{id:i,needAll:1},function(i,o,e,n){0==i&&t(e)})},giveMeRecommendData:function(i){i&&o._api("/blog/pl/nv/recommendPage",{},function(t,o,e,n){0==t&&i(e)})},getQueryString:function(i,t){var o=new RegExp("(^|&)"+i+"=([^&]*)(&|$)","i"),e=t||window.location.search.substr(1),n=e.match(o);return null!=n?decodeURIComponent(n[2]):null},initLocal:function(){this.novelHistory||(this.novelHistory=localStorage.getItem("blog_touch_novel_history"),this.novelHistory?this.novelHistory=JSON.parse(this.novelHistory):this.novelHistory={})},saveLocal:function(i,t,o){this.initLocal(),this.novelHistory[i]=[t,o],localStorage.setItem("blog_touch_novel_history",JSON.stringify(this.novelHistory))},getLocal:function(i){return this.initLocal(),this.novelHistory[i]},clone:function(i){var t={};for(var o in i){var e=i[o];"object"==typeof e&&(e=this.clone(e)),t[o]=e}return t}};i.exports=o},function(i,t,o){"use strict";var e=o(6),n=o(4),s={getBookList:function(){var i=e.getItem("blog_touch_book_list");return i||(i=[]),i},saveBookList:function(i){i&&e.setItem("blog_touch_book_list",i)},addBook:function(i){if(i)if("string"==typeof i){var t=this;n.giveMeNovelById(i,function(i){t.addBook(i)})}else{if(i.chapters){var o=n.clone(i);o.chapters=[],i=o}var e=this.getBookList();this.isExistBookById(i,e)?this.adjustTheFistBook(i.id):(e.unshift(i),this.saveBookList(e))}},updateBook:function(i){if(i){for(var t=this.getBookList(),o=0;o<t.length;o++){if(t[o].id==i.id){t[o]=i;break}}this.saveBookList(t)}},isExistBookById:function(i,t){var o=!1,e=i;"object"==typeof i&&(e=i.id),t||(t=this.getBookList());for(var n=0;n<t.length;n++){var i=t[n];if(i.id==e){o=!0;break}}return o},removeBookById:function(i){for(var t=this.getBookList(),o=0;o<t.length;o++){if(t[o].id==i){t.splice(o,1);break}}this.saveBookList(t),this.removeBookTag(i)},adjustTheFistBook:function(i){for(var t=this.getBookList(),o=0;o<t.length;o++){var e=t[o];if(e.id==i){var e=t.splice(o,1)[0];t.unshift(e);break}}this.saveBookList(t)},setHasGetInitBook:function(){e.setItem("blog_touch_novel_getinitbook",1)},isHasGetInitBook:function(){return e.getItem("blog_touch_novel_getinitbook")},saveBookTag:function(i,t,o){this.isExistBookById(i)||this.addBook(i);var n=[t,o];e.setItem("blog_touch_novel_history_"+i,n)},getBookTag:function(i){return e.getItem("blog_touch_novel_history_"+i)},removeBookTag:function(i){e.removeItem("blog_touch_novel_history_"+i)}};i.exports=s},function(i,t){"use strict";var o=window.localStorage,e={setItem:function(i,t){if(o&&o.setItem&&t){var e=t;"object"==typeof t&&(e=JSON.stringify(t)),o.setItem(i,e)}},getItem:function(i){if(o&&o.getItem){var t=o.getItem(i);return t&&(t=JSON.parse(t)),t}return null},removeItem:function(i){o&&o.removeItem&&o.removeItem(i)}};i.exports=e},,,,,,,,,function(i,t,o){var e=o(4),n=o(16),s=o(18),a=o(5),l={novelId:"",novelData:null,init:function(){n.init(),this.initPage(),this.initListener()},initPage:function(){var i=this;n.show(),this.initData(function(t){n.hide(),i.renderPage(t)})},initData:function(i){if(i){var t=this.novelId=e.getQueryString("novelId"),o=this;e.giveMeNovelAllById(t,function(t){o.novelData=t,i(t)})}},renderPage:function(i){console.log(i);var t=s(i);$("#detailPage").html(t).show(),$("img").error(function(){$(this).attr("src","http://www.37zw.com/d/image/3/3753/3753s.jpg")})},initListener:function(){var i=this;$("body").on("click",".backFrame",function(){history.back()}),$("body").on("click","#readBtn",function(){a.addBook(i.novelData),window.location.href="read.html?novelId="+i.novelId}),$("body").on("click",".novel-index-item",function(){a.addBook(i.novelData);var t=$(this).attr("chapter");window.location.href="read.html?novelId="+i.novelId+"&chapter="+t})}};$(document).ready(function(){l.init()})},function(i,t,o){"use strict";var e=o(17),n={init:function(){var i=e({});$("body").append(i)},show:function(){$(".loading-frame").show()},hide:function(){$(".loading-frame").hide()}};i.exports=n},function(i,t){i.exports=function(i){return'<div class="loading-frame h-c" style="display:none;"> <div class="search-load h-c"> <div class="my-dot"></div> <div class="my-dot"></div> <div class="my-dot"></div> </div></div>'}},function(i,t){i.exports=function(i){var t="",o=i,e=o.chapters;t+='<div class="header h-c"> <div class="backFrame"> <div class="back"> <div class="bg-frame"></div> <div class="front-frame"></div> </div> </div> <div class="title">'+o.name+'</div></div><div class="container"> <div class="info h-u"> <div class="left v-u"> <div class="imgframe"> <img src="http://www.37zw.com'+o.cover+'" alt=""/> </div> </div> <div class="right"> <div class="name">'+o.name+'</div> <div class="info-item h-l-u"> <div class="lb">作者：</div> <div class="lbv">'+o.author+'</div> </div> <div class="info-item h-l-u"> <div class="lb">分类：</div> <div class="lbv">'+o.cate+'</div> </div> <div class="info-item h-l-u"> <div class="lb">更新：</div> <div class="lbv">'+o.updateTime.split(" ")[0]+'</div> </div> <div class="info-item h-l-u"> <div class="lb">最新：</div> <div class="lbv">'+e[0].name+'</div> </div> </div> </div> <div class="read-btn-frame h-c"> <div class="read-btn h-c" id="readBtn" onclick="">开始阅读</div> </div> <div class="desc-frame"> <div class="desc-t h-c"> <div class="desc-title h-l">小说简介</div> </div> <div class="desc-info h-c"> <div class="desc"> '+o.descripe+' </div> </div> </div> <div class="novel-index-frame"> <div class="novel-index-t h-c"> <div class="novel-index-title h-l"> 目录 </div> </div> <div class="novel-index-list"> ';var n=e;if(n)for(var s,a=-1,l=n.length-1;a<l;)s=n[a+=1],t+=' <div class="novel-index-item h-c" onclick="" chapter="'+s.chapter+'"> <div class="novel-index-item-info h-l">'+s.name+"</div> </div> ";return t+=" </div> </div></div>"}}]);