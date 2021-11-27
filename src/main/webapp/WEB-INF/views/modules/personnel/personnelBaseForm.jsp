<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人员基本情况信息管理</title>
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
			/*$("#idNumber").keydown(function (e) {
				if(e.keyCode == 13){
					var idNumber = $("#idNumber").val();
					if(idNumber.length == 18){
						var yearBirth = idNumber.substring(6,10);
						var monthBirth = idNumber.substring(10,12);
						var dayBirth = idNumber.substring(12,14);
						//获取当前年月日并计算年龄
						var myDate = new Date();
						var monthNow = myDate.getMonth() + 1;
						var dayNow = myDate.getDay();
						var age = myDate.getFullYear() - yearBirth;
						if(monthNow < monthBirth || (monthNow == monthBirth && dayNow < dayBirth)){
							age--;
						}
						//得到年龄
						var age = age;
						$("#age").val(age);
						if(parseInt(idNumber.substr(16,1)) % 2 == 1){
							$("#sex").val(1);
							$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
						}else{
							$("#sex").val(2);
							$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
						}
					}else if(idNumber.length == 15){
						var yearBirth = "19"+idNumber.substring(6,8);
						var monthBirth = idNumber.substring(8,10);
						var dayBirth = idNumber.substring(10,12);
						//获取当前年月日并计算年龄
						var myDate = new Date();
						var monthNow = myDate.getMonth() + 1;
						var dayNow = myDate.getDay();
						var age = myDate.getFullYear() - yearBirth;
						if(monthNow < monthBirth || (monthNow == monthBirth && dayNow < dayBirth)){
							age--;
						}
						//得到年龄
						var age = age;
						$("#age").val(age);
						if(parseInt(idNumber.substr(14,1)) % 2 == 1){
							$("#sex").val(1);
							$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
						}else{
							$("#sex").val(2);
							$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
						}
					}else {
						alert("请输入正确的身份证号！")
					}
				}
			})*/
		});
		function save() {
			$("#inputForm").submit();
		}
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="personnelBase" action="${ctx}/personnel/personnelBase/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
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
		<input type="hidden" id="age">
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input path="idNumber" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">民族：</label>
			<div class="controls">
				<form:select path="nation" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('nation')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出生日期：</label>
			<div class="controls">
				<input name="birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${personnelBase.birthday}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人员状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personnel_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">血型：</label>
			<div class="controls">
				<form:input path="bloodType" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">警员库标志：</label>
			<div class="controls">
				<form:select path="policeDepotSign" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">警号：</label>
			<div class="controls">
				<form:input path="policeIdNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人员类别：</label>
			<div class="controls">
				<form:input path="personnelType" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">籍贯：</label>
			<div class="controls">
				<form:input path="nativePlace" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学历：</label>
			<div class="controls">
				<form:input path="education" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话：</label>
			<div class="controls">
				<form:input path="phoneNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">出生地：</label>
			<div class="controls">
				<form:input path="birthPlace" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成长地：</label>
			<div class="controls">
				<form:input path="growPlace" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">户口性质：</label>
			<div class="controls">
				<form:input path="populationCharacter" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">户籍所在地：</label>
			<div class="controls">
				<form:input path="hjszd" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">个人身份：</label>
			<div class="controls">
				<form:input path="identity" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">婚姻状况：</label>
			<div class="controls">
				<form:select path="marriageStatus" class="input-xlarge">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_marital_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">涉密标志：</label>
			<div class="controls">
				<form:select path="secretStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">健康状态：</label>
			<div class="controls">
				<form:input path="healthStatus" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">户籍所在地详址：</label>
			<div class="controls">
				<form:input path="hjszdxz" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">暂缓列入套改实施范围原因类别：</label>
			<div class="controls">
				<form:input path="reason" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参加工作日期：</label>
			<div class="controls">
				<input name="workDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${personnelBase.workDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参加公安工作日期：</label>
			<div class="controls">
				<input name="publicSecurityDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${personnelBase.publicSecurityDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">基层工作经历时间：</label>
			<div class="controls">
				<form:input path="jcgzjlsj" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工龄计算校正值：</label>
			<div class="controls">
				<form:input path="gljsjzz" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">警衔应加学制年限：</label>
			<div class="controls">
				<form:input path="jxyjxznx" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">政治面貌：</label>
			<div class="controls">
				<form:select path="politicsFace" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('political_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参加组织日期：</label>
			<div class="controls">
				<input name="organizationDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${personnelBase.organizationDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作单位代码：</label>
			<div class="controls">
				<form:input path="workunitCode" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作单位名称：</label>
			<div class="controls">
				<sys:treeselect id="workunitName" name="workunitId" value="${personnelBase.workunitId}" labelName="workunitName" labelValue="${personnelBase.workunitName}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作单位(实际)：</label>
			<div class="controls">
				<sys:treeselect id="actualUnit" name="actualUnitId" value="${personnelBase.actualUnitId}" labelName="actualUnit" labelValue="${personnelBase.actualUnit}"
								title="单位" url="/sys/office/treeData?type=2" allowClear="true" notAllowSelectParent="false"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">工作单位名称：</label>
			<div class="controls">
				<form:input path="workunitName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">单位代码：</label>
			<div class="controls">
				<form:input path="unitCode" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关系所在单位：</label>
			<div class="controls">
				<form:input path="relationshipUnit" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人员所属部门和警种：</label>
			<div class="controls">
				<form:input path="bmhjz" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职务简称：</label>
			<div class="controls">
				<form:input path="jobAbbreviation" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职务全称：</label>
			<div class="controls">
				<form:input path="jobFullname" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人员工作岗位：</label>
			<div class="controls">
				<form:select path="job" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personnel_gw')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">协管干部标识：</label>
			<div class="controls">
				<form:input path="xggbbs" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否是协管干部：</label>
			<div class="controls">
				<form:select path="isXggb" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">管理类别：</label>
			<div class="controls">
				<form:input path="category" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">特长：</label>
			<div class="controls">
				<form:input path="expertise" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖励综述：</label>
			<div class="controls">
				<form:input path="award" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年度考核综述：</label>
			<div class="controls">
				<form:input path="assessment" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人员照片：</label>
			<div class="controls">
				<form:hidden id="photo" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="photo" type="files" uploadPath="personnel/personnelBase" selectMultiple="true"/>
<%--				<form:input path="photo" htmlEscape="false" class="input-xlarge "/>--%>
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
			<shiro:hasPermission name="personnel:personnelBase:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="save()" />&nbsp;</shiro:hasPermission>
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