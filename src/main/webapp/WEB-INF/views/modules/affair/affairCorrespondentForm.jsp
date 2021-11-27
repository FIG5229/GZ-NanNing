<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通讯员管理管理</title>
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
		function setUserInfo() {
			//清空
			$("#idNumber").val('');
			$.ajax({
				type:"post",
				url:"${ctx}/personnel/personnelBase/getPersonByName",
				data:{name:$("#name").val()},
				dataType:"json",
				success:function(data){
					////debugger;er;
					if (data.success==true ){


						if( data.result.length==1){
							//获取到身份证号后 查询家庭关系
							// setFamilyInfo();
							$("#idNumber").val(data.result[0].idNumber);
							$("#policeNo").val(data.result[0].policeIdNumber);
							$("#nation").val(data.result[0].nation);
							// $('#sex').siblings('.select2-container').find('.select2-chosen').text($("#nation").find("option:selected").text());
							$("#birthday").val(data.result[0].birthday);
							$("#sex").val(data.result[0].sex);
							$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
						}else if( data.result.length>1){
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
								//获取到身份证号后 查询家庭关系
								// setFamilyInfo();
								$("#idNumber").val(data.result[0].idNumber);
								$("#policeNo").val(data.result[0].policeIdNumber);
								$("#nation").val(data.result[0].nation);
								// $('#sex').siblings('.select2-container').find('.select2-chosen').text($("#nation").find("option:selected").text());
								$("#birthday").val(data.result[0].birthday);
								$("#sex").val(data.result[0].sex);
								$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
								return true;
							};

							top.$.jBox(html, { title: "输入",width: 600, height: 300, submit: submit });
						}else {
							$.jBox.tip('没有查询到该人名相关信息');
						}
					}
				}
			});
			// var idNumber = $("#idNumber").val();
			// if (idNumber != null && idNumber != ''){
			// setFamilyInfo();
			// }
		}
	</script>
</head>
<body>
	 <br/>
	<form:form id="inputForm" modelAttribute="affairCorrespondent" action="${ctx}/affair/affairCorrespondent/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>

		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"
							onkeydown="if(event.keyCode==13){setUserInfo();return false;}"/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
					<%--家庭信息查看时显示--%>
				<form:input path="idNumber" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
		<c:choose>
			<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
				<div class="control-group">
				<label class="control-label">单位：</label>
				<div class="controls">
					<sys:treeselect id="unit" name="unitId" value="${affairCorrespondent.unitId}" labelName="unit" labelValue="${affairCorrespondent.unit}"
									title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" isAll="true"/>
				</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="control-group">
					<label class="control-label">单位：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairCorrespondent.unitId}" labelName="unit" labelValue="${affairCorrespondent.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false"/>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		<%--<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairCorrespondent.unitId}" labelName="unit" labelValue="${affairCorrespondent.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false"/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">职务：</label>
			<div class="controls">
				<form:input path="duty" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系方式：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">培训情况：</label>
            <div class="controls">
                <form:textarea path="remarks" htmlEscape="false" rows="4" class="input-xxlarge "/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">通讯员等级评定：</label>
			<div class="controls">
				<form:select path="star" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('correspondent_star')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairCorrespondent:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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