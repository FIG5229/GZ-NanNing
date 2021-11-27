<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党内主题实践活动管理</title>
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
								$("#searchForm").attr("action","${ctx}/affair/affairThemeActivity/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairThemeActivity/manageList");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairThemeActivity/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairThemeActivity/manageList");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairThemeActivity", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairThemeActivity/manageList"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function addthemeActivity() {
			$.post('${ctx}/affair/affairThemeActivity/form',{},function (res) {

			})
		};
		//添加修改弹窗
		function openAddEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairThemeActivity/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairThemeActivity/form";
			}
			top.$.jBox.open(url, "党内主题实践活动",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairThemeActivity/manageList";
				}
			});
		};
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairThemeActivity/formDetail?id="+id;
			top.$.jBox.open(url, "党内主题实践活动",1100,500,{
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
		<%--<li><a href="${ctx}/affair/affairThemeActivity/">查询</a></li>
		<shiro:hasPermission name="affair:affairThemeActivity:edit"><li class="active"><a href="${ctx}/affair/affairThemeActivity/manageList">管理</a></li></shiro:hasPermission>--%>
		<li><a href="${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}">党组织</a></li>
		<li><a href="${ctx}/affair/affairMonthStudy?treeId=${treeId}">月度学习计划</a></li>
		<li><a href="${ctx}/affair/affairTwoOne?treeId=${treeId}">"两学一做"专题学习</a></li>
		<li class="active"><a href="${ctx}/affair/affairThemeActivity/manageList?treeId=${treeId}">党内主题实践活动情况</a></li>
		<li><a href="${ctx}/affair/affairCreateBranch?treeId=${treeId}">党内创品牌活动</a></li>
		<li><a href="${ctx}/affair/affairVolunteerService?treeId=${treeId}">志愿服务活动</a></li>
		<li><a href="${ctx}/affair/affairChuangGangJianQu?treeId=${treeId}">创岗建区活动</a></li>
		<li><a href="${ctx}/affair/affairYearThreeOnePlan?treeId=${treeId}">年度“三会一课”计划</a></li>
		<li><a href="${ctx}/affair/affairYearThreeOne?treeId=${treeId}">“三会一课”录入</a></li>
		<shiro:hasPermission name="affair:affairYearThreeOnePlan:manage"><li><a href="${ctx}/affair/affairYearThreeOnePlan/manageList?treeId=${treeId}">年度“三会一课”计划审核</a></li></shiro:hasPermission>
		<shiro:hasPermission name="affair:affairYearThreeOne:manage"><li><a href="${ctx}/affair/affairYearThreeOne/manageList?treeId=${treeId}">“三会一课”录入审核</a></li></shiro:hasPermission>
		<li><a href="${ctx}/affair/affairLifeMeet?treeId=${treeId}">组织生活会</a></li>
		<shiro:hasPermission name="affair:affairLifeMeet:manage"><li><a href="${ctx}/affair/affairLifeMeet/manageList?treeId=${treeId}">组织生活会管理</a></li></shiro:hasPermission>
		<li><a href="${ctx}/affair/affairIdeaAnalysis?treeId=${treeId}">党员队伍思想状况分析</a></li>
		<%--1:专报简报 2：调研文章--%>
		<li><a href="${ctx}/affair/affairResearchArticle?flag=1&treeId=${treeId}">专报简报</a></li>
		<li><a href="${ctx}/affair/affairResearchArticle?flag=2&treeId=${treeId}">调研文章</a></li>
		<li><a href="${ctx}/affair/affairAssess?treeId=${treeId}">党委书记述职测评</a></li>
		<li><a href="${ctx}/affair/affairClassMember?treeId=${treeId}">班子成员</a></li>
		<li><a href="${ctx}/affair/affairElection/list?treeId=${treeId}">党组织换届选举</a></li>
		<li><a href="${ctx}/affair/affairOrgRewardPunish?treeId=${treeId}">组织奖惩信息</a></li>
		<shiro:hasPermission name="affair:affairElection:manage"><li><a href="${ctx}/affair/affairElection/manageList?treeId=${treeId}">党组织换届选举管理</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairThemeActivity" action="${ctx}/affair/affairThemeActivity/manageList?treeId=${treeId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="党内主题实践活动表.xlsx"/>
		<ul class="ul-form">
			<li><label>活动名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>活动时间：</label>
				<input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairThemeActivity.startTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				 --
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairThemeActivity.endTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>活动类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_theme_activity')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>活动地点：</label>
				<form:input path="place" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>参加人员：</label>
				<form:input path="joinPerson" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>党组织名称：</label>
				<sys:treeselect id="partyOrganizationName" name="partyOrganizationId" value="${affairThemeActivity.partyOrganizationId}" labelName="partyOrganizationName" labelValue="${affairThemeActivity.partyOrganizationName}"
					title="党组织名称" url="/affair/affairGeneralSituation/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>举办单位：</label>
				<sys:treeselect id="holdUnitName" name="holdUnitId" value="${affairThemeActivity.holdUnitId}" labelName="holdUnitName" labelValue="${affairThemeActivity.holdUnitName}"
					title="举办单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairThemeActivity:manage">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()" value="添加"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairThemeActivity/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairThemeActivity/manageList'"/></li>
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
				<th>活动名称</th>
				<th>活动类型</th>
				<th>党组织名称</th>
				<th>举办单位</th>
				<th>活动地点</th>
				<th>活动时间</th>
				<shiro:hasPermission name="affair:affairThemeActivity:manage"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairThemeActivity" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairThemeActivity.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<a onclick="openDetailDialog('${affairThemeActivity.id}')">
					    ${affairThemeActivity.name}
				    </a>
                </td>
				<td>
					${fns:getDictLabel(affairThemeActivity.type, 'affair_theme_activity', '')}
				</td>
				<td>
					${affairThemeActivity.partyOrganizationName}
				</td>
				<td>
					${affairThemeActivity.holdUnitName}
				</td>
				<td>
					${affairThemeActivity.place}
				</td>
				<td>
					<fmt:formatDate value="${affairThemeActivity.startTime}" pattern="yyyy-MM-dd HH:mm"/> 至 <fmt:formatDate value="${affairThemeActivity.endTime}" pattern="yyyy-MM-dd HH:mm"/>
				</td>
				<shiro:hasPermission name="affair:affairThemeActivity:manage"><td class="handleTd">
    				<a onclick="openAddEditDialog('${affairThemeActivity.id}')">修改</a>
					<a href="${ctx}/affair/affairThemeActivity/delete?id=${affairThemeActivity.id}" onclick="return confirmx('确认要删除该党内主题实践活动吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>