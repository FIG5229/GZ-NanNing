<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党内培训管理</title>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairPartyTraining/">党内培训列表</a></li>
		<li class="active"><a href="${ctx}/affair/affairPartyTraining/form?id=${affairPartyTraining.id}">党内培训<shiro:hasPermission name="affair:affairPartyTraining:edit">${not empty affairPartyTraining.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairPartyTraining:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="affairPartyTraining" action="${ctx}/affair/affairPartyTraining/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">主办单位：</label>
			<div class="controls">
				<form:input path="hostUnit" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<input name="beginTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairPartyTraining.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairPartyTraining.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培训地点：</label>
			<div class="controls">
				<form:input path="trainingSites" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培训名称：</label>
			<div class="controls">
				<form:input path="trainingName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培训形式：</label>
			<div class="controls">
				<form:input path="trainingForms" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培训结果：</label>
			<div class="controls">
				<form:input path="trainingOutcome" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div><%--
		<div class="control-group">
			<label class="control-label">创建者：</label>
			<div class="controls">
				<form:input path="creator" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建者机构ID：</label>
			<div class="controls">
				<form:input path="creatorOrgId" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建者身份证号：</label>
			<div class="controls">
				<form:input path="creatorIdNo" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<input name="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairPartyTraining.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更新者机构id：</label>
			<div class="controls">
				<form:input path="updateOrgId" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更新者身份证号：</label>
			<div class="controls">
				<form:input path="updateIdNo" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairPartyTraining:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>