<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>教学设计</title>
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
			<li class="active">
				<a href="${ctx}/affair/affairCourseResource/JXForm?id=${affairCourseResource.id}">通过条件</a>
			</li>
			<li>
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
			<label class="control-label">学习进度：</label>
			<div class="controls">
				<form:input path="plan" htmlEscape="false" class="input-xlarge "/>%
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学习时长：</label>
			<div class="controls">
				<form:input path="learnTime" htmlEscape="false" class="input-xlarge "/>分钟
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课后试卷考试成绩：</label>
			<div class="controls">
				<form:input path="score" htmlEscape="false" class="input-xlarge "/>分
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提交课程评估：</label>
			<div class="controls">
				<input style='margin-left:12px' type='checkbox' name="assess" value="1"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课件本身自带测试题成绩：</label>
			<div class="controls">
				<form:input path="scoreSelf" htmlEscape="false" class="input-xlarge "/>分
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