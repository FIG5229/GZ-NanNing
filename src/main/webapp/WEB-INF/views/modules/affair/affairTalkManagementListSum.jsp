<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>谈话函询统计汇总</title>
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
    <li><a href="${ctx}/affair/affairTalkManagement/">谈话函询</a></li>
    <li class="active"><a href="${ctx}/affair/affairTalkManagement/affairTalkManagementListSum">统计汇总</a></li>
    <%--<shiro:hasPermission name="affair:affairTalkManagement:edit"><li><a href="${ctx}/affair/affairTalkManagement/form">谈话函询添加</a></li></shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="affairTalkManagement" action="${ctx}/affair/affairTalkManagement/affairTalkManagementListSum" method="post" class="breadcrumb form-search">
    <ul class="ul-form">
        <li><label>时间：</label>
            <input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairTalkManagement.startDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            至
            <input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairTalkManagement.endDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li><label>单位：</label>
            <sys:treeselect id="unit" name="unitId" value="${affairTalkManagement.unitId}" labelName="unit" labelValue="${affairTalkManagement.unit}"
                            title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <shiro:hasPermission name="affair:affairTalkManagement:edit">
            <li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
        </shiro:hasPermission>
        <li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairTalkManagement/affairTalkManagementListSum'"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
        <tr>
            <th>类型</th>
            <th>数量</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${list}" var="m">
            <tr>
                <td>${fns:getDictLabel(m.category, 'affair_tanhua', '')}</td>
                <td>${m.num}</td>
            </tr>
        </c:forEach>
        <tr>
            <td>合计</td>
            <td>${sum}</td>
        </tr>
    </tbody>
</table>
</body>
</html>