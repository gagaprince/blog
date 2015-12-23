<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=GBK">
        <meta charset="gb2312">
        <title>${resultMap.daily.title}</title>
        <meta name="keywords" content="前端技术,html5,游戏开发,技术教程,技术博客,个人空间,个人简历">
        <meta name="description" content="这是一个个人博客，记录技术日志，解决技术问题，也有生活小记。">
        <link href="./css/styles.css" rel="stylesheet">
        <link href="./css/view.css" rel="stylesheet">
        <!-- 返回顶部调用 begin -->
        <link href="./css/lrtk.css" rel="stylesheet">
        <script type="text/javascript" src="./js/jquery.js"></script>
        <script type="text/javascript" src="./js/js.js"></script>
        <!-- 返回顶部调用 end-->
        <!--[if lt IE 9]>
        <script src="js/modernizr.js"></script>
        <![endif]-->
        <script src="js/editor/ueditor.parse.js"></script>
        <%@ include file="common/tongji.jsp"%>
    </head>
    <body>
    <header>
        <nav id="nav">
            <ul>
                <li><a href="/blog/index">我的首页</a></li>
                <li><a href="/blog/tech" title="技术博客">技术博客</a></li>
                <li><a href="/blog/video" title="视频教程">视频教程</a></li>
                <li><a href="/blog/fe" title="前端特效">前端特效</a></li>
                <li><a href="/blog/life" title="微生活">微生活</a></li>
                <li><a href="/blog/words" title="碎言碎语">碎言碎语</a></li>
            </ul>
            <!--获取当前页导航 高亮显示标题-->
        </nav>
    </header>
    <!--header end-->
    <div id="mainbody">
        <div class="blogs">
            <div id="index_view">
                <h1 class="c_titile">${resultMap.daily.title}</h1>
                <!--<p class="box">发布时间：2013-07-25<span>编辑：DanceSmile</span>阅读（30）</p>-->
                <ul>
                    ${resultMap.daily.content}
                </ul>
                <div class="otherlink">
                    <h2>相关文章</h2>
                    <ul>
                        <li><a href="http://www.yangqq.com/newstalk/mood/2013-07-24/518.html" title="我希望我的爱情是这样的">我希望我的爱情是这样的有种情谊，不是爱情，也算不得友情有种情谊，不是爱情，也算不得友情</a></li>
                        <li><a href="http://www.yangqq.com/newstalk/mood/2013-07-02/335.html" title="有种情谊，不是爱情，也算不得友情">有种情谊，不是爱情，也算不得友情有种情谊，不是爱情，也算不得友情有种情谊，不是爱情，也算不得友情</a></li>
                        <li><a href="http://www.yangqq.com/newstalk/mood/2013-07-01/329.html" title="世上最美好的爱情">世上最美好的爱情</a></li>
                        <li><a href="http://www.yangqq.com/news/read/2013-06-11/213.html" title="爱情没有永远，地老天荒也走不完">爱情没有永远，地老天荒也走不完</a></li>
                        <li><a href="http://www.yangqq.com/news/s/2013-06-06/24.html" title="爱情的背叛者">爱情的背叛者</a></li>
                    </ul>
                </div>
            </div>
            <!--bloglist end-->
            <!-- aside include -->
            <%@ include file="common/aside.jsp"%>
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
            rootPath:'js/editor/'
        });
    </script>
    </body>
</html>