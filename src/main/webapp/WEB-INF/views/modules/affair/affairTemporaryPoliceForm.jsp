<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>临时抽调民警管理管理</title>
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
			//姓名输入框回车事件绑定
			$("#name").keydown(function(e){
				if(e.keyCode == 13){
					//清空
					$("#idNumber").val('');
					$("#sex").val('');
					$('#sex').siblings('.select2-container').find('.select2-chosen').text('');
					$("#policeNo").val('');
					$("#ofUnitId").val('');
					$("#ofUnitName").val('');
					$.ajax({
						type:"post",
						url:"${ctx}/personnel/personnelBase/getPersonByName",
						data:{name:$("#name").val()},
						dataType:"json",
						success:function(data){
							if(data.success==true && data.result.length==1){
								$("#idNumber").val(data.result[0].idNumber);
								$("#sex").val(data.result[0].sex);
								$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
								$("#policeNo").val(data.result[0].policeIdNumber);
								$("#ofUnitId").val(data.result[0].workunitId);
								$("#ofUnitName").val(data.result[0].workunitName);
							}else if(data.success==true && data.result.length>1){
								var html = '<table id="contentTable" class="table table-striped table-bordered table-condensed">';
								html += '<thead><tr><th></th><th>姓名</th><th>身份证号</th></tr></thead>';
								html += '<tbody>';
								for(var i=0; i< data.result.length; i++) {
									html += '<tr><td><input type="radio" name="selected" value="'+i+'"></td>';
									html += '<td>'+data.result[i].name+'</td>';
									html += '<td>'+data.result[i].idNumber+'</td>';
									html += '</tr>';
								}

								html +=	'</tbody>';
								html +=	'</table>';
								var submit = function (v, h, f) {
									$("#idNumber").val(data.result[f.selected].idNumber);
									$("#sex").val(data.result[f.selected].sex);
									$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
									$("#policeNo").val(data.result[f.selected].policeIdNumber);
									$("#ofUnitId").val(data.result[f.selected].workunitId);
									$("#ofUnitName").val(data.result[f.selected].workunitName);
									return true;
								};
								top.$.jBox(html, { title: "输入",width: 600, height: 300, submit: submit });
							}else {
								$.jBox.tip('没有查询到该人名相关信息');
							}
						}
					})
				}
			});
		});

		function save() {
			$("#inputForm").submit();
		}
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="affairTemporaryPolice" action="${ctx}/affair/affairTemporaryPolice/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input id="name" path="name" htmlEscape="false" class="input-xlarge required" onkeydown="if(event.keyCode==13){setDefaults();return false;}"/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">警号：</label>
			<div class="controls">
				<form:input id="policeNo" path="policeNo" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input id="idNumber" path="idNumber" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:select id="sex" path="sex" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属单位：</label>
			<div class="controls">
				<sys:treeselect id="ofUnit" name="ofUnitId" value="${affairTemporaryPolice.ofUnitId}" labelName="ofUnit" labelValue="${affairTemporaryPolice.ofUnit}"
					title="所属单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" isAll="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">抽调单位：</label>
			<div class="controls">
				<sys:treeselect id="cdUnit" name="cdUnitId" value="${affairTemporaryPolice.cdUnitId}" labelName="cdUnit" labelValue="${affairTemporaryPolice.cdUnit}"
					title="抽调单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" isAll="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">抽调时间：</label>
			<div class="controls">
				<input name="cdDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairTemporaryPolice.cdDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">截至时间：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairTemporaryPolice.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">抽调形式：</label>
			<div class="controls">
				<form:select path="samplingForm" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sampling_form')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">抽调事由：</label>
			<div class="controls">
				<form:textarea path="reason" htmlEscape="false" rows="4" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairTemporaryPolice:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="save()" />&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
	<script>
		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</body>
</html>