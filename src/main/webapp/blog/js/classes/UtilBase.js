gaga.UtilBase = gaga.Class.extend({
    compile:function(tpl,obj){
        return tpl.replace(new RegExp("@=(.*?)@","g"),function($0,$1){
            if(typeof obj[$1] != "undefined"){
                return obj[$1]
            }else{
                return "";
            }
        });
    },
    api:(function(){
        var filters = [];
        function addFilter(fiter){
            filters.push(fiter);
        }
        function dofilter(res,callback){
            var filter = filters[0];
            for(var i=0;filter;filter=filters[++i]){
                var flag = filter(res);
                if(!flag){
                    return;
                }
            }
            callback(res);
        }
        //登录filter
        addFilter(function(res){
            return true;
        });
        /**
         * @param useMock {Boolean} 是否使用测试桩。
         */
        return function(t, data, method ,callback, useMock,noFilter){
            if(!callback){
                callback=$.noop;
            }

            var doneFns=[];
            var stepFns=[callback];

            var e={
                done:function(fn){
                    doneFns.push(fn);
                    return this;
                },
                on:function(fn){
                    stepFns.push(fn);
                    return this;
                },
                resolve:function(){
                    e.status='done';
                    for (var i = 0; i < doneFns.length; i++) {
                        doneFns[i].apply(e,arguments);
                    }
                    return this;
                }
            };
            /*var dataSend = JSON.stringify({
                "bparams":data,
                "cparams":{
                    "local_lang":"zh-Hans"
                }
            });*/
            var dataSend = data;
            var ajaxOption={
                url : t,
                type : method||'GET',
                data : dataSend,
                contentType:"application/json; charset=UTF-8",
                dataType : 'json',
                timeout : 3e4,
                success : function(res){
                    // 当接口挂了
                    if(typeof res == "string"){
                        res = JSON.parse(res);
                    }
                    e.status='';
                    if(!noFilter){
                        dofilter(res,function(){
                            for (var i = 0; i < stepFns.length; i++) {
                                stepFns[i].call(e,res.bstatus&&res.bstatus.code,res.bstatus&&res.bstatus.desc,res.data,res);
                            }
                        });
                    }else{
                        for (var i = 0; i < stepFns.length; i++) {
                            stepFns[i].call(e,res.bstatus&&res.bstatus.code,res.bstatus&&res.bstatus.desc,res.data,res);
                        }
                    }


                },
                error : function(res){
                    e.status='fail';
                    // time out 的status 也是0
                    for (var i = 0; i < stepFns.length; i++) {
                        stepFns[i].call(e,-res.status||-1,Res.base_network_error,res.responseText);
                    }
                }
            };
            if(useMock){// 使用测试桩数据
                ajaxOption.url = 'js/mock/' + t + '.json' + '?rdm=' + Math.random();
                ajaxOption.type = 'GET';
            }
            e.retry=function(){
                if(e.status==='loading'){
                    return;
                }
                e.status='loading';
                e.ajax=$.ajax(ajaxOption);
                return this;
            };
            if(useMock){
                setTimeout(function(){
                    e.retry();
                },500)
            }else{
                e.retry();
            }
            return e;
        };
    })()
});