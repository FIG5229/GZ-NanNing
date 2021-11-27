<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
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
						<th colspan="4">重点工作</th>
						<th colspan="13">业务工作</th>
						<th colspan="11">队伍建设</th>
						<th rowspan="2">公共扣分</th>
						<th rowspan="2">公共加分</th>
						<th rowspan="2">考核得分合计</th>
					</tr>
					<tr>
						<th>高铁安保</th>
						<th>反恐防暴</th>
						<th>党建工作</th>
						<th>信息化工作</th>
						<th>治安工作</th>
						<th>刑侦工作</th>
						<th>刑事技术工作</th>
						<th>禁毒工作</th>
						<th>安检工作</th>
						<th>国保工作</th>
						<th>内保工作</th>
						<th>法制监管</th>
						<th>消防工作</th>
						<th>警卫工作</th>
						<th>保安管理</th>
						<th>交通管理</th>
						<th>警犬工作</th>
						<th>指挥中心工作</th>
						<th>网络安全</th>
						<th>办公室工作</th>
						<th>装备财务</th>
						<th>组织人事工作</th>
						<th>宣教工作</th>
						<th>教育培训</th>
						<th>纪检监察工作</th>
						<th>警务督察</th>
						<th>工会工作</th>
						<th>共青团工作</th>
					</tr>
					</thead>
					<tbody>
					<%--权重值--%>
					<tr>
						<td colspan="3">权重分值</td>
						<td>${pcsResultList[0].weight高铁安保}</td>
						<td>${pcsResultList[0].weight反恐防暴}</td>
						<td>${pcsResultList[0].weight党建工作}</td>
						<td>${pcsResultList[0].weight信息化工作}</td>
						<td>${pcsResultList[0].weight治安工作}</td>
						<td>${pcsResultList[0].weight刑侦工作}</td>
						<td>${pcsResultList[0].weight刑事技术工作}</td>
						<td>${pcsResultList[0].weight禁毒工作}</td>
						<td>${pcsResultList[0].weight安检工作}</td>
						<td>${pcsResultList[0].weight国保工作}</td>
						<td>${pcsResultList[0].weight内保工作}</td>
						<td>${pcsResultList[0].weight法制监管}</td>
						<td>${pcsResultList[0].weight消防工作}</td>
						<td>${pcsResultList[0].weight警卫工作}</td>
						<td>${pcsResultList[0].weight保安管理}</td>
						<td>${pcsResultList[0].weight交通管理}</td>
						<td>${pcsResultList[0].weight警犬工作}</td>
						<td>${pcsResultList[0].weight指挥中心工作}</td>
						<td>${pcsResultList[0].weight网络安全}</td>
						<td>${pcsResultList[0].weight办公室工作}</td>
						<td>${pcsResultList[0].weight装备财务}</td>
						<td>${pcsResultList[0].weight组织人事工作}</td>
						<td>${pcsResultList[0].weight宣教工作}</td>
						<td>${pcsResultList[0].weight教育培训}</td>
						<td>${pcsResultList[0].weight纪检监察工作}</td>
						<td>${pcsResultList[0].weight警务督察}</td>
						<td>${pcsResultList[0].weight工会工作}</td>
						<td>${pcsResultList[0].weight共青团工作}</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<%--派出所--%>
					<c:forEach items="${pcsResultList}" var="m" varStatus="status">
						<tr>
							<td rowspan="2">${status.index+1}</td>
							<td rowspan="2">${m.fillPersonName}</td>
							<td>考核原始分</td>
								<%--分数--%>
							<td>${m.weightScore高铁安保}</td>
							<td>${m.weightScore反恐防暴}</td>
							<td>${m.weightScore党建工作}</td>
							<td>${m.weightScore信息化工作}</td>
							<td>${m.Score治安工作}</td>
							<td>${m.Score刑侦工作}</td>
							<td>${m.Score刑事技术工作}</td>
							<td>${m.Score禁毒工作}</td>
							<td>${m.Score安检工作}</td>
							<td>${m.Score国保工作}</td>
							<td>${m.Score内保工作}</td>
							<td>${m.Score法制监管}</td>
							<td>${m.Score消防工作}</td>
							<td>${m.Score警卫工作}</td>
							<td>${m.Score保安管理}</td>
							<td>${m.Score交通管理}</td>
							<td>${m.Score警犬工作}</td>
							<td>${m.Score指挥中心工作}</td>
							<td>${m.Score网络安全}</td>
							<td>${m.Score办公室工作}</td>
							<td>${m.Score装备财务}</td>
							<td>${m.Score组织人事工作}</td>
							<td>${m.Score宣教工作}</td>
							<td>${m.Score教育培训}</td>
							<td>${m.Score纪检监察工作}</td>
							<td>${m.Score警务督察}</td>
							<td>${m.Score工会工作}</td>
							<td>${m.Score共青团工作}</td>
							<td>${m.Score公共扣分}</td>
							<td>${m.Score公共加分}</td>
							<td rowspan="2"><span onclick="openCKPcsDetail('${m.fillPersonId}','${workflowId}')" style="cursor: pointer;color: #0D8BBD"><fmt:formatNumber type="number" value="${m.totalScore}" pattern="#.####"/></span></td>
						</tr>
						<tr>
							<td>权重折算</td>
								<%--分数--%>
							<td><fmt:formatNumber type="number" value="${m.weightScore高铁安保}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore反恐防暴}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore党建工作}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore信息化工作}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore治安工作}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore刑侦工作}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore刑事技术工作}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore禁毒工作}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore安检工作}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore国保工作}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore内保工作}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore法制监管}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore消防工作}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore警卫工作}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore保安管理}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore交通管理}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore警犬工作}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore指挥中心工作}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore网络安全}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore办公室工作}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore装备财务}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore组织人事工作}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore宣教工作}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore教育培训}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore纪检监察工作}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore警务督察}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore工会工作}" pattern="#.####"/></td>
							<td><fmt:formatNumber type="number" value="${m.weightScore共青团工作}" pattern="#.####"/></td>
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

