<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民警思想动态分析管理</title>
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
<%--	<ul class="nav nav-tabs">--%>
		<%--<li><a href="${ctx}/affair/affairPoliceThoughtAnalysis/">民警思想动态分析列表</a></li>
		<li class="active"><a href="${ctx}/affair/affairPoliceThoughtAnalysis/form?id=${affairPoliceThoughtAnalysis.id}">民警思想动态分析<shiro:hasPermission name="affair:affairPoliceThoughtAnalysis:edit">${not empty affairPoliceThoughtAnalysis.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairPoliceThoughtAnalysis:edit">查看</shiro:lacksPermission></a></li>--%>
<%--	</ul>--%>
<br/>
	<form:form id="inputForm" modelAttribute="affairPoliceThoughtAnalysis" action="${ctx}/affair/affairPoliceThoughtAnalysis/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" name="classify" value="${affairPoliceThoughtAnalysis.classify}">
		<sys:message content="${message}"/>
<%--		<div class="control-group">--%>
<%--			<label class="control-label ">报送人：</label>--%>
<%--			<div class="controls">--%>
<%--				<form:input path="personName" htmlEscape="false" class="input-xlarge required"/>--%>
<%--				<span class="help-inline"><font color="red">*</font> </span>--%>
<%--			</div>--%>
<%--		</div>--%>
		<c:choose>
			<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
				<div class="control-group">
					<label class="control-label">单位：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairPoliceThoughtAnalysis.unitId}" labelName="unit" labelValue="${affairPoliceThoughtAnalysis.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息" isAll="true"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="control-group">
					<label class="control-label">单位：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairPoliceThoughtAnalysis.unitId}" labelName="unit" labelValue="${affairPoliceThoughtAnalysis.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		<%--<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairPoliceThoughtAnalysis.unitId}" labelName="unit" labelValue="${affairPoliceThoughtAnalysis.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>

		<div class="control-group">
			<label class="control-label">报送时间：</label>
			<div class="controls">
				<%--<input name="time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairPoliceThoughtAnalysis.time}" pattern="yyyy"/>"
					onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>--%>
				<input name="reportTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairPoliceThoughtAnalysis.reportTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">报告类型：</label>
			<div class="controls">
				<form:select path="reportType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('warn_month')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">简要内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge required"/>
				<sys:ckeditor replace="content"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="files" path="files" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="files" type="files" uploadPath="/affair/affairPoliceThoughtAnalysis" selectMultiple="true"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">更新者机构id：</label>
			<div class="controls">
				<form:input path="updateOrgId" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位id：</label>
			<div class="controls">
				<form:input path="unitId" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairPoliceThoughtAnalysis:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
		</div>
	</form:form>
<script>
	if("sucess"=="${saveResult}"){
		closeLoading();
		parent.$.jBox.tip("保存成功");
		parent.$.jBox.close();
	}
</script>
</body>
</html>