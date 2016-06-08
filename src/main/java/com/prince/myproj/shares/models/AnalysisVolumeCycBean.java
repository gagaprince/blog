package com.prince.myproj.shares.models;

/**
 * Created by zidong.wang on 2016/6/8.
 */
public class AnalysisVolumeCycBean {
    private boolean largerVol;  //是否是放量
    private boolean isHigherCyc;    //是否是超越13日成本
    private SharesModel desModel;   //目标bean
    private boolean isTomIncrease;  //是否二日涨
    private boolean isAfterTomIncrease; //是否三日涨
    private float tomIncreasePer;       //二日涨幅
    private float afterTomIncreasePer;  //三日涨幅

    public float getAfterTomIncreasePer() {
        return afterTomIncreasePer;
    }

    public float getTomIncreasePer() {
        return tomIncreasePer;
    }

    public SharesModel getDesModel() {
        return desModel;
    }

    public boolean isAfterTomIncrease() {
        return isAfterTomIncrease;
    }

    public boolean isHigherCyc() {
        return isHigherCyc;
    }

    public boolean isLargerVol() {
        return largerVol;
    }

    public boolean isTomIncrease() {
        return isTomIncrease;
    }

    public void setAfterTomIncreasePer(float afterTomIncreasePer) {
        this.afterTomIncreasePer = afterTomIncreasePer;
    }

    public void setDesModel(SharesModel desModel) {
        this.desModel = desModel;
    }

    public void setIsAfterTomIncrease(boolean isAfterTomIncrease) {
        this.isAfterTomIncrease = isAfterTomIncrease;
    }

    public void setIsHigherCyc(boolean isHigherCyc) {
        this.isHigherCyc = isHigherCyc;
    }

    public void setIsTomIncrease(boolean isTomIncrease) {
        this.isTomIncrease = isTomIncrease;
    }

    public void setLargerVol(boolean largerVol) {
        this.largerVol = largerVol;
    }

    public void setTomIncreasePer(float tomIncreasePer) {
        this.tomIncreasePer = tomIncreasePer;
    }

}
