<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>警衔信息管理</title>
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
	<form:form id="inputForm" modelAttribute="personnelPoliceRank" action="${ctx}/personnel/personnelPoliceRank/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<c:choose>
			<c:when test="${personnelPoliceRank.idNumber == null}">
				<div class="control-group">
					<label class="control-label">身份证号：</label>
					<div class="controls">
						<form:input path="idNumber" htmlEscape="false" class="input-xlarge required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${isHistory != null && isHistory != ''}">
						<div class="control-group">
							<label class="control-label">身份证号：</label>
							<div class="controls">
								<form:input path="idNumber" htmlEscape="false" class="input-xlarge required"/>
								<span class="help-inline"><font color="red">*</font> </span>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<input id="idNumber" name="idNumber" type="hidden" value="${personnelPoliceRank.idNumber}"/>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
		<div class="control-group">
			<label class="control-label">警衔名称：</label>
			<div class="controls">
				<form:select path="policeRankLevel" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('police_rank_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">警衔名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<%--关联人员表查询--%>
		<%--<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="peopleName" htmlEscape="false" class="input-xlarge required"/>
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
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${personnelPoliceRank.unitId}" labelName="unit" labelValue="${personnelPoliceRank.unit}"
								title="所属单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false"/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">警衔类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personnel_jxtype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">衔称变动原因：</label>
			<div class="controls">
				<form:select path="changeReason" class="input-xlarge">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('dict_change_reason')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<%--<form:input path="changeReason" htmlEscape="false" class="input-xlarge "/>--%>
			</div>
		</div>
		<%--关联人员表查询--%>
<%--		<div class="control-group">
			<label class="control-label">出生日期：</label>
			<div class="controls">
				<input name="birthdayTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${personnelPoliceRank.birthdayTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参加工作时间：</label>
			<div class="controls">
				<input name="workTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${personnelPoliceRank.workTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">起算日期：</label>
			<div class="controls">
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${personnelPoliceRank.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">终止日期：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${personnelPoliceRank.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授衔日期：</label>
			<div class="controls">
				<input name="awardTitleDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${personnelPoliceRank.awardTitleDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</div>
		</div>
		<%--关联人员表查询--%>
<%--		<div class="control-group">
			<label class="control-label">学制：</label>
			<div class="controls">
				<form:input path="xuezhi" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">应加学制年限：</label>
			<div class="controls">
				<form:input path="xuezhiYear" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授衔批准单位名称：</label>
			<div class="controls">
				<form:input path="approvalUnitName" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授衔令号：</label>
			<div class="controls">
				<form:input path="approvalNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授衔批准单位代码：</label>
			<div class="controls">
				<form:input path="approvalUnitCode" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">衔称状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personnel_xctype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">警衔取消原因：</label>
			<div class="controls">
				<form:select path="cancelReason" class="input-xlarge">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('dict_cancel_reason')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<%--<form:input path="cancelReason" htmlEscape="false" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">警衔保留原因：</label>
			<div class="controls">
				<form:select path="retainReason" class="input-xlarge">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('dict_retain_reason')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<%--<form:input path="retainReason" htmlEscape="false" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">警衔不保留原因：</label>
			<div class="controls">
				<form:select path="noretainReason" class="input-xlarge">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('dict_noretain_reason')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<%--<form:input path="noretainReason" htmlEscape="false" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授衔来源：</label>
			<div class="controls">
				<form:select path="source" class="input-xlarge">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('dict_rank_source')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<%--<form:input path="source" htmlEscape="false" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现任警衔：</label>
			<div class="controls">
				<form:input path="nowRank" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="personnel:personnelPoliceRank:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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