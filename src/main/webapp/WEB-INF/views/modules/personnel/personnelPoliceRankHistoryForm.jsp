<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>警衔信息管理</title>
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

		//添加弹窗
		function openAddEditDialog(idNumber) {
			var url = "iframe:${ctx}/personnel/personnelPoliceRank/newForm?idNumber="+idNumber;

			top.$.jBox.open(url, "警衔信息",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					parent.$.jBox.close();
				}
			});
		};

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
	<form:form id="inputForm" modelAttribute="personnelPoliceRank" action="${ctx}/personnel/personnelPoliceRank/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<ul class="ul-form2">
			<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openAddEditDialog('${idNumber}')"/></li>
		</ul>
		<sys:message content="${message}"/>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
			<tr>
				<th>单位</th>
				<th>姓名</th>
				<th>性别</th>
				<th>出生时间</th>
				<th>参加工作时间</th>
				<th>学制</th>
				<th>学制年限</th>
				<th>现任警衔</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${list}" var="personnelPoliceRank" varStatus="status">
				<tr>
					<td>
							${personnelPoliceRank.unit}
					</td>
					<td>
							${personnelPoliceRank.personName}
					</td>
					<td>
							${fns:getDictLabel(personnelPoliceRank.sex, 'sex', '')}
					</td>
					<td>
						<fmt:formatDate value="${personnelPoliceRank.birthdayTime}" pattern="yyyy-MM-dd"/>
					</td>
					<td>
						<fmt:formatDate value="${personnelPoliceRank.workTime}" pattern="yyyy-MM-dd"/>

					</td>
					<td>
							${personnelPoliceRank.xuezhi}

					</td>
					<td>
							${personnelPoliceRank.xuezhiYear}

					</td>
					<td>
							${personnelPoliceRank.nowRank}

					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</form:form>
</body>
</html>