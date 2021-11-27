<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>管理员信息统计管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairAdminStatistics/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairAdminStatistics/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairAdminStatistics/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairAdminStatistics/");
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
				$('#headTitle').addClass("table table-striped table-bordered table-condensed");
				$('#headTitle').removeAttr('style');

				$("#contentTable").printThis({
					debug: false,
					importCSS: false,
					importStyle: false,
					printContainer: true,
					loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css"],
					pageTitle: "",
					removeInline: false,
					printDelay: 333,
					header: "",
					formValues: false,
					afterPrint:function(){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
						$('#headTitle').css('display', 'none');

					}
				});
			});
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairAdminStatistics", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairAdminStatistics"}});
			});
			
		});

		//添加，修改弹窗
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, " ",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairAdminStatistics"}
			});
		}

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairAdminStatistics/formDetail?id="+id;
			top.$.jBox.open(url, "",900,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
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
	<form:form id="searchForm" modelAttribute="affairAdminStatistics" action="${ctx}/affair/affairAdminStatistics/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="管理员信息统计表.xlsx"/>
		<ul class="ul-form">

			<li><label>域：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairAdminStatistics.unitId}" labelName="unit" labelValue="${affairAdminStatistics.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>机构：</label>
			<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>角色：</label>
			<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairAdminStatistics:edit">
				<li class="btns">
					<input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairAdminStatistics/form?id=${affairAdminStatistics.id}')"/>
				</li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
										onclick="deleteByIds('${ctx}/affair/affairAdminStatistics/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairAdminStatistics'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >南宁铁路公安处管理员信息统计管理报表</td>
			</tr>
		</table>
		<table  class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>用户名</th>
				<th>姓名</th>
				<th>性别</th>
				<th>部门</th>
				<th>管理范围，管理权限</th>
				<shiro:hasPermission name="affair:affairAdminStatistics:edit"><th class="handleTd">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairAdminStatistics" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairAdminStatistics.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${affairAdminStatistics.nickName}
				</td>
				<td>
						${affairAdminStatistics.name}
				</td>
				<td>
						${fns:getDictLabel(affairAdminStatistics.sex, 'sex', '')}

				<td>
						${affairAdminStatistics.unit}
				</td>
				<td>
						${affairAdminStatistics.control}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairAdminStatistics.id}')">查看</a>
					<shiro:hasPermission name="affair:affairAdminStatistics:edit">
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairAdminStatistics/form?id=${affairAdminStatistics.id}')">修改</a>
						<a href="${ctx}/affair/affairAdminStatistics/delete?id=${affairAdminStatistics.id}" onclick="return confirmx('确认要删除该管理员信息统计吗？', this.href)">删除</a>
				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>