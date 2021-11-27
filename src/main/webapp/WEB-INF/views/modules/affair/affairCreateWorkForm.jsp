<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>创建工作管理</title>
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
	<%--<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairCreateWork/">创建工作列表</a></li>
		<li class="active"><a href="${ctx}/affair/affairCreateWork/form?id=${affairCreateWork.id}">创建工作<shiro:hasPermission name="affair:affairCreateWork:edit">${not empty affairCreateWork.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairCreateWork:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairCreateWork" action="${ctx}/affair/affairCreateWork/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">测评项目：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairCreateWork.unitId}" labelName="unit" labelValue="${affairCreateWork.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息" isAll="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">层级：</label>
			<div class="controls">
				<form:select path="level" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('evaluation_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年度：</label>
			<div class="controls">
				<input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="${affairCreateWork.year}"
					onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</div>

		</div>
		<div class="control-group">
			<label class="control-label">测评标准：</label>
			<div class="controls">
				<form:textarea path="standard" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">测评方法：</label>
			<div class="controls">
				<form:select path="method" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('evaluation_method')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">测评分值：</label>
			<div class="controls">
				<form:input path="score" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">测评内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开展工作情况：</label>
			<div class="controls">
				<form:textarea path="workingConditions" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filePath" type="files" uploadPath="affair/affairCreateWork" selectMultiple="true"/>
			</div>
		</div>
<%--		<div class="control-group">
			<label class="control-label">整改意见：</label>
			<div class="controls">
				<form:input path="shOpinion" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
	<%--	<div class="control-group">
			<label class="control-label">自评分：</label>
			<div class="controls">
				<form:input path="selfRating" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考核分：</label>
			<div class="controls">
				<form:input path="assessmentScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairCreateWork:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
		</div>
	</form:form>
	<script>
		if ("success" ==  "${saveResult}"){
			parent.$.jBox.tip("保存成功");
			parent.$.jBox.close();
		}
	</script>
</body>
</html>