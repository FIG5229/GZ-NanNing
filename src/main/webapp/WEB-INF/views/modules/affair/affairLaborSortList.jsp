<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考勤人员排序管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairLaborSort/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairLiveFire/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出数据': 'all','取消':'cancel'} });
					}
			);
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairLaborSort", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairLaborSort"}});
			});
		});
		function page(n,s){
			$("#searchForm").submit();
        	return false;
        }
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "人员排序",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairLaborSort"}
			});
		}
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairLaborSort/">考勤人员排序列表</a></li>
		<shiro:hasPermission name="affair:affairLaborSort:edit"><li><a href="${ctx}/affair/affairLaborSort/form">考勤人员排序添加</a></li></shiro:hasPermission>
	</ul>--%>
	<form:form id="searchForm" modelAttribute="affairLaborSort" action="${ctx}/affair/affairLaborSort/" method="post" class="breadcrumb form-search">
		<input id="fileName" name="fileName" type="hidden" value="人员排序.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairLaborSort.unitId}" labelName="unit" labelValue="${affairLaborSort.unit}"
								title="单位" url="/affair/affairLaborOffice/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairLaborSort/form')"/></li>
			<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairLaborSort/deleteByIds','checkAll','myCheckBox')"/></li>
			<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairLaborSort'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>姓名</th>
				<th>单位</th>
				<th>排序</th>
				<shiro:hasPermission name="affair:affairLaborSort:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairLaborSort" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairLaborSort.id}"/>
				</td>
				<td>
						${status.index+1}
				</td>
				<td>
					${affairLaborSort.name}
				</td>
				<td>
					${affairLaborSort.unit}
				</td>
				<td>
					${affairLaborSort.sort}
				</td>
				<shiro:hasPermission name="affair:affairLaborSort:edit"><td>
					<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairLaborSort/form?id=${affairLaborSort.id}')">修改</a>
					<a href="${ctx}/affair/affairLaborSort/delete?id=${affairLaborSort.id}" onclick="return confirmx('确认要删除该考勤人员排序吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>