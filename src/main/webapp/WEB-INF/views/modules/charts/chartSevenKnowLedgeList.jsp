<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>七知档案-统计分析</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#print").click(function () {
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$("#contentTable").printThis({
					debug: false,
					importCSS: false,
					importStyle: false,
					printContainer: true,
					loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css", "${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css", "${ctxStatic}/common/jeesite.css"],
					pageTitle: "打印",
					removeInline: false,
					printDelay: 333,
					header: null,
					formValues: false,
					afterPrint: function () {
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairSevenKnowledge/exportBatch");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairSevenKnowledge/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairSevenKnowledge/exportBatch?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairSevenKnowledge/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairSevenKnowledge", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairSevenKnowledge"}});
			});

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
	</script>
</head>
<body>
<form:form id="searchForm" modelAttribute="affairSevenKnowledge" action="${ctx}/affair/affairSevenKnowledge/openSevenKnowledgeDetail/" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<input id="unit" name="unit" type="hidden" value="${affairSevenKnowledge.unit}"/>
	<input id="year" name="year" type="hidden" value="${affairSevenKnowledge.year}"/>
	<input id="month" name="month" type="hidden" value="${affairSevenKnowledge.month}"/>
	<input id="dateType" name="dateType" type="hidden" value="${affairSevenKnowledge.dateType}"/>
	<input id="dateStart" name="dateStart" type="hidden" value="${affairSevenKnowledge.dateStart}"/>
	<input id="dateEnd" name="dateEnd" type="hidden" value="${affairSevenKnowledge.dateEnd}"/>
	<input id="label" name="label" type="hidden" value="${affairSevenKnowledge.label}"/>
	<input id="fileName" name="fileName" type="hidden" value="3.5七知档案.xlsx"/>

</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
		<th>序号</th>
		<th>工作单位</th>
		<th>姓名</th>
		<th>性别</th>
		<th>民族</th>
		<th>政治面貌</th>
		<th>出生日期</th>
		<th>参加工作时间</th>
		<th>从警时间</th>
		<th>职务</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="affairSevenKnowledge" varStatus="status">
		<tr
				<c:choose>
					<c:when test="${affairSevenKnowledge.evaluate == '3'}">
						style="color: #4e8bff"
					</c:when>
					<c:when test="${affairSevenKnowledge.evaluate == '2'}">
						style="color: #d5bb38"
					</c:when>
					<c:when test="${affairSevenKnowledge.evaluate == '1'}">
						style="color: #d53732"
					</c:when>
					<c:otherwise></c:otherwise>
				</c:choose>
		>

			<td class="checkTd">
				<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairSevenKnowledge.id}"/>
			</td>
			<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
			</td>
			<td>
					${affairSevenKnowledge.unit}
			</td>
			<td>
					${affairSevenKnowledge.name}
			</td>
			<td>
					${fns:getDictLabel(affairSevenKnowledge.sex, 'sex', '')}
			</td>
			<td>
					${fns:getDictLabel(affairSevenKnowledge.nation, 'nation', '')}
			</td>
			<td>
					${fns:getDictLabel(affairSevenKnowledge.politicsFace, 'political_status', '')}
			</td>
			<td>
				<fmt:formatDate value="${affairSevenKnowledge.birthday}" pattern="yyyy-MM-dd"/>
			</td>
			<td>
				<fmt:formatDate value="${affairSevenKnowledge.workTime}" pattern="yyyy-MM-dd"/>
			</td>
			<td>
				<fmt:formatDate value="${affairSevenKnowledge.fromPoliceTime}" pattern="yyyy-MM-dd"/>
			</td>
			<td>
					${affairSevenKnowledge.job}
			</td>

		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>