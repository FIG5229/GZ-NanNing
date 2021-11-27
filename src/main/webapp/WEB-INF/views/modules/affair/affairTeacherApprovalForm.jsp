<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>教官晋级审批管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

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
		function setState(state) {
			$("#status").val(state);
			$.post($("#inputForm").attr('action'), $("#inputForm").serialize(), function(result){

				window.parent.window.jBox.close();
			});
		}


		if ("success" == "${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</head>
<body><br/>
	<form:form id="inputForm" modelAttribute="affairTeacherApproval" action="${ctx}/affair/affairTeacherApproval/pass" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" readonly="true" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input path="idNumber" htmlEscape="false" readonly="true" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教官类别：</label>
			<div class="controls">
				<form:select id="instructorCategory" path="instructorCategory" disabled="true"  class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('Instructor_category')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申报类别：</label>
			<div class="controls">
				<form:select id="applyCategory" path="applyCategory" disabled="true"  class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('Instructor_category')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教官级别：</label>
			<div class="controls">
				<form:select id="instructorLevel" path="instructorLevel" disabled="true" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('instructor_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申报级别：</label>
			<div class="controls">
				<form:select id="applyLevel" path="applyLevel" disabled="true" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('instructor_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<input id="status" name="state" type="hidden"/>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairTeacherApproval:approval">
		 		<input id="btnSubmit" class="btn btn-primary" type="button" value="通 过" onclick="setState(2)"/>
		 		<input id="btnSubmit" class="btn btn-primary" type="button" value="驳 回" onclick="setState(3)"/>
			&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>