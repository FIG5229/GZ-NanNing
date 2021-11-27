<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党内培训管理</title>
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

		function openAddDialog() {
			top.$.jBox.open("iframe:${ctx}/affair/affairPartyTrain/form", "党内培训",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairPartyTrain/list?treeId=${treeId}&idNumber=${pmId}"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairPartyTrain/formDetail?id="+id;
			top.$.jBox.open(url, "党内培训详情",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		function openEditDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairPartyTrain/form?id="+id,"党内培训编辑",1000,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				},closed:function () {
					self.location.href="${ctx}/affair/affairPartyTrain/list?treeId=${treeId}&idNumber=${pmId}";
				}
			});
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
<%--		<li class="active"><a href="${ctx}/affair/affairPartyTrain/list?treeId=${treeId}">党内培训</a></li>--%>
	<c:if test="${pmId == null || pmId == ''}">
		<li><a href="${ctx}/affair/affairPartyMember/list?partyBranchId=${treeId}&cardNum=${pmId}">党员${pmId}</a></li>
	</c:if>
	<shiro:hasPermission name="affair:affairRelationshipTransfer:view"><li><a href="${ctx}/affair/affairRelationshipTransfer?treeId=${treeId}&idNumber=${pmId}">组织关系转接</a></li></shiro:hasPermission>
	<shiro:hasPermission name="affair:affairBelongTo:view"><li><a href="${ctx}/affair/affairBelongTo?treeId=${treeId}&idNumber=${pmId}">组织隶属</a></li></shiro:hasPermission>
	<shiro:hasPermission name="affair:affairPartyRewardPunish:view"><li><a href="${ctx}/affair/affairPartyRewardPunish?treeId=${treeId}&idNumber=${pmId}">奖惩信息</a></li></shiro:hasPermission>
<%--	<li><a href="${ctx}/affair/affairStandard?treeId=${treeId}&idNumber=${pmId}">个人合格党员标准</a></li>--%>
	<shiro:hasAnyRoles
			name="nanningju_relationship_transfer,nanning_relationship_transfer,liuzhouchu_relationship_transfer,beihaichu_relationship_transfer">
		<li><a href="${ctx}/affair/affairRelationshipTransfer/manageList?treeId=${treeId}&idNumber=${pmId}">组织关系转接审核管理</a>
		</li>
	</shiro:hasAnyRoles>
<%--	<shiro:hasPermission name="affair:affairStandard:manage"><li><a href="${ctx}/affair/affairStandard/manageList?treeId=${treeId}&idNumber=${pmId}">个人合格党员标准审核管理</a></li></shiro:hasPermission>--%>
	<shiro:hasPermission name="affair:affairPartyTrain:view"><li class="active"><a href="${ctx}/affair/affairPartyTrain/list?treeId=${treeId}&idNumber=${pmId}">党内培训</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairPartyTrain" action="${ctx}/affair/affairPartyTrain/list?treeId=${treeId}&idNumber=${pmId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>开始时间：</label>
				<input name="beginStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairPartyTrain.beginStartTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairPartyTrain.endStartTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>结束时间：</label>
				<input name="beginEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairPartyTrain.beginEndTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairPartyTrain.endEndTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<%--<li><label>主办单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairPartyTrain.unitId}" labelName="unit" labelValue="${affairPartyTrain.unit}"
								title="单位" url="/affair/affairGeneralSituation/treeData" cssClass="required" isAll="true" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</li>--%>
			<li><label>培训名称：</label>
				<form:input path="trainName" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairPartyTrain:edit">
				<c:if test="${pmId != null && pmId != ''}">
					<li class="btns"><input id="btnAdd" class="btn btn-primary" type="button" value="添加"
											onclick="openAddDialog()"/></li>
				</c:if>
			<li class="btns"><input id="btnDel" class="btn btn-primary" type="button" value="删除"
									onclick="deleteByIds('${ctx}/affair/affairPartyTrain/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="btnPrint" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input id="btnReset" class="btn btn-primary" type="button" value="重置"
									onclick="window.location.href='${ctx}/affair/affairPartyTrain/list?treeId=${treeId}&idNumber=${pmId}'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>主办单位</th>
				<th>培训地点</th>
				<th>培训形式</th>
				<th>培训名称</th>
				<th>培训结果</th>
				<shiro:hasPermission name="affair:affairPartyTrain:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairPartyTrain" varStatus="status">
			<tr>
				<td class="checkTd"><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairPartyTrain.id}"/></td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td>
					<fmt:formatDate value="${affairPartyTrain.startTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairPartyTrain.endTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairPartyTrain.unit}
				</td>
				<td>
					${affairPartyTrain.trainPlace}
				</td>
				<td>
					${affairPartyTrain.trainForm}
				</td>
				<td>
					${affairPartyTrain.trainName}
				</td>
				<td>
					${affairPartyTrain.trainResult}
				</td>
				<td class="handleTd">
						<a href="javascript:" onclick="openDetailDialog('${affairPartyTrain.id}')">查看</a>
					<shiro:hasPermission name="affair:affairPartyTrain:edit">
						<a href="javascript:" onclick="openEditDialog('${affairPartyTrain.id}')">修改</a>
						<a href="${ctx}/affair/affairPartyTrain/delete?id=${affairPartyTrain.id}&treeId=${treeId}&idNumber=${pmId}" onclick="return confirmx('确认要删除该党内培训吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>