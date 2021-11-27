<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党委中心组学习管理</title>
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
			//处分种类改变时触发事件，联动子选项
			$("#method").change(function(){
				showAndHide();
			});
			//控制处分种类子选项下拉框的隐藏与显示
			function showAndHide(){
				if($("#method").val() == '1' || $("#method").val() == '2' || $("#method").val() == '3' || $("#method").val() == '5'){
					$('#otherMethod').css('display', 'none');
				}else if($("#method").val() == '4'){
					$('#otherMethod').css('display', 'inline-block');
				}
			}
			showAndHide();
		});
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="affairGroupStudy" action="${ctx}/affair/affairGroupStudy/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）--%>
		<c:choose>
			<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
				<div class="control-group">
					<label class="control-label">单位：</label>
					<div class="controls">
						<sys:treeselect id="organization" name="organizationId" value="${affairGroupStudy.organizationId}" labelName="organization" labelValue="${affairGroupStudy.organization}"
                                title="所属组织" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true"  dataMsgRequired="必填信息" isAll="true"/>
                	<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="control-group">
					<label class="control-label">单位：</label>
					<div class="controls">
						<sys:treeselect id="organization" name="organizationId" value="${affairGroupStudy.organizationId}" labelName="organization" labelValue="${affairGroupStudy.organization}"
                                title="所属组织" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true"  dataMsgRequired="必填信息"/>
                		<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
       <%-- <div class="control-group">
            <label class="control-label">单位：</label>
            <div class="controls">
                <sys:treeselect id="organization" name="organizationId" value="${affairGroupStudy.organizationId}" labelName="organization" labelValue="${affairGroupStudy.organization}"
                                title="所属组织" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true"  dataMsgRequired="必填信息"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>--%>
		<div class="control-group">
			<label class="control-label">学习内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学习时间：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="<fmt:formatDate value="${affairGroupStudy.date}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学习形式：</label>
			<div class="controls">
				<form:select id="method" path="method" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_study_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
				<form:input id="otherMethod" path="otherMethod" htmlEscape="false" class="input-xlarge" cssStyle="display: none;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学习地点：</label>
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
			<label class="control-label">中心发言人员：</label>
			<div class="controls">
				<form:input path="zxPerson" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">补充发言人员：</label>
			<div class="controls">
				<form:input path="bcPerson" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">记录人：</label>
			<div class="controls">
				<form:input path="recorder" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参加人员：</label>
			<div class="controls">
				<form:input path="joinPerson" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关文件：</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filePath" type="files" uploadPath="affair/affairGroupStudy" selectMultiple="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairGroupStudy:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
	<script>
		if("sucess"=="${saveResult}"){
			parent.$.jBox.tip("保存成功");
			parent.$.jBox.close();
		}
	</script>
</body>
</html>