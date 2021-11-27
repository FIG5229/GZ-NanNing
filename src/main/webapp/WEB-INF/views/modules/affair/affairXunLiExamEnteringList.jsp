<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>训厉管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairXunLi/exportTwo?idNumber=${idNumber}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairXunLi/examEntering?idNumber=${idNumber}");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairXunLi/exportTwo?flag=true&idNumber=${idNumber}")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairXunLi/examEntering?idNumber=${idNumber}");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairExamEnteringTwo", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairXunLi/examEntering?idNumber=${idNumber}"}});
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
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairXunLi/onlineCourse?idNumber=${idNumber}">在线课程</a></li>
		<li><a href="${ctx}/affair/affairXunLi/trainingCourses?idNumber=${idNumber}">培训班课程</a></li>
		<li class="active"><a href="${ctx}/affair/affairXunLi/examEntering?idNumber=${idNumber}">考试</a></li>
		<li><a href="${ctx}/affair/affairXunLi/record?idNumber=${idNumber}">委外培训</a></li>
		<li><a href="${ctx}/affair/affairXunLi/exchangeLearning?idNumber=${idNumber}">交流学习</a></li>
		<li><a href="${ctx}/affair/affairXunLi/job?idNumber=${idNumber}">岗位练兵</a></li>
	</ul>
	<form:form id="searchForm" action="${ctx}/affair/affairExamEntering/" method="post" class="breadcrumb form-search">
		<input id="fileName" name="fileName" type="hidden" value="考试成绩管理表.xlsx"/>
		<ul class="ul-form2">
			<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>考试名称</th>
				<th>考试类别</th>
				<th>考试时长(分钟)</th>
				<th>通过分数</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page}" var="affairCourseResource" varStatus="status">
			<tr>
				<td>
					${affairCourseResource.examName}
				</td>
				<td>
					${affairCourseResource.examType}
				</td>
				<td>
					${affairCourseResource.examTime}
				</td>
				<td>
					${affairCourseResource.score}
				</td>

			</tr>
		</c:forEach>
		</tbody>
	</table>

	<form:form id="inputForm" modelAttribute="affairXunLi" action="${ctx}/affair/affairXunLi/insertOne?idNumber=${idNumber}" method="post" class="form-horizontal">
	<div class="control-group">



		<label class="control-label">附件：</label>
		<%--	<div class="controls">
                <form:hidden id="appendfile" path="appendfile" htmlEscape="false" class="input-xlarge"/>
                <sys:ckfinder input="appendfile" type="files" uploadPath="/affair/affairActivityMien" selectMultiple="true"/>
            </div>--%>

		<div class="controls">
			<form:hidden id="appendfile" path="appendfile" htmlEscape="false" maxlength="255" class="input-xlarge"/>
			<sys:webuploader input="appendfile" type="files" uploadPath="affair/affairExamEntering"
							 selectMultiple="true"/>
		</div>
	</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairExamEntering:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
		</div>
	</form:form>
</body>
</html>