<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考评环节管理</title>
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
		<li class="active"><a href="${ctx}/exam/examWorflowSegments/">考评环节列表</a></li>
		<shiro:hasPermission name="exam:examWorflowSegments:edit"><li><a href="${ctx}/exam/examWorflowSegments/form">考评环节添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="examWorflowSegments" action="${ctx}/exam/examWorflowSegments/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>id：</label>
				<form:input path="id" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>更新时间：</label>
				<input name="updateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${examWorflowSegments.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>流程ID</th>
				<th>流程类型</th>
				<th>更新时间</th>
				<shiro:hasPermission name="exam:examWorflowSegments:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examWorflowSegments">
			<tr>
				<td>
					<a href="${ctx}/exam/examWorflowSegments/form?id=${examWorflowSegments.id}">
					${examWorflowSegments.flowId}
					</a>
				</td>
				<td>
					${examWorflowSegments.segmentId}
				</td>
				<td>
					<fmt:formatDate value="${examWorflowSegments.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="exam:examWorflowSegments:edit"><td>
    				<a href="${ctx}/exam/examWorflowSegments/form?id=${examWorflowSegments.id}">修改</a>
					<a href="${ctx}/exam/examWorflowSegments/delete?id=${examWorflowSegments.id}" onclick="return confirmx('确认要删除该考评环节吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>