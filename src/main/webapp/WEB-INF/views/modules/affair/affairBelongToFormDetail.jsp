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
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairBelongTo.name}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所在支部：</span><span class="modal-custom-info2-value">${affairBelongTo.partyBranch}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否转离支部：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairBelongTo.isLeave, 'yes_no', '')}</span></div>
						</div>
						<div class="modal-custom-info2-col modal-custom-info2-col2">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairBelongTo.sex, 'sex', '')}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">进入支部时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairBelongTo.enterDate}" pattern="yyyy-MM-dd"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">转离支部类型：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairBelongTo.leaveType, 'affair_leave_type', '')}</span></div>
						</div>
						<div class="modal-custom-info2-col modal-custom-info2-col3">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${affairBelongTo.idNumber}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">转离支部时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairBelongTo.leaveDate}" pattern="yyyy-MM-d"/></span></div>
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