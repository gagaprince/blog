$(function(){

    function calresultfirst(){
        var cals = $(".money-input");
        var calitem = cals[0];
        var total = 0;
        for(var i=0;calitem;calitem=cals[++i]){
            var moneyStr = $(calitem).val();
            if(moneyStr=="")moneyStr=0;
            var money = parseFloat(moneyStr);
            var day = parseInt($(calitem).attr("day"));
            total += money/360*day;
        }

        if(total<100000){
            return 0;
        }
        if(total<600000){
            return 0.0015*total- 150;
        }
        if(total<1100000){
            return 0.002*total - 450;
        }
        return 0.0025*total - 1000;
    }

    function calresultScecond(){
        var moneyStr = $(".money-input1").val();
        if(moneyStr=='')moneyStr=0;
        var money = parseFloat(moneyStr);
        return money * 0.0007;
    }

    function calresult(){
        return calresultfirst()+calresultScecond();
    }

    function initListener(){
        $("#reset").click(function(){
            $(".money-input").val("");
            $(".money-input1").val("");
            $(".result-text").html("");
        });
        $("#cal").click(function(){
            var result = calresult();
            $(".result-text").html(result);
        });
    }

    initListener();

})