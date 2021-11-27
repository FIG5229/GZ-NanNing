<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>任前廉政鉴定管理</title>
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

<form:form id="searchForm" modelAttribute="affairRqlzIntegrity" action="" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="year" name="year" type="hidden" value="${affairRqlzIntegrity.year}"/>
    <input id="month" name="month" type="hidden" value="${affairRqlzIntegrity.month}"/>
    <input id="dateStart" name="dateStart" type="hidden" value="${affairRqlzIntegrity.dateStart}"/>
    <input id="dateEnd" name="dateEnd" type="hidden" value="${affairRqlzIntegrity.dateEnd}"/>
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
        <th>鉴定类型</th>
        <th>鉴定时间</th>
        <th>鉴定单位</th>
        <th>是否同意</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairRqlzIntegrity" varStatus="status">
        <tr>
            <td>
                    ${(page.pageNo-1)*page.pageSize+status.index+1}
            </td>
            <td>
                    ${affairRqlzIntegrity.name}
            </td>
            <td>
                    ${affairRqlzIntegrity.lzUnit}
            </td>
            <td>
                    ${affairRqlzIntegrity.job}
            </td>
            <td>
                    ${fns:getDictLabel(affairRqlzIntegrity.type, 'affair_lzjd', '')}
            </td>
            <td>
                <fmt:formatDate value="${affairRqlzIntegrity.foundDate}" pattern="yyyy-MM-dd"/>
            </td>
            <td>
                    ${fns:getDictLabel(affairRqlzIntegrity.jdUnit, 'affair_xfjb_unit', '')}
            </td>
            <td>
                    ${fns:getDictLabel(affairRqlzIntegrity.isAgree, 'yes_no', '')}
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>