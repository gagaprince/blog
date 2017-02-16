package com.prince.myproj.platform.novel.dao;

import com.prince.myproj.platform.novel.models.NovelModel;

import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2017/2/15.
 */
public interface NovelDao {
    public void save(NovelModel novelModel);
    public void update(NovelModel novelModel);
    public NovelModel getNovelById(Map<String,Long> idMap);
    public NovelModel getNovelBySourceUrl(NovelModel novelModel);
    public List<NovelModel> getAllNovels();
    public List<NovelModel> getNovelList(Map<String,Object> cateMap);

}
