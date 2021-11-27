<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title></title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairAssess/export?treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action", "${ctx}/affair/affairAssess/list?treeId=${treeId}");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairAssess/export?flag=true&treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action", "${ctx}/affair/affairAssess/list?treeId=${treeId}");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairAssess", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText: "导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
					closed: function () {
						self.location.href = "${ctx}/affair/affairAssess/list?treeId=${treeId}"
					}
				});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        };
		//添加修改弹窗
		function openAddEditDialog(title,id) {
			var url = "iframe:${ctx}/affair/affairAssess/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairAssess/form";
			}
			top.$.jBox.open(url, title,1000,650,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href = "${ctx}/affair/affairAssess/list?treeId=${treeId}";
				}
			});
		};
		//详情弹窗
		function openDetailDialog(title,id) {
			var url = "iframe:${ctx}/affair/affairAssess/formDetail?id="+id;
			top.$.jBox.open(url, title,1000,400,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
	</script>
</head>
<body>

<form:form id="searchForm" modelAttribute="affairAssess" action="${ctx}/affair/affairAssess/assessLevelDetail"
		   method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="year" name="years" type="hidden" value="${affairAssess.years}"/>
		<input id="month" name="month" type="hidden" value="${affairAssess.month}"/>
		<input id="dateType" name="dateType" type="hidden" value="${affairAssess.dateType}"/>
		<input id="label" name="label" type="hidden" value="${affairAssess.label}"/>
	<input style="width: 140px" name="startDate" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
		   value="<fmt:formatDate value="${affairAssess.startDate}" pattern="yyyy-MM-dd"/>"
		   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
	<input style="width: 140px" name="endDate" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
		   value="<fmt:formatDate value="${affairAssess.endDate}" pattern="yyyy-MM-dd"/>"
		   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>

				<th>序号</th>
				<th>党组织书记</th>
				<th>党组织名称</th>
				<th>测评时间</th>
				<th>参加测评人数</th>
				<th>测评等次</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairAssess" varStatus="status">
			<tr>

				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairAssess.shuji}
				</td>
				<td>
					${affairAssess.partyOrganization}
				</td>
				<td>
					<fmt:formatDate value="${affairAssess.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairAssess.personNum}
				</td>
				<td>
					${fns:getDictLabel(affairAssess.grade, 'affair_assess_grade', '')}
				</td>

			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>