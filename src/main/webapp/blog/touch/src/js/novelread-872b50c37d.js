!function(t){function e(i){if(n[i])return n[i].exports;var r=n[i]={exports:{},id:i,loaded:!1};return t[i].call(r.exports,r,r.exports,e),r.loaded=!0,r.exports}var n={};return e.m=t,e.c=n,e.p="",e(0)}({0:function(t,e,n){n(21)},2:function(t,e){"use strict";var n="/blog/pl/nv/novelListAll",i="/blog/pl/nv/novelListPage",r="/blog/pl/nv/novelCateListPage",o="/blog/pl/nv/getNovelContent",a="/blog/pl/nv/novelIndexListPage",s="/blog/pl/nv/novelRandomBooks",h="/blog/pl/nv/novelById",u="/blog/pl/nv/novelByIds",c="/blog/pl/nv/recommendPage",l="/blog/pl/nv/novelSearchListPage",g="blog_touch_novel_history",f={novelHistory:null,_api:function(t,e,n){$.ajax({url:t,data:e,type:"POST",dataType:"json",timeout:3e4,success:function(t){"string"==typeof t&&(t=JSON.parse(t)),console.log(t);var e=t.status;n&&n(e.code,e.des,t.data,t)}})},giveMeNovelListAll:function(t){this._api(n,{},function(e,n,i,r){0==e&&t&&t(i)})},giveMeNovelListPage:function(t,e){this._api(i,{pno:t,psize:20},function(t,n,i,r){0==t&&e&&e(i)})},giveMeNovelCateListPage:function(t,e,n){this._api(r,{pno:t,psize:20,cate:e},function(t,e,i,r){0==t&&n&&n(i)})},giveMeNovelIndexListPage:function(t,e,n){this._api(a,{novelId:t,pno:e,psize:20},function(t,e,i,r){0==t&&n&&n(i)})},giveMeNovelListByKey:function(t,e,n){n&&this._api(l,{pno:t,psize:20,key:e},function(t,e,i,r){0==t&&n&&n(i)})},giveMeNovelByIdAndChapter:function(t,e,n){f._api(o,{novelId:t,chapter:e},function(t,e,i,r){0==t&&n&&n(i)})},giveMeRandomBooks:function(t,e){f._api(s,{num:t},function(t,n,i,r){0==t&&e&&e(i)})},giveMeNovelById:function(t,e){e&&f._api(h,{id:t},function(t,n,i,r){0==t&&e(i)})},giveMeNovelsByIds:function(t,e){e&&f._api(u,{ids:t},function(t,n,i,r){0==t&&e(i)})},giveMeNovelAllById:function(t,e){e&&f._api(h,{id:t,needAll:1},function(t,n,i,r){0==t&&e(i)})},giveMeRecommendData:function(t){t&&f._api(c,{},function(e,n,i,r){0==e&&t(i)})},getQueryString:function(t,e){var n=new RegExp("(^|&)"+t+"=([^&]*)(&|$)","i"),i=e||window.location.search.substr(1),r=i.match(n);return null!=r?decodeURIComponent(r[2]):null},initLocal:function(){this.novelHistory||(this.novelHistory=localStorage.getItem(g),this.novelHistory?this.novelHistory=JSON.parse(this.novelHistory):this.novelHistory={})},saveLocal:function(t,e,n){this.initLocal(),this.novelHistory[t]=[e,n],localStorage.setItem(g,JSON.stringify(this.novelHistory))},getLocal:function(t){return this.initLocal(),this.novelHistory[t]},clone:function(t){var e={};for(var n in t){var i=t[n];"object"==typeof i&&(i=this.clone(i)),e[n]=i}return e}};t.exports=f},3:function(t,e,n){"use strict";var i=n(4),r=n(2),o="blog_touch_book_list",a="blog_touch_novel_history",s="blog_touch_novel_getinitbook",h={getBookList:function(){var t=i.getItem(o);return t||(t=[]),t},saveBookList:function(t){t&&i.setItem(o,t)},addBook:function(t){if(t)if("string"==typeof t){var e=this;r.giveMeNovelById(t,function(t){e.addBook(t)})}else{if(t.chapters){var n=r.clone(t);n.chapters=[],t=n}var i=this.getBookList();this.isExistBookById(t,i)?this.adjustTheFistBook(t.id):(i.unshift(t),this.saveBookList(i))}},updateBook:function(t){if(t){for(var e=this.getBookList(),n=0;n<e.length;n++){var i=e[n];if(i.id==t.id){e[n]=t;break}}this.saveBookList(e)}},isExistBookById:function(t,e){var n=!1,i=t;"object"==typeof t&&(i=t.id),e||(e=this.getBookList());for(var r=0;r<e.length;r++){var t=e[r];if(t.id==i){n=!0;break}}return n},removeBookById:function(t){for(var e=this.getBookList(),n=0;n<e.length;n++){var i=e[n];if(i.id==t){e.splice(n,1);break}}this.saveBookList(e),this.removeBookTag(t)},adjustTheFistBook:function(t){for(var e=this.getBookList(),n=0;n<e.length;n++){var i=e[n];if(i.id==t){var i=e.splice(n,1)[0];e.unshift(i);break}}this.saveBookList(e)},setHasGetInitBook:function(){i.setItem(s,1)},isHasGetInitBook:function(){return i.getItem(s)},saveBookTag:function(t,e,n){var r=this.isExistBookById(t);r||this.addBook(t);var o=[e,n];i.setItem(a+"_"+t,o)},getBookTag:function(t){var e=i.getItem(a+"_"+t);return e},removeBookTag:function(t){i.removeItem(a+"_"+t)}};t.exports=h},4:function(t,e){"use strict";var n=window.localStorage,i={setItem:function(t,e){if(n&&n.setItem&&e){var i=e;"object"==typeof e&&(i=JSON.stringify(e)),n.setItem(t,i)}},getItem:function(t){if(n&&n.getItem){var e=n.getItem(t);return e&&(e=JSON.parse(e)),e}return null},removeItem:function(t){n&&n.removeItem&&n.removeItem(t)}};t.exports=i},21:function(t,e,n){"use strict";n(22);var i=n(2),r=n(3),o={novelId:140,init:function(){this.novelId=i.getQueryString("novelId")||this.novelId;var t=i.getQueryString("chapter"),e=r.getBookTag(this.novelId)||[0,0],n=0;null==t?(t=e[0],n=e[1]):t==e[0]&&(n=e[1]),t=parseInt(t),this.initReader(t,n),r.saveBookTag(this.novelId,t,n)},initReader:function(t,e){var n=this,o=$("body").height(),a=$("body").width(),s=window.devicePixelRatio;NovelCanvas.init({width:a,height:o,bgUrl:"http://img1.qunarzz.com/car/1702/98/bcea154fb2e12802.jpg",fontSize:16*s,lineHeight:28*s,scale:s,fontColor:"#123456",turnType:1,currentArtIndex:t,currentPage:e,rect:{top:20,bottom:35,left:20,right:15},pullData:function(t,e){t>=0&&n.getContentByPage(t,function(t){e(t)})},onPageTurn:function(t,e){console.log("当前章节："+t+"  当前页码："+e);var n=i.getQueryString("novelId");r.saveBookTag(n,t,e)}})},getContentByPage:function(t,e){i.giveMeNovelByIdAndChapter(this.novelId,t,function(t){e&&e(t)})}};window.onload=function(){o.init()}},22:function(t,e){!function(t){function e(i){if(n[i])return n[i].exports;var r=n[i]={exports:{},id:i,loaded:!1};return t[i].call(r.exports,r,r.exports,e),r.loaded=!0,r.exports}var n={};return e.m=t,e.c=n,e.p="",e(0)}([function(t,e,n){n(16),n(5)},,,,,function(t,e,n){var i;!function(r,o){var a=o();i=function(){return a}.call(e,n,e,t),!(void 0!==i&&(t.exports=i)),window.NovelCanvas=a}("undefined"!=typeof window?window:this,function(){"use strict";var t=n(6),e=n(13),i=n(15),r=null,o={canvasFrame:null,canvas:null,options:{width:375,height:600,bgUrl:null,scale:1,lineHeight:20,currentArtIndex:0,currentPage:0,rect:{}},init:function(t){$.extend(this.options,t),this.initDom(),this.initCanvas(),this.initArtProvider()},initDom:function(){this.canvasFrame=$("#novelCanvasFrame"),this.canvas=this.canvasFrame.find("canvas")},initCanvas:function(){this.canvas.css({width:this.options.width+"px",height:this.options.height+"px"}),this.canvas.attr("width",this.options.width*this.options.scale),this.canvas.attr("height",this.options.height*this.options.scale),r=2==this.options.turnType?new e(this.canvas,this.options):new t(this.canvas,this.options)},initArtProvider:function(){var t=this.options.pullData,e=this.options.onPageTurn;i=new i({needArtByIndex:t,onPageTurn:e,initIndex:this.options.currentArtIndex,initPage:this.options.currentPage,initReady:function(){console.log("artProvider ready"),setTimeout(function(){r.drawArt(i)})}})},next:function(){r.drawNextPage()},pre:function(){r.drawPrePage()}};return{init:function(t){o.init(t)},next:function(){o.next()},pre:function(){o.pre()}}})},function(t,e,n){"use strict";var i,r,o=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||window.oRequestAnimationFrame||window.msRequestAnimationFrame||function(t){window.setTimeout(t,10)}}(),a=n(7),s=a.extend({turnLock:!1,init:function(t,e){this._super(t,e),this.initListener()},drawNextPage:function(t){if(!this.turnLock){var e=this.currentTextArt,n=e.getCurrentPage(),i=e.getNextPage();n==i?this.drawPage(i):null!=i&&this.startNormalTurnPage(n,i,!0,t)}},drawPrePage:function(t){if(!this.turnLock){var e=this.currentTextArt,n=e.getCurrentPage(),i=e.getPrePage();n==i?this.drawPage(i):null!=i&&this.startNormalTurnPage(i,n,!1,t)}},startNormalTurnPage:function(t,e,n,r){if(!this.turnLock)if(this.turnLock=!0,n){var o=r||0;i=(new Date).getTime(),this.turnNextPage(t,e,o)}else{var o=r||-this.width;this.turnPrePage(t,e,o)}},turnNextPage:function(t,e,n){if(this.drawPage(e),n<-this.width){var a=this.currentTextArt;a.movePage(1),this.turnLock=!1,r=(new Date).getTime(),console.log("耗时："+(r-i))}else{this.drawPage(t,n);var s=this;o(function(){s.turnNextPage(t,e,n-80)})}},turnPrePage:function(t,e,n){if(this.drawPage(e),n>0){this.drawPage(t);var i=this.currentTextArt;i.movePage(-1),this.turnLock=!1}else{this.drawPage(t,n);var r=this;o(function(){r.turnPrePage(t,e,n+80)})}},dragNextPage:function(t){var e=this.currentTextArt,n=e.getCurrentPage(),i=e.getNextPage();null!=i&&(this.drawPage(i),this.drawPage(n,t))},dragPrePage:function(t){var e=this.currentTextArt,n=e.getCurrentPage(),i=e.getPrePage();null!=i&&(this.drawPage(n),this.drawPage(i,t))},initListener:function(){var t=this.canvas,e=this,n={},i={},r={},o=!0,a=!0;t.addEventListener("touchstart",function(t){var i=t.touches[0];n={x:i.pageX*e.scaleX,y:i.pageY*e.scaleY},t.stopPropagation(),t.preventDefault()},!1),t.addEventListener("touchmove",function(t){var r=t.touches[0];i={x:r.pageX*e.scaleX,y:r.pageY*e.scaleY};var s=i.x-n.x;(s>10||s<-10)&&(o&&(a=!(s>10)),o=!1),o||(a?e.dragNextPage(i.x-e.width):e.dragPrePage(i.x-e.width)),t.stopPropagation(),t.preventDefault()},!1),t.addEventListener("touchend",function(t){var s=t.changedTouches[0];r={x:s.pageX*e.scaleX,y:s.pageY*e.scaleY};var h=s.pageX*e.scaleX;if(o)h>.66*e.width?e.drawNextPage():h<e.width/3&&e.drawPrePage();else{var u=e.currentTextArt,c=h-e.width;a?h>e.width/2?(u.movePage(1),e.drawPrePage(c)):e.drawNextPage(c):h>e.width/2?e.drawPrePage(c):(u.movePage(-1),e.drawNextPage(c))}t.stopPropagation(),t.preventDefault(),o=!0,n={},i={},r={}},!1)}});t.exports=s},function(t,e,n){"use strict";var i=n(8),r=n(10),o=r.extend({ctx:null,canvas:null,fontSize:18,lineHeight:20,fontFamilay:"arial",domWidth:0,domHeight:0,width:0,height:0,scaleX:1,scaleY:1,scale:1,currentTextArt:null,bufferCanvas:null,bctx:null,bg:null,bgBox:null,rect:{},ctor:function(t,e){this.init(t,e)},init:function(t,e){this.canvas=t[0],this.ctx=this.canvas.getContext("2d"),this.width=this.canvas.width,this.height=this.canvas.height,this.domWidth=t.width(),this.domHeight=t.height(),this.scaleX=this.width/this.domWidth,this.scaleY=this.height/this.domHeight,this.initBufferCanvas(),this.initSetting(e),i.init(this)},initSetting:function(t){this.setFont(t.fontSize,t.family,t.fontColor);var e=t.bgUrl;this.scale=t.scale||1,this.lineHeight=t.lineHeight||20,this.rect=t.rect,this.initBg(e)},initBg:function(t){if(t){var e=$('<img src="'+t+'"/>'),n=this;e.on("load",function(){var t=n.bg=e[0],i=t.width,r=t.height,o=n.width,a=n.height,s=i/o*a,h=0,u=0;s<r?(u=s,h=i):(u=r,h=r/a*o),n.bgBox={x:0,y:0,width:h,height:u},n.drawCurrentPage()})}},initBufferCanvas:function(){var t=$("<canvas></canvas>");this.bufferCanvas=t[0],t.attr("width",this.width),t.attr("height",this.height);var e=this.bctx=this.bufferCanvas.getContext("2d");e.moveTo(0,0),e.lineTo(0,this.height),e.lineTo(this.width,this.height),e.lineTo(this.width,0),e.lineTo(0,0),e.clip()},setFont:function(t,e,n){var i=this.bctx;this.fontSize=t||18,this.fontFamilay=e||this.fontFamilay||"arial",i.fillStyle=n||i.fillStyle||"#000",i.font=this.fontSize+"px "+this.fontFamilay},drawArt:function(t){this.currentTextArt=t,this.drawCurrentPage()},drawCurrentPage:function(){var t=this.currentTextArt;if(t){var e=t.getCurrentPage();null!=e&&this.drawPage(e)}},drawNextPage:function(){var t=this.currentTextArt,e=t.getNextPage();return null!=e&&this.drawPage(e),t.getPage()},drawPrePage:function(){var t=this.currentTextArt,e=t.getPrePage();null!=e&&this.drawPage(e)},drawPage:function(t,e){e||(e=0);var n=this.bctx;n.save(),n.translate(e,0),this.clearDisk(),this.drawTitle(t),this.drawTextLines(t),this.drawCurrentPageNum(t),n.restore(),this.repaint()},drawTitle:function(t){var e=t.getTitle();this.drawText(e,20,30,16)},drawTextLines:function(t){var e=this.bctx;e.save();for(var n=t.getTextLines(),i=0;i<n.length;i++){var r=n[i];this.drawTextLine(r)}e.restore()},drawCurrentPageNum:function(t){var e="第"+(t.getPage()+1)+"页";this.drawText(e,this.width-70,this.height-30,18)},drawTextLine:function(t){var e=t.text,n=t.x,i=t.y;this.drawText(e,n,i)},clearDisk:function(){var t=this.bctx;if(t.save(),t.fillStyle="#ffffff",t.fillRect(0,0,this.width,this.height),this.bg){t.globalAlpha=.7;var e=this.bgBox.x,n=this.bgBox.y,i=this.bgBox.width,r=this.bgBox.height;t.drawImage(this.bg,e,n,i,r,0,0,this.width,this.height)}t.restore()},drawText:function(t,e,n,i){var r=this.bctx;if(i){var r=this.bctx;r.save(),r.font=i+"px "+this.fontFamilay,r.fillText(t,e,n),r.restore()}else r.fillText(t,e,n)},repaint:function(){var t=this.ctx,e=this.bufferCanvas;t.drawImage(e,0,0)}});t.exports=o},function(t,e,n){"use strict";var i=n(9),r=n(11),o=n(12),a={width:0,height:0,lineHeight:0,fontSize:0,ctx:null,rect:{top:0,left:0,right:0,bottom:0},init:function(t){this.width=t.width,this.height=t.height,this.fontSize=t.fontSize||1,this.lineHeight=t.lineHeight,this.rect=t.rect||this.rect,this.ctx=t.bctx},splitArt:function(t,e){var n=t,r="";"string"!=typeof t&&(n=t.content,r=t.chapterTitle),n=this.replace(n);for(var o=new i(n,e,r),a=n;a.length>0;)a=this.splitPage(o,a);return o},replace:function(t){return t.replace(/\n/g,"").replace(/<br\/>/g,"\n").replace(/<br>/g,"\n").replace(/&nbsp;/g," ")},splitPage:function(t,e){for(var n=this.rect,i=this.height-n.top-n.bottom,o=this.lineHeight,a=Math.floor(i/o),s=new r,h=0;h<a&&(e=this.splitLine(s,e),!(e<=0));h++);return t.addTextPage(s),e},splitLine:function(t,e){var n=this.rect,i=this.width-n.left-n.right,r=this.fontSize,a=this.ctx,s=Math.floor(i/r),h="",u=e.indexOf("\n");if(u>=0&&u<s)h=e.substring(0,u+1),e=e.substring(u+1,e.length);else for(;;){if(!(s+1<e.length)){h=e,e="";break}h=e.substring(0,s+1);var c=a.measureText(h).width,l=h.substring(s,s+1);if("\n"==l){h=e.substring(0,s+1),e=e.substring(s+1,e.length);break}if(c>i){h=e.substring(0,s),e=e.substring(s,e.length);break}s++}var n=this.rect,g=new o(h,this.lineHeight,n.left,n.top);return t.addTextLine(g),e}};t.exports=a},function(t,e,n){"use strict";var i=n(10),r=i.extend({textPages:null,allText:"",currentPage:0,artIndex:0,title:"",ctor:function(t,e,n){this.allText=t,this.textPages=[],this.artIndex=e,this.title=n},getArtIndex:function(){return this.artIndex},size:function(){return this.textPages.length},getPageList:function(){return this.textPages},addTextPage:function(t){t.setPage(this.textPages.length),t.setTitle(this.title),this.textPages.push(t)},setCurrentPage:function(t){this.currentPage=t},getCurrentPage:function(){return this.getTextPage(this.currentPage)},getNextPage:function(){var t=this.currentPage+1;return t>=this.textPages.length?null:this.getTextPage(t)},getPrePage:function(){var t=this.currentPage-1;return t<0?null:this.getTextPage(t)},movePage:function(t){this.currentPage=this.currentPage+t,this.currentPage<0&&(this.currentPage=0),this.currentPage>=this.textPages.length&&(this.currentPage=this.textPages.length-1)},getTextPage:function(t){return t<0&&(t=0),t>=this.textPages.length&&(t=this.textPages.length-1),this.textPages[t]}});t.exports=r},function(t,e){var n=function(){},i=/\b_super\b/;n.extend=function(t){function e(){this.ctor&&this.ctor.apply(this,arguments)}var r=this.prototype,o=Object.create(r),a={writable:!0,enumerable:!1,configurable:!0};e.prototype=o;for(var s=0,h=arguments.length;s<h;++s){var u=arguments[s];for(var c in u){var l="function"==typeof u[c],g="function"==typeof r[c],f=i.test(u[c]);l&&g&&f?(a.value=function(t,e){return function(){var n=this._super;this._super=r[t];var i=e.apply(this,arguments);return this._super=n,i}}(c,u[c]),Object.defineProperty(o,c,a)):l?(a.value=u[c],Object.defineProperty(o,c,a)):o[c]=u[c]}}return e.extend=n.extend,e},t.exports=n},function(t,e,n){"use strict";var i=n(10),r=i.extend({textLines:null,pageIndex:0,title:"",ctor:function(){this.textLines=[]},addTextLine:function(t){t.setLine(this.textLines.length),this.textLines.push(t)},setPage:function(t){this.pageIndex=t},getPage:function(){return this.pageIndex},setTitle:function(t){this.title=t},getTitle:function(){return this.title},getTextLines:function(){return this.textLines},getTextLine:function(t){return t<0&&(t=0),t>=this.textLines.length&&(t=this.textLines.length-1),this.textLines[t]}});t.exports=r},function(t,e,n){"use strict";var i=n(10),r=i.extend({x:0,y:0,lineHeight:0,text:"",top:0,ctor:function(t,e,n,i){this.text=t,this.lineHeight=e,this.x=n,this.top=i},setLine:function(t){this.y=(t+1)*this.lineHeight+this.top}});t.exports=r},function(t,e,n){"use strict";var i=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||window.oRequestAnimationFrame||window.msRequestAnimationFrame||function(t){window.setTimeout(t,1e3/60)}}(),r=n(7),o=n(14),a=r.extend({turnLock:!1,init:function(t,e){this._super(t,e),this.initListener()},drawPrePageByPoint:function(t,e,n){var i=t.y;i>this.height/2?this.drawPrePageBottomPoint(t,e,n):this.drawPrePageUpPoint(t,e,n)},drawPrePageUpPoint:function(t){},drawPrePageBottomPoint:function(t,e,n){var i=o.p(this.width,0),r=o.p(0,this.height),a=o.p(this.width,this.height),s=o.giveMeDisPP(r,t);s>this.width-80&&(t=o.giveMePByPPK(r,t,(this.width-80)/s));var h=o.giveMePByPPK(a,t,.5),u=o.giveMePByPPK(t,h,.5),c=o.createLineFunByPP(t,a),l=c.B/c.A,g=o.createLineFunByPK(h,l),f=o.createLineFunByPK(u,l),d=o.createLineFunByPP(a,i),v=o.giveMeLLP(d,g),x=o.createLineFunByPP(r,a),p=o.giveMeLLP(x,g),P=o.giveMeLLP(f,d),b=o.createLineFunByPP(t,v),w=o.giveMeLLP(f,b),y=o.giveMeLLP(f,x),m=o.createLineFunByPP(t,p),T=o.giveMeLLP(f,m),A=o.giveMeQuadraticPByT(P,v,w,.5),k=o.giveMeQuadraticPByT(y,p,T,.5);this.clearDisk(),this.drawUpPage(t,y,p,T,k,P,w,v,A,e),this.drawDownPage(t,y,p,T,k,P,w,v,A,n),this.drawbgPage(t,y,p,T,k,P,w,v,A)},drawUpPage:function(t,e,n,i,r,o,a,s,h,u){var c=this.bctx;c.save(),c.beginPath(),c.moveTo(0,0),c.lineTo(this.width,0),c.lineTo(o.x,o.y),c.quadraticCurveTo(s.x,s.y,a.x,a.y),c.lineTo(t.x,t.y),c.lineTo(i.x,i.y),c.quadraticCurveTo(n.x,n.y,e.x,e.y),c.lineTo(0,this.height),c.closePath(),c.clip(),this.drawPage(u),c.restore(),this.repaint()},drawDownPage:function(t,e,n,i,r,o,a,s,h,u){var c=this.bctx;c.save(),c.beginPath(),c.moveTo(o.x,o.y),c.quadraticCurveTo(s.x,s.y,a.x,a.y),c.moveTo(h.x,h.y),c.lineTo(r.x,r.y),c.moveTo(i.x,i.y),c.quadraticCurveTo(n.x,n.y,e.x,e.y),c.lineTo(this.width,this.height),c.lineTo(o.x,o.y),c.moveTo(o.x,o.y),c.closePath(),c.clip(),c.beginPath(),c.moveTo(r.x,r.y),c.lineTo(e.x,e.y),c.lineTo(this.width,this.height),c.lineTo(o.x,o.y),c.lineTo(h.x,h.y),c.closePath(),c.clip(),this.currentTextArt,this.drawPage(u),c.beginPath(),c.moveTo(o.x,o.y),c.lineTo(h.x,h.y),c.lineTo(r.x,r.y),c.lineTo(e.x,e.y),c.lineTo(t.x,t.y),c.closePath(),c.fillStyle="rgba(0, 0, 0,0.8)",c.shadowOffsetX=15,c.shadowOffsetY=15,c.shadowBlur=14,c.shadowColor="rgba(0, 0, 0, 0.5)",c.fill(),c.restore(),this.repaint()},drawbgPage:function(t,e,n,i,r,a,s,h,u){var c=this.bctx;c.save(),c.beginPath(),c.moveTo(a.x,a.y),c.quadraticCurveTo(h.x,h.y,s.x,s.y),c.lineTo(t.x,t.y),c.lineTo(i.x,i.y),c.quadraticCurveTo(n.x,n.y,e.x,e.y),c.lineTo(this.width,this.height),c.lineTo(a.x,a.y),c.moveTo(a.x,a.y),c.closePath(),c.clip(),c.beginPath(),c.moveTo(r.x,r.y),c.lineTo(u.x,u.y),c.lineTo(0,0),c.lineTo(0,this.height),c.closePath(),c.clip();var l=o.giveMePByPPK(o.p(this.width,this.height),t,.5),g=c.createLinearGradient(t.x,t.y,l.x,l.y);g.addColorStop(0,"#999999"),g.addColorStop(.4,"#aaaaaa"),g.addColorStop(.6,"#dddddd"),g.addColorStop(1,"#666666"),c.fillStyle=g,c.globalAlpha=.7,c.fill(),c.restore(),this.repaint()},drawCurrentPage:function(t){var e=this.currentTextArt;if(e){var n=e.getCurrentPage();null!=n&&(t||this.clearDisk(),this.drawPage(n))}},drawPage:function(t){for(var e=(this.bctx,t.getTextLines()),n=0;n<e.length;n++){var i=e[n];this.drawTextLine(i)}this.repaint()},dragNextPage:function(t){var e=this.currentTextArt,n=e.getCurrentPage(),i=e.getNextPage();null!=i&&this.drawPrePageBottomPoint(t,n,i)},dragPrePage:function(t){var e=this.currentTextArt,n=e.getCurrentPage(),i=e.getPrePage();null!=i&&this.drawPrePageBottomPoint(t,i,n)},drawNextPage:function(t){if(!this.turnLock){var e=this.currentTextArt,n=e.getCurrentPage(),i=e.getNextPage();n==i?this.drawPage(i):null!=i&&this.startEmulateTurnPage(n,i,!0,t)}},drawPrePage:function(t){if(!this.turnLock){var e=this.currentTextArt,n=e.getCurrentPage(),i=e.getPrePage();n==i?this.drawPage(i):null!=i&&this.startEmulateTurnPage(i,n,!1,t)}},startEmulateTurnPage:function(t,e,n,i){if(!this.turnLock)if(this.turnLock=!0,n){var r=o.createTweenFun(i,o.p(-this.width,this.height),120);this.turnPage(t,e,n,r)}else{var r=o.createTweenFun(i,o.p(this.width,this.height),120);this.turnPage(t,e,n,r)}},turnPage:function(t,e,n,r){var o=r();if(null==o){var a=this.currentTextArt;n?a.movePage(1):a.movePage(-1),this.turnLock=!1,this.drawCurrentPage()}else{this.drawPrePageBottomPoint(o,t,e);var s=this;i(function(){s.turnPage(t,e,n,r)})}},initListener:function(){var t=this.canvas,e=this,n={},i={},r={},o=!0,a=!0;t.addEventListener("touchstart",function(t){var i=t.touches[0];n={x:i.pageX*e.scaleX,y:i.pageY*e.scaleY},t.stopPropagation(),t.preventDefault()},!1),t.addEventListener("touchmove",function(t){var r=t.touches[0];i={x:r.pageX*e.scaleX,y:r.pageY*e.scaleY};var s=i.x-n.x;(s>10||s<-10)&&(o&&(a=!(s>10)),o=!1),o||(a?e.dragNextPage(i):e.dragPrePage(i)),t.stopPropagation(),t.preventDefault()},!1),t.addEventListener("touchend",function(t){var s=t.changedTouches[0];r={x:s.pageX*e.scaleX,y:s.pageY*e.scaleY};var h=s.pageX*e.scaleX;if(o)h>.66*e.width?e.drawNextPage(r):h<e.width/3&&e.drawPrePage(r);else{var u=e.currentTextArt;h-e.width,a?h>e.width/2?(u.movePage(1),e.drawPrePage(r)):e.drawNextPage(r):h>e.width/2?e.drawPrePage(r):(u.movePage(-1),e.drawNextPage(r))}t.stopPropagation(),t.preventDefault(),o=!0,n={},i={},r={}},!1)}});t.exports=a},function(t,e){"use strict";var n={createLineFunByPP:function(t,e){var n=t.x,i=t.y,r=e.x,o=e.y;return{A:o-i,B:n-r,C:r*i-n*o}},createLineFunByPK:function(t,e){var n=t.x,i=t.y,r=e;return{A:r,B:-1,C:i-r*n}},giveMeLLP:function(t,e){var n=t.A,i=t.B,r=t.C,o=e.A,a=e.B,s=e.C;return n*a==o*i?null:this.p((i*s-a*r)/(n*a-o*i),(o*r-n*s)/(n*a-o*i))},giveMeQuadraticPByT:function(t,e,n,i){var r=(1-i)*(1-i),o=2*i*(1-i),a=i*i;return this.p(t.x*r+e.x*o+n.x*a,t.y*r+e.y*o+n.y*a)},giveMeDisPP:function(t,e){var n=e.x-t.x,i=e.y-t.y;return Math.sqrt(n*n+i*i)},giveMePByPPK:function(t,e,n){var i=n/(1-n),r=t.x,o=e.x,a=t.y,s=e.y;return this.p((r+i*o)/(1+i),(a+i*s)/(1+i))},giveMeMidPByPPP:function(t,e,n){return this.p((t.x+e.x+n.x)/3,(t.y+e.y+n.y)/3)},createTweenFun:function(t,e,n){var i=0,r=n/this.giveMeDisPP(t,e),o=this;return function(){var a=o.giveMePByPPK(t,e,r*i++);return o.giveMeDisPP(e,a)<n?null:a}},p:function(t,e){return{x:t,y:e}}};t.exports=n},function(t,e,n){"use strict";var i=n(8),r=n(9),o=r.extend({needArtByIndex:null,initIndex:0,initPage:0,preArt:null,currentArt:null,nextArt:null,initReady:null,onPageTurn:null,ctor:function(t){this.needArtByIndex=t.needArtByIndex,this.initIndex=t.initIndex,this.initPage=t.initPage,this.initReady=t.initReady,this.onPageTurn=t.onPageTurn,this.init()},init:function(){if(this.textPages=[],!this.needArtByIndex)throw"needArtByIndx function is undefined ！";var t=this.initIndex,e=this;this.needArtByIndex(this.initIndex,function(n){var r=i.splitArt(n,t);e.currentArt=r,e.setCurrentPage(e.initPage),e.pushAllPageIn(r),e.getNextArt(r),e.getPreArt(r),e.initReady&&e.initReady()})},getNextArt:function(t){var e=this,n=t.getArtIndex();console.log("请求第"+(n+1)+"页数据"),this.nextArt=null,this.needArtByIndex(n+1,function(t){if(null==e.nextArt){var r=i.splitArt(t,n+1);e.nextArt=r,e.pushAllPageIn(r)}})},getPreArt:function(t){var e=this,n=t.getArtIndex();console.log("请求第"+(n-1)+"页数据"),this.preArt=null,this.needArtByIndex(n-1,function(t){if(null==e.preArt){var r=i.splitArt(t,n-1);e.preArt=r,e.pushAllPageIn(r,!0)}})},pushAllPageIn:function(t,e){var n=this.textPages,i=t.getPageList();if(e){for(var r=i.length-1;r>=0;r--)n.unshift(i[r]);this.currentPage+=i.length}else for(var r=0;r<i.length;r++)n.push(i[r])},removeFirstArtPage:function(){var t=this.preArt;if(null!=t){for(var e=0;e<t.size();e++)this.textPages.shift();this.currentPage=this.currentPage-t.size()}},removeLastArtPage:function(){var t=this.nextArt;if(null!=t)for(var e=0;e<t.size();e++)this.textPages.pop()},movePage:function(t){this.currentPage,this._super(t);var e=this.currentPage;if(null!=this.preArt)var n=this.preArt.size();else var n=0;var i=this.currentArt.size(),r=i+n;if(e<n?this.preparedPreArt():e>=r&&this.preparedNextArt(),this.onPageTurn){var o=this.currentArt.getArtIndex(),a=this.getCurrentPage().getPage();this.onPageTurn(o,a)}},preparedPreArt:function(){var t=this.preArt;null==t||0==t.size()||(this.removeLastArtPage(),this.nextArt=this.currentArt,this.currentArt=this.preArt),this.getPreArt(this.currentArt)},preparedNextArt:function(){var t=this.nextArt;null==t||0==t.size()||(this.removeFirstArtPage(),this.preArt=this.currentArt,this.currentArt=this.nextArt),this.getNextArt(this.currentArt)}});t.exports=o},function(t,e,n){var i=n(17);"string"==typeof i&&(i=[[t.id,i,""]]),n(19)(i,{}),i.locals&&(t.exports=i.locals)},function(t,e,n){e=t.exports=n(18)(),e.push([t.id,".fz-16 {\n  font-size: 16px;\n}\n.fz-14 {\n  font-size: 14px;\n}\n.fz-12 {\n  font-size: 12px;\n}\n.fz-10 {\n  font-size: 10px;\n}\n.fz-8 {\n  font-size: 8px;\n}\n.fz-18 {\n  font-size: 18px;\n}\n.fz-20 {\n  font-size: 20px;\n}\n.fz-22 {\n  font-size: 22px;\n}\n.fz-24 {\n  font-size: 24px;\n}\n.fz-28 {\n  font-size: 28px;\n}\n@font-face {\n  font-family: 'myfont';\n  src: url(\"http://localhost:9990/src/font/SingleMalta.ttf\") format('truetype');\n}\n.myfont {\n  font-family: 'myfont' !important;\n}\n.text-el {\n  text-overflow: ellipsis;\n  overflow: hidden;\n  white-space: nowrap;\n}\n.h-c {\n  display: -webkit-box;\n  display: -moz-box;\n  display: box;\n  box-orient: horizontal;\n  -webkit-box-orient: horizontal;\n  -moz-box-orient: horizontal;\n  -o-box-orient: horizontal;\n  box-pack: center;\n  -webkit-box-pack: center;\n  -moz-box-pack: center;\n  -o-box-pack: center;\n  box-align: center;\n  -webkit-box-align: center;\n  -moz-box-align: center;\n  -o-box-align: center;\n}\n.h-l {\n  display: -webkit-box;\n  display: -moz-box;\n  display: box;\n  box-orient: horizontal;\n  -webkit-box-orient: horizontal;\n  -moz-box-orient: horizontal;\n  -o-box-orient: horizontal;\n  box-pack: start;\n  -webkit-box-pack: start;\n  -moz-box-pack: start;\n  -o-box-pack: start;\n  box-align: center;\n  -webkit-box-align: center;\n  -moz-box-align: center;\n  -o-box-align: center;\n}\n.h-r {\n  display: -webkit-box;\n  display: -moz-box;\n  display: box;\n  box-orient: horizontal;\n  -webkit-box-orient: horizontal;\n  -moz-box-orient: horizontal;\n  -o-box-orient: horizontal;\n  box-pack: end;\n  -webkit-box-pack: end;\n  -moz-box-pack: end;\n  -o-box-pack: end;\n  box-align: center;\n  -webkit-box-align: center;\n  -moz-box-align: center;\n  -o-box-align: center;\n}\n.v-c {\n  display: -webkit-box;\n  display: -moz-box;\n  display: box;\n  box-orient: vertical;\n  -webkit-box-orient: vertical;\n  -moz-box-orient: vertical;\n  -o-box-orient: vertical;\n  box-pack: center;\n  -webkit-box-pack: center;\n  -moz-box-pack: center;\n  -o-box-pack: center;\n  box-align: center;\n  -webkit-box-align: center;\n  -moz-box-align: center;\n  -o-box-align: center;\n}\n.v-u {\n  display: -webkit-box;\n  display: -moz-box;\n  display: box;\n  box-orient: vertical;\n  -webkit-box-orient: vertical;\n  -moz-box-orient: vertical;\n  -o-box-orient: vertical;\n  box-pack: start;\n  -webkit-box-pack: start;\n  -moz-box-pack: start;\n  -o-box-pack: start;\n  box-align: center;\n  -webkit-box-align: center;\n  -moz-box-align: center;\n  -o-box-align: center;\n}\n.v-d {\n  display: -webkit-box;\n  display: -moz-box;\n  display: box;\n  box-orient: vertical;\n  -webkit-box-orient: vertical;\n  -moz-box-orient: vertical;\n  -o-box-orient: vertical;\n  box-pack: start;\n  -webkit-box-pack: end;\n  -moz-box-pack: end;\n  -o-box-pack: end;\n  box-align: center;\n  -webkit-box-align: center;\n  -moz-box-align: center;\n  -o-box-align: center;\n}\n* {\n  margin: 0;\n  padding: 0;\n}\nbody .ga_novel_Canvas {\n  position: absolute;\n  left: 0;\n  top: 0;\n  right: 0;\n  bottom: 0;\n}\nbody .ga_novel_Canvas canvas {\n  position: absolute;\n  display: block;\n  width: 320px;\n  height: 600px;\n  margin: auto;\n  left: 0;\n  top: 0;\n  right: 0;\n  bottom: 0;\n}\n",""])},function(t,e){t.exports=function(){var t=[];return t.toString=function(){for(var t=[],e=0;e<this.length;e++){var n=this[e];n[2]?t.push("@media "+n[2]+"{"+n[1]+"}"):t.push(n[1])}return t.join("")},t.i=function(e,n){"string"==typeof e&&(e=[[null,e,""]]);for(var i={},r=0;r<this.length;r++){var o=this[r][0];"number"==typeof o&&(i[o]=!0)}for(r=0;r<e.length;r++){var a=e[r];"number"==typeof a[0]&&i[a[0]]||(n&&!a[2]?a[2]=n:n&&(a[2]="("+a[2]+") and ("+n+")"),t.push(a))}},t}},function(t,e,n){function i(t,e){for(var n=0;n<t.length;n++){var i=t[n],r=f[i.id];if(r){r.refs++;for(var o=0;o<r.parts.length;o++)r.parts[o](i.parts[o]);for(;o<i.parts.length;o++)r.parts.push(u(i.parts[o],e))}else{for(var a=[],o=0;o<i.parts.length;o++)a.push(u(i.parts[o],e));f[i.id]={id:i.id,refs:1,parts:a}}}}function r(t){for(var e=[],n={},i=0;i<t.length;i++){var r=t[i],o=r[0],a=r[1],s=r[2],h=r[3],u={css:a,media:s,sourceMap:h};n[o]?n[o].parts.push(u):e.push(n[o]={id:o,parts:[u]})}return e}function o(t,e){var n=x(),i=b[b.length-1];if("top"===t.insertAt)i?i.nextSibling?n.insertBefore(e,i.nextSibling):n.appendChild(e):n.insertBefore(e,n.firstChild),b.push(e);else{if("bottom"!==t.insertAt)throw new Error("Invalid value for parameter 'insertAt'. Must be 'top' or 'bottom'.");n.appendChild(e)}}function a(t){t.parentNode.removeChild(t);var e=b.indexOf(t);e>=0&&b.splice(e,1)}function s(t){var e=document.createElement("style");return e.type="text/css",o(t,e),e}function h(t){var e=document.createElement("link");return e.rel="stylesheet",o(t,e),e}function u(t,e){var n,i,r;if(e.singleton){var o=P++;n=p||(p=s(e)),i=c.bind(null,n,o,!1),r=c.bind(null,n,o,!0)}else t.sourceMap&&"function"==typeof URL&&"function"==typeof URL.createObjectURL&&"function"==typeof URL.revokeObjectURL&&"function"==typeof Blob&&"function"==typeof btoa?(n=h(e),i=g.bind(null,n),r=function(){a(n),n.href&&URL.revokeObjectURL(n.href)}):(n=s(e),i=l.bind(null,n),r=function(){a(n)});return i(t),function(e){if(e){if(e.css===t.css&&e.media===t.media&&e.sourceMap===t.sourceMap)return;i(t=e)}else r()}}function c(t,e,n,i){var r=n?"":i.css;if(t.styleSheet)t.styleSheet.cssText=w(e,r);else{var o=document.createTextNode(r),a=t.childNodes;a[e]&&t.removeChild(a[e]),a.length?t.insertBefore(o,a[e]):t.appendChild(o)}}function l(t,e){var n=e.css,i=e.media;if(i&&t.setAttribute("media",i),t.styleSheet)t.styleSheet.cssText=n;else{for(;t.firstChild;)t.removeChild(t.firstChild);t.appendChild(document.createTextNode(n))}}function g(t,e){var n=e.css,i=e.sourceMap;i&&(n+="\n/*# sourceMappingURL=data:application/json;base64,"+btoa(unescape(encodeURIComponent(JSON.stringify(i))))+" */");var r=new Blob([n],{type:"text/css"}),o=t.href;t.href=URL.createObjectURL(r),o&&URL.revokeObjectURL(o)}var f={},d=function(t){var e;return function(){return"undefined"==typeof e&&(e=t.apply(this,arguments)),e}},v=d(function(){return/msie [6-9]\b/.test(window.navigator.userAgent.toLowerCase())}),x=d(function(){return document.head||document.getElementsByTagName("head")[0]}),p=null,P=0,b=[];t.exports=function(t,e){e=e||{},"undefined"==typeof e.singleton&&(e.singleton=v()),"undefined"==typeof e.insertAt&&(e.insertAt="bottom");var n=r(t);return i(n,e),function(t){for(var o=[],a=0;a<n.length;a++){var s=n[a],h=f[s.id];h.refs--,o.push(h)}if(t){var u=r(t);i(u,e)}for(var a=0;a<o.length;a++){var h=o[a];if(0===h.refs){for(var c=0;c<h.parts.length;c++)h.parts[c]();delete f[h.id]}}}};var w=function(){var t=[];return function(e,n){return t[e]=n,t.filter(Boolean).join("\n")}}()}])}});