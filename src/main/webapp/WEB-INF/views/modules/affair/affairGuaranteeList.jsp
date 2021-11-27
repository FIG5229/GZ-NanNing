<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>医保金管理</title>
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
		<li class="active"><a href="${ctx}/affair/affairGuarantee/">医保金列表</a></li>
		<shiro:hasPermission name="affair:affairGuarantee:edit"><li><a href="${ctx}/affair/affairGuarantee/form">医保金添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairGuarantee" action="${ctx}/affair/affairGuarantee/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>身份证号：</label>
				<form:input path="idNumber" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>性别：</label>
				<form:select path="sex" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>保险种类：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('baoxian_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>保险名称：</label>
				<form:input path="bxName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>保险机构名称：</label>
				<form:input path="bxOrg" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>身份证号</th>
				<th>性别</th>
				<th>保险种类</th>
				<th>保险名称</th>
				<th>保险机构名称</th>
				<th>保险费</th>
				<th>保险金额</th>
				<shiro:hasPermission name="affair:affairGuarantee:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairGuarantee">
			<tr>
				<td><a href="${ctx}/affair/affairGuarantee/form?id=${affairGuarantee.id}">
					${affairGuarantee.name}
				</a></td>
				<td>
					${affairGuarantee.idNumber}
				</td>
				<td>
					${fns:getDictLabel(affairGuarantee.sex, 'sex', '')}
				</td>
				<td>
					${fns:getDictLabel(affairGuarantee.type, 'baoxian_type', '')}
				</td>
				<td>
					${affairGuarantee.bxName}
				</td>
				<td>
					${affairGuarantee.bxOrg}
				</td>
				<td>
					${affairGuarantee.cost}
				</td>
				<td>
					${affairGuarantee.money}
				</td>
				<shiro:hasPermission name="affair:affairGuarantee:edit"><td>
    				<a href="${ctx}/affair/affairGuarantee/form?id=${affairGuarantee.id}">修改</a>
					<a href="${ctx}/affair/affairGuarantee/delete?id=${affairGuarantee.id}" onclick="return confirmx('确认要删除该医保金吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>