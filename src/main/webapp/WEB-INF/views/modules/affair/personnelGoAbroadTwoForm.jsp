<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>领导干部出国管理表管理</title>
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
	<form:form id="inputForm" modelAttribute="personnelGoAbroadTwo" action="${ctx}/affair/personnelGoAbroadTwo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<c:choose>
			<c:when test="${personnelGoAbroadTwo.idNumber == null}">
				<div class="control-group">
					<label class="control-label">身份证号：</label>
					<div class="controls">
						<form:input path="idNumber" htmlEscape="false" class="input-xlarge required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelGoAbroadTwo.idNumber}"/>
			</c:otherwise>
		</c:choose>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出国境时单位及职务：</label>
			<div class="controls">
				<form:input path="unitJob" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出国境身份：</label>
			<div class="controls">
				<form:input path="identity" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出国境性质：</label>
			<div class="controls">
				<form:input path="character" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出国事由：</label>
			<div class="controls">
				<form:textarea path="reason" htmlEscape="false" rows="4" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">持有护照（通行证）上交日期：</label>
			<div class="controls">
				<input name="handinDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${personnelGoAbroadTwo.handinDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">持有护照（通行证）领用日期：</label>
			<div class="controls">
				<input name="receiveDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${personnelGoAbroadTwo.receiveDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出国（境）日期：</label>
			<div class="controls">
				<input name="goAbroadDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${personnelGoAbroadTwo.goAbroadDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回国日期：</label>
			<div class="controls">
				<input name="returnDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${personnelGoAbroadTwo.returnDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所至国别(地区)：</label>
			<div class="controls">
				<form:input path="toPlace" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出国（境）团组名称：</label>
			<div class="controls">
				<form:input path="groupName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出国（境）审批单位名称：</label>
			<div class="controls">
				<form:input path="approvalUnitName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出国（境）审批单位代码：</label>
			<div class="controls">
				<form:input path="approvalUnitCode" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出国（境）审批文号：</label>
			<div class="controls">
				<form:input path="approvalFileNo" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">派出单位：</label>
			<div class="controls">
				<sys:treeselect id="assignUnit" name="assignUnitCode" value="${personnelGoAbroad.assignUnitCode}" labelName="assignUnit" labelValue="${personnelGoAbroad.assignUnit}"
					title="派出单位" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">所至单位：</label>
			<div class="controls">
				<form:input path="assignUnit" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">派出单位代码：</label>
			<div class="controls">
				<form:input path="assignUnitCode" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所至单位：</label>
			<div class="controls">
				<form:input path="toUnit" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出国（境）经费来源：</label>
			<div class="controls">
				<form:input path="fundsSource" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">本次使用护照（通行证）编号：</label>
			<div class="controls">
				<form:input path="passportNo" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
                <form:hidden id="annex" path="annex" htmlEscape="false" maxlength="255" class="input-xlarge"/>
                    <sys:webuploader input="annex" type="files" uploadPath="/affair/personnelGoAbroadTwo" selectMultiple="true"/>
            </div>
		</div>

		<div class="control-group">
			<label class="control-label">本次组织部门审查意见：</label>
			<div class="controls">
				<form:textarea path="suggestion" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出国（境）备注：</label>
			<div class="controls">
				<form:textarea path="beizhu" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:personnelGoAbroad:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
		</div>
	</form:form>
	<script>
		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</body>
</html>