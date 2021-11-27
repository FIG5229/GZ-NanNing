<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考评人员分配规则子表管理管理</title>
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
		<li class="active"><a href="${ctx}/exam/examPersonsAssignRuleChild/">考评人员分配规则子表管理列表</a></li>
		<shiro:hasPermission name="exam:examPersonsAssignRuleChild:edit"><li><a href="${ctx}/exam/examPersonsAssignRuleChild/form">考评人员分配规则子表管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="examPersonsAssignRuleChild" action="${ctx}/exam/examPersonsAssignRuleChild/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>考核部门：</label>
				<form:input path="examDepart" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>自评人IDS：</label>
				<sys:treeselect id="selfPersonIds" name="selfPersonIds" value="${examPersonsAssignRuleChild.selfPersonIds}" labelName="" labelValue="${examPersonsAssignRuleChild.}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>考评人IDS：</label>
				<sys:treeselect id="examPersonIds" name="examPersonIds" value="${examPersonsAssignRuleChild.examPersonIds}" labelName="" labelValue="${examPersonsAssignRuleChild.}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>考评人Names：</label>
				<form:input path="examPersonNames" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<shiro:hasPermission name="exam:examPersonsAssignRuleChild:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examPersonsAssignRuleChild">
			<tr>
				<shiro:hasPermission name="exam:examPersonsAssignRuleChild:edit"><td>
    				<a href="${ctx}/exam/examPersonsAssignRuleChild/form?id=${examPersonsAssignRuleChild.id}">修改</a>
					<a href="${ctx}/exam/examPersonsAssignRuleChild/delete?id=${examPersonsAssignRuleChild.id}" onclick="return confirmx('确认要删除该考评人员分配规则子表管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>