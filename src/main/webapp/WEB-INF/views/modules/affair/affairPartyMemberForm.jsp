<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党员花名册管理</title>
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

			$("#type").change(function () {
				switchType();
			});
			function switchType() {
				if ($("#type").val() == '1'){
					$("#type1").css('display', 'inline-block');
					$("#type2").css('display', 'none');
					$("#type2Value").val("");
				}else if ($("#type").val() == '2'){
					$("#type1").css('display',  'none');
					$("#type1Value").val("");
					$("#type2").css('display', 'inline-block');
				}
			}
			switchType();
		});
		if("sucess"=="${saveResult}"){
			parent.$.jBox.close();
		}

		function setDefaults() {
			//清空
			$('#cardNum').val('');
			$("#policeNo").val('');
			$("#sex").val('');
			$('#sex').siblings('.select2-container').find('.select2-chosen').text('');
			$("#nation").val('');
			$('#nation').siblings('.select2-container').find('.select2-chosen').text('');
			$('#birthplace').val('');
			$("#birth").val('');
			$("#workDate").val('');
			$('#joinDate').val('');
			$.ajax({
				type:"post",
				url:"${ctx}/personnel/personnelBase/getPersonByName",
				data:{name:$("#name").val()},
				dataType:"json",
				success:function(data){
					if(data.success==true && data.result.length==1){
						$("#cardNum").val(data.result[0].idNumber);
						$("#policeNo").val(data.result[0].policeIdNumber);
						$("#sex").val(data.result[0].sex);
						$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
						$("#nation").val(data.result[0].nation);
						$('#nation').siblings('.select2-container').find('.select2-chosen').text($("#nation").find("option:selected").text());
						$('#birthplace').val(data.result[0].nativePlace);
						$("#birth").val(data.result[0].birthday);
						$("#workDate").val(data.result[0].workDate);
						$("#joinDate").val(data.result[0].organizationDate);
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
							$('#cardNum').val(data.result[f.selected].idNumber);
							$("#policeNo").val(data.result[f.selected].policeIdNumber);
							$("#sex").val(data.result[f.selected].sex);
							$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
							$("#nation").val(data.result[f.selected].nation);
							$('#nation').siblings('.select2-container').find('.select2-chosen').text($("#nation").find("option:selected").text());
							$('#birthplace').val(data.result[f.selected].nativePlace);
							$("#birth").val(data.result[f.selected].birthday);
							$("#workDate").val(data.result[f.selected].workDate);
							$("#joinDate").val(data.result[f.selected].organizationDate);
							return true;
						};
						top.$.jBox(html, { title: "输入",width: 600, height: 300, submit: submit });
					}else {
						$.jBox.tip('没有查询到该人名相关信息');
					}
				}
			})
		}
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="affairPartyMember" action="${ctx}/affair/affairPartyMember/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"  onkeydown="if(event.keyCode==13){setDefaults();return false;}"/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input path="cardNum" htmlEscape="false" class="input-xlarge required"/>
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
            <label class="control-label">民族：</label>
            <div class="controls">
                <form:select id="nation" path="nation" class="input-xlarge required">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('nation')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">籍贯：</label>
            <div class="controls">
                <form:input id="birthplace" path="birthplace" htmlEscape="false" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">是否台湾省籍：</label>
            <div class="controls">
                <form:select path="isTaiwan" class="input-xlarge required">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">出生日期：</label>
            <div class="controls">
                <input id="birth" name="birth" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
                       value="<fmt:formatDate value="${affairPartyMember.birth}" pattern="yyyy-MM-dd"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">学历：</label>
            <div class="controls">
				<form:select path="education" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_party_member_xueli')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">人员类别一：</label>
			<div class="controls">
				<form:select path="personnelType" class="input-xlarge required" id="type">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_personnel_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="type1" style="display:none;">
			<label class="control-label">人员类别二：</label>
			<div class="controls">
				<form:select path="personnelCategory" class="input-xlarge " id="type1Value">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_personnel_category')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group" id="type2" style="display:none;">
			<label class="control-label">人员类别二：</label>
			<div class="controls">
				<form:select path="personnelCategory2" class="input-xlarge " id="type2Value">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_personnel_category2')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">所在党支部：</label>
            <div class="controls">
				<sys:treeselect id="partyBranchId" name="partyBranchId" value="${affairPartyMember.partyBranchId}" labelName="partyBranch" labelValue="${affairPartyMember.partyBranch}"
						title="所在党支部" url="/affair/affairGeneralSituation/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">加入党组织日期：</label>
			<div class="controls">
				<input id="joinDate" name="joinDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairPartyMember.joinDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转为正式党员日期：</label>
			<div class="controls">
				<input name="turnDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairPartyMember.turnDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作岗位：</label>
			<div class="controls">
				<form:input path="workPlace" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参加工作时间：</label>
			<div class="controls">
				<input id="workDate" name="workDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairPartyMember.workDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="phoneNum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">固定电话：</label>
			<div class="controls">
				<form:input path="telephone" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">家庭住址：</label>
			<div class="controls">
				<form:textarea path="homeAddress" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">婚姻状况：</label>
			<div class="controls">
				<form:select path="maritalStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_marital_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">减少原因：</label>
			<div class="controls">
				<form:select path="deleteReason" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('delete_reason')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" class="input-xlarge "/>
				<span class="help-inline"><font color="red">请按照1-1-1(公安局-单位序号-人员序号),2-1-1-1(公安处-公安处序号-单位序号-人员序号)，这种格式排序</font> </span>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="affair:affairPartyMember:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>