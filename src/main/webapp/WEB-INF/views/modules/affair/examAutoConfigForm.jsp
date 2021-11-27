<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>自动考评配置管理</title>
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
		let height =  $(window).height()-200;
		/*子iframe获取不到let类型 可以获取到var类型*/
		var test =  "咋获取不到呢";
		let width =  $(window).width()-200;
		/*推送失败的考评项进行更新*/
		function updateTerm(id) {
			<%--<c:choose>
			<c:when test="${standardInfoList.size() != 0 }">
			var url = "iframe:${ctx}/exam/examWorkflow/updateTermView?standardId=${standardInfoList.get(0).id}&id=${workflowId}&objId=${objId}&fillPersonId=${fillPersonId}&workflowDatasId="+id;
			</c:when>
			<c:otherwise>
			var url = "iframe:${ctx}/exam/examWorkflow/updateTermView?&id=${workflowId}&objId=${objId}&fillPersonId=${fillPersonId}&workflowDatasId="+id;
			</c:otherwise>
			</c:choose>--%>
			var url = "iframe:${ctx}/exam/examWorkflow/selectTermBeta?standardId="+$("#standardId").val();
			<%--var url = "iframe:${ctx}/exam/examWorkflow/selectTerm?standardId=${standardInfoList.get(0).id}&workflowId=${workflowId}&objId=${objId}&fillPersonId=${fillPersonId}";--%>
			this.$.jBox.open(url, "选择考评项",width,height,{
				id:"termIFrame",
				target:top,
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){

				}
			});
		}

		function setRowNum(rowNum) {
			console.log(rowNum);
			$("#rowNum").val(rowNum);
		}
		function getOptionByStandardRowNum(rowNum) {
			console.log(rowNum+"------>getOptionByStandardRowNum");
			$.ajax({
            type:"post",
            url: "${ctx}/exam/examStandardTemplateData/getStandardOption",
                data:{standard:$("#standardId").val(),rowId:rowNum},
                success: function(data){
            	//debugger;er;
                    if (data.success){
                        $.jBox.closeTip();
                        window.parent.jBox.close();
                        $("#standardOption").val(data.result.itemValue);
                        // $("#optionId").val(data.result.itemId);
                    }
                }
            });
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/examAutoConfig/">自动考评配置列表</a></li>
		<li class="active"><a href="${ctx}/affair/examAutoConfig/form?id=${examAutoConfig.id}">自动考评配置<shiro:hasPermission name="affair:examAutoConfig:edit">${not empty examAutoConfig.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:examAutoConfig:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="examAutoConfig" action="${ctx}/affair/examAutoConfig/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">考评类别：</label>
			<div class="controls">
				<form:select path="kpType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('kp_type')}" itemLabel="label" itemValue="value"
								  htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考评周期：</label>
			<div class="controls">
				<form:select path="cycle" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('exam_cycle')}" itemLabel="label" itemValue="value"
								  htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考评标准：</label>
			<div class="controls">
				<form:select path="standardId" class="input-xlarge required"  cssStyle="width: 500px;">
					<form:option value="" label=""/>
					<form:options items="${templateList}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">评分标准：</label>
			<div class="controls">
				<form:input id="standardOption" path="standardOption" htmlEscape="false" class="input-xlarge " readonly="true"/>
				<a href="javascript:;" onclick="updateTerm('${workflowData.id}')">选择标准</a>
			</div>
		</div>
		<input id="rowNum" type="hidden" hidden="hidden" value="${examAutoConfig.rowNum}">
		<%--<div class="control-group">
			<label class="control-label">行号：</label>
			<div class="controls">
				<form:input id="rowNum" path="rowNum" htmlEscape="false" class="input-xlarge " readonly="true" />
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">系统：</label>
			<div class="controls">
				<form:select path="sys" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('from_sys')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考核事项：</label>
			<div class="controls">
				<form:input path="items" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>

		<div class="form-actions">
<%--			<shiro:hasPermission name="affair:examAutoConfig:edit">--%>
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
<%--			</shiro:hasPermission>--%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>