<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单位集体奖励表管理</title>
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
			if($("#nameCode").val() == '12'){
				document.getElementById('name').style.visibility="visible";
			}else{
				document.getElementById('name').style.visibility="hidden";
			}
		});
	</script>
</head>
<body>
<br>
	<form:form id="inputForm" modelAttribute="affairXcUnitReward" action="${ctx}/affair/affairXcUnitReward/save" method="post" class="form-horizontal" style="margin-top:10px;">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
		<c:choose>
			<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
				<div class="control-group">
					<label class="control-label">受奖单位名称：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairXcUnitReward.unitId}" labelName="unit" labelValue="${affairXcUnitReward.unit}"
										title="受奖单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息" isAll="true"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="control-group">
					<label class="control-label">受奖单位名称：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairXcUnitReward.unitId}" labelName="unit" labelValue="${affairXcUnitReward.unit}"
										title="受奖单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		<%--<div class="control-group">
			<label class="control-label">受奖单位名称：</label>
			<div class="controls">
&lt;%&ndash;				<form:input path="unitName" htmlEscape="false" class="input-xlarge required"/>&ndash;%&gt;
				<sys:treeselect id="unit" name="unitId" value="${affairXcUnitReward.unitId}" labelName="unit" labelValue="${affairXcUnitReward.unit}"
								title="受奖单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<%--<div class="control-group">
			<label class="control-label">受奖单位代码：</label>
			<div class="controls">
				<form:input path="unitCode" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建制标志：</label>
			<div class="controls">
				<form:select path="flag" class="input-xlarge " >
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">奖励名称：</label>
			<div class="controls">
				<form:select path="nameCode" class="input-xlarge required"  onchange="Awards()">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_reward_code')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
				<form:input path="name" htmlEscape="false" class="input-xlarge "  cssStyle="visibility:hidden"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">荣誉级别：</label>
			<div class="controls">
				<form:select path="level" class="input-xlarge " >
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_approval_unitLevel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">奖励类别：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge " >
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_org_reward_punish')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
--%>
		<div class="control-group">
			<label class="control-label">单位警种：</label>
			<div class="controls">
				<form:select path="unitPolice" class="input-xlarge required" >
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_unit_police')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">奖励名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge " id="nameInput"/>
			</div>
		</div>--%>
		<%--<div class="control-group">
			<label class="control-label">批准单位：</label>
			<div class="controls">
				<sys:treeselect id="approvalUnit" name="approvalUnitId" value="${affairXcUnitReward.approvalUnitId}" labelName="approvalUnit" labelValue="${affairXcUnitReward.approvalUnit}"
								title="批准单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">批准单位：</label>
			<div class="controls">
				<form:select path="approvalUnit" class="input-xlarge " >
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_approval_unit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">批准单位级别：</label>
			<div class="controls">
				<form:select path="approvalUnitLevel" class="input-xlarge required" >
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_approval_unitLevel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">批准日期：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="<fmt:formatDate value="${affairXcUnitReward.date}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准文号：</label>
			<div class="controls">
				<form:input path="fileNo" htmlEscape="false" class="input-xlarge  "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准文件名称：</label>
			<div class="controls">
				<form:input path="fileName" htmlEscape="false" class="input-xlarge  "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准机关类别：</label>
			<div class="controls">
				<form:select path="approvalUnitType" class="input-xlarge " >
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_approval_unitType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">撤销奖励日期：</label>
			<div class="controls">
				<input name="reDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${affairXcUnitReward.reDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">事迹材料：</label>
			<div class="controls">
				<form:textarea path="deedsMaterial" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filePath" type="files" uploadPath="affair/affairKnowledge" selectMultiple="true"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">撤销奖励批准单位：</label>
			<div class="controls">
				<sys:treeselect id="reUnit" name="reUnitId" value="${affairXcUnitReward.reUnitId}" labelName="reUnit" labelValue="${affairXcUnitReward.reUnit}"
								title="批准单位" url="/sys/office/treeData?type=2"  allowClear="true" notAllowSelectParent="false"/>
			</div>
		</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairXcUnitReward:add">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
<script>
	if("sucess"=="${saveResult}"){
		parent.$.jBox.tip("保存成功");
		parent.$.jBox.close();
	}
</script>

<script language="JavaScript">

	function Awards() {
		if($("#nameCode").val() == '12'){
				document.getElementById('name').style.visibility="visible";
			}else{
				document.getElementById('name').style.visibility="hidden";
			}
		}

</script>
</body>
</html>