<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<aside>
    <!--<div class="search">
        <form class="searchform" method="get" action="http://www.yangqq.com/web/24/view.html#">
            <input type="text" name="s" value="Search" onfocus="this.value=&#39;&#39;" onblur="this.value=&#39;Search&#39;">
        </form>
    </div>-->
    <!--<div class="sunnav">
        <ul>
            <li><a href="http://www.yangqq.com/web/" target="_blank" title="网站建设">网站建设</a></li>
            <li><a href="http://www.yangqq.com/newshtml5/" target="_blank" title="HTML5 / CSS3">HTML5 / CSS3</a></li>
            <li><a href="http://www.yangqq.com/jstt/" target="_blank" title="技术探讨">技术探讨</a></li>
            <li><a href="http://www.yangqq.com/news/s/" target="_blank" title="慢生活">慢生活</a></li>
        </ul>
    </div>-->
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
            <li><a href="http://www.yangqq.com/"><img src="/blog/images/k01.jpg">腐女不可怕，就怕腐女会画画！
                <p>伤不起</p>
            </a></li>
            <li><a href="http://www.yangqq.com/"><img src="/blog/images/k02.jpg">问前任，你还爱我吗？无限戳中泪点~
                <p>感兴趣</p>
            </a></li>
            <li><a href="http://www.yangqq.com/"><img src="/blog/images/k03.jpg">世上所谓幸福，就是一个笨蛋遇到一个傻瓜。
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
    <c:if test="${resultMap.music!=null}">
        <div class="viny">
            <dl>
                <dt class="art"><img src="${resultMap.music.bgImg}" alt="专辑"></dt>
                <dd class="icon-song"><span></span>${resultMap.music.name}</dd>
                <dd class="icon-artist"><span></span>歌手：${resultMap.music.singer}</dd>
                <dd class="icon-album"><span></span>所属专辑：${resultMap.music.album}</dd>
                <!--<dd class="icon-like"><span></span><a href="http://www.yangqq.com/">喜欢</a></dd>-->
                <dd class="music">
                    <audio src="${resultMap.music.src}"  loop="loop" controls></audio>
                </dd>
                <!--也可以添加loop属性 音频加载到末尾时，会重新播放 autoplay="autoplay"-->
            </dl>
        </div>
    </c:if>
</aside>