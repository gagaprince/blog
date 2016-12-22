package com.prince.myproj.shares.models;

/**
 * Created by zidong.wang on 2016/12/22.
 */
public class SingleWaveModel {
    private float liveMoney=0;
    private int currentShareNum=0;
    private int newBuyShareNum=0;
    private int allShareNum=0;
    private float sharePrice=0;
    private float buyPrice=0;
    private float sellPrice=0;
    private float allMoney=0;

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("活动资金:"+liveMoney).append("\n")
                .append("当前持股:" + allShareNum).append("\n")
                .append("股价:" + sharePrice).append("\n")
                .append("买入价格:" + buyPrice).append("\n")
                .append("卖出价格:" + sellPrice).append("\n")
                .append("总资产:" + allMoney).append("\n");
        return sb.toString();
    }

    public void cacuAll(){
        this.allMoney = liveMoney+sharePrice*currentShareNum*100;
        this.allShareNum = currentShareNum+newBuyShareNum;
    }

    public SingleWaveModel clonePre(){
        SingleWaveModel singleWaveModel = new SingleWaveModel();
        singleWaveModel.setAllMoney(allMoney);
        singleWaveModel.setBuyPrice(buyPrice);
        singleWaveModel.setSellPrice(sellPrice);
        singleWaveModel.setAllShareNum(allShareNum);
        singleWaveModel.setCurrentShareNum(allShareNum);
        singleWaveModel.setLiveMoney(liveMoney);
        singleWaveModel.setSharePrice(sharePrice);
        return singleWaveModel;
    }

    public int getAllShareNum() {
        return allShareNum;
    }

    public void setAllShareNum(int allShareNum) {
        this.allShareNum = allShareNum;
    }

    public int getNewBuyShareNum() {
        return newBuyShareNum;
    }

    public void setNewBuyShareNum(int newBuyShareNum) {
        this.newBuyShareNum = newBuyShareNum;
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
