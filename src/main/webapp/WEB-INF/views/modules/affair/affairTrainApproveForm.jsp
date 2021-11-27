<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>培训计划审批管理</title>
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
	<form:form id="inputForm" modelAttribute="affairTrainApprove" action="${ctx}/affair/affairTrainApprove/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">培训年度：</label>
			<div class="controls">
				<input name="trainYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairTrainApprove.trainYear}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">填报机构：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairTrainApprove.unitId}" labelName="unit" labelValue="${affairTrainApprove.unit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">填报人：</label>
			<div class="controls">
				<form:input path="informant" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核人：</label>
			<div class="controls">
				<form:input path="reviewer" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提交审批日期：</label>
			<div class="controls">
				<input name="approveDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairTrainApprove.approveDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审批层级：</label>
			<div class="controls">
				<form:select path="approveLevel" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('approve_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
<%--		<div class="control-group">--%>
<%--			<label class="control-label">审批结果：</label>--%>
<%--			<div class="controls">--%>
<%--				<form:input path="approveResult" htmlEscape="false" class="input-xlarge "/>--%>
<%--			</div>--%>
<%--		</div>--%>
<%--		<div class="control-group">--%>
<%--			<label class="control-label">审批状态：</label>--%>
<%--			<div class="controls">--%>
<%--				<form:select path="approveStatus" class="input-xlarge ">--%>
<%--					<form:option value="" label=""/>--%>
<%--					<form:options items="${fns:getDictList('approve_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
<%--				</form:select>--%>
<%--			</div>--%>
<%--		</div>--%>
		<div class="control-group">
			<label class="control-label">已填报的计划培训班总数：</label>
			<div class="controls">
				<form:input path="filledClassCount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已审批通过的计划培训班数量：</label>
			<div class="controls">
				<form:input path="approvedClassCount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已审批通过的计划培训总人数：</label>
			<div class="controls">
				<form:input path="approvedCount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">未完成审批的计划培训班数量：</label>
			<div class="controls">
				<form:input path="incompleteApprovalCount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairTrainApprove:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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