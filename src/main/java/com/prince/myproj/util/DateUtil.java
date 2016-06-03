package com.prince.myproj.util;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zidong.wang on 2016/6/3.
 */
@Service
public class DateUtil {

    public String getNowDate(){
        return getNowDate("yyyyMMdd");
    }

    public String getNowDate(String fomateStr){
        SimpleDateFormat df = new SimpleDateFormat(fomateStr);//设置日期格式
        return df.format(new Date());
    }
}
