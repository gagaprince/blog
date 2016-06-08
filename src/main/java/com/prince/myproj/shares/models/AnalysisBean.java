package com.prince.myproj.shares.models;

/**
 * Created by zidong.wang on 2016/6/7.
 */
public class AnalysisBean {
    private String code;
    private float highestCys;   //cys最高值
    private float lowestCys;    //cys最低值
    private String highestDate; //cys 最高时的日期
    private String lowestDate;  //cys 最低时的日期
    private String zfDate;      //cys由正转负的日期
    private int t;

    public String getZfDate() {
        return zfDate;
    }

    public String getLowestDate() {
        return lowestDate;
    }

    public String getHighestDate() {
        return highestDate;
    }

    public String getCode() {
        return code;
    }

    public float getHighestCys() {
        return highestCys;
    }

    public float getLowestCys() {
        return lowestCys;
    }

    public int getT() {
        return t;
    }

    public void setZfDate(String zfDate) {
        this.zfDate = zfDate;
    }

    public void setT(int t) {
        this.t = t;
    }

    public void setLowestDate(String lowestDate) {
        this.lowestDate = lowestDate;
    }

    public void setLowestCys(float lowestCys) {
        this.lowestCys = lowestCys;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setHighestCys(float highestCys) {
        this.highestCys = highestCys;
    }

    public void setHighestDate(String highestDate) {
        this.highestDate = highestDate;
    }

}
