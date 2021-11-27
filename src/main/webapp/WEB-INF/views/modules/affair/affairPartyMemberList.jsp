<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党员花名册管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairPartyMember/export");
								/*form 表单有字段 无需重复添加*/
								<%--$("#searchForm").attr("action","${ctx}/affair/affairPartyMember/export?partyBranchId=${treeId}&cardNum=${pmId}");--%>
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPartyMember/list?");
								<%--$("#searchForm").attr("action","${ctx}/affair/affairPartyMember/list?partyBranchId=${treeId}&cardNum=${pmId}");--%>
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairPartyMember/export?flag=true");
								<%--$("#searchForm").attr("action","${ctx}/affair/affairPartyMember/export?flag=true&partyBranchId=${treeId}&cardNum=${pmId}");--%>
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPartyMember/list");
								<%--$("#searchForm").attr("action","${ctx}/affair/affairPartyMember/list?partyBranchId=${treeId}&cardNum=${pmId}");--%>
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairPartyMember", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairPartyMember/list?partyBranchId=${treeId}&cardNum=${pmId}"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
		}
		//添加修改详情弹窗
		function openAddEditDialog(id,flag) {
			var url = "";
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairPartyMember/form";
			}else{
				if (flag == "1"){
					url = "iframe:${ctx}/affair/affairPartyMember/form?id="+id+"&flag="+flag;
				}else{
					url = "iframe:${ctx}/affair/affairPartyMember/form?id="+id;
				}
			}
			top.$.jBox.open(url, "党员花名册",1000,700,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairPartyMember/list?partyBranchId=${treeId}&cardNum=${pmId}";
				}
			});
		}
		//添加修改弹窗
		function openAddEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairPartyMember/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairPartyMember/form";
			}
			top.$.jBox.open(url, "党员花名册",1000,700,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairPartyMember/list?partyBranchId=${treeId}&cardNum=${pmId}";
				}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairPartyMember/formDetail?id="+id;
			top.$.jBox.open(url, "党员花名册",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}	//详情弹窗
		function openDetailInfoDialog(id) {
			var url = "iframe:${ctx}/affair/affairPartyMember/list?id="+id+"&isDialog=isDialog";
			top.$.jBox.open(url, "党员花名册",1200,600,{
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
			<li class="active"><a href="${ctx}/affair/affairPartyMember/list?partyBranchId=${treeId}&cardNum=${pmId}">党员${pmId}</a></li>
		</c:if>
		<shiro:hasPermission name="affair:affairRelationshipTransfer:view"><li><a href="${ctx}/affair/affairRelationshipTransfer?treeId=${treeId}&idNumber=${pmId}">组织关系转接</a></li></shiro:hasPermission>
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
	<form:form id="searchForm" modelAttribute="affairPartyMember" action="${ctx}/affair/affairPartyMember/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="党员花名册表.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>身份证号：</label>
				<form:input path="cardNum" htmlEscape="false" class="input-medium" readonly="true"/>
			</li>
			<li><label>性别：</label>
				<form:select path="sex" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>所在党支部：</label>
				<sys:treeselect id="partyBranch" name="partyBranchId" value="${affairPartyMember.partyBranchId}" labelName="partyBranch" labelValue="${affairPartyMember.partyBranch}"
					title="所在党支部" url="/affair/affairGeneralSituation/treeData?flag=pm" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>人员类别：</label>
				<form:select path="personnelCategory" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_personnel_category')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label class="width120">加入党组织日期：</label>
				<input name="joinStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairPartyMember.joinStartDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				 --
				<input name="joinEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairPartyMember.joinEndDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label class="width120">转为正式党员日期：</label>
				<input name="turnStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairPartyMember.turnStartDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				 --
				<input name="turnEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairPartyMember.turnEndDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<%--<shiro:hasPermission name="affair:affairPartyMember:add">--%>
			<shiro:hasPermission name="affair:affairPartyMember:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()" value="添加"/></li>
			<%--</shiro:hasPermission>--%>

				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairPartyMember/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairPartyMember/list?partyBranchId=${treeId}&cardNum=${pmId}'"/></li>
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
				<th>身份证号</th>
				<th>所在党支部</th>
				<th>人员类别</th>
				<th>学历</th>
				<th>加入党组织日期</th>
				<th>转为正式党员日期</th>
				<shiro:hasPermission name="affair:affairPartyMember:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairPartyMember" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairPartyMember.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>

						${affairPartyMember.name}

				</td>
				<td>
					${affairPartyMember.cardNum}
				</td>
				<td>
					${affairPartyMember.partyBranch}
				</td>
				<td>
					<c:if test="${'1'.equals(affairPartyMember.personnelType)}">
						${fns:getDictLabel(affairPartyMember.personnelCategory, 'affair_personnel_category', '')}
					</c:if>
					<c:if test="${'2'.equals(affairPartyMember.personnelType)}">
						${fns:getDictLabel(affairPartyMember.personnelCategory2, 'affair_personnel_category2', '')}
					</c:if>
				</td>
				<td>
					${fns:getDictLabel(affairPartyMember.education, 'affair_party_member_xueli', '')}
				</td>
				<td>
					<fmt:formatDate value="${affairPartyMember.joinDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairPartyMember.turnDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairPartyMember.id}')">查看</a>
					<a href="javascript:" onclick="openDetailInfoDialog('${affairPartyMember.id}')">党员信息</a>
				<shiro:hasPermission name="affair:affairPartyMember:edit">
<%--					<c:if test="${affairPartyMember.createBy.id == fns:getUser().id}">--%>
						<a href="javascript:void(0)" onclick="openAddEditDialog('${affairPartyMember.id}')">修改</a>
						<a href="${ctx}/affair/affairPartyMember/delete?id=${affairPartyMember.id}&partyBranchId=${treeId}&cardNum=${pmId}"
						   onclick="return confirmx('确认要删除该党员信息吗？', this.href)">删除</a>
<%--					</c:if>--%>
				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>