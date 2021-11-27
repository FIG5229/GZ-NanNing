<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民警维权管理</title>
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
	<br/>
	<form:form id="inputForm" modelAttribute="affairRightsProtection" action="${ctx}/affair/affairRightsProtection/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<%--10-7问题反馈，督察信息管理账号应改为全局所有单位可选--%>
		<c:choose>
			<c:when test="${fns:getUser().id eq 'ca32723864644fa8979693dc9a539b91' || fns:getUser().id eq '1fdedc31fd6944eb8cbb9a279f4cb3c4' ||
				fns:getUser().id eq '26449823050b49c786f7baff26b6a7a2' || fns:getUser().id eq 'ad04613867cc41f081c78ac19f159263'}">
				<div class="control-group">
					<label class="control-label">责任单位：</label>
					<div class="controls">
						<sys:treeselect id="responsibleUnitId" name="responsibleUnitId" value="${affairRightsProtection.responsibleUnitId}" labelName="responsibleUnit" labelValue="${affairRightsProtection.responsibleUnit}"
										title="责任单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true"  dataMsgRequired="必填信息" isAll="true"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="control-group">
					<label class="control-label">责任单位：</label>
					<div class="controls">
						<sys:treeselect id="responsibleUnitId" name="responsibleUnitId" value="${affairRightsProtection.responsibleUnitId}" labelName="responsibleUnit" labelValue="${affairRightsProtection.responsibleUnit}"
										title="责任单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true"  dataMsgRequired="必填信息" />
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		<div class="control-group">
			<label class="control-label">时间：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairRightsProtection.date}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">监督单位：</label>
			<div class="controls">
				<form:select path="unit" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_mjwq')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主要内容：</label>
			<div class="controls">
				<form:textarea id="mainContent" htmlEscape="false" path="mainContent" rows="10" maxlength="500" class="input-xlarge" cssStyle="width: 450px;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="annex" path="annex" htmlEscape="false" maxlength="255" class="input-xlarge" />
				<sys:webuploader input="annex" type="files" uploadPath="affair/affairRightsProtection" selectMultiple="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">查询范围：</label>
			<div class="controls">
				<form:select path="problemCategoryArr" class="input-xlarge required"  multiple="true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_mjwq')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
<%--				<form:checkboxes path="range" items="${fns:getDictList('affair_mjwq')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>--%>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairRightsProtection:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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