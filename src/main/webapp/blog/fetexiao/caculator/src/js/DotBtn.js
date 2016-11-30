"use strict";
var em = EventManager;
var DotBtn = Btn.extend({
    name:null,
    ctor:function(id){
        Btn.prototype.ctor.call(this,id);
        this.name = "小数点";
    },
    postMsg:function(){
        var e = new QEvent(QEvent.EventName.DOTBTN);
        em.postMsg(e);
    }
});