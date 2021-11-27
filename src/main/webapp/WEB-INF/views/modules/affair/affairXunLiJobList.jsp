<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>训厉管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function () {
			$("#btnPrint").click(function () {
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$('#checkTh').css('display', 'none');
				$('.checkTd').css('display', 'none');
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
						$('#checkTh').css('display', 'table-cell');
						$('.checkTd').css('display', 'table-cell');
					}

				});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		if ("success" == "${saveResult}") {
			parent.$.jBox.close();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%--<li><a href="${ctx}/affair/affairXunLi/onlineCourse?idNumber=${idNumber}">在线课程</a></li>--%>
		<li><a href="${ctx}/affair/affairXunLi/trainingCourses?idNumber=${idNumber}">培训班课程</a></li>
		<%--<li><a href="${ctx}/affair/affairXunLi/examEntering?idNumber=${idNumber}">考试</a></li>--%>
		<li><a href="${ctx}/affair/affairXunLi/record?idNumber=${idNumber}">委外培训</a></li>
		<%--<li><a href="${ctx}/affair/affairXunLi/exchangeLearning?idNumber=${idNumber}">交流学习</a></li>--%>
		<li class="active"><a href="${ctx}/affair/affairXunLi/job?idNumber=${idNumber}">岗位练兵</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>训练开始时间</th>
				<th>训练结束时间</th>
				<th>项目类别</th>
				<th>训练时间(小时)</th>
				<th>训练情况</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page}" var="affairCourseResource" varStatus="status">
			<tr>
				<td>
					<fmt:formatDate value="${affairCourseResource.drillDateBegin}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${affairCourseResource.drillDateOver}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(affairCourseResource.itemClassification, 'project_type', '')}
				</td>
				<td>
					${affairCourseResource.xunlianTime}
				</td>
				<td>
					${affairCourseResource.drillGeneralSituation}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>