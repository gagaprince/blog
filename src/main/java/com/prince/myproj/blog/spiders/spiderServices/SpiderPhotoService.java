package com.prince.myproj.blog.spiders.spiderServices;

import com.prince.myproj.blog.dao.PhotoDao;
import com.prince.myproj.blog.dao.PhotoFolderDao;
import com.prince.myproj.blog.models.PhotoFolderModel;
import com.prince.myproj.blog.models.PhotoModel;
import com.prince.myproj.blog.spiders.spiderModel.FolderModel;
import com.prince.util.httputil.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gagaprince on 15-12-27.
 */
@Service
public class SpiderPhotoService {
    public static final Logger logger = Logger.getLogger(SpiderPhotoService.class);

    @Autowired
    private PhotoFolderDao folderDao;
    @Autowired
    private PhotoDao photoDao;

    private String OSS_BUCKET_NAME="gagablog";

    public void saveFolderModels(List<FolderModel> folderModels){
        int size = folderModels.size();
        for (int i=0;i<size;i++){
            FolderModel folderModel = folderModels.get(i);
            saveFolderModel(folderModel);
        }
    }

    private void saveFolderModel(FolderModel folderModel){
        PhotoFolderModel photoFolderModel = new PhotoFolderModel();
        String cover = folderModel.getCover();
        photoFolderModel.setCover(cover);
        photoFolderModel.setCreateTime(new Date());
        String title = folderModel.getTitle();
        photoFolderModel.setName(title);

        folderDao.save(photoFolderModel);

        folderModel.setPhotoFolderId(photoFolderModel.getId());

        List<String> jpgs = folderModel.getJpgs();
        int size = jpgs.size();
        for (int i=0;i<size;i++){
            String jpg = jpgs.get(i);
            PhotoModel photoModel = new PhotoModel();
            photoModel.setCreateTime(new Date());
            photoModel.setName(title);
            photoModel.setPhotoFolderId(photoFolderModel.getId());
            photoModel.setPicUrl(jpg);
            photoDao.save(photoModel);
        }

    }

    public void uploadFolder(){
        int allCount = folderDao.getAllCount();
        Map<String,Object> limitMap = new HashMap<String, Object>();
        limitMap.put("fromIndex", 0);
        limitMap.put("toIndex", allCount);
        List<PhotoFolderModel> photoFolderModels = folderDao.getPhotoFolders(limitMap);

        int size = photoFolderModels.size();
        for(int i=0;i<size;i++){
            PhotoFolderModel photoFolderModel = photoFolderModels.get(i);
            String cover = photoFolderModel.getCover();
            if (cover==null || cover.startsWith("http://gagablog"))continue;
            logger.info(cover);
            String newUrl = upLoadFileToOSS(cover);
            photoFolderModel.setCover(newUrl);
            logger.info(newUrl);
            logger.info(photoFolderModel.getId());
            folderDao.update(photoFolderModel);
        }
    }

    public void uploadPhotos(){

        int allCount = photoDao.getAllCount(new HashMap<String, Object>());
        Map<String,Object> limitMap = new HashMap<String, Object>();
        limitMap.put("fromIndex", 0);
        limitMap.put("toIndex", allCount);
        List<PhotoModel> photoModels = photoDao.getPhotoList(limitMap);

        int size = photoModels.size();
        for(int i=0;i<size;i++){
            PhotoModel photoModel = photoModels.get(i);
            String picUrl = photoModel.getPicUrl();
            if(picUrl.startsWith("http://gagablog"))continue;
            if(picUrl.startsWith("http://www.xiaohuar.com../..")){
                picUrl = picUrl.replace("http://www.xiaohuar.com../..","http://www.xiaohuar.com");
                logger.info(picUrl);
//                continue;
            }
            String newUrl = upLoadFileToOSS(picUrl);
            photoModel.setPicUrl(newUrl);
            logger.info(newUrl);
            logger.info(photoModel.getId());
            photoDao.update(photoModel);
        }
    }

    private String upLoadFileToOSS(String url){
        try{
            HttpUtil httpUtil = HttpUtil.getInstance();
            String selfUrl = httpUtil.uploadImgToOss(url,OSS_BUCKET_NAME,"photo");
            return selfUrl;
        }catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }
}
