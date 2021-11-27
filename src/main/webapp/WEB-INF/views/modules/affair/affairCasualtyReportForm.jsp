<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>抚恤申报管理</title>
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
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="affairCasualtyReport" action="${ctx}/affair/affairCasualtyReport/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input path="idNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">种类与性质：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_casualty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">部门与警种：</label>
			<div class="controls">
				<form:input path="depPolice" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">伤亡时间：</label>
			<div class="controls">
				<input name="casualtyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairCasualtyReport.casualtyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">部门名称：</label>
			<div class="controls">
				<sys:treeselect id="affirmDep" name="affirmDepId" value="${affairCasualtyReport.affirmDepId}" labelName="affirmDep" labelValue="${affairCasualtyReport.affirmDep}"
					title="部门名称" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">认定部门：</label>
			<div class="controls">
				<sys:treeselect id="depName" name="depNameId" value="${affairCasualtyReport.depNameId}" labelName="depName" labelValue="${affairCasualtyReport.depName}"
								title="认定部门" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">部门名称：</label>
			<div class="controls">
				<sys:treeselect id="depName" name="depNameId" value="${affairCasualtyReport.depNameId}" labelName="depName" labelValue="${affairCasualtyReport.depName}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">认定时间：</label>
			<div class="controls">
				<input name="affirmDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairCasualtyReport.affirmDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">复核部门：</label>
			<div class="controls">
				<form:input path="checkDep" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">复核时间：</label>
			<div class="controls">
				<input name="checkDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairCasualtyReport.checkDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">牺牲/病故证明书编号：</label>
			<div class="controls">
				<form:input path="certificateNo1" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">负伤程度：</label>
			<div class="controls">
				<form:input path="injuryDegree" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">残疾等级：</label>
			<div class="controls">
				<form:input path="level" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">伤残评定机构：</label>
			<div class="controls">
				<form:input path="evaluateOrg" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">记录状态：</label>
			<div class="controls">
				<form:input path="jlStatus" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否自杀：</label>
			<div class="controls">
				<form:select path="isSelfKill" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">伤亡原因：</label>
			<div class="controls">
				<form:input path="reason" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">被伤害方式：</label>
			<div class="controls">
				<form:input path="method" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">执行勤务情况：</label>
			<div class="controls">
				<form:input path="zxqwSituation" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">伤亡事件实力对比：</label>
			<div class="controls">
				<form:input path="comparison" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用武器警械：</label>
			<div class="controls">
				<form:input path="useArm" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">伤亡情节：</label>
			<div class="controls">
				<form:input path="plot" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发生日期：</label>
			<div class="controls">
				<input name="happenDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairCasualtyReport.happenDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">事故性质：</label>
			<div class="controls">
				<form:input path="character" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">责任认定：</label>
			<div class="controls">
				<form:input path="dutyAffirm" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">违章情况：</label>
			<div class="controls">
				<form:input path="wzSituation" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">烈士标识：</label>
			<div class="controls">
				<form:input path="martyrLogo" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证书编号：</label>
			<div class="controls">
				<form:input path="certificateNo2" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准日期：</label>
			<div class="controls">
				<input name="approvalDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairCasualtyReport.approvalDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准部门：</label>
			<div class="controls">
				<form:input path="approvalDep" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">认定因公牺牲文件：</label>
			<div class="controls">
				<%--<form:hidden id="rdygxsFile" path="rdygxsFile" htmlEscape="false" class="input-xlarge"/>
				<sys:ckfinder input="rdygxsFile" type="files" uploadPath="/affair/affairCasualtyReport" selectMultiple="true"/>--%>
					<form:hidden id="rdygxsFile" path="rdygxsFile" htmlEscape="false" maxlength="255" class="input-xlarge"/>
					<sys:webuploader input="rdygxsFile" type="files" uploadPath="/affair/affairCasualtyReport" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">民政（人社）部门复核认定文件：</label>
			<div class="controls">
				<%--<form:hidden id="bmfhrdFile" path="bmfhrdFile" htmlEscape="false" class="input-xlarge"/>
				<sys:ckfinder input="bmfhrdFile" type="files" uploadPath="/affair/affairCasualtyReport" selectMultiple="true"/>--%>
					<form:hidden id="bmfhrdFile" path="bmfhrdFile" htmlEscape="false" maxlength="255" class="input-xlarge"/>
					<sys:webuploader input="bmfhrdFile" type="files" uploadPath="/affair/affairCasualtyReport" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">烈士批复文件：</label>
			<div class="controls">
				<%--<form:hidden id="lspfFile" path="lspfFile" htmlEscape="false" class="input-xlarge"/>
				<sys:ckfinder input="lspfFile" type="files" uploadPath="/affair/affairCasualtyReport" selectMultiple="true"/>--%>
					<form:hidden id="lspfFile" path="lspfFile" htmlEscape="false" maxlength="255" class="input-xlarge"/>
					<sys:webuploader input="lspfFile" type="files" uploadPath="/affair/affairCasualtyReport" selectMultiple="true"/>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人民警察牺牲/病故证明书：</label>
			<div class="controls">
				<%--<form:hidden id="certificatePath" path="certificatePath" htmlEscape="false" class="input-xlarge"/>
				<sys:ckfinder input="certificatePath" type="files" uploadPath="/affair/affairCasualtyReport" selectMultiple="true"/>--%>
					<form:hidden id="certificatePath" path="certificatePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
					<sys:webuploader input="certificatePath" type="files" uploadPath="/affair/affairCasualtyReport" selectMultiple="true"/>
			</div>
		</div>

		<%--<div class="control-group">
			<div class="controls">
				<form:hidden path="shStatus" value="3"/>
			</div>
		</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairCasualtyReport:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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