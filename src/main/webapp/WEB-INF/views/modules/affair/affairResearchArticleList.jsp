<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专报简报/调研文章管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#print").click(function(){
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$("#contentTable").printThis({
					debug: false,
					importCSS: false,
					importStyle: false,
					printContainer: true,
					loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css"],
					pageTitle: "打印",
					removeInline: false,
					printDelay: 333,
					header: null,
					formValues: false,
					afterPrint:function (){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        };
		//添加修改弹窗
		function openAddEditDialog(id,flag) {
			var url = "iframe:${ctx}/affair/affairResearchArticle/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairResearchArticle/form?flag="+flag;
			}
			var title = "专报简报";
			if (flag == '2'){
				title = "调研文章";
			}
			top.$.jBox.open(url, title,1000,650,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairResearchArticle/?flag="+flag;
				}
			});
		};
		//详情弹窗
		function openDetailDialog(id,flag) {
			var title = "专报简报";
			if (flag == '2'){
				title = "调研文章";
			}
			var url = "iframe:${ctx}/affair/affairResearchArticle/formDetail?id="+id;
			top.$.jBox.open(url, title,1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%--<li><a href="${ctx}/affair/affairIdeaAnalysis/">党员队伍思想状况分析</a></li>
		&lt;%&ndash;1:专报简报 2：调研文章&ndash;%&gt;
		<c:choose>
			<c:when test="${affairResearchArticle.flag == '1'}">
				<li class="active"><a href="${ctx}/affair/affairResearchArticle?flag=1">专报简报</a></li>
				<li><a href="${ctx}/affair/affairResearchArticle?flag=2">调研文章</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="${ctx}/affair/affairResearchArticle?flag=1">专报简报</a></li>
				<li class="active"><a href="${ctx}/affair/affairResearchArticle?flag=2">调研文章</a></li>
			</c:otherwise>
		</c:choose>--%>
		<li><a href="${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}">党组织</a></li>
		<li><a href="${ctx}/affair/affairMonthStudy?treeId=${treeId}">月度学习计划</a></li>
		<li><a href="${ctx}/affair/affairTwoOne?treeId=${treeId}">"两学一做"专题学习</a></li>
		<li ><a href="${ctx}/affair/affairThemeActivity/manageList?treeId=${treeId}">党内主题实践活动情况</a></li>
		<li><a href="${ctx}/affair/affairCreateBranch?treeId=${treeId}">党内创品牌活动</a></li>
		<li><a href="${ctx}/affair/affairVolunteerService?treeId=${treeId}">志愿服务活动</a></li>
		<li><a href="${ctx}/affair/affairChuangGangJianQu?treeId=${treeId}">创岗建区活动</a></li>
		<li><a href="${ctx}/affair/affairYearThreeOnePlan?treeId=${treeId}">年度“三会一课”计划</a></li>
		<li><a href="${ctx}/affair/affairYearThreeOne?treeId=${treeId}">“三会一课”录入</a></li>
		<shiro:hasPermission name="affair:affairYearThreeOnePlan:manage"><li><a href="${ctx}/affair/affairYearThreeOnePlan/manageList?treeId=${treeId}">年度“三会一课”计划审核</a></li></shiro:hasPermission>
		<shiro:hasPermission name="affair:affairYearThreeOne:manage"><li><a href="${ctx}/affair/affairYearThreeOne/manageList?treeId=${treeId}">“三会一课”录入审核</a></li></shiro:hasPermission>
		<li><a href="${ctx}/affair/affairLifeMeet?treeId=${treeId}">组织生活会</a></li>
		<shiro:hasPermission name="affair:affairLifeMeet:manage"><li><a href="${ctx}/affair/affairLifeMeet/manageList?treeId=${treeId}">民主（生活会管理</a></li></shiro:hasPermission>
		<li><a href="${ctx}/affair/affairIdeaAnalysis?treeId=${treeId}">党员队伍思想状况分析</a></li>
		<%--1:专报简报 2：调研文章--%>
		<c:choose>
			<c:when test="${affairResearchArticle.flag == '1'}">
				<li class="active"><a href="${ctx}/affair/affairResearchArticle?flag=1&treeId=${treeId}">专报简报</a></li>
				<li><a href="${ctx}/affair/affairResearchArticle?flag=2&treeId=${treeId}">调研文章</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="${ctx}/affair/affairResearchArticle?flag=1&treeId=${treeId}">专报简报</a></li>
				<li class="active"><a href="${ctx}/affair/affairResearchArticle?flag=2&treeId=${treeId}">调研文章</a></li>
			</c:otherwise>
		</c:choose>
		<li><a href="${ctx}/affair/affairAssess?treeId=${treeId}">党委书记述职测评</a></li>
		<li><a href="${ctx}/affair/affairClassMember?treeId=${treeId}">班子成员</a></li>
		<li><a href="${ctx}/affair/affairElection/list?treeId=${treeId}">党组织换届选举</a></li>
		<li><a href="${ctx}/affair/affairOrgRewardPunish?treeId=${treeId}">组织奖惩信息</a></li>
		<shiro:hasPermission name="affair:affairElection:manage"><li><a href="${ctx}/affair/affairElection/manageList?treeId=${treeId}">党组织换届选举管理</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairResearchArticle" action="${ctx}/affair/affairResearchArticle?treeId=${treeId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>所属党组织：</label>
				<sys:treeselect id="partyOrganization" name="partyOrganizationId" value="${affairResearchArticle.partyOrganizationId}" labelName="partyOrganization" labelValue="${affairResearchArticle.partyOrganization}"
					title="所属党组织" url="/affair/affairGeneralSituation/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>发布日期：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairResearchArticle.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				 --
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairResearchArticle.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<input id="flag" name="flag" type="hidden" value="${affairResearchArticle.flag}"/>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairResearchArticle:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog(null,'${affairResearchArticle.flag}')" value="添加"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairResearchArticle/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairResearchArticle?flag=${affairResearchArticle.flag}'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>
					<input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>
				</th>
				<th>序号</th>
				<th>标题</th>
				<th>所属党组织</th>
				<th>发布日期</th>
				<shiro:hasPermission name="affair:affairResearchArticle:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairResearchArticle" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairResearchArticle.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td><a onclick="openDetailDialog('${affairResearchArticle.id}','${affairResearchArticle.flag}')">
					${affairResearchArticle.title}
				</a></td>
				<td>
					${affairResearchArticle.partyOrganization}
				</td>
				<td>
					<fmt:formatDate value="${affairResearchArticle.publishDate}" pattern="yyyy-MM-dd"/>
				</td>
				<shiro:hasPermission name="affair:affairResearchArticle:edit"><td class="handleTd">
    				<a onclick="openAddEditDialog('${affairResearchArticle.id}','${affairResearchArticle.flag}')">修改</a>
					<a href="${ctx}/affair/affairResearchArticle/delete?id=${affairResearchArticle.id}" onclick="return confirmx('确认要删除该专报简报/调研文章吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>