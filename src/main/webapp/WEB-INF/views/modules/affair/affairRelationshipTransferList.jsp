<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统内组织关系移交转接管理</title>
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
			var url = "iframe:${ctx}/affair/affairRelationshipTransfer/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairRelationshipTransfer/form";
			}
			if (${pmId !=null &&  pmId != ''}){
				url="iframe:${ctx}/affair/affairRelationshipTransfer/form?idNumber=${pmId}"
			}
			top.$.jBox.open(url, "组织关系转接", 1000, 530, {
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairRelationshipTransfer/list?treeId=${treeId}&idNumber=${pmId}";
				}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairRelationshipTransfer/formDetail?id="+id;
			top.$.jBox.open(url, "系组织关系转接", 1000, 350, {
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
		<c:if test="${pmId == null || pmId == ''}">
			<li><a href="${ctx}/affair/affairPartyMember/list?partyBranchId=${treeId}&cardNum=${pmId}">党员${pmId}</a></li>
		</c:if>
		<shiro:hasPermission name="affair:affairRelationshipTransfer:view"><li class="active"><a
				href="${ctx}/affair/affairRelationshipTransfer?treeId=${treeId}&idNumber=${pmId}">组织关系转接</a></li></shiro:hasPermission>
		<shiro:hasPermission name="affair:affairBelongTo:view"><li><a href="${ctx}/affair/affairBelongTo?treeId=${treeId}&idNumber=${pmId}">组织隶属</a></li></shiro:hasPermission>
		<shiro:hasPermission name="affair:affairPartyRewardPunish:view"><li><a href="${ctx}/affair/affairPartyRewardPunish?treeId=${treeId}&idNumber=${pmId}">奖惩信息</a></li></shiro:hasPermission>
<%--		<li><a href="${ctx}/affair/affairStandard?treeId=${treeId}&idNumber=${pmId}">个人合格党员标准</a></li>--%>
		<shiro:hasAnyRoles
				name="nanningju_relationship_transfer,nanning_relationship_transfer,liuzhouchu_relationship_transfer,beihaichu_relationship_transfer">
			<li><a href="${ctx}/affair/affairRelationshipTransfer/manageList?treeId=${treeId}&idNumber=${pmId}">组织关系转接审核管理</a>
			</li>
		</shiro:hasAnyRoles>
<%--		<shiro:hasPermission name="affair:affairStandard:manage"><li><a href="${ctx}/affair/affairStandard/manageList?treeId=${treeId}&idNumber=${pmId}">个人合格党员标准审核管理</a></li></shiro:hasPermission>--%>
		<shiro:hasPermission name="affair:affairPartyTrain:view"><li><a href="${ctx}/affair/affairPartyTrain/list?treeId=${treeId}&idNumber=${pmId}">党内培训</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairRelationshipTransfer" action="${ctx}/affair/affairRelationshipTransfer?treeId=${treeId}&idNumber=${pmId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<%--<li><label>警号：</label>
				<form:input path="policeNo" htmlEscape="false" class="input-medium"/>
			</li>--%>
			<li><label>处理时间：</label>
				<input name="handleStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairRelationshipTransfer.handleStartTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				 --
				<input name="handleEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairRelationshipTransfer.handleEndTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairRelationshipTransfer:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()" value="添加"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairRelationshipTransfer/deleteByIds','checkAll','myCheckBox')"/></li>
				<%--<li class="btns"><input  class="btn btn-primary" type="button" value="导入"/></li>--%>
			</shiro:hasPermission>
				<%--<li class="btns"><input  class="btn btn-primary" type="button" value="导出"/></li>--%>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairRelationshipTransfer/list?treeId=${treeId}&idNumber=${pmId}'"/></li>
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
				<%--<th>警号</th>--%>
				<th>身份证号</th>
				<th>原组织</th>
				<th>申请转入组织</th>
				<th>申请时间</th>
				<th>处理时间</th>
				<th>审批状态</th>
				<shiro:hasPermission name="affair:affairRelationshipTransfer:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairRelationshipTransfer" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairRelationshipTransfer.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairRelationshipTransfer.name}
				</td>
				<%--<td>
					${affairRelationshipTransfer.policeNo}
				</td>--%>
				<td>
					${affairRelationshipTransfer.idNumber}
				</td>
				<td>
					${affairRelationshipTransfer.oldOrganization}
				</td>
				<td>
					${affairRelationshipTransfer.nowOrganization}
				</td>
				<td>
					<fmt:formatDate value="${affairRelationshipTransfer.applyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairRelationshipTransfer.handleTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${affairRelationshipTransfer.status}
					<%--未通过气泡后期考虑再加
					<c:choose>
						<c:when test="${affairRelationshipTransfer.status == '未通过'}">
							<a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
							   data-content=""  style="cursor: pointer;color: red">未通过</a>
						</c:when>
						<c:otherwise>
							${affairRelationshipTransfer.status}
						</c:otherwise>
					</c:choose>--%>
				</td>
				<shiro:hasPermission name="affair:affairRelationshipTransfer:edit">
					<td class="handleTd">
						<c:if test="${affairRelationshipTransfer.createBy.id == fns:getUser().id}">
							<%--保存之后开始进入审核，不能再修改--%>
							<c:if test="${empty affairRelationshipTransfer.id}">
								<a href="javascript:void(0)" onclick="openAddEditDialog('${affairRelationshipTransfer.id}')">修改</a>
							</c:if>
							<a href="${ctx}/affair/affairRelationshipTransfer/delete?id=${affairRelationshipTransfer.id}&treeId=${treeId}&idNumber=${pmId}"
							   onclick="return confirmx('确认要删除该组织关系转接吗？', this.href)">删除</a>
						</c:if>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>