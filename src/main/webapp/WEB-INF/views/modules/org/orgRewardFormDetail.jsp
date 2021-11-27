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
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">受奖单位名称：</span><span class="modal-custom-info2-value">${orgReward.winUnitName}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">建制标志：</span><span class="modal-custom-info2-value">${orgReward.sign}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">荣誉称号级别：</span><span class="modal-custom-info2-value">${orgReward.level}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">奖励名称：</span><span class="modal-custom-info2-value">${orgReward.rewardName}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准单位级别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(orgReward.approvalUnitLevel, 'org_approval_unit_level', '')}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准文号：</span><span class="modal-custom-info2-value">${orgReward.fileNo}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准机关类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(orgReward.approvalOrgType, 'org_approval_org_type', '')}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">撤销奖励批准单位：</span><span class="modal-custom-info2-value">${orgReward.cancelApprovalUnit}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${orgReward.remark}</span></div>
						</div>
						<div class="modal-custom-info2-col modal-custom-info2-col2">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">受奖单位代码：</span><span class="modal-custom-info2-value">${orgReward.winUnitCode}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">奖励名称代码：</span><span class="modal-custom-info2-value">${orgReward.rewardNameCode}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位警种：</span><span class="modal-custom-info2-value">${orgReward.policeType}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准单位：</span><span class="modal-custom-info2-value">${orgReward.approvalUnit}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${orgReward.approvalDate}" pattern="yyyy-MM-dd"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准文件名称：</span><span class="modal-custom-info2-value">${orgReward.fileName}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">撤销奖励日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${orgReward.cancelDate}" pattern="yyyy-MM-dd"/></span></div>
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