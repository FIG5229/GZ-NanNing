<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript"  src="${ctxStatic}/js/export.js"></script>
<title>处考队所-生成表格</title>
<style>
	/*表格居中样式设置*/
	.table th, .table td {
		text-align: center;
		vertical-align: middle !important;
	}
</style>
<script>
	$(document).ready(function() {
		$("#print").click(function(){
			$("#contentTable").printThis({
				debug: false,
				importCSS: true,
				importStyle: true,
				printContainer: true,
				loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
				pageTitle: "打印",
				removeInline: false,
				printDelay: 333,
				header: null,
				formValues: false,
				afterPrint:function (){
					$('.download').css('display', '');
				}
			});
		});
	/*	$("#export").click(

				function(){
					dataExport('contentTable');
				}
		);*/
	/*	$("#export").click(
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
		);*/
	});
	//详情窗口
	function openCKPcsDetail(fillPersonId,workflowId,) {
		top.$.jBox.open("iframe:${ctx}/exam/examWorkflow/ingchuKPcsDetail?fillPersonId="+fillPersonId+"&workflowId="+workflowId,"得分详情",1200,600,{
			buttons:{"关闭":true},
			loaded:function () {
				$(".jbox-content",top.document).css("overflow-y","hidden");
			},closed:function () {
			}
		});
	}

	function exportData() {
		dataExport("contentTable");
	}
</script>
<meta name="decorator" content="default"/>
<!-- 绩效考评-局考处-生成表格-->
<div id="modalAllPublic" class="">
	<div class="">
		<div class="modal-custom-main">
			<%--工作流程--%>
		<%--	<%@include file="/WEB-INF/views/modules/exam/step.jsp" %>--%>
			<div>
				<form:form id="chuKpcsExportForm" action="" method="post" class="breadcrumb form-search" cssStyle="text-align: right">
					<input id="export" class="btn btn-primary" type="button" value="导出" onclick="exportData()"/>
					<input id="print" class="btn btn-primary" type="button" value="打印"/>
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
					<%--第二行表头--%>
					<tr>
						<th rowspan="2">序号</th>
						<th rowspan="2">被考单位</th>
						<th rowspan="2">项目</th>
						<th colspan="${zdNameList.size()}">重点工作</th>
						<th colspan="${cgNameList.size()}">常规业务</th>
						<%--<th colspan="11">队伍建设</th>--%>
						<th rowspan="2">公共扣分</th>
						<th rowspan="2">公共加分</th>
						<th rowspan="2">考核得分合计</th>
					</tr>
					<tr>
						<c:forEach items="${zdNameList}" var="zdName" varStatus="status">
							<th>${zdName}</th>
						</c:forEach>
						<c:forEach items="${cgNameList}" var="cgName" varStatus="status">
							<th>${cgName}</th>
						</c:forEach>
					</tr>
					<%--权重值--%>
					<tr>
						<td colspan="3">权重分值</td>
						<c:forEach items="${zdWeightList}" var="zdWeight" varStatus="status">
							<th>${zdWeight}</th>
						</c:forEach>
						<c:forEach items="${cgWeightList}" var="cgWeight" varStatus="status">
							<th>${cgWeight}</th>
						</c:forEach>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					</thead>
					<tbody>

					<%--派出所--%>
					<c:forEach items="${pcsResultList}" var="m" varStatus="status">
						<tr>
							<td rowspan="2">${status.index+1}</td>
							<td rowspan="2">${m.fillPersonName}</td>
							<td>考核原始分</td>
								<%--分数--%>
							<c:forEach items="${zdScoreNameList}" var="zdScoreName" varStatus="status">
								<td><fmt:formatNumber type="number" value="${m[zdScoreName]}" pattern="#.##"/></td>
							</c:forEach>
							<c:forEach items="${cgScoreNameList}" var="cgScoreName" varStatus="status">
								<td><fmt:formatNumber type="number" value="${m[cgScoreName]}" pattern="#.##"/></td>
							</c:forEach>

							<td>${m.Score公共扣分}</td>
							<td>${m.Score公共加分}</td>
							<td rowspan="2"><span onclick="openCKPcsDetail('${m.fillPersonId}','${workflowId}')" style="cursor: pointer;color: #0D8BBD"><fmt:formatNumber type="number" value="${m.totalScore}" pattern="#.####"/></span></td>
						</tr>
						<tr>
							<td>权重折算</td>
								<%--分数--%>
							<c:forEach items="${zdNameList}" var="zdName" varStatus="status">
							<td><fmt:formatNumber type="number" value="${m[zdName]}" pattern="#.##"/></td>
							</c:forEach>
							<c:forEach items="${cgNameList}" var="cgName" varStatus="status">
								<td><fmt:formatNumber type="number" value="${m[cgName]}" pattern="#.##"/></td>
							</c:forEach>
							<td>${m.Score公共扣分}</td>
							<td>${m.Score公共加分}</td>
						</tr>
					</c:forEach>

					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

