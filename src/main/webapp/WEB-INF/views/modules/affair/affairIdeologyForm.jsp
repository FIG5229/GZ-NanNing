<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>意识形态管理</title>
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
		<li><a href="${ctx}/affair/affairIdeology/">意识形态列表</a></li>
		<li class="active"><a href="${ctx}/affair/affairIdeology/form?id=${affairIdeology.id}">意识形态<shiro:hasPermission name="affair:affairIdeology:edit">${not empty affairIdeology.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairIdeology:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairIdeology" action="${ctx}/affair/affairIdeology/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）--%>
		<c:choose>
			<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
				<div class="control-group">
					<label class="control-label">单位：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairIdeology.unitId}" labelName="unit" labelValue="${affairIdeology.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" isAll="true" dataMsgRequired="必填信息"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="control-group">
					<label class="control-label">单位：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairIdeology.unitId}" labelName="unit" labelValue="${affairIdeology.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		<%--<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
&lt;%&ndash;				<form:input path="unit" htmlEscape="false" class="input-xlarge "/>&ndash;%&gt;
				<sys:treeselect id="unit" name="unitId" value="${affairIdeology.unitId}" labelName="unit" labelValue="${affairIdeology.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">报告类型：</label>
			<div class="controls">
<%--				<form:input path="reportType" htmlEscape="false" class="input-xlarge "/>--%>
					<form:select path="reportType" class="input-xlarge required">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('report_tale')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">报送时间：</label>
			<div class="controls">
				<input name="reportTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${affairIdeology.reportTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
<%--		<div class="control-group">--%>
<%--			<label class="control-label">时间：</label>--%>
<%--			<div class="controls">--%>
<%--				<input name="time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "--%>
<%--					value="<fmt:formatDate value="${affairIdeology.time}" pattern="yyyy-MM-dd"/>"--%>
<%--					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>--%>
<%--			</div>--%>
<%--		</div>--%>
		<div class="control-group">
			<label class="control-label">报送内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
<%--				<form:input path="filePath" htmlEscape="false" class="input-xlarge "/>--%>
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filePath" type="files" uploadPath="affair/affairIdeology" selectMultiple="true"/>
			</div>
		</div>

		<%--<div class="control-group">
			<label class="control-label">报送状态：</label>
			<div class="controls">
				<form:input path="reportStatus" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签收时间：</label>
			<div class="controls">
				<input name="signInTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairIdeology.signInTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairIdeology:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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