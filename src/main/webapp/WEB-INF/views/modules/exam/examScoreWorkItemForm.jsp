<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>得分制工作项管理管理</title>
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
	<form:form id="inputForm" modelAttribute="examScoreWorkItem" action="${ctx}/exam/examScoreWorkItem/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<br/>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:select path="examType" class="input-xlarge ">
					<form:option value="" label=""/>
<%--					<form:options items="${fns:getDictList('')}" itemLabel=<form:option value="1" label="局考处"/>
					<form:option value="3" label="处考队所"/>"label" itemValue="value" htmlEscape="false"/>--%>
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('exam_weights_kpType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作项：</label>
			<div class="controls">
				<form:select path="workName" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('exam_weigths')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="exam:examScoreWorkItem:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script>
		if("sucess"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</body>
</html>