<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单位通讯信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/org/orgCommunication/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/org/orgCommunication/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/org/orgCommunication/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/org/orgCommunication/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        };
		//添加修改弹窗
		function openAddEditDialog(id,orgId) {
			if (orgId == null || orgId == undefined || orgId ==""){
				top.$.jBox.info("添加前请先确认机构基本信息已保存",'系统提示');
				return false;
			}
			var url = "iframe:${ctx}/org/orgCommunication/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/org/orgCommunication/form?orgId="+orgId;
			}
			top.$.jBox.open(url, "单位通讯信息",900,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/org/orgCommunication/list?orgId="+orgId;
				}
			});
		};
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/org/orgCommunication/formDetail?id="+id;
			top.$.jBox.open(url, "单位通讯信息",800,350,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		//导入
		function excelImport(orgId) {
			if (orgId == null || orgId == undefined || orgId ==""){
				top.$.jBox.info("导入前请先确认机构基本信息已保存",'系统提示');
				return false;
			}
			top.$.jBox.open("iframe:${ctx}/org/upload/template/download/view?id=org_orgCommunication&orgId="+orgId, "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/org/orgCommunication?orgId="+orgId}});
		}
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/org/orgCommunication/">单位通讯信息列表</a></li>
		<shiro:hasPermission name="org:orgCommunication:edit"><li><a href="${ctx}/org/orgCommunication/form">单位通讯信息添加</a></li></shiro:hasPermission>
	</ul>--%>
	<form:form id="searchForm" modelAttribute="orgCommunication" action="${ctx}/org/orgCommunication/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="单位通讯信息集.xlsx"/>
		<%--机构id--%>
		<input id="orgId" name="orgId" type="hidden" value="${orgCommunication.orgId}"/>
		<ul class="ul-form">
			<%--<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>--%>
			<shiro:hasPermission name="org:orgCommunication:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog(null,'${orgCommunication.orgId}')" value="添加"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/org/orgCommunication/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input class="btn btn-primary" type="button" value="导入" onclick="excelImport('${orgCommunication.orgId}')"/></li>
				<%--<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/org/orgCommunication/'"/></li>--%>
			</shiro:hasPermission>
				<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
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
				<th>单位邮政编码</th>
				<th>单位地址</th>
				<th>单位电话号码</th>
				<th>单位传真号码</th>
				<th>单位网址</th>
				<th>单位E_MAIL地址</th>
				<shiro:hasPermission name="org:orgCommunication:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orgCommunication" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${orgCommunication.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${orgCommunication.postCode}
				</td>
				<td>
					<a onclick="openDetailDialog('${orgCommunication.id}')">
						${orgCommunication.address}
					</a>
				</td>
				<td>
					${orgCommunication.telephone}
				</td>
				<td>
					${orgCommunication.faxNumber}
				</td>
				<td>
					${orgCommunication.website}
				</td>
				<td>
					${orgCommunication.email}
				</td>
				<shiro:hasPermission name="org:orgCommunication:edit"><td>
					<a onclick="openAddEditDialog('${orgCommunication.id}','${orgCommunication.orgId}')">修改</a>
					<a href="${ctx}/org/orgCommunication/delete?id=${orgCommunication.id}&orgId=${orgCommunication.orgId}" onclick="return confirmx('确认要删除该单位通讯信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>