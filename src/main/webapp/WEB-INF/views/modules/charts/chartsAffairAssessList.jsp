<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title></title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairAssess/export?treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action", "${ctx}/affair/affairAssess/list?treeId=${treeId}");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairAssess/export?flag=true&treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action", "${ctx}/affair/affairAssess/list?treeId=${treeId}");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairAssess", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText: "导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
					closed: function () {
						self.location.href = "${ctx}/affair/affairAssess/list?treeId=${treeId}"
					}
				});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        };
		//添加修改弹窗
		function openAddEditDialog(title,id) {
			var url = "iframe:${ctx}/affair/affairAssess/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairAssess/form";
			}
			top.$.jBox.open(url, title,1000,650,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href = "${ctx}/affair/affairAssess/list?treeId=${treeId}";
				}
			});
		};
		//详情弹窗
		function openDetailDialog(title,id) {
			var url = "iframe:${ctx}/affair/affairAssess/formDetail?id="+id;
			top.$.jBox.open(url, title,1000,400,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
	</script>
</head>
<body>

<form:form id="searchForm" modelAttribute="affairAssess" action="${ctx}/sys/charts/assessPie/detail"
		   method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="党委书记述职测评表.xlsx"/>
		<%--<ul class="ul-form">
			<li><label>党组织书记：</label>
				<form:input path="shuji" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>党组织名称：</label>
				<sys:treeselect id="partyOrganization" name="partyOrganizationId" value="${affairAssess.partyOrganizationId}" labelName="partyOrganization" labelValue="${affairAssess.partyOrganization}"
					title="党组织名称" url="/affair/affairGeneralSituation/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>测评等次：</label>
				<form:select path="grade" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_assess_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>述职年度：</label>
				<input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairAssess.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairAssess:edit">
				<c:choose>
					<c:when test="${treeId == '1' || treeId == '34' || treeId ==  '95' || treeId == '156' || treeId == '2'}">
						<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog('党委书记测评')" value="添加"/></li>
					</c:when>
					<c:when test="${treeId == '51a154399faf457f8e1ef12136cf1260' || treeId == '8e66c12b4d3a4fc3ac780c3b44cb1078'}">
						<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog('党总支书记测评')" value="添加"/></li>
					</c:when>
					<c:otherwise>
						<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog('党支部书记测评')" value="添加"/></li>
					</c:otherwise>
				</c:choose>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairAssess/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input class="btn btn-primary" type="button" value="重置"
									onclick="window.location.href='${ctx}/affair/affairAssess/list?treeId=${treeId}'"/></li>
			<li class="clearfix"></li>
		</ul>--%>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
<%--				<th>--%>
<%--					<input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>--%>
<%--				</th>--%>
				<th>序号</th>
				<th>党组织书记</th>
				<th>党组织名称</th>
				<th>测评时间</th>
				<th>参加测评人数</th>
				<th>测评等次</th>
<%--				<shiro:hasPermission name="affair:affairAssess:edit"><th id="handleTh">操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairAssess" varStatus="status">
			<tr>
				<%--<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairAssess.id}"/>
				</td>--%>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairAssess.shuji}
				</td>
				<td>
					${affairAssess.partyOrganization}
				</td>
				<td>
					<fmt:formatDate value="${affairAssess.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairAssess.personNum}
				</td>
				<td>
					${fns:getDictLabel(affairAssess.grade, 'affair_assess_grade', '')}
				</td>
			<%--	<td class="handleTd">
					<c:choose>
						<c:when test="${treeId == '1' || treeId == '34' || treeId ==  '95' || treeId == '156' || treeId == '2'}">
							<a onclick="openDetailDialog('党委书记测评','${affairAssess.id}')">查看</a>
						</c:when>
						<c:when test="${treeId == '51a154399faf457f8e1ef12136cf1260' || treeId == '8e66c12b4d3a4fc3ac780c3b44cb1078'}">
							<a onclick="openDetailDialog('党总支书记测评','${affairAssess.id}')">查看</a>
						</c:when>
						<c:otherwise>
							<a onclick="openDetailDialog('党支部书记测评','${affairAssess.id}')">查看</a>
						</c:otherwise>
					</c:choose>

				<shiro:hasPermission name="affair:affairAssess:edit">
                    <c:if test="${affairAssess.createBy.id == fns:getUser().id}">
						<c:choose>
							<c:when test="${treeId == '1' || treeId == '34' || treeId ==  '95' || treeId == '156' || treeId == '2'}">
								<a onclick="openAddEditDialog('党委书记测评','${affairAssess.id}')">修改</a>
							</c:when>
							<c:when test="${treeId == '51a154399faf457f8e1ef12136cf1260' || treeId == '8e66c12b4d3a4fc3ac780c3b44cb1078'}">
								<a onclick="openAddEditDialog('党总支书记测评','${affairAssess.id}')">修改</a>
							</c:when>
							<c:otherwise>
								<a onclick="openAddEditDialog('党支部书记测评','${affairAssess.id}')">修改</a>
							</c:otherwise>
						</c:choose>

                        <a href="${ctx}/affair/affairAssess/delete?id=${affairAssess.id}&treeId=${treeId}"
                           onclick="return confirmx('确认要删除该党委书记述职测评吗？', this.href)">删除</a>
                    </c:if>
				</shiro:hasPermission>
				</td>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>