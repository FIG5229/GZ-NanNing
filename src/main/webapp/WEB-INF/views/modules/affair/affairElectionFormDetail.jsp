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
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">党组织名称：</span><span class="modal-custom-info2-value">${affairElection.partyOrganizationName}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">该届换届时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairElection.hjDate}" pattern="yyyy-MM-dd"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">实到会人数：</span><span class="modal-custom-info2-value">${affairElection.sdhNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">换届资料：</span><span class="modal-custom-info2-value">${affairElection.electionInformation}</span></div>
						</div>
						<div class="modal-custom-info2-col modal-custom-info2-col2">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">届次：</span><span class="modal-custom-info2-value">${affairElection.jc}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">该届届满时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairElection.jmDate}" pattern="yyyy-MM-dd"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准委员名额：</span><span class="modal-custom-info2-value">${affairElection.quota}</span></div>
						</div>
						<div class="modal-custom-info2-col modal-custom-info2-col3">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">选举方式：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairElection.method, 'affair_xuanju_method', '')}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">应到会人数：</span><span class="modal-custom-info2-value">${affairElection.ydhNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">审核状态：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairElection.status2, 'affair_query_shenhe', '')}</span></div>
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