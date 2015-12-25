package com.prince.myproj.blog.services;

import com.prince.myproj.blog.dao.FontLinkDao;
import com.prince.myproj.blog.models.FontLinkModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zidong.wang on 2015/12/25.
 */
@Service
public class FontLinkService {

    @Autowired
    private FontLinkDao fontLinkDao;

    public List<FontLinkModel> giveMeUpdateLink(){
        return null;
    }
    public List<FontLinkModel> giveMePhotoFontLink(){
        return null;
    }
    public List<FontLinkModel> giveMeHotClick(){
        return null;
    }
    public List<FontLinkModel> giveMePhotoLink(){
        return null;
    }



}
