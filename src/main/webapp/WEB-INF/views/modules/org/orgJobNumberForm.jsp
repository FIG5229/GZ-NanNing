<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单位职数信息管理</title>
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
		if("sucess"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li><a href="${ctx}/org/orgJobNumber/">单位职数信息列表</a></li>
		<li class="active"><a href="${ctx}/org/orgJobNumber/form?id=${orgJobNumber.id}">单位职数信息<shiro:hasPermission name="org:orgJobNumber:edit">${not empty orgJobNumber.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="org:orgJobNumber:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="orgJobNumber" action="${ctx}/org/orgJobNumber/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<%--机构id--%>
		<input id="orgId" name="orgId" type="hidden" value="${orgJobNumber.orgId}"/>
		<div class="control-group">
			<label class="control-label">批准时间：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${orgJobNumber.date}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准单位：</label>
			<div class="controls">
				<form:input path="unit" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准文号：</label>
			<div class="controls">
				<form:input path="fileNo" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">部级正职领导职数：</label>
			<div class="controls">
				<form:input path="bzNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">部级副职领导职数：</label>
			<div class="controls">
				<form:input path="bfNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">厅级正职领导职数：</label>
			<div class="controls">
				<form:input path="tzNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">厅级副职领导职数：</label>
			<div class="controls">
				<form:input path="tfNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">厅级正职非领导职数：</label>
			<div class="controls">
				<form:input path="tzfNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">厅级副职非领导职数：</label>
			<div class="controls">
				<form:input path="tffNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处级正职领导职数：</label>
			<div class="controls">
				<form:input path="czNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处级副职领导职数：</label>
			<div class="controls">
				<form:input path="cfNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处级正职非领导职数：</label>
			<div class="controls">
				<form:input path="czfNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处级副职非领导职数：</label>
			<div class="controls">
				<form:input path="cffNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">科级正职领导职数：</label>
			<div class="controls">
				<form:input path="kzNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">科级副职领导职数：</label>
			<div class="controls">
				<form:input path="kfNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">科级正职非领导职数：</label>
			<div class="controls">
				<form:input path="kzfNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">科级副职非领导职数：</label>
			<div class="controls">
				<form:input path="kffNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">股级正职领导职数：</label>
			<div class="controls">
				<form:input path="gzNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">股级副职领导职数：</label>
			<div class="controls">
				<form:input path="gfNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="org:orgJobNumber:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>