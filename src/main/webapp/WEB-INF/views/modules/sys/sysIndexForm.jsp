<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>索引管理管理</title>
	<meta name="decorator" content="default"/>
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
	</script>
</head>
<body>

	<form:form id="inputForm" modelAttribute="sysIndex" action="${ctx}/sys/sysIndex/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<br>

		<div class="control-group">
			<label class="control-label">角色编号：</label>
			<div class="controls">
				<form:select path="roleId" class="input-xlarge " id="production" name="roleId">
					<form:option value="" label=""/>
					<c:forEach items="${strings}" var="strings">
						<form:option value="${strings.id}" label="${strings.name}"/><%--:${strings.id}--%>
					</c:forEach>
				</form:select>

			</div>
		</div>

<%--

		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:select path="name" class="input-xlarge " id="selectName" >
					<form:option value="" label=""/>
					<c:forEach items="${selectNames}" var="selectNames">
						<form:option value="${selectNames}" label="${selectNames} "/>
					</c:forEach>
				</form:select>

			</div>
		</div>
--%>


		<%--<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">索引：</label>
			<div class="controls">
				<form:input path="indexCode" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>


		<div class="form-actions">
			<shiro:hasPermission name="sys:sysIndex:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
	<script language="JavaScript">
		if ("sucess" == "${saveResult}") {
			parent.$.jBox.close();
		}
		var deviceList = [];
		function inquireIds() {
			$.ajax({
				url: "${ctx}/sys/sysIndex/inquireIds",
				type: "post",
				success: function (data) {
					deviceList = data;
					if (data != null && data != "" && data != undefined) {
						for (var i = 0; i < deviceList.length; i++) {
							console.log(deviceList[i].name+":"+deviceList[i].id)
							$('#production').append("<option value='"+deviceList[i].id+':'+deviceList[i].name+"'>" + deviceList[i].id+':'+deviceList[i].name+ "</option>");
						}

					}
				}
			});
		}
/*-------------------------------------------下面暂时用不到-----------------------------------------------------*/
			function inquireNames() {
				var id = window.document.getElementById("production").value;
				$.ajax({
					url: "${ctx}/sys/sysIndex/inquireNames?id="+id,
					type: "post",
					success: function (data) {
						if (data != null && data != "" && data != undefined) {
							for (var i = 0; i < data.length; i++) {
								//清空下拉框中的值
								var selectName= document.getElementById("selectName");
								selectName.innerHTML = "";
								$('#selectName').append("<option value='" + data[i] + "' >" + data[i] + "</option>");

							}
						}
					}
				});
			}


	</script>
</body>
</html>