<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查看报名详细管理</title>
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
		});
		if ("success" == "${saveResult}") {
			parent.$.jBox.close();
		}
	</script>
</head>
<body>
<br/>
	<form:form id="inputForm" modelAttribute="affairPersonnelMessage" action="${ctx}/affair/affairPersonnelMessage/save?lookPers=${lookPers}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">用户姓名：</label>
			<div class="controls">
				<form:input path="username" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<input name="personnelId" value="${personnelId}" style="display:none">
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input path="idNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:select id="gender" path="gender" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">民族：</label>
			<div class="controls">
				<form:input path="nation" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出生日期：</label>
			<div class="controls">
				<input name="birthDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairPersonnelMessage.birthDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">政治面貌：</label>
			<div class="controls">
				<form:input path="politicsStatus" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学历：</label>
			<div class="controls">
				<form:input path="educationBackground" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学位：</label>
			<div class="controls">
				<form:input path="degree" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构：</label>
			<div class="controls">
				<form:input path="organization" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职务：</label>
			<div class="controls">
				<form:input path="post" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参加工作时间：</label>
			<div class="controls">
				<input name="workTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairPersonnelMessage.workTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">到达时间：</label>
			<div class="controls">
				<input name="arrivalTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairPersonnelMessage.arrivalTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">到达车次/航班：</label>
			<div class="controls">
				<form:input path="arrivalTrainNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">到达车站/机场：</label>
			<div class="controls">
				<form:input path="arrivalStationAirport" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公免号：</label>
			<div class="controls">
				<form:input path="officialAccounts" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">返程时间：</label>
			<div class="controls">
				<input name="returnTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairPersonnelMessage.returnTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">返程车次/航班：</label>
			<div class="controls">
				<form:input path="returnTrainNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">返程车站/机场：</label>
			<div class="controls">
				<form:input path="returnStationAirport" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否预定返程车票/机票：</label>
			<div class="controls">
				<form:radiobuttons path="isReserveReturnTicket" items="${fns:getDictList('affair_isValid')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairPersonnelMessage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>