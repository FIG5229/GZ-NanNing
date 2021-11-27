<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>纪律规定管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairDisciplinaryRegulation/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairDisciplinaryRegulation/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairDisciplinaryRegulation/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairDisciplinaryRegulation/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairDisciplinaryRegulation", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairDisciplinaryRegulation"}});
			});

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
					afterPrint:function(){
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
		//添加function,用于打开form添加页面弹窗,需要传一个url
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "纪律规定 ",1000,640,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairDisciplinaryRegulation"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairDisciplinaryRegulation/formDetail?id="+id;
			top.$.jBox.open(url, "纪律规定",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairDisciplinaryRegulation/">纪律规定</a></li>
<%--		<shiro:hasPermission name="affair:affairDisciplinaryRegulation:edit"><li><a href="${ctx}/affair/affairDisciplinaryRegulation/form">纪律规定添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairDisciplinaryRegulation" action="${ctx}/affair/affairDisciplinaryRegulation/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="纪律规定.xlsx"/>
		<ul class="ul-form">
			<li><label>文件名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label="--"/>
					<form:options items="${fns:getDictList('affair_jilv')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>上传时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairDisciplinaryRegulation.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairDisciplinaryRegulation.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>上传单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairDisciplinaryRegulation.unitId}" labelName="unit" labelValue="${affairDisciplinaryRegulation.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false" isAll="true"/>
			</li>
			<li><label>状态：</label>
				<form:select path="isType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_jlgd_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairDisciplinaryRegulation:add">
				<li class="btns"><input  class="btn btn-primary" type="button" value="添加" onclick="openForm('${ctx}/affair/affairDisciplinaryRegulation/form?id=${affairDisciplinaryRegulation.id}')"/></li>
			</shiro:hasPermission>
			<c:if test="${'d5ec905f77714c6f8a216e5cbd14ff67' == fns:getUser().id}">
			<shiro:hasPermission name="affair:affairDisciplinaryRegulation:delete">
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairDisciplinaryRegulation/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			</c:if>
			<%--<shiro:hasPermission name="affair:affairDisciplinaryRegulation:edit">
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>--%>
			<li class="btns"><input id="print"  class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairDisciplinaryRegulation'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>文件名称</th>
				<th>类型</th>
				<th>上传时间</th>
				<th>上传单位</th>
				<th>施行时间</th>
                <th>状态</th>
				<shiro:hasPermission name="affair:affairDisciplinaryRegulation:add"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairDisciplinaryRegulation" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairDisciplinaryRegulation.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairDisciplinaryRegulation.name}
				</td>
				<td>
					${fns:getDictLabel(affairDisciplinaryRegulation.type, 'affair_jilv', '')}
				</td>
				<td>
					<fmt:formatDate value="${affairDisciplinaryRegulation.uploadTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${affairDisciplinaryRegulation.unit}
				</td>
				<td>
					<fmt:formatDate value="${affairDisciplinaryRegulation.impTime}" pattern="yyyy-MM-dd"/>
				</td>
                <td>
                        ${fns:getDictLabel(affairDisciplinaryRegulation.isType, 'affair_jlgd_type', '')}
                </td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairDisciplinaryRegulation.id}')">查看</a>
					<c:choose>
						<c:when test="${'d5ec905f77714c6f8a216e5cbd14ff67' == fns:getUser().id}">
							<shiro:hasPermission name="affair:affairDisciplinaryRegulation:add">
								<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairDisciplinaryRegulation/form?id=${affairDisciplinaryRegulation.id}')">修改</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="affair:affairDisciplinaryRegulation:delete">
								<a href="${ctx}/affair/affairDisciplinaryRegulation/delete?id=${affairDisciplinaryRegulation.id}"
								   onclick="return confirmx('确认要删除信息吗？', this.href)">删除</a>
							</shiro:hasPermission>
						</c:when>
						<c:otherwise>
							<%--三个处的信息管理--%>
							<c:if test="${'35737e5582804ef08502c7283db5c5cf' == fns:getUser().id}">
								<c:if test="${'19e70728419d4051bd4f9f496fbf0d7c' == affairDisciplinaryRegulation.createBy.id}">
									<shiro:hasPermission name="affair:affairDisciplinaryRegulation:add">
										<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairDisciplinaryRegulation/form?id=${affairDisciplinaryRegulation.id}')">修改</a>
									</shiro:hasPermission>
									<shiro:hasPermission name="affair:affairDisciplinaryRegulation:delete">
										<a href="${ctx}/affair/affairDisciplinaryRegulation/delete?id=${affairDisciplinaryRegulation.id}"
										   onclick="return confirmx('确认要删除信息吗？', this.href)">删除</a>
									</shiro:hasPermission>
								</c:if>
							</c:if>
							<c:if test="${'5a0766c9a3df41a88f5759a29886f1ae' == fns:getUser().id || 'b25351b897104a698accd3583ceba19f' == fns:getUser().id}">
								<c:if test="${'49e960f9fe6c4f7786ae894ffac51c7d' == affairDisciplinaryRegulation.createBy.id}">
									<shiro:hasPermission name="affair:affairDisciplinaryRegulation:add">
										<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairDisciplinaryRegulation/form?id=${affairDisciplinaryRegulation.id}')">修改</a>
									</shiro:hasPermission>
									<shiro:hasPermission name="affair:affairDisciplinaryRegulation:delete">
										<a href="${ctx}/affair/affairDisciplinaryRegulation/delete?id=${affairDisciplinaryRegulation.id}"
										   onclick="return confirmx('确认要删除信息吗？', this.href)">删除</a>
									</shiro:hasPermission>
								</c:if>
							</c:if>
							<c:if test="${'b25351b897104a698accd3583ceba19f' == fns:getUser().id}">
								<c:if test="${'f278b35db9ca4f5d8418cc44acec36de' == affairDisciplinaryRegulation.createBy.id}">
									<shiro:hasPermission name="affair:affairDisciplinaryRegulation:add">
										<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairDisciplinaryRegulation/form?id=${affairDisciplinaryRegulation.id}')">修改</a>
									</shiro:hasPermission>
									<shiro:hasPermission name="affair:affairDisciplinaryRegulation:delete">
										<a href="${ctx}/affair/affairDisciplinaryRegulation/delete?id=${affairDisciplinaryRegulation.id}"
										   onclick="return confirmx('确认要删除信息吗？', this.href)">删除</a>
									</shiro:hasPermission>
								</c:if>
							</c:if>
							<c:if test="${affairDisciplinaryRegulation.createBy.id == fns:getUser().id}">
								<shiro:hasPermission name="affair:affairDisciplinaryRegulation:add">
									<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairDisciplinaryRegulation/form?id=${affairDisciplinaryRegulation.id}')">修改</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="affair:affairDisciplinaryRegulation:delete">
									<a href="${ctx}/affair/affairDisciplinaryRegulation/delete?id=${affairDisciplinaryRegulation.id}"
									   onclick="return confirmx('确认要删除信息吗？', this.href)">删除</a>
								</shiro:hasPermission>
							</c:if>
						</c:otherwise>
					</c:choose>
					<shiro:hasPermission name="affair:affairDisciplinaryRegulation:edit">
						<c:choose>
							<c:when test="${affairDisciplinaryRegulation.orderId == '0'}">
								<a href="${ctx}/affair/affairDisciplinaryRegulation/updateOrderId?id=${affairDisciplinaryRegulation.id}">置顶</a>
							</c:when>
							<c:otherwise>
								<a href="${ctx}/affair/affairDisciplinaryRegulation/reUpdateOrderId?id=${affairDisciplinaryRegulation.id}">取消置顶</a>
							</c:otherwise>
						</c:choose>
					</shiro:hasPermission>
                   <%-- <c:if test="${affairDisciplinaryRegulation.createBy.id == fns:getUser().id || 'd5ec905f77714c6f8a216e5cbd14ff67' == fns:getUser().id}">
					<shiro:hasPermission name="affair:affairDisciplinaryRegulation:add">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairDisciplinaryRegulation/form?id=${affairDisciplinaryRegulation.id}')">修改</a>
					</shiro:hasPermission>
                    </c:if>
					<c:if test="${affairDisciplinaryRegulation.createBy.id == fns:getUser().id || 'd5ec905f77714c6f8a216e5cbd14ff67' == fns:getUser().id}">
						<shiro:hasPermission name="affair:affairDisciplinaryRegulation:delete">
							<a href="${ctx}/affair/affairDisciplinaryRegulation/delete?id=${affairDisciplinaryRegulation.id}"
							   onclick="return confirmx('确认要删除信息吗？', this.href)">删除</a>
						</shiro:hasPermission>
					</c:if>
					<shiro:hasPermission name="affair:affairDisciplinaryRegulation:edit">
						<c:choose>
							<c:when test="${affairDisciplinaryRegulation.orderId == '0'}">
								<a href="${ctx}/affair/affairDisciplinaryRegulation/updateOrderId?id=${affairDisciplinaryRegulation.id}">置顶</a>
							</c:when>
							<c:otherwise>
								<a href="${ctx}/affair/affairDisciplinaryRegulation/reUpdateOrderId?id=${affairDisciplinaryRegulation.id}">取消置顶</a>
							</c:otherwise>
						</c:choose>
					</shiro:hasPermission>--%>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>