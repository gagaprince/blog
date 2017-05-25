package com.prince.myproj.platform.photo.controllers;

import com.prince.myproj.platform.common.models.AjaxModel;
import com.prince.myproj.platform.common.statics.ErrorCode;
import com.prince.myproj.util.StringUtil;
import com.prince.util.httputil.HttpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zidong.wang on 2017/5/25.
 */
@Controller
@RequestMapping("/blog/pl/mm")
public class Base64Trans {
    private static final Logger logger =  Logger.getLogger(Base64Trans.class);



    @RequestMapping(value = "/base64LoadTrans")
    @ResponseBody
    public AjaxModel base64LoadTrans(HttpServletRequest request){
        String url = StringUtil.parseStringFromRequest(request,"photoUrl","");


        AjaxModel ajaxModel = loadPhotoByUrl(url);


        return ajaxModel;

    }

    private AjaxModel loadPhotoByUrl(String url){
        AjaxModel ajaxModel = new AjaxModel();


        HttpUtil httpUtil = HttpUtil.getInstance();
        String fileUrl = url.substring(url.lastIndexOf("/")+1);
        int index = url.lastIndexOf(".");
        String ext = "jpg";
        if(index!=-1){
            ext = url.substring(index+1);
        }else{
            fileUrl+="."+ext;
        }

//        logger.info(ext);

        String path = Base64Trans.class.getResource("/").getPath()+"../../src/main/webapp/blog/tempPhoto/";
        String filePath = path+fileUrl;
//        logger.info(filePath);



        try {
            httpUtil.saveImgByUrl(url,filePath);
            String httpUrl = "/blog/tempPhoto/"+fileUrl;

            Map<String,String> map=new HashMap<String, String>();
            map.put("ext",ext);
            map.put("url",httpUrl);
            ajaxModel.setStatus(ErrorCode.SUCCESS);
            ajaxModel.setData(map);

        }catch (Exception e){
            ajaxModel.setStatus(ErrorCode.NOT_LOAD_PHOTO);
            e.printStackTrace();
        }




        return ajaxModel;
    }
}
