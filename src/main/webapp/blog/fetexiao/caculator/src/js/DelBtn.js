"use strict";
//退格按钮
var em = EventManager;
var DelBtn = Btn.extend({
    num:null,
    ctor:function(id){
        Btn.prototype.ctor.call(this,id);
    },
    postMsg:function(){
        var e = new QEvent(QEvent.EventName.DELBTN);
        em.postMsg(e);
    }
});