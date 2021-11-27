<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script type="text/javascript">
	$(document).ready(function() {
		$("#print").click(function(){
			$('.download').css('display', 'none');
			$("#contentTable").printThis({
				debug: false,
				importCSS: true,
				importStyle: true,
				printContainer: true,
				loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
				pageTitle: "打印",
				removeInline: false,
				printDelay: 333,
				header: null,
				formValues: false,
				afterPrint:function (){
					$('.download').css('display', '');
				}
			});
		});
	});
</script>
<!-- 详细信息1 -->
<div id="modalInfo1">
	<div class="modal-custom">
		<div class="modal-custom-main">
			<div class="modal-custom-content">
				<div id="contentTable" class="modal-custom-content">
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-row">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 110px;">标题：</span><span class="modal-custom-info2-value">${affairPolicewomanWork.title}</span></div>
						</div>
						<div class="modal-custom-info2-col modal-custom-info2-col4">
							<div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 110px;">发布部门：</span><span class="modal-custom-info2-value" style="width: 800px;">${affairPolicewomanWork.publishDep}</span></div>
							<div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 110px;">接收部门：</span><span class="modal-custom-info2-value" style="width: 800px;">${affairPolicewomanWork.receiveDep}</span></div>
							<div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 110px;">主要内容：</span><span class="modal-custom-info2-value" style="padding: 0 110px;">${affairPolicewomanWork.content}</span></div>
						</div>
					</div>
					<div style="padding: 0 110px;">
						<span>附件：</span>
						<c:forEach items="${filePathList}" var="m" varStatus="status">
							<div>
								<span>${m.fileName}</span>
									<%--<a href="#">在线预览</a>--%>
								<a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
							</div>
						</c:forEach>
					</div>
				</div>
				<div class="modal-custom-info1-bottom">
					<div id="print" class="modal-custom-info1-btn red">打印</div>
				</div>
			</div>
		</div>
	</div>
</div>