<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>上级拨款管理</title>
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
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairSuperiorGrant", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairSuperiorGrant"}});
			});
			//导出功能的JS
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairSuperiorGrant/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairSuperiorGrant/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairSuperiorGrant/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairSuperiorGrant/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "上级拨款",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairSuperiorGrant"}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairGroupManagement/">团费收缴</a></li>
		<li class="active"><a href="${ctx}/affair/affairSuperiorGrant/">上级拨款</a></li>
		<li><a href="${ctx}/affair/affairGroupFee/">团费支出</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairSuperiorGrant" action="${ctx}/affair/affairSuperiorGrant/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="上级拨款表.xlsx"/>
		<ul class="ul-form">
			<li><label>拨款类型：</label>
				<form:select path="grantType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_bokuan')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>金额：</label>
				<form:input path="beginAmount" htmlEscape="false" class="input-medium"/>--
				<form:input path="endAmount" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>拨款日期：</label>
				<input name="beginGrantDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairSuperiorGrant.beginGrantDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endGrantDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairSuperiorGrant.endGrantDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairSuperiorGrant:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairSuperiorGrant/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairSuperiorGrant/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairSuperiorGrant'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>拨款类型</th>
				<th>金额</th>
				<th>拨款单位</th>
				<th>接受组织</th>
				<th>拨款日期</th>
				<shiro:hasPermission name="affair:affairSuperiorGrant:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairSuperiorGrant" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairSuperiorGrant.id}"/></td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td>
					${fns:getDictLabel(affairSuperiorGrant.grantType, 'affair_bokuan', '')}
				</td>
				<td>
					${affairSuperiorGrant.amount}
				</td>
				<td>
					${affairSuperiorGrant.appropriationUnit}
				</td>
				<td>
					${affairSuperiorGrant.acceptingUnit}
				</td>
				<td>
					<fmt:formatDate value="${affairSuperiorGrant.grantDate}" pattern="yyyy-MM-dd"/>
				</td>
				<shiro:hasPermission name="affair:affairSuperiorGrant:edit"><td class="handleTd">
					<c:if test="${affairSuperiorGrant.createBy.id == fns:getUser().id}">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairSuperiorGrant/form?id=${affairSuperiorGrant.id}')">修改</a>
						<a href="${ctx}/affair/affairSuperiorGrant/delete?id=${affairSuperiorGrant.id}" onclick="return confirmx('确认要删除该上级拨款吗？', this.href)">删除</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>