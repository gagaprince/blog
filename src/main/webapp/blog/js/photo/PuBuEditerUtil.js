var gaga = gaga||{};

gaga.PubuEditorUtil = gaga.PubuUtil.extend({
    pubuItemTpl:['<a class="selectitem" data-id="@=id@" href="javascript:void(0)">',
        '                                <div id="imgItem@=id@" class="blog-img-item">',
        '                                    <span class="title"><font>@=name@</font></span>',
        '                                    <span class="img"><img src="@=cover@"></span>',
        '                                    <span class="addbtn redus" data-id="@=id@">减</span>',
        '                                    <span class="addbtn rank">@=rank@</span>',
        '                                    <span class="addbtn add" data-id="@=id@">加</span>',
        '                                </div>',
        '                            </a>'].join(""),
    addRankUrl:"/blog/life/addRank",

    addRank:function(id,rank){
        var url = this.addRankUrl;
        data={
            id:id,
            rank:rank
        }
        this.api(url,data,"GET",function(code,desc,data,res){
            if(code==0){
                var photofolder = data;
                var newRank = photofolder.rank;
                $("#imgItem"+id+" .rank").html(newRank);
            }
        },false,true);
    },
    initListener : function(){
        //alert(this._super)
        this._super();
        var _this = this;
        $("body").on("click",".add",function(){
            var id = $(this).attr("data-id");
            _this.addRank(id,100);
        });
        $("body").on("click",".redus",function(){
            var id = $(this).attr("data-id");
            _this.addRank(id,-100);
        });
    },
});
gaga.PubuEditorUtil.create = function(num){
    var pubuUtil = new gaga.PubuEditorUtil;
    pubuUtil.init(num);
    return pubuUtil;
}
$(document).ready(function(){
    gaga.PubuEditorUtil.create(3);
});
