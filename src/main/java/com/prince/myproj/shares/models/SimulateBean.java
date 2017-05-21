package com.prince.myproj.shares.models;

/**
 * Created by gagaprince on 2017/5/21.
 */
public class SimulateBean {
    private String currentDate;//当前操作的开始日期
    private float money;//当前操作资金
    private float endMoney;//操作过后的资金
    private float allMoney;//当前操作后 总资金
    private LHBCacularResult lhbCacularResult;
    private float incomePer;//当前的收益率
    private String endDate;//当前操作结束的日期

    public float getEndMoney() {
        return endMoney;
    }

    public void setEndMoney(float endMoney) {
        this.endMoney = endMoney;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public float getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(float allMoney) {
        this.allMoney = allMoney;
    }

    public float getIncomePer() {
        return incomePer;
    }

    public float getMoney() {
        return money;
    }

    public LHBCacularResult getLhbCacularResult() {
        return lhbCacularResult;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public void setIncomePer(float incomePer) {
        this.incomePer = incomePer;
    }

    public void setLhbCacularResult(LHBCacularResult lhbCacularResult) {
        this.lhbCacularResult = lhbCacularResult;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
