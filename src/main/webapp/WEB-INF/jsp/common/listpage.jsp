<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:set var="pno" value="${listPage.pno}"></c:set>
<c:set var="allPage" value="${listPage.allPage}"></c:set>
<c:set var="listpageUri" value="/blog/index?pno="></c:set>
<c:if test="${pno-1>=0}">
    <c:set var="prepno" value="${pno-1}"></c:set>
</c:if>
<c:if test="${pno-1<0}">
    <c:set var="prepno" value="0"></c:set>
</c:if>
<c:if test="${pno+1>=allPage}">
    <c:set var="nextpno" value="${allPage-1}"></c:set>
</c:if>
<c:if test="${pno+1<allPage}">
    <c:set var="nextpno" value="${pno+1}"></c:set>
</c:if>
<div class="pagelistfram cl">
    <div class="pagelist h-c">
        <a class="pageitem" href="${listpageUri}0">首页</a>
        <a class="pageitem" href="${listpageUri}${prepno}">上一页</a>
        <c:choose>
            <c:when test="${allPage<=6 || (allPage>6 && pno>allPage-5)}">
                <c:if test="${allPage<=6}">
                    <c:set var="beginPage" value="1"></c:set>
                </c:if>
                <c:if test="${allPage>6 && pno>allPage-5}">
                    <c:set var="beginPage" value="${allPage-5}"></c:set>
                </c:if>
                <c:set var="endPage" value="${allPage}"></c:set>
                <c:forEach var="i" begin="${beginPage}" end="${endPage}" step="1">
                    <c:if test="${pno == i-1}">
                        <a class="pageitem current">${i}</a>
                    </c:if>
                    <c:if test="${pno != i-1}">
                        <a class="pageitem" href="${listpageUri}${i-1}">${i}</a>
                    </c:if>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${pno>2}">
                        <c:set var="beginPage" value="${pno-2}"></c:set>
                    </c:when>
                    <c:otherwise>
                        <c:set var="beginPage" value="0"></c:set>
                    </c:otherwise>
                </c:choose>
                <c:forEach var="i" begin="1" end="6" step="1">
                    <c:if test="${i<=3}">
                        <c:if test="${pno == beginPage+i-1}">
                            <a class="pageitem current">${beginPage+i}</a>
                        </c:if>
                        <c:if test="${pno != beginPage+i-1}">
                            <a class="pageitem" href="${listpageUri}${beginPage+i-1}">${beginPage+i}</a>
                        </c:if>
                    </c:if>
                    <c:if test="${i==4}">
                        <a class="pageitem">……</a>
                    </c:if>
                    <c:if test="${i>4}">
                        <a class="pageitem" href="${listpageUri}${allPage-7+i}">${allPage-6+i}</a>
                    </c:if>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <a class="pageitem" href="${listpageUri}${nextpno}">下一页</a>
    </div>
</div>
<!-- listpage end -->