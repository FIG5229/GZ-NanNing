<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>日常学习管理</title>
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
	<form:form id="inputForm" modelAttribute="affairLearnDaily" action="${ctx}/affair/affairLearnDaily/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		

		<c:choose>
			<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
				<div class="control-group">
					<label class="control-label">单位：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairLearnDaily.unitId}" labelName="unit" labelValue="${affairLearnDaily.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" isAll="true" dataMsgRequired="必填信息"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="control-group">
					<label class="control-label">单位：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairLearnDaily.unitId}" labelName="unit" labelValue="${affairLearnDaily.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		<div class="control-group">
			<label class="control-label">时间：</label>
			<div class="controls">
				<input name="learnTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${affairLearnDaily.learnTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学习地点：</label>
			<div class="controls">
				<form:input path="site" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主持人：</label>
			<div class="controls">
				<form:input path="compere" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参加人员：</label>
			<div class="controls">
				<sys:treeselect onchange="on()" id="participant" name="participantId" value="${affairLearnDaily.participantId}" labelName="participant" labelValue="${affairLearnDaily.participant}"
								title="参加人员" url="/sys/office/treeData?type=3" allowClear="true" checked="true" notAllowSelectParent="true" cssStyle="width:300px;" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人数：</label>
			<div class="controls">
				<form:input id="peoId" path="shouldBeTo" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学习内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>

		<%--<div class="control-group">
			<label class="control-label">学习形式：</label>
			<div class="controls">
				<form:select id="modality" path="modality" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_learn_daily_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">中心发言人：</label>
			<div class="controls">
				<form:input path="centreSpokesman" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">补充发言人：</label>
			<div class="controls">
				<form:input path="supplementarySpeaker" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">记录人：</label>
			<div class="controls">
				<form:input path="registrar" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">实到人数：</label>
			<div class="controls">
				<form:input path="attendance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
        <div class="control-group">
            <label class="control-label">附件：</label>
            <div class="controls">
                <form:hidden id="adjunct" path="adjunct" htmlEscape="false" maxlength="255" class="input-xlarge"/>
                <sys:webuploader input="adjunct" type="files" uploadPath="affair/affairLearnDaily" selectMultiple="true"/>
            </div>
        </div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairLearnDaily:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
		</div>
	</form:form>
	<script>
		function on() {
			var participantId = document.getElementById("participantId").value;
			if(participantId.split(",").length != 0){
				document.getElementById("peoId").value = participantId.split(",").length;
			}else {
				document.getElementById("peoId").value = 0;
			}
		}
		if("sucess"=="${saveResult}"){
			parent.$.jBox.tip("保存成功");
			parent.$.jBox.close();
		}
	</script>
</body>
</html>