"use strict";
var MultiBtn = OpBtn.extend({
    ctor:function(){
        OpBtn.prototype.ctor.apply(this,arguments);
        this.eventName=QEvent.EventName.MULTIBTN;
    },
    operate:function(a,b){
        return a*b;
    }
});