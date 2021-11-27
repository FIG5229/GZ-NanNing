<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>班子成员管理</title>
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
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairClassMember/export?treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairClassMember/list?treeId=${treeId}");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairClassMember/export?flag=true&treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairClassMember/list?treeId=${treeId}");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairClassMember", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairClassMember/list?treeId=${treeId}"}});
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
			var url = "iframe:${ctx}/affair/affairClassMember/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairClassMember/form";
			}
			top.$.jBox.open(url, "班子成员",1000,650,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairClassMember/list?treeId=${treeId}";
				}
			});
        }
        //详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairClassMember/formDetail?id="+id;
			top.$.jBox.open(url, "班子成员",1000,550,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }

		//审核页面
		function openExamineDialog(id) {
			var url = "iframe:${ctx}/affair/affairClassMember/shenHeDialog?id="+id+"&treeId=${treeId}";
			top.$.jBox.open(url, " 审核",1000,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairClassMember/list?treeId=${treeId}";
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
					<c:choose>
						<c:when test="${treeId == '51a154399faf457f8e1ef12136cf1260' || treeId == '8e66c12b4d3a4fc3ac780c3b44cb1078'}">
							<li><a href="${ctx}/affair/affairAssess/list?treeId=${treeId}&parentId=${parentId}">党总支书记述职测评</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="${ctx}/affair/affairAssess/list?treeId=${treeId}&parentId=${parentId}">党支部书记述职测评</a></li>
						</c:otherwise>
					</c:choose>
					<shiro:hasPermission name="affair:affairLifeMeet:view"><li><a href="${ctx}/affair/affairLifeMeet/list?treeId=${treeId}">组织生活会</a></li></shiro:hasPermission>
					<li><a href="${ctx}/affair/affairComment/list?treeId=${treeId}">民主评议</a></li>
					<li class="active"><a href="${ctx}/affair/affairElection/list?treeId=${treeId}">换届选举</a></li>
					<shiro:hasPermission name="affair:affairIdeaAnalysis:view"><li><a href="${ctx}/affair/affairIdeaAnalysis/list?treeId=${treeId}">党员队伍思想分析</a></li></shiro:hasPermission>
					<%--1:专报简报 2：调研文章--%>
					<%--<li><a href="${ctx}/affair/affairResearchArticle?flag=1&treeId=${treeId}">专报简报</a></li>
                    <li><a href="${ctx}/affair/affairResearchArticle?flag=2&treeId=${treeId}">调研文章</a></li>--%>
					<li><a href="${ctx}/affair/affairOrgRewardPunish/list?treeId=${treeId}">组织奖惩信息</a></li>
					<li><a href="${ctx}/affair/affairWorkDoneManage/list?treeId=${treeId}">工作总结</a></li>
					<li><a href="${ctx}/affair/affairSystemConstruction/list?treeId=${treeId}&parentId=${parentId}">制度建设</a></li>
				</c:otherwise>
			</c:choose>
	</ul>
	<ul class="nav nav-tabs">
		<c:choose>
			<c:when test="${treeId == '1' || treeId == '2' || treeId == '34' || treeId ==  '95' || treeId == '156'}">
				<li><a href="${ctx}/affair/affairSjPartyRepresentative?treeId=${treeId}">上级党代表</a></li>
				<li><a href="${ctx}/affair/affairPartyRepresentative?treeId=${treeId}">本级党代表</a></li>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
		<li class="active"><a href="${ctx}/affair/affairClassMember?treeId=${treeId}">班子成员</a></li>
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
	<form:form id="searchForm" modelAttribute="affairClassMember" action="${ctx}/affair/affairClassMember?treeId=${treeId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="班子成员表.xlsx"/>
		<ul class="ul-form">
			<li><label>党组织名称：</label>
				<sys:treeselect id="partyOrganization" name="partyOrganizationId" value="${affairClassMember.partyOrganizationId}" labelName="partyOrganization" labelValue="${affairClassMember.partyOrganization}"
					title="党组织名称" url="/affair/affairGeneralSituation/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>党组织书记：</label>
				<form:input path="shuji" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label class="width120">党组织副书记：</label>
				<form:input path="fushuji" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>组织委员：</label>
				<form:input path="zzwy" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>纪检委员：</label>
				<form:input path="jjwy" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>宣传委员：</label>
				<form:input path="xcwy" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>青年委员：</label>
				<form:input path="yqwy" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairClassMember:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()" value="添加"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairClassMember/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairClassMember/list?treeId=${treeId}'"/></li>
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
				<th>党组织书记</th>
				<th>党组织副书记</th>
				<th>组织委员</th>
				<th>纪检委员</th>
				<th>宣传委员</th>
				<th>青年委员</th>
				<th>审核状态</th>
				<shiro:hasPermission name="affair:affairClassMember:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairClassMember" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairClassMember.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>

						${affairClassMember.partyOrganization}

				</td>
				<td>
					${affairClassMember.shuji}
				</td>
				<td>
					${affairClassMember.fushuji}
				</td>
				<td>
					${affairClassMember.zzwy}
				</td>
				<td>
					${affairClassMember.jjwy}
				</td>
				<td>
					${affairClassMember.xcwy}
				</td>
				<td>
					${affairClassMember.yqwy}
				</td>
				<td>
						${fns:getDictLabel(affairClassMember.status, 'affair_party_report_status', '')}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairClassMember.id}')">查看</a>
					<shiro:hasPermission name="affair:affairClassMember:edit">
                	    <c:if test="${affairClassMember.createBy.id == fns:getUser().id}">
                	        <a href="javascript:void(0)" onclick="openAddEditDialog('${affairClassMember.id}')">修改</a>
                	        <a href="${ctx}/affair/affairClassMember/delete?id=${affairClassMember.id}&treeId=${treeId}"
                	           onclick="return confirmx('确认要删除该班子成员吗？', this.href)">删除</a>
                	    </c:if>
						<c:choose>
							<%--只有创建人能上报  并且是非 同意状态下--%>
							<c:when test="${affairClassMember.createBy.id == fns:getUser().id}">
								<c:if test="${ affairClassMember.status eq '1'}">
									<a href="${ctx}/affair/affairClassMember/report?id=${affairClassMember.id}&treeId=${treeId}"
									   onclick="return confirmx('确认要上报吗？', this.href)">上报</a>
								</c:if>
							</c:when>
							<c:when test="${fns:getUser().id eq 'a699d2e49a3443739e451e294fb66410' ||
											fns:getUser().id eq 'b016b34a09bd45349b3e1ecc5fde9c5d' ||
							 				fns:getUser().id eq '630ee4f59967404488d3a3f1fdec8381' ||
							 				fns:getUser().id eq '8c95559336ab47c688e78b9d4bd04984' ||
							 				fns:getUser().id eq '6eb08ded66314165a692f76d8f660abb'}">
								<c:if test="${affairClassMember.status eq '2'}">
									<a href="javascript:" onclick="openExamineDialog('${affairClassMember.id}')">审核</a>
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