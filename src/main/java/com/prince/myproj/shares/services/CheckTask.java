package com.prince.myproj.shares.services;

import com.prince.myproj.shares.dao.CheckSharesDao;
import com.prince.myproj.shares.models.CheckShareModel;
import com.prince.myproj.shares.models.ShareConfig;
import com.prince.util.httputil.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2016/6/15.
 */
@Service
public class CheckTask {
    public static final Logger logger = Logger.getLogger(CheckTask.class);
    @Autowired
    private CheckSharesService checkSharesService;
    @Autowired
    private SharesMailService sharesMailService;

    @Autowired
    private CheckSharesDao checkSharesDao;

    @Autowired
    private ShareConfig config;

    private List<CheckShareModel> checkShareModels;
    private Map<String,CheckShareModel> checkMap;

    private Thread task;
    private boolean taskFlag = false;

    public void begin(){
        if(taskFlag)return;
        taskFlag = true;
        checkShareModels = giveMeNeedCheckList();
        checkMap = new HashMap<String, CheckShareModel>();
        for(int i=0;i<checkShareModels.size();i++){
            CheckShareModel checkShareModel = checkShareModels.get(i);
            checkMap.put(checkShareModel.getCode(),checkShareModel);
        }
        task = new Thread(new Runnable() {
            public void run() {
                while(taskFlag){
                    runTaskOne();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        task.start();
    }

    public void runTaskOne(){
        String url = parseUrl();
        logger.info(url);
        if(url!=null){
            HttpUtil httpUtil = HttpUtil.getInstance();
            String todayContent = httpUtil.getContentByUrl(url);
            parseContent(todayContent);
        }else{
//            logger.info("检测队列为空");
        }

    }

    public void addCheckInList(CheckShareModel model){
        String code = model.getCode();
        model.setStatus(1);
        CheckShareModel modelIn = checkMap.get(code);
        if(modelIn==null){//新添加的
            //检测数据库中是否有这条数据
            checkShareModels.add(model);
            checkMap.put(code,model);
        }else{
            modelIn.setMaxPrice(model.getMaxPrice());
            modelIn.setMinPrice(model.getMinPrice());
        }
        saveOrUpdateModel(model);
    }
    public void removeCheckInList(CheckShareModel model){
        String code = model.getCode();
        model.setStatus(0);
        CheckShareModel modelIn = checkMap.get(code);
        if(modelIn==null){//没有这条数据
        }else{
            saveOrUpdateModel(model);
            checkMap.remove(code);
            checkShareModels.remove(modelIn);
        }
    }


    private void parseContent(String content){
        String[] codeContents = content.split(";\n");
        int length = codeContents.length;
        logger.info("length:" + length);
        for(int i=0;i<length;i++){
            String codeContent = codeContents[i];
            String[] fields = codeContent.split(",");
            if(fields.length>=32){
                String code = fields[0].substring(11,19);
                logger.info(code);
                CheckShareModel checkShareModel = checkMap.get(code);
                if(checkShareModel==null)continue;

                float nowPrice = Float.parseFloat(fields[3]);
                float maxPrice = checkShareModel.getMaxPrice();
                float minPrice = checkShareModel.getMinPrice();
                if(nowPrice>maxPrice){
                    sharesMailService.sendCheckMail("代码："+code+" 现价："+nowPrice+" 超过监测价："+maxPrice+" 请决策是否卖出");
                    removeCheckInList(checkShareModel);
                }
                if(nowPrice<minPrice){
                    sharesMailService.sendCheckMail("代码："+code+" 现价："+nowPrice+" 低于监测价："+minPrice+" 请决策是否买入");
                    removeCheckInList(checkShareModel);
                }
            }

        }
    }

    private String parseUrl(){
        if(checkShareModels!=null&&checkShareModels.size()>0){
            int size = checkShareModels.size();
            StringBuffer sb = new StringBuffer("");
            for(int i=0;i<size;i++){
                CheckShareModel model = checkShareModels.get(i);
                sb.append(model.getCode()).append(",");
            }
            String url = config.getRealTimeUrl()+sb.toString();
            return url;
        }
        return null;
    }

    public void stop(){
        taskFlag = false;
    }

    public List<CheckShareModel> giveMeNeedCheckList(){
        List<CheckShareModel> checkShareModels = null;

        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("status", 1);

        checkShareModels = checkSharesDao.getCheckShares(paramMap);
        return checkShareModels;

    }

    public CheckShareModel giveMeCheckModelByCode(String code){
        List<CheckShareModel> checkShareModels = null;

        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code",code);

        checkShareModels = checkSharesDao.getCheckShares(paramMap);
        if(checkShareModels!=null&&checkShareModels.size()>0){
            return checkShareModels.get(0);
        }else{
            return null;
        }
    }

    public void saveOrUpdateModel(CheckShareModel model){
        String code = model.getCode();
        CheckShareModel modelInDb = giveMeCheckModelByCode(code);
        if(modelInDb==null){
            checkSharesDao.save(model);
        }else{
            checkSharesDao.update(model);
        }
    }

    public boolean isRunning(){
        return taskFlag;
    }
}
