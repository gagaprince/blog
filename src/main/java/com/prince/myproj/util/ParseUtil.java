package com.prince.myproj.util;

import org.springframework.stereotype.Service;

/**
 * Created by zidong.wang on 2016/8/1.
 */
@Service
public class ParseUtil {
    public String parseString(String reqS,String defaultS){
        if(reqS==null){
            return defaultS;
        }
        return reqS;
    }
    public int parseInt(String reqs,int defaultI){
        int re = defaultI;
        try {
            re = Integer.parseInt(reqs);
        }catch (Exception e){
        }
        return re;
    }
    public float parseFloat(String reqs,float defaultF){
        float re = defaultF;
        try {
            re = Float.parseFloat(reqs);
        }catch (Exception e){
        }
        return re;
    }
}
