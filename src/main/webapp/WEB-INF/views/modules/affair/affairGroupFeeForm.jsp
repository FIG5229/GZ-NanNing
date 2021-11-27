<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团费支出管理</title>
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

<%--		<li><a href="${ctx}/affair/affairGroupFee/">团费支出列表</a></li>--%>
		<%--<li class="active"><a href="${ctx}/affair/affairGroupFee/form?id=${affairGroupFee.id}">团费支出<shiro:hasPermission name="affair:affairGroupFee:edit">${not empty affairGroupFee.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairGroupFee:edit">查看</shiro:lacksPermission></a></li>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairGroupFee" action="${ctx}/affair/affairGroupFee/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">支出类别：</label>
			<div class="controls">
				<form:select path="expenditureCategory" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_tftype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支出金额：</label>
			<div class="controls">
				<form:input path="payoutAmount" htmlEscape="false" class="input-xlarge  digits required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支出时间：</label>
			<div class="controls">
				<input name="expenditureDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="<fmt:formatDate value="${affairGroupFee.expenditureDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支出内容：</label>
			<div class="controls">
				<form:input path="expenditureContent" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经办人：</label>
			<div class="controls">
				<form:input path="manager" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经办人身份证：</label>
			<div class="controls">
				<form:input path="managerNum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">团组织：</label>
			<div class="controls">
				<sys:treeselect id="groupOrg" name="groupOrgId" value="${affairGroupFee.groupOrgId}" labelName="groupOrg" labelValue="${affairGroupFee.groupOrg}"
					title="团组织" url="/affair/affairTwBase/treeData" cssClass="required" allowClear="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="affair:affairGroupFee:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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