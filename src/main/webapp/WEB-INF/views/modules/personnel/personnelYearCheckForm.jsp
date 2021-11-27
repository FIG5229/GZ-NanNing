<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>年度考核信息管理</title>
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
	<form:form id="inputForm" modelAttribute="personnelYearCheck" action="${ctx}/personnel/personnelYearCheck/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<c:choose>
			<c:when test="${personnelYearCheck.idNumber == null}">
				<div class="control-group">
					<label class="control-label">身份证号：</label>
					<div class="controls">
						<form:input path="idNumber" htmlEscape="false" class="input-xlarge required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelYearCheck.idNumber}"/>
			</c:otherwise>
		</c:choose>
		<div class="control-group">
			<label class="control-label">考核年度：</label>
			<div class="controls">
				<form:input path="year" htmlEscape="false" class="input-xlarge  digits required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考核结论：</label>
			<div class="controls">
				<form:input path="conclusion" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考核日期：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${personnelYearCheck.date}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考核单位名称：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${personnelYearCheck.unitId}" labelName="unit" labelValue="${personnelYearCheck.unit}"
								title="考核单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考核单位代码：</label>
			<div class="controls">
				<form:input path="unitCode" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参加考核的应到人数：</label>
			<div class="controls">
				<form:input path="shouldNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实到考核人数：</label>
			<div class="controls">
				<form:input path="realNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考核优秀得票数：</label>
			<div class="controls">
				<form:input path="yxVoteNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考核称职(合格)得票数：</label>
			<div class="controls">
				<form:input path="czVoteNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考核基本称职(基本合格)得票数：</label>
			<div class="controls">
				<form:input path="jbczVoteNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考核不称职(不合格)得票数：</label>
			<div class="controls">
				<form:input path="bczVoteNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">弃权票数：</label>
			<div class="controls">
				<form:input path="qqVoteNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考核组成员：</label>
			<div class="controls">
				<form:input path="member" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考核备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="personnel:personnelYearCheck:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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