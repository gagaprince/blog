<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>gagaprince的前端特效</title>
        <%@ include file="common/meta.jsp"%>
        <link href="/blog/css/styles.css?ss" rel="stylesheet">
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
            <ul class="blog-img-list">
                <c:forEach var="feModel" items="${feResultMap.feModels}" varStatus="status">
                    <li>
                        <a href="/blog/fe/detail/${feModel.id}" target="_blank">
                            <div class="blog-img-item">
                                <span class="title"><font>${feModel.name}</font></span>
                                <c:if test="${not empty feModel.cover}">
                                    <span class="img feimg"><img src="${feModel.cover}"></span>
                                </c:if>
                                <c:if test="${empty feModel.cover}">
                                    <span class="img feimg"><img src="http://gagablog.oss-cn-beijing.aliyuncs.com/video/default.png"></span>
                                </c:if>
                                <span class="title fetitle"><font>${feModel.desc}</font></span>
                            </div>
                        </a>
                    </li>
                </c:forEach>

                <!-- listpage -->
                <c:set var="listPage" value="${feResultMap.listpage}"></c:set>
                <c:set var="listpageUri" value="/blog/fe/"></c:set>
                <%@ include file="common/listpage.jsp"%>
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