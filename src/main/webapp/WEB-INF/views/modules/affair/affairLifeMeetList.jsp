<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民主（组织）生活会管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("[data-toggle='popover']").popover();
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
        $('#notPass').popover();

		//添加修改弹窗
		function openAddEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairLifeMeet/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairLifeMeet/form";
			}
			top.$.jBox.open(url, "民主（组织）生活会",1000,650,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href = "${ctx}/affair/affairLifeMeet/list?treeId=${treeId}";
				}
			});
        }
        //详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairLifeMeet/formDetail?id="+id;
			top.$.jBox.open(url, "民主（组织）生活会",1200,600,{
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
		<%--<li><a href="${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}">党组织</a></li>
		<li><a href="${ctx}/affair/affairMonthStudy/list?treeId=${treeId}">两学一做</a></li>
		<li><a href="${ctx}/affair/affairYearThreeOnePlan/list?treeId=${treeId}">三会一课</a></li>
		<li><a href="${ctx}/affair/affairCreateBranch/list?treeId=${treeId}">活动载体</a></li>
		<li><a href="${ctx}/affair/affairAssess/list?treeId=${treeId}">党委书记述职测评</a></li>
		<li class="active"><a href="${ctx}/affair/affairLifeMeet/list?treeId=${treeId}">民主（组织）生活会</a></li>
		<li><a href="${ctx}/affair/affairComment/list?treeId=${treeId}">民主评议</a></li>
		<li><a href="${ctx}/affair/affairElection/list?treeId=${treeId}">换届选举</a></li>
		<li><a href="${ctx}/affair/affairIdeaAnalysis/list?treeId=${treeId}">党员队伍思想分析</a></li>
		<li><a href="${ctx}/affair/affairOrgRewardPunish/list?treeId=${treeId}">组织奖惩信息</a></li>--%>
			<c:choose>
				<c:when test="${treeId == '1' || treeId == '34' || treeId ==  '95' || treeId == '156' || treeId == '2'}">
					<li><a href="${ctx}/affair/affairWorkLead/list?treeId=${treeId}">主体责任落实</a></li>
					<li><a href="${ctx}/affair/affairElection/list?treeId=${treeId}">换届选举</a></li>
					<shiro:hasPermission name="affair:affairLifeMeet:view"><li class="active"><a href="${ctx}/affair/affairLifeMeet/list?treeId=${treeId}">民主生活会</a></li></shiro:hasPermission>
					<li><a href="${ctx}/affair/affairAssess/list?treeId=${treeId}">党委书记述职测评</a></li>
					<li	><a href="${ctx}/affair/affairMemberPartyCommittee/list?treeId=${treeId}">党委委员</a>
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
<%--					<li><a href="${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}">党组织</a></li>--%>
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
					<shiro:hasPermission name="affair:affairLifeMeet:view"><li class="active"><a href="${ctx}/affair/affairLifeMeet/list?treeId=${treeId}&parentId=${parentId}">组织生活会</a></li></shiro:hasPermission>
					<shiro:hasPermission name="affair:affairComment:view"><li><a href="${ctx}/affair/affairComment/list?treeId=${treeId}&parentId=${parentId}">民主评议</a></li></shiro:hasPermission>
					<shiro:hasPermission name="affair:affairElection:view"><li><a href="${ctx}/affair/affairElection/list?treeId=${treeId}&parentId=${parentId}">换届选举</a></li></shiro:hasPermission>
					<shiro:hasPermission name="affair:affairIdeaAnalysis:view"><li><a href="${ctx}/affair/affairIdeaAnalysis/list?treeId=${treeId}&parentId=${parentId}">党员队伍思想分析</a></li></shiro:hasPermission>
					<%--1:专报简报 2：调研文章--%>
					<%--<li><a href="${ctx}/affair/affairResearchArticle?flag=1&treeId=${treeId}">专报简报</a></li>
                    <li><a href="${ctx}/affair/affairResearchArticle?flag=2&treeId=${treeId}">调研文章</a></li>--%>
					<shiro:hasPermission name="affair:affairOrgRewardPunish:view"><li><a href="${ctx}/affair/affairOrgRewardPunish/list?treeId=${treeId}&parentId=${parentId}">组织奖惩信息</a></li>	</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairWorkDoneManage:view"><li><a href="${ctx}/affair/affairWorkDoneManage/list?treeId=${treeId}">工作总结</a></li>	</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairSystemConstruction:view">
						<li><a href="${ctx}/affair/affairSystemConstruction/list?treeId=${treeId}&parentId=${parentId}">制度建设</a></li>
					</shiro:hasPermission>
				</c:otherwise>
			</c:choose>
	</ul>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairLifeMeet/list?treeId=${treeId}">录入</a></li>
		<shiro:hasPermission name="affair:affairLifeMeet:manage">
			<li><a href="${ctx}/affair/affairLifeMeet/manageList?treeId=${treeId}">管理</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairLifeMeet"
			   action="${ctx}/affair/affairLifeMeet/list?treeId=${treeId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会议名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>党组织名称：</label>
				<sys:treeselect id="partyOrganization" name="partyOrganizationId" value="${affairLifeMeet.partyOrganizationId}" labelName="partyOrganization" labelValue="${affairLifeMeet.partyOrganization}"
					title="党组织名称" url="/affair/affairGeneralSituation/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>召开时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairLifeMeet.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				 -
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairLifeMeet.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>主持人：</label>
				<form:input path="hold" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>记录人：</label>
				<form:input path="noteTaker" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairLifeMeet:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()" value="添加"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairLifeMeet/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input class="btn btn-primary" type="button" value="重置"
									onclick="window.location.href='${ctx}/affair/affairLifeMeet/list?treeId=${treeId}'"/></li>
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
				<th>会议名称</th>
				<th>党组织名称</th>
				<th>召开时间</th>
				<th>主持人</th>
				<th>记录人</th>
				<th>审核状态</th>
				<shiro:hasPermission name="affair:affairLifeMeet:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairLifeMeet" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairLifeMeet.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>

						${affairLifeMeet.name}

				</td>
				<td>
					${affairLifeMeet.partyOrganization}
				</td>
				<td>
					<fmt:formatDate value="${affairLifeMeet.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairLifeMeet.hold}
				</td>
				<td>
					${affairLifeMeet.noteTaker}
				</td>
				<td>
					<%--2为已经提交上去--%>
					<c:if test="${affairLifeMeet.addStatus == '2'}">
						<c:choose>
							<c:when test="${affairLifeMeet.status == '2'}">
								<a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
								   data-content="${affairLifeMeet.reason}"  style="cursor: pointer;color: red">不通过</a>
							</c:when>
							<c:otherwise>
								${fns:getDictLabel(affairLifeMeet.status, 'affair_query_shenhe', '')}
							</c:otherwise>
						</c:choose>
					</c:if>
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairLifeMeet.id}')">查看</a>
				<shiro:hasPermission name="affair:affairLifeMeet:edit">

                       <%-- <c:if test="${affairLifeMeet.createBy.id == fns:getUser().id}">--%>
                            <%--其他人的数据无权操作  自己的数据已提交后不能再重复保存和提交--%>
                            <c:if test="${affairLifeMeet.addStatus != '2'}">
                                <a href="javascript:void(0)" onclick="openAddEditDialog('${affairLifeMeet.id}')">修改</a>
                            </c:if>
                            <a href="${ctx}/affair/affairLifeMeet/delete?id=${affairLifeMeet.id}&treeId=${treeId}"
                               onclick="return confirmx('确认要删除该民主（组织）生活会吗？', this.href)">删除</a>
                        <%--</c:if>--%>

				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>