package com.prince.myproj.util;

import org.springframework.stereotype.Service;

import java.text.ParseException;
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

    public String getAddDate(String date,String fomateStr,long time){
        SimpleDateFormat sdf = new SimpleDateFormat(fomateStr);
        Date returnDate = null;
        try {
            returnDate = sdf.parse(date);
            returnDate.setTime(returnDate.getTime() +time);
            return sdf.format(returnDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
