(function(win){

    function Request(name){
        if(!(this instanceof Request)){
            return new Request(name);
        }else {
            var p = Promis.create(this);
            this.name = name;
            this.promis = p;
        }
    }

    Request.prototype={
        promis:null,
        name:"",
        _isfirst:true,
        begin:function(){
            this.ask();
            this.answer();
        },
        fail:function(){
            alert("求婚失败");
        },
        done:function(){
            alert("求婚成功");
        },
        ask:function(){
            var name = this.name;
            var askany = '小王：'+name+',我可以和小米在一起么？';
            this._say(askany);
        },
        answer:function(){
            var name = this.name;
            var answerAny = name+"：我得想一下。。。";
            this._say(answerAny);
            _this = this;
            setTimeout(function(){
                if(_this._isAgree()){
                    _this._say(name+'：好吧我同意了~');
                    _this.promis.resolve();
                }else{
                    _this._say(name+'：我不同意');

                    if(!_this._isfirst){
                        _this.promis.reject();
                        return;
                    }
                    _this._isfirst=false;
                    _this.begin();
                }
            },1000);
        },
        _isAgree:function(){
            var randomNum = parseInt(Math.random()*5);
            if(randomNum>=3){
                return false;
            }
            return true;
        },
        _say:function(any){
            $("#logFrame").append('<p>'+any+'</p>');
        }
    }

    win.Request = Request;

})(window);