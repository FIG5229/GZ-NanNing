<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考评流程定义管理</title>
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
			//调用
			getList();

			//根据考评流程类型的值渲染table的tbody内容
			function getList(){
				/**
				if($("#examWorkflowDefineId").val() != null && $("#examWorkflowDefineId").val() != undefined && $("#examWorkflowDefineId").val() != ''){
					$('#flowType').attr('disabled', 'true');//禁用
				}
				 **/
				var list = [];
				var tbBody = "";
				$("#examWorkflowDefineList").html("");
				$.ajax({
					url:"${ctx}/exam/examWorkflowSegmentsDefine/findByType",
					dataType:"json",
					async:true,//请求是否异步，默认为异步
					data:{type:$("#flowType").val()},
					type:"POST",
					success:function(res){
						if (res.result != null){
							list = res.result;
							var segments = [];
							var sorts = [];
							list.forEach((item, index) => {
								tbBody += "<tr><td>"+item.name+"</td></tr>";
								//tbBody += "<tr><td>"+item.name+"</td><td><a onclick='openTaskDialog("+index+")'>任务分配</a></td><td id="+"td"+index+">未分配</td></tr>";
								segments.push(item.name);
								sorts.push(item.sort);
							})
							/*for(var i=0 ;i<list.length;i++){
								tbBody += "<tr><td>"+list[i].name+"</td><td><a onclick='openTaskDialog("+i+")'>任务分配</a></td><td id="+"td"+i+">未分配</td></tr>";
							}*/
							$("#examWorkflowDefineList").append(tbBody);
							$("#segments").val(segments);
							$("#sorts").val(sorts);
						}
					}
				});
			};
			//考评流程类型改变时触发事件，重新渲染table的tbody内容
			$("#flowType").change(function(){
				getList();
			});
		});
		//任务分配的弹出框
		function openTaskDialog(index) {
			var url = "iframe:${ctx}/exam/examWorkflowSegmentsTask/";
			top.$.jBox.open(url, "任务分配",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					//$("#td"+index).html("已分配");
				}
			});
		};
		if("sucess"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</head>
<body>
<%--<ul class="nav nav-tabs">
	<li><a href="${ctx}/exam/examWorkflowDefine/">考评流程定义列表</a></li>
	<li class="active"><a href="${ctx}/exam/examWorkflowDefine/form?id=${examWorkflowDefine.id}">考评流程定义<shiro:hasPermission name="exam:examWorkflowDefine:edit">${not empty examWorkflowDefine.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="exam:examWorkflowDefine:edit">查看</shiro:lacksPermission></a></li>
</ul>--%>
<br/>
<form:form id="inputForm" modelAttribute="examWorkflowDefine" action="${ctx}/exam/examWorkflowDefine/save" method="post" class="form-horizontal">
	<form:hidden id="examWorkflowDefineId" path="id"/>
	<%--环节名称集合--%>
	<input id="segments" name="segments" type="hidden" value=""/>
	<%--环节排序集合--%>
	<input id="sorts" name="sorts" type="hidden" value=""/>
	<sys:message content="${message}"/>
	<div class="control-group">
		<label class="control-label">流程名称：</label>
		<div class="controls">
			<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
			<span class="help-inline"><font color="red">*</font> </span>
			考评类别：
			<form:select path="examType" class="input-xlarge required">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('kp_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			<span class="help-inline"><font color="red">*</font> </span>
			考评周期：
			<form:select path="examCycle" class="input-xlarge required" cssStyle="width: 100px;">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('exam_cycle')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			<span class="help-inline"><font color="red">*</font> </span>
			被考评对象类别：
			<form:select path="examObjectType" class="input-xlarge required" cssStyle="width: 100px;">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('exam_object_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">对应考评标准模板：</label>
		<div class="controls">
			<%--<sys:treeselect id="templatesName" name="templatesIds" value="${examWorkflowDefine.templatesIds}" labelName="templatesName" labelValue="${examWorkflowDefine.templatesName}"
                    title="标准模板" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true" cssStyle="width:1000px;" dataMsgRequired="必填信息"/>--%>
			<form:select path="templatesIdsArr" class="input-xlarge required" multiple="true" cssStyle="width: 500px;">
				<form:option value="" label=""/>
				<form:options items="${templateFile}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">考评流程类型：</label>
		<div class="controls">
			<form:select id="flowType" path="flowType" class="input-xlarge required" >
				<%--<form:option value="" label=""/>--%>
				<form:options items="${fns:getDictList('exam_flow_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label"></label>
		<div class="controls">
			<div>
				<table class="table table-striped table-bordered table-condensed" style="width: 30%;">
					<thead>
						<tr>
							<th>环节名称</th>
							<%--<th>操作</th>
							<th>状态</th>--%>
						</tr>
					</thead>
					<tbody id="examWorkflowDefineList"></tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="form-actions">
		<shiro:hasPermission name="exam:examWorkflowDefine:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
	</div>
</form:form>
</body>
</html>