<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>自动考评考评标准关联管理</title>
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
			var evealType = $("#evealType").val();
			if(evealType==null || evealType == '' || evealType=='1'||evealType == '2' || evealType == '5' || evealType == '6' || evealType == '7'){
				$("#suoshuC").hide();
			}else{
				$("#suoshuC").show();
			}
			var optionId_input = $("#optionId_input").val()
			var modelId_input = $("#modelId_input").val()
			if(modelId_input!=null && modelId_input!='' && typeof modelId_input != undefined || evealType != ''){
				getChooseStandardList();
			}

				getChooseStandardListnew();


		});
		function getChooseStandardList() {
			var list = [];
			var content = "";
			$.ajax({
				url: "${ctx}/exam/examStandardBaseInfo/getStandardListBeta",
				dataType: "json",
				async: true,//请求是否异步，默认为异步
				data: {kpType: $("#evealType").val()},
				type: "POST",
				success: function (res) {
					if (res.result != null) {
						list = res.result;
						content = '';
						list.forEach((item, index) => {
							var modelId_input = $("#modelId_input").val();
							if(modelId_input!=null && modelId_input!='' && typeof modelId_input != undefined && modelId_input == item.id){
								content += '<option value=' + item.id + ' selected="selected">' + item.name + '</option>';
							}else{
								content += '<option value=' + item.id + '>' + item.name + '</option>';
							}
						});
						$("#modelId").empty();
						$("#modelId").append(content);
						var modelId_input = $("#modelId_input").val();
						if(modelId_input==null || modelId_input=='' || typeof modelId_input == undefined){
							$("#modelId").val('');
						}
						$("#modelId").siblings('.select2-container').find('.select2-chosen').text($("#modelId").find("option:selected").text());
						getChooseOptionsList();
					}
				}
			});
			var evealType = $("#evealType").val();
			if(evealType==null || evealType == '' || evealType=='1'||evealType == '2' || evealType == '5' || evealType == '6' || evealType == '7'){
				$("#suoshuC").hide();
			}else{
				$("#suoshuC").show();
			}
		};
		//根据使用模板的值渲染选择项所对应的内容
		function getChooseOptionsList() {

			var list = [];
			var content = "";
			$.ajax({
				<%--url: "${ctx}/exam/examCheck/findChooseOptionsByUseModel",--%>
				url: "${ctx}/exam/examCheck/findChooseOptionsByUseModel2",
				dataType: "json",
				async: true,//请求是否异步，默认为异步
				data: {useModel: $("#modelId").val()},
				type: "POST",
				success: function (res) {
					if (res.result != null) {
						list = res.result;
						content = '';
						list.forEach((item, index) => {
							var optionId_input = $("#optionId_input").val();
							if(optionId_input!=null && optionId_input!='' && typeof optionId_input != undefined && optionId_input == item.optionId){
								content += '<option value=' + item.optionId + ' selected="selected">' + item.itemValue + '</option>';
							}else{
								content += '<option value=' + item.optionId + '>' + item.itemValue + '</option>';
							}
						});
						$("#optionId").empty();
						$("#optionId").append(content);
						var optionId_input = $("#optionId_input").val();
						if(optionId_input==null || optionId_input=='' || typeof optionId_input == undefined){
						$("#optionId").val('');
						}
						$("#optionId").siblings('.select2-container').find('.select2-chosen').text($("#optionId").find("option:selected").text());

					}
				}
			});
		};

		//------------------------------------------------------------------------------------------------------------------------------------
		function getChooseStandardListnew() {
			var list = [];
			var content = "";
			$.ajax({
				url: "${ctx}/exam/examStandardBaseInfo/getStandardListBeta",
				dataType: "json",
				async: true,//请求是否异步，默认为异步
				data: {kpType: $("#evealType").val()},
				type: "POST",
				success: function (res) {
					if (res.result != null) {
						list = res.result;
						content = '';
						list.forEach((item, index) => {
							var new_modelId_input = $("#new_modelId_input").val();
						if(new_modelId_input!=null && new_modelId_input!='' && typeof new_modelId_input != undefined && new_modelId_input == item.id){
							content += '<option value=' + item.id + ' selected="selected">' + item.name + '</option>';
						}else{
							content += '<option value=' + item.id + '>' + item.name + '</option>';
						}
					});
						$("#newModelId").empty();
						$("#newModelId").append(content);
						var new_modelId_input = $("#new_modelId_input").val();
						if(new_modelId_input==null || new_modelId_input=='' || typeof new_modelId_input == undefined){
							$("#newModelId").val('');
						}
						$("#newModelId").siblings('.select2-container').find('.select2-chosen').text($("#newModelId").find("option:selected").text());
						getChooseOptionsListnew();
					}
				}
			});
			var evealType = $("#evealType").val();
			if(evealType==null || evealType == '' || evealType=='1'||evealType == '2' || evealType == '5' || evealType == '6' || evealType == '7'){
				$("#suoshuC").hide();
			}else{
				$("#suoshuC").show();
			}
		};
		//根据使用模板的值渲染选择项所对应的内容
		function getChooseOptionsListnew() {
			var list = [];
			var content = "";
			$.ajax({
				<%--url: "${ctx}/exam/examCheck/findChooseOptionsByUseModel",--%>
				url: "${ctx}/exam/examCheck/findChooseOptionsByUseModel2",
				dataType: "json",
				async: true,//请求是否异步，默认为异步
				data: {useModel: $("#newModelId").val()},
				type: "POST",
				success: function (res) {
					if (res.result != null) {
						list = res.result;
						content = '';
						list.forEach((item, index) => {
							var new_optionId_input = $("#new_optionId_input").val();
						if(new_optionId_input!=null && new_optionId_input!='' && typeof new_optionId_input != undefined && new_optionId_input == item.optionId){
							content += '<option value=' + item.optionId + ' selected="selected">' + item.itemValue + '</option>';
						}else{
							content += '<option value=' + item.optionId + '>' + item.itemValue + '</option>';
						}
					});
						$("#newOptionId").empty();
						$("#newOptionId").append(content);
						var new_optionId_input = $("#new_optionId_input").val();
						if(new_optionId_input==null || new_optionId_input=='' || typeof new_optionId_input == undefined){
							$("#newOptionId").val('');
						}
						$("#newOptionId").siblings('.select2-container').find('.select2-chosen').text($("#newOptionId").find("option:selected").text());

					}
				}
			});
		};
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li><a href="${ctx}/exam/examAutoStandardRelevance/">自动考评考评标准关联列表</a></li>
		<li class="active"><a href="${ctx}/exam/examAutoStandardRelevance/form?id=${examAutoStandardRelevance.id}">自动考评考评标准关联&lt;%&ndash;<shiro:hasPermission name="exam:examAutoStandardRelevance:edit">&ndash;%&gt;${not empty examAutoStandardRelevance.id?'修改':'添加'}&lt;%&ndash;</shiro:hasPermission><shiro:lacksPermission name="exam:examAutoStandardRelevance:edit">查看</shiro:lacksPermission>&ndash;%&gt;</a></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="examAutoStandardRelevance" action="${ctx}/exam/examAutoStandardRelevance/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input id="modelId_input" value = "${examAutoStandardRelevance.modelId}"  type="hidden"/>
		<input id="optionId_input" value = "${examAutoStandardRelevance.optionId}" type="hidden"/>
		<input id="new_modelId_input" value = "${examAutoStandardRelevance.newModelId}"  type="hidden"/>
		<input id="new_optionId_input" value = "${examAutoStandardRelevance.newOptionId}" type="hidden"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">考评事项：</label>
			<div class="controls">
				<form:input path="item" htmlEscape="false" class="input-xlarge  required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考评类别：</label>
			<div class="controls">
				<form:select path="evealType" class="input-xlarge required" onchange="getChooseStandardList();getChooseStandardListnew()">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('kp_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考评周期：</label>
			<div class="controls">
				<form:select path="period" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('cycle')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="suoshuC">
			<label class="control-label">所属公安处：</label>
			<div class="controls">
				<form:select path="chuId" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:option value="" label=""/>
					<form:option value="34" label="南宁处"/>
					<form:option value="95" label="柳州处"/>
					<form:option value="156" label="北海处"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">使用模板：</label>
			<div class="controls">
				<form:input path="model" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">使用模板：</label>
			<div class="controls">
				<form:select path="modelId" class="input-xlarge " onchange="getChooseOptionsList()">
					<form:option value="" label="请先选择考评类别"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">选择项：</label>
			<div class="controls">
				<form:input path="option" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">选择项：</label>
			<div class="controls">
				<form:select path="optionId" class="input-xlarge ">
					<form:option value="" label="请先选择模板"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<c:if test="${examAutoStandardRelevance.id != null and examAutoStandardRelevance.id != '' }">
		<div class="control-group">
			<label class="control-label">当前使用模板：</label>
			<div class="controls">
				<form:select path="newModelId" class="input-xlarge " onchange="getChooseOptionsListnew()">
					<form:option value="" label="请先选择考评类别"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">当前选择项：</label>
			<div class="controls">
				<form:select path="newOptionId" class="input-xlarge ">
					<form:option value="" label="请先选择模板"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		</c:if>

		<div class="control-group">
			<label class="control-label">初核人员：</label>
			<div class="controls">
				<sys:treeselect id="assessId" name="assessId" value="${examAutoStandardRelevance.assessId}" labelName="assess" labelValue="${examAutoStandardRelevance.assess}"
								title="初核人员" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">初核人员：</label>
			<div class="controls">
				<form:input path="assess" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="form-actions">
			<%--<shiro:hasPermission name="exam:examAutoStandardRelevance:edit">--%>
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<%--</shiro:hasPermission>--%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
	<script>
		if ("success" == "${saveResult}"){
			parent.$.jBox.tip("保存成功");
			parent.$.jBox.close();
		}
	</script>
</body>
</html>