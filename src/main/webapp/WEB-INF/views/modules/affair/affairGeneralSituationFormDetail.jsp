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
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">党组织名称：</span><span class="modal-custom-info2-value">${affairGeneralSituation.partyOrganization}</span></div>
							<%--<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">党组织联系人：</span><span class="modal-custom-info2-value">${affairGeneralSituation.contactor}</span></div>--%>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">组织党员数：</span><span class="modal-custom-info2-value">${affairGeneralSituation.num}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所在单位：</span><span class="modal-custom-info2-value">${affairGeneralSituation.unit}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">专兼职情况：</span><span class="modal-custom-info2-value">${affairGeneralSituation.zzSituation}</span></div>
							<%--<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否是高铁领域党支部：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairGeneralSituation.isGtly, 'yes_no', '')}</span></div>--%>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">党组织简介：</span><span class="modal-custom-info2-value">${affairGeneralSituation.introduction}</span></div>
						</div>

						<div class="modal-custom-info2-col modal-custom-info2-col2">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">党组织类型：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairGeneralSituation.type1, 'affair_g_s_type1', '')}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">联系电话：</span><span class="modal-custom-info2-value">${affairGeneralSituation.telephone}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">党组织成立时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairGeneralSituation.foundDate}" pattern="yyyy-MM-dd"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否设支委会：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairGeneralSituation.isSzwh, 'yes_no', '')}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">民警数：</span><span class="modal-custom-info2-value">${affairGeneralSituation.zgNum}</span></div>
						</div>
						<div class="modal-custom-info2-col modal-custom-info2-col3" style="width: 200px;">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">党组织书记：</span><span class="modal-custom-info2-value" style="width: 90px;">${affairGeneralSituation.shuji}</span></div>
							<%--<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">党组织类别：</span><span class="modal-custom-info2-value" style="width: 90px;">${fns:getDictLabel(affairGeneralSituation.type2, 'affair_g_s_type2', '')}</span></div>--%>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">换届时间：</span><span class="modal-custom-info2-value" style="width: 90px;"><fmt:formatDate value="${affairGeneralSituation.hjDate}" pattern="yyyy-MM-dd"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所在地区：</span><span class="modal-custom-info2-value" style="width: 90px;">${affairGeneralSituation.area}</span></div>
						</div>
					</div>
				</div>
				<div class="modal-custom-info1-bottom">
					<div id="print" class="modal-custom-info1-btn red">打印</div>
					<%--<div class="modal-custom-info1-btn red">导出</div>--%>
				</div>
			</div>
		</div>
	</div>
</div>