<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单位年度考评结果管理</title>
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
		<li><a href="${ctx}/exam/examUnitAllPublicYear/">单位年度考评结果列表</a></li>
		<li class="active"><a href="${ctx}/exam/examUnitAllPublicYear/form?id=${examUnitAllPublicYear.id}">单位年度考评结果<shiro:hasPermission name="exam:examUnitAllPublicYear:edit">${not empty examUnitAllPublicYear.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="exam:examUnitAllPublicYear:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="examUnitAllPublicYear" action="${ctx}/exam/examUnitAllPublicYear/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">单位名称（又叫考核对象）：</label>
			<div class="controls">
				<form:input path="unitName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位id：</label>
			<div class="controls">
				<form:input path="unitId" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">各业务工作：</label>
			<div class="controls">
				<form:input path="workName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">各业务工作所占权重分：</label>
			<div class="controls">
				<form:input path="weight" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务100分制得分：</label>
			<div class="controls">
				<form:input path="hundred" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务折算权重后得分：</label>
			<div class="controls">
				<form:input path="zsqzhScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公共加、扣分合计：</label>
			<div class="controls">
				<form:input path="publicScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">各业务加扣分情况及公共加扣分归类分析：</label>
			<div class="controls">
				<form:input path="analysis" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考评流程ID：</label>
			<div class="controls">
				<form:input path="workflowId" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加减分标识（1：加分  -1：减分）：</label>
			<div class="controls">
				<form:input path="valueType" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="exam:examUnitAllPublicYear:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>