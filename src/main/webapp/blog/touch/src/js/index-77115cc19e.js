!function(i){function t(n){if(e[n])return e[n].exports;var o=e[n]={exports:{},id:n,loaded:!1};return i[n].call(o.exports,o,o.exports,t),o.loaded=!0,o.exports}var e={};return t.m=i,t.c=e,t.p="",t(0)}([function(i,t,e){e(6)},,function(i,t){"use strict";var e="/blog/pl/nv/novelListAll",n="/blog/pl/nv/novelListPage",o="/blog/pl/nv/novelCateListPage",c="/blog/pl/nv/getNovelContent",v="/blog/pl/nv/novelIndexListPage",a="/blog/pl/nv/novelRandomBooks",s="/blog/pl/nv/novelById",l="/blog/pl/nv/novelByIds",d="/blog/pl/nv/recommendPage",r="/blog/pl/nv/novelSearchListPage",f="blog_touch_novel_history",u={novelHistory:null,_api:function(i,t,e){$.ajax({url:i,data:t,type:"POST",dataType:"json",timeout:3e4,success:function(i){"string"==typeof i&&(i=JSON.parse(i)),console.log(i);var t=i.status;e&&e(t.code,t.des,i.data,i)}})},giveMeNovelListAll:function(i){this._api(e,{},function(t,e,n,o){0==t&&i&&i(n)})},giveMeNovelListPage:function(i,t){this._api(n,{pno:i,psize:20},function(i,e,n,o){0==i&&t&&t(n)})},giveMeNovelCateListPage:function(i,t,e){this._api(o,{pno:i,psize:20,cate:t},function(i,t,n,o){0==i&&e&&e(n)})},giveMeNovelIndexListPage:function(i,t,e){this._api(v,{novelId:i,pno:t,psize:20},function(i,t,n,o){0==i&&e&&e(n)})},giveMeNovelListByKey:function(i,t,e){e&&this._api(r,{pno:i,psize:20,key:t},function(i,t,n,o){0==i&&e&&e(n)})},giveMeNovelByIdAndChapter:function(i,t,e){u._api(c,{novelId:i,chapter:t},function(i,t,n,o){0==i&&e&&e(n)})},giveMeRandomBooks:function(i,t){u._api(a,{num:i},function(i,e,n,o){0==i&&t&&t(n)})},giveMeNovelById:function(i,t){t&&u._api(s,{id:i},function(i,e,n,o){0==i&&t(n)})},giveMeNovelsByIds:function(i,t){t&&u._api(l,{ids:i},function(i,e,n,o){0==i&&t(n)})},giveMeNovelAllById:function(i,t){t&&u._api(s,{id:i,needAll:1},function(i,e,n,o){0==i&&t(n)})},giveMeRecommendData:function(i){i&&u._api(d,{},function(t,e,n,o){0==t&&i(n)})},getQueryString:function(i,t){var e=new RegExp("(^|&)"+i+"=([^&]*)(&|$)","i"),n=t||window.location.search.substr(1),o=n.match(e);return null!=o?decodeURIComponent(o[2]):null},initLocal:function(){this.novelHistory||(this.novelHistory=localStorage.getItem(f),this.novelHistory?this.novelHistory=JSON.parse(this.novelHistory):this.novelHistory={})},saveLocal:function(i,t,e){this.initLocal(),this.novelHistory[i]=[t,e],localStorage.setItem(f,JSON.stringify(this.novelHistory))},getLocal:function(i){return this.initLocal(),this.novelHistory[i]},clone:function(i){var t={};for(var e in i){var n=i[e];"object"==typeof n&&(n=this.clone(n)),t[e]=n}return t}};i.exports=u},,,,function(i,t,e){"use strict";var n=e(2),o=e(7),c={init:function(){this.initPage(),this.initListener()},initPage:function(){var i=this;this.initData(function(t){i.render(t)})},initData:function(i){i&&n.giveMeRecommendData(function(t){console.log(t),i(t)})},render:function(i){var t=o(i);$("#recommendFrame").html(t),$("img").error(function(){$(this).attr("src","http://www.37zw.com/d/image/3/3753/3753s.jpg")})},initListener:function(){$("body").on("click",".linkbook",function(){var i=$(this).attr("novelId");window.location.href="detail.html?novelId="+i}),$("body").on("click",".cate",function(){var i=$(this).html()+"小说";window.location.href="list.html?cate="+i})}};$(document).ready(function(){c.init()})},function(i,t){i.exports=function(i){var t="",e=i,n=e.recommend,o=e.boy,c=e.girl,v=e.update;t+='<div class="tj-frame"> <div class="tj-frame-title h-l">推荐</div> <div class="tj-frame-content v-c"> ';var a=n;if(a)for(var s,l=-1,d=a.length-1;l<d;)s=a[l+=1],t+=" ",l%4==0&&(t+=' <div class="tj-frame-l h-u"> '),t+=' <div class="tj-frame-item h-c linkbook" novelId="'+s.id+'" onclick=""> <div class="tj-item v-c" onclick=""> <div class="cover"> <img src="http://www.37zw.com'+s.cover+'" alt=""/> </div> <div class="desc"> <div class="name h-c">'+s.name+'</div> <div class="author h-c">'+s.author+"</div> </div> </div> </div> ",l%4==3&&(t+=" </div> "),t+=" ";t+=" ",l%4!=3&&(t+=" </div> "),t+=' </div></div><div class="tj-frame"> <div class="tj-frame-title h-l">男生</div> <div class="tj-frame-content v-c"> ';var r=o;if(r)for(var s,l=-1,f=r.length-1;l<f;)s=r[l+=1],t+=" ",l%4==0&&(t+=' <div class="tj-frame-l h-u"> '),t+=' <div class="tj-frame-item h-c linkbook" novelId="'+s.id+'" onclick=""> <div class="tj-item v-c" onclick=""> <div class="cover"> <img src="http://www.37zw.com'+s.cover+'" alt=""/> </div> <div class="desc"> <div class="name h-c">'+s.name+'</div> <div class="author h-c">'+s.author+"</div> </div> </div> </div> ",l%4==3&&(t+=" </div> "),t+=" ";t+=" ",l%4!=3&&(t+=" </div> "),t+=' </div></div><div class="tj-frame"> <div class="tj-frame-title h-l">女生</div> <div class="tj-frame-content v-c"> ';var u=c;if(u)for(var s,l=-1,h=u.length-1;l<h;)s=u[l+=1],t+=" ",l%4==0&&(t+=' <div class="tj-frame-l h-u"> '),t+=' <div class="tj-frame-item h-c linkbook" novelId="'+s.id+'" onclick=""> <div class="tj-item v-c" onclick=""> <div class="cover"> <img src="http://www.37zw.com'+s.cover+'" alt=""/> </div> <div class="desc"> <div class="name h-c">'+s.name+'</div> <div class="author h-c">'+s.author+"</div> </div> </div> </div> ",l%4==3&&(t+=" </div> "),t+=" ";t+=" ",l%4!=3&&(t+=" </div> "),t+=' </div></div><div class="tj-frame"> <div class="tj-frame-title h-l">最近更新</div> <div class="tj-frame-content v-c"> ';var m=v;if(m)for(var s,l=-1,g=m.length-1;l<g;)s=m[l+=1],t+=" ",l%4==0&&(t+=' <div class="tj-frame-l h-u"> '),t+=' <div class="tj-frame-item h-c linkbook" novelId="'+s.id+'" onclick=""> <div class="tj-item v-c" > <div class="cover"> <img src="http://www.37zw.com'+s.cover+'" alt=""/> </div> <div class="desc"> <div class="name h-c">'+s.name+'</div> <div class="author h-c">'+s.author+"</div> </div> </div> </div> ",l%4==3&&(t+=" </div> "),t+=" ";return t+=" ",l%4!=3&&(t+=" </div> "),t+=" </div></div>"}}]);