package com.prince.myproj.shares.models;

import java.util.List;

/**
 * Created by zidong.wang on 2016/5/16.
 */
public class realTimeModel {
    private String name;            //股票名称
    private String code;            //股票代码
    private float open;             //今日开盘
    private float yClose;           //昨日收盘
    private float hPrice;           //最高价
    private float lPrice;           //最低价
    private float currentPrice;     //当前价
    private float growth;           //价格涨幅
    private float growthPercent;    //涨幅比例
    private int dealnumber;         //成交股数
    private int tumover;            //成交金额
    private float hPrice52;         //52周最高
    private float lPrice52;         //52周最低

    private List<DealModel> buyDeals;   //买5
    private List<DealModel> sellDeals;  //卖5；


    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public float getOpen() {
        return open;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public float getGrowth() {
        return growth;
    }

    public float getGrowthPercent() {
        return growthPercent;
    }

    public float gethPrice() {
        return hPrice;
    }

    public float gethPrice52() {
        return hPrice52;
    }

    public float getlPrice() {
        return lPrice;
    }

    public float getlPrice52() {
        return lPrice52;
    }

    public float getyClose() {
        return yClose;
    }

    public int getDealnumber() {
        return dealnumber;
    }

    public int getTumover() {
        return tumover;
    }

    public List<DealModel> getBuyDeals() {
        return buyDeals;
    }

    public List<DealModel> getSellDeals() {
        return sellDeals;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public void setBuyDeals(List<DealModel> buyDeals) {
        this.buyDeals = buyDeals;
    }

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setDealnumber(int dealnumber) {
        this.dealnumber = dealnumber;
    }

    public void setGrowth(float growth) {
        this.growth = growth;
    }

    public void setGrowthPercent(float growthPercent) {
        this.growthPercent = growthPercent;
    }

    public void sethPrice(float hPrice) {
        this.hPrice = hPrice;
    }

    public void sethPrice52(float hPrice52) {
        this.hPrice52 = hPrice52;
    }

    public void setlPrice(float lPrice) {
        this.lPrice = lPrice;
    }

    public void setlPrice52(float lPrice52) {
        this.lPrice52 = lPrice52;
    }

    public void setSellDeals(List<DealModel> sellDeals) {
        this.sellDeals = sellDeals;
    }

    public void setTumover(int tumover) {
        this.tumover = tumover;
    }

    public void setyClose(float yClose) {
        this.yClose = yClose;
    }
}
