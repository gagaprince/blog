package com.prince.myproj.shares.services;

import com.prince.myproj.shares.dao.SharesDao;
import com.prince.myproj.shares.dao.SharesHistoryDao;
import com.prince.myproj.shares.models.ShareConfig;
import com.prince.myproj.shares.models.SharesModel;
import com.prince.myproj.shares.models.SharesSingleModel;
import com.prince.myproj.util.MailService;
import com.prince.myproj.util.bean.Mail;
import com.prince.util.httputil.HttpUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public void downloadTable(){
        long start = 0;
        long end = 3000;
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code","sh000001");
        SharesModel lastModel = sharesHistoryDao.selectLastModel(paramMap);
        String dateStart = lastModel.getDate().replaceAll("-","");
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
        paramMap.put("codes",codeList);
        logger.info(codes);
        List<SharesSingleModel> sharesModels = sharesDao.getSharesIncodes(paramMap);
        logger.info(sharesModels.size());
        return sharesModels;
    }
    //获取要操作的股票代码
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
    //将一条数据放入数据库
    private void saveOneTableInDB(SharesSingleModel model){
        String path = config.getShareTablePath()+model.getCodeAll()+"_"+model.getName().replace("*","x")+".csv";
        File f = new File(path);
        saveOneTableInDBWithFile(model,f,0);
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
    //判断是否已经存在
    private boolean isExitHistory(SharesModel sharesModel){
        Map<String,Object> keyMap = new HashMap<String, Object>();
        keyMap.put("code",sharesModel.getCode());
        keyMap.put("date", sharesModel.getDate());
        List<SharesModel> exitList = sharesHistoryDao.selectByMap(keyMap);
        if(exitList.size()==0){
            return false;
        }
        return true;
    }


    //更新今天的数据 主要用于更新均线数据
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

    public void cacularMean(){
        long start = 0;
        long end = 3000;

        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code","sh000001");
        SharesModel sharesModel =sharesHistoryDao.selectLastMeanModel(paramMap);
        String startDate = getDateByMinus(sharesModel.getDate(),-30);
        String endDate = getNowDate("yyyy-MM-dd");

        logger.info("startDate:"+startDate);
        logger.info("endDate:" + endDate);
        cacularMean(start,end,startDate,endDate);

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
        return getNowDate("yyyyMMdd");
    }

    private String getNowDate(String fomateStr){
        SimpleDateFormat df = new SimpleDateFormat(fomateStr);//设置日期格式
        return df.format(new Date());
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
    //找出超过备选涨幅的列表
    public List<SharesModel> findIncreaseHigherList(float per){
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("high",per);
        paramMap.put("date", getNowDate("yyyy-MM-dd"));
        return sharesHistoryDao.selectWithHighLow(paramMap);
    }

    public List<SharesModel> findIncreaseLowerList(float per){
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("low",per);
        paramMap.put("date",getNowDate("yyyy-MM-dd"));
        return sharesHistoryDao.selectWithHighLow(paramMap);
    }


    //发送分析邮件
    public void sendMail(){
        Mail mail = new Mail();
        mail.setSubject(getSubject());
        mail.setContent(getMailContent());
        mail.setFrom(config.getFromUser());
        mail.setTo(config.getToUser());
        mail.setSmtp(config.getStmp());
        mail.setUsername(config.getMailUserName());
        mail.setPassword(config.getMailPassword());
        MailService.send(mail);
    }

    private String getSubject(){
        String dateStr = getNowDate();
        return dateStr+"股票市场分析";
    }

    private String getMailContent(){
        StringBuffer sb = new StringBuffer("<p>");

        sb.append(getSHdata()).append("<br>");

        return sb.toString();
    }

    private String getHigherModelsHtml(){
        List<SharesModel> liseModels = findIncreaseHigherList(8);
        return parseModerListToHtml(liseModels);
    }

    private String getLowerModelsHtml(){
        List<SharesModel> liseModels = findIncreaseLowerList(-8);
        return parseModerListToHtml(liseModels);
    }

    //获取股票list转成的html
    private String parseModerListToHtml(List<SharesModel> listModels){
        StringBuffer sb = new StringBuffer("");

        sb.append("<table>")
                .append("<thead>")

                .append("<tr>")
                .append("<th>股票代码<th>")
                .append("<th>股票名称<th>")
                .append("<th>涨幅<th>")
                .append("</tr>")
                .append("</thead>")
                .append("<tbody>")
                .append("</tbody>")
                .append("</table>");

        return sb.toString();
    }

    //获取大盘综合信息
    private String getSHdata(){
        StringBuffer sb = new StringBuffer("<p>");

        //获取最新大盘交易日的数据
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code","sh000001");
        SharesModel sharesModel = sharesHistoryDao.selectLastModel(paramMap);
        sb.append("今日大盘<br>开盘：").append(sharesModel.getOpen()).append("<br>")
                .append("收盘：").append(sharesModel.getClose()).append("<br>")
                .append("最高：").append(sharesModel.getHigh()).append("<br> ")
                .append("最低：").append(sharesModel.getLow()).append("<br>")
                .append("涨幅：").append(sharesModel.getIncreasePer()).append("<br>")
                .append("涨幅值：").append(sharesModel.getIncreaseVal()).append("<br>")
                .append("6日均线：").append(sharesModel.getSixMean()).append("<br>")
                .append("21日均线：").append(sharesModel.getTweentyMean()).append("<br>")
                .append("日期：").append(sharesModel.getDate()).append("<br></p>");


        return sb.toString();
    }

}
