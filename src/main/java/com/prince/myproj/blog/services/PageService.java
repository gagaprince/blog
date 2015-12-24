package com.prince.myproj.blog.services;

import com.prince.myproj.blog.models.ListPageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zidong.wang on 2015/12/24.
 */
@Service
public class PageService {

    @Autowired
    private UtilService utilService;

    public ListPageModel preparedListPage(HttpServletRequest request,int defaultPsize){
        ListPageModel listPageModel = new ListPageModel();

        String pnoStr = utilService.getDefaultWhenNull(request.getParameter("pno"), "0");
        int pno = Integer.parseInt(pnoStr);
        String psizeStr = utilService.getDefaultWhenNull(request.getParameter("psize"), defaultPsize+"");
        int psize = Integer.parseInt(psizeStr);

        listPageModel.setPno(pno);
        listPageModel.setPsize(psize);

        return listPageModel;
    }
}
