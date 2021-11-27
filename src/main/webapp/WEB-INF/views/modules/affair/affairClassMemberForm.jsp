<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>班子成员管理</title>
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
		}
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairClassMember/">班子成员列表</a></li>
		<li class="active"><a href="${ctx}/affair/affairClassMember/form?id=${affairClassMember.id}">班子成员<shiro:hasPermission name="affair:affairClassMember:edit">${not empty affairClassMember.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairClassMember:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairClassMember" action="${ctx}/affair/affairClassMember/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">党组织名称：</label>
			<div class="controls">
				<sys:treeselect id="partyOrganization" name="partyOrganizationId" value="${affairClassMember.partyOrganizationId}" labelName="partyOrganization" labelValue="${affairClassMember.partyOrganization}"
					title="党组织名称" url="/affair/affairGeneralSituation/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">党组织书记：</label>
			<div class="controls">
				<form:input path="shuji" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">党组织副书记：</label>
			<div class="controls">
				<form:input path="fushuji" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">组织委员：</label>
			<div class="controls">
				<form:input path="zzwy" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">纪检委员：</label>
			<div class="controls">
				<form:input path="jjwy" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">宣传委员：</label>
			<div class="controls">
				<form:input path="xcwy" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">青年委员：</label>
			<div class="controls">
				<form:input path="yqwy" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系包保情况：</label>
			<div class="controls">
				<form:textarea path="situation" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" style="width: 200px;">工作分工及量化标准附件：</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filePath" type="files" uploadPath="affair/affairClassMember" selectMultiple="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairClassMember:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>