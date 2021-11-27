<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团员教育评议管理</title>
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
			//身份证号输入框回车事件绑定
			/*$("#idNumber").keydown(function(e){
				if(e.keyCode == 13){
					$.ajax({
						url:"${ctx}/affair/affairYouthTalent/findInfoByIdNumber",
						dataType:"json",
						async:true,//请求是否异步，默认为异步
						data:{"idNumber":$("#idNumber").val()},
						type:"POST",
						success:function(res){
							if (res.result != null){
								$("#name").val(res.result.name);
								$("#sex").val(res.result.sex);
								$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
								$("#birthday").val(res.result.birthday);
								$("#policeNo").val(res.result.policeIdNumber);
								$("#nativePlace").val(res.result.nativePlace);
								//$("#job").val();
								$("#flag").val(false);
							}else{
								$("#flag").val(true);
							}

						}
					});
				}
			});*/
			//姓名输入框回车事件绑定
			$("#name").keydown(function(e){
				if(e.keyCode == 13){
					//清空
					$("#idNumber").val('');
					$("#sex").val('');
					$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
					$("#birthday").val('');
					$("#policeNo").val('');
					$("#nativePlace").val('');
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
								$("#policeNo").val(data.result[0].policeIdNumber);
								$("#nativePlace").val(data.result[0].nativePlace);
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
									$("#birthday").val(data.result[f.selected].birthday);
									$("#policeNo").val(data.result[f.selected].policeIdNumber);
									$("#nativePlace").val(data.result[f.selected].nativePlace);
									return true;
								};
								top.$.jBox(html, { title: "输入",width: 600, height: 300, submit: submit });
							}else {
								$.jBox.tip('没有查询到该人名相关信息');
							}
						}
					});
				}
			});
		});
		function save() {
			$("#inputForm").submit();
		}
	</script>
</head>
<body>

<%--		<li><a href="${ctx}/affair/affairEducationComment/">团员教育评议列表</a></li>--%>
<%--		<li class="active"><a href="${ctx}/affair/affairEducationComment/form?id=${affairEducationComment.id}">团员教育评议<shiro:hasPermission name="affair:affairEducationComment:edit">${not empty affairEducationComment.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairEducationComment:edit">查看</shiro:lacksPermission></a></li>--%>
<br/>
	<form:form id="inputForm" modelAttribute="affairEducationComment" action="${ctx}/affair/affairEducationComment/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<input id="flag" name="flag" type="hidden" value="false"/>
		<div class="control-group" >
			<h4>个人信息</h4>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input id="idNumber" path="idNumber" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">警号：</label>
			<div class="controls">
				<form:input path="policeNo" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
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
			<label class="control-label">出生年月：</label>
			<div class="controls">
				<input id="birthday" name="birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairEducationComment.birthday}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">籍贯：</label>
			<div class="controls">
				<form:input id="nativePlace" path="nativePlace" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属团组织：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairEducationComment.unitId}" labelName="unit" labelValue="${affairEducationComment.unit}"
								title="所属团组织" url="/affair/affairTwBase/treeData" cssClass="required" allowClear="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">入团时间：</label>
			<div class="controls">
				<input name="rtDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairEducationComment.rtDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职务：</label>
			<div class="controls">
				<form:input id="job" path="job" htmlEscape="false" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group" >
			<h4>教育评议</h4>
		</div>
		<div class="control-group">
			<label class="control-label">评议时间：</label>
			<div class="controls">
				<input name="pyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairEducationComment.pyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">自评等级：</label>
			<div class="controls">
				<form:select path="level" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_comment_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">个人自评内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">团支部意见：</label>
			<div class="controls">
				<form:select path="opinion" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_comment_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filePath" type="files" uploadPath="affair/affairEducationComment" selectMultiple="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairEducationComment:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="save()"/>&nbsp;</shiro:hasPermission>
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