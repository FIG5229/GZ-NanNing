<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考勤关联计算管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairAttendanceSummary/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairAttendanceSummary/list");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出数据': 'all','取消':'cancel'} });
					}
			);
			$("#print").click(function(){
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
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
					afterPrint:function(){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
						$('#headTitle').css('display', 'none');
					}
				});
			});
		});
		function page(n,s){
			$("#searchForm").submit();
        	return false;
        }
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "关联计算",800,520,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairAttendanceSummary"}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairAttendanceCount/">考勤数据统计</a></li>
		<li class="active"><a href="${ctx}/affair/affairAttendanceSummary/">考勤关联计算</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairAttendanceSummary" action="${ctx}/affair/affairAttendanceSummary/" method="post" class="breadcrumb form-search">
		<input id="fileName" name="fileName" type="hidden" value="考勤关联计算表.xlsx"/>
		<ul class="ul-form">
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairAttendanceSummary.unitId}" labelName="unit" labelValue="${affairAttendanceSummary.unit}"
								title="单位" url="/affair/affairLaborOffice/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>年份：</label>
				<form:input path="year" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>月份：</label>
				<form:input path="month" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairAttendanceSummary'"/></li>

			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >考勤关联计算</td>
			</tr>
		</table>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th rowspan="2" style="text-align: center">序号</th>
				<th rowspan="2" style="text-align: center">年份</th>
				<th rowspan="2" style="text-align: center">月份</th>
				<th rowspan="2" style="text-align: center">姓名</th>
				<th rowspan="2" style="text-align: center">身份证</th>
				<th rowspan="2" style="text-align: center">单位</th>
				<th colspan="3" style="text-align: center">值勤津贴</th>
				<th colspan="3" style="text-align: center">线路津贴</th>
				<th colspan="3" style="text-align: center">加班补贴</th>
				<th rowspan="2" style="text-align: center">汇总</th>
			</tr>
			<tr>
				<th style="text-align: center">值勤岗位类别</th>
				<th style="text-align: center">值勤天数</th>
				<th style="text-align: center">值勤津贴金额</th>
				<th style="text-align: center">线路岗位类别</th>
				<th style="text-align: center">缺勤天数</th>
				<th style="text-align: center">线路津贴</th>
				<th style="text-align: center">加班天数</th>
				<th style="text-align: center">加发天数</th>
				<th style="text-align: center">加班补贴金额</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairAttendanceSummary" varStatus="status">
			<tr>
				<td style="text-align: center">
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td style="text-align: center">
					${affairAttendanceSummary.year}
				</td>
				<td style="text-align: center">
					${affairAttendanceSummary.month}
				</td>
				<td style="text-align: center">
					${affairAttendanceSummary.name}
				</td>
				<td style="text-align: center">
						${affairAttendanceSummary.idNumber}
				</td>
				<td style="text-align: center">
					${affairAttendanceSummary.unit}
				</td>
				<td style="text-align: center">
<%--					${affairAttendanceSummary.zqType}--%>
							${fns:getDictLabel(affairAttendanceSummary.zqType, 'attendence_police_types', '')}
				</td>
				<td style="text-align: center">
					${affairAttendanceSummary.zqDays}
				</td>
				<td style="text-align: center">
					${affairAttendanceSummary.zqMoney}
				</td>
				<td style="text-align: center">
<%--					${affairAttendanceSummary.xlType}--%>
							${fns:getDictLabel(affairAttendanceSummary.xlType, 'attendence_police_types', '')}
				</td>
				<td style="text-align: center">
					${affairAttendanceSummary.qqDays}
				</td>
				<td style="text-align: center">
					${affairAttendanceSummary.xlMoney}
				</td>
				<td style="text-align: center">
						${affairAttendanceSummary.jbDays}
				</td>
				<td style="text-align: center">
						${affairAttendanceSummary.jfDays}
				</td>
				<td style="text-align: center">
						${affairAttendanceSummary.jbMoney}
				</td>
				<td style="text-align: center">
					${affairAttendanceSummary.summary}
				</td>
				<%--<td class="handleTd">
				<shiro:hasPermission name="affair:affairAttendanceSummary:edit">
					<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairAttendanceSummary/form?id=${affairAttendanceSummary.id}')">修改</a>
				</shiro:hasPermission>
				</td>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
</body>
</html>