package com.prince.myproj.shares.models;

import java.util.Date;

/**
 * Created by zidong.wang on 2017/5/18.
 */
public class DragonTigerBean {
    private long id;
    private String shareCode;
    private int buy1Division;
    private float buy1Val;
    private int buy2Division;
    private float buy2Val;
    private int buy3Division;
    private float buy3Val;
    private int buy4Division;
    private float buy4Val;
    private int buy5Division;
    private float buy5Val;

    private int sell1Division;
    private float sell1Val;
    private int sell2Division;
    private float sell2Val;
    private int sell3Division;
    private float sell3Val;
    private int sell4Division;
    private float sell4Val;
    private int sell5Division;
    private float sell5Val;

    private Date currentDate;

    public Date getCurrentDate() {
        return currentDate;
    }

    public float getBuy1Val() {
        return buy1Val;
    }

    public float getBuy2Val() {
        return buy2Val;
    }

    public float getBuy3Val() {
        return buy3Val;
    }

    public float getBuy4Val() {
        return buy4Val;
    }

    public float getBuy5Val() {
        return buy5Val;
    }

    public float getSell1Val() {
        return sell1Val;
    }

    public float getSell2Val() {
        return sell2Val;
    }

    public float getSell3Val() {
        return sell3Val;
    }

    public float getSell4Val() {
        return sell4Val;
    }

    public float getSell5Val() {
        return sell5Val;
    }

    public int getBuy1Division() {
        return buy1Division;
    }

    public int getBuy2Division() {
        return buy2Division;
    }

    public int getBuy3Division() {
        return buy3Division;
    }

    public int getBuy4Division() {
        return buy4Division;
    }

    public int getBuy5Division() {
        return buy5Division;
    }

    public int getSell1Division() {
        return sell1Division;
    }

    public int getSell2Division() {
        return sell2Division;
    }

    public int getSell3Division() {
        return sell3Division;
    }

    public int getSell4Division() {
        return sell4Division;
    }

    public int getSell5Division() {
        return sell5Division;
    }

    public long getId() {
        return id;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBuy1Division(int buy1Division) {
        this.buy1Division = buy1Division;
    }

    public void setBuy1Val(float buy1Val) {
        this.buy1Val = buy1Val;
    }

    public void setBuy2Division(int buy2Division) {
        this.buy2Division = buy2Division;
    }

    public void setBuy2Val(float buy2Val) {
        this.buy2Val = buy2Val;
    }

    public void setBuy3Division(int buy3Division) {
        this.buy3Division = buy3Division;
    }

    public void setBuy3Val(float buy3Val) {
        this.buy3Val = buy3Val;
    }

    public void setBuy4Division(int buy4Division) {
        this.buy4Division = buy4Division;
    }

    public void setBuy4Val(float buy4Val) {
        this.buy4Val = buy4Val;
    }

    public void setBuy5Division(int buy5Division) {
        this.buy5Division = buy5Division;
    }

    public void setBuy5Val(float buy5Val) {
        this.buy5Val = buy5Val;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public void setSell1Division(int sell1Division) {
        this.sell1Division = sell1Division;
    }

    public void setSell1Val(float sell1Val) {
        this.sell1Val = sell1Val;
    }

    public void setSell2Division(int sell2Division) {
        this.sell2Division = sell2Division;
    }

    public void setSell2Val(float sell2Val) {
        this.sell2Val = sell2Val;
    }

    public void setSell3Division(int sell3Division) {
        this.sell3Division = sell3Division;
    }

    public void setSell3Val(float sell3Val) {
        this.sell3Val = sell3Val;
    }

    public void setSell4Division(int sell4Division) {
        this.sell4Division = sell4Division;
    }

    public void setSell4Val(float sell4Val) {
        this.sell4Val = sell4Val;
    }

    public void setSell5Division(int sell5Division) {
        this.sell5Division = sell5Division;
    }

    public void setSell5Val(float sell5Val) {
        this.sell5Val = sell5Val;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

}
