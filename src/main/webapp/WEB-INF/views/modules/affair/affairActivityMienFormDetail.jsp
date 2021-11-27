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
				afterPrint:function(){
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
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">活动名称：</span><span class="modal-custom-info2-value">${affairActivityMien.name}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">活动时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairActivityMien.startDate}" pattern="yyyy-MM-dd HH:mm"/>至<fmt:formatDate value="${affairActivityMien.endDate}" pattern="yyyy-MM-dd HH:mm"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">参加人员：</span><span class="modal-custom-info2-value">${affairActivityMien.joinPerson}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">活动内容：</span><span class="modal-custom-info2-value">${affairActivityMien.content}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">申报状态：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairActivityMien.checkType, 'check_type', '')}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">申报人：</span><span class="modal-custom-info2-value">${affairActivityMien.submitMan}</span></div>
						</div>
						<div class="modal-custom-info2-col modal-custom-info2-col2">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">举办单位：</span><span class="modal-custom-info2-value">${affairActivityMien.unit}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">活动地点：</span><span class="modal-custom-info2-value">${affairActivityMien.place}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">简要情况：</span><span class="modal-custom-info2-value">${affairActivityMien.brief}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">一级审核人：</span><span class="modal-custom-info2-value">${affairActivityMien.chuCheckMan}</span></div>
							<c:if test="${affairActivityMien.juCheckMan != null && affairActivityMien.juCheckMan != ''}">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">二级审核人：</span><span class="modal-custom-info2-value">${affairActivityMien.juCheckMan}</span></div>
							</c:if>
						</div>
					</div>
					<div class="modal-custom-info1-file">
						<div class="modal-custom-info1-file-1">附件：</div>
						<div class="modal-custom-info1-file-r">
							<c:forEach items="${filePathList}" var="m" varStatus="status">
								<div class="modal-custom-info1-file-item">
									<span>${m.fileName}</span>
										<%--<a href="#">在线预览</a>--%>
									<a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
								</div>
							</c:forEach>
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