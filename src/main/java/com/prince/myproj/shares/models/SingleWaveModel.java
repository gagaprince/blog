package com.prince.myproj.shares.models;

/**
 * Created by zidong.wang on 2016/12/22.
 */
public class SingleWaveModel {
    private float liveMoney;
    private int currentShareNum;
    private float sharePrice;
    private float buyPrice;
    private float sellPrice;
    private float allMoney;

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("��ʽ�:"+liveMoney).append("\n")
                .append("��ǰ�ֹ�:" + currentShareNum).append("\n")
                .append("�ɼ�:" + sharePrice).append("\n")
                .append("����۸�:" + buyPrice).append("\n")
                .append("�����۸�:" + sellPrice).append("\n")
                .append("���ʲ�:" + allMoney).append("\n");
        return sb.toString();
    }

    public void cacuAll(){
        this.allMoney = liveMoney+sharePrice*currentShareNum*100;
    }

    public float getAllMoney() {
        return allMoney;
    }

    public float getBuyPrice() {
        return buyPrice;
    }

    public float getLiveMoney() {
        return liveMoney;
    }

    public float getSellPrice() {
        return sellPrice;
    }

    public float getSharePrice() {
        return sharePrice;
    }

    public int getCurrentShareNum() {
        return currentShareNum;
    }

    public void setAllMoney(float allMoney) {
        this.allMoney = allMoney;
    }

    public void setBuyPrice(float buyPrice) {
        this.buyPrice = buyPrice;
    }

    public void setCurrentShareNum(int currentShareNum) {
        this.currentShareNum = currentShareNum;
    }

    public void setLiveMoney(float liveMoney) {
        this.liveMoney = liveMoney;
    }

    public void setSellPrice(float sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void setSharePrice(float sharePrice) {
        this.sharePrice = sharePrice;
    }


}
