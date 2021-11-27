<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民警帮教管理</title>
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
	<ul class="nav nav-tabs">
		<%--<li><a href="${ctx}/affair/affairPoliceHelpEducate/">民警帮教列表</a></li>--%>
		<%--<li class="active"><a href="${ctx}/affair/affairPoliceHelpEducate/form?id=${affairPoliceHelpEducate.id}">民警帮教<shiro:hasPermission name="affair:affairPoliceHelpEducate:edit">${not empty affairPoliceHelpEducate.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairPoliceHelpEducate:edit">查看</shiro:lacksPermission></a></li>--%>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="affairPoliceHelpEducate" action="${ctx}/affair/affairPoliceHelpEducate/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<c:choose>
			<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
				<div class="control-group">
					<label class="control-label">单位：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairPoliceHelpEducate.unitId}" labelName="unit" labelValue="${affairPoliceHelpEducate.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息" isAll="true"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="control-group">
					<label class="control-label">单位：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairPoliceHelpEducate.unitId}" labelName="unit" labelValue="${affairPoliceHelpEducate.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		<%--<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairPoliceHelpEducate.unitId}" labelName="unit" labelValue="${affairPoliceHelpEducate.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">帮教时间：</label>
			<div class="controls">
				<input name="time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairPoliceHelpEducate.time}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>

			</div>
		</div>
<%--		<div class="control-group">--%>
<%--			<label class="control-label">帮教地点：</label>--%>
<%--			<div class="controls">--%>
<%--				<form:input path="place" htmlEscape="false" class="input-xlarge required"/>--%>
<%--				<span class="help-inline"><font color="red">*</font> </span>--%>
<%--			</div>--%>
<%--		</div>--%>
		<div class="control-group">
			<label class="control-label">帮教人：</label>
			<div class="controls">
				<form:input path="helpers" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">帮教对象：</label>
			<div class="controls">
				<form:input path="helpobject" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
<%--		<div class="control-group">--%>
<%--			<label class="control-label">对象存在问题：</label>--%>
<%--			<div class="controls">--%>
<%--				<form:textarea path="question" htmlEscape="false"  rows="4" class="input-xlarge required"/>--%>
<%--				<span class="help-inline"><font color="red">*</font> </span>--%>
<%--			</div>--%>
<%--		</div>--%>
<%--		<div class="control-group">--%>
<%--			<label class="control-label">帮教措施：</label>--%>
<%--			<div class="controls">--%>
<%--				<form:textarea path="measures" htmlEscape="false" rows="4" class="input-xlarge required"/>--%>
<%--				<span class="help-inline"><font color="red">*</font> </span>--%>
<%--			</div>--%>
<%--		</div>--%>
		<div class="control-group">
			<label class="control-label">帮教成效：</label>
			<div class="controls">
				<form:textarea path="results" htmlEscape="false" rows="4" class="input-xxlarge "/>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">简要内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge "/>
				<sys:ckeditor replace="content"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="filepath" path="filepath" htmlEscape="false" class="input-xlarge "/>
				<sys:webuploader input="filepath" type="files" uploadPath="/affari/affairPoliceHelpEducate" selectMultiple="true"/>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="affair:affairPoliceHelpEducate:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>