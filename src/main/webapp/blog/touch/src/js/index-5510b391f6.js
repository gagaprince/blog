!function(i){function t(s){if(e[s])return e[s].exports;var v=e[s]={exports:{},id:s,loaded:!1};return i[s].call(v.exports,v,v.exports,t),v.loaded=!0,v.exports}var e={};return t.m=i,t.c=e,t.p="",t(0)}([function(i,t,e){e(6)},,function(i,t){"use strict";var e="/blog/pl/nv/novelListAll",s="/blog/pl/nv/novelListPage",v="/blog/pl/nv/getNovelContent",a="/blog/pl/nv/novelIndexListPage",n="/blog/pl/nv/novelRandomBooks",o="/blog/pl/nv/novelById",c="/blog/pl/nv/recommendPage",d="blog_touch_novel_history",l={novelHistory:null,_api:function(i,t,e){$.ajax({url:i,data:t,type:"POST",dataType:"json",timeout:3e4,success:function(i){"string"==typeof i&&(i=JSON.parse(i)),console.log(i);var t=i.status;e&&e(t.code,t.des,i.data,i)}})},giveMeNovelListAll:function(i){this._api(e,{},function(t,e,s,v){0==t&&i&&i(s)})},giveMeNovelListPage:function(i,t){this._api(s,{pno:i,psize:20},function(i,e,s,v){0==i&&t&&t(s)})},giveMeNovelIndexListPage:function(i,t,e){this._api(a,{novelId:i,pno:t,psize:20},function(i,t,s,v){0==i&&e&&e(s)})},giveMeNovelByIdAndChapter:function(i,t,e){l._api(v,{novelId:i,chapter:t},function(i,t,s,v){0==i&&e&&e(s)})},giveMeRandomBooks:function(i,t){l._api(n,{num:i},function(i,e,s,v){0==i&&t&&t(s)})},giveMeNovelById:function(i,t){t&&l._api(o,{id:i},function(i,e,s,v){0==i&&t(s)})},giveMeNovelAllById:function(i,t){t&&l._api(o,{id:i,needAll:1},function(i,e,s,v){0==i&&t(s)})},giveMeRecommendData:function(i){i&&l._api(c,{},function(t,e,s,v){0==t&&i(s)})},getQueryString:function(i,t){var e=new RegExp("(^|&)"+i+"=([^&]*)(&|$)","i"),s=t||window.location.search.substr(1),v=s.match(e);return null!=v?decodeURIComponent(v[2]):null},initLocal:function(){this.novelHistory||(this.novelHistory=localStorage.getItem(d),this.novelHistory?this.novelHistory=JSON.parse(this.novelHistory):this.novelHistory={})},saveLocal:function(i,t,e){this.initLocal(),this.novelHistory[i]=[t,e],localStorage.setItem(d,JSON.stringify(this.novelHistory))},getLocal:function(i){return this.initLocal(),this.novelHistory[i]},clone:function(i){var t={};for(var e in i){var s=i[e];"object"==typeof s&&(s=this.clone(s)),t[e]=s}return t}};i.exports=l},,,,function(i,t,e){"use strict";var s=e(2),v=e(7),a={init:function(){this.initPage(),this.initListener()},initPage:function(){var i=this;this.initData(function(t){i.render(t)})},initData:function(i){i&&s.giveMeRecommendData(function(t){console.log(t),i(t)})},render:function(i){var t=v(i);$("#recommendFrame").html(t)},initListener:function(){}};$(document).ready(function(){a.init()})},function(i,t){i.exports=function(i){var t="",e=i,s=e.recommend,v=e.boy,a=e.girl;t+='<div class="tj-frame"> <div class="tj-frame-title h-l">推荐</div> <div class="tj-frame-content v-c"> ';var n=s;if(n)for(var o,c=-1,d=n.length-1;c<d;)o=n[c+=1],t+=" ",c%4==0&&(t+=' <div class="tj-frame-l h-l-u"> '),t+=' <div class="tj-frame-item h-c" novelId="'+o.id+'"> <div class="tj-item"> <div class="cover"> <img src="http://www.37zw.com'+o.cover+'" alt=""/> </div> <div class="desc"> <div class="name">'+o.name+'</div> <div class="author">'+o.author+"</div> </div> </div> </div> ",c%4==3&&(t+=" </div> "),t+=" ";t+=" ",c%4!=3&&(t+=" </div> "),t+=' </div></div><div class="tj-frame"> <div class="tj-frame-title h-l">男生</div> <div class="tj-frame-content v-c"> ';var l=v;if(l)for(var o,c=-1,r=l.length-1;c<r;)o=l[c+=1],t+=" ",c%4==0&&(t+=' <div class="tj-frame-l h-l-u"> '),t+=' <div class="tj-frame-item h-c" novelId="'+o.id+'"> <div class="tj-item"> <div class="cover"> <img src="http://www.37zw.com'+o.cover+'" alt=""/> </div> <div class="desc"> <div class="name">'+o.name+'</div> <div class="author">'+o.author+"</div> </div> </div> </div> ",c%4==3&&(t+=" </div> "),t+=" ";t+=" ",c%4!=3&&(t+=" </div> "),t+=' </div></div><div class="tj-frame"> <div class="tj-frame-title h-l">女生</div> <div class="tj-frame-content v-c"> ';var m=a;if(m)for(var o,c=-1,f=m.length-1;c<f;)o=m[c+=1],t+=" ",c%4==0&&(t+=' <div class="tj-frame-l h-l-u"> '),t+=' <div class="tj-frame-item h-c" novelId="'+o.id+'"> <div class="tj-item"> <div class="cover"> <img src="http://www.37zw.com'+o.cover+'" alt=""/> </div> <div class="desc"> <div class="name">'+o.name+'</div> <div class="author">'+o.author+"</div> </div> </div> </div> ",c%4==3&&(t+=" </div> "),t+=" ";return t+=" ",c%4!=3&&(t+=" </div> "),t+=' </div></div><div class="tj-frame"> <div class="tj-frame-title h-l">最近更新</div> <div class="tj-frame-content v-c"> <div class="tj-frame-l h-l"> <div class="tj-frame-item h-c"> <div class="tj-item"> <div class="cover"> <img src="http://www.37zw.com/d/image/0/330/330s.jpg" alt=""/> </div> <div class="desc"> <div class="name">大主宰</div> <div class="author">天蚕土豆</div> </div> </div> </div> <div class="tj-frame-item h-c"> <div class="tj-item"> <div class="cover"> <img src="http://www.37zw.com/d/image/0/330/330s.jpg" alt=""/> </div> <div class="desc"> <div class="name">大主宰</div> <div class="author">天蚕土豆</div> </div> </div> </div> <div class="tj-frame-item h-c"> <div class="tj-item"> <div class="cover"> <img src="http://www.37zw.com/d/image/0/330/330s.jpg" alt=""/> </div> <div class="desc"> <div class="name">大主宰</div> <div class="author">天蚕土豆</div> </div> </div> </div> <div class="tj-frame-item h-c"> <div class="tj-item"> <div class="cover"> <img src="http://www.37zw.com/d/image/0/330/330s.jpg" alt=""/> </div> <div class="desc"> <div class="name">大主宰</div> <div class="author">天蚕土豆</div> </div> </div> </div> </div> </div></div>'}}]);