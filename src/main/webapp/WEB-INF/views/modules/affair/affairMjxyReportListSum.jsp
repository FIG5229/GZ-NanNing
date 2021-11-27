<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>民警休养申报统计汇总</title>
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
        //弹窗
        function openDialog(id,name) {
            var url = "iframe:${ctx}/affair/affairMjxyReport/findByUnitId?unitId="+id;
            top.$.jBox.open(url, name,1000,700,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        };
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/affair/affairMjxyReport?mType=3">休养申报</a></li>
    <shiro:hasPermission name="affair:affairMjxyReportSum:view"><li><a href="${ctx}/affair/affairMjxyReportSum/">汇总审核</a></li></shiro:hasPermission>
    <shiro:hasPermission name="affair:affairMjxyReportSum:view"><li class="active"><a href="${ctx}/affair/affairMjxyReport/affairMjxyReportListSum?type=1&sort=1">统计汇总</a></li></shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="affairMjxyReport" action="${ctx}/affair/affairMjxyReport/affairMjxyReportListSum" method="post" class="breadcrumb form-search">
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
        <li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairMjxyReport/affairMjxyReportListSum'"/></li>
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

                <c:choose>
                    <c:when test="${'南宁铁路公安局' eq affairMjxyReportStatistic.unitName}">
                        <a href="#" onclick="openDialog('${affairMjxyReportStatistic.unitId}','${affairMjxyReportStatistic.unitName}')">公安局机关</a>
                    </c:when>
                    <c:otherwise>
                        <a href="#" onclick="openDialog('${affairMjxyReportStatistic.unitId}','${affairMjxyReportStatistic.unitName}')">${affairMjxyReportStatistic.unitName}</a>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                ${affairMjxyReportStatistic.juGuanNei}
            </td>
            <td>
                ${affairMjxyReportStatistic.juGuanWai}
            </td>
            <td>
                ${affairMjxyReportStatistic.laoMo}
            </td>
            <td>
                    ${affairMjxyReportStatistic.other}
            </td>
            <td>
                ${affairMjxyReportStatistic.sum}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>