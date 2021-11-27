<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<html>
<head>
	<title>网评员管理管理</title>
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

		//添加弹窗
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "账号情况添加",600,400,{
				buttons:{"关闭":true},
				loaded:function(h){
					 $(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					window.close()
				}
			});
		}


		//根据姓名自动查询相关信息
		function setUserInfo() {
			//清空
			$("#idNumber").val('');
			$.ajax({
				type:"post",
				url:"${ctx}/personnel/personnelBase/getPersonByName",
				data:{name:$("#name").val()},
				dataType:"json",
				success:function(data){
					//debugger;er;
					if (data.success==true ){


						if( data.result.length==1){
							//获取到身份证号后 查询家庭关系
							// setFamilyInfo();
							$("#idNumber").val(data.result[0].idNumber);
							$("#policeNo").val(data.result[0].policeIdNumber);
							$("#nation").val(data.result[0].nation);
							// $('#sex').siblings('.select2-container').find('.select2-chosen').text($("#nation").find("option:selected").text());
							$("#birthday").val(data.result[0].birthday);
							$("#sex").val(data.result[0].sex);
							$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
						}else if( data.result.length>1){
							var html = '<table id="contentTable" class="table table-striped table-bordered table-condensed">';
							html += '<thead><tr><th></th><th>姓名</th><th>单位</th><th>身份证号</th><th>警号</th></tr></thead>';
							html += '<tbody>';
							for(var i=0; i< data.result.length; i++) {
								html += '<tr><td><input type="radio" name="selected" value="'+i+'"></td>';
								html += '<td>'+data.result[i].name+'</td>';
								html += '<td>'+data.result[i].workunitName+'</td>';
								html += '<td>'+data.result[i].idNumber+'</td>';
								html += '<td>'+data.result[i].policeIdNumber+'</td>';
								html += '</tr>';
							}

							html +=	'</tbody>';
							html +=	'</table>';
							var submit = function (v, h, f) {
								//获取到身份证号后 查询家庭关系
								// setFamilyInfo();
								$("#idNumber").val(data.result[0].idNumber);
								$("#policeNo").val(data.result[0].policeIdNumber);
								$("#nation").val(data.result[0].nation);
								// $('#sex').siblings('.select2-container').find('.select2-chosen').text($("#nation").find("option:selected").text());
								$("#birthday").val(data.result[0].birthday);
								$("#sex").val(data.result[0].sex);
								$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
								return true;
							};

							top.$.jBox(html, { title: "输入",width: 600, height: 300, submit: submit });
						}else {
							$.jBox.tip('没有查询到该人名相关信息');
						}
					}
				}
			});
			// var idNumber = $("#idNumber").val();
			// if (idNumber != null && idNumber != ''){
			// setFamilyInfo();
			// }
		}
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="affairCommentators" action="${ctx}/affair/affairCommentators/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"
							onkeydown="if(event.keyCode==13){setUserInfo();return false;}"/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
					<%--家庭信息查看时显示--%>
				<form:input path="idNumber" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
		<c:choose>
			<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
				<div class="control-group">
					<label class="control-label">单位：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairCommentators.unitId}" labelName="unit" labelValue="${affairCommentators.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" isAll="true"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="control-group">
					<label class="control-label">单位：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairCommentators.unitId}" labelName="unit" labelValue="${affairCommentators.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		<%--<div class="control-group">
			<label class="control-label">单位：</label>
				<div class="controls">
					<sys:treeselect id="unit" name="unitId" value="${affairCommentators.unitId}" labelName="unit" labelValue="${affairCommentators.unit}"
									title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">职务：</label>
			<div class="controls">
				<form:input path="duty" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系方式：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">网评员账号：</label>
			<div class="controls">
				<form:input path="account" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">培训情况：</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">账号情况：</label>
			<div class="controls">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th class="hide"></th>
							<th>平台名称</th>
							<th>账号</th>
							<th>身份证号</th>
							<shiro:hasPermission name="affair:affairCommentators:edit">
								<th width="10">&nbsp;</th>
							</shiro:hasPermission>
						</tr>
					</thead>
					<tbody id="affairCommentatorsDeputies">
					</tbody>
						<shiro:hasPermission name="affair:affairCommentators:edit">
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
					<td>
						<input id="affairCommentatorsDeputies{{idx}}_account" name="affairCommentatorsDeputies[{{idx}}].account" type="text" value="{{row.account}}" class="input-small "/>
					</td>
					<td>
						<input id="affairCommentatorsDeputies{{idx}}_idNumber" name="affairCommentatorsDeputies[{{idx}}].idNumber" type="text" value="{{row.idNumber}}" class="input-small "/>
					</td>
					<shiro:hasPermission name="affair:affairCommentators:edit">
						<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#affairCommentatorsDeputies{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td></shiro:hasPermission>
				</tr>
				</script>
				<script type="text/javascript">
					var affairCommentatorsDeputiesIdx = 0,
							affairCommentatorsDeputiesTpl = $("#affairCommentatorsDeputiesTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
					$(document).ready(function () {
						var data = ${fns:toJson(affairCommentators.affairCommentatorsDeputies)};
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
<script>
	if("sucess"=="${saveResult}"){
		parent.$.jBox.tip("保存成功");
		parent.$.jBox.close();
	}
</script>
</body>
</html>