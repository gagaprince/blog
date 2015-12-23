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
        <meta name="keywords" content="黑色模板,个人网站模板,个人博客模板,博客模板,css3,html5,网站模板">
        <meta name="description" content="这是一个有关黑色时间轴的css3 html5 网站模板">
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
            <aside>
                <!--<div class="search">
                    <form class="searchform" method="get" action="http://www.yangqq.com/web/24/view.html#">
                        <input type="text" name="s" value="Search" onfocus="this.value=&#39;&#39;" onblur="this.value=&#39;Search&#39;">
                    </form>
                </div>-->
                <div class="sunnav">
                    <ul>
                        <li><a href="http://www.yangqq.com/web/" target="_blank" title="网站建设">网站建设</a></li>
                        <li><a href="http://www.yangqq.com/newshtml5/" target="_blank" title="HTML5 / CSS3">HTML5 / CSS3</a></li>
                        <li><a href="http://www.yangqq.com/jstt/" target="_blank" title="技术探讨">技术探讨</a></li>
                        <li><a href="http://www.yangqq.com/news/s/" target="_blank" title="慢生活">慢生活</a></li>
                    </ul>
                </div>
                <div class="tuijian">
                    <h2>栏目更新</h2>
                    <ol>
                        <li><span><strong>1</strong></span><a href="http://www.yangqq.com/">有一种思念，是淡淡的幸福,一个心情一行文字</a></li>
                        <li><span><strong>2</strong></span><a href="http://www.yangqq.com/">励志人生-要做一个潇洒的女人</a></li>
                        <li><span><strong>3</strong></span><a href="http://www.yangqq.com/">女孩都有浪漫的小情怀——浪漫的求婚词</a></li>
                        <li><span><strong>4</strong></span><a href="http://www.yangqq.com/">Green绿色小清新的夏天-个人博客模板</a></li>
                        <li><span><strong>5</strong></span><a href="http://www.yangqq.com/">女生清新个人博客网站模板</a></li>
                        <li><span><strong>6</strong></span><a href="http://www.yangqq.com/">Wedding-婚礼主题、情人节网站模板</a></li>
                        <li><span><strong>7</strong></span><a href="http://www.yangqq.com/">Column 三栏布局 个人网站模板</a></li>
                        <li><span><strong>8</strong></span><a href="http://www.yangqq.com/">时间煮雨-个人网站模板</a></li>
                        <li><span><strong>9</strong></span><a href="http://www.yangqq.com/">花气袭人是酒香—个人网站模板</a></li>
                    </ol>
                </div>
                <div class="toppic">
                    <h2>图文并茂</h2>
                    <ul>
                        <li><a href="http://www.yangqq.com/"><img src="./images/k01.jpg">腐女不可怕，就怕腐女会画画！
                            <p>伤不起</p>
                        </a></li>
                        <li><a href="http://www.yangqq.com/"><img src="./images/k02.jpg">问前任，你还爱我吗？无限戳中泪点~
                            <p>感兴趣</p>
                        </a></li>
                        <li><a href="http://www.yangqq.com/"><img src="./images/k03.jpg">世上所谓幸福，就是一个笨蛋遇到一个傻瓜。
                            <p>喜欢</p>
                        </a></li>
                    </ul>
                </div>
                <div class="clicks">
                    <h2>热门点击</h2>
                    <ol>
                        <li><span><a href="http://www.yangqq.com/">慢生活</a></span><a href="http://www.yangqq.com/">有一种思念，是淡淡的幸福,一个心情一行文字</a></li>
                        <li><span><a href="http://www.yangqq.com/">爱情美文</a></span><a href="http://www.yangqq.com/">励志人生-要做一个潇洒的女人</a></li>
                        <li><span><a href="http://www.yangqq.com/">慢生活</a></span><a href="http://www.yangqq.com/">女孩都有浪漫的小情怀——浪漫的求婚词</a></li>
                        <li><span><a href="http://www.yangqq.com/">博客模板</a></span><a href="http://www.yangqq.com/">Green绿色小清新的夏天-个人博客模板</a></li>
                        <li><span><a href="http://www.yangqq.com/">女生个人博客</a></span><a href="http://www.yangqq.com/">女生清新个人博客网站模板</a></li>
                        <li><span><a href="http://www.yangqq.com/">Wedding</a></span><a href="http://www.yangqq.com/">Wedding-婚礼主题、情人节网站模板</a></li>
                        <li><span><a href="http://www.yangqq.com/">三栏布局</a></span><a href="http://www.yangqq.com/">Column 三栏布局 个人网站模板</a></li>
                        <li><span><a href="http://www.yangqq.com/">个人网站模板</a></span><a href="http://www.yangqq.com/">时间煮雨-个人网站模板</a></li>
                        <li><span><a href="http://www.yangqq.com/">古典风格</a></span><a href="http://www.yangqq.com/">花气袭人是酒香—个人网站模板</a></li>
                    </ol>
                </div>
            </aside>
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