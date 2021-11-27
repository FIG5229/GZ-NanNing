<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>模板项管理管理</title>
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
		<li class="active"><a href="${ctx}/exam/examStandardTemplateItemDefine/">模板项管理列表</a></li>
		<shiro:hasPermission name="exam:examStandardTemplateItemDefine:edit"><li><a href="${ctx}/exam/examStandardTemplateItemDefine/form">模板项管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="examStandardTemplateItemDefine" action="${ctx}/exam/examStandardTemplateItemDefine/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>列名称：</label>
				<form:input path="columnName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>列类型：</label>
				<form:select path="columnType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('column_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>列顺序号：</label>
				<form:input path="columnOrder" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>列名称</th>
				<th>列类型</th>
				<th>列顺序号</th>
				<th>更新时间</th>
				<shiro:hasPermission name="exam:examStandardTemplateItemDefine:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examStandardTemplateItemDefine">
			<tr>
				<td><a href="${ctx}/exam/examStandardTemplateItemDefine/form?id=${examStandardTemplateItemDefine.id}">
					${examStandardTemplateItemDefine.columnName}
				</a></td>
				<td>
					${fns:getDictLabel(examStandardTemplateItemDefine.columnType, 'column_type', '')}
				</td>
				<td>
					${examStandardTemplateItemDefine.columnOrder}
				</td>
				<td>
					<fmt:formatDate value="${examStandardTemplateItemDefine.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="exam:examStandardTemplateItemDefine:edit"><td>
    				<a href="${ctx}/exam/examStandardTemplateItemDefine/form?id=${examStandardTemplateItemDefine.id}">修改</a>
					<a href="${ctx}/exam/examStandardTemplateItemDefine/delete?id=${examStandardTemplateItemDefine.id}" onclick="return confirmx('确认要删除该模板项管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>