<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>困难帮扶申报管理</title>
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
			// $("#idNumber").val('');
			$("#sex").val('');
			$("#birthday").val('');
			$("#joinPartyTime").val('');
			$("#unit").val('');
			$("#job").val('');
			$("#unitId").val('');
			$("#unitName").val('');
			$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
			$.ajax({
				type:"post",
				url:"${ctx}/personnel/personnelBase/getPersonByName",
				data:{name:$("#name").val()},
				dataType:"json",
				success:function(data){
					if(data.success==true && data.result.length==1){
						$("#birthday").val(data.result[0].birthday);
						$("#joinPartyTime").val(data.result[0].organizationDate);
						$("#job").val(data.result[0].jobFullname);
						$("#unit").val(data.result[0].workunitName);
						$("#sex").val(data.result[0].sex);
						$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
						$("#unitId").val(data.result[0].actualUnitId);
						$("#unitName").val(data.result[0].actualUnit);
					}else if(data.success==true && data.result.length>1){
						var html = '<table id="contentTable" class="table table-striped table-bordered table-condensed">';
						html += '<thead><tr><th></th><th>姓名</th><th>所在单位</th><th>职务</th><th>身份证号</th><th>参加组织日期</th></tr></thead>';
						html += '<tbody>';
						for(var i=0; i< data.result.length; i++) {
							html += '<tr><td><input type="radio" name="selected" value="'+i+'"></td>';
							html += '<td>'+data.result[i].name+'</td>';
							html += '<td>'+data.result[i].workunitName+'</td>';
							html += '<td>'+data.result[i].jobFullname+'</td>';
							html += '<td>'+data.result[i].idNumber+'</td>';　
							html += '<td>'+data.result[i].organizationDate+'</td>';
							html += '</tr>';
						}

						html +=	'</tbody>';
						html +=	'</table>';
						var submit = function (v, h, f) {
							// $("#idNumber").val(data.result[f.selected].idNumber);
							$("#birthday").val(data.result[f.selected].birthday);
							$("#joinPartyTime").val(data.result[f.selected].organizationDate);
							$("#job").val(data.result[f.selected].jobFullname);
							$("#unit").val(data.result[f.selected].workunitName);　
							$("#sex").val(data.result[f.selected].sex);
							$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
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
		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</head>
<body>
<%--	<ul class="nav nav-tabs">--%>
<%--		<li><a href="${ctx}/affair/affairDifficultHelp/">困难帮扶申报列表</a></li>--%>
<%--		<li class="active"><a href="${ctx}/affair/affairDifficultHelp/form?id=${affairDifficultHelp.id}">困难帮扶申报<shiro:hasPermission name="affair:affairDifficultHelp:edit">${not empty affairDifficultHelp.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairDifficultHelp:edit">查看</shiro:lacksPermission></a></li>--%>
<%--	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairDifficultHelp" action="${ctx}/affair/affairDifficultHelp/save" method="post" class="form-horizontal">
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
					   value="<fmt:formatDate value="${affairDifficultHelp.birthday}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">入党时间：</label>
			<div class="controls">
				<input id="joinPartyTime" name="joinPartyTime" type="text" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairDifficultHelp.joinPartyTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所在单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairDifficultHelp.unitId}" labelName="unit" labelValue="${affairDifficultHelp.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职务：</label>
			<div class="controls">
				<form:input id="job" path="job" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">慰问对象类别：</label>
			<div class="controls">
				<form:select path="wwType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('comfort_category')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">慰问原因：</label>
			<div class="controls">
				<form:textarea path="reason" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">慰问金额：</label>
			<div class="controls">
				<form:input path="money" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">慰问款源：</label>
			<div class="controls">
				<form:select path="moneySource" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('source_condolence_payment')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">慰问时间：</label>
			<div class="controls">
				<input name="time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairDifficultHelp.time}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="phoneNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairDifficultHelp:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>