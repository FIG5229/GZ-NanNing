<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<title>处领导-生成表格</title>
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
							//$("#searchForm").attr("action","${ctx}/exam/examWorkflow/jukaochuExport");
							$("#chuLDExamExportForm").submit();
							//$("#searchForm").attr("action","${ctx}/affair/affairZyInfo/");
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
<!-- 绩效考评-处领导审核-生成表格-->
<div id="modalAllPublic" class="">
	<div class="">
		<div class="modal-custom-main">
			<%--<%@include file="/WEB-INF/views/modules/exam/step.jsp" %>--%>
			<div>
				<form:form id="chuLDExamExportForm" action="${ctx}/exam/examWorkflow/chuLDExamExport?workflowId=${workflowId}" method="post" class="breadcrumb form-search" cssStyle="text-align: right">
					<input id="export" class="btn btn-primary" type="button" value="导出"/>
				</form:form>
				<c:if test="${not empty message}">
					<div id="messageBox" class="alert alert-error"><button data-dismiss="alert" class="close">×</button>${message}</div>
				</c:if>
				<c:if test="${not empty unitList && unitList.size()>0 }">
					<c:forEach items="${unitList}" var="list">
						<table id="contentTable" class="table table-striped table-bordered table-condensed">
							<caption>
								<h4>${list.unitName}领导班子</h4>
							</caption>
							<thead>
							<tr>
								<th style="width:100px">序号</th>
								<th style="width:100px">单位</th>
								<th style="width:100px">姓名</th>
								<th style="width:100px">职务</th>
								<th style="width:100px">分数</th>
								<th>加扣分事项</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach items="${list.ldInfoMap}" var="m" varStatus="status" >
								<tr>
									<td>${status.index+1}</td>
									<td>${m.unitName}</td>
									<td>${m.objName}</td>
									<td>${m.job}</td>
									<td><fmt:formatNumber type="number" value="${m.totalScore}" pattern="#.####"/></td>
									<td>${m.items}</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</c:forEach>
				</c:if>
			</div>
		</div>
	</div>
</div>

