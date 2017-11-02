!function(t){function n(o){if(e[o])return e[o].exports;var r=e[o]={exports:{},id:o,loaded:!1};return t[o].call(r.exports,r,r.exports,n),r.loaded=!0,r.exports}var e={};n.m=t,n.c=e,n.p="",n(0)}([function(t,n,e){"use strict";e(3)},,,function(t,n,e){"use strict";var o=e(4),r=e(74),i=e(76),u={bookShelfState:0,novelList:null,updateMap:null,init:function(){this.initPage(),this.initListener()},initPage:function(){var t=this;this.initBookList(function(n){t.novelList=n;var e={state:t.bookShelfState,novelList:t.cloneNovelList(n)};t.renderPage(e),t.checkUpdate()})},checkUpdate:function(){for(var t=this,n=this.novelList,e=n.length,r="",i=0;i<e;i++){r+=","+n[i].id}r&&(r=r.substring(1)),r&&o.giveMeNovelsByIds(r,function(n){console.log(n),t.updateMap=t._makeUpdateMap(n),t.renderUpdate()})},_makeUpdateMap:function(t){for(var n={},e=t.length,o=0;o<e;o++){var r=t[o];n[r.id]=r}return n},renderUpdate:function(){var t=this.novelList,n=this.updateMap;if(t&&n&&t.length>0)for(var e=t.length,o=0;o<e;o++){var r=t[o],i=r.id,u=n[i];r.updateTime!=u.updateTime&&this.renderUpdateNovel(r)}},renderUpdateNovel:function(t){var n=t.id;$("#novel_"+n).find(".update").show()},updateBookShelfWidthNew:function(t){var n=this.updateMap,e=n[t];r.updateBook(e),$("#novel_"+t).find(".update").hide()},initListener:function(){var t=this;$("body").on("click",".bookItem",function(){if(0==t.bookShelfState){var n=$(this).attr("novelId");window.location.href="detail.html?novelId="+n,t.updateBookShelfWidthNew(n)}}),$("body").on("click",".bookAddBtn",function(){0==t.bookShelfState&&(window.location.href="index.html")}),$("body").on("click","#bjBtn",function(){0==t.bookShelfState?($(".remove-btn").show(),t.bookShelfState=1,$(this).html("完成")):($(".remove-btn").hide(),t.bookShelfState=0,$(this).html("编辑"))}),$("body").on("click",".remove-btn",function(){var n=$(this).attr("novelId");r.removeBookById(n),t.initPage()})},initBookList:function(t){if(t){var n=r.getBookList();n&&n.length?t(n):r.isHasGetInitBook()?t([]):o.giveMeRandomBooks(6,function(n){t(n),r.saveBookList(n)})}},renderPage:function(t){var n=i(t);$("#bookshelfFrame").html(n)},_filterNovelList:function(t){if(t)for(var n=0;n<t.length;n++){t[n]}},cloneNovelList:function(t){for(var n=[],e=0;e<t.length;e++){var r=t[e],i=o.clone(r);n.push(i)}return n}};$(document).ready(function(){u.init()})},function(t,n,e){"use strict";function o(t){return t&&t.__esModule?t:{default:t}}var r=e(5),i=o(r),u=e(72),c=o(u),s={novelHistory:null,_api:function(t,n,e){$.ajax({url:t,data:n,type:"POST",dataType:"json",timeout:1e5,success:function(t){"string"==typeof t&&(t=JSON.parse(t)),console.log(t);var n=t.status;e&&e(n&&n.code,n&&n.des,t.data,t)}})},giveMeNovelListAll:function(t){this._api("/blog/pl/nv/novelListAll",{},function(n,e,o,r){0==n&&t&&t(o)})},giveMeNovelListPage:function(t,n){this._api("/blog/pl/nv/novelListPage",{pno:t,psize:20},function(t,e,o,r){0==t&&n&&n(o)})},giveMeNovelCateListPage:function(t,n,e){this._api("/blog/pl/nv/novelCateListPage",{pno:t,psize:20,cate:n},function(t,n,o,r){0==t&&e&&e(o)})},giveMeNovelIndexListPage:function(t,n,e){this._api("/blog/pl/nv/novelIndexListPage",{novelId:t,pno:n,psize:20},function(t,n,o,r){0==t&&e&&e(o)})},giveMeNovelListByKey:function(t,n,e){e&&this._api("/blog/pl/nv/novelSearchListPage",{pno:t,psize:20,key:n},function(t,n,o,r){0==t&&e&&e(o)})},giveMeNovelByIdAndChapter:function(t,n,e){s._api("/blog/pl/nv/getNovelContent",{novelId:t,chapter:n},function(t,n,o,r){0==t&&e&&e(o)})},giveMeRandomBooks:function(t,n){s._api("/blog/pl/nv/novelRandomBooks",{num:t},function(t,e,o,r){0==t&&n&&n(o)})},giveMeNovelById:function(t,n){n&&s._api("/blog/pl/nv/novelById",{id:t},function(t,e,o,r){0==t&&n(o)})},giveMeNovelsByIds:function(t,n){n&&s._api("/blog/pl/nv/novelByIds",{ids:t},function(t,e,o,r){0==t&&n(o)})},giveMeNovelAllById:function(t,n){n&&s._api("/blog/pl/nv/novelById",{id:t,needAll:1},function(t,e,o,r){0==t&&n(o)})},giveMeRecommendData:function(t){t&&s._api("/blog/pl/nv/recommendPage",{},function(n,e,o,r){0==n&&t(o)})},getQueryString:function(t,n){var e=new RegExp("(^|&)"+t+"=([^&]*)(&|$)","i"),o=n||window.location.search.substr(1),r=o.match(e);return null!=r?decodeURIComponent(r[2]):null},initLocal:function(){this.novelHistory||(this.novelHistory=localStorage.getItem("blog_touch_novel_history"),this.novelHistory?this.novelHistory=JSON.parse(this.novelHistory):this.novelHistory={})},saveLocal:function(t,n,e){this.initLocal(),this.novelHistory[t]=[n,e],localStorage.setItem("blog_touch_novel_history",(0,c.default)(this.novelHistory))},getLocal:function(t){return this.initLocal(),this.novelHistory[t]},clone:function(t){var n={};for(var e in t){var o=t[e];"object"==(void 0===o?"undefined":(0,i.default)(o))&&(o=this.clone(o)),n[e]=o}return n}};t.exports=s},function(t,n,e){"use strict";function o(t){return t&&t.__esModule?t:{default:t}}n.__esModule=!0;var r=e(6),i=o(r),u=e(57),c=o(u),s="function"==typeof c.default&&"symbol"==typeof i.default?function(t){return typeof t}:function(t){return t&&"function"==typeof c.default&&t.constructor===c.default&&t!==c.default.prototype?"symbol":typeof t};n.default="function"==typeof c.default&&"symbol"===s(i.default)?function(t){return void 0===t?"undefined":s(t)}:function(t){return t&&"function"==typeof c.default&&t.constructor===c.default&&t!==c.default.prototype?"symbol":void 0===t?"undefined":s(t)}},function(t,n,e){t.exports={default:e(7),__esModule:!0}},function(t,n,e){e(8),e(52),t.exports=e(56).f("iterator")},function(t,n,e){"use strict";var o=e(9)(!0);e(12)(String,"String",function(t){this._t=String(t),this._i=0},function(){var t,n=this._t,e=this._i;return e>=n.length?{value:void 0,done:!0}:(t=o(n,e),this._i+=t.length,{value:t,done:!1})})},function(t,n,e){var o=e(10),r=e(11);t.exports=function(t){return function(n,e){var i,u,c=String(r(n)),s=o(e),f=c.length;return s<0||s>=f?t?"":void 0:(i=c.charCodeAt(s),i<55296||i>56319||s+1===f||(u=c.charCodeAt(s+1))<56320||u>57343?t?c.charAt(s):i:t?c.slice(s,s+2):u-56320+(i-55296<<10)+65536)}}},function(t,n){var e=Math.ceil,o=Math.floor;t.exports=function(t){return isNaN(t=+t)?0:(t>0?o:e)(t)}},function(t,n){t.exports=function(t){if(void 0==t)throw TypeError("Can't call method on  "+t);return t}},function(t,n,e){"use strict";var o=e(13),r=e(14),i=e(29),u=e(19),c=e(30),s=e(31),f=e(32),a=e(48),l=e(50),p=e(49)("iterator"),v=!([].keys&&"next"in[].keys()),d=function(){return this};t.exports=function(t,n,e,h,y,g,b){f(e,n,h);var m,_,k,S=function(t){if(!v&&t in O)return O[t];switch(t){case"keys":case"values":return function(){return new e(this,t)}}return function(){return new e(this,t)}},x=n+" Iterator",L="values"==y,w=!1,O=t.prototype,B=O[p]||O["@@iterator"]||y&&O[y],I=B||S(y),M=y?L?S("entries"):I:void 0,j="Array"==n?O.entries||B:B;if(j&&(k=l(j.call(new t)))!==Object.prototype&&k.next&&(a(k,x,!0),o||c(k,p)||u(k,p,d)),L&&B&&"values"!==B.name&&(w=!0,I=function(){return B.call(this)}),o&&!b||!v&&!w&&O[p]||u(O,p,I),s[n]=I,s[x]=d,y)if(m={values:L?I:S("values"),keys:g?I:S("keys"),entries:M},b)for(_ in m)_ in O||i(O,_,m[_]);else r(r.P+r.F*(v||w),n,m);return m}},function(t,n){t.exports=!0},function(t,n,e){var o=e(15),r=e(16),i=e(17),u=e(19),c=function(t,n,e){var s,f,a,l=t&c.F,p=t&c.G,v=t&c.S,d=t&c.P,h=t&c.B,y=t&c.W,g=p?r:r[n]||(r[n]={}),b=g.prototype,m=p?o:v?o[n]:(o[n]||{}).prototype;p&&(e=n);for(s in e)(f=!l&&m&&void 0!==m[s])&&s in g||(a=f?m[s]:e[s],g[s]=p&&"function"!=typeof m[s]?e[s]:h&&f?i(a,o):y&&m[s]==a?function(t){var n=function(n,e,o){if(this instanceof t){switch(arguments.length){case 0:return new t;case 1:return new t(n);case 2:return new t(n,e)}return new t(n,e,o)}return t.apply(this,arguments)};return n.prototype=t.prototype,n}(a):d&&"function"==typeof a?i(Function.call,a):a,d&&((g.virtual||(g.virtual={}))[s]=a,t&c.R&&b&&!b[s]&&u(b,s,a)))};c.F=1,c.G=2,c.S=4,c.P=8,c.B=16,c.W=32,c.U=64,c.R=128,t.exports=c},function(t,n){var e=t.exports="undefined"!=typeof window&&window.Math==Math?window:"undefined"!=typeof self&&self.Math==Math?self:Function("return this")();"number"==typeof __g&&(__g=e)},function(t,n){var e=t.exports={version:"2.5.1"};"number"==typeof __e&&(__e=e)},function(t,n,e){var o=e(18);t.exports=function(t,n,e){if(o(t),void 0===n)return t;switch(e){case 1:return function(e){return t.call(n,e)};case 2:return function(e,o){return t.call(n,e,o)};case 3:return function(e,o,r){return t.call(n,e,o,r)}}return function(){return t.apply(n,arguments)}}},function(t,n){t.exports=function(t){if("function"!=typeof t)throw TypeError(t+" is not a function!");return t}},function(t,n,e){var o=e(20),r=e(28);t.exports=e(24)?function(t,n,e){return o.f(t,n,r(1,e))}:function(t,n,e){return t[n]=e,t}},function(t,n,e){var o=e(21),r=e(23),i=e(27),u=Object.defineProperty;n.f=e(24)?Object.defineProperty:function(t,n,e){if(o(t),n=i(n,!0),o(e),r)try{return u(t,n,e)}catch(t){}if("get"in e||"set"in e)throw TypeError("Accessors not supported!");return"value"in e&&(t[n]=e.value),t}},function(t,n,e){var o=e(22);t.exports=function(t){if(!o(t))throw TypeError(t+" is not an object!");return t}},function(t,n){t.exports=function(t){return"object"==typeof t?null!==t:"function"==typeof t}},function(t,n,e){t.exports=!e(24)&&!e(25)(function(){return 7!=Object.defineProperty(e(26)("div"),"a",{get:function(){return 7}}).a})},function(t,n,e){t.exports=!e(25)(function(){return 7!=Object.defineProperty({},"a",{get:function(){return 7}}).a})},function(t,n){t.exports=function(t){try{return!!t()}catch(t){return!0}}},function(t,n,e){var o=e(22),r=e(15).document,i=o(r)&&o(r.createElement);t.exports=function(t){return i?r.createElement(t):{}}},function(t,n,e){var o=e(22);t.exports=function(t,n){if(!o(t))return t;var e,r;if(n&&"function"==typeof(e=t.toString)&&!o(r=e.call(t)))return r;if("function"==typeof(e=t.valueOf)&&!o(r=e.call(t)))return r;if(!n&&"function"==typeof(e=t.toString)&&!o(r=e.call(t)))return r;throw TypeError("Can't convert object to primitive value")}},function(t,n){t.exports=function(t,n){return{enumerable:!(1&t),configurable:!(2&t),writable:!(4&t),value:n}}},function(t,n,e){t.exports=e(19)},function(t,n){var e={}.hasOwnProperty;t.exports=function(t,n){return e.call(t,n)}},function(t,n){t.exports={}},function(t,n,e){"use strict";var o=e(33),r=e(28),i=e(48),u={};e(19)(u,e(49)("iterator"),function(){return this}),t.exports=function(t,n,e){t.prototype=o(u,{next:r(1,e)}),i(t,n+" Iterator")}},function(t,n,e){var o=e(21),r=e(34),i=e(46),u=e(43)("IE_PROTO"),c=function(){},s=function(){var t,n=e(26)("iframe"),o=i.length;for(n.style.display="none",e(47).appendChild(n),n.src="javascript:",t=n.contentWindow.document,t.open(),t.write("<script>document.F=Object<\/script>"),t.close(),s=t.F;o--;)delete s.prototype[i[o]];return s()};t.exports=Object.create||function(t,n){var e;return null!==t?(c.prototype=o(t),e=new c,c.prototype=null,e[u]=t):e=s(),void 0===n?e:r(e,n)}},function(t,n,e){var o=e(20),r=e(21),i=e(35);t.exports=e(24)?Object.defineProperties:function(t,n){r(t);for(var e,u=i(n),c=u.length,s=0;c>s;)o.f(t,e=u[s++],n[e]);return t}},function(t,n,e){var o=e(36),r=e(46);t.exports=Object.keys||function(t){return o(t,r)}},function(t,n,e){var o=e(30),r=e(37),i=e(40)(!1),u=e(43)("IE_PROTO");t.exports=function(t,n){var e,c=r(t),s=0,f=[];for(e in c)e!=u&&o(c,e)&&f.push(e);for(;n.length>s;)o(c,e=n[s++])&&(~i(f,e)||f.push(e));return f}},function(t,n,e){var o=e(38),r=e(11);t.exports=function(t){return o(r(t))}},function(t,n,e){var o=e(39);t.exports=Object("z").propertyIsEnumerable(0)?Object:function(t){return"String"==o(t)?t.split(""):Object(t)}},function(t,n){var e={}.toString;t.exports=function(t){return e.call(t).slice(8,-1)}},function(t,n,e){var o=e(37),r=e(41),i=e(42);t.exports=function(t){return function(n,e,u){var c,s=o(n),f=r(s.length),a=i(u,f);if(t&&e!=e){for(;f>a;)if((c=s[a++])!=c)return!0}else for(;f>a;a++)if((t||a in s)&&s[a]===e)return t||a||0;return!t&&-1}}},function(t,n,e){var o=e(10),r=Math.min;t.exports=function(t){return t>0?r(o(t),9007199254740991):0}},function(t,n,e){var o=e(10),r=Math.max,i=Math.min;t.exports=function(t,n){return t=o(t),t<0?r(t+n,0):i(t,n)}},function(t,n,e){var o=e(44)("keys"),r=e(45);t.exports=function(t){return o[t]||(o[t]=r(t))}},function(t,n,e){var o=e(15),r=o["__core-js_shared__"]||(o["__core-js_shared__"]={});t.exports=function(t){return r[t]||(r[t]={})}},function(t,n){var e=0,o=Math.random();t.exports=function(t){return"Symbol(".concat(void 0===t?"":t,")_",(++e+o).toString(36))}},function(t,n){t.exports="constructor,hasOwnProperty,isPrototypeOf,propertyIsEnumerable,toLocaleString,toString,valueOf".split(",")},function(t,n,e){var o=e(15).document;t.exports=o&&o.documentElement},function(t,n,e){var o=e(20).f,r=e(30),i=e(49)("toStringTag");t.exports=function(t,n,e){t&&!r(t=e?t:t.prototype,i)&&o(t,i,{configurable:!0,value:n})}},function(t,n,e){var o=e(44)("wks"),r=e(45),i=e(15).Symbol,u="function"==typeof i;(t.exports=function(t){return o[t]||(o[t]=u&&i[t]||(u?i:r)("Symbol."+t))}).store=o},function(t,n,e){var o=e(30),r=e(51),i=e(43)("IE_PROTO"),u=Object.prototype;t.exports=Object.getPrototypeOf||function(t){return t=r(t),o(t,i)?t[i]:"function"==typeof t.constructor&&t instanceof t.constructor?t.constructor.prototype:t instanceof Object?u:null}},function(t,n,e){var o=e(11);t.exports=function(t){return Object(o(t))}},function(t,n,e){e(53);for(var o=e(15),r=e(19),i=e(31),u=e(49)("toStringTag"),c="CSSRuleList,CSSStyleDeclaration,CSSValueList,ClientRectList,DOMRectList,DOMStringList,DOMTokenList,DataTransferItemList,FileList,HTMLAllCollection,HTMLCollection,HTMLFormElement,HTMLSelectElement,MediaList,MimeTypeArray,NamedNodeMap,NodeList,PaintRequestList,Plugin,PluginArray,SVGLengthList,SVGNumberList,SVGPathSegList,SVGPointList,SVGStringList,SVGTransformList,SourceBufferList,StyleSheetList,TextTrackCueList,TextTrackList,TouchList".split(","),s=0;s<c.length;s++){var f=c[s],a=o[f],l=a&&a.prototype;l&&!l[u]&&r(l,u,f),i[f]=i.Array}},function(t,n,e){"use strict";var o=e(54),r=e(55),i=e(31),u=e(37);t.exports=e(12)(Array,"Array",function(t,n){this._t=u(t),this._i=0,this._k=n},function(){var t=this._t,n=this._k,e=this._i++;return!t||e>=t.length?(this._t=void 0,r(1)):"keys"==n?r(0,e):"values"==n?r(0,t[e]):r(0,[e,t[e]])},"values"),i.Arguments=i.Array,o("keys"),o("values"),o("entries")},function(t,n){t.exports=function(){}},function(t,n){t.exports=function(t,n){return{value:n,done:!!t}}},function(t,n,e){n.f=e(49)},function(t,n,e){t.exports={default:e(58),__esModule:!0}},function(t,n,e){e(59),e(69),e(70),e(71),t.exports=e(16).Symbol},function(t,n,e){"use strict";var o=e(15),r=e(30),i=e(24),u=e(14),c=e(29),s=e(60).KEY,f=e(25),a=e(44),l=e(48),p=e(45),v=e(49),d=e(56),h=e(61),y=e(62),g=e(65),b=e(21),m=e(37),_=e(27),k=e(28),S=e(33),x=e(66),L=e(68),w=e(20),O=e(35),B=L.f,I=w.f,M=x.f,j=o.Symbol,P=o.JSON,N=P&&P.stringify,T=v("_hidden"),E=v("toPrimitive"),A={}.propertyIsEnumerable,C=a("symbol-registry"),F=a("symbols"),H=a("op-symbols"),$=Object.prototype,R="function"==typeof j,G=o.QObject,z=!G||!G.prototype||!G.prototype.findChild,D=i&&f(function(){return 7!=S(I({},"a",{get:function(){return I(this,"a",{value:7}).a}})).a})?function(t,n,e){var o=B($,n);o&&delete $[n],I(t,n,e),o&&t!==$&&I($,n,o)}:I,J=function(t){var n=F[t]=S(j.prototype);return n._k=t,n},U=R&&"symbol"==typeof j.iterator?function(t){return"symbol"==typeof t}:function(t){return t instanceof j},W=function(t,n,e){return t===$&&W(H,n,e),b(t),n=_(n,!0),b(e),r(F,n)?(e.enumerable?(r(t,T)&&t[T][n]&&(t[T][n]=!1),e=S(e,{enumerable:k(0,!1)})):(r(t,T)||I(t,T,k(1,{})),t[T][n]=!0),D(t,n,e)):I(t,n,e)},V=function(t,n){b(t);for(var e,o=y(n=m(n)),r=0,i=o.length;i>r;)W(t,e=o[r++],n[e]);return t},K=function(t,n){return void 0===n?S(t):V(S(t),n)},q=function(t){var n=A.call(this,t=_(t,!0));return!(this===$&&r(F,t)&&!r(H,t))&&(!(n||!r(this,t)||!r(F,t)||r(this,T)&&this[T][t])||n)},Q=function(t,n){if(t=m(t),n=_(n,!0),t!==$||!r(F,n)||r(H,n)){var e=B(t,n);return!e||!r(F,n)||r(t,T)&&t[T][n]||(e.enumerable=!0),e}},Y=function(t){for(var n,e=M(m(t)),o=[],i=0;e.length>i;)r(F,n=e[i++])||n==T||n==s||o.push(n);return o},X=function(t){for(var n,e=t===$,o=M(e?H:m(t)),i=[],u=0;o.length>u;)!r(F,n=o[u++])||e&&!r($,n)||i.push(F[n]);return i};R||(j=function(){if(this instanceof j)throw TypeError("Symbol is not a constructor!");var t=p(arguments.length>0?arguments[0]:void 0),n=function(e){this===$&&n.call(H,e),r(this,T)&&r(this[T],t)&&(this[T][t]=!1),D(this,t,k(1,e))};return i&&z&&D($,t,{configurable:!0,set:n}),J(t)},c(j.prototype,"toString",function(){return this._k}),L.f=Q,w.f=W,e(67).f=x.f=Y,e(64).f=q,e(63).f=X,i&&!e(13)&&c($,"propertyIsEnumerable",q,!0),d.f=function(t){return J(v(t))}),u(u.G+u.W+u.F*!R,{Symbol:j});for(var Z="hasInstance,isConcatSpreadable,iterator,match,replace,search,species,split,toPrimitive,toStringTag,unscopables".split(","),tt=0;Z.length>tt;)v(Z[tt++]);for(var nt=O(v.store),et=0;nt.length>et;)h(nt[et++]);u(u.S+u.F*!R,"Symbol",{for:function(t){return r(C,t+="")?C[t]:C[t]=j(t)},keyFor:function(t){if(!U(t))throw TypeError(t+" is not a symbol!");for(var n in C)if(C[n]===t)return n},useSetter:function(){z=!0},useSimple:function(){z=!1}}),u(u.S+u.F*!R,"Object",{create:K,defineProperty:W,defineProperties:V,getOwnPropertyDescriptor:Q,getOwnPropertyNames:Y,getOwnPropertySymbols:X}),P&&u(u.S+u.F*(!R||f(function(){var t=j();return"[null]"!=N([t])||"{}"!=N({a:t})||"{}"!=N(Object(t))})),"JSON",{stringify:function(t){if(void 0!==t&&!U(t)){for(var n,e,o=[t],r=1;arguments.length>r;)o.push(arguments[r++]);return n=o[1],"function"==typeof n&&(e=n),!e&&g(n)||(n=function(t,n){if(e&&(n=e.call(this,t,n)),!U(n))return n}),o[1]=n,N.apply(P,o)}}}),j.prototype[E]||e(19)(j.prototype,E,j.prototype.valueOf),l(j,"Symbol"),l(Math,"Math",!0),l(o.JSON,"JSON",!0)},function(t,n,e){var o=e(45)("meta"),r=e(22),i=e(30),u=e(20).f,c=0,s=Object.isExtensible||function(){return!0},f=!e(25)(function(){return s(Object.preventExtensions({}))}),a=function(t){u(t,o,{value:{i:"O"+ ++c,w:{}}})},l=function(t,n){if(!r(t))return"symbol"==typeof t?t:("string"==typeof t?"S":"P")+t;if(!i(t,o)){if(!s(t))return"F";if(!n)return"E";a(t)}return t[o].i},p=function(t,n){if(!i(t,o)){if(!s(t))return!0;if(!n)return!1;a(t)}return t[o].w},v=function(t){return f&&d.NEED&&s(t)&&!i(t,o)&&a(t),t},d=t.exports={KEY:o,NEED:!1,fastKey:l,getWeak:p,onFreeze:v}},function(t,n,e){var o=e(15),r=e(16),i=e(13),u=e(56),c=e(20).f;t.exports=function(t){var n=r.Symbol||(r.Symbol=i?{}:o.Symbol||{});"_"==t.charAt(0)||t in n||c(n,t,{value:u.f(t)})}},function(t,n,e){var o=e(35),r=e(63),i=e(64);t.exports=function(t){var n=o(t),e=r.f;if(e)for(var u,c=e(t),s=i.f,f=0;c.length>f;)s.call(t,u=c[f++])&&n.push(u);return n}},function(t,n){n.f=Object.getOwnPropertySymbols},function(t,n){n.f={}.propertyIsEnumerable},function(t,n,e){var o=e(39);t.exports=Array.isArray||function(t){return"Array"==o(t)}},function(t,n,e){var o=e(37),r=e(67).f,i={}.toString,u="object"==typeof window&&window&&Object.getOwnPropertyNames?Object.getOwnPropertyNames(window):[],c=function(t){try{return r(t)}catch(t){return u.slice()}};t.exports.f=function(t){return u&&"[object Window]"==i.call(t)?c(t):r(o(t))}},function(t,n,e){var o=e(36),r=e(46).concat("length","prototype");n.f=Object.getOwnPropertyNames||function(t){return o(t,r)}},function(t,n,e){var o=e(64),r=e(28),i=e(37),u=e(27),c=e(30),s=e(23),f=Object.getOwnPropertyDescriptor;n.f=e(24)?f:function(t,n){if(t=i(t),n=u(n,!0),s)try{return f(t,n)}catch(t){}if(c(t,n))return r(!o.f.call(t,n),t[n])}},function(t,n){},function(t,n,e){e(61)("asyncIterator")},function(t,n,e){e(61)("observable")},function(t,n,e){t.exports={default:e(73),__esModule:!0}},function(t,n,e){var o=e(16),r=o.JSON||(o.JSON={stringify:JSON.stringify});t.exports=function(t){return r.stringify.apply(r,arguments)}},function(t,n,e){"use strict";var o=e(5),r=function(t){return t&&t.__esModule?t:{default:t}}(o),i=e(75),u=e(4),c={getBookList:function(){var t=i.getItem("blog_touch_book_list");return t||(t=[]),t},saveBookList:function(t){t&&i.setItem("blog_touch_book_list",t)},addBook:function(t){if(t)if("string"==typeof t){var n=this;u.giveMeNovelById(t,function(t){n.addBook(t)})}else{if(t.chapters){var e=u.clone(t);e.chapters=[],t=e}var o=this.getBookList();this.isExistBookById(t,o)?this.adjustTheFistBook(t.id):(o.unshift(t),this.saveBookList(o))}},updateBook:function(t){if(t){for(var n=this.getBookList(),e=0;e<n.length;e++){if(n[e].id==t.id){n[e]=t;break}}this.saveBookList(n)}},isExistBookById:function(t,n){var e=!1,o=t;"object"==(void 0===t?"undefined":(0,r.default)(t))&&(o=t.id),n||(n=this.getBookList());for(var i=0;i<n.length;i++){var t=n[i];if(t.id==o){e=!0;break}}return e},removeBookById:function(t){for(var n=this.getBookList(),e=0;e<n.length;e++){if(n[e].id==t){n.splice(e,1);break}}this.saveBookList(n),this.removeBookTag(t)},adjustTheFistBook:function(t){for(var n=this.getBookList(),e=0;e<n.length;e++){var o=n[e];if(o.id==t){var o=n.splice(e,1)[0];n.unshift(o);break}}this.saveBookList(n)},setHasGetInitBook:function(){i.setItem("blog_touch_novel_getinitbook",1)},isHasGetInitBook:function(){return i.getItem("blog_touch_novel_getinitbook")},saveBookTag:function(t,n,e){this.isExistBookById(t)||this.addBook(t);var o=[n,e];i.setItem("blog_touch_novel_history_"+t,o)},getBookTag:function(t){return i.getItem("blog_touch_novel_history_"+t)},removeBookTag:function(t){i.removeItem("blog_touch_novel_history_"+t)}};t.exports=c},function(t,n,e){"use strict";function o(t){return t&&t.__esModule?t:{default:t}}var r=e(72),i=o(r),u=e(5),c=o(u),s=window.localStorage,f={setItem:function(t,n){if(s&&s.setItem&&n){var e=n;"object"==(void 0===n?"undefined":(0,c.default)(n))&&(e=(0,i.default)(n)),s.setItem(t,e)}},getItem:function(t){if(s&&s.getItem){var n=s.getItem(t);return n&&(n=JSON.parse(n)),n}return null},removeItem:function(t){s&&s.removeItem&&s.removeItem(t)}};t.exports=f},function(t,n){t.exports=function(t){var n="",e=t.novelList,o=t.state;e.push({tag:"jia"});var r=e;if(r)for(var i,u=-1,c=r.length-1;u<c;)i=r[u+=1],n+=" ",u%3==0&&(n+='<div class="bookshelf-item h-l"> '),n+=" ","jia"!=i.tag?n+=' <div class="book-frame h-c bookItem" onclick="" id="novel_'+i.id+'" novelId="'+i.id+'"> <div class="remove-btn h-c" style="'+(1==o?"":"display:none;")+'" onclick="" novelId="'+i.id+'">+</div> <div class="book-f"> <div class="update" style="display:none;"><img src="http://img1.qunarzz.com/car/1703/32/9b9ead3e29881302.png" alt=""/></div> <div class="book"> <img src="'+(i.cover?"http://www.37zw.com"+i.cover:"http://www.37zw.com/d/image/3/3911/3911s.jpg")+'" alt=""/> </div> </div> </div> ':n+=' <div class="book-frame h-c bookAddBtn" onclick=""> <div class="book h-c jia"> <img src="http://pic.sucaibar.com/pic/201307/16/311d33c37b.png" alt=""/> </div> </div> ',n+=" ",u%3==2&&(n+="</div> ");return(e.length-1)%3!=2&&(n+="</div>"),n}}]);