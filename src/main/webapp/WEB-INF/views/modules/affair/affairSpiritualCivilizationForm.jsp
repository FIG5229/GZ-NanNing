<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>精神文明单位管理管理</title>
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
	<form:form id="inputForm" modelAttribute="affairSpiritualCivilization" action="${ctx}/affair/affairSpiritualCivilization/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairSpiritualCivilization.unitId}" labelName="unit" labelValue="${affairSpiritualCivilization.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" isAll="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年度：</label>
			<div class="controls">
				<input name="annual" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="${affairSpiritualCivilization.annual}" onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准层级：</label>
			<div class="controls">
				<form:select path="approvalLevels" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('approval_levels')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准时间：</label>
			<div class="controls">
				<input name="approvalTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${affairSpiritualCivilization.approvalTime}" pattern="yyyy-MM-dd HH:mm"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>　
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="adjunct" path="adjunct" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="adjunct" type="files" uploadPath="affair/affairWorkDone" selectMultiple="true"/>
			</div>
		</div>



		<div class="form-actions">
			<shiro:hasPermission name="affair:affairSpiritualCivilization:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
<script>
	if("sucess"=="${saveResult}"){
		parent.$.jBox.tip("保存成功");
		parent.$.jBox.close();
	}
</script>
</body>
</html>