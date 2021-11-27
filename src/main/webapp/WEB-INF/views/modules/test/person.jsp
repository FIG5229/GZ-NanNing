<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<!-- 人员信息集 -->
<div id="modalPeo">
	<div class="modal-custom">
		<div class="modal-custom-head">
			<h3 class="close" onclick="$(this).parent().parent().parent().css('display','none')">x</h3>
		</div>
		<div class="modal-custom-main">
			<div class="modal-custom-main-title">教育培训（进修）信息集管理</div>
			<div class="modal-custom-bar">
				<div class="modal-custom-btn red">编辑</div>
				<div class="modal-custom-btn">删除</div>
				<div class="modal-custom-btn">导入</div>
			</div>
			<div class="modal-custom-content">
				<table id="modalPeoTb" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th><input type="checkbox" onclick="selectAll(this.checked, 'peocheck')" />全选</th>
							<th>序号</th>
							<th>培训班名称</th>
							<th>培训类型</th>
							<th>培新完成情况</th>
							<th>培训时所在单位及职务</th>
							<th>培训机构名称</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input class="i-checks" type="checkbox" name="peocheck"></td>
							<td>1</td>
							<td>治安工作</td>
							<td>治安工作</td>
							<td>治安工作</td>
							<td>治安工作</td>
							<td>治安工作</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="modal-custom-page">分页组件</div>
		</div>
	</div>
</div>
