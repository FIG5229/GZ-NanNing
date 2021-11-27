<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>组织关系对应关系管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

		});

/*		//添加，修改弹窗
 		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "组织关系对应关系",800,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/sys/sysOffices"}
			});
		}*/

		//添加function,用于打开form添加页面弹窗,需要传一个url
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "组织关系对应关系",800,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/sys/sysOffices"}
			});
		}
		//修改function,用于打开form添加页面弹窗,需要传一个url
		function openchangeDialog(url) {
			top.$.jBox.open("iframe:"+url, "组织关系对应关系",800,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/sys/sysOffices"}
			});
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
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/sysIndex/">索引管理列表</a></li>
		<li class="active"><a href="${ctx}/sys/sysOffices/">组织关系对应关系列表</a></li>
<%--
		<shiro:hasPermission name="sys:sysOffices:edit"><li><a href="${ctx}/sys/sysOffices/form">组织关系对应关系添加</a></li></shiro:hasPermission>
--%>
	</ul>
	<form:form id="searchForm" modelAttribute="sysOffices" action="${ctx}/sys/sysOffices/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label class="width110">单位名称：</label>
				<sys:treeselect id="name" name="id" value="${sysOffices.id}" labelName="name" labelValue="${sysOffices.name}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<%--<form:input path="name" htmlEscape="false" class="input-medium"/>--%>
			</li>
			<li><label class="width110">党组织名称：</label>
				<sys:treeselect id="partyName" name="partyId" value="${sysOffices.partyId}" labelName="partyName" labelValue="${sysOffices.partyName}"
								title="党组织名称" url="/affair/affairGeneralSituation/treeData" allowClear="true" notAllowSelectParent="false"/>
					<%--<form:input path="partyName" htmlEscape="false" class="input-medium"/>--%>
			</li>
			<li><label class="width110">团组织名称：</label>
				<sys:treeselect id="groupName" name="groupId" value="${sysOffices.groupId}" labelName="groupName" labelValue="${sysOffices.groupName}"
								title="团组织名称" url="/affair/affairTwBase/treeData" allowClear="true"/>
				<%--<form:input path="groupName" htmlEscape="false" class="input-medium"/>--%>
			</li>
			<li><label class="width110">工会组织名称：</label>
				<sys:treeselect id="unionName" name="unionId" value="${sysOffices.unionId}" labelName="unionName" labelValue="${sysOffices.unionName}"
								title="工会组织名称" url="/affair/affairOrganizationBulid/treeData" allowClear="true"/>
				<%--<form:input path="unionName" htmlEscape="false" class="input-medium"/>--%>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="sys:sysIndex:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/sys/sysOffices/newForm')"/></li>

				<%--
                                <li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/sys/sysOffices/deleteByIds','checkAll','myCheckBox')"/></li>
                --%>
			</shiro:hasPermission>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/sys/sysOffices'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>单位名称</th>
				<th>党组织名称</th>
				<th>团组织名称</th>
				<th>工会组织名称</th>
				<shiro:hasPermission name="sys:sysOffices:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysOffices">
			<tr>
				<td>
					${sysOffices.name}
				</td>
				<td>
						${sysOffices.partyName}
				</td>
				<td>
						${sysOffices.groupName}
				</td>
				<td>
						${sysOffices.unionName}
				</td>
				<shiro:hasPermission name="sys:sysOffices:edit"><td>
    				<%--<a href="${ctx}/sys/sysOffices/form?id=${sysOffices.id}">修改</a>--%>
<%--
					<a href="javascript:void(0)"  onclick="openDialog('${ctx}/sys/sysOffices/form?id=${sysOffices.id}">修改</a>
--%>
					<a href="javascript:void(0)" onclick="openchangeDialog('${ctx}/sys/sysOffices/form?id=${sysOffices.id}')">修改</a>
<%--
					<a href="${ctx}/sys/sysOffices/deleteById?id=${sysOffices.id}" onclick="return confirmx('确认要删除该组织关系对应关系吗？', this.href)">删除</a>
--%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
