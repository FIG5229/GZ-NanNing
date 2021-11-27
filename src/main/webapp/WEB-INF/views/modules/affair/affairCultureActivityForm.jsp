<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>警营文化活动管理</title>
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
	<form:form id="inputForm" modelAttribute="affairCultureActivity" action="${ctx}/affair/affairCultureActivity/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">活动名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
		<c:choose>
			<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
				<div class="control-group">
					<label class="control-label">组织单位/举办单位：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairCultureActivity.unitId}" labelName="unit" labelValue="${affairCultureActivity.unit}"
										title="组织单位/举办单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息" isAll="true"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="control-group">
					<label class="control-label">组织单位/举办单位：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairCultureActivity.unitId}" labelName="unit" labelValue="${affairCultureActivity.unit}"
										title="组织单位/举办单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		<%--<div class="control-group">
			<label class="control-label">组织单位/举办单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairCultureActivity.unitId}" labelName="unit" labelValue="${affairCultureActivity.unit}"
								title="组织单位/举办单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">活动开始时间：</label>
			<div class="controls">
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairCultureActivity.startDate}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动结束时间：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairCultureActivity.endDate}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动地点：</label>
			<div class="controls">
				<form:input path="place" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参加人员：</label>
			<div class="controls">
				<form:input path="joinPerson" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作流程：</label>
			<div class="controls">
				<form:input path="workflow" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关资料：</label>
			<div class="controls">
				<form:hidden id="material" path="material" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="material" type="files" uploadPath="affair/affairCultureActivity" selectMultiple="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairCultureActivity:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
	<script>
		if("sucess"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</body>
</html>