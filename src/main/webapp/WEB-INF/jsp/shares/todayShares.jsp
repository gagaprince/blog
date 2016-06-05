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
        <title>大盘分析</title>
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
            <h2>今日分析</h2>
            <div class="all">
                <p class="title">${shareDate} 大盘</p>
                <table>
                    <thead>
                        <tr>
                            <th>指数名称</th>
                            <th>开盘</th>
                            <th>收盘</th>
                            <th>最高</th>
                            <th>最低</th>
                            <th>涨幅</th>
                            <th>成交量</th>
                            <th>周均</th>
                            <th>月均</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="model" items="${zhiModels}" varStatus="status">
                        <tr>
                            <td>${model.code}</td>
                            <td>${model.open}</td>
                            <td>${model.close}</td>
                            <td>${model.high}</td>
                            <td>${model.low}</td>
                            <td>${model.increasePer}</td>
                            <td>${model.volume}</td>
                            <td>${model.sixMean}</td>
                            <td>${model.tweentyMean}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="all">
                <p class="title">${shareDate} 涨幅高的股票</p>
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
                            <th>周均</th>
                            <th>月均</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="model" items="${highModels}" varStatus="status">
                            <tr>
                                <td>${model.code}</td>
                                <td>${model.open}</td>
                                <td>${model.close}</td>
                                <td>${model.high}</td>
                                <td>${model.low}</td>
                                <td>${model.increasePer}</td>
                                <td>${model.volume}</td>
                                <td>${model.sixMean}</td>
                                <td>${model.tweentyMean}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="all">
                <p class="title">${shareDate} 跌幅高的股票</p>
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
                            <th>周均</th>
                            <th>月均</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="model" items="${lowModels}" varStatus="status">
                            <tr>
                                <td>${model.code}</td>
                                <td>${model.open}</td>
                                <td>${model.close}</td>
                                <td>${model.high}</td>
                                <td>${model.low}</td>
                                <td>${model.increasePer}</td>
                                <td>${model.volume}</td>
                                <td>${model.sixMean}</td>
                                <td>${model.tweentyMean}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
           <div class="all">
               <p class="title">${shareDate} 短期超跌股</p>
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
                       <th>周均</th>
                       <th>月均</th>
                       <th>短期cys</th>
                       <th>中期cys</th>
                       <th>长期cys</th>
                       <th>短期成本</th>
                       <th>中期成本</th>
                       <th>长期成本</th>
                   </tr>
                   </thead>
                   <tbody>
                   <c:forEach var="model" items="${cys5LowModels}" varStatus="status">
                       <tr>
                           <td>${model.code}</td>
                           <td>${model.open}</td>
                           <td>${model.close}</td>
                           <td>${model.high}</td>
                           <td>${model.low}</td>
                           <td>${model.increasePer}</td>
                           <td>${model.volume}</td>
                           <td>${model.sixMean}</td>
                           <td>${model.tweentyMean}</td>
                           <td>${model.cys5}</td>
                           <td>${model.cys13}</td>
                           <td>${model.cys34}</td>
                           <td>${model.cyc5}</td>
                           <td>${model.cyc13}</td>
                           <td>${model.cyc34}</td>
                       </tr>
                   </c:forEach>
                   </tbody>
               </table>
           </div>
           <div class="all">
               <p class="title">${shareDate} 中期超跌股</p>
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
                       <th>周均</th>
                       <th>月均</th>
                       <th>短期cys</th>
                       <th>中期cys</th>
                       <th>长期cys</th>
                       <th>短期成本</th>
                       <th>中期成本</th>
                       <th>长期成本</th>
                   </tr>
                   </thead>
                   <tbody>
                   <c:forEach var="model" items="${cys13LowModels}" varStatus="status">
                       <tr>
                           <td>${model.code}</td>
                           <td>${model.open}</td>
                           <td>${model.close}</td>
                           <td>${model.high}</td>
                           <td>${model.low}</td>
                           <td>${model.increasePer}</td>
                           <td>${model.volume}</td>
                           <td>${model.sixMean}</td>
                           <td>${model.tweentyMean}</td>
                           <td>${model.cys5}</td>
                           <td>${model.cys13}</td>
                           <td>${model.cys34}</td>
                           <td>${model.cyc5}</td>
                           <td>${model.cyc13}</td>
                           <td>${model.cyc34}</td>
                       </tr>
                   </c:forEach>
                   </tbody>
               </table>
           </div>
           <div class="all">
               <p class="title">${shareDate} 长线超跌股</p>
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
                       <th>周均</th>
                       <th>月均</th>
                       <th>短期cys</th>
                       <th>中期cys</th>
                       <th>长期cys</th>
                       <th>短期成本</th>
                       <th>中期成本</th>
                       <th>长期成本</th>
                   </tr>
                   </thead>
                   <tbody>
                   <c:forEach var="model" items="${cys34LowModels}" varStatus="status">
                       <tr>
                           <td>${model.code}</td>
                           <td>${model.open}</td>
                           <td>${model.close}</td>
                           <td>${model.high}</td>
                           <td>${model.low}</td>
                           <td>${model.increasePer}</td>
                           <td>${model.volume}</td>
                           <td>${model.sixMean}</td>
                           <td>${model.tweentyMean}</td>
                           <td>${model.cys5}</td>
                           <td>${model.cys13}</td>
                           <td>${model.cys34}</td>
                           <td>${model.cyc5}</td>
                           <td>${model.cyc13}</td>
                           <td>${model.cyc34}</td>
                       </tr>
                   </c:forEach>
                   </tbody>
               </table>
           </div>
           <div class="all">
               <p class="title">${shareDate} 短期超涨股</p>
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
                       <th>周均</th>
                       <th>月均</th>
                       <th>短期cys</th>
                       <th>中期cys</th>
                       <th>长期cys</th>
                       <th>短期成本</th>
                       <th>中期成本</th>
                       <th>长期成本</th>
                   </tr>
                   </thead>
                   <tbody>
                   <c:forEach var="model" items="${cys5HighModels}" varStatus="status">
                       <tr>
                           <td>${model.code}</td>
                           <td>${model.open}</td>
                           <td>${model.close}</td>
                           <td>${model.high}</td>
                           <td>${model.low}</td>
                           <td>${model.increasePer}</td>
                           <td>${model.volume}</td>
                           <td>${model.sixMean}</td>
                           <td>${model.tweentyMean}</td>
                           <td>${model.cys5}</td>
                           <td>${model.cys13}</td>
                           <td>${model.cys34}</td>
                           <td>${model.cyc5}</td>
                           <td>${model.cyc13}</td>
                           <td>${model.cyc34}</td>
                       </tr>
                   </c:forEach>
                   </tbody>
               </table>
           </div>
           <div class="all">
               <p class="title">${shareDate} 中期超涨股</p>
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
                       <th>周均</th>
                       <th>月均</th>
                       <th>短期cys</th>
                       <th>中期cys</th>
                       <th>长期cys</th>
                       <th>短期成本</th>
                       <th>中期成本</th>
                       <th>长期成本</th>
                   </tr>
                   </thead>
                   <tbody>
                   <c:forEach var="model" items="${cys13HighModels}" varStatus="status">
                       <tr>
                           <td>${model.code}</td>
                           <td>${model.open}</td>
                           <td>${model.close}</td>
                           <td>${model.high}</td>
                           <td>${model.low}</td>
                           <td>${model.increasePer}</td>
                           <td>${model.volume}</td>
                           <td>${model.sixMean}</td>
                           <td>${model.tweentyMean}</td>
                           <td>${model.cys5}</td>
                           <td>${model.cys13}</td>
                           <td>${model.cys34}</td>
                           <td>${model.cyc5}</td>
                           <td>${model.cyc13}</td>
                           <td>${model.cyc34}</td>
                       </tr>
                   </c:forEach>
                   </tbody>
               </table>
           </div>
           <div class="all">
               <p class="title">${shareDate} 长期超涨股</p>
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
                       <th>周均</th>
                       <th>月均</th>
                       <th>短期cys</th>
                       <th>中期cys</th>
                       <th>长期cys</th>
                       <th>短期成本</th>
                       <th>中期成本</th>
                       <th>长期成本</th>
                   </tr>
                   </thead>
                   <tbody>
                   <c:forEach var="model" items="${cys34HighModels}" varStatus="status">
                       <tr>
                           <td>${model.code}</td>
                           <td>${model.open}</td>
                           <td>${model.close}</td>
                           <td>${model.high}</td>
                           <td>${model.low}</td>
                           <td>${model.increasePer}</td>
                           <td>${model.volume}</td>
                           <td>${model.sixMean}</td>
                           <td>${model.tweentyMean}</td>
                           <td>${model.cys5}</td>
                           <td>${model.cys13}</td>
                           <td>${model.cys34}</td>
                           <td>${model.cyc5}</td>
                           <td>${model.cyc13}</td>
                           <td>${model.cyc34}</td>
                       </tr>
                   </c:forEach>
                   </tbody>
               </table>
           </div>

        </div>
    </body>
</html>