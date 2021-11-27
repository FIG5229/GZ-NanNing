<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>实弹训练管理</title>
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
			$("#name").keydown(function(e){
				if(e.keyCode == 13){
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
									return true;
								};
								top.$.jBox(html, { title: "输入",width: 600, height: 300, submit: submit });
							}else {
								$.jBox.tip('没有查询到该人名相关信息');
							}
						}
					})
				}
			});
		});
		function save() {
			$("#inputForm").submit();
		}
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="affairTrainCombat" action="${ctx}/affair/affairTrainCombat/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<br>
		<%--<div class="control-group">
			<label class="control-label">用户名：</label>
			<div class="controls">
				<form:input path="userName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">警号：</label>
			<div class="controls">
				<form:input path="number" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">训练日期：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairTrainCombat.date}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input id="name" path="name" htmlEscape="false" class="input-xlarge required "/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input id="idNumber" path="idNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属机构：</label>
			<div class="controls">
				<sys:treeselect id="organizationId" name="organizationId" value="${affairTrainCombat.organizationId}" labelName="organization" labelValue="${affairTrainCombat.organization}"
								title="所属机构" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">项目类别：</label>
			<div class="controls">
				<form:select path="projectType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('project_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>--%>
		<%--<div class="control-group">
			<label class="control-label">警种：</label>
			<div class="controls">
				<form:select path="policeClassification" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('police_classification')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">警衔：</label>
			<div class="controls">
				<form:select path="policeRank" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('police_rank_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					&lt;%&ndash;<form:options items="${fns:getDictList('police_rank')}" itemLabel="label" itemValue="value" htmlEscape="false"/>&ndash;%&gt;
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人员类别：</label>
			<div class="controls">
				<form:select path="personType" class="input-xlarge ">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('person_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">管理类别：</label>
			<div class="controls">
				<form:select path="managementType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('management_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行政职务：</label>
			<div class="controls">
				<form:select path="post" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('administration_post')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职务级别：</label>
			<div class="controls">
				<form:select path="postLevel" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('post_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairTrainCombat.unitId}" labelName="unit" labelValue="${affairTrainCombat.unit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">域：</label>
			<div class="controls">
				<sys:treeselect id="region" name="regionId" value="${affairTrainCombat.regionId}" labelName="region" labelValue="${affairTrainCombat.region}"
					title="区域" url="/sys/area/treeData" cssClass="required"  notAllowSelectParent="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">子弹数量：</label>
			<div class="controls">
				<form:input path="count" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">平均单发环数：</label>
			<div class="controls">
				<form:input path="average" htmlEscape="false" class="input-xlarge number required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairTrainCombat:edit"><input id="btnSubmit" class="btn btn-primary" value="保 存" onclick="save()" />&nbsp;&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
	<script>
		if ("success" == "${saveResult}") {
			parent.$.jBox.close();
		}
	</script>
</body>

</html>