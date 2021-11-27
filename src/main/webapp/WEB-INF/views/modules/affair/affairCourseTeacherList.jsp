<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>辅导教官管理</title>
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

	<form:form id="searchForm" modelAttribute="affairCourseTeacher" action="${ctx}/affair/affairCourseTeacher/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>教官姓名：</label>
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
				<th>教官姓名</th>
				<th>更新时间</th>
				<shiro:hasPermission name="affair:affairCourseTeacher:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairCourseTeacher">
			<tr>
				<td><a href="${ctx}/affair/affairCourseTeacher/form?id=${affairCourseTeacher.id}">
					${affairCourseTeacher.name}
				</a></td>
				<td>
					<fmt:formatDate value="${affairCourseTeacher.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="affair:affairCourseTeacher:edit"><td>
    				<a href="${ctx}/affair/affairCourseTeacher/form?id=${affairCourseTeacher.id}">修改</a>
					<a href="${ctx}/affair/affairCourseTeacher/delete?id=${affairCourseTeacher.id}" onclick="return confirmx('确认要删除该辅导教官吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>