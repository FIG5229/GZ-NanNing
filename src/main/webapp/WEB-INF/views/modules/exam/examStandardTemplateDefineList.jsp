<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考评标准模板管理</title>
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
		<li class="active"><a href="${ctx}/exam/examStandardTemplateDefine/">考评标准模板格式列表</a></li>
		<shiro:hasPermission name="exam:examStandardTemplateDefine:edit"><li><a href="${ctx}/exam/examStandardTemplateDefine/form">考评标准模板格式添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="examStandardTemplateDefine" action="${ctx}/exam/examStandardTemplateDefine/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>考评周期：</label>
				<form:select path="cycle" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('exam_cycle')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>被考评对象类别：</label>
				<form:select path="objectCategory" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('exam_object_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>考评类别：</label>
				<form:select path="kpType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('kp_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/exam/examStandardTemplateDefine'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>考评周期</th>
				<th>被考评对象类别</th>
				<th>考评类别</th>
				<th>名称</th>
				<th>开始行号</th>
				<th>结束行号</th>
				<shiro:hasPermission name="exam:examStandardTemplateDefine:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examStandardTemplateDefine">
			<tr>
				<td>
								${fns:getDictLabel(examStandardTemplateDefine.cycle, 'exam_cycle', '')}
				</td>
				<td>
								${fns:getDictLabel(examStandardTemplateDefine.objectCategory, 'exam_object_type', '')}
				</td>
				<td>
								${fns:getDictLabel(examStandardTemplateDefine.kpType, 'kp_type', '')}
				</td>
				<td>
					${examStandardTemplateDefine.name}
				</td>
				<td>
					${examStandardTemplateDefine.startrowNum}
				</td>
				<td>
					${examStandardTemplateDefine.endrowNum}
				</td>
				<shiro:hasPermission name="exam:examStandardTemplateDefine:edit"><td>
					<c:if test="${examStandardTemplateDefine.createBy.id == fns:getUser().id}">
					<a href="${ctx}/exam/examStandardTemplateDefine/form?id=${examStandardTemplateDefine.id}">修改</a>
					<a href="${ctx}/exam/examStandardTemplateDefine/delete?id=${examStandardTemplateDefine.id}" onclick="return confirmx('确认要删除该考评标准模板吗？', this.href)">删除</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>