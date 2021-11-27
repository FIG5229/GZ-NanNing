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

		if ("success" == "${saveResult}"){
			parent.$.jBox.close();
		}

	</script>
</head>
<body>
	 <br/>
	<form:form id="inputForm" modelAttribute="affairCourseResource" action="${ctx}/affair/affairCourseResource/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">课程名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课程编码：</label>
			<div class="controls">
				<form:input path="code" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课程类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge ">
					<form:option value="${affairCourseResource.type}" label=""/>
					<form:options items="${fns:getDictList('swf_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课件标签：</label>
			<div class="controls">
				<form:input path="label" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学时：</label>
			<div class="controls">
				<form:input path="time" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否公开课：</label>
			<div class="controls">
				<form:select path="open" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('open_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">辅导，授权讲师：</label>
			<div class="controls">
				<form:input path="teacher" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发布状态：</label>
			<div class="controls">
				<form:select path="state" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('release_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课程分类：</label>
			<div class="controls">
				<form:input path="classify" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">语言：</label>
			<div class="controls">
				<form:select path="language" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('language_classify')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">作者：</label>
			<div class="controls">
				<form:input path="author" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">制作单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairCourseResource.unitId}" labelName="unit" labelValue="${affairCourseResource.unit}"
								title="制作单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课程简介：</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课程封面：</label>
			<div class="controls">
				<form:hidden id="adjunct" path="adjunct" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="adjunct" type="files" uploadPath="affair/affairCourseResource" selectMultiple="true"/>
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