<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>自动考评考评标准关联管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//getChooseStandardList();
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

		function getChooseStandardList() {
			var list = [];
			var content = "";
			$.ajax({
				url: "${ctx}/exam/examStandardBaseInfo/getStandardListBeta",
				dataType: "json",
				async: true,//请求是否异步，默认为异步
				data: {kpType: $("#evealType").val()},
				type: "POST",
				success: function (res) {
					if (res.result != null) {
						list = res.result;
						content = '';
						list.forEach((item, index) => {
							content += '<option value=' + item.id + '>' + item.name + '</option>';
						})
						$("#modelId").empty();
						$("#modelId").append(content);
						$("#modelId").val('');
						$("#modelId").siblings('.select2-container').find('.select2-chosen').text($("#modelId").find("option:selected").text());
						getChooseOptionsList();
					}
				}
			});
		};
		//根据使用模板的值渲染选择项所对应的内容
		function getChooseOptionsList() {
			var list = [];
			var content = "";
			$.ajax({
				<%--url: "${ctx}/exam/examCheck/findChooseOptionsByUseModel",--%>
				url: "${ctx}/exam/examCheck/findChooseOptionsByUseModel2",
				dataType: "json",
				async: true,//请求是否异步，默认为异步
				data: {useModel: $("#modelId").val()},
				type: "POST",
				success: function (res) {
					if (res.result != null) {
						list = res.result;
						content = '';
						list.forEach((item, index) => {
							content += '<option value=' + item.optionId + '>' + item.itemValue + '</option>';
						})
						$("#optionId").empty();
						$("#optionId").append(content);
						$("#optionId").val('');
						$("#optionId").siblings('.select2-container').find('.select2-chosen').text($("#optionId").find("option:selected").text());
					}
				}
			});
		};
		//修改
		function openDialog(url) {
			top.$.jBox.open("iframe:" + url, "模板标准管理", 800, 600, {
				persistent: true,
				buttons: {"关闭": true},
				loaded: function (h) {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}, closed: function () {
					$("#searchForm").submit();
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/exam/examAutoStandardRelevance/">自动考评考评标准关联列表</a></li>
		<%--<shiro:hasPermission name="exam:examAutoStandardRelevance:edit">--%>
		<%--<c:if test="${fns:getUser().id eq '1'}">
			<li><a href="${ctx}/exam/examAutoStandardRelevance/form">自动考评考评标准关联添加</a></li>
		</c:if>--%>
		<%--</shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="examAutoStandardRelevance" action="${ctx}/exam/examAutoStandardRelevance/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>考评事项：</label>
				<form:select path="item" class="input-medium">
					<form:option value="" label=""/>
					<form:option value="" label=""/>
					<c:forEach items="${itemList}" var="item" varStatus="status">
							<form:option value="${item}" label="${item}"/>
					</c:forEach>
				</form:select>
				<%--<form:input path="item" htmlEscape="false" class="input-medium"/>--%>
			</li>
			<li><label>考评类别：</label>
				<form:select path="evealType" class="input-medium"><%--onchange="getChooseStandardList()"--%>
					<form:option value="" label=""/>
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('kp_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<%--<li><label>模板：</label>
				<form:select path="modelId" class="input-xlarge" onchange="getChooseOptionsList()">
					<form:option value="" label="请先选择考评类别"/>
				</form:select>
			</li>
			<li><label>选择项：</label>
				<form:select path="optionId" class="input-xlarge">
					<form:option value="" label="请先选择模板"/>
				</form:select>
			</li>--%>
			<li><label>考评周期：</label>
				<form:select path="period" class="input-medium">
					<form:option value="" label=""/>
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('cycle')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<%--<li><label>考核部门：</label>
				<sys:treeselect id="assessid" name="assessid" value="${examAutoStandardRelevance.assessid}" labelName="assess" labelValue="${examAutoStandardRelevance.assess}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			--%>
			<li><label>所属公安处：</label>
				<form:select path="chuId" class="input-medium required">
					<form:option value="" label=""/>
					<form:option value="" label=""/>
					<form:option value="34" label="南宁处"/>
					<form:option value="95" label="柳州处"/>
					<form:option value="156" label="北海处"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<c:if test="${fns:getUser().id eq '1'}">
			<li class="btns"><input class="btn btn-primary" type="button" value="添加" onclick="openDialog('${ctx}/exam/examAutoStandardRelevance/form')"/></li>
			</c:if>
			<li class="btns"><input class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/exam/examAutoStandardRelevance/'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
                <th>序号</th>
				<th>考评事项</th>
				<th>考评类别</th>
				<th>考评周期</th>
				<%--<th>考核部门</th>--%>
				<th>使用模板</th>
				<th>选择项</th>
				<th>当前使用模板</th>
				<th>当前选择项</th>
				<th>所属处</th>
				<%--<shiro:hasPermission name="exam:examAutoStandardRelevance:edit">--%>
				<th>操作</th>
				<%--</shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examAutoStandardRelevance" varStatus="status">
			<tr>
                <td>${status.index+1}</td>
				<td>
					${examAutoStandardRelevance.item}
				</td>
				<td>
					${fns:getDictLabel(examAutoStandardRelevance.evealType, 'kp_type', '')}
				</td>
				<td>
					${fns:getDictLabel(examAutoStandardRelevance.period, 'cycle', '')}
				</td>
				<%--<td>
					${examAutoStandardRelevance.assess}
				</td>--%>
				<td>
					${examAutoStandardRelevance.model}
				</td>
				<td>
					${examAutoStandardRelevance.option}
				</td>
				<td>
						${examAutoStandardRelevance.newModel}
				</td>
				<td>
						${examAutoStandardRelevance.newOption}
				</td>
				<td>
					${examAutoStandardRelevance.chu}
				</td>
				<%--<shiro:hasPermission name="exam:examAutoStandardRelevance:edit">--%><td>
				<c:if test="${fns:getUser().id eq '1'}">
    				<%--<a href="${ctx}/exam/examAutoStandardRelevance/form?id=${examAutoStandardRelevance.id}">修改</a>--%>
    				<a href="javaScript:void(0);" onclick="openDialog('${ctx}/exam/examAutoStandardRelevance/form?id=${examAutoStandardRelevance.id}')">修改</a>
					<a href="${ctx}/exam/examAutoStandardRelevance/delete?id=${examAutoStandardRelevance.id}" onclick="return confirmx('确认要删除该自动考评考评标准关联吗？', this.href)">删除</a>
				</c:if>
				<a href="javascript:void(0)"
				   		onclick="openDialog('${ctx}/exam/examAutoStandardRelevance/template?id=${examAutoStandardRelevance.id}')">更改标准</a>
				</td><%--</shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>