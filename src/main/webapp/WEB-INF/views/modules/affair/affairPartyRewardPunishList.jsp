<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党员奖惩信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairPartyRewardPunish/export?treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPartyRewardPunish/list?treeId=${treeId}");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairPartyRewardPunish/export?flag=true&treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPartyRewardPunish/list?treeId=${treeId}");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairPartyRewardPunish", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairPartyRewardPunish/list?treeId=${treeId}&idNumber=${pmId}"}});
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
			var url = "iframe:${ctx}/affair/affairPartyRewardPunish/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairPartyRewardPunish/form";
			}
			if (${pmId !=null &&  pmId != ''}){
				url="iframe:${ctx}/affair/affairPartyRewardPunish/form?idNumber=${pmId}"
			}
			top.$.jBox.open(url, "党员奖惩信息",1000,760,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairPartyRewardPunish/list?treeId=${treeId}&idNumber=${pmId}";
				}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairPartyRewardPunish/formDetail?id="+id;
			top.$.jBox.open(url, "党员奖惩信息",1000,450,{
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
				},closed:function (){self.location.href="${ctx}/affair/affairPartyRewardPunish/list?treeId=${treeId}&idNumber=${pmId}"}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%--<li><a href="${ctx}/affair/affairRelationshipTransfer/">系统内组织关系移交转接</a></li>
		<li><a href="${ctx}/affair/affairComment/">民主评议</a></li>
		<li><a href="${ctx}/affair/affairBelongTo/">党员组织隶属</a></li>
		<li class="active"><a href="${ctx}/affair/affairPartyRewardPunish/">党员奖惩信息</a></li>
		<li><a href="${ctx}/affair/affairStandard/">个人合格党员标准</a></li>
		<shiro:hasAnyRoles name="nanningju_relationship_transfer,nanning_relationship_transfer,liuzhouchu_relationship_transfer,beihaichu_relationship_transfer"><li><a href="${ctx}/affair/affairRelationshipTransfer/manageList">系统内组织关系移交转接管理</a></li></shiro:hasAnyRoles>
		<shiro:hasPermission name="affair:affairStandard:manage"><li><a href="${ctx}/affair/affairStandard/manageList">个人合格党员标准审核管理</a></li></shiro:hasPermission>--%>
			<c:if test="${pmId == null || pmId == ''}">
				<li><a href="${ctx}/affair/affairPartyMember/list?partyBranchId=${treeId}&cardNum=${pmId}">党员${pmId}</a></li>
			</c:if>
			<shiro:hasPermission name="affair:affairRelationshipTransfer:view">  <li><a href="${ctx}/affair/affairRelationshipTransfer?treeId=${treeId}&idNumber=${pmId}">组织关系转接</a></li></shiro:hasPermission>
			<shiro:hasPermission name="affair:affairBelongTo:view">  <li><a href="${ctx}/affair/affairBelongTo?treeId=${treeId}&idNumber=${pmId}">组织隶属</a></li></shiro:hasPermission>
			<shiro:hasPermission name="affair:affairPartyRewardPunish:view">  <li class="active"><a href="${ctx}/affair/affairPartyRewardPunish?treeId=${treeId}&idNumber=${pmId}">奖惩信息</a>
        </li></shiro:hasPermission>
<%--		<li><a href="${ctx}/affair/affairStandard?treeId=${treeId}&idNumber=${pmId}">个人合格党员标准</a></li>--%>
			<shiro:hasAnyRoles
					name="nanningju_relationship_transfer,nanning_relationship_transfer,liuzhouchu_relationship_transfer,beihaichu_relationship_transfer">
                <li><a href="${ctx}/affair/affairRelationshipTransfer/manageList?treeId=${treeId}&idNumber=${pmId}">组织关系转接审核管理</a>
				</li>
			</shiro:hasAnyRoles>
<%--		<shiro:hasPermission name="affair:affairStandard:manage"><li><a href="${ctx}/affair/affairStandard/manageList?treeId=${treeId}&idNumber=${pmId}">个人合格党员标准审核管理</a></li></shiro:hasPermission>--%>
			<shiro:hasPermission name="affair:affairPartyTrain:view"><li><a href="${ctx}/affair/affairPartyTrain/list?treeId=${treeId}&idNumber=${pmId}">党内培训</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairPartyRewardPunish" action="${ctx}/affair/affairPartyRewardPunish?treeId=${treeId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="党员奖惩信息表.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>身份证号：</label>
				<form:input path="idNumber" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>奖惩名称：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>批准党委：</label>
				<sys:treeselect id="approvalOrg" name="approvalOrgId" value="${affairPartyRewardPunish.approvalOrgId}" labelName="approvalOrg" labelValue="${affairPartyRewardPunish.approvalOrg}"
					title="批准党委" url="/affair/affairGeneralSituation/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
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
			<li><label>批准时间：</label>
				<input name="approvalStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairPartyRewardPunish.approvalStartTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				 --
				<input name="approvalEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairPartyRewardPunish.approvalEndTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairPartyRewardPunish:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()" value="添加"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairPartyRewardPunish/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairPartyRewardPunish/list?treeId=${treeId}&idNumber=${pmId}'"/></li>
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
				<th>性别</th>
				<th>身份证号</th>
				<%--<th>警号</th>--%>
				<th>奖惩标题</th>
				<th>党组织</th>
				<th>批准党委</th>
				<th>奖惩类别</th>
				<th>批准时间</th>
				<th>推送状态</th>
				<shiro:hasPermission name="affair:affairPartyRewardPunish:edit"><th id="handleTh" >操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairPartyRewardPunish" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairPartyRewardPunish.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>

						${affairPartyRewardPunish.name}

				</td>
				<td>
					${fns:getDictLabel(affairPartyRewardPunish.sex, 'sex', '')}
				</td>
				<td>
					${affairPartyRewardPunish.idNumber}
				</td>
				<%--<td>
					${affairPartyRewardPunish.policeNo}
				</td>--%>
				<td>
					${affairPartyRewardPunish.title}
				</td>
				<td>
					${affairPartyRewardPunish.partyOrganization}
				</td>
				<td>
					<%--
					${affairPartyRewardPunish.approvalOrg}--%>
						${fns:getDictLabel(affairPartyRewardPunish.approvalOrgId, 'affair_paryt_committee', '')}
				</td>
				<td>
					${fns:getDictLabel(affairPartyRewardPunish.awardCategory, 'award_category', '')}
				</td>
				<td>
					<fmt:formatDate value="${affairPartyRewardPunish.approvalTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${fns:getDictLabel(affairPartyRewardPunish.pushType, 'push_types', '')}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairPartyRewardPunish.id}')">查看</a>
				<shiro:hasPermission name="affair:affairPartyRewardPunish:edit">
					<c:if test="${affairPartyRewardPunish.createBy.id == fns:getUser().id}">
						<a href="javascript:void(0)" onclick="openAddEditDialog('${affairPartyRewardPunish.id}')">修改</a>
						<a href="${ctx}/affair/affairPartyRewardPunish/delete?id=${affairPartyRewardPunish.id}&treeId=${treeId}&idNumber=${pmId}"
						   onclick="return confirmx('确认要删除该党员奖惩信息吗？', this.href)">删除</a>
<%--						<a href="javascript:void(0)"--%>
<%--						   onclick="openForm('${ctx}/exam/examJcInfoPlus/partyRewardPunishForm?id=${affairPartyRewardPunish.id}')">推送到奖惩信息库</a>--%>
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