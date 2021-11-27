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
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${orgJobNumber.date}" pattern="yyyy-MM-dd"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准文号：</span><span class="modal-custom-info2-value">${orgJobNumber.fileNo}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">部级副职领导职数：</span><span class="modal-custom-info2-value">${orgJobNumber.bfNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">厅级副职领导职数：</span><span class="modal-custom-info2-value">${orgJobNumber.tfNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">厅级副职非领导职数：</span><span class="modal-custom-info2-value">${orgJobNumber.tffNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">处级副职领导职数：</span><span class="modal-custom-info2-value">${orgJobNumber.cfNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">处级副职非领导职数：</span><span class="modal-custom-info2-value">${orgJobNumber.cffNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">科级副职领导职数：</span><span class="modal-custom-info2-value">${orgJobNumber.kfNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">科级副职非领导职数：</span><span class="modal-custom-info2-value">${orgJobNumber.kffNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">股级副职领导职数：</span><span class="modal-custom-info2-value">${orgJobNumber.gfNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">股级副职领导职数：</span><span class="modal-custom-info2-value">${orgJobNumber.gfNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${orgJobNumber.remark}</span></div>
						</div>
						<div class="modal-custom-info2-col modal-custom-info2-col2">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准单位：</span><span class="modal-custom-info2-value">${orgJobNumber.unit}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">部级正职领导职数：</span><span class="modal-custom-info2-value">${orgJobNumber.bzNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">厅级正职领导职数：</span><span class="modal-custom-info2-value">${orgJobNumber.tzNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">厅级正职非领导职数：</span><span class="modal-custom-info2-value">${orgJobNumber.tzfNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">处级正职领导职数：</span><span class="modal-custom-info2-value">${orgJobNumber.czNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">处级正职非领导职数：</span><span class="modal-custom-info2-value">${orgJobNumber.czfNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">科级正职领导职数：</span><span class="modal-custom-info2-value">${orgJobNumber.kzNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">科级正职非领导职数：</span><span class="modal-custom-info2-value">${orgJobNumber.kzfNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">股级正职领导职数：</span><span class="modal-custom-info2-value">${orgJobNumber.gzNum}</span></div>
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