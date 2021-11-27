<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统最终审核公示管理</title>
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
		<li class="active"><a href="${ctx}/exam/examSystemDisplayCheck/">系统最终审核公示列表</a></li>
		<shiro:hasPermission name="exam:examSystemDisplayCheck:edit"><li><a href="${ctx}/exam/examSystemDisplayCheck/form">系统最终审核公示添加</a></li></shiro:hasPermission>
	</ul>
	<div class="modal-custom-head">
		<h3 class="close" onclick="$(this).parent().parent().parent().css('display','none')">x</h3>
	</div>
	<div class="modal-custom-main">
		<div class="modal-custom-main-step">
			<div class="step-start active">开始</div>
			<div class="modal-step-col active">系统自评</div>
			<div class="modal-step-col active">系统初步考核</div>
			<div class="modal-step-col active">系统公示</div>
			<div class="modal-step-col active">部门负责人签字</div>
			<div class="modal-step-col active">分管局领导签字</div>
			<div class="modal-step-col active">绩效考评领导小组复核及调整</div>
			<div class="modal-step-col active">局主管领导最终审签</div>
			<div class="modal-step-col">最终结果公示</div>
			<div class="step-start step-end">结束</div>
		</div>
	</div>
	<form:form id="searchForm" modelAttribute="examSystemDisplayCheck" action="${ctx}/exam/examSystemDisplayCheck/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>单位名称</th>
				<th>总得分</th>
				<th>基础分</th>
				<th>扣分情况</th>
				<th>实际得分</th>
				<th>换算后得分</th>
				<th>绩效考评领导小组复合及调整人</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examSystemDisplayCheck">
			<tr>
				<td><a href="${ctx}/exam/examSystemDisplayCheck/form?id=${examSystemDisplayCheck.id}">
					${examSystemDisplayCheck.unit}
				</a></td>
				<td>
					${examSystemDisplayCheck.sumCode}
				</td>
				<td>
					${examSystemDisplayCheck.initCode}
				</td>
				<td>
					${examSystemDisplayCheck.lessCode}
				</td>
				<td>
					${examSystemDisplayCheck.realCode}
				</td>
				<td>
					${examSystemDisplayCheck.conversonCode}
				</td>

				<td>
					${examSystemDisplayCheck.updateBy.id}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>