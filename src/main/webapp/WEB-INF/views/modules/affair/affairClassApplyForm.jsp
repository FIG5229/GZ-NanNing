<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>班级报名管理</title>
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
		//获取人员信息，并为相应字段赋值
		function setPersonIfo() {
			var personId = $("#stuNameId").val();
			$.ajax({
				type:"post",
				url:"${ctx}/personnel/personnelBase/getPersonByIdNumber",
				data:{idNumber:personId},
				dataType:"json",
				success:function(data){
					if(data.success==true){
						//console.log(data);
						$("#stuSex").val(data.result.sex);
						$('#stuSex').siblings('.select2-container').find('.select2-chosen').text($("#stuSex").find("option:selected").text());
						$("#stuNation").val(data.result.nation);
						$('#stuNation').siblings('.select2-container').find('.select2-chosen').text($("#stuNation").find("option:selected").text());
						$("#stuIdNum").val(data.result.idNumber);
						$("#stuSysPhoneNum").val(data.result.phoneNumber);
						$("#stuUnitId").val(data.result.workunitId);
						$("#stuUnitName").val(data.result.workunitName);
					}else {
						$.jBox.tip('没有查询到该人员相关信息');
					}
				}
			})
		}
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairClassApply/form?id=${affairClassApply.id}">班级报名<shiro:hasPermission name="affair:affairClassApply:edit">${not empty affairClassApply.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairClassApply:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%><br/>
	<div class="control-group">
		<label class="control-label">培训时间：
			<fmt:formatDate value="${affairClassManage.beganDate}" pattern="yyyy-MM-dd"/>
			~<fmt:formatDate value="${affairClassManage.resultDate}" pattern="yyyy-MM-dd"/></label>
		&nbsp;&nbsp;
		<label class="control-label">培训班类型：
			${fns:getDictLabel(affairClassManage.type, 'affair_train_type', '')}</label>

	</div>
	<form:form id="inputForm" modelAttribute="affairClassApply" action="${ctx}/affair/affairClassApply/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group" hidden>
			<label class="control-label">培训班id：</label>
			<div class="controls">
				<form:input path="classId" htmlEscape="false" class="input-xlarge required" value="${classId}"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培训班名称：</label>
			<div class="controls">
				<form:input path="className" htmlEscape="false" class="input-xlarge " value="${affairClassManage.name}" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">选择人员：</label>
			<div class="controls">
			<sys:treeselect id="stuName" name="stuNameId" value="${affairClassApply.stuNameId}" labelName="stuName" labelValue="${affairClassApply.stuName}"
							title="个人信息" url="/sys/office/treeData?type=3" cssClass="required"
							allowClear="true" notAllowSelectParent="true" dataMsgRequired="必填信息" onchange="setPersonIfo()"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:select id="stuSex" path="stuSex" class="input-xlarge " placeholder="请先完成人员的选择" >
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<%--<form:input path="stuSex" id="stuSex" htmlEscape="false" class="input-xlarge required"/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">民族：</label>
			<div class="controls">
				<form:select id="stuNation" path="stuNation" class="input-xlarge " placeholder="请先完成人员的选择" >
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('nation')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<%--<form:input path="stuNation" id="stuNation" htmlEscape="false" class="input-xlarge required"/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件号：</label>
			<div class="controls">
				<form:input path="stuIdNum" id="stuIdNum" htmlEscape="false" class="input-xlarge" placeholder="请先完成人员的选择" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话(系统预留)：</label>
			<div class="controls">
				<form:input path="stuSysPhoneNum" id="stuSysPhoneNum" htmlEscape="false" class="input-xlarge " placeholder="请先完成人员的选择" readonly="true"/>
			</div>
		</div>
		<div class="control-group" hidden>
			<label class="control-label">单位id：</label>
			<div class="controls">
				<form:input path="stuUnitId" id="stuUnitId" htmlEscape="false" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位名称：</label>
			<div class="controls">
				<form:input path="stuUnitName" id="stuUnitName" htmlEscape="false" class="input-xlarge" placeholder="请先完成人员的选择" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号码：</label>
			<div class="controls">
				<form:input path="stuPhoneNum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">到达时间：</label>
			<div class="controls">
				<input name="stuArrivalTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairClassApply.stuArrivalTime}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">到达车次/航班：</label>
			<div class="controls">
				<form:input path="stuArrivalNum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">到达车站/机场：</label>
			<div class="controls">
				<form:input path="stuArrivalSite" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">返程时间：</label>
			<div class="controls">
				<input name="stuBackTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairClassApply.stuBackTime}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">返程车次/航班：</label>
			<div class="controls">
				<form:input path="stuBackNum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">返程车站/机场：</label>
			<div class="controls">
				<form:input path="stuBackSite" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否预定返程火车票：</label>
			<div class="controls">
				<form:radiobuttons id="stuIsreserve" path="stuIsreserve" items="${fns:getDictList('yes_no')}" itemLabel="label"
								 itemValue="value" htmlEscape="false"/>
					<%--<form:input path="stuIsreserve" htmlEscape="false" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公免号：</label>
			<div class="controls">
				<form:input path="stuPubExmpNum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairClassApply:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="确 认 报 名"/>&nbsp;</shiro:hasPermission>
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