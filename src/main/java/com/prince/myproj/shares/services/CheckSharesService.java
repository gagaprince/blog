package com.prince.myproj.shares.services;

import com.prince.myproj.shares.dao.CheckSharesDao;
import com.prince.myproj.shares.models.CheckShareModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2016/6/15.
 */
@Service
public class CheckSharesService {
    @Autowired
    private CheckSharesDao checkSharesDao;

    @Autowired
    private CheckTask checkTask;


    public void ctrlCheck(int type,float max,float min,String code){
        if(checkTask.isRunning()){
            CheckShareModel model = new CheckShareModel();
            model.setCode(code);
            model.setMaxPrice(max);
            model.setMinPrice(min);
            if(type==0){
                //ֹͣ
                checkTask.removeCheckInList(model);
            }else{
                checkTask.addCheckInList(model);
            }
        }
    }

    public void onOffTask(boolean onOrOff){
        if(onOrOff){
            checkTask.begin();
        }else{
            checkTask.stop();
        }
    }



}
