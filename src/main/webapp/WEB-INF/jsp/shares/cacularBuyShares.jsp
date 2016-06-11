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
        <title>预测买入</title>
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
            <h2>预测选股</h2>

           <h2>目标涨幅：${analysisTotal.inc} 容忍时间:${analysisTotal.maxWaitDay}</h2>
           <h2>预测成功的股：${analysisTotal.successNum} 预测失败的股数:${analysisTotal.fallNum} 平均等待时间:${analysisTotal.waitTime} 预测收涨的股:${analysisTotal.increaseNum}</h2>


               <div class="all">
                   <p class="title">${date} 优股预测</p>
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
                           <th>是否成功</th>
                           <th>是否收涨</th>
                       </tr>
                       </thead>
                       <tbody>
                       <c:forEach var="analysisBean" items="${cacularShares}" varStatus="status">
                           <tr>
                               <td><a target="_blank" href="http://gagalulu.wang/shares/analysisByCode?codes=${analysisBean.code}&day=60">${analysisBean.code}</a></td>
                               <td>${analysisBean.open}</td>
                               <td>${analysisBean.close}</td>
                               <td>${analysisBean.high}</td>
                               <td>${analysisBean.low}</td>
                               <td>${analysisBean.increasePer}</td>
                               <td>${analysisBean.volume}</td>
                               <td>${analysisBean.cys5}</td>
                               <td>${analysisBean.cys13}</td>
                               <td>${analysisBean.cys34}</td>
                               <td>${analysisBean.cyc5}</td>
                               <td>${analysisBean.cyc13}</td>
                               <td>${analysisBean.cyc34}</td>
                               <td>${analysisBean.date}</td>
                               <td>${buyTimeList[status.index].success}</td>
                               <td>${buyTimeList[status.index].increasePer>0}</td>
                           </tr>
                       </c:forEach>

                       </tbody>
                   </table>
               </div>

           <c:forEach var="analysisBean" items="${buyTimeList}" varStatus="status">

               <div class="all">
                   <p class="title">${analysisBean.code} ${day}日策略结果</p>
                   <p class="title">策略结果 ${analysisBean.success}</p>
                   <p class="title">等待时间 ${analysisBean.waitDay}</p>
                   <p class="title">收益率 ${analysisBean.increasePer}</p>
                   <p class="title">每股收益 ${analysisBean.increaseVal}</p>
                   <table>
                       <thead>
                       <tr>
                           <th>类型</th>
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
                       <c:set var="minModel" value="${analysisBean.minModel}"></c:set>
                       <c:set var="buyModel" value="${analysisBean.buyModel}"></c:set>
                       <c:set var="sellModel" value="${analysisBean.sellModel}"></c:set>
                       <c:set var="todayModel" value="${analysisBean.todayModel}"></c:set>
                       <tr>
                           <td>计算当日</td>
                           <td>${minModel.open}</td>
                           <td>${minModel.close}</td>
                           <td>${minModel.high}</td>
                           <td>${minModel.low}</td>
                           <td>${minModel.increasePer}</td>
                           <td>${minModel.volume}</td>
                           <td>${minModel.cys5}</td>
                           <td>${minModel.cys13}</td>
                           <td>${minModel.cys34}</td>
                           <td>${minModel.cyc5}</td>
                           <td>${minModel.cyc13}</td>
                           <td>${minModel.cyc34}</td>
                           <td>${minModel.date}</td>
                       </tr>
                       <tr>
                           <td>购买</td>
                           <td>${buyModel.open}</td>
                           <td>${buyModel.close}</td>
                           <td>${buyModel.high}</td>
                           <td>${buyModel.low}</td>
                           <td>${buyModel.increasePer}</td>
                           <td>${buyModel.volume}</td>
                           <td>${buyModel.cys5}</td>
                           <td>${buyModel.cys13}</td>
                           <td>${buyModel.cys34}</td>
                           <td>${buyModel.cyc5}</td>
                           <td>${buyModel.cyc13}</td>
                           <td>${buyModel.cyc34}</td>
                           <td>${buyModel.date}</td>
                       </tr>
                       <tr>
                           <td>卖出</td>
                           <td>${sellModel.open}</td>
                           <td>${sellModel.close}</td>
                           <td>${sellModel.high}</td>
                           <td>${sellModel.low}</td>
                           <td>${sellModel.increasePer}</td>
                           <td>${sellModel.volume}</td>
                           <td>${sellModel.cys5}</td>
                           <td>${sellModel.cys13}</td>
                           <td>${sellModel.cys34}</td>
                           <td>${sellModel.cyc5}</td>
                           <td>${sellModel.cyc13}</td>
                           <td>${sellModel.cyc34}</td>
                           <td>${sellModel.date}</td>
                       </tr>
                       <tr>
                           <td>最近</td>
                           <td>${todayModel.open}</td>
                           <td>${todayModel.close}</td>
                           <td>${todayModel.high}</td>
                           <td>${todayModel.low}</td>
                           <td>${todayModel.increasePer}</td>
                           <td>${todayModel.volume}</td>
                           <td>${todayModel.cys5}</td>
                           <td>${todayModel.cys13}</td>
                           <td>${todayModel.cys34}</td>
                           <td>${todayModel.cyc5}</td>
                           <td>${todayModel.cyc13}</td>
                           <td>${todayModel.cyc34}</td>
                           <td>${todayModel.date}</td>
                       </tr>
                       </tbody>
                   </table>
               </div>
           </c:forEach>

        </div>
    </body>
</html>