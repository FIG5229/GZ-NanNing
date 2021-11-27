<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>训厉管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function () {
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
		<li><a href="${ctx}/affair/affairXunLi/onlineCourse?idNumber=${idNumber}">在线课程</a></li>
		<li><a href="${ctx}/affair/affairXunLi/trainingCourses?idNumber=${idNumber}">培训班课程</a></li>
		<li><a href="${ctx}/affair/affairXunLi/examEntering?idNumber=${idNumber}">考试</a></li>
		<li><a href="${ctx}/affair/affairXunLi/record?idNumber=${idNumber}">委外培训</a></li>
		<li class="active"><a href="${ctx}/affair/affairXunLi/exchangeLearning?idNumber=${idNumber}">交流学习</a></li>
		<li><a href="${ctx}/affair/affairXunLi/job?idNumber=${idNumber}">岗位练兵</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>交流名称</th>
				<th>交流规格类型</th>
				<th>交流时间</th>
				<th>交流岗位类型</th>
				<th>交流学习类型</th>
				<th>交流单位任职情况</th>
				<th>交流学习鉴定</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>
						${page.swapName}
				</td>
				<td>
						${fns:getDictLabel(page.sizeType, 'size_type', '')}
				</td>
				<td>
						<fmt:formatDate value="${page.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${fns:getDictLabel(page.jobType, 'job_type', '')}
				</td>
				<td>
						${fns:getDictLabel(page.studyType, 'study_type', '')}
				</td>
				<td>
						${fns:getDictLabel(page.serviceCondition, 'service_condition', '')}
				</td>
				<td>
						${page.studyIdentity}
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>