var index = {
    init:function(){
        this._api("/blog/wx/getShareBisic",{
            location:window.location.href
        },function(res){
            console.log(res);
            $("body").html(JSON.stringify(res));
        })
    },
    _api:function(url,data,callback){
        $.ajax({
            url:url,
            data:JSON.stringify(data),
            contentType:"application/json; charset=UTF-8",
            type : 'POST',
            dataType : 'json',
            timeout : 3e4,
            success:function(res){
                if(callback){
                    callback(res);
                }
            }
        });
    }
};
$(document).ready(function(){
    index.init();
});