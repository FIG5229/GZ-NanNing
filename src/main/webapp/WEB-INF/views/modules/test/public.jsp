<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>

<!-- 绩效考评-系统公示 -->
<div id="modalSysPublic" class="">
	<div class="">
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
				<div class="modal-step-col">最终结果全局公示</div>
				<div class="step-start step-end">结束</div>
			</div>
			<div class="modal-custom-content">
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
						<tr>
							<td>南宁公安处</td>
							<td style="color: #2429FF;">90</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>