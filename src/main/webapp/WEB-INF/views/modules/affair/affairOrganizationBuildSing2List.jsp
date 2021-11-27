<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>组织建设关联表2管理</title>
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
		<li class="active"><a href="${ctx}/affair/affairOrganizationBuildSing2/">组织建设关联表2列表</a></li>
		<shiro:hasPermission name="affair:affairOrganizationBuildSing2:edit"><li><a href="${ctx}/affair/affairOrganizationBuildSing2/form">组织建设关联表2添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairOrganizationBuildSing2" action="${ctx}/affair/affairOrganizationBuildSing2/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>经费审查委员会情况：</label>
				<form:select path="review" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>经费审查委员会名字：</label>
				<form:input path="reviewName" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>经费审查委员会情况</th>
				<th>经费审查委员会名字</th>
				<shiro:hasPermission name="affair:affairOrganizationBuildSing2:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairOrganizationBuildSing2">
			<tr>
				<td><a href="${ctx}/affair/affairOrganizationBuildSing2/form?id=${affairOrganizationBuildSing2.id}">
					${fns:getDictLabel(affairOrganizationBuildSing2.review, '', '')}
				</a></td>
				<td>
					${affairOrganizationBuildSing2.reviewName}
				</td>
				<shiro:hasPermission name="affair:affairOrganizationBuildSing2:edit"><td>
    				<a href="${ctx}/affair/affairOrganizationBuildSing2/form?id=${affairOrganizationBuildSing2.id}">修改</a>
					<a href="${ctx}/affair/affairOrganizationBuildSing2/delete?id=${affairOrganizationBuildSing2.id}" onclick="return confirmx('确认要删除该组织建设关联表2吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>