<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>民警小家建设统计汇总</title>
    <meta name="decorator" content="default"/>
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
    <li><a href="${ctx}/affair/affairPoliceHome/">民警小家建设</a></li>
    <li class="active"><a href="${ctx}/affair/affairPoliceHome/affairPoliceHomeListSum">统计汇总</a></li>
    <%--<shiro:hasPermission name="affair:affairPoliceHome:edit"><li><a href="${ctx}/affair/affairPoliceHome/form">民警小家建设添加</a></li></shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="affairPoliceHome" action="${ctx}/affair/affairPoliceHome/" method="post" class="breadcrumb form-search">
    <ul class="ul-form">
        <li><label>申报时间：</label>
            <input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairPoliceHome.beginDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            至
            <input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairPoliceHome.finishDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li><label>排序：</label>
            <form:select path="type" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_jianshe')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <shiro:hasPermission name="affair:affairPoliceHome:edit">
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
        <th>小种养</th>
        <th>生活（设施）建设</th>
        <th>文体设施（设备）建设</th>
        <th>其他建设</th>
    </tr>
    </thead>
    <tbody>
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
        </tr>
    </tbody>
</table>
</body>
</html>