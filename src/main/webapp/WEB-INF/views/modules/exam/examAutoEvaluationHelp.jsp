<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>自动考评管理</title>
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
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/exam/examAutoEvaluation/formDetail?id=" + id;
			top.$.jBox.open(url, "详情", 1000, 500, {
				buttons: {"关闭": true},
				loaded: function (h) {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}
			});
		}
		function openOption(standardBaseInfoId) {
			var url = "iframe:${ctx}/exam/examAutoEvaluation/optionDetail?option=${option}&standardBaseInfoId=" + standardBaseInfoId;
			top.$.jBox.open(url, "考评事项详情", 1000, 500, {
				buttons: {"关闭": true},
				loaded: function (h) {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">

	</ul>
	<form:form id="searchForm" modelAttribute="examAutoEvaluation" action="${ctx}/exam/examAutoEvaluation/model" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>考核事项：</label>
				<form:input path="option" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>周期：</label>
				<form:select path="period" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('exam_cycle')}" itemLabel="label" itemValue="value"
								  htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>考评类别：</label>
				<form:select path="evalType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('kp_type')}" itemLabel="label" itemValue="value"
								  htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnQuery" class="btn btn-primary" type="submit" value="查询模板"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/exam/examAutoEvaluation'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<%--<form:form id="searchForm" modelAttribute="examAutoEvaluation" action="${ctx}/exam/examAutoEvaluation/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>来源：</label>
				<form:select path="flagType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('from_sys')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>类型：</label>
				<form:select path="period" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('exam_cycle')}" itemLabel="label" itemValue="value"
								  htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>年份：</label>
				<form:input path="year" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>月份：</label>
				<form:input path="month" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/exam/examAutoEvaluation'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>--%>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>id(modelId)</th>
				<th>类别</th>
				<th>周期</th>
				<th>考评标准名称</th>
<%--				<shiro:hasPermission name="exam:examAutoEvaluation:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="standardBaseInfo" varStatus="status">
			<tr>
				<td>${status.index+1}</td>
				<td>
						${standardBaseInfo.id}
				</td>
				<td>
						${fns:getDictLabel(standardBaseInfo.kpType,'kp_type' , '')}
				</td>
				<td>
						${fns:getDictLabel(standardBaseInfo.cycle,'exam_cycle' , '')}
				</td>
				<td>
						<a href="javascript:;" onclick="openOption('${standardBaseInfo.id}')">${standardBaseInfo.name}</a>
				</td>
				<%--<shiro:hasPermission name="exam:examAutoEvaluation:edit"><td>
    				<a href="${ctx}/exam/examAutoEvaluation/form?id=${examAutoEvaluation.id}">修改</a>
					<a href="${ctx}/exam/examAutoEvaluation/delete?id=${examAutoEvaluation.id}" onclick="return confirmx('确认要删除该自动考评吗？', this.href)">删除</a>
				</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>