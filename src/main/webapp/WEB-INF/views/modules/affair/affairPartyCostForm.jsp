<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<html>
<head>
	<title>党费管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			$("#baseNum").keydown(function (e) {
				if (e.keyCode == 13) {
					if ($("#type").val() == 1){
						if (0 <= $("#baseNum").val() && $("#baseNum").val() <= 3000){
							$("#proportion").val("0.5");
							$("#cost1").val($("#baseNum").val() * ($("#proportion").val() / 100) );
							$("#cost2").val($("#baseNum").val() * ($("#proportion").val()/ 100) );
						}else if (3000 < $("#baseNum").val() && $("#baseNum").val() <= 5000){
							$("#proportion").val("1");
							$("#cost1").val($("#baseNum").val() * ($("#proportion").val() / 100) );
							$("#cost2").val($("#baseNum").val() * ($("#proportion").val()/ 100) );
						}else if (5000 < $("#baseNum").val() && $("#baseNum").val() <= 10000){
							$("#proportion").val("1.5");
							$("#cost1").val($("#baseNum").val() * ($("#proportion").val() / 100) );
							$("#cost2").val($("#baseNum").val() * ($("#proportion").val()/ 100) );
						}else if (10000 < $("#baseNum").val()){
							$("#proportion").val("2");
							$("#cost1").val($("#baseNum").val() * ($("#proportion").val() / 100) );
							$("#cost2").val($("#baseNum").val() * ($("#proportion").val()/ 100) );
						}
					}else if ($("#type").val() == 2) {
						if (0 <= $("#baseNum").val() && $("#baseNum").val() <= 3000){
							$("#proportion").val("0.5");
							$("#cost1").val($("#baseNum").val() * ($("#proportion").val() / 100) );
							$("#cost2").val($("#baseNum").val() * ($("#proportion").val()/ 100) );
						}else if (3000 < $("#baseNum").val() && $("#baseNum").val() <= 5000){
							$("#proportion").val("1");
							$("#cost1").val($("#baseNum").val() * ($("#proportion").val() / 100) );
							$("#cost2").val($("#baseNum").val() * ($("#proportion").val()/ 100) );
						}else if (5000 < $("#baseNum").val() && $("#baseNum").val() <= 10000){
							$("#proportion").val("1.5");
							$("#cost1").val($("#baseNum").val() * ($("#proportion").val() / 100) );
							$("#cost2").val($("#baseNum").val() * ($("#proportion").val()/ 100) );
						}else if (10000 < $("#baseNum").val()){
							$("#proportion").val("2");
							$("#cost1").val($("#baseNum").val() * ($("#proportion").val() / 100) );
							$("#cost2").val($("#baseNum").val() * ($("#proportion").val()/ 100) );
						}
					}else if ($("#type").val() == 3) {
						if (0 <= $("#baseNum").val() && $("#baseNum").val() <= 5000){
							$("#proportion").val("0.5");
							$("#cost1").val($("#baseNum").val() * ($("#proportion").val() / 100) );
							$("#cost2").val($("#baseNum").val() * ($("#proportion").val()/ 100) );
						}else if (5000 < $("#baseNum").val()){
							$("#proportion").val("1");
							$("#cost1").val($("#baseNum").val() * ($("#proportion").val() / 100) );
							$("#cost2").val($("#baseNum").val() * ($("#proportion").val()/ 100) );
						}
					}else {
						if (0 <= $("#baseNum").val() && $("#baseNum").val() <= 5000){
							$("#proportion").val("0.5");
							$("#cost1").val($("#baseNum").val() * ($("#proportion").val() / 100) );
							$("#cost2").val($("#baseNum").val() * ($("#proportion").val()/ 100) );
						}else if (5000 < $("#baseNum").val()){
							$("#proportion").val("1");
							$("#cost1").val($("#baseNum").val() * ($("#proportion").val() / 100) );
							$("#cost2").val($("#baseNum").val() * ($("#proportion").val()/ 100) );
						}
					}
				}
			})
		});
		if("sucess"=="${saveResult}"){
			parent.$.jBox.close();
		}
		function save() {
			$("#inputForm").submit();
		}
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="affairPartyCost" action="${ctx}/affair/affairPartyCost/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div id="modalInfo1">
			<div class="modal-custom">
				<div class="modal-custom-main">
					<div class="modal-custom-content">
						<div id="contentTable" class="modal-custom-content">
							<div class="modal-custom-info2">
								<div class="modal-custom-info2-col modal-custom-info2-col1">
									<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairPartyCost.affairPartyMember.name}</span></div>
									<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">民族：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPartyCost.affairPartyMember.nation, 'nation', '')}</span></div>
									<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出生日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairPartyCost.affairPartyMember.birth}" pattern="yyyy-MM-dd"/></span></div>
									<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所在党支部：</span><span class="modal-custom-info2-value">${affairPartyCost.affairPartyMember.partyBranch}</span></div>
									<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工作岗位：</span><span class="modal-custom-info2-value">${affairPartyCost.affairPartyMember.workPlace}</span></div>
									<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">参加工作日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairPartyCost.affairPartyMember.workDate}" pattern="yyyy-MM-dd"/></span></div>
									<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">家庭住址：</span><span class="modal-custom-info2-value">${affairPartyCost.affairPartyMember.homeAddress}</span></div>
								</div>
								<div class="modal-custom-info2-col modal-custom-info2-col2">
									<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" >身份证号：</span><span class="modal-custom-info2-value">${affairPartyCost.affairPartyMember.cardNum}</span></div>
									<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">籍贯：</span><span class="modal-custom-info2-value">${affairPartyCost.affairPartyMember.birthplace}</span></div>
									<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学历：</span><span class="modal-custom-info2-value">${affairPartyCost.affairPartyMember.education}</span></div>
									<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">加入党组织日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairPartyCost.affairPartyMember.joinDate}" pattern="yyyy-MM-dd"/></span></div>
									<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">联系电话：</span><span class="modal-custom-info2-value">${affairPartyCost.affairPartyMember.phoneNum}</span></div>
									<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">婚姻状况：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPartyCost.affairPartyMember.maritalStatus, 'affair_marital_status', '')}</span></div>
								</div>
								<div class="modal-custom-info2-col modal-custom-info2-col3" style="width: 300px;">
									<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">性别：</span><span class="modal-custom-info2-value" style="width: 150px;">${fns:getDictLabel(affairPartyCost.affairPartyMember.sex, 'sex', '')}</span></div>
									<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">是否台湾省籍：</span><span class="modal-custom-info2-value" style="width: 150px;">${fns:getDictLabel(affairPartyCost.affairPartyMember.isTaiwan, 'yes_no', '')}</span></div>
									<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">人员类别：</span><span class="modal-custom-info2-value" style="width: 150px;">${fns:getDictLabel(affairPartyCost.affairPartyMember.personnelCategory, 'affair_personnel_category', '')}</span></div>
									<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">转为正式党员日期：</span><span class="modal-custom-info2-value" style="width: 150px;"><fmt:formatDate value="${affairPartyCost.affairPartyMember.turnDate}" pattern="yyyy-MM-dd"/></span></div>
									<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">固定电话：</span><span class="modal-custom-info2-value" style="width: 150px;">${affairPartyCost.affairPartyMember.telephone}</span></div>
									<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">已减少原因：</span><span class="modal-custom-info2-value" style="width: 150px;">${fns:getDictLabel(affairPartyCost.affairPartyMember.deleteReason, 'delete_reason', '')}</span></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="control-group">
			<%--此div是页面为了显示好看--%>
		</div>
		<div class="control-group">
			<label class="control-label">缴费年度：</label>
			<div class="controls">
				<input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
				value="${affairPartyCost.year}" onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">党员类别：</label>
			<div class="controls">
				<form:select id="type" path="type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_personnel_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">缴费基数(元)：</label>
			<div class="controls">
				<form:input id="baseNum" path="baseNum" htmlEscape="false" class="input-xlarge  number required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交纳党费比例(%)：</label>
			<div class="controls">
				<form:input id="bili" path="bili" htmlEscape="false" class="input-xlarge  number required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">应交党费(元)：</label>
			<div class="controls">
				<form:input id="cost1" path="cost1" htmlEscape="false" class="input-xlarge  number required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">实交党费(元)：</label>
			<div class="controls">
				<form:input id="cost2" path="cost2" htmlEscape="false" class="input-xlarge  number required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairPartyCost:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="save()"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>