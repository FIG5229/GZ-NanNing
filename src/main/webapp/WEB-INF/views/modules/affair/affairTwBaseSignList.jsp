<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团委（支部）基本信息关联管理</title>
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
		<li class="active"><a href="${ctx}/affair/affairTwBaseSign/">团委（支部）基本信息关联列表</a></li>
		<shiro:hasPermission name="affair:affairTwBaseSign:edit"><li><a href="${ctx}/affair/affairTwBaseSign/form">团委（支部）基本信息关联添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairTwBaseSign" action="${ctx}/affair/affairTwBaseSign/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>角色：</label>
				<form:select path="role" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>角色</th>
				<th>职责</th>
				<shiro:hasPermission name="affair:affairTwBaseSign:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTwBaseSign">
			<tr>
				<td><a href="${ctx}/affair/affairTwBaseSign/form?id=${affairTwBaseSign.id}">
					${fns:getDictLabel(affairTwBaseSign.role, '', '')}
				</a></td>
				<td>
					${affairTwBaseSign.job}
				</td>
				<shiro:hasPermission name="affair:affairTwBaseSign:edit"><td>
    				<a href="${ctx}/affair/affairTwBaseSign/form?id=${affairTwBaseSign.id}">修改</a>
					<a href="${ctx}/affair/affairTwBaseSign/delete?id=${affairTwBaseSign.id}" onclick="return confirmx('确认要删除该团委（支部）基本信息关联吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>