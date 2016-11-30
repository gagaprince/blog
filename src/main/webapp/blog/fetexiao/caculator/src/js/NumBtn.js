"use strict";
var em = EventManager;
var NumBtn = Btn.extend({
    num:null,
    ctor:function(id,num){
        Btn.prototype.ctor.call(this,id);
        this.num = num;
    },
    postMsg:function(){
        var e = new QEvent(QEvent.EventName.NUMBTN);
        e.setData(this.num);
        em.postMsg(e);
    }
});