<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人才库-统计分析-详情</title>
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
	<form:form id="searchForm" modelAttribute="personnelTalents" action="${ctx}/personnel/personnelTalents/specialityListDetail?specialityName=${specialityName}&unitId=${unitId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>单位</th>
				<th>姓名</th>
				<th>性别</th>
				<th>身份证号</th>
				<th>出生年月</th>
				<th>特长</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelSkill" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelSkill.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${personnelSkill.unit}
				</td>
				<td>
						${personnelSkill.name}
				</td>
				<td>
						${fns:getDictLabel(personnelSkill.sex, 'sex', '')}
				</td>
				<td>
						${personnelSkill.idNumber}
				</td>
				<%--<td>
					<fmt:formatDate value="${personnelSkill.birthday}" pattern="yyyy-MM-dd"/>
				</td>--%>
				<td>
					${personnelSkill.birthday}
				</td>
				<td>
						${personnelSkill.skill}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
