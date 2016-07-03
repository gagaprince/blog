package com.prince.myproj.blog.spiders.spiderServices;

import com.prince.myproj.blog.spiders.spiderModel.TravelPicModel;
import com.prince.util.fileutil.FileUtil;
import com.prince.util.httputil.HttpUtil;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gagaprince on 16-7-3.
 */
@Service
public class TravelPicService {
    public static final Logger logger = Logger.getLogger(TravelPicService.class);
    private HttpUtil httpUtil = HttpUtil.getInstance();

    public String getTravelContent(String url){
        String content = httpUtil.getContentByUrl(url,"utf-8");
        return content;
    }

    public void analysis(){
        String url="https://lvyou.baidu.com/scene/a-guonei_l-chengshi";
        List<TravelPicModel> allTravelModes = new ArrayList<TravelPicModel>();
        for(int i=0;i<29;i++){
            String reUrl = url+"?rn=12&pn="+i*12;
            String content = getTravelContent(reUrl);
            List<TravelPicModel> travelPicModels = parseTravelPicList(content);
            allTravelModes.addAll(travelPicModels);
        }
        loadTravelPic(allTravelModes);
    }
    public List<TravelPicModel> parseTravelPicList(String content){
        List<TravelPicModel> travelPicModels = new ArrayList<TravelPicModel>();
        Document doc = Jsoup.parse(content);
        Element imgbody = doc.getElementById("body");
        Elements imgLis = imgbody.getElementsByClass("filter-result-item");

        logger.info(imgLis.size());

        for(int i=0;i<imgLis.size();i++){
            Element imgli = imgLis.get(i);
            Element cityA = imgli.getElementsByClass("nslog").get(0);
            String city = cityA.attr("title");
            Element descP = imgli.getElementsByClass("filter-result-abstract").get(0);
            String desc = descP.text();
            Element imgE = imgli.getElementsByClass("img-wrap").get(0).getElementsByTag("img").get(0);
            String src = "http:"+imgE.attr("src");
            logger.info(city+"---"+desc+"---"+src);
            TravelPicModel travelPicModel = new TravelPicModel();
            travelPicModel.setCity(city);
            travelPicModel.setDesc(desc);
            travelPicModel.setSrc(src);
            travelPicModels.add(travelPicModel);

        }

        return travelPicModels;
    }
    public void loadTravelPic(List<TravelPicModel> travelPicModels){
        String textPath = "/Users/gagaprince/work/travelFiles/travel.txt";
        String filePath = "/Users/gagaprince/work/travelFiles/imgs/";
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<travelPicModels.size();i++){
            TravelPicModel travelPicModel = travelPicModels.get(i);
            String city = travelPicModel.getCity();
            String src = travelPicModel.getSrc();
            String desc = travelPicModel.getDesc();
            String path = filePath+city+".jpg";
            httpUtil.saveImgByUrl(src,path);
            sb.append(city+"   "+desc+"\n");
        }
        File desFile = new File(textPath);
        try {
            FileWriter fr = new FileWriter(desFile);
            fr.write(sb.toString());
            fr.flush();
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
