'use strict';
;(function(win, QNRCar) {
    var doc = win.document;
    var docEl = doc.documentElement;
    var dpr = 0;
    var scale = 0;
    var tid;
    var flexible = QNRCar.flexible || (QNRCar.flexible = {});


    if (!dpr && !scale) {
        var isAndroid = win.navigator.appVersion.match(/android/gi);
        var isIPhone = win.navigator.appVersion.match(/iphone/gi);
        var devicePixelRatio = win.devicePixelRatio;
        dpr = devicePixelRatio;
        scale = 1 / dpr;
    }

    docEl.setAttribute('data-dpr', dpr);

    /*var metaEl = doc.createElement('meta');
    metaEl.setAttribute('name', 'viewport');
    metaEl.setAttribute('content', 'width=device-width,initial-scale=' + scale + ', maximum-scale=' + scale + ', minimum-scale=' + scale + ', user-scalable=no');
    if (docEl.firstElementChild) {
        docEl.firstElementChild.appendChild(metaEl);
    } else {
        var wrap = doc.createElement('div');
        wrap.appendChild(metaEl);
        doc.write(wrap.innerHTML);
    }*/


    function refreshRem(){
        var width = window.innerWidth;
        alert(width);
        var rem = width / 750;
        docEl.style.fontSize = rem*100 + 'px';
        flexible.rem = win.rem = rem;
        alert(rem);
    }

    win.addEventListener('resize', function() {
        clearTimeout(tid);
        tid = setTimeout(refreshRem, 300);
    }, false);
    win.addEventListener('pageshow', function(e) {
        if (e.persisted) {
            clearTimeout(tid);
            tid = setTimeout(refreshRem, 300);
        }
    }, false);

    if (doc.readyState === 'complete') {
        doc.body.style.fontSize = 0.32 + 'rem';
    } else {
        doc.addEventListener('DOMContentLoaded', function(e) {
            doc.body.style.fontSize = 0.32 + 'rem';
        }, false);
    }

    refreshRem();

    flexible.dpr = win.dpr = dpr;
    flexible.refreshRem = refreshRem;
})(window, window);
