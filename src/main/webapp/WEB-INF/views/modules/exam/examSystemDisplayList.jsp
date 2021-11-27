<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
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
		<li class="active"><a href="${ctx}/exam/examSystemDisplay/">系统公示列表</a></li>
		<shiro:hasPermission name="exam:examSystemDisplay:edit"><li><a href="${ctx}/exam/examSystemDisplay/form">系统公示添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="examSystemDisplay" action="${ctx}/exam/examSystemDisplay/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<div class="modal-custom-head">
		<h3 class="close" onclick="$(this).parent().parent().parent().css('display','none')">x</h3>
	</div>
	<div class="modal-custom-main">
		<div class="modal-custom-main-step">
			<div class="step-start active">开始</div>
			<div class="modal-step-col active">系统自评</div>
			<div class="modal-step-col active">系统初步考核</div>
			<div class="modal-step-col active">系统公示</div>
			<div class="modal-step-col">部门负责人签字</div>
			<div class="modal-step-col">分管局领导签字</div>
			<div class="modal-step-col">绩效考评领导小组复核及调整</div>
			<div class="modal-step-col">局主管领导最终审签</div>
			<div class="modal-step-col">最终结果公示</div>
			<div class="step-start step-end">结束</div>
		</div>
	</div>
	<sys:message content="${message}"/>
	<table id="modalPeoTb" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>单位名称</th>
				<th>总得分</th>
				<th>基础分</th>
				<th>扣分情况</th>
				<th>实际得分</th>
				<th>扣算后得分</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examSystemDisplay">
		  <tr>
			  <td>
				  ${examSystemDisplay.unit}
			  </a></td>
			  <td><a href="${ctx}/exam/examSystemDisplay/form?id=${examSystemDisplay.id}">
				  ${examSystemDisplay.sumCode}
			  </td>
			  <td>
				  ${examSystemDisplay.initCode}
			  </td>
			  <td>
				  ${examSystemDisplay.lessCode}
			  </td>
			  <td>
				  ${examSystemDisplay.realCode}
			  </td>
			  <td>
				  ${examSystemDisplay.conversonCode}
			  </td>
			  <td>
				  ${examSystemDisplay.remark}
			  </td>
		   </tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>