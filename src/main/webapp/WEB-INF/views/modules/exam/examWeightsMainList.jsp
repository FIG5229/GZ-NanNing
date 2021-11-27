<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>权重管理</title>
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
		<li class="active"><a href="${ctx}/exam/examWeightsMain/">权重列表</a></li>
		<shiro:hasPermission name="exam:examWeightsMain:edit"><li><a href="${ctx}/exam/examWeightsMain/form">权重添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="examWeightsMain" action="${ctx}/exam/examWeightsMain/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>工作名称：</label>
				<form:select path="workName" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>权重分数：</label>
				<form:input path="weights" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>工作名称</th>
				<th>权重分数</th>
				<shiro:hasPermission name="exam:examWeightsMain:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examWeightsMain">
			<tr>
				<td><a href="${ctx}/exam/examWeightsMain/form?id=${examWeightsMain.id}">
					${fns:getDictLabel(examWeightsMain.workName, '', '')}
				</a></td>
				<td>
					${examWeightsMain.weights}
				</td>
				<shiro:hasPermission name="exam:examWeightsMain:edit"><td>
    				<a href="${ctx}/exam/examWeightsMain/form?id=${examWeightsMain.id}">修改</a>
					<a href="${ctx}/exam/examWeightsMain/delete?id=${examWeightsMain.id}" onclick="return confirmx('确认要删除该权重吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>