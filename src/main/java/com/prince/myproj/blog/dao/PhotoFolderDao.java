package com.prince.myproj.blog.dao;


import com.prince.myproj.blog.models.PhotoFolderModel;

import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2015/12/22.
 */
public interface PhotoFolderDao {
    public void save(PhotoFolderModel folderModel);
    public void update(PhotoFolderModel folderModel);
    public PhotoFolderModel getPhotoFolderById(Map<String,Object> idMap);
    public List<PhotoFolderModel> getPhotoFolders(Map<String,Object> limitMap);
    public int getAllCount();
}
