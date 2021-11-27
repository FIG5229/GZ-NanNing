<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>传递单管理</title>
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
	<form:form id="inputForm" modelAttribute="affairTransmitForm" action="${ctx}/affair/affairTransmitForm/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">经办人：</label>
			<div class="controls">
				<form:input path="handler" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经办日期：</label>
			<div class="controls">
				<input name="handleDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairTransmitForm.handleDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">字：</label>
			<div class="controls">
				<form:input path="handleZi" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">第号：</label>
			<div class="controls">
				<form:input path="handleDh" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">人员名单：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>姓名</th>
								<th>单位</th>
								<th>职务</th>
								<th>转递原因</th>
								<th>档案材料（份）</th>
								<shiro:hasPermission name="affair:affairTransmitForm:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="affairTransmitPersonList">
						</tbody>
						<shiro:hasPermission name="affair:affairTransmitForm:edit"><tfoot>
							<tr><td colspan="9"><a href="javascript:" onclick="addRow('#affairTransmitPersonList', affairTransmitPersonRowIdx, affairTransmitPersonTpl);affairTransmitPersonRowIdx = affairTransmitPersonRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="affairTransmitPersonTpl">//<!--
						<tr id="affairTransmitPersonList{{idx}}">
							<td class="hide">
								<input id="affairTransmitPersonList{{idx}}_id" name="affairTransmitPersonList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="affairTransmitPersonList{{idx}}_delFlag" name="affairTransmitPersonList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="affairTransmitPersonList{{idx}}_name" name="affairTransmitPersonList[{{idx}}].name" type="text" value="{{row.name}}" class="input-small required"/>
							</td>
							<td>
								<input id="affairTransmitPersonList{{idx}}_unit" name="affairTransmitPersonList[{{idx}}].unit" type="text" value="{{row.unit}}" class="input-small required"/>
							</td>
							<td>
								<input id="affairTransmitPersonList{{idx}}_job" name="affairTransmitPersonList[{{idx}}].job" type="text" value="{{row.job}}" class="input-small required"/>
							</td>
							<td>
								<textarea id="affairTransmitPersonList{{idx}}_reason" name="affairTransmitPersonList[{{idx}}].reason" rows="4" class="input-small required">{{row.reason}}</textarea>
							</td>
							<td>
								<input id="affairTransmitPersonList{{idx}}_num" name="affairTransmitPersonList[{{idx}}].num" type="text" value="{{row.num}}" class="input-small  digits required"/>
							</td>
							<shiro:hasPermission name="affair:affairTransmitForm:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#affairTransmitPersonList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var affairTransmitPersonRowIdx = 0, affairTransmitPersonTpl = $("#affairTransmitPersonTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(affairTransmitForm.affairTransmitPersonList)};
							for (var i=0; i<data.length; i++){
								addRow('#affairTransmitPersonList', affairTransmitPersonRowIdx, affairTransmitPersonTpl, data[i]);
								affairTransmitPersonRowIdx = affairTransmitPersonRowIdx + 1;
							}
						});
					</script>

				</div>
			</div>
		<div class="control-group">
			<label class="control-label">接收人：</label>
			<div class="controls">
				<form:input path="receiver" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收件日期：</label>
			<div class="controls">
				<input name="receiveDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="<fmt:formatDate value="${affairTransmitForm.receiveDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收件机关：</label>
			<div class="controls">
				<form:input path="receiveOrg" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">字：</label>
			<div class="controls">
				<form:input path="receiveZi" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">第号：</label>
			<div class="controls">
				<form:input path="receiveDh" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">档案正本数量：</label>
			<div class="controls">
				<form:input path="zNum" htmlEscape="false" class="input-xlarge  digits required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">档案副本数量：</label>
			<div class="controls">
				<form:input path="fNum" htmlEscape="false" class="input-xlarge  digits required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">材料数量：</label>
			<div class="controls">
				<form:input path="materialNum" htmlEscape="false" class="input-xlarge  digits required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairTransmitForm:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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