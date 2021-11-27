<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>廉政学习教育活动管理</title>
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
		function setStatus(status) {
			$("#status").val(status);
			/*$.post($("#inputForm").attr('action'), $("#inputForm").serialize(), function(result){
                window.parent.window.jBox.close();
            });*/
		};
	</script>
</head>
<body>
	<br/>
	<%--@elvariable id="affairLzxxjyActivities" type="com.thinkgem.jeesite.modules.affair.entity.AffairLzxxjyActivities"--%>
	<form:form id="inputForm" modelAttribute="affairLzxxjyActivities" action="${ctx}/affair/affairLzxxjyActivities/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<input id="flag" name="flag" type="hidden" value="${affairLzxxjyActivities.flag}"/>
		<%--<div class="control-group">
			<label class="control-label">发布部门：</label>
			<div class="controls">
				<sys:treeselect id="publishDep" name="publishOrgId" value="${affairLzxxjyActivities.publishOrgId}" labelName="publishDep" labelValue="${affairLzxxjyActivities.publishDep}"
								title="发布部门" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">接收部门：</label>
			<div class="controls">
				<sys:treeselect id="receiveDep" name="receiveDepId" value="${affairLzxxjyActivities.receiveDepId}" labelName="receiveDep" labelValue="${affairLzxxjyActivities.receiveDep}"
								title="接收部门" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" checked="true" notAllowSelectParent="false" dataMsgRequired="必填信息" isAll="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标题名称：</label>
			<div class="controls">
				<form:input path="eventName" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">摘要：</label>
			<div class="controls">
				<form:textarea id="summary" htmlEscape="false" path="summary" rows="10" maxlength="800" class="input-xlarge" cssStyle="width: 450px;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发布单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairLzxxjyActivities.unitId}" labelName="unit" labelValue="${affairLzxxjyActivities.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教育类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_lzjy_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发布时间：</label>
			<div class="controls">
				<input name="eventDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="<fmt:formatDate value="${affairLzxxjyActivities.eventDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="annex" path="annex" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="annex" type="files" uploadPath="affair/affairLzxxjyActivities" selectMultiple="true"/>
			</div>
		</div>
		<input id="status" name="status" type="hidden"/>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairLzxxjyActivities:manage">
				<%-- 一旦发布不可再操作--%>
				<c:if test="${affairLzxxjyActivities.status != '2'}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="setStatus(1)"/>
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="发 布" onclick="setStatus(2)"/>
				</c:if>
			</shiro:hasPermission>
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