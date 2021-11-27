<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党内创品牌活动管理</title>
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
	<br/>
	<form:form id="inputForm" modelAttribute="affairCreateBranch" action="${ctx}/affair/affairCreateBranch/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">活动名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge requred"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动开始时间：</label>
			<div class="controls">
				<input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairCreateBranch.startTime}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动结束时间：</label>
			<div class="controls">
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${affairCreateBranch.endTime}" pattern="yyyy-MM-dd HH:mm"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">党组织：</label>
			<div class="controls">
				<sys:treeselect id="partyBranch" name="partyBranchId" value="${affairCreateBranch.partyBranchId}" labelName="partyBranch" labelValue="${affairCreateBranch.partyBranch}"
								title="党组织" url="/affair/affairGeneralSituation/treeData" cssClass="required"
								allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">举办单位：</label>
			<div class="controls">
				<sys:treeselect id="holdUnit" name="holdUnitId" value="${affairCreateBranch.holdUnitId}" labelName="holdUnit" labelValue="${affairCreateBranch.holdUnit}"
					title="举办单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否被命名：</label>
			<div class="controls">
				<form:select path="isNamed" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_is_named')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动地点：</label>
			<div class="controls">
				<form:textarea path="place" htmlEscape="false" rows="4" class="input-xxlarge requred"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">参加人员：</label>
			<div class="controls">
				<form:textarea path="joinPerson" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">参加人员：</label>
			<div class="controls">
					<%--								url="/sys/office/treeData?type=3" --%>
				<sys:treeselect id="joinPerson" name="joinPersonId" value="${affairCreateBranch.joinPersonId}" labelName="joinPerson" labelValue="${affairCreateBranch.joinPerson}" examTreeType="dym"
								title="参加人员"
								url ="/affair/affairGeneralSituation/treeData2?examTreeType=dym&type=3&flag=pm"
								allowClear="true" checked="true" notAllowSelectParent="true" cssStyle="width:300px;" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动方案：</label>
			<div class="controls">
				<form:textarea path="scheme" htmlEscape="false" rows="4" class="input-xxlarge "/>
				<%--<sys:ckeditor replace="scheme"/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">阶段活动开展简报：</label>
			<div class="controls">
				<form:textarea path="briefReport" htmlEscape="false" rows="4" class="input-xxlarge "/>
				<%--<sys:ckeditor replace="briefReport"/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关资料：</label>
			<div class="controls">
				<form:hidden id="materialPath" path="materialPath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="materialPath" type="files" uploadPath="affair/affairCreateBranch" selectMultiple="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairCreateBranch:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>