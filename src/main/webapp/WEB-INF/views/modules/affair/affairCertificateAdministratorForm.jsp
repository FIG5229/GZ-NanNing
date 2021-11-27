<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>证书管理员管理</title>
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
	<br/>
	<form:form id="inputForm" modelAttribute="affairCertificateAdministrator" action="${ctx}/affair/affairCertificateAdministrator/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">用户编号：</label>
			<div class="controls">
				<form:input path="number" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构全路径：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairCertificateAdministrator.unitId}" labelName="unit" labelValue="${affairCertificateAdministrator.unit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
<%--		<div class="control-group">--%>
<%--			<label class="control-label">机构id：</label>--%>
<%--			<div class="controls">--%>
<%--				<form:input path="unitId" htmlEscape="false" class="input-xlarge "/>--%>
<%--			</div>--%>
<%--		</div>--%>
<%--		<div class="control-group">--%>
<%--			<label class="control-label">创建者机构id：</label>--%>
<%--			<div class="controls">--%>
<%--				<form:input path="createOrgId" htmlEscape="false" class="input-xlarge "/>--%>
<%--			</div>--%>
<%--		</div>--%>
<%--		<div class="control-group">--%>
<%--			<label class="control-label">更新者机构id：</label>--%>
<%--			<div class="controls">--%>
<%--				<form:input path="updateOrgId" htmlEscape="false" class="input-xlarge "/>--%>
<%--			</div>--%>
<%--		</div>--%>
<%--		<div class="control-group">--%>
<%--			<label class="control-label">创建者身份证号：</label>--%>
<%--			<div class="controls">--%>
<%--				<form:input path="createIdNo" htmlEscape="false" class="input-xlarge "/>--%>
<%--			</div>--%>
<%--		</div>--%>
<%--		<div class="control-group">--%>
<%--			<label class="control-label">更新者身份证号：</label>--%>
<%--			<div class="controls">--%>
<%--				<form:input path="updateIdNo" htmlEscape="false" class="input-xlarge "/>--%>
<%--			</div>--%>
<%--		</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairCertificateAdministrator:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.Close();"/>
		</div>
	</form:form>
	<script>
		if ("success" == "${saveResult}") {
			parent.$.jBox.close();
		}
	</script>
</body>
</html>