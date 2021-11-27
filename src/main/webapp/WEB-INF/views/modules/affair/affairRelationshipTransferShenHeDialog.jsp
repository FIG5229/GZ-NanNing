<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统内组织关系移交转接审核</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function setStatus(status) {
			$("#status").val(status);
			$.post($("#inputForm").attr('action'), $("#inputForm").serialize(), function(result){
				window.parent.window.jBox.close();
			});
		};
		function setStatus(status) {
			$("#status").val(status);
			$.post($("#inputForm").attr('action'), $("#inputForm").serialize(), function(result){
				window.parent.window.jBox.close();
			});
		};
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="affairRelationshipTransfer" action="${ctx}/affair/affairRelationshipTransfer/shenHe" method="post" class="form-horizontal" cssStyle="margin-top: 20px;">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<input name="affairTransferSh.transferId" type="hidden" value="${affairRelationshipTransfer.id}"/>
		<div class="control-group">
			<label class="control-label">审核人：</label><span>${fns:getUser().name}</span>
		</div>
		<div class="control-group">
			<label class="control-label">审核意见：</label>
			<div class="controls">
				<form:textarea path="affairTransferSh.opinion" htmlEscape="false" rows="6" class="input-xxlarge "/>
			</div>
		</div>
		<input id="status" name="affairTransferSh.status" type="hidden"/>
		<div style="margin-left: 500px;margin-top: 70px;">
			<shiro:hasPermission name="affair:affairElection:manage"><input id="btnSubmit" class="btn btn-primary" type="button" value="通 过" onclick="setStatus(1)"/>&nbsp;</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairElection:manage"><input id="btnSubmit2" class="btn btn-default" type="button" value="不通过" onclick="setStatus(2)"/>&nbsp;</shiro:hasPermission>
		</div>
	</form:form>
</body>
</html>