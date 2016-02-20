(function(win){
    function Promis(){

    }
    Promis.prototype = {
        task:null,
        nextTask:null,
        init:function(task){
            this.task = task;
        },
        then:function(task){
            this.nextTask = task;
            return task.promis;
        },
        resolve:function(){
            if(this.nextTask)
                this.nextTask.begin();
            else
                this.task.done();
        },
        reject:function(){
            this.task.fail();
        }
    }

    win.Promis = Promis;
    Promis.create=function(task){
        var p = new Promis();
        p.init(task);
        return p;
    }

    win.Deferred = function(task){
        task.begin();
        return task.promis;
    }

})(window);