<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>职务工资标准管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairBasicWage/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairBasicWage/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairBasicWage/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairBasicWage/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairBasicWage", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairBasicWage"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "工资标准",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairBasicWage"}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairBasicWage/">职务工资标准</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairBasicWage" action="${ctx}/affair/affairBasicWage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="工资标准.xlsx"/>
		<ul class="ul-form">
			<li><label>年度：</label>
				<input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairBasicWage.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairBasicWage:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairBasicWage/form?id=${affairBasicWage.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairBasicWage/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairBasicWage'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>职级</th>
				<th>职务工资</th>
				<th>工作补贴</th>
				<th>生活补贴</th>
				<th>年度</th>
				<shiro:hasPermission name="affair:affairBasicWage:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairBasicWage" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairBasicWage.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
							${fns:getDictLabel(affairBasicWage.level, 'affair_base_wage', '')}
				</td>
				<td>
					${affairBasicWage.jobSalary}
				</td>
				<td>
					${affairBasicWage.workAllowance}
				</td>
				<td>
					${affairBasicWage.livingAllowance}
				</td>
				<td>
					${affairBasicWage.year}
				</td>
				<shiro:hasPermission name="affair:affairBasicWage:edit"><td class="handleTd">
					<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairBasicWage/form?id=${affairBasicWage.id}')">修改</a>
					<a href="${ctx}/affair/affairBasicWage/delete?id=${affairBasicWage.id}" onclick="return confirmx('确认要删除该职务工资标准吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>