<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>抚恤申报明细</title>
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
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairCasualtyReport/formDetail?id="+id;
			top.$.jBox.open(url, "抚恤申报详情",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="affairCasualtyReport" action="${ctx}/affair/affairCasualtyReport/mingXi" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="affirmDepId" name="affirmDepId" type="hidden" value="${affairCasualtyReport.affirmDepId}"/>
		<input id="type" name="type" type="hidden" value="${affairCasualtyReport.type}"/>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>姓名</th>
				<th>种类与性质</th>
				<th>部门与警种</th>
				<th>伤亡时间</th>
				<th>部门名称</th>
				<th>认定部门</th>
				<th>认定时间</th>
				<th>复核部门</th>
				<th>牺牲/病故证明书编号</th>
				<th>负伤程度</th>
				<th>审核状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairCasualtyReport" varStatus="status">
			<tr>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td>
					<a onclick="openDetailDialog('${affairCasualtyReport.id}')">
						${affairCasualtyReport.name}
					</a>
				</td>
				<td>
					${fns:getDictLabel(affairCasualtyReport.type, 'affair_casualty_type', '')}
				</td>
				<td>
					${affairCasualtyReport.depPolice}
				</td>
				<td>
					<fmt:formatDate value="${affairCasualtyReport.casualtyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairCasualtyReport.affirmDep}
				</td>
				<td>
					${affairCasualtyReport.depName}
				</td>
				<td>
					<fmt:formatDate value="${affairCasualtyReport.affirmDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairCasualtyReport.checkDep}
				</td>
				<td>
					${affairCasualtyReport.certificateNo1}
				</td>
				<td>
					${affairCasualtyReport.injuryDegree}
				</td>
				<td>
					<c:choose>
						<c:when test="${affairCasualtyReport.shStatus == '2'}">
							<a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
							   data-content="${affairCasualtyReport.opinion}"  style="cursor: pointer;color: red">未通过</a>
						</c:when>
						<c:otherwise>
							${fns:getDictLabel(affairCasualtyReport.shStatus, 'affair_query_shenhe', '')}
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>