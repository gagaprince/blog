var gaga = gaga || {};
gaga.Class=function(){};
gaga.Class.extend=function(prop){
    var _superProp = this.prototype;            //拿到父类的prototype
    var _prototype = Object.create(_superProp);
    //构造一个以父类prototype为__proto__的对象作为子类的prototype
    //当然还需要对此prototype做修饰
    var desc={
         writable:true,
       enumerable:false,
       configurable:true
    };
    //设置默认的prototype成员的属性描述
    //定义所有子类成员都不可枚举，但都可以读写，
    //当然你也可以设置为可以枚举，设为不可枚举的好处是对成员的一种保护。

//添加对构造函数的支持
    function rClass(){
        if(this.ctor){
            this.ctor.apply(this,arguments);
        }
    }
    rClass.prototype = _prototype;

    //设置子类的constructor 其实不设置也可以，因为之后我们会重新定义构造函数
    desc.value = rClass;
    Object.defineProperty(_prototype,"constructor",desc);

    //遍历prop 并将对应的prototype修改掉
    /*for(var name in prop){
        desc.value=prop[name];
        Object.defineProperty(_prototype,name,desc);
    }*/
    //新版本的遍历prop 将对应的prototype修改掉  start
    var regSuper = /\b_super\b/;
    //检测函数是否包含_super 如果包含则说明存在对父类同名方法的调用
    for(var name in prop){
        var isFun = (typeof prop[name] == "function");
        //检测属性是否为函数
        var isOverride = (typeof _prototype[name] == "function")
        //检测父类中同名属性是否为函数
        var hasSuperCall = regSuper.test(prop[name]);
        //使用正则表达式检测子函数中有没有_super调用
        if(isFun && isOverride && hasSuperCall){
            //三种条件都满足的开启递归调用模式

            desc.value=(function(name,propCall){
                return function(){
                    var tmp = this._super;
                    //此处的this指向子类对象  暂存_super指向
                    this._super = _superProp[name];
                    //_super指向父类同名方法
                    var ret = propCall.apply(this,arguments);
                    //调用子类目标方法
                    //（目标方法中会调用this._super 而此时_super已经指向父类同名方法）;
                    this._super = tmp;
                    //将_super指针还原，这点很重要，
                    //因为父类中方法也可能会调用父类的父类方法，如果不还原，
                    //可能会造成指针混乱，这一点大家自己去思考
                    return ret;
                    //返回结果集
                }
            })(name,prop[name]);
            //一定要使用闭包，这个地方的原因可以参考
            //问题：每隔一秒钟输出i每次输出i+1 的闭包写法
            //不在过多描述
            Object.defineProperty(_prototype,name,desc);
            //将生成的方法赋值给_prototype
            //可以看出 这里其实使用了代理的设计模式，
            //我在生成成员的时候，
            //并非将传入的prop参数中的成员之间赋值，
            //而是构造了一个代理方法，
            //然后使用这个代理方法，请体会这里的技巧
        }else{
            //一般情况下，沿用之前的方法
            desc.value=prop[name];
            Object.defineProperty(_prototype,name,desc);
        }
    }
 //新版本的遍历prop 将对应的prototype修改掉  end
    rClass.extend = gaga.Class.extend;
    //将构造好的class返回 而此rClass就拥有父类和子类共同的属性
    return rClass;
}