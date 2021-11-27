<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团委（支部）基本信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
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
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "团委（支部）基本信息",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairTwBase/list?treeId=${treeId}"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairTwBase/formDetail?id="+id;
			top.$.jBox.open(url, "团委（支部）基本信息详情",1000,600,{
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
		<li class="active"><a href="${ctx}/affair/affairTwBase/list?treeId=${treeId}">团委（支部）基本信息</a></li>
		<%--<li><a href="${ctx}/affair/affairYouthTalent/">青年人才库</a></li>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairTwBase" action="${ctx}/affair/affairTwBase/list?treeId=${treeId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<li><label>组织名称：</label>
			<form:input path="name" htmlEscape="false" class="input-medium"/>
		</li>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairTwBase:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairTwBase/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairTwBase/deleteByIds','checkAll','myCheckBox')"/></li>
				<%--
                                <li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
                --%>
			</shiro:hasPermission>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairTwBase/list?treeId=${treeId}'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>组织名称</th>
				<%--<th>所辖单位</th>--%>
				<th>组织团员人数</th>
				<th>团（支）委人数</th>
				<shiro:hasPermission name="affair:affairTwBase:view"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTwBase" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTwBase.id}"/></td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairTwBase.name}
				</td>
				<%--<td style="width:20%">
						${affairTwBase.unit}
				</td>--%>
				<td>
						${affairTwBase.partyNum}
				</td>
				<td>
					${affairTwBase.num}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairTwBase.id}')">查看</a>
					<shiro:hasPermission name="affair:affairTwBase:edit">
					<%--<c:if test="${affairTwBase.createBy.id == fns:getUser().id}">--%>
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairTwBase/form?id=${affairTwBase.id}')">修改</a>
						<a href="${ctx}/affair/affairTwBase/delete?id=${affairTwBase.id}&treeId=${treeId}" onclick="return confirmx('确认要删除该团委（支部）基本信息吗？', this.href)">删除</a>
					<%--</c:if>--%>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>