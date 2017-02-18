package com.prince.myproj.platform.novel.dao;

import com.prince.myproj.platform.novel.models.ChapterModel;

import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2017/2/15.
 */
public interface ChapterDao {
    public void save(ChapterModel chapterModel);
    public void update(ChapterModel chapterModel);
    public List<ChapterModel> getChapterListByNovelId(Map<String,Object> idMap);
    public ChapterModel getChapterByNovelIdAndChapter(ChapterModel chapterModel);

}
