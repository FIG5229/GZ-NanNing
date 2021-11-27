<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>创建工作管理</title>
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

		function signOrNo(status) {
			$("#inputForm").attr("action","${ctx}/affair/affairCreateWork/save?sign=true&status="+status);
			$("#inputForm").submit();
			$("#inputForm").attr("action","${ctx}/affair/affairCreateWork/sign");
		}

	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairCreateWork/">创建工作列表</a></li>
		<li class="active"><a href="${ctx}/affair/affairCreateWork/form?id=${affairCreateWork.id}">创建工作<shiro:hasPermission name="affair:affairCreateWork:edit">${not empty affairCreateWork.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairCreateWork:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairCreateWork" action="${ctx}/affair/affairCreateWork/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">评分：</label>
			<div class="controls">
				<form:input path="assessmentScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">整改意见：</label>
			<div class="controls">
				<form:textarea path="shOpinion" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>

<%--		<div class="control-group">
			<label class="control-label">整改意见：</label>
			<div class="controls">
				<form:input path="shOpinion" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
	<%--	<div class="control-group">
			<label class="control-label">自评分：</label>
			<div class="controls">
				<form:input path="selfRating" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考核分：</label>
			<div class="controls">
				<form:input path="assessmentScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairCreateWork:edit">
				<input id="btnSubmit" class="btn btn-primary" type="button" value="通过" onclick="signOrNo('3')"/>&nbsp;
				<input id="btnSubmit" class="btn btn-primary" type="button" value="不通过" onclick="signOrNo('4')"/>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
		</div>
	</form:form>

</body>
</html>