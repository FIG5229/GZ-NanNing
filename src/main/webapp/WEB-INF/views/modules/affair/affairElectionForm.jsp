<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党组织换届选举管理</title>
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
		function setStatus(status) {
			$("#status1").val(status);
		};
		if("sucess"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="affairElection" action="${ctx}/affair/affairElection/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">党组织名称：</label>
			<div class="controls">
				<sys:treeselect id="partyOrganizationName" name="partyOrganizationId" value="${affairElection.partyOrganizationId}" labelName="partyOrganizationName" labelValue="${affairElection.partyOrganizationName}"
					title="党组织名称" url="/affair/affairGeneralSituation/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">届次：</label>
			<div class="controls">
				<form:input path="jc" htmlEscape="false" class="input-xlarge  digits required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">选举方式：</label>
			<div class="controls">
				<form:select path="method" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_xuanju_method')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">该届届满时间：</label>
			<div class="controls">
				<input name="jmDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairElection.jmDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">该届换届时间：</label>
			<div class="controls">
				<input name="hjDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairElection.hjDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">应到会人数：</label>
			<div class="controls">
				<form:input path="ydhNum" htmlEscape="false" class="input-xlarge  digits required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实到会人数：</label>
			<div class="controls">
				<form:input path="sdhNum" htmlEscape="false" class="input-xlarge  digits required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准委员会名额：</label>
			<div class="controls">
				<form:input path="quota" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">换届资料：</label>
			<div class="controls">
				<form:textarea path="electionInformation" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filePath" type="files" uploadPath="affair/affairElection" selectMultiple="true"/>

			</div>
		</div>
		<input id="status1" name="status1" type="hidden"/>
		<div class="form-actions">
			<c:choose>
				<c:when test="${empty affairElection.id}">
					<shiro:hasPermission name="affair:affairElection:edit"><input class="btn btn-primary" type="submit" value="保 存" onclick="setStatus(1)"/>&nbsp;</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairElection:edit"><input class="btn btn-primary" type="submit" value="提 交" onclick="setStatus(2)"/>&nbsp;</shiro:hasPermission>
				</c:when>
				<c:otherwise>
					<%--其他人的数据无权操作  自己的数据已提交后不能再重复保存和提交--%>
					<c:if test="${affairElection.createBy.id == fns:getUser().id && affairElection.status1 != '2'}">
						<shiro:hasPermission name="affair:affairElection:edit"><input class="btn btn-primary" type="submit" value="保 存" onclick="setStatus(1)"/>&nbsp;</shiro:hasPermission>
						<shiro:hasPermission name="affair:affairElection:edit"><input class="btn btn-primary" type="submit" value="提 交" onclick="setStatus(2)"/>&nbsp;</shiro:hasPermission>
					</c:if>
				</c:otherwise>
			</c:choose>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>