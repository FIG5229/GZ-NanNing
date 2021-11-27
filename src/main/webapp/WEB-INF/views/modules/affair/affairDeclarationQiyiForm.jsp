<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工作申报管理</title>
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
		<li><a href="${ctx}/affair/affairDeclarationQiyi/">工作申报列表</a></li>
		<li class="active"><a href="${ctx}/affair/affairDeclarationQiyi/form?id=${affairDeclarationQiyi.id}">工作申报<shiro:hasPermission name="affair:affairDeclarationQiyi:edit">${not empty affairDeclarationQiyi.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairDeclarationQiyi:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairDeclarationQiyi" action="${ctx}/affair/affairDeclarationQiyi/save?type=${type}&topType=${topType}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<c:if test="${type != 1}">
			<div class="control-group">
				<label class="control-label">
					<c:if test="${type==2 || type ==3}">姓名</c:if>
					<c:if test="${type==4}">品牌名</c:if>：
				</label>
				<div class="controls">
					<form:input path="name" htmlEscape="false" class="input-xlarge "/>
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">
				<c:if test="${type == 1}">上报单位</c:if>
				<c:if test="${type == 2 || type == 3 || type == 4 }">所在单位</c:if>：
			</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairDeclarationQiyi.unitId}" labelName="unit" labelValue="${affairDeclarationQiyi.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</div>
		</div>
		<c:if test="${type ==2 || type == 3}">
			<div class="control-group">
				<label class="control-label">职务：</label>
				<div class="controls">
					<form:input path="job" htmlEscape="false" class="input-xlarge "/>
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">主要事迹：</label>
			<div class="controls">
				<form:textarea path="mainStory" htmlEscape="false" class="input-xlarge " rows="6"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">组织意见：</label>
			<div class="controls">
				<form:textarea path="orgOpinion" htmlEscape="false" class="input-xlarge " rows="6"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" class="input-xlarge " rows="4"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				<sys:webuploader input="filePath" type="files" uploadPath="/affair/affairDeclarationQiyi" selectMultiple="true"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">审核人：</label>
			<div class="controls">
				<form:input path="checkMan" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提交人：</label>
			<div class="controls">
				<form:input path="submitMan" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核状态（0-4/提交）：</label>
			<div class="controls">
				<form:input path="checkType" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核单位id：</label>
			<div class="controls">
				<form:input path="checkId" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提交人id：</label>
			<div class="controls">
				<form:input path="submitId" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">整改意见：</label>
			<div class="controls">
				<form:input path="shOpinion" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更新者机构id：</label>
			<div class="controls">
				<form:input path="updateOrgId" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">1、现进党组织2、优秀党员3、党务工作者4、党内品牌：</label>
			<div class="controls">
				<form:input path="type" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairDeclarationQiyi:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
		</div>
	</form:form>
<script>
	if ("success"=="${saveResult}") {
		parent.$.jBox.close();
	}

</script>
</body>
</html>