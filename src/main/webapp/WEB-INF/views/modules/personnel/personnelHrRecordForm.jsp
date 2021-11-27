<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人事信息管理</title>
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

			$("#name").keydown(function(e){
				if(e.keyCode == 13){
					//清空
					$("#idNumber").val('');
					$("#department").val('');
					$("#duty").val('');
					$.ajax({
						type:"post",
						url:"${ctx}/personnel/personnelBase/getPersonByName",
						data:{name:$("#name").val()},
						dataType:"json",
						success:function(data){
							if(data.success==true && data.result.length==1){
								$("#idNumber").val(data.result[0].idNumber);
								$("#department").val(data.result[0].workunitName);
								$("#duty").val(data.result[0].jobAbbreviation);

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
									$("#department").val(data.result[f.selected].workunitName);
									$("#duty").val(data.result[f.selected].jobAbbreviation);
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
	<form:form id="inputForm" modelAttribute="personnelHrRecord" action="${ctx}/personnel/personnelHrRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
			</div>
		</div>
		<c:choose>
			<c:when test="${personnelHrRecord.idNumber == null}">
				<div class="control-group">
					<label class="control-label">身份证号：</label>
					<div class="controls">
						<form:input path="idNumber" htmlEscape="false" class="input-xlarge required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelHrRecord.idNumber}"/>
			</c:otherwise>
		</c:choose>
		<div class="control-group">
			<label class="control-label">部门：</label>
			<div class="controls">
				<form:input id="department" path="department" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职务：</label>
			<div class="controls">
				<form:input id="duty" path="duty" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">级别：</label>
			<div class="controls">
				<form:input id="level" path="level" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>



		<div class="control-group">
			<label class="control-label">档案转入日期：</label>
			<div class="controls">
				<input name="intoDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${personnelHrRecord.intoDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">档案来处：</label>
			<div class="controls">
				<form:input path="source" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">档案管理单位：</label>
			<div class="controls">
				<form:select path="unit" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personnel_daunit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">档案编号：</label>
			<div class="controls">
				<form:input path="recordNo" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">档案版本类别：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personnel_banben')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">档案转出日期：</label>
			<div class="controls">
				<input name="outDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${personnelHrRecord.outDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">档案去处：</label>
			<div class="controls">
				<form:input path="toPlace" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">正本卷数：</label>
			<div class="controls">
				<form:input path="zNum" htmlEscape="false" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">副本卷数：</label>
			<div class="controls">
				<form:input path="fNum2" htmlEscape="false" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">档案位置：</label>
			<div class="controls">
				<form:select path="location" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personnel_daunit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">档案条形码号：</label>
			<div class="controls">
				<form:input path="barCodeNo" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">档案备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="personnel:personnelHrRecord:edit"><input id="btnSubmit" class="btn btn-primary" value="保 存" onclick="save()" />&nbsp;</shiro:hasPermission>
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