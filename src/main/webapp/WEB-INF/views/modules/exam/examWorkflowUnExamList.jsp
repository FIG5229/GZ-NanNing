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


	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>自评人</th>
				<th>初核人</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="examWorkflowDatas" varStatus="status">
			<tr>
				<td>
					${status.index+1}
				</td>
				<td>
					<c:choose>
						<c:when test="${examWorkflowDatas.fillPerson != null && examWorkflowDatas.fillPerson != ''}">
							${examWorkflowDatas.fillPerson}
						</c:when>
						<c:otherwise>
							${fns:getUserById(examWorkflowDatas.fillPersonId).name}
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					${examWorkflowDatas.examPerson}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>