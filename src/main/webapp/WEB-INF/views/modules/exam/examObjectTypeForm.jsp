<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>被考评对象类别关系表管理</title>
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
			var examType = $("#examType").val();
			if(examType=='3'){
				$("#isGt_div").show()
			}else{
				$("#isGt_div").hide()
			}
		});
		function showIsGtDiv(d) {
			if(d.value == '3'){
				$("#isGt_div").show()
			}else{
				$("#isGt_div").hide()
			}
		}
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li><a href="${ctx}/exam/examObjectType/">被考评对象类别关系表列表</a></li>
		<li class="active"><a href="${ctx}/exam/examObjectType/form?id=${examObjectType.id}">被考评对象类别关系表<shiro:hasPermission name="exam:examObjectType:edit">${not empty examObjectType.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="exam:examObjectType:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="examObjectType" action="${ctx}/exam/examObjectType/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">类别名称：</label>
			<div class="controls">
				<form:input path="typeName" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考评类别：</label>
			<div class="controls">
				<form:select path="examType" class="input-xlarge " onchange="showIsGtDiv(this)">
					<form:option value="" label=""/>
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('kp_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考评所属处/局：</label>
			<div class="controls" id="unitType">
				<form:select path="juChuId" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:option value="1" label="南宁局"/>
					<form:option value="34" label="南宁处"/>
					<form:option value="95" label="柳州处"/>
					<form:option value="156" label="北海处"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">被考评对象：</label>
			<div class="controls">
				<sys:treeselect id="objectUserId" name="objectUserId" value="${examObjectType.objectUserId}" labelName="objectUserName" labelValue="${examObjectType.objectUserName}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true" checked="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="isGt_div">
			<label class="control-label">高铁所/普铁所：</label>
			<div class="controls">
				<form:select path="isGt" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:option value="0" label="普铁/既有线"/>
					<form:option value="1" label="高铁"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="exam:examObjectType:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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