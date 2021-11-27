<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script type="text/javascript">
	$(document).ready(function() {
		$("#print").click(function(){
			$("#contentTable").printThis({
				debug: false,
				importCSS: true,
				importStyle: true,
				printContainer: true,
				loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
				pageTitle: "打印",
				removeInline: false,
				printDelay: 333,
				header: null,
				formValues: false
			});
		});
	});
</script>
<div id="modalInfo1">
	<div class="modal-custom">
		<div class="modal-custom-main">
			<div class="modal-custom-content">
				<div id="contentTable" class="modal-custom-content">
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位邮政编码：</span><span class="modal-custom-info2-value">${orgCommunication.postCode}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位地址：</span><span class="modal-custom-info2-value">${orgCommunication.address}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位网址：</span><span class="modal-custom-info2-value">${orgCommunication.website}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位E_MAIL地址：</span><span class="modal-custom-info2-value">${orgCommunication.email}</span></div>
						</div>
						<div class="modal-custom-info2-col modal-custom-info2-col2">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位电话号码：</span><span class="modal-custom-info2-value">${orgCommunication.telephone}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位传真号码：</span><span class="modal-custom-info2-value">${orgCommunication.faxNumber}</span></div>
						</div>
					</div>
				</div>
				<div class="modal-custom-info1-bottom">
					<div id="print" class="modal-custom-info1-btn red">打印</div>
				</div>
			</div>
		</div>
	</div>
</div>