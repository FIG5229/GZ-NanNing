<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>一帮一管理</title>
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


		function importData(){
			top.$.jBox.open("iframe:${ctx}/affair/affairOneHelpOne/template/import?id=${id}&fileName=一帮一-帮扶信息.xlsx",
					"导入",800,520,{title:"导入数据", buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){refreshData('${id}')}});
		}
		function exportChild(id) {
			if (id != null || id != undefined){
				window.location.href='${ctx}/affair/affairOneHelpOne/exportChildFile?id='+id;
			}
		}

		function refreshData(id) {

			$.getJSON("${ctx}/affair/affairOneHelpOne/getChildById",{"id":id},function (result) {
				if (result.success ){
					$("#isImport").val(true);
					if (result.result != null){
						initData(result.result)
					}
				} else {

				}
			});
		}
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="affairOneHelpOne" action="${ctx}/affair/affairOneHelpOne/save?genId=${id}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden id="isImport" path="isImport"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">单位：</label>
            <div class="controls">
                <form:input path="unit" htmlEscape="false" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">帮扶信息：</label>
			<div class="controls">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
					<tr>
						<th class="hide"></th>
						<th>帮扶人姓名</th>
						<th>职务</th>
						<th>被帮扶姓名</th>
						<th>单位职务</th>
						<th>被帮扶人情况</th>
						<th>被帮扶人住址</th>
						<th>帮扶金额</th>
						<th>联系电话</th>
						<th>慰问时间</th>
						<shiro:hasPermission name="affair:affairOneHelpOne:edit">
							<th width="10">
								<c:if test="${alter == 'alter'}">
									<input id="btnImport" class="btn btn-primary" type="button" value="导入" onclick="importData()"/>
								</c:if>
								<input id="btnExport" class="btn btn-primary" type="button" value="导出" onclick="exportChild('${affairOneHelpOne.id}')"/>
							</th>
						</shiro:hasPermission>
					</tr>
					</thead>
					<tbody id="oneHelpOneMainList">
					</tbody>
					<shiro:hasPermission name="affair:affairOneHelpOne:edit">
						<tfoot>
						<tr>
							<td colspan="46">
								<a href="javascript:;"
								   onclick="addRow('#oneHelpOneMainList', affairOneHelpOneChildRowIdx, affairOneHelpOneChildTpl);affairOneHelpOneChildRowIdx = affairOneHelpOneChildRowIdx + 1;"
								   class="btn">新增</a>
							</td>
						</tr>
						</tfoot>
					</shiro:hasPermission>
				</table>
				<script type="text/template" id="affairOneHelpOneChildTpl">//
				<tr id="oneHelpOneMainList{{idx}}">
					<td class="hide">
						<input id="oneHelpOneMainList{{idx}}_id" name="oneHelpOneMainList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="oneHelpOneMainList{{idx}}_delFlag" name="oneHelpOneMainList[{{idx}}].delFlag" type="hidden" value="0"/>
						<input id="oneHelpOneMainList{{idx}}_mainId" name="oneHelpOneMainList[{{idx}}].mainId" type="hidden" value="{{row.mainId}}"/>
					</td>
					<td>
						<input id="oneHelpOneMainList{{idx}}_name" name="oneHelpOneMainList[{{idx}}].name" type="text" value="{{row.name}}" class="input-small "/>
					</td>
					<td>
						<input id="oneHelpOneMainList{{idx}}_job" name="oneHelpOneMainList[{{idx}}].job" type="text" value="{{row.job}}" class="input-small "/>
					</td>
					<td>
						<input id="oneHelpOneMainList{{idx}}_beName" name="oneHelpOneMainList[{{idx}}].beName" type="text" value="{{row.beName}}" class="input-small "/>
					</td>
					<td>
						<input id="oneHelpOneMainList{{idx}}_unitJob" name="oneHelpOneMainList[{{idx}}].unitJob" type="text" value="{{row.unitJob}}" class="input-small "/>
					</td>
					<td>
						<input id="oneHelpOneMainList{{idx}}_situation" name="oneHelpOneMainList[{{idx}}].situation" type="text" value="{{row.situation}}" class="input-small "/>
					</td>
					<td>
						<input id="oneHelpOneMainList{{idx}}_address" name="oneHelpOneMainList[{{idx}}].address" type="text" value="{{row.address}}" class="input-small "/>
					</td>
					<td>
						<input id="oneHelpOneMainList{{idx}}_money" name="oneHelpOneMainList[{{idx}}].money" type="text" value="{{row.money}}" class="input-small "/>
					</td>
					<td>
						<input id="oneHelpOneMainList{{idx}}_tel" name="oneHelpOneMainList[{{idx}}].tel" type="text" value="{{row.tel}}" class="input-small "/>
					</td>
					<td>
						<input id="oneHelpOneMainList{{idx}}_date" name="oneHelpOneMainList[{{idx}}].date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="{{row.date}}"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
<%--						<input id="oneHelpOneMainList{{idx}}_date" name="oneHelpOneMainList[{{idx}}].date" type="text" value="{{row.date}}" class="input-small " data-type="date"/>--%>
					</td>
					<shiro:hasPermission name="affair:affairOneHelpOne:edit"><td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#oneHelpOneMainList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td></shiro:hasPermission>
				</tr>
				</script>
				<script type="text/javascript">
					var affairOneHelpOneChildRowIdx = 0,
							affairOneHelpOneChildTpl = $("#affairOneHelpOneChildTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
					function initData(data) {

						for (var i = 0; i < data.length; i++) {
							addRow('#oneHelpOneMainList', affairOneHelpOneChildRowIdx, affairOneHelpOneChildTpl, data[i]);
							affairOneHelpOneChildRowIdx = affairOneHelpOneChildRowIdx + 1;
						}
					}
					$(document).ready(function () {

						var data = ${fns:toJson(affairOneHelpOne.oneHelpOneMainList)};
						initData(data);

					});
				</script>
			</div>
		</div>
	<%--	<div class="control-group">
			<label class="control-label">帮扶人姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职务：</label>
			<div class="controls">
				<form:input path="job" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">被帮扶姓名：</label>
			<div class="controls">
				<form:input path="beName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位职务：</label>
			<div class="controls">
				<form:input path="unitJob" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">被帮扶人情况：</label>
			<div class="controls">
				<form:input path="situation" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">被帮扶人住址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">帮扶金额：</label>
			<div class="controls">
				<form:input path="money" htmlEscape="false" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="tel" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">慰问时间：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairOneHelpOne.date}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairOneHelpOne:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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