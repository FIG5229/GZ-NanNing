<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人合格党员标准审核管理</title>
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
		function openDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairStandard/shenHeDialog?id="+id, "个人合格党员标准审核",800,420,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairStandard/manageList?treeId=${treeId}&idNumber=${pmId}";
				}
			});
		};
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairStandard/formDetail?id="+id;
			top.$.jBox.open(url, "个人合格党员标准",1000,650,{
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
	<%--<li><a href="${ctx}/affair/affairRelationshipTransfer/">系统内组织关系移交转接</a></li>
	<li><a href="${ctx}/affair/affairComment/">民主评议</a></li>
	<li><a href="${ctx}/affair/affairBelongTo/">党员组织隶属</a></li>
	<li><a href="${ctx}/affair/affairPartyRewardPunish/">党员奖惩信息</a></li>
	<li><a href="${ctx}/affair/affairStandard/">个人合格党员标准</a></li>
	<shiro:hasAnyRoles name="nanningju_relationship_transfer,nanning_relationship_transfer,liuzhouchu_relationship_transfer,beihaichu_relationship_transfer"><li><a href="${ctx}/affair/affairRelationshipTransfer/manageList">系统内组织关系移交转接管理</a></li></shiro:hasAnyRoles>
	<shiro:hasPermission name="affair:affairStandard:manage"><li class="active"><a href="${ctx}/affair/affairStandard/manageList">个人合格党员标准审核管理</a></li></shiro:hasPermission>--%>
		<c:if test="${pmId == null || pmId == ''}">
			<li><a href="${ctx}/affair/affairPartyMember/list?partyBranchId=${treeId}&cardNum=${pmId}">党员${pmId}</a></li>
		</c:if>
    <li><a href="${ctx}/affair/affairRelationshipTransfer?treeId=${treeId}&idNumber=${pmId}">组织关系转接</a></li>
    <li><a href="${ctx}/affair/affairBelongTo?treeId=${treeId}&idNumber=${pmId}">组织隶属</a></li>
    <li><a href="${ctx}/affair/affairPartyRewardPunish?treeId=${treeId}&idNumber=${pmId}">奖惩信息</a></li>
		<li><a href="${ctx}/affair/affairStandard?treeId=${treeId}&idNumber=${pmId}">个人合格党员标准</a></li>
		<shiro:hasAnyRoles
				name="nanningju_relationship_transfer,nanning_relationship_transfer,liuzhouchu_relationship_transfer,beihaichu_relationship_transfer">
            <li><a href="${ctx}/affair/affairRelationshipTransfer/manageList?treeId=${treeId}&idNumber=${pmId}">组织关系转接审核管理</a>
			</li>
		</shiro:hasAnyRoles>
		<shiro:hasPermission name="affair:affairStandard:manage"><li class="active"><a href="${ctx}/affair/affairStandard/manageList?treeId=${treeId}&idNumber=${pmId}">个人合格党员标准审核管理</a></li></shiro:hasPermission>
		<li><a href="${ctx}/affair/affairPartyTrain/list?treeId=${treeId}&idNumber=${pmId}">党内培训</a></li>

</ul>
<form:form id="searchForm" modelAttribute="affairStandard" action="${ctx}/affair/affairStandard/manageList?treeId=${treeId}&idNumber=${pmId}" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<ul class="ul-form">
		<li><label>党组织名称：</label>
			<sys:treeselect id="partyOrganization" name="partyOrganizationId" value="${affairStandard.partyOrganizationId}" labelName="partyOrganization" labelValue="${affairStandard.partyOrganization}"
				title="党组织名称" url="/affair/affairGeneralSituation/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
		</li>
		<li><label>审核状态：</label>
			<form:select path="status" class="input-medium">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('affair_query_shenhe')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</li>
		<li><label>修订时间：</label>
			<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				   value="<fmt:formatDate value="${affairStandard.startDate}" pattern="yyyy-MM-dd"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				   value="<fmt:formatDate value="${affairStandard.endDate}" pattern="yyyy-MM-dd"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		</li>
	</ul>
	<ul class="ul-form2">
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
		<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
		<li class="btns"><input class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairStandard/manageList?treeId=${treeId}&idNumber=${pmId}'"/></li>
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
		<th>党组织名称</th>
		<th>姓名</th>
		<th>身份证号</th>
		<th>修订时间</th>
		<th>审核状态</th>
		<shiro:hasPermission name="affair:affairStandard:manage"><th id="handleTh">操作</th></shiro:hasPermission>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="affairStandard" varStatus="status">
		<tr>
			<td>
				<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairStandard.id}"/>
			</td>
			<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
			<td>
					${affairStandard.partyOrganization}
			</td>
			<td>

					${affairStandard.name}

			</td>
			<td>${affairStandard.idNumber}</td>
			<td>
				<fmt:formatDate value="${affairStandard.updateDate}" pattern="yyyy-MM-dd"/>
			</td>
			<td>
				${fns:getDictLabel(affairStandard.status, 'affair_query_shenhe', '')}
			</td>
			<td class="handleTd">
				<a onclick="openDetailDialog('${affairStandard.id}')">查看</a>
			<shiro:hasPermission name="affair:affairStandard:manage">
				<a onclick="openDialog('${affairStandard.id}')">审核</a>
			</shiro:hasPermission>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>