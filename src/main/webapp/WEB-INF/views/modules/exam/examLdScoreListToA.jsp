<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>绩效等次管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

		function excellentByIds(url,checkAllId,checkBoxsName) {
			var excellentIds = getIds(checkBoxsName);
			if (excellentIds.length > 0) {
				$.ajax({
					type:"post",
					url:url,
					data:{ids:excellentIds},
					dataType:"json",
					success:function(data){
						$.jBox.tip(data.message);
						resetCheckBox(checkAllId,checkBoxsName);
						location.reload();
					}
				})
			} else {
				$.jBox.tip('请先选择要删除的内容');
			}
		};

	</script>
</head>
<body>
<%--ABCD 为优秀 达标 基本达标 不达标 toA 为添加达标--%>

		<%--人工选择转换为优秀--%>
<%--	<ul class="nav nav-tabs">
		<li><a href="${ctx}/exam/examLdScore/list?grades=3">基本达标</a></li>
		<li><a href="${ctx}/exam/examLdScore/list?grades=2">不达标</a></li>
		<li><a href="${ctx}/exam/examLdScore/list?grades=5">优秀</a></li>
		<li class="active"><a href="${ctx}/exam/examLdScore/list?grades=4">达标</a></li>
	</ul>--%>
	<form:form id="searchForm" modelAttribute="examLdScore" action="${ctx}/exam/examLdScore/formExcellent" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>分值：</label>
				<form:input path="beginSumScore" htmlEscape="false" class="input-medium"/>
				—
				<form:input path="endSumScore" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnEvaluate" class="btn btn-primary" type="button" value="评为优秀" onclick="excellentByIds('${ctx}/exam/examLdScore/excellentByIds','checkAll','myCheckBox')"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>姓名</th>
				<th>最终得分</th>
				<shiro:hasPermission name="exam:examLdScore:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examLdScore" varStatus="status">
			<tr>
				<td class="checkTd"><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${examLdScore.id}"/></td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${examLdScore.name}
				</td>
				<td>
					${examLdScore.sumScore}
				</td>
				<shiro:hasPermission name="exam:examLdScore:edit"><td>
    				<a href="${ctx}/exam/examLdScore/excellent?id=${examLdScore.id}" onclick="return confirmx('确认要评为优秀吗？', this.href)">评为优秀</a>
<%--					<a href="${ctx}/exam/examLdScore/delete?id=${examLdScore.id}" onclick="return confirmx('确认要删除该绩效等次吗？', this.href)">删除</a>--%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
<%--	<div class="pagination">${page}</div>--%>
</body>
</html>