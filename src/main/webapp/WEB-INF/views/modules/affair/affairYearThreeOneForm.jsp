<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>&ldquo;三会一课&rdquo;录入管理</title>
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
		if("sucess"=="${saveResult}"){
			console.log(111);
			parent.$.jBox.close();
		};
		function setStatus(status) {
			$("#addStatus").val(status);
		}
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		&lt;%&ndash;<li><a href="${ctx}/affair/affairYearThreeOne/">&ldquo;三会一课&rdquo;录入列表</a></li>&ndash;%&gt;
		<li class="active"><a href="${ctx}/affair/affairYearThreeOne/form?id=${affairYearThreeOne.id}">&ldquo;三会一课&rdquo;录入<shiro:hasPermission name="affair:affairYearThreeOne:edit">${not empty affairYearThreeOne.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairYearThreeOne:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairYearThreeOne" action="${ctx}/affair/affairYearThreeOne/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">会议名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">党组织名称：</label>
			<div class="controls">
				<sys:treeselect id="partyOrganization" name="partyOrganizationId" value="${affairYearThreeOne.partyOrganizationId}" labelName="partyOrganization" labelValue="${affairYearThreeOne.partyOrganization}"
					title="党组织名称" url="/affair/affairGeneralSituation/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会议类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label=""/>
<%--					<form:options items="${fns:getDictList('affair_year_three_one_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
					<form:options items="${fns:getDictList('affair_year_three_one_type_Two')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年度：</label>
			<div class="controls">
				<input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="${affairYearThreeOne.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会议时间：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairYearThreeOne.date}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主持人：</label>
			<div class="controls">
				<form:input path="hold" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">记录人：</label>
			<div class="controls">
				<form:input path="noteTaker" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会议议程：</label>
			<div class="controls">
				<form:textarea path="agenda" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">应参会人员：</label>
			<div class="controls">
				<sys:treeselect id="person1" name="person1Id" value="${affairYearThreeOne.person1Id}" labelName="person1" labelValue="${affairYearThreeOne.person1}"
								title="单位" url="/sys/office/treeData?type=3" cssClass="required" checked="true"  allowClear="true" notAllowSelectParent="false"/>
&lt;%&ndash;				<form:textarea path="person1" htmlEscape="false" rows="4" class="input-xxlarge "/>&ndash;%&gt;
			</div>
		</div>--%>
		<%--<div class="control-group">
			<label class="control-label">已会人员：</label>
			<div class="controls">
				<form:textarea path="person2" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">参会人员：</label>
			<div class="controls">
				<sys:treeselect id="person2" name="person2Id" value="${affairYearThreeOne.person2Id}" labelName="person2" labelValue="${affairYearThreeOne.person2}"
								title="参加人员" url="/sys/office/treeData?type=3" allowClear="true" checked="true" notAllowSelectParent="true" cssStyle="width:300px;" isAll="true" />
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">未会人员：</label>
			<div class="controls">
				<form:textarea path="person3" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">缺席人员：</label>
			<div class="controls">
				<sys:treeselect id="person3" name="person3Id" value="${affairYearThreeOne.person3Id}" labelName="person3" labelValue="${affairYearThreeOne.person3}"
								title="缺席人员" url="/sys/office/treeData?type=3" allowClear="true" checked="true" notAllowSelectParent="true" cssStyle="width:300px;" isAll="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关文件和影像视频：</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filePath" type="files" uploadPath="affair/affairYearThreeOne" selectMultiple="true"/>
			</div>
			<div class="controls" style="color: red">注意：需审核上传再使用或有要求时才上传</div>
		</div>
		<input id="addStatus" name="addStatus" type="hidden"/>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairYearThreeOne:edit">
				<c:choose>
					<c:when test="${empty affairYearThreeOne.id}">
						<input class="btn btn-primary" type="submit" value="保 存" onclick="setStatus(1)"/>
						<input class="btn btn-primary" type="submit" value="提 交" onclick="setStatus(2)"/>
					</c:when>
					<c:otherwise>
						<%--其他人的数据无权操作  自己的数据已提交后不能再重复保存和提交--%>
						<%--<c:if test="${affairYearThreeOne.createBy.id == fns:getUser().id && affairYearThreeOne.addStatus != '2'}">--%>
							<input class="btn btn-primary" type="submit" value="保 存" onclick="setStatus(1)"/>
							<input class="btn btn-primary" type="submit" value="提 交" onclick="setStatus(2)"/>
						<%--</c:if>--%>
					</c:otherwise>
				</c:choose>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>