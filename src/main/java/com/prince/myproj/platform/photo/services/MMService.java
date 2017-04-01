package com.prince.myproj.platform.photo.services;

import com.prince.myproj.platform.common.models.AjaxModel;
import com.prince.myproj.platform.common.statics.Constant;
import com.prince.myproj.platform.common.statics.ErrorCode;
import com.prince.util.httputil.HttpUtil;
import org.springframework.stereotype.Service;

/**
 * Created by zidong.wang on 2017/3/24.
 */
@Service
public class MMService {

    private HttpUtil httpUtil = HttpUtil.getInstance();

    public AjaxModel giveMeMMByPno(int pno){
        AjaxModel ajaxModel = new AjaxModel();

        String dataUrl = Constant.mmPhotoUrl+pno;
        String dataContent = httpUtil.getContentByUrl(dataUrl);
        ajaxModel.setStatus(ErrorCode.SUCCESS);
        ajaxModel.setData(dataContent);

        return ajaxModel;
    }
}
