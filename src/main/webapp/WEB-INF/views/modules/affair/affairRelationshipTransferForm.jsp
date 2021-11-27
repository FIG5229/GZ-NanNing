<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统内组织关系移交转接管理</title>
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
			parent.$.jBox.close();
		}

		//根据人员类别选择展示相应意见输入框
		function showOpinionDiv(d) {
			console.log(d.value);
			if(d.value=='4'){
				//选择局外转接
				$("#jwzjDiv").show();
				$("#ptDiv").hide();
			}else{
				$("#ptDiv").show();
				$("#jwzjDiv").hide();
			}
		}

		//根据姓名自动查询相关信息
		function setDefaults() {
			//清空
			$("#idNumber").val('');
			$.ajax({
				type:"post",
				url:"${ctx}/personnel/personnelBase/getPersonByName",
				data:{name:$("#name").val()},
				dataType:"json",
				success:function(data){
					if(data.success==true && data.result.length==1){
						$("#idNumber").val(data.result[0].idNumber);
					}else if(data.success==true && data.result.length>1){
						var html = '<table id="contentTable" class="table table-striped table-bordered table-condensed">';
						html += '<thead><tr><th></th><th>姓名</th><th>身份证号</th></tr></thead>';
						html += '<tbody>';
						for(var i=0; i< data.result.length; i++) {
							html += '<tr><td><input type="radio" name="selected" value="'+i+'"></td>';
							html += '<td>'+data.result[i].name+'</td>';
							html += '<td>'+data.result[i].idNumber+'</td>';
							html += '</tr>';
						}

						html +=	'</tbody>';
						html +=	'</table>';
						var submit = function (v, h, f) {
							$("#idNumber").val(data.result[f.selected].idNumber);
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
		<li><a href="${ctx}/affair/affairRelationshipTransfer/">系统内组织关系移交转接列表</a></li>
		<li class="active"><a href="${ctx}/affair/affairRelationshipTransfer/form?id=${affairRelationshipTransfer.id}">系统内组织关系移交转接<shiro:hasPermission name="affair:affairRelationshipTransfer:edit">${not empty affairRelationshipTransfer.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairRelationshipTransfer:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairRelationshipTransfer" action="${ctx}/affair/affairRelationshipTransfer/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input id="name" path="name" htmlEscape="false" class="input-xlarge required" onkeydown="if(event.keyCode==13){setDefaults();return false;}"/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">警号：</label>
			<div class="controls">
				<form:input path="policeNo" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input id="idNumber" path="idNumber" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转移类型：</label>
			<div class="controls">
				<form:select path="transferType" class="input-xlarge required"  onchange="showOpinionDiv(this)">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_transfer_type')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">原组织：</label>
			<div class="controls">
				<sys:treeselect id="oldOrganization" name="oldOrganizationId" value="${affairRelationshipTransfer.oldOrganizationId}" labelName="oldOrganization" labelValue="${affairRelationshipTransfer.oldOrganization}"
								title="原组织" url="/affair/affairGeneralSituation/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div id="jwzjDiv" class="control-group" style="display: none">
			<label class="control-label">申请转入组织：</label>
			<div class="controls">
				<form:input path="nowOrganization" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div id="ptDiv" class="control-group">
			<label class="control-label">申请转入组织：</label>
			<div class="controls">
				<sys:treeselect id="nowOrganization" name="nowOrganizationId" value="${affairRelationshipTransfer.nowOrganizationId}" labelName="nowOrganization" labelValue="${affairRelationshipTransfer.nowOrganization}"
								title="申请转入组织" url="/affair/affairGeneralSituation/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" isAll="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>



		<div class="control-group">
			<label class="control-label">申请时间：</label>
			<div class="controls">
				<input name="applyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairRelationshipTransfer.applyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="form-actions">
			<%--保存之后开始进入审核，不能再修改--%>
			<c:if test="${empty affairRelationshipTransfer.id}">
				<shiro:hasPermission name="affair:affairRelationshipTransfer:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>