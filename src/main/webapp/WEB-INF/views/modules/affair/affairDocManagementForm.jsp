<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文档管理管理</title>
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
		function setdocClassifyName() {
			$("#docClassifyName").val('');
			var name = $("#docClassifyId").find("option:selected").text();
			$("#docClassifyName").val(name);
		}
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairDocManagement/">文档管理列表</a></li>
		<li class="active">文档<shiro:hasPermission name="affair:affairDocManagement:edit">${not empty affairDocManagement.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairDocManagement:edit">查看</shiro:lacksPermission></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairDocManagement" action="${ctx}/affair/affairDocManagement/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">文档编码：</label>
			<div class="controls">
				<form:input path="docCode" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文档名称：</label>
			<div class="controls">
				<form:input path="docName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文档分类：</label>
			<div class="controls">
                <form:hidden id="docClassifyName" path="docClassifyName" htmlEscape="false" maxlength="255" class="input-xlarge"/>
                <form:select id="docClassifyId" path="docClassifyId" itemValue="docClassifyId" itemLabel="docClassifyName" class="input-xlarge" onchange="setdocClassifyName()">
					<form:option value="" label=""/>
					<c:forEach items="${affairDocClassifyList}" var="affairDocClassify">
						<form:option value="${affairDocClassify.id}" label="${affairDocClassify.classifyName}"></form:option>
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关键字：</label>
			<div class="controls">
				<form:input path="keyword" htmlEscape="false" class="input-xlarge "/>
				<span style="color: red;font-size: 12px">关键字之间用逗号,分开</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否公开：</label>
			<div class="controls">
				<form:radiobuttons id="ispublic" path="ispublic" items="${fns:getDictList('docManage_isPublic')}" itemLabel="label"
								   itemValue="value" htmlEscape="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文档语言：</label>
			<div class="controls">
				<form:radiobuttons id="docLanguage" path="docLanguage" items="${fns:getDictList('doc_manage_language')}" itemLabel="label"
				itemValue="value" htmlEscape="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">适用对象：</label>
			<div class="controls">
				<form:input path="suitableObject" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主要内容：</label>
			<div class="controls">
				<form:textarea path="mainContent" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发布状态：</label>
			<div class="controls">
				<form:radiobuttons id="releaseStatus" path="releaseStatus" items="${fns:getDictList('certificate_status')}" itemLabel="label"
								   itemValue="value" htmlEscape="false"/>
				<%--<form:select path="releaseStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('certificate_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否可下载：</label>
			<div class="controls">
				<form:radiobuttons id="isdownload" path="isdownload" items="${fns:getDictList('affair_isDownload')}" itemLabel="label"
								   itemValue="value" htmlEscape="false"/>
				<%--<form:input path="isdownload" htmlEscape="false" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">下载需要资源数：</label>
			<div class="controls">
				<form:input path="resourcesNum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>

			<div class="controls">
				<form:hidden id="appendfile" path="appendfile" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="appendfile" type="files" uploadPath="affair/affairDocManagement" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairDocManagement:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
	<script>
		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</body>
</html>