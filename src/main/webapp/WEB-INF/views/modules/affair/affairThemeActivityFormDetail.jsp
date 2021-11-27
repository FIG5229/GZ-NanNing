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
				loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
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
<div id="modalInfo1">
	<div class="modal-custom">
		<div class="modal-custom-main">
			<div class="modal-custom-content">
				<div id="contentTable" class="modal-custom-content">
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">活动名称：</span><span class="modal-custom-info2-value">${affairThemeActivity.name}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">党组织名称：</span><span class="modal-custom-info2-value">${affairThemeActivity.partyOrganizationName}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">举办单位：</span><span class="modal-custom-info2-value">${affairThemeActivity.holdUnitName}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">活动时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairThemeActivity.startTime}" pattern="yyyy-MM-dd HH:mm"/> 至 <fmt:formatDate value="${affairThemeActivity.endTime}" pattern="yyyy-MM-dd HH:mm"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">活动地点：</span><span class="modal-custom-info2-value">${affairThemeActivity.place}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">参加人员：</span><span class="modal-custom-info2-value">${affairThemeActivity.joinPerson}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工作流程：</span><span class="modal-custom-info2-value">${affairThemeActivity.workflow}</span></div>
						</div>
						<div class="modal-custom-info2-col modal-custom-info2-col2">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">类型：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairThemeActivity.type, 'affair_theme_activity', '')}</span></div>
						</div>
					</div>
					<div>
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