<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>${resultMap.daily.title}</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta charset="utf-8">
        <meta name="keywords" content="${resultMap.daily.tag}">
        <meta name="description" content="${resultMap.daily.description}">
        <link href="/blog/css/styles.css" rel="stylesheet">
        <link href="/blog/css/view.css" rel="stylesheet">
        <link href="/blog/css/jianshu.css" rel="stylesheet">
        <!-- 返回顶部调用 begin -->
        <link href="/blog/css/lrtk.css" rel="stylesheet">
        <script type="text/javascript" src="/blog/js/jquery.js"></script>
        <script type="text/javascript" src="/blog/js/js.js"></script>
        <!-- 返回顶部调用 end-->
        <!--[if lt IE 9]>
        <script src="js/modernizr.js"></script>
        <![endif]-->
        <script src="/blog/js/editor/ueditor.parse.js"></script>
        <%@ include file="common/tongji.jsp"%>

        <style>
            #ds-thread #ds-reset .ds-powered-by{
                display:none;
            }
            #ds-thread #ds-reset .ds-meta{
                display:none;
            }
            #ds-thread #ds-reset .ds-toolbar{
                display:none;
            }
            #ds-thread #ds-reset .ds-comments-info{
                margin-top:0 !important;
            }
        </style>
    </head>
    <body>
    <%@include file="common/header.jsp" %>
    <!--header end-->
    <div id="mainbody">
        <div class="blogs">
            <div id="index_view">
                <h1 class="c_titile">${resultMap.daily.title}</h1>
                <!--<p class="box">发布时间：2013-07-25<span>编辑：DanceSmile</span>阅读（30）</p>-->
                <div class="article">
                    <div class="show-content">
                        ${resultMap.daily.content}
                    </div>
                </div>
                <div class="otherlink">
                    <h2></h2>
                    <div class="detail-tag">
                    <c:forEach var="tag" items="${resultMap.dailyTags}" varStatus="status">
                        <span><a href="/blog/search/${tag}">${tag}</a></span>
                    </c:forEach>
                    </div>
                    <!--高速版-->
                    <div id="SOHUCS" sid="${resultMap.daily.id}"></div>
                    <script charset="utf-8" type="text/javascript" src="http://changyan.sohu.com/upload/changyan.js" ></script>
                    <script type="text/javascript">
                        window.changyan.api.config({
                            appid: 'cytmzt3Bc',
                            conf: 'prod_2d2e1939c5d3d5f6d3a24a59490ea030'
                        });
                    </script>

                </div>

            </div>
            <!--bloglist end-->

            <!-- aside include -->
            <%@ include file="common/aside.jsp"%>

            <c:if test="${not empty resultMap.relativeDailys}">
            <aside style="margin:0;">
                <div class="tuijian">
                    <h2>相关文章</h2>
                    <ol>
                        <c:forEach var="relativeDaily" items="${resultMap.relativeDailys}" varStatus="status">
                            <li><span><strong>${status.index+1}</strong></span><a href="/blog/detail/${relativeDaily.id}">${relativeDaily.title}</a></li>
                        </c:forEach>
                    </ol>
                </div>
            </aside>
            </c:if>

            <!--<div class="comment">

            </div>-->
        </div>
        <!--blogs end-->
    </div>
    <!--mainbody end-->
    <!--common footer -->
    <%@ include file="common/footer.jsp"%>
    <!-- jQuery仿腾讯回顶部和建议 代码开始 -->
    <div id="tbox"> <a id="gotop" href="javascript:void(0)" style="display: none;"></a> </div>
    <!-- 代码结束 -->
    <script>
        uParse("#index_view ul",{
            rootPath:'/blog/js/editor/'
        });
    </script>
    </body>
</html>