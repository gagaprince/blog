package com.prince.myproj.blog.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by gagaprince on 15-12-20.
 */
@Service
public class UtilService {
    public static final Logger logger = Logger.getLogger(UtilService.class);

    public String getDefaultWhenNull(String des,String defaultS){
        if (des==null){
            return defaultS;
        }
        return des;
    }
    //处理date数据到日
    public String formateDate(Date date){
        return DateFormat.getDateInstance(DateFormat.DEFAULT).format(date);
    }
    //会将字符串截取num之后加上...
    public String spliceString(String des,int num,int lines){
        int length = des.length();
        if (length>num+3) {
            des = des.substring(0, num);
        }
        int lineNum = num/lines;
        String[] tempLines = des.split("\n");
        int needredusNum = 0;
        for (int i=0;i<tempLines.length&&i<lines;i++){
            String tempLine = tempLines[i];
            int lineLength = tempLine.length();
            needredusNum+=(lineLength-1)/lineNum;
        }
        StringBuffer sb = new StringBuffer("");
        for(int i=0;i<lines-needredusNum && i<tempLines.length;i++){
            sb.append(tempLines[i]).append("\n");
        }
        sb.deleteCharAt(sb.lastIndexOf("\n"));
        return sb.toString()+"...";
    }
    //将换行符变成br标签
    public String replaceBr(String des){
        return des.replaceAll("\n","<br>");
    }

    public String replaceTag(String des){
        return des.replaceAll("<.+?>","");
    }

}
