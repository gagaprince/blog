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
        <title>gagaprince的视频教程</title>
        <meta name="keywords" content="前端技术,html5,游戏开发,技术教程,技术博客,个人空间,个人简历">
        <meta name="description" content="这是一个个人博客，记录技术日志，解决技术问题，也有生活小记。">
        <link href="./css/styles.css" rel="stylesheet">
        <link href="./css/animation.css" rel="stylesheet">
        <!-- 返回顶部调用 begin -->
        <link href="./css/lrtk.css" rel="stylesheet">
        <script type="text/javascript" src="./js/jquery.js"></script>
        <script type="text/javascript" src="./js/js.js"></script>
        <!-- 返回顶部调用 end-->
        <!--[if lt IE 9]>
        <script src="./js/modernizr.js"></script>
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
                <c:set var="pno" value="${listPage.pno}"></c:set>
                <c:set var="allPage" value="${listPage.allPage}"></c:set>
                <c:set var="listpageUri" value="/blog/index?pno="></c:set>
                <c:if test="${pno-1>=0}">
                    <c:set var="prepno" value="${pno-1}"></c:set>
                </c:if>
                <c:if test="${pno-1<0}">
                    <c:set var="prepno" value="0"></c:set>
                </c:if>
                <c:if test="${pno+1>=allPage}">
                    <c:set var="nextpno" value="${allPage-1}"></c:set>
                </c:if>
                <c:if test="${pno+1<allPage}">
                    <c:set var="nextpno" value="${pno+1}"></c:set>
                </c:if>
                <div class="pagelistfram">
                    <div class="pagelist h-c">
                        <a class="pageitem" href="${listpageUri}0">首页</a>
                        <a class="pageitem" href="${listpageUri}${prepno}">上一页</a>
                        <c:choose>
                            <c:when test="${allPage<=6 || (allPage>6 && pno>allPage-5)}">
                                <c:if test="${allPage<=6}">
                                    <c:set var="beginPage" value="1"></c:set>
                                </c:if>
                                <c:if test="${allPage>6 && pno>allPage-5}">
                                    <c:set var="beginPage" value="${allPage-5}"></c:set>
                                </c:if>
                                <c:set var="endPage" value="${allPage}"></c:set>
                                <c:forEach var="i" begin="${beginPage}" end="${endPage}" step="1">
                                    <c:if test="${pno == i-1}">
                                        <a class="pageitem current">${i}</a>
                                    </c:if>
                                    <c:if test="${pno != i-1}">
                                        <a class="pageitem" href="${listpageUri}${i-1}">${i}</a>
                                    </c:if>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${pno>2}">
                                        <c:set var="beginPage" value="${pno-2}"></c:set>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="beginPage" value="0"></c:set>
                                    </c:otherwise>
                                </c:choose>
                                <c:forEach var="i" begin="1" end="6" step="1">
                                    <c:if test="${i<=3}">
                                        <c:if test="${pno == beginPage+i-1}">
                                            <a class="pageitem current">${beginPage+i}</a>
                                        </c:if>
                                        <c:if test="${pno != beginPage+i-1}">
                                            <a class="pageitem" href="${listpageUri}${beginPage+i-1}">${beginPage+i}</a>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${i==4}">
                                        <a class="pageitem">……</a>
                                    </c:if>
                                    <c:if test="${i>4}">
                                        <a class="pageitem" href="${listpageUri}${allPage-7+i}">${allPage-6+i}</a>
                                    </c:if>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                        <a class="pageitem" href="${listpageUri}${nextpno}">下一页</a>
                    </div>
                </div>
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