<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党章党规及党建知识管理</title>
	<meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#print").click(function(){
				$("#contentTable").printThis({
					debug: false,
					importCSS: false,
					importStyle: false,
					printContainer: true,
					loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css"],
					pageTitle: "打印",
					removeInline: false,
					printDelay: 333,
					header: null,
					formValues: false
				});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        };
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairKnowledge/detail?id="+id;
			top.$.jBox.open(url, "党章党规及党建知识",1000,700,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairKnowledge/">查询</a></li>
		<shiro:hasPermission name="affair:affairKnowledge:manage"><li><a href="${ctx}/affair/affairKnowledge/manageList">管理</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairKnowledge" action="${ctx}/affair/affairKnowledge/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_knowledge_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>发布日期：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairKnowledge.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				 --
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairKnowledge.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairKnowledge/'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>
					<input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>
				</th>
				<th>序号</th>
				<th>标题</th>
				<th>类型</th>
				<th>发布部门</th>
				<th>发布人</th>
				<th>发布日期</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairKnowledge" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairKnowledge.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<a onclick="openDetailDialog('${affairKnowledge.id}')">
						${affairKnowledge.title}
					</a>
				</td>
				<td>
					${fns:getDictLabel(affairKnowledge.type, 'affair_knowledge_type', '')}
				</td>
				<td>
					${affairKnowledge.publishDep}
				</td>
				<td>
					${affairKnowledge.publisher}
				</td>
				<td>
					<%--<fmt:formatDate value="${affairKnowledge.publishDate}" pattern="yyyy-MM-dd"/>--%>
					<fmt:formatDate value="${affairKnowledge.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>