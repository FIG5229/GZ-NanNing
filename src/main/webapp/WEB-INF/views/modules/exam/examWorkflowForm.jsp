<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考评管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
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
			examFlowChoose();
		});

		function change() {
			$("#flowId").val("");
			examFlowChoose();
		}
		function examFlowChoose(){
			var selected = $("#flowTemplateId").val();
			var flowId = $("#flowId").val();
			$.ajax({
				type: 'POST',
				url: "${ctx}/exam/examWorkflow/getExamFlowSegments",
				timeout :90000,
				beforeSend: function(){
					$("#loading").show();
					$("#main").hide();
				},
				data: {
					"flowTemplateId":selected,
					"flowId":flowId
				} ,
				dataType:'text',
				success: function (result) {
					$("#main").empty();
					//console.log(result);
					$("#main").append(result);
					$("#main").show();
				},error: function(XMLHttpRequest, textStatus, errorThrown) {
					$("#loading").hide();
					$("#main").html("查询失败");
				},
				complete: function(XMLHttpRequest, textStatus) {
					$("#loading").hide();
				}
			});
		}
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="examWorkflow" action="${ctx}/exam/examWorkflow/save" method="post" class="form-horizontal margin-top20">
		<form:hidden path="id"/>
		<input name="flowId" id ="flowId" value="${examWorkflow.id}" type="hidden"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				时间：
				<c:choose>
					<c:when test="${'1'.equals(examWorkflow.examCycle)}">
						<input name="time" id="time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
							   onclick="WdatePicker({dateFmt:'yyyyMM',isShowClear:false});" value="${examWorkflow.time}"/>
					</c:when>
					<c:otherwise>
						<input name="time" id="time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
							   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});" value="${examWorkflow.time}"/>
					</c:otherwise>
				</c:choose>
				(考评周期：${fns:getDictLabel(examWorkflow.examCycle, "exam_cycle", "")})
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考评流程:</label>
			<div class="controls">
				<select id="flowTemplateId" name="flowTemplateId" onchange="change()" required = "required">
					<c:forEach items="${workList}" var="examWorkflowDefine">
						<c:choose>
							<c:when test="${examWorkflow.flowTemplateId eq examWorkflowDefine.id}">
								<option value="${examWorkflowDefine.id}" selected>${examWorkflowDefine.name}</option>
							</c:when>
							<c:otherwise>
								<option value="${examWorkflowDefine.id}">${examWorkflowDefine.name}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

			<div class="control-group">
				<div class="controls">
					<div id="loading" hidden="hidden">
						<img src="${ctxStatic}/images/wait.gif" alt=""/>正在加载数据,请稍候...
					</div>
					<div id="main" style="display:none">

					</div>
				</div>
			</div>
		<div class="form-actions">
			<%--
			<shiro:hasPermission name="exam:examWorkflow:edit"></shiro:hasPermission>--%>
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;

			<!--<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>-->
		</div>
	</form:form>
	<script>
		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</body>
</html>