<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>个人奖励管理</title>
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
<form:form id="searchForm" modelAttribute="affairPersonalReward" action="" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="year" name="year" type="hidden" value="${affairPersonalReward.year}"/>
    <input id="month" name="month" type="hidden" value="${affairPersonalReward.month}"/>
    <input id="dateStart" name="dateStart" type="hidden" value="${affairPersonalReward.startDate}"/>
    <input id="dateEnd" name="dateEnd" type="hidden" value="${affairPersonalReward.endDate}"/>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>序号</th>
        <th>姓名</th>
        <th>性别</th>
        <th>单位</th>
        <th>批准时间</th>
        <th>批准机关名称</th>
        <th>奖励名称代码</th>
        <th>批准文件文号</th>
        <th>推送状态</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairPersonalReward" varStatus="status">
        <tr>
            <td>
                    ${(page.pageNo-1)*page.pageSize+status.index+1}
            </td>
            <td>
                    ${affairPersonalReward.name}
            </td>
            <td>
                    ${fns:getDictLabel(affairPersonalReward.sex, 'sex', '')}
            </td>
            <td>
                    ${affairPersonalReward.approvalUnit}
            </td>
            <td>
                <fmt:formatDate value="${affairPersonalReward.date}" pattern="yyyy-MM-dd"/>
            </td>
            <td>
                    ${affairPersonalReward.unit}
            </td>
            <td>
                    ${fns:getDictLabel(affairPersonalReward.rewardName, 'affair_personnel_rewardCode', '')}
            </td>
            <td>
                    ${affairPersonalReward.fileNo}
            </td>
            <td>
                    ${fns:getDictLabel(affairPersonalReward.pushType, 'push_types', '')}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>