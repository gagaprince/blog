"use strict";
function Btn(id){

}
Btn.prototype={
    ctor:function(id){
        this.btn = document.getElementById(id);
        this.init();
    },
    btn:null,
    init:function(){
        this.initListener();
    },
    initListener:function(){
        var _this = this;
        this.btn.addEventListener("click",function(){
            //点击了数字键
            _this.postMsg();
        },false);
    }
};
Btn.extend=function(prototype){
    function Class(){
        if(this.ctor){
            this.ctor.apply(this,arguments);
        }
    }
    Class.prototype = Object.create(this.prototype);
    for(var key in prototype){
        Class.prototype[key]=prototype[key];
    }
    Class.extend = Btn.extend;
    return Class;
}