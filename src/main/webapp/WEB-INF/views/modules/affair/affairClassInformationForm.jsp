<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程信息管理</title>
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
	<form:form id="inputForm" modelAttribute="affairClassInformation" action="${ctx}/affair/affairClassInformation/save?look=${look}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">培训班名称：</label>
			<div class="controls">
				<form:input path="className" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课程编号：</label>
			<div class="controls">
				<form:input path="number" htmlEscape="false" class= "input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课程名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教官：</label>
			<div class="controls">
				<form:input path="teacher" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<%--
				<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<input name="beganDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairClassInformation.beganDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<input name="resultDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairClassInformation.resultDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>

		--%>


		<div class="control-group">
			<label class="control-label">开课时间：</label>
			<div class="controls">
				<form:input path="learnTime" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<input name="classId" value="${classId}" style="display:none">
		<div class="control-group">
			<label class="control-label">在线/面授：</label>
			<div class="controls">
				<form:select path="way" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('way_classify')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学分：</label>
			<div class="controls">
				<form:input path="score" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建人：</label>
			<div class="controls">
				<form:input path="creator" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<input name="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairClassInformation.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairClassInformation:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
	<script>
		if ("success" == "${saveResult}"){
			parent.$.jBox.close();
		}

	</script>
</body>
</html>