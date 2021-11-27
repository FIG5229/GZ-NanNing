<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>其他党代表会管理</title>
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
								$("#searchForm").attr("action","${ctx}/affair/affairOtherPartyRepresentative/export?treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairOtherPartyRepresentative/list?treeId=${treeId}");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairOtherPartyRepresentative/export?treeId=${treeId}&flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairOtherPartyRepresentative/list?treeId=${treeId}");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairOtherPartyRepresentative", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairOtherPartyRepresentative/list?treeId=${treeId}"}});
			});
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "其他党代会代表",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairOtherPartyRepresentative/list?treeId=${treeId}";
				}
			});
		}
	</script>
</head>
<body>

	<form:form id="searchForm" modelAttribute="affairOtherPartyRepresentative" action="${ctx}/affair/affairOtherPartyRepresentative/partyRepresentativeDetail" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
<%--		<input id="fileName" name="fileName" type="hidden" value="其他党代会代表.xlsx"/>--%>
		<input id="year" name="year" type="hidden" value="${affairDevelopObject.year}"/>
		<input id="month" name="month" type="hidden" value="${affairDevelopObject.month}"/>
		<input id="dateStart" name="dateStart" type="hidden" value="${affairDevelopObject.dateStart}"/>
		<input id="dateEnd" name="dateEnd" type="hidden" value="${affairDevelopObject.dateEnd}"/>
		<input id="dateType" name="dateType" type="hidden" value="${affairDevelopObject.dateType}"/>

	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>姓名</th>
				<th>党组织</th>
				<th>性别</th>
				<th>民族</th>
				<th>年龄</th>
				<th>学历</th>
				<th>是否在职</th>
				<th>届次</th>
<%--				<shiro:hasPermission name="affair:affairOtherPartyRepresentative:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairOtherPartyRepresentative" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairOtherPartyRepresentative.id}"/></td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td>
					${affairOtherPartyRepresentative.name}
				</td>
				<td>
					${affairOtherPartyRepresentative.unit}
				</td>
				<td>
					${fns:getDictLabel(affairOtherPartyRepresentative.sex, 'sex', '')}
				</td>
				<td>
					${fns:getDictLabel(affairOtherPartyRepresentative.nation, 'nation', '')}
				</td>
				<td>
					${affairOtherPartyRepresentative.age}
				</td>
				<td>
					${affairOtherPartyRepresentative.education}
				</td>
				<td>
					${fns:getDictLabel(affairOtherPartyRepresentative.isWork, 'yes_no', '')}
				</td>
				<td>
					${affairOtherPartyRepresentative.session}
				</td>
			<%--	<shiro:hasPermission name="affair:affairOtherPartyRepresentative:edit"><td>
					<a href="javascript:void(0)"
					   onclick="openForm('${ctx}/affair/affairOtherPartyRepresentative/form?id=${affairOtherPartyRepresentative.id}')">修改</a>
					<a href="${ctx}/affair/affairOtherPartyRepresentative/delete?id=${affairOtherPartyRepresentative.id}&treeId=${treeId}"
					   onclick="return confirmx('确认要删除该党代会代表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>