<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>其他数据管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">

		//根据使用模板的值渲染选择项所对应的内容
		function getChooseOptionsList(index) {
			var list = [];
			var content = "";
			$.ajax({
				url: "${ctx}/exam/examCheck/findChooseOptionsByUseModel2",
				dataType: "json",
				async: true,//请求是否异步，默认为异步
				data: {useModel: $("#examOtherDataList" + index + "_useModel").val()},
				type: "POST",
				success: function (res) {
					if (res.result != null) {
						list = res.result;
						content = '';
						list.forEach((item, index) => {
							content += '<option value=' + item.optionId + '>' + item.itemValue + '</option>';
					})
						$("#examOtherDataList" + index + "_chooseOptions").empty();
						$("#examOtherDataList" + index + "_chooseOptions").append(content);
						$("#examOtherDataList" + index + "_chooseOptions").val('');
						$("#examOtherDataList" + index + "_chooseOptions").siblings('.select2-container').find('.select2-chosen').text($("#examOtherDataList" + index + "_chooseOptions").find("option:selected").text());
					}
				}
			});
		};
		function getChooseStandardList(index) {
			var list = [];
			var content = "";
			$.ajax({
				url: "${ctx}/exam/examStandardBaseInfo/getStandardListBeta",
				dataType: "json",
				async: true,//请求是否异步，默认为异步
				data: {kpType: $("#examOtherDataList" + index + "_type").val()},
				type: "POST",
				success: function (res) {
					if (res.result != null) {
						list = res.result;
						content = '';
						list.forEach((item, index) => {
							content += '<option value=' + item.id + '>' + item.name + '</option>';
					})
						$("#examOtherDataList" + index + "_useModel").empty();
						$("#examOtherDataList" + index + "_useModel").append(content);
						$("#examOtherDataList" + index + "_useModel").val('');
						$("#examOtherDataList" + index + "_useModel").siblings('.select2-container').find('.select2-chosen').text($("#examOtherDataList" + index + "_useModel").find("option:selected").text());
						getChooseOptionsList(0);
						getOptionsDetail(0);
					}
				}
			});
		};

		function getOptionsDetail(index) {
			debugger
			$.ajax({
				url: "${ctx}/exam/examStandardTemplateDefine/getOptionsDetail",
				dataType: "json",
				async: true,//请求是否异步，默认为异步
				data: {optionId: $("#examOtherDataList" + index + "_chooseOptions").val(),standardId:$("#examOtherDataList" + index + "_useModel").val()},
				type: "POST",
				success: function (res) {
					if (res.result != null) {
						$("#score"+index).val(res.result);
					}
				}
			});
		}

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
			<c:forEach varStatus="status" items="${examOtherData.examOtherDataList}" var="examOtherDataList">
				getChooseStandardList('${status.index+1}');
			</c:forEach>

		});

		function addRow(list, idx, tpl, row) {
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list + idx).find("select").each(function () {
				$(this).val($(this).attr("data-value"));
			});
			$(list + idx).find("input[type='checkbox'], input[type='radio']").each(function () {
				var ss = $(this).attr("data-value").split(',');
				for (var i = 0; i < ss.length; i++) {
					if ($(this).val() == ss[i]) {
						$(this).attr("checked", "checked");
					}
				}
			});
		}

		function delRow(obj, prefix) {
			var id = $(prefix + "_id");
			var delFlag = $(prefix + "_delFlag");
			if (id.val() == "") {
				$(obj).parent().parent().remove();
			} else if (delFlag.val() == "0") {
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			} else if (delFlag.val() == "1") {
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}


		/*//根据使用模板的值渲染选择项所对应的内容
		function getChooseOptionsList() {
			var list = [];
			var content = "";
			loading("加载选择项")
			$.ajax({
				url: "${ctx}/exam/examOtherData/findChooseOptionsByUseModel",
				dataType: "json",
				async: true,//请求是否异步，默认为异步
				data: {useModel: $("#useModel").val()},
				type: "POST",
				success: function (res) {
					if (res.result != null) {
						list = res.result;
						content = '';
						list.forEach((item, index) => {
							content += '<option value=' + item + '>' + item + '</option>';
						});
						$("#chooseOptions").append(content);
						$("#chooseOptions").val('');
						$("#chooseOptions").siblings('.select2-container').find('.select2-chosen').text($("#chooseOptions").find("option:selected").text());
					}
					closeLoading();
				},
				error:function (d) {
					$.jBox.tip("发生错误")
				}
			});
		};*/
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li><a href="${ctx}/exam/examOtherData/">其他数据列表</a></li>
		<li class="active"><a href="${ctx}/exam/examOtherData/form?id=${examOtherData.id}">其他数据<shiro:hasPermission name="exam:examOtherData:edit">${not empty examOtherData.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="exam:examOtherData:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="examOtherData" action="${ctx}/exam/examOtherData/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
<%--		<div class="control-group">
			<label class="control-label">检查时间：</label>
			<div class="controls">
				<input name="checkDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${examOtherData.checkDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核单位：</label>
			<div class="controls">
				<sys:treeselect id="checkUnitId" name="checkUnitId" value="${examOtherData.checkUnitId}" labelName="checkUnit" labelValue="${examOtherData.checkUnit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">检查人：</label>
			<div class="controls">
				<sys:treeselect id="checkPerson" name="checkPersonId" value="${examOtherData.checkPersonId}" labelName="checkPerson" labelValue="${examOtherData.checkPerson}"
								title="检查人" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true" cssStyle="width:300px;" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<c:forEach items="${examOtherData.examOtherDataList}" var="child" varStatus="status">
			<input id="chooseOptions${status.index}" name="chooseOptions${status.index}" type="hidden"
				   value="${child.chooseOptions}"/>
		</c:forEach>

		<div class="control-group">
<%--			<label class="control-label">问题录入：</label>--%>
<%--			<div class="controls">--%>
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
					<tr>
						<th class="hide"></th>
						<th>时间</th>
						<th>层次<span class="help-inline"><%--<font color="red">*</font>--%> </span></th>
						<th>使用模板<span class="help-inline"><%--<font color="red">*</font>--%> </span></th>
						<th>选择项<span class="help-inline"><%--<font color="red">*</font> --%></span></th>
						<th>分数</th>
						<th>责任单位<span class="help-inline"><font color="red">*</font> </span></th>
						<th>责任单位扣分<span class="help-inline"><!--<font color="red">*</font>--> </span></th>
						<th>责任领导</th>
						<th>责任领导扣分<span class="help-inline"><!--<font color="red">*</font>--></span></th>
						<th>责任人</th>
						<th>责任人扣分<span class="help-inline"><!--<font color="red">*</font>--></span></th>
						<!--
						<th>绩效考评标准</th>-->
						<th>详细情况<span class="help-inline"><font color="red">*</font> </span></th>
						<th>备注</th>
						<shiro:hasPermission name="exam:examOtherData:edit">
							<th width="10">&nbsp;</th>
						</shiro:hasPermission>
					</tr>
					</thead>
					<tbody id="examOtherDataList">
					</tbody>
					<shiro:hasPermission name="exam:examOtherData:edit">
						<tfoot>
						<tr>
							<td colspan="13"><a href="javascript:"
												onclick="addRow('#examOtherDataList', examOtherDataChildRowIdx, examOtherDataChildTpl);examOtherDataChildRowIdx = examOtherDataChildRowIdx + 1;"
												class="btn">新增</a></td>
						</tr>
						</tfoot>
					</shiro:hasPermission>
				</table>
				<script type="text/template" id="examOtherDataChildTpl">//<!--
						<tr id="examOtherDataList{{idx}}">
							<td class="hide">
								<input id="examOtherDataList{{idx}}_id" name="examOtherDataList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="examOtherDataList{{idx}}_delFlag" name="examOtherDataList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="examOtherDataList{{idx}}_time" name="examOtherDataList[{{idx}}].time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
									value="{{row.time}}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
							</td>
							<td>
								<select id="examOtherDataList{{idx}}_type" name="examOtherDataList[{{idx}}].type" data-value="{{row.type}}" class="input-small " onchange="getChooseStandardList({{idx}})" style="width: 150px;">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('kp_type')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
								<script>
									<%--子表中下拉框搜索--%>
									$("#examOtherDataList{{idx}}_type").select2();
									$("#examOtherDataList{{idx}}_type").select2('val','{{row.type}}');
								</script>
							</td>
							<td>
								<select id="examOtherDataList{{idx}}_useModel" name="examOtherDataList[{{idx}}].useModel" data-value="{{row.useModel}}" class="input-small " onchange="getChooseOptionsList({{idx}})" style="width: 250px;">
									<option value=""></option>
									<c:forEach items="${templateFile}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
								<script>
									<%--子表中下拉框搜索--%>
									$("#examOtherDataList{{idx}}_useModel").select2();
									$("#examOtherDataList{{idx}}_useModel").select2('val','{{row.useModel}}');
								</script>
							</td>
							<td>
								<select id="examOtherDataList{{idx}}_chooseOptions" name="examOtherDataList[{{idx}}].chooseOptions" data-value="{{row.chooseOptions}}" onchange="getOptionsDetail({{idx}})" class="input-small" style="width: 250px;">
									<option value=""></option>
								</select>
								<script>
									<%--子表中下拉框搜索--%>
									$("#examOtherDataList{{idx}}_chooseOptions").select2();
									$("#examOtherDataList{{idx}}_chooseOptions").select2('val','{{row.chooseOptions}}');
								</script>
							</td>
							<td><textarea rows="3" id="score{{idx}}" value="" disabled/></td>
							<td>
								<sys:treeselect id="examOtherDataList{{idx}}_dutyUnit" name="examOtherDataList[{{idx}}].dutyUnitId" value="{{row.dutyUnitId}}" labelName="examOtherDataList[{{idx}}].dutyUnit" labelValue="{{row.dutyUnit}}"
									title="责任单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true"  dataMsgRequired="必填信息"/>
							</td>
							<td>
								<input id="examOtherDataList{{idx}}_dutyUnitScore" name="examOtherDataList[{{idx}}].dutyUnitScore" type="text" value="{{row.dutyUnitScore}}" class="input-small number"/>
							</td>
							<td>
								<sys:treeselect id="examOtherDataList{{idx}}_dutyLeader" name="examOtherDataList[{{idx}}].dutyLeaderId" value="{{row.dutyLeaderId}}" labelName="examOtherDataList[{{idx}}].dutyLeader" labelValue="{{row.dutyLeader}}"
									title="责任领导" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
							</td>
							<td>
								<input id="examOtherDataList{{idx}}_dutyLeaderScore" name="examOtherDataList[{{idx}}].dutyLeaderScore" type="text" value="{{row.dutyLeaderScore}}" class="input-small number"/>
							</td>
							<td>
								<sys:treeselect id="examOtherDataList{{idx}}_dutyPerson" name="examOtherDataList[{{idx}}].dutyPersonId" value="{{row.dutyPersonId}}" labelName="examOtherDataList[{{idx}}].dutyPerson" labelValue="{{row.dutyPerson}}"
									title="责任人" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
							</td>
							<td>
								<input id="examOtherDataList{{idx}}_dutyPersonScore" name="examOtherDataList[{{idx}}].dutyPersonScore" type="text" value="{{row.dutyPersonScore}}" class="input-small number"/>
							</td>
							<!--
							<td>
								<textarea id="examOtherDataList{{idx}}_testStandart" name="examOtherDataList[{{idx}}].testStandart" rows="4" class="input-small ">{{row.testStandart}}</textarea>
							</td>-->
				<td>
					<textarea id="examOtherDataList{{idx}}_scortSituation" name="examOtherDataList[{{idx}}].scortSituation" rows="4" class="input-small required">{{row.scortSituation}}</textarea>
				</td>
				<td>
					<input id="examOtherDataList{{idx}}_remark" name="examOtherDataList[{{idx}}].remark" type="text" value="{{row.remark}}" class="input-small "/>
				</td>
					<shiro:hasPermission name="exam:examOtherData:edit"><td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#examOtherDataList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td></shiro:hasPermission>
				</tr>//-->
				</script>
				<script type="text/javascript">
					var examOtherDataChildRowIdx = 0,
							examOtherDataChildTpl = $("#examOtherDataChildTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
					$(document).ready(function () {
						var data = ${fns:toJson(examOtherData.examOtherDataList)};
						for (var i = 0; i < data.length; i++) {
							addRow('#examOtherDataList', examOtherDataChildRowIdx, examOtherDataChildTpl, data[i]);
							examOtherDataChildRowIdx = examOtherDataChildRowIdx + 1;
						}

						//根据使用模板的值渲染选择项所对应的内容   回显
						function getChooseOptionsListView(chooseOptionsValue, index) {
							var list = [];
							var content = "";
							$.ajax({
								url: "${ctx}/exam/examOtherData/findChooseOptionsByUseModel",
								dataType: "json",
								async: true,//请求是否异步，默认为异步
								data: {useModel: $("#examOtherDataList" + index + "_useModel").val()},
								type: "POST",
								success: function (res) {
									if (res.result != null) {
										list = res.result;
										list.forEach((item, index) => {
											if (chooseOptionsValue == item) {
											$("#examOtherDataList" + index + "_chooseOptions").val(chooseOptionsValue);
											content += '<option value=' + item + ' selected >' + item + '</option>';
										} else {
											content += '<option value=' + item + '>' + item + '</option>';
										}
									})
										$("#examOtherDataList" + index + "_chooseOptions").append(content);
										$("#examOtherDataList" + index + "_chooseOptions").siblings('.select2-container').find('.select2-chosen').text($("#examOtherDataList" + index + "_chooseOptions").find("option:selected").text());
									}
								}
							});
						};
						if (${not empty examOtherData.id}) {
							/*for (var i = 0; i < ${examOtherData.examOtherDataList.size()}; i++) {
								// getChooseOptionsListView($("#chooseOptions" + i).val(), i);
							}*/
						}
					});
				</script>
<%--			</div>--%>
		</div>
		<%--<div class="control-group">
			<label class="control-label">责任单位：</label>
			<div class="controls">
				<sys:treeselect id="dutyUnitId" name="dutyUnitId" value="${examOtherData.dutyUnitId}" labelName="dutyUnit" labelValue="${examOtherData.dutyUnit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">责任领导：</label>
			<div class="controls">
				<sys:treeselect id="dutyLeader" name="dutyLeaderId" value="${examOtherData.dutyLeader}" labelName="dutyLeader" labelValue="${examOtherData.dutyLeader}"
								title="责任领导" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">责任人：</label>
			<div class="controls">
				<sys:treeselect id="dutyPerson" name="dutyPersonId" value="${examOtherData.dutyPersonId}" labelName="dutyPerson" labelValue="${examOtherData.dutyPerson}"
								title="责任人" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用模板：</label>
			<div class="controls">
				<select id="useModel" name="useModel" onchange="getChooseOptionsList()" style="width: 350px;">
					<option value=""></option>
					<c:forEach items="${templateFile}" var="dict">
						<option value="${dict.value}">${dict.label}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">选择项：</label>
			<div class="controls">
				<select id="chooseOptions" name="chooseOptions"  class="input-small" style="width: 330px;">
					<option value=""></option>
				</select>
				<span class="help-inline"><font color="red">*请先选择模板</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">责任单位加扣分：</label>
			<div class="controls">
				<form:input path="dutyUnitScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">责任领导加扣分：</label>
			<div class="controls">
				<form:input path="dutyLeaderScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">责任人加扣分：</label>
			<div class="controls">
				<form:input path="dutyPersonScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加扣分情况：</label>
			<div class="controls">
				<form:input path="scortSituation" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="exam:examOtherData:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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