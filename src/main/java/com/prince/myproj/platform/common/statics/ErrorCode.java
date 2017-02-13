package com.prince.myproj.platform.common.statics;

/**
 * Created by zidong.wang on 2017/2/13.
 */
public enum ErrorCode {
    SUCCESS("成功",0),
    NETWORK_ERROR("网络服务错误",1),
    NOT_FIND_ERROR("没有找到对应的条目",2);
    private String des;
    private int code;
    // 构造方法
    private ErrorCode(String des, int code) {
        this.des = des;
        this.code = code;
    }
    public String getDes(){
        return des;
    }

    public int getCode() {
        return code;
    }
}
