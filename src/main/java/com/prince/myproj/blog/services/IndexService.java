package com.prince.myproj.blog.services;

import com.prince.myproj.blog.dao.SuggestDao;
import com.prince.myproj.blog.models.SuggestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Created by gagaprince on 15-12-19.
 */
@Service
public class IndexService {
    @Autowired
    private SuggestDao suggestDao;

    public SuggestModel getRandomSuggestModel(){
        List<SuggestModel> suggestModels = suggestDao.getAllSuggest();
        int size = suggestModels.size();
        if (size>0){
            Random r = new Random();
            int index = r.nextInt(size);
            return suggestModels.get(index);
        }
        return null;
    }
}
