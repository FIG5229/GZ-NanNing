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
		<li class="active"><a href="${ctx}/affair/affairXunLi/onlineCourse?idNumber=${idNumber}">在线课程</a></li>
		<li><a href="${ctx}/affair/affairXunLi/trainingCourses?idNumber=${idNumber}">培训班课程</a></li>
		<li><a href="${ctx}/affair/affairXunLi/examEntering?idNumber=${idNumber}">考试</a></li>
		<li><a href="${ctx}/affair/affairXunLi/record?idNumber=${idNumber}">委外培训</a></li>
		<li><a href="${ctx}/affair/affairXunLi/exchangeLearning?idNumber=${idNumber}">交流学习</a></li>
		<li><a href="${ctx}/affair/affairXunLi/job?idNumber=${idNumber}">岗位练兵</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>课程名称</th>
				<th>学习总时间</th>
				<th>学习进度</th>
				<th>学习总次数</th>
				<th>课前成绩</th>
				<th>课后成绩</th>
				<th>通过状态</th>
				<th>通过时间</th>
				<th>课时(分钟)</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairOnlineCourse" varStatus="status">
			<tr>
				<td>
						${affairOnlineCourse.name}
				</td>
				<td>
						${affairOnlineCourse.plan}
				</td>
				<td>
						${affairOnlineCourse.learnTime}
				</td>
				<td>
						${affairOnlineCourse.studyTime}
				</td>
				<td>
						${affairOnlineCourse.beforeClassGrade}
				</td>
				<td>
						${affairOnlineCourse.afterClassGrade}
				</td>
				<td>
						${affairOnlineCourse.passStatus}
				</td>
				<td>
						${affairOnlineCourse.passTime}
				</td>
				<td>
						${affairOnlineCourse.hour}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>