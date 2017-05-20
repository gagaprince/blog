package com.prince.myproj.shares.models;

/**
 * Created by zidong.wang on 2017/5/18.
 */
public class DivisionBean {
    private Integer id;
    private int code;
    private String name;
    private String province;
    private String company;
    private int successNum;

    public int getSuccessNum() {
        return successNum;
    }

    public void setSuccessNum(int successNum) {
        this.successNum = successNum;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setId(int id) {
        this.id = id;
    }
}
