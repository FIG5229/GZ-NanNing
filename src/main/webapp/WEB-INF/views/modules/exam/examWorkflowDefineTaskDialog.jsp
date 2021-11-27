<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考评流程-任务分配管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});

		});
		//人员分配的弹出框
		function openPersonDialog(segDefId,templatesIds,workflowDefineId,flowType) {
			var url = "iframe:${ctx}/exam/examWorkflowSegmentsTask/?segmentId="+segDefId+"&templatesIds="+templatesIds+"&workflowId="+workflowDefineId;
			top.$.jBox.open(url, "人员分配",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					//刷新本页面
					self.location.href="${ctx}/exam/examWorkflowDefine/segmentDialog?id="+workflowDefineId+"&flowType="+flowType;
				}
			});
		};
		//人员分配的弹出框
		function openPersonAssignDialog(workflowId,segmentId,segmentName) {
			/*根据segmentName判断是否多选*/
			var url = "iframe:${ctx}/exam/examWorkflowSegmentsTask/personAssign?workflowId="+workflowId+"&segmentId="+segmentId+"&segmentName="+segmentName;
			top.$.jBox.open(url, "人员分配",1000,600,{
				buttons:{"关闭":true},
				persistent: true,  //设置点击窗口外不关闭的参数
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					//刷新本页面
					self.location.href="${ctx}/exam/examWorkflowDefine/segmentDialog?id="+workflowId;
				}
			});
		};
	</script>
</head>
<body>
<br/>
<form:form id="inputForm" modelAttribute="examWorkflowDefine" action="${ctx}/exam/examWorkflowDefine/save" method="post" class="form-horizontal">
	<sys:message content="${message}"/>
	<div>
		<div>
			<div>
				<table class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>环节名称</th>
							<th>操作</th>
							<th>状态</th>
						</tr>
					</thead>
					<tbody id="examWorkflowDefineList">
					<c:forEach items="${list}" var="segment" varStatus="status">
						<tr>
							<td>${segment.name}</td>
							<c:choose>
								<c:when test="${templatesIds !=null && !''.equals(templatesIds) && segment.name.indexOf('公示')== -1}">
									<td><a href="#" onclick="openPersonAssignDialog('${workflowId}','${segment.sort}','${segment.name}')">
										任务分配</a></td>
								</c:when>
								<c:otherwise>
										<td>任务分配</td>
								</c:otherwise>
							</c:choose>
                            <td>
								<c:choose>
									<c:when test="${templatesIds ==null || ''.equals(templatesIds)}">
										<span style="color:#E40000;">考评模板未选择</span>
									</c:when>
									<c:otherwise>
										${segment.status}
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<%--<div class="form-actions">
		&lt;%&ndash;<shiro:hasPermission name="exam:examWorkflowDefine:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>&ndash;%&gt;
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
	</div>--%>
</form:form>
</body>
</html>