<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党内培训管理</title>
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
<%--	<ul class="nav nav-tabs">--%>
<%--		<li><a href="${ctx}/affair/affairPartyTrain/">党内培训列表</a></li>--%>
<%--		<li class="active"><a href="${ctx}/affair/affairPartyTrain/form?id=${affairPartyTrain.id}">党内培训<shiro:hasPermission name="affair:affairPartyTrain:edit">${not empty affairPartyTrain.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairPartyTrain:edit">查看</shiro:lacksPermission></a></li>--%>
<%--	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairPartyTrain" action="${ctx}/affair/affairPartyTrain/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairPartyTrain.startTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairPartyTrain.endTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主办单位：</label>
			<div class="controls">
				<%--<sys:treeselect id="unit" name="unitId" value="${affairPartyTrain.unitId}" labelName="unit" labelValue="${affairPartyTrain.unit}"
								title="单位" url="/affair/affairGeneralSituation/treeData" cssClass="required" isAll="true" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>--%>
					<form:input path="unit" htmlEscape="false" class="input-xlarge "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培训地点：</label>
			<div class="controls">
				<form:input path="trainPlace" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培训形式：</label>
			<div class="controls">
				<form:input path="trainForm" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培训名称：</label>
			<div class="controls">
				<form:input path="trainName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培训结果：</label>
			<div class="controls">
				<form:textarea path="trainResult" htmlEscape="false" rows="4" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group" style="display:none">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input path="idNum" htmlEscape="false" rows="4" class="input-xlarge " value="${affairPartyTrain.idNum}"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairPartyTrain:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>

<script type="text/javascript">
	if("success"=="${saveResult}"){
		parent.$.jBox.close();
	}
</script>
</body>
</html>