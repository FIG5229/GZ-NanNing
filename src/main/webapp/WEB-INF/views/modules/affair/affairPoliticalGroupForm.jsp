<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党小组管理</title>
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

		if("faile"=="${saveResult}"){
			top.$.jBox("存在相同的小组名");
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
<%--		<li><a href="${ctx}/affair/affairPoliticalGroup/">党小组列表</a></li>--%>
<%--		<li class="active"><a href="${ctx}/affair/affairPoliticalGroup/form?id=${affairPoliticalGroup.id}">党小组<shiro:hasPermission name="affair:affairPoliticalGroup:edit">${not empty affairPoliticalGroup.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairPoliticalGroup:edit">查看</shiro:lacksPermission></a></li>--%>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="affairPoliticalGroup" action="${ctx}/affair/affairPoliticalGroup/save" method="post" class="form-horizontal">
		<input id="treeId" name="parentId" type="hidden" value="${parentId}"/>
		<input id="parentIds" name="parentIds" type="hidden" value="${parentIds}"/>
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">党小组名称：</label>
			<div class="controls">
				<form:input path="groupName" htmlEscape="false" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">党小组成立时间：</label>
			<div class="controls">
				<input name="time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairPoliticalGroup.time}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">党小组组长：</label>
			<div class="controls">
				<form:input path="groupHeadman" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="phoneNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">党组织联系人：</label>
			<div class="controls">
				<form:input path="groupContact" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">党小组党员数：</label>
			<div class="controls">
				<form:input path="groupPoliticalNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairPoliticalGroup:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
<%--			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>/--%>
		</div>
	</form:form>
</body>
</html>