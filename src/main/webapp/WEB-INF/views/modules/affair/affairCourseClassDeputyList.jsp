<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程研发管理</title>
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

		//添加，修改弹窗
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url,"",800,600 ,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairCourseClass/deputyList?id=${affairCourseClass.id}"}
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
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairCourseResource/KCForm?id=${affairCourseClass.classId}">主持研发</a></li>
		<li class="active"><a href="${ctx}/affair/affairCourseClass/deputyList?id=${affairCourseClass.classId}">参与研发</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairCourseClass" action="${ctx}/affair/affairCourseClass/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form2">
			<li class="btns">
				<input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairCourseClass/deputyForm?id=${affairCourseClass.id}')"/>
			</li>
			<li class="btns">
				<input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairCourseClass/deleteByIds','checkAll','myCheckBox')"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>教官姓名</th>
				<th>登记日期</th>
				<th>研发类型</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${affairCourseClassList}" var="affairCourseClass" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairCourseClass.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairCourseClass.name}
				</td>
				<td>
					<fmt:formatDate value="${affairCourseClass.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${affairCourseClass.openTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>