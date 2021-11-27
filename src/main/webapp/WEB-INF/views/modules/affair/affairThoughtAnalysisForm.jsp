<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>思想状况分析管理</title>
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

<%--		<li><a href="${ctx}/affair/affairThoughtAnalysis/">思想状况分析列表</a></li>--%>
		<%--<li class="active"><a href="${ctx}/affair/affairThoughtAnalysis/form?id=${affairThoughtAnalysis.id}">思想状况分析<shiro:hasPermission name="affair:affairThoughtAnalysis:edit">${not empty affairThoughtAnalysis.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairThoughtAnalysis:edit">查看</shiro:lacksPermission></a></li>--%>
<br/>
	<form:form id="inputForm" modelAttribute="affairThoughtAnalysis" action="${ctx}/affair/affairThoughtAnalysis/save" method="post" class="form-horizontal">
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
			<label class="control-label">所属团组织：</label>
			<div class="controls">
				<sys:treeselect id="ownGroup" name="ownGroupId" value="${affairThoughtAnalysis.ownGroupId}" labelName="ownGroup" labelValue="${affairThoughtAnalysis.ownGroup}"
					title="所属团组织" url="/affair/affairTwBase/treeData" cssClass="required" allowClear="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主要内容：</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="true" path="mainContent" rows="4" maxlength="150" class="input-xxlarge required"/>
				<sys:ckeditor replace="content" uploadPath="/cms/article" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发布时间：</label>
			<div class="controls">
				<input name="releaseTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairThoughtAnalysis.releaseTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="annex" path="annex" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="annex" type="files" uploadPath="affair/affairThoughtAnalysis" selectMultiple="true"/>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="affair:affairThoughtAnalysis:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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