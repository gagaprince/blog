"use strict";
var em = EventManager;
var EqualBtn = OpBtn.extend({
    ctor:function(){
        OpBtn.prototype.ctor.apply(this,arguments);
        this.eventName=QEvent.EventName.EQUELBTN;
    },
    postMsg:function(){
        var e = new QEvent(QEvent.EventName.EQUELBTN);
        em.postMsg(e);
    },
    operate:function(){

    }
});