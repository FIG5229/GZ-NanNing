<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>年度&ldquo;三会一课&rdquo;计划管理</title>
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
		function setStatus(status) {
			$("#addStatus").val(status);
		}
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="affairYearThreeOnePlan" action="${ctx}/affair/affairYearThreeOnePlan/save" method="post" class="form-horizontal">
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
			<label class="control-label">党组织名称：</label>
			<div class="controls">
				<sys:treeselect id="partyOrganization" name="partyOrganizationId" value="${affairYearThreeOnePlan.partyOrganizationId}" labelName="partyOrganization" labelValue="${affairYearThreeOnePlan.partyOrganization}"
					title="党组织名称" url="/affair/affairGeneralSituation/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年度：</label>
			<div class="controls">
				<input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="${affairYearThreeOnePlan.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
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
			<label class="control-label">相关文件和影像视频：</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filePath" type="files" uploadPath="affair/affairYearThreeOnePlan" selectMultiple="true"/>
			</div>
			<div class="controls" style="color: red">注意：需审核上传再使用或有要求时才上传</div>
		</div>
		<input id="addStatus" name="addStatus" type="hidden"/>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairYearThreeOnePlan:edit">
				<c:choose>
					<c:when test="${empty affairYearThreeOnePlan.id}">
						<input class="btn btn-primary" type="submit" value="保 存" onclick="setStatus(1)"/>
						<input class="btn btn-primary" type="submit" value="提 交" onclick="setStatus(2)"/>
					</c:when>
					<c:otherwise>
						<%--其他人的数据无权操作  自己的数据已提交后不能再重复保存和提交--%>
						<%--<c:if test="${affairYearThreeOnePlan.createBy.id == fns:getUser().id && affairYearThreeOnePlan.addStatus != '2'}">--%>
							<input class="btn btn-primary" type="submit" value="保 存" onclick="setStatus(1)"/>
							<input class="btn btn-primary" type="submit" value="提 交" onclick="setStatus(2)"/>
						<%--</c:if>--%>
					</c:otherwise>
				</c:choose>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>