<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团员名册管理</title>
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
			//身份证号输入框回车事件绑定
			/*$("#idNumber").keydown(function(e){
				if(e.keyCode == 13){
					$.ajax({
						url:"${ctx}/affair/affairYouthTalent/findInfoByIdNumber",
						dataType:"json",
						async:true,//请求是否异步，默认为异步
						data:{"idNumber":$("#idNumber").val()},
						type:"POST",
						success:function(res){
							if (res.result != null){
								$("#name").val(res.result.name);
								$("#sex").val(res.result.sex);
								$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
								 $("#policeNo").val(res.result.policeIdNumber);
								$("#birthday").val(res.result.birthday);

							}

						}
					});
				}
			});*/
			//姓名输入框回车事件绑定
			$("#name").keydown(function(e){
				if(e.keyCode == 13){
					//清空
					$("#idNumber").val('');
					$("#sex").val('');
					$('#sex').siblings('.select2-container').find('.select2-chosen').text('');
					$("#policeNo").val('');
					$("#birthday").val('');
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
								$("#birthday").val(data.result[0].birthday);
							}else if(data.success==true && data.result.length>1){
								var html = '<table id="contentTable" class="table table-striped table-bordered table-condensed">';
								html += '<thead><tr><th></th><th>姓名</th><th>单位</th><th>身份证号</th><th>警号</th></tr></thead>';
								html += '<tbody>';
								for(var i=0; i< data.result.length; i++) {
									html += '<tr><td><input type="radio" name="selected" value="'+i+'"></td>';
									html += '<td>'+data.result[i].name+'</td>';
									html += '<td>'+data.result[i].workunitName+'</td>';
									html += '<td>'+data.result[i].idNumber+'</td>';
									html += '<td>'+data.result[i].policeIdNumber+'</td>';
									html += '</tr>';
								}

								html +=	'</tbody>';
								html +=	'</table>';
								var submit = function (v, h, f) {
									$("#idNumber").val(data.result[f.selected].idNumber);
									$("#sex").val(data.result[f.selected].sex);
									$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
									$("#policeNo").val(data.result[f.selected].policeIdNumber);
									$("#birthday").val(data.result[f.selected].birthday);
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
	<form:form id="inputForm" modelAttribute="affairTjRegister" action="${ctx}/affair/affairTjRegister/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<%--<input id="flag" name="flag" type="hidden" value="false"/>--%>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input id="name" path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
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
			<label class="control-label">警号：</label>
			<div class="controls">
				<form:input id="policeNo" path="policeNo" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所在团组织：</label>
			<div class="controls">
				<sys:treeselect id="partyBranch" name="partyBranchId" value="${affairTjRegister.partyBranchId}" labelName="partyBranch" labelValue="${affairTjRegister.partyBranch}"
					title="所在团组织" url="/affair/affairTwBase/treeData" cssClass="required" allowClear="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出生日期：</label>
			<div class="controls">
				<input id="birthday" name="birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="<fmt:formatDate value="${affairTjRegister.birthday}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">年龄：</label>
			<div class="controls">
				<form:input id="age" path="age" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
	<%--	<div class="control-group">
			<label class="control-label">注册时间：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairTjRegister.date}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">团内职务：</label>
			<div class="controls">
				<form:select path="job" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_tnjob')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
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
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" rows="4" class="input-xxlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" class="input-xlarge "/>
				<span class="help-inline"><font color="red">请按照1-1-1(公安局-单位序号-人员序号),2-1-1-1(公安处-公安处序号-单位序号-人员序号)，这种格式排序</font> </span>
			</div>
		</div>
		<%--<div align=center>
			<span class="help-inline"><font color="red">*</font> </span>
			<font color="red">该信息会同步到人员信息库,请慎重填写及修改</font>
		</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairTjRegister:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="save()" />&nbsp;</shiro:hasPermission>
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