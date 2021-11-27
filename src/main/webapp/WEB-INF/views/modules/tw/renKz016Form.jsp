<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>自动考评-精神病患者信息拓展管理</title>
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
		<li><a href="${ctx}/tw/renKz016/">自动考评-精神病患者信息拓展列表</a></li>
		<li class="active"><a href="${ctx}/tw/renKz016/form?id=${renKz016.id}">自动考评-精神病患者信息拓展<shiro:hasPermission name="tw:renKz016:edit">${not empty renKz016.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="tw:renKz016:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="renKz016" action="${ctx}/tw/renKz016/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">rid：</label>
			<div class="controls">
				<form:input path="rid" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">rylb：</label>
			<div class="controls">
				<form:input path="rylb" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">rylbdm：</label>
			<div class="controls">
				<form:input path="rylbdm" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">ywwhtljl：</label>
			<div class="controls">
				<form:input path="ywwhtljl" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">sclx：</label>
			<div class="controls">
				<form:input path="sclx" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">cjzh：</label>
			<div class="controls">
				<form:input path="cjzh" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">scdj：</label>
			<div class="controls">
				<form:input path="scdj" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">swwplx：</label>
			<div class="controls">
				<form:input path="swwplx" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">lwzazdrly：</label>
			<div class="controls">
				<form:input path="lwzazdrly" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">sflx：</label>
			<div class="controls">
				<form:input path="sflx" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">sfdd：</label>
			<div class="controls">
				<form:input path="sfdd" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">sfly：</label>
			<div class="controls">
				<form:input path="sfly" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">fxsfrdd：</label>
			<div class="controls">
				<form:input path="fxsfrdd" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">cjsj：</label>
			<div class="controls">
				<input name="cjsj" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${renKz016.cjsj}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
			<label class="control-label">cjdwdm：</label>
			<div class="controls">
				<form:input path="cjdwdm" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">xgsj：</label>
			<div class="controls">
				<input name="xgsj" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${renKz016.xgsj}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
			<label class="control-label">fxsfrsj：</label>
			<div class="controls">
				<input name="fxsfrsj" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${renKz016.fxsfrsj}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">gzdw：</label>
			<div class="controls">
				<form:input path="gzdw" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">sgtlqc：</label>
			<div class="controls">
				<form:input path="sgtlqc" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">sfdtlbmsf：</label>
			<div class="controls">
				<form:input path="sfdtlbmsf" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">impmark：</label>
			<div class="controls">
				<form:input path="impmark" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">jtzzjltljl：</label>
			<div class="controls">
				<form:input path="jtzzjltljl" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">sfjl：</label>
			<div class="controls">
				<form:input path="sfjl" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">sftlzg：</label>
			<div class="controls">
				<form:input path="sftlzg" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">xzbbr：</label>
			<div class="controls">
				<form:input path="xzbbr" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">bbrzw：</label>
			<div class="controls">
				<form:input path="bbrzw" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">bbrlxdh：</label>
			<div class="controls">
				<form:input path="bbrlxdh" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">swgwlx：</label>
			<div class="controls">
				<form:input path="swgwlx" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">zyshgx：</label>
			<div class="controls">
				<form:input path="zyshgx" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">kpfs：</label>
			<div class="controls">
				<form:input path="kpfs" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">bz：</label>
			<div class="controls">
				<form:input path="bz" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">zazdrybh：</label>
			<div class="controls">
				<form:input path="zazdrybh" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">ssxzc：</label>
			<div class="controls">
				<form:input path="ssxzc" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">whsgs：</label>
			<div class="controls">
				<form:input path="whsgs" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">sfqy：</label>
			<div class="controls">
				<form:input path="sfqy" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">xchfjl：</label>
			<div class="controls">
				<form:input path="xchfjl" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">ssdfpcs：</label>
			<div class="controls">
				<form:input path="ssdfpcs" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">dfpcsdh：</label>
			<div class="controls">
				<form:input path="dfpcsdh" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">jszh：</label>
			<div class="controls">
				<form:input path="jszh" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">jdccx：</label>
			<div class="controls">
				<form:input path="jdccx" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">cph：</label>
			<div class="controls">
				<form:input path="cph" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">jdcssdwgr：</label>
			<div class="controls">
				<form:input path="jdcssdwgr" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">ywwfjl：</label>
			<div class="controls">
				<form:input path="ywwfjl" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">sfslmd：</label>
			<div class="controls">
				<form:input path="sfslmd" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">lgqk：</label>
			<div class="controls">
				<form:input path="lgqk" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">jxrq：</label>
			<div class="controls">
				<input name="jxrq" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${renKz016.jxrq}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">jxyy：</label>
			<div class="controls">
				<form:input path="jxyy" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">jxfs：</label>
			<div class="controls">
				<form:input path="jxfs" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">jxbw：</label>
			<div class="controls">
				<form:input path="jxbw" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">clqk：</label>
			<div class="controls">
				<form:input path="clqk" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">sfjsbhz：</label>
			<div class="controls">
				<form:input path="sfjsbhz" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">sfhjsgldx：</label>
			<div class="controls">
				<form:input path="sfhjsgldx" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">sf：</label>
			<div class="controls">
				<form:input path="sf" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">zy：</label>
			<div class="controls">
				<form:input path="zy" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">grwlxx：</label>
			<div class="controls">
				<form:input path="grwlxx" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">ky：</label>
			<div class="controls">
				<form:input path="ky" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">lx：</label>
			<div class="controls">
				<form:input path="lx" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">zc：</label>
			<div class="controls">
				<form:input path="zc" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">xh：</label>
			<div class="controls">
				<form:input path="xh" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">tmtz：</label>
			<div class="controls">
				<form:input path="tmtz" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">tbbj：</label>
			<div class="controls">
				<form:input path="tbbj" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">qtzjlx：</label>
			<div class="controls">
				<form:input path="qtzjlx" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">qtzjh：</label>
			<div class="controls">
				<form:input path="qtzjh" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">glyj：</label>
			<div class="controls">
				<form:input path="glyj" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">lgmj：</label>
			<div class="controls">
				<form:input path="lgmj" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">clr：</label>
			<div class="controls">
				<form:input path="clr" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">tbdw：</label>
			<div class="controls">
				<form:input path="tbdw" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">jchdtlqy：</label>
			<div class="controls">
				<form:input path="jchdtlqy" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">jcfmqy：</label>
			<div class="controls">
				<form:input path="jcfmqy" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">xsz：</label>
			<div class="controls">
				<form:input path="xsz" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">stjkzk：</label>
			<div class="controls">
				<form:input path="stjkzk" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">sfrylx：</label>
			<div class="controls">
				<form:input path="sfrylx" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">sfryfl：</label>
			<div class="controls">
				<form:input path="sfryfl" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">zazdryjb：</label>
			<div class="controls">
				<form:input path="zazdryjb" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="tw:renKz016:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>