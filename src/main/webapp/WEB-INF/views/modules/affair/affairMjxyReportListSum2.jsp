<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>民警休养申报统计汇总2</title>
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
        //明细弹窗
        function openDialog(id,type) {
            var url = "iframe:${ctx}/affair/affairMjxyReport/mingXi?unitId="+id+"&type="+type;
            if (type == null || type == undefined){
                url = "iframe:${ctx}/affair/affairMjxyReport/mingXi?unitId="+id;
            }
            top.$.jBox.open(url, "休养明细",1000,500,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        };
    </script>
</head>
<body>
<%--点单位弹出的页面--%>
<form:form id="searchForm" modelAttribute="affairMjxyReport" action="${ctx}/affair/affairMjxyReport/findByUnitId" method="post" class="breadcrumb form-search">
    <input id="unitId" name="unitId" type="hidden" value="${affairMjxyReportStatistic.unitId}"/>
    <ul class="ul-form">
        <li><label style="width: 100px;">休养开始时间：</label>
            <input name="beginStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairMjxyReport.beginStartDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            至
            <input name="finishStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairMjxyReport.finishStartDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li><label style="width: 100px;">休养结束时间：</label>
            <input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairMjxyReport.beginDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            至
            <input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairMjxyReport.finishDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li><label>休养类型：</label>
            <form:select path="type" class="input-medium">
                <form:options items="${fns:getDictList('affair_xiuyang')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>排序：</label>
            <form:select path="sort" class="input-medium">
                <form:options items="${fns:getDictList('affair_mjxy_sort')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
    </ul>
    <ul class="ul-form2">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <shiro:hasPermission name="affair:affairMjxyReport:edit">
            <li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
        </shiro:hasPermission>
        <li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairMjxyReport/findByUnitId?unitId=${affairMjxyReportStatistic.unitId}'"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>责任单位</th>
        <th>局管内</th>
        <th>局管外</th>
        <th>劳模</th>
        <th>其他</th>
        <th>合计</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${statistic}" var="affairMjxyReportStatistic">
        <tr>
            <td>
                ${affairMjxyReportStatistic.unitName}
            </td>
            <td>
                <a href="#" onclick="openDialog('${affairMjxyReportStatistic.unitId}',1)">${affairMjxyReportStatistic.juGuanNei}</a>
            </td>
            <td>
                <a href="#" onclick="openDialog('${affairMjxyReportStatistic.unitId}',2)">${affairMjxyReportStatistic.juGuanWai}</a>
            </td>
            <td>
                <a href="#" onclick="openDialog('${affairMjxyReportStatistic.unitId}',3)"> ${affairMjxyReportStatistic.laoMo}</a>
            </td>
            <td>
                <a href="#" onclick="openDialog('${affairMjxyReportStatistic.unitId}',4)"> ${affairMjxyReportStatistic.other}</a>
            </td>
            <td>
                <a href="#" onclick="openDialog('${affairMjxyReportStatistic.unitId}')"> ${affairMjxyReportStatistic.sum}</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>