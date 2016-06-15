package com.prince.myproj.shares.services;

import com.prince.myproj.shares.dao.SharesDao;
import com.prince.myproj.shares.dao.SharesHistoryDao;
import com.prince.myproj.shares.dao.SharesTempDao;
import com.prince.myproj.shares.mathutils.ShareFun;
import com.prince.myproj.shares.models.ShareConfig;
import com.prince.myproj.shares.models.SharesModel;
import com.prince.myproj.shares.models.SharesSingleModel;
import com.prince.myproj.util.DateUtil;
import com.prince.myproj.util.MailService;
import com.prince.myproj.util.bean.Mail;
import com.prince.util.httputil.HttpUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.io.*;
import java.text.ParseException;
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
    @Autowired
    private SharesTempDao sharesTempDao;
    @Autowired
    private ShareFun shareFun;

    @Autowired
    private DateUtil dateUtil;


    //一键下载缺失的数据
    public void downloadTable(){
        long start = 0;
        long end = 3000;


        String dateStart = giveMeLastDate().replaceAll("-", "");

        String dateEnd = dateUtil.getNowDate();
        logger.info("dateStart:"+dateStart);
        logger.info("dateEnd:" + dateEnd);
        updateTodayHistory(start,end,dateStart,dateEnd);
    }

    public String giveMeLastDate(){
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code","sh000001");
        SharesModel lastModel = sharesHistoryDao.selectLastModel(paramMap);
        String dateStart = "19901219";
        if(lastModel!=null){
            dateStart = lastModel.getDate();
        }
        return dateStart;
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
        List<SharesSingleModel> sharesModels = getSharesModels(start, end);

        int size = sharesModels.size();
        for(int i=0;i<size;i++){
            SharesSingleModel shareModel = sharesModels.get(i);
            if(shareModel.getName().contains("*")){
                downloadOneTable(shareModel);
            }
        }
    }

    //获取要操作的股票代码
    private List<SharesSingleModel> getSharesModels(String codes){
        HashMap<String,Object> paramMap = new HashMap<String, Object>();
        List<String> codeList = new ArrayList<String>();
        String[] codeItems = codes.split(",");
        for(int i=0;i<codeItems.length;i++){
            codeList.add(codeItems[i]);
        }
        paramMap.put("codes", codeList);
        logger.info(codes);
        List<SharesSingleModel> sharesModels = sharesDao.getSharesIncodes(paramMap);
        logger.info(sharesModels.size());
        return sharesModels;
    }
    //获取要操作的股票代码
    public List<SharesSingleModel> getSharesModels(long start,long end){
        HashMap<String,Object> paramMap = new HashMap<String, Object>();

        paramMap.put("fromIndex", start);
        paramMap.put("toIndex", end - start);

        List<SharesSingleModel> sharesModels = sharesDao.getShares(paramMap);
        return sharesModels;
    }
    //获取code和name的对应关系
    public Map<String,String> getCodeNameMap(){
        List<SharesSingleModel> singleModels = getSharesModels(0,3000);
        Map<String,String> codeMap = new HashMap<String, String>();
        int size = singleModels.size();
        for(int i=0;i<size;i++){
            SharesSingleModel singleModel = singleModels.get(i);
            codeMap.put(singleModel.getCodeAll(),singleModel.getName());
        }
        return codeMap;
    }

    public List<SharesSingleModel> getSharesModelsWithOutSC(long start,long end){
        List<SharesSingleModel> sharesModels = getSharesModels(start,end);
        int size = sharesModels.size();
        for(int i=0;i<size;i++){
            String code = sharesModels.get(i).getCodeAll();
            if (code.startsWith("sz30")){
                sharesModels.remove(i);
                i--;
                size--;
            }
        }
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
        httpUtil.saveImgByUrl(csvUrl, path);


    }


    public void saveTableInDB(long start,long end){
        //将csv文件中的数据保存到数据库中
        List<SharesSingleModel> sharesModels = getSharesModels(start, end);
        int size = sharesModels.size();
        for(int i=0;i<size;i++){
            saveOneTableInDB(sharesModels.get(i));
        }

    }
    //将一条数据放入数据库
    private void saveOneTableInDB(SharesSingleModel model){
        String path = config.getShareTablePath()+model.getCodeAll()+"_"+model.getName().replace("*","x")+".csv";
        File f = new File(path);
        saveOneTableInDBWithFile(model, f, 0);
    }

    //将文件分析后  放入数据库
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


                            if(!isExitHistory(sharesModel,"history")){
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
    //判断是否已经存在
    private boolean isExitHistory(SharesModel sharesModel,String type){
        Map<String,Object> keyMap = new HashMap<String, Object>();
        keyMap.put("code", sharesModel.getCode());

        List<SharesModel> exitList=null;
        if("history".equals(type)){
            keyMap.put("date", sharesModel.getDate());
            exitList = sharesHistoryDao.selectByMap(keyMap);
        }else{
            exitList = sharesTempDao.selectByMap(keyMap);
        }

        if(exitList!=null && exitList.size()==0){
            return false;
        }
        return true;
    }


    //更新今天的数据 主要用于更新均线数据
    /*public void updateTodayHistory(long start,long end){
        List<SharesSingleModel> sharesModels = getSharesModels(start, end);
        int size = sharesModels.size();
        for(int i=0;i<size;i++){
            List<SharesSingleModel> updateModels = new ArrayList<SharesSingleModel>();
            for(int j=0;j<10&&i+j<size;j++){
                updateModels.add(sharesModels.get(i+j));
            }
            updateOneTodayHistory(updateModels);
        }
    }*/

    public void updateTodayHistory(String codes ,String dateStart,String dateEnd){
        List<SharesSingleModel> sharesModels = getSharesModels(codes);
        updateOneTodayHistory(sharesModels, dateStart, dateEnd);
    }

    public void updateTodayHistory(long start,long end,String dateStart,String dateEnd){
        List<SharesSingleModel> sharesModels = getSharesModels(start, end);
        updateOneTodayHistory(sharesModels, dateStart, dateEnd);
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
            saveOneTableInDBWithFile(model, f, 2);

        }


    }

    /**
     * 计算平滑移动平均值 收盘价 均线
     */

    public void cacularMean(){
        long start = 0;
        long end = 3000;

        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", "sh000001");
        SharesModel sharesModel =sharesHistoryDao.selectLastMeanModel(paramMap);
        String startDate = getDateByMinus(sharesModel.getDate(), -30);
        String endDate = dateUtil.getNowDate("yyyy-MM-dd");

        logger.info("startDate:"+startDate);
        logger.info("endDate:" + endDate);
        cacularMean(start, end, startDate, endDate);

    }

    public void cacularMean(long start,long end ,String startDate,String endDate){
        List<SharesSingleModel> sharesModels = getSharesModels(start, end);

        int size = sharesModels.size();
        for(int i=0;i<size;i++){
            SharesSingleModel shareModel = sharesModels.get(i);
            cacularMeanOneModel(shareModel, startDate, endDate);
        }
    }

    private void cacularMeanOneModel(SharesSingleModel model,String startDate,String endDate){
        List<SharesModel> models = getModelsByStartEndDate(model, startDate, endDate);
        cacularAndSaveMean(models, 6);
        cacularAndSaveMean(models, 21);
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

//        saveModelList(models);

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



    //拿出将要计算的model 非停牌的数据
    private List<SharesModel> getModelsByStartEndDate(SharesSingleModel sharesSingleModel,String startDate,String endDate){
        return getModelsByStartEndDate(sharesSingleModel.getCodeAll(),startDate,endDate);
    }

    //获取对应code对应date的数据
    public SharesModel getModelByCodeDate(String code,String date){
        List<SharesModel> sharesModels = getModelsByStartEndDate(code,date,date);
        if(sharesModels.size()>0){
            return sharesModels.get(0);
        }
        return null;
    }

    //拿出将要计算的model 非停牌的数据
    private List<SharesModel> getModelsByStartEndDate(String code,String startDate,String endDate){
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("codeAll", code);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate",endDate);
        paramMap.put("volume","1");

        List<SharesModel> models = sharesHistoryDao.selectWithDate(paramMap);

        return models;

    }

    private String getDateByMinus(String date,long day){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = null;
        try {
            now = sdf.parse(date);
            now.setTime(now.getTime() +day*24*3600*1000);
            return sdf.format(now);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 计算cyc 和 cys 指导支撑和压力
     * 保存数据库
     */

    public void cacularCycHistory(){
        //从数据库中拿出一只股票的历史数据 除去停牌的数据
        List<SharesSingleModel> sharesModels = getSharesModels(0, 3000);
        for(int i=0;i<sharesModels.size();i++){
            cacularOneCycHistory(sharesModels.get(i));
        }
    }

    public void cacularCycLastDay(){
        List<SharesSingleModel> sharesModels = getSharesModels(0, 3000);
        for(int i=0;i<sharesModels.size();i++){
            cacularOneCycLastDay(sharesModels.get(i));
        }
    }

    public void modifyCacularCycHistory(){
        List<SharesSingleModel> sharesModels = getSharesModels(0, 3000);
        for(int i=0;i<sharesModels.size();i++){
            modifyCacularOneCysHistory(sharesModels.get(i));
        }
    }

    public void cacularOneCycHistory(SharesSingleModel sharesModel){
        String code = sharesModel.getCodeAll();
        if(!code.equals("sh000001")&&!code.equals("sz399001")&&!code.equals("sz399006")){
            List<SharesModel> cacuList = getModelsByStartEndDate(sharesModel, null, null);

            //计算5日cyc cys
            cacularOneCycHistoryByDay(cacuList, 5);
            //计算13日cyc cys
            cacularOneCycHistoryByDay(cacuList, 13);
            //计算34日cyc cys
            cacularOneCycHistoryByDay(cacuList, 34);

            saveModelList(cacuList);
        }

    }

    public void modifyCacularOneCysHistory(SharesSingleModel sharesModel){
        String code = sharesModel.getCodeAll();
        if(!code.equals("sh000001")&&!code.equals("sz399001")&&!code.equals("sz399006")){
            List<SharesModel> cacuList = getModelsByStartEndDate(sharesModel, null, null);
            //计算5日cyc cys
            modifyCycWithQuanByDay(cacuList, 5);
            //计算13日cyc cys
            modifyCycWithQuanByDay(cacuList, 13);
            //计算34日cyc cys
            modifyCycWithQuanByDay(cacuList, 34);
        }
    }

    public void cacularOneCycLastDay(SharesSingleModel sharesModel){
        String code = sharesModel.getCodeAll();
        if(!code.equals("sh000001")&&!code.equals("sz399001")&&!code.equals("sz399006")) {
            List<SharesModel> cacuList = getModelsByStartEndDate(sharesModel, null, null);

            int index = cacuList.size() - 1;
            //计算5日cyc cys
            cacularOneCycHistoryOneDay(cacuList, 5, index);
            //计算13日cyc cys
            cacularOneCycHistoryOneDay(cacuList, 13, index);
            //计算34日cyc cys
            cacularOneCycHistoryOneDay(cacuList, 34, index);

            saveModelOne(cacuList.get(index));
        }
    }

    private void saveModelOne(SharesModel model){
        try{
            sharesHistoryDao.updateMeans(model);
        }catch (Exception e){
            e.printStackTrace();
        }
//        sharesHistoryDao.updateMeans(model);
    }

    private void saveModelList(List<SharesModel> cacuList){
        for(int i=0;i<cacuList.size();i++){
            SharesModel model = cacuList.get(i);
            logger.info("update  id:" + model.getId() + " code:" + model.getCode()
                            + " 6daysmean:" + model.getSixMean() + " 21daysmean:" + model.getTweentyMean()
                            + " cyc5:" + model.getCyc5()
                            + " cyc13:" + model.getCyc13()
                            + " cyc34:" + model.getCyc34()
                            + " cys5:" + model.getCys5()
                            + " cys13:" + model.getCys13()
                            + " cys34:" + model.getCys34()
            );
            try{
                sharesHistoryDao.updateMeans(model);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    private void cacularOneCycHistoryByDay(List<SharesModel> cacuList ,int day){
        for(int i=day;i<cacuList.size();i++){
            cacularOneCycHistoryOneDay(cacuList, day, i);
        }
    }
    //cyc的计算没有除权 这个方法将没有除权的结果修正
    public void modifyCycWithQuanByDay(List<SharesModel> cacuList ,int day){
        for(int i=day;i<cacuList.size()-1;i++){
            SharesModel yestoday = cacuList.get(i);
            SharesModel today = cacuList.get(i+1);

            float yestoryClose = yestoday.getClose();
            float todayOpen = today.getOpen();
            if(todayOpen<yestoryClose*0.9-0.5){
                int begin = day;
                int end = cacuList.size();
                if(i+1>day){
                    begin = i+1;
                }
                if(i+day<end){
                    end = i+day;
                }
                for(int j=begin;j<end;j++){
                    SharesModel model = cacularOneCycHistoryOneDay(cacuList,day,j);
                    saveModelOne(model);
                }
            }


        }
    }

    private SharesModel cacularOneCycHistoryOneDay(List<SharesModel> cacuList,int day,int index){
        List<Float> volumList = splitShareList(cacuList,day,index,"volume");
        List<Float> volumValList = splitShareList(cacuList, day, index, "volumeVal");
        SharesModel model = cacuList.get(index);
        if(volumList!=null && volumValList!=null){
            float emaVolum = shareFun.ema(volumList, 2 / day);
            float emaVolumVal = shareFun.ema(volumValList,2/day);
            float cyc = emaVolumVal/emaVolum;
            float currentPrice = model.getClose();
            float cys = (currentPrice/cyc-1)*100;
            logger.info("日期："+model.getDate());
            logger.info("成交量滑动平均值："+emaVolum);
            logger.info("成交金额滑动平均值：" + emaVolumVal);
            logger.info(day+"日平均成本："+cyc);
            logger.info(day+"日当前价： "+ currentPrice);
            logger.info(day+"日cys： "+ cys);
            logger.info("********************************************");
            model.setCycByDay(day, cyc);
            model.setCysByDay(day, cys);
        }
        return model;
    }



    //获取一个size长度的 量的list 末尾下标为index
    private List<Float> splitShareList(List<SharesModel> modelList,int size,int index,String type){
        if(index<size-1)return null;
        float quan = 1;
        int quanindex = 0;
        if("volume".equals(type)){
            //若果是获取量的 则要除权 先确认高增转的这一天的下标
            for(int i=index-size+1;i<index;i++){
                SharesModel yestoday = modelList.get(i);
                SharesModel today = modelList.get(i+1);
                float yestodayClose = yestoday.getClose();
                float todayOpen = today.getOpen();
                if(todayOpen<yestodayClose*0.9-0.5){
                    //第二日开盘价低于第一日收盘价的跌停板 说明高增转
                    quan = yestodayClose/todayOpen;
                    quanindex = i+i;
                    break;
                }
            }
        }

        List<Float> valList = new ArrayList<Float>();
        for(int i=index-size+1;i<index+1;i++){
            SharesModel model = modelList.get(i);
            if("volume".equals(type)){
                if(i<quanindex){
                    valList.add(model.getVolume()*quan);
                }else{
                    valList.add(model.getVolume());
                }

            }else{
                valList.add(model.getVolumeVal());
            }
        }
        return valList;
    }


    /**
     * 收盘前获取数据存入临时表 计算cyc cys 发送策略邮件
     */

    public void downloadTablePre(){
        //获取股票代码
        long start = 0;
        long end = 3000;
        List<SharesSingleModel> sharesModels = getSharesModels(start, end);

        int size = sharesModels.size();
        int len = 100;
        for(int i=0;i<size;){
            List<SharesSingleModel> updateModels = new ArrayList<SharesSingleModel>();
            for(int j=0;j<len&&i+j<size;j++){
                updateModels.add(sharesModels.get(i+j));
            }
            updateTodayRealTime(updateModels);
            i = i+len;
        }
    }

    public void cacularCycLastDayPre(){
        Map<String ,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("volume",1);
        List<SharesModel> models = sharesTempDao.selectByMap(paramMap);
        int size = models.size();
        for(int i=0;i<size;i++){
            cacularOneCycPre(models.get(i));
        }
    }

    private void cacularOneCycPre(SharesModel sharesModel){
        String code = sharesModel.getCode();
        if(!code.equals("sh000001")&&!code.equals("sz399001")&&!code.equals("sz399006")) {
            List<SharesModel> cacuList = getModelsByStartEndDate(code, "1990-01-01", sharesModel.getDate());
            SharesModel lastDay = cacuList.get(cacuList.size() - 1);
            cacuList.add(sharesModel);
            int index = cacuList.size() - 1;
            //计算5日cyc cys
            cacularOneCycHistoryOneDay(cacuList, 5, index);
            //计算13日cyc cys
            cacularOneCycHistoryOneDay(cacuList, 13, index);
            //计算34日cyc cys
            cacularOneCycHistoryOneDay(cacuList, 34, index);

            saveTempModelOne(cacuList.get(index));
        }
    }

    private void saveTempModelOne(SharesModel model){
        try{
            sharesTempDao.update(model);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //获取实时数据存入临时表
    private void updateTodayRealTime(List<SharesSingleModel> models){
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
//        logger.info(todayContent);

        parseTodayData(todayContent, models);

    }
    //分析并存单条数据
    private void parseTodayData(String todayContent,List<SharesSingleModel> models){
        String[] codeContents = todayContent.split(";");
        int length = codeContents.length;
        int size = models.size();
        logger.info("length:"+length+"    size:"+size);
        for(int i=0;i<length && i<size;i++){
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
                sharesModel.setVolumeVal(Float.parseFloat(fields[9]));
                sharesModel.setDate(fields[30]);
                if(!isExitHistory(sharesModel,"temp")){
                    logger.info("save:"+sharesModel.getCode());
                    sharesTempDao.save(sharesModel);
                }else{
                    logger.info("update:"+sharesModel.getCode());
                    sharesTempDao.update(sharesModel);
                }
            }

        }
    }
}
