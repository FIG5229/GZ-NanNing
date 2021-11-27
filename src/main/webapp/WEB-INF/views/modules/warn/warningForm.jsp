<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>预警信息管理</title>
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
			//默认禁用
			$('#voice').attr('disabled', 'true');
			$('#bubbleContent').attr('disabled', 'true');
			$('#bubbleDegree').attr('disabled', 'true');
			/*$('#alertContent').attr('disabled', 'true');
			$('#alertDegree').attr('disabled', 'true');*/
			//根据form表单回显的数据控制是否禁用
			if("1"=="${warning.isVoice}"){
				$('#voice').removeAttr("disabled");
			};
			if("1"=="${warning.isBubble}"){
				$('#bubbleContent').removeAttr("disabled");
				$('#bubbleDegree').removeAttr("disabled");
			};
			if("1"=="${warning.isAlert}"){
				$('#alertContent').removeAttr("disabled");
				$('#alertDegree').removeAttr("disabled");
			};
			//重复周期默认显示每月
			$("#repeatCycle").val(2);
			$('#repeatCycle').siblings('.select2-container').find('.select2-chosen').text($("#repeatCycle").find("option:selected").text());

			function clearData() {
				$('#month').val('');
				$('#month').siblings('.select2-container').find('.select2-chosen').text('');
				$('#day').val('');
				$('#day').siblings('.select2-container').find('.select2-chosen').text('');
				$('#week').val('');
				$('#week').siblings('.select2-container').find('.select2-chosen').text('');
				$('#hour').val('');
				$('#hour').siblings('.select2-container').find('.select2-chosen').text('');
				$('#minute').val('');
				$('#minute').siblings('.select2-container').find('.select2-chosen').text('');
				$('#date').val('');

			};
			//重复周期change事件
			$("#repeatCycle").change(function(){
				//清空之前的数据
				clearData();
				if($("#repeatCycle").val() == '0'){//每天
					$('#monthDiv').css('display', 'none');
					$('#dayDiv').css('display', 'none');
					$('#date').css('display', 'none');
					$('#weekDiv').css('display', 'none');
					$('#hourDiv').css('display', 'inline-block');
					$('#minuteDiv').css('display', 'inline-block');
				} else if($("#repeatCycle").val() == '1'){//每周
					$('#monthDiv').css('display', 'none');
					$('#dayDiv').css('display', 'none');
					$('#date').css('display', 'none');
					$('#weekDiv').css('display', 'inline-block');
					$('#hourDiv').css('display', 'inline-block');
					$('#minuteDiv').css('display', 'inline-block');
				}else if($("#repeatCycle").val() == '2'){//每月
					$('#monthDiv').css('display', 'none');
					$('#weekDiv').css('display', 'none');
					$('#date').css('display', 'none');
					$('#dayDiv').css('display', 'inline-block');
					$('#hourDiv').css('display', 'inline-block');
					$('#minuteDiv').css('display', 'inline-block');
				}
				else if($("#repeatCycle").val() == '3'){//每年
					$('#monthDiv').css('display', 'inline-block');
					$('#dayDiv').css('display', 'inline-block');
					$('#weekDiv').css('display', 'none');
					$('#date').css('display', 'none');
					$('#hourDiv').css('display', 'inline-block');
					$('#minuteDiv').css('display', 'inline-block');
				}else{//4：永不
					$('#monthDiv').css('display', 'none');
					$('#dayDiv').css('display', 'none');
					$('#weekDiv').css('display', 'none');
					$('#hourDiv').css('display', 'none');
					$('#minuteDiv').css('display', 'none');
					$('#date').css('display', 'inline-block');
				}
			});

			//form表单回显的数据控制开始时间的显示
			if("${warning.repeatCycle}" !=null && "${warning.repeatCycle}" != undefined &&"${warning.repeatCycle}" != ''){
				if("0" == "${warning.repeatCycle}"){
					//重复周期：每天
					$("#repeatCycle").val(0);
					$('#repeatCycle').siblings('.select2-container').find('.select2-chosen').text($("#repeatCycle").find("option:selected").text());
					$('#monthDiv').css('display', 'none');
					$('#dayDiv').css('display', 'none');
					$('#date').css('display', 'none');
					$('#weekDiv').css('display', 'none');
					$('#hourDiv').css('display', 'inline-block');
					$('#minuteDiv').css('display', 'inline-block');
				}else if("1" == "${warning.repeatCycle}"){
					//重复周期：每周
					$("#repeatCycle").val(1);
					$('#repeatCycle').siblings('.select2-container').find('.select2-chosen').text($("#repeatCycle").find("option:selected").text());
					$('#monthDiv').css('display', 'none');
					$('#dayDiv').css('display', 'none');
					$('#date').css('display', 'none');
					$('#weekDiv').css('display', 'inline-block');
					$('#hourDiv').css('display', 'inline-block');
					$('#minuteDiv').css('display', 'inline-block');
				}else if("2" == "${warning.repeatCycle}"){
					//重复周期：每月
					$("#repeatCycle").val(2);
					$('#repeatCycle').siblings('.select2-container').find('.select2-chosen').text($("#repeatCycle").find("option:selected").text());
					$('#monthDiv').css('display', 'none');
					$('#weekDiv').css('display', 'none');
					$('#date').css('display', 'none');
					$('#dayDiv').css('display', 'inline-block');
					$('#hourDiv').css('display', 'inline-block');
					$('#minuteDiv').css('display', 'inline-block');
				}else if("3" == "${warning.repeatCycle}"){
					//重复周期：每年
					$("#repeatCycle").val(3);
					$('#repeatCycle').siblings('.select2-container').find('.select2-chosen').text($("#repeatCycle").find("option:selected").text());
					$('#monthDiv').css('display', 'inline-block');
					$('#dayDiv').css('display', 'inline-block');
					$('#weekDiv').css('display', 'none');
					$('#date').css('display', 'none');
					$('#hourDiv').css('display', 'inline-block');
					$('#minuteDiv').css('display', 'inline-block');
				}else {
					//重复周期：永不
					$("#repeatCycle").val(4);
					$('#repeatCycle').siblings('.select2-container').find('.select2-chosen').text($("#repeatCycle").find("option:selected").text());
					$('#monthDiv').css('display', 'none');
					$('#dayDiv').css('display', 'none');
					$('#weekDiv').css('display', 'none');
					$('#hourDiv').css('display', 'none');
					$('#minuteDiv').css('display', 'none');
					$('#date').css('display', 'inline-block');
				}
			}

		});
		function voiceChange(obj) {
			if (obj.checked) {
				$('#voice').removeAttr("disabled");
			}else{
				$('#voice').attr('disabled', 'true');
			}
		};
		function bubbleChange(obj) {
			if (obj.checked) {
				$('#bubbleContent').removeAttr("disabled");
				$('#bubbleDegree').removeAttr("disabled");
			}else{
				$('#bubbleContent').attr('disabled', 'true');
				$('#bubbleDegree').attr('disabled', 'true');
			}
		};
		//切换弹窗内容，弹窗紧急程度是否可用
		function alertChange(obj) {
			if (obj.checked) {
				$('#alertContent').removeAttr("disabled");
				$('#alertDegree').removeAttr("disabled");
			}else{
				$('#alertContent').attr('disabled', 'true');
				$('#alertDegree').attr('disabled', 'true');
			}

		};
		var continueDayTemp = '';
		function isNum() {
			var reg = /^[0-9]*$/;
			var continueDay = $('#continueDay').val();
			if(reg.test(continueDay)){
				continueDayTemp = continueDay;
			}else {
				showTipDialog();

			}
		}

		function showTipDialog() {
			top.$.jBox.error('请输入数字', '系统提示', {
				buttonsFocus: 1, closed: function () {
					$('#continueDay').val(continueDayTemp)
				}
			});
			//top.$('.jbox-body .jbox-icon').css('top','55px');
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/warn/warning/">预警信息列表</a></li>
		<li class="active"><a href="${ctx}/warn/warning/form?id=${warning.id}">预警信息<shiro:hasPermission name="warn:warning:edit">${not empty warning.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="warn:warning:edit">查看</shiro:lacksPermission></a></li>
		<li><a href="${ctx}/warn/warnHistory/">预警历史记录</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="warning" action="${ctx}/warn/warning/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">预警名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
            <label class="control-label">接收人员：</label>
			<div class="controls">
                <sys:treeselect id="receivePerName" name="receivePerId" value="${warning.receivePerId}"
                                labelName="receivePerName" labelValue="${warning.receivePerName}"
                                title="部门" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true"
                                notAllowSelectParent="true" checked="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">重复周期：</label>
			<div class="controls">
				<form:select id="repeatCycle" path="repeatCycle" class="input-xlarge required">
					<form:options items="${fns:getDictList('warn_repeat_cycle')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<div id="monthDiv" style="display: none">
					<form:select id="month" path="month" class="input-xlarge " cssStyle="width:100px;">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('warn_month')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>月
				</div>
				<div id="dayDiv" style="display: inline-block;">
					<form:select id="day" path="day" class="input-xlarge " cssStyle="width:100px;">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('warn_day')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>日
				</div>
				<div id="weekDiv" style="display: none;">
					<form:select id="week" path="week" class="input-xlarge " cssStyle="width:100px;">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('warn_week')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</div>
				<div id="hourDiv" style="display: inline-block;">
					<form:select id="hour" path="hour" class="input-xlarge " cssStyle="width:100px;">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('warn_hour')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>时
				</div>
				<div id="minuteDiv" style="display: inline-block;">
					<form:select id="minute" path="minute" class="input-xlarge " cssStyle="width:100px;">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('warn_minute')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>分钟
				</div>
				<input id="date" name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="display: none"
					   value="<fmt:formatDate value="${warning.date}" pattern="yyyy-MM-dd HH:mm"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>

		</div>
		<div class="control-group">
			<label class="control-label">重复提醒：</label>
			<div class="controls">
				<form:select path="remind" class="input-xlarge ">
					<form:options items="${fns:getDictList('warn_remind')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*注意：该字段为当天每几分钟提醒一次</font> </span>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">持续时间：</label>
            <div class="controls">
				<form:input path="continueDay" htmlEscape="false" class="input-xlarge digits required"  placeholder="若收到后停止请填写:0"/>
				<%--<form:input path="continueDay" htmlEscape="false" class="input-xlarge required" onkeyup="isNum(this)" placeholder="若收到后停止请填写:0"/>--%>
				<span class="help-inline">天<font color="red">*</font> </span>
			</div>
        </div>
	<%--	<div class="control-group">
			<label class="control-label">是否有声音：</label>
			<div class="controls">
				<form:checkboxes id="isVoice" onChange="voiceChange(this)" path="isVoice" items="${fns:getDictList('warn_yes')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">铃声：</label>
			<div class="controls">
				<form:select id="voice" path="voice" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('warn_voice')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否有气泡：</label>
			<div class="controls">
				<form:checkboxes id="isBubble" onChange="bubbleChange(this)" path="isBubble" items="${fns:getDictList('warn_yes')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">气泡内容：</label>
			<div class="controls">
				<form:textarea path="bubbleContent" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">气泡紧急程度：</label>
			<div class="controls">
				<form:select id="bubbleDegree" path="bubbleDegree" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('warn_degree')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">是否有弹窗：</label>
			<div class="controls">
				<form:checkboxes id="isAlert" path="isAlert" items="${fns:getDictList('warn_yes')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">弹窗内容：</label>
			<div class="controls">
				<%--<form:input id="alertContent" path="alertContent" htmlEscape="false" class="input-xlarge "/>--%>
				<form:textarea path="alertContent" htmlEscape="false" rows="4" class="input-xxlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">弹窗紧急程度：</label>
			<div class="controls">
				<form:select id="alertDegree" path="alertDegree" class="input-xlarge">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('warn_degree')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="warn:warning:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>