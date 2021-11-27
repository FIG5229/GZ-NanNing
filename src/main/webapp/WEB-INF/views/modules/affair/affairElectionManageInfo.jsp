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
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<shiro:hasPermission name="affair:affairElection:manage"><li><a href="${ctx}/affair/affairElection/manage">党组织换届选举详情</a></li></shiro:hasPermission>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="affairElection" action="${ctx}/affair/affairElection/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">届次：</label>
			<div class="controls">
				<form:input path="jc" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">选举方式：</label>
			<div class="controls">
				<form:select path="method" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_xuanju_method')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">该届届满时间：</label>
			<div class="controls">
				<input name="jmDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairElection.jmDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">该届换届时间：</label>
			<div class="controls">
				<input name="hjDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairElection.hjDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">应到会人数：</label>
			<div class="controls">
				<form:input path="ydhNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实到会人数：</label>
			<div class="controls">
				<form:input path="sdhNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准委员会名额：</label>
			<div class="controls">
				<form:input path="quota" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">党组织名称：</label>
			<div class="controls">
				<sys:treeselect id="partyOrganizationName" name="partyOrganizationId" value="${affairElection.partyOrganizationId}" labelName="partyOrganizationName" labelValue="${affairElection.partyOrganizationName}"
					title="党组织名称" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="false"/>
			</div>
		</div>
		<input id="status1" name="status1" type="hidden"/>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>