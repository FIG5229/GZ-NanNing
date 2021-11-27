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
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${orgBianzhi.date}" pattern="yyyy-MM-dd"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准文号：</span><span class="modal-custom-info2-value">${orgBianzhi.fileNo}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">参公事业编：</span><span class="modal-custom-info2-value">${orgBianzhi.cgsyb}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">其他编制数：</span><span class="modal-custom-info2-value">${orgBianzhi.otherNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">差额拨款编制数：</span><span class="modal-custom-info2-value">${orgBianzhi.ceNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">编制总数：</span><span class="modal-custom-info2-value">${orgBianzhi.sum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${orgBianzhi.remark}</span></div>
						</div>
						<div class="modal-custom-info2-col modal-custom-info2-col2">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准单位：</span><span class="modal-custom-info2-value">${orgBianzhi.unit}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">政法专项编：</span><span class="modal-custom-info2-value">${orgBianzhi.zfzxb}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">事业编：</span><span class="modal-custom-info2-value">${orgBianzhi.syb}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">全额拨款编制数：</span><span class="modal-custom-info2-value">${orgBianzhi.qeNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">经费自理编制数：</span><span class="modal-custom-info2-value">${orgBianzhi.jfzlNum}</span></div>
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