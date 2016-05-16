package com.prince.myproj.shares.models;

import java.util.Date;

/**
 * Created by zidong.wang on 2016/5/16.
 */
public class SharesModel {
    private String code;    //股票代码
    private String name;    //股票名称
    private float open;
    private float close;
    private float high;
    private float low;
    private float volume;
    private Date date;

    public Date getDate() {
        return date;
    }

    public float getClose() {
        return close;
    }

    public float getHigh() {
        return high;
    }

    public float getLow() {
        return low;
    }

    public float getOpen() {
        return open;
    }

    public float getVolume() {
        return volume;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }
}
