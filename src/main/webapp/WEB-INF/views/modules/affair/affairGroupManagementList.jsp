<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团费收缴管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairGroupManagement/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairGroupManagement/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairGroupManagement/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairGroupManagement/");
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
					afterPrint:function(){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairGroupManagement", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairGroupManagement"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "团费收缴",800,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairGroupManagement"}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairGroupManagement/">团费收缴</a></li>
		<li><a href="${ctx}/affair/affairSuperiorGrant/">上级拨款</a></li>
		<li><a href="${ctx}/affair/affairGroupFee/">团费支出</a></li>
<%--		<shiro:hasPermission name="affair:affairGroupManagement:edit"><li><a href="${ctx}/affair/affairGroupManagement/form">团费收缴添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairGroupManagement" action="${ctx}/affair/affairGroupManagement/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="团费收缴表.xlsx"/>
		<ul class="ul-form">
			<li><label>缴费人：</label>
				<form:input path="payer" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>金额：</label>
				<form:input path="minMoney" htmlEscape="false" class="input-medium"/>至
				<form:input path="maxMoney" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>缴费日期：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairGroupManagement.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairGroupManagement.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>收费人：</label>
				<form:input path="payee" htmlEscape="false" class="input-medium"/>
			</li>
			<%--<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairGroupManagement.unitId}" labelName="unit" labelValue="${affairGroupManagement.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>--%>
			<li><label>团组织：</label>
				<sys:treeselect id="group" name="groupId" value="${affairGroupManagement.groupId}" labelName="group1" labelValue="${affairGroupManagement.group1}"
					title="团组织" url="/affair/affairTwBase/treeData" cssClass="input-small" allowClear="true" />
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairGroupManagement:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairGroupManagement/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairGroupManagement/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出" /></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairGroupManagement'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>缴费人</th>
				<th>金额</th>
				<th>缴费内容</th>
				<th>团组织</th>
				<th>缴费日期</th>
				<th>收费人</th>
				<shiro:hasPermission name="affair:affairGroupManagement:view"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairGroupManagement" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairGroupManagement.id}"/></td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairGroupManagement.payer}
				</td>
				<td>
					${affairGroupManagement.amount}
				</td>
				<td>
					${affairGroupManagement.paymentContent}
				</td>
				<td>
					${affairGroupManagement.group1}
				</td>
				<td>
					<fmt:formatDate value="${affairGroupManagement.paymentTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairGroupManagement.payee}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairGroupManagement/form?id=${affairGroupManagement.id}')">修改</a>
				<shiro:hasPermission name="affair:affairGroupManagement:edit">
					<%--<c:if test="${affairGroupManagement.createBy.id == fns:getUser().id}">--%>

						<a href="${ctx}/affair/affairGroupManagement/delete?id=${affairGroupManagement.id}" onclick="return confirmx('确认要删除该团费收缴吗？', this.href)">删除</a>
					<%--</c:if>--%>
				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>