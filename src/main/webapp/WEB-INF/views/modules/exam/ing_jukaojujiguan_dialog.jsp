<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<title>局考局机关-生成表格</title>
<style>
	/*表格居中样式设置*/
	.table th, .table td {
		text-align: center;
		vertical-align: middle !important;
	}
</style>
<script>
	$(document).ready(function() {
		$("#export").click(
				function(){
					var submit = function (v, h, f) {
						if (v == 'export') {
							$("#jukaojujiguanExportForm").submit();
						}
						if (v == 'cancel') {
							$.jBox.tip('已取消');
						}
						return true;
					};
					$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出': 'export', '取消':'cancel'} });
				}
		);
	});
</script>
<meta name="decorator" content="default"/>
<!-- 绩效考评-局考局机关-生成表格-->
<div id="modalAllPublic" class="">
	<div class="">
		<div class="modal-custom-main">
			<%--<%@include file="/WEB-INF/views/modules/exam/step.jsp" %>--%>
			<div>
				<form:form id="jukaojujiguanExportForm" action="${ctx}/exam/examWorkflow/jukaojujiguanExport?workflowId=${workflowId}" method="post" class="breadcrumb form-search" cssStyle="text-align: right">
					<input id="export" class="btn btn-primary" type="button" value="导出"/>
				</form:form>
				<c:if test="${not empty message}">
					<div id="messageBox" class="alert alert-error"><button data-dismiss="alert" class="close">×</button>${message}</div>
				</c:if>
				<%--<sys:message content="${message}"></sys:message>--%>
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<caption>
						<h3>${workflowName}</h3>
					</caption>
					<thead>
						<tr>
							<th>序号</th>
							<th>部门</th>
							<th>扣分项目</th>
							<th>加分项目</th>
							<th>扣分</th>
							<th>加分</th>
							<th>总得分</th>
						</tr>
					</thead>
					<tbody>
					<c:if  test="${not empty unitMapList && unitMapList.size()>0}">
						<c:forEach items="${unitMapList}" var="m" varStatus="status" >
							<tr>
								<td>${status.index+1}</td>
								<td>${m.objName}</td>
								<td style="text-align: left">${m.deductScoreItems}</td>
								<td style="text-align: left">${m.addScoreItems}</td>
								<td><fmt:formatNumber type="number" value="${m.deductScore}" pattern="#.####"/></td>
								<td><fmt:formatNumber type="number" value="${m.addScore}" pattern="#.####"/></td>
								<td><fmt:formatNumber type="number" value="${m.totalScore}" pattern="#.####"/></td>
							</tr>
						</c:forEach>
					</c:if>

					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

