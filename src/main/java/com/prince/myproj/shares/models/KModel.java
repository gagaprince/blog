package com.prince.myproj.shares.models;

/**
 * Created by gagaprince on 2017/6/4.
 */
public class KModel {
    private float open;
    private float close;
    private float high;
    private float low;
    private boolean color;
    private float sixMean;
    private float volum;
    private String currentDate;

    public void setAllBySharesModel(SharesModel sharesModel){
        this.open = sharesModel.getOpen();
        this.close = sharesModel.getClose();
        this.high = sharesModel.getHigh();
        this.low = sharesModel.getLow();
        this.sixMean = sharesModel.getSixMean();
        this.currentDate = sharesModel.getDate();
        this.volum = sharesModel.getVolume();

        this.color = this.close>=this.open;
    }

    public float getVolum() {
        return volum;
    }

    public void setVolum(float volum) {
        this.volum = volum;
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

    public boolean getColor(){
        return this.color;
    }

    public float getSixMean() {
        return sixMean;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
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

    public void setSixMean(float sixMean) {
        this.sixMean = sixMean;
    }
}
