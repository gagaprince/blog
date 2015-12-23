<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<footer>
        <div class="footer-mid">
            <div class="links">
                <h2>友情链接</h2>
                <ul>
                    <c:forEach var="friendLink" items="${footerResultMap.friendLinks}" varStatus="status">
                        <li><a href="${friendLink.link}" target="_blank">${friendLink.desc}</a></li>
                    </c:forEach>
                    <!--<li><a href="http://www.yangqq.com/">杨青个人博客</a></li>
                    <li><a href="http://www.3dst.com/">3DST技术服务中心</a></li>-->
                </ul>
            </div>
            <!--
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
-->
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
        <!--<div class="footer-bottom">
            <p>Copyright 2013 Design by <a href="http://www.yangqq.com/">DanceSmile</a></p>
        </div>-->
    </footer>