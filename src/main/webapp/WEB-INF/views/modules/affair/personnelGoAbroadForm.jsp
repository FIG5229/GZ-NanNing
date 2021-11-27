<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>领导干部出国管理表管理</title>
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
							if(data.success==true && data.result.length==1){
								$("#idNumber").val(data.result[0].idNumber);
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
	<form:form id="inputForm" modelAttribute="personnelGoAbroad" action="${ctx}/affair/personnelGoAbroad/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
			</div>
		</div>
		<c:choose>
			<c:when test="${personnelGoAbroad.idNumber == null}">
				<div class="control-group">
					<label class="control-label">身份证号：</label>
					<div class="controls">
						<form:input path="idNumber" htmlEscape="false" class="input-xlarge required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelGoAbroad.idNumber}"/>
			</c:otherwise>
		</c:choose>

		<div class="control-group">
			<label class="control-label">出国境时单位及职务：</label>
			<div class="controls">
				<form:input path="unitJob" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出国境身份：</label>
			<div class="controls">
				<form:input path="identity" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出国境性质：</label>
			<div class="controls">
				<form:input path="character" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出国事由：</label>
			<div class="controls">
				<form:textarea path="reason" htmlEscape="false" rows="4" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">持有护照（通行证）上交日期：</label>
			<div class="controls">
				<input name="handinDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${personnelGoAbroad.handinDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">持有护照（通行证）领用日期：</label>
			<div class="controls">
				<input name="receiveDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${personnelGoAbroad.receiveDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出国（境）日期：</label>
			<div class="controls">
				<input name="goAbroadDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${personnelGoAbroad.goAbroadDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回国日期：</label>
			<div class="controls">
				<input name="returnDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${personnelGoAbroad.returnDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所至国别(地区)：</label>
			<div class="controls">
				<form:input path="toPlace" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出国（境）团组名称：</label>
			<div class="controls">
				<form:input path="groupName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出国（境）审批单位名称：</label>
			<div class="controls">
				<form:input path="approvalUnitName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出国（境）审批单位代码：</label>
			<div class="controls">
				<form:input path="approvalUnitCode" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出国（境）审批文号：</label>
			<div class="controls">
				<form:input path="approvalFileNo" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">派出单位：</label>
			<div class="controls">
				<sys:treeselect id="assignUnit" name="assignUnitCode" value="${personnelGoAbroad.assignUnitCode}" labelName="assignUnit" labelValue="${personnelGoAbroad.assignUnit}"
					title="派出单位" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">所至单位：</label>
			<div class="controls">
				<form:input path="assignUnit" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">派出单位代码：</label>
			<div class="controls">
				<form:input path="assignUnitCode" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所至单位：</label>
			<div class="controls">
				<form:input path="toUnit" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出国（境）经费来源：</label>
			<div class="controls">
				<form:input path="fundsSource" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">本次使用护照（通行证）编号：</label>
			<div class="controls">
				<form:input path="passportNo" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
                <form:hidden id="annex" path="annex" htmlEscape="false" maxlength="255" class="input-xlarge"/>
                    <sys:webuploader input="annex" type="files" uploadPath="/affair/personnelGoAbroad" selectMultiple="true"/>
            </div>
		</div>

		<div class="control-group">
			<label class="control-label">本次组织部门审查意见：</label>
			<div class="controls">
				<form:textarea path="suggestion" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出国（境）备注：</label>
			<div class="controls">
				<form:textarea path="beizhu" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:personnelGoAbroad:edit"><input id="btnSubmit" class="btn btn-primary" value="保 存" onclick="save()" />&nbsp;</shiro:hasPermission>
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