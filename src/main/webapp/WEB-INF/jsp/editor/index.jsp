<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html lang="en-US">

<head>
    <meta charset="UTF-8">
    <title>日志运营系统</title>
</head>

<body>
<!-- 加载编辑器的容器 -->
<div style="width:100%;">
    <div style="width:660px;margin:auto">
        <script id="container" name="content" type="text/plain">
        <c:if test="${not empty daily}">
            ${daily.content}
        </c:if>
        </script>
        <br><br>
        title : <input id="title" type="text">
        <br><br>
        cate : <input id="cate" type="text" ><br><br>
        bigcate : <input id="bigcate" type="text" ><br><br>
        tag:<input id="tag" type="text" ><br><br>
        description:<input id="description" type="text" ><br><br>
        <button id="submit">提交</button><br>
    </div>

</div>
<c:if test="${not empty daily}">
    <script>
        var nowId = "${daily.id}";
        var nowtitle = "${daily.title}";
        var nowcate = "${daily.cate}";
        var nowbigCate = "${daily.bigCate}";
        var nowtag = "${daily.tag}";
        var nowdescription = "${daily.description}";
    </script>
</c:if>


<script src="/blog/js/jquery.js"></script>
<!-- 配置文件 -->
<script type="text/javascript" src="/blog/js/editor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="/blog/js/editor/ueditor.all.js"></script>
<!-- 实例化编辑器 -->
<script src="/blog/js/editor/editor.js"></script>
</body>

</html>