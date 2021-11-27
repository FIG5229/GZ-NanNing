<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<html>
<head>
	<title>共享设置</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$("#contentTable").printThis({
			debug: false,
			importCSS: true,
			importStyle: true,
			printContainer: true,
			loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
			pageTitle: "打印",
			removeInline: false,
			printDelay: 333,
			header: null,
			formValues: false
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

		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					parent.$.jBox.close();
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
				<a href="${ctx}/affair/affairCourseResource/formDetail?id=${affairCourseResource.id}">基本信息</a>
			</li>
			<li>
				<a href="${ctx}/affair/affairCourseResource/SwfForm?id=${affairCourseResource.id}">课件上传</a>
			</li>
			<li>
				<a href="${ctx}/affair/affairCourseResource/ZSForm?id=${affairCourseResource.id}">知识点设置</a>
			</li>
			<li class="active">
				<a  href="${ctx}/affair/affairCourseResource/GXForm?id=${affairCourseResource.id}">共享设置</a>
			</li>

		</shiro:hasPermission>
	</ul>
	<form:form id="inputForm" modelAttribute="affairCourseUnit" action="${ctx}/affair/affairCourseUnit/save" method="post" class="form-horizontal">


		<div class="control-group">
			<label class="control-label">账号情况：</label>
			<div class="controls">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th class="hide"></th>
							<th>共享单位</th>
							<shiro:hasPermission name="affair:affairCourseResource:edit">
								<th width="10">&nbsp;</th>
							</shiro:hasPermission>
						</tr>
					</thead>
					<tbody id="affairCommentatorsDeputies">
					</tbody>
						<shiro:hasPermission name="affair:affairCourseResource:edit">
							<tfoot>
								<tr>
									<td colspan="46">
										<a href="javascript:;"
										   onclick="addRow('#affairCommentatorsDeputies', affairCommentatorsDeputiesIdx, affairCommentatorsDeputiesTpl);
										   affairCommentatorsDeputiesIdx = affairCommentatorsDeputiesIdx + 1;"class="btn">新增</a>
									</td>
								</tr>
							</tfoot>
						</shiro:hasPermission>
				</table>
				<script type="text/template" id="affairCommentatorsDeputiesTpl">
				<tr id="affairCommentatorsDeputies{{idx}}">
					<td class="hide">
						<input id="affairCommentatorsDeputiesList{{idx}}_id" name="affairCommentatorsDeputies[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="affairCommentatorsDeputies{{idx}}_delFlag" name="affairCommentatorsDeputies[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					<td>
						<input id="affairCommentatorsDeputies{{idx}}_platform" name="affairCommentatorsDeputies[{{idx}}].platform" type="text" value="{{row.platform}}" class="input-small "/>
					</td>
					<shiro:hasPermission name="affair:affairCourseResource:edit">
						<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#affairCommentatorsDeputies{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td></shiro:hasPermission>
				</tr>
				</script>
				<script type="text/javascript">
					var affairCommentatorsDeputiesIdx = 0,
							affairCommentatorsDeputiesTpl = $("#affairCommentatorsDeputiesTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
					$(document).ready(function () {
						var data = ${fns:toJson(affairCourseUnit.classId)};
						for (var i = 0; i < data.length; i++) {
							addRow('#affairCommentatorsDeputies', affairCommentatorsDeputiesIdx, affairCommentatorsDeputiesTpl, data[i]);
							affairCommentatorsDeputiesIdx = affairCommentatorsDeputiesIdx + 1;
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