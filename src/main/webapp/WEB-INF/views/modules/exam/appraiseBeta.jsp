<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script type="text/javascript">
	$(document).ready(function () {
		// var children= $("#tabs").children()[0].click();
	});
	function changeStandard(standardId,modelType) {
		$("#tabs").children().each(function () {
			if($(this).attr('id')==standardId){
				$(this).addClass("red");
			}
			else{
				$(this).removeClass("red");
			}
		});
		var url="${ctx}/exam/examWorkflow/appaise/content?examType=${examType}&standardId="+standardId+"&modelType="+modelType+"&workflowId="+$("#workflowId").val()+"&status="+$("#status").val()+"&processType=${processType}&personType=${personType}&history=${history}";
		$("#iframe").attr("src",url);
	}
	function back(){
		window.location.href = "${ctx}/exam/examWorkflow/flowList?examType=${examType}"
	}
	function chanage(standardId,modelType) {
		/*iframe为子页面Id*/
		var childWindow = $("#iframe")[0].contentWindow; //表示获取了嵌入在iframe中的子页面的window对象。  []将JQuery对象转成DOM对象，用DOM对象的contentWindow获取子页面window对象。
		childWindow.subFunction(standardId,modelType);  //调用子页面中的subFunction方法。
	}
	//生成表格
	function createTable() {
		var url = "iframe:${ctx}/exam/examWorkflow/createTable?workflowId="+$("#workflowId").val();
		top.$.jBox.open(url, "目前各项得分情况",1000,600,{
			buttons:{"关闭":true},
			loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	}
</script>
<input id="workflowId" name="workflowId" value="${workflowId}" type="hidden"/>
<input id="status" name="status" value="${status}" type="hidden"/>
<!-- 绩效考评-系统自评 -->
<div id="modalSysSelf">
	<div class="">
		<div class="modal-custom-main">
			<%@include file="/WEB-INF/views/modules/exam/step.jsp" %>
			<%--<div class="modal-custom-bar" id="tabs">
				&lt;%&ndash;<c:forEach items="${standardInfoList}" var="info" varStatus="status">
					<c:choose>
						<c:when test="${status.index==0}">
							<div class="modal-custom-btn red"  onclick="changeStandard('${info.id}','info.modelType')" id="${info.id}">${info.abbreviation}</div>
						</c:when>
						<c:otherwise>
							<div class="modal-custom-btn" onclick="changeStandard('${info.id}','info.modelType')" id="${info.id}">${info.abbreviation}</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>&ndash;%&gt;
				&lt;%&ndash;	<label>考评标准：</label>
				<select id="standardIds" style="width: 120px;" onchange="standardChange()">
					<c:forEach items="${standardInfoList}" var="standard" varStatus="status">
						<option value="${standard.id}">${standard.abbreviation}</option>
					</c:forEach>
				</select>&ndash;%&gt;
			</div>--%>
			<div>
				&nbsp;&nbsp;${headItemScoreMap.cgInfo}&nbsp;&nbsp;${headItemScoreMap.zdInfo}&nbsp;&nbsp;${headItemScoreMap.decInfo}&nbsp;&nbsp;${headItemScoreMap.addInfo}&nbsp;&nbsp;
				<%--&nbsp;&nbsp;业务得分：(治安工作：5分)&nbsp;&nbsp;重点工作：(反恐防暴：5分)&nbsp;&nbsp; 公共部分得分：4分&nbsp;&nbsp;--%>
				<input id="check" class="btn btn-primary" type="button" value="生成表格" onclick="createTable()"/>
				<%--<input id="check" class="btn btn-primary" type="button" value="汇总表" onclick=""/>--%>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="complete" class="btn btn-primary" type="button" value="返回" onclick="back()"/>
			</div>
			<div class="modal-custom-content">

<%--				<iframe id="iframe" src="" width="100%" height="500" style="border:0;" noresize="noresize"></iframe>--%>
				<%@include file="/WEB-INF/views/modules/exam/examWorkflowDatasListBeta.jsp" %>
<%--				<jsp:include page="appraise_content_beta.jsp"/>--%>
			</div>
		</div>
	</div>
</div>