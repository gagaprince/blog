package com.prince.myproj.shares.celue;

import com.prince.myproj.shares.models.SharesModel;


/**
 * Created by zidong.wang on 2016/8/1.
 */
public interface SelectSharesCelue {
    //��ѡ ���� ѡȡ����
    public SharesModel select(String code,String date);
    public String getName();
}
