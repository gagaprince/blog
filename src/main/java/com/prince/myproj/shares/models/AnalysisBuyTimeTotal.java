package com.prince.myproj.shares.models;

import java.util.List;

/**
 * Created by gagaprince on 16-6-11.
 */
public class AnalysisBuyTimeTotal {
    float inc;
    int maxWaitDay;
    int successNum;
    int fallNum;
    int increaseNum;
    float waitTime;
    List<AnalysisBuyTimeBean> analysisBuyTimeBeanList;

    public float getInc() {
        return inc;
    }

    public float getWaitTime() {
        return waitTime;
    }

    public int getFallNum() {
        return fallNum;
    }

    public int getIncreaseNum() {
        return increaseNum;
    }

    public int getMaxWaitDay() {
        return maxWaitDay;
    }

    public int getSuccessNum() {
        return successNum;
    }

    public List<AnalysisBuyTimeBean> getAnalysisBuyTimeBeanList() {
        return analysisBuyTimeBeanList;
    }

    public void setAnalysisBuyTimeBeanList(List<AnalysisBuyTimeBean> analysisBuyTimeBeanList) {
        this.analysisBuyTimeBeanList = analysisBuyTimeBeanList;
    }

    public void setFallNum(int fallNum) {
        this.fallNum = fallNum;
    }

    public void setInc(float inc) {
        this.inc = inc;
    }

    public void setIncreaseNum(int increaseNum) {
        this.increaseNum = increaseNum;
    }

    public void setMaxWaitDay(int maxWaitDay) {
        this.maxWaitDay = maxWaitDay;
    }

    public void setSuccessNum(int successNum) {
        this.successNum = successNum;
    }

    public void setWaitTime(float waitTime) {
        this.waitTime = waitTime;
    }

}
