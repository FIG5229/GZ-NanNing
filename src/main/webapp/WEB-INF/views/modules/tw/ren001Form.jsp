<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>自动考评-精神病患者基本信息管理</title>
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
		<li><a href="${ctx}/tw/ren001/">自动考评-精神病患者基本信息列表</a></li>
		<li class="active"><a href="${ctx}/tw/ren001/form?id=${ren001.id}">自动考评-精神病患者基本信息<shiro:hasPermission name="tw:ren001:edit">${not empty ren001.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="tw:ren001:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="ren001" action="${ctx}/tw/ren001/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">xm：</label>
			<div class="controls">
				<form:input path="xm" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">cym：</label>
			<div class="controls">
				<form:input path="cym" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">zjlx：</label>
			<div class="controls">
				<form:input path="zjlx" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">zjh：</label>
			<div class="controls">
				<form:input path="zjh" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">xb：</label>
			<div class="controls">
				<form:input path="xb" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">xx：</label>
			<div class="controls">
				<form:input path="xx" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">mz：</label>
			<div class="controls">
				<form:input path="mz" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">gj：</label>
			<div class="controls">
				<form:input path="gj" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">hyzk：</label>
			<div class="controls">
				<form:input path="hyzk" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">zc：</label>
			<div class="controls">
				<form:input path="zc" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">csrq：</label>
			<div class="controls">
				<input name="csrq" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${ren001.csrq}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">cm：</label>
			<div class="controls">
				<form:input path="sg" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">whcd：</label>
			<div class="controls">
				<form:input path="whcd" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">zzmm：</label>
			<div class="controls">
				<form:input path="zzmm" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">zjxy：</label>
			<div class="controls">
				<form:input path="zjxy" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">hjdssqx：</label>
			<div class="controls">
				<form:input path="hjdssqx" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">xzzssqx：</label>
			<div class="controls">
				<form:input path="xzzssqx" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">hjdxz：</label>
			<div class="controls">
				<form:input path="hjdxz" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">xzzxz：</label>
			<div class="controls">
				<form:input path="xzzxz" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">gddh：</label>
			<div class="controls">
				<form:input path="gddh" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">qtlxhm：</label>
			<div class="controls">
				<form:input path="qtlxhm" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">sjhm：</label>
			<div class="controls">
				<form:input path="sjhm" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">cjsj：</label>
			<div class="controls">
				<input name="cjsj" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${ren001.cjsj}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">cjr：</label>
			<div class="controls">
				<form:input path="cjr" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">cjrjh：</label>
			<div class="controls">
				<form:input path="cjrjh" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">cjdw：</label>
			<div class="controls">
				<form:input path="cjdw" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">xgsj：</label>
			<div class="controls">
				<input name="xgsj" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${ren001.xgsj}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">xgr：</label>
			<div class="controls">
				<form:input path="xgr" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">xgrjh：</label>
			<div class="controls">
				<form:input path="xgrjh" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">xgdw：</label>
			<div class="controls">
				<form:input path="xgdw" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">xgdwdm：</label>
			<div class="controls">
				<form:input path="xgdwdm" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">nl：</label>
			<div class="controls">
				<form:input path="nl" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">sfczrk：</label>
			<div class="controls">
				<form:input path="sfczrk" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">impmark：</label>
			<div class="controls">
				<form:input path="impmark" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">hjd：</label>
			<div class="controls">
				<form:input path="hjd" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">xmpy：</label>
			<div class="controls">
				<form:input path="xmpy" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">chpy：</label>
			<div class="controls">
				<form:input path="chpy" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">tz：</label>
			<div class="controls">
				<form:input path="tz" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">bm：</label>
			<div class="controls">
				<form:input path="bm" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">cym2：</label>
			<div class="controls">
				<form:input path="cym2" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">hjdpcs：</label>
			<div class="controls">
				<form:input path="hjdpcs" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">jzdpcs：</label>
			<div class="controls">
				<form:input path="jzdpcs" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">jg：</label>
			<div class="controls">
				<form:input path="jg" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">kpfs：</label>
			<div class="controls">
				<form:input path="kpfs" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">ly：</label>
			<div class="controls">
				<form:input path="ly" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">wxh：</label>
			<div class="controls">
				<form:input path="wxh" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">qqh：</label>
			<div class="controls">
				<form:input path="qqh" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">zfbzh：</label>
			<div class="controls">
				<form:input path="zfbzh" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">wbh：</label>
			<div class="controls">
				<form:input path="wbh" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">qtwlxx：</label>
			<div class="controls">
				<form:input path="qtwlxx" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">cjdwdm：</label>
			<div class="controls">
				<form:input path="cjdwdm" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="tw:ren001:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>