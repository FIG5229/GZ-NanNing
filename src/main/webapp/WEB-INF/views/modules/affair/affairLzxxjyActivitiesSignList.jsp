<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>签收状态</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

		});

	</script>
</head>
<body>

<sys:message content="${message}"/>
<div class="control-group">
	<div id="sign">
		<label class="control-label" style="margin-left: 40px;">已签收：</label>
		<div class="controls" style="margin-right: 30px;height: 120px;overflow-y:auto">
			<table id="contentTable" class="table table-striped table-bordered table-condensed" style="margin-left: 37px;width: 635px;">
				<thead>
				<tr>
					<th width="10%">序号</th>
					<th width="20%">单位名称</th>
					<th width="25%">签收时间</th>
				</tr>
				</thead>
				<tbody id="affairTeamDisciplineSign">
				<c:forEach items="${signList}" var="affairLzxxjyActivitiesSign" varStatus="status">
					<tr>
						<td>
								${(page.pageNo-1)*page.pageSize+status.index+1}
						</td>
						<td>
								${affairLzxxjyActivitiesSign.unit}
						</td>
						<td>
							<fmt:formatDate value="${affairLzxxjyActivitiesSign.date}" pattern="yyyy-MM-dd HH:ss:mm"/>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div id="notSign">
		<label class="control-label" style="margin-left: 40px;">未签收：</label>
		<%--11.13  隐藏催办功能 kevin.jia--%>
		<%--<input type="button" class="btn btn-primary" value="一键催办" onclick="window.location.href='${ctx}/affair/affairLzxxjyActivitiesSign/oneKeyUrge?noticeId=${noticeId}'">--%>
		<div class="controls" style="margin-right: 30px;height: 130px;overflow-y:auto">
			<table id="contentTable2" class="table table-striped table-bordered table-condensed" style="margin-left: 37px;;width: 635px;">
				<thead>
				<tr>
					<th width="10%">序号</th>
					<th width="20%">单位名称</th>
					<%--11.13  隐藏催办功能 kevin.jia--%>
					<%--<th width="25%" colspan="2">催办</th>--%>
				</tr>
				</thead>
				<tbody id="affairFileNoticeSignList2">
				<c:forEach items="${notSignList}" var="affairLzxxjyActivitiesSign" varStatus="status">
					<tr>
						<td>
								${(page.pageNo-1)*page.pageSize+status.index+1}
						</td>
						<td>
								${affairLzxxjyActivitiesSign.unit}
						</td>
							<%--11.13  隐藏催办功能 kevin.jia--%>
						<%--<td>
								${affairLzxxjyActivitiesSign.urge}
						</td>
						<td>
							<button><a href="${ctx}/affair/affairLzxxjyActivitiesSign/urge?id=${affairLzxxjyActivitiesSign.id}">催办</a></button>
						</td>--%>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
</body>
</html>