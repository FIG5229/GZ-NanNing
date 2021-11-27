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
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">主办单位：</span>
								<span class="modal-custom-info2-value">${affairTrainOutsourceStatistics.unit}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">人数：</span>
								<span class="modal-custom-info2-value">${affairTrainOutsourceStatistics.peopleSum}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">全脱产：</span>
								<span class="modal-custom-info2-value">${affairTrainOutsourceStatistics.offJob}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">半脱产：</span>
								<span class="modal-custom-info2-value">${affairTrainOutsourceStatistics.halfJob}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">不脱产：</span>
								<span class="modal-custom-info2-value">${affairTrainOutsourceStatistics.notJob}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公安部：</span>
								<span class="modal-custom-info2-value">${affairTrainOutsourceStatistics.hq}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公安省部：</span>
								<span class="modal-custom-info2-value">${affairTrainOutsourceStatistics.provincial}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公安市部：</span>
								<span class="modal-custom-info2-value">${affairTrainOutsourceStatistics.city}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公安县部：</span>
								<span class="modal-custom-info2-value">${affairTrainOutsourceStatistics.county}</span>
							</div>
						</div>
					</div>


					<%--<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">标题：</span>
								<span class="modal-custom-info2-value">${affairWorkBuild.title}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">内容：</span>
								<span class="modal-custom-info2-value">${affairWorkBuild.description}</span>
							</div>
						</div>
					</div>
					<div>
						<span>附件：</span>
						<c:forEach items="${filePathList}" var="m" varStatus="status">
							<div>
								<span>${m.fileName}</span>
									&lt;%&ndash;<a href="#">在线预览</a>&ndash;%&gt;
								<a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
							</div>
						</c:forEach>
					</div>--%>
				</div>
				<div class="modal-custom-info1-bottom">
					<div id="print" class="modal-custom-info1-btn red">打印</div>
				</div>
			</div>
		</div>
	</div>
</div>