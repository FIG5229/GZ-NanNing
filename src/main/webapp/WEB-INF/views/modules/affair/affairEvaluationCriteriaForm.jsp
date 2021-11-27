<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>测评标准管理</title>
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
	<%--<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairEvaluationCriteria/">测评标准列表</a></li>
		<li class="active"><a href="${ctx}/affair/affairEvaluationCriteria/form?id=${affairEvaluationCriteria.id}">测评标准<shiro:hasPermission name="affair:affairEvaluationCriteria:edit">${not empty affairEvaluationCriteria.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairEvaluationCriteria:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairEvaluationCriteria" action="${ctx}/affair/affairEvaluationCriteria/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">测评项目：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年度：</label>
			<div class="controls">
					<%--				<form:input path="year" htmlEscape="false" class="input-xlarge "/>--%>
				<input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="${affairEvaluationCriteria.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">层级：</label>
			<div class="controls">
<%--				<form:input path="level" htmlEscape="false" class="input-xlarge "/>--%>
				<form:select path="level" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('evaluation_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">测评标准：</label>
			<div class="controls">
				<form:textarea path="standard" htmlEscape="false" class="input-xlarge " rows="6"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">测评方法：</label>
			<div class="controls">
				<form:select path="method" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('evaluation_method')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分数值：</label>
			<div class="controls">
				<form:input path="score" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<%--9.2 问题反馈 删除考核目标--%>
<%--		<div class="control-group">
			<label class="control-label">考核目标：</label>
			<div class="controls">
&lt;%&ndash;				<form:input path="unit" htmlEscape="false" class="input-xlarge "/>&ndash;%&gt;
			<sys:treeselect id="unit" name="unitId" value="${affairEvaluationCriteria.unitId}" labelName="unit" labelValue="${affairEvaluationCriteria.unit}"
							title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
<%--				<form:input path="filePath" htmlEscape="false" class="input-xlarge "/>--%>
					<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
					<sys:webuploader input="filePath" type="files" uploadPath="affair/affairEvaluationCriteria" selectMultiple="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairEvaluationCriteria:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
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