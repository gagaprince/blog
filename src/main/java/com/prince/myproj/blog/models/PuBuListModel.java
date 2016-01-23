package com.prince.myproj.blog.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zidong.wang on 2015/12/31.
 */
public class PuBuListModel {
    public List<Object> pubuList;
    public PuBuListModel(){
        pubuList = new ArrayList<Object>();
    }

    public void addPubuToList(List<Object> list){
        if (list!=null&&list.size()>0){
            pubuList.add(list);
        }
    }

    public List<Object> getPubuList() {
        return pubuList;
    }

    public void setPubuList(List<Object> pubuList) {
        this.pubuList = pubuList;
    }
}
