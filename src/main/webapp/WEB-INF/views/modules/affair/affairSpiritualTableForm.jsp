<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>复查表管理</title>
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
	<form:form id="inputForm" modelAttribute="affairSpiritualTable" action="${ctx}/affair/affairSpiritualTable/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">部门：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairSpiritualTable.unitId}" labelName="unit" labelValue="${affairSpiritualTable.unit}"
								title="所属单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" isAll="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位地址：</label>
			<div class="controls">
				<form:input path="unitPlace" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">检查成绩：</label>
			<div class="controls">
				<form:input path="number" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">抽查成绩：</label>
			<div class="controls">
				<form:input path="randomNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建成效简介：</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>　
		<div class="control-group">
			<label class="control-label">存在问题：</label>
			<div class="controls">
				<form:textarea path="problem" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>　

		<div class="control-group">
			<label class="control-label">复查建议：</label>
			<div class="controls">
				<form:input path="suggest" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年度：</label>
			<div class="controls"><input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				   value="${affairSpiritualTable.year}"
				   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准层级：</label>
			<div class="controls">
				<form:input path="ratify" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准时间：</label>
			<div class="controls">
				<input name="time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairSpiritualTable.time}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="adjunct" path="adjunct" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="adjunct" type="files" uploadPath="affair/affairSpiritualTable" selectMultiple="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairSpiritualTable:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
<script>
	if ("success" == "${saveResult}"){
		parent.$.jBox.tip("保存成功");
		parent.$.jBox.close();
	}
</script>
</body>
</html>