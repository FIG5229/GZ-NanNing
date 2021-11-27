<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>检查法相情况录入管理</title>
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
		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		};
		function setStatus(status) {
			$("#addStatus").val(status);
		}
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="examCheck1" action="${ctx}/exam/examCheck1/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">检查时间：</label>
			<div class="controls">
				<input name="checkDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${examCheck1.checkDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">检查单位：</label>
			<div class="controls">
				<sys:treeselect id="checkUnit" name="checkUnitId" value="${examCheck1.checkUnitId}" labelName="checkUnit" labelValue="${examCheck1.checkUnit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">检查人：</label>
			<div class="controls">
				<form:input path="checkPerson" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">责任单位：</label>
			<div class="controls">
				<sys:treeselect id="dutyUnit" name="dutyUnitId" value="${examCheck1.dutyUnitId}" labelName="dutyUnit" labelValue="${examCheck1.dutyUnit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用模板：</label>
			<div class="controls">
				<form:select path="dutyUnit" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('use_model')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">选择项：</label>
			<div class="controls">
				<form:select path="chooseOptions" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('choose_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">绩效考评标准：</label>
			<div class="controls">
				<form:textarea path="testStandart" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扣分情况：</label>
			<div class="controls">
				<form:textarea path="scortSituation" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">责任领导：</label>
			<div class="controls">
				<form:input path="dutyLeader" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">责任人：</label>
			<div class="controls">
				<form:input path="dutyPerson" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">整改情况：</label>
			<div class="controls">
				<form:select path="reviewType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('exam_rectify')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="exam:examCheck1:edit">
<%--				<c:choose>--%>
<%--					<c:when test="${empty examCheck1.id}">--%>
<%--						<input class="btn btn-primary" type="submit" value="保 存" onclick="setStatus(1)"/>--%>
<%--						<input class="btn btn-primary" type="submit" value="提 交" onclick="setStatus(2)"/>--%>
<%--					</c:when>--%>
<%--					<c:otherwise>--%>
<%--						&lt;%&ndash;其他人的数据无权操作  自己的数据已提交后不能再重复保存和提交&ndash;%&gt;--%>
<%--						<c:if test="${examCheck1.createBy.id == fns:getUser().id && examCheck1.addStatus != '2'}">--%>
<%--							<input class="btn btn-primary" type="submit" value="保 存" onclick="setStatus(1)"/>--%>
<%--							<input class="btn btn-primary" type="submit" value="提 交" onclick="setStatus(2)"/>--%>
<%--						</c:if>--%>
<%--					</c:otherwise>--%>
<%--				</c:choose>--%>
				<input class="btn btn-primary" type="submit" value="保 存" onclick="setStatus(1)"/>
				<input class="btn btn-primary" type="submit" value="提 交" onclick="setStatus(2)"/>
			</shiro:hasPermission>
<%--			<shiro:hasPermission name="exam:examCheck1:edit">--%>
<%--				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>--%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>