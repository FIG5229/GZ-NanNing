<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民主（组织）生活会管理</title>
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
		function setStatus(status) {
			$("#addStatus").val(status);
		};
		if("sucess"=="${saveResult}"){
			parent.$.jBox.close();
		};
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="affairLifeMeet" action="${ctx}/affair/affairLifeMeet/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">会议名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">党组织名称：</label>
			<div class="controls">
				<sys:treeselect id="partyOrganization" name="partyOrganizationId" value="${affairLifeMeet.partyOrganizationId}" labelName="partyOrganization" labelValue="${affairLifeMeet.partyOrganization}"
					title="党组织名称" url="/affair/affairGeneralSituation/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">召开时间：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairLifeMeet.date}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主持人：</label>
			<div class="controls">
				<form:input path="hold" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">记录人：</label>
			<div class="controls">
				<form:input path="noteTaker" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会议议程：</label>
			<div class="controls">
				<form:textarea path="agenda" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">应参会人员：</label>
			<div class="controls">
				<form:textarea path="person1" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">参会人员：</label>
			<div class="controls">
				<sys:treeselect id="person2" name="person2Id" value="${affairLifeMeet.person2Id}" labelName="person2" labelValue="${affairLifeMeet.person2}"
								title="参加人员"
								examTreeType="dym"
								url ="/affair/affairGeneralSituation/treeData2?examTreeType=dym&type=3&flag=pm"
								allowClear="true" checked="true" notAllowSelectParent="true" cssStyle="width:300px;" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">缺席人员：</label>
			<div class="controls">
				<sys:treeselect id="person3" name="person3Id" value="${affairLifeMeet.person3Id}" labelName="person3" labelValue="${affairLifeMeet.person3}"
								title="缺席人员"
								examTreeType="dym"
								url ="/affair/affairGeneralSituation/treeData2?examTreeType=dym&type=3&flag=pm"
								allowClear="true" checked="true" notAllowSelectParent="true" cssStyle="width:300px;" />

			</div>
		</div>
		<div class="control-group">
			<label class="control-label" >相关文件:</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filePath" type="files" uploadPath="affair/affairLifeMeet" selectMultiple="true"/>
			</div>
			<div class="controls" style="color: red">注意：需审核上传再使用或有要求时才上传</div>
		</div>
		<input id="addStatus" name="addStatus" type="hidden"/>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairLifeMeet:edit">
				<c:choose>
					<c:when test="${empty affairLifeMeet.id}">
						<shiro:hasPermission name="affair:affairElection:edit"><input class="btn btn-primary" type="submit" value="保 存" onclick="setStatus(1)"/>&nbsp;</shiro:hasPermission>
						<shiro:hasPermission name="affair:affairElection:edit"><input class="btn btn-primary" type="submit" value="提 交" onclick="setStatus(2)"/>&nbsp;</shiro:hasPermission>
					</c:when>
					<c:otherwise>
						<%--其他人的数据无权操作  自己的数据已提交后不能再重复保存和提交--%>
						<c:if test="${affairLifeMeet.createBy.id == fns:getUser().id && affairLifeMeet.addStatus != '2'}">
							<shiro:hasPermission name="affair:affairElection:edit"><input class="btn btn-primary" type="submit" value="保 存" onclick="setStatus(1)"/>&nbsp;</shiro:hasPermission>
							<shiro:hasPermission name="affair:affairElection:edit"><input class="btn btn-primary" type="submit" value="提 交" onclick="setStatus(2)"/>&nbsp;</shiro:hasPermission>
						</c:if>
					</c:otherwise>
				</c:choose>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>