<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人民警察证信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});


		//历史记录
		function openAddEditDialog(idNumber) {
			var url = "iframe:${ctx}/personnel/personnelPoliceCertificate/newForm?idNumber="+idNumber;
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
	<br/>
	<form:form id="inputForm" modelAttribute="personnelPoliceCertificate" action="${ctx}/personnel/personnelPoliceCertificate/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<ul class="ul-form2">
			<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openAddEditDialog('${personnelPoliceCertificate.idNumber}')"/></li>





		</ul>
		<sys:message content="${message}"/>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
			<tr>

				<th>姓名</th>
				<th>身份证号</th>
				<th>警察证号</th>
				<th>制发原因</th>
				<th>警察证状态</th>
				<th>发证日期</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${list}" var="personnelPoliceCertificate" varStatus="status">
				<tr>
					<td>
							${personnelPoliceCertificate.name}
					</td>
					<td>
							${personnelPoliceCertificate.idNumber}
					</td>
					<td>
							${personnelPoliceCertificate.certificateNo}
					</td>
					<td>
							${fns:getDictLabel(personnelPoliceCertificate.createReason, 'certification_type', '')}
					</td>
					<td>
							${fns:getDictLabel(personnelPoliceCertificate.status, 'personnel_jcztype', '')}
					</td>
					<td>
						<fmt:formatDate value="${personnelPoliceCertificate.sendDate}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</form:form>
</body>
</html>