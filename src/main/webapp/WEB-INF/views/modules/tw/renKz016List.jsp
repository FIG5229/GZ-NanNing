<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>自动考评-精神病患者信息拓展管理</title>
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
		<li class="active"><a href="${ctx}/tw/renKz016/">自动考评-精神病患者信息拓展列表</a></li>
		<shiro:hasPermission name="tw:renKz016:edit"><li><a href="${ctx}/tw/renKz016/form">自动考评-精神病患者信息拓展添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="renKz016" action="${ctx}/tw/renKz016/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<shiro:hasPermission name="tw:renKz016:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="renKz016">
			<tr>
				<shiro:hasPermission name="tw:renKz016:edit"><td>
    				<a href="${ctx}/tw/renKz016/form?id=${renKz016.id}">修改</a>
					<a href="${ctx}/tw/renKz016/delete?id=${renKz016.id}" onclick="return confirmx('确认要删除该自动考评-精神病患者信息拓展吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>