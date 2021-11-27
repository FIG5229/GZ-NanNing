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
								<span class="modal-custom-info2-key">时间：</span>
								<span class="modal-custom-info2-value">${affairMedicalInsurance.timeYear}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">姓名：</span>
								<span class="modal-custom-info2-value">${affairMedicalInsurance.name}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">性别：</span>
								<span class="modal-custom-info2-value">${affairMedicalInsurance.sex}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">出生年月：</span>
								<span class="modal-custom-info2-value">${affairMedicalInsurance.birthday}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">年龄：</span>
								<span class="modal-custom-info2-value">${affairMedicalInsurance.age}</span>
							</div>
						</div>
					</div>


					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">身份证号：</span>
								<span class="modal-custom-info2-value">${affairMedicalInsurance.idNumber}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">缴费基数：</span>
								<span class="modal-custom-info2-value">${affairMedicalInsurance.cardinalNumber}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">个人缴费比例：</span>
								<span class="modal-custom-info2-value">${affairMedicalInsurance.individualProportion}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">月个人缴费：</span>
								<span class="modal-custom-info2-value">${affairMedicalInsurance.individualPayment}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">单位缴费比例：</span>
								<span class="modal-custom-info2-value">${affairMedicalInsurance.unitProportion}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">月单位缴费：</span>
								<span class="modal-custom-info2-value">${affairMedicalInsurance.unitPayment}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">月个账划入比例：</span>
								<span class="modal-custom-info2-value">${affairMedicalInsurance.monthAccount}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">月个账划入：</span>
								<span class="modal-custom-info2-value">${affairMedicalInsurance.monthAccountDelimit}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">补充资金月个账划入比例：</span>
								<span class="modal-custom-info2-value">${affairMedicalInsurance.addition}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">补充资金月个账划入：</span>
								<span class="modal-custom-info2-value">${affairMedicalInsurance.additionFunds}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">全区公务员平均月工资：</span>
								<span class="modal-custom-info2-value">${affairMedicalInsurance.averageSalary}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">年度补助比例：</span>
								<span class="modal-custom-info2-value">${affairMedicalInsurance.annualProportion}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">年度个账划入：</span>
								<span class="modal-custom-info2-value">${affairMedicalInsurance.annualAccountDelimit}</span>
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