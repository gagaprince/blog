"use strict";
var MinusBtn = OpBtn.extend({
    ctor:function(){
        OpBtn.prototype.ctor.apply(this,arguments);
        this.eventName=QEvent.EventName.MINUSBTN;
    },
    operate:function(a,b){
        return a-b;
    }
});