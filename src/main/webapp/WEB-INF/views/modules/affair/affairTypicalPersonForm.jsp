<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>典型人物管理</title>
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

		//根据姓名自动查询相关信息
		function setDefaults() {
			//清空
			$("#idNumber").val('');
			$("#sex").val('');
			$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
			$("#nation").val('');
			$('#nation').siblings('.select2-container').find('.select2-chosen').text($("#nation").find("option:selected").text());
			$("#politicsFace").val('');
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
						$("#nation").val(data.result[0].nation);
						$('#nation').siblings('.select2-container').find('.select2-chosen').text($("#nation").find("option:selected").text());
						$("#politicsFace").val(data.result[0].politicsFace);
						$('#politicsFace').siblings('.select2-container').find('.select2-chosen').text($("#politicsFace").find("option:selected").text());
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
							$("#policeNo").val(data.result[f.selected].policeIdNumber);
							$("#sex").val(data.result[f.selected].sex);
							$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
							$("#nation").val(data.result[0].nation);
							$('#nation').siblings('.select2-container').find('.select2-chosen').text($("#nation").find("option:selected").text());
							$("#politicsFace").val(data.result[0].politicsFace);
							$("#job").val(data.result[0].jobAbbreviation);
							return true;
						};
						top.$.jBox(html, { title: "输入",width: 600, height: 300, submit: submit });
					}else {
						$.jBox.tip('没有查询到该人名相关信息');
					}
				}
			})
        }


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

		function what(){
			top.$.jBox.open("iframe:${ctx}/affair/affairTypicalPerson/template/import?id=${id}&fileName=典型培树-典型个人-走访记录.xlsx",
					"导入",800,520,{title:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){refreshData('${id}')}});
		}
		function exportChild(id) {
			if (id != null || id != undefined){
				window.location.href='${ctx}/affair/affairTypicalPerson/exportVisitRecordFile?id='+id;
			}
		}

		function refreshData(id) {
			$.getJSON("${ctx}/affair/affairTypicalPerson/getChildById",{"id":id},function (result) {
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
<%--	<ul class="nav nav-tabs">--%>
<%--		<li><a href="${ctx}/affair/affairTypicalPerson/">典型人物列表</a></li>--%>
<%--		<li class="active"><a href="${ctx}/affair/affairTypicalPerson/form?id=${affairTypicalPerson.id}">典型人物<shiro:hasPermission name="affair:affairTypicalPerson:edit">${not empty affairTypicalPerson.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairTypicalPerson:edit">查看</shiro:lacksPermission></a></li>--%>
<%--	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairTypicalPerson" action="${ctx}/affair/affairTypicalPerson/save?genId=${id}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden id="isImport" path="isImport"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge " onkeydown="if(event.keyCode==13){setDefaults();return false;}"/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
<%--				<form:input path="sex" htmlEscape="false" class="input-xlarge "/>--%>
				<form:select path="sex" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">民族：</label>
			<div class="controls">
<%--				<form:input path="nation" htmlEscape="false" class="input-xlarge "/>--%>
				<form:select path="nation" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('nation')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input path="idNumber" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">政治面貌：</label>
			<div class="controls">
<%--				<form:input path="politicsFace" htmlEscape="false" class="input-xlarge "/>--%>
				<form:select path="politicsFace" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('political_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
		<c:choose>
			<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
				<div class="control-group">
					<label class="control-label">单位：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairTypicalPerson.unitId}" labelName="unit" labelValue="${affairTypicalPerson.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息" isAll="true"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="control-group">
					<label class="control-label">单位：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairTypicalPerson.unitId}" labelName="unit" labelValue="${affairTypicalPerson.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		<%--<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairTypicalPerson.unitId}" labelName="unit" labelValue="${affairTypicalPerson.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">职务：</label>
			<div class="controls">
				<form:input path="job" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培树时间：</label>
			<div class="controls">
<%--				<form:input path="psTime" htmlEscape="false" class="input-xlarge "/>--%>
				<input name="psTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="<fmt:formatDate value="${affairTypicalPerson.psTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培树级别：</label>
			<div class="controls">
<%--				<form:input path="psLevel" htmlEscape="false" class="input-xlarge "/>--%>
				<form:select path="psLevel" class="input-xlarge required">
					<form:option value="" label=""/>
					<%--应胡工要求  改成荣誉管理的荣誉级别字典 修改范围：典型培树以及统计分析--%>
					<form:options items="${fns:getDictList('affair_approval_unitLevel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">曾获荣誉：</label>
			<div class="controls">
				<form:input path="wonHonor" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培树目标：</label>
			<div class="controls">
				<form:input path="psTarget" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<%--	根据731问题跟踪 改为上传附件
		<div class="control-group">
			<label class="control-label">培树方案：</label>
			<div class="controls">
				<form:input path="psProgramme" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">培树部门：</label>
			<div class="controls">
				<%--<sys:treeselect id="psDepartment" name="psDepartmentId" value="${affairTypicalPerson.psDepartmentId}" labelName="psDepartment" labelValue="${affairTypicalPerson.psDepartment}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				--%>
				<form:input path="psDepartment" htmlEscape="false" class="input-xlarge " readonly="true"/>
					<span class="help-inline"><font color="red">*</font> </span>
					<form:hidden path="psDepartmentId"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人：</label>
			<div class="controls">
				<form:input path="contactPerson" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">走访记录：</label>
			<div class="controls">
&lt;%&ndash;				<form:input path="visitRecord" htmlEscape="false" class="input-xlarge "/>&ndash;%&gt;
				<form:textarea path="visitRecord" htmlEscape="false" rows="4" class="input-xxlarge "/>
				<sys:ckeditor replace="visitRecord" uploadPath="/affair/affairTypicalPerson" />
			</div>
		</div>--%>


		<div class="control-group">
			<label class="control-label">走访记录：</label>
			<div class="controls">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
					<tr>
						<th class="hide"></th>
						<th>时间</th>
						<th>走访人</th>
						<th>单位</th>
						<th>职务</th>
						<th>检验情况</th>
						<shiro:hasPermission name="affair:affairTypicalPerson:edit">
							<th width="10">&nbsp;
								<%--修改时不支持导入--%>
								<c:if test="${!alter}">
									<input  class="btn btn-primary"  type="button" value="导入" onclick="what()"/>
								</c:if>
									<input  class="btn btn-primary"  type="button" value="导出" onclick="exportChild('${affairTypicalPerson.id}')"/>
							</th>
						</shiro:hasPermission>
					</tr>
					</thead>
					<tbody id="personNewsList">
					</tbody>
					<shiro:hasPermission name="affair:affairTypicalPerson:edit">
						<tfoot>
						<tr>
							<td colspan="46">
								<a href="javascript:"
												onclick="addRow('#personNewsList', affairTypicalPersonChildRowIdx, affairTypicalPersonChildTpl);affairTypicalPersonChildRowIdx = affairTypicalPersonChildRowIdx + 1;"
												class="btn">新增</a>
							</td>
						</tr>
						</tfoot>
					</shiro:hasPermission>
				</table>
				<script type="text/template" id="affairTypicalPersonChildTpl">
						<tr id="personNewsList{{idx}}">
							<td class="hide">
								<input id="personNewsList{{idx}}_id" name="personNewsList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="personNewsList{{idx}}_delFlag" name="personNewsList[{{idx}}].delFlag" type="hidden" value="0"/>
								<input id="personNewsList{{idx}}_typicalPersonId" name="personNewsList[{{idx}}].typicalPersonId" type="hidden" value="{{row.typicalPersonId}}"/>

							</td>
							<td>

								<input id="personNewsList{{idx}}_time" name="personNewsList[{{idx}}].time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
									   value="{{row.time}}"
									   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>

							</td>
							<td>
								<input id="personNewsList{{idx}}_visitors" name="personNewsList[{{idx}}].visitors" type="text" value="{{row.visitors}}" class="input-small "/>
							</td>
							<td>
								<input id="personNewsList{{idx}}_unit" name="personNewsList[{{idx}}].unit" type="text" value="{{row.unit}}" class="input-small "/>
							</td>
							<td>
								<input id="personNewsList{{idx}}_job" name="personNewsList[{{idx}}].job" type="text" value="{{row.job}}" class="input-small "/>
							</td>
							<td>
								<input id="personNewsList{{idx}}_inspection" name="personNewsList[{{idx}}].inspection" type="text" value="{{row.inspection}}" class="input-small "/>
							</td>
							<shiro:hasPermission name="affair:affairTypicalPerson:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#personNewsList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>
				</script>
				<script type="text/javascript">
					var affairTypicalPersonChildRowIdx = 0,
							affairTypicalPersonChildTpl = $("#affairTypicalPersonChildTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
					function initData(data){
						for (var i = 0; i < data.length; i++) {
							addRow('#personNewsList', affairTypicalPersonChildRowIdx, affairTypicalPersonChildTpl, data[i]);
							affairTypicalPersonChildRowIdx = affairTypicalPersonChildRowIdx + 1;
						}
					}
					$(document).ready(function () {
						var data = ${fns:toJson(affairTypicalPerson.personNewsList)};
						initData(data);
					});
				</script>
			</div>
		</div>

		<%--根据731问题跟踪 改为上传附件
		<div class="control-group">
			<label class="control-label">事迹材料：</label>
			<div class="controls">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
					<tr>
						<th class="hide"></th>
						<th>标题</th>
						<th>地址</th>
						<shiro:hasPermission name="affair:affairTypicalPerson:edit">
							<th width="10">&nbsp;</th>
						</shiro:hasPermission>
					</tr>
					</thead>
					<tbody id="personNewsList">
					</tbody>
					<shiro:hasPermission name="affair:affairTypicalPerson:edit">
						<tfoot>
						<tr>
							<td colspan="46">
								<a href="javascript:;"
												onclick="addRow('#personNewsList', affairTypicalPersonChildRowIdx, affairTypicalPersonChildTpl);affairTypicalPersonChildRowIdx = affairTypicalPersonChildRowIdx + 1;"
												class="btn">新增</a>
							</td>
						</tr>
						</tfoot>
					</shiro:hasPermission>
				</table>
				<script type="text/template" id="affairTypicalPersonChildTpl">//
						<tr id="personNewsList{{idx}}">
							<td class="hide">
								<input id="personNewsList{{idx}}_id" name="personNewsList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="personNewsList{{idx}}_delFlag" name="personNewsList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="personNewsList{{idx}}title" name="personNewsList[{{idx}}].title" type="text" value="{{row.title}}" class="input-small "/>
							</td>
							<td>
								<input id="personNewsList{{idx}}_url" name="personNewsList[{{idx}}].url" type="text" value="{{row.url}}" class="input-small "/>
							</td>
							<shiro:hasPermission name="affair:affairTypicalPerson:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#personNewsList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>
				</script>
				<script type="text/javascript">
					var affairTypicalPersonChildRowIdx = 0,
							affairTypicalPersonChildTpl = $("#affairTypicalPersonChildTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
					$(document).ready(function () {
						var data = ${fns:toJson(affairTypicalPerson.personNewsList)};
						for (var i = 0; i < data.length; i++) {
							addRow('#personNewsList', affairTypicalPersonChildRowIdx, affairTypicalPersonChildTpl, data[i]);
							affairTypicalPersonChildRowIdx = affairTypicalPersonChildRowIdx + 1;
						}
					});
				</script>
			</div>
		</div>--%>

		<div class="control-group">
			<label class="control-label">宣传情况：</label>
			<div class="controls">
<%--				<form:input path="publicity" htmlEscape="false" class="input-xlarge "/>--%>
				<form:select path="publicity" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('publicity_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">推送机构：</label>
			<div class="controls">
				<sys:treeselect id="pushOrganization" name="pushOrganizationId" value="${affairTypicalPerson.pushOrganizationId}" labelName="pushOrganization" labelValue="${affairTypicalPerson.pushOrganization}"
								title="单位" url="/sys/office/treeData?type=2" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息" isAll="true"/>
<%--				<span class="help-inline"><font color="red">*</font> </span>--%>
<%--				<form:input path="pushOrganization" htmlEscape="false" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培树方案：</label>
			<div class="controls">
				<form:hidden id="psProgramme" path="psProgramme" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="psProgramme" type="files" uploadPath="affair/affairTypicalPerson" selectMultiple="true"  simple="true"/>
					<%--				<form:input path="filePath" htmlEscape="false" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">事迹材料：</label>
			<div class="controls">
				<form:hidden id="materials" path="materials" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="materials" type="files" uploadPath="affair/affairTypicalPerson" selectMultiple="true"  simple="true"/>
					<%--				<form:input path="filePath" htmlEscape="false" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filePath" type="files" uploadPath="affair/affairTypicalPerson" selectMultiple="true"  simple="true"/>
<%--				<form:input path="filePath" htmlEscape="false" class="input-xlarge "/>--%>
			</div>
		</div>

		<div class="form-actions">
			<%--<shiro:hasPermission name="affair:affairTypicalPerson:edit">--%>
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<%--</shiro:hasPermission>--%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
	<script>
		if("success"=="${saveResult}"){
			parent.$.jBox.tip("保存成功");
			parent.$.jBox.close();
		}
	</script>
</body>
</html>