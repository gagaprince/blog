(function(){
    $(document).ready(function(){
        listCorlor();
        initListener();
    });

    function transformNum(intnum){
        var newNum = intnum.toString(16);
        if(newNum.length<2){
            newNum = 0+newNum;
        }
        return newNum.toUpperCase();
    }

    function transformIntNum(unum){
        return parseInt(unum,16);
    }

    function initListener(){
        $(".rgb").on("blur","input",function(){
            var inputEles = $(".rgb input");
            var inputEle = inputEles[0];
            var value = "#";
            for(var i=0;inputEle;inputEle=inputEles[++i]){
                var intnum = parseInt($(inputEle).val());
                if(isNaN(intnum)||intnum>255){
                    intnum=0;
                }
                value+=transformNum(intnum);
            }
            $(".uu16 input").val(value);
        });
        $(".uu16").on("blur","input",function(){
            var value = $(this).val();
            if(value.length>=7){
                value = value.substring(1);
            }
            var inputEles = $(".rgb input");
            for(var i=0;i<3;i++){
                var begin = i*2;
                var end = begin+2;
                var itemvalue = value.substring(begin,end);
                $(inputEles[i]).val(transformIntNum(itemvalue));
            }
        });
    }

    function listCorlor(){
        for(var r=0;r<6;r++){
            for(var g=0;g<6;g++){
                for(var b=0;b<6;b++){
                    var color = "#"+transformNum(r*51)+transformNum(g*51)+transformNum(b*51);
                    if(g<3){
                        var fontColor = "#fff";
                    }else{
                        var fontColor = "#000";
                    }
                    var html = '<div class="often-item" style="background:'+color+';color:'+fontColor+'">'+color+'</div>';
                    $("#oftenColors").append(html);
                }
            }
        }
        $("#oftenColors").append('<div style="clear:both;"></div>');
    }
})()