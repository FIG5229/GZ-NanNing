<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团费收缴管理</title>
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
		//根据缴款人姓名自动查询相关信息
		function setDefaultsPayer() {
			//清空
			$("#payerNum").val('');
			$.ajax({
				type:"post",
				url:"${ctx}/personnel/personnelBase/getPersonByName",
				data:{name:$("#payer").val()},
				dataType:"json",
				success:function(data){
					if(data.success==true && data.result.length==1){
						$("#payerNum").val(data.result[0].idNumber);
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
							$("#payerNum").val(data.result[f.selected].idNumber);
							return true;
						};
						top.$.jBox(html, { title: "输入",width: 600, height: 300, submit: submit });
					}else {
						$.jBox.tip('没有查询到该人名相关信息');
					}
				}
			})
		};

		//根据收款人姓名自动查询相关信息
		function setDefaultsPayee() {
			//清空
			$("#payeeNum").val('');
			$.ajax({
				type:"post",
				url:"${ctx}/personnel/personnelBase/getPersonByName",
				data:{name:$("#payee").val()},
				dataType:"json",
				success:function(data){
					if(data.success==true && data.result.length==1){
						$("#payeeNum").val(data.result[0].idNumber);
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
							$("#payeeNum").val(data.result[f.selected].idNumber);
							return true;
						};
						top.$.jBox(html, { title: "输入",width: 600, height: 300, submit: submit });
					}else {
						$.jBox.tip('没有查询到该人名相关信息');
					}
				}
			})
		};
	</script>
</head>
<body>

<%--		<li><a href="${ctx}/affair/affairGroupManagement/">团费收缴列表</a></li>--%>
<%--		<li class="active"><a href="${ctx}/affair/affairGroupManagement/form?id=${affairGroupManagement.id}">团费收缴<shiro:hasPermission name="affair:affairGroupManagement:edit">${not empty affairGroupManagement.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairGroupManagement:edit">查看</shiro:lacksPermission></a></li>--%>
<br/>
	<form:form id="inputForm" modelAttribute="affairGroupManagement" action="${ctx}/affair/affairGroupManagement/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">缴费人：</label>
			<div class="controls">
				<%--<form:input id="payer" path="payer" htmlEscape="false" class="input-xlarge required" onkeydown="if(event.keyCode==13){setDefaultsPayer();return false;};"/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>--%>
					<form:input id="payer" path="payer" htmlEscape="false" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">缴款人身份证：</label>
			<div class="controls">
				<form:input id="payerNum" path="payerNum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">金额：</label>
			<div class="controls">
				<form:input path="amount" htmlEscape="false" class="input-xlarge  digits required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">缴费日期：</label>
			<div class="controls">
				<input name="paymentTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="<fmt:formatDate value="${affairGroupManagement.paymentTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收费人：</label>
			<div class="controls">
				<%--<form:input id="payee" path="payee" htmlEscape="false" class="input-xlarge required" onkeydown="if(event.keyCode==13){setDefaultsPayee();return false;};"/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>--%>
				<form:input id="payee" path="payee" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">收款人身份证：</label>
			<div class="controls">
				<form:input id="payeeNum" path="payeeNum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">缴费内容：</label>
			<div class="controls">
				<form:input path="paymentContent" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairGroupManagement.unitId}" labelName="unit"
								labelValue="${affairGroupManagement.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true"
								notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">团组织：</label>
			<div class="controls">
				<sys:treeselect id="group" name="groupId" value="${affairGroupManagement.groupId}" labelName="group1" labelValue="${affairGroupManagement.group1}"
					title="团组织" url="/affair/affairTwBase/treeData" cssClass="required" allowClear="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairGroupManagement:view"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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