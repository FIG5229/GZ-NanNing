<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>组织奖惩信息添加</title>
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

			$("#awardCategory").change(function () {
				console.log($("#awardCategory").val());
				switchType();
			});
			function switchType() {
				if ($("#awardCategory").val() == '1'){
					$("#s2id_type").css('display', 'inline-block');
					$("#s2id_cjType").css('display', 'none');
					$("#cjType").val("");
				}else if ($("#awardCategory").val() == '2'){
					$("#s2id_type").css('display', 'none');
					$("#type").val("");
					$("#s2id_cjType").css('display', 'inline-block');
				}
			}
			switchType();
		});
		if("sucess"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="affairOrgRewardPunish" action="${ctx}/affair/affairOrgRewardPunish/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">奖惩时间：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="<fmt:formatDate value="${affairOrgRewardPunish.date}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准机关：</label>
			<div class="controls">
				<form:select path="approvalOrg" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_paryt_committee')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖惩类别：</label>
			<div class="controls">
				<form:select id="awardCategory" path="awardCategory" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('award_category')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>


				<form:select id="type" path="type" class="input-xlarge required" cssStyle="display: none">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_org_reward_punish')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<form:select id="cjType" path="cjType" class="input-xlarge required" cssStyle="display: none">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('cj_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">奖惩标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">党组织名称：</label>
			<div class="controls">
				<sys:treeselect id="partyOrganization" name="partyOrganizationId" value="${affairOrgRewardPunish.partyOrganizationId}" labelName="partyOrganization" labelValue="${affairOrgRewardPunish.partyOrganization}"
					title="党组织名称" url="/affair/affairGeneralSituation/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖惩文号：</label>
			<div class="controls">
				<form:input path="fileNo" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖惩内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairOrgRewardPunish:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>