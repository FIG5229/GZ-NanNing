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
		<li class="active"><a href="${ctx}/affair/affairCdObject/">查(借)阅审批列表</a></li>
		<shiro:hasPermission name="affair:affairCdObject:edit"><li><a href="${ctx}/affair/affairCdObject/form">查(借)阅审批添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairCdObject" action="${ctx}/affair/affairCdObject/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>查档对象姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>查档对象单位：</label>
				<sys:treeselect id="unit" name="unit" value="${affairCdObject.unit}" labelName="" labelValue="${affairCdObject.}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>查档对象姓名</th>
				<th>查档对象单位</th>
				<th>查档对象政治面貌</th>
				<shiro:hasPermission name="affair:affairCdObject:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairCdObject">
			<tr>
				<td><a href="${ctx}/affair/affairCdObject/form?id=${affairCdObject.id}">
					${affairCdObject.name}
				</a></td>
				<td>
					${affairCdObject.}
				</td>
				<td>
					${fns:getDictLabel(affairCdObject.face, 'political_status', '')}
				</td>
				<shiro:hasPermission name="affair:affairCdObject:edit"><td>
    				<a href="${ctx}/affair/affairCdObject/form?id=${affairCdObject.id}">修改</a>
					<a href="${ctx}/affair/affairCdObject/delete?id=${affairCdObject.id}" onclick="return confirmx('确认要删除该查(借)阅审批吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>