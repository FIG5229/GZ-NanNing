<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考评流程任务分配-部门公共加分/扣分管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">

	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#baseInfoForm").validate({
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
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        };
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/exam/examWorkflowSegmentsTask/">考评流程任务分配列表</a></li>
		<shiro:hasPermission name="exam:examWorkflowSegmentsTask:edit"><li><a href="${ctx}/exam/examWorkflowSegmentsTask/form">考评流程任务分配添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="examWorkflowSegmentsTask" action="${ctx}/exam/examWorkflowSegmentsTask/" method="post" class="breadcrumb form-search">
		&lt;%&ndash;<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>&ndash;%&gt;
		<form:input path="tagType" htmlEscape="false" class="input-medium" type="hidden"/>
	</form:form>--%>
	<%--<sys:message content="${message}"/>--%>
	<sys:message content="${message}"/>
	${message}
	<form:form id="baseInfoForm" modelAttribute="examWorkflowSegmentsTask" action="${ctx}/exam/examWorkflowSegmentsTask/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input id="tagType" name="tagType" type="hidden" value="${examWorkflowSegmentsTask.tagType}"/>
		<input name="segmentId" type="hidden" value="${segmentId}"/>
		<input name="workflowId" type="hidden" value="${workflowId}"/>
		<div style="height: 365px;overflow-y: auto">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
				<tr>
					<th>工作分类</th>
					<shiro:hasPermission name="exam:examWorkflowSegmentsTask:edit"><th>分配人员</th></shiro:hasPermission>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="examWorkflowSegmentsTask" varStatus="status">
					<tr>
						<td>
							<input name="workType${examWorkflowSegmentsTask.id}" type="hidden" value="${examWorkflowSegmentsTask.workType}"/>
							<input name="segmentId${examWorkflowSegmentsTask.id}" type="hidden" value="${examWorkflowSegmentsTask.segmentId}"/>
								${examWorkflowSegmentsTask.workType}
						</td>
						<shiro:hasPermission name="exam:examWorkflowSegmentsTask:edit">
							<td>
								<input name="personIds" type="hidden" value="${examWorkflowSegmentsTask.id}"/>
								<sys:treeselect id="personNames${examWorkflowSegmentsTask.id}" name="ids${examWorkflowSegmentsTask.id}" value="${examWorkflowSegmentsTask.ids}" labelName="personNames${examWorkflowSegmentsTask.id}" labelValue="${examWorkflowSegmentsTask.personNames}"
												title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" checked="true"/>
							</td>
						</shiro:hasPermission>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="exam:examWorkflowSegmentsTask:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" style="float: right;"/>&nbsp;</shiro:hasPermission>
		</div>
	</form:form>

</body>
</html>