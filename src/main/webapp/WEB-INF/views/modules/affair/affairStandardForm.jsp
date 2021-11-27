<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人合格党员标准管理</title>
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
		function setStatus(status) {
			$("#addStatus").val(status);
		};
		if("sucess"=="${saveResult}"){
			parent.$.jBox.close();
		}
		//根据姓名自动查询相关信息
		function setDefaults() {
			//清空
			$("#idNumber").val('');
			$("#policeNo").val('');
			$.ajax({
				type:"post",
				url:"${ctx}/personnel/personnelBase/getPersonByName",
				data:{name:$("#name").val()},
				dataType:"json",
				success:function(data){
					if(data.success==true && data.result.length==1){
						$("#idNumber").val(data.result[0].idNumber);
						$("#policeNo").val(data.result[0].policeIdNumber);
					}else if(data.success==true && data.result.length>1){
						var html = '<table id="contentTable" class="table table-striped table-bordered table-condensed">';
						html += '<thead><tr><th></th><th>姓名</th><th>单位</th><th>身份证号</th><th>警号</th></tr></thead>';
						html += '<tbody>';
						for(var i=0; i< data.result.length; i++) {
							html += '<tr><td><input type="radio" name="selected" value="'+i+'"></td>';
							html += '<td>'+data.result[i].name+'</td>';
							html += '<td>'+data.result[i].workunitName+'</td>';
							html += '<td>'+data.result[i].idNumber+'</td>';
							html += '<td>'+data.result[i].policeIdNumber+'</td>';
							html += '</tr>';
						}

						html +=	'</tbody>';
						html +=	'</table>';
						var submit = function (v, h, f) {
							$("#idNumber").val(data.result[f.selected].idNumber);
							$("#policeNo").val(data.result[f.selected].policeIdNumber);
							return true;
						};
						top.$.jBox(html, { title: "输入",width: 600, height: 300, submit: submit });
					}else {
						$.jBox.tip('没有查询到该人名相关信息');
					}
				}
			})
		}
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairStandard/form?id=${affairStandard.id}">个人合格党员标准<shiro:hasPermission name="affair:affairStandard:edit">${not empty affairStandard.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairStandard:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairStandard" action="${ctx}/affair/affairStandard/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">所属党组织名称：</label>
			<div class="controls">
				<sys:treeselect id="partyOrganization" name="partyOrganizationId" value="${affairStandard.partyOrganizationId}" labelName="partyOrganization" labelValue="${affairStandard.partyOrganization}"
					title="党组织名称" url="/affair/affairGeneralSituation/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input id="name" path="name" htmlEscape="false" class="input-xlarge required" onkeydown="if(event.keyCode==13){setDefaults();return false;};"/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input id="idNumber" path="idNumber" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge required "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关材料：</label>
			<div class="controls">
				<form:hidden id="materialPath" path="materialPath" htmlEscape="false"  maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="materialPath" type="files" uploadPath="affair/affairStandard" selectMultiple="true"/>
			</div>
		</div>
		<input id="addStatus" name="addStatus" type="hidden"/>
		<div class="form-actions">
			<c:choose>
				<c:when test="${empty affairStandard.id}">
					<shiro:hasPermission name="affair:affairStandard:edit"><input class="btn btn-primary" type="submit" value="保 存" onclick="setStatus(1)"/>&nbsp;</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairStandard:edit"><input class="btn btn-primary" type="submit" value="提 交" onclick="setStatus(2)"/>&nbsp;</shiro:hasPermission>
				</c:when>
				<c:otherwise>
					<%--其他人的数据无权操作  自己的数据已提交后不能再重复保存和提交--%>
					<c:if test="${affairStandard.createBy.id == fns:getUser().id && affairStandard.addStatus != '2'}">
						<shiro:hasPermission name="affair:affairStandard:edit"><input class="btn btn-primary" type="submit" value="保 存" onclick="setStatus(1)"/>&nbsp;</shiro:hasPermission>
						<shiro:hasPermission name="affair:affairStandard:edit"><input class="btn btn-primary" type="submit" value="提 交" onclick="setStatus(2)"/>&nbsp;</shiro:hasPermission>
					</c:if>
				</c:otherwise>
			</c:choose>

			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>