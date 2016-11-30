"use strict";
var em = EventManager;
function Result(id){
    this.input = document.getElementById(id);
    this.init();
}
Result.prototype={
    input:null,
    result:0,
    init:function(){
        this.initListener();
    },
    initListener:function(){
        var _this = this;
        em.addEventListener(QEvent.EventName.RENDER,function(e){
            var result=e.getData();
            _this.render(result);
        })
    },
    render:function(result){
        if(typeof result!="undefined"){
            this.result = result;
            this.input.value=this.result;
        }
    }
}