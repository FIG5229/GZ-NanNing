<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>绩效等次管理</title>
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
<%--ABCD 为优秀 达标 基本达标 不达标 toA 为添加达标--%>

	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/exam/examLdScore/list?grades=3&workflowId=${workflowId}">基本达标</a></li>
		<li><a href="${ctx}/exam/examLdScore/list?grades=2&workflowId=${workflowId}">不达标</a></li>
		<li><a href="${ctx}/exam/examLdScore/list?grades=5&workflowId=${workflowId}">优秀</a></li>
		<li><a href="${ctx}/exam/examLdScore/list?grades=4&workflowId=${workflowId}">达标</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="examLdScore" action="${ctx}/exam/examLdScore/?grades=3" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="workflowId" name="workflowId" type="hidden" value="${workflowId}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
		<%--	<li><label>最终得分：</label>
				<form:input path="sumScore" htmlEscape="false" class="input-medium"/>
			</li>--%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>姓名</th>
				<th>最终得分</th>
<%--				<shiro:hasPermission name="exam:examLdScore:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examLdScore" varStatus="status">
			<tr>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${examLdScore.name}
				</td>
				<td>
					${examLdScore.sumScore}
				</td>
			<%--	<shiro:hasPermission name="exam:examLdScore:edit"><td>
    				<a href="${ctx}/exam/examLdScore/form?id=${examLdScore.id}">修改</a>
					<a href="${ctx}/exam/examLdScore/delete?id=${examLdScore.id}" onclick="return confirmx('确认要删除该绩效等次吗？', this.href)">删除</a>
				</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>