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
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">党组织名称：</span><span class="modal-custom-info2-value">${affairClassMember.partyOrganization}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">组织委员：</span><span class="modal-custom-info2-value">${affairClassMember.zzwy}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">青年委员：</span><span class="modal-custom-info2-value">${affairClassMember.yqwy}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">联系包保情况：</span><span class="modal-custom-info2-value">${affairClassMember.situation}</span></div>
						</div>
						<div class="modal-custom-info2-col modal-custom-info2-col2">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">党组织书记：</span><span class="modal-custom-info2-value">${affairClassMember.shuji}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">宣传委员：</span><span class="modal-custom-info2-value">${affairClassMember.xcwy}</span></div>
						</div>
						<div class="modal-custom-info2-col modal-custom-info2-col3">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">党组织副书记：</span><span class="modal-custom-info2-value">${affairClassMember.fushuji}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">纪检委员：</span><span class="modal-custom-info2-value">${affairClassMember.jjwy}</span></div>
						</div>
					</div>
					<div>
						<span>工作分工及量化标准附件：</span>
						<c:forEach items="${filePathList}" var="m" varStatus="status">
							<div>
								<span>${m.fileName}</span>
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