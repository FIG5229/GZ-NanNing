<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民警家庭管理</title>
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
		<li class="active"><a href="${ctx}/personnel/personnelPoliceFamilyInfo/">民警家庭列表</a></li>
		<shiro:hasPermission name="personnel:personnelPoliceFamilyInfo:edit"><li><a href="${ctx}/personnel/personnelPoliceFamilyInfo/form">民警家庭添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="personnelPoliceFamilyInfo" action="${ctx}/personnel/personnelPoliceFamilyInfo/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="personnel:personnelPoliceFamilyInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelPoliceFamilyInfo">
			<tr>
				<td><a href="${ctx}/personnel/personnelPoliceFamilyInfo/form?id=${personnelPoliceFamilyInfo.id}">
					${personnelPoliceFamilyInfo.name}
				</a></td>
				<shiro:hasPermission name="personnel:personnelPoliceFamilyInfo:edit"><td>
    				<a href="${ctx}/personnel/personnelPoliceFamilyInfo/form?id=${personnelPoliceFamilyInfo.id}">修改</a>
					<a href="${ctx}/personnel/personnelPoliceFamilyInfo/delete?id=${personnelPoliceFamilyInfo.id}" onclick="return confirmx('确认要删除该民警家庭吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>