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

	<form:form id="searchForm" modelAttribute="affairGroupManagement" action="${ctx}/affair/affairGroupManagement/feeDetail" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="year" name="year" type="hidden" value="${affairGroupManagement.year}"/>
		<input id="month" name="month" type="hidden" value="${affairGroupManagement.month}"/>
		<input id="dateType" name="dateType" type="hidden" value="${affairGroupManagement.dateType}"/>
		<input id="label" name="label" type="hidden" value="${affairGroupManagement.label}"/>
		<input name="startDate" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
			   value="<fmt:formatDate value="${affairGroupManagement.startDate}" pattern="yyyy-MM-dd"/>"
			   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		<input name="endDate" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
			   value="<fmt:formatDate value="${affairGroupManagement.endDate}" pattern="yyyy-MM-dd"/>"
			   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>缴费人</th>
				<th>金额</th>
				<th>缴费内容</th>
				<th>缴费日期</th>
				<th>收款人</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairGroupManagement" varStatus="status">
			<tr>
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
					<fmt:formatDate value="${affairGroupManagement.paymentTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairGroupManagement.payee}
				</td>

			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>