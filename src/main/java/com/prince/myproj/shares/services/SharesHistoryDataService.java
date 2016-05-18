package com.prince.myproj.shares.services;

import com.prince.myproj.shares.dao.SharesDao;
import com.prince.myproj.shares.dao.SharesHistoryDao;
import com.prince.myproj.shares.models.ShareConfig;
import com.prince.myproj.shares.models.SharesModel;
import com.prince.myproj.shares.models.SharesSingleModel;
import com.prince.util.httputil.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2016/5/17.
 */
@Service
public class SharesHistoryDataService {

    public static final Logger logger = Logger.getLogger(SharesHistoryDataService.class);

    @Autowired
    private ShareConfig config;

    @Autowired
    private SharesDao sharesDao;
    @Autowired
    private SharesHistoryDao sharesHistoryDao;

    public void downloadTable(long start,long end){
        //下载备选股票的历史数据 保存在文件中
        List<SharesSingleModel> sharesModels = getSharesModels(start, end);

        int size = sharesModels.size();
        for(int i=0;i<size;i++){
            SharesSingleModel shareModel = sharesModels.get(i);
            downloadOneTable(shareModel);
        }
    }

    public void downloadTableContainXing(long start,long end){
        //下载备选股票的历史数据 保存在文件中
        List<SharesSingleModel> sharesModels = getSharesModels(start,end);

        int size = sharesModels.size();
        for(int i=0;i<size;i++){
            SharesSingleModel shareModel = sharesModels.get(i);
            if(shareModel.getName().contains("*")){
                downloadOneTable(shareModel);
            }
        }
    }

    private List<SharesSingleModel> getSharesModels(long start,long end){
        HashMap<String,Object> paramMap = new HashMap<String, Object>();

        paramMap.put("fromIndex",start);
        paramMap.put("toIndex",end-start);

        List<SharesSingleModel> sharesModels = sharesDao.getShares(paramMap);
        return sharesModels;
    }

    private void downloadOneTable(SharesSingleModel model){
        //下载对应code的股票文件 到对应目录
        String codeAll = model.getCodeAll();
        String code = model.getCode();
        String codeReal = "";
        String path = config.getShareTablePath()+model.getCodeAll()+"_"+model.getName().replace("*","x")+".csv";
        logger.info(path);
        if(codeAll.startsWith("sh")){
            codeReal = code+".ss";
        }else{
            codeReal = code+".sz";
        }

        String csvUrl = config.getHistoryUrl()+codeReal;
        logger.info(csvUrl);

        HttpUtil httpUtil = HttpUtil.getInstance();
        httpUtil.saveImgByUrl(csvUrl,path);


    }


    public void saveTableInDB(long start,long end){
        //将csv文件中的数据保存到数据库中
        List<SharesSingleModel> sharesModels = getSharesModels(start, end);
        int size = sharesModels.size();
        for(int i=0;i<size;i++){
            saveOneTableInDB(sharesModels.get(i));
        }

    }

    private void saveOneTableInDB(SharesSingleModel model){
        String path = config.getShareTablePath()+model.getCodeAll()+"_"+model.getName().replace("*","x")+".csv";
        File f = new File(path);
        if(f.exists()){
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(f));
                String line = br.readLine();//第一行先读出
                while((line=br.readLine())!=null){
                    logger.info(line);
                    String[] fileds = line.split(",");

                    if(fileds.length>=6){
                        SharesModel sharesModel = new SharesModel();
                        sharesModel.setDate(fileds[0].trim());
                        sharesModel.setCode(model.getCodeAll());
                        sharesModel.setOpen(Float.parseFloat(fileds[1].trim()));
                        sharesModel.setHigh(Float.parseFloat(fileds[2].trim()));
                        sharesModel.setLow(Float.parseFloat(fileds[3].trim()));
                        sharesModel.setClose(Float.parseFloat(fileds[4].trim()));
                        sharesModel.setVolume(Float.parseFloat(fileds[5].trim()));

                        if(isExitHistory(sharesModel)){
                            sharesHistoryDao.save(sharesModel);
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private boolean isExitHistory(SharesModel sharesModel){
        Map<String,Object> keyMap = new HashMap<String, Object>();
        keyMap.put("code",sharesModel.getCode());
        keyMap.put("date",sharesModel.getDate());
        List<SharesModel> exitList = sharesHistoryDao.selectByMap(keyMap);
        if(exitList.size()==0){
            return false;
        }
        return true;
    }


    public void updateTodayHistory(long start,long end){
        List<SharesSingleModel> sharesModels = getSharesModels(start, end);
        int size = sharesModels.size();
        for(int i=0;i<size;i++){
            List<SharesSingleModel> updateModels = new ArrayList<SharesSingleModel>();
            for(int j=0;j<10&&i+j<size;j++){
                updateModels.add(sharesModels.get(i+j));
            }
            updateOneTodayHistory(updateModels);
        }
    }

    private void updateOneTodayHistory(List<SharesSingleModel> models){
        int size = models.size();
        StringBuffer sb = new StringBuffer("");
        for(int i=0;i<size;i++){
            SharesSingleModel model = models.get(i);
            sb.append(model.getCodeAll()).append(",");
        }
        String url = config.getRealTimeUrl()+sb.toString();
        logger.info(url);
        HttpUtil httpUtil = HttpUtil.getInstance();
        String todayContent = httpUtil.getContentByUrl(url);
        logger.info(todayContent);

        parseTodayData(todayContent,models);

    }

    private void parseTodayData(String todayContent,List<SharesSingleModel> models){
        String[] codeContents = todayContent.split(";");
        int length = codeContents.length;
        for(int i=0;i<length;i++){
            String codeContent = codeContents[i];
            String[] fields = codeContent.split(",");
            SharesSingleModel model = models.get(i);
            if(fields.length>=32){
                SharesModel sharesModel = new SharesModel();
                sharesModel.setCode(model.getCodeAll());
                sharesModel.setOpen(Float.parseFloat(fields[1]));
                sharesModel.setClose(Float.parseFloat(fields[3]));
                sharesModel.setHigh(Float.parseFloat(fields[4]));
                sharesModel.setLow(Float.parseFloat(fields[5]));
                sharesModel.setVolume(Float.parseFloat(fields[8]));
                sharesModel.setDate(fields[30]);
                if(isExitHistory(sharesModel)){
                    sharesHistoryDao.save(sharesModel);
                }
            }

        }
    }

}
