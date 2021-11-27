<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党员花名册管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        };
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairPartyMember/formDetail?id="+id;
			top.$.jBox.open(url, "党员名册",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="affairPartyMember" action="${ctx}/affair/affairPartyMember/echartNationFindPage" method="post">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="flag" name="flag" type="hidden" value="${affairPartyMember.flag}"/>
		<input id="pbId" name="pbId" type="hidden" value="${affairPartyMember.pbId}"/>
		<input id="year" name="year" type="hidden" value="${affairPartyMember.year}"/>
		<input id="month" name="month" type="hidden" value="${affairPartyMember.month}"/>
		<input id="dateStart" name="dateStart" type="hidden" value="${affairPartyMember.dateStart}"/>
		<input id="dateEnd" name="dateEnd" type="hidden" value="${affairPartyMember.dateEnd}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>姓名</th>
				<th>身份证号</th>
				<th>所在党支部</th>
				<th>人员类别</th>
				<th>学历</th>
				<th>加入党组织日期</th>
				<th>转为正式党员日期</th>
				<th>民族</th>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairPartyMember" varStatus="status">
			<tr>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairPartyMember.name}
				</td>
				<td>
					${affairPartyMember.cardNum}
				</td>
				<td>
					${affairPartyMember.partyBranch}
				</td>
				<td>
					${fns:getDictLabel(affairPartyMember.personnelCategory, 'affair_personnel_category', '')}
				</td>
				<td>
					${affairPartyMember.education}
				</td>
				<td>
					<fmt:formatDate value="${affairPartyMember.joinDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairPartyMember.turnDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairPartyMember.nation}
				</td>
				<td class="handleTd">
					<a onclick="openDetailDialog('${affairPartyMember.id}')">查看</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>