<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民警学习统计报表管理</title>
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
		<li><a href="${ctx}/affair/affairPoliceStudyStatistics/">民警学习统计报表列表</a></li>
		<li class="active"><a href="${ctx}/affair/affairPoliceStudyStatistics/form?id=${affairPoliceStudyStatistics.id}">民警学习统计报表<shiro:hasPermission name="affair:affairPoliceStudyStatistics:edit">${not empty affairPoliceStudyStatistics.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairPoliceStudyStatistics:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%><br/>
	<form:form id="inputForm" modelAttribute="affairPoliceStudyStatistics" action="${ctx}/affair/affairPoliceStudyStatistics/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">用户名：</label>
			<div class="controls">
				<form:input path="nickName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">警号：</label>
			<div class="controls">
				<form:input path="alarm" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属机构：</label>
			<div class="controls">
				<sys:treeselect id="unitId" name="unitId" value="${affairPoliceStudyStatistics.unitId}" labelName="unitName" labelValue="${affairPoliceStudyStatistics.unitName}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课程总数：</label>
			<div class="controls">
				<form:input path="courseNum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学习总次数：</label>
			<div class="controls">
				<form:input path="studyTotalNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学习总时长(小时)：</label>
			<div class="controls">
				<form:input path="studyTotalDate" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通过课程数：</label>
			<div class="controls">
				<form:input path="passCourseNum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课程通过率：</label>
			<div class="controls">
				<form:input path="coursePassRate" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairPoliceStudyStatistics:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>