<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人才库</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			//这是打印功能的JS
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
					afterPrint:function (){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});

		});
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelTalents/formDetail?id="+id;
			top.$.jBox.open(url, "特长信息",1000,700,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
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
	function aa(){
		alert(window.document.getElementById('uname').value);
		console.log(window.document.getElementById('uname').value);
	}
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/personnel/personnelTalents/">人才库</a></li>
	<%--不知道mType干啥用的啊--%>
	<li><a href="${ctx}/personnel/personnelSpeciality/?mType=1">专长信息</a></li>
	<li><a href="${ctx}/personnel/personnelSkill?mType=1">高层次人才</a></li>
</ul>
	<form:form id="searchForm" modelAttribute="personnelTalents" action="${ctx}/personnel/personnelTalents/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>单位</th>
				<th>姓名</th>
				<th>性别</th>
				<th>身份证号</th>
				<th>出生年月</th>
				<th>特长</th>
				<%--<shiro:hasPermission name="personnel:personnelSkill:edit"><th id="handleTh">操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelSkill" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelSkill.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${personnelSkill.unit}
				</td>
				<td>
						${personnelSkill.name}
				</td>
				<td>
						${fns:getDictLabel(personnelSkill.sex, 'sex', '')}
				</td>
				<td>
						${personnelSkill.idNumber}
				</td>
				<%--<td>
					<fmt:formatDate value="${personnelSkill.birthday}" pattern="yyyy-MM-dd"/>
				</td>--%>
				<td>
					${personnelSkill.birthday}
				</td>
				<td>
						${personnelSkill.skill}
				</td>
				<%--<td class="handleTd">
					<a onclick="openDetailDialog('${personnelSkill.id}')">查看</a>
				</td>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
