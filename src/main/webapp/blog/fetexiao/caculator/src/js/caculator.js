"use strict";
var em = EventManager;
function CaculatorManager (){
    this.init();
}
var CacularStates={
    LEFTINPUT:0,
    RIGHTINPUT:1,
    ENSURE:2
}
CaculatorManager.prototype={
    resultContent:0,

    leftNum:null,
    rightNum:null,
    currentNum:null,

    currentOperate:null,

    state:CacularStates.LEFTINPUT,//当前计算状态
    init:function(){
        this.initData();
        this.initListener();
        this.postReuslt();
    },
    initData:function(){
        this.leftNum = new CacuNum();
        this.rightNum = new CacuNum();
        this.currentNum = this.leftNum;
    },
    postReuslt:function(){
        //向屏幕发送一个消息 改变屏幕内容
        this.resultContent = this.currentNum.parseAll();
        var result = this.resultContent;
        var e = new QEvent(QEvent.EventName.RENDER);
        e.setData(result);
        em.postMsg(e);
    },
    initListener:function(){
        //接收按键消息
        var _this = this;
        //接收数字键
        em.addEventListener(QEvent.EventName.NUMBTN,function(e){
            var num = e.getData();
            _this.excuteNum(num);
        });
        em.addEventListener(QEvent.EventName.DELBTN,function(e){
            _this.delNum();
        });
        em.addEventListener(QEvent.EventName.OPBTN,function(e){
            var operate = e.getData();
            _this.cacheOp(operate);
        });
        em.addEventListener(QEvent.EventName.EQUELBTN,function(e){
            _this.ensure();
        });
        em.addEventListener(QEvent.EventName.CLEARBTN,function(e){
           //归零操作
            _this.reset();
            _this.postReuslt();
        });
        em.addEventListener(QEvent.EventName.DOTBTN,function(e){
            _this.currentNum.addDot();
        });
        em.addEventListener(QEvent.EventName.PMBTN,function(e){
            _this.currentNum.addMinus();
            _this.postReuslt();
        });
    },
    excuteNum:function(num){
        if(this.state==CacularStates.ENSURE){
            //如果是ENSURE状态 说明上次计算已经结束
            this.reset();
        }
        this.currentNum.addNum(num);
        this.postReuslt();
    },
    delNum:function(){
        if(this.currentNum.canBeUse()){
            this.currentNum.removeNum();
            this.postReuslt();
        }
    },
    cacheOp:function(operate){
        if(this.state==CacularStates.ENSURE){
            //如果当前是ensure状态 则将当前状态转为rightnum
            this.state=CacularStates.RIGHTINPUT;
        }
        if(this.state==CacularStates.LEFTINPUT){
            //如果是输入第一个数字的阶段 输入了一个运算符
            //将运算符cache 并且将当前的state切换到rightInput
            this.currentOperate = operate;
            this.state=CacularStates.RIGHTINPUT;
            this.currentNum = this.rightNum;
        }else if(this.state==CacularStates.RIGHTINPUT){
            //如果处在输入第二个数字的阶段
            //将之前的结束 结果放入第一个数字 将新的运算缓存
            if(this.currentOperate!=null){
                this.caculatorResult();
            }
            this.currentOperate = operate;
        }
    },
    caculatorResult:function(){
        var leftNum = this.leftNum;
        var rightNum = this.rightNum;
        if(rightNum.canBeUse()){
            var left = leftNum.parseAll();
            var right = rightNum.parseAll();
            var oprate = this.currentOperate;
            console.log(left);
            console.log(right);
            var result = oprate(left,right);
            console.log(result);
            this.leftNum.parseNumIn(result);
            this.currentNum = this.leftNum;
            this.rightNum.clear();
            this.postReuslt();
            this.currentNum = this.rightNum;
            this.currentOperate=null;
        }
    },
    ensure:function(){
        if(this.state==CacularStates.RIGHTINPUT){
            this.caculatorResult();
            this.state=CacularStates.ENSURE;
        }
    },
    reset:function(){
        this.leftNum.clear();
        this.rightNum.clear();
        this.currentNum=this.leftNum;
        this.currentOperate = null;
        this.state = CacularStates.LEFTINPUT;
    }
}