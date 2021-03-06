<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考评人员分配规则子表管理管理</title>
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
		<li><a href="${ctx}/exam/examPersonsAssignRuleChild/">考评人员分配规则子表管理列表</a></li>
		<li class="active"><a href="${ctx}/exam/examPersonsAssignRuleChild/form?id=${examPersonsAssignRuleChild.id}">考评人员分配规则子表管理<shiro:hasPermission name="exam:examPersonsAssignRuleChild:edit">${not empty examPersonsAssignRuleChild.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="exam:examPersonsAssignRuleChild:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="examPersonsAssignRuleChild" action="${ctx}/exam/examPersonsAssignRuleChild/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">考核部门：</label>
			<div class="controls">
				<form:input path="examDepart" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">自评人：</label>
			<div class="controls">
				<sys:treeselect id="selfPersonIds" name="selfPersonIds" value="${examPersonsAssignRuleChild.selfPersonIds}" labelName="selfPersonNames" labelValue="${examPersonsAssignRuleChild.selfPersonNames}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"  checked="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考评人：</label>
			<div class="controls">
				<sys:treeselect id="examPersonIds" name="examPersonIds" value="${examPersonsAssignRuleChild.examPersonIds}" labelName="examPersonNames" labelValue="${examPersonsAssignRuleChild.examPersonNames}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="exam:examPersonsAssignRuleChild:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>