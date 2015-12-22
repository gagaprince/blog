package com.prince.myproj.blog.services;

import com.prince.myproj.blog.dao.PhotoDao;
import com.prince.myproj.blog.dao.PhotoFolderDao;
import com.prince.myproj.blog.models.PhotoFolderModel;
import com.prince.myproj.blog.models.PhotoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2015/12/22.
 */
@Service
public class PhotoService {

    @Autowired
    private PhotoFolderDao photoFolderDao;
    @Autowired
    private PhotoDao photoDao;

    public List<PhotoFolderModel> giveMePhotoFolders(int pno,int psize){
        int begin = pno * psize;
        int length = psize;
        Map<String,Object> limitMap = new HashMap<String, Object>();
        limitMap.put("fromIndex",begin);
        limitMap.put("toIndex",length);
        return photoFolderDao.getPhotoFolders(limitMap);
    }

    public int giveMeAllcountPhotoFolder(){
        return photoFolderDao.getAllCount();
    }

    public List<PhotoModel> giveMePhotosByFolder(int pno,int psize,int folderId){
        int begin = pno * psize;
        int length = psize;
        Map<String,Object> limitMap = new HashMap<String, Object>();
        limitMap.put("fromIndex",begin);
        limitMap.put("toIndex",length);
        limitMap.put("photoFolderId",folderId);
        return photoDao.getPhotoList(limitMap);
    }

    public int giveAllPhotoByFolder(int folderId){
        Map<String,Object> limitMap = new HashMap<String, Object>();
        limitMap.put("photoFolderId",folderId);
        return photoDao.getAllCount(limitMap);
    }


}
