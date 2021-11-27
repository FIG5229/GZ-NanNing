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
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">用户姓名：</span><span class="modal-custom-info2-value">${affairPersonnelMessage.username}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${affairPersonnelMessage.idNumber}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPersonnelMessage.gender, 'sex','')}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">民族：</span><span class="modal-custom-info2-value">${affairPersonnelMessage.nation}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出生日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairPersonnelMessage.birthDate}" pattern="yyyy-MM-dd HH:mm"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">政治面貌：</span><span class="modal-custom-info2-value">${affairPersonnelMessage.politicsStatus}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学历：</span><span class="modal-custom-info2-value">${affairPersonnelMessage.educationBackground}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学位：</span><span class="modal-custom-info2-value">${affairPersonnelMessage.degree}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">机构：</span><span class="modal-custom-info2-value">${affairPersonnelMessage.organization}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务：</span><span class="modal-custom-info2-value">${affairPersonnelMessage.post}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">参加工作日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairPersonnelMessage.workTime}" pattern="yyyy-MM-dd"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">手机号码：</span><span class="modal-custom-info2-value">${affairPersonnelMessage.phone}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">到达时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairPersonnelMessage.arrivalTime}" pattern="yyyy-MM-dd"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">到达车次/航班：</span><span class="modal-custom-info2-value">${affairPersonnelMessage.arrivalTrainNumber}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">到达车站/机场：</span><span class="modal-custom-info2-value">${affairPersonnelMessage.arrivalStationAirport}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公免号：</span><span class="modal-custom-info2-value">${affairPersonnelMessage.officialAccounts}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">返程时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairPersonnelMessage.returnTime}" pattern="yyyy-MM-dd"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">返程车次/航班：</span><span class="modal-custom-info2-value">${affairPersonnelMessage.returnTrainNumber}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">返程车站/机场：</span><span class="modal-custom-info2-value">${affairPersonnelMessage.returnStationAirport}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否预定返程车票：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPersonnelMessage.isReserveReturnTicket, 'affair_isValid','')}</span></div>
						</div>
					</div>
					<%--<div class="modal-custom-info1-file">
						<div class="modal-custom-info1-file-1">附件：</div>
						<div class="modal-custom-info1-file-r">
							<c:forEach items="${filePathList}" var="m" varStatus="status">
								<div class="modal-custom-info1-file-item">
									<span>${m.fileName}</span>
										&lt;%&ndash;<a href="#">在线预览</a>&ndash;%&gt;
									<a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
								</div>
							</c:forEach>
						</div>
					</div>--%>
				</div>
				<div class="modal-custom-info1-bottom">
					<div id="print" class="modal-custom-info1-btn red">打印</div>
				</div>
			</div>
		</div>
	</div>
</div>