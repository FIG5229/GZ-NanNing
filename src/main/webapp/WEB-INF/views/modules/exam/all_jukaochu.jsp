<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
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
							$("#jukaochuExportForm").submit();
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
<!-- 绩效考评-全局公示 局考处、处考队所-->
<div id="modalAllPublic" class="">
	<div class="">
		<div class="modal-custom-main">
			<%--<%@include file="/WEB-INF/views/modules/exam/step.jsp" %>--%>
			<form:form id="jukaochuExportForm" action="${ctx}/exam/examWorkflow/jukaochuExport?workflowId=${workflowId}&objId=${objId}" method="post" class="breadcrumb form-search" cssStyle="text-align: right">
				<input id="export" class="btn btn-primary" type="button" value="导出"/>
				<input id="back" class="btn" type="button" value="返回"  onclick="history.go(-1)"/>
			</form:form>
			<div>
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<caption>
						<h3>${workflowName}</h3>
					</caption>
					<thead>
					<tr>
						<th colspan="${3+2*conventionScoreList[0].size()}">
							<c:forEach items="${conventionScoreList[0]}" var="m" varStatus="status">
							${m.unitName}总得分：
								<%--<a href="${ctx}/exam/examWorkflowDatas/examBeta?examWorkflowId=${workflowId}&objId=${m.unitId}&publicDetail=true&noTree=${noTree}">${weightSorceList[status.index]}</a>--%>
								<a href="${ctx}/exam/examWorkflow/jkcAllPublicDetail?workflowId=${workflowId}&objId=${m.unitId}">${sumSorceList[status.index]}</a>
								分 &nbsp;&nbsp;&nbsp;&nbsp;
							</c:forEach>
						</th>
					</tr>
					<tr>
						<th rowspan="2">序号</th>
						<th rowspan="2">各业务工作及权重</th>
						<th rowspan="2">各业务工作所占权重分</th>
						<c:forEach items="${conventionScoreList[0]}" var="m">
							<th colspan="2">${m.unitName}</th>
						</c:forEach>
					</tr>
					<tr>
						<c:forEach items="${conventionScoreList[0]}" var="m">
							<th>业务100分制得分</th>
							<th>折算权重后得分</th>
						</c:forEach>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${conventionScoreList}" var="list" varStatus="status">
						<tr>
							<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
							<td>${list[0].workName}</td>
							<c:choose>
								<c:when test="${list[0].workNameType eq '3'}">
									<%--公共加扣分不显示--%>
									<td></td>
								</c:when>
								<c:otherwise><td>${list[0].weight}</td></c:otherwise>
							</c:choose>
							<c:forEach items="${list}" var="m">
								<c:choose>
									<c:when test="${not empty m}">
										<c:choose>
											<c:when test="${m.workNameType eq '1'}">
												<td>-</td>
											</c:when>
											<c:when test="${list[0].workNameType eq '3'}">
												<%--公共加扣分不显示--%>
												<td></td>
											</c:when>
											<c:otherwise>
												<td><fmt:formatNumber type="number" value="${m.hundredSum}" pattern="#.####"/></td>
											</c:otherwise>
										</c:choose>
										<td><fmt:formatNumber type="number" value="${m.zsqzhScore}" pattern="#.####"/></td>
									</c:when>
									<c:otherwise>
										<td></td>
										<td></td>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="4">业务权重合计得分</td>
						<c:forEach items="${weightSorceList}" var="m" varStatus="status">
							<c:choose>
								<c:when test="${status.first}">
									<td>${m}</td>
								</c:when>
								<c:otherwise>
									<td></td>
									<td>${m}</td>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</tr>
					<tr>
						<td colspan="4">总得分</td>
						<c:forEach items="${sumSorceList}" var="m" varStatus="status">
							<c:choose>
								<c:when test="${status.first}">
									<td>${m}</td>
								</c:when>
								<c:otherwise>
									<td colspan="2">${m}</td>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

