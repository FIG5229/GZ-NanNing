<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>谈话函询管理</title>
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
			//姓名输入框回车事件绑定
			$("#name").keydown(function(e){
				if(e.keyCode == 13){
					//清空
					$("#personalNum").val('');
					$("#siren").val('');
					$("#unitId").val('');
					$("#unitName").val('');
					$("#job").val('');
					$("#mianmao").val('');
					$('#mianmao').siblings('.select2-container').find('.select2-chosen').text('');
					$.ajax({
						type:"post",
						url:"${ctx}/personnel/personnelBase/getPersonByName",
						data:{name:$("#name").val()},
						dataType:"json",
						success:function(data){
							if(data.success==true && data.result.length==1){
								$("#personalNum").val(data.result[0].idNumber);
								$("#siren").val(data.result[0].policeIdNumber);
								$("#unitId").val(data.result[0].actualUnitId);
								$("#unitName").val(data.result[0].actualUnit);
								$("#job").val(data.result[0].jobAbbreviation);
								$("#mianmao").val(data.result[0].politicsFace);
								$('#mianmao').siblings('.select2-container').find('.select2-chosen').text($("#mianmao").find("option:selected").text());
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
									$("#personalNum").val(data.result[f.selected].idNumber);
									$("#siren").val(data.result[f.selected].policeIdNumber);
									$("#unitId").val(data.result[f.selected].actualUnitId);
									$("#unitName").val(data.result[f.selected].actualUnit);
									$("#job").val(data.result[f.selected].jobAbbreviation);
									$("#mianmao").val(data.result[f.selected].politicsFace);
									$('#mianmao').siblings('.select2-container').find('.select2-chosen').text($("#mianmao").find("option:selected").text());
									return true;
								};
								top.$.jBox(html, { title: "输入",width: 600, height: 300, submit: submit });
							}else {
								$.jBox.tip('没有查询到该人名相关信息');
							}
						}
					})
				}
			});
		});
		function save() {
			$("#inputForm").submit();
		}
	</script>
</head>
<body>

<%--		<li><a href="${ctx}/affair/affairTalkManagement/">谈话函询列表</a></li>--%>
		<%--<li class="active"><a href="${ctx}/affair/affairTalkManagement/form?id=${affairTalkManagement.id}">谈话函询<shiro:hasPermission name="affair:affairTalkManagement:edit">${not empty affairTalkManagement.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairTalkManagement:edit">查看</shiro:lacksPermission></a></li>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairTalkManagement" action="${ctx}/affair/affairTalkManagement/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">个人基本信息：</label>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input id="name" path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号码：</label>
			<div class="controls">
				<form:input id="personalNum" path="personalNum" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">警号：</label>
			<div class="controls">
				<form:input id="siren" path="siren" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairTalkManagement.unitId}" labelName="unit" labelValue="${affairTalkManagement.unit}"
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
			<label class="control-label">职级：</label>
			<div class="controls">
				<form:input path="jobLevel" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">政治面貌：</label>
			<div class="controls">
				<form:select id="mianmao" path="mianmao" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('political_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">谈话函询相关信息：</label>
		</div>

		<div class="control-group">
			<label class="control-label">主要问题：</label>
			<div class="controls">
				<form:textarea id="problem" htmlEscape="false" path="problem" rows="10" maxlength="500" class="input-xlarge" cssStyle="width: 450px;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主办部门：</label>
			<div class="controls">
				<form:select path="zbUnit" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_zb_unit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:select path="letterCategory" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_tanhua')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">谈话人：</label>
			<div class="controls">
				<form:textarea id="talker" htmlEscape="false" path="talker" rows="8" maxlength="200" class="input-xlarge" cssStyle="width: 450px;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">谈话地点：</label>
			<div class="controls">
				<form:input path="talkPlace" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">时间：</label>
			<div class="controls">
				<input name="time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="<fmt:formatDate value="${affairTalkManagement.time}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="annex" path="annex" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="annex" type="files" uploadPath="affair/affairTalkManagement" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group" style="display: none">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="qxUnit" name="qxUnitId" value="${affairTalkManagement.qxUnitId}" labelName="qxUnit" labelValue="${affairTalkManagement.qxUnit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairTalkManagement:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="save()"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
		</div>
	</form:form>
	<script>
		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</body>
</html>