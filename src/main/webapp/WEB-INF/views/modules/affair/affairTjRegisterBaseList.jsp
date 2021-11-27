<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团籍注册管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairTjRegisterBase/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTjRegisterBase/list");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairTjRegisterBase/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTjRegisterBase/list");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);

			$("#print").click(function(){
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$("#contentTable").printThis({
					debug: false,
					importCSS: false,
					importStyle: false,
					printContainer: true,
					loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css"],
					pageTitle: "打印",
					removeInline: false,
					printDelay: 333,
					header: null,
					formValues: false,
					afterPrint:function(){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairTjRegisterBase", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairTjRegisterBase/list?partyBranchId=${affairTjRegisterBase.partyBranchId}"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "团籍注册",900,700,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairTjRegisterBase/list?partyBranchId=${affairTjRegisterBase.partyBranchId}"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairTjRegisterBase/formDetail?id="+id;
			top.$.jBox.open(url, "团籍注册详情",800,500,{
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
	<li class="active"><a href="${ctx}/affair/affairTjRegisterBase/list?partyBranchId=${affairTjRegisterBase.partyBranchId}">团籍注册列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="affairTjRegisterBase" action="${ctx}/affair/affairTjRegisterBase/list" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<input id="fileName" name="fileName" type="hidden" value="团籍注册表.xlsx"/>
	<ul class="ul-form">
		<li><label>警号：</label>
			<form:input path="policeNo" htmlEscape="false" class="input-medium"/>
		</li>
		<li><label>姓名：</label>
			<form:input path="name" htmlEscape="false" class="input-medium"/>
		</li>
		<li><label>团内职务：</label>
			<form:select path="job" class="input-medium">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('affair_tnjob')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</li>
		<li><label>所在团组织：</label>
			<sys:treeselect id="partyBranch" name="partyBranchId" value="${affairTjRegisterBase.partyBranchId}"
							labelName="partyBranch" labelValue="${affairTjRegisterBase.partyBranch}"
							title="所在团组织" url="/affair/affairTwBase/treeData" cssClass="input-small"
							allowClear="true"/>
		</li>
	</ul>
	<ul class="ul-form2">
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
		<shiro:hasPermission name="affair:affairTjRegisterBase:edit">
			<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairTjRegisterBase/form?id=${affairTjRegisterBase.id}')"/></li>
			<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairTjRegisterBase/deleteByIds','checkAll','myCheckBox')"/></li>
			<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
		</shiro:hasPermission>
		<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
		<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
		<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairTjRegisterBase/list?partyBranchId=${affairTjRegisterBase.partyBranchId}'"/></li>
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
		<th>性别</th>
		<th>出生日期</th>
		<th>注册时间</th>
		<th>所在团组织</th>
		<th>团内职务</th>
		<th>备注</th>
		<shiro:hasPermission name="affair:affairTjRegisterBase:edit"><th id="handleTh">操作</th></shiro:hasPermission>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="affairTjRegisterBaseInfo" varStatus="status">
		<tr>
			<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTjRegisterBaseInfo.id}"/></td>
			<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
			</td>
			<td>
					${affairTjRegisterBaseInfo.name}
			</td>
			<td>
					${fns:getDictLabel(affairTjRegisterBaseInfo.sex, 'sex', '')}
			</td>
			<td>
				<fmt:formatDate value="${affairTjRegisterBaseInfo.birthday}" pattern="yyyy-MM-dd"/>
			</td>
			<td>
					${affairTjRegisterBaseInfo.date}
			</td>
			<td>
					${affairTjRegisterBaseInfo.partyBranch}
			</td>
			<td>
					${fns:getDictLabel(affairTjRegisterBaseInfo.job, 'affair_tnjob', '')}
			</td>
			<td>
					${affairTjRegisterBaseInfo.remark}
			</td>
			<shiro:hasPermission name="affair:affairTjRegisterBase:edit"><td class="handleTd">

					<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTjRegisterBase/form?id=${affairTjRegisterBaseInfo.id}')">修改</a>
					<a href="${ctx}/affair/affairTjRegisterBase/delete?id=${affairTjRegisterBaseInfo.id}&partyBranchId=${affairTjRegisterBase.partyBranchId}" onclick="return confirmx('确认要删除该团籍注册吗？', this.href)">删除</a>

			</td></shiro:hasPermission>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>