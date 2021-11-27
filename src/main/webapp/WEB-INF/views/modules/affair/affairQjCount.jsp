<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>请假信息管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("[data-toggle='popover']").popover();

        });

        function page(n,s){
           /* $("#pageNo").val(n);
            $("#pageSize").val(s);*/
            $("#searchForm").submit();
            return false;
        }
        $('#notPass').popover();
    </script>
</head>
<body>

<ul class="nav nav-tabs">
    <li><a href="${ctx}/affair/affairQj">请假信息</a></li>
    <li class="active"><a href="${ctx}/affair/affairQjCount/count">请假信息汇总</a></li>
    <li ><a href="${ctx}/affair/affairQjSum/statistic">请假信息统计</a></li>
</ul>
<form:form id="searchForm" modelAttribute="affairQj" action="${ctx}/affair/affairQjCount/count" method="post" class="breadcrumb form-search">
   <%-- <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>--%>
    <ul class="ul-form">
        <li><label>年份：</label>
            <form:input path="year" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>月份：</label>
            <form:input path="month" htmlEscape="false" class="input-medium"/>
        </li>
    </ul>
    <ul class="ul-form2">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
        <li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairQjCount/count'"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
        <th style="text-align: center">序号</th>
        <th style="text-align: center">姓名</th>
        <th style="text-align: center">年份</th>
        <th style="text-align: center">月份</th>
        <th style="text-align: center">请假总天数</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairQj" varStatus="status">
        <tr><td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairQj.id}"/></td>
            <td style="text-align: center">${(page.pageNo-1)*page.pageSize+status.index+1}</td>
            <td style="text-align: center">
                    ${affairQj.name}
            </td>
            <td style="text-align: center">
                ${affairQj.year}
            </td>
            <td style="text-align: center">
                ${affairQj.month}
            </td>
            <td style="text-align: center">
                    ${affairQj.summary}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%--<div class="pagination">${page}</div>--%>
</body>
</html>