<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>预备党员转正管理</title>
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
		<li class="active"><a href="${ctx}/affair/affairProbationaryParty/">预备党员转正列表</a></li>
		<shiro:hasPermission name="affair:affairProbationaryParty:edit"><li><a href="${ctx}/affair/affairProbationaryParty/form">预备党员转正添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairProbationaryParty" action="${ctx}/affair/affairProbationaryParty/list?treeId=${treeId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="预备党员转正.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>性别：</label>
				<form:select path="gender" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('gender')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>所在党支部：</label>
				<form:input path="partyBranch" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairProbationaryParty:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()"
										value="添加"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
										onclick="deleteByIds('${ctx}/affair/affairProbationaryParty/deleteByIds','checkAll','myCheckBox')"/>
				</li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input class="btn btn-primary" type="button" value="重置"
									onclick="window.location.href='${ctx}/affair/affairProbationaryParty/list?treeId=${treeId}'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>
					<input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>
				</th>
				<th>序号</th>
				<th>身份证号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>学历</th>
				<th>人员类别</th>
				<th>所在党支部</th>
				<th>加入党组织日期</th>
				<shiro:hasPermission name="affair:affairProbationaryParty:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairProbationaryParty">
			<tr>
				<td><a href="${ctx}/affair/affairProbationaryParty/form?id=${affairProbationaryParty.id}">
					${affairProbationaryParty.id}
				</a></td>
				<td>
					${affairProbationaryParty.identityCardNumber}
				</td>
				<td>
					${affairProbationaryParty.name}
				</td>
				<td>
					${fns:getDictLabel(affairProbationaryParty.gender, 'sex', '')}
				</td>
				<td>
					${affairProbationaryParty.educational}
				</td>
				<td>
					${affairProbationaryParty.personnelCategory}
				</td>
				<td>
					${affairProbationaryParty.partyBranch}
				</td>
				<td>
					<fmt:formatDate value="${affairProbationaryParty.joiningPartyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="affair:affairProbationaryParty:edit"><td>
    				<a href="${ctx}/affair/affairProbationaryParty/form?id=${affairProbationaryParty.id}">修改</a>
					<a href="${ctx}/affair/affairProbationaryParty/delete?id=${affairProbationaryParty.id}" onclick="return confirmx('确认要删除该预备党员转正吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>