package com.prince.myproj.shares.models;

/**
 * Created by zidong.wang on 2016/12/21.
 */
public class WaveModel {
    private String startDate;
    private String endDate;
    private float waveSwing;
    private int initMoney;
    private String shareCode;

    public float getWaveSwing() {
        return waveSwing;
    }

    public int getInitMoney() {
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

    public void setInitMoney(int initMoney) {
        this.initMoney = initMoney;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setWaveSwing(float waveSwing) {
        this.waveSwing = waveSwing;
    }

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("shareCode:"+shareCode).append("\n")
                .append("initMoney:" + initMoney).append("\n")
                .append("startDate:" + startDate).append("\n")
                .append("endDate:" + endDate).append("\n")
                .append("waveSwing:" + waveSwing).append("\n");
        return sb.toString();
    }

}
