"use strict";

var main = {
    result:null,
    numBtns:[],
    acBtn:null,
    delBtn:null,
    opBtns:[],
    caculator:null,
    dotBtn:null,
    pmBtn:null,
    init:function(){
        this.initResult();
        this.initNumBtn();
        this.initAcBtn();
        this.initDelBtn();
        this.initOpBtn();
        this.initDotBtn();
        this.initPmBtn();
        this.initCaculator();
    },
    initResult: function () {
        this.result = new Result("result");
    },
    initNumBtn:function(){
        var numBtns = this.numBtns;
        for(var i=0;i<10;i++){
            var numBtn = new NumBtn("numBtn"+i,i);
            numBtns.push(numBtn);
        }
    },
    initAcBtn:function(){
        this.acBtn = new AcBtn("acBtn");
    },
    initDelBtn:function(){
        this.delBtn = new DelBtn("delBtn");
    },
    initOpBtn:function(){
        var opBtns = this.opBtns;
        var plusBtn = new PlusBtn("plusBtn");
        var minusBtn = new MinusBtn("minusBtn");
        var multiBtn = new MultiBtn("multiBtn");
        var diviBtn = new DiviBtn("diviBtn");
        var equalBtn = new EqualBtn("equalBtn");
        opBtns.push(plusBtn);
        opBtns.push(minusBtn);
        opBtns.push(multiBtn);
        opBtns.push(diviBtn);
        opBtns.push(equalBtn);
    },
    initDotBtn: function () {
        this.dotBtn = new DotBtn("dotBtn");
    },
    initPmBtn:function(){
        this.pmBtn = new PmBtn("pmBtn");
    },
    initCaculator:function(){
        this.caculator = new CaculatorManager();
    }
};


document.addEventListener("DOMContentLoaded",function(){
    main.init();
},false);