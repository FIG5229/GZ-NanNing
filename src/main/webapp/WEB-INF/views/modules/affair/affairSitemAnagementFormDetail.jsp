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
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">场地名称：</span><span class="modal-custom-info2-value">${affairSitemAnagement.siteName}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">省：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairSitemAnagement.city, 'affair_sheng','')}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">市：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairSitemAnagement.city, 'affair_shi','')}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">最大承训能力：</span><span class="modal-custom-info2-value">${affairSitemAnagement.maxChengxunCapacity}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">场地电脑个数：</span><span class="modal-custom-info2-value">${affairSitemAnagement.siteComputerNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">地址：</span><span class="modal-custom-info2-value">${affairSitemAnagement.site}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">联系电话：</span><span class="modal-custom-info2-value">${affairSitemAnagement.phone}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">邮箱：</span><span class="modal-custom-info2-value">${affairSitemAnagement.email}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${affairSitemAnagement.remark}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">附件：</span><span class="modal-custom-info2-value">${affairSitemAnagement.accessoryFile}</span></div>
						</div>
						<div class="modal-custom-info2-col modal-custom-info2-col2">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key"></span><span class="modal-custom-info2-value"></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key"></span><span class="modal-custom-info2-value"></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key"></span><span class="modal-custom-info2-value"></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">场地床位个数：</span><span class="modal-custom-info2-value">${affairSitemAnagement.siteBedNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">场地食堂就餐人数：</span><span class="modal-custom-info2-value">${affairSitemAnagement.siteCanteenRepastNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">联系人：</span><span class="modal-custom-info2-value">${affairSitemAnagement.linkman}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">传真：</span><span class="modal-custom-info2-value">${affairSitemAnagement.fax}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否有效：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairSitemAnagement.isValid, 'affair_isValid','')}</span></div>
							<%--<div class="control-group">
									<label class="control-label">是否有效：</label>
									<div class="controls">
										<form:radiobuttons path="isValid" items="${fns:getDictList('affair_isValid')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
									</div>
								</div>--%>



							<%--<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">简要情况：</span><span class="modal-custom-info2-value">${affairSitemAnagement.brief}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">一级审核人：</span><span class="modal-custom-info2-value">${affairSitemAnagement.chuCheckMan}</span></div>
							<c:if test="${affairActivityMien.juCheckMan != null && affairActivityMien.juCheckMan != ''}">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">二级审核人：</span><span class="modal-custom-info2-value">${affairSitemAnagement.juCheckMan}</span></div>
							</c:if>--%>
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