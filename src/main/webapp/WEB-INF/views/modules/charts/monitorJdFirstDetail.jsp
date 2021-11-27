<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>廉政监督管理</title>
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

<form:form id="searchForm" modelAttribute="affairLianzhengSupervise" action="" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="year" name="year" type="hidden" value="${affairLianzhengSupervise.year}"/>
    <input id="month" name="month" type="hidden" value="${affairLianzhengSupervise.month}"/>
    <input id="dateStart" name="dateStart" type="hidden" value="${affairLianzhengSupervise.dateStart}"/>
    <input id="dateEnd" name="dateEnd" type="hidden" value="${affairLianzhengSupervise.dateEnd}"/>
    <ul class="ul-form">
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>序号</th>
        <th>时间</th>
        <th>参加人员</th>
        <th>主办单位</th>
        <th>项目名称</th>
        <th>金额</th>
        <th>监督单位</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairLianzhengSupervise" varStatus="status">
        <tr>
            <td>
                    ${(page.pageNo-1)*page.pageSize+status.index+1}
            </td>
            <td>
                <fmt:formatDate value="${affairLianzhengSupervise.foundDate}" pattern="yyyy-MM-dd"/>
            </td>
            <td>
                    ${affairLianzhengSupervise.joinPeople}
            </td>
            <td>
                    ${affairLianzhengSupervise.zbUnit}
            </td>
            <td>
                    ${affairLianzhengSupervise.title}
            </td>
            <td>
                    ${affairLianzhengSupervise.money}
            </td>
            <td>
                    ${fns:getDictLabel(affairLianzhengSupervise.jdUnit, 'affair_xfjb_unit', '')}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>