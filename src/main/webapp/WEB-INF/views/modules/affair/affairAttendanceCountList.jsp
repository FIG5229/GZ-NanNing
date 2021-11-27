<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考勤数据统计管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairAttendanceCount/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairAttendanceCount/list");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairAttendanceCount/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairAttendanceCount/list");
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
				$('#cbTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$('.cbTd').css('display', 'none');
				$('#headTitle').addClass("table table-striped table-bordered table-condensed");
				$('#headTitle').removeAttr('style');
				$("#contentTable").printThis({
					debug: false,
					importCSS: false,
					importStyle: false,
					printContainer: true,
					loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css"],
					pageTitle: "",
					removeInline: false,
					printDelay: 333,
					header: "",
					formValues: false,
					afterPrint:function (){
						$('#handleTh').css('display', 'table-cell');
						$('#cbTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
						$('.cbTd').css('display', 'table-cell');
						$('#headTitle').css('display', 'none');
					}
				});
			});
		});
		function page(n,s){
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairAttendanceCount/">考勤数据统计</a></li>
		<li><a href="${ctx}/affair/affairAttendanceSummary/">考勤关联计算</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairAttendanceCount" action="${ctx}/affair/affairAttendanceCount/" method="post" class="breadcrumb form-search">
		<input id="fileName" name="fileName" type="hidden" value="考勤数据统计表.xlsx"/>
		<ul class="ul-form">
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairAttendanceCount.unitId}" labelName="unit" labelValue="${affairAttendanceCount.unit}"
								title="借阅人单位" url="/affair/affairLaborOffice/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>年份：</label>
				<form:input path="yearDate" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>月份：</label>
				<form:input path="date" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairAttendanceCount'"/></li>

			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >考勤数据统计</td>
			</tr>
		</table>
	<table  class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="cbTh" style="text-align: center">序号</th>
				<th style="text-align: center">单位</th>
				<th style="text-align: center">年份</th>
				<th style="text-align: center">月份</th>
				<th style="text-align: center">姓名</th>
				<th style="text-align: center">身份证号</th>
				<th style="text-align: center">考勤请假天数</th>
				<th style="text-align: center">上报请假天数</th>
				<th style="text-align: center">休假天数差</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairAttendanceCount" varStatus="status">
			<tr>
				<td class="cbTd" style="text-align: center">
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td style="text-align: center">
					${affairAttendanceCount.unit}
				</td>
				<td style="text-align: center">
					${affairAttendanceCount.yearDate}
				</td>
				<td style="text-align: center">
					${affairAttendanceCount.date}
				</td>
				<td style="text-align: center">
					${affairAttendanceCount.name}
				</td>
				<td style="text-align: center">
					${affairAttendanceCount.idNumber}
				</td>
				<td style="text-align: center">
					${affairAttendanceCount.leaveDays}
				</td>
				<td style="text-align: center">
					${affairAttendanceCount.acLeaveDays}
				</td>
				<td style="text-align: center">
					${affairAttendanceCount.leaveDays - affairAttendanceCount.acLeaveDays}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
</body>
</html>