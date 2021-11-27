<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>警衔管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "警衔管理",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairPoliceRank"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairPoliceRank/formDetail?id="+id;
			top.$.jBox.open(url, "警衔管理",800,500,{
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
		<li class="active"><a href="${ctx}/affair/affairPoliceRank/">警衔管理</a></li>
		<%--<shiro:hasPermission name="affair:affairPoliceRank:edit"><li><a href="${ctx}/affair/affairPoliceRank/form">警衔管理表添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairPoliceRank" action="${ctx}/affair/affairPoliceRank/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>警衔类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('police_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>起算时间：</label>
				<input name="beginStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairPoliceRank.beginStartDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<input name="endStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairPoliceRank.endStartDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>终止时间：</label>
				<input name="beginEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairPoliceRank.beginEndDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairPoliceRank.endEndDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>警衔名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label style="width:100px;">授衔批准单位名称：</label>
				<form:input path="approvalUnit" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>授衔令号：</label>
				<form:input path="lhNo" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="clearfix"></li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairPoliceRank:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairPoliceRank/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairPoliceRank/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input  class="btn btn-primary" type="submit" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairPoliceRank'"/></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>警衔类型</th>
				<th>起算时间</th>
				<th>终止时间</th>
				<th>警衔名称</th>
				<th>授衔批准单位名称</th>
				<th>授衔令号</th>
				<th>授衔状态</th>
				<th>授衔日期</th>
				<shiro:hasPermission name="affair:affairPoliceRank:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairPoliceRank" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairPoliceRank.id}"/></td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td><a onclick="openDetailDialog('${affairPoliceRank.id}')">
					${fns:getDictLabel(affairPoliceRank.type, 'police_type', '')}
				</a></td>
				<td>
					<fmt:formatDate value="${affairPoliceRank.startDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairPoliceRank.endDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairPoliceRank.name}
				</td>
				<td>
					${affairPoliceRank.approvalUnit}
				</td>
				<td>
					${affairPoliceRank.lhNo}
				</td>
				<td>
					${fns:getDictLabel(affairPoliceRank.status, 'police_rank_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${affairPoliceRank.grantDate}" pattern="yyyy-MM-dd"/>
				</td>
				<shiro:hasPermission name="affair:affairPoliceRank:edit"><td>
					<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairPoliceRank/form?id=${affairPoliceRank.id}')">修改</a>
					<a href="${ctx}/affair/affairPoliceRank/delete?id=${affairPoliceRank.id}" onclick="return confirmx('确认要删除该警衔管理表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>