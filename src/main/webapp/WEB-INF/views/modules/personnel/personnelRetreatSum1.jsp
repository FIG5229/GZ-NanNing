<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>离退信息管理</title>
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
        //弹窗
        function openDialog(id,name) {
            var url = "iframe:${ctx}/personnel/personnelRetreatSum/findByUnitId?unitId="+id;
            top.$.jBox.open(url, name,1000,700,{
                persistent: true,  //设置点击窗口外不关闭的参数
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
    <li ><a href="${ctx}/personnel/personnelRetreat?mType=3">离退休信息</a></li>
    <li ><a href="${ctx}/personnel/personnelRetreat/manage">离退休手续办理</a></li>
    <li class="active"><a href="${ctx}/personnel/personnelRetreatSum/statistic">离退休情况统计</a></li>
</ul>
<form:form id="searchForm" modelAttribute="personnelRetreat" action="${ctx}/personnel/personnelRetreatSum/statistic" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>离退日期：</label>
            <input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${personnelRetreat.startDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            -
            <input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${personnelRetreat.endDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
    </ul>
    <ul class="ul-form2">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelRetreatSum/statistic'"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>单位</th>
        <th>现员</th>
        <th>其中男</th>
        <th>其中女</th>
        <th>平均年龄</th>
        <th>55-60岁</th>
        <th>61-65岁</th>
        <th>66-70岁</th>
        <th>71-75岁</th>
        <th>76-80岁</th>
        <th>81岁以上</th>
        <th>离休</th>
        <th>退休</th>
        <th>合计</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${statistic}" var="personnelRetreatSum" varStatus="status">
        <tr>
            <td>
                <a onclick="openDialog('${personnelRetreatSum.unitId}','${personnelRetreatSum.unitName}')">${personnelRetreatSum.unitName}</a>
            </td>
            <td>${personnelRetreatSum.nowMan}</td>
            <td>${personnelRetreatSum.man}</td>
            <td>${personnelRetreatSum.woman}</td>
            <td>${personnelRetreatSum.avgAge}</td>
            <td>${personnelRetreatSum.age1}</td>
            <td>${personnelRetreatSum.age2}</td>
            <td>${personnelRetreatSum.age3}</td>
            <td>${personnelRetreatSum.age4}</td>
            <td>${personnelRetreatSum.age5}</td>
            <td>${personnelRetreatSum.age6}</td>
            <td>${personnelRetreatSum.liXiu}</td>
            <td>${personnelRetreatSum.tuiXiu}</td>
            <td>${personnelRetreatSum.nowMan}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
