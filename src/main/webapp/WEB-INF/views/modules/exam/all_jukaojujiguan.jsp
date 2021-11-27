<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript"  src="${ctxStatic}/js/export.js"></script>
<style>
	/*表格居中样式设置*/
	.table th, .table td {
		text-align: center;
		vertical-align: middle !important;
	}
</style>
<script>
	function exportData() {
		dataExport("contentTable");
	}
	//详情窗口
	function createTable(fillPersonId,workflowId,) {
		var url = "iframe:${ctx}/exam/examWorkflow/ckdsAllPublicDetail?workflowId="+workflowId+"&fillPersonId="+fillPersonId;
		top.$.jBox.open(url, "目前各项得分情况",1000,600,{
			buttons:{"关闭":true},
			loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	}
</script>
<meta name="decorator" content="default"/>
<!-- 绩效考评-全局公示 局考局机关-->
<div id="modalAllPublic" class="">
	<div class="">
		<div class="modal-custom-main">
			<%@include file="/WEB-INF/views/modules/exam/step.jsp" %>
			<form:form id="jukaojujiguanExportForm" action="" method="post" class="breadcrumb form-search" cssStyle="text-align: right">
				<input id="export" class="btn btn-primary" type="button" value="导出" onclick="exportData()"/>
				<input id="back" class="btn" type="button" value="返回"  onclick="history.go(-1)"/>
			</form:form>
			<div>
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
							<c:if test="${allPublicExamType ne '3' && isWeightCalculate ne 'isWeightCalculate'}">
							<th>扣分</th>
							<th>加分</th>
							</c:if>
							<th>总得分</th>
						</tr>
					</thead>
					<tbody>
					<c:if  test="${not empty list && list.size()>0}">
						<c:forEach items="${list}" var="m" varStatus="status" >
							<tr>
								<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
								<td>${m.depName}</td>
								<td>${m.depItem}</td>
								<td>${m.addItem}</td>
								<c:if test="${allPublicExamType ne '3' && isWeightCalculate ne 'isWeightCalculate'}">
									<td><fmt:formatNumber type="number" value="${m.deductSource}" pattern="#.####"/></td>
									<td><fmt:formatNumber type="number" value="${m.addSource}" pattern="#.####"/></td>
								</c:if>
								<c:choose>
									<c:when test="${allPublicExamType eq '3' || isWeightCalculate eq 'isWeightCalculate'}">
										<td>
											<%--<span onclick="createTable('${m.depId}','${workflowId}')" style="cursor: pointer;color: #0D8BBD">--%>
												<a href="${ctx}/exam/examWorkflowDatas/examBeta?examWorkflowId=${workflowId}&objId=${m.depId}&publicDetail=true&fillPersonId=${m.depId}">
												<fmt:formatNumber type="number" value="${m.endSumScore}" pattern="#.####"/>
												</a>
											<%--</span>--%>
										</td>
									</c:when>
									<c:otherwise>
										<td>
											<c:choose>
												<c:when test="${empty m.baseSum}">
													<a href="${ctx}/exam/examWorkflowDatas/examBeta?examWorkflowId=${workflowId}&objId=${m.depId}&publicDetail=true&fillPersonId=${m.depId}"><fmt:formatNumber type="number" value="${100-m.deductSource+m.addSource}" pattern="#.####"/></a>
												</c:when>
												<c:otherwise>
													<a href="${ctx}/exam/examWorkflowDatas/examBeta?examWorkflowId=${workflowId}&objId=${m.depId}&publicDetail=true&fillPersonId=${m.depId}"><fmt:formatNumber type="number" value="${m.baseSum-m.deductSource+m.addSource}" pattern="#.####"/></a>
												</c:otherwise>
											</c:choose>
										</td>
									</c:otherwise>
								</c:choose>

							</tr>
						</c:forEach>
					</c:if>

					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

