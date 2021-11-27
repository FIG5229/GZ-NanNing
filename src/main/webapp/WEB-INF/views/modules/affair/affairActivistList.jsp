<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>入党积极分子管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairActivist/export?treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairActivist/list?treeId=${treeId}");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairActivist/export?flag=true&treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairActivist/list?treeId=${treeId}");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairActivist", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairActivist/list?treeId=${treeId}"}});
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
			var url = "iframe:${ctx}/affair/affairActivist/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairActivist/form";
			}
			top.$.jBox.open(url, "入党积极分子",900,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairActivist/list?treeId=${treeId}";
				}
			});
		};
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairActivist/formDetail?id="+id;
			top.$.jBox.open(url, "入党积极分子",1000,370,{
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
		<li><a href="${ctx}/affair/affairApplicant/list?treeId=${treeId}">入党申请人</a></li>
		<li class="active"><a href="${ctx}/affair/affairActivist/list?treeId=${treeId}">入党积极分子</a></li>
		<li><a href="${ctx}/affair/affairDevelopObject/list?treeId=${treeId}">入党发展对象</a></li>
<%--		<li><a href="${ctx}/affair/affairReservePartyMembers/zhuanzheng?treeId=${treeId}">预备党员转正</a></li>--%>
		<li><a href="${ctx}/affair/affairProbationaryMember/list?treeId=${treeId}">预备党员</a></li>
		<%--<li><a href="${ctx}/affair/affairClassMember/">班子成员</a></li>
		<li><a href="${ctx}/affair/affairElection/">党组织换届选举</a></li>
		<li><a href="${ctx}/affair/affairOrgRewardPunish/list">组织奖惩信息</a></li>
		<shiro:hasPermission name="affair:affairElection:manage"><li><a href="${ctx}/affair/affairElection/manageList">党组织换届选举管理</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairActivist" action="${ctx}/affair/affairActivist/list?treeId=${treeId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="入党积极分子表.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>警号：</label>
				<form:input path="policeNo" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>所属党支部：</label>
				<sys:treeselect id="partyBranch" name="partyBranchId" value="${affairActivist.partyBranchId}" labelName="partyBranch" labelValue="${affairActivist.partyBranch}"
					title="所属党支部" url="/affair/affairGeneralSituation/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>培养人：</label>
				<form:input path="fosterPeople" htmlEscape="false" class="input-medium"/>
			</li>
<%--			<li><label class="width120">申请入党时间：</label>--%>
<%--				<input name="approvalStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
<%--					value="<fmt:formatDate value="${affairActivist.approvalStartDate}" pattern="yyyy-MM-dd"/>"--%>
<%--					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>--%>
<%--			----%>
<%--			<input name="approvalEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
<%--				   value="<fmt:formatDate value="${affairActivist.approvalEndDate}" pattern="yyyy-MM-dd"/>"--%>
<%--				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>--%>
<%--			</li>--%>
<%--			<li><label class="width143">列为入党积极分子时间：</label>--%>
<%--				<input name="enterStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
<%--					value="<fmt:formatDate value="${affairActivist.enterStartDate}" pattern="yyyy-MM-dd"/>"--%>
<%--					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>--%>
<%--				----%>
<%--				<input name="enterEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
<%--					   value="<fmt:formatDate value="${affairActivist.enterEndDate}" pattern="yyyy-MM-dd"/>"--%>
<%--					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>--%>
<%--			</li>--%>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairActivist:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()" value="添加"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairActivist/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairActivist/list?treeId=${treeId}'"/></li>
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
				<th>姓名</th>
				<th>警号</th>
				<th>所在党支部</th>
				<th>培养人</th>
				<th>申请入党时间</th>
				<th>列为入党积极分子时间</th>
				<shiro:hasPermission name="affair:affairActivist:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairActivist" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairActivist.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
<%--					<a onclick="openDetailDialog('${affairActivist.id}')">--%>
<%--						${affairActivist.name}--%>
<%--					</a>--%>
		            ${affairActivist.name}
				</td>
				<td>
					${affairActivist.policeNo}
				</td>
				<td>
					${affairActivist.partyBranch}
				</td>
				<td>
					${affairActivist.fosterPeople}
				</td>
				<td>
					<fmt:formatDate value="${affairActivist.approvalDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairActivist.enterDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td class="handleTd">
				<a href="javascript:void(0)" onclick="openDetailDialog('${affairActivist.id}')">查看</a>
				<shiro:hasPermission name="affair:affairActivist:edit">
					<c:if test="${affairActivist.createBy.id == fns:getUser().id}">
						<a href="javascript:void(0)" onclick="openAddEditDialog('${affairActivist.id}')">修改</a>
						<a href="${ctx}/affair/affairActivist/delete?id=${affairActivist.id}&treeId=${treeId}"
						   onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a>
					</c:if>
					<a href="${ctx}/affair/affairActivist/push?id=${affairActivist.id}&treeId=${treeId}"
					   onclick="return confirmx('确认要推送至入党发展对象吗？', this.href)">推送</a>
				</shiro:hasPermission></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>