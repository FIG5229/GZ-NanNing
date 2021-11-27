<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>场地管理管理</title>
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
    <li><a href="${ctx}/affair/affairSitemAnagement/">场地管理列表</a></li>
		<li class="active"><a href="${ctx}/affair/affairSitemAnagement/form?id=${affairSitemAnagement.id}">场地管理<shiro:hasPermission name="affair:affairSitemAnagement:edit">${not empty affairSitemAnagement.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairSitemAnagement:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>--%>
	<form:form id="inputForm" modelAttribute="affairSitemAnagement" action="${ctx}/affair/affairSitemAnagement/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">省：</label>
			<div class="controls">
				<form:select id="province" path="province" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_sheng')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">市：</label>
			<div class="controls">
				<form:select id="city" path="city" class="input-xlarge required">
				<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_shi')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">场地名称：</label>
			<div class="controls">
				<form:input path="siteName" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最大承受能力：</label>
			<div class="controls">
				<form:input path="maxChengxunCapacity" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">场地电脑个数：</label>
			<div class="controls">
				<form:input path="siteComputerNum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地址：</label>
			<div class="controls">
				<form:input path="site" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱：</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>

			<div class="controls">
				<form:hidden id="accessoryFile" path="accessoryFile" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="appendfile" type="files" uploadPath="/affair/affairSitemAnagement" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">场地床位个数：</label>
			<div class="controls">
				<form:input path="siteBedNum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">食堂就餐人数：</label>
			<div class="controls">
				<form:input path="siteCanteenRepastNum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人：</label>
			<div class="controls">
				<form:input path="linkman" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">传真：</label>
			<div class="controls">
				<form:input path="fax" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否有效：</label>
			<div class="controls">
				<form:radiobuttons path="isValid" items="${fns:getDictList('affair_isValid')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairSitemAnagement:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script>
		if("sucess"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</body>
</html>