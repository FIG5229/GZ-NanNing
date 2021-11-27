<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>绩效考核情况管理</title>
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
			$("#examType").change(function () {
				showAndHide();
			});
			function showAndHide(){
				if ($("#examType").val() == '1') {
					$('#year').css('display', 'inline-block');
					$('#month').css('display', 'none');
				}else if($("#examType").val() == '2'){
					$('#year').css('display', 'none');
					$('#month').css('display', 'inline-block');
				}
			}
			showAndHide();
		});
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="examLdPerformanceAppraisal" action="${ctx}/exam/examLdPerformanceAppraisal/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<form:input path="unit" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职务：</label>
			<div class="controls">
				<form:input path="job" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分数：</label>
			<div class="controls">
				<form:input path="fraction" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加扣分事项：</label>
			<div class="controls">
				<form:textarea path="item" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考评周期：</label>
			<div class="controls">
				<form:select path="examType" id="examType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('exam_kp_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group" id="year" style="display: none">
			<label class="control-label">年度：</label>
			<div class="controls">
				<input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${examLdPerformanceAppraisal.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group" id="month" style="display: none">
			<label class="control-label">月度：</label>
			<div class="controls">
				<input name="month" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${examLdPerformanceAppraisal.month}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考评流程id：</label>
			<div class="controls">
				<form:input path="workflowId" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
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