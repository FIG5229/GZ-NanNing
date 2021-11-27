<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>女警工作管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			var permission=$("#permission").val();
			if (permission=='hasPermission') {
				window.location.href="${ctx}/affair/affairPolicewomanWork/manageList";
			}

			$("#print").click(function(){
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
					formValues: false
				});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

		let height =  $(window).height();
		let width =  $(window).width();
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairPolicewomanWork/detail?id="+id;
			top.$.jBox.open(url, "女警工作",width,height,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairPolicewomanWork/";
				}
			});
		};
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<shiro:lacksPermission name="affair:affairPolicewomanWork:manage">
			<li class="active"><a href="${ctx}/affair/affairPolicewomanWork/">女警工作管理</a></li>
		</shiro:lacksPermission>

		<shiro:hasPermission name="affair:affairPolicewomanWork:manage">
			<%--有管理权限时 跳转到管理页 无权限则是查询页 达到合并tab效果--%>
			<input type="hidden" value="hasPermission" id="permission">
			<li class="active"><a href="${ctx}/affair/affairPolicewomanWork/manageList">女警工作管理</a></li>
		</shiro:hasPermission>
		<li><a href="${ctx}/affair/affairPolicewomanTalent/">女警风采信息</a></li>
		<li ><a href="${ctx}/affair/affairChildSubsidy/">幼儿补助信息</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairPolicewomanWork" action="${ctx}/affair/affairPolicewomanWork/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>发布日期：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairPolicewomanWork.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				--
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairPolicewomanWork.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul  class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairPolicewomanWork/'"/></li>
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
				<th>标题</th>
				<th>发布部门</th>
				<th>发布人</th>
				<th>状态</th>
				<th>发布日期</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairPolicewomanWork" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairPolicewomanWork.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<a onclick="openDetailDialog('${affairPolicewomanWork.id}')">${affairPolicewomanWork.title}</a>
				</td>
				<td>
					${affairPolicewomanWork.publishDep}
				</td>
				<td>
					${affairPolicewomanWork.publisher}
				</td>
				<td>
					${fns:getDictLabel(affairPolicewomanWork.status, 'affair_publish_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${affairPolicewomanWork.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>