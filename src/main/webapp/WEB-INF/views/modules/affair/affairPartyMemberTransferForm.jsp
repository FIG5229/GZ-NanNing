<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党员名册-组织转接</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
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
	<form:form id="inputForm" modelAttribute="affairRelationshipTransfer" action="${ctx}/affair/affairRelationshipTransfer/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input path="idNumber" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原组织：</label>
			<div class="controls">
				<sys:treeselect id="oldOrganization" name="oldOrganizationId" value="${affairRelationshipTransfer.oldOrganizationId}" labelName="oldOrganization" labelValue="${affairRelationshipTransfer.oldOrganization}"
					title="原组织" url="/affair/affairGeneralSituation/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申请转入组织：</label>
			<div class="controls">
				<sys:treeselect id="nowOrganization" name="nowOrganizationId" value="${affairRelationshipTransfer.nowOrganization}" labelName="nowOrganization" labelValue="${affairRelationshipTransfer.nowOrganization}"
					title="申请转入组织" url="/affair/affairGeneralSituation/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申请时间：</label>
			<div class="controls">
				<input name="applyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairRelationshipTransfer.applyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转移类型：</label>
			<div class="controls">
				<form:select path="transferType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_transfer_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
				<shiro:hasPermission name="affair:affairRelationshipTransfer:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/><span class="help-inline"><font color="red">请勿重复保存，耐心等待审核结果</font> </span></shiro:hasPermission>
		</div>
	</form:form>
</body>
</html>