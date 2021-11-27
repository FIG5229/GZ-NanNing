<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>一帮一管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairOneHelpOne/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairOneHelpOne/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairOneHelpOne/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairOneHelpOne/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairOneHelpOne", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairOneHelpOne"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
		let height =  $(window).height()-50;
		let width =  $(window).width()-50;
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairOneHelpOne/formDetail?id="+id;
			top.$.jBox.open(url, "一帮一",width,height,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }
        function openForm(url) {
			top.$.jBox.open("iframe:"+url, "一帮一",width,height,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairOneHelpOne/list"}
			});
		}
		function openAddEditDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairOneHelpOne/form?id="+id, "一帮一",width,height,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairOneHelpOne/list"}
			});
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<shiro:hasPermission name="affair:affairZkInfo:view"><li><a href="${ctx}/affair/affairZkInfo/">助困管理</a></li></shiro:hasPermission>
		<shiro:hasPermission name="affair:affairOneHelpOne:view"><li class="active"><a href="${ctx}/affair/affairOneHelpOne/">一帮一</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairOneHelpOne" action="${ctx}/affair/affairOneHelpOne/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="全局“一帮一”重困民警慰问情况.xlsx"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
			<%--<li><label>单位：</label>
				<sys:treeselect id="unit" name="unit" value="${affairOneHelpOne.unit}" labelName="" labelValue="${affairOneHelpOne.}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>--%>
			<li class="clearfix"></li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairOneHelpOne:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairOneHelpOne/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairOneHelpOne/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairOneHelpOne'"/></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>标题</th>
				<th>单位</th>
				<th>录入时间</th>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairOneHelpOne" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairOneHelpOne.id}"/>
				</td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td>
					${affairOneHelpOne.title}
				</td>
				<td>
					${affairOneHelpOne.unit}
				</td>

				<td>
					<fmt:formatDate value="${affairOneHelpOne.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td class="handleTd">
					<a href="javascript:" onclick="openDetailDialog('${affairOneHelpOne.id}')">查看</a>
    				<a href="javascript:" onclick="openAddEditDialog('${affairOneHelpOne.id}')">修改</a>
					<a href="${ctx}/affair/affairOneHelpOne/delete?id=${affairOneHelpOne.id}" onclick="return confirmx('确认要删除该一帮一吗？', this.href)">删除</a>
			</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>