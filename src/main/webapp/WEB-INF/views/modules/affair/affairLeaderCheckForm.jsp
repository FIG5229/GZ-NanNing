<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>领导干部年度考核表管理</title>
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
	<form:form id="inputForm" modelAttribute="affairLeaderCheck" action="${ctx}/affair/affairLeaderCheck/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">领领导干部名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairLeaderCheck.unitId}" labelName="unit" labelValue="${affairLeaderCheck.unit}"
					title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">测评时间：</label>
			<div class="controls">
				<input name="receiveDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="<fmt:formatDate value="${affairLeaderCheck.date}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">诉职年度：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="${affairLeaderCheck.date}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">测评时间：</label>
			<div class="controls">
				<input name="gradeDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="<fmt:formatDate value="${affairLeaderCheck.gradeDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参加测评人数：</label>
			<div class="controls">
				<form:input path="num" htmlEscape="false" class="input-xlarge  digits required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">测评等次：</label>
			<div class="controls">
				<form:select path="grade" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('assessment_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关材料：</label>
			<div class="controls">
				<form:hidden id="materialPath" path="materialPath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
					<sys:webuploader input="materialPath" type="files" uploadPath="affair/affairLeaderCheck" selectMultiple="true"/>
			</div>
		</div>
		<%--审核状态--%>
		<div class="control-group">
               <form:hidden path="status" value="0"/>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairLeaderCheck:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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