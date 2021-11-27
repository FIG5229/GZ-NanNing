<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>选课条件</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
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

		//添加，修改弹窗
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url,"",800,600 ,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairCourseTeacher"}
			});
		}

		if ("success" == "${saveResult}"){
			parent.$.jBox.close();
		}


	</script>
</head>
<body>
	<br/>
	<ul class="nav nav-tabs">
		<shiro:hasPermission name="affair:affairCourseResource:view">
			<li class="active">
				<a href="${ctx}/affair/affairCourseTeacher/list?id=${affairCourseTeacher.id}">辅导教官</a>
			</li>
			<li>
				<a href="${ctx}/affair/affairCourseResource/CKForm?id=${affairCourseTeacher.id}">参考文档</a>
			</li>
			<li>
				<a href="${ctx}/affair/affairCourseResource/HDForm?id=${affairCourseTeacher.id}">互动组件</a>
			</li>
		</shiro:hasPermission>
	</ul>
	<form:form id="inputForm" modelAttribute="affairCourseTeacher" action="${ctx}/affair/affairCourseTeacher/save" method="post" class="form-horizontal">

		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<ul>
			 <li class="btns">
			 	<input id="addBtn" class="btn btn-primary" type="button" value="添加" onclick="openDialog('${ctx}/affair/affairCourseTeacher/form?id=${affairCourseTeacher.id}')"/>
			 </li>
			 <li class="btns">
			 	<input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairCourseTeacher/deleteByIds','checkAll','myCheckBox')"/>
			 </li>
		</ul>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>教官姓名</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>状态</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.list}" var="affairCourseTeacher" varStatus="status">
				<tr>
					<td class="checkTd">
						<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairCourseTeacher.id}"/>
					</td>
					<td>
							${(page.pageNo-1)*page.pageSize+status.index+1}
					</td>
					<td>
							${affairCourseTeacher.name}
					</td>
					<td>
							${affairCourseTeacher.createBy}
					</td>
					<td>
						<fmt:formatDate value="${affairCourseTeacher.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
							${fns:getDictLabel(affairCourseTeacher.state, 'state_result', '')}
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</form:form>
</body>
</html>