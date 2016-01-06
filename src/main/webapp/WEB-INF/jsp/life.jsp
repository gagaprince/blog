<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>美女校花</title>
        <%@ include file="common/meta.jsp"%>
        <link href="/blog/css/styles.css" rel="stylesheet">
        <link href="/blog/css/animation.css" rel="stylesheet">
        <!-- 返回顶部调用 begin -->
        <link href="/blog/css/lrtk.css" rel="stylesheet">
        <script type="text/javascript" src="/blog/js/jquery.js"></script>
        <script type="text/javascript" src="/blog/js/js.js"></script>
        <script src="/blog/js/classes/Class.js"></script>
        <script src="/blog/js/classes/UtilBase.js"></script>
        <script src="/blog/js/photo/PuBuLiuUtil.js"></script>
        <script src="/blog/js/photo/life.js"></script>
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
                <c:set var="folderList" value="${lifeResultMap.puBuListModel.pubuList}"></c:set>
                <c:forEach var="folders" items="${folderList}" varStatus="status">
                    <li id="pubu${status.index}">
                        <c:forEach var="folder" items="${folders}" varStatus="status1">
                            <a href="/blog/life/detail/${folder.id}" target="_blank">
                                <div class="blog-img-item">
                                    <span class="title"><font>${folder.name}</font></span>
                                    <c:if test="${not empty folder.cover}">
                                        <span class="img"><img src="${folder.cover}"></span>
                                    </c:if>
                                    <c:if test="${empty folder.cover}">
                                        <span class="img"><img src="http://gagablog.oss-cn-beijing.aliyuncs.com/video/default.png"></span>
                                    </c:if>
                                </div>
                            </a>
                        </c:forEach>
                    </li>

                </c:forEach>
                <!-- listpage -->
                <!--<c:set var="listPage" value="${lifeResultMap.listpage}"></c:set>
                <c:set var="listpageUri" value="/blog/life/"></c:set>
                <%@ include file="common/listpage.jsp"%>-->
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