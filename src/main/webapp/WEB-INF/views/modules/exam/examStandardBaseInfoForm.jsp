<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考评标准管理管理</title>
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
<!--
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/exam/examStandardBaseInfo/">考评标准管理列表</a></li>
		<li class="active"><a href="${ctx}/exam/examStandardBaseInfo/form?id=${examStandardBaseInfo.id}">考评标准管理<shiro:hasPermission name="exam:examStandardBaseInfo:edit">${not empty examStandardBaseInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="exam:examStandardBaseInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	-->
	<br/>
	<form:form id="inputForm" modelAttribute="examStandardBaseInfo" action="${ctx}/exam/examStandardBaseInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">考评标准名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考评标准简称：</label>
			<div class="controls">
				<form:input path="abbreviation" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">被考评对象类别：</label>
			<div class="controls">
				<form:select path="objType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('obj_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考评类别：</label>
			<div class="controls">
				<form:select path="kpType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('kp_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模板类型：</label>
			<div class="controls">
				<form:select path="modelType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('model_types')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考评周期：</label>
			<div class="controls">
				<form:select path="cycle" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('cycle')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unitId" name="unitId" value="${examStandardBaseInfo.unitId}" labelName="unitName" labelValue="${examStandardBaseInfo.unitName}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" dataMsgRequired="必填信息" notAllowSelectParent="false"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="exam:examStandardBaseInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
	<script>
		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</body>
</html>