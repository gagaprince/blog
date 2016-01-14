(function(win){
    function Html2js(){}
    Html2js.prototype={
        init:function(){
            this.initPage();
            this.initListener();
        },
        initPage:function(){
            $("#in").val("");
            $("#out").val("");
        },
        initListener:function(){
            var _this = this;
            $("#useDan").on("click",function(){
                _this._covert('\'');
            });
            $("#useShuang").on("click",function(){
                _this._covert('"');
            });
        },
        _covert:function(co){
            var inStr = $("#in").val();
            var sb = this._covert2js(inStr,co,this._isPlus());
            $("#out").val(sb);
        },
        _covert2js:function(inStr,co,isPlus){
            var ins = inStr.trim().replace(new RegExp(co,"g"),"\\"+co).split("\n");
            if(ins=="")return "";
            if(isPlus){
                var item = ins[0];
                var sb = "";
                for(var i=0;item;item=ins[++i]){
                    sb += co+item+co+'+\n';
                }
                sb=sb.substr(0,sb.length-2);
                sb+=';';
                return sb;
            }else{
                var item = ins[0];
                var sb = "[";
                for(var i=0;item;item=ins[++i]){
                    sb += co+item+co+',\n';
                }
                sb=sb.substr(0,sb.length-2);
                sb+='].join("");';
                return sb;
            }

        },
        _isPlus:function(){
            return $("#usePlus")[0].checked;
        }
    }
    Html2js.create=function(){
        var html2js = new Html2js();
        html2js.init();
        return html2js;
    }

    win.Html2js = Html2js;

})(window);