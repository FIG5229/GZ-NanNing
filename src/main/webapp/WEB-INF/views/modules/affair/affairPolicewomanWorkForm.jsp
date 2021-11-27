<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>女警工作管理</title>
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
		function setStatus(status) {
			$("#status").val(status);
		};
		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="affairPolicewomanWork" action="${ctx}/affair/affairPolicewomanWork/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发布部门：</label>
			<div class="controls">
				<sys:treeselect id="publishDep" name="publishDepId" value="${affairPolicewomanWork.publishDepId}" labelName="publishDep" labelValue="${affairPolicewomanWork.publishDep}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">接收部门：</label>
			<div class="controls">
				<sys:treeselect id="receiveDep" name="receiveDepId" value="${affairPolicewomanWork.receiveDepId}" labelName="receiveDep" labelValue="${affairPolicewomanWork.receiveDep}"
					title="接收部门" url="/sys/office/treeData?type=2"  allowClear="true" notAllowSelectParent="true" checked="true" />
				<%--<span class="help-inline"><font color="red">*</font> </span> cssClass="required" dataMsgRequired="必填信息" --%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主要内容：</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="true" path="content" rows="4" class="input-xxlarge"/>
				<sys:ckeditor replace="content" uploadPath="/cms/article" />
			</div>
		</div>
		<%--发布日期暂时用更新日期，不让手填--%>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filePath" type="files" uploadPath="affair/affairPolicewomanWork" selectMultiple="true"/>
			</div>
		</div>
		<input id="status" name="status" type="hidden"/>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairPolicewomanWork:manage">
				<%-- 一旦发布不可再操作--%>
				<c:if test="${affairPolicewomanWork.status != '2'}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="setStatus(1)"/>
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="发 布" onclick="setStatus(2)"/>
				</c:if>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>