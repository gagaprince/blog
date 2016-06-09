package com.prince.myproj.shares.models;

/**
 * Created by gagaprince on 16-6-9.
 */
public class AnalysisBuyTimeBean {
    String code;    //股票代码

    SharesModel minModel;       //cys最低的日期
    SharesModel todayModel;     //最近一个交易日的数据
    SharesModel buyModel;       //买入日的数据
    SharesModel sellModel;      //卖出日的数据

    float increasePer;      //买入日到最近交易日的营收比
    float increaseVal;      //到今日的每股盈利
    int waitDay;            //等待天数

    boolean success;

    public SharesModel getSellModel() {
        return sellModel;
    }

    public void setSellModel(SharesModel sellModel) {
        this.sellModel = sellModel;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public float getIncreasePer() {
        return increasePer;
    }

    public float getIncreaseVal() {
        return increaseVal;
    }

    public int getWaitDay() {
        return waitDay;
    }

    public SharesModel getBuyModel() {
        return buyModel;
    }

    public SharesModel getMinModel() {
        return minModel;
    }

    public SharesModel getTodayModel() {
        return todayModel;
    }

    public void setBuyModel(SharesModel buyModel) {
        this.buyModel = buyModel;
    }

    public void setIncreasePer(float increasePer) {
        this.increasePer = increasePer;
    }

    public void setIncreaseVal(float increaseVal) {
        this.increaseVal = increaseVal;
    }

    public void setMinModel(SharesModel minModel) {
        this.minModel = minModel;
    }

    public void setTodayModel(SharesModel todayModel) {
        this.todayModel = todayModel;
    }

    public void setWaitDay(int waitDay) {
        this.waitDay = waitDay;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
