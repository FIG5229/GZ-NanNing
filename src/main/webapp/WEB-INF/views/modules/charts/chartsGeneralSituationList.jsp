<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党组织概况管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//导出
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairGeneralSituation/export?treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairGeneralSituation/export?flag=true&treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
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
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairGeneralSituation", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		};
		//添加修改弹窗
		function openAddEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairGeneralSituation/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairGeneralSituation/form";
			}
			top.$.jBox.open(url, "党组织概况",1000,700,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}";
				}
			});
		};
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairGeneralSituation/formDetail?id="+id;
			top.$.jBox.open(url, "党组织概况",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};

		//新建党小组
		//此处treeId为新建的党小组的父Id，点击党小组后根据父Id替换党组织，并且根据父Id查询数据
		function addPoliticalGroup(treeId) {
			top.$.jBox.open("iframe:${ctx}/affair/affairPoliticalGroup/form?parentId="+treeId, "党小组添加",800,500,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}";}
			});
		};
		if("sucess"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
	<style>
		.form-search .ul-form li label {
			width: 104px;
			text-align: right;
		}
	</style>
</head>
<body>
<%--	<ul class="nav nav-tabs">--%>
<%--		<c:choose>--%>
<%--            <c:when test="${treeId == '1' || treeId == '34' || treeId ==  '95' || treeId == '156' || treeId == '2'}">--%>
<%--				<li><a href="${ctx}/affair/affairWorkLead/list?treeId=${treeId}">主体责任落实</a></li>--%>
<%--				<li><a href="${ctx}/affair/affairElection/list?treeId=${treeId}">换届选举</a></li>--%>
<%--				<li><a href="${ctx}/affair/affairLifeMeet/list?treeId=${treeId}">民主生活会</a></li>--%>
<%--				<li><a href="${ctx}/affair/affairAssess/list?treeId=${treeId}">党委书记述职测评</a></li>--%>
<%--				<li><a href="${ctx}/affair/affairMemberPartyCommittee/list?treeId=${treeId}">党委委员</a>--%>
<%--				<li><a href="${ctx}/affair/affairOrgRewardPunish/list?treeId=${treeId}">组织奖惩信息</a></li>--%>
<%--            </c:when>--%>
<%--            <c:otherwise>--%>
<%--				<c:choose>--%>
<%--					<c:when test="${treeId=='21321' || parentId=='21321'}">--%>
<%--						<li class="active"><a href="${ctx}/affair/affairPoliticalGroup/list?treeId=${treeId}">党小组</a></li>--%>
<%--					</c:when>--%>
<%--					<c:otherwise>--%>
<%--						<li class="active"><a href="${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}">党组织</a></li>--%>
<%--					</c:otherwise>--%>
<%--				</c:choose>--%>
<%--				<li><a href="${ctx}/affair/affairMonthStudy/list?treeId=${treeId}">两学一做</a></li>--%>
<%--				<li><a href="${ctx}/affair/affairYearThreeOnePlan/list?treeId=${treeId}">三会一课</a></li>--%>
<%--				<li><a href="${ctx}/affair/affairCreateBranch/list?treeId=${treeId}">活动载体</a></li>--%>
<%--				<c:choose>--%>
<%--					<c:when test="${treeId == '51a154399faf457f8e1ef12136cf1260' || treeId == '8e66c12b4d3a4fc3ac780c3b44cb1078'}">--%>
<%--						<li><a href="${ctx}/affair/affairAssess/list?treeId=${treeId}">党总支书记述职测评</a></li>--%>
<%--					</c:when>--%>
<%--					<c:otherwise>--%>
<%--						<li><a href="${ctx}/affair/affairAssess/list?treeId=${treeId}">党支部书记述职测评</a></li>--%>
<%--					</c:otherwise>--%>
<%--				</c:choose>--%>
<%--				<li><a href="${ctx}/affair/affairLifeMeet/list?treeId=${treeId}">组织生活会</a></li>--%>
<%--				<li><a href="${ctx}/affair/affairComment/list?treeId=${treeId}">民主评议</a></li>--%>
<%--				<li><a href="${ctx}/affair/affairElection/list?treeId=${treeId}">换届选举</a></li>--%>
<%--				<li><a href="${ctx}/affair/affairIdeaAnalysis/list?treeId=${treeId}">党员队伍思想分析</a></li>--%>
<%--				&lt;%&ndash;1:专报简报 2：调研文章&ndash;%&gt;--%>
<%--				&lt;%&ndash;<li><a href="${ctx}/affair/affairResearchArticle?flag=1&treeId=${treeId}">专报简报</a></li>--%>
<%--                <li><a href="${ctx}/affair/affairResearchArticle?flag=2&treeId=${treeId}">调研文章</a></li>&ndash;%&gt;--%>
<%--				<li><a href="${ctx}/affair/affairOrgRewardPunish/list?treeId=${treeId}">组织奖惩信息</a></li>--%>
<%--				<li><a href="${ctx}/affair/affairWorkDoneManage/list?treeId=${treeId}">工作总结</a></li>--%>
<%--				<shiro:hasPermission name="affair:affairSystemConstruction:view">--%>
<%--					<li><a href="${ctx}/affair/affairSystemConstruction/list?treeId=${treeId}&parentId=${parentId}">制度建设</a></li>--%>
<%--				</shiro:hasPermission>--%>
<%--			</c:otherwise>--%>
<%--		</c:choose>--%>
<%--	</ul>--%>
<form:form id="searchForm" modelAttribute="affairGeneralSituation" action="${ctx}/sys/charts/lifeMeet/detail" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<input id="fileName" name="label" type="hidden" value="${label}"/>
	<input id="fileName" name="year" type="hidden" value="${year}"/>
	<input id="fileName" name="dateStart" type="hidden" value="${dateStart}"/>
	<input id="fileName" name="dateEnd" type="hidden" value="${dateEnd}"/>
	<input id="fileName" name="month" type="hidden" value="${month}"/>
	<input id="fileName" name="dateType" type="hidden" value="${dateType}"/>


	<%--<ul class="ul-form">
		&lt;%&ndash;<li><label>党组织名称：</label>
			<form:input path="partyOrganization" htmlEscape="false" class="input-medium" readonly="true"/>
		</li>&ndash;%&gt;
			&lt;%&ndash;所属党组织去掉&ndash;%&gt;
			&lt;%&ndash;<li><label>所属党组织：</label>
				<sys:treeselect id="ofPartyOrganization" name="ofPartyOrgId" value="${affairGeneralSituation.ofPartyOrgId}" labelName="ofPartyOrganization" labelValue="${affairGeneralSituation.ofPartyOrganization}"
					title="所属党组织" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>&ndash;%&gt;

		<li><label>党组织成立时间：</label>
			<input style="width: 140px" name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				   value="<fmt:formatDate value="${affairGeneralSituation.startDate}" pattern="yyyy-MM-dd"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			&nbsp;--&nbsp;
			<input style="width: 140px" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				   value="<fmt:formatDate value="${affairGeneralSituation.endDate}" pattern="yyyy-MM-dd"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		</li>
		<li><label>所在单位：</label>
			<sys:treeselect id="unit" name="unitId" value="${affairGeneralSituation.unitId}" labelName="unit" labelValue="${affairGeneralSituation.unit}"
				title="所在单位" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true" />
		</li>
	</ul>
	<ul class="ul-form2">
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
		<shiro:hasPermission name="affair:affairGeneralSituation:edit">
			<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()" value="添加"/></li>
			<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairGeneralSituation/deleteByIds','checkAll','myCheckBox')"/></li>
			<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
		</shiro:hasPermission>
		<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
		<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
		<li class="btns"><input id="addGroup" class="btn btn-primary" type="button" value="新建党小组"  onclick="addPoliticalGroup('${treeId}')"/></li>
		<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}'"/></li>
		<li class="clearfix"></li>
	</ul>--%>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<%--<th>
			<input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>
		</th>--%>
		<th>序号</th>
		<th>党组织名称</th>
		<%--<th>所属党组织</th>--%>
		<th>党组织成立时间</th>
		<th>所在单位</th>
		<th>党组织党员数</th>
		<th>完成情况</th>
<%--		<shiro:hasPermission name="affair:affairGeneralSituation:edit"><th id="handleTh">操作</th></shiro:hasPermission>--%>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="affairGeneralSituation" varStatus="status">
		<tr>
		<%--	<td>
				<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairGeneralSituation.id}"/>
			</td>--%>
			<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
			</td>
			<td>
						${affairGeneralSituation.partyOrganization}
			</td>
				<%--	<td>
                        ${affairGeneralSituation.ofPartyOrganization}
                    </td>--%>
			<td>
				<fmt:formatDate value="${affairGeneralSituation.foundDate}" pattern="yyyy-MM-dd"/>
			</td>
			<td>
					${affairGeneralSituation.unit}
			</td>
			<td>
					${affairGeneralSituation.num}
			</td>
			<td>
				${label}
			</td>
			<%--<shiro:hasPermission name="affair:affairGeneralSituation:edit"><td class="handleTd">
				<a onclick="openDetailDialog('${affairGeneralSituation.id}')">
					查看
				</a>
                <c:if test="${affairGeneralSituation.createBy.id == fns:getUser().id}">
                    <a onclick="openAddEditDialog('${affairGeneralSituation.id}')">修改</a>
                    <a href="${ctx}/affair/affairGeneralSituation/delete?id=${affairGeneralSituation.id}&treeId=${treeId}"
                       onclick="return confirmx('确认要删除该党组织吗？', this.href)">删除</a>
                </c:if>
			</td></shiro:hasPermission>--%>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>