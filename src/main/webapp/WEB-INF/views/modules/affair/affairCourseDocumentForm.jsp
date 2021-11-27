<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>参考文档管理</title>
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

		function addByIds(url,checkAllId,checkBoxsName) {
			var delIds = getIds(checkBoxsName);
			if (delIds.length > 0) {
				$.ajax({
					type:"post",
					url:url,
					data:{ids:delIds},
					dataType:"json",
					success:function(data){
						$.jBox.tip(data.message);
						resetCheckBox(checkAllId,checkBoxsName);
						location.reload();
					}
				})
			} else {
				$.jBox.tip('请先选择要添加的文本');
			}
		};
	</script>
</head>
<body>
		<form:form id="inputForm" modelAttribute="affairDocManagement" action="${ctx}/affair/affairCourseDocument/save" method="post" class="form-horizontal">
			<form:hidden path="id"/>
			<sys:message content="${message}"/>
			<ul class="ul-form2">
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="添加" onclick="addByIds('${ctx}/affair/affairCourseResource/addByIds?classId='${affairCourseDocument.id},'checkAll','myCheckBox')"/></li>
				<li class="clearfix"></li>
			</ul>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
				<tr>
					<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
					<th>序号</th>
					<th>文档名称</th>
					<th>创建人</th>
					<th>创建时间</th>
					<th>状态</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${list}" var="affairCourseDocument" varStatus="status">
					<tr>
						<td class="checkTd">
							<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairCourseDocument.id}"/>
						</td>
						<td>
								${(page.pageNo-1)*page.pageSize+status.index+1}
						</td>
						<td>
								${affairCourseDocument.docName}
						</td>
						<td>
								${affairCourseDocument.createBy}
						</td>
						<td>
							<fmt:formatDate value="${affairCourseDocument.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
								${fns:getDictLabel(affairCourseDocument.isdownload, 'state_result', '')}
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</form:form>
</body>
</html>