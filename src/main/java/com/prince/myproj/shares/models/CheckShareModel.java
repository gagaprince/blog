package com.prince.myproj.shares.models;

/**
 * Created by zidong.wang on 2016/6/15.
 */
public class CheckShareModel {
    int id;
    String code;
    float maxPrice;
    float minPrice;
    int status;

    public float getMaxPrice() {
        return maxPrice;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
