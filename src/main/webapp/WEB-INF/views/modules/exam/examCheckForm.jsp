<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>检查法相情况录入管理</title>
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
				data: {useModel: $("#examCheckChildList" + index + "_useModel").val()},
				type: "POST",
				success: function (res) {
					if (res.result != null) {
						list = res.result;
						content = '';
						list.forEach((item, index) => {
							content += '<option value=' + item.optionId + '>' + item.itemValue + '</option>';
						})
						$("#examCheckChildList" + index + "_chooseOptions").empty();
						$("#examCheckChildList" + index + "_chooseOptions").append(content);
						$("#examCheckChildList" + index + "_chooseOptions").val('');
						$("#examCheckChildList" + index + "_chooseOptions").siblings('.select2-container').find('.select2-chosen').text($("#examCheckChildList" + index + "_chooseOptions").find("option:selected").text());
					}
				}
			});
		};

		function getChooseStandardList(index) {
			debugger
			var list = [];
			var content = "";
			$.ajax({
				url: "${ctx}/exam/examStandardBaseInfo/getStandardListBeta",
				dataType: "json",
				async: true,//请求是否异步，默认为异步
				data: {kpType: $("#examCheckChildList" + index + "_type").val()},
				type: "POST",
				success: function (res) {
					if (res.result != null) {
						list = res.result;
						content = '';
						list.forEach((item, index) => {
							content += '<option value=' + item.id + '>' + item.name + '</option>';
						})
						$("#examCheckChildList" + index + "_useModel").empty();
						$("#examCheckChildList" + index + "_useModel").append(content);
						$("#examCheckChildList" + index + "_useModel").val('');
						$("#examCheckChildList" + index + "_useModel").siblings('.select2-container').find('.select2-chosen').text($("#examCheckChildList" + index + "_useModel").find("option:selected").text());
						getChooseOptionsList(0);
						getOptionsScore(0);
					}
				}
			});
		};

		function getOptionsScore(index) {
			var list = [];
			var content = "";
			$.ajax({
				url: "${ctx}/exam/examStandardTemplateDefine/getOptionsDetail",
				dataType: "json",
				async: true,//请求是否异步，默认为异步
				data: {optionId: $("#examCheckChildList" + index + "_chooseOptions").val(),standardId:$("#examCheckChildList" + index + "_useModel").val()},
				type: "POST",
				success: function (res) {
					if (res.result != null) {
						$("#score"+index).val(res.result);
					}
				}
			});
		};
		$(document).ready(function () {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function (form) {
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function (error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});

			<c:forEach varStatus="status" items="${examCheck.examCheckChildList}" var="examCheckChildList">
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
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="examCheck" action="${ctx}/exam/examCheck/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">检查时间：</label>
			<div class="controls">
				<input name="checkDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${examCheck.checkDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">检查单位：</label>
			<div class="controls">
				<sys:treeselect id="checkUnit" name="checkUnitId" value="${examCheck.checkUnitId}" labelName="checkUnit" labelValue="${examCheck.checkUnit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">检查人：</label>
			<div class="controls">
				<sys:treeselect id="checkPerson" name="checkPersonId" value="${examCheck.checkPersonId}" labelName="checkPerson" labelValue="${examCheck.checkPerson}"
					title="检查人" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true" cssStyle="width:300px;" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<c:forEach items="${examCheck.examCheckChildList}" var="child" varStatus="status">
			<input id="chooseOptions${status.index}" name="chooseOptions${status.index}" type="hidden"
				   value="${child.chooseOptions}"/>
		</c:forEach>
		<div class="control-group">
			<label class="control-label">问题录入：</label>
			<div class="controls">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
					<tr>
						<th class="hide"></th>
						<th>层次<span class="help-inline"><%--<font color="red">*</font>--%> </span></th>
						<th>使用模板<span class="help-inline"><%--<font color="red">*</font>--%> </span></th>
						<th>选择项<span class="help-inline"><%--<font color="red">*</font> --%></span></th>
						<th>标准分值</th>
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
						<shiro:hasPermission name="exam:examCheck:edit">
							<th width="10">&nbsp;</th>
						</shiro:hasPermission>
					</tr>
					</thead>
					<tbody id="examCheckChildList">
					</tbody>
					<shiro:hasPermission name="exam:examCheck:edit">
						<tfoot>
						<tr>
							<td colspan="13"><a href="javascript:"
												onclick="addRow('#examCheckChildList', examCheckChildRowIdx, examCheckChildTpl);examCheckChildRowIdx = examCheckChildRowIdx + 1;"
												class="btn">新增</a></td>
						</tr>
						</tfoot>
					</shiro:hasPermission>
				</table>

				<script type="text/template" id="examCheckChildTpl">//<!--
						<tr id="examCheckChildList{{idx}}">
							<td class="hide">
								<input id="examCheckChildList{{idx}}_id" name="examCheckChildList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="examCheckChildList{{idx}}_delFlag" name="examCheckChildList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<select id="examCheckChildList{{idx}}_type" name="examCheckChildList[{{idx}}].type" data-value="{{row.type}}" class="input-small " onchange="getChooseStandardList({{idx}})" style="width: 150px;">
									<option value=""></option>
                                      <c:choose>
                                            <c:when test="${fns:getUser().name.contains('派出所')}">
                                                <option value="7">民警考评</option>
                                            </c:when>
                                            <c:when test="${fns:getUser().company.id == '34' || fns:getUser().company.id == '95' || fns:getUser().company.id == '156'}">
                                                <option value="1">局考处</option>
                                                <option value="2">局考局机关</option>
                                                <option value="5">处领导考评</option>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach items="${fns:getDictList('kp_type')}" var="dict">
                                                    <option value="${dict.value}">${dict.label}</option>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>

								</select>
								<script>
								<%--子表中下拉框搜索--%>
										$("#examCheckChildList{{idx}}_type").select2();
										$("#examCheckChildList{{idx}}_type").select2('val','{{row.type}}');
									</script>
							</td>
							<td>

								<select id="examCheckChildList{{idx}}_useModel" name="examCheckChildList[{{idx}}].useModel" data-value="{{row.useModel}}" class="input-small " onchange="getChooseOptionsList({{idx}})" style="width: 250px;">
									<option value=""></option>
									<c:forEach items="${templateFile}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
									<script>
										$("#examCheckChildList{{idx}}_useModel").select2();
										$("#examCheckChildList{{idx}}_useModel").select2('val','{{row.useModel}}');
									</script>
							</td>
							<td>
								<select id="examCheckChildList{{idx}}_chooseOptions" name="examCheckChildList[{{idx}}].chooseOptions" onchange="getOptionsScore({{idx}})" data-value="{{row.chooseOptions}}" class="input-small" style="width: 250px;">
									<option value=""></option>
								</select>
								<script>
									<%--子表中下拉框搜索--%>
									$("#examCheckChildList{{idx}}_chooseOptions").select2();
									$("#examCheckChildList{{idx}}_chooseOptions").select2('val','{{row.chooseOptions}}');
								</script>
							</td>
							<td><textarea rows="3" id="score{{idx}}" value="" disabled/></td>
							<td>
								<sys:treeselect id="examCheckChildList{{idx}}_dutyUnit" name="examCheckChildList[{{idx}}].dutyUnitId" value="{{row.dutyUnitId}}" labelName="examCheckChildList[{{idx}}].dutyUnit" labelValue="{{row.dutyUnit}}"
									title="责任单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true"  dataMsgRequired="必填信息"/>
							</td>
							<td>
								<input id="examCheckChildList{{idx}}_dutyUnitScore" name="examCheckChildList[{{idx}}].dutyUnitScore" type="text" value="{{row.dutyUnitScore}}" class="input-small number"/>
							</td>
							<td>
								<sys:treeselect id="examCheckChildList{{idx}}_dutyLeader" name="examCheckChildList[{{idx}}].dutyLeaderId" value="{{row.dutyLeaderId}}" labelName="examCheckChildList[{{idx}}].dutyLeader" labelValue="{{row.dutyLeader}}"
									title="责任领导" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
							</td>
							<td>
								<input id="examCheckChildList{{idx}}_dutyLeaderScore" name="examCheckChildList[{{idx}}].dutyLeaderScore" type="text" value="{{row.dutyLeaderScore}}" class="input-small number"/>
							</td>
							<td>
								<sys:treeselect id="examCheckChildList{{idx}}_dutyPerson" name="examCheckChildList[{{idx}}].dutyPersonId" value="{{row.dutyPersonId}}" labelName="examCheckChildList[{{idx}}].dutyPerson" labelValue="{{row.dutyPerson}}"
									title="责任人" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
							</td>
							<td>
								<input id="examCheckChildList{{idx}}_dutyPersonScore" name="examCheckChildList[{{idx}}].dutyPersonScore" type="text" value="{{row.dutyPersonScore}}" class="input-small number"/>
							</td>
							<!--
							<td>
								<textarea id="examCheckChildList{{idx}}_testStandart" name="examCheckChildList[{{idx}}].testStandart" rows="4" class="input-small ">{{row.testStandart}}</textarea>
							</td>-->
							<td>
								<textarea id="examCheckChildList{{idx}}_scortSituation" name="examCheckChildList[{{idx}}].scortSituation" rows="4" class="input-small required">{{row.scortSituation}}</textarea>
							</td>
							<td>
								<input id="examCheckChildList{{idx}}_remark" name="examCheckChildList[{{idx}}].remark" type="text" value="{{row.remark}}" class="input-small "/>
							</td>
							<shiro:hasPermission name="exam:examCheck:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#examCheckChildList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
				</script>
				<script type="text/javascript">
					var examCheckChildRowIdx = 0,
							examCheckChildTpl = $("#examCheckChildTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
					$(document).ready(function () {
						var data = ${fns:toJson(examCheck.examCheckChildList)};
						for (var i = 0; i < data.length; i++) {
							addRow('#examCheckChildList', examCheckChildRowIdx, examCheckChildTpl, data[i]);
							examCheckChildRowIdx = examCheckChildRowIdx + 1;
						}

						//根据使用模板的值渲染选择项所对应的内容   回显
						function getChooseOptionsListView(chooseOptionsValue, index) {
							var list = [];
							var content = "";
							$.ajax({
								url: "${ctx}/exam/examCheck/findChooseOptionsByUseModel",
								dataType: "json",
								async: true,//请求是否异步，默认为异步
								data: {useModel: $("#examCheckChildList" + index + "_useModel").val()},
								type: "POST",
								success: function (res) {
									if (res.result != null) {
										list = res.result;
										list.forEach((item, index) => {
											if (chooseOptionsValue == item) {
												$("#examCheckChildList" + index + "_chooseOptions").val(chooseOptionsValue);
												content += '<option value=' + item + ' selected >' + item + '</option>';
											} else {
												content += '<option value=' + item + '>' + item + '</option>';
											}
										})
										$("#examCheckChildList" + index + "_chooseOptions").append(content);
										$("#examCheckChildList" + index + "_chooseOptions").siblings('.select2-container').find('.select2-chosen').text($("#examCheckChildList" + index + "_chooseOptions").find("option:selected").text());
									}
								}
							});
						};
						if (${not empty examCheck.id}) {
							/*for (var i = 0; i < ${examCheck.examCheckChildList.size()}; i++) {
								// getChooseOptionsListView($("#chooseOptions" + i).val(), i);
							}*/
						}
					});
				</script>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="exam:examCheck:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
		</div>
		<script>
			if("success"=="${saveResult}"){
				parent.$.jBox.close();
			}
		</script>
	</form:form>
</body>
</html>