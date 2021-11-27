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
            var url = "iframe:${ctx}/affair/affairQjSum/findByUnitId?unitId="+id;
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
    <li><a href="${ctx}/affair/affairQj">请假信息</a></li>
  <%--  <li><a href="${ctx}/affair/affairQj?mType=1">人事审核</a></li>
    <li ><a href="${ctx}/affair/affairQj?mType=2">部门审核</a></li>
    <li><a href="${ctx}/affair/affairQj?mType=3">局处审核</a></li>--%>
    <li><a href="${ctx}/affair/affairQjCount/count">请假信息汇总</a></li>
    <li class="active"><a href="${ctx}/affair/affairQjSum/statistic">请假信息统计</a></li>
</ul>
<form:form id="searchForm" modelAttribute="affairQj" action="${ctx}/affair/affairQjSum/statistic" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label class="width140">请假开始时间：</label>
            <input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairQj.startDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            -
            <input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairQj.endDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
    </ul>
    <ul class="ul-form2">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairQjSum/list'"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th style="text-align: center">单位</th>
        <th style="text-align: center">年休假</th>
        <th style="text-align: center">探亲假</th>
        <th style="text-align: center">婚假</th>
        <th style="text-align: center">病假</th>
        <th style="text-align: center">陪产假</th>
        <th style="text-align: center">产假</th>
        <th style="text-align: center">丧假</th>
        <th style="text-align: center">事假</th>
        <th style="text-align: center">工伤</th>
        <th style="text-align: center">出差</th>
        <th style="text-align: center">联系工作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${statistic}" var="affairQjSum" varStatus="status">
        <tr>
            <td style="text-align: center">
                <a href="javascript:void(0)" onclick="openDialog('${affairQjSum.unitId}','${affairQjSum.unitName}')">${affairQjSum.unitName}</a>
            </td>
            <td style="text-align: center">${affairQjSum.nianXiu}</td>
            <td style="text-align: center">${affairQjSum.tanQin}</td>
            <td style="text-align: center">${affairQjSum.hun}</td>
            <td style="text-align: center">${affairQjSum.bing}</td>
            <td style="text-align: center">${affairQjSum.peiCan}</td>
            <td style="text-align: center">${affairQjSum.can}</td>
            <td style="text-align: center">${affairQjSum.sang}</td>
            <td style="text-align: center">${affairQjSum.shi}</td>
            <td style="text-align: center">${affairQjSum.gongShang}</td>
            <td style="text-align: center">${affairQjSum.chucai}</td>
            <td style="text-align: center">${affairQjSum.lianXi}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>