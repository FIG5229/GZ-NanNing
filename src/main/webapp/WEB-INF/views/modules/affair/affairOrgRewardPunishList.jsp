<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>组织奖惩信息</title>
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
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairOrgRewardPunish/export?treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action", "${ctx}/affair/affairOrgRewardPunish/list?treeId=${treeId}");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairOrgRewardPunish/export?flag=true&treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action", "${ctx}/affair/affairOrgRewardPunish/list?treeId=${treeId}");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairOrgRewardPunish", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText: "导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
					closed: function () {
						self.location.href = "${ctx}/affair/affairOrgRewardPunish/list?treeId=${treeId}"
					}
				});
			});


			$("#awardCategory").change(function () {
				console.log($("#awardCategory").val());
				switchType();
			});
			function switchType() {
				if ($("#awardCategory").val() == '1'){
					$("#s2id_type").css('display', 'inline-block');
					$("#s2id_cjType").css('display', 'none');
					$("#cjType").val("");
				}else if ($("#awardCategory").val() == '2'){
					$("#s2id_type").css('display', 'none');
					$("#type").val("");
					$("#s2id_cjType").css('display', 'inline-block');
				}
			}
			switchType();
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
		}
		//添加修改弹窗
		function openAddEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairOrgRewardPunish/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairOrgRewardPunish/form";
			}
			top.$.jBox.open(url, "组织奖惩信息",900,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href = "${ctx}/affair/affairOrgRewardPunish/list?treeId=${treeId}";
				}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairOrgRewardPunish/formDetail?id="+id;
			top.$.jBox.open(url, "组织奖惩信息",1000,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//推送到奖惩信息库
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "推送奖惩信息库",1000,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}, closed: function () {
					self.location.href = "${ctx}/affair/affairOrgRewardPunish/list?treeId=${treeId}"
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
			<c:choose>
				<c:when test="${treeId == '1' || treeId == '2' || treeId == '34' || treeId ==  '95' || treeId == '156'}">
					<%--<li	><a href="">主体责任落实</a>--%>
					<li><a href="${ctx}/affair/affairWorkLead/list?treeId=${treeId}">主体责任落实</a></li>
					<li><a href="${ctx}/affair/affairElection/list?treeId=${treeId}">换届选举</a></li>
					<c:if test="${treeId != '2'}">
						<shiro:hasPermission name="affair:affairLifeMeet:view"><li><a href="${ctx}/affair/affairLifeMeet/list?treeId=${treeId}">民主生活会</a></li></shiro:hasPermission>
					</c:if>
					<li><a href="${ctx}/affair/affairAssess/list?treeId=${treeId}">党委书记述职测评</a></li>
					<li	><a href="${ctx}/affair/affairMemberPartyCommittee/list?treeId=${treeId}">党委委员</a>
					<li class="active"><a href="${ctx}/affair/affairOrgRewardPunish/list?treeId=${treeId}">组织奖惩信息</a></li>
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
					<shiro:hasPermission name="affair:affairLifeMeet:view"><li><a href="${ctx}/affair/affairLifeMeet/list?treeId=${treeId}&parentId=${parentId}">组织生活会</a></li></shiro:hasPermission>
					<shiro:hasPermission name="affair:affairComment:view"><li><a href="${ctx}/affair/affairComment/list?treeId=${treeId}&parentId=${parentId}">民主评议</a></li></shiro:hasPermission>
					<shiro:hasPermission name="affair:affairElection:view"><li><a href="${ctx}/affair/affairElection/list?treeId=${treeId}&parentId=${parentId}">换届选举</a></li></shiro:hasPermission>
					<shiro:hasPermission name="affair:affairIdeaAnalysis:view"><li><a href="${ctx}/affair/affairIdeaAnalysis/list?treeId=${treeId}&parentId=${parentId}">党员队伍思想分析</a></li></shiro:hasPermission>
					<%--1:专报简报 2：调研文章--%>
					<%--<li><a href="${ctx}/affair/affairResearchArticle?flag=1&treeId=${treeId}">专报简报</a></li>
                    <li><a href="${ctx}/affair/affairResearchArticle?flag=2&treeId=${treeId}">调研文章</a></li>--%>
					<shiro:hasPermission name="affair:affairOrgRewardPunish:view"><li class="active"><a href="${ctx}/affair/affairOrgRewardPunish/list?treeId=${treeId}&parentId=${parentId}">组织奖惩信息</a></li></shiro:hasPermission>
					<shiro:hasPermission name="affair:affairWorkDoneManage:view"><li><a href="${ctx}/affair/affairWorkDoneManage/list?treeId=${treeId}">工作总结</a></li></shiro:hasPermission>
					<shiro:hasPermission name="affair:affairSystemConstruction:view">
						<li><a href="${ctx}/affair/affairSystemConstruction/list?treeId=${treeId}&parentId=${parentId}">制度建设</a></li>
					</shiro:hasPermission>
				</c:otherwise>
			</c:choose>
	</ul>
<form:form id="searchForm" modelAttribute="affairOrgRewardPunish"
		   action="${ctx}/affair/affairOrgRewardPunish/list?treeId=${treeId}" method="post"
		   class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="党组织奖惩信息表.xlsx"/>
		<ul class="ul-form">
			<li><label>奖惩标题：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>奖惩类别：</label>
				<form:select id="awardCategory" path="awardCategory" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('award_category')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>


				<form:select id="type" path="type" class="input-xlarge " cssStyle="display: none">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_org_reward_punish')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<form:select id="cjType" path="cjType" class="input-xlarge " cssStyle="display: none">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('cj_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>奖惩时间：</label>
			<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				   value="<fmt:formatDate value="${affairOrgRewardPunish.startDate}" pattern="yyyy-MM-dd"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				--
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairOrgRewardPunish.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>党组织名称：</label>
				<sys:treeselect id="partyOrganization" name="partyOrganizationId" value="${affairOrgRewardPunish.partyOrganizationId}" labelName="partyOrganization" labelValue="${affairOrgRewardPunish.partyOrganization}"
					title="党组织名称" url="/affair/affairGeneralSituation/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairOrgRewardPunish:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()" value="添加"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairOrgRewardPunish/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input class="btn btn-primary" type="button" value="重置"
									onclick="window.location.href='${ctx}/affair/affairOrgRewardPunish/list?treeId=${treeId}'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>
					<input style='margin-left:12px' type='checkbox'id='checkAll' onclick='chooseAll(this, "myCheckBox")'/>
				</th>
				<th>序号</th>
				<th>奖惩时间</th>
				<th>奖惩标题</th>
				<th>奖惩类别</th>
				<th>党组织名称</th>
				<th>批准党委</th>
				<th>推送状态</th>
				<shiro:hasPermission name="affair:affairOrgRewardPunish:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairOrgRewardPunish" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairOrgRewardPunish.id}"/>
				</td>
				<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<fmt:formatDate value="${affairOrgRewardPunish.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairOrgRewardPunish.title}
				</td>
				<td>
						${fns:getDictLabel(affairOrgRewardPunish.awardCategory, 'award_category', '')}
				</td>
				<td>
					${affairOrgRewardPunish.partyOrganization}
				</td>
				<td>
					${fns:getDictLabel(affairOrgRewardPunish.approvalOrg, 'affair_paryt_committee', '')}
				</td>
				<td>
					${fns:getDictLabel(affairOrgRewardPunish.pushType, 'push_types', '')}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairOrgRewardPunish.id}')">查看</a>
				<shiro:hasPermission name="affair:affairOrgRewardPunish:edit">
                    <c:if test="${affairOrgRewardPunish.createBy.id == fns:getUser().id}">
                        <a href="javascript:void(0)" onclick="openAddEditDialog('${affairOrgRewardPunish.id}')">修改</a>
                        <a href="${ctx}/affair/affairOrgRewardPunish/delete?id=${affairOrgRewardPunish.id}&treeId=${treeId}"
                           onclick="return confirmx('确认要删除该组织奖惩信息吗？', this.href)">删除</a>
                       <%-- <a href="javascript:void(0)"
                           onclick="openForm('${ctx}/exam/examJcInfoPlus/OrgRewardPunishForm?id=${affairOrgRewardPunish.id}')">推送到奖惩信息库</a>--%>
                    </c:if>
				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>