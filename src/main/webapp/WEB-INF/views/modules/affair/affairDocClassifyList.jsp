<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文档分类管理</title>
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
        //打开添加页面
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "文档分类基本信息",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairDocClassify/list?treeId=${treeId}"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairDocClassify/formDetail?id="+id;
			top.$.jBox.open(url, "文档分类详情",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairDocManagement/">文档管理</a></li>
		<li class="active"><a href="${ctx}/affair/affairDocClassify/list?treeId=${treeId}">文档分类</a></li>
		<shiro:hasPermission name="affair:affairDocClassify:edit"><li><a href="${ctx}/affair/affairDocClassify/form">文档分类添加</a></li></shiro:hasPermission>
	</ul>--%>
	<br>
	<form:form id="searchForm" modelAttribute="affairDocClassify" action="${ctx}/affair/affairDocClassify/list?treeId=${treeId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<li><label>分类编码：</label>
			<form:input path="classifyCode" htmlEscape="false" class="input-medium"/>
		</li>
		<li><label>分类名称：</label>
			<form:input path="classifyName" htmlEscape="false" class="input-medium"/>
		</li>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairDocClassify:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairDocClassify/form')"/></li>
				<%--<li class="btns"><a class="btn btn-primary" href="${ctx}/affair/affairDocClassify/delete?id=${treeId}" onclick="return confirmx('确认要删除该文档分类吗？', this.href)">删除</a></li>
--%>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairDocClassify/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairDocClassify/list?treeId=${treeId}'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/></th>
				<th>分类编码</th>
				<th>分类名称</th>
				<shiro:hasPermission name="affair:affairDocClassify:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairDocClassify">
			<tr>
				<td  class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairDocClassify.id}"/>
				</td>
				<td>
					${affairDocClassify.classifyCode}
				</td>
				<td>
					${affairDocClassify.classifyName}
				</td>
				<shiro:hasPermission name="affair:affairDocClassify:edit"><td>
					<a onclick="openDetailDialog('${affairDocClassify.id}')" style="cursor: pointer">查看</a>
    				<a onclick="openDialog('${ctx}/affair/affairDocClassify/form?id=${affairDocClassify.id}')" style="cursor: pointer">修改</a>


				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
