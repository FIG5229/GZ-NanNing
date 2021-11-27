<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党员组织隶属管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairBelongTo/export?treeId=${treeId}&idNumber=${pmId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairBelongTo/list?treeId=${treeId}&idNumber=${pmId}");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairBelongTo/export?flag=true&treeId=${treeId}&idNumber=${pmId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairBelongTo/list?treeId=${treeId}&idNumber=${pmId}");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairBelongTo", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairBelongTo/list?treeId=${treeId}&idNumber=${pmId}"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
		}
		//添加修改弹窗
		function openAddEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairBelongTo/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairBelongTo/form";
			}
			if (${pmId !=null &&  pmId != ''}){
				url="iframe:${ctx}/affair/affairBelongTo/form?idNumber=${pmId}"
			}
			top.$.jBox.open(url, "党员组织隶属",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairBelongTo/list?treeId=${treeId}&idNumber=${pmId}";
				}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairBelongTo/formDetail?id="+id;
			top.$.jBox.open(url, "党员组织隶属",1000,350,{
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
		<%--<li><a href="${ctx}/affair/affairRelationshipTransfer/">系统内组织关系移交转接</a></li>
		<li><a href="${ctx}/affair/affairComment/">民主评议</a></li>
		<li class="active"><a href="${ctx}/affair/affairBelongTo/">党员组织隶属</a></li>
		<li><a href="${ctx}/affair/affairPartyRewardPunish/">党员奖惩信息</a></li>
		<li><a href="${ctx}/affair/affairStandard/">个人合格党员标准</a></li>
		<shiro:hasAnyRoles name="nanningju_relationship_transfer,nanning_relationship_transfer,liuzhouchu_relationship_transfer,beihaichu_relationship_transfer"><li><a href="${ctx}/affair/affairRelationshipTransfer/manageList">系统内组织关系移交转接管理</a></li></shiro:hasAnyRoles>
		<shiro:hasPermission name="affair:affairStandard:manage"><li><a href="${ctx}/affair/affairStandard/manageList">个人合格党员标准审核管理</a></li></shiro:hasPermission>--%>
		<c:if test="${pmId == null || pmId == ''}">
			<li><a href="${ctx}/affair/affairPartyMember/list?partyBranchId=${treeId}&cardNum=${pmId}">党员${pmId}</a></li>
		</c:if>
			<shiro:hasPermission name="affair:affairRelationshipTransfer:view"> <li><a href="${ctx}/affair/affairRelationshipTransfer?treeId=${treeId}&idNumber=${pmId}">组织关系转接</a></li></shiro:hasPermission>
			<shiro:hasPermission name="affair:affairBelongTo:view"> <li class="active"><a href="${ctx}/affair/affairBelongTo?treeId=${treeId}&idNumber=${pmId}">组织隶属</a></li></shiro:hasPermission>
			<shiro:hasPermission name="affair:affairPartyRewardPunish:view"> <li><a href="${ctx}/affair/affairPartyRewardPunish?treeId=${treeId}&idNumber=${pmId}">奖惩信息</a></li></shiro:hasPermission>
<%--		<li><a href="${ctx}/affair/affairStandard?treeId=${treeId}&idNumber=${pmId}">个人合格党员标准</a></li>--%>
			<shiro:hasAnyRoles
					name="nanningju_relationship_transfer,nanning_relationship_transfer,liuzhouchu_relationship_transfer,beihaichu_relationship_transfer">
                <li><a href="${ctx}/affair/affairRelationshipTransfer/manageList?treeId=${treeId}&idNumber=${pmId}">组织关系转接审核管理</a>
				</li>
			</shiro:hasAnyRoles>
<%--		<shiro:hasPermission name="affair:affairStandard:manage"><li><a href="${ctx}/affair/affairStandard/manageList?treeId=${treeId}&idNumber=${pmId}">个人合格党员标准审核管理</a></li></shiro:hasPermission>--%>
			<shiro:hasPermission name="affair:affairPartyTrain:view"><li><a href="${ctx}/affair/affairPartyTrain/list?treeId=${treeId}&idNumber=${pmId}">党内培训</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairBelongTo" action="${ctx}/affair/affairBelongTo?treeId=${treeId}&idNumber=${pmId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="组织隶属表.xlsx"/>
		<ul class="ul-form">
			<li><label>党员姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>所在党支部：</label>
				<sys:treeselect id="partyBranch" name="partyBranchId" value="${affairBelongTo.partyBranchId}" labelName="partyBranch" labelValue="${affairBelongTo.partyBranch}"
					title="所在党支部" url="/affair/affairGeneralSituation/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label class="width120">进入支部时间：</label>
				<input name="enterStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairBelongTo.enterStartDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				 --
				<input name="enterEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairBelongTo.enterEndDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label class="width120">转离支部时间：</label>
				<input name="leaveStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairBelongTo.leaveStartDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				 --
				<input name="leaveEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairBelongTo.leaveEndDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label class="width120">转离支部类型：</label>
				<form:select path="leaveType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_leave_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairBelongTo:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()" value="添加"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairBelongTo/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairBelongTo/list?treeId=${treeId}&idNumber=${pmId}'"/></li>
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
				<th>党员姓名</th>
				<th>性别</th>
				<%--<th>警号</th>--%>
				<th>身份证号</th>
				<th>所在党支部</th>
				<th>进入支部时间</th>
				<th>转离支部时间</th>
				<th>转离支部类型</th>
				<shiro:hasPermission name="affair:affairBelongTo:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairBelongTo" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairBelongTo.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>

						${affairBelongTo.name}

				</td>
				<td>
					${fns:getDictLabel(affairBelongTo.sex, 'sex', '')}
				</td>
				<%--<td>
					${affairBelongTo.policeNo}
				</td>--%>
				<td>
					${affairBelongTo.idNumber}
				</td>
				<td>
					${affairBelongTo.partyBranch}
				</td>
				<td>
					<fmt:formatDate value="${affairBelongTo.enterDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairBelongTo.leaveDate}" pattern="yyyy-MM-d"/>
				</td>
				<td>
					${fns:getDictLabel(affairBelongTo.leaveType, 'affair_leave_type', '')}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairBelongTo.id}')">查看</a>
				<shiro:hasPermission name="affair:affairBelongTo:edit">
                    <c:if test="${affairBelongTo.createBy.id == fns:getUser().id}">
                        <a href="javascript:void(0)" onclick="openAddEditDialog('${affairBelongTo.id}')">修改</a>
                        <a href="${ctx}/affair/affairBelongTo/delete?id=${affairBelongTo.id}&treeId=${treeId}&idNumber=${pmId}"
                           onclick="return confirmx('确认要删除该党员组织隶属吗？', this.href)">删除</a>
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