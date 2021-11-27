<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>制度建设管理</title>
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
        }

		function openAddEditDialog(id) {
			var url="${ctx}/affair/affairSystemConstruction/form";
			if (id != null || id != undefined) {
				url=url+"?id="+id;
			}
			top.$.jBox.open("iframe:"+url, "制度审核",800,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairSystemConstruction/list?treeId=${treeId}&parentId=${parentId}"}
			});
		}
		function openDetailDialog(id) {

			top.$.jBox.open("iframe:${ctx}/affair/affairSystemConstruction/formDetail?id="+id, "制度建设详情",800,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//审核页面
		function openExamineDialog(id) {
			var url = "iframe:${ctx}/affair/affairSystemConstruction/examineView?id="+id+"&treeId=${treeId}";
			top.$.jBox.open(url, "制度建设审核",1000,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<c:choose>
			<c:when test="${treeId == '1' || treeId == '2' || treeId == '34' || treeId ==  '95' || treeId == '156'}">
				<li><a href="${ctx}/affair/affairWorkLead/list?treeId=${treeId}">主体责任落实</a></li>
				<li	><a href="${ctx}/affair/affairMemberPartyCommittee/list?treeId=${treeId}">党委委员</a>
				<li><a href="${ctx}/affair/affairElection/list?treeId=${treeId}">换届选举</a></li>
				<shiro:hasPermission name="affair:affairLifeMeet:view"><li><a href="${ctx}/affair/affairLifeMeet/list?treeId=${treeId}">民主生活会</a></li></shiro:hasPermission>
				<li><a href="${ctx}/affair/affairAssess/list?treeId=${treeId}">党委书记述职测评</a></li>
				<li><a href="${ctx}/affair/affairOrgRewardPunish/list?treeId=${treeId}">组织奖惩信息</a></li>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${parentId.length()>10}">
						<shiro:hasPermission name="affair:affairPoliticalGroup:view"><li><a href="${ctx}/affair/affairPoliticalGroup/list?treeId=${treeId}&parentId=${parentId}">党小组</a></li></shiro:hasPermission>
					</c:when>
					<c:otherwise>
						<shiro:hasPermission name="affair:affairGeneralSituation:view"><li><a href="${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}">党组织</a></li></shiro:hasPermission>
					</c:otherwise>
				</c:choose>
				<%--					<li><a href="${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}&parentId=${parentId}">党组织</a></li>--%>
				<shiro:hasPermission name="affair:affairMonthStudy:view"><li><a href="${ctx}/affair/affairMonthStudy/list?treeId=${treeId}&parentId=${parentId}">两学一做</a></li></shiro:hasPermission>
				<shiro:hasPermission name="affair:affairYearThreeOnePlan:view"><li><a href="${ctx}/affair/affairYearThreeOnePlan/list?treeId=${treeId}&parentId=${parentId}">三会一课</a></li></shiro:hasPermission>
				<shiro:hasPermission name="affair:affairCreateBranch:view"><li><a href="${ctx}/affair/affairCreateBranch/list?treeId=${treeId}&parentId=${parentId}">活动载体</a></li></shiro:hasPermission>
				<c:choose>
					<c:when test="${treeId == '51a154399faf457f8e1ef12136cf1260' || treeId == '8e66c12b4d3a4fc3ac780c3b44cb1078'}">
						<shiro:hasPermission name="affair:affairAssess:view"><li><a href="${ctx}/affair/affairAssess/list?treeId=${treeId}&parentId=${parentId}">党总支书记述职测评</a></li></shiro:hasPermission>
					</c:when>
					<c:otherwise>
						<shiro:hasPermission name="affair:affairAssess:view"><li><a href="${ctx}/affair/affairAssess/list?treeId=${treeId}&parentId=${parentId}">党支部书记述职测评</a></li></shiro:hasPermission>
					</c:otherwise>
				</c:choose>
				<shiro:hasPermission name="affair:affairLifeMeet:view"><li><a href="${ctx}/affair/affairLifeMeet/list?treeId=${treeId}&parentId=${parentId}">组织生活会</a></li></shiro:hasPermission>
				<shiro:hasPermission name="affair:affairComment:view"><li><a href="${ctx}/affair/affairComment/list?treeId=${treeId}&parentId=${parentId}">民主评议</a></li>	</shiro:hasPermission>
				<shiro:hasPermission name="affair:affairElection:view"><li><a href="${ctx}/affair/affairElection/list?treeId=${treeId}&parentId=${parentId}">换届选举</a></li>	</shiro:hasPermission>
				<shiro:hasPermission name="affair:affairIdeaAnalysis:view"><li><a href="${ctx}/affair/affairIdeaAnalysis/list?treeId=${treeId}&parentId=${parentId}">党员队伍思想分析</a></li></shiro:hasPermission>
				<%--1:专报简报 2：调研文章--%>
				<%--<li><a href="${ctx}/affair/affairResearchArticle?flag=1&treeId=${treeId}">专报简报</a></li>
                <li><a href="${ctx}/affair/affairResearchArticle?flag=2&treeId=${treeId}">调研文章</a></li>--%>
				<shiro:hasPermission name="affair:affairOrgRewardPunish:view"><li><a href="${ctx}/affair/affairOrgRewardPunish/list?treeId=${treeId}">组织奖惩信息</a></li>	</shiro:hasPermission>
				<shiro:hasPermission name="affair:affairWorkDoneManage:view"><li><a href="${ctx}/affair/affairWorkDoneManage/list?treeId=${treeId}">工作总结</a></li>	</shiro:hasPermission>
				<shiro:hasPermission name="affair:affairSystemConstruction:view">
					<li class="active"><a href="${ctx}/affair/affairSystemConstruction/list?treeId=${treeId}&parentId=${parentId}">制度建设</a></li>
				</shiro:hasPermission>
			</c:otherwise>
		</c:choose>
	</ul>

	<form:form id="searchForm" modelAttribute="affairSystemConstruction" action="${ctx}/affair/affairSystemConstruction/list?treeId=${treeId}&parentId=${parentId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairSystemConstruction:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()" value="添加"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
										onclick="deleteByIds('${ctx}/affair/affairSystemConstruction/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
				<%--<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>--%>
				<%--<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>--%>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairSystemConstruction/list?treeId=${treeId}'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>标题</th>
				<th>党组织</th>
				<th>内容</th>
				<th>审核状态</th>
				<shiro:hasPermission name="affair:affairSystemConstruction:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairSystemConstruction" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairSystemConstruction.id}"/></td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td>
					${affairSystemConstruction.title}
				</td>
				<td>
					${affairSystemConstruction.partyOrganization}
				</td>
				<td>
					${affairSystemConstruction.content}
				</td>
				<td>
						${fns:getDictLabel(affairSystemConstruction.examineStatus, 'affair_party_report_status', '')}
				</td>
				<td class="handleTd">
						<a href="javascript:" onclick="openDetailDialog('${affairSystemConstruction.id}')">查看</a>
					<shiro:hasPermission name="affair:affairSystemConstruction:edit">
						<a href="javascript:" onclick="openAddEditDialog('${affairSystemConstruction.id}')">修改</a>
						<a href="${ctx}/affair/affairSystemConstruction/delete?id=${affairSystemConstruction.id}" onclick="return confirmx('确认要删除该制度建设吗？', this.href)">删除</a>
						<c:choose>
							<%--只有创建人能上报  并且是非 同意状态下--%>
							<c:when test="${affairSystemConstruction.createBy.id == fns:getUser().id}">
								<c:if test="${affairSystemConstruction.examineStatus eq '1'}">
									<a href="${ctx}/affair/affairSystemConstruction/report?id=${affairSystemConstruction.id}&treeId=${treeId}"
									   onclick="return confirmx('确认要上报该制度建设吗？', this.href)">上报</a>
								</c:if>
							</c:when>
							<c:when test="${fns:getUser().id eq '6eb08ded66314165a692f76d8f660abb'|| fns:getUser().id eq '04f9b5355e054b40b7dc7e2a202dc0c3' ||
						 fns:getUser().id eq '8c95559336ab47c688e78b9d4bd04984' || fns:getUser().id eq '630ee4f59967404488d3a3f1fdec8381' || fns:getUser().id eq 'b016b34a09bd45349b3e1ecc5fde9c5d'||
						  fns:getUser().id eq 'b5395bf9f65f4c93a47e37e4a2a5e1f3' || fns:getUser().id eq '0294b5bc43d44b71a2c952f2212e5c57' || fns:getUser().id eq 'a699d2e49a3443739e451e294fb66410' ||
						  fns:getUser().id eq '6502f47ca7bf45539a848135ee3c6bc3' || fns:getUser().id eq 'fcaf9421273643d587baaa88735fe661' || fns:getUser().id eq 'ff371386d33a407b9c1e652b613de2bc'}">
								<c:if test="${affairSystemConstruction.examineStatus != '1'}">
									<a href="javascript:" onclick="openExamineDialog('${affairSystemConstruction.id}')">审核</a>
								</c:if>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>