<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查(借)阅审批管理</title>
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
	<br/>
	<form:form id="inputForm" modelAttribute="affairArchiveApproval" action="${ctx}/affair/affairArchiveApproval/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">查档事由：</label>
			<div class="controls">
				<form:input path="reason" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">查档内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">查档单位意见内容：</label>
			<div class="controls">
				<form:input path="unitOpinion" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">查档单位负责人：</label>
			<div class="controls">
				<form:input path="unitPerson" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">查档单位负责人签字日期：</label>
			<div class="controls">
				<input name="unitDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairArchiveApproval.unitDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主管部门意见内容：</label>
			<div class="controls">
				<form:input path="depOpinion" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主管部门负责人：</label>
			<div class="controls">
				<form:input path="depPerson" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主管部门负责人签字日期：</label>
			<div class="controls">
				<input name="depDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairArchiveApproval.depDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">领导批示：</label>
			<div class="controls">
				<form:input path="leaderInstruction" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">干部档案被查档人员表：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>被查档对象身份证号</th>
								<th>被查档对象姓名</th>
								<th>被查档对象单位</th>
								<th>被查档对象职务</th>
								<th>被查档对象政治面貌</th>
								<shiro:hasPermission name="affair:affairArchiveApproval:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="affairCdObjectList">
						</tbody>
						<shiro:hasPermission name="affair:affairArchiveApproval:edit"><tfoot>
							<tr><td colspan="7"><a href="javascript:" onclick="addRow('#affairCdObjectList', affairCdObjectRowIdx, affairCdObjectTpl);affairCdObjectRowIdx = affairCdObjectRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="affairCdObjectTpl">//<!--
						<tr id="affairCdObjectList{{idx}}">
							<td class="hide">
								<input id="affairCdObjectList{{idx}}_id" name="affairCdObjectList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="affairCdObjectList{{idx}}_delFlag" name="affairCdObjectList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="affairCdObjectList{{idx}}_idNumber" name="affairCdObjectList[{{idx}}].idNumber" type="text" value="{{row.idNumber}}" class="input-small "/>
							</td>
							<td>
								<input id="affairCdObjectList{{idx}}_name" name="affairCdObjectList[{{idx}}].name" type="text" value="{{row.name}}" class="input-small required"/>

							</td>
							<td>
								<sys:treeselect id="affairCdObjectList{{idx}}_unit" name="affairCdObjectList[{{idx}}].unitId" value="{{row.unitId}}" labelName="affairCdObjectList[{{idx}}].unit" labelValue="{{row.unit}}"
									title="部门" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" dataMsgRequired="必填信息"/>
							</td>
							<td>
								<select id="affairCdObjectList{{idx}}_job" name="affairCdObjectList[{{idx}}].job" data-value="{{row.job}}" class="input-small required">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('cd_position')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<select id="affairCdObjectList{{idx}}_face" name="affairCdObjectList[{{idx}}].face" data-value="{{row.face}}" class="input-small required">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('zzmm')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<shiro:hasPermission name="affair:affairArchiveApproval:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#affairCdObjectList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var affairCdObjectRowIdx = 0, affairCdObjectTpl = $("#affairCdObjectTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(affairArchiveApproval.affairCdObjectList)};
							for (var i=0; i<data.length; i++){
								addRow('#affairCdObjectList', affairCdObjectRowIdx, affairCdObjectTpl, data[i]);
								affairCdObjectRowIdx = affairCdObjectRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">干部档案查档人员表：</label>
				<div class="controls">
					<table id="contentTable1" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>查档人员身份证号</th>
								<th>查档人员姓名</th>
								<th>查档人员单位</th>
								<th>查档人员职务</th>
								<th>查档人员政治面貌</th>
								<shiro:hasPermission name="affair:affairArchiveApproval:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="affairCdPersonList">
						</tbody>
						<shiro:hasPermission name="affair:affairArchiveApproval:edit"><tfoot>
							<tr><td colspan="7"><a href="javascript:" onclick="addRow('#affairCdPersonList', affairCdPersonRowIdx, affairCdPersonTpl);affairCdPersonRowIdx = affairCdPersonRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="affairCdPersonTpl">//<!--
						<tr id="affairCdPersonList{{idx}}">
							<td class="hide">
								<input id="affairCdPersonList{{idx}}_id" name="affairCdPersonList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="affairCdPersonList{{idx}}_delFlag" name="affairCdPersonList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="affairCdPersonList{{idx}}_idNumber" name="affairCdPersonList[{{idx}}].idNumber" type="text" value="{{row.idNumber}}" class="input-small "/>
							</td>
							<td>
								<input id="affairCdPersonList{{idx}}_name" name="affairCdPersonList[{{idx}}].name" type="text" value="{{row.name}}" class="input-small required"/>

							</td>
							<td>
								<sys:treeselect id="affairCdPersonList{{idx}}_unit" name="affairCdPersonList[{{idx}}].unitId" value="{{row.unitId}}" labelName="affairCdPersonList[{{idx}}].unit" labelValue="{{row.unit}}"
									title="部门" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" dataMsgRequired="必填信息"/>

							</td>
							<td>
								<select id="affairCdPersonList{{idx}}_job" name="affairCdPersonList[{{idx}}].job" data-value="{{row.job}}" class="input-small required">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('cd_position')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
									<span class="help-inline"><font color="red">*</font> </span>
								</select>
							</td>
							<td>
								<select id="affairCdPersonList{{idx}}_face" name="affairCdPersonList[{{idx}}].face" data-value="{{row.face}}" class="input-small required">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('zzmm')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
									<span class="help-inline"><font color="red">*</font> </span>
								</select>
							</td>
							<shiro:hasPermission name="affair:affairArchiveApproval:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#affairCdPersonList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var affairCdPersonRowIdx = 0, affairCdPersonTpl = $("#affairCdPersonTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(affairArchiveApproval.affairCdPersonList)};
							for (var i=0; i<data.length; i++){
								addRow('#affairCdPersonList', affairCdPersonRowIdx, affairCdPersonTpl, data[i]);
								affairCdPersonRowIdx = affairCdPersonRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairArchiveApproval:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
		</div>
	</form:form>
	<script>
		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</body>
</html>