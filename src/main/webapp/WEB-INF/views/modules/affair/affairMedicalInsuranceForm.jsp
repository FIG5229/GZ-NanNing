<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>医疗保险管理</title>
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
		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
		function save() {
			$("#inputForm").submit();
		}

		function setDefaults() {
			//清空
			$('#idNumber').val('');
			$("#sex").val('');
			$('#sex').siblings('.select2-container').find('.select2-chosen').text('');
			$("#birthday").val('');
			$("#age").val('');
			$("#unitId").val('');
			$("#unitName").val('');
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
						$("#birthday").val(data.result[0].birthday);
						var bir = $("#birthday").val();
						var startDate = new Date(bir);
						var date = new Date();
						var newDate = date.getTime() - startDate.getTime();
						var age = Math.ceil(newDate / 1000 / 60 / 60 / 24 /365);
						if (isNaN(age)){
							age = "";
						}
						$("#age").val(age);
						$("#unitId").val(data.result[0].actualUnitId);
						$("#unitName").val(data.result[0].actualUnit);
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
							$('#idNumber').val(data.result[f.selected].idNumber);
							$("#sex").val(data.result[f.selected].sex);
							$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
							$("#birthday").val(data.result[f.selected].birthday);
							var bir = $("#birthday").val();
							var startDate = new Date(bir);
							var date = new Date();
							var newDate = date.getTime() - startDate.getTime();
							var age = Math.ceil(newDate / 1000 / 60 / 60 / 24 /365);
							if (isNaN(age)){
								age = "";
							}
							$("#age").val(age);
							$("#unitId").val(data.result[f.selected].actualUnitId);
							$("#unitName").val(data.result[f.selected].actualUnit);
							return true;
						};
						top.$.jBox(html, { title: "输入",width: 600, height: 300, submit: submit });
					}else {
						$.jBox.tip('没有查询到该人名相关信息');
					}
				}
			})
		}
		function getAge(strBirthday) { //传入形式yyyy-MM-dd
			//strBirthday = util.formatTime(strBirthday);转换成yyyy-MM-dd形式
			var returnAge;
			var strBirthdayArr = strBirthday.split('-');
			var birthYear = strBirthdayArr[0];
			var birthMonth = strBirthdayArr[1];
			var birthDay = strBirthdayArr[2];
			var d = new Date();
			var nowYear = d.getFullYear();
			var nowMonth = d.getMonth() + 1;
			var nowDay = d.getDate();
			if (nowYear == birthYear) {
				returnAge = 0 //同年 则为0岁
			} else {
				var ageDiff = nowYear - birthYear; //年之差
				if (ageDiff > 0) {
					if (nowMonth == birthMonth) {
						var dayDiff = nowDay - birthDay; //日之差
						if (dayDiff < 0) {
							returnAge = ageDiff - 1
						} else {
							returnAge = ageDiff
						}
					} else {
						var monthDiff = nowMonth - birthMonth; //月之差
						if (monthDiff < 0) {
							returnAge = ageDiff - 1
						} else {
							returnAge = ageDiff
						}
					}
				} else {
					returnAge = -1 //返回-1 表示出生日期输入错误 晚于今天
				}
			}
			return returnAge //返回周岁年龄
		}
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="affairMedicalInsurance" action="${ctx}/affair/affairMedicalInsurance/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<br>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge " onkeydown="if(event.keyCode==13){setDefaults();return false;}"/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairMedicalInsurance.unitId}" labelName="unit" labelValue="${affairMedicalInsurance.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">时间：</label>
			<div class="controls">
				<input name="timeYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value= "${affairMedicalInsurance.timeYear}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:select path="sex" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出生年月：</label>
			<div class="controls">
				<input id="birthday" name="birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${affairMedicalInsurance.birthday}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年龄：</label>
			<div class="controls">
				<form:input path="age" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input path="idNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">缴费基数：</label>
			<div class="controls">
				<form:input path="cardinalNumber" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">个人缴费比例：</label>
			<div class="controls">
				<form:input path="individualProportion" htmlEscape="false" class="input-xlarge "/>%
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
<%--		<div class="control-group">--%>
<%--			<label class="control-label">月个人缴费：</label>--%>
<%--			<div class="controls">--%>
<%--				<form:input path="individualPayment" htmlEscape="false" class="input-xlarge  number"/>--%>
<%--				<span class="help-inline">%(请直接输入整数数值)</span>--%>
<%--			</div>--%>
<%--		</div>--%>
		<div class="control-group">
			<label class="control-label">单位缴费比例：</label>
			<div class="controls">
				<form:input path="unitProportion" htmlEscape="false" class="input-xlarge required"/>%
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
<%--		<div class="control-group">--%>
<%--			<label class="control-label">月单位缴费：</label>--%>
<%--			<div class="controls">--%>
<%--				<form:input path="unitPayment" htmlEscape="false" class="input-xlarge  number"/>--%>
<%--				<span class="help-inline">%(请直接输入整数数值)</span>--%>
<%--			</div>--%>
<%--		</div>--%>
<%--		<div class="control-group">--%>
<%--			<label class="control-label">月个账划入：</label>--%>
<%--			<div class="controls">--%>
<%--				<form:input path="monthAccountDelimit" htmlEscape="false" class="input-xlarge  number"/>--%>
<%--				<span class="help-inline">%(请直接输入整数数值)</span>--%>
<%--			</div>--%>
<%--		</div>--%>
<%--		<div class="control-group">--%>
<%--			<label class="control-label">补充资金月个账划入：</label>--%>
<%--			<div class="controls">--%>
<%--				<form:input path="additionFunds" htmlEscape="false" class="input-xlarge  number"/>--%>
<%--				<span class="help-inline">%(请直接输入整数数值)</span>--%>
<%--			</div>--%>
<%--		</div>--%>
		<div class="control-group">
			<label class="control-label">月个账划入比例：</label>
			<div class="controls">
				<form:input path="monthAccount" htmlEscape="false" class="input-xlarge  number"/>%
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">补充资金月个账划入比例：</label>
			<div class="controls">
				<form:input path="addition" htmlEscape="false" class="input-xlarge  number"/>%
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年度补助比例：</label>
			<div class="controls">
				<form:input path="annualProportion" htmlEscape="false" class="input-xlarge  number"/>%
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">全区公务员平均月工资：</label>
			<div class="controls">
				<form:input path="averageSalary" htmlEscape="false" class="input-xlarge  required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
<%--		<div class="control-group">--%>
<%--			<label class="control-label">年度补助比例：</label>--%>
<%--			<div class="controls">--%>
<%--				<form:input path="annualProportion" htmlEscape="false" class="input-xlarge  number"/>--%>
<%--				<span class="help-inline">%(请直接输入整数数值)</span>--%>
<%--			</div>--%>
<%--		</div>--%>
<%--		<div class="control-group">--%>
<%--			<label class="control-label">年度个账划入：</label>--%>
<%--			<div class="controls">--%>
<%--				<form:input path="annualAccountDelimit" htmlEscape="false" class="input-xlarge  number"/>--%>
<%--				<span class="help-inline">%(请直接输入整数数值)</span>--%>
<%--			</div>--%>
<%--		</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairMedicalInsurance:edit">
				<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="save()"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
		</div>
	</form:form>
</body>
</html>