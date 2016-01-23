package com.prince.myproj.blog.dao;

import com.prince.myproj.blog.models.SuggestModel;

import java.util.List;

/**
 * Created by gagaprince on 15-12-19.
 */
public interface SuggestDao {
    public void save(SuggestModel suggestModel);
    public List<SuggestModel> getAllSuggest();
}
