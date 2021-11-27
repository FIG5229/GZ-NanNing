<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>组织建设关联表管理</title>
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
		<li class="active"><a href="${ctx}/affair/affairOrganziationBuildSign/">组织建设关联表列表</a></li>
		<shiro:hasPermission name="affair:affairOrganziationBuildSign:edit"><li><a href="${ctx}/affair/affairOrganziationBuildSign/form">组织建设关联表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairOrganziationBuildSign" action="${ctx}/affair/affairOrganziationBuildSign/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>委员会情况：</label>
				<form:select path="committee" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>委员会角色名字：</label>
				<form:input path="committeeName" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>委员会情况</th>
				<th>委员会角色名字</th>
				<shiro:hasPermission name="affair:affairOrganziationBuildSign:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairOrganziationBuildSign">
			<tr>
				<td><a href="${ctx}/affair/affairOrganziationBuildSign/form?id=${affairOrganziationBuildSign.id}">
					${fns:getDictLabel(affairOrganziationBuildSign.committee, '', '')}
				</a></td>
				<td>
					${affairOrganziationBuildSign.committeeName}
				</td>
				<shiro:hasPermission name="affair:affairOrganziationBuildSign:edit"><td>
    				<a href="${ctx}/affair/affairOrganziationBuildSign/form?id=${affairOrganziationBuildSign.id}">修改</a>
					<a href="${ctx}/affair/affairOrganziationBuildSign/delete?id=${affairOrganziationBuildSign.id}" onclick="return confirmx('确认要删除该组织建设关联表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>