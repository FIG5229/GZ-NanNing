<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>预警信息管理</title>
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
        function openFormDetail(warnId) {
			var url = "iframe:${ctx}/warn/warning/formDetail?id="+warnId;
			top.$.jBox.open(url, "预警信息详情",800,520,{
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
		<li class="active"><a href="${ctx}/warn/warning/">预警信息列表</a></li>
		<shiro:hasPermission name="warn:warning:edit"><li><a href="${ctx}/warn/warning/form">预警信息添加</a></li></shiro:hasPermission>
		<li><a href="${ctx}/warn/warnHistory/">预警历史记录</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="warning" action="${ctx}/warn/warning/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>预警名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>重复周期：</label>
				<form:select path="repeatCycle" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('warn_repeat_cycle')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="warn:warning:edit">
			<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/warn/warning/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/warn/warning/'"/></li>
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
				<th>预警名称</th>
				<th>重复周期</th>
				<%--<th>是否有声音</th>
				<th>是否有气泡</th>--%>
				<th>是否有弹窗</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="warning" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${warning.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${warning.name}
				</td>
				<td>
					${fns:getDictLabel(warning.repeatCycle, 'warn_repeat_cycle', '')}
				</td>
			<%--	<td>
					<c:choose>
						<c:when test="${warning.isVoice == 1}">
							是
						</c:when>
						<c:otherwise>
							否
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${warning.isBubble == 1}">
							是
						</c:when>
						<c:otherwise>
							否
						</c:otherwise>
					</c:choose>
				</td>--%>
				<td>
					<c:choose>
						<c:when test="${warning.isAlert == 1}">
							是
						</c:when>
						<c:otherwise>
							否
						</c:otherwise>
					</c:choose>
				</td>
				<td>
				<a href="javaScript:void(0);" onclick="openFormDetail('${warning.id}')">查看</a>
				<shiro:hasPermission name="warn:warning:edit">
					<c:if test="${warning.createBy.id == fns:getUser().id || fns:getUser().id eq '1' }">
						<a href="${ctx}/warn/warning/form?id=${warning.id}">修改</a>
						<a href="${ctx}/warn/warning/delete?id=${warning.id}"
						   onclick="return confirmx('确认要删除该预警信息吗？', this.href)">删除</a>
					</c:if>
				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>