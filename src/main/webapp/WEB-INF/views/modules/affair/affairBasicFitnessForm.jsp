<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>基本体能成绩管理</title>
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
			$("#idNumber").val('');
			$("#policeNo").val('');
			$("#unitId").val('');
			$("#unitName").val('');
			$("#sex").val('');
			$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
			$.ajax({
				type:"post",
				url:"${ctx}/personnel/personnelBase/getPersonByName",
				data:{name:$("#name").val()},
				dataType:"json",
				success:function(data){
					if(data.success==true && data.result.length==1){
						$("#idNumber").val(data.result[0].idNumber);
						$("#policeNo").val(data.result[0].policeIdNumber);
						$("#unitId").val(data.result[0].actualUnitId);
						$("#unitName").val(data.result[0].actualUnit);
						$("#sex").val(data.result[0].sex);
						$("#age").val(getAge(data.result[0].birthday));
						$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
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
							$("#policeNo").val(data.result[f.selected].policeIdNumber);
							$("#unitId").val(data.result[f.selected].actualUnitId);
							$("#unitName").val(data.result[f.selected].actualUnit);
							$("#sex").val(data.result[f.selected].sex);
							$("#age").val(getAge(data.result[0].birthday));
							$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
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
	<form:form id="inputForm" modelAttribute="affairBasicFitness" action="${ctx}/affair/affairBasicFitness/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">时间：</label>
			<div class="controls">
				<form:input path="yearMonth" type="text"  maxlength="20" class="input-medium Wdate "
							value="${yearMonth}"
							onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width: 120px;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input id="name" path="name" htmlEscape="false" class="input-xlarge required" onkeydown="if(event.keyCode==13){setDefaults();return false;}"/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件号：</label>
			<div class="controls">
				<form:input id="idNumber" path="idNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年龄：</label>
			<div class="controls">
				<form:input id="age" path="age" htmlEscape="false" class="input-xlarge "/>
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
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairBasicFitness.unitId}" labelName="unit" labelValue="${affairBasicFitness.unit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身高（米）：</label>
			<div class="controls">
				<form:input id="height" path="height" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">体重（公斤）：</label>
			<div class="controls">
				<form:input id="weight" path="weight" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身体质量指数（BMI）数值：</label>
			<div class="controls">
				<form:input id="weight" path="value" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身体质量指数（BMI）评定：</label>
			<div class="controls">
				<form:select path="assess" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('fitness_assess')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">50米跑成绩（秒）：</label>
			<div class="controls">
				<form:input path="fiftyRunScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">50米跑达标情况：</label>
			<div class="controls">
				<form:select path="fiftyRunStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">立定跳远成绩（米）：</label>
			<div class="controls">
				<form:input path="jumpScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">立定跳远达标情况：</label>
			<div class="controls">
				<form:select path="jumpStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">2000米跑成绩（分+秒）：</label>
			<div class="controls">
				<form:input path="twokmRunScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">2000米跑达标情况：</label>
			<div class="controls">
				<form:select path="twokmRunStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">仰卧起坐成绩（个）：</label>
			<div class="controls">
				<form:input path="nvSitUpsScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">仰卧起坐达标情况：</label>
			<div class="controls">
				<form:select path="nvSitUpsStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">引体向上成绩（个）：</label>
			<div class="controls">
				<form:input path="nanPullUpsScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">引体向上达标情况：</label>
			<div class="controls">
				<form:select path="nanPullUpsStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">俯卧撑成绩（个）：</label>
			<div class="controls">
				<form:input path="nanPushUpsScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">俯卧撑达标情况：</label>
			<div class="controls">
				<form:select path="nanPushIpsStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">握力成绩（kg）：</label>
			<div class="controls">
				<form:input path="gripScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">握力达标情况：</label>
			<div class="controls">
				<form:select path="gripStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">坐位体前屈成绩（cm）：</label>
			<div class="controls">
				<form:input path="sittingForwardBendingScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">坐位体前屈达标情况：</label>
			<div class="controls">
				<form:select path="sittingForwardBendingStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">综合评定：</label>
			<div class="controls">
<%--				<form:input path="comprehensiveAssessment" htmlEscape="false" class="input-xlarge "/>--%>
				<form:select path="comprehensiveAssessment" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pass_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairBasicFitness:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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