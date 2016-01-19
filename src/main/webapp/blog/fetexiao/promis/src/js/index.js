$(document).ready(function () {

    $("#beginBtn").on("click",function(){
        $("#logFrame").html("").append('<p>程序员小王想娶女神小米。</p>');
        Deferred(Request("爸爸"))
            .then(Request("妈妈"))
            .then(Request("大伯"))
            .then(Request("小米"));
    });
});