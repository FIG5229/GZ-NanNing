<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程资源管理</title>
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
	 <br/>
	 <ul class="nav nav-tabs">
		 <shiro:hasPermission name="affair:affairCourseResource:view">
			 <li>
				 <a href="${ctx}/affair/affairCourseResource/formDetail?id=${affairCourseResource.id}">基本信息</a>
			 </li>
			 <li class="active">
				 <a href="${ctx}/affair/affairCourseResource/SwfForm?id=${affairCourseResource.id}">课件上传</a>
			 </li>
			 <li>
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
			<label class="control-label">适用终端：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge ">
					<form:option value="${affairCourseResource.terminal}" label=""/>
					<form:options items="${fns:getDictList('apply_terminal')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课件表现形式：</label>
			<div class="controls">
				<form:select path="formset" class="input-xlarge ">
					<form:option value="${affairCourseResource.formset}" label=""/>
					<form:options items="${fns:getDictList('pattern_of_manifestation')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课件标准：</label>
			<div class="controls">
				<form:select path="norm" class="input-xlarge ">
					<form:option value="${affairCourseResource.norm}" label=""/>
					<form:options items="${fns:getDictList('courseware_standards')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课件模板：</label>
			<div class="controls">
				<form:input path="stencil" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">PC端入口地址：</label>
			<div class="controls">
				<form:input path="entrance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">当前课件：</label>
			<div class="controls">
				<form:hidden id="swf" path="swf" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="swf" type="files" uploadPath="affair/affairCourseResource" selectMultiple="true"/>
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