<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工会活动报名管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
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
		function adjust(width,height)
		{//debugger;er
			var table = $("table");
			var tableId = table.attr('id');
			var freezeRowNum = table.attr('freezeRowNum');
			var freezeColumnNum = table.attr('freezeColumnNum');
			var width = window.screen.availWidth-width;
			var height =window.screen.availHeight-document.body.scrollTop-height;
			width = width == null ? pageWidth() : width;
			height = height == null ? pageHeight() : height;

			if (typeof(freezeRowNum) != 'undefined' || typeof(freezeColumnNum) != 'undefined') {
				freezeTable(table, freezeRowNum || 0, freezeColumnNum || 0,width , height);
				var flag = false;
				$(window).resize(function() {
					if (flag)
						return ;

					setTimeout(function() {
						adjustTableSize(tableId,width , height);
						flag = false;
					}, 200);

					flag = true;
				});
			}
		}
		/**
		 * 主子表的添加和删除列
		 */
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
			//adjust(500,520);
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
			//adjust(500,200);
		}

		//人员信息导入
		function what(){
			top.$.jBox.open("iframe:${ctx}/affair/affairGhActivityEnroll/template/import?id=${id}&fileName=工会活动报名-人员表.xlsx",
					"导入",800,520,{title:"导入数据", buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){refreshData('${id}')}});
		}
		//导入页面关闭后执行方法
		function refreshData(id) {
			$.getJSON("${ctx}/affair/affairGhActivityEnroll/getChildById",{"id":id},function (result) {
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

<%--		<li><a href="${ctx}/affair/affairGhActivityEnroll/">工会活动报名列表</a></li>--%>
		<%--<li class="active"><a href="${ctx}/affair/affairGhActivityEnroll/form?id=${affairGhActivityEnroll.id}">工会活动报名<shiro:hasPermission name="affair:affairGhActivityEnroll:edit">${not empty affairGhActivityEnroll.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairGhActivityEnroll:edit">查看</shiro:lacksPermission></a></li>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairGhActivityEnroll" action="${ctx}/affair/affairGhActivityEnroll/save?genId=${id}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<form:hidden id="isImport" path="isImport"/>
		<form:hidden id="isAdd" path="isAdd" value="${isAdd}"/>
		<div class="control-group">
			<label class="control-label">时间 ：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairGhActivityEnroll.date}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairGhActivityEnroll.unitId}" labelName="unit" labelValue="${affairGhActivityEnroll.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<%--<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<%--<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input path="idNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<%--<div class="control-group">
			<label class="control-label">职务：</label>
			<div class="controls">
				<form:input path="job" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:select path="sex" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出生年月：</label>
			<div class="controls">
				<input name="birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairGhActivityEnroll.birthday}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">活动项目：</label>
			<div class="controls">
				<form:input path="project" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">审核意见：</label>
			<div class="controls">
				<form:input path="opinion" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<%--<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>

		<div class="control-group">
			<label class="control-label">报名人员：</label>
			<div class="controls">
				<table id="contentTable" class="table table-striped table-bordered table-condensed"  freezeRowNum="1"
					   freezeColumnNum="0">
					<thead>
					<tr>
						<th class="hide"></th>
						<th>单位</th>
						<th>姓名</th>
						<th>身份证号</th>
						<th>职务</th>
						<th>性别</th>
						<th>出生年月</th>
						<th>审核意见</th>
						<th>备注</th>
						<th width="10">&nbsp;
							<input  class="btn btn-primary"  type="button" value="导入" onclick="what()"/>
							<%--<input  class="btn btn-primary"  type="button" value="导出" onclick="exportChild('${affairPoliceHome.id}')"/>
						--%>
						</th>
					</tr>
					</thead>
					<tbody id="activityEnrollChildrenList">
					</tbody>
					<shiro:hasPermission name="affair:affairGhActivityEnroll:edit">
						<tfoot>
						<tr>
							<td colspan="46">
								<a href="javascript:;"
								   onclick="addRow('#activityEnrollChildrenList', affairEnrollChildrenRowIdx, affairEnrollChildrenTpl);affairEnrollChildrenRowIdx = affairEnrollChildrenRowIdx + 1;"
								   class="btn">新增</a>
							</td>
						</tr>
						</tfoot>
					</shiro:hasPermission>
				</table>
				<script type="text/template" id="affairEnrollChildrenTpl">//<!--
				<tr id="activityEnrollChildrenList{{idx}}">
					<td class="hide">
						<input id="activityEnrollChildrenList{{idx}}_id" name="activityEnrollChildrenList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="activityEnrollChildrenList{{idx}}_delFlag" name="activityEnrollChildrenList[{{idx}}].delFlag" type="hidden" value="0"/>
						<input id="activityEnrollChildrenList{{idx}}_ghActivityEnrollId" name="activityEnrollChildrenList[{{idx}}].ghActivityEnrollId" type="hidden" value="{{row.policeHomeId}}"/>
					</td>
					<td>
						<%--<input id="activityEnrollChildrenList{{idx}}_unit" name="activityEnrollChildrenList[{{idx}}].unit" type="text" value="{{row.unit}}" class="input-small " onclick="testOnclick(this)"/>
						--%>
						<sys:treeselect id="activityEnrollChildrenList{{idx}}_unit" name="activityEnrollChildrenList[{{idx}}].unitId"  value="{{row.unitId}}" labelName="activityEnrollChildrenList[{{idx}}].unit" labelValue="{{row.unit}}"
										title="单位" url="/sys/office/treeData?type=2" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息" />
					</td>
					<td>
						<input id="activityEnrollChildrenList{{idx}}_name" name="activityEnrollChildrenList[{{idx}}].name" type="text" value="{{row.name}}" class="input-small "/>
					</td>
					<td>
						<input id="activityEnrollChildrenList{{idx}}_idNumber" name="activityEnrollChildrenList[{{idx}}].idNumber" type="text" value="{{row.idNumber}}" class="input-small "/>
					</td>
					<td>
						<input id="activityEnrollChildrenList{{idx}}_job" name="activityEnrollChildrenList[{{idx}}].job" type="text" value="{{row.job}}" class="input-small "/>
					</td>
					<td>
					<select id="activityEnrollChildrenList{{idx}}_sex" name="activityEnrollChildrenList[{{idx}}].sex" data-value="{{row.sex}}" class="input-small">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('sex')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
					</select>

					<%--<input id="activityEnrollChildrenList{{idx}}_sex" name="activityEnrollChildrenList[{{idx}}].sex" type="text" value="{{row.sex}}" class="input-small "/>--%>
					</td>
					<td>
                        <input id="activityEnrollChildrenList{{idx}}_birthday" name="activityEnrollChildrenList[{{idx}}].birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="{{row.birthday}}"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						<%--<input id="activityEnrollChildrenList{{idx}}_birthday" name="activityEnrollChildrenList[{{idx}}].birthday" type="text" value="{{row.birthday}}" class="input-small "/>--%>
					</td>
					<td>
						<input id="activityEnrollChildrenList{{idx}}_opinion" name="activityEnrollChildrenList[{{idx}}].opinion" type="text" value="{{row.opinion}}" class="input-small "/>
					</td>
					<td>
						<input id="activityEnrollChildrenList{{idx}}_remark" name="activityEnrollChildrenList[{{idx}}].remark" type="text" value="{{row.remark}}" class="input-small "/>
					</td>
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#activityEnrollChildrenList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
				</script>
				<script type="text/javascript">
					var affairEnrollChildrenRowIdx = 0,
							affairEnrollChildrenTpl = $("#affairEnrollChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
					function initData(data){
						$('#activityEnrollChildrenList').html('');
						for (var i = 0; i < data.length; i++) {
							addRow('#activityEnrollChildrenList', affairEnrollChildrenRowIdx, affairEnrollChildrenTpl, data[i]);
							affairEnrollChildrenRowIdx = affairEnrollChildrenRowIdx + 1;
						}
					}
					$(document).ready(function () {
						var data = ${fns:toJson(affairGhActivityEnroll.activityEnrollChildrenList)};
						initData(data);
					});
				</script>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="affair:affairGhActivityEnroll:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.close();"/>
		</div>
	</form:form>
	<script>
		if("success"=="${saveResult}"){
			// parent.$.jBox.close();
			window.close();
		}
	</script>
<%--
<link rel="stylesheet" href="${ctxStatic}/freezeTable/css/table.css" type="text/css"/>
<script src="${ctxStatic}/freezeTable/js/table.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		///adjust(500,520);
	});
</script>
--%>
</body>
</html>