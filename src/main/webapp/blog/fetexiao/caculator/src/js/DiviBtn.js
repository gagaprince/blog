"use strict";
var DiviBtn = OpBtn.extend({
    ctor:function(){
        OpBtn.prototype.ctor.apply(this,arguments);
        this.eventName=QEvent.EventName.DIVIBTN;
    },
    operate:function(a,b){
        if(b==0){
            throw "divi num can not be zero!";
            return null;
        }
        return a/b;
    }
});