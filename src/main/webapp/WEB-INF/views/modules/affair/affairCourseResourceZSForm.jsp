<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程资源管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>

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
	 <br/>
	 <ul class="nav nav-tabs">
		 <shiro:hasPermission name="affair:affairCourseResource:view">
			 <li>
				 <a href="${ctx}/affair/affairCourseResource/formDetail?id=${affairCourseResource.id}">基本信息</a>
			 </li>
			 <li>
				 <a href="${ctx}/affair/affairCourseResource/SwfForm?id=${affairCourseResource.id}">课件上传</a>
			 </li>
			 <li class="active">
				 <a href="${ctx}/affair/affairCourseResource/ZSForm?id=${affairCourseResource.id}">知识点设置</a>
			 </li>
			 <li>
				 <a  href="${ctx}/affair/affairCourseResource/GXForm?id=${affairCourseResource.id}">共享设置</a>
			 </li>

		 </shiro:hasPermission>
	 </ul>
	<form:form id="inputForm" modelAttribute="affairCourseResource" action="${ctx}/affair/affairCourseResource/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>

		<div class="control-group">
			<label class="control-label">知识点设置方式：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge ">
					<form:option value="${affairCourseResource.terminal}" label=""/>
					<form:options items="${fns:getDictList('zs_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="affair:affairCourseResource:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="checkForm()"/>&nbsp;
			</shiro:hasPermission>
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>