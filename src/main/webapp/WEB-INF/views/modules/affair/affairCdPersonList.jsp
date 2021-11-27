<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查(借)阅审批管理(关联表页面,无用,待删)</title>
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
		<li class="active"><a href="${ctx}/affair/affairCdPerson/">查(借)阅审批列表</a></li>
		<shiro:hasPermission name="affair:affairCdPerson:edit"><li><a href="${ctx}/affair/affairCdPerson/form">查(借)阅审批添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairCdPerson" action="${ctx}/affair/affairCdPerson/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>查档人员姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>查档人员职务：</label>
				<form:select path="job" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('cd_position')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>查档人员姓名</th>
				<th>查档人员职务</th>
				<th>查档人员政治面貌</th>
				<shiro:hasPermission name="affair:affairCdPerson:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairCdPerson">
			<tr>
				<td><a href="${ctx}/affair/affairCdPerson/form?id=${affairCdPerson.id}">
					${affairCdPerson.name}
				</a></td>
				<td>
					${fns:getDictLabel(affairCdPerson.job, 'cd_position', '')}
				</td>
				<td>
					${fns:getDictLabel(affairCdPerson.face, 'zzmm', '')}
				</td>
				<shiro:hasPermission name="affair:affairCdPerson:edit"><td>
    				<a href="${ctx}/affair/affairCdPerson/form?id=${affairCdPerson.id}">修改</a>
					<a href="${ctx}/affair/affairCdPerson/delete?id=${affairCdPerson.id}" onclick="return confirmx('确认要删除该查(借)阅审批吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>