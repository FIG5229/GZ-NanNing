<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script type="text/javascript">
	$(document).ready(function () {
		var children= $("#tabs").children()[0].click();
	});
	function changeStandard(standardId) {
		$("#tabs").children().each(function () {
			if($(this).attr('id')==standardId){
				$(this).addClass("red");
			}
			else{
				$(this).removeClass("red");
			}
		});
		var url="${ctx}/exam/examWorkflowSegmentsTask/content?workflowId=${workflowId}&segmentId=${segmentId}&standardId="+standardId+"&segmentName=${segmentName}";
		$("#iframe").attr("src",url);
	}
</script>
<div id="modalSysSelf">
	<div class="">
		<div class="modal-custom-main">
			<div class="modal-custom-bar" id="tabs">
				<c:forEach items="${standardInfoList}" var="info" varStatus="status">
					<c:choose>
						<c:when test="${status.index==0}">
							<%--好多简称一模一样  无法区分--%>
							<div class="modal-custom-btn red"  onclick="changeStandard('${info.id}','info.modelType')" id="${info.id}">${info.name}</div>
						</c:when>
						<c:otherwise>
							<div class="modal-custom-btn" onclick="changeStandard('${info.id}','info.modelType')" id="${info.id}">${info.name}</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<div class="modal-custom-content">
				<iframe id="iframe" src="" width="100%" height="500" style="border:0;" noresize="noresize"></iframe>
			</div>
		</div>
	</div>
</div>