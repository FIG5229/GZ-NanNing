<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>创岗建区管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}">党组织</a></li>
		<li><a href="${ctx}/affair/affairMonthStudy/list?treeId=${treeId}">两学一做</a></li>
		<li><a href="${ctx}/affair/affairYearThreeOnePlan/list?treeId=${treeId}">三会一课</a></li>
		<li class="active"><a href="${ctx}/affair/affairCreateBranch/list?treeId=${treeId}">活动载体</a></li>
		<c:choose>
			<c:when test="${treeId == '1' || treeId == '2' || treeId == '34' || treeId ==  '95' || treeId == '156'}">
				<li><a href="${ctx}/affair/affairAssess/list?treeId=${treeId}&parentId=${parentId}">党委书记述职测评</a></li>
			</c:when>
			<c:when test="${treeId == '51a154399faf457f8e1ef12136cf1260' || treeId == '8e66c12b4d3a4fc3ac780c3b44cb1078'}">
				<li><a href="${ctx}/affair/affairAssess/list?treeId=${treeId}&parentId=${parentId}">党总支书记述职测评</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="${ctx}/affair/affairAssess/list?treeId=${treeId}&parentId=${parentId}">党支部书记述职测评</a></li>
			</c:otherwise>
		</c:choose>
		<shiro:hasPermission name="affair:affairLifeMeet:view"><li><a href="${ctx}/affair/affairLifeMeet/list?treeId=${treeId}">组织生活会</a></li></shiro:hasPermission>
		<li><a href="${ctx}/affair/affairComment/list?treeId=${treeId}">民主评议</a></li>
		<li><a href="${ctx}/affair/affairElection/list?treeId=${treeId}">换届选举</a></li>
		<shiro:hasPermission name="affair:affairIdeaAnalysis:view"><li><a href="${ctx}/affair/affairIdeaAnalysis/list?treeId=${treeId}">党员队伍思想分析</a></li></shiro:hasPermission>
		<li><a href="${ctx}/affair/affairOrgRewardPunish/list?treeId=${treeId}">组织奖惩信息</a></li>
		<li><a href="${ctx}/affair/affairWorkDoneManage/list?treeId=${treeId}">工作总结</a></li>
		<li><a href="${ctx}/affair/affairSystemConstruction/list?treeId=${treeId}&parentId=${parentId}">制度建设</a></li>
	</ul>
	<ul class="nav nav-tabs">
		<shiro:hasPermission name="affair:affairCreateBranch:view"><li><a href="${ctx}/affair/affairCreateBranch/list?treeId=${treeId}">品牌创建</a></li></shiro:hasPermission>
		<shiro:hasPermission name="affair:affairVolunteerService:view"><li><a href="${ctx}/affair/affairVolunteerService?treeId=${treeId}">志愿服务</a></li></shiro:hasPermission>
		<li class="active"><a href="${ctx}/affair/affairChuangGangJianQu?treeId=${treeId}">创岗建区</a></li>
		<shiro:hasPermission name="affair:affairPartyDayActivities:view"><li><a href="${ctx}/affair/affairPartyDayActivities/list?treeId=${treeId}">党日活动</a></li></shiro:hasPermission>
	</ul>

</body>
</html>