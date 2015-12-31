$(document).ready(function(){
    var pv=new PhotoView();  // 初始化PhotoView,生成DOM元素

    var imgArray = $(".blog-img-list img");
    imgArray.sort(function(item1,item2){
        var item1Rank = parseInt($(item1).attr("data-index"));
        var item2Rank = parseInt($(item2).attr("data-index"));
        console.log(item1Rank-item2Rank)
        return item1Rank-item2Rank;
    });


    pv.add(imgArray);  // 将元素添加到PhotoView
    $(".blog-img-list").on("click",".cover",function(){
        var index = parseInt($(this).attr("data-index"));
        pv.show();  // 显示PhotoView
        pv.aim(index);  // 定位到某个图片
    });
});
