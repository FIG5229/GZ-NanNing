<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>子女信息管理</title>
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

		//根据姓名自动查询相关信息
		function setDefaults() {
			//清空
			$("#idNumber").val('');
			$("#sex").val('');
			$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
			$("#unitId").val('');
			$("#unitName").val('');
			$("#job").val('');
			$.ajax({
				type:"post",
				url:"${ctx}/personnel/personnelBase/getPersonByName",
				data:{name:$("#name").val()},
				dataType:"json",
				success:function(data){
					if(data.success==true && data.result.length==1){
						$("#idNumber").val(data.result[0].idNumber);
						$("#sex").val(data.result[0].sex);
						$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
						$("#unitId").val(data.result[0].actualUnitId);
						$("#unitName").val(data.result[0].actualUnit);
						$("#job").val(data.result[0].jobAbbreviation);
					}else if(data.success==true && data.result.length>1){
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
							$("#idNumber").val(data.result[f.selected].idNumber);
							$("#sex").val(data.result[f.selected].sex);
							$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
							$("#unitId").val(data.result[f.selected].actualUnitId);
							$("#unitName").val(data.result[f.selected].actualUnit);
							$("#job").val(data.result[f.selected].jobAbbreviation);
							return true;
						};
						top.$.jBox(html, { title: "输入",width: 600, height: 300, submit: submit });
					}else {
						$.jBox.tip('没有查询到该人名相关信息');
					}
				}
			})
		}
	</script>
</head>
<body>
     <br>
	<form:form id="inputForm" modelAttribute="affairChildSubsidy" action="${ctx}/affair/affairChildSubsidy/addSave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">时间：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${affairChildSubsidy.date}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairChildSubsidy.unitId}" labelName="unit" labelValue="${affairChildSubsidy.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="false" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">民警姓名：</label>
			<div class="controls">
				<form:input id="name" path="name" htmlEscape="false" class="input-xlarge " onkeydown="if(event.keyCode==13){setDefaults();return false;}"/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职务：</label>
			<div class="controls">
				<form:input path="job" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input id="idNumber" path="idNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">民警性别：</label>
			<div class="controls">
				<form:select id="sex" path="sex" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">补助金额（元）：</label>
			<div class="controls">
				<form:input path="money" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">补助类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_zxtype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">子女信息：</label>
			<div class="controls">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
					<tr>
						<th class="hide"></th>
						<th>子女姓名</th>
						<th>子女性别</th>
						<th>子女出生年月日</th>
						<th>子女现状</th>
					</tr>
					</thead>
					<tbody id="affairChildSubsidyChildList">
					</tbody>
					<shiro:hasPermission name="affair:affairChildSubsidy:edit"><tfoot>
					<tr>
						<td colspan="4">
							<a href="javascript:"
							   onclick="addRow('#affairChildSubsidyChildList', affairChildSubsidyChildRowIdx, affairChildSubsidyChildTpl);affairChildSubsidyChildRowIdx = affairChildSubsidyChildRowIdx + 1;"
							   class="btn">新增</a></td>
					</tr>
					</tfoot></shiro:hasPermission>
				</table>
				<script type="text/template" id="affairChildSubsidyChildTpl">//<!--
						<tr id="affairChildSubsidyChildList{{idx}}">
							<td class="hide">
								<input id="affairChildSubsidyChildList{{idx}}_id" name="affairChildSubsidyChildList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="affairChildSubsidyChildList{{idx}}_delFlag" name="affairChildSubsidyChildList[{{idx}}].delFlag" type="hidden" value="0"/>
								<input id="affairChildSubsidyChildList{{idx}}_zxInfoId" name="affairChildSubsidyChildList[{{idx}}].zxInfoId" type="hidden" value="{{row.zxInfoId}}"/>
								<input id="affairChildSubsidyChildList{{idx}}_zxInfoId" name="affairChildSubsidyChildList[{{idx}}].zxInfoId" type="hidden" value="{{row.zxInfoId}}"/>

							</td>
							<td>
								<input id="affairChildSubsidyChildList{{idx}}_childName" name="affairChildSubsidyChildList[{{idx}}].childName" type="text" value="{{row.childName}}" class="input-small "/>
							</td>
							<td>
								<select id="affairChildSubsidyChildList{{idx}}_childSex" name="affairChildSubsidyChildList[{{idx}}].childSex" data-value="{{row.childSex}}" class="input-small ">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('sex')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="affairChildSubsidyChildList{{idx}}_childBirthDay" name="affairChildSubsidyChildList[{{idx}}].childBirthDay" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
									value="{{row.childBirthDay}}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
							</td>
							<td>
								<input id="affairChildSubsidyChildList{{idx}}_childNow" name="affairChildSubsidyChildList[{{idx}}].childNow" type="text" value="{{row.childNow}}" class="input-small "/>
							</td>
							<shiro:hasPermission name="affair:affairChildSubsidy:edit">
							<td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#affairChildSubsidyChildList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td>
							</shiro:hasPermission>
						</tr>//-->
				</script>
				<script type="text/javascript">
					var affairChildSubsidyChildRowIdx = 0, affairChildSubsidyChildTpl = $("#affairChildSubsidyChildTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
					function initData(data){
						for (var i=0; i<data.length; i++){
							addRow('#affairChildSubsidyChildList', affairChildSubsidyChildRowIdx, affairChildSubsidyChildTpl, data[i]);
							affairChildSubsidyChildRowIdx = affairChildSubsidyChildRowIdx + 1;
						}
					}
					$(document).ready(function() {
						var data = ${fns:toJson(affairChildSubsidy.affairChildSubsidyChildList)};
						initData(data);
					});
				</script>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairChildSubsidy:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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