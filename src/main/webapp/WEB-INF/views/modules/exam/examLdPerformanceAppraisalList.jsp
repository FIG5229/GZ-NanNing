<%--
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>绩效考核情况管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#examType").change(function () {
				showAndHide();
			});
			function showAndHide(){
				if ($("#examType").val() == '1') {
					$('#year').css('display', 'inline-block');
					$('#month').css('display', 'none');
					$('#monthVal').val('');
				}else if($("#examType").val() == '2'){
					$('#year').css('display', 'none');
					$('#yearVal').val('');
					$('#month').css('display', 'inline-block');
				}
			}
			showAndHide();
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "部门绩效考核情况",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/exam/examLdPerformanceAppraisal"}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/charts/performance?id=${id}">绩效考核</a></li>
&lt;%&ndash;		<li><a href="${ctx}/exam/examPerformanceAppraisal/">单位绩效考核情况</a></li>&ndash;%&gt;
		<li class="active"><a href="${ctx}/exam/examLdPerformanceAppraisal/">绩效考核情况</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="examLdPerformanceAppraisal" action="${ctx}/exam/examLdPerformanceAppraisal/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>考评周期：</label>
				<form:select path="examType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('exam_kp_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li id="year" style="display: none"><label>年度：</label>
				<input name="year" id="yearVal" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${examLdPerformanceAppraisal.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
					&lt;%&ndash;<form:input path="year" htmlEscape="false" class="input-medium digits"/>&ndash;%&gt;
			</li>
			<li id="month" style="display: none"><label>月度：</label>
				<input name="month" id="monthVal" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${examLdPerformanceAppraisal.month}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
&lt;%&ndash;			<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/exam/examLdPerformanceAppraisal/form')"/></li>&ndash;%&gt;
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>单位</th>
				<th>职务</th>
				<th>姓名</th>
				<th>分数</th>
				<th>加扣分事项</th>
				<shiro:hasPermission name="exam:examLdPerformanceAppraisal:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examLdPerformanceAppraisal" varStatus="status">
			<tr>
				<td>
					${status.index+1}
				</td>
				<td>
					${examLdPerformanceAppraisal.unit}
				</td>
				<td>
					${examLdPerformanceAppraisal.job}
				</td>
				<td>
					${examLdPerformanceAppraisal.name}
				</td>
				<td>
					${examLdPerformanceAppraisal.fraction}
				</td>
				<td>
					${examLdPerformanceAppraisal.item}
				</td>
				<shiro:hasPermission name="exam:examLdPerformanceAppraisal:edit"><td>
    				<a href="${ctx}/exam/examLdPerformanceAppraisal/form?id=${examLdPerformanceAppraisal.id}">修改</a>
					<a href="${ctx}/exam/examLdPerformanceAppraisal/delete?id=${examLdPerformanceAppraisal.id}" onclick="return confirmx('确认要删除该绩效考核情况吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>绩效考核情况管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.content-div {
			margin: 20px;
			padding: 40px;
			border:1px solid #000;
			border-radius: 4px;
			width: auto;
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
	<script type="text/javascript">
		$(document).ready(function() {
		/*	$("#reason").change(function(){
				showAndHide();
				console.log($("#reason").val())
			});
			function showAndHide() {
				if ($("#reason").val() == '3') {
					$("#jzz").attr("style","display:block")
				}else {
					$("#jzz").attr("style","display:none")
				}
			}
			showAndHide();*/
		});

		function openExamRecord(personId,idNumber,name) {
			var url = '${ctx}/exam/examRecord/list?type=person&personId='+personId+'&idNumber='+idNumber+'&name='+name;
			top.$.jBox.open("iframe:"+url, "考评档案",1000,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});

		}
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li><a href="${ctx}/sys/charts/performance?id=${unitId}">绩效考核（单位）</a></li>
	<li class="active"><a href="${ctx}/exam/examLdPerformanceAppraisal?unitId=${unitId}">绩效考核（人员）</a></li>
</ul>
<form:form id="searchForm" modelAttribute="examLdScoreMonth" action="${ctx}/exam/examLdScoreMonth/" method="post" class="breadcrumb form-search">
<div class="content-div">
	<div >
		<div >
			考评周期：
			<form:select id="dateType" path="dateType" class="input-medium" cssStyle="margin-bottom: 10px;width: 100px;">
				<form:options items="${fns:getDictList('statistics_dateType2')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			<input id="year" name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
				   value="${year}"
				   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});" style="width: 60px;display: none"/>

			<input id="month" name="month" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
				   value="${month}"
				   onclick="WdatePicker({dateFmt:'yyyyMM',isShowClear:false});" style="width: 120px;"/>
			<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" style="margin-bottom: 10px;" onclick="selectEcharts()"/>
		</div>
	</div>
</div>
	<c:choose>
		<c:when test="${unitType eq 'isJu' or unitType eq 'isNNC' or unitType eq 'isLZC'or unitType eq 'isBHC' }">
			<div style="float: left">
				<select id="reason" style="width: 140px;">
					<option value="1">处班子考评情况</option>
					<option value="2">中基层领导考评情况</option>
					<option value="3" selected>普通民警考评情况</option>
				</select>
			</div>
		</c:when>
		<c:when test="${unitType eq 'isJJG' or unitType eq 'isNNCpcs' or unitType eq 'isLZCpcs' or unitType eq 'isBHCpcs' or unitType eq 'isNNCjg' or unitType eq 'isLZCjg' or unitType eq 'isBHCjg'}">
			<div style="float: left">
				<select id="reason" style="width: 140px;">
					<option value="2">中基层领导考评情况</option>
					<option value="3" selected>普通民警考评情况</option>
				</select>
			</div>
		</c:when>
		<c:otherwise>
			<div style="float: left">
				<select id="reason" style="width: 140px;">
					<option value="1">处班子考评情况</option>
					<option value="2">中基层领导考评情况</option>
					<option value="3" selected>普通民警考评情况</option>
				</select>
			</div>
		</c:otherwise>
	</c:choose>
	<div id="jzz" style="float: left">
		<select id="jz" >
			<option value="">请选择部门和警种</option>
			<c:forEach items="${bmhjzList}" var="bmhjz">
				<option value="${bmhjz}">${bmhjz}</option>
			</c:forEach>

		</select>
		<select id="zw">
			<option value="">请选择职务</option>
			<c:forEach items="${jobAbbreviationList}" var="jobAbbreviation">
				<option value="${jobAbbreviation}">${jobAbbreviation}</option>
			</c:forEach>
		</select>

		<%--<select id="queryScope">
			<option value="">请选择查询范围</option>
			<option value="1">全局</option>
			<option value="2">局机关</option>
		</select>--%>

	</div>
	<%--<div id="jzz" style="margin-left:30px;float: left">
		类别：
		<select id="type">
			<option value="1">类别</option>
		</select>
	</div>--%>
	<div id="jzz" style="margin-left:30px;float: left">
		年龄：
		<input id="age_start" name="age_start" style="width: 30px"/>-<input id="age_end" name="age_end" style="width: 30px"/>岁
	</div>
</form:form>
<div id="mainDiv" style="height: 500px; width: 100%">
	<iframe id="mainContent" src="${ctx}/exam/examLdPerformanceAppraisal/policemanList?reasonType=1&pageNo=1&pageSize=10" width="100%" height="100%" frameborder="0"></iframe>
</div>
<%--<sys:message content="${message}"/>
&lt;%&ndash;普通民警考核情况&ndash;%&gt;
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th>序号</th>
		<th>姓名</th>
		<th>分数</th>
		<th>等次</th>
		<th>考评档案</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${list}" var="examLdScoreMonth" varStatus="status">
		<tr>
			<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
			</td>
			<td>
					${examLdScoreMonth.name}
			</td>
			<td>
					${examLdScoreMonth.score}
			</td>
			<td>
					${examLdScoreMonth.level}<a href="">评定</a>
			</td>
			<td>
					${examLdScoreMonth.dangan}<a href="javaScript:void(0);" onclick="openExamRecord('${examLdScoreMonth.personnelBaseId}','${examLdScoreMonth.idNumber}','${examLdScoreMonth.name}')">详情</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>--%>
<script type="text/javascript">
	//调用方法
/*	exam($("#dateType").val(),$("#reason").val());

	$("#dateType").change(function(){
		exam($("#dateType").val(),$("#reason").val());
	});*/
	$("#reason").change(function(){
		createExamInfoTable($("#dateType").val(),$("#reason").val(),$("#jz").val(),$("#zw").val(),$("#age_start").val(),$("#age_end").val());
		//$("#mainContent").attr("src","${ctx}/exam/examLdPerformanceAppraisal/policemanList?reasonType="+$("#reason").val()+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&month="+$("#month").val());
	});
	$("#jz").change(function () {
		createExamInfoTable($("#dateType").val(),$("#reason").val(),$("#jz").val(),$("#zw").val(),$("#age_start").val(),$("#age_end").val());
	});
	$("#zw").change(function () {
		createExamInfoTable($("#dateType").val(),$("#reason").val(),$("#jz").val(),$("#zw").val(),$("#age_start").val(),$("#age_end").val());
	})
	//年度、月份
	$("#dateType").change(function(){
		if($("#dateType").val() == '2'){//年度
			$('#year').css('display', 'inline-block');
			$('#month').css('display', 'none');
		}else if($("#dateType").val() == '1'){//月度
			$('#year').css('display', 'none');
			$('#month').css('display', 'inline-block');
		}
	});
	function createExamInfoTable(dataType,reasonType,jz,zw,ageStart,ageEnd) {
		$("#mainContent").attr("src","${ctx}/exam/examLdPerformanceAppraisal/policemanList?reasonType="+reasonType+"&jz="+jz+"&zw="+zw+"&ageStart="+ageStart+"&ageEnd="+ageEnd+"&dateType="+dataType+"&year="+$("#year").val()+"&month="+$("#month").val()+"&unitId=${unitId}&pageNo=1&pageSize=10");
	}
	createExamInfoTable($("#dateType").val(),$("#reason").val(),$("#jz").val(),$("#zw").val(),$("#age_start").val(),$("#age_end").val());
	//查询按钮
	function selectEcharts(){
	    //$.jBox.tip("查询")
		createExamInfoTable($("#dateType").val(),$("#reason").val(),$("#jz").val(),$("#zw").val(),$("#age_start").val(),$("#age_end").val())
		//exam($("#dateType").val(),$("#reason").val());
	}
</script>
<div class="pagination">${page}</div>
</body>
</html>