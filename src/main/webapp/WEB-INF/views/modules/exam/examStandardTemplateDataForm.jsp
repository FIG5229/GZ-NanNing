<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>模板项数据管理管理</title>
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
<%--	<ul class="nav nav-tabs">
		<li><a href="${ctx}/exam/examStandardTemplateData/">模板项数据管理列表</a></li>
		<li class="active"><a href="${ctx}/exam/examStandardTemplateData/form?id=${examStandardTemplateData.id}">模板项数据管理<shiro:hasPermission name="exam:examStandardTemplateData:edit">${not empty examStandardTemplateData.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="exam:examStandardTemplateData:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="examStandardTemplateData" action="${ctx}/exam/examStandardTemplateData/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<%--<div class="control-group">
			<label class="control-label">模板项目ID：</label>
			<div class="controls">
				<form:input path="itemId" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数据项值：</label>
			<div class="controls">
				<form:input path="itemValue" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行号：</label>
			<div class="controls">
				<form:input path="rowNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>--%>

		<%--有多少列--%>
		<input value="${list.size()}" name="columnNum" type="hidden">
		<%--行号--%>
		<input value="${examStandardTemplateData.rowNum}" name="rowNum" type="hidden">
		<%--考评标准Id，更新缓存--%>
		<input value="${examStandardTemplateData.itemId}" name="examStardardId" type="hidden">
		<c:forEach items="${list}" var="list" varStatus="status">
			<div class="control-group">
				<label class="control-label">${list.get("column_name")}：</label>
				<div class="controls">
					<input type="text" name="column${list.get("column_order")}" value="${list.get('item_value')}">
					<input type="hidden" name="id${list.get("column_order")}" value="${list.get('id')}">
					<input type="hidden" name="itemId${list.get("column_order")}" value="${list.get('item_id')}">
				</div>
			</div>
		</c:forEach>

		<div class="form-actions">
			<shiro:hasPermission name="exam:examStandardBaseInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<%--<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>--%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
<script>
	if ("success" == "${saveResult}"){
		parent.$.jBox.close();
	}
</script>
</body>
</html>