<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>督察问题库管理</title>
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

<%--		<li><a href="${ctx}/affair/affairDcwtLibrary/">督察问题库列表</a></li>--%>
<%--		<li class="active"><a href="${ctx}/affair/affairDcwtLibrary/form?id=${affairDcwtLibrary.id}">督察问题库<shiro:hasPermission name="affair:affairDcwtLibrary:edit">${not empty affairDcwtLibrary.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairDcwtLibrary:edit">查看</shiro:lacksPermission></a></li>--%>
<br/>
<form:form id="inputForm" modelAttribute="affairDcwtLibrary" action="${ctx}/affair/affairDcwtLibrary/save" method="post" class="form-horizontal">
	<form:hidden path="id"/>
	<sys:message content="${message}"/>
	<c:choose>
		<%--11.10  已添加的督察记录无法修改“责任单位一栏”，理应可以修改  kevin.jia--%>
		<c:when test="${affairDcwtLibrary.responsibleUnitId == fns:getUser().office.id && (fns:getUser().id ne 'ca32723864644fa8979693dc9a539b91' && fns:getUser().id ne '1fdedc31fd6944eb8cbb9a279f4cb3c4' &&
				fns:getUser().id ne '26449823050b49c786f7baff26b6a7a2' && fns:getUser().id ne 'ad04613867cc41f081c78ac19f159263')}">
			<div class="control-group">
				<label class="control-label">开始时间：</label>
				<div class="controls">
					<input name="time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
						   value="<fmt:formatDate value="${affairDcwtLibrary.time}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" disabled />
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">结束时间：</label>
				<div class="controls">
					<input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
						   value="<fmt:formatDate value="${affairDcwtLibrary.finishDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" disabled/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
                <label class="control-label">责任单位：</label>
                <div class="controls">
                    <sys:treeselect id="responsibleUnitId" name="responsibleUnitId" value="${affairDcwtLibrary.responsibleUnitId}" labelName="responsibleUnit" labelValue="${affairDcwtLibrary.responsibleUnit}"
                                    title="责任单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true"  dataMsgRequired="必填信息" disabled="disabled"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
			<div class="control-group">
				<label class="control-label">存在不足：</label>
				<div class="controls">
					<form:select path="problemCategory" class="input-xlarge required" disabled="true" multiple="true">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('affair_wtlb')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">整改情况：</label>
				<div class="controls">
					<form:select path="rectification" class="input-xlarge required" disabled="true">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('affair_zhenggai')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">督察方式：</label>
				<div class="controls">
					<form:select path="dcType" class="input-xlarge required" disabled="true">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('affair_dc_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">监督单位：</label>
				<div class="controls">
					<form:select path="supervisoryUnit" class="input-xlarge required" disabled="true">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('affair_jdunit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">问题概述：</label>
				<div class="controls">
					<form:textarea id="foundProblem" htmlEscape="false" path="foundProblem" rows="10" maxlength="500" class="input-xlarge" cssStyle="width: 450px;" disabled="true"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">整改说明：</label>
				<div class="controls">
					<form:textarea id="processingSituation" htmlEscape="false" path="processingSituation" rows="10" maxlength="500" class="input-xlarge" cssStyle="width: 450px;"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">附件：</label>
				<div class="controls">
					<form:hidden id="annex" path="annex" htmlEscape="false" maxlength="255" class="input-xlarge" disabled="true"/>
					<sys:webuploader input="annex" type="files" uploadPath="affair/affairDcwtLibrary" selectMultiple="true" readonly="true"/>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="control-group">
				<label class="control-label">开始时间：</label>
				<div class="controls">
					<input name="time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
						   value="<fmt:formatDate value="${affairDcwtLibrary.time}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"  />
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">结束时间：</label>
				<div class="controls">
					<input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
						   value="<fmt:formatDate value="${affairDcwtLibrary.finishDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
	<%--		<div class="control-group">
                <label class="control-label">责任单位：</label>
                <div class="controls">
                    <sys:treeselect id="responsibleUnit" name="responsibleUnitId" value="${affairDcwtLibrary.responsibleUnitId}" labelName="responsibleUnit" labelValue="${affairDcwtLibrary.responsibleUnit}"
                                    title="责任单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>--%>
			<c:choose>
				<c:when test="${fns:getUser().id eq 'ca32723864644fa8979693dc9a539b91' || fns:getUser().id eq '1fdedc31fd6944eb8cbb9a279f4cb3c4' ||
				fns:getUser().id eq '26449823050b49c786f7baff26b6a7a2' || fns:getUser().id eq 'ad04613867cc41f081c78ac19f159263'}">
					<div class="control-group">
						<label class="control-label">责任单位：</label>
						<div class="controls">
							<sys:treeselect id="responsibleUnit" name="responsibleUnitId"
											value="${affairDcwtLibrary.responsibleUnitId}" labelName="responsibleUnit"
											labelValue="${affairDcwtLibrary.responsibleUnit}"
											title="责任单位" url="/sys/office/treeData?type=2" cssClass="required"
											allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"
											isAll="true"/>
							<span class="help-inline"><font color="red">*</font> </span>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="control-group">
						<label class="control-label">责任单位：</label>
						<div class="controls">
							<sys:treeselect id="responsibleUnit" name="responsibleUnitId"
											value="${affairDcwtLibrary.responsibleUnitId}" labelName="responsibleUnit"
											labelValue="${affairDcwtLibrary.responsibleUnit}"
											title="责任单位" url="/sys/office/treeData?type=2" cssClass="required"
											allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
							<span class="help-inline"><font color="red">*</font> </span>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
			<div class="control-group">
				<label class="control-label">存在不足：</label>
				<div class="controls">
					<form:select path="problemCategoryArr" class="input-xlarge required" multiple="true"  cssStyle="width: 500px;">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('affair_wtlb')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">督察方式：</label>
				<div class="controls">
					<form:select path="dcType" class="input-xlarge required">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('affair_dc_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">整改情况：</label>
				<div class="controls">
					<form:select path="rectification" class="input-xlarge required" >
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('affair_zhenggai')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">监督单位：</label>
				<div class="controls">
					<form:select path="supervisoryUnit" class="input-xlarge required" >
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('affair_jdunit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">问题概述：</label>
				<div class="controls">
					<form:textarea id="foundProblem" htmlEscape="false" path="foundProblem" rows="10" maxlength="500" class="input-xlarge" cssStyle="width: 450px;"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">整改说明：</label>
				<div class="controls">
					<form:textarea id="processingSituation" htmlEscape="false" path="processingSituation" rows="10" maxlength="500" class="input-xlarge" cssStyle="width: 450px;"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">附件：</label>
				<div class="controls">
					<form:hidden id="annex" path="annex" htmlEscape="false" maxlength="255" class="input-xlarge" />
					<sys:webuploader input="annex" type="files" uploadPath="affair/affairDcwtLibrary" selectMultiple="true" />
				</div>
			</div>
		</c:otherwise>
	</c:choose>


	<div class="form-actions">
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
		<shiro:hasPermission name="affair:affairDcwtLibrary:edit"></shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
	</div>
</form:form>
<script>
	if("success"=="${saveResult}"){
		parent.$.jBox.close();
	}
</script>
</body>
</html>