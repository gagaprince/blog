package com.prince.myproj.blog.dao;

import com.prince.myproj.blog.models.PhotoModel;

import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2015/12/22.
 */
public interface PhotoDao {
    public void save(PhotoModel photoModel);
    public List<PhotoModel> getPhotoList(Map<String,Object> limitMap);
    public int getAllCount(Map<String,Object> folderIdMap);
}
