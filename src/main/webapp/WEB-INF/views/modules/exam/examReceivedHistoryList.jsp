<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>绩效考评项推送管理</title>
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
<%--	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/exam/examPushHistory/">绩效考评项推送列表</a></li>
		<shiro:hasPermission name="exam:examPushHistory:edit"><li><a href="${ctx}/exam/examPushHistory/form">绩效考评项推送添加</a></li></shiro:hasPermission>
	</ul>--%>
	<form:form id="searchForm" modelAttribute="examPushHistory" action="${ctx}/exam/examPushHistory/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<%--	<ul class="ul-form">
		&lt;%&ndash;	<li><label>考评id：</label>
				<form:input path="workflowId" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>考评项id：</label>
				<form:input path="workflowDatasId" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>被推送对象的Id：</label>
				<form:input path="objId" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>推送人的Id：</label>
				<form:input path="fromId" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>状态，1：推送到workflowdata表：</label>
				<form:input path="status" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>推送人姓名：</label>
				<form:input path="fromName" htmlEscape="false" class="input-medium"/>
			</li>&ndash;%&gt;
			<li ><label style="width: 100px">评分标准内容：</label>
				<form:input path="itemStanddard" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>具体事项：</label>
				<form:input path="itemDetail" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>--%>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>推送对象</th>
				<th>推送人</th>
				<th>考评标准内容</th>
				<th>具体事项</th>
<%--				<th>推送状态</th>--%>
				<shiro:hasPermission name="exam:examPushHistory:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
<%--		<c:forEach items="${page.list}" var="examPushHistory" varStatus="status">--%>
			<tr>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td>${examPushHistory.objName}</td>
				<td>${examPushHistory.fromName}</td>
				<td>${examPushHistory.itemStanddard}</td>
				<td>${examPushHistory.itemDetail}</td>
<%--				<td>${fns:getDictLabel(examPushHistory.status,"exam_push_status" ,"" )}</td>--%>
				<shiro:hasPermission name="exam:examPushHistory:edit"><td>
    				<a href="${ctx}/exam/examPushHistory/form?id=${examPushHistory.id}">修改</a>
					<a href="${ctx}/exam/examPushHistory/delete?id=${examPushHistory.id}" onclick="return confirmx('确认要删除该绩效考评项推送吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
<%--		</c:forEach>--%>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>