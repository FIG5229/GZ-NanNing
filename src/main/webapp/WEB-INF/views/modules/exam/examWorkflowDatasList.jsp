<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考评数据表管理</title>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/exam/examWorkflowDatas/">考评数据表列表</a></li>
		<shiro:hasPermission name="exam:examWorkflowDatas:edit"><li><a href="${ctx}/exam/examWorkflowDatas/form">考评数据表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="examWorkflowDatas" action="${ctx}/exam/examWorkflowDatas/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>考评流程ID：</label>
				<form:input path="workflowId" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>被考评对象ID：</label>
				<form:input path="objId" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>填报人：</label>
				<form:input path="fillPerson" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>初步审核人：</label>
				<form:input path="examPerson" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>部门签字负责人：</label>
				<form:input path="departSign" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>分管局签字领导：</label>
				<form:input path="partBureausSign" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>绩效调整人：</label>
				<form:input path="adjustPerson" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>局主管领导：</label>
				<form:input path="mainBureausSign" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>说明</th>
				<th>值</th>
				<th>附件路径</th>
				<th>更新时间</th>
				<th>考评流程ID</th>
				<th>被考评对象ID</th>
				<th>填报人</th>
				<th>初步审核人</th>
				<th>部门签字负责人</th>
				<th>分管局签字领导</th>
				<th>绩效调整人</th>
				<th>局主管领导</th>
				<shiro:hasPermission name="exam:examWorkflowDatas:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examWorkflowDatas">
			<tr>
				<td><a href="${ctx}/exam/examWorkflowDatas/form?id=${examWorkflowDatas.id}">
					${examWorkflowDatas.detail}
				</a></td>
				<td>
					${examWorkflowDatas.value}
				</td>
				<td>
					${examWorkflowDatas.path}
				</td>
				<td>
					<fmt:formatDate value="${examWorkflowDatas.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${examWorkflowDatas.workflowId}
				</td>
				<td>
					${examWorkflowDatas.objId}
				</td>
				<td>
					${examWorkflowDatas.fillPerson}
				</td>
				<td>
					${examWorkflowDatas.examPerson}
				</td>
				<td>
					${examWorkflowDatas.departSign}
				</td>
				<td>
					${examWorkflowDatas.partBureausSign}
				</td>
				<td>
					${examWorkflowDatas.adjustPerson}
				</td>
				<td>
					${examWorkflowDatas.mainBureausSign}
				</td>
				<shiro:hasPermission name="exam:examWorkflowDatas:edit"><td>
    				<a href="${ctx}/exam/examWorkflowDatas/form?id=${examWorkflowDatas.id}">修改</a>
					<a href="${ctx}/exam/examWorkflowDatas/delete?id=${examWorkflowDatas.id}" onclick="return confirmx('确认要删除该考评数据表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>