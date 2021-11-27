<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民警记实功能管理</title>
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


		function fun(d) {
			//var today = new Date();
			var str = d.value;
			var t = str.substr(0,4);
			document.getElementById("years").value=t;
			var today= new Date(Date.parse(str.replace(/-/g,  "/"))); //转换成Data();
			var firstDay = new Date(today.getFullYear(),0, 1);
			var dayOfWeek = firstDay.getDay();
			var spendDay= 1;
			if (dayOfWeek !=0) {
				spendDay=7-dayOfWeek+1;
			}
			firstDay = new Date(today.getFullYear(),0, 1+spendDay);
			var d =Math.ceil((today.valueOf()- firstDay.valueOf())/ 86400000);
			var result =Math.ceil(d/7);
			var week=result+1;
			document.getElementById("week").value=week+"周";

		}

	</script>

</head>
<body>

	<form:form id="inputForm" modelAttribute="affairWorkRecord" action="${ctx}/affair/affairWorkRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<br>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairWorkRecord.unitId}" labelName="unit" labelValue="${affairWorkRecord.unit}"
								title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职务：</label>
			<div class="controls">
				<form:input path="postNape" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" onclick="getWeek(dt)">开始时间：</label>
			<div class="controls">
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${affairWorkRecord.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" onchange="fun(this)"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<input name="overDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${affairWorkRecord.overDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
        <div class="control-group">
			<label class="control-label">年：</label>
			<div class="controls">

				<form:input path="years" htmlEscape="false" class="input-xlarge " id="years"/>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">周：</label>
			<div class="controls">
				<form:input path="weeks" htmlEscape="false" class="input-xlarge " id="week"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">岗位工作完成情况：</label>
			<div class="controls">
				<form:textarea path="jobCompletionCondition" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">单位负责人意见：</label>
			<div class="controls">
				<form:textarea path="unitPrincipalOpinion" htmlEscape="false" class="input-xxlarge "/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">附件：</label>

			<div class="controls">
				<form:hidden id="appendfile" path="appendfile" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="appendfile" type="files" uploadPath="affair/affairWorkRecord"
								 selectMultiple="true"/>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="affair:affairWorkRecord:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
    <script>
        if ("success" == "${saveResult}") {
            parent.$.jBox.close();
        }
	</script>
</body>

</html>