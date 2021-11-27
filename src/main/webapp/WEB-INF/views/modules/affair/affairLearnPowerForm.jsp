<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学习强国管理</title>
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
					$("#idNumber").val('');
					$.ajax({
						type:"post",
						url:"${ctx}/personnel/personnelBase/getPersonByName",
						data:{name:$("#name").val()},
						dataType:"json",
						success:function(data){
							if(data.success == true && data.result.length == 1){
								$("#idNumber").val(data.result[0].idNumber);
							}else if(data.success == true && data.result.length > 1){
								var html = '<table id="contentTable" class="table table-striped table-bordered table-condensed">';
								html += '<thead><tr><th></th><th>身份证号</th></tr></thead>';
								html += '<tbody>';
								for(var i=0; i< data.result.length; i++) {
									html += '<tr><td><input type="radio" name="selected" value="'+i+'"></td>';
									html += '<td>'+data.result[i].idNumber+'</td>';
									html += '</tr>';
								}

								html +=	'</tbody>';
								html +=	'</table>';
								var submit = function (v, h, f) {
									$("#idNumber").val(data.result[f.selected].idNumber);
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
	<br/>
	<form:form id="inputForm" modelAttribute="affairLearnPower" action="${ctx}/affair/affairLearnPower/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）--%>
		<c:choose>
			<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
				<div class="control-group">
					<label class="control-label">单位：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairLearnPower.unitId}" labelName="unit" labelValue="${affairLearnPower.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" isAll="true" dataMsgRequired="必填信息"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="control-group">
					<label class="control-label">单位：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairLearnPower.unitId}" labelName="unit" labelValue="${affairLearnPower.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		<%--<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairLearnPower.unitId}" labelName="unit" labelValue="${affairLearnPower.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false"/>
			</div>
		</div>--%>

		<%--<div class="control-group">
			<label class="control-label">时间：</label>
			<div class="controls">
				<input name="time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${affairLearnPower.time}" pattern="yyyy-MM"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
			</div>
		</div>--%>
		<%--<div class="control-group">
			<label class="control-label">时间：</label>
			<div class="controls">
				<form:input path="time" htmlEscape="false" class="input-xlarge "/>(时间格式请按照此格式填写：xxxx-xx)
			</div>
		</div>--%>

		<div class="control-group">
			<label class="control-label">时间：</label>
			<div class="controls">
				<input name="time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="${affairLearnPower.time}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge "/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input path="idNumber" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">本月学习积分：</label>
			<div class="controls">
				<form:input path="thisMonthIntegral" htmlEscape="false" class="input-xlarge required number" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="adjunct" path="adjunct" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="adjunct" type="files" uploadPath="affair/affairLearnPower" selectMultiple="true"/>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="affair:affairLearnPower:edit">
				<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="save()"/>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
		</div>
	</form:form>
	<script>
		if("sucess"=="${saveResult}"){
			parent.$.jBox.tip("保存成功");
			parent.$.jBox.close();
		}
	</script>
</body>
</html>