<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>模板项管理管理</title>
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
		<li><a href="${ctx}/exam/examStandardTemplateItemDefine/">模板项管理列表</a></li>
		<li class="active"><a href="${ctx}/exam/examStandardTemplateItemDefine/form?id=${examStandardTemplateItemDefine.id}">模板项管理<shiro:hasPermission name="exam:examStandardTemplateItemDefine:edit">${not empty examStandardTemplateItemDefine.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="exam:examStandardTemplateItemDefine:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="examStandardTemplateItemDefine" action="${ctx}/exam/examStandardTemplateItemDefine/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">列名称：</label>
			<div class="controls">
				<form:input path="columnName" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">列类型：</label>
			<div class="controls">
				<form:select path="columnType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('column_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">列顺序号：</label>
			<div class="controls">
				<form:input path="columnOrder" htmlEscape="false" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模板：</label>
			<div class="controls">
				<form:input path="templateId" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="exam:examStandardTemplateItemDefine:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>