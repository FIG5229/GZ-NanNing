<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>三会一课管理</title>
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
		
		function absentPersonCount() {
			let attend = $("#attendantsId").val();
			console.log(attend)
			$.get("${ctx}/affair/affairThreeOne/absentPerson?attendIds="+attend,function (data) {
				let result = data.result;
				$("#absentPersonId").val(result.absentPersonId);
				$("#absentPersonName").val(result.absentPersonName);
			});
		}
		
	</script>
</head>
<body>
<br>
	<form:form id="inputForm" modelAttribute="affairThreeOne" action="${ctx}/affair/affairThreeOne/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">会议名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会议类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_huiyi')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">团组织：</label>
			<div class="controls">
				<sys:treeselect id="organization" name="organizationId" value="${affairThreeOne.organizationId}" labelName="organization" labelValue="${affairThreeOne.organization}"
					title="团组织" url="/affair/affairTwBase/treeData" cssClass="required" allowClear="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会议时间：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairThreeOne.date}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年度：</label>
			<div class="controls">
				<form:input path="niandu" htmlEscape="false" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
<%--		<div class="control-group">--%>
<%--			<label class="control-label">年度：</label>--%>
<%--			<div class="controls">--%>
<%--				<input name="niandu" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"--%>
<%--					   value="<fmt:formatDate value="${affairThreeOne.niandu}" pattern="yyyy"/>"--%>
<%--					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>--%>
<%--				<span class="help-inline"><font color="red">*</font> </span>--%>
<%--			</div>--%>
<%--		</div>--%>
		<div class="control-group">
			<label class="control-label">会议地点：</label>
			<div class="controls">
				<form:input path="place" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主持人：</label>
			<div class="controls">
				<form:input path="host" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">记录人：</label>
			<div class="controls">
				<form:input path="recorder" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">应到人数：</label>
			<div class="controls">
				<form:input path="ydNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实到人数：</label>
			<div class="controls">
				<form:input path="sdNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">出席人员：</label>
			<div class="controls">
			<%--添加 examTreeType 属性，标识不同的树类型，修改请求用户的接口--%>
				<c:choose>
					<c:when test="${fns:getUser().id == '45e43fc5465d4dbf849c6ec50609ecf4'}">
						<sys:treeselect id="attendants" name="attendantsId" value="${affairThreeOne.attendantsId}" labelName="attendants" labelValue="${affairThreeOne.attendants}" examTreeType="tym"
										title="用户" url="/affair/affairTwBase/treeData?examTreeType=tym&type=3&flag=tym" cssClass="input-small" allowClear="true" notAllowSelectParent="true" checked="true" />
					</c:when>
					<c:otherwise>
						<sys:treeselect id="attendants" name="attendantsId" value="${affairThreeOne.attendantsId}" labelName="attendants" labelValue="${affairThreeOne.attendants}" examTreeType="tym" onchange="absentPersonCount()"
										title="用户" url="/affair/affairTwBase/treeData?examTreeType=tym&type=3&flag=tym" cssClass="input-small" allowClear="true" notAllowSelectParent="true" checked="true" />
					</c:otherwise>
				</c:choose>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">缺席人员：</label>
			<div class="controls">
<%--				<form:input path="absentPerson" htmlEscape="false" class="input-xlarge"/>--%>
				<sys:treeselect id="absentPerson" name="defaulterId" value="${affairThreeOne.defaulterId}" labelName="absentPerson" labelValue="${affairThreeOne.absentPerson}" examTreeType="tym"
								title="用户" url="/affair/affairTwBase/treeData?examTreeType=tym&type=3&flag=tym" cssClass="input-small" allowClear="true" notAllowSelectParent="true" checked="true"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">会议内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	--%>
		<%--和会议内容有啥区别吗？？--%>
		<div class="control-group">
			<label class="control-label">会议议程：</label>
			<div class="controls">
				<form:textarea path="agenda" htmlEscape="false" rows="4" class="input-xxlarge"/>
<%--				<span class="help-inline"><font color="red">*</font> </span>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关文件：</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				<sys:webuploader input="filePath" type="files" uploadPath="affair/affairThreeOne" selectMultiple="true"/>
			</div>
			<div class="controls" style="color: red">注意：需审核上传再使用或有要求时才上传</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="affair:affairThreeOne:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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