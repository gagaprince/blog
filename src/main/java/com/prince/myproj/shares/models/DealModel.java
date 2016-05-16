package com.prince.myproj.shares.models;

/**
 * Created by zidong.wang on 2016/5/16.
 */
public class DealModel {
    private float dealPrice;    //成交金额
    private int dealNumber;     //成交量

    public float getDealPrice() {
        return dealPrice;
    }

    public void setDealNumber(int dealNumber) {
        this.dealNumber = dealNumber;
    }

    public int getDealNumber() {
        return dealNumber;
    }

    public void setDealPrice(float dealPrice) {
        this.dealPrice = dealPrice;
    }
}
