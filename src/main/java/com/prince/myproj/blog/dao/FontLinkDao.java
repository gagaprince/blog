package com.prince.myproj.blog.dao;

import com.prince.myproj.blog.models.FontLinkModel;

import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2015/12/25.
 */
public interface FontLinkDao {
    public void save(FontLinkModel fontLinkModel);
    public List<FontLinkModel> getFontLinksByCate(Map<String,Object> limitMap);
    public void update(FontLinkModel fontLinkModel);

}
