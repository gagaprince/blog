"use strict";
var em = EventManager;
var PlusBtn = OpBtn.extend({
    ctor:function(){
        OpBtn.prototype.ctor.apply(this,arguments);
        this.eventName=QEvent.EventName.PLUSBTN;
    },
    operate:function(a,b){
        return a+b;
    }
});