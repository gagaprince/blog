package com.prince.myproj.shares.mathutils;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zidong.wang on 2016/6/3.
 */
@Service
public class ShareFun {
    public static final Logger logger = Logger.getLogger(ShareFun.class);
    /**
     * ���㻬��ƽ��ֵ
     * ���㹫ʽ ���Ƽ���
     * Et = a*��pt-Ey��+Ey
     */

    public float ema(List<Float> ps,float a){
        float pt = ps.remove(ps.size()-1);
        float ey = 0;
        if(ps.size()==1){
            return ps.get(0);
        }else{
            ey = ema(ps,a);
        }
        return a*(pt-ey)+ey;
    }

}
