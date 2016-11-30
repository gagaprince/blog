"use strict";
//加减乘除按钮
var em = EventManager;
var OpBtn = Btn.extend({
    eventName:"",
    ctor:function(id){
        Btn.prototype.ctor.call(this,id);
    },
    postMsg:function(){
        var e = new QEvent(QEvent.EventName.OPBTN);
        e.setData(this.operate);
        em.postMsg(e);
    },
    operate:function(a,b){
        console.log("这个方法会被重写");
    }
});