<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>疗（休）养信息集管理</title>
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
	<form:form id="inputForm" modelAttribute="personnelRecuperate" action="${ctx}/personnel/personnelRecuperate/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<c:choose>
			<c:when test="${personnelRecuperate.idNumber == null}">
				<div class="control-group">
					<label class="control-label">身份证号：</label>
					<div class="controls">
						<form:input path="idNumber" htmlEscape="false" class="input-xlarge required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelRecuperate.idNumber}"/>
			</c:otherwise>
		</c:choose>
		<div class="control-group">
			<label class="control-label">疗(休)养起始日期：</label>
			<div class="controls">
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${personnelRecuperate.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">疗(休)养结束日期：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${personnelRecuperate.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否带家属：</label>
			<div class="controls">
				<form:select path="isBringMember" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">疗(休)养名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">疗(休)养去处：</label>
			<div class="controls">
				<form:input path="place" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">疗(休)养去处(补充)：</label>
			<div class="controls">
				<form:input path="placeSupplement" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">疗(休)养备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">疗(休)养组织单位名称：</label>
			<div class="controls">
				<form:input path="unitName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">疗(休)养组织单位代码：</label>
			<div class="controls">
				<form:input path="unitCode" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">疗(休)养组织单位级别：</label>
			<div class="controls">
				<form:select path="unitLevel" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personnel_lyzzl')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">疗(休)养组织单位性质类别：</label>
			<div class="controls">
				<form:select path="unitType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personnel_lyzzdw')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="personnel:personnelRecuperate:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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