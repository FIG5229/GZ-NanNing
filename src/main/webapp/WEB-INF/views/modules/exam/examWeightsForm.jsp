<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>权重管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			showKpChuSel();
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
		function showKpChuSel() {
			var kpType = $("#kpType").val();
			if(kpType == '3' || kpType == '4'){
				$("#kpChuDiv").show();
			}else{
				$("#kpChuDiv").hide();
				//$("#kpChu").val("");
				$("#kpChu")[0][0].selected = true;
				$("#s2id_kpChu .select2-chosen").text("");
			}
		}
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="examWeights" action="${ctx}/exam/examWeights/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="department" name="departmentId" value="${examWeights.departmentId}" labelName="department" labelValue="${examWeights.department}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" checked="true" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考评类别：</label>
			<div class="controls">
				<form:select path="kpType" class="input-small required" onchange="showKpChuSel()">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('exam_weights_kpType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="kpChuDiv" style="display: none">
			<label class="control-label">公安处：</label>
			<div class="controls">
				<form:select path="kpChu" class="input-small">
					<form:option value=""></form:option>
					<form:option value=""></form:option>
					<form:option value="34">南宁公安处</form:option>
					<form:option value="95">柳州公安处</form:option>
					<form:option value="156">北海公安处</form:option>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">工作权重关联表：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>类别</th>
								<th>工作名称</th>
								<th>权重分数</th>
								<shiro:hasPermission name="exam:examWeights:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="examWeightsMainList">
						</tbody>
						<shiro:hasPermission name="exam:examWeights:edit"><tfoot>
							<tr><td colspan="4"><a href="javascript:" onclick="addRow('#examWeightsMainList', examWeightsMainRowIdx, examWeightsMainTpl);examWeightsMainRowIdx = examWeightsMainRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="examWeightsMainTpl">//<!--
						<tr id="examWeightsMainList{{idx}}">
							<td class="hide">
								<input id="examWeightsMainList{{idx}}_id" name="examWeightsMainList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="examWeightsMainList{{idx}}_delFlag" name="examWeightsMainList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<select id="examWeightsMainList{{idx}}_workNameType" name="examWeightsMainList[{{idx}}].workNameType" data-value="{{row.workNameType}}" class="input-small ">
									<%--<option value=""></option>--%>
									<c:forEach items="${fns:getDictList('weight_work_type')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<select id="examWeightsMainList{{idx}}_workName" name="examWeightsMainList[{{idx}}].workName" data-value="{{row.workName}}" class="input-medium">
									<option value=""></option>
									<option value=""></option>
									<c:forEach items="${fns:getDictList('exam_weigths')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
								<script>
									<%--子表中下拉框搜索--%>
									$("#examWeightsMainList{{idx}}_workName").select2();
									$("#examWeightsMainList{{idx}}_workName").select2('val','{{row.workName}}');
								</script>
							</td>
							<td>
								<input id="examWeightsMainList{{idx}}_weights" name="examWeightsMainList[{{idx}}].weights" type="text" value="{{row.weights}}" class="input-small " onkeyup="value=value.replace(/[^1234567890.]+/g,'')"/>
							</td>
							<shiro:hasPermission name="exam:examWeights:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#examWeightsMainList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var examWeightsMainRowIdx = 0, examWeightsMainTpl = $("#examWeightsMainTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(examWeights.examWeightsMainList)};
							for (var i=0; i<data.length; i++){
								addRow('#examWeightsMainList', examWeightsMainRowIdx, examWeightsMainTpl, data[i]);
								examWeightsMainRowIdx = examWeightsMainRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="exam:examWeights:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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