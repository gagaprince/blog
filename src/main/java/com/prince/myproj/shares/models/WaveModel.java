package com.prince.myproj.shares.models;

/**
 * Created by zidong.wang on 2016/12/21.
 */
public class WaveModel {
    private String startDate;
    private String endDate;
    private float waveSwing;
    private float initMoney;
    private float initPercent;
    private String shareCode;
    private int waveShareNum;

    public float getInitPercent() {
        return initPercent;
    }

    public void setInitPercent(float initPercent) {
        this.initPercent = initPercent;
    }

    public int getWaveShareNum() {
        return waveShareNum;
    }

    public void setWaveShareNum(int waveShareNum) {
        this.waveShareNum = waveShareNum;
    }

    public float getWaveSwing() {
        return waveSwing;
    }

    public float getInitMoney() {
        return initMoney;
    }

    public String getShareCode() {
        return shareCode;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStartDate() {
        return startDate;
    }


    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public void setInitMoney(float initMoney) {
        this.initMoney = initMoney;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setWaveSwing(float waveSwing) {
        this.waveSwing = waveSwing;
    }




}
