<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>固资管理管理</title>
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
	<form:form id="inputForm" modelAttribute="affairGz" action="${ctx}/affair/affairGz/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<input type="hidden" value="${affairGz.tabFlag}" name="tabFlag">
		<div class="control-group">
			<label class="control-label">使用时间：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairGz.date}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairGz.unitId}" labelName="unit" labelValue="${affairGz.unit}"
								title="使用单位" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="false" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">固资名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">固资编号：</label>
			<div class="controls">
				<form:input path="serialNumber" htmlEscape="false" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">条形码：</label>
			<div class="controls">
				<form:input path="barCode" htmlEscape="false" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格：</label>
			<div class="controls">
				<form:input path="specification" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">型号：</label>
			<div class="controls">
				<form:input path="model" htmlEscape="false" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数量：</label>
			<div class="controls">
				<form:input path="num" id="num" htmlEscape="false" class="input-xlarge  digits required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单价：</label>
			<div class="controls">
				<form:input path="price" id="price" htmlEscape="false" class="input-xlarge  number required"/>元
				<span class="help-inline"><font color="red">*</font> </span>
			</div>

		</div>
		<div class="control-group">
			<label class="control-label">合计总价：</label>
			<div class="controls">
				<form:input path="totalPrice" id="sum" htmlEscape="false" class="input-xlarge  number" readonly="true"/>元
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预计使用年限：</label>
			<div class="controls">
				<form:input path="userYear" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">保管人：</label>
			<div class="controls">
				<form:input path="bgPerson" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">验收人员：</label>
			<div class="controls">
				<form:input path="ysPerson" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">验收意见：</label>
			<div class="controls">
				<form:input path="ysOpinion" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div>
			<div class="controls">
				<a href="${ctx}/file/template/download?fileName=固定资产新建交接记录.xls">《固定资产新建交接记录表（模板）》下载</a>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">《固定资产新建交接记录表》上传：</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filePath" type="files" uploadPath="affair/affairGz" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				<form:hidden path="shType" value="1"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairGz:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
		</div>
	</form:form>
	<script>
		if("success"=="${saveResult}"){
			parent.tabFlag.val(${affairGz.tabFlag});
			parent.$.jBox.close();
		}
	</script>

</body>
</html>