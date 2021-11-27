<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查看文档分类详情</title>
	<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

		});
	</script>
</head>
<body>
<br/>
<!-- 详细信息1 -->
<div id="modalInfo1">
	<div class="modal-custom">
		<div class="modal-custom-main">
			<div class="modal-custom-content">
				<div id="contentTable" class="modal-custom-content">
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">分类编码：</span><span class="modal-custom-info2-value">${affairDocClassify.classifyCode}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">分类名称：</span><span class="modal-custom-info2-value">${affairDocClassify.classifyName}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${affairDocClassify.remark}</span></div>
						</div>
					</div>

				<div class="form-actions" style="text-align: right">
					<%--<shiro:hasPermission name="affair:affairDocClassify:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>--%>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>