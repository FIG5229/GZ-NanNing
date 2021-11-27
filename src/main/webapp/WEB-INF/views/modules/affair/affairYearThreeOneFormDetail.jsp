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
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">会议名称：</span><span class="modal-custom-info2-value">${affairYearThreeOne.name}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">会议时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairYearThreeOne.date}" pattern="yyyy-MM-dd HH:mm"/></span></div>
						</div>
						<div class="modal-custom-info2-col modal-custom-info2-col2">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">会议类型：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairYearThreeOne.type, 'affair_year_three_one_type_Two', '')}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">主持人：</span><span class="modal-custom-info2-value">${affairYearThreeOne.hold}</span></div>
						</div>
						<div class="modal-custom-info2-col modal-custom-info2-col3" style="width: 450px;">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">党组织：</span><span class="modal-custom-info2-value" style="width: 340px;">${affairYearThreeOne.partyOrganization}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">记录人：</span><span class="modal-custom-info2-value" style="width: 340px;">${affairYearThreeOne.noteTaker}</span></div>
						</div>
					</div>
					<div class="modal-custom-info2-col modal-custom-info2-col4">
						<div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 140px;">会议议程：</span><span class="modal-custom-info2-value" style="padding: 0 110px;">${affairYearThreeOne.agenda}</span></div>
<%--
						<div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 140px;">应参会人员：</span><span class="modal-custom-info2-value" style="padding: 0 110px;">${affairYearThreeOne.person1}</span></div>
--%>
						<div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 140px;">已会人员：</span><span class="modal-custom-info2-value" style="padding: 0 110px;">${affairYearThreeOne.person2}</span></div>
						<div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 140px;">未会人员：</span><span class="modal-custom-info2-value" style="padding: 0 110px;">${affairYearThreeOne.person3}</span></div>
					</div>
					<div>
						<span>相关文件和影像视频：</span>
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