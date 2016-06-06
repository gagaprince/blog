$(document).ready(function(){

    var saveHistroyDataUrl='/shares/saveHistoryData';
    var cacularDataUrl = '/shares/cacularAllMeans';
//    var cacularAllCyc = '/shares/cacularAllCyc';
    var cacularLastCyc = '/shares/cacularLastCyc';
    var sendMailUrl = '/shares/sendMail';
    var kingkeyUrl = '/shares/kingKey';//一键完成以上操作

    var Shares = {
        init:function(){
            this.initListener();
        },
        initListener:function(){

            $("#spiderData").on('click',function(){
                $("#resultFrame").html("");
                Util.api(saveHistroyDataUrl,{
                },function(res){
//                    alert(JSON.stringify(res));
                    $("#resultFrame").html(res.bstatus.desc);
                });
            });

            $("#cacularData").on("click",function(){
                $("#resultFrame").html("");
                Util.api(cacularDataUrl,{
                },function(res){
                    $("#resultFrame").html(res.bstatus.desc);
                });
            });

            $("#cacularCyc").on("click",function(){
                $("#resultFrame").html("");
                Util.api(cacularLastCyc,{
                },function(res){
                    $("#resultFrame").html(res.bstatus.desc);
                });
            });

            $("#sendMail").on("click",function(){
                $("#resultFrame").html("");
                Util.api(sendMailUrl,{
                },function(res){
                    $("#resultFrame").html(res.bstatus.desc);
                });
            });
            $("#kingkey").on("click",function(){
                $("#resultFrame").html("");
                Util.api(kingkeyUrl,{
                },function(res){
                    $("#resultFrame").html(res.bstatus.desc);
                });
            });

        }
    };

    Shares.init();

});