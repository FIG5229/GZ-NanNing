<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考评人员分配规则管理管理</title>
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
		<li class="active"><a href="${ctx}/exam/examPersonsAssignRule/">考评人员分配规则管理列表</a></li>
		<shiro:hasPermission name="exam:examPersonsAssignRule:edit"><li><a href="${ctx}/exam/examPersonsAssignRule/form">考评人员分配规则管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="examPersonsAssignRule" action="${ctx}/exam/examPersonsAssignRule/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<!--
			<li><label>简称：</label>
				<form:input path="abbreviation" htmlEscape="false" class="input-medium"/>
			</li>-->
			<li><label style="width: 140px;">被考评对象类别：</label>
				<form:select path="objType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('obj_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<!--
			<li><label>考评类别：</label>
				<form:select path="kpType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('kp_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>考评周期：</label>
				<form:select path="cycle" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('cycle')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>是否启用：</label>
				<form:select path="isEnable" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			-->
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<!--
				<th>简称</th>
				<th>被考评对象类别</th>
				-->
				<th>考评类别</th>
				<!--
				<th>考评周期</th>
				<th>是否启用</th>
				<th>单位</th>-->
				<th>单位名称</th>
				<shiro:hasPermission name="exam:examPersonsAssignRule:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examPersonsAssignRule">
			<tr>
				<td><a href="${ctx}/exam/examPersonsAssignRule/form?id=${examPersonsAssignRule.id}">
					${examPersonsAssignRule.name}
				</a></td>
				<!--
				<td>
					${examPersonsAssignRule.abbreviation}
				</td>
				<td>
					${fns:getDictLabel(examPersonsAssignRule.objType, 'obj_type', '')}
				</td>
				-->
				<td>
					${fns:getDictLabel(examPersonsAssignRule.kpType, 'kp_type', '')}
				</td>
				<!--
				<td>
					${fns:getDictLabel(examPersonsAssignRule.cycle, 'cycle', '')}
				</td>
				<td>
					${fns:getDictLabel(examPersonsAssignRule.isEnable, 'yes_no', '')}
				</td>
				<td>
					${examPersonsAssignRule.unitId}
				</td>-->
				<td>
					${examPersonsAssignRule.unitName}
				</td>
				<shiro:hasPermission name="exam:examPersonsAssignRule:edit"><td>
    				<a href="${ctx}/exam/examPersonsAssignRule/form?id=${examPersonsAssignRule.id}">修改</a>
					<a href="${ctx}/exam/examPersonsAssignRule/delete?id=${examPersonsAssignRule.id}" onclick="return confirmx('确认要删除该考评人员分配规则管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>