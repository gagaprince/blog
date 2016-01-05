<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<aside>
    <div class="search">
        <form class="searchform" name="blogSearch" method="get" action="/blog/search">
            <input id="blogSearchInput" type="text" name="key" placeholder="输入您要搜索的内容" value="">
        </form>
        <script>
            $(document).ready(function(){
                $("#blogSearchInput").on("keydown",function(e){
                    var e = event || window.event || arguments.callee.caller.arguments[0];
                    if(e && e.keyCode==13){
                        blogSearch.submit();
                    }
                });
            });
        </script>
    </div>
    <!--<div class="sunnav">
        <ul>
            <li><a href="http://www.yangqq.com/web/" target="_blank" title="网站建设">网站建设</a></li>
            <li><a href="http://www.yangqq.com/newshtml5/" target="_blank" title="HTML5 / CSS3">HTML5 / CSS3</a></li>
            <li><a href="http://www.yangqq.com/jstt/" target="_blank" title="技术探讨">技术探讨</a></li>
            <li><a href="http://www.yangqq.com/news/s/" target="_blank" title="慢生活">慢生活</a></li>
        </ul>
    </div>-->
    <c:if test="${not empty sliderResult.updateModels}">
        <div class="tuijian">
            <h2>栏目更新</h2>
            <ol>
                <c:forEach var="updateModel" items="${sliderResult.updateModels}" varStatus="status">
                    <li><span><strong>${status.index+1}</strong></span><a href="${updateModel.link}">${updateModel.title}</a></li>
                </c:forEach>
                </ol>
        </div>
    </c:if>
    <c:if test="${not empty sliderResult.photoFontModels}">
        <div class="toppic">
            <h2>图文并茂</h2>
            <ul>
                <c:forEach var="photoFontModel" items="${sliderResult.photoFontModels}" varStatus="status">
                <li><a href="${photoFontModel.link}"><img src="${photoFontModel.cover}">${photoFontModel.title}
                    
                </a></li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <c:if test="${not empty sliderResult.hotClickModels}">
        <div class="clicks">
            <h2>热门点击</h2>
            <ol>
                <c:forEach var="hotClickModel" items="${sliderResult.hotClickModels}" varStatus="status">
                    <li><span><a href="http://www.yangqq.com/">慢生活</a></span><a href="${hotClickModel.link}">${hotClickModel.title}</a></li>
                </c:forEach>
                <!--
                <li><span><a href="http://www.yangqq.com/">爱情美文</a></span><a href="http://www.yangqq.com/">励志人生-要做一个潇洒的女人</a></li>
                <li><span><a href="http://www.yangqq.com/">慢生活</a></span><a href="http://www.yangqq.com/">女孩都有浪漫的小情怀——浪漫的求婚词</a></li>
                <li><span><a href="http://www.yangqq.com/">博客模板</a></span><a href="http://www.yangqq.com/">Green绿色小清新的夏天-个人博客模板</a></li>
                <li><span><a href="http://www.yangqq.com/">女生个人博客</a></span><a href="http://www.yangqq.com/">女生清新个人博客网站模板</a></li>
                <li><span><a href="http://www.yangqq.com/">Wedding</a></span><a href="http://www.yangqq.com/">Wedding-婚礼主题、情人节网站模板</a></li>
                <li><span><a href="http://www.yangqq.com/">三栏布局</a></span><a href="http://www.yangqq.com/">Column 三栏布局 个人网站模板</a></li>
                <li><span><a href="http://www.yangqq.com/">个人网站模板</a></span><a href="http://www.yangqq.com/">时间煮雨-个人网站模板</a></li>
                <li><span><a href="http://www.yangqq.com/">古典风格</a></span><a href="http://www.yangqq.com/">花气袭人是酒香—个人网站模板</a></li>-->
            </ol>
        </div>
    </c:if>
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