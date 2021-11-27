<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>组织建设管理</title>
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
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="affairOrganizationBulid" action="${ctx}/affair/affairOrganizationBulid/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">时间：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairOrganizationBulid.date}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工会名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所辖单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairOrganizationBulid.unitId}" labelName="unit" labelValue="${affairOrganizationBulid.unit}"
								title="所辖单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true" checked="true" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属上级工会组织：</label>
			<div class="controls">
				<form:select path="orgName" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_gh_org')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支所队工会主席：</label>
			<div class="controls">
				<form:input path="zghzNum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否建立会员评价制度：</label>
			<div class="controls">
				<form:select path="isAssessSys" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会员评价测评结果：</label>
			<div class="controls">
				<form:input path="result" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所队支工会数：</label>
			<div class="controls">
				<form:input path="zghNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支工会人数：</label>
			<div class="controls">
				<form:input path="zghrNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">满意数：</label>
			<div class="controls">
				<form:input path="satisfyNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">自行自查报告：</label>
			<div class="controls">
				<form:input path="report" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主要做法：</label>
			<div class="controls">
				<form:textarea id="method" htmlEscape="true" path="method" rows="4" maxlength="150" class="input-xxlarge"/>
				<sys:ckeditor replace="method" uploadPath="/cms/article" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">换届时间：</label>
			<div class="controls">
				<form:input path="hjDate" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文件：</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filePath" type="files" uploadPath="affair/affairNews" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">委员会分工：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>委员会情况</th>
								<th>委员会角色名字</th>
								<shiro:hasPermission name="affair:affairOrganizationBulid:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="affairOrganziationBuildSignList">
						</tbody>
						<shiro:hasPermission name="affair:affairOrganizationBulid:edit"><tfoot>
							<tr><td colspan="6"><a href="javascript:" onclick="addRow('#affairOrganziationBuildSignList', affairOrganziationBuildSignRowIdx, affairOrganziationBuildSignTpl);affairOrganziationBuildSignRowIdx = affairOrganziationBuildSignRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="affairOrganziationBuildSignTpl">//<!--
						<tr id="affairOrganziationBuildSignList{{idx}}">
							<td class="hide">
								<input id="affairOrganziationBuildSignList{{idx}}_id" name="affairOrganziationBuildSignList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="affairOrganziationBuildSignList{{idx}}_delFlag" name="affairOrganziationBuildSignList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<select id="affairOrganziationBuildSignList{{idx}}_committee" name="affairOrganziationBuildSignList[{{idx}}].committee" data-value="{{row.committee}}" class="input-small ">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('affair_wyh')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="affairOrganziationBuildSignList{{idx}}_committeeName" name="affairOrganziationBuildSignList[{{idx}}].committeeName" type="text" value="{{row.committeeName}}" class="input-small "/>
							</td>
							<shiro:hasPermission name="affair:affairOrganizationBulid:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#affairOrganziationBuildSignList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var affairOrganziationBuildSignRowIdx = 0, affairOrganziationBuildSignTpl = $("#affairOrganziationBuildSignTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(affairOrganizationBulid.affairOrganziationBuildSignList)};
							for (var i=0; i<data.length; i++){
								addRow('#affairOrganziationBuildSignList', affairOrganziationBuildSignRowIdx, affairOrganziationBuildSignTpl, data[i]);
								affairOrganziationBuildSignRowIdx = affairOrganziationBuildSignRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="control-group">
			<label class="control-label">经费检查委员会分工：</label>
			<div class="controls">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
					<tr>
						<th class="hide"></th>
						<th>经费审查委员会情况</th>
						<th>经费审查委员会名字</th>
						<shiro:hasPermission name="affair:affairOrganizationBulid:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
					</tr>
					</thead>
					<tbody id="affairOrganizationBuildSingList2">
					</tbody>
					<shiro:hasPermission name="affair:affairOrganizationBulid:edit"><tfoot>
					<tr><td colspan="6"><a href="javascript:" onclick="addRow('#affairOrganizationBuildSingList2', affairOrganizationBuildSingList2RowIdx, affairOrganizationBuildSingList2Tpl);affairOrganizationBuildSingList2RowIdx = affairOrganizationBuildSingList2RowIdx + 1;" class="btn">新增</a></td></tr>
					</tfoot></shiro:hasPermission>
				</table>
				<script type="text/template" id="affairOrganizationBuildSingList2Tpl">//<!--
						<tr id="affairOrganizationBuildSingList2{{idx}}">
							<td class="hide">
								<input id="affairOrganizationBuildSingList2{{idx}}_id" name="affairOrganizationBuildSingList2[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="affairOrganizationBuildSingList2{{idx}}_delFlag" name="affairOrganizationBuildSingList2[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<select id="affairOrganizationBuildSingList2{{idx}}_review" name="affairOrganizationBuildSingList2[{{idx}}].review" data-value="{{row.review}}" class="input-small ">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('affair_jfsc')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="affairOrganizationBuildSingList2{{idx}}_reviewName" name="affairOrganizationBuildSingList2[{{idx}}].reviewName" type="text" value="{{row.reviewName}}" class="input-small "/>
							</td>
							<shiro:hasPermission name="affair:affairOrganizationBulid:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#affairOrganizationBuildSingList2{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
				</script>
				<script type="text/javascript">
					var affairOrganizationBuildSingList2RowIdx = 0, affairOrganizationBuildSingList2Tpl = $("#affairOrganizationBuildSingList2Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
					$(document).ready(function() {
						var data = ${fns:toJson(affairOrganizationBulid.affairOrganizationBuildSingList2)};
						for (var i=0; i<data.length; i++){
							addRow('#affairOrganizationBuildSingList2', affairOrganizationBuildSingList2RowIdx, affairOrganizationBuildSingList2Tpl, data[i]);
							affairOrganizationBuildSingList2RowIdx = affairOrganizationBuildSingList2RowIdx + 1;
						}
					});
				</script>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" class="input-xlarge "/>
				<span class="help-inline"><font color="red">请按照1-1(公安局-单位序号),2-1-1(公安处-公安处序号-单位序号)，这种格式排序</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairOrganizationBulid:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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