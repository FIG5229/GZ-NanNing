<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>内部教官管理</title>
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
<form:form id="searchForm" modelAttribute="affairInteriorInstructorLibrary" action="${ctx}/affair/affairInteriorInstructorLibrary/jiaoGuanDetail?label=${label}" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

</form:form>
<sys:message content="${message}"/>
<div id="contentTable">
    <table  class="table table-striped table-bordered table-condensed">
        <thead>
        <tr>
            <th>序号</th>
            <th>身份证号</th>
            <th>姓名</th>
            <th>单位</th>
            <th>特长</th>
            <th>状态</th>
            <th>创建者</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.list}" var="affairInteriorInstructorLibrary" varStatus="status">
            <tr>
                <td>
                        ${(page.pageNo-1)*page.pageSize+status.index+1}
                </td>
                <td>
                        ${affairInteriorInstructorLibrary.idNumber}
                </td>
                <td>
                        ${affairInteriorInstructorLibrary.name}
                </td>
                <td>
                        ${affairInteriorInstructorLibrary.unit}
                </td>
                <td>
                        ${affairInteriorInstructorLibrary.speciality}
                </td>
                <td>
                        ${affairInteriorInstructorLibrary.perpleState}
                </td>
                <td>
                        ${fns:getUserById(affairInteriorInstructorLibrary.createBy).getName()}
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="pagination">${page}</div>
</body>
</html>