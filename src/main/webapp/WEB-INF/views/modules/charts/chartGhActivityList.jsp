<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工会活动记录管理</title>
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
					afterPrint:function(){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairGhActivityRecord/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairGhActivityRecord/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairGhActivityRecord/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairGhActivityRecord/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairGhActivityRecord", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairGhActivityRecord"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "活动记录",1000,500,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairGhActivityRecord"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairGhActivityRecord/formDetail?id="+id;
			top.$.jBox.open(url, "\n" +
					"活动记录",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		//详情弹窗
		function openPropellingDialog(id) {
			var url = "iframe:${ctx}/affair/affairGhActivityRecord/propelling?id="+id;
			top.$.jBox.open(url, "\n" + "数据推送",500,300,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairGhActivityRecord"}
			});
		};
		function submitByIds() {
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				console.log($(this).val())
				ids.push($(this).val());
			});
			if (ids.length > 0) {
				var idsStr = ids.join(",");
				$("#idsValue").val(idsStr);
				$("#searchForm2").submit();
			} else {
				$.jBox.tip('请先选择要提交的内容');
			}
		};

	</script>
</head>
<body>

	<form:form id="searchForm" modelAttribute="affairGhActivityRecord" action="${ctx}/affair/affairGhActivityRecord/activityDetail" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="year" name="year" type="hidden" value="${affairZxInfo.year}"/>
		<input id="month" name="month" type="hidden" value="${affairZxInfo.month}"/>
		<input id="dateType" name="dateType" type="hidden" value="${affairZxInfo.dateType}"/>
		<input id="label" name="label" type="hidden" value="${affairZxInfo.label}"/>

		<input name="dateStart" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
			value="<fmt:formatDate value="${affairGhActivityRecord.dateStart}" pattern="yyyy-MM-dd"/>"
			onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>

		<input name="dateEnd" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
			value="<fmt:formatDate value="${affairGhActivityRecord.dateEnd}" pattern="yyyy-MM-dd"/>"
			onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>标题</th>
				<th>基层工会</th>
				<th>时间</th>
				<th>推送状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairGhActivityRecord" varStatus="status">
			<tr>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairGhActivityRecord.title}
				</td>
				<td>
					${affairGhActivityRecord.partyBranch}
				</td>
				<td>
					<fmt:formatDate value="${affairGhActivityRecord.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
			   ${fns:getDictLabel(affairGhActivityRecord.checkType, 'check_type', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>