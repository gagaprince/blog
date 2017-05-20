package com.prince.myproj.shares.models;

/**
 * Created by zidong.wang on 2017/5/19.
 */
public class LHBCacularResult {
    private String shareCode;
    private String shareName;
    private boolean isSuccess;
    private float increase;
    private float buy;
    private float sell;
    private String buyDate;
    private String sellDate;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getShareName() {
        return shareName;
    }

    public float getBuy() {
        return buy;
    }

    public float getIncrease() {
        return increase;
    }

    public float getSell() {
        return sell;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public String getSellDate() {
        return sellDate;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    public void setBuy(float buy) {
        this.buy = buy;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public void setIncrease(float increase) {
        this.increase = increase;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public void setSell(float sell) {
        this.sell = sell;
    }

    public void setSellDate(String sellDate) {
        this.sellDate = sellDate;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

}
