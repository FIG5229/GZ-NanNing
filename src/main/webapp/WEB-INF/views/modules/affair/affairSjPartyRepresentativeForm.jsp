<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>上级党代表管理</title>
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

		//根据姓名自动查询相关信息
		function setDefaults() {
			//清空
			$("#sex").val('');
			$("#birthday").val('');
			$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
			$("#nation").val('');
			$('#nation').siblings('.select2-container').find('.select2-chosen').text($("#nation").find("option:selected").text());
			$('#education').val('');
			$.ajax({
				type:"post",
				url:"${ctx}/personnel/personnelBase/getPersonByName",
				data:{name:$("#name").val()},
				dataType:"json",
				success:function(data){
					if(data.success==true && data.result.length==1){
						$("#sex").val(data.result[0].sex);
						$("#age").val(getAge(data.result[0].birthday));
						$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
						$("#nation").val(data.result[0].nation);
						$('#nation').siblings('.select2-container').find('.select2-chosen').text($("#nation").find("option:selected").text());
						$("#education").val(data.result[0].education);
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
							$("#age").val(getAge(data.result[0].birthday));
							$("#sex").val(data.result[f.selected].sex);
							$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
							$("#nation").val(data.result[f.selected].nation);
							$('#nation').siblings('.select2-container').find('.select2-chosen').text($("#nation").find("option:selected").text());
							$("#education").val(data.result[f.selected].education);
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
	<br/>
	<form:form id="inputForm" modelAttribute="affairSjPartyRepresentative" action="${ctx}/affair/affairSjPartyRepresentative/save" method="post" class="form-horizontal">
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
			<label class="control-label">党组织：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairSjPartyRepresentative.unitId}" labelName="unit" labelValue="${affairSjPartyRepresentative.unit}"
								title="党组织名称" url="/affair/affairGeneralSituation/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:select id="sex" path="sex" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">民族：</label>
			<div class="controls">
				<form:select id="nation" path="nation" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('nation')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年龄：</label>
			<div class="controls">
				<form:input id="age" path="age" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学历：</label>
			<div class="controls">
				<form:input id="education" path="education" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否在职：</label>
			<div class="controls">
				<form:select path="isWork" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">届次：</label>
			<div class="controls">
				<form:input path="session" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairSjPartyRepresentative:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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