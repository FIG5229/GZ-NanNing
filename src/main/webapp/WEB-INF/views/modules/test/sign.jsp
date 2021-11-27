<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>


<!-- 绩效考评-部门负责人签字 -->
<div id="modalSysDepartment" class="">
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
				<div class="modal-step-col active">部门负责人签字</div>
				<div class="modal-step-col">分管局领导签字</div>
				<div class="modal-step-col">绩效考评领导小组复核及调整</div>
				<div class="modal-step-col">局主管领导最终审签</div>
				<div class="modal-step-col">最终结果全局公示</div>
				<div class="step-start step-end">结束</div>
			</div>
			<div class="modal-custom-tab">
				<div class="modal-custom-tab-item">南宁公安处（99）</div>
				<div class="modal-custom-tab-item">南宁公安处（99）</div>
				<div class="modal-custom-tab-item">南宁公安处（99）</div>
			</div>
			<div class="modal-custom-bar">
				<div class="modal-custom-btn red">部门业务扣分</div>
				<div class="modal-custom-btn">部门公共扣分</div>
				<div class="modal-custom-btn">部门公共扣分</div>
			</div>
			<div class="modal-custom-content">
				<table id="modalPeoTb" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th><input type="checkbox" onclick="selectAll(this.checked, 'peocheck')" />全选</th>
							<th>序号</th>
							<th>工作</th>
							<th>考评内容</th>
							<th>分值/奖金</th>
							<th>自评时间</th>
							<th>自评人</th>
							<th>附件</th>
							<th>考评人</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input class="i-checks" type="checkbox" name="peocheck"></td>
							<td>1</td>
							<td>安检工作</td>
							<td>
								<p>1.xxxx,扣x分</p>
								<p>2.xxxx,扣x分</p>
								<p>3.xxxx,扣x分</p>
							</td>
							<td>-2</td>
							<td>2019-01-01 15:01:01</td>
							<td>xxx</td>
							<td>
								<p>xxx.pdf</p>
								<p style="cursor: pointer;color: #2429FF;"><i class="icon-paperclip"></i>附件</p>
							</td>
							<td>xxx</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="modal-custom-info1-bottom">
				<div class="modal-custom-info1-btn red">确认签字</div>
			</div>
		</div>
	</div>
</div>
