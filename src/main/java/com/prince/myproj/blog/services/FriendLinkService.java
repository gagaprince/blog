package com.prince.myproj.blog.services;

import com.prince.myproj.blog.dao.FriendLinkDao;
import com.prince.myproj.blog.models.FriendLinkModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gagaprince on 15-12-20.
 */
@Service
public class FriendLinkService {
    @Autowired
    private FriendLinkDao friendLinkDao;
    public List<FriendLinkModel> getSomeFriendLink(int num){
        Map<String,Object> numMap = new HashMap<String,Object>();
        numMap.put("num",num);
        return friendLinkDao.getLastFriendLinks(numMap);
    }
}
