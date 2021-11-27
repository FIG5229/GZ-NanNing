<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单位集体奖励申报管理</title>
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
	<form:form id="inputForm" modelAttribute="affairXcRewardDeclaration" action="${ctx}/affair/affairXcRewardDeclaration/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>

		<div class="control-group">
			<label class="control-label">奖励名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">荣誉级别：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge " >
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_approval_unitLevel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
<%--					<form:options items="${fns:getDictList('affair_approval_unitLevel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
<%--					更换为统一字典<form:options items="${fns:getDictList('affair_jtsb_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申报时间：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairXcRewardDeclaration.date}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
		<c:choose>
			<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
				<div class="control-group">
					<label class="control-label">申报单位：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairXcRewardDeclaration.unitId}" labelName="unit" labelValue="${affairXcRewardDeclaration.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息" isAll="true"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="control-group">
					<label class="control-label">申报单位：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairXcRewardDeclaration.unitId}" labelName="unit" labelValue="${affairXcRewardDeclaration.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		<%--<div class="control-group">
			<label class="control-label">申报单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairXcRewardDeclaration.unitId}" labelName="unit" labelValue="${affairXcRewardDeclaration.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">审核单位：</label>
			<div class="controls">
					<%--此树type若发生调整，导入方法中的审核单位id设置也要调整--%>
				<sys:treeselect id="approvalUnit" name="approvalUnitId" value="${affairXcRewardDeclaration.approvalUnitId}" labelName="approvalUnit" labelValue="${affairXcRewardDeclaration.approvalUnit}"
								title="审核通过单位" url="/sys/office/treeData?type=3" allowClear="true" notAllowSelectParent="false" isAll="true"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">主要负责人：</label>
			<div class="controls">
				<form:input path="adminPeople" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">简要事迹：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filePath" type="files" uploadPath="affair/affairXcRewardDeclaration" selectMultiple="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairXcRewardDeclaration:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
<script>
	if("success"=="${saveResult}"){
		parent.$.jBox.tip("保存成功");
		parent.$.jBox.close();
	}
</script>
</body>
</html>