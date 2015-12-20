package com.prince.myproj.blog.dao;

import com.prince.myproj.blog.models.FriendLinkModel;

import java.util.List;
import java.util.Map;

/**
 * Created by gagaprince on 15-12-20.
 */
public interface FriendLinkDao {
    public void save(FriendLinkModel friendLinkModel);
    public List<FriendLinkModel> getLastFriendLinks(Map<String,Object> numMap);

}
