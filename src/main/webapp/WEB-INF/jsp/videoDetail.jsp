<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <c:set var="video" value="${videoResultMap.video}"></c:set>
        <title>${video.title}</title>
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
            <ul class="video-detail">
                <li><h1>${video.title}</h1></li>
                <embed
                    src="http://player.youku.com/player.php/sid/${video.src}/v.swf?VideoIDS=${video.src}&isAutoPlay=true&isShowRelatedVideo=false&showAd=0"
                    allowFullScreen="true"
                    quality="high" width="600" height="500"
                    align="middle" allowScriptAccess="always"
                    type="application/x-shockwave-flash">
                </embed>
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