<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- saved from url=(0029)http://www.yangqq.com/web/24/ -->
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta charset="utf-8">
        <title>gagaprince的世界</title>
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
            <script src="./js/silder.js"></script>
            <!--获取当前页导航 高亮显示标题-->
        </nav>
    </header>
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
                <li>
                    <div class="arrow_box">
                        <div class="ti"></div>
                        <!--三角形-->
                        <div class="ci"></div>
                        <!--圆形-->
                        <h2 class="title"><a href="http://www.yangqq.com/" target="_blank">我希望我的爱情是这样的</a></h2>
                        <ul class="textinfo">
                            <a href="http://www.yangqq.com/"><img src="./images/s1.jpg"></a>

                            <p>
                                我希望我的爱情是这样的，相濡以沫，举案齐眉，平淡如水。我在岁月中找到他，依靠他，将一生交付给他。做他的妻子，他孩子的母亲，为他做饭，洗衣服，缝一颗掉了的纽扣。然后，我们一起在时光中变老。</p>
                        </ul>
                        <ul class="details">
                            <li class="likes"><a href="http://www.yangqq.com/web/24/#">10</a></li>
                            <li class="comments"><a href="http://www.yangqq.com/web/24/#">34</a></li>
                            <li class="icon-time"><a href="http://www.yangqq.com/web/24/#">2013-8-7</a></li>
                        </ul>
                    </div>
                    <!--arrow_box end-->
                </li>
                <li>
                    <div class="arrow_box">
                        <div class="ti"></div>
                        <!--三角形-->
                        <div class="ci"></div>
                        <!--圆形-->
                        <h2 class="title"><a href="http://www.yangqq.com/" target="_blank">谁更心软，谁就先长大</a></h2>
                        <ul class="textinfo">
                            <a href="http://www.yangqq.com/"><img src="./images/s2.jpg"></a>

                            <p>
                                男人都是孩子，需要用一生时间来长大。女人都想当孩子，却最擅长的角色是妈妈。恋爱一开始，是两个孩子之间的游戏，到后来，成了大人和孩子之间的游戏。恋爱这回事，总要有一个人先长大，对另一半多些包容和宠溺。而通常来看：谁更心软，谁就先长大...</p>
                        </ul>
                        <ul class="details">
                            <li class="likes"><a href="http://www.yangqq.com/web/24/#">102</a></li>
                            <li class="comments"><a href="http://www.yangqq.com/web/24/#">58</a></li>
                            <li class="icon-time"><a href="http://www.yangqq.com/web/24/#">2013-8-7</a></li>
                        </ul>
                    </div>
                    <!--arrow_box end-->
                </li>
                <li>
                    <div class="arrow_box">
                        <div class="ti"></div>
                        <!--三角形-->
                        <div class="ci"></div>
                        <!--圆形-->
                        <h2 class="title"><a href="http://www.yangqq.com/" target="_blank">Nothing is as sweet as you</a>
                        </h2>
                        <ul class="textinfo">
                            <a href="http://www.yangqq.com/"><img src="./images/s3.jpg"></a>

                            <p> 有时候不是我不理你，其实我也想你了，只是我不知道该对你说什么。不管过去如何，过去的已经过去，最好的总在未来等着你。当我们懂得珍惜平凡的幸福的时候，就已经成了人生的赢家。Nothing
                                is as sweet as you再没什么，能甜蜜如你。我以为只要很认真的喜欢就能打动一个人...</p>
                        </ul>
                        <ul class="details">
                            <li class="likes"><a href="http://www.yangqq.com/web/24/#">15</a></li>
                            <li class="comments"><a href="http://www.yangqq.com/web/24/#">2</a></li>
                            <li class="icon-time"><a href="http://www.yangqq.com/web/24/#">2013-8-7</a></li>
                        </ul>
                    </div>
                    <!--arrow_box end-->
                </li>
                <li>
                    <div class="arrow_box">
                        <div class="ti"></div>
                        <!--三角形-->
                        <div class="ci"></div>
                        <!--圆形-->
                        <h2 class="title"><a href="http://www.yangqq.com/" target="_blank">谁更心软，谁就先长大</a></h2>
                        <ul class="textinfo">
                            <a href="http://www.yangqq.com/"><img src="./images/s4.jpg"></a>

                            <p>
                                男人都是孩子，需要用一生时间来长大。女人都想当孩子，却最擅长的角色是妈妈。恋爱一开始，是两个孩子之间的游戏，到后来，成了大人和孩子之间的游戏。恋爱这回事，总要有一个人先长大，对另一半多些包容和宠溺。而通常来看：谁更心软，谁就先长大...</p>
                        </ul>
                        <ul class="details">
                            <li class="likes"><a href="http://www.yangqq.com/web/24/#">102</a></li>
                            <li class="comments"><a href="http://www.yangqq.com/web/24/#">58</a></li>
                            <li class="icon-time"><a href="http://www.yangqq.com/web/24/#">2013-8-7</a></li>
                        </ul>
                    </div>
                    <!--arrow_box end-->
                </li>
                <li>
                    <div class="arrow_box">
                        <div class="ti"></div>
                        <!--三角形-->
                        <div class="ci"></div>
                        <!--圆形-->
                        <h2 class="title"><a href="http://www.yangqq.com/" target="_blank">趁我们都还年轻</a></h2>
                        <ul class="textinfo">
                            <a href="http://www.yangqq.com/"><img src="./images/s5.jpg"></a>

                            <p>
                                趁我们都还年轻,多走几步路，多欣赏下沿途的风景，不要急于抵达目的地而错过了流年里温暖的人和物；趁我们都还年轻，多说些浪漫的话语，多做些幼稚的事情，不要嫌人笑话错过了生命中最美好的片段和场合；趁我们都还年轻，把距离缩短，把时间延长。趁我们都还年轻，多做些我们想要做的任何事...</p>
                        </ul>
                        <ul class="details">
                            <li class="likes"><a href="http://www.yangqq.com/web/24/#">15</a></li>
                            <li class="comments"><a href="http://www.yangqq.com/web/24/#">2</a></li>
                            <li class="icon-time"><a href="http://www.yangqq.com/web/24/#">2013-8-7</a></li>
                        </ul>
                    </div>
                    <!--arrow_box end-->
                </li>
            </ul>
            <!--bloglist end-->
            <aside>
                <div class="tuijian">
                    <h2>推荐文章</h2>
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
                        <li><span><a href="http://www.yangqq.com/">慢生活</a></span><a href="http://www.yangqq.com/">有一种思念，是淡淡的幸福,一个心情一行文字</a>
                        </li>
                        <li><span><a href="http://www.yangqq.com/">爱情美文</a></span><a href="http://www.yangqq.com/">励志人生-要做一个潇洒的女人</a>
                        </li>
                        <li><span><a href="http://www.yangqq.com/">慢生活</a></span><a href="http://www.yangqq.com/">女孩都有浪漫的小情怀——浪漫的求婚词</a>
                        </li>
                        <li><span><a href="http://www.yangqq.com/">博客模板</a></span><a href="http://www.yangqq.com/">Green绿色小清新的夏天-个人博客模板</a>
                        </li>
                        <li><span><a href="http://www.yangqq.com/">女生个人博客</a></span><a href="http://www.yangqq.com/">女生清新个人博客网站模板</a>
                        </li>
                        <li><span><a href="http://www.yangqq.com/">Wedding</a></span><a href="http://www.yangqq.com/">Wedding-婚礼主题、情人节网站模板</a>
                        </li>
                        <li><span><a href="http://www.yangqq.com/">三栏布局</a></span><a href="http://www.yangqq.com/">Column
                            三栏布局 个人网站模板</a></li>
                        <li><span><a href="http://www.yangqq.com/">个人网站模板</a></span><a href="http://www.yangqq.com/">时间煮雨-个人网站模板</a>
                        </li>
                        <li><span><a href="http://www.yangqq.com/">古典风格</a></span><a href="http://www.yangqq.com/">花气袭人是酒香—个人网站模板</a>
                        </li>
                    </ol>ƒ
                </div>
                <div class="search">
                    <form class="searchform" method="get" action="http://www.yangqq.com/web/24/#">
                        <input type="text" name="s" value="Search" onfocus="this.value=&#39;&#39;"
                               onblur="this.value=&#39;Search&#39;">
                    </form>
                </div>
                <div class="viny">
                    <dl>
                        <dt class="art"><img src="./images/artwork.png" alt="专辑"></dt>
                        <dd class="icon-song"><span></span>南方姑娘</dd>
                        <dd class="icon-artist"><span></span>歌手：赵雷</dd>
                        <dd class="icon-album"><span></span>所属专辑：《赵小雷》</dd>
                        <dd class="icon-like"><span></span><a href="http://www.yangqq.com/">喜欢</a></dd>
                        <dd class="music">
                            <audio src="images/nf.mp3" controls=""></audio>
                        </dd>
                        <!--也可以添加loop属性 音频加载到末尾时，会重新播放-->
                    </dl>
                </div>
            </aside>
        </div>
        <!--blogs end-->
    </div>
    <!--mainbody end-->
    <footer>
        <div class="footer-mid">
            <div class="links">
                <h2>友情链接</h2>
                <ul>
                    <li><a href="http://www.yangqq.com/">杨青个人博客</a></li>
                    <li><a href="http://www.3dst.com/">3DST技术服务中心</a></li>
                </ul>
            </div>
            <div class="visitors">
                <h2>最新评论</h2>
                <dl>
                    <dt><img src="./images/s8.jpg">
                    </dt>
                    <dt>
                    </dt>
                    <dd>DanceSmile
                        <time>49分钟前</time>
                    </dd>
                    <dd>在 <a href="http://www.yangqq.com/jstt/bj/2013-07-28/530.html"
                             class="title">如果要学习web前端开发，需要学习什么？ </a>中评论：
                    </dd>
                    <dd>文章非常详细，我很喜欢.前端的工程师很少，我记得几年前yahoo花高薪招聘前端也招不到</dd>
                </dl>
                <dl>
                    <dt><img src="./images/s7.jpg">
                    </dt>
                    <dt>
                    </dt>
                    <dd>yisa
                        <time>2小时前</time>
                    </dd>
                    <dd>在 <a href="http://www.yangqq.com/news/s/2013-07-31/533.html" class="title">芭蕾女孩的心事儿</a>中评论：</dd>
                    <dd>我手机里面也有这样一个号码存在</dd>
                </dl>
                <dl>
                    <dt><img src="./images/s6.jpg">
                    </dt>
                    <dt>
                    </dt>
                    <dd>小林博客
                        <time>8月7日</time>
                    </dd>
                    <dd>在 <a href="http://www.yangqq.com/jstt/bj/2013-06-18/285.html"
                             class="title">如果个人博客网站再没有价值，你还会坚持吗？ </a>中评论：
                    </dd>
                    <dd>博客色彩丰富，很是好看</dd>
                </dl>
            </div>
            <section class="flickr">
                <h2>摄影作品</h2>
                <ul>
                    <li><a href="http://www.yangqq.com/"><img src="./images/01.jpg"></a></li>
                    <li><a href="http://www.yangqq.com/"><img src="./images/02.jpg"></a></li>
                    <li><a href="http://www.yangqq.com/"><img src="./images/03.jpg"></a></li>
                    <li><a href="http://www.yangqq.com/"><img src="./images/04.jpg"></a></li>
                    <li><a href="http://www.yangqq.com/"><img src="./images/05.jpg"></a></li>
                    <li><a href="http://www.yangqq.com/"><img src="./images/06.jpg"></a></li>
                    <li><a href="http://www.yangqq.com/"><img src="./images/07.jpg"></a></li>
                    <li><a href="http://www.yangqq.com/"><img src="./images/08.jpg"></a></li>
                    <li><a href="http://www.yangqq.com/"><img src="./images/09.jpg"></a></li>
                </ul>
            </section>
        </div>
        <div class="footer-bottom">
            <p>Copyright 2013 Design by <a href="http://www.yangqq.com/">DanceSmile</a></p>
        </div>
    </footer>
    <!-- jQuery仿腾讯回顶部和建议 代码开始 -->
    <div id="tbox"> <a id="gotop" href="javascript:void(0)" style="display: none;"></a>
    </div>
    <!-- 代码结束 -->

    <embed id="xunlei_com_thunder_helper_plugin_d462f475-c18e-46be-bd10-327458d045bd"
           type="application/thunder_download_plugin" height="0" width="0">
    </body>
</html>