package com.prince.myproj.blog.intercepters;

import com.prince.myproj.blog.models.FriendLinkModel;
import com.prince.myproj.blog.services.FriendLinkService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2015/12/23.
 * 加载公共的footer 友链 和 摄影集合
 */
@Aspect
@Component
public class FooterCommonAdvice {
    @Pointcut("@annotation(com.prince.myproj.blog.annotations.FooterCommon)")
    public void footerCommon(){}

    @Autowired
    private FriendLinkService friendLinkService;
    @Around("footerCommon()")
    public Object doAround(ProceedingJoinPoint joinPoint)throws Throwable{
        //先拿到httprequest
        Object[] args = joinPoint.getArgs();
        if(args.length>0){
            Object maybeReq = args[0];
            if(maybeReq instanceof HttpServletRequest){
                HttpServletRequest request = (HttpServletRequest)maybeReq;
//                HttpServletResponse response = (HttpServletResponse)maybeRes;

                List<FriendLinkModel> friendLinkModels = friendLinkService.getSomeFriendLink(20);
                Map<String,Object> footerResult = new HashMap<String, Object>();
                footerResult.put("friendLinks",friendLinkModels);
                request.setAttribute("footerResultMap",footerResult);
            }
        }
        return joinPoint.proceed();
    }
}
