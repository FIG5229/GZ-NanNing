<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民警家庭管理</title>
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

			$('input[type=radio][name=hasMarried]').change(function () {
				if (this.value =="1") {
					//隐藏其他的radio
				$('#hasBrother').attr('class',"control-group hide");
				$('#hasChild').attr('class',"control-group hide");
				} else {
					//显示其他的radio
					$("input[name='hasBrother'][value='1']").attr("checked",true);
					$('#hasBrother').attr('class',"control-group");
				}
			});

			$('input[type=radio][name=hasBrother]').change(function () {
				if (this.value =="1") {
					//隐藏其他的radio
					$('#hasChild').attr('class',"control-group hide");
				} else {
					//显示其他的radio
					$("input[name='hasChild'][value='1']").attr("checked",true);
					$('#hasChild').attr('class',"control-group");
				}
			});
		});

		function complete() {
			$("#inputForm").attr("action","${ctx}/personnel/personnelPoliceFamilyTwo/save?isComplete=true");
			$("#inputForm").submit();
		}


		var sex = ${userMessage.sex}
		console.log(sex)

	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li><a href="${ctx}/personnel/personnelPoliceFamily/">民警家庭列表</a></li>
		<li class="active"><a href="${ctx}/personnel/personnelPoliceFamily/form?id=${personnelPoliceFamily.id}">民警家庭<shiro:hasPermission name="personnel:personnelPoliceFamily:edit">${not empty personnelPoliceFamily.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="personnel:personnelPoliceFamily:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
	<br/>


	<form:form id="inputForm" modelAttribute="personnelPoliceFamilyTwo" action="${ctx}/personnel/personnelPoliceFamilyTwo/save?add=${add}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="step" name="step" value="${personnelPoliceFamilyTwo.step}"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input path="idNumber" value="${userMessage.idNumber}" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">
				<c:choose>
					<c:when test="${personnelPoliceFamilyTwo.step == '0'}">
						父亲
					</c:when>
					<c:when test="${personnelPoliceFamilyTwo.step eq '1'}">
						母亲
					</c:when>
					<c:when test="${personnelPoliceFamilyTwo.step eq '2'}">
						配偶
					</c:when>
					<c:when test="${personnelPoliceFamilyTwo.step eq '3'}">
						配偶父亲
					</c:when>
					<c:when test="${personnelPoliceFamilyTwo.step eq '4'}">
						配偶母亲
					</c:when>
					<c:when test="${personnelPoliceFamilyTwo.step eq '5'}">
						兄弟姐妹
					</c:when>
					<c:when test="${personnelPoliceFamilyTwo.step eq '6'}">
						子女
					</c:when>
					<c:when test="${personnelPoliceFamilyTwo.step eq '7'}">
						子女配偶
					</c:when>
					<c:otherwise></c:otherwise>
				</c:choose>
				姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">称谓：</label>
			<div class="controls">
				<form:select path="relationship" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('family_appellation')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:select path="sex" class="input-xlarge ">
					<form:option value="" label="${sex}"/>
					<form:options id="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出生日期：</label>
			<div class="controls">
				<input name="birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${personnelPoliceFamilyTwo.birthday}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">政治面貌：</label>
			<div class="controls">
				<form:select path="politicsFace" class="input-xlarge ">
					<form:option value="" label="${politicsFace}"/>
					<form:options items="${fns:getDictList('political_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现状：</label>
			<div class="controls">
				<form:input path="status" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作单位名称及职务：</label>
			<div class="controls">
				<form:input path="unitNameJob" htmlEscape="false" value="单位：${userMessage.workunitName}职务：${userMessage.jobAbbreviation}" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作单位所在政区：</label>
			<div class="controls">
				<form:input path="unitArea" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">国籍：</label>
			<div class="controls">
				<form:input path="nationality" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">民族：</label>
			<div class="controls">
				<form:select path="nation" class="input-xlarge ">
					<form:option value="" label="${nation}"/>
					<form:options items="${fns:getDictList('nation')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学历：</label>
			<div class="controls">
				<form:input path="education" htmlEscape="false" value="${userMessage.education}" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份：</label>
			<div class="controls">
				<form:input path="identity" htmlEscape="false" value="${userMessage.identity}" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份或职位：</label>
			<div class="controls">
				<form:input path="identityJob" htmlEscape="false" value="${userMessage.expertise}" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职务层次：</label>
			<div class="controls">
				<form:input path="jobLevel" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系方式：</label>
			<div class="controls">
				<form:input path="contactMethod" htmlEscape="false" value="${userMessage.phoneNumber}" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">住址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<c:choose>
			<c:when test="${personnelPoliceFamilyTwo.step eq '1'}">
				<div class="control-group" id="hasMarried">
					<label class="control-label">是否已婚:</label>
					<div class="controls">
						<%--非零则真 true--%>
							<input name="hasMarried" value="1" type="radio" class="input-xlarge " checked="checked" />是
							<input name="hasMarried" value="0" type="radio" class="input-xlarge " />否
					</div>
				</div>
				<div class="control-group hide" id="hasBrother">
					<label class="control-label">是否有兄弟姐妹:</label>
					<div class="controls">
						<input name="hasBrother" value="1" type="radio" class="input-xlarge " checked="checked"/>是
						<input name="hasBrother" value="0" type="radio" class="input-xlarge "/>否
					</div>
				</div>
				<div class="control-group hide" id="hasChild">
					<label class="control-label">是否有子女:</label>
					<div class="controls">
						<input name="hasChild" value="1" type="radio" class="input-xlarge " checked="checked"/>是
						<input name="hasChild" value="0" type="radio" class="input-xlarge "/>否
					</div>
				</div>
			</c:when>
			<c:when test="${personnelPoliceFamilyTwo.step eq '4'}">
				<div class="control-group" id="hasBrother">
					<label class="control-label">是否有兄弟姐妹:</label>
					<div class="controls">
						<input name="hasBrother" value="1" type="radio" class="input-xlarge " checked="checked"/>是
						<input name="hasBrother" value="0" type="radio" class="input-xlarge "/>否
					</div>
				</div>

				<div class="control-group hide" id="hasChild">
					<label class="control-label">是否有子女:</label>
					<div class="controls">
						<input name="hasChild" value="1" type="radio" class="input-xlarge " checked="checked"/>是
						<input name="hasChild" value="0" type="radio" class="input-xlarge "/>否
					</div>
				</div>
			</c:when>
			<c:when test="${personnelPoliceFamilyTwo.step eq '5'}">
				<div class="control-group" id="hasBrother">
					<label class="control-label">是否有兄弟姐妹:</label>
					<div class="controls">
						<input name="hasBrother" value="1" type="radio" class="input-xlarge " checked="checked"/>是
						<input name="hasBrother" value="0" type="radio" class="input-xlarge "/>否
					</div>
				</div>
				<%--是否还有兄弟姐妹，没有时则显示是否有子女--%>
				<div class="control-group hide" id="hasChild">
					<label class="control-label">是否有子女:</label>
					<div class="controls">
						<input name="hasChild" value="1" type="radio" class="input-xlarge " checked="checked"/>是
						<input name="hasChild" value="0" type="radio" class="input-xlarge "/>否
					</div>
				</div>
			</c:when>
			<c:when test="${personnelPoliceFamilyTwo.step eq '6'}">
				<div class="control-group">
					<label class="control-label">子女是否有配偶:</label>
					<div class="controls">
						<input name="hasChildInLow" value="1" type="radio" class="input-xlarge " checked="checked"/>是
						<input name="hasChildInLow" value="0" type="radio" class="input-xlarge "/>否
						<%--否则完成--%>
					</div>
				</div>

			</c:when>
			<c:when test="${personnelPoliceFamilyTwo.step eq '7'}">
				<div class="control-group">
					<label class="control-label">是否有子女:</label>
					<div class="controls">
						<input name="hasChild" value="1" type="radio" class="input-xlarge " checked="checked"/>是
						<input name="hasChild" value="0" type="radio" class="input-xlarge "/>否
							<%--否则完成--%>
					</div>
				</div>

			</c:when>
			<c:otherwise></c:otherwise>
		</c:choose>

<%--		<input id="btn" class="btn btn-primary" type="button" value="下一步" onclick=""/>--%>

		<%--如果已婚则 填写配偶和配偶父母信息--%>
		<%--如果有兄弟姐妹则填写兄弟姐妹信息--%>
		<%--如果有子女则填写兄弟姐妹信息--%>
		<%--如果子女有配偶则填写子女配偶信息--%>
	<%--	<div class="control-group">
			<lable class="control-label">是否已婚：</lable>
			<div class="controls">
				<form:radiobuttons path="delFlag" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
			</div>
		</div>--%>
		<div class="form-actions">
			<c:if test="${add == 'add' && step != '7'}">
				<shiro:hasPermission name="personnel:personnelPoliceFamily:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="下一步"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<%--<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.history.go(-1)"/>--%>
			<shiro:hasPermission name="personnel:personnelPoliceFamily:edit">
				<input id="btnEnd" class="btn btn-primary" type="button" value="结 束" onclick="complete()"/>
			</shiro:hasPermission>
		</div>
	</form:form>
	<script>
		if ("success" == "${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</body>
</html>