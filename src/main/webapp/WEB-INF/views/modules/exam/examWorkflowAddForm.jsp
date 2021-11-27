<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考评管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			if($("#examCycle").val() == 1){
				$("#month").show();
				$("#year").hide();
			}else {
				$("#month").hide();
				$("#year").show();
			}

			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					if($("#examCycle").val() == 1){
						$("#time").val($("#month").val());
					}else {
						$("#time").val($("#year").val());
					}
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

		function examCycleChange() {
			if($("#examCycle").val() == 1){
				$("#month").show();
				$("#year").hide();
			}else {
				$("#month").hide();
				$("#year").show();
			}

			$.ajax({
				type: 'POST',
				url: "${ctx}/exam/examWorkflow/workList",
				timeout :90000,
				beforeSend: function(){
					$("#loading").show();
				},
				data: {
					"examCycle":$("#examCycle").val(),
					"examType":${examType}
				} ,
				success: function (data) {
					if (data.success){
						//debugger;er
						let workList = data.result;
						if (workList != null && workList != undefined) {
								$("#flowTemplateId").empty();
							// $("#flowTemplateId").hide();
							// $("#flowTemplateId").append(('<select id="flowTemplateId1" name="flowTemplateId" onchange="change()">'));
							for (let i = 0; i < workList.length; i++){
								$("#flowTemplateId").append(('<option value='+workList[i].id+'>'+workList[i].name+'</option>'));
							}
							// $("#flowTemplateId").append(('</select>'));
							$("#flowTemplateId").val('');
							/*下拉框关联改变*/
							$("#flowTemplateId").siblings('.select2-container').find('.select2-chosen').text($("#flowTemplateId").find("option:selected").text());

							$("#flowTemplateId").selectedIndex=0;//index为索引值

							// $("#flowTemplateId").show();
							$("#main").show();
							change()
							// $("#flowTemplateId").val(workList[0].name);
						}else {
							$("#flowTemplateId").empty();
							$("#flowTemplateId").hide();
							$("#flowTemplateId").show();
							// var flowTemplate = $("#flowTemplateId option:selected");
							// flowTemplate.remove();
							$("#main").empty();
							$("#main").hide();
							// $("#flowTemplateId").append(('<option value='+""+'>'+"222"+'</option>'));
						}
						var option=document.createElement("option");
						// initFlowTemplate(workList);
					}
				},error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert("error")
					$("#loading").hide();
				},
				complete: function(XMLHttpRequest, textStatus) {
					$("#loading").hide();
				}
			});

		}
		function initFlowTemplate(workList){

		}

		function change() {
			$("#id").val("");
			examFlowChoose();
		}
		function examFlowChoose(){
			var selected = $("#flowTemplateId").val();
			var id = $("#id").val();
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
					"flowId":id
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
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				考评周期：
				<form:select path="examCycle" class="input-xlarge required" cssStyle="width: 100px;" onchange="examCycleChange()" itemValue="${examWorkflow.examCycle}">
					<form:options items="${fns:getDictList('exam_cycle')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
				时间：
				<input name="month" id="month" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   onclick="WdatePicker({dateFmt:'yyyyMM',isShowClear:false});"/>
				<input name="year" id="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<input id="time" name="time" type="hidden" value=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考评流程:</label>
			<div class="controls">
				<select id="flowTemplateId" name="flowTemplateId" onchange="change()" required="required">
					<c:forEach items="${workList}" var="examWorkflowDefine">
						<option value="${examWorkflowDefine.id}">${examWorkflowDefine.name}</option>
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
<%--			<shiro:hasPermission name="exam:examWorkflow:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>--%>
	<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
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