package com.prince.myproj.blog.dao;

import com.prince.myproj.blog.models.VideoModel;

import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2015/12/24.
 */
public interface VideoDao {
    public void save(VideoModel videoModel);
    public List<VideoModel> getVideos(Map<String,Object> limitMap);
    public Long getAllCount(Map<String,Object> cateMap);
    public VideoModel getVideoById(Map<String,Object> idMap);
    public void update(VideoModel videoModel);
}
