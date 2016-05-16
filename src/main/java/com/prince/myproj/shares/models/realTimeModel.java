package com.prince.myproj.shares.models;

import java.util.List;

/**
 * Created by zidong.wang on 2016/5/16.
 */
public class realTimeModel {
    private String name;            //��Ʊ����
    private String code;            //��Ʊ����
    private float open;             //���տ���
    private float yClose;           //��������
    private float hPrice;           //��߼�
    private float lPrice;           //��ͼ�
    private float currentPrice;     //��ǰ��
    private float growth;           //�۸��Ƿ�
    private float growthPercent;    //�Ƿ�����
    private int dealnumber;         //�ɽ�����
    private int tumover;            //�ɽ����
    private float hPrice52;         //52�����
    private float lPrice52;         //52�����

    private List<DealModel> buyDeals;   //��5
    private List<DealModel> sellDeals;  //��5��


    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public float getOpen() {
        return open;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public float getGrowth() {
        return growth;
    }

    public float getGrowthPercent() {
        return growthPercent;
    }

    public float gethPrice() {
        return hPrice;
    }

    public float gethPrice52() {
        return hPrice52;
    }

    public float getlPrice() {
        return lPrice;
    }

    public float getlPrice52() {
        return lPrice52;
    }

    public float getyClose() {
        return yClose;
    }

    public int getDealnumber() {
        return dealnumber;
    }

    public int getTumover() {
        return tumover;
    }

    public List<DealModel> getBuyDeals() {
        return buyDeals;
    }

    public List<DealModel> getSellDeals() {
        return sellDeals;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public void setBuyDeals(List<DealModel> buyDeals) {
        this.buyDeals = buyDeals;
    }

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setDealnumber(int dealnumber) {
        this.dealnumber = dealnumber;
    }

    public void setGrowth(float growth) {
        this.growth = growth;
    }

    public void setGrowthPercent(float growthPercent) {
        this.growthPercent = growthPercent;
    }

    public void sethPrice(float hPrice) {
        this.hPrice = hPrice;
    }

    public void sethPrice52(float hPrice52) {
        this.hPrice52 = hPrice52;
    }

    public void setlPrice(float lPrice) {
        this.lPrice = lPrice;
    }

    public void setlPrice52(float lPrice52) {
        this.lPrice52 = lPrice52;
    }

    public void setSellDeals(List<DealModel> sellDeals) {
        this.sellDeals = sellDeals;
    }

    public void setTumover(int tumover) {
        this.tumover = tumover;
    }

    public void setyClose(float yClose) {
        this.yClose = yClose;
    }
}
