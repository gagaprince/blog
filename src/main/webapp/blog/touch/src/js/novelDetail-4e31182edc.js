!function(t){function n(o){if(e[o])return e[o].exports;var i=e[o]={exports:{},id:o,loaded:!1};return t[o].call(i.exports,i,i.exports,n),i.loaded=!0,i.exports}var e={};n.m=t,n.c=e,n.p="",n(0)}([function(t,n,e){"use strict";e(114)},,,,function(t,n,e){"use strict";function o(t){return t&&t.__esModule?t:{default:t}}var i=e(5),r=o(i),u=e(72),c=o(u),s={novelHistory:null,_api:function(t,n,e){$.ajax({url:t,data:n,type:"POST",dataType:"json",timeout:1e5,success:function(t){"string"==typeof t&&(t=JSON.parse(t)),console.log(t);var n=t.status;e&&e(n&&n.code,n&&n.des,t.data,t)}})},giveMeNovelListAll:function(t){this._api("/blog/pl/nv/novelListAll",{},function(n,e,o,i){0==n&&t&&t(o)})},giveMeNovelListPage:function(t,n){this._api("/blog/pl/nv/novelListPage",{pno:t,psize:20},function(t,e,o,i){0==t&&n&&n(o)})},giveMeNovelCateListPage:function(t,n,e){this._api("/blog/pl/nv/novelCateListPage",{pno:t,psize:20,cate:n},function(t,n,o,i){0==t&&e&&e(o)})},giveMeNovelIndexListPage:function(t,n,e){this._api("/blog/pl/nv/novelIndexListPage",{novelId:t,pno:n,psize:20},function(t,n,o,i){0==t&&e&&e(o)})},giveMeNovelListByKey:function(t,n,e){e&&this._api("/blog/pl/nv/novelSearchListPage",{pno:t,psize:20,key:n},function(t,n,o,i){0==t&&e&&e(o)})},giveMeNovelByIdAndChapter:function(t,n,e){s._api("/blog/pl/nv/getNovelContent",{novelId:t,chapter:n},function(t,n,o,i){0==t&&e&&e(o)})},giveMeRandomBooks:function(t,n){s._api("/blog/pl/nv/novelRandomBooks",{num:t},function(t,e,o,i){0==t&&n&&n(o)})},giveMeNovelById:function(t,n){n&&s._api("/blog/pl/nv/novelById",{id:t},function(t,e,o,i){0==t&&n(o)})},giveMeNovelsByIds:function(t,n){n&&s._api("/blog/pl/nv/novelByIds",{ids:t},function(t,e,o,i){0==t&&n(o)})},giveMeNovelAllById:function(t,n){n&&s._api("/blog/pl/nv/novelById",{id:t,needAll:1},function(t,e,o,i){0==t&&n(o)})},giveMeRecommendData:function(t){t&&s._api("/blog/pl/nv/recommendPage",{},function(n,e,o,i){0==n&&t(o)})},getQueryString:function(t,n){var e=new RegExp("(^|&)"+t+"=([^&]*)(&|$)","i"),o=n||window.location.search.substr(1),i=o.match(e);return null!=i?decodeURIComponent(i[2]):null},initLocal:function(){this.novelHistory||(this.novelHistory=localStorage.getItem("blog_touch_novel_history"),this.novelHistory?this.novelHistory=JSON.parse(this.novelHistory):this.novelHistory={})},saveLocal:function(t,n,e){this.initLocal(),this.novelHistory[t]=[n,e],localStorage.setItem("blog_touch_novel_history",(0,c.default)(this.novelHistory))},getLocal:function(t){return this.initLocal(),this.novelHistory[t]},clone:function(t){var n={};for(var e in t){var o=t[e];"object"==(void 0===o?"undefined":(0,r.default)(o))&&(o=this.clone(o)),n[e]=o}return n}};t.exports=s},function(t,n,e){"use strict";function o(t){return t&&t.__esModule?t:{default:t}}n.__esModule=!0;var i=e(6),r=o(i),u=e(57),c=o(u),s="function"==typeof c.default&&"symbol"==typeof r.default?function(t){return typeof t}:function(t){return t&&"function"==typeof c.default&&t.constructor===c.default&&t!==c.default.prototype?"symbol":typeof t};n.default="function"==typeof c.default&&"symbol"===s(r.default)?function(t){return void 0===t?"undefined":s(t)}:function(t){return t&&"function"==typeof c.default&&t.constructor===c.default&&t!==c.default.prototype?"symbol":void 0===t?"undefined":s(t)}},function(t,n,e){t.exports={default:e(7),__esModule:!0}},function(t,n,e){e(8),e(52),t.exports=e(56).f("iterator")},function(t,n,e){"use strict";var o=e(9)(!0);e(12)(String,"String",function(t){this._t=String(t),this._i=0},function(){var t,n=this._t,e=this._i;return e>=n.length?{value:void 0,done:!0}:(t=o(n,e),this._i+=t.length,{value:t,done:!1})})},function(t,n,e){var o=e(10),i=e(11);t.exports=function(t){return function(n,e){var r,u,c=String(i(n)),s=o(e),a=c.length;return s<0||s>=a?t?"":void 0:(r=c.charCodeAt(s),r<55296||r>56319||s+1===a||(u=c.charCodeAt(s+1))<56320||u>57343?t?c.charAt(s):r:t?c.slice(s,s+2):u-56320+(r-55296<<10)+65536)}}},function(t,n){var e=Math.ceil,o=Math.floor;t.exports=function(t){return isNaN(t=+t)?0:(t>0?o:e)(t)}},function(t,n){t.exports=function(t){if(void 0==t)throw TypeError("Can't call method on  "+t);return t}},function(t,n,e){"use strict";var o=e(13),i=e(14),r=e(29),u=e(19),c=e(30),s=e(31),a=e(32),f=e(48),l=e(50),v=e(49)("iterator"),d=!([].keys&&"next"in[].keys()),p=function(){return this};t.exports=function(t,n,e,h,y,g,m){a(e,n,h);var b,_,x,S=function(t){if(!d&&t in I)return I[t];switch(t){case"keys":case"values":return function(){return new e(this,t)}}return function(){return new e(this,t)}},k=n+" Iterator",w="values"==y,O=!1,I=t.prototype,L=I[v]||I["@@iterator"]||y&&I[y],B=L||S(y),P=y?w?S("entries"):B:void 0,j="Array"==n?I.entries||L:L;if(j&&(x=l(j.call(new t)))!==Object.prototype&&x.next&&(f(x,k,!0),o||c(x,v)||u(x,v,p)),w&&L&&"values"!==L.name&&(O=!0,B=function(){return L.call(this)}),o&&!m||!d&&!O&&I[v]||u(I,v,B),s[n]=B,s[k]=p,y)if(b={values:w?B:S("values"),keys:g?B:S("keys"),entries:P},m)for(_ in b)_ in I||r(I,_,b[_]);else i(i.P+i.F*(d||O),n,b);return b}},function(t,n){t.exports=!0},function(t,n,e){var o=e(15),i=e(16),r=e(17),u=e(19),c=function(t,n,e){var s,a,f,l=t&c.F,v=t&c.G,d=t&c.S,p=t&c.P,h=t&c.B,y=t&c.W,g=v?i:i[n]||(i[n]={}),m=g.prototype,b=v?o:d?o[n]:(o[n]||{}).prototype;v&&(e=n);for(s in e)(a=!l&&b&&void 0!==b[s])&&s in g||(f=a?b[s]:e[s],g[s]=v&&"function"!=typeof b[s]?e[s]:h&&a?r(f,o):y&&b[s]==f?function(t){var n=function(n,e,o){if(this instanceof t){switch(arguments.length){case 0:return new t;case 1:return new t(n);case 2:return new t(n,e)}return new t(n,e,o)}return t.apply(this,arguments)};return n.prototype=t.prototype,n}(f):p&&"function"==typeof f?r(Function.call,f):f,p&&((g.virtual||(g.virtual={}))[s]=f,t&c.R&&m&&!m[s]&&u(m,s,f)))};c.F=1,c.G=2,c.S=4,c.P=8,c.B=16,c.W=32,c.U=64,c.R=128,t.exports=c},function(t,n){var e=t.exports="undefined"!=typeof window&&window.Math==Math?window:"undefined"!=typeof self&&self.Math==Math?self:Function("return this")();"number"==typeof __g&&(__g=e)},function(t,n){var e=t.exports={version:"2.5.1"};"number"==typeof __e&&(__e=e)},function(t,n,e){var o=e(18);t.exports=function(t,n,e){if(o(t),void 0===n)return t;switch(e){case 1:return function(e){return t.call(n,e)};case 2:return function(e,o){return t.call(n,e,o)};case 3:return function(e,o,i){return t.call(n,e,o,i)}}return function(){return t.apply(n,arguments)}}},function(t,n){t.exports=function(t){if("function"!=typeof t)throw TypeError(t+" is not a function!");return t}},function(t,n,e){var o=e(20),i=e(28);t.exports=e(24)?function(t,n,e){return o.f(t,n,i(1,e))}:function(t,n,e){return t[n]=e,t}},function(t,n,e){var o=e(21),i=e(23),r=e(27),u=Object.defineProperty;n.f=e(24)?Object.defineProperty:function(t,n,e){if(o(t),n=r(n,!0),o(e),i)try{return u(t,n,e)}catch(t){}if("get"in e||"set"in e)throw TypeError("Accessors not supported!");return"value"in e&&(t[n]=e.value),t}},function(t,n,e){var o=e(22);t.exports=function(t){if(!o(t))throw TypeError(t+" is not an object!");return t}},function(t,n){t.exports=function(t){return"object"==typeof t?null!==t:"function"==typeof t}},function(t,n,e){t.exports=!e(24)&&!e(25)(function(){return 7!=Object.defineProperty(e(26)("div"),"a",{get:function(){return 7}}).a})},function(t,n,e){t.exports=!e(25)(function(){return 7!=Object.defineProperty({},"a",{get:function(){return 7}}).a})},function(t,n){t.exports=function(t){try{return!!t()}catch(t){return!0}}},function(t,n,e){var o=e(22),i=e(15).document,r=o(i)&&o(i.createElement);t.exports=function(t){return r?i.createElement(t):{}}},function(t,n,e){var o=e(22);t.exports=function(t,n){if(!o(t))return t;var e,i;if(n&&"function"==typeof(e=t.toString)&&!o(i=e.call(t)))return i;if("function"==typeof(e=t.valueOf)&&!o(i=e.call(t)))return i;if(!n&&"function"==typeof(e=t.toString)&&!o(i=e.call(t)))return i;throw TypeError("Can't convert object to primitive value")}},function(t,n){t.exports=function(t,n){return{enumerable:!(1&t),configurable:!(2&t),writable:!(4&t),value:n}}},function(t,n,e){t.exports=e(19)},function(t,n){var e={}.hasOwnProperty;t.exports=function(t,n){return e.call(t,n)}},function(t,n){t.exports={}},function(t,n,e){"use strict";var o=e(33),i=e(28),r=e(48),u={};e(19)(u,e(49)("iterator"),function(){return this}),t.exports=function(t,n,e){t.prototype=o(u,{next:i(1,e)}),r(t,n+" Iterator")}},function(t,n,e){var o=e(21),i=e(34),r=e(46),u=e(43)("IE_PROTO"),c=function(){},s=function(){var t,n=e(26)("iframe"),o=r.length;for(n.style.display="none",e(47).appendChild(n),n.src="javascript:",t=n.contentWindow.document,t.open(),t.write("<script>document.F=Object<\/script>"),t.close(),s=t.F;o--;)delete s.prototype[r[o]];return s()};t.exports=Object.create||function(t,n){var e;return null!==t?(c.prototype=o(t),e=new c,c.prototype=null,e[u]=t):e=s(),void 0===n?e:i(e,n)}},function(t,n,e){var o=e(20),i=e(21),r=e(35);t.exports=e(24)?Object.defineProperties:function(t,n){i(t);for(var e,u=r(n),c=u.length,s=0;c>s;)o.f(t,e=u[s++],n[e]);return t}},function(t,n,e){var o=e(36),i=e(46);t.exports=Object.keys||function(t){return o(t,i)}},function(t,n,e){var o=e(30),i=e(37),r=e(40)(!1),u=e(43)("IE_PROTO");t.exports=function(t,n){var e,c=i(t),s=0,a=[];for(e in c)e!=u&&o(c,e)&&a.push(e);for(;n.length>s;)o(c,e=n[s++])&&(~r(a,e)||a.push(e));return a}},function(t,n,e){var o=e(38),i=e(11);t.exports=function(t){return o(i(t))}},function(t,n,e){var o=e(39);t.exports=Object("z").propertyIsEnumerable(0)?Object:function(t){return"String"==o(t)?t.split(""):Object(t)}},function(t,n){var e={}.toString;t.exports=function(t){return e.call(t).slice(8,-1)}},function(t,n,e){var o=e(37),i=e(41),r=e(42);t.exports=function(t){return function(n,e,u){var c,s=o(n),a=i(s.length),f=r(u,a);if(t&&e!=e){for(;a>f;)if((c=s[f++])!=c)return!0}else for(;a>f;f++)if((t||f in s)&&s[f]===e)return t||f||0;return!t&&-1}}},function(t,n,e){var o=e(10),i=Math.min;t.exports=function(t){return t>0?i(o(t),9007199254740991):0}},function(t,n,e){var o=e(10),i=Math.max,r=Math.min;t.exports=function(t,n){return t=o(t),t<0?i(t+n,0):r(t,n)}},function(t,n,e){var o=e(44)("keys"),i=e(45);t.exports=function(t){return o[t]||(o[t]=i(t))}},function(t,n,e){var o=e(15),i=o["__core-js_shared__"]||(o["__core-js_shared__"]={});t.exports=function(t){return i[t]||(i[t]={})}},function(t,n){var e=0,o=Math.random();t.exports=function(t){return"Symbol(".concat(void 0===t?"":t,")_",(++e+o).toString(36))}},function(t,n){t.exports="constructor,hasOwnProperty,isPrototypeOf,propertyIsEnumerable,toLocaleString,toString,valueOf".split(",")},function(t,n,e){var o=e(15).document;t.exports=o&&o.documentElement},function(t,n,e){var o=e(20).f,i=e(30),r=e(49)("toStringTag");t.exports=function(t,n,e){t&&!i(t=e?t:t.prototype,r)&&o(t,r,{configurable:!0,value:n})}},function(t,n,e){var o=e(44)("wks"),i=e(45),r=e(15).Symbol,u="function"==typeof r;(t.exports=function(t){return o[t]||(o[t]=u&&r[t]||(u?r:i)("Symbol."+t))}).store=o},function(t,n,e){var o=e(30),i=e(51),r=e(43)("IE_PROTO"),u=Object.prototype;t.exports=Object.getPrototypeOf||function(t){return t=i(t),o(t,r)?t[r]:"function"==typeof t.constructor&&t instanceof t.constructor?t.constructor.prototype:t instanceof Object?u:null}},function(t,n,e){var o=e(11);t.exports=function(t){return Object(o(t))}},function(t,n,e){e(53);for(var o=e(15),i=e(19),r=e(31),u=e(49)("toStringTag"),c="CSSRuleList,CSSStyleDeclaration,CSSValueList,ClientRectList,DOMRectList,DOMStringList,DOMTokenList,DataTransferItemList,FileList,HTMLAllCollection,HTMLCollection,HTMLFormElement,HTMLSelectElement,MediaList,MimeTypeArray,NamedNodeMap,NodeList,PaintRequestList,Plugin,PluginArray,SVGLengthList,SVGNumberList,SVGPathSegList,SVGPointList,SVGStringList,SVGTransformList,SourceBufferList,StyleSheetList,TextTrackCueList,TextTrackList,TouchList".split(","),s=0;s<c.length;s++){var a=c[s],f=o[a],l=f&&f.prototype;l&&!l[u]&&i(l,u,a),r[a]=r.Array}},function(t,n,e){"use strict";var o=e(54),i=e(55),r=e(31),u=e(37);t.exports=e(12)(Array,"Array",function(t,n){this._t=u(t),this._i=0,this._k=n},function(){var t=this._t,n=this._k,e=this._i++;return!t||e>=t.length?(this._t=void 0,i(1)):"keys"==n?i(0,e):"values"==n?i(0,t[e]):i(0,[e,t[e]])},"values"),r.Arguments=r.Array,o("keys"),o("values"),o("entries")},function(t,n){t.exports=function(){}},function(t,n){t.exports=function(t,n){return{value:n,done:!!t}}},function(t,n,e){n.f=e(49)},function(t,n,e){t.exports={default:e(58),__esModule:!0}},function(t,n,e){e(59),e(69),e(70),e(71),t.exports=e(16).Symbol},function(t,n,e){"use strict";var o=e(15),i=e(30),r=e(24),u=e(14),c=e(29),s=e(60).KEY,a=e(25),f=e(44),l=e(48),v=e(45),d=e(49),p=e(56),h=e(61),y=e(62),g=e(65),m=e(21),b=e(37),_=e(27),x=e(28),S=e(33),k=e(66),w=e(68),O=e(20),I=e(35),L=w.f,B=O.f,P=k.f,j=o.Symbol,M=o.JSON,T=M&&M.stringify,N=d("_hidden"),E=d("toPrimitive"),A={}.propertyIsEnumerable,F=f("symbol-registry"),C=f("symbols"),D=f("op-symbols"),H=Object.prototype,R="function"==typeof j,$=o.QObject,G=!$||!$.prototype||!$.prototype.findChild,J=r&&a(function(){return 7!=S(B({},"a",{get:function(){return B(this,"a",{value:7}).a}})).a})?function(t,n,e){var o=L(H,n);o&&delete H[n],B(t,n,e),o&&t!==H&&B(H,n,o)}:B,z=function(t){var n=C[t]=S(j.prototype);return n._k=t,n},V=R&&"symbol"==typeof j.iterator?function(t){return"symbol"==typeof t}:function(t){return t instanceof j},W=function(t,n,e){return t===H&&W(D,n,e),m(t),n=_(n,!0),m(e),i(C,n)?(e.enumerable?(i(t,N)&&t[N][n]&&(t[N][n]=!1),e=S(e,{enumerable:x(0,!1)})):(i(t,N)||B(t,N,x(1,{})),t[N][n]=!0),J(t,n,e)):B(t,n,e)},K=function(t,n){m(t);for(var e,o=y(n=b(n)),i=0,r=o.length;r>i;)W(t,e=o[i++],n[e]);return t},Q=function(t,n){return void 0===n?S(t):K(S(t),n)},U=function(t){var n=A.call(this,t=_(t,!0));return!(this===H&&i(C,t)&&!i(D,t))&&(!(n||!i(this,t)||!i(C,t)||i(this,N)&&this[N][t])||n)},Y=function(t,n){if(t=b(t),n=_(n,!0),t!==H||!i(C,n)||i(D,n)){var e=L(t,n);return!e||!i(C,n)||i(t,N)&&t[N][n]||(e.enumerable=!0),e}},q=function(t){for(var n,e=P(b(t)),o=[],r=0;e.length>r;)i(C,n=e[r++])||n==N||n==s||o.push(n);return o},X=function(t){for(var n,e=t===H,o=P(e?D:b(t)),r=[],u=0;o.length>u;)!i(C,n=o[u++])||e&&!i(H,n)||r.push(C[n]);return r};R||(j=function(){if(this instanceof j)throw TypeError("Symbol is not a constructor!");var t=v(arguments.length>0?arguments[0]:void 0),n=function(e){this===H&&n.call(D,e),i(this,N)&&i(this[N],t)&&(this[N][t]=!1),J(this,t,x(1,e))};return r&&G&&J(H,t,{configurable:!0,set:n}),z(t)},c(j.prototype,"toString",function(){return this._k}),w.f=Y,O.f=W,e(67).f=k.f=q,e(64).f=U,e(63).f=X,r&&!e(13)&&c(H,"propertyIsEnumerable",U,!0),p.f=function(t){return z(d(t))}),u(u.G+u.W+u.F*!R,{Symbol:j});for(var Z="hasInstance,isConcatSpreadable,iterator,match,replace,search,species,split,toPrimitive,toStringTag,unscopables".split(","),tt=0;Z.length>tt;)d(Z[tt++]);for(var nt=I(d.store),et=0;nt.length>et;)h(nt[et++]);u(u.S+u.F*!R,"Symbol",{for:function(t){return i(F,t+="")?F[t]:F[t]=j(t)},keyFor:function(t){if(!V(t))throw TypeError(t+" is not a symbol!");for(var n in F)if(F[n]===t)return n},useSetter:function(){G=!0},useSimple:function(){G=!1}}),u(u.S+u.F*!R,"Object",{create:Q,defineProperty:W,defineProperties:K,getOwnPropertyDescriptor:Y,getOwnPropertyNames:q,getOwnPropertySymbols:X}),M&&u(u.S+u.F*(!R||a(function(){var t=j();return"[null]"!=T([t])||"{}"!=T({a:t})||"{}"!=T(Object(t))})),"JSON",{stringify:function(t){if(void 0!==t&&!V(t)){for(var n,e,o=[t],i=1;arguments.length>i;)o.push(arguments[i++]);return n=o[1],"function"==typeof n&&(e=n),!e&&g(n)||(n=function(t,n){if(e&&(n=e.call(this,t,n)),!V(n))return n}),o[1]=n,T.apply(M,o)}}}),j.prototype[E]||e(19)(j.prototype,E,j.prototype.valueOf),l(j,"Symbol"),l(Math,"Math",!0),l(o.JSON,"JSON",!0)},function(t,n,e){var o=e(45)("meta"),i=e(22),r=e(30),u=e(20).f,c=0,s=Object.isExtensible||function(){return!0},a=!e(25)(function(){return s(Object.preventExtensions({}))}),f=function(t){u(t,o,{value:{i:"O"+ ++c,w:{}}})},l=function(t,n){if(!i(t))return"symbol"==typeof t?t:("string"==typeof t?"S":"P")+t;if(!r(t,o)){if(!s(t))return"F";if(!n)return"E";f(t)}return t[o].i},v=function(t,n){if(!r(t,o)){if(!s(t))return!0;if(!n)return!1;f(t)}return t[o].w},d=function(t){return a&&p.NEED&&s(t)&&!r(t,o)&&f(t),t},p=t.exports={KEY:o,NEED:!1,fastKey:l,getWeak:v,onFreeze:d}},function(t,n,e){var o=e(15),i=e(16),r=e(13),u=e(56),c=e(20).f;t.exports=function(t){var n=i.Symbol||(i.Symbol=r?{}:o.Symbol||{});"_"==t.charAt(0)||t in n||c(n,t,{value:u.f(t)})}},function(t,n,e){var o=e(35),i=e(63),r=e(64);t.exports=function(t){var n=o(t),e=i.f;if(e)for(var u,c=e(t),s=r.f,a=0;c.length>a;)s.call(t,u=c[a++])&&n.push(u);return n}},function(t,n){n.f=Object.getOwnPropertySymbols},function(t,n){n.f={}.propertyIsEnumerable},function(t,n,e){var o=e(39);t.exports=Array.isArray||function(t){return"Array"==o(t)}},function(t,n,e){var o=e(37),i=e(67).f,r={}.toString,u="object"==typeof window&&window&&Object.getOwnPropertyNames?Object.getOwnPropertyNames(window):[],c=function(t){try{return i(t)}catch(t){return u.slice()}};t.exports.f=function(t){return u&&"[object Window]"==r.call(t)?c(t):i(o(t))}},function(t,n,e){var o=e(36),i=e(46).concat("length","prototype");n.f=Object.getOwnPropertyNames||function(t){return o(t,i)}},function(t,n,e){var o=e(64),i=e(28),r=e(37),u=e(27),c=e(30),s=e(23),a=Object.getOwnPropertyDescriptor;n.f=e(24)?a:function(t,n){if(t=r(t),n=u(n,!0),s)try{return a(t,n)}catch(t){}if(c(t,n))return i(!o.f.call(t,n),t[n])}},function(t,n){},function(t,n,e){e(61)("asyncIterator")},function(t,n,e){e(61)("observable")},function(t,n,e){t.exports={default:e(73),__esModule:!0}},function(t,n,e){var o=e(16),i=o.JSON||(o.JSON={stringify:JSON.stringify});t.exports=function(t){return i.stringify.apply(i,arguments)}},function(t,n,e){"use strict";var o=e(5),i=function(t){return t&&t.__esModule?t:{default:t}}(o),r=e(75),u=e(4),c={getBookList:function(){var t=r.getItem("blog_touch_book_list");return t||(t=[]),t},saveBookList:function(t){t&&r.setItem("blog_touch_book_list",t)},addBook:function(t){if(t)if("string"==typeof t){var n=this;u.giveMeNovelById(t,function(t){n.addBook(t)})}else{if(t.chapters){var e=u.clone(t);e.chapters=[],t=e}var o=this.getBookList();this.isExistBookById(t,o)?this.adjustTheFistBook(t.id):(o.unshift(t),this.saveBookList(o))}},updateBook:function(t){if(t){for(var n=this.getBookList(),e=0;e<n.length;e++){if(n[e].id==t.id){n[e]=t;break}}this.saveBookList(n)}},isExistBookById:function(t,n){var e=!1,o=t;"object"==(void 0===t?"undefined":(0,i.default)(t))&&(o=t.id),n||(n=this.getBookList());for(var r=0;r<n.length;r++){var t=n[r];if(t.id==o){e=!0;break}}return e},removeBookById:function(t){for(var n=this.getBookList(),e=0;e<n.length;e++){if(n[e].id==t){n.splice(e,1);break}}this.saveBookList(n),this.removeBookTag(t)},adjustTheFistBook:function(t){for(var n=this.getBookList(),e=0;e<n.length;e++){var o=n[e];if(o.id==t){var o=n.splice(e,1)[0];n.unshift(o);break}}this.saveBookList(n)},setHasGetInitBook:function(){r.setItem("blog_touch_novel_getinitbook",1)},isHasGetInitBook:function(){return r.getItem("blog_touch_novel_getinitbook")},saveBookTag:function(t,n,e){this.isExistBookById(t)||this.addBook(t);var o=[n,e];r.setItem("blog_touch_novel_history_"+t,o)},getBookTag:function(t){return r.getItem("blog_touch_novel_history_"+t)},removeBookTag:function(t){r.removeItem("blog_touch_novel_history_"+t)}};t.exports=c},function(t,n,e){"use strict";function o(t){return t&&t.__esModule?t:{default:t}}var i=e(72),r=o(i),u=e(5),c=o(u),s=window.localStorage,a={setItem:function(t,n){if(s&&s.setItem&&n){var e=n;"object"==(void 0===n?"undefined":(0,c.default)(n))&&(e=(0,r.default)(n)),s.setItem(t,e)}},getItem:function(t){if(s&&s.getItem){var n=s.getItem(t);return n&&(n=JSON.parse(n)),n}return null},removeItem:function(t){s&&s.removeItem&&s.removeItem(t)}};t.exports=a},,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,function(t,n,e){"use strict";var o=e(4),i=e(115),r=e(117),u=e(74),c={novelId:"",novelData:null,init:function(){i.init(),this.initPage(),this.initListener()},initPage:function(){var t=this;i.show(),this.initData(function(n){i.hide(),t.renderPage(n)})},initData:function(t){if(t){var n=this.novelId=o.getQueryString("novelId"),e=this;o.giveMeNovelAllById(n,function(n){e.novelData=n,t(n)})}},renderPage:function(t){console.log(t);var n=r(t);$("#detailPage").html(n).show(),$("img").error(function(){$(this).attr("src","http://www.37zw.com/d/image/3/3753/3753s.jpg")})},initListener:function(){var t=this;$("body").on("click",".backFrame",function(){history.back()}),$("body").on("click","#readBtn",function(){u.addBook(t.novelData),window.location.href="read.html?novelId="+t.novelId}),$("body").on("click",".novel-index-item",function(){u.addBook(t.novelData);var n=$(this).attr("chapter");window.location.href="read.html?novelId="+t.novelId+"&chapter="+n})}};$(document).ready(function(){c.init()})},function(t,n,e){"use strict";var o=e(116),i={init:function(){var t=o({});$("body").append(t)},show:function(){$(".loading-frame").show()},hide:function(){$(".loading-frame").hide()}};t.exports=i},function(t,n){t.exports=function(t){return'<div class="loading-frame h-c" style="display:none;"> <div class="search-load h-c"> <div class="my-dot"></div> <div class="my-dot"></div> <div class="my-dot"></div> </div></div>'}},function(t,n){t.exports=function(t){var n="",e=t,o=e.chapters;n+='<div class="header h-c"> <div class="backFrame"> <div class="back"> <div class="bg-frame"></div> <div class="front-frame"></div> </div> </div> <div class="title">'+e.name+'</div></div><div class="container"> <div class="info h-u"> <div class="left v-u"> <div class="imgframe"> <img src="http://www.37zw.com'+e.cover+'" alt=""/> </div> </div> <div class="right"> <div class="name">'+e.name+'</div> <div class="info-item h-l-u"> <div class="lb">作者：</div> <div class="lbv">'+e.author+'</div> </div> <div class="info-item h-l-u"> <div class="lb">分类：</div> <div class="lbv">'+e.cate+'</div> </div> <div class="info-item h-l-u"> <div class="lb">更新：</div> <div class="lbv">'+e.updateTime.split(" ")[0]+'</div> </div> <div class="info-item h-l-u"> <div class="lb">最新：</div> <div class="lbv">'+o[0].name+'</div> </div> </div> </div> <div class="read-btn-frame h-c"> <div class="read-btn h-c" id="readBtn" onclick="">开始阅读</div> </div> <div class="desc-frame"> <div class="desc-t h-c"> <div class="desc-title h-l">小说简介</div> </div> <div class="desc-info h-c"> <div class="desc"> '+e.descripe+' </div> </div> </div> <div class="novel-index-frame"> <div class="novel-index-t h-c"> <div class="novel-index-title h-l"> 目录 </div> </div> <div class="novel-index-list"> ';var i=o;if(i)for(var r,u=-1,c=i.length-1;u<c;)r=i[u+=1],n+=' <div class="novel-index-item h-c" onclick="" chapter="'+r.chapter+'"> <div class="novel-index-item-info h-l">'+r.name+"</div> </div> ";return n+=" </div> </div></div>"}}]);