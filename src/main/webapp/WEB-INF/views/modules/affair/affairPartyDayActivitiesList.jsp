<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党日活动管理</title>
	<meta name="decorator" content="default"/>
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
		//添加修改弹窗
		function openAddEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairPartyDayActivities/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairPartyDayActivities/form";
			}
			top.$.jBox.open(url, "党日活动",1100,650,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairPartyDayActivities/list?treeId=${treeId}";
				}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairPartyDayActivities/formDetail?id="+id;
			top.$.jBox.open(url, "党日活动",900,650,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }

		//审核页面
		function openExamineDialog(id) {
			var url = "iframe:${ctx}/affair/affairPartyDayActivities/examineView?id="+id+"&treeId=${treeId}";
			top.$.jBox.open(url, "党日活动审核",1000,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairPartyDayActivities/list?treeId=${treeId}";
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}">党组织</a></li>
		<li><a href="${ctx}/affair/affairMonthStudy/list?treeId=${treeId}">两学一做</a></li>
		<li><a href="${ctx}/affair/affairYearThreeOnePlan/list?treeId=${treeId}">三会一课</a></li>
		<li class="active"><a href="${ctx}/affair/affairCreateBranch/list?treeId=${treeId}">活动载体</a></li>
		<c:choose>
			<c:when test="${treeId == '1' || treeId == '2' || treeId == '34' || treeId ==  '95' || treeId == '156'}">
				<li><a href="${ctx}/affair/affairAssess/list?treeId=${treeId}&parentId=${parentId}">党委书记述职测评</a></li>
			</c:when>
			<c:when test="${treeId == '51a154399faf457f8e1ef12136cf1260' || treeId == '8e66c12b4d3a4fc3ac780c3b44cb1078'}">
				<li><a href="${ctx}/affair/affairAssess/list?treeId=${treeId}&parentId=${parentId}">党总支书记述职测评</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="${ctx}/affair/affairAssess/list?treeId=${treeId}&parentId=${parentId}">党支部书记述职测评</a></li>
			</c:otherwise>
		</c:choose>
		<shiro:hasPermission name="affair:affairLifeMeet:view"><li><a href="${ctx}/affair/affairLifeMeet/list?treeId=${treeId}">组织生活会</a></li></shiro:hasPermission>
		<li><a href="${ctx}/affair/affairComment/list?treeId=${treeId}">民主评议</a></li>
		<li><a href="${ctx}/affair/affairElection/list?treeId=${treeId}">换届选举</a></li>
		<shiro:hasPermission name="affair:affairIdeaAnalysis:view"><li><a href="${ctx}/affair/affairIdeaAnalysis/list?treeId=${treeId}">党员队伍思想分析</a></li></shiro:hasPermission>
		<li><a href="${ctx}/affair/affairOrgRewardPunish/list?treeId=${treeId}">组织奖惩信息</a></li>
		<li><a href="${ctx}/affair/affairWorkDoneManage/list?treeId=${treeId}">工作总结</a></li>
		<li><a href="${ctx}/affair/affairSystemConstruction/list?treeId=${treeId}&parentId=${parentId}">制度建设</a></li>
	</ul>
	<ul class="nav nav-tabs">
		<shiro:hasPermission name="affair:affairCreateBranch:view"><li><a href="${ctx}/affair/affairCreateBranch/list?treeId=${treeId}">品牌创建</a></li></shiro:hasPermission>
		<shiro:hasPermission name="affair:affairVolunteerService:view"><li><a href="${ctx}/affair/affairVolunteerService?treeId=${treeId}">志愿服务</a></li></shiro:hasPermission>
		<li><a href="${ctx}/affair/affairChuangGangJianQu?treeId=${treeId}">创岗建区</a></li>
		<shiro:hasPermission name="affair:affairPartyDayActivities:view"><li class="active"><a href="${ctx}/affair/affairPartyDayActivities/list?treeId=${treeId}">党日活动</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairPartyDayActivities" action="${ctx}/affair/affairPartyDayActivities/?treeId=${treeId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>活动名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label class="width120">活动开始时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairPartyDayActivities.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				--
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairPartyDayActivities.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>党组织名称：</label>
				<sys:treeselect id="partyBranch" name="partyBranchId" value="${affairPartyDayActivities.partyBranchId}" labelName="partyBranch" labelValue="${affairPartyDayActivities.partyBranch}"
								title="党组织名称" url="/affair/affairGeneralSituation/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>举办单位：</label>
				<sys:treeselect id="holdUnit" name="holdUnitId" value="${affairPartyDayActivities.holdUnitId}" labelName="holdUnit" labelValue="${affairPartyDayActivities.holdUnit}"
								title="举办单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>活动地点：</label>
				<form:input path="place" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>参加人员：</label>
				<%--<form:input path="joinPerson" htmlEscape="false" class="input-medium"/>--%>
				<sys:treeselect id="joinPerson" name="joinPersonId" value="${affairPartyDayActivities.joinPersonId}" labelName="joinPerson" labelValue="${affairPartyDayActivities.joinPerson}"
								title="参加人员" url="/sys/office/treeData?type=3" allowClear="true" checked="true" notAllowSelectParent="true" cssStyle="width:300px;" />
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairPartyDayActivities:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()" value="添加"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairPartyDayActivities/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairPartyDayActivities/list?treeId=${treeId}'"/></li>
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
				<th>活动开始时间</th>
				<th>党组织名称</th>
				<th>举办单位</th>
				<th>活动地点</th>
				<th>审核状态</th>
				<shiro:hasPermission name="affair:affairPartyDayActivities:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairPartyDayActivities" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairPartyDayActivities.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairPartyDayActivities.name}
				</td>
				<td>
					<fmt:formatDate value="${affairPartyDayActivities.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${affairPartyDayActivities.partyBranch}
				</td>
				<td>
					${affairPartyDayActivities.holdUnit}
				</td>
				<td>
					${affairPartyDayActivities.place}
				</td>
				<td>
						${fns:getDictLabel(affairPartyDayActivities.examineStatus, 'affair_party_report_status', '')}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairPartyDayActivities.id}')">查看</a>
					<shiro:hasPermission name="affair:affairPartyDayActivities:edit">
						<c:if test="${affairPartyDayActivities.createBy.id == fns:getUser().id}">
							<a href="javascript:void(0)" onclick="openAddEditDialog('${affairPartyDayActivities.id}')">修改</a>
							<a href="${ctx}/affair/affairPartyDayActivities/delete?id=${affairPartyDayActivities.id}&treeId=${treeId}"
							   onclick="return confirmx('确认要删除该志愿服务活动吗？', this.href)">删除</a>
						</c:if>
						<c:choose>
							<%--只有创建人能上报  并且是非 同意状态下--%>
							<c:when test="${affairPartyDayActivities.createBy.id == fns:getUser().id}">
								<c:if test="${affairPartyDayActivities.examineStatus eq '1'}">
									<a href="${ctx}/affair/affairPartyDayActivities/report?id=${affairPartyDayActivities.id}&treeId=${treeId}"
									   onclick="return confirmx('确认要上报该党日活动吗？', this.href)">上报</a>
								</c:if>
							</c:when>
							<c:when test="${fns:getUser().id eq '6eb08ded66314165a692f76d8f660abb'|| fns:getUser().id eq '04f9b5355e054b40b7dc7e2a202dc0c3' ||
						 fns:getUser().id eq '8c95559336ab47c688e78b9d4bd04984' || fns:getUser().id eq '630ee4f59967404488d3a3f1fdec8381' || fns:getUser().id eq 'b016b34a09bd45349b3e1ecc5fde9c5d'||
						  fns:getUser().id eq 'b5395bf9f65f4c93a47e37e4a2a5e1f3' || fns:getUser().id eq '0294b5bc43d44b71a2c952f2212e5c57' || fns:getUser().id eq 'a699d2e49a3443739e451e294fb66410' ||
						  fns:getUser().id eq '6502f47ca7bf45539a848135ee3c6bc3' || fns:getUser().id eq 'fcaf9421273643d587baaa88735fe661' || fns:getUser().id eq 'ff371386d33a407b9c1e652b613de2bc'}">
								<c:if test="${affairPartyDayActivities.examineStatus != '1'}">
									<a href="javascript:" onclick="openExamineDialog('${affairPartyDayActivities.id}')">审核</a>
								</c:if>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>