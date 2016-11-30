"use strict";
function CacuNum(){

}
CacuNum.prototype={
    isMinus:0,//是否是负数
    intPart:0,//整数部分
    floatPart:0,//小数部分
    dotType:0,//是否有小数点
    all:0,//整个数

    isUse:false,//当前数是否启用 如果未被启用 则 不参与运算
    clear:function(){
        this.isMinus=0;
        this.intPart=0;
        this.floatPart=0;
        this.dotType=0;
        this.all=0;
        this.isUse = false;
    },
    addMinus:function(){
        if(this.isMinus){
            this.isMinus=0;
        }else{
            this.isMinus=1;
        }
    },
    addDot:function(){
        if(!this.dotType){
            this.dotType=1;
        }
    },
    addNum:function(num){
        this.isUse = true;
        if(this.dotType){
            //有小数点
            var floatPart = this.floatPart;
            if(floatPart=="0"){
                floatPart+="."+num;
            }else{
                floatPart+=""+num;
            }
            this.floatPart = floatPart;
        }else{
            var intPart = this.intPart;
            if(intPart==0){
                intPart=num;
            }else{
                intPart+=""+num;
            }
            this.intPart=parseInt(intPart);
        }
    },
    removeNum:function(){
        if(this.dotType){
            //有小数点
            var floatPart = this.floatPart+"";
            if(floatPart.length>2){
                floatPart = floatPart.substring(0,floatPart.length-1);
            }
            this.floatPart = floatPart;
        }else{
            var intPart = this.intPart+"";
            if(intPart.length>0){
                intPart = intPart.substring(0,intPart.length-1);
            }
            if(intPart==""){
                intPart=0;
            }
            this.intPart=parseInt(intPart);
        }
    },
    //将一个数字设置到此对象中
    parseNumIn:function(num){
        if(num==0){
            this.isMinus=0;
        }else{
            this.isMinus = num/Math.abs(num)==-1?1:0;
        }
        num = Math.abs(num);
        var intPart = Math.floor(num);
        var floatPart = num-intPart;
        if(floatPart!=0){
            this.dotType=1
        }else{
            this.dotType=0;
        }
        this.intPart = intPart;
        this.floatPart = floatPart;
    },
    parseAll:function(){
        this.all = parseInt(this.intPart);
        if(this.dotType) {
            var floatPart = this.floatPart+"";
            this.all += "." + floatPart.substring(2, floatPart.length);
        }
        if(this.isMinus){
            this.all = -this.all;
        }
        return this.all;
    },
    canBeUse:function(){
        return this.isUse;
    }
}