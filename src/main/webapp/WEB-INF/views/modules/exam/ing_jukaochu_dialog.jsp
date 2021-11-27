<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<title>局考处-生成表格</title>
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
<!-- 绩效考评-局考处-生成表格-->
<div id="modalAllPublic" class="">
	<div class="">
		<div class="modal-custom-main">
			<%--工作流程--%>
			<%--<%@include file="/WEB-INF/views/modules/exam/step.jsp" %>--%>
			<div>
				<form:form id="jukaochuExportForm" action="${ctx}/exam/examWorkflow/jukaochuExport?isFillPerson=${isFillPerson}&workflowId=${workflowId}&objId=${objId}" method="post" class="breadcrumb form-search" cssStyle="text-align: right">
					<input id="export" class="btn btn-primary" type="button" value="导出"/>
					<c:if test="${not empty objId}">
						<input id="back" class="btn" type="button" value="返回"  onclick="history.go(-1)"/>
					</c:if>
				</form:form>
				<c:if test="${not empty message}">
					<div id="messageBox" class="alert alert-error"><button data-dismiss="alert" class="close">×</button>${message}</div>
				</c:if>
				<%--<sys:message content="${message}"></sys:message>--%>
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<caption>
						<%--考评名称--%>
						<h3>${workflowName}</h3>
					</caption>
					<thead>
					<tr>
						<%--当前流程状态非自评状态且当前用户非填报人，显示各个公安处总得分--%>
						<c:if test="${isFillPerson ne 'isFillPerson'}">
						<%--table表头   北海处总得分  南宁公安处  柳州公安处总得分  --%>
						<th colspan="${3+3*unitList.size()}">
							<c:forEach items="${unitList}" var="m" varStatus="status">
								${m.unitName}总得分：
								<span style="color: #2aabd2"><fmt:formatNumber type="number"  value="${m.totaltScore}" pattern="#.####"/></span>分 &nbsp;&nbsp;&nbsp;&nbsp;
								<%--<a href="${ctx}/exam/examWorkflow/publicDetail?workflowId=${workflowId}&objId=${m.unitId}">${m.totaltScore}</a>分 &nbsp;&nbsp;&nbsp;&nbsp;--%>
							</c:forEach>
						</th>
						</c:if>
						<%--<th colspan="${3+2*conventionScoreList[0].size()}">
							<c:forEach items="${conventionScoreList[0]}" var="m" varStatus="status">
							${m.unitName}总得分：
								&lt;%&ndash;a标签跳转查看详情&ndash;%&gt;
								<a href="${ctx}/exam/examWorkflow/publicDetail?workflowId=${workflowId}&objId=${m.unitId}">${weightSorceList[status.index]}</a>分 &nbsp;&nbsp;&nbsp;&nbsp;
							</c:forEach>
						</th>--%>
					</tr>
					<%--第二行表头--%>
					<tr>
						<th rowspan="2">序号</th>
						<th rowspan="2">各业务工作及权重</th>
						<th rowspan="2">各业务工作所占权重分</th>
						<%--北海公安处、南宁公安处、柳州公安处。。。。。--%>
						<%--<th  colspan="4"> 南宁公安处(测试)</th>--%>
						<c:forEach items="${unitList}" var="m">
							<th colspan="3">${m.unitName}</th>
						</c:forEach>
					</tr>
					<tr>
						<%--<th>业务100分制得分</th>
						<th>业务折算权重后得分</th>
						<th>公共加、扣分合计</th>
						<th>具体事项</th>--%>
						<c:forEach items="${unitList}" var="m">
							<th>业务100分制得分</th>
							<th>折算权重后得分</th>
							<%--<th>公共加、扣分合计</th>--%>
							<c:choose>
								<c:when test="${fn:length(unitList) == 1}"><th>具体事项</th></c:when>
								<c:otherwise><th style="width: 230px;">具体事项</th></c:otherwise>
							</c:choose>

						</c:forEach>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${conventionScoreList}" var="list" varStatus="status">
						<tr>
							<td>${status.index+1}</td>
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
											<%--生成的表格，重点工作是直接按照“权重分”分值往下扣，没有100分这种，（反恐防爆、党建工作、信息化工作这三个）11.19--%>
											<%--<c:when test="${list[0].workNameValue eq '7' || list[0].workNameValue eq '18' || list[0].workNameValue eq '29'}">--%>
											<c:when test="${list[0].workNameType eq '1'}">
											<%--<c:when test="${list[0].workName eq '反恐防暴' || list[0].workName eq '党建工作' || list[0].workName eq '信息化工作'}">--%>
												<td>${m.hundredScore}</td>
											</c:when>
											<c:when test="${list[0].workNameType eq '3'}">
												<%--公共加扣分不显示--%>
												<td></td>
											</c:when>
											<c:otherwise>
												<td><fmt:formatNumber type="number" value="${m.hundredScore}" pattern="#.####"/></td>
											</c:otherwise>
										</c:choose>
										<td><fmt:formatNumber type="number" value="${m.weightScore}" pattern="#.####"/></td>
										<%--<td>公共加扣分</td>--%>
										<td style="text-align: left">${m.specificItem}</td><%--具体事项--%>
									</c:when>
									<c:otherwise>
										<td></td>
										<td></td>
										<%--<td></td>--%>
										<td></td>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="4">业务权重合计得分</td>
						<c:forEach items="${unitList}" var="m" varStatus="status">
							<c:choose>
								<c:when test="${status.first}">
									<td colspan="2"><fmt:formatNumber type="number" value="${m.totalWeightScore}" pattern="#.####"/></td>
								</c:when>
								<c:otherwise>
									<td colspan="3"><fmt:formatNumber type="number" value="${m.totalWeightScore}" pattern="#.####"/></td>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</tr>
					<tr>
						<td colspan="4">总公共扣分</td>
						<c:forEach items="${unitList}" var="m" varStatus="status">
							<c:choose>
								<c:when test="${status.first}">
									<td colspan="2"><fmt:formatNumber type="number" value="${m.totalPublicDeductScore}" pattern="#.####"/></td>
								</c:when>
								<c:otherwise>
									<td colspan="3"><fmt:formatNumber type="number" value="${m.totalPublicDeductScore}" pattern="#.####"/></td>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</tr>
					<tr>
						<td colspan="4">总公共加分</td>
						<c:forEach items="${unitList}" var="m" varStatus="status">
							<c:choose>
								<c:when test="${status.first}">
									<td colspan="2"><fmt:formatNumber type="number" value="${m.totalPublicAddScore}" pattern="#.####"/></td>
								</c:when>
								<c:otherwise>
									<td colspan="3"><fmt:formatNumber type="number" value="${m.totalPublicAddScore}" pattern="#.####"/></td>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</tr>
					<tr>
						<td colspan="4">总得分</td>
						<c:forEach items="${unitList}" var="m" varStatus="status">
							<c:choose>
								<c:when test="${status.first}">
									<td colspan="2"><fmt:formatNumber type="number" value="${m.totaltScore}" pattern="#.####"/></td>
								</c:when>
								<c:otherwise>
									<td colspan="3"><fmt:formatNumber type="number" value="${m.totaltScore}" pattern="#.####"/></td>
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

