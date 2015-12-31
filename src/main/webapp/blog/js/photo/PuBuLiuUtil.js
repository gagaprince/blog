var gaga = gaga||{};
gaga.PubuUtil = gaga.UtilBase.extend({
    pubuItemTpl:['<a href="/blog/life/detail?id=@=id@" target="_blank">',
        '                                <div class="blog-img-item">',
        '                                    <span class="title"><font>@=name@</font></span>',
        '                                    <span class="img"><img src="@=cover@"></span>',
        '                                </div>',
        '                            </a>'].join(""),

    listNum:0,
    pno:1,
    psize:18,
    allPageCount:9999,
    loadLock:false,
    dataUrl:"/blog/lifeJson",
    appendLiName:"#pubu",

    init:function(num){
        this.listNum = num||3;
        this.initListener();
    },
    _loadMore:function(onLoadData,onAllDataReturn){
        var pno = this.pno;
        var psize = this.psize;
        if(pno>=this.allPageCount){
            if(onAllDataReturn){
                onAllDataReturn.call(this);
            }
        }
        //加载数据
        this.loadLock = true;
        var url = this.dataUrl;
        var _this = this;
        var data = {
            pno:pno
        }
        this.api(url,data,"GET",function(code,des,data,res){
            if(code==0){
                _this.pno++;
                if(onLoadData){
                    onLoadData.call(_this,data);
                }
                if(_this.pno>=_this.allPageCount){
                    if(onAllDataReturn){
                        onAllDataReturn.call(_this);
                    }
                }
            }else{
                console.log(des);
            }
            _this.loadLock = false;
        },false,true);

    },

    _addElementByData:function(data){
        var folders = data["folders"]||[];
        var folderItem = folders[0];
        var listNum = this.listNum;
        var appendLiName = this.appendLiName
        for(var i=0;folderItem;folderItem=folders[++i]){
            var index = i%listNum;
            var appendLiNameTemp = appendLiName+index;
            if(!folderItem.cover){
                folderItem.cover = "http://gagablog.oss-cn-beijing.aliyuncs.com/video/default.png";
            }
            var html = this.compile(this.pubuItemTpl,folderItem);
            $(appendLiNameTemp).append(html);
        }
        var listPage = data["listpage"]||{};
        this.allPageCount = listPage["allPage"];
    },

    _onAllDataReturn:function(){

    },

    loadNext:function(){
        var _this = this;
        this._loadMore(function(data){
            _this._addElementByData(data);
        },function(){
            _this._onAllDataReturn();
        });
    },

    initListener:function(){
        var _this = this;
        var timeOut = null;
        $(document).scroll(function(){
            if(timeOut!=null){
                clearTimeout(timeOut);
            }
            timeOut = setTimeout(function(){
                var winHeight = $(window).height();
                var allHeight = $(document).height();
                var scrollTop = $(document).scrollTop();
                if(winHeight+scrollTop>=allHeight-50){
                    _this.loadNext();
                }
            },100);

        });
    }
});
gaga.PubuUtil.create = function(num){
    var pubuUtil = new gaga.PubuUtil;
    pubuUtil.init(num);
    return pubuUtil;
}