"use strict";
var em = EventManager;
var PmBtn = Btn.extend({
    name:null,
    ctor:function(id){
        Btn.prototype.ctor.call(this,id);
        this.name = "正负号";
    },
    postMsg:function(){
        var e = new QEvent(QEvent.EventName.PMBTN);
        em.postMsg(e);
    }
});