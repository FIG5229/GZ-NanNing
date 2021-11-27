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
				loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
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
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairPartyRewardPunish.name}</span></div>

							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">奖惩名称：</span><span class="modal-custom-info2-value">${affairPartyRewardPunish.title}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">党组织：</span><span class="modal-custom-info2-value">${affairPartyRewardPunish.partyOrganization}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairPartyRewardPunish.approvalTime}" pattern="yyyy-MM-dd"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">奖惩说明：</span><span class="modal-custom-info2-value">${affairPartyRewardPunish.illustration}</span></div>
						</div>
						<div class="modal-custom-info2-col modal-custom-info2-col2">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPartyRewardPunish.sex, 'sex', '')}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">奖惩文号：</span><span class="modal-custom-info2-value">${affairPartyRewardPunish.fileNo}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准党委：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPartyRewardPunish.approvalOrgId, 'affair_paryt_committee', '')}</span></div>
						</div>
						<div class="modal-custom-info2-col modal-custom-info2-col3">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${affairPartyRewardPunish.idNumber}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairPartyRewardPunish.name}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">奖惩类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPartyRewardPunish.awardCategory, 'award_category', '')}</span>
							</div>
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