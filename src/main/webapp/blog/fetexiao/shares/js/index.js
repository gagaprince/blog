$(document).ready(function(){

    var saveHistroyDataUrl='/shares/saveHistoryData';

    var Shares = {
        init:function(){
            this.initListener();
        },
        initListener:function(){

            $("#spiderData").on('click',function(){
                Util.api(saveHistroyDataUrl,{

                },function(res){
                    alert(JSON.stringify(res));
                    $("#resultFrame").html(res.bstatus.desc);
                });
            });

        }
    };

    Shares.init();

});