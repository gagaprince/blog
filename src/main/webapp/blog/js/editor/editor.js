$(document).ready(function(){
    initUE();
    initListener();
});
function initUE(){
    window.ue = UE.getEditor('container');
    if(typeof nowtitle!="undefined"){
        $("#title").val(nowtitle);
    }
    if(typeof nowcate!="undefined"){
        $("#cate").val(nowcate);
    }
    ue.ready(function(){
        //alert(content)
    });
}
function initListener(){
    var ue = window.ue;
    $("#submit").click(function(){
        var title = $("#title").val().trim();
        var cate = $("#cate").val().trim();
        var allContent = ue.getContent();
        addOrUpdate(title,cate,allContent);
    });
}

function addOrUpdate(title,cate,allContent){
    var nowId = window.nowId;
    var data = {
        title:title,
        cate:cate,
        allContent:allContent
    };
    if(nowId){
        data["id"]=nowId;
    }
    $.ajax({
        url:"/blog/editor/addDaily",
        type:"POST",
        data:data,
        success:function(res){
            var result = JSON.parse(res);
            if(result.code==0){
                var newId = result.id;
                window.location = "/blog/editor/index?id="+newId;
            }
        }
    })
}