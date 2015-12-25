$(document).ready(function(){
    var pv=new PhotoView();  // 初始化PhotoView,生成DOM元素
        pv.add($(".video-list img"));  // 将元素添加到PhotoView
        $(".video-list").on("click",".cover",function(){
            var index = parseInt($(this).attr("data-index"));
            pv.show();  // 显示PhotoView
            pv.aim(index);  // 定位到某个图片
        });
});