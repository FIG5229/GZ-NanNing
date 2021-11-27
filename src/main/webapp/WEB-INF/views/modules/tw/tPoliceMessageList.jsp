<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>警情预警-自动考评管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/tw/tPoliceMessage/">警情预警-自动考评列表</a></li>
		<shiro:hasPermission name="tw:tPoliceMessage:edit"><li><a href="${ctx}/tw/tPoliceMessage/form">警情预警-自动考评添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tPoliceMessage" action="${ctx}/tw/tPoliceMessage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<shiro:hasPermission name="tw:tPoliceMessage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tPoliceMessage">
			<tr>
				<td><a href="${ctx}/tw/tPoliceMessage/form?id=${tPoliceMessage.id}">
					${tPoliceMessage.name}
				</a></td>
				<shiro:hasPermission name="tw:tPoliceMessage:edit"><td>
    				<a href="${ctx}/tw/tPoliceMessage/form?id=${tPoliceMessage.id}">修改</a>
					<a href="${ctx}/tw/tPoliceMessage/delete?id=${tPoliceMessage.id}" onclick="return confirmx('确认要删除该警情预警-自动考评吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>