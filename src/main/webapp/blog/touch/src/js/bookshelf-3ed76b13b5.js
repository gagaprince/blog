!function(t){function o(i){if(e[i])return e[i].exports;var n=e[i]={exports:{},id:i,loaded:!1};return t[i].call(n.exports,n,n.exports,o),n.loaded=!0,n.exports}var e={};return o.m=t,o.c=e,o.p="",o(0)}([function(t,o,e){e(3)},,,function(t,o,e){"use strict";var i=e(4),n=e(5),s=e(7),a=6,r={bookShelfState:0,novelList:null,updateMap:null,init:function(){this.initPage(),this.initListener()},initPage:function(){var t=this;this.initBookList(function(o){t.novelList=o;var e={state:t.bookShelfState,novelList:t.cloneNovelList(o)};t.renderPage(e),t.checkUpdate()})},checkUpdate:function(){for(var t=this,o=this.novelList,e=o.length,n="",s=0;s<e;s++){var a=o[s];n+=","+a.id}n&&(n=n.substring(1)),n&&i.giveMeNovelsByIds(n,function(o){console.log(o),t.updateMap=t._makeUpdateMap(o),t.renderUpdate()})},_makeUpdateMap:function(t){for(var o={},e=t.length,i=0;i<e;i++){var n=t[i],s=n.id;o[s]=n}return o},renderUpdate:function(){var t=this.novelList,o=this.updateMap;if(t&&o&&t.length>0)for(var e=t.length,i=0;i<e;i++){var n=t[i],s=n.id,a=o[s];n.updateTime!=a.updateTime&&this.renderUpdateNovel(n)}},renderUpdateNovel:function(t){var o=t.id;$("#novel_"+o).find(".update").show()},updateBookShelfWidthNew:function(t){var o=this.updateMap,e=o[t];n.updateBook(e),$("#novel_"+t).find(".update").hide()},initListener:function(){var t=this;$("body").on("click",".bookItem",function(){if(0==t.bookShelfState){var o=$(this).attr("novelId");window.location.href="detail.html?novelId="+o,t.updateBookShelfWidthNew(o)}}),$("body").on("click",".bookAddBtn",function(){0==t.bookShelfState&&(window.location.href="index.html")}),$("body").on("click","#bjBtn",function(){0==t.bookShelfState?($(".remove-btn").show(),t.bookShelfState=1,$(this).html("完成")):($(".remove-btn").hide(),t.bookShelfState=0,$(this).html("编辑"))}),$("body").on("click",".remove-btn",function(){var o=$(this).attr("novelId");n.removeBookById(o),t.initPage()})},initBookList:function(t){if(t){var o=n.getBookList();o&&o.length?t(o):n.isHasGetInitBook()?t([]):i.giveMeRandomBooks(a,function(o){t(o),n.saveBookList(o)})}},renderPage:function(t){var o=s(t);$("#bookshelfFrame").html(o)},_filterNovelList:function(t){if(t)for(var o=0;o<t.length;o++){t[o]}},cloneNovelList:function(t){for(var o=[],e=0;e<t.length;e++){var n=t[e],s=i.clone(n);o.push(s)}return o}};$(document).ready(function(){r.init()})},function(t,o){"use strict";var e="/blog/pl/nv/novelListAll",i="/blog/pl/nv/novelListPage",n="/blog/pl/nv/novelCateListPage",s="/blog/pl/nv/getNovelContent",a="/blog/pl/nv/novelIndexListPage",r="/blog/pl/nv/novelRandomBooks",l="/blog/pl/nv/novelById",v="/blog/pl/nv/novelByIds",c="/blog/pl/nv/recommendPage",d="/blog/pl/nv/novelSearchListPage",u="blog_touch_novel_history",f={novelHistory:null,_api:function(t,o,e){$.ajax({url:t,data:o,type:"POST",dataType:"json",timeout:1e5,success:function(t){"string"==typeof t&&(t=JSON.parse(t)),console.log(t);var o=t.status;e&&e(o&&o.code,o&&o.des,t.data,t)}})},giveMeNovelListAll:function(t){this._api(e,{},function(o,e,i,n){0==o&&t&&t(i)})},giveMeNovelListPage:function(t,o){this._api(i,{pno:t,psize:20},function(t,e,i,n){0==t&&o&&o(i)})},giveMeNovelCateListPage:function(t,o,e){this._api(n,{pno:t,psize:20,cate:o},function(t,o,i,n){0==t&&e&&e(i)})},giveMeNovelIndexListPage:function(t,o,e){this._api(a,{novelId:t,pno:o,psize:20},function(t,o,i,n){0==t&&e&&e(i)})},giveMeNovelListByKey:function(t,o,e){e&&this._api(d,{pno:t,psize:20,key:o},function(t,o,i,n){0==t&&e&&e(i)})},giveMeNovelByIdAndChapter:function(t,o,e){f._api(s,{novelId:t,chapter:o},function(t,o,i,n){0==t&&e&&e(i)})},giveMeRandomBooks:function(t,o){f._api(r,{num:t},function(t,e,i,n){0==t&&o&&o(i)})},giveMeNovelById:function(t,o){o&&f._api(l,{id:t},function(t,e,i,n){0==t&&o(i)})},giveMeNovelsByIds:function(t,o){o&&f._api(v,{ids:t},function(t,e,i,n){0==t&&o(i)})},giveMeNovelAllById:function(t,o){o&&f._api(l,{id:t,needAll:1},function(t,e,i,n){0==t&&o(i)})},giveMeRecommendData:function(t){t&&f._api(c,{},function(o,e,i,n){0==o&&t(i)})},getQueryString:function(t,o){var e=new RegExp("(^|&)"+t+"=([^&]*)(&|$)","i"),i=o||window.location.search.substr(1),n=i.match(e);return null!=n?decodeURIComponent(n[2]):null},initLocal:function(){this.novelHistory||(this.novelHistory=localStorage.getItem(u),this.novelHistory?this.novelHistory=JSON.parse(this.novelHistory):this.novelHistory={})},saveLocal:function(t,o,e){this.initLocal(),this.novelHistory[t]=[o,e],localStorage.setItem(u,JSON.stringify(this.novelHistory))},getLocal:function(t){return this.initLocal(),this.novelHistory[t]},clone:function(t){var o={};for(var e in t){var i=t[e];"object"==typeof i&&(i=this.clone(i)),o[e]=i}return o}};t.exports=f},function(t,o,e){"use strict";var i=e(6),n=e(4),s="blog_touch_book_list",a="blog_touch_novel_history",r="blog_touch_novel_getinitbook",l={getBookList:function(){var t=i.getItem(s);return t||(t=[]),t},saveBookList:function(t){t&&i.setItem(s,t)},addBook:function(t){if(t)if("string"==typeof t){var o=this;n.giveMeNovelById(t,function(t){o.addBook(t)})}else{if(t.chapters){var e=n.clone(t);e.chapters=[],t=e}var i=this.getBookList();this.isExistBookById(t,i)?this.adjustTheFistBook(t.id):(i.unshift(t),this.saveBookList(i))}},updateBook:function(t){if(t){for(var o=this.getBookList(),e=0;e<o.length;e++){var i=o[e];if(i.id==t.id){o[e]=t;break}}this.saveBookList(o)}},isExistBookById:function(t,o){var e=!1,i=t;"object"==typeof t&&(i=t.id),o||(o=this.getBookList());for(var n=0;n<o.length;n++){var t=o[n];if(t.id==i){e=!0;break}}return e},removeBookById:function(t){for(var o=this.getBookList(),e=0;e<o.length;e++){var i=o[e];if(i.id==t){o.splice(e,1);break}}this.saveBookList(o),this.removeBookTag(t)},adjustTheFistBook:function(t){for(var o=this.getBookList(),e=0;e<o.length;e++){var i=o[e];if(i.id==t){var i=o.splice(e,1)[0];o.unshift(i);break}}this.saveBookList(o)},setHasGetInitBook:function(){i.setItem(r,1)},isHasGetInitBook:function(){return i.getItem(r)},saveBookTag:function(t,o,e){var n=this.isExistBookById(t);n||this.addBook(t);var s=[o,e];i.setItem(a+"_"+t,s)},getBookTag:function(t){var o=i.getItem(a+"_"+t);return o},removeBookTag:function(t){i.removeItem(a+"_"+t)}};t.exports=l},function(t,o){"use strict";var e=window.localStorage,i={setItem:function(t,o){if(e&&e.setItem&&o){var i=o;"object"==typeof o&&(i=JSON.stringify(o)),e.setItem(t,i)}},getItem:function(t){if(e&&e.getItem){var o=e.getItem(t);return o&&(o=JSON.parse(o)),o}return null},removeItem:function(t){e&&e.removeItem&&e.removeItem(t)}};t.exports=i},function(t,o){t.exports=function(t){var o="",e=t.novelList,i=t.state;e.push({tag:"jia"});var n=e;if(n)for(var s,a=-1,r=n.length-1;a<r;)s=n[a+=1],o+=" ",a%3==0&&(o+='<div class="bookshelf-item h-l"> '),o+=" ",o+="jia"!=s.tag?' <div class="book-frame h-c bookItem" onclick="" id="novel_'+s.id+'" novelId="'+s.id+'"> <div class="remove-btn h-c" style="'+(1==i?"":"display:none;")+'" onclick="" novelId="'+s.id+'">+</div> <div class="book-f"> <div class="update" style="display:none;"><img src="http://img1.qunarzz.com/car/1703/32/9b9ead3e29881302.png" alt=""/></div> <div class="book"> <img src="'+(s.cover?"http://www.37zw.com"+s.cover:"http://www.37zw.com/d/image/3/3911/3911s.jpg")+'" alt=""/> </div> </div> </div> ':' <div class="book-frame h-c bookAddBtn" onclick=""> <div class="book h-c jia"> <img src="http://pic.sucaibar.com/pic/201307/16/311d33c37b.png" alt=""/> </div> </div> ',o+=" ",a%3==2&&(o+="</div> ");return(e.length-1)%3!=2&&(o+="</div>"),o}}]);