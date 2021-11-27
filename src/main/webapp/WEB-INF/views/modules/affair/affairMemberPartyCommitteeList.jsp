<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党委委员管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnPrint").click(function(){
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$('#checkTh').css('display', 'none');
				$('.checkTd').css('display', 'none');
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
					afterPrint:function(){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
						$('#checkTh').css('display', 'table-cell');
						$('.checkTd').css('display', 'table-cell');
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
			var url = "iframe:${ctx}/affair/affairMemberPartyCommittee/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairMemberPartyCommittee/form";
			}
			top.$.jBox.open(url, "党委委员",900,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href = "${ctx}/affair/affairMemberPartyCommittee/list?treeId=${treeId}";
				}
			});
        }
        //详情弹窗
		function openDetailDialog(url) {
			var url = "iframe:"+url;
			top.$.jBox.open(url, "党组织换届选举",800,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }

		//审核页面
		function openExamineDialog(id) {
			var url = "iframe:${ctx}/affair/affairMemberPartyCommittee/shenHeDialog?id="+id+"&treeId=${treeId}";
			top.$.jBox.open(url, " 审核",1000,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairMemberPartyCommittee/list?treeId=${treeId}";
				}
			});
		}
    </script>
</head>
<body>
	<ul class="nav nav-tabs">
<%--		<li class="active"><a href="${ctx}/affair/affairMemberPartyCommittee/">党委委员列表</a></li>--%>
<%--		<shiro:hasPermission name="affair:affairMemberPartyCommittee:edit"><li><a href="${ctx}/affair/affairMemberPartyCommittee/form">党委委员添加</a></li></shiro:hasPermission>--%>
	<c:choose>
		<c:when test="${treeId == '1' || treeId == '2'  || treeId == '34' || treeId ==  '95' || treeId == '156'}">
			<li><a href="${ctx}/affair/affairWorkLead/list?treeId=${treeId}">主体责任落实</a></li>
			<li><a href="${ctx}/affair/affairElection/list?treeId=${treeId}">换届选举</a></li>
			<c:if test="${treeId != '2'}">
				<shiro:hasPermission name="affair:affairLifeMeet:view"><li><a href="${ctx}/affair/affairLifeMeet/list?treeId=${treeId}">民主生活会</a></li></shiro:hasPermission>
			</c:if>
			<li><a href="${ctx}/affair/affairAssess/list?treeId=${treeId}">党委书记述职测评</a></li>
			<li	class="active"><a href="${ctx}/affair/affairMemberPartyCommittee/list?treeId=${treeId}">党委委员</a>
			<li><a href="${ctx}/affair/affairOrgRewardPunish/list?treeId=${treeId}">组织奖惩信息</a></li>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${parentId.length()>10}">

					<li><a href="${ctx}/affair/affairPoliticalGroup/list?treeId=${treeId}&parentId=${parentId}">党小组</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}">党组织</a></li>
				</c:otherwise>
			</c:choose>
			<%--					<li><a href="${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}">党组织</a></li>--%>
			<li><a href="${ctx}/affair/affairMonthStudy/list?treeId=${treeId}&parentId=${parentId}">两学一做</a></li>
			<li><a href="${ctx}/affair/affairYearThreeOnePlan/list?treeId=${treeId}&parentId=${parentId}">三会一课</a></li>
			<li><a href="${ctx}/affair/affairCreateBranch/list?treeId=${treeId}&parentId=${parentId}">活动载体</a></li>
			<c:choose>
				<c:when test="${treeId == '51a154399faf457f8e1ef12136cf1260' || treeId == '8e66c12b4d3a4fc3ac780c3b44cb1078'}">
					<li><a href="${ctx}/affair/affairAssess/list?treeId=${treeId}&parentId=${parentId}">党总支书记述职测评</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="${ctx}/affair/affairAssess/list?treeId=${treeId}&parentId=${parentId}">党支部书记述职测评</a></li>
				</c:otherwise>
			</c:choose>
			<shiro:hasPermission name="affair:affairLifeMeet:view"><li><a href="${ctx}/affair/affairLifeMeet/list?treeId=${treeId}&parentId=${parentId}">组织生活会</a></li></shiro:hasPermission>
			<li><a href="${ctx}/affair/affairComment/list?treeId=${treeId}&parentId=${parentId}">民主评议</a></li>
			<li class="active"><a href="${ctx}/affair/affairElection/list?treeId=${treeId}&parentId=${parentId}">换届选举</a></li>
			<shiro:hasPermission name="affair:affairIdeaAnalysis:view"><li><a href="${ctx}/affair/affairIdeaAnalysis/list?treeId=${treeId}&parentId=${parentId}">党员队伍思想分析</a></li></shiro:hasPermission>
			<%--1:专报简报 2：调研文章--%>
			<%--<li><a href="${ctx}/affair/affairResearchArticle?flag=1&treeId=${treeId}">专报简报</a></li>
            <li><a href="${ctx}/affair/affairResearchArticle?flag=2&treeId=${treeId}">调研文章</a></li>--%>
			<li><a href="${ctx}/affair/affairOrgRewardPunish/list?treeId=${treeId}&parentId=${parentId}">组织奖惩信息</a></li>
		</c:otherwise>
	</c:choose>
	</ul>

	<form:form id="searchForm" modelAttribute="affairMemberPartyCommittee" action="${ctx}/affair/affairMemberPartyCommittee/list?treeId=${treeId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>党委委员：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>党组织：</label>
				<sys:treeselect id="partyOrganization" name="partyOrganizationId" value="${affairMemberPartyCommittee.partyOrganizationId}" labelName="partyOrganization" labelValue="${affairMemberPartyCommittee.partyOrganization}"
								title="党组织名称" url="/affair/affairGeneralSituation/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</li>
		</ul>

		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairMemberPartyCommittee:edit">
			<li class="btns"><input id="btnAdd" class="btn btn-primary" type="button" value="添加" onclick="openAddEditDialog()"/></li>
			<li class="btns"><input id="btnDel" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairMemberPartyCommittee/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="btnPrint" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairMemberPartyCommittee/list?treeId=${treeId}'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh">
					<input style='margin-left:12px' type='checkbox'id='checkAll' onclick='chooseAll(this, "myCheckBox")'/>
				</th>
				<th>序号</th>
				<th>党委委员</th>
				<th>党组织</th>
				<th>性别</th>
				<th>民族</th>
				<th>年龄</th>
				<th>职务</th>
				<th>党委委员上党课情况</th>
				<th>落实联系点制度情况</th>
				<th>审核状态</th>
<%--				<th>落实党建工作责任报告</th>
落实党建工作责任报告”只保留上传附件功能即可
--%>
				<shiro:hasPermission name="affair:affairMemberPartyCommittee:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairMemberPartyCommittee" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairMemberPartyCommittee.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${affairMemberPartyCommittee.name}
				</td>
				<td>
					${affairMemberPartyCommittee.partyOrganization}
				</td>
				<td>
						${fns:getDictLabel(affairMemberPartyCommittee.sex, "sex", "")}
				</td>
				<td>
						${fns:getDictLabel(affairMemberPartyCommittee.nation, "nation", "")}
				</td>
				<td>
						${affairMemberPartyCommittee.age}
				</td>
				<td>
						${affairMemberPartyCommittee.job}
				</td>
				<td>
					${affairMemberPartyCommittee.partyClass}
				</td>
				<td>
					${affairMemberPartyCommittee.associatedPiont}
				</td>
				<td>
						${fns:getDictLabel(affairMemberPartyCommittee.status, 'affair_party_report_status', '')}
				</td>
				<%--
				<td>
				落实党建工作责任报告”只保留上传附件功能即可
					${affairMemberPartyCommittee.responsibilityReport}
				</td>--%>
				<shiro:hasPermission name="affair:affairMemberPartyCommittee:edit"><td class="handleTd">
    				<a href="javascript:" onclick="openDetailDialog('${ctx}/affair/affairMemberPartyCommittee/formDetail?id=${affairMemberPartyCommittee.id}')">查看</a>
    				<a href="javascript:" onclick="openAddEditDialog('${affairMemberPartyCommittee.id}')">修改</a>
					<a href="${ctx}/affair/affairMemberPartyCommittee/delete?id=${affairMemberPartyCommittee.id}&treeId=${treeId}" onclick="return confirmx('确认要删除该党委委员吗？', this.href)">删除</a>
					<c:choose>
						<%--只有创建人能上报  并且是非 同意状态下--%>
						<c:when test="${affairMemberPartyCommittee.createBy.id == fns:getUser().id}">
							<c:if test="${ affairMemberPartyCommittee.status eq '1'}">
								<a href="${ctx}/affair/affairMemberPartyCommittee/report?id=${affairMemberPartyCommittee.id}&treeId=${treeId}"
								   onclick="return confirmx('确认要上报吗？', this.href)">上报</a>
							</c:if>
						</c:when>
						<c:when test="${fns:getUser().id eq 'a699d2e49a3443739e451e294fb66410' ||
										fns:getUser().id eq 'b016b34a09bd45349b3e1ecc5fde9c5d' ||
						 				fns:getUser().id eq '630ee4f59967404488d3a3f1fdec8381' ||
						 				fns:getUser().id eq '8c95559336ab47c688e78b9d4bd04984' ||
						 				fns:getUser().id eq '6eb08ded66314165a692f76d8f660abb'}">
							<c:if test="${affairMemberPartyCommittee.status eq '2'}">
								<a href="javascript:" onclick="openExamineDialog('${affairMemberPartyCommittee.id}')">审核</a>
							</c:if>
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>
				</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>