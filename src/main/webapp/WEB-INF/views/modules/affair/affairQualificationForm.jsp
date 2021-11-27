<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>警务技术任职资格管理管理</title>
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
	<form:form id="inputForm" modelAttribute="affairQualification" action="${ctx}/affair/affairQualification/save" method="post" class="form-horizontal">
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
			<label class="control-label">任职资格名称：</label>
			<div class="controls">
				<form:input path="jobName" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">取得资格日期：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairQualification.date}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">任职资格级别：</label>
			<div class="controls">
				<form:select path="level" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('police_technical_qualification_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">取得资格途径：</label>
			<div class="controls">
				<form:input path="channel" htmlEscape="false" class="input-xlarge "/>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准资格评定文件名称（文号）：</label>
			<div class="controls">
				<form:input path="fileNo" htmlEscape="false" class="input-xlarge "/>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证书编号：</label>
			<div class="controls">
				<form:input path="certificateNo" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专业方向：</label>
			<div class="controls">
				<form:input path="direction" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">资格考试年度：</label>
			<div class="controls">
				<form:input path="examYear" htmlEscape="false" class="input-xlarge "/>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">资格评审年度：</label>
			<div class="controls">
				<form:input path="reviewYear" htmlEscape="false" class="input-xlarge "/>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考试名称：</label>
			<div class="controls">
				<form:input path="examName" htmlEscape="false" class="input-xlarge "/>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">评委会名称：</label>
			<div class="controls">
				<form:input path="juryName" htmlEscape="false" class="input-xlarge "/>

			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairQualification:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
		</div>
	</form:form>
	<script>
		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</body>
</html>