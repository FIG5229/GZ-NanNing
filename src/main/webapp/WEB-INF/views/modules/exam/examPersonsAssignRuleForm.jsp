<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考评人员分配规则管理管理</title>
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
		<li><a href="${ctx}/exam/examPersonsAssignRule/">考评人员分配规则管理列表</a></li>
		<li class="active"><a href="${ctx}/exam/examPersonsAssignRule/form?id=${examPersonsAssignRule.id}">考评人员分配规则管理<shiro:hasPermission name="exam:examPersonsAssignRule:edit">${not empty examPersonsAssignRule.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="exam:examPersonsAssignRule:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="examPersonsAssignRule" action="${ctx}/exam/examPersonsAssignRule/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">简称：</label>
			<div class="controls">
				<form:input path="abbreviation" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<!--
		<div class="control-group">
			<label class="control-label">被考评对象类别：</label>
			<div class="controls">
				<form:select path="objType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('obj_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		-->
		<div class="control-group">
			<label class="control-label">考评类别：</label>
			<div class="controls">
				<form:select path="kpType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('kp_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<!--
		<div class="control-group">
			<label class="control-label">考评周期：</label>
			<div class="controls">
				<form:select path="cycle" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('cycle')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否启用：</label>
			<div class="controls">
				<form:select path="isEnable" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>-->
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unitId" name="unitId" value="${examPersonsAssignRule.unitId}" labelName="unitName" labelValue="${examPersonsAssignRule.unitName}"
								title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="false" checked="true"/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">分配规则：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>考核部门</th>
								<th>自评人</th>
								<th>考评人</th>
								<shiro:hasPermission name="exam:examPersonsAssignRule:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="examPersonsAssignRuleChildList">
						</tbody>
						<shiro:hasPermission name="exam:examPersonsAssignRule:edit"><tfoot>
							<tr><td colspan="5"><a href="javascript:" onclick="addRow('#examPersonsAssignRuleChildList', examPersonsAssignRuleChildRowIdx, examPersonsAssignRuleChildTpl);examPersonsAssignRuleChildRowIdx = examPersonsAssignRuleChildRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="examPersonsAssignRuleChildTpl">//<!--
						<tr id="examPersonsAssignRuleChildList{{idx}}">
							<td class="hide">
								<input id="examPersonsAssignRuleChildList{{idx}}_id" name="examPersonsAssignRuleChildList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="examPersonsAssignRuleChildList{{idx}}_delFlag" name="examPersonsAssignRuleChildList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="examPersonsAssignRuleChildList{{idx}}_examDepart" name="examPersonsAssignRuleChildList[{{idx}}].examDepart" type="text" value="{{row.examDepart}}" class="input-small "/>
							</td>
							<td>
								<sys:treeselect id="examPersonsAssignRuleChildList{{idx}}_selfPersonIds" name="examPersonsAssignRuleChildList[{{idx}}].selfPersonIds" value="{{row.selfPersonIds}}" labelName="examPersonsAssignRuleChildList[{{idx}}].selfPersonNames" labelValue="{{row.selfPersonNames}}"
									title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" checked="true"/>
							</td>
							<td>
								<sys:treeselect id="examPersonsAssignRuleChildList{{idx}}_examPersonIds" name="examPersonsAssignRuleChildList[{{idx}}].examPersonIds" value="{{row.examPersonIds}}" labelName="examPersonsAssignRuleChildList[{{idx}}].examPersonNames" labelValue="{{row.examPersonNames}}"
									title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
							</td>
							<shiro:hasPermission name="exam:examPersonsAssignRule:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#examPersonsAssignRuleChildList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var examPersonsAssignRuleChildRowIdx = 0, examPersonsAssignRuleChildTpl = $("#examPersonsAssignRuleChildTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(examPersonsAssignRule.examPersonsAssignRuleChildList)};
							for (var i=0; i<data.length; i++){
								addRow('#examPersonsAssignRuleChildList', examPersonsAssignRuleChildRowIdx, examPersonsAssignRuleChildTpl, data[i]);
								examPersonsAssignRuleChildRowIdx = examPersonsAssignRuleChildRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="exam:examPersonsAssignRule:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>