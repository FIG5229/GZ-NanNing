<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考评管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}

		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "考评管理",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/exam/examWorkflow/"}
			});
		}
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<!--
		<li class="active"><a href="${ctx}/exam/examWorkflow/flowList?examType=${examType}">考评查询</a></li>-->
	<li><a href="${ctx}/exam/examWorkflow/flowList?examType=${examType}">待办</a></li>
	<li class="active"><a href="${ctx}/exam/examWorkflow/flowList?examType=${examType}&history=true">已办</a></li>
	<c:if test="${'1'.equals(examType)}">
		<shiro:hasPermission name="exam:examWorkflow:edit1"><li><a href="${ctx}/exam/examWorkflow?examType=${examType}">管理</a></li></shiro:hasPermission>
	</c:if>
	<c:if test="${'2'.equals(examType)}">
		<shiro:hasPermission name="exam:examWorkflow:edit2"><li><a href="${ctx}/exam/examWorkflow?examType=${examType}">管理</a></li></shiro:hasPermission>
	</c:if>
	<c:if test="${'3'.equals(examType)}">
		<shiro:hasPermission name="exam:examWorkflow:edit3"><li><a href="${ctx}/exam/examWorkflow?examType=${examType}">管理</a></li></shiro:hasPermission>
	</c:if>
	<c:if test="${'4'.equals(examType)}">
		<shiro:hasPermission name="exam:examWorkflow:edit4"><li><a href="${ctx}/exam/examWorkflow?examType=${examType}">管理</a></li></shiro:hasPermission>
	</c:if>
	<c:if test="${'5'.equals(examType)}">
		<shiro:hasPermission name="exam:examWorkflow:edit5"><li><a href="${ctx}/exam/examWorkflow?examType=${examType}">管理</a></li></shiro:hasPermission>
	</c:if>
	<c:if test="${'6'.equals(examType)}">
		<shiro:hasPermission name="exam:examWorkflow:edit6"><li><a href="${ctx}/exam/examWorkflow?examType=${examType}">管理</a></li></shiro:hasPermission>
	</c:if>
	<c:if test="${'7'.equals(examType)}">
		<shiro:hasPermission name="exam:examWorkflow:edit7"><li><a href="${ctx}/exam/examWorkflow?examType=${examType}">管理</a></li></shiro:hasPermission>
	</c:if>
</ul>



<form:form id="searchForm" modelAttribute="examWorkflow" action="${ctx}/exam/examWorkflow/flowList?examType=${examType}" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<ul class="ul-form">
		<li><label>名称：</label>
			<form:input path="name" htmlEscape="false" class="input-medium"/>
		</li>
			<%--
			<li><label class="width120">流程启动时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${examWorkflow.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label  class="width120">流程结束时间：</label>
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${examWorkflow.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			--%>
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
		<li class="clearfix"></li>
	</ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th>名称</th>
		<th>考评类别</th>
		<th>考评周期</th>
		<th>考评对象</th>
		<th>更新时间</th>
		<th>状态</th>
		<th>操作</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${list}" var="examWorkflow">
		<c:if test="${examWorkflow.status > examWorkflow.personStatus}">
			<c:choose>
				<c:when test="${(examWorkflow.status == 3 && examWorkflow.personStatus==1)}"></c:when>
				<c:otherwise>
					<tr>
						<td>${examWorkflow.name}</td>
						<td>
								${fns:getDictLabel(examWorkflow.exam_type, "kp_type", "")}
						</td>
						<td>
								${fns:getDictLabel(examWorkflow.exam_cycle, "cycle", "")}
						</td>
						<td>
								${fns:getDictLabel(examWorkflow.exam_object_type, "exam_object_type", "")}
						</td>
						<td>
							<fmt:formatDate value="${examWorkflow.update_date}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
								${fns:getDictLabel(examWorkflow.status, "workflow_status", "")}
						</td>
						<td>
							<c:if test="${examWorkflow.noTree == null && examWorkflow.status != '8'}">
								<a href="${ctx}/exam/examWorkflowDatas/appraise/examIndex?examWorkflowId=${examWorkflow.id}&history=true&examType=${examType}">查看</a>
							</c:if>
							<c:if test="${examWorkflow.noTree == 'true' || examWorkflow.status == '8'}">
								<a href="${ctx}/exam/examWorkflowDatas/examBeta?examWorkflowId=${examWorkflow.id}&examType=${examType}&history=true&noTree=true" >查看</a>
							</c:if>
<%--							<a href="${ctx}/exam/examWorkflow/history?id=${examWorkflow.id}">查看</a>--%>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
			<c:if test="${examType == '1'}">
				<c:if test="${fns:getUser().id == '46c521bf67e24db28772b3eac52dc454' || fns:getUser().id == '6af0e615e9b0477da82eff06ff532c8b'
						 || fns:getUser().id == '978958003ea44a4bba3eed8ee6ceff3c'}">
					<c:choose>
						<c:when test="${examType == 1}">
							<c:if test="${status == 1 || status == 3 || status == 8}">
								<a href="${ctx}/exam/examWorkflowDatas/examChuIndexBeta?examWorkflowId=${examWorkflow.id}&examType=${examType}" style="color: red">查看本处</a>
							</c:if>
						</c:when>
						<c:when test="${examType == 2}"></c:when>
						<c:otherwise>
							<a href="${ctx}/exam/examWorkflowDatas/examChuIndexBeta?examWorkflowId=${examWorkflow.id}&examType=${examType}" style="color: red">查看本处</a>
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:if>
		</c:if>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>