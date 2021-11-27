<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>被考评对象类别关系表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<style>
		.show_part {
			display: inline-block;
			*display: inline;
			*zoom: 1;
			width: 400px;
			height: 20px;
			line-height: 20px;
			font-size: 12px;
			overflow: hidden;
			-ms-text-overflow: ellipsis;
			text-overflow: ellipsis;
			white-space: nowrap;}
		.show_part:hover {height: auto;white-space: normal;}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//添加function,用于打开form添加页面弹窗
		function openForm(url) {
			top.$.jBox.open("iframe:" + url, "被考评对象", 1000, 600, {
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){$("#searchForm").submit();}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/exam/examObjectType/">被考评对象信息维护</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="examObjectType" action="${ctx}/exam/examObjectType/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>类别名称：</label>
				<form:input path="typeName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>考评类别：</label>
				<form:select path="examType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('kp_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
			<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="exam:examObjectType:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/exam/examObjectType/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/exam/examObjectType/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/exam/examObjectType'"/></li>
				<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/></th>
				<th>序号</th>
				<th>类别名称</th>
				<th>考评类别</th>
				<th>被考评对象</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examObjectType">
			<tr>
				<td  class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${examObjectType.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${examObjectType.typeName}
				</td>
				<td>
					${fns:getDictLabel(examObjectType.examType, 'kp_type', '')}
				</td>
				<td>
					<span class="show_part">${examObjectType.objectUserName}</span>
				</td>
				<td>
						<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examObjectType/formDetail?id=${examObjectType.id}')">查看</a>
					<shiro:hasPermission name="exam:examObjectType:edit">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examObjectType/form?id=${examObjectType.id}')">修改</a>
						<a href="${ctx}/exam/examObjectType/delete?id=${examObjectType.id}" onclick="return confirmx('确认要删除该被考评对象类别关系表吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>