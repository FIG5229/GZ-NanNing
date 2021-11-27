<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>养老保险管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#inputForm").validate({
				submitHandler: function(form){
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
		if("sucess"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</head>
<body>
<br/>
	<form:form id="inputForm" modelAttribute="personnelEndowmentInsurance" action="${ctx}/personnel/personnelEndowmentInsurance/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		

		<div class="control-group">
			<label class="control-label">保险年份：</label>
			<div class="controls">
				<input name="timeYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required" value="${personnelEndowmentInsurance.timeYear}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${personnelEndowmentInsurance.unitId}" labelName="unit" labelValue="${personnelEndowmentInsurance.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge   required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input path="idNumber" htmlEscape="false" class="input-xlarge   required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">养老保险缴费基数：</label>
			<div class="controls">
				<form:input path="cardinalNumber" htmlEscape="false" class="input-xlarge   required"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">养老金个人缴费比例：</label>
			<div class="controls">
				<form:input path="individualProportion" htmlEscape="false" class="input-xlarge   required"/>%
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">养老金单位缴费比例：</label>
			<div class="controls">
				<form:input path="unitProportion" htmlEscape="false" class="input-xlarge   required"/>%
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职业年金个人缴费比例：</label>
			<div class="controls">
				<form:input path="oaIndividualProportion" htmlEscape="false" class="input-xlarge   required"/>%
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职业年金单位缴费比例：</label>
			<div class="controls">
				<form:input path="oaUnitProportion" htmlEscape="false" class="input-xlarge   required"/>%
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="personnel:personnelEndowmentInsurance:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/></shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
		</div>
	</form:form>
</body>
</html>