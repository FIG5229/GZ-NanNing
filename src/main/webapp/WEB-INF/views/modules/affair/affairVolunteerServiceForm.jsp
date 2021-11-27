<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>志愿服务活动管理</title>
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
		&lt;%&ndash;<li><a href="${ctx}/affair/affairVolunteerService/">志愿服务活动列表</a></li>&ndash;%&gt;
		<li class="active"><a href="${ctx}/affair/affairVolunteerService/form?id=${affairVolunteerService.id}">志愿服务活动<shiro:hasPermission name="affair:affairVolunteerService:edit">${not empty affairVolunteerService.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairVolunteerService:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairVolunteerService" action="${ctx}/affair/affairVolunteerService/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">活动名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">党组织名称：</label>
			<div class="controls">
				<sys:treeselect id="partyBranch" name="partyBranchId" value="${affairVolunteerService.partyBranchId}" labelName="partyBranch" labelValue="${affairVolunteerService.partyBranch}"
					title="党组织名称" url="/affair/affairGeneralSituation/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">举办单位：</label>
			<div class="controls">
				<sys:treeselect id="holdUnit" name="holdUnitId" value="${affairVolunteerService.holdUnitId}" labelName="holdUnit" labelValue="${affairVolunteerService.holdUnit}"
					title="举办单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动开始时间：</label>
			<div class="controls">
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairVolunteerService.startDate}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动结束时间：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairVolunteerService.endDate}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动地点：</label>
			<div class="controls">
				<form:textarea path="place" htmlEscape="false" rows="4" class="input-xxlarge required"/>
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
				<sys:treeselect id="joinPerson" name="joinPersonId" value="${affairVolunteerService.joinPersonId}" labelName="joinPerson" labelValue="${affairVolunteerService.joinPerson}"
								title="参加人员"
								examTreeType="dym"
								url ="/affair/affairGeneralSituation/treeData2?examTreeType=dym&type=3&flag=pm"
								allowClear="true" checked="true" notAllowSelectParent="true" cssStyle="width:300px;" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作流程：</label>
			<div class="controls">
				<form:textarea path="workflow" htmlEscape="false" rows="4" class="input-xxlarge "/>
				<%--<sys:ckeditor replace="workflow"/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动记录主要内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge "/>
				<sys:ckeditor replace="content"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动相关资料：</label>
			<div class="controls">
				<form:hidden id="materialPathA1" path="materialPath1" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="materialPathA1" type="files" uploadPath="affair/affairVolunteerService" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动记录相关资料：</label>
			<div class="controls">
				<form:hidden id="materialPath2" path="materialPath2" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="materialPath2" type="files" uploadPath="affair/affairVolunteerService" selectMultiple="true"/>

			</div>
		</div>


		<div class="form-actions">
			<shiro:hasPermission name="affair:affairVolunteerService:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>