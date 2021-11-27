<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>单位集体奖励表管理</title>
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

<form:form id="searchForm" modelAttribute="affairXcUnitReward" action="" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="year" name="year" type="hidden" value="${affairXcUnitReward.year}"/>
    <input id="month" name="month" type="hidden" value="${affairXcUnitReward.month}"/>
    <input id="dateStart" name="dateStart" type="hidden" value="${affairXcUnitReward.startDate}"/>
    <input id="dateEnd" name="dateEnd" type="hidden" value="${affairXcUnitReward.endDate}"/>

</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>序号</th>
        <th>批准时间</th>
        <th>受奖单位名称</th>
        <th>批准单位</th>
        <th>奖励名称</th>
        <th>批准文号</th>
        <th>推送状态</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairXcUnitReward" varStatus="status">
        <tr>
            <td>
                    ${(page.pageNo-1)*page.pageSize+status.index+1}
            </td>
            <td>
                <fmt:formatDate value="${affairXcUnitReward.date}" pattern="yyyy-MM-dd"/>
            </td>
            <td>
                    ${affairXcUnitReward.unit}
            </td>
            <td>
                    ${affairXcUnitReward.approvalUnit}
            </td>
            <td>
                    ${affairXcUnitReward.name}
            </td>
            <td>
                    ${affairXcUnitReward.fileNo}
            </td>
            <td>
                    ${fns:getDictLabel(affairXcUnitReward.pushType, 'push_types', '')}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>