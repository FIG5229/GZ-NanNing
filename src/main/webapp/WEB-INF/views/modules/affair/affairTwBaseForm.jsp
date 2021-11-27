<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团委（支部）基本信息管理</title>
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
<br>
	<form:form id="inputForm" modelAttribute="affairTwBase" action="${ctx}/affair/affairTwBase/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">组织名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所辖单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairTwBase.unitId}" labelName="unit" labelValue="${affairTwBase.unit}"
					title="所辖单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" checked="true" dataMsgRequired="必填信息" isAll="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属上级团组织：</label>
			<div class="controls">
				<form:select path="orgName" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_org_name')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上一次换届改选时间：</label>
			<div class="controls">
				<form:input path="hjDate" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">团（支）委人数：</label>
			<div class="controls">
				<form:input path="num" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">分工：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>角色</th>
								<th>姓名</th>
								<th>职责</th>
								<shiro:hasPermission name="affair:affairTwBase:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="affairTwBaseSignList">
						</tbody>
						<shiro:hasPermission name="affair:affairTwBase:edit"><tfoot>
							<tr><td colspan="4"><a href="javascript:" onclick="addRow('#affairTwBaseSignList', affairTwBaseSignRowIdx, affairTwBaseSignTpl);affairTwBaseSignRowIdx = affairTwBaseSignRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="affairTwBaseSignTpl">//<!--
						<tr id="affairTwBaseSignList{{idx}}">
							<td class="hide">
								<input id="affairTwBaseSignList{{idx}}_id" name="affairTwBaseSignList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="affairTwBaseSignList{{idx}}_delFlag" name="affairTwBaseSignList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<select id="affairTwBaseSignList{{idx}}_role" name="affairTwBaseSignList[{{idx}}].role" data-value="{{row.role}}" class="input-small ">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('affair_twrole')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="affairTwBaseSignList{{idx}}_name" name="affairTwBaseSignList[{{idx}}].name" type="text" value="{{row.name}}" class="input-small "/>
							</td>
							<td>
								<input id="affairTwBaseSignList{{idx}}_job" name="affairTwBaseSignList[{{idx}}].job" type="text" value="{{row.job}}" class="input-small "/>
							</td>
							<shiro:hasPermission name="affair:affairTwBase:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#affairTwBaseSignList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var affairTwBaseSignRowIdx = 0, affairTwBaseSignTpl = $("#affairTwBaseSignTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(affairTwBase.affairTwBaseSignList)};
							for (var i=0; i<data.length; i++){
								addRow('#affairTwBaseSignList', affairTwBaseSignRowIdx, affairTwBaseSignTpl, data[i]);
								affairTwBaseSignRowIdx = affairTwBaseSignRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" class="input-xlarge "/>
				<span class="help-inline"><font color="red">请按照1-1(公安局-单位序号),2-1-1(公安处-公安处序号-单位序号)，这种格式排序</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairTwBase:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
	<script>
		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</body>
</html>