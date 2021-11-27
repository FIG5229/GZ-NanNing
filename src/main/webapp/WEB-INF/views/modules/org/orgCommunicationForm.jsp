<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单位通讯信息管理</title>
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
		<li><a href="${ctx}/org/orgCommunication/">单位通讯信息列表</a></li>
		<li class="active"><a href="${ctx}/org/orgCommunication/form?id=${orgCommunication.id}">单位通讯信息<shiro:hasPermission name="org:orgCommunication:edit">${not empty orgCommunication.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="org:orgCommunication:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="orgCommunication" action="${ctx}/org/orgCommunication/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<%--机构id--%>
		<input id="orgId" name="orgId" type="hidden" value="${orgCommunication.orgId}"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">单位邮政编码：</label>
			<div class="controls">
				<form:input path="postCode" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位电话号码：</label>
			<div class="controls">
				<form:input path="telephone" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位传真号码：</label>
			<div class="controls">
				<form:input path="faxNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位网址：</label>
			<div class="controls">
				<form:input path="website" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位E_MAIL地址：</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="org:orgCommunication:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>