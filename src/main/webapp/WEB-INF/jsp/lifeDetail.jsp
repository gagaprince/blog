<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta charset="utf-8">
        <title>gagaprince的微生活</title>
        <%@ include file="common/meta.jsp"%>
        <link href="/blog/css/styles.css?ss" rel="stylesheet">
        <link href="/blog/css/animation.css" rel="stylesheet">
        <!-- 返回顶部调用 begin -->
        <link href="/blog/css/lrtk.css" rel="stylesheet">
        <link href="/blog/photoutil/photoview.css" rel="stylesheet">
        <script type="text/javascript" src="/blog/js/jquery.js"></script>
        <script type="text/javascript" src="/blog/js/js.js"></script>
        <script type="text/javascript" src="/blog/photoutil/photoview.js"></script>
        <script src="/blog/js/photo/photoinit.js">
        <!-- 返回顶部调用 end-->
        <!--[if lt IE 9]>
        <script src="/blog/js/modernizr.js"></script>
        <![endif]-->
        <%@ include file="common/tongji.jsp"%>
    </head>
    <body>
    <%@include file="common/header.jsp" %>
    <!--header end-->
    <div id="mainbody">
        <!--info end-->
        <div class="blank"></div>
        <div class="blogs">
            <ul class="blog-img-list">
                <c:set var="photosList" value="${photoResultMap.puBuListModel.pubuList}"></c:set>
                <c:set var="photolieCount" value="${fn:length(photosList)}"></c:set>
                <c:forEach var="photos" items="${photosList}" varStatus="status">
                    <li id="pubu${status.index}" class="pubuitem">
                        <c:forEach var="photo" items="${photos}" varStatus="status1">
                            <a class="cover" data-index="${photolieCount*status1.index+status.index}">
                                <div class="blog-img-item">
                                    <!--<span class="title"><font>${folder.name}</font></span>-->
                                    <span class="title"><font></font></span>
                                    <span class="img"><img src="${photo.picUrl}" data-index="${photolieCount*status1.index+status.index}"></span>
                                </div>
                            </a>
                        </c:forEach>
                    </li>

                </c:forEach>
            </ul>

            <!--bloglist end-->

            <!-- aside include -->
            <%@ include file="common/aside.jsp"%>
        </div>
        <!--blogs end-->
    </div>
    <!--mainbody end-->
    <%@ include file="common/footer.jsp"%>
    <!-- jQuery仿腾讯回顶部和建议 代码开始 -->
    <div id="tbox"> <a id="gotop" href="javascript:void(0)" style="display: none;"></a>
    </div>
    <!-- 代码结束 -->
</html>