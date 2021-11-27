<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>教官授课统计管理</title>
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
		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairInstructorLessonsStatistics/">教官授课统计列表</a></li>
		<li class="active"><a href="${ctx}/affair/affairInstructorLessonsStatistics/form?id=${affairInstructorLessonsStatistics.id}">教官授课统计<shiro:hasPermission name="affair:affairInstructorLessonsStatistics:edit">${not empty affairInstructorLessonsStatistics.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairInstructorLessonsStatistics:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairInstructorLessonsStatistics" action="${ctx}/affair/affairInstructorLessonsStatistics/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input path="idNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教官姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教官类型：</label>
			<div class="controls">
				<form:input path="instructorType" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属机构：</label>
			<div class="controls">
				<sys:treeselect id="unitId" name="unitId" value="${affairInstructorLessonsStatistics.unitId}" labelName="unitName" labelValue="${affairInstructorLessonsStatistics.unitName}"
					title="所属机构" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">授课总数：</label>
			<div class="controls">
				<form:input path="giveLessonsTotal" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课时总数：</label>
			<div class="controls">
				<form:input path="classHourTotal" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授课总时长(小时)：</label>
			<div class="controls">
				<form:input path="giveLessonsTotalHour" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairInstructorLessonsStatistics:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>