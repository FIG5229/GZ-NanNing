<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>上级党代表管理</title>
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
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "上级党代表",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairSjPartyRepresentative/list?treeId=${treeId}";
				}
			});
		}

		//审核页面
		function openExamineDialog(id) {
			var url = "iframe:${ctx}/affair/affairSjPartyRepresentative/shenHeDialog?id="+id+"&treeId=${treeId}";
			top.$.jBox.open(url, " 审核",1000,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairSjPartyRepresentative/list?treeId=${treeId}";
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<c:choose>
			<c:when test="${treeId == '1' || treeId == '2' || treeId == '34' || treeId ==  '95' || treeId == '156'}">
				<li><a href="${ctx}/affair/affairWorkLead/list?treeId=${treeId}">主体责任落实</a></li>
				<li class="active"><a href="${ctx}/affair/affairElection/list?treeId=${treeId}">换届选举</a></li>
				<c:if test="${treeId != '2'}">
					<shiro:hasPermission name="affair:affairLifeMeet:view"><li><a href="${ctx}/affair/affairLifeMeet/list?treeId=${treeId}">民主生活会</a></li></shiro:hasPermission>
				</c:if>
				<li><a href="${ctx}/affair/affairAssess/list?treeId=${treeId}">党委书记述职测评</a></li>
				<li><a href="${ctx}/affair/affairMemberPartyCommittee/list?treeId=${treeId}">党委委员</a>
				<li><a href="${ctx}/affair/affairOrgRewardPunish/list?treeId=${treeId}">组织奖惩信息</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}">党组织</a></li>
				<li><a href="${ctx}/affair/affairMonthStudy/list?treeId=${treeId}">两学一做</a></li>
				<li><a href="${ctx}/affair/affairYearThreeOnePlan/list?treeId=${treeId}">三会一课</a></li>
				<li><a href="${ctx}/affair/affairCreateBranch/list?treeId=${treeId}">活动载体</a></li>
				<li><a href="${ctx}/affair/affairAssess/list?treeId=${treeId}">党委书记述职测评</a></li>
				<shiro:hasPermission name="affair:affairLifeMeet:view"><li><a href="${ctx}/affair/affairLifeMeet/list?treeId=${treeId}">组织生活会</a></li></shiro:hasPermission>
				<li><a href="${ctx}/affair/affairComment/list?treeId=${treeId}">民主评议</a></li>
				<li class="active"><a href="${ctx}/affair/affairElection/list?treeId=${treeId}">换届选举</a></li>
				<shiro:hasPermission name="affair:affairIdeaAnalysis:view"><li><a href="${ctx}/affair/affairIdeaAnalysis/list?treeId=${treeId}">党员队伍思想分析</a></li></shiro:hasPermission>
				<li><a href="${ctx}/affair/affairOrgRewardPunish/list?treeId=${treeId}">组织奖惩信息</a></li>
				<li><a href="${ctx}/affair/affairWorkDoneManage/list?treeId=${treeId}">工作总结管理</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairSjPartyRepresentative?treeId=${treeId}">上级党代表</a></li>
		<li><a href="${ctx}/affair/affairPartyRepresentative?treeId=${treeId}">本级党代表</a></li>
		<li><a href="${ctx}/affair/affairClassMember?treeId=${treeId}">班子成员</a></li>
		<c:choose>
			<c:when test="${treeId == '1' || treeId == '2' || treeId == '34' || treeId ==  '95' || treeId == '156'}">
				<li><a href="${ctx}/affair/affairElection/list?treeId=${treeId}">换届选举</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="${ctx}/affair/affairElection/list?treeId=${treeId}">换届选举</a></li>
				<shiro:hasPermission name="affair:affairElection:manage">
					<li><a href="${ctx}/affair/affairElection/manageList?treeId=${treeId}">换届选举审核</a></li>
				</shiro:hasPermission>
			</c:otherwise>
		</c:choose>
	</ul>
	<form:form id="searchForm" modelAttribute="affairSjPartyRepresentative" action="${ctx}/affair/affairSjPartyRepresentative?treeId=${treeId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>性别：</label>
				<form:select path="sex" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>届次：</label>
				<form:input path="session" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairSjPartyRepresentative:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairSjPartyRepresentative/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairSjPartyRepresentative/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="导入" /></li>
			</shiro:hasPermission>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairSjPartyRepresentative/list?treeId=${treeId}'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this)'/>全选</th>
				<th>序号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>民族</th>
				<th>年龄</th>
				<th>学历</th>
				<th>是否在职</th>
				<th>届次</th>
				<th>审核状态</th>
				<shiro:hasPermission name="affair:affairSjPartyRepresentative:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairSjPartyRepresentative" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairSjPartyRepresentative.id}"/></td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td>
					${affairSjPartyRepresentative.name}
				</td>
				<td>
					${fns:getDictLabel(affairSjPartyRepresentative.sex, 'sex', '')}
				</td>
				<td>
					${fns:getDictLabel(affairSjPartyRepresentative.nation, 'nation', '')}
				</td>
				<td>
					${affairSjPartyRepresentative.age}
				</td>
				<td>
					${affairSjPartyRepresentative.education}
				</td>
				<td>
					${fns:getDictLabel(affairSjPartyRepresentative.isWork, 'yes_no', '')}
				</td>
				<td>
					${affairSjPartyRepresentative.session}
				</td>
				<td>
						${fns:getDictLabel(affairSjPartyRepresentative.status, 'affair_party_report_status', '')}
				</td>
				<shiro:hasPermission name="affair:affairSjPartyRepresentative:edit">
					<td class="handleTd">
						<a href="javascript:void(0)"
						   onclick="openForm('${ctx}/affair/affairSjPartyRepresentative/form?id=${affairSjPartyRepresentative.id}')">修改</a>
						<a href="${ctx}/affair/affairSjPartyRepresentative/delete?id=${affairSjPartyRepresentative.id}&treeId=${treeId}" onclick="return confirmx('确认要删除该上级党代表吗？', this.href)">删除</a>
						<c:choose>
							<%--只有创建人能上报  并且是非 同意状态下--%>
							<c:when test="${affairSjPartyRepresentative.createBy.id == fns:getUser().id}">
								<c:if test="${ affairSjPartyRepresentative.status eq '1'}">
									<a href="${ctx}/affair/affairSjPartyRepresentative/report?id=${affairSjPartyRepresentative.id}&treeId=${treeId}"
									   onclick="return confirmx('确认要上报吗？', this.href)">上报</a>
								</c:if>
							</c:when>
							<c:when test="${fns:getUser().id eq 'a699d2e49a3443739e451e294fb66410' ||
										fns:getUser().id eq 'b016b34a09bd45349b3e1ecc5fde9c5d' ||
						 				fns:getUser().id eq '630ee4f59967404488d3a3f1fdec8381' ||
						 				fns:getUser().id eq '8c95559336ab47c688e78b9d4bd04984' ||
						 				fns:getUser().id eq '6eb08ded66314165a692f76d8f660abb'}">
								<c:if test="${affairSjPartyRepresentative.status eq '2'}">
									<a href="javascript:" onclick="openExamineDialog('${affairSjPartyRepresentative.id}')">审核</a>
								</c:if>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>