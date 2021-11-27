<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<!-- 绩效考评-系统自评 -->
<div id="modalSysSelf">
	<div class="">
		<div class="modal-custom-head">
			<h3 class="close" onclick="$(this).parent().parent().parent().css('display','none')">x</h3>
		</div>
		<div class="modal-custom-main">
			<div class="modal-custom-main-step">
				<div class="step-start active">开始</div>
				<div class="modal-step-col active">系统自评</div>
				<div class="modal-step-col">系统初步考核</div>
				<div class="modal-step-col">系统公示</div>
				<div class="modal-step-col">部门负责人签字</div>
				<div class="modal-step-col">分管局领导签字</div>
				<div class="modal-step-col">绩效考评领导小组复核及调整</div>
				<div class="modal-step-col">局主管领导最终审签</div>
				<div class="modal-step-col">最终结果全局公示</div>
				<div class="step-start step-end">结束</div>
			</div>
			<div class="modal-custom-bar">
				<div class="modal-custom-btn red">部门业务扣分</div>
				<div class="modal-custom-btn">部门公共扣分</div>
				<div class="modal-custom-btn">部门公共加分</div>
			</div>
			<div class="modal-custom-content">
				<div class="modal-custom-tb-l">
					<table class="table table-striped table-bordered table-condensed" style="width: 100%;">
						<thead>
							<tr>
								<th>分类</th>
								<th>序号</th>
								<th>考评点</th>
								<th>考点类型</th>
								<th>分值/扣奖</th>
								<th>选择</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>治安工作</td>
								<td>1</td>
								<td>xxxxxxxxxxxxxxxx</td>
								<td>扣分</td>
								<td>10</td>
								<td><input class="i-checks" type="checkbox" name="peocheck"></td>
							</tr>
							<tr>
								<td>治安工作</td>
								<td>2</td>
								<td>xxxxxxxxxxxxxxxx</td>
								<td>扣分</td>
								<td>10</td>
								<td><input class="i-checks" type="checkbox" name="peocheck"></td>
							</tr>
							<tr>
								<td>安检工作</td>
								<td>3</td>
								<td>xxxxxxxxxxxxxxxx</td>
								<td>扣分</td>
								<td>10</td>
								<td><input class="i-checks" type="checkbox" name="peocheck"></td>
							</tr>
							<tr>
								<td>安检工作</td>
								<td>4</td>
								<td>xxxxxxxxxxxxxxxx</td>
								<td>扣分</td>
								<td>10</td>
								<td><input class="i-checks" type="checkbox" name="peocheck"></td>
							</tr>
							<tr>
								<td>安检工作</td>
								<td>5</td>
								<td>xxxxxxxxxxxxxxxx</td>
								<td>扣分</td>
								<td>10</td>
								<td><input class="i-checks" type="checkbox" name="peocheck"></td>
							</tr>
							<tr>
								<td>安检工作</td>
								<td>6</td>
								<td>xxxxxxxxxxxxxxxx</td>
								<td>扣分</td>
								<td>10</td>
								<td><input class="i-checks" type="checkbox" name="peocheck"></td>
							</tr>
							<tr>
								<td>安检工作</td>
								<td>7</td>
								<td>xxxxxxxxxxxxxxxx</td>
								<td>扣分</td>
								<td>10</td>
								<td><input class="i-checks" type="checkbox" name="peocheck"></td>
							</tr>
							<tr>
								<td>安检工作</td>
								<td>8</td>
								<td>xxxxxxxxxxxxxxxx</td>
								<td>扣分</td>
								<td>10</td>
								<td><input class="i-checks" type="checkbox" name="peocheck"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="modal-custom-tb-c">
					<div class="tb-btn tb-jia"><i class="icon-long-arrow-right"></i></div>
					<div class="tb-btn tb-jian"><i class="icon-long-arrow-left"></i></div>
				</div>
				<div class="modal-custom-tb-r">
					<table class="table table-striped table-bordered table-condensed" style="width: 100%;">
						<thead>
							<tr>
								<th>分类</th>
								<th>序号</th>
								<th>上级检查评分情况</th>
								<th>考评点</th>
								<th>考点类型</th>
								<th>分值/扣奖</th>
								<th>理由</th>
								<th>附件</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>治安工作</td>
								<td>1</td>
								<td>xxxxxxxxxx</td>
								<td>xxxxxxxxxxx</td>
								<td>扣分</td>
								<td><input style="width: 40px;height: 30px;" type="text"></td>
								<td><textarea rows="" cols=""></textarea></td>
								<td>
									<p>xxx.pdf</p>
									<p style="cursor: pointer;color: #2429FF;"><i class="icon-paperclip"></i>附件</p>
								</td>
							</tr>
							<tr>
								<td>治安工作</td>
								<td>2</td>
								<td>xxxxxxxxxx</td>
								<td>xxxxxxxxxxx</td>
								<td>扣分</td>
								<td><input style="width: 40px;height: 30px;" type="text"></td>
								<td><textarea rows="" cols=""></textarea></td>
								<td>
									<p>xxx.pdf</p>
									<p style="cursor: pointer;color: #2429FF;"><i class="icon-paperclip"></i>附件</p>
								</td>
							</tr>
							<tr>
								<td>治安工作</td>
								<td>3</td>
								<td>xxxxxxxxxx</td>
								<td>xxxxxxxxxxx</td>
								<td>扣分</td>
								<td><input style="width: 40px;height: 30px;" type="text"></td>
								<td><textarea rows="" cols=""></textarea></td>
								<td>
									<p>xxx.pdf</p>
									<p style="cursor: pointer;color: #2429FF;"><i class="icon-paperclip"></i>附件</p>
								</td>
							</tr>
							<tr>
								<td>治安工作</td>
								<td>4</td>
								<td>xxxxxxxxxx</td>
								<td>xxxxxxxxxxx</td>
								<td>扣分</td>
								<td><input style="width: 40px;height: 30px;" type="text"></td>
								<td><textarea rows="" cols=""></textarea></td>
								<td>
									<p>xxx.pdf</p>
									<p style="cursor: pointer;color: #2429FF;"><i class="icon-paperclip"></i>附件</p>
								</td>
							</tr>
						</tbody>
					</table>
					<div style="text-align: right;">填报人：<input type="text"></div>
				</div>
			</div>
		</div>
	</div>
</div>