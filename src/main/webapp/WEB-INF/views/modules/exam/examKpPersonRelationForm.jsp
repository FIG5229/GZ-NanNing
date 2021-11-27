<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考评初审人员关系表管理</title>
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
<%--	<ul class="nav nav-tabs">
		<li><a href="${ctx}/exam/examKpPersonRelation/">考评初审人员关系表列表</a></li>
		<li class="active"><a href="${ctx}/exam/examKpPersonRelation/form?id=${examKpPersonRelation.id}">考评初审人员关系表<shiro:hasPermission name="exam:examKpPersonRelation:edit">${not empty examKpPersonRelation.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="exam:examKpPersonRelation:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="examKpPersonRelation" action="${ctx}/exam/examKpPersonRelation/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">考评类别：</label>
			<div class="controls">
				<form:select path="kpType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('kp_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">自评人员/单位：</label>
			<div class="controls">
				<sys:treeselect id="kpuser" name="kpUserId" value="${examKpPersonRelation.kpUserId}" labelName="kpUserName" labelValue="${examKpPersonRelation.kpUserName}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"  checked="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">初审人员/单位：</label>
			<div class="controls">
				<sys:treeselect id="csUser" name="csUserId" value="${examKpPersonRelation.csUserName}" labelName="csUserName" labelValue="${examKpPersonRelation.csUserName}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="exam:examKpPersonRelation:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回"  onclick="parent.$.jBox.close()" />
		</div>
	</form:form>
	<script>
		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</body>
</html>