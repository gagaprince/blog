package com.prince.myproj.shares.models;

import java.util.Date;

/**
 * Created by zidong.wang on 2016/5/16.
 */
public class SharesModel {
    private long id;
    private String code;    //��Ʊ����
    private String name;    //��Ʊ����
    private float open;
    private float close;
    private float high;
    private float low;
    private float volume;   //�ɽ���
    private String date;
    private Date createTime=new Date();


    private float increasePer;  //�ǵ���
    private float increaseVal;  //�ǵ���
    private float changePer;    //������
    private float volumeVal;    //�ɽ����
    private float totalValue;   //����ֵ
    private float transValue;   //��ͨ��ֵ


    private float sixMean;//6�վ�ֵ
    private float tweentyMean;  //21�վ�ֵ

    private float cyc5; //5��ƽ���ɱ�
    private float cyc13;    //13��ƽ���ɱ�
    private float cyc34;    //34��ƽ���ɱ�

    private float cys5;     //5��ӯ���� ���߲���
    private float cys13;    //13��ӯ����    ����
    private float cys34;    //34��ӯ���� ����

    public float getCyc13() {
        return cyc13;
    }

    public float getCyc34() {
        return cyc34;
    }

    public float getCyc5() {
        return cyc5;
    }

    public float getCys13() {
        return cys13;
    }

    public float getCys34() {
        return cys34;
    }

    public float getCys5() {
        return cys5;
    }

    public void setCyc13(float cyc13) {
        this.cyc13 = cyc13;
    }

    public void setCyc34(float cyc34) {
        this.cyc34 = cyc34;
    }

    public void setCyc5(float cyc5) {
        this.cyc5 = cyc5;
    }

    public void setCys13(float cys13) {
        this.cys13 = cys13;
    }

    public void setCys34(float cys34) {
        this.cys34 = cys34;
    }

    public void setCys5(float cys5) {
        this.cys5 = cys5;
    }

    public float getChangePer() {
        return changePer;
    }

    public float getTotalValue() {
        return totalValue;
    }

    public float getTransValue() {
        return transValue;
    }

    public Float getTweentyMean() {
        return tweentyMean;
    }

    public float getVolumeVal() {
        return volumeVal;
    }

    public void setChangePer(float changePer) {
        this.changePer = changePer;
    }

    public void setTotalValue(float totalValue) {
        this.totalValue = totalValue;
    }

    public void setTransValue(float transValue) {
        this.transValue = transValue;
    }

    public void setVolumeVal(float volumeVal) {
        this.volumeVal = volumeVal;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public float getIncreasePer() {
        return increasePer;
    }

    public float getIncreaseVal() {
        return increaseVal;
    }

    public Float getSixMean() {
        return sixMean;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setIncreasePer(float increasePer) {
        this.increasePer = increasePer;
    }

    public void setIncreaseVal(float increaseVal) {
        this.increaseVal = increaseVal;
    }

    public void setSixMean(float sixMean) {
        this.sixMean = sixMean;
    }

    public void setTweentyMean(float tweentyMean) {
        this.tweentyMean = tweentyMean;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public float getClose() {
        return close;
    }

    public float getHigh() {
        return high;
    }

    public float getLow() {
        return low;
    }

    public float getOpen() {
        return open;
    }

    public float getVolume() {
        return volume;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCycByDay(int day,float val){
        switch (day){
            case 5:
                setCyc5(val);
                break;
            case 13:
                setCyc13(val);
                break;
            case 34:
                setCyc34(val);
        }
    }
    public void setCysByDay(int day,float val){
        switch (day){
            case 5:
                setCys5(val);
                break;
            case 13:
                setCys13(val);
                break;
            case 34:
                setCys34(val);
        }
    }

}