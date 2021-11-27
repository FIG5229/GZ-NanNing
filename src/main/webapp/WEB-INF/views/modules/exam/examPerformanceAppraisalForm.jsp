<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>部门绩效考核情况管理</title>
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
			$("#sum").keydown(function (e) {
				if (e.keyCode == 13) {
					var less = $("#less").val();
					var add = $("#add").val();
					$("#sum").val((100 - Number(less)) + Number(add))
					console.log($("#sum").val())
				}
			})
		});
		function save() {
			$("#inputForm").submit();
		}
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="examPerformanceAppraisal" action="${ctx}/exam/examPerformanceAppraisal/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">部门：</label>
			<div class="controls">
				<form:input path="dep" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扣分项目：</label>
			<div class="controls">
<%--				<form:input path="lessItem" htmlEscape="false" class="input-xlarge "/>--%>
				<form:textarea path="lessItem" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加分项目：</label>
			<div class="controls">
<%--				<form:input path="addItem" htmlEscape="false" class="input-xlarge "/>--%>
				<form:textarea path="addItem" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扣分：</label>
			<div class="controls">
				<form:input path="penalties" id="less" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加分：</label>
			<div class="controls">
				<form:input path="addPoints" id="add" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">总得分：</label>
			<div class="controls">
				<form:input path="sum" id="sum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">时间：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${examPerformanceAppraisal.date}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
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
					   value="${examPerformanceAppraisal.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group" id="month" style="display: none">
			<label class="control-label">月度：</label>
			<div class="controls">
				<input name="month" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${examPerformanceAppraisal.month}"
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
			<input id="btnSubmit" class="btn btn-primary" type="button" onclick="save()" value="保 存"/>&nbsp;
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