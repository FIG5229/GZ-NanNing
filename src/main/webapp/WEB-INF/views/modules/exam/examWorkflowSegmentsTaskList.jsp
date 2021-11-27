<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考评流程任务分配管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.cntorder{
			width: auto;
			height: auto;
		}
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
		//tab页切换
		$(function () {
			$(".modal-custom-bar div").click(function () {
				var index_tab = $(this).parent().children().index($(this)); //选项卡的索引值,索引值从0开始
				//$(this).parent().find(".modal-custom-btn.red").removeClass("red"); //选项卡背景处理
				//$(this).removeClass("modal-custom-btn").addClass("modal-custom-btn red");
				$(this).parent().children().removeClass("red");
				$(this).addClass("red");
				var content_obj = $(".cntorder") //内容节点
				content_obj.hide();
				content_obj.eq(index_tab).show();
			});
		});
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/exam/examWorkflowSegmentsTask/">考评流程任务分配列表</a></li>
		<shiro:hasPermission name="exam:examWorkflowSegmentsTask:edit"><li><a href="${ctx}/exam/examWorkflowSegmentsTask/form">考评流程任务分配添加</a></li></shiro:hasPermission>
	</ul>--%>
	<%--<form:form id="searchForm" modelAttribute="examWorkflowSegmentsTask" action="${ctx}/exam/examWorkflowSegmentsTask/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:input path="tagType" htmlEscape="false" class="input-medium" type="hidden"/>
	</form:form>--%>
	<br/>
	<div class="modal-custom-bar">
		<%--<div class="modal-custom-btn red">部门业务扣分</div>
		<div class="modal-custom-btn">部门公共扣分</div>
		<div class="modal-custom-btn">部门公共加分</div>--%>
        <c:forEach items="${tabList}" var="m" varStatus="status">
            <c:choose>
                <c:when test="${status.first}">
                    <div class="modal-custom-btn red">${m.abbreviation}</div>
                </c:when>
                <c:otherwise>
                    <div class="modal-custom-btn">${m.abbreviation}</div>
                </c:otherwise>
            </c:choose>
        </c:forEach>

	</div>
	<%--部门业务扣分--%>
	<div class="cntorder" >
		<sys:message content="${message}"/>
		<form:form id="baseInfoForm" modelAttribute="examWorkflowSegmentsTask" action="${ctx}/exam/examWorkflowSegmentsTask/save" method="post" class="form-horizontal">
			<form:hidden path="id"/>
			<input id="tagType" name="tagType" type="hidden" value="${examWorkflowSegmentsTask.tagType}"/>
			<input name="workflowId" type="hidden" value="${workflowId}"/>
			<input name="segmentId" type="hidden" value="${segmentId}"/>
            <%--考核标准模版用于tag的显示--%>
            <c:forEach items="${templatesIds}" var="t">
                <input name="templatesIds" type="hidden" value="${t}"/>
            </c:forEach>
            <%--第一个tag页的索引--%>
            <input name="tagIndex" type="hidden" value="0"/>
			<div style="height: 365px;overflow-y: auto;float: left;width: 970px;">
				<table id="contentTable1" class="table table-striped table-bordered table-condensed">
					<thead>
					<tr>
						<th>工作分类</th>
<%--						<shiro:hasPermission name="exam:examWorkflowSegmentsTask:edit"><th>分配人员</th></shiro:hasPermission>--%>
						<th>分配人员</th>

					</tr>
					</thead>
					<tbody>
					<c:forEach items="${page.list}" var="examWorkflowSegmentsTask">
						<tr>
							<td>
								<input name="workType${examWorkflowSegmentsTask.id}" type="hidden" value="${examWorkflowSegmentsTask.workType}"/>
								<input name="segmentId${examWorkflowSegmentsTask.id}" type="hidden" value="${examWorkflowSegmentsTask.segmentId}"/>
									${examWorkflowSegmentsTask.workType}
							</td>
							<%--<shiro:hasPermission name="exam:examWorkflowSegmentsTask:edit">--%>
								<td>
									<input name="personIds" type="hidden" value="${examWorkflowSegmentsTask.id}"/>
									<sys:treeselect id="personNames${examWorkflowSegmentsTask.id}" name="ids${examWorkflowSegmentsTask.id}" value="${examWorkflowSegmentsTask.ids}" labelName="personNames${examWorkflowSegmentsTask.id}" labelValue="${examWorkflowSegmentsTask.personNames}"
													title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" checked="true" cssStyle="width:300px;"/>
								</td>
							<%--</shiro:hasPermission>--%>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>

			<%--<div class="pagination">${page}</div>--%>
			<div class="form-actions" style="display: block">
<%--				<shiro:hasPermission name="exam:examWorkflowSegmentsTask:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" style="float: right;"/>&nbsp;</shiro:hasPermission>--%>
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" style="float: right;"/>&nbsp;
			</div>
		</form:form>

	</div>
   <%-- 引入其他tab页内容--%>
    <c:forEach items="${tabList}" var="m" begin="1" varStatus="status">
        <div class="cntorder" style="display: none;">
            <iframe src="${ctx}/exam/examWorkflowSegmentsTask/findBytTagType?tagType=${m.id}&workflowId=${workflowId}&segmentId=${segmentId}" width="100%" height="460px;" frameborder="0"></iframe>
        </div>
    </c:forEach>
</body>
</html>