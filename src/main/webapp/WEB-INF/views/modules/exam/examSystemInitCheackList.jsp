<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<html>
<head>
	<title>系统公示管理</title>
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
		<li class="active"><a href="${ctx}/exam/examSystemInitCheack/">系统公示列表</a></li>
		<shiro:hasPermission name="exam:examSystemInitCheack:edit"><li><a href="${ctx}/exam/examSystemInitCheack/form">系统公示添加</a></li></shiro:hasPermission>
	</ul>
	<div class="modal-custom-head">
		<h3 class="close" onclick="$(this).parent().parent().parent().css('display','none')">x</h3>
	</div>
	<div class="modal-custom-main-step">
		<div class="step-start active">开始</div>
		<div class="modal-step-col active">系统自评</div>
		<div class="modal-step-col active">系统初步考核</div>
		<div class="modal-step-col">系统公示</div>
		<div class="modal-step-col">部门负责人签字</div>
		<div class="modal-step-col">分管局领导签字</div>
		<div class="modal-step-col">绩效考评领导小组复核及调整</div>
		<div class="modal-step-col">局主管领导最终审签</div>
		<div class="modal-step-col">最终结果全局公示</div>
		<div class="step-start step-end">结束</div>
	</div>
	<form:form id="searchForm" modelAttribute="examSystemInitCheack" action="${ctx}/exam/examSystemInitCheack/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<div class="modal-custom-content">
		<table id="modalPeoTb" class="table table-striped table-bordered table-condensed">
			<thead>
			<tr>
				<th>单位名称</th>
				<th>自评状态</th>
				<th>系统初核</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.list}" var="examSystemInitCheack">
				<tr>
					<td><a href="${ctx}/exam/examSystemInitCheack/form?id=${examSystemInitCheack.id}">
							${examSystemInitCheack.unit}
					</a></td>
					<td>
						<c:choose>
							<c:when test="${examSystemInitCheack.selfExamType eq '1'}">
								<i style="color: #6FAD47;" class="icon-check"></i>
							</c:when>
							<c:when test="${examSystemInitCheack.selfExamType eq '2'}">
								<i style="color: #D0282E;" class="icon-remove"></i>
							</c:when>
							<c:otherwise>
								<i style="color: #D0282E;" class="icon-lock"></i>
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${examSystemInitCheack.systemExamType eq '1'}">
								<i style="color: #6FAD47;" class="icon-check"></i>
							</c:when>
							<c:when test="${examSystemInitCheack.systemExamType eq '2'}">
								<i style="color: #D0282E;" class="icon-remove"></i>
							</c:when>
							<c:otherwise>
								<i style="color: #D0282E;" class="icon-lock"></i>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>