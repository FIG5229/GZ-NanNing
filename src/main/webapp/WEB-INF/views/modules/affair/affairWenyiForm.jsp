<%@ taglib prefix="tags" uri="http://ckfinder.com" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文艺作品管理</title>
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
	<%--<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairWenyi/">文艺作品列表</a></li>
		<li class="active"><a href="${ctx}/affair/affairWenyi/form?id=${affairWenyi.id}">文艺作品<shiro:hasPermission name="affair:affairWenyi:edit">${not empty affairWenyi.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairWenyi:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%><br/>
	<form:form id="inputForm" modelAttribute="affairWenyi" action="${ctx}/affair/affairWenyi/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">作品名称：</label>
			<div class="controls">
				<form:input path="proName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类别：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_wenyi_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">作者姓名：</label>
			<div class="controls">
				<form:input path="peoName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">作品内容：</label>
			<div class="controls">
				<form:textarea id="proContent" htmlEscape="true" path="proContent" rows="4" maxlength="150" class="input-xxlarge"/>
				<sys:ckeditor replace="proContent" uploadPath="affair/affairWenyi" />
			</div>
		</div>
		<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
		<c:choose>
			<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
				<div class="control-group">
					<label class="control-label">单位名称：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairWenyi.unitId}" labelName="unitName" labelValue="${affairWenyi.unitName}"
										title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="false" isAll="true"/>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="control-group">
					<label class="control-label">单位名称：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairWenyi.unitId}" labelName="unitName" labelValue="${affairWenyi.unitName}"
										title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="false"/>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		<%--<div class="control-group">
			<label class="control-label">单位名称：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairWenyi.unitId}" labelName="unitName" labelValue="${affairWenyi.unitName}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="false"/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">奖项名称：</label>
			<div class="controls">
				<form:input path="awardName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖项级别：</label>
			<div class="controls">
				<%--统计分析需要，不可为空--%>
				<form:select path="awardLevel" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_wenyi_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准单位：</label>
			<div class="controls">
				<form:input path="ratifyUnit" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准时间：</label>
			<div class="controls">
				<%--统计分析需要，不可为空--%>
				<input name="ratifyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairWenyi.ratifyTime}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采用媒体及版面：</label>
			<div class="controls">
				<form:input path="adoptMedia" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采用时间：</label>
			<div class="controls">
				<input name="adoptTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairWenyi.adoptTime}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="appendfile" path="appendfile" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="appendfile" type="files" uploadPath="affair/affairWenyi" selectMultiple="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairWenyi:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
<script>
	if("success"=="${saveResult}"){
		parent.$.jBox.tip("保存成功");
		parent.$.jBox.close();
	}

</script>
</body>
</html>