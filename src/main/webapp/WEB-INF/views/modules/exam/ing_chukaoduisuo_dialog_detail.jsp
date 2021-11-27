<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<title>处考队所-生成表格-详情</title>
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
		/*$("#export").click(
				function(){
					dataExport('contentTable');
				}
		);*/
	$("#export").click(
				function(){
					var submit = function (v, h, f) {
						if (v == 'export') {
							//$("#searchForm").attr("action","${ctx}/exam/examWorkflow/jukaochuExport");
							$("#chuKpcsExportForm").submit();
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
			<div>
				<form:form id="chuKpcsExportForm" action="${ctx}/exam/examWorkflow/chuKaoDuiSuoExport" method="post" class="breadcrumb form-search" cssStyle="text-align: right">
					<input name="workflowId" id="workflowId" type="hidden" value="${workflowId}" />
					<input name="fillPersonId" id="fillPersonId" type="hidden" value="${fillPersonId}"/>
					<input id="export" class="btn btn-primary" type="button" value="导出"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="print" class="btn btn-primary" type="button" value="打印"/>
				</form:form>
				<c:if test="${not empty message}">
					<div id="messageBox" class="alert alert-error"><button data-dismiss="alert" class="close">×</button>${message}</div>
				</c:if>
				<%--<sys:message content="${message}"></sys:message>--%>
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
					<%--第二行表头--%>
					<tr>
						<th colspan="7" class="centerTd" style="text-align: center">绩效考核评分表</th>
					</tr>
					</thead>
					<tbody>
					<tr>
						<td colspan="7" style="text-align: left">单位：${resultMap.fillPersonName}</td>
					</tr>
					<tr >
						<td colspan="7" style="text-align: center">考核评分情况</td>
					</tr>
					<%--权重值--%>
					<tr>
						<td colspan="5" style="text-align: center">考核扣分</td>
						<td rowspan="2" style="text-align: center">考核加分</td>
						<td rowspan="2" style="text-align: center">考核得分</td>
					</tr>
					<tr>
						<td style="text-align: center">公共部分</td>
						<td colspan="2" style="text-align: center">重点工作</td>
						<td colspan="2" style="text-align: center">业务部分</td>
					</tr>
					<tr>
						<%--扣分还是得分--%>
						<td style="text-align: center">扣分</td>
						<td style="text-align: center">扣分</td>
						<td style="text-align: center">得分</td>
						<td style="text-align: center">扣分</td>
						<td style="text-align: center">得分</td>
						<td rowspan="2" style="text-align: center">${resultMap.publicADD}</td><%--考核加分--%>
						<td rowspan="2" style="text-align: center"><fmt:formatNumber type="number" value="${resultMap.totalScore}" pattern="#.####"/></td><%--考核得分--%>
					</tr>
					<tr>
						<%--得分情况--%>
						<td style="text-align: center"><fmt:formatNumber type="number" value="${resultMap.publicDec}" pattern="#.####"/></td>
						<td style="text-align: center"><fmt:formatNumber type="number" value="${resultMap.zdDec}" pattern="#.####"/></td>
						<td style="text-align: center"><fmt:formatNumber type="number" value="${resultMap.sumZd}" pattern="#.####"/></td>
						<td style="text-align: center"><fmt:formatNumber type="number" value="${resultMap.cgDec}" pattern="#.####"/></td>
						<td style="text-align: center"><fmt:formatNumber type="number" value="${resultMap.sumCg}" pattern="#.####"/></td>
					</tr>
					<tr>
						<td colspan="5" style="text-align: center">扣分理由</td>
						<td style="text-align: center">加分理由</td>
						<td style="text-align: center">备注</td>
					</tr>
					<tr>
						<td>${resultMap.pulicDecItems}</td>
						<td colspan="2">${resultMap.zdDecItems}</td>
						<td colspan="2">${resultMap.cgDecItems}</td>
						<td>${resultMap.pulicAddItems}</td>
						<td></td>
					</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

