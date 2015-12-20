package com.prince.myproj.blog.dao;

import com.prince.myproj.blog.models.MusicModel;

import java.util.List;
import java.util.Map;

/**
 * Created by gagaprince on 15-12-20.
 */
public interface MusicDao {
    public void save(MusicModel musicModel);
    public List<MusicModel> getMusicList();
    public MusicModel getMusicById(Map<String,Object> idMap);
}
