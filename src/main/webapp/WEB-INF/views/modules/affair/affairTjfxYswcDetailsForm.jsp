<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>民警因私外出</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#print").click(function () {
                $('#handleTh').css('display', 'none');
                $('.handleTd').css('display', 'none');
                $('#checkTh').css('display', 'none');
                $('.checkTd').css('display', 'none');
                $("#contentTable").printThis({
                    debug: false,
                    importCSS: false,
                    importStyle: false,
                    printContainer: true,
                    loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css", "${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css", "${ctxStatic}/common/jeesite.css"],
                    pageTitle: "打印",
                    removeInline: false,
                    printDelay: 333,
                    header: null,
                    formValues: false,
                    afterPrint: function () {
                        $('#handleTh').css('display', 'table-cell');
                        $('.handleTd').css('display', 'table-cell');
                        $('#checkTh').css('display', 'table-cell');
                        $('.checkTd').css('display', 'table-cell');
                    }
                });
            });
        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

    </script>
</head>
<body>
<form:form id="searchForm" modelAttribute="affairYjGoOutReport" action="${ctx}/affair/affairYjGoOutReport/tjfxFormDetail?size=${affairYjGoOutReport.size}" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>序号</th>
        <th>姓名</th>
        <th>事由</th>
        <th>部门名称</th>
        <th>前往地区</th>
        <th>离开时间</th>
        <th>返回时间</th>
        <th>审核状态</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairYjGoOutReport" varStatus="status">
        <tr>
            <td>
                    ${(page.pageNo-1)*page.pageSize+status.index+1}
            </td>
            <td>
                    ${affairYjGoOutReport.name}
            </td>
            <td>
                    ${affairYjGoOutReport.thing}
            </td>
            <td>
                    ${affairYjGoOutReport.unit}
            </td>
            <td>
                    ${affairYjGoOutReport.goPlace}
            </td>
            <td>
                <fmt:formatDate value="${affairYjGoOutReport.leaveTime}" pattern="yyyy-MM-dd HH:mm"/>
            </td>
            <td>
                <fmt:formatDate value="${affairYjGoOutReport.backTime}" pattern="yyyy-MM-dd HH:mm"/>
            </td>
            <td>
                    ${fns:getDictLabel(affairYjGoOutReport.checkType, 'check_type', '')}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>