<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>模板项数据管理管理</title>
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
				<th>模板项目ID(optionId)</th>
				<th>数据项值</th>
				<th>行号</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="examStandardTemplateData">
			<tr>
				<td>
					${examStandardTemplateData.id}
				</td>
				<td>
					${examStandardTemplateData.itemValue}
				</td>
				<td>
						${examStandardTemplateData.rowNum}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>