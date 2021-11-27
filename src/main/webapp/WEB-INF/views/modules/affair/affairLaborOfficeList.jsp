<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>劳资组织树管理</title>
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
		<li class="active"><a href="${ctx}/affair/affairLaborOffice/">劳资组织树列表</a></li>
		<shiro:hasPermission name="affair:affairLaborOffice:edit"><li><a href="${ctx}/affair/affairLaborOffice/form">劳资组织树添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairLaborOffice" action="${ctx}/affair/affairLaborOffice/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>单位名称：</label>
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
				<th>单位名称</th>
				<th>update_date</th>
				<th>remarks</th>
				<shiro:hasPermission name="affair:affairLaborOffice:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairLaborOffice">
			<tr>
				<td><a href="${ctx}/affair/affairLaborOffice/form?id=${affairLaborOffice.id}">
					${affairLaborOffice.name}
				</a></td>
				<td>
					<fmt:formatDate value="${affairLaborOffice.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${affairLaborOffice.remarks}
				</td>
				<shiro:hasPermission name="affair:affairLaborOffice:edit"><td>
    				<a href="${ctx}/affair/affairLaborOffice/form?id=${affairLaborOffice.id}">修改</a>
					<a href="${ctx}/affair/affairLaborOffice/delete?id=${affairLaborOffice.id}" onclick="return confirmx('确认要删除该劳资组织树吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>