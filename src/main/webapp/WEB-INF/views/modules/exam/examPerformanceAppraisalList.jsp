<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>部门绩效考核情况管理</title>
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
				},closed:function (){self.location.href="${ctx}/exam/examPerformanceAppraisal"}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/charts/performance?id=${id}">绩效考核</a></li>
		<li class="active"><a href="${ctx}/exam/examPerformanceAppraisal/">单位绩效考核情况</a></li>
		<li ><a href="${ctx}/exam/examLdPerformanceAppraisal/">绩效考核情况</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="examPerformanceAppraisal" action="${ctx}/exam/examPerformanceAppraisal/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>考评周期：</label>
				<form:select path="examType" class="input-medium" id="examType">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('exam_kp_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li id="year" style="display: none"><label>年度：</label>
				<input name="year" id="yearVal" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${examPerformanceAppraisal.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
					<%--<form:input path="year" htmlEscape="false" class="input-medium digits"/>--%>
			</li>
			<li id="month" style="display: none"><label>月度：</label>
				<input name="month" id="monthVal" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${examPerformanceAppraisal.month}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
<%--			<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/exam/examPerformanceAppraisal/form')"/></li>--%>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>部门</th>
				<th>扣分项目</th>
				<th>加分项目</th>
				<th>扣分</th>
				<th>加分</th>
				<th>总得分</th>
				<shiro:hasPermission name="exam:examPerformanceAppraisal:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examPerformanceAppraisal" varStatus="status">
			<tr>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${examPerformanceAppraisal.dep}
				</td>
				<td>
					${examPerformanceAppraisal.lessItem}
				</td>
				<td>
					${examPerformanceAppraisal.addItem}
				</td>
				<td>
					${examPerformanceAppraisal.penalties}
				</td>
				<td>
					${examPerformanceAppraisal.addPoints}
				</td>
				<td>
					${examPerformanceAppraisal.sum}
				</td>
				<shiro:hasPermission name="exam:examPerformanceAppraisal:edit"><td>
    				<a href="${ctx}/exam/examPerformanceAppraisal/form?id=${examPerformanceAppraisal.id}">修改</a>
					<a href="${ctx}/exam/examPerformanceAppraisal/delete?id=${examPerformanceAppraisal.id}" onclick="return confirmx('确认要删除该部门绩效考核情况吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>