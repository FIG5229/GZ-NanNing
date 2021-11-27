<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>组织关系对应关系管理</title>
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
		if ("success" == "${saveResult}"){
			parent.$.jBox.tip("保存成功");
			parent.$.jBox.close();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%--<li><a href="${ctx}/sys/sysOffices/">组织关系对应关系列表</a></li>--%>
<%--
		<li class="active"><a href="${ctx}/sys/sysOffices/form?id=${sysOffices.id}">组织关系对应关系<shiro:hasPermission name="sys:sysOffices:edit">${not empty sysOffices.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:sysOffices:edit">查看</shiro:lacksPermission></a></li>
--%>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysOffices" action="${ctx}/sys/sysOffices/saveNew" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="name" name="id" value="${sysOffices.id}" labelName="name" labelValue="${sysOffices.name}"
								title="部门" url="/sys/office/treeData?type=2"  cssClass="required" allowClear="true" notAllowSelectParent="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">党组织名称：</label>
			<div class="controls">
				<sys:treeselect id="partyName" name="partyId" value="${sysOffices.partyId}" labelName="partyName" labelValue="${sysOffices.partyName}"
								title="党组织名称" url="/affair/affairGeneralSituation/treeData" allowClear="true" notAllowSelectParent="false"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">团组织名称：</label>
			<div class="controls">
				<sys:treeselect id="groupName" name="groupId" value="${sysOffices.groupId}" labelName="groupName" labelValue="${sysOffices.groupName}"
								title="团组织名称" url="/affair/affairTwBase/treeData" allowClear="true"/>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工会组织名称：</label>
			<div class="controls">
				<sys:treeselect id="unionName" name="unionId" value="${sysOffices.unionId}" labelName="unionName" labelValue="${sysOffices.unionName}"
								title="工会组织名称" url="/affair/affairOrganizationBulid/treeData" allowClear="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:sysOffices:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>