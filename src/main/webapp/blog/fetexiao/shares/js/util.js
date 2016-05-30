window.Util = {
    api:function(url,data,call){
        $.ajax({
            url:url,
            method:"GET",
            data:data,
            dataType:'JSON',
            success:function(res){
                call(res);
            }
        });
    }
}