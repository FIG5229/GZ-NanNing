<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>奖惩信息库管理</title>
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

			$("#jcObject").change(function () {
				jcObject();
			});
			function jcObject() {
				if($("#jcObject").val() == '0'){
					$('#jcObjectPersonnel').css('display', 'inline-block');
					$('#idNumber').css('display', 'inline-block');
				}else if($("#ischeck").val() == '1'){
					$('#jcObjectPersonnel').css('display', 'none');
					$('#idNumber').css('display', 'none');
				}
			}
			//调用
			jcObject();

			//根据使用模板的值渲染选择项所对应的内容
			function getChangeTypeList(){
				var list = [];
				var content = "";
				$.ajax({
					url:"${ctx}/exam/examJcInfo/findChangeTypeByUseModel",
					dataType:"json",
					async:true,//请求是否异步，默认为异步
                    data: {useModel: $("#myUseModel").val()},
					type:"POST",
					success:function(res){
						if (res.result != null){
							list = res.result;
							content = '';
							list.forEach((item, index) => {
								content += '<option value=' + item + '>' + item + '</option>';
							})
							$("#changeType").append(content);
							$("#changeType ").val('');
							$('#changeType').siblings('.select2-container').find('.select2-chosen').text($("#changeType").find("option:selected").text());
						}
					}
				});
			};
			//根据使用模板的值渲染选择项所对应的内容   回显
			function getChangeTypeListView(changeTypeValue){
				var list = [];
				var content = "";
				$("#examWorkflowDefineList").html("");
				$.ajax({
					url:"${ctx}/exam/examJcInfo/findChangeTypeByUseModel",
					dataType:"json",
					async:true,//请求是否异步，默认为异步
                    data: {useModel: $("#myUseModel").val()},
					type:"POST",
					success:function(res){
						if (res.result != null){
							list = res.result;
							content = '';
							list.forEach((item, index) => {
								if(changeTypeValue == item){
									$("#changeType ").val(changeTypeValue);
									content += '<option value=' + item + ' selected >' + item + '</option>';
								}else{
									content += '<option value=' + item + '>' + item + '</option>';
								}
							})
							$("#changeType").append(content);
							$('#changeType').siblings('.select2-container').find('.select2-chosen').text($("#changeType").find("option:selected").text());
						}
					}
				});
			};
			//使用模板改变时触发事件，重新渲染选择项所对应的内容
			$("#myUseModel").change(function(){
				getChangeTypeList();
			});
			if(${not empty examJcInfo.id}){
				getChangeTypeListView('${examJcInfo.changeType}');
			}
		});

	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="examJcInfo" action="${ctx}/exam/examJcInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">奖惩类型：</label>
			<div class="controls">
				<form:select path="jcType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('jc_types')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖惩名称：</label>
			<div class="controls">
				<form:input path="jcTypeName" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖惩文号：</label>
			<div class="controls">
				<form:input path="fileNum" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">时间：</label>
			<div class="controls">
				<input name="jcDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="<fmt:formatDate value="${examJcInfo.jcDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用模板：</label>
			<div class="controls">
				<form:select  id="myUseModel" path="myUseModel" class="input-xlarge required" cssStyle="width: 400px;">
					<form:option value="" label=""/>
					<form:options items="${templateFile}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">选择项：</label>
			<div class="controls">
				<select id="changeType" style="width: 400px;" name="changeType">
					<option value=""></option>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加减分情况：</label>
			<div class="controls">
				<form:input path="jcCode" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖惩对象：</label>
			<div class="controls">
				<form:select  id="jcObject" path="jcObject" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('jc_objects')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<form:input  path="jcObjectPersonnel" htmlEscape="true" class="input-xlarge "  cssStyle="display: none; width: 100px;" />
				<form:input  path="idNumber" htmlEscape="false" class="input-xlarge "  cssStyle="display: none; width: 200px"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="jcObjectUnit" name="jcObjectUnitId" value="${examJcInfo.jcObjectUnitId}" labelName="jcObjectUnit" labelValue="${examJcInfo.jcObjectUnit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">绩效考评标准：</label>
			<div class="controls">
				<form:textarea path="examStandart" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filePath" type="files" uploadPath="exam/examJcInfo" selectMultiple="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="exam:examJcInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" onclick="save()" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
		</div>
	</form:form>
	<script>
		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</body>
</html>