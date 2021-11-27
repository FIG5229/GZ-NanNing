<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党内主题实践活动管理</title>
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
		<li><a href="${ctx}/affair/affairThemeActivity/">党内主题实践活动列表</a></li>
		<li class="active"><a href="${ctx}/affair/affairThemeActivity/form?id=${affairThemeActivity.id}">党内主题实践活动<shiro:hasPermission name="affair:affairThemeActivity:edit">${not empty affairThemeActivity.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairThemeActivity:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
    <br/>
	<form:form id="inputForm" modelAttribute="affairThemeActivity" action="${ctx}/affair/affairThemeActivity/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">活动名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">党组织名称：</label>
			<div class="controls">
				<sys:treeselect id="partyOrganizationName" name="partyOrganizationId" value="${affairThemeActivity.partyOrganizationId}" labelName="partyOrganizationName" labelValue="${affairThemeActivity.partyOrganizationName}"
					title="党组织名称" url="/affair/affairGeneralSituation/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">举办单位：</label>
			<div class="controls">
				<sys:treeselect id="holdUnitName" name="holdUnitId" value="${affairThemeActivity.holdUnitId}" labelName="holdUnitName" labelValue="${affairThemeActivity.holdUnitName}"
					title="举办单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动开始时间：</label>
			<div class="controls">
				<input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairThemeActivity.startTime}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动结束时间：</label>
			<div class="controls">
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairThemeActivity.endTime}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_theme_activity')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动地点：</label>
			<div class="controls">
				<form:textarea path="place" htmlEscape="false" rows="4" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参加人员：</label>
			<div class="controls">
				<form:textarea path="joinPerson" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作流程：</label>
			<div class="controls">
				<form:textarea id="workflow" htmlEscape="true" path="workflow" rows="4" class="input-xxlarge"/>
				<%--<sys:ckeditor replace="workflow"/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关材料：</label>
			<div class="controls">
				<form:hidden id="materialPath" path="materialPath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="materialPath" type="files" uploadPath="affair/affairThemeActivity" selectMultiple="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairThemeActivity:manage"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>