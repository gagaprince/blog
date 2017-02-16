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
    private Map<String,Object> status;


    public Object getData() {
        return data;
    }



    public void setData(Object data) {
        this.data = data;
    }


    public void setStatus(ErrorCode c){
        if(status==null){
            status = new HashMap<String, Object>();
        }
        status.clear();
        status.put("code",c.getCode());
        status.put("des",c.getDes());
    }

    public Map<String, Object> getStatus() {
        return status;
    }
}
