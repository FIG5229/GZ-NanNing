<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>职务目录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//导出功能的JS
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairJobDirectory/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairJobDirectory/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairJobDirectory/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairJobDirectory/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairJobDirectory", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairJobDirectory"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "职务目录管理",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairJobDirectory"}
			});
		}
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairJobDirectory/formDetail?id="+id;
			top.$.jBox.open(url, "职务详情",800,500,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/affair/affairArchiveDirectory/">档案目录</a></li>
		<li ><a href="${ctx}/affair/affairSalaryDirectory/">工资目录</a></li>
		<li class="active"><a href="${ctx}/affair/affairJobDirectory/">职务目录</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairJobDirectory" action="${ctx}/affair/affairJobDirectory/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="职务目录.xlsx"/>
		<ul class="ul-form">
			<li><label>批准时间：</label>
				<input name="beginApproveTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairJobDirectory.beginApproveTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endApproveTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairJobDirectory.endApproveTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairJobDirectory:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairJobDirectory/form')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入" /></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairJobDirectory/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairJobDirectory'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>姓名</th>
				<th>任职时间</th>
				<th>免职时间</th>
				<th>职务</th>
				<th>批准文电号</th>
				<shiro:hasPermission name="affair:affairJobDirectory:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairJobDirectory" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairJobDirectory.id}"/></td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td><a onclick="openDetailDialog('${affairJobDirectory.id}')">
					${affairJobDirectory.name}
				</a></td>
				<td>
					${affairJobDirectory.workTime}
				</td>
				<td>
					<fmt:formatDate value="${affairJobDirectory.removalTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairJobDirectory.position}
				</td>
				<td>
					${affairJobDirectory.pzNum}
				</td>
				<shiro:hasPermission name="affair:affairJobDirectory:edit"><td>
					<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairJobDirectory/form?id=${affairJobDirectory.id}')">修改</a>
					<a href="${ctx}/affair/affairJobDirectory/delete?id=${affairJobDirectory.id}" onclick="return confirmx('确认要删除该职务目录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>