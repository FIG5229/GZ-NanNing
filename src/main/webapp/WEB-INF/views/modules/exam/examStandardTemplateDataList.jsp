<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>模板项数据管理管理</title>
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
		<li class="active"><a href="${ctx}/exam/examStandardTemplateData/">模板项数据管理列表</a></li>
		<shiro:hasPermission name="exam:examStandardTemplateData:edit"><li><a href="${ctx}/exam/examStandardTemplateData/form">模板项数据管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="examStandardTemplateData" action="${ctx}/exam/examStandardTemplateData/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>模板项目ID：</label>
				<form:input path="itemId" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>数据项值：</label>
				<form:input path="itemValue" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>模板项目ID</th>
				<th>数据项值</th>
				<shiro:hasPermission name="exam:examStandardTemplateData:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examStandardTemplateData">
			<tr>
				<td><a href="${ctx}/exam/examStandardTemplateData/form?id=${examStandardTemplateData.id}">
					${examStandardTemplateData.itemId}
				</a></td>
				<td>
					${examStandardTemplateData.itemValue}
				</td>
				<shiro:hasPermission name="exam:examStandardTemplateData:edit"><td>
    				<a href="${ctx}/exam/examStandardTemplateData/form?id=${examStandardTemplateData.id}">修改</a>
					<a href="${ctx}/exam/examStandardTemplateData/delete?id=${examStandardTemplateData.id}" onclick="return confirmx('确认要删除该模板项数据管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>