<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>谈话函询管理</title>
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
<form:form id="searchForm" modelAttribute="affairTalkManagement" action="" method="post" class="breadcrumb form-search">
<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
<input id="year" name="year" type="hidden" value="${affairTalkManagement.year}"/>
<input id="month" name="month" type="hidden" value="${affairTalkManagement.month}"/>
<input id="dateStart" name="dateStart" type="hidden" value="${affairTalkManagement.dateStart}"/>
<input id="dateEnd" name="dateEnd" type="hidden" value="${affairTalkManagement.dateEnd}"/>
<ul class="ul-form">

    </form:form>
    <sys:message content="${message}"/>
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead>
        <tr>
            <th>序号</th>
            <th>姓名</th>
            <th>单位</th>
            <th>职务</th>
            <th>职级</th>
            <th>政治面貌</th>
            <th>主办部门</th>
            <th>类型</th>
            <th>谈话人</th>
            <th>谈话时间</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.list}" var="affairTalkManagement" varStatus="status">
            <tr>
                <td>
                        ${(page.pageNo-1)*page.pageSize+status.index+1}
                </td>
                <td>
                        ${affairTalkManagement.name}
                </td>
                <td>
                        ${affairTalkManagement.unit}
                </td>
                <td>
                        ${affairTalkManagement.job}
                </td>
                <td>
                        ${affairTalkManagement.jobLevel}
                </td>
                <td>
                        ${fns:getDictLabel(affairTalkManagement.mianmao, 'political_status', '')}
                </td>
                <td>
                        ${fns:getDictLabel(affairTalkManagement.zbUnit, 'affair_zb_unit', '')}
                </td>
                <td>
                        ${fns:getDictLabel(affairTalkManagement.letterCategory, 'affair_tanhua', '')}
                </td>
                <td>
                        ${affairTalkManagement.talker}
                </td>
                <td>
                    <fmt:formatDate value="${affairTalkManagement.time}" pattern="yyyy-MM-dd"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagination">${page}</div>
</body>
</html>