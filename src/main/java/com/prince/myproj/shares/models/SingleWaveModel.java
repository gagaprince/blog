package com.prince.myproj.shares.models;

/**
 * Created by zidong.wang on 2016/12/22.
 */
public class SingleWaveModel {
    private float cbMoney=0;//成本资金
    private float liveMoney=0;
    private int currentShareNum=0;
    private int newBuyShareNum=0;
    private int allShareNum=0;
    private float sharePrice=0;
    private float buyPrice=0;
    private float sellPrice=0;
    private float allMoney=0;
    private String today;
    private float cbPrice=0;//成本


    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("活动资金:"+liveMoney).append("\n")
                .append("当前持股:" + allShareNum).append("\n")
                .append("股价:" + sharePrice).append("\n")
                .append("买入价格:" + buyPrice).append("\n")
                .append("卖出价格:" + sellPrice).append("\n")
                .append("成本价格:" + cbPrice).append("\n")
                .append("盈亏:" + allShareNum*100*(sharePrice-cbPrice)).append("\n")
                .append("总资产:" + allMoney).append("\n");
        return sb.toString();
    }

    public float getCbMoney() {
        return cbMoney;
    }

    public void setCbMoney(float cbMoney) {
        this.cbMoney = cbMoney;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public float getCbPrice() {
        return cbPrice;
    }

    public void setCbPrice(float cbPrice) {
        this.cbPrice = cbPrice;
    }

    public boolean sell(int num,float swing){
        //计算剩余是否大于卖出
        if(currentShareNum>=num){
            liveMoney += num*100*sellPrice;
            cbMoney -= num*100*sellPrice;
            currentShareNum -=num;
            sellPrice+=swing;
            buyPrice+=swing;
            return true;
        }
        return false;
    }

    public boolean buy(int num,float swing){
        //计算剩余是否可以买
        float useMoney = num*100*buyPrice;
        if(liveMoney>useMoney){
            liveMoney-=useMoney;
            cbMoney += useMoney;
            newBuyShareNum+=num;
            sellPrice-=swing;
            buyPrice-=swing;
            return true;
        }
        return false;
    }

    public void cacuAll(){
        this.allShareNum = currentShareNum+newBuyShareNum;
        this.allMoney = liveMoney+sharePrice*allShareNum*100;
        this.cbPrice = cbMoney/allShareNum/100;
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
        singleWaveModel.setCbMoney(cbMoney);
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
