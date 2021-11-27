<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>警情预警-自动考评管理</title>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/tw/tPoliceMessage/">警情预警-自动考评列表</a></li>
		<li class="active"><a href="${ctx}/tw/tPoliceMessage/form?id=${tPoliceMessage.id}">警情预警-自动考评<shiro:hasPermission name="tw:tPoliceMessage:edit">${not empty tPoliceMessage.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="tw:tPoliceMessage:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tPoliceMessage" action="${ctx}/tw/tPoliceMessage/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input path="idCard" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
				<form:input path="iphoneNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">微信号：</label>
			<div class="controls">
				<form:input path="wechatId" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">报警地点(1 :列车上'| 2' : '火车站')：</label>
			<div class="controls">
				<form:input path="policeAddress" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">报警人ip：</label>
			<div class="controls">
				<form:input path="piliceIp" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">车次：</label>
			<div class="controls">
				<form:input path="trainNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">车厢：</label>
			<div class="controls">
				<form:input path="coach" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">车站：</label>
			<div class="controls">
				<form:input path="stationName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">车站位置：</label>
			<div class="controls">
				<form:input path="stationSite" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">报警信息：</label>
			<div class="controls">
				<form:input path="policeMessage" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<input name="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tPoliceMessage.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">组织机构：</label>
			<div class="controls">
				<form:input path="organizationId" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">报警方式（1 :电话报警；2 :语音报警；3：视频报警；4：文字报警）：</label>
			<div class="controls">
				<form:input path="policeWay" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">报警时间：</label>
			<div class="controls">
				<input name="policeDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tPoliceMessage.policeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">座位号：</label>
			<div class="controls">
				<form:input path="seatNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">警情状态：</label>
			<div class="controls">
				<form:input path="caseStatus" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处理时间：</label>
			<div class="controls">
				<input name="disposeTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tPoliceMessage.disposeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">受理时间：</label>
			<div class="controls">
				<input name="processTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tPoliceMessage.processTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回执消息：</label>
			<div class="controls">
				<form:input path="receiptMessage" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回执民警：</label>
			<div class="controls">
				<form:input path="receiptPeople" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回执时间：</label>
			<div class="controls">
				<input name="receiptTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tPoliceMessage.receiptTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">市级是否指派（0，1   表示）：</label>
			<div class="controls">
				<form:input path="status1" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处级是否指派（0，1   表示）：</label>
			<div class="controls">
				<form:input path="status2" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">代表派出所和列车民警的状态（0，1   表示）：</label>
			<div class="controls">
				<form:input path="status3" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回执状态：</label>
			<div class="controls">
				<form:input path="receiptStatus" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">受理状态：</label>
			<div class="controls">
				<form:input path="feedbackStatus" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属区域，用于统计：</label>
			<div class="controls">
				<form:input path="district" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">无效原因：</label>
			<div class="controls">
				<form:input path="caseMessage" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回执民警id：</label>
			<div class="controls">
				<form:input path="receiptPeopleId" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">添加无效信息的时间：</label>
			<div class="controls">
				<input name="caseTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tPoliceMessage.caseTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">受理单位：</label>
			<div class="controls">
				<form:input path="processOrganization" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处理结果(新增加的字段)：</label>
			<div class="controls">
				<form:input path="processMessage" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">警情类别：</label>
			<div class="controls">
				<form:input path="alertName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经度：</label>
			<div class="controls">
				<form:input path="longitude" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">纬度：</label>
			<div class="controls">
				<form:input path="latitude" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">报警类别（0表示是一件报警内容，1表示是一键救助）：</label>
			<div class="controls">
				<form:input path="policeType" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">督促单位：</label>
			<div class="controls">
				<form:input path="superviseOrganization" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">督促时间：</label>
			<div class="controls">
				<input name="superviseTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tPoliceMessage.superviseTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">督促状态（1表是受理超时，2表示回执超时）：</label>
			<div class="controls">
				<form:input path="superviseStatus" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">督促人（获取的是当前登录人姓名）：</label>
			<div class="controls">
				<form:input path="superviseName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">超时状态（1表示超时数据）：</label>
			<div class="controls">
				<form:input path="overtimeStatus" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">线路名称：</label>
			<div class="controls">
				<form:input path="lineName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">线路区段：</label>
			<div class="controls">
				<form:input path="linePosition" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">受理人(获取当前登录人姓名)：</label>
			<div class="controls">
				<form:input path="processPeople" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">process_type：</label>
			<div class="controls">
				<form:input path="processType" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">process_case：</label>
			<div class="controls">
				<form:input path="processCase" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">receipt_type：</label>
			<div class="controls">
				<form:input path="receiptType" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发站：</label>
			<div class="controls">
				<form:input path="fromStation" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">到站：</label>
			<div class="controls">
				<form:input path="toStation" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">区域名称：</label>
			<div class="controls">
				<form:input path="districtName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内部单位区域名称：</label>
			<div class="controls">
				<form:input path="interiorAreaName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="tw:tPoliceMessage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>