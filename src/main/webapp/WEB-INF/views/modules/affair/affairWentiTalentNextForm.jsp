<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文体人才库管理</title>
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
<br>
<form:form id="inputForm" modelAttribute="affairWentiTalentNext" action="${ctx}/affair/affairWentiTalentNext/save" method="post" class="form-horizontal">
	<form:hidden path="id"/>
	<sys:message content="${message}"/>
	<div class="control-group">
		<label class="control-label">姓名：</label>
		<div class="controls">
			<form:input id="name" path="name" htmlEscape="false" class="input-xlarge required" />
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">性别：</label>
		<div class="controls">
			<form:select path="sex" class="input-xlarge required">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">身份证号：</label>
		<div class="controls">
			<form:input id="idNumber" path="idNumber" htmlEscape="false" class="input-xlarge required" />
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">警号：</label>
		<div class="controls">
			<form:input id="policeNo" path="policeNo" htmlEscape="false" class="input-xlarge required"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">单位：</label>
		<div class="controls">
			<sys:treeselect id="unit" name="unitId" value="${affairWentiTalentNext.unitId}" labelName="unit" labelValue="${affairWentiTalentNext.unit}"
							title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true" dataMsgRequired="必填信息"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">出生年月：</label>
		<div class="controls">
			<input id="birthday"  name="birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
				   value="<fmt:formatDate value="${affairWentiTalentNext.birthday}" pattern="yyyy-MM-dd"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">毕业院校：</label>
		<div class="controls">
			<form:input id="school" path="school" htmlEscape="false" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">何种艺术特长：</label>
		<div class="controls">
			<form:input path="skill" htmlEscape="false" class="input-xlarge required"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">艺术等级：</label>
		<div class="controls">
			<form:input path="yisuLevel" htmlEscape="false" class="input-xlarge"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">取得成绩：</label>
		<div class="controls">
			<form:textarea path="achievement" htmlEscape="false" rows="4" class="input-xxlarge "/>
		</div>
	</div>
	<div class="form-actions">
		<shiro:hasPermission name="affair:affairWentiTalentNext:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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