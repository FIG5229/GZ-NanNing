<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>一般慰问</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairConsolationWorkInfo/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairConsolationWorkInfo/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairConsolationWorkInfo/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairConsolationWorkInfo/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairConsolationWorkInfo", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairConsolationWorkInfo"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "慰问工作",1200,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairConsolationWorkInfo"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairConsolationWorkInfo/formDetail?id="+id;
			top.$.jBox.open(url, "慰问工作详情",900,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		$('#notPass').popover();
	</script>
</head>
<body>

	<form:form id="searchForm" modelAttribute="affairConsolationWorkInfo" action="${ctx}/affair/affairConsolationWorkInfo/sympathyDetail" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="year" name="year" type="hidden" value="${affairConsolationWorkInfo.year}"/>
		<input id="month" name="month" type="hidden" value="${affairConsolationWorkInfo.month}"/>
		<input id="dateType" name="dateType" type="hidden" value="${affairConsolationWorkInfo.dateType}"/>
		<input id="label" name="label" type="hidden" value="${affairConsolationWorkInfo.label}"/>
		<input name="startDate" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
			   value="<fmt:formatDate value="${affairConsolationWorkInfo.startDate}" pattern="yyyy-MM-dd"/>"
			   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		<input name="endDate" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
			   value="<fmt:formatDate value="${affairConsolationWorkInfo.endDate}" pattern="yyyy-MM-dd"/>"
			   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>


	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>标题</th>
				<th>时间</th>
				<th>单位</th>
				<th>姓名</th>
				<th>慰问类型</th>
				<th>慰问金（慰问品）</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairConsolationWorkInfo" varStatus="status">
			<tr>

				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairConsolationWorkInfo.title}
				</td>
				<td>
					<fmt:formatDate value="${affairConsolationWorkInfo.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairConsolationWorkInfo.unit}
				</td>
				<td>
					${affairConsolationWorkInfo.name}
				</td>
				<td>
					${fns:getDictLabel(affairConsolationWorkInfo.type, 'affair_ww', '')}
				</td>
				<td>
					${affairConsolationWorkInfo.money}
				</td>

			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>