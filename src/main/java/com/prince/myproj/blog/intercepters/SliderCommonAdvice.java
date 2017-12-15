package com.prince.myproj.blog.intercepters;

import com.prince.myproj.blog.models.FontLinkModel;
import com.prince.myproj.blog.models.FriendLinkModel;
import com.prince.myproj.blog.services.FontLinkService;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2015/12/23.
 * 加载公共的footer 友链 和 摄影集合
 */
@Aspect
@Component
public class SliderCommonAdvice {
    public static final Logger logger = Logger.getLogger(SliderCommonAdvice.class);

    @Pointcut("@annotation(com.prince.myproj.blog.annotations.FooterCommon)")
    public void sliderCommon(){}

    @Autowired
    private FontLinkService fontLinkService;
    @Around("sliderCommon()")
    public Object doAround(ProceedingJoinPoint joinPoint)throws Throwable{
        //先拿到httprequest
        Object[] args = joinPoint.getArgs();
        if(args.length>0){
            Object maybeReq = args[0];
            if(maybeReq instanceof HttpServletRequest){
                HttpServletRequest request = (HttpServletRequest)maybeReq;
//                HttpServletResponse response = (HttpServletResponse)maybeRes;

                List<FontLinkModel> updateModels = fontLinkService.giveMeUpdateLink();
                List<FontLinkModel> photoFontModels = fontLinkService.giveMePhotoFontLink();
                List<FontLinkModel> hotClickModels = fontLinkService.giveMeHotClick();
                List<FontLinkModel> cateModels = fontLinkService.giveMeCateCountList();


                logger.info("updataModels:"+updateModels);
                logger.info("photoFontModels:"+photoFontModels);
                logger.info("hotClickModels:"+hotClickModels);

                Map<String,Object> sliderResult = new HashMap<String, Object>();
                sliderResult.put("updateModels",updateModels);
                sliderResult.put("photoFontModels",photoFontModels);
                sliderResult.put("hotClickModels",hotClickModels);
                sliderResult.put("cateModels",cateModels);
                request.setAttribute("sliderResult",sliderResult);
            }
        }
        return joinPoint.proceed();
    }
}
