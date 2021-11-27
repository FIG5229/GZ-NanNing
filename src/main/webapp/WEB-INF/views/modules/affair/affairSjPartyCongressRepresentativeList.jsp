<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>上级党代会代表管理</title>
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
			$("#btnExport").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairSjPartyCongressRepresentative/export?treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairSjPartyCongressRepresentative/list?treeId=${treeId}");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairSjPartyCongressRepresentative/export?flag=true&treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairSjPartyCongressRepresentative/list?treeId=${treeId}");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairSjPartyCongressRepresentative", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairSjPartyCongressRepresentative/list?treeId=${treeId}"}});
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
					self.location.href="${ctx}/affair/affairSjPartyCongressRepresentative/list?treeId=${treeId}";
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairSjPartyCongressRepresentative/list?treeId=${treeId}">上级党代会代表</a></li>
		<li><a href="${ctx}/affair/affairJuPartyCongressRepresentative?treeId=${treeId}">公安局党代会代表</a></li>
        <li><a href="${ctx}/affair/affairChuPartyCongressRepresentative?treeId=${treeId}">公安处党代会代表</a></li>
		<li><a href="${ctx}/affair/affairOtherPartyRepresentative/list?treeId=${treeId}">其他党代会代表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairSjPartyCongressRepresentative" action="${ctx}/affair/affairSjPartyCongressRepresentative/list?treeId=${treeId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="上级党代会代表.xlsx"/>
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
			<shiro:hasPermission name="affair:affairSjPartyCongressRepresentative:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairSjPartyCongressRepresentative/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairSjPartyCongressRepresentative/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入" /></li>
			</shiro:hasPermission>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出" /></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairSjPartyCongressRepresentative/list?treeId=${treeId}'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>民族</th>
				<th>年龄</th>
				<th>学历</th>
				<th>是否在职</th>
				<th>届次</th>
				<shiro:hasPermission name="affair:affairSjPartyCongressRepresentative:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairSjPartyCongressRepresentative" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairSjPartyCongressRepresentative.id}"/></td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td>
					${affairSjPartyCongressRepresentative.name}
				</td>
				<td>
					${fns:getDictLabel(affairSjPartyCongressRepresentative.sex, 'sex', '')}
				</td>
				<td>
						${fns:getDictLabel(affairSjPartyCongressRepresentative.nation, 'nation', '')}
				</td>
				<td>
					${affairSjPartyCongressRepresentative.age}
				</td>
				<td>
					${affairSjPartyCongressRepresentative.education}
				</td>
				<td>
					${fns:getDictLabel(affairSjPartyCongressRepresentative.isWork, 'yes_no', '')}
				</td>
				<td>
					${affairSjPartyCongressRepresentative.session}
				</td>
				<shiro:hasPermission name="affair:affairSjPartyCongressRepresentative:edit"><td class="handleTd">
					<a href="javascript:void(0)"
					   onclick="openForm('${ctx}/affair/affairSjPartyCongressRepresentative/form?id=${affairSjPartyCongressRepresentative.id}')">修改</a>
					<a href="${ctx}/affair/affairSjPartyCongressRepresentative/delete?id=${affairSjPartyCongressRepresentative.id}&treeId=${treeId}" onclick="return confirmx('确认要删除该上级党代会代表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>