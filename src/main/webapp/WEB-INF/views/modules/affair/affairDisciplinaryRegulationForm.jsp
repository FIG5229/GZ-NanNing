<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<jsp:useBean id="now" class="java.util.Date" />
<html>
<head>
	<title>纪律规定管理</title>
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
	<form:form id="inputForm" modelAttribute="affairDisciplinaryRegulation" action="${ctx}/affair/affairDisciplinaryRegulation/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">文件名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">施行时间：</label>
			<div class="controls">
				<input name="impTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairDisciplinaryRegulation.impTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_jilv')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">上传时间：</label>
			<div class="controls">
				<input name="uploadTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>"
					   />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上传单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairDisciplinaryRegulation.unitId}" labelName="unit" labelValue="${affairDisciplinaryRegulation.unit}"
								title="单位" url="/sys/office/treeData?type=2"  allowClear="true" notAllowSelectParent="false" isAll="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="isType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_jlgd_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">查阅范围：</label>
			<div class="controls">
				<sys:treeselect id="receiveUnit" name="receiveUnitId" value="${affairDisciplinaryRegulation.receiveUnitId}" labelName="receiveUnit" labelValue="${affairDisciplinaryRegulation.receiveUnit}"
					title="接收单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" checked="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主要内容：</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="true" path="content" rows="4" class="input-xxlarge"/>
				<sys:ckeditor replace="content"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filePath" type="files" uploadPath="affair/affairDisciplinaryRegulation" selectMultiple="true"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">审核状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_shenhe')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核人：</label>
			<div class="controls">
				<form:input path="reviewer" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核意见：</label>
			<div class="controls">
				<form:input path="auditOpinion" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>

		<div class="form-actions">
			<shiro:hasPermission name="affair:affairDisciplinaryRegulation:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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