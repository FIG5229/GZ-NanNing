<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function viewComment(href){
			top.$.jBox.open('iframe:'+href,'查看评论',$(top.document).width()-220,$(top.document).height()-120,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
					$(".nav,.form-actions,[class=btn]", h.find("iframe").contents()).hide();
					$("body", h.find("iframe").contents()).css("margin","10px");
				}
			});
			return false;
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>

	<form:form id="searchForm" modelAttribute="article" action="${ctx}/cms/article/governmentDetail" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="year" name="year" type="hidden" value="${article.year}"/>
		<input id="month" name="month" type="hidden" value="${article.month}"/>
		<input id="dateType" name="dateType" type="hidden" value="${article.dateType}"/>
		<input id="label" name="label" type="hidden" value="${article.label}"/>
		<input style="width: 140px" name="beginDate" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
			   value="<fmt:formatDate value="${article.beginDate}" pattern="yyyy-MM-dd"/>"
			   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		<input style="width: 140px" name="endDate" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
			   value="<fmt:formatDate value="${article.endDate}" pattern="yyyy-MM-dd"/>"
			   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>栏目</th>
			<th>标题</th>
			<th>权重</th>
			<th>点击数</th>
			<th>发布者</th>
			<th>更新时间</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="article">
			<tr>
				<td>${article.category.name}</td>
				<td>${fns:abbr(article.title,40)}</td>
				<td>${article.weight}</td>
				<td>${article.hits}</td>
				<td>${article.user.name}</td>
				<td><fmt:formatDate value="${article.updateDate}" type="both"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>