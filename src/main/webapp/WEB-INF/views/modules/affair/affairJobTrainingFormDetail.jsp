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
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">用戶名：</span><span class="modal-custom-info2-value">${affairJobTraining.username}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">训练开始时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairJobTraining.drillDateBegin}" pattern="yyyy-MM-dd HH:mm:ss"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">训练结束时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairJobTraining.drillDateOver}" pattern="yyyy-MM-dd HH:mm:ss"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairJobTraining.name}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairJobTraining.itemClassification, 'project_type', '')}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">创建人：</span><span class="modal-custom-info2-value">${affairJobTraining.creator}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">创建日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairJobTraining.creationTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">训练概况：</span><span class="modal-custom-info2-value">${affairJobTraining.drillGeneralSituation}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注信息：</span><span class="modal-custom-info2-value">${affairJobTraining.remarks}</span></div>

<%--
							<span${fns:getDictLabel(affairJobTraining.isValid, 'affair_isValid','')}</span>
--%>
							<%--<div class="control-group">
									<label class="control-label">是否有效：</label>
									<div class="controls">
										<form:radiobuttons path="isValid" items="${fns:getDictList('affair_isValid')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
									</div>
								</div>--%>
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