"use strict";
/**
 * 小型的事件注册 侦听的manage
 * */
//事件类
function QEvent(name){
    this.name = name;
}
QEvent.prototype={
    name:null,
    model:null,
    getData:function(){
        return this.model
    },
    setData:function(model){
        this.model = model;
    },
    getName:function(){
        return this.name;
    }
}
QEvent.EventName={
    RENDER:"result_render",
    NUMBTN:"numbtn_click",
    DELBTN:"delbtn_click",
    PLUSBTN:"plusbtn_click",
    MINUSBTN:"minusbtn_click",
    MULTIBTN:"multibtn_click",
    DIVIBTN:"divibtn_click",
    OPBTN:"opbtn_clcik",
    EQUELBTN:"equle_click",
    CLEARBTN:"clear_click",
    DOTBTN:"dotbtn_click",
    PMBTN:"pmbtn_click"
}


var EventManager = {
    eventMap:{},

    addEventListener:function(eventName,callback){
        if(callback){
            var eventMap = this.eventMap;
            if(!eventMap[eventName]){
                eventMap[eventName] = [];
            }
            eventMap[eventName].push(callback);
        }
    },
    postMsg:function(event){//event 是一个事件对象
        var eventName = event.getName();
        var eventMap = this.eventMap;
        if(eventMap[eventName]){
            var list = eventMap[eventName];
            var itemCall = list[0];
            for(var i=0;itemCall;itemCall=list[++i]){//事件的调用 不产生阻塞
                setTimeout((function(itemCall,event){
                    itemCall(event);
                })(itemCall,event));
            }
        }
    }
}