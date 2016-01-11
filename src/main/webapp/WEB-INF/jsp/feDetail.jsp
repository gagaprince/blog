<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <c:set var="feModel" value="${feResultMap.feModel}"></c:set>
        <title>${feModel.name}</title>
        <%@ include file="common/meta.jsp"%>
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
            <ul class="video-list">
                <div class="title">
                    <h1>${feModel.name}</h1>
                </div>
                <div class="fe-frame">
                    <iframe id="preview" src="${feModel.url}" frameborder="0" noresize="noresize" onload="Javascript:SetWinHeight(this)"></iframe>
                    <script>
                        function SetWinHeight(obj){
                            var win=obj;
                            if (document.getElementById){
                                if (win && !window.opera){
                                    if (win.contentDocument && win.contentDocument.body.offsetHeight)
                                        win.height = win.contentDocument.body.offsetHeight;
                                    else if(win.Document && win.Document.body.scrollHeight)
                                        win.height = win.Document.body.scrollHeight;
                                }
                            }
                        }
                    </script>
                </div>
                <div class="fedesc">
                    <h2>${feModel.desc}</h2>
                </div>
            </ul>
            <%@ include file="common/aside.jsp"%>
        </div>
        <!--blogs end-->
    </div>
    <!--mainbody end-->
    <%@ include file="common/footer.jsp"%>
    <div id="tbox"> <a id="gotop" href="javascript:void(0)" style="display: none;"></a>
    </div>
    <!-- 代码结束 -->
</html>