<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党员民主评议管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
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
<form:form id="searchForm" modelAttribute="affairComment" action="${ctx}/affair/affairComment/echartGradeFindPage"
		   method="post">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<input id="flag" name="flag" type="hidden" value="${affairComment.flag}"/>
	<input id="dateType" name="dateType" type="hidden" value="${affairComment.dateType}"/>
	<input id="echartYear" name="echartYear" type="hidden" value="${affairComment.echartYear}"/>
	<input id="dateStart" name="dateStart" type="hidden" value="${affairComment.dateStart}"/>
	<input id="dateEnd" name="dateEnd" type="hidden" value="${affairComment.dateEnd}"/>
	<input id="month" name="month" type="hidden" value="${affairComment.month}"/>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th>序号</th>
		<th>党员姓名</th>
		<th>性别</th>
		<th>警号</th>
		<th>身份证号</th>
		<th>党组织名称</th>
		<th>评议年度</th>
		<th>评议时间</th>
		<th>参加评议人数</th>
		<th>评议等级</th>
		<th>评议结果</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="affairComment" varStatus="status">
		<tr>
			<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
			</td>
			<td>
					${affairComment.name}
			</td>
			<td>
					${fns:getDictLabel(affairComment.sex, 'sex', '')}
			</td>
			<td>
					${affairComment.policeNo}
			</td>
			<td>
					${affairComment.idNumber}
			</td>
			<td>
					${affairComment.partyOrganization}
			</td>
			<td>
					${affairComment.year}
			</td>
			<td>
					<fmt:formatDate value="${affairComment.date}" pattern="yyyy-MM-dd"/>
			</td>
			<td>
					${affairComment.personNum}
			</td>
			<td>
					${fns:getDictLabel(affairComment.grade, 'affair_comment_grade', '')}
			</td>
			<td>
					${affairComment.result}
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>