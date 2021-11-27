<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>发展对象管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairDevelopObject/export?treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairDevelopObject/list?treeId=${treeId}");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairDevelopObject/export?flag=true&treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairDevelopObject/list?treeId=${treeId}");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairDevelopObject", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairDevelopObject/list?treeId=${treeId}"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        };
		//添加修改详情弹窗
		function openAddEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairDevelopObject/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairDevelopObject/form";
			}
			top.$.jBox.open(url, "入党发展对象", 900, 600, {
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairDevelopObject/list?treeId=${treeId}";
				}
			});
		};
		//详情弹窗
		function openDetailDialog(id) {
			var url  = "iframe:${ctx}/affair/affairDevelopObject/formDetail?id="+id;
			top.$.jBox.open(url, "入党发展对象", 1000, 370, {
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="affairDevelopObject" action="${ctx}/affair/affairDevelopObject/list?treeId=${treeId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
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
				<th>警号</th>
				<th>身份证号</th>
				<th>所属党支部</th>
				<th>培养人</th>
				<th>申请入党时间</th>
				<th>列入培养对象的时间</th>
				<th>列为入党发展对象时间</th>

			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairDevelopObject" varStatus="status">
			<tr>

				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
<%--					<a onclick="openDetailDialog('${affairDevelopObject.id}')">--%>
<%--						${affairDevelopObject.name}--%>
<%--				    </a>--%>
		            ${affairDevelopObject.name}
				</td>
				<td>
					${affairDevelopObject.policeNo}
				</td>
				<td>
					${affairDevelopObject.idNumber}
				</td>
				<td>
					${affairDevelopObject.partyBranch}
				</td>
				<td>
					${affairDevelopObject.fosterPeople}
				</td>
				<td>
					<fmt:formatDate value="${affairDevelopObject.approvalDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairDevelopObject.enterDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairDevelopObject.enterPartDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>