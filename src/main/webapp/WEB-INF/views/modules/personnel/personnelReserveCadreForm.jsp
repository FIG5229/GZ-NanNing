<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>后备干部信息管理</title>
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
	<form:form id="inputForm" modelAttribute="personnelReserveCadre" action="${ctx}/personnel/personnelReserveCadre/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<c:choose>
			<c:when test="${personnelReserveCadre.idNumber == null}">
				<div class="control-group">
					<label class="control-label">身份证号：</label>
					<div class="controls">
						<form:input path="idNumber" htmlEscape="false" class="input-xlarge required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelReserveCadre.idNumber}"/>
			</c:otherwise>
		</c:choose>
		<div class="control-group">
			<label class="control-label">呈报单位名称：</label>
			<div class="controls">
				<form:input path="unitName" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">呈报单位代码：</label>
			<div class="controls">
				<form:input path="unitCode" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">呈报日期：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${personnelReserveCadre.date}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培养目标（方向）：</label>
			<div class="controls">
				<form:input path="target" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培养目标任职机构类别：</label>
			<div class="controls">
				<form:select path="targetOrgType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personnel_pymbrzjglb')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培养目标职务级别：</label>
			<div class="controls">
				<form:select path="targetJobLevel" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personnel_pymbzwjb')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培养目标职务类别：</label>
			<div class="controls">
				<form:select path="targetJobTypr" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personnel_pymbzwlb')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">后备类别：</label>
			<div class="controls">
				<form:select path="reserveType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personnel_hblb')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">当前状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personnel_dqzt')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="personnel:personnelReserveCadre:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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