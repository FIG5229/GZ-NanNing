<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>小种养管理</title>
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
<br>
	<form:form id="inputForm" modelAttribute="affairXzy" action="${ctx}/affair/affairXzy/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">建设时间：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${affairXzy.date}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairXzy.unitId}" labelName="unit" labelValue="${affairXzy.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">种养点名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">小菜园面积（㎡）：</label>
			<div class="controls">
				<form:input path="vgArea" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">小果园面积（㎡）：</label>
			<div class="controls">
				<form:input path="orArea" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">果树数量（棵）：</label>
			<div class="controls">
				<form:input path="orNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">小养殖园面积（㎡）：</label>
			<div class="controls">
				<form:input path="yzArea" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">鸡（只）：</label>
			<div class="controls">
				<form:input path="ckNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">鸭（只）：</label>
			<div class="controls">
				<form:input path="dkNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">鹅（只）：</label>
			<div class="controls">
				<form:input path="geNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">猪（头）：</label>
			<div class="controls">
				<form:input path="pgNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">其他家禽家畜：</label>
			<div class="controls">
				<form:input path="otherNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">鱼（尾）：</label>
			<div class="controls">
				<form:input path="fhNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">鱼塘面积（㎡）：</label>
			<div class="controls">
				<form:input path="fpArea" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:input path="content" htmlEscape="false" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经办人：</label>
			<div class="controls">
				<form:input path="jingBan" id="content" htmlEscape="false" class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申报单位审核人：</label>
			<div class="controls">
				<form:input path="unitShRen" id="content" htmlEscape="false" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处工会审核意见：</label>
			<div class="controls">
				<form:input path="chuShOpinion" id="content" htmlEscape="false" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">局工会审核意见：</label>
			<div class="controls">
				<form:input path="juShOpinion" id="content" htmlEscape="false" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filePath" type="files" uploadPath="affair/affairXzy" selectMultiple="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairXzy:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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