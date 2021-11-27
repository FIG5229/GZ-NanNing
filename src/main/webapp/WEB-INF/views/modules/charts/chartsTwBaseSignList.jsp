<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>组织建设-团委人员信息</title>
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
        }
	</script>
</head>
<body>

	<form:form id="searchForm" modelAttribute="affairTwBaseSign" action="" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>姓名</th>
				<th>角色</th>
				<th>职责</th>
				<th>所在团组织</th>
				<%--<th>备注</th>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTwBaseSign" varStatus="status">
			<tr>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${affairTwBaseSign.name}
				</td>
				<td>
						${fns:getDictLabel(affairTwBaseSign.role, 'affair_twrole', '')}
				</td>
				<td>
						${affairTwBaseSign.job}
				</td>

				<td>
					${affairTwBaseSign.partyBranch}
				</td>
					<%--<td>
                        ${affairTwBaseSign.remark}
                    </td>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>