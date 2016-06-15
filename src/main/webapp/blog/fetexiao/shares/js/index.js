$(document).ready(function(){

    var saveHistroyDataUrl='/shares/saveHistoryData';
    var cacularDataUrl = '/shares/cacularAllMeans';
    var cacularAllCyc = '/shares/cacularAllCyc';
    var cacularLastCyc = '/shares/cacularLastCyc';
    var modifyCacularCyc = '/shares/modifyCyc';
    var sendMailUrl = '/shares/sendMail';
    var kingkeyUrl = '/shares/cacuAll';//一键完成以上操作
    var cacuAllPre = '/shares/cacuAllPre';//收盘前预测
    var analysisVolCyc = '/shares/analysisVolCyc';//统计
    var tjCacularBuyShares = '/shares/tjCacularBuySHares';
    var checkShares = '/shares/checkShares';
    var onCheckUrl = '/shares/beginCheck?on=1';
    var offCheckUrl = '/shares/beginCheck?on=0';

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
                Util.api(cacularAllCyc,{
                },function(res){
                    $("#resultFrame").html(res.bstatus.desc);
                });
            });
            $("#cacularLastCyc").on("click",function(){
                $("#resultFrame").html("");
                Util.api(cacularLastCyc,{
                },function(res){
                    $("#resultFrame").html(res.bstatus.desc);
                });
            });

            $("#modifyCacularCyc").on("click",function(){
                $("#resultFrame").html("");
                Util.api(modifyCacularCyc,{
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

            $("#cacularPre").on("click",function(){
                $("#resultFrame").html("");
                Util.api(cacuAllPre,{
                },function(res){
                    $("#resultFrame").html(res.bstatus.desc);
                });
            });

            $("#tjvolcyc").on("click",function(){
                $("#resultFrame").html("");
                Util.api(analysisVolCyc,{
                },function(res){
                    $("#resultFrame").html(res.bstatus.desc);
                });
            });

            $("#tjCacularBuyShares").on("click",function(){
                $("#resultFrame").html("");
                Util.api(tjCacularBuyShares,{
                },function(res){
                    $("#resultFrame").html(res.bstatus.desc);
                });
            });

            $("#beginCheck").on("click",function(){
                var code = $("#checkCode").val();
                var min = $("#checkMin").val();
                var max = $("#checkMax").val();
                if(code!=null){
                    $("#resultFrame").html("");
                    Util.api(checkShares,{
                        check:1,
                        code:code,
                        min:min,
                        max:max
                    },function(res){
                        $("#resultFrame").html(res.bstatus.desc);
                    });
                }
            });

            $("#stopCheck").on("click",function(){
                var code = $("#checkCode").val();
                var min = $("#checkMin").val();
                var max = $("#checkMax").val();
                if(code!=""){
                    $("#resultFrame").html("");
                    Util.api(checkShares,{
                        check:0,
                        code:code
                    },function(res){
                        $("#resultFrame").html(res.bstatus.desc);
                    });
                }
            });

            $("#onCheck").on("click",function(){
                $("#resultFrame").html("");
                Util.api(onCheckUrl,{
                },function(res){
                    $("#resultFrame").html(res.bstatus.desc);
                });
            });

            $("#OffCheck").on("click",function(){
                $("#resultFrame").html("");
                Util.api(offCheckUrl,{
                },function(res){
                    $("#resultFrame").html(res.bstatus.desc);
                });
            });

        }
    };

    Shares.init();

});