<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>一帮一明细管理</title>
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
		<li class="active"><a href="${ctx}/affair/affairOneHelpOneMain/">一帮一明细列表</a></li>
		<shiro:hasPermission name="affair:affairOneHelpOneMain:edit"><li><a href="${ctx}/affair/affairOneHelpOneMain/form">一帮一明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairOneHelpOneMain" action="${ctx}/affair/affairOneHelpOneMain/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>帮扶人姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>被帮扶姓名：</label>
				<form:input path="beName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>被帮扶人住址：</label>
				<form:input path="address" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>联系电话：</label>
				<form:input path="tel" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>关联表标记：</label>
				<form:input path="mainId" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>帮扶人姓名</th>
				<th>被帮扶姓名</th>
				<th>被帮扶人情况</th>
				<th>被帮扶人住址</th>
				<th>帮扶金额</th>
				<th>联系电话</th>
				<th>更新时间</th>
				<shiro:hasPermission name="affair:affairOneHelpOneMain:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairOneHelpOneMain">
			<tr>
				<td><a href="${ctx}/affair/affairOneHelpOneMain/form?id=${affairOneHelpOneMain.id}">
					${affairOneHelpOneMain.name}
				</a></td>
				<td>
					${affairOneHelpOneMain.beName}
				</td>
				<td>
					${affairOneHelpOneMain.situation}
				</td>
				<td>
					${affairOneHelpOneMain.address}
				</td>
				<td>
					${affairOneHelpOneMain.money}
				</td>
				<td>
					${affairOneHelpOneMain.tel}
				</td>
				<td>
					<fmt:formatDate value="${affairOneHelpOneMain.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="affair:affairOneHelpOneMain:edit"><td>
    				<a href="${ctx}/affair/affairOneHelpOneMain/form?id=${affairOneHelpOneMain.id}">修改</a>
					<a href="${ctx}/affair/affairOneHelpOneMain/delete?id=${affairOneHelpOneMain.id}" onclick="return confirmx('确认要删除该一帮一明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>