<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>预备党员管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#print").click(function () {
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$("#contentTable").printThis({
					debug: false,
					importCSS: false,
					importStyle: false,
					printContainer: true,
					loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css", "${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css", "${ctxStatic}/common/jeesite.css"],
					pageTitle: "打印",
					removeInline: false,
					printDelay: 333,
					header: null,
					formValues: false,
					afterPrint: function () {
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
			var url = "iframe:${ctx}/affair/affairProbationaryMember/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairProbationaryMember/form";
			}
			top.$.jBox.open(url, "党员花名册",1000,700,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairProbationaryMember/list?treeId=${treeId}";
				}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairProbationaryMember/formDetail?id="+id;
			top.$.jBox.open(url, "党员花名册",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		function openDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairProbationaryMember/dialog?id="+id, "预备党员转正审核",800,420,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairProbationaryMember/list?treeId=${treeId}";
				}
			});
			//window.parent.window.jBox.close();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairApplicant/list?treeId=${treeId}">入党申请人</a></li>
		<li><a href="${ctx}/affair/affairActivist/list?treeId=${treeId}">入党积极分子</a></li>
		<li><a href="${ctx}/affair/affairDevelopObject/list?treeId=${treeId}">入党发展对象</a></li>
<%--		<li><a href="${ctx}/affair/affairReservePartyMembers/zhuanzheng?treeId=${treeId}">预备党员转正</a></li>--%>
		<li class="active"><a href="${ctx}/affair/affairProbationaryMember/list?treeId=${treeId}">预备党员</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairProbationaryMember" action="${ctx}/affair/affairProbationaryMember/list?treeId=${treeId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>所在党组织：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairProbationaryMember.unitId}"
								labelName="partyBranch" labelValue="${affairProbationaryMember.unit}"
								title="所在党组织" url="/affair/affairGeneralSituation/treeData" cssClass="input-small" allowClear="true"
								notAllowSelectParent="false"/>
			</li>
			<li><label>性别：</label>
				<form:select path="sex" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairProbationaryMember:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()"
										value="添加"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairProbationaryMember/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairProbationaryMember/list?treeId=${treeId}'"/></li>
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
				<th>审核状态</th>
				<shiro:hasPermission name="affair:affairProbationaryMember:view"> <th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairProbationaryMember" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairProbationaryMember.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairProbationaryMember.name}
				</td>
				<td>
					${affairProbationaryMember.idNumber}
				</td>
				<td>
					${affairProbationaryMember.unit}
				</td>
				<td>
					${fns:getDictLabel(affairProbationaryMember.type, 'affair_personnel_category', '')}
				</td>
				<td>
					${affairProbationaryMember.education}
				</td>
				<td>
					<fmt:formatDate value="${affairProbationaryMember.joinDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairProbationaryMember.turnDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${fns:getDictLabel(affairProbationaryMember.status, 'affair_query_shenhe', '')}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairProbationaryMember.id}')">查看</a>
					<shiro:hasPermission name="affair:affairProbationaryMember:edit">
<%--						<c:if test="${affairProbationaryMember.createBy.id == fns:getUser().id}">--%>
							<a href="javascript:void(0)" onclick="openAddEditDialog('${affairProbationaryMember.id}')">修改</a>
							<a href="${ctx}/affair/affairProbationaryMember/deletePerson?id=${affairProbationaryMember.id}&treeId=${treeId}"
							   onclick="return confirmx('确认要删除该党员信息吗？', this.href)">删除</a>
<%--						</c:if>--%>
					</shiro:hasPermission>
					<c:if test="${fns:getUser().id eq '6eb08ded66314165a692f76d8f660abb'|| fns:getUser().id eq '04f9b5355e054b40b7dc7e2a202dc0c3' ||
								fns:getUser().id eq '8c95559336ab47c688e78b9d4bd04984' || fns:getUser().id eq '630ee4f59967404488d3a3f1fdec8381' || fns:getUser().id eq 'b016b34a09bd45349b3e1ecc5fde9c5d'}">
						<c:if test="${affairProbationaryMember.status != '1'}">
						<a href="javascript:void(0)" onclick="openDialog('${affairProbationaryMember.id}')">审核</a>
						</c:if>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>