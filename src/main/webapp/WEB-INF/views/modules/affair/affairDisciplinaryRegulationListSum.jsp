<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>纪律规定统计汇总</title>
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
    <li><a href="${ctx}/affair/affairDisciplinaryRegulation/">纪律处分</a></li>
    <li class="active"><a href="${ctx}/affair/affairDisciplinaryRegulation/affairDisciplinaryRegulationListSum">统计汇总</a></li>
    <%--<shiro:hasPermission name="affair:affairDisciplinaryAction:edit"><li><a href="${ctx}/affair/affairDisciplinaryAction/form">纪律处分添加</a></li></shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="affairDisciplinaryRegulation" action="${ctx}/affair/affairDisciplinaryRegulation/" method="post" class="breadcrumb form-search">
    <ul class="ul-form">
        <li><label>录入时间：</label>
            <input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value=""
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            至
            <input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value=""
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li><label>收到时间：</label>
            <input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value=""
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            至
            <input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value=""
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li><label>违纪问题性质：</label>
            <form:select path="nature" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_wenti')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>处分种类：</label>
            <form:select path="disciplinaryType" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_xzchufen')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>单位：</label>
            <sys:treeselect id="unit" name="unitId" value="${affairDisciplinaryRegulation.unitId}" labelName="unit" labelValue="${affairDisciplinaryRegulation.unit}"
                            title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <shiro:hasPermission name="affair:affairDisciplinaryRegulation:edit">
            <li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
        </shiro:hasPermission>
        <li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairDisciplinaryAction'"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>类别</th>
        <th>数量</th>
        <th>重复件统计数量</th>
        <th>属实数量</th>
        <th>未属实数量</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairDisciplinaryRegulation">
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
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>