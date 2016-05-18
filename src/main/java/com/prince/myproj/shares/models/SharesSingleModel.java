package com.prince.myproj.shares.models;

/**
 * Created by zidong.wang on 2016/5/17.
 */
public class SharesSingleModel {
    private String codeAll;//代码全称
    private String code;    //去掉字母的代码
    private String name;    //代码名称
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public String getCodeAll() {
        return codeAll;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCodeAll(String codeAll) {
        this.codeAll = codeAll;
    }

    public void setName(String name) {
        this.name = name;
    }
}
