<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>教官信息管理</title>
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

		if ("success" == ${saveResult}){
			parent.$.jBox.close();
		}
	</script>
</head>
<body> <br/>
<form:form id="inputForm" modelAttribute="affairInteriorInstructorLibrary" action="${ctx}/affair/affairInteriorInstructorLibrary/save" method="post" class="form-horizontal">
	<form:hidden path="id"/>
	<sys:message content="${message}"/>
	<div class="control-group">
		<label class="control-label">姓名：</label>
		<div class="controls">
			<form:input path="name" htmlEscape="false" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">身份证号：</label>
		<div class="controls">
			<form:input path="idNumber" htmlEscape="false" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">性别：</label>
		<div class="controls">
			<form:select id="sex" path="sex" class="input-xlarge required">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">职务：</label>
		<div class="controls">
			<form:input path="job" htmlEscape="false" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">授课方向：</label>
		<div class="controls">
			<form:textarea path="direction" htmlEscape="false" rows="4" class="input-xxlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">教官简介：</label>
		<div class="controls">
			<form:textarea path="instructorProfile" htmlEscape="false" rows="4" class="input-xxlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">民族：</label>
		<div class="controls">
			<form:select id="nation" path="nation" class="input-xlarge required">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('nation')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">健康状况：</label>
		<div class="controls">
			<form:input path="health" htmlEscape="false" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">人员状态：</label>
		<div class="controls">
			<form:input path="perpleState" htmlEscape="false" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">电话：</label>
		<div class="controls">
			<form:input path="telephone" htmlEscape="false" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">出生日期：</label>
		<div class="controls">
			<input name="birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
				   value="<fmt:formatDate value="${affairInteriorInstructorLibrary.birthday}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">血型：</label>
		<div class="controls">
			<form:input path="blood" htmlEscape="false" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">籍贯：</label>
		<div class="controls">
			<form:input path="nativePlace" htmlEscape="false" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">警号：</label>
		<div class="controls">
			<form:input path="alarm" htmlEscape="false" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">单位：</label>
		<div class="controls">
			<sys:treeselect id="unit" name="unitId" value="${affairInteriorInstructorLibrary.unitId}" labelName="unit" labelValue="${affairInteriorInstructorLibrary.unit}"
							title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">参加工作时间：</label>
		<div class="controls">
			<input name="jobTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
				   value="<fmt:formatDate value="${affairInteriorInstructorLibrary.jobTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">参加公安工作时间：</label>
		<div class="controls">
			<input name="policeTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
				   value="<fmt:formatDate value="${affairInteriorInstructorLibrary.policeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">政治面貌：</label>
		<div class="controls">
			<form:input path="politicsStatus" htmlEscape="false" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">参加组织日期：</label>
		<div class="controls">
			<input name="organizationTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
				   value="<fmt:formatDate value="${affairInteriorInstructorLibrary.organizationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">所属部门：</label>
		<div class="controls">
			<sys:treeselect id="department" name="departmentId" value="${affairInteriorInstructorLibrary.departmentId}" labelName="department" labelValue="${affairInteriorInstructorLibrary.department}"
							title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">警衔：</label>
		<div class="controls">
			<form:input path="policeRank" htmlEscape="false" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">警种：</label>
		<div class="controls">
			<form:input path="policeClassification" htmlEscape="false" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">管理类别：</label>
		<div class="controls">
			<form:input path="management" htmlEscape="false" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">职务级别：</label>
		<div class="controls">
			<form:input path="jobClassify" htmlEscape="false" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">人员类别：</label>
		<div class="controls">
			<form:input path="peopleClassify" htmlEscape="false" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">教官级别：</label>
		<div class="controls">
			<form:select id="instructorLevel" path="instructorLevel" class="input-xlarge required">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('instructor_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">教官类别：</label>
		<div class="controls">
			<form:select id="instructorCategory" path="instructorCategory" class="input-xlarge required">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('Instructor_category')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">聘用单位：</label>
		<div class="controls">
			<sys:treeselect id="workUnits" name="workUnitsId" value="${affairInteriorInstructorLibrary.workUnitsId}" labelName="workUnits" labelValue="${affairInteriorInstructorLibrary.workUnits}"
							title="聘用单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">聘用日期：</label>
		<div class="controls">
			<input name="hireDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
				   value="<fmt:formatDate value="${affairInteriorInstructorLibrary.hireDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">门户是否显示：</label>
		<div class="controls">
			<form:input path="isShow" htmlEscape="false" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">解聘日期：</label>
		<div class="controls">
			<input name="terminationDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
				   value="<fmt:formatDate value="${affairInteriorInstructorLibrary.terminationDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">评审日期：</label>
		<div class="controls">
			<input name="inspectionDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
				   value="<fmt:formatDate value="${affairInteriorInstructorLibrary.inspectionDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">评审人员：</label>
		<div class="controls">
			<form:input path="judge" htmlEscape="false" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">教官特长：</label>
		<div class="controls">
			<form:input path="speciality" htmlEscape="false" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">评审理由：</label>
		<div class="controls">
			<form:textarea path="reviewTheReason" htmlEscape="false" rows="4" class="input-xxlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">工作经历：</label>
		<div class="controls">
			<form:textarea path="workExperience" htmlEscape="false" rows="4" class="input-xxlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">任教经历：</label>
		<div class="controls">
			<form:textarea path="teachExperience" htmlEscape="false" rows="4" class="input-xxlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">进修经历：</label>
		<div class="controls">
			<form:textarea path="educationExperience" htmlEscape="false" rows="4" class="input-xxlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">参与培训教材编导情况：</label>
		<div class="controls">
			<form:textarea path="scenarist" htmlEscape="false" rows="4" class="input-xxlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">参与典型事件工作情况：</label>
		<div class="controls">
			<form:textarea path="typicalEvent" htmlEscape="false" rows="4" class="input-xxlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">文化程度：</label>
		<div class="controls">
			<form:textarea path="education" htmlEscape="false" rows="4" class="input-xxlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">其他：</label>
		<div class="controls">
			<form:textarea path="other" htmlEscape="false" rows="4" class="input-xxlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">附件：</label>
		<div class="controls">
			<form:hidden id="adjunct" path="adjunct" htmlEscape="false" maxlength="255" class="input-xlarge"/>
			<sys:webuploader input="adjunct" type="files" uploadPath="affair/affairLearnDaily" selectMultiple="true"/>
		</div>
	</div>
	<div class="form-actions">
		<shiro:hasPermission name="affair:affairInstructorLibrary:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
	</div>
</form:form>
<script>
	if("success"=="${saveResult}"){
		parent.$.jBox.close();
	}
</script>
</body>
</html>