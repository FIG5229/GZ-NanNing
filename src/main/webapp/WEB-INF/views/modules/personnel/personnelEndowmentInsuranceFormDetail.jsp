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
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">保险年份：</span>
								<span class="modal-custom-info2-value">${personnelEndowmentInsurance.timeYear}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">姓名：</span>
								<span class="modal-custom-info2-value">${personnelEndowmentInsurance.name}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">身份证号：</span>
								<span class="modal-custom-info2-value">${personnelEndowmentInsurance.idNumber}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">缴费基数：</span>
								<span class="modal-custom-info2-value">${personnelEndowmentInsurance.cardinalNumber}</span>
							</div>
						</div>
					</div>


					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">养老保险个人缴费比例：</span>
								<span class="modal-custom-info2-value">${personnelEndowmentInsurance.individualProportion}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">养老保险个人月缴费：</span>
								<span class="modal-custom-info2-value">${personnelEndowmentInsurance.individualPayment}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">养老保险单位缴费比例：</span>
								<span class="modal-custom-info2-value">${personnelEndowmentInsurance.unitProportion}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">养老保险单位月缴费：</span>
								<span class="modal-custom-info2-value">${personnelEndowmentInsurance.unitPayment}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">职业年金个人缴费比例：</span>
								<span class="modal-custom-info2-value">${personnelEndowmentInsurance.oaIndividualProportion}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">职业年金个人月缴费：</span>
								<span class="modal-custom-info2-value">${personnelEndowmentInsurance.oaIndividualPayment}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">职业年金单位缴费比例：</span>
								<span class="modal-custom-info2-value">${personnelEndowmentInsurance.oaUnitProportion}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">职业年金单位月缴费：</span>
								<span class="modal-custom-info2-value">${personnelEndowmentInsurance.oaUnitPayment}</span>
							</div>
						</div>
					</div>
					<%--<div>
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