<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>新闻宣传管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {

        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

    </script>
</head>
<body>

<form:form id="searchForm" modelAttribute="affairNews" action="" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="year" name="year" type="hidden" value="${affairNews.year}"/>
    <input id="month" name="month" type="hidden" value="${affairNews.month}"/>
    <input id="dateStart" name="dateStart" type="hidden" value="${affairNews.startDate}"/>
    <input id="dateEnd" name="dateEnd" type="hidden" value="${affairNews.endDate}"/>

</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>序号</th>
        <th>新闻标题</th>
        <th>媒体名称</th>
        <th>栏目</th>
        <th>作者</th>
        <%--<th>篇幅字数</th>--%>
        <th>刊发时间</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairNews" varStatus="status" >
        <tr>
            <td>
                    ${(page.pageNo-1)*page.pageSize+status.index+1}
            </td>
            <td>
                    ${affairNews.title}
            </td>
            <td>
                    ${affairNews.mediaName}
            </td>
            <td>
                    ${affairNews.lm}
            </td>
            <td>
                    ${affairNews.author}
            </td>
            <%--<td>
                    ${affairNews.wordNum}
            </td>--%>
            <td>
                <fmt:formatDate value="${affairNews.date}" pattern="yyyy-MM-dd"/>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>