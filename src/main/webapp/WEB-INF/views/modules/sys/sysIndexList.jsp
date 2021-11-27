<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>索引管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/sys/sysIndex/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/sys/sysIndex/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/sys/sysIndex/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/sys/sysIndex/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=sys_sysIndex", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/sys/sysIndex"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

		//添加，修改弹窗
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "索引管理",1000,520,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/sys/sysIndex"}
			});
		}

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/sys/sysIndex/formDetail?id="+id;
			top.$.jBox.open(url, "索引管理详情",1000,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
	</script>
</head>
<body>
		<ul class="nav nav-tabs">
			<li class="active"><a href="${ctx}/sys/sysIndex/">索引管理列表</a></li>
			<li><a href="${ctx}/sys/sysOffices/">组织关系对应关系列表</a></li>
		</ul>
	<form:form id="searchForm" modelAttribute="sysIndex" action="${ctx}/sys/sysIndex/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="索引管理表.xlsx"/>
		<ul class="ul-form">
			<li><label>角色编号：</label>
				<form:input path="roleId" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>索引：</label>
				<form:input path="indexCode" htmlEscape="false" class="input-medium"/>
			</li>
			<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="sys:sysIndex:edit">
					<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/sys/sysIndex/form')"/></li>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/sys/sysIndex/deleteByIds','checkAll','myCheckBox')"/></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:sysIndex:edit">
					<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				</shiro:hasPermission>
				<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>

				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/sys/sysIndex'"/></li>
				<li class="clearfix">x</li>
			</ul>

			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll'
										onclick='chooseAll(this,"myCheckBox")'/></th>
				<th>序号</th>
				<th>名称</th>
				<th>角色编号</th>
				<th>索引</th>
				<shiro:hasPermission name="sys:sysIndex:edit"><th style="width: 200px">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysIndex" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${sysIndex.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${sysIndex.name}
				</td>
				<td>
					${sysIndex.roleId}
				</td>

				<td>
					${sysIndex.indexCode}
				</td>
				<td>
				<a onclick="openDetailDialog('${sysIndex.id}')" style="cursor: pointer">查看</a>
				<shiro:hasPermission name="sys:sysIndex:edit">
					<a href="javascript:void(0)"  onclick="openDialog('${ctx}/sys/sysIndex/form?id=${sysIndex.id}&srId=${sysIndex.roleId}')">修改</a>
					<%--<a href="${ctx}/sys/sysIndex/form?id=${sysIndex.id}">修改</a>--%>
					<a href="${ctx}/sys/sysIndex/delete?id=${sysIndex.id}" onclick="return confirmx('确认要删除该索引管理吗？', this.href)">删除</a>
				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>