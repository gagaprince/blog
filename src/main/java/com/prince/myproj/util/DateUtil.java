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
        return parseDateStr(new Date(), fomateStr);
    }

    public String parseDateStr(Date date,String fomateStr){
        SimpleDateFormat df = new SimpleDateFormat(fomateStr);//�������ڸ�ʽ
        return df.format(date);
    }

    public Date parseDate(String date,String fomateStr){
        SimpleDateFormat sdf = new SimpleDateFormat(fomateStr);
        Date returnDate = null;
        try {
            returnDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnDate;
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

    public int compareDate(String d1,String d2){
        Date date1 = this.parseDate(d1,"yyyy-MM-dd");
        Date date2 = this.parseDate(d2,"yyyy-MM-dd");

        if(date1.getTime()>date2.getTime()){
            return 1;
        }else if(date1.getTime()==date2.getTime()){
            return 0;
        }else{
            return -1;
        }
    }
}
