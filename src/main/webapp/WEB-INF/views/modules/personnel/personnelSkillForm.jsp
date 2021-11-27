<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专长信息管理</title>
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
					$("#sex").val('');
					$('#sex').siblings('.select2-container').find('.select2-chosen').text('');
					$("#birthday").val('');
					$("#unitId").val('');
					$("#unitName").val('');
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
								$("#unitId").val(data.result[0].actualUnitId);
								$("#unitName").val(data.result[0].actualUnit);
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
			});
		});
		function save() {
			$("#inputForm").submit();
		}
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="personnelSkill" action="${ctx}/personnel/personnelSkill/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>

		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input id="name" path="personnelName" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
			</div>
		</div>
		<c:choose>
			<c:when test="${personnelSkill.idNumber == null}">
				<div class="control-group">
					<label class="control-label">身份证号：</label>
					<div class="controls">
						<form:input id="idNumber" path="idNumber" htmlEscape="false" class="input-xlarge required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelSkill.idNumber}"/>
			</c:otherwise>
		</c:choose>

		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:select id="sex" path="sex" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">出生年月：</label>
			<div class="controls">
				<input id="birthday" name="birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${personnelSkill.birthday}" pattern="yyyy-MM-dd HH:mm"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">特长：</label>
			<div class="controls">
				<form:input path="speciality" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
					<%--<sys:treeselect id="unit" name="unitId" value="${affairActivityMien.unitId}" labelName="affairCultureActivity" labelValue="${affairCultureActivity.unit}"
                                    title="举办单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
        --%>
				<sys:treeselect id="unit" name="unitId" value="${personnelSkill.unitId}" labelName="unit"
								labelValue="${personnelSkill.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true"
								notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">人才类别：</label>
			<div class="controls">
				<form:select path="category" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('talents_category')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">人才名称：</label>
			<div class="controls">
				<form:input path="talentsName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">享受待遇：</label>
			<div class="controls">
				<form:input path="talentsWelfare" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人才用久居留权地名称：</label>
			<div class="controls">
				<form:input path="resideAddress" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人才用久居留权地代码：</label>
			<div class="controls">
				<form:input path="resideAddressCode" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专业类别描述：</label>
			<div class="controls">
				<form:input path="specialtyCategoryDescribe" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">称号批准日期：</label>
			<div class="controls">
				<input name="titleRatifyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${personnelSkill.titleRatifyDate}" pattern="yyyy-MM-dd HH:mm"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">称号批准单位：</label>
			<div class="controls">
				<form:input path="titleRatifyUnit" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">称号批准单位级别：</label>
			<div class="controls">
				<form:select path="titleRatifyUnitGrade" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('unit_rank')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">称号批准单位隶属关系：</label>
			<div class="controls">
				<form:input path="titleRatifyUnitRelation" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>




		<%--<div class="control-group">
			<label class="control-label">专长类别：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personnel_zclb')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计算机使用程度：</label>
			<div class="controls">
				<form:select path="computerDegree" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personnel_jsjlevel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">等级程度：</label>
			<div class="controls">
				<form:select path="gradeDegree" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personnel_djcd')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专长认定单位名称：</label>
			<div class="controls">
				<form:input path="unitName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专长类别补充：</label>
			<div class="controls">
				<form:input path="supplement" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专长描述：</label>
			<div class="controls">
				<form:input path="describe" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>

		<div class="form-actions">
			<shiro:hasPermission name="personnel:personnelSkill:edit"><input id="btnSubmit" class="btn btn-primary" value="保 存" onclick="save()" />&nbsp;</shiro:hasPermission>
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