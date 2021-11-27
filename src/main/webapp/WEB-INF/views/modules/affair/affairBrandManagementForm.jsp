<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>品牌创建管理</title>
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

<%--		<li><a href="${ctx}/affair/affairBrandManagement/">品牌创建列表</a></li>--%>
		<%--<li class="active"><a href="${ctx}/affair/affairBrandManagement/form?id=${affairBrandManagement.id}">品牌创建<shiro:hasPermission name="affair:affairBrandManagement:edit">${not empty affairBrandManagement.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairBrandManagement:edit">查看</shiro:lacksPermission></a></li>--%>
<br/>
	<form:form id="inputForm" modelAttribute="affairBrandManagement" action="${ctx}/affair/affairBrandManagement/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<input name="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${affairBrandManagement.createTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">品牌类型：</label>
			<div class="controls">
				<form:select path="brandType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_pinpai')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所在团组织：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairBrandManagement.unitId}" labelName="unit" labelValue="${affairBrandManagement.unit}"
								title="所在团组织" url="/affair/affairTwBase/treeData" cssClass="required" allowClear="true" dataMsgRequired="必填信息" notAllowSelectParent="false"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人员组成：</label>
			<div class="controls">
				<form:textarea path="personnel" htmlEscape="false" rows="4" class="input-xxlarge requred"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">基本情况：</label>
			<div class="controls">
				<form:textarea path="basicSituation" htmlEscape="false" rows="4" class="input-xxlarge requred"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="annex" path="annex" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="annex" type="files" uploadPath="affair/affairBrandManagement" selectMultiple="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairBrandManagement:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
	<script>
		if("sucess"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</body>
</html>