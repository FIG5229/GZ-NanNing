<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>一帮一明细管理</title>
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
<%--		<li><a href="${ctx}/affair/affairOneHelpOneMain/">一帮一明细列表</a></li>--%>
<%--		<li class="active"><a href="${ctx}/affair/affairOneHelpOneMain/form?id=${affairOneHelpOneMain.id}">一帮一明细<shiro:hasPermission name="affair:affairOneHelpOneMain:edit">${not empty affairOneHelpOneMain.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairOneHelpOneMain:edit">查看</shiro:lacksPermission></a></li>--%>
<%--	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairOneHelpOneMain" action="${ctx}/affair/affairOneHelpOneMain/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
<%--		<input id="mainId" name="mainId" type="hidden" value="${affairOneHelpOneMain.mainId}"/>--%>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">帮扶人姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职务：</label>
			<div class="controls">
				<form:input path="job" htmlEscape="false" class="input-xlarge "/>
<%--				<form:select path="job" class="input-xlarge ">--%>
<%--					<form:option value="" label=""/>--%>
<%--					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
<%--				</form:select>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">被帮扶姓名：</label>
			<div class="controls">
				<form:input path="beName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位职务：</label>
			<div class="controls">
				<form:input path="unitJob" htmlEscape="false" class="input-xlarge "/>
			<%--	<form:select path="unitJob" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">被帮扶人情况：</label>
			<div class="controls">
				<form:input path="situation" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">被帮扶人住址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">帮扶金额：</label>
			<div class="controls">
				<form:input path="money" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="tel" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
<%--		<div class="control-group">
			<label class="control-label">创建者机构id：</label>
			<div class="controls">
				<form:input path="createOrgId" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
<%--		<div class="control-group">
			<label class="control-label">创建者身份证号：</label>
			<div class="controls">
				<form:input path="createIdNo" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
<%--		<div class="control-group">
			<label class="control-label">更新者机构id：</label>
			<div class="controls">
				<form:input path="updateOrgId" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
<%--		<div class="control-group">--%>
<%--			<label class="control-label">关联表标记：</label>--%>
<%--			<div class="controls">--%>
				<%--不可删除 添加时存放主表Id 进行保存--%>
				<form:hidden path="mainId" htmlEscape="false" class="input-xlarge "/>
<%--			</div>--%>
<%--		</div>--%>
		<div class="control-group">
			<label class="control-label">慰问时间：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairOneHelpOneMain.date}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairOneHelpOne:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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