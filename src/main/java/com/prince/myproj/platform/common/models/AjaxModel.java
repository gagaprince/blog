package com.prince.myproj.platform.common.models;

import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.prince.myproj.platform.common.statics.ErrorCode;

/**
 * Created by zidong.wang on 2017/2/13.
 */
public class AjaxModel {
    private Object data;
    private int code;
    private String des;

    public int getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public String getDes() {
        return des;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setStatus(ErrorCode c){
        this.setCode(c.getCode());
        this.setDes(c.getDes());
    }

    @Override
    public String toString() {
        Map<String,Object> returnMap = new HashMap<String, Object>();
        Map<String,Object> status = new HashMap<String, Object>();
        status.put("code",code);
        status.put("des",des);
        returnMap.put("data",data);
        returnMap.put("status",status);
        return JSON.toJSONString(returnMap);
    }
}
