package com.prince.myproj.blog.models;

/**
 * Created by zidong.wang on 2015/12/31.
 */
public class ResultModel {
    public ResultModel(){
        this.bstatus = new Bstatus();
    }

    private Bstatus bstatus;
    private Object data;

    public Bstatus getBstatus() {
        return bstatus;
    }

    public Object getData() {
        return data;
    }

    public void setBstatus(Bstatus bstatus) {
        this.bstatus = bstatus;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public class Bstatus{
        private int code;
        private String desc;

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
