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
import java.text.SimpleDateFormat;
import java.util.*;

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


    public void downloadTable(){
        long start = 0;
        long end = 3000;
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code","sh000001");
        SharesModel lastModel = sharesHistoryDao.selectLastModel(paramMap);
        String dateStart = lastModel.getDate();
        String dateEnd = getNowDate();
        logger.info("dateStart:"+dateStart);
        logger.info("dateEnd:" + dateEnd);
        updateTodayHistory(start,end,dateStart,dateEnd);
    }

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

    private List<SharesSingleModel> getSharesModels(String codes){
        HashMap<String,Object> paramMap = new HashMap<String, Object>();
        List<String> codeList = new ArrayList<String>();
        String[] codeItems = codes.split(",");
        for(int i=0;i<codeItems.length;i++){
            codeList.add(codeItems[i]);
        }
        paramMap.put("codes",codeList);
        logger.info(codes);
        List<SharesSingleModel> sharesModels = sharesDao.getSharesIncodes(paramMap);
        logger.info(sharesModels.size());
        return sharesModels;
    }

    private List<SharesSingleModel> getSharesModels(long start,long end){
        HashMap<String,Object> paramMap = new HashMap<String, Object>();

        paramMap.put("fromIndex",start);
        paramMap.put("toIndex", end - start);

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
        saveOneTableInDBWithFile(model,f,0);
    }

    private void saveOneTableInDBWithFile(SharesSingleModel model,File f,int type){
        if(f.exists()){
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(f));
                String line = br.readLine();//第一行先读出
                while((line=br.readLine())!=null){
                    logger.info(line);
                    try {
                        String[] fileds = line.split(",");
                        if(fileds.length>=6){
                            SharesModel sharesModel = new SharesModel();
                            sharesModel.setDate(fileds[0].trim());
                            sharesModel.setCode(model.getCodeAll());
                            sharesModel.setOpen(parseFloat(fileds,type + 1));
                            sharesModel.setHigh(parseFloat(fileds,type + 2));
                            sharesModel.setLow(parseFloat(fileds,type + 3));
                            sharesModel.setClose(parseFloat(fileds,type + 4));
                            sharesModel.setVolume(parseFloat(fileds,type+5));

                            if (fileds.length >= 12) {
                                sharesModel.setIncreaseVal(parseFloat(fileds,type + 6));
                                sharesModel.setIncreasePer(parseFloat(fileds,type + 7));
                                sharesModel.setChangePer(parseFloat(fileds,type+8));
                                sharesModel.setVolumeVal(parseFloat(fileds,type+9));
                                sharesModel.setTotalValue(parseFloat(fileds,type+10));
                                sharesModel.setTransValue(parseFloat(fileds,type+11));

                            }


                            if(!isExitHistory(sharesModel)){
                                sharesHistoryDao.save(sharesModel);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        logger.error(e.getMessage());
                    }

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private float parseFloat(String[] ss,int index){
        try {
            String s = ss[index].trim();
            if("".equals(s)){
                return 0;
            }else{
                return Float.parseFloat(s);
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;
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

    public void updateTodayHistory(String codes ,String dateStart,String dateEnd){
        List<SharesSingleModel> sharesModels = getSharesModels(codes);
        updateOneTodayHistory(sharesModels, dateStart, dateEnd);
    }

    public void updateTodayHistory(long start,long end,String dateStart,String dateEnd){
        List<SharesSingleModel> sharesModels = getSharesModels(start, end);
        updateOneTodayHistory(sharesModels, dateStart, dateEnd);
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

        parseTodayData(todayContent, models);

    }

    private void updateOneTodayHistory(List<SharesSingleModel> models,String dateStart,String dateEnd){
        int size = models.size();
        for(int i=0;i<size;i++){
            StringBuffer sb = new StringBuffer("");
            SharesSingleModel model = models.get(i);
            String codeAll = model.getCodeAll();
            String path = config.getShareTablePath()+codeAll+".csv";
            if(codeAll.startsWith("sz")){
                sb.append("&code=1");
            }else{
                sb.append("&code=0");
            }
            sb.append(model.getCode()).append("&start=").append(dateStart)
                    .append("&end=").append(dateEnd);
            String url = config.getHistoryAddUrl()+sb.toString();
            logger.info(url);
            HttpUtil httpUtil = HttpUtil.getInstance();
            httpUtil.saveImgByUrl(url, path);

            File f = new File(path);
            saveOneTableInDBWithFile(model, f,2);

        }


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
                if(!isExitHistory(sharesModel)){
                    sharesHistoryDao.save(sharesModel);
                }
            }

        }
    }

    public void cacularMean(long start,long end ,String startDate,String endDate){
        List<SharesSingleModel> sharesModels = getSharesModels(start, end);

        int size = sharesModels.size();
        for(int i=0;i<size;i++){
            SharesSingleModel shareModel = sharesModels.get(i);
            cacularMeanOneModel(shareModel,startDate,endDate);
        }
    }

    private void cacularMeanOneModel(SharesSingleModel model,String startDate,String endDate){
        List<SharesModel> models = getModelsByStartEndDate(model, startDate, endDate);
        cacularAndSaveMean(models, 6);
        cacularAndSaveMean(models,21);
    }

    private void cacularAndSaveMean(List<SharesModel> models,int days){
        //先取出 days 数据算总和
        float sum = 0;
        int size = models.size();
        int indexBegin=-1;

        for(int i=0;i<days-1 && i<size;i++){
            SharesModel model = models.get(i);
            float close = model.getClose();
            if(close==0){
                models.remove(i);
                i--;
                size--;
            }else {
                sum+=close;
            }
            indexBegin = i;
        }
        if(days>size){
            return ;
        }

        for(int i=indexBegin+1;i<size;i++){
            SharesModel model = models.get(i);
            float close = model.getClose();
            if(close==0){
                models.remove(i);
                size--;
                i--;
            }else{
                sum += close;
                float daysMean = sum/days;
                SharesModel preModel = models.get(i-days+1);
                float preClose = preModel.getClose();
                sum -= preClose;
                saveMeanInDb(model,daysMean,days);
            }
        }

    }

    private void saveMeanInDb(SharesModel model,float means,int days){
        if(days==6){
            model.setSixMean(means);
        }else{
            model.setTweentyMean(means);
        }
        if(model.getSixMean()!=null && model.getSixMean()!=0 && model.getTweentyMean()!=null && model.getTweentyMean()!=0){
            logger.info("update  id:"+model.getId() +" code:"+model.getCode()
                    +" 6daysmean:"+model.getSixMean()+" 21daysmean:"+model.getTweentyMean());
            sharesHistoryDao.updateMeans(model);
        }
    }

    //拿出将要计算的model
    private List<SharesModel> getModelsByStartEndDate(SharesSingleModel sharesSingleModel,String startDate,String endDate){
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("codeAll",sharesSingleModel.getCodeAll());
        paramMap.put("startDate",startDate);
        paramMap.put("endDate",endDate);

        List<SharesModel> models = sharesHistoryDao.selectWithDate(paramMap);

        return models;

    }

    private String getNowDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        return df.format(new Date());
    }

}
