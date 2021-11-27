<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>&ldquo;三会一课&rdquo;录入管理</title>
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
		function openAddEditDialog(treeId) {
			url = "iframe:${ctx}/affair/affairYearThreeOne/form?treeId="+treeId;
			top.$.jBox.open(url, "&ldquo;三会一课&rdquo;",1000,650,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairYearThreeOne/list?treeId=${treeId}";
				}
			});
        }

		function openUpEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairYearThreeOne/form?id="+id;
			top.$.jBox.open(url, "&ldquo;三会一课&rdquo;",1000,650,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairYearThreeOne/list?treeId=${treeId}";
				}
			});
		}

        //详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairYearThreeOne/formDetail?id="+id;
			top.$.jBox.open(url, "&ldquo;三会一课&rdquo;",1200,600,{
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
		<li><a href="${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}">党组织</a></li>
		<li><a href="${ctx}/affair/affairMonthStudy/list?treeId=${treeId}">两学一做</a></li>
		<li class="active"><a href="${ctx}/affair/affairYearThreeOnePlan/list?treeId=${treeId}">三会一课</a></li>
		<li><a href="${ctx}/affair/affairCreateBranch/list?treeId=${treeId}">活动载体</a></li>
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
		<li><a href="${ctx}/affair/affairYearThreeOnePlan/list?treeId=${treeId}">年度“三会一课”计划</a></li>
		<li class="active"><a href="${ctx}/affair/affairYearThreeOne?treeId=${treeId}">“三会一课”录入</a></li>
		<shiro:hasPermission name="affair:affairYearThreeOnePlan:manage"><li><a href="${ctx}/affair/affairYearThreeOnePlan/manageList?treeId=${treeId}">年度“三会一课”计划审核</a></li></shiro:hasPermission>
		<shiro:hasPermission name="affair:affairYearThreeOne:manage"><li><a href="${ctx}/affair/affairYearThreeOne/manageList?treeId=${treeId}">“三会一课”录入审核</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairYearThreeOne" action="${ctx}/affair/affairYearThreeOne?treeId=${treeId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会议名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<%--<li><label>党组织名称：</label>
				<sys:treeselect id="partyOrganization" name="partyOrganizationId" value="${affairYearThreeOne.partyOrganizationId}" labelName="partyOrganization" labelValue="${affairYearThreeOne.partyOrganization}"
					title="党组织名称" url="/affair/affairGeneralSituation/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="true" disabled="disabled"/>
			</li>--%>
			<li><label>会议时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairYearThreeOne.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				 --
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				value="<fmt:formatDate value="${affairYearThreeOne.endDate}" pattern="yyyy-MM-dd"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>主持人：</label>
				<form:input path="hold" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>记录人：</label>
				<form:input path="noteTaker" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairYearThreeOne:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog('${treeId}')" value="添加"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairYearThreeOne/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairYearThreeOne/list?treeId=${treeId}'"/></li>
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
				<th>年度</th>
				<th>会议时间</th>
				<th>会议名称</th>
				<th>党组织名称</th>
				<th>主持人</th>
				<th>记录人</th>
				<th>审核状态</th>
				<c:if test="${not empty fns:getUser() && fns:getUser().id eq '1'}">
					<th>创建时间</th>
				</c:if>
				<shiro:hasPermission name="affair:affairYearThreeOne:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairYearThreeOne"  varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairYearThreeOne.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>${affairYearThreeOne.year}</td>
				<td>
					<fmt:formatDate value="${affairYearThreeOne.date}" pattern="yyyy-MM-dd HH:mm"/>
				</td>
				<td>

						${affairYearThreeOne.name}

				</td>
				<td>
					${affairYearThreeOne.partyOrganization}
				</td>
				<td>
					${affairYearThreeOne.hold}
				</td>
				<td>
					${affairYearThreeOne.noteTaker}
				</td>
				<td>
					<c:if test="${affairYearThreeOne.addStatus == '2'}"><%--2：已经提交过了--%>
						<c:choose>
							<c:when test="${affairYearThreeOne.status == '2'}">
								<a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
								   data-content="${affairYearThreeOne.reason}"  style="cursor: pointer;color: red">不通过</a>
							</c:when>
							<c:otherwise>
								${fns:getDictLabel(affairYearThreeOne.status, 'affair_query_shenhe', '')}
							</c:otherwise>
						</c:choose>
					</c:if>
				</td>
				<c:if test="${not empty fns:getUser() && fns:getUser().id eq '1'}">
					<td><fmt:formatDate value="${affairYearThreeOne.updateDate}" pattern="yyyy-MM-dd HH:mm"/></td>
				</c:if>
				<shiro:hasPermission name="affair:affairYearThreeOne:edit">
					<td class="handleTd">
						<a href="javascript:void(0)" onclick="openDetailDialog('${affairYearThreeOne.id}')">查看</a>
						<%--其他人的数据无权操作  自己的数据已提交后不能再重复保存和提交--%>
                        <c:if test="${affairYearThreeOne.createBy.id == fns:getUser().id}">
							<c:if test="${affairYearThreeOne.status != '1'}">
								<%--<c:if test="${affairYearThreeOne.addStatus != '2'}">--%>
								<a href="javascript:void(0)" onclick="openUpEditDialog('${affairYearThreeOne.id}')">修改</a>
								<%--</c:if>--%>
								<a href="${ctx}/affair/affairYearThreeOne/delete?id=${affairYearThreeOne.id}&treeId=${treeId}" onclick="return confirmx('确认要删除该&ldquo;三会一课&rdquo;录入吗？', this.href)">删除</a>
							</c:if>
                        </c:if>
					</td>
				</shiro:hasPermission>
				<%--<c:choose>
					&lt;%&ndash;只有创建人能上报  并且是非 同意状态下&ndash;%&gt;
					<c:when test="${affairWorkLead.createBy.id == fns:getUser().id}">
						<c:if test="${ affairWorkLead.status eq '1'}">
							<a href="${ctx}/affair/affairWorkLead/report?id=${affairWorkLead.id}&treeId=${treeId}"
							   onclick="return confirmx('确认要上报吗？', this.href)">上报</a>
						</c:if>
					</c:when>
					<c:when test="${fns:getUser().id eq 'a699d2e49a3443739e451e294fb66410' ||
										fns:getUser().id eq 'b016b34a09bd45349b3e1ecc5fde9c5d' ||
						 				fns:getUser().id eq '630ee4f59967404488d3a3f1fdec8381' ||
						 				fns:getUser().id eq '8c95559336ab47c688e78b9d4bd04984' ||
						 				fns:getUser().id eq '6eb08ded66314165a692f76d8f660abb'}">
						<c:if test="${affairWorkLead.status eq '2'}">
							<a href="javascript:" onclick="openExamineDialog('${affairWorkLead.id}')">审核</a>
						</c:if>
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>