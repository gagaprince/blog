<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta charset="utf-8">
        <title>gagaprince的技术博客</title>
        <meta name="keywords" content="前端技术,html5,游戏开发,技术教程,技术博客,个人空间,个人简历">
        <meta name="description" content="这是一个个人博客，记录技术日志，解决技术问题，也有生活小记。">
        <link href="/blog/css/styles.css" rel="stylesheet">
        <link href="/blog/css/animation.css" rel="stylesheet">
        <!-- 返回顶部调用 begin -->
        <link href="/blog/css/lrtk.css" rel="stylesheet">
        <script type="text/javascript" src="/blog/js/jquery.js"></script>
        <script type="text/javascript" src="/blog/js/js.js"></script>
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
            <ul class="bloglist">
                <c:forEach var="daily" items="${techResultMap.dailys}" varStatus="status">
                    <li>
                        <div class="arrow_box">
                            <div class="ti"></div>
                            <!--三角形-->
                            <div class="ci"></div>
                            <!--圆形-->
                            <h2 class="title"><a href="/blog/detail?id=${daily.id}" target="_blank">${daily.title}</a></h2>
                            <ul class="textinfo">
                                <c:if test="${not empty daily.pic}">
                                    <a href="/blog/detail?id=${daily.id}"><img src="${daily.pic}"></a>
                                </c:if>
                                <p>
                                    ${daily.content}
                                </p>
                            </ul>
                            <ul class="details">
                                <li class="icon-time"><a href="javascript:void(0)">${daily.createTimeStr}</a></li>
                            </ul>
                        </div>
                        <!--arrow_box end-->
                    </li>
                </c:forEach>
                <!-- listpage -->
                <c:set var="listPage" value="${techResultMap.listpage}"></c:set>
                <c:set var="listpageUri" value="/blog/tech?pno="></c:set>
                <%@ include file="common/listpage.jsp"%>
                <!-- listpage end -->
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