<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>绩效考核情况</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<style type="text/css">
		.content-div {
			margin: 20px;
			padding: 40px;
			border:1px solid #000;
			border-radius: 4px;
			width: 1000px;
		}
		.inner-div {
			padding: 20px;
			border:1px solid #000;
			border-radius: 4px;
		}
		
		.charts-div {
			display: inline-block;
			width: 450px;
			height: 550px;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/charts/personnel?id=${id}">人员信息情况</a></li>
		<li><a href="${ctx}/sys/charts/organization?id=${id}">机构信息情况</a></li>
		<li><a href="${ctx}/sys/charts/publicity?id=${id}">宣传教育情况</a></li>
		<li><a href="${ctx}/sys/charts/monitor?id=${id}">纪检监察情况</a></li>
		<li><a href="${ctx}/sys/charts/labour?id=${id}">工团工作情况</a></li>
		<li><a href="${ctx}/sys/charts/leader?id=${id}">组织干部情况</a></li>
<%--		<li  class="active"><a href="${ctx}/sys/charts/performance?id=${id}">绩效考核情况</a></li>--%>
<%--		<li class="active"><a href="${ctx}/exam/examPerformanceAppraisal/">部门绩效考核情况</a></li>--%>
<%--		<li class="active"><a href="${ctx}/exam/examLdPerformanceAppraisal/">绩效考核情况</a></li>--%>
	</ul>
	
	<div class="content-div">
		<label for="select">类别：</label>
		<select id="select">
			<option value="1">年度</option>
			<option value="2">月度</option>
		</select>
		
		<select id="year" style="width:100px;"></select>
		<input id="month" type="month" style="display:none;">
	</div>
	
	<script src="${ctx}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script type="text/javascript">

	var date = new Date();
	var year = date.getYear() + 1900
	var html = '';
	for (var i = 0; i < 10; i++) {
		html += '<option value=' + (year-i) + '>' + (year-i) + '年' + '</option>';
	}
	$('#year').append(html);
	$('#year').val(year);

	</script>
	
</body>
</html>