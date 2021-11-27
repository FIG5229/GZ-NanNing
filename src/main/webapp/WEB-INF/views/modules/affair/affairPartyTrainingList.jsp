<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党内培训管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
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
		<li class="active"><a href="${ctx}/affair/affairPartyTraining/">党内培训列表</a></li>
		<shiro:hasPermission name="affair:affairPartyTraining:edit"><li><a href="${ctx}/affair/affairPartyTraining/form">党内培训添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairPartyTraining" action="${ctx}/affair/affairPartyTraining/list?treeId=${treeId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="党内培训.xlsx"/>
		<ul class="ul-form">
			<li><label>主办单位：</label>
				<form:input path="hostUnit" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>开始时间：</label>
				<input name="beginTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairPartyTraining.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>结束时间：</label>
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairPartyTraining.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>培训名称：</label>
				<form:input path="trainingName" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairPartyTraining:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()"
										value="添加"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
										onclick="deleteByIds('${ctx}/affair/affairPartyTraining/deleteByIds','checkAll','myCheckBox')"/>
				</li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input class="btn btn-primary" type="button" value="重置"
									onclick="window.location.href='${ctx}/affair/affairPartyTraining/list?treeId=${treeId}'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>
					<input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>
				</th>
				<th>序号</th>
				<th>主办单位</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>培训地点</th>
				<th>培训名称</th>
				<th>培训形式</th>
				<th>培训结果</th>
				<shiro:hasPermission name="affair:affairPartyTraining:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairPartyTraining">
			<tr>
				<td><a href="${ctx}/affair/affairPartyTraining/form?id=${affairPartyTraining.id}">
					${affairPartyTraining.id}
				</a></td>
				<td>
					${affairPartyTraining.hostUnit}
				</td>
				<td>
					<fmt:formatDate value="${affairPartyTraining.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${affairPartyTraining.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${affairPartyTraining.trainingSites}
				</td>
				<td>
					${affairPartyTraining.trainingName}
				</td>
				<td>
					${affairPartyTraining.trainingForms}
				</td>
				<td>
					${affairPartyTraining.trainingOutcome}
				</td>
				<shiro:hasPermission name="affair:affairPartyTraining:edit"><td>
    				<a href="${ctx}/affair/affairPartyTraining/form?id=${affairPartyTraining.id}">修改</a>
					<a href="${ctx}/affair/affairPartyTraining/delete?id=${affairPartyTraining.id}" onclick="return confirmx('确认要删除该党内培训吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>