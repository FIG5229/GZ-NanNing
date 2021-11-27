<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>基本知识成绩管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
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

<form:form id="searchForm" modelAttribute="affairBasicKnowledge" action="${ctx}/affair/affairBasicKnowledge/basicKnoledgeDetail?status=${status}" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>序号</th>
        <th>考试</th>
        <th>警号</th>
        <th>姓名</th>
        <th>证件号码</th>
        <th>机构</th>
        <th>交卷时间</th>
        <th>最终成绩</th>
        <th>通过状态</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairBasicKnowledge" varStatus="status">
        <tr>
            <td>
                    ${(page.pageNo-1)*page.pageSize+status.index+1}
                    <%--								${affairBasicKnowledge.number}--%>
            </td>
            <td>
                    ${affairBasicKnowledge.exam}
            </td>
            <td>
                    ${affairBasicKnowledge.policeNo}
            </td>
            <td>
                    ${affairBasicKnowledge.name}
            </td>
            <td>
                    ${affairBasicKnowledge.idNumber}
            </td>
            <td>
                    ${affairBasicKnowledge.unit}
            </td>
            <td>
                <fmt:formatDate value="${affairBasicKnowledge.handoverTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                    ${affairBasicKnowledge.finalResult}
            </td>
            <td>
                    ${fns:getDictLabel(affairBasicKnowledge.status, 'pass_status', '')}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>