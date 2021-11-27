<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>选课条件</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
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

	</script>
</head>
<body>
	<br/>
	<ul class="nav nav-tabs">
		<shiro:hasPermission name="affair:affairCourseResource:view">
			<li>
				<a href="${ctx}/affair/affairCourseResource/XKForm?id=${affairCourseResource.id}">选课条件</a>
			</li>
			<li class="active">
				<a href="${ctx}/affair/affairCourseResource/KJForm?id=${affairCourseResource.id}">课件使用范围</a>
			</li>
			<li>
				<a href="${ctx}/affair/affairCourseResource/SPForm?id=${affairCourseResource.id}">审批设置</a>
			</li>
		</shiro:hasPermission>
	</ul>
	<form:form id="inputForm" modelAttribute="affairCourseResource" action="${ctx}/affair/affairCourseResource/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>

		<div class="control-group">
			<label class="control-label">受众名称：</label>
			<div class="controls">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
					<tr>
						<th class="hide"></th>
						<th>受众名称</th>
						<shiro:hasPermission name="affair:affairTypicalTeam:edit">
							<th width="10">&nbsp;</th>
						</shiro:hasPermission>
					</tr>
					</thead>
					<tbody id="typicalTeamChildList">
					</tbody>
					<shiro:hasPermission name="affair:affairTypicalTeam:edit">
						<tfoot>
						<tr>
							<td colspan="4">
								<a href="javascript:;"
								   onclick="addRow('#typicalTeamChildList', affairTypicalTeamChildRowIdx, affairTypicalTeamChildTpl);affairTypicalTeamChildRowIdx = affairTypicalTeamChildRowIdx + 1;"
								   class="btn">新增</a>
							</td>
						</tr>
						</tfoot>
					</shiro:hasPermission>
				</table>
				<script type="text/template" id="affairTypicalTeamChildTpl">//
				<tr id="typicalTeamChildList{{idx}}">
					<td class="hide">
						<input id="typicalTeamChildList{{idx}}_id" name="typicalTeamChildList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="typicalTeamChildList{{idx}}_delFlag" name="typicalTeamChildList[{{idx}}].delFlag" type="hidden" value="0"/>
						<input id="typicalTeamChildList{{idx}}_typicalTeamId" name="typicalTeamChildList[{{idx}}].typicalTeamId" type="hidden" value="{{row.typicalTeamId}}"/>
					</td>
					<td>
						<input id="typicalTeamChildList{{idx}}_time" name="typicalTeamChildList[{{idx}}].time" type="text" readonly="readonly" maxlength="20"
							   value="{{row.time}}" class="input-medium Wdate "
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</td>
					<shiro:hasPermission name="affair:affairTypicalTeam:edit"><td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#typicalTeamChildList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td></shiro:hasPermission>
				</tr>
				</script>
				<script type="text/javascript">
					var affairTypicalTeamChildRowIdx = 0,
							affairTypicalTeamChildTpl = $("#affairTypicalTeamChildTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
					$(document).ready(function () {
						var data = ${fns:toJson(affairTypicalTeam.typicalTeamChildList)};
						for (var i = 0; i < data.length; i++) {
							addRow('#typicalTeamChildList', affairTypicalTeamChildRowIdx, affairTypicalTeamChildTpl, data[i]);
							affairTypicalTeamChildRowIdx = affairTypicalTeamChildRowIdx + 1;
						}
					});
				</script>
			</div>
		</div>


		<div class="form-actions">
			<shiro:hasPermission name="affair:affairCommentators:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
		</div>
	</form:form>
</body>
</html>