<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考评标准模板管理</title>
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
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/exam/examStandardTemplateDefine/">考评标准模板格式列表</a></li>
		<li class="active"><a href="${ctx}/exam/examStandardTemplateDefine/form?id=${examStandardTemplateDefine.id}">考评标准模板格式<shiro:hasPermission name="exam:examStandardTemplateDefine:edit">${not empty examStandardTemplateDefine.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="exam:examStandardTemplateDefine:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="examStandardTemplateDefine" action="${ctx}/exam/examStandardTemplateDefine/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">考评周期：</label>
			<div class="controls">
				<form:select path="cycle" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('exam_cycle')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">被考评对象类别：</label>
			<div class="controls">
				<form:select path="objectCategory" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('exam_object_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考评类别：</label>
			<div class="controls">
				<form:select path="kpType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('kp_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始行号：</label>
			<div class="controls">
				<form:input path="startrowNum" htmlEscape="false" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
<%--		<div class="control-group">
			<label class="control-label">结束行号：</label>
			<div class="controls">
				<form:input path="endrowNum" htmlEscape="false" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
			<div class="control-group">
				<label class="control-label">考评标准模板项定义表：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>列名称</th>
								<th>列类型</th>
								<th>列顺序号</th>
								<shiro:hasPermission name="exam:examStandardTemplateDefine:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="examStandardTemplateItemDefineList">
						</tbody>
						<shiro:hasPermission name="exam:examStandardTemplateDefine:edit"><tfoot>
							<tr><td colspan="5"><a href="javascript:" onclick="addRow('#examStandardTemplateItemDefineList', examStandardTemplateItemDefineRowIdx, examStandardTemplateItemDefineTpl);examStandardTemplateItemDefineRowIdx = examStandardTemplateItemDefineRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="examStandardTemplateItemDefineTpl">//<!--
						<tr id="examStandardTemplateItemDefineList{{idx}}">
							<td class="hide">
								<input id="examStandardTemplateItemDefineList{{idx}}_id" name="examStandardTemplateItemDefineList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="examStandardTemplateItemDefineList{{idx}}_delFlag" name="examStandardTemplateItemDefineList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="examStandardTemplateItemDefineList{{idx}}_columnName" name="examStandardTemplateItemDefineList[{{idx}}].columnName" type="text" value="{{row.columnName}}" class="input-small required"/>
							</td>
							<td>
								<select id="examStandardTemplateItemDefineList{{idx}}_columnType" name="examStandardTemplateItemDefineList[{{idx}}].columnType" data-value="{{row.columnType}}" class="input-small required">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('column_type')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="examStandardTemplateItemDefineList{{idx}}_columnOrder" name="examStandardTemplateItemDefineList[{{idx}}].columnOrder" type="text" value="{{row.columnOrder}}" class="input-small required digits"/>
							</td>
							<shiro:hasPermission name="exam:examStandardTemplateDefine:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#examStandardTemplateItemDefineList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var examStandardTemplateItemDefineRowIdx = 0, examStandardTemplateItemDefineTpl = $("#examStandardTemplateItemDefineTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(examStandardTemplateDefine.examStandardTemplateItemDefineList)};
							for (var i=0; i<data.length; i++){
								addRow('#examStandardTemplateItemDefineList', examStandardTemplateItemDefineRowIdx, examStandardTemplateItemDefineTpl, data[i]);
								examStandardTemplateItemDefineRowIdx = examStandardTemplateItemDefineRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div>
			（备注：列顺序号从0开始）
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="exam:examStandardTemplateDefine:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script>
		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</body>
</html>