<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专报简报/调研文章管理</title>
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
		if("sucess"=="${saveResult}"){
			parent.$.jBox.close();
		};
	</script>
</head>
<body>
<%--	<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairIdeaAnalysis/">党员队伍思想状况分析列表</a></li>
		<li><a href="${ctx}/affair/affairResearchArticle?flag=1">专报简报列表</a></li>
		<li><a href="${ctx}/affair/affairResearchArticle?flag=2">调研文章列表</a></li>
		<c:choose>
			<c:when test="${affairResearchArticle.flag == '1'}">
				<li class="active"><a href="${ctx}/affair/affairResearchArticle/form?id=${affairResearchArticle.id}&flag=1">专报简报<shiro:hasPermission name="affair:affairResearchArticle:edit">${not empty affairResearchArticle.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairResearchArticle:edit">查看</shiro:lacksPermission></a></li>
				<li><a href="${ctx}/affair/affairResearchArticle/form?id=${affairResearchArticle.id}&flag=2">调研文章<shiro:hasPermission name="affair:affairResearchArticle:edit">${not empty affairResearchArticle.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairResearchArticle:edit">查看</shiro:lacksPermission></a></li>
			</c:when>
			<c:otherwise>
				<li><a href="${ctx}/affair/affairResearchArticle/form?id=${affairResearchArticle.id}&flag=1">专报简报<shiro:hasPermission name="affair:affairResearchArticle:edit">${not empty affairResearchArticle.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairResearchArticle:edit">查看</shiro:lacksPermission></a></li>
				<li class="active"><a href="${ctx}/affair/affairResearchArticle/form?id=${affairResearchArticle.id}&flag=2">调研文章<shiro:hasPermission name="affair:affairResearchArticle:edit">${not empty affairResearchArticle.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairResearchArticle:edit">查看</shiro:lacksPermission></a></li>
			</c:otherwise>
		</c:choose>
		<shiro:hasPermission name="affair:affairIdeaAnalysis:edit"><li><a href="${ctx}/affair/affairIdeaAnalysis/form">党员队伍思想状况分析添加</a></li></shiro:hasPermission>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairResearchArticle" action="${ctx}/affair/affairResearchArticle/save?flag=${affairResearchArticle.flag}" method="post" class="form-horizontal">
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
			<label class="control-label">所属党组织：</label>
			<div class="controls">
				<sys:treeselect id="partyOrganization" name="partyOrganizationId" value="${affairResearchArticle.partyOrganizationId}" labelName="partyOrganization" labelValue="${affairResearchArticle.partyOrganization}"
					title="所属党组织" url="/affair/affairGeneralSituation/treeData" cssClass="required" allowClear="true" notAllowSelectParent="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发布日期：</label>
			<div class="controls">
				<input name="publishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairResearchArticle.publishDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主要内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge "/>
				<sys:ckeditor replace="content"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关文件：</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filePath" type="files" uploadPath="affair/affairResearchArticle" selectMultiple="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairResearchArticle:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>