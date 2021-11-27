<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人民警察证信息管理</title>
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
	<form:form id="inputForm" modelAttribute="personnelPoliceCertificate" action="${ctx}/personnel/personnelPoliceCertificate/backFill" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input id="status" name="status" type="hidden" value="${status}"/>
		<input id="startEndDate" name="startEndDate" type="hidden" value="${startEndDate}"/>
		<input id="endEndDate" name="endEndDate" type="hidden" value="${endEndDate}"/>

		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">有效截止日期：</label>
			<div class="controls">
				<input name="backFillDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value=""
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<li class="btns"><input id="export" class="btn btn-primary" type="submit" value="回填"/></li>
	</form:form>
	<script>
		if("success"=="${backFillResult}"){
			parent.$.jBox.close();
		}
	</script>
</body>
</html>