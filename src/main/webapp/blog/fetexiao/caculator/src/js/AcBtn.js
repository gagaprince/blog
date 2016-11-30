"use strict";
//清空按钮
var em = EventManager;
var AcBtn = Btn.extend({
    num:null,
    ctor:function(id){
        Btn.prototype.ctor.call(this,id);
    },
    postMsg:function(){
        var e = new QEvent(QEvent.EventName.CLEARBTN);
        em.postMsg(e);
    }
});