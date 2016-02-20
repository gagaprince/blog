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
    if(typeof nowbigCate!="undefined"){
        $("#bigcate").val(nowbigCate);
    }
    if(typeof nowtag!="undefined"){
        $("#tag").val(nowtag);
    }
    if(typeof nowdescription!="undefined"){
        $("#description").val(nowdescription);
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
        var bigcate = $("#bigcate").val().trim();
        var tag = $("#tag").val().trim();
        var description = $("#description").val().trim();
        var allContent = ue.getContent();
        addOrUpdate(title,cate,bigcate,allContent,tag,description);
    });
}

function addOrUpdate(title,cate,bigcate,allContent,tag,description){
    var nowId = window.nowId;
    var data = {
        title:title,
        cate:cate,
        bigCate:bigcate,
        allContent:allContent,
    };
    if(tag!=""){
        data["tag"]=tag;
    }
    if(description!=""){
        data["description"]=description;
    }
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