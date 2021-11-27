<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单位奖励信息管理</title>
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
		if("sucess"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li><a href="${ctx}/org/orgReward/">单位奖励信息列表</a></li>
		<li class="active"><a href="${ctx}/org/orgReward/form?id=${orgReward.id}">单位奖励信息<shiro:hasPermission name="org:orgReward:edit">${not empty orgReward.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="org:orgReward:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="orgReward" action="${ctx}/org/orgReward/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<%--机构id--%>
		<input id="orgId" name="orgId" type="hidden" value="${orgReward.orgId}"/>
		<div class="control-group">
			<label class="control-label">受奖单位名称：</label>
			<div class="controls">
				<form:input path="winUnitName" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">受奖单位代码：</label>
			<div class="controls">
				<form:input path="winUnitCode" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建制标志：</label>
			<div class="controls">
				<form:input path="sign" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖励名称代码：</label>
			<div class="controls">
				<form:input path="rewardNameCode" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">荣誉称号级别：</label>
			<div class="controls">
				<form:input path="level" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位警种：</label>
			<div class="controls">
				<form:input path="policeType" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖励名称：</label>
			<div class="controls">
				<form:input path="rewardName" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准单位：</label>
			<div class="controls">
				<form:input path="approvalUnit" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准单位级别：</label>
			<div class="controls">
				<form:select path="approvalUnitLevel" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('org_approval_unit_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准日期：</label>
			<div class="controls">
				<input name="approvalDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${orgReward.approvalDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准文号：</label>
			<div class="controls">
				<form:input path="fileNo" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准文件名称：</label>
			<div class="controls">
				<form:input path="fileName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准机关类别：</label>
			<div class="controls">
				<form:select path="approvalOrgType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('org_approval_org_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">撤销奖励日期：</label>
			<div class="controls">
				<input name="cancelDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${orgReward.cancelDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">撤销奖励批准单位：</label>
			<div class="controls">
				<form:textarea path="cancelApprovalUnit" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">事迹材料：</label>
			<div class="controls">
				<form:hidden id="material" path="material" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="material" type="files" uploadPath="org/orgReward" selectMultiple="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="org:orgReward:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>