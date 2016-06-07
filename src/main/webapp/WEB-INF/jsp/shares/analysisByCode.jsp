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
        <meta name="baidu_union_verify" content="8dcbc42830416c07c5a1164f8d7d9de1">
        <%@ include file="../common/meta.jsp"%>
        <title>历史数据分析</title>
        <script type="text/javascript" src="/blog/js/jquery.js"></script>
        <%@ include file="../common/tongji.jsp"%>
        <link href="/blog/css/shares/shares.css" rel="stylesheet">
    </head>
    <body>
        <style>
    *{
    margin:0;
    padding:0;
    }
    body{
    }
    .main{
    width:90%;
    margin:auto;
    }
    .main h2{
    text-align: center;
    margin-top: 5px;
    }
    table{
    border-collapse: collapse;
    border-spacing: 0;
    width:100%;
    }
    table td ,table th{
    border: solid #e2e2e2 1px;
    text-align: center;
    line-height:50px;
    color:#373737;
    background:#fff;
    }
    table th{
    background: #eaf6fd;
    }
    .main .all{
    margin-top:10px;
    }
    .main .all .title{
    font-weight:bold;
    font-size:18px;
    padding:0 0 5px 0;
    }

    </style>

       <div class="main">
            <h2>个股分析</h2>
           <c:forEach var="listMap" items="${cysModelListMap}" varStatus="status">
           <c:set var="keycode" value="${listMap.key}"/>
           <c:set var="models" value="${listMap.value}"/>
           <c:set var="analysisBean" value="${analysisBeanMap[keycode]}"/>
           <div class="all">
              <p class="title">${keycode} 近期分析 </p>
              <p class="title">cys最低值 ${analysisBean.lowestCys} 最低值出现日期 ${analysisBean.lowestDate}</p>
              <p class="title">cys最高值 ${analysisBean.highestCys} 最高值出现日期 ${analysisBean.highestDate}</p>
              <p class="title">cys从最低到最高运行周期 ${analysisBean.t}</p>
              <p class="title">cys卖出信号出现日期 ${analysisBean.zfDate}</p>
              <table>
                  <thead>
                  <tr>
                      <th>股票代码</th>
                      <th>开盘</th>
                      <th>收盘</th>
                      <th>最高</th>
                      <th>最低</th>
                      <th>涨幅</th>
                      <th>成交量</th>
                      <th>短期cys</th>
                      <th>中期cys</th>
                      <th>长期cys</th>
                      <th>短期成本</th>
                      <th>中期成本</th>
                      <th>长期成本</th>
                      <th>日期</th>
                  </tr>
                  </thead>
                  <tbody>
                  <c:forEach var="model" items="${models}" varStatus="status">
                      <tr>
                          <td>${model.code}</td>
                          <td>${model.open}</td>
                          <td>${model.close}</td>
                          <td>${model.high}</td>
                          <td>${model.low}</td>
                          <td>${model.increasePer}</td>
                          <td>${model.volume}</td>
                          <td>${model.cys5}</td>
                          <td>${model.cys13}</td>
                          <td>${model.cys34}</td>
                          <td>${model.cyc5}</td>
                          <td>${model.cyc13}</td>
                          <td>${model.cyc34}</td>
                          <td>${model.date}</td>
                      </tr>
                  </c:forEach>
                  </tbody>
              </table>
          </div>

           </c:forEach>

        </div>
    </body>
</html>