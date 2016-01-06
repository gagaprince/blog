<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="baidu-site-verification" content="IlBwkvnFa1" />
        <meta name="baidu_union_verify" content="238732ef4a6d39841c148534e9b9cb0c">
        <%@ include file="common/meta.jsp"%>
        <title>gagaprince的世界</title>
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
        <div class="info">
            <figure><img src="${resultMap.suggest.bg}" alt="Panama Hat">
                <figcaption>
                    <strong>${resultMap.suggest.title}</strong>
                    ${resultMap.suggest.content}
                </figcaption>
            </figure>
            <div class="card">
                <h1>我的名片</h1>

                <p>网名：gagaprince | 伪装者</p>

                <p>职业：去哪儿网前端工程师</p>

                <p>Email：923731573@qq.com</p>

                <p>谨言：要有最朴素的生活与最遥远的梦想</p>
                <ul class="linkmore">
                    <li id="showQR"><a href="javascript:void(0)"  class="talk" title="给我留言"></a></li>
                    <div id="myQRFrame" class="myqrframe">
                        <div class="myqrimg">
                            <img src="http://gagablog.oss-cn-beijing.aliyuncs.com/peoplecenter/myqr.jpg">
                            <div class="ti1"></div>
                        </div>
                    </div>

                    <!--<li><a href="http://www.yangqq.com/" class="address" title="联系地址"></a></li>-->
                    <li><a href="mailto:923731573@qq.com?subject=我有事情要联系你！&body=写下您的建议" class="email" title="给我写信"></a></li>
                    <li><a href="/blog/life" class="photos" title="生活照片"></a></li>
                    <!--<li><a href="http://www.yangqq.com/" class="heart" title="关注我"></a></li>-->
                </ul>
            </div>
        </div>
        <!--info end-->
        <div class="blank"></div>
        <div class="blogs">
            <ul class="bloglist">
                <c:forEach var="daily" items="${resultMap.dailys}" varStatus="status">
                    <li>
                        <div class="arrow_box">
                            <div class="ti"></div>
                            <!--三角形-->
                            <div class="ci"></div>
                            <!--圆形-->
                            <h2 class="title"><a href="/blog/detail/${daily.id}" target="_blank">${daily.title}</a></h2>
                            <ul class="textinfo">
                                <c:if test="${not empty daily.pic}">
                                    <a href="/blog/detail/${daily.id}"><img src="${daily.pic}"></a>
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
                <c:set var="listPage" value="${resultMap.listpage}"></c:set>
                <c:set var="listpageUri" value="/blog/index/"></c:set>
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