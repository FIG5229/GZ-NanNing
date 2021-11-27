<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民警个人训历档案报表管理</title>
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
		<li><a href="${ctx}/affair/affairPolicePersonalTrainingFile/">民警个人训历档案报表列表</a></li>
		<li class="active"><a href="${ctx}/affair/affairPolicePersonalTrainingFile/form?id=${affairPolicePersonalTrainingFile.id}">民警个人训历档案报表<shiro:hasPermission name="affair:affairPolicePersonalTrainingFile:edit">${not empty affairPolicePersonalTrainingFile.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairPolicePersonalTrainingFile:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="affairPolicePersonalTrainingFile" action="${ctx}/affair/affairPolicePersonalTrainingFile/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:input path="sex" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<form:input path="unit" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位id：</label>
			<div class="controls">
				<sys:treeselect id="unitId" name="unitId" value="${affairPolicePersonalTrainingFile.unitId}" labelName="" labelValue="${affairPolicePersonalTrainingFile.}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职务：</label>
			<div class="controls">
				<form:input path="job" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出生日期：</label>
			<div class="controls">
				<input name="birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairPolicePersonalTrainingFile.birthday}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">入警时间：</label>
			<div class="controls">
				<input name="hiredate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairPolicePersonalTrainingFile.hiredate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学历：</label>
			<div class="controls">
				<form:input path="education" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">政治面貌：</label>
			<div class="controls">
				<form:input path="politicsFace" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专业技术职称：</label>
			<div class="controls">
				<form:input path="technicalTitle" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">在线课程-参加次数：</label>
			<div class="controls">
				<form:input path="onlineCourseNum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">在线课程-通过率：</label>
			<div class="controls">
				<form:input path="onlineCoursePassing" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">在线课程-平均分：</label>
			<div class="controls">
				<form:input path="onlineCourseAverage" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">在线课程-总课时(分钟)：</label>
			<div class="controls">
				<form:input path="onlineCourseTotalTime" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">在线课程-学分：</label>
			<div class="controls">
				<form:input path="onlineCourseCredit" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培训班课程-参加次数：</label>
			<div class="controls">
				<form:input path="trainCourseNum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培训班课程-通过率：</label>
			<div class="controls">
				<form:input path="trainCoursePassing" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培训班课程-平均分：</label>
			<div class="controls">
				<form:input path="trainCourseAverage" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培训班课程-总课时(分钟)：</label>
			<div class="controls">
				<form:input path="trainCourseTotalTime" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培训班课程-学分：</label>
			<div class="controls">
				<form:input path="trainCourseCredit" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">岗位练兵-参加次数：</label>
			<div class="controls">
				<form:input path="jobTrainingNum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">岗位练兵-总时长(小时)：</label>
			<div class="controls">
				<form:input path="jobTrainingTotalTime" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">委外培训-参加次数：</label>
			<div class="controls">
				<form:input path="outTrainingNum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">委外培训-完成率：</label>
			<div class="controls">
				<form:input path="outTrainingFinish" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">委外培训-主要类别：</label>
			<div class="controls">
				<form:input path="outTrainingMainType" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">委外培训-最高级别：</label>
			<div class="controls">
				<form:input path="outTrainingHighestLevel" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">委外培训取得证书：</label>
			<div class="controls">
				<form:input path="outTrainingGetCcie" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交流学习-参加次数：</label>
			<div class="controls">
				<form:input path="exchangeStudyNum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交流学习-完成率：</label>
			<div class="controls">
				<form:input path="exchangeStudyFinish" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交流学习-主要岗位：</label>
			<div class="controls">
				<form:input path="exchangeStudyMainJob" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交流学习-最高规格：</label>
			<div class="controls">
				<form:input path="exchangeStudyHighestSpec" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交流学习-总时长：</label>
			<div class="controls">
				<form:input path="exchangeStudyTotalTime" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考试-参加次数：</label>
			<div class="controls">
				<form:input path="examNum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考试-合格率：</label>
			<div class="controls">
				<form:input path="examPass" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考试-平均分：</label>
			<div class="controls">
				<form:input path="examAverage" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考试-作弊次数：</label>
			<div class="controls">
				<form:input path="examCheat" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教育训练积分：</label>
			<div class="controls">
				<form:input path="trainingIntegral" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教育训练主管部门鉴定意见：</label>
			<div class="controls">
				<form:input path="expertOpinion" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建者机构id：</label>
			<div class="controls">
				<form:input path="createOrgId" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建者身份证号：</label>
			<div class="controls">
				<form:input path="createIdNo" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更新者机构id：</label>
			<div class="controls">
				<form:input path="updateOrgId" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更新者身份证号：</label>
			<div class="controls">
				<form:input path="updateIdNo" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairPolicePersonalTrainingFile:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>