package com.prince.myproj.shares.models;

/**
 * Created by zidong.wang on 2016/5/17.
 */
public class SharesSingleModel {
    private String codeAll;//����ȫ��
    private String code;    //ȥ����ĸ�Ĵ���
    private String name;    //��������
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
