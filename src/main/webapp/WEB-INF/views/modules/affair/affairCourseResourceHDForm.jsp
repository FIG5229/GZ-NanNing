<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>互动组件</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
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
	<ul class="nav nav-tabs">
		<shiro:hasPermission name="affair:affairCourseResource:view">
			<li>
				<a href="${ctx}/affair/affairCourseTeacher/list?id=${affairCourseResource.id}">辅导教官</a>
			</li>
			<li>
				<a href="${ctx}/affair/affairCourseResource/CKForm?id=${affairCourseResource.id}">参考文档</a>
			</li>
			<li class="active">
				<a href="${ctx}/affair/affairCourseResource/HDForm?id=${affairCourseResource.id}">互动组件</a>
			</li>
		</shiro:hasPermission>
	</ul>
	<form:form id="inputForm" modelAttribute="affairCourseResource" action="${ctx}/affair/affairCourseResource/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">是否启用答疑室：</label>
			<div class="controls">
				<form:select path="answer" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('answer_open')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairCourseResource:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>

</body>
</html>