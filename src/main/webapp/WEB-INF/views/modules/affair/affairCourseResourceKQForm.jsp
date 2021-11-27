<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课前</title>
	<meta name="decorator" content="default"/>
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
				<a href="${ctx}/affair/affairCourseResource/JXForm?id=${affairCourseResource.id}">通过条件</a>
			</li>
			<li class="active">
				<a href="${ctx}/affair/affairCourseResource/KQForm?id=${affairCourseResource.id}">课前测试</a>
			</li>
			<li>
				<a href="${ctx}/affair/affairCourseResource/KHForm?id=${affairCourseResource.id}">课后考试</a>
			</li>
		</shiro:hasPermission>
	</ul>
	<form:form id="inputForm" modelAttribute="affairCourseResource" action="${ctx}/affair/affairCourseResource/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">是否启用：</label>
			<div class="controls">
				<form:select path="kqTest" class="input-xlarge ">
					<form:option value="${affairCourseResource.kqTest}" label=""/>
					<form:options items="${fns:getDictList('open_up')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参加考试的前提条件：</label>
			<div class="controls">
				<form:input path="precondition" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考试时长（分钟）：</label>
			<div class="controls">
				<form:input path="testTime" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">卷面显示形式：</label>
			<div class="controls">
				<form:select path="shape" class="input-xlarge ">
					<form:option value="${affairCourseResource.shape}" label=""/>
					<form:options items="${fns:getDictList('shou_shape')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">允许参加考试次数：</label>
			<div class="controls">
				<form:input path="degree" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通过分数：</label>
			<div class="controls">
				<form:input path="grade" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设置试卷：</label>
			<div class="controls">
				<form:input path="paper" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>


		<div class="form-actions">
			<shiro:hasPermission name="affair:affairCommentators:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
		</div>
	</form:form>
</body>
</html>