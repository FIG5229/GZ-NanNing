<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>档案目录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//导出功能的JS
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairArchiveDirectory/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairArchiveDirectory/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairArchiveDirectory/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairArchiveDirectory/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
			//导入功能的JS
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairArchiveDirectory", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairArchiveDirectory"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "档案目录管理",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairArchiveDirectory"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairArchiveDirectory/formDetail?id="+id;
			top.$.jBox.open(url, "档案详情",800,500,{
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
		<li class="active"><a href="${ctx}/affair/affairArchiveDirectory/">档案目录</a></li>
		<li ><a href="${ctx}/affair/affairSalaryDirectory/">工资目录</a></li>
		<li ><a href="${ctx}/affair/affairJobDirectory/">职务目录</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairArchiveDirectory" action="${ctx}/affair/affairArchiveDirectory/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="档案目录.xlsx"/>
		<ul class="ul-form">
			<li><label>材料名称：</label>
				<form:input path="clName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>材料类型：</label>
				<form:select path="clType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('material_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label class="width120">材料制成时间：</label>
				<input name="beginClTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairArchiveDirectory.beginClTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endClTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairArchiveDirectory.endClTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairArchiveDirectory:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairArchiveDirectory/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairArchiveDirectory/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairArchiveDirectory'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>材料名称</th>
				<th>材料类型</th>
				<th>材料制成时间</th>
				<th>页码</th>
				<th>备注</th>
				<shiro:hasPermission name="affair:affairArchiveDirectory:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairArchiveDirectory" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairArchiveDirectory.id}"/></td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td><a onclick="openDetailDialog('${affairArchiveDirectory.id}')">
					${affairArchiveDirectory.clName}
				</a></td>
				<td>
					${fns:getDictLabel(affairArchiveDirectory.clType, 'material_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${affairArchiveDirectory.clTime}" pattern="yyyy-MM-dd "/>
				</td>
				<td>
					${affairArchiveDirectory.pageNumber}
				</td>
				<td>
					${affairArchiveDirectory.remark}
				</td>
				<shiro:hasPermission name="affair:affairArchiveDirectory:edit"><td>
					<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairArchiveDirectory/form?id=${affairArchiveDirectory.id}')">修改</a>
					<a href="${ctx}/affair/affairArchiveDirectory/delete?id=${affairArchiveDirectory.id}" onclick="return confirmx('确认要删除该档案目录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>