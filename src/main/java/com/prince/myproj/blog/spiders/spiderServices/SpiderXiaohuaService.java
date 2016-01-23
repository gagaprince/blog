package com.prince.myproj.blog.spiders.spiderServices;

import com.prince.myproj.blog.spiders.spiderModel.FolderModel;
import com.prince.myproj.blog.spiders.spiderModel.XiaohuaModel;
import com.prince.util.httputil.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gagaprince on 15-12-26.
 */
@Service
public class SpiderXiaohuaService {
    public static final Logger logger = Logger.getLogger(SpiderXiaohuaService.class);


    @Autowired
    private AnalysisService analysisService;
    @Autowired
    private SpiderPhotoService spiderPhotoService;

    private HttpUtil httpUtil = HttpUtil.getInstance();

    public void startSpider(){
        int pno=0;
        while (spiderXiaoHuaByPage(pno)){
            pno++;
        }
    }

    private boolean spiderXiaoHuaByPage(int pno){
        String content = giveMeXiaoHuaContentByPage(pno);

        XiaohuaModel xiaohuaModel = analysisService.analysisXiaoHuaIndex(content,"");

        List<FolderModel> folderModels = xiaohuaModel.getFolderModels();
        int size = folderModels.size();
        for(int i=0;i<size;i++){
            FolderModel folderModel = folderModels.get(i);
            String detailUrl = folderModel.getDetailUrl();
            logger.info(detailUrl);
            String detailContent = httpUtil.getContentByUrl(detailUrl);
            analysisService.analysisXiaoHuaDetail(detailContent, folderModel);
        }

        logger.info(xiaohuaModel.toString());

        spiderPhotoService.saveFolderModels(xiaohuaModel.getFolderModels());

        return !xiaohuaModel.isLast();
    }

    private String giveMeXiaoHuaContentByPage(int pno){
        String pageUrl = "http://www.xiaohuar.com/list-1-"+pno+".html";
        return httpUtil.getContentByUrl(pageUrl);
    }

    public void uploadJpg(){
        spiderPhotoService.uploadFolder();
        spiderPhotoService.uploadPhotos();
    }

}
