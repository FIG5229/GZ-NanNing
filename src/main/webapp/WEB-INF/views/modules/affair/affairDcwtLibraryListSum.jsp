<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>督察问题库管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#print").click(function(){
                $("#contentTable").printThis({
                    debug: false,
                    importCSS: false,
                    importStyle: false,
                    printContainer: true,
                    loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css"],
                    pageTitle: "打印",
                    removeInline: false,
                    printDelay: 333,
                    header: null,
                    formValues: false
                });
            });
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/affair/affairDcwtLibrary/">督察问题库</a></li>
    <li class="active"><a href="${ctx}/affair/affairDcwtLibrary/affairDcwtLibraryListSum">统计汇总</a></li>
    <%--		<shiro:hasPermission name="affair:affairDcwtLibrary:edit"><li><a href="${ctx}/affair/affairDcwtLibrary/form">督察问题库添加</a></li></shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="affairDcwtLibrary" action="${ctx}/affair/affairDcwtLibrary/" method="post" class="breadcrumb form-search">
    <ul class="ul-form">
        <li><label>时间：</label>
            <input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairDcwtLibrary.startDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            至
            <input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairDcwtLibrary.endDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li><label>问题类别：</label>
            <form:select path="problemCategory" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_wtlb')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <shiro:hasPermission name="affair:affairDcwtLibrary:edit">
            <li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
        </shiro:hasPermission>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>责任单位</th>
        <th>发现问题个数</th>
        <th>全局问题总数占比</th>
        <th>全年问题总数占比</th>
        <th>执法监督类问题及占比</th>
        <th>已整改问题及占比</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairDcwtLibrary">
        <tr>
            <td>

            </td>
            <td>

            </td>
            <td>

            </td>
            <td>

            </td>
            <td>

            </td>
            <td>

            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>