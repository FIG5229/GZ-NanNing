<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>年度领导考核评分管理</title>
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
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/exam/examLdScore/">年度领导考核评分列表</a></li>
		<li class="active"><a href="${ctx}/exam/examLdScore/form?id=${examLdScore.id}">年度领导考核评分<shiro:hasPermission name="exam:examLdScore:edit">${not empty examLdScore.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="exam:examLdScore:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="examLdScore" action="${ctx}/exam/examLdScore/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">职位：</label>
			<div class="controls">
				<form:input path="job" htmlEscape="false" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge " disabled="true"/>
			</div>
		</div>
	<%--	<div class="control-group">
			<label class="control-label">一月：</label>
			<div class="controls">
				<form:input path="january" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">一月分数：</label>
			<div class="controls">
				<form:input path="januaryScore" htmlEscape="false" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">二月：</label>
			<div class="controls">
				<form:input path="february" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">二月分数：</label>
			<div class="controls">
				<form:input path="februaryScore" htmlEscape="false" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">三月：</label>
			<div class="controls">
				<form:input path="march" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">三月分数：</label>
			<div class="controls">
				<form:input path="marchScore" htmlEscape="false" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">四月：</label>
			<div class="controls">
				<form:input path="april" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">四月分数：</label>
			<div class="controls">
				<form:input path="aprilScore" htmlEscape="false" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">五月：</label>
			<div class="controls">
				<form:input path="may" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">五月分数：</label>
			<div class="controls">
				<form:input path="mayScore" htmlEscape="false" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">六月：</label>
			<div class="controls">
				<form:input path="june" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">六月分数：</label>
			<div class="controls">
				<form:input path="juneScore" htmlEscape="false" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">七月：</label>
			<div class="controls">
				<form:input path="july" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">七月分数：</label>
			<div class="controls">
				<form:input path="julyScore" htmlEscape="false" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">八月：</label>
			<div class="controls">
				<form:input path="august" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">八月分数：</label>
			<div class="controls">
				<form:input path="augustScore" htmlEscape="false" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">九月：</label>
			<div class="controls">
				<form:input path="september" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">九月分数：</label>
			<div class="controls">
				<form:input path="septemberScore" htmlEscape="false" class="input-xlarge " disabled="true"/>
			</div>
		</div>
	<%--	<div class="control-group">
			<label class="control-label">十月：</label>
			<div class="controls">
				<form:input path="october" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">十月分数：</label>
			<div class="controls">
				<form:input path="octoberScore" htmlEscape="false" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">十一月：</label>
			<div class="controls">
				<form:input path="november" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">十一月分数：</label>
			<div class="controls">
				<form:input path="novemberSocre" htmlEscape="false" class="input-xlarge " disabled="true"/>
			</div>
		</div>
	<%--	<div class="control-group">
			<label class="control-label">十二月：</label>
			<div class="controls">
				<form:input path="december" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">十二月分数：</label>
			<div class="controls">
				<form:input path="decemberScore" htmlEscape="false" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">日常分数：</label>
			<div class="controls">
				<form:input path="dailyScore" htmlEscape="false" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位得分：</label>
			<div class="controls">
				<form:input path="unitScore" htmlEscape="false" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">民主测评分数（5分）：</label>
			<div class="controls">
				<form:input path="minzhuScore" htmlEscape="false" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">综合履历得分（5分）：</label>
			<div class="controls">
				<form:input path="zongheScore" htmlEscape="false" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最终得分：</label>
			<div class="controls">
				<form:input path="sumScore" htmlEscape="false" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">绩效等次：</label>
			<div class="controls">
				<form:select path="grades" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('performance_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="exam:examLdScore:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>