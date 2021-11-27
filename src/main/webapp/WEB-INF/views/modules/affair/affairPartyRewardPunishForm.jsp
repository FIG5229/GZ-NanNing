<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党员奖惩信息管理</title>
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

			$("#awardCategory").change(function () {
				console.log($("#awardCategory").val());
				switchType();
			});
			function switchType() {
				if ($("#awardCategory").val() == '1'){
					$("#s2id_type").css('display', 'inline-block');
					$("#s2id_cjType").css('display', 'none');
					$("#cjType").val("");
				}else if ($("#awardCategory").val() == '2'){
					$("#s2id_type").css('display', 'none');
					$("#type").val("");
					$("#s2id_cjType").css('display', 'inline-block');
				}
			}
			switchType();
		});
		if("sucess"=="${saveResult}"){
			parent.$.jBox.close();
		}

		//根据姓名自动查询相关信息
		function setDefaults() {
			//清空
			$("#idNumber").val('');
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
						$("#sex").val(data.result[0].sex);
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
							$("#sex").val(data.result[f.selected].sex);
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


	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairPartyRewardPunish/">党员奖惩信息列表</a></li>
		<li class="active"><a href="${ctx}/affair/affairPartyRewardPunish/form?id=${affairPartyRewardPunish.id}">党员奖惩信息<shiro:hasPermission name="affair:affairPartyRewardPunish:edit">${not empty affairPartyRewardPunish.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairPartyRewardPunish:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairPartyRewardPunish" action="${ctx}/affair/affairPartyRewardPunish/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge " onkeydown="if(event.keyCode==13){setDefaults();return false;}"/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:select path="sex" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input path="idNumber" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">警号：</label>
			<div class="controls">
				<form:input path="policeNo" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">奖惩文号：</label>
			<div class="controls">
				<form:input path="fileNo" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖惩名称：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">党组织：</label>
			<div class="controls">
				<sys:treeselect id="partyOrganization" name="partyOrganizationId" value="${affairPartyRewardPunish.partyOrganizationId}" labelName="partyOrganization" labelValue="${affairPartyRewardPunish.partyOrganization}"
					title="党组织" url="/affair/affairGeneralSituation/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准党委：</label>
			<div class="controls">
				<%--
				<sys:treeselect id="approvalOrg" name="approvalOrgId" value="${affairPartyRewardPunish.approvalOrgId}" labelName="approvalOrg" labelValue="${affairPartyRewardPunish.approvalOrg}"
					title="批准党委" url="/affair/affairGeneralSituation/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息" isAll="true"/>
				--%>
					<form:select path="approvalOrgId" class="input-xlarge required">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('affair_paryt_committee')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖惩类别：</label>
			<div class="controls">
				<form:select id="awardCategory" path="awardCategory" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('award_category')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>


				<form:select id="type" path="type" class="input-xlarge required" cssStyle="display: none">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_org_reward_punish')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<form:select id="cjType" path="cjType" class="input-xlarge required" cssStyle="display: none">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('cj_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准时间：</label>
			<div class="controls">
				<input name="approvalTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairPartyRewardPunish.approvalTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖惩说明：</label>
			<div class="controls">
				<form:textarea path="illustration" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairPartyRewardPunish:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>