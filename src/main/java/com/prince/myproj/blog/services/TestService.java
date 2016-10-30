package com.prince.myproj.blog.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.prince.myproj.blog.models.DataModel;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zidong.wang on 2016/8/12.
 */
@Service
public class TestService {
    public static final Logger logger = Logger.getLogger(TestService.class);

    public List<DataModel> indexModelList = new ArrayList<DataModel>();
    public List<DataModel> rankModelList = new ArrayList<DataModel>();

    public void ana(){
        String path = "D:\\work\\data\\detail_20160811.txt";
        File f = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = null;
            while ((line=br.readLine())!=null){
                if(line.contains("js-index-transferCard_pv")){
                    String[] lines = line.split("\\s+");
                    for(int i=0;i<lines.length;i++){
                        String lineItem = lines[i];
                        if(lineItem.contains("FUNPARAMS")){
                            JSONObject dataJson = JSON.parseObject(lineItem);
                            JSONObject detailJson = JSON.parseObject(dataJson.getString("FUNPARAMS"));
                            String uid = detailJson.getString("uid");
                            String nfrom = detailJson.getString("nfrom");
//                            logger.info("uid:"+uid);
//                            logger.info("nfrom:"+nfrom);
                            if(nfrom.equals("179")){
                                indexModelList.add(new DataModel(uid,nfrom,"index"));
                            }
                        }
                    }
                }else if(line.contains("car-rank")){
                    String[] lines = line.split("\\s+");
                    for(int i=0;i<lines.length;i++){
                        String lineItem = lines[i];
                        if(lineItem.contains("FUNPARAMS")){
                            JSONObject dataJson = JSON.parseObject(lineItem);
                            JSONObject detailJson = JSON.parseObject(dataJson.getString("FUNPARAMS"));
                            String uid = detailJson.getString("uid");
                            String nfrom = detailJson.getString("from");
                            String actionName = detailJson.getString("actionName");
//                            logger.info("uid:"+uid);
//                            logger.info("nfrom:"+nfrom);
                            if(nfrom.equals("179")&&actionName.equals("view")){
                                rankModelList.add(new DataModel(uid,nfrom,"rank"));
                            }
                        }
                    }
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set<String> index = getUV(indexModelList);
        Set<String> rank = getUV(rankModelList);
        logger.info("179 index pv:"+indexModelList.size());
        logger.info("179 index uv:" + index.size());
        logger.info("179 rank pv:"+rankModelList.size());
        logger.info("179 rank uv:"+rank.size());
        diffUV(index,rank);
    }

    public Set<String> getUV(List<DataModel> dataModels){
        Set<String> tjSet = new HashSet<String>();
        for(int i=0;i<dataModels.size();i++)
        {
            DataModel dataModel = dataModels.get(i);
            tjSet.add(dataModel.getUid());
        }
        return  tjSet;
    }

    public void diffUV(Set<String> index,Set<String> rank){
        List<String> diffUid = new ArrayList<String>();
        Iterator<String> it = rank.iterator();
        while(it.hasNext()){
            String uid = it.next();
            if(!index.contains(uid)){
                diffUid.add(uid);
            }
        }

        for(int i=0;i<diffUid.size();i++){
            logger.info(diffUid.get(i));
        }
    }

    public void anaNode(){
        String path = "D:\\work\\data\\INFO.20160817.log";
        File f = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = null;
            Set<String> uidSet = new HashSet<String>();
            int num = 0;
            while ((line=br.readLine())!=null){
                if(line.contains("\"nfrom\":\"25\"")){
                    Pattern pattern = Pattern.compile("\"uid\":\"(.*?)\"");
                    Matcher matcher = pattern.matcher(line);
                    if(matcher.find()){
                        String uid = matcher.group(1);
                        logger.info(uid);
                        uidSet.add(uid);
                    }
                    num++;
                }
            }
            logger.info("simple uv:"+uidSet.size());
            logger.info("simple pv:"+num);
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        TestService t = new TestService();
        t.anaNode();
    }

}
