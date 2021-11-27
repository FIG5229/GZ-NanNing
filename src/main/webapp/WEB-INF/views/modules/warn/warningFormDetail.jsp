<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#print").click(function(){
				$("#contentTable").printThis({
					debug: false,
					importCSS: true,
					importStyle: true,
					printContainer: true,
					loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
					pageTitle: "打印",
					removeInline: false,
					printDelay: 333,
					header: null,
					formValues: false
				});
			});
		});
	</script>
	<style>
		.td25 {
			width: 25%;
		}
		.td75 {
			width: 74%;
		}

	</style>
</head>
<body>
<br>
<table id="contentTable" class="table table-bordered" style="width: 90%;margin: auto;text-align: center">
	<tr>
		<td class="td25">预警名称</td>
		<td colspan="3" class="td75">${warning.name}</td>
	</tr>
	<tr>
		<td class="td25">接受人员</td>
		<td colspan="3" class="td75">${warning.receivePerName}</td>
	</tr>
	<tr>
		<td class="td25">重复周期</td>
		<td colspan="3" class="td75">${fns:getDictLabel(warning.repeatCycle, 'warn_repeat_cycle', '')}</td>
	</tr>
	<tr>
		<td class="td25">开始时间</td>
		<td colspan="3" class="td75">
			<c:if test="${warning.repeatCycle == '0'}">
			${warning.hour}:${warning.minute}
		</c:if>
			<c:if test="${warning.repeatCycle == '1'}">
				${fns:getDictLabel(warning.week,'warn_week','')}&nbsp;&nbsp;${warning.hour}:${warning.minute}
			</c:if>
			<c:if test="${warning.repeatCycle == '2'}">
				${warning.day}日&nbsp; ${warning.hour}:${warning.minute}
			</c:if>
			<c:if test="${warning.repeatCycle == '3'}">
				${warning.month}月${warning.day}日${warning.hour}:${warning.minute}
			</c:if>
			<c:if test="${warning.repeatCycle == '4'}">
				<fmt:formatDate value="${warning.date}" pattern="yyyy-MM-dd HH:mm"/>
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="td25">重复提醒</td>
		<td colspan="3" class="td75">
			${fns:getDictLabel(warning.remind, 'warn_remind','')}
				<br>
			<span><font color="red">*注意：${fns:getDictLabel(warning.remind, 'warn_remind','')}，表示正常情况下每${fns:getDictLabel(warning.remind, 'warn_remind','')}提醒一次</font> </span>
		</td>
	</tr>
	<tr>
		<td class="td25">持续时间</td>
		<td colspan="3" class="td75">${warning.continueDay}天</td>
	</tr>
	<tr>
		<td class="td25">是否有弹窗</td>
		<td colspan="3" class="td75">${fns:getDictLabel(warning.isAlert,'warn_yes','')}</td>
	</tr>
	<tr>
		<td class="td25">弹窗内容</td>
		<td colspan="3" class="td75">${warning.alertContent}</td>
	</tr>
	<tr>
		<td class="td25">紧急程度</td>
		<td colspan="3" class="td75">${fns:getDictLabel(warning.alertDegree, 'warn_degree', '')}</td>
	</tr>
</table>
<br>
<div class="modal-custom-info1-bottom">
	<div class="modal-custom-info1-btn red" id="print">打印</div>
</div>
</body>
</html>

