<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>自动考评配置管理</title>
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
		<li class="active"><a href="${ctx}/affair/examAutoConfig/">自动考评配置列表</a></li>
<%--		<shiro:hasPermission name="affair:examAutoConfig:edit">--%>
			<li><a href="${ctx}/affair/examAutoConfig/form">自动考评配置添加</a></li>
<%--		</shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="examAutoConfig" action="${ctx}/affair/examAutoConfig/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>类型：</label>
				<form:select path="kpType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('kp_type')}" itemLabel="label" itemValue="value"
								  htmlEscape="false"/>
				</form:select>
			</li>
			</li>
			<li><label>周期：</label>
				<form:select path="cycle" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('exam_cycle')}" itemLabel="label" itemValue="value"
								  htmlEscape="false"/>
				</form:select>
			</li>
			</li>
			<li><label>考评标准：</label>
				<form:input path="standard" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>评分标准：</label>
				<form:input path="standardOption" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>系统：</label>
				<form:select path="sys" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('from_sys')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>考核事项：</label>
				<form:input path="items" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>系统</th>
				<th>考核事项</th>
				<th>考评类别</th>
				<th>考评周期</th>
				<th>考评标准</th>
	<%--			<th>项目</th>
				<th>类型</th>
				<th>评分标准</th>
				<th>标准分</th>--%>
				<th>评分标准</th>
<%--				<shiro:hasPermission name="affair:examAutoConfig:edit">--%>
					<th>操作</th>
<%--				</shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examAutoConfig">
			<tr>
				<td>${fns:getDictLabel(examAutoConfig.sys,"from_sys" , "")}</td>
				<td>
					${examAutoConfig.items}
				</td>
				<td><a href="${ctx}/affair/examAutoConfig/form?id=${examAutoConfig.id}">
					${fns:getDictLabel(examAutoConfig.kpType, "kp_type", "")}
				</a></td>
				<td>
					${fns:getDictLabel(examAutoConfig.cycle, "cycle", "")}
				</td>
				<td>
					${examAutoConfig.standard}
				</td>
				<td>
					${examAutoConfig.standardOption}
				</td>

<%--				<shiro:hasPermission name="affair:examAutoConfig:edit">--%>
				<td>
    				<a href="${ctx}/affair/examAutoConfig/form?id=${examAutoConfig.id}">修改</a>
					<a href="${ctx}/affair/examAutoConfig/delete?id=${examAutoConfig.id}" onclick="return confirmx('确认要删除该自动考评配置吗？', this.href)">删除</a>
				</td>
<%--				</shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>