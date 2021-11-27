<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民警小家建设管理</title>
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
		//设备导入
		function what(){
			top.$.jBox.open("iframe:${ctx}/affair/affairPoliceHome/template/import?id=${id}&fileName=民警小家建设表格.xlsx",
					"导入",800,520,{title:"导入数据", buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){refreshData('${id}')}});
		}
		//设备导出
		function exportChild(id) {
			if (id != null || id != undefined){
				window.location.href='${ctx}/affair/affairPoliceHome/exportChildRecordFile?id='+id;
			}
		}
		//导入页面关闭后执行方法
		function refreshData(id) {
			$.getJSON("${ctx}/affair/affairPoliceHome/getChildById",{"id":id},function (result) {
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
<br>
	<form:form id="inputForm" modelAttribute="affairPoliceHome" action="${ctx}/affair/affairPoliceHome/save?genId=${id}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden id="isImport" path="isImport"/>
		<form:hidden id="isAdd" path="isAdd" value="${isAdd}"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">建设时间：</label>
			<div class="controls">
				<input name="pointDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="<fmt:formatDate value="${affairPoliceHome.pointDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
		</div>
		<div class="control-group">
			<label class="control-label">所属单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairPoliceHome.unitId}" labelName="unit" labelValue="${affairPoliceHome.unit}"
								title="单位" url="/sys/office/treeData?type=1" cssClass="required" isAll="true" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">小家建设名称：</label>
			<div class="controls">
				<sys:treeselect id="pointUnit" name="pointUnitId" value="${affairPoliceHome.pointUnitId}" labelName="pointUnit" labelValue="${affairPoliceHome.pointUnit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required"  allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建设项目：</label>
			<div class="controls">
				<form:select path="project" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_jsxm')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备：</label>
			<div class="controls">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
					<tr>
						<th class="hide"></th>
						<th>所需设备</th>
						<th>建设数量</th>
						<th>单价</th>
						<th>小计</th>
						<th>内容</th>
						<th>经办人</th>
						<th>申报单位审核人</th>
						<th>处工会审核人</th>
						<th>局工会审核人</th>
						<shiro:hasPermission name="affair:affairPoliceHome:edit">
							<th width="10">&nbsp;
								<input  class="btn btn-primary"  type="button" value="导入" onclick="what()"/>
								<input  class="btn btn-primary"  type="button" value="导出" onclick="exportChild('${affairPoliceHome.id}')"/>
							</th>
						</shiro:hasPermission>
					</tr>
					</thead>
					<tbody id="policeHomeChildList">
					</tbody>
					<shiro:hasPermission name="affair:affairPoliceHome:edit">
						<tfoot>
						<tr>
							<td colspan="46">
								<a href="javascript:;"
								   onclick="addRow('#policeHomeChildList', affairPoliceHomeChildRowIdx, affairPoliceHomeChildTpl);affairPoliceHomeChildRowIdx = affairPoliceHomeChildRowIdx + 1;"
								   class="btn">新增</a>
							</td>
						</tr>
						</tfoot>
					</shiro:hasPermission>
				</table>
				<script type="text/template" id="affairPoliceHomeChildTpl">//
				<tr id="policeHomeChildList{{idx}}">
					<td class="hide">
						<input id="policeHomeChildList{{idx}}_id" name="policeHomeChildList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="policeHomeChildList{{idx}}_delFlag" name="policeHomeChildList[{{idx}}].delFlag" type="hidden" value="0"/>
						<input id="policeHomeChildList{{idx}}_policeHomeId" name="policeHomeChildList[{{idx}}].policeHomeId" type="hidden" value="{{row.policeHomeId}}"/>
					</td>
					<td>
						<input id="policeHomeChildList{{idx}}_device" name="policeHomeChildList[{{idx}}].device" type="text" value="{{row.device}}" class="input-small "/>
					</td>
					<td>
						<input id="policeHomeChildList{{idx}}_nums" name="policeHomeChildList[{{idx}}].nums" type="text" value="{{row.nums}}" class="input-small "/>
					</td>
					<td>
						<input id="policeHomeChildList{{idx}}_price" name="policeHomeChildList[{{idx}}].price" type="text" value="{{row.price}}" class="input-small "/>
					</td>
					<td>
						<input id="policeHomeChildList{{idx}}_sum" name="policeHomeChildList[{{idx}}].sum" type="text" value="{{row.sum}}" class="input-small "/>
					</td>
					<td>
						<input id="policeHomeChildList{{idx}}_content" name="policeHomeChildList[{{idx}}].content" type="text" value="{{row.content}}" class="input-small "/>
					</td>
					<td>
						<input id="policeHomeChildList{{idx}}_jingBan" name="policeHomeChildList[{{idx}}].jingBan" type="text" value="{{row.jingBan}}" class="input-small required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</td>
					<td>
						<input id="policeHomeChildList{{idx}}_unitShRen" name="policeHomeChildList[{{idx}}].unitShRen" type="text" value="{{row.unitShRen}}" class="input-small "/>
					</td>
					<td>
						<input id="policeHomeChildList{{idx}}_chuShOpinion" name="policeHomeChildList[{{idx}}].chuShOpinion" type="text" value="{{row.chuShOpinion}}" class="input-small "/>
					</td>
					<td>
						<input id="policeHomeChildList{{idx}}_juShOpinion" name="policeHomeChildList[{{idx}}].juShOpinion" type="text" value="{{row.juShOpinion}}" class="input-small "/>
					</td>
					<shiro:hasPermission name="affair:affairPoliceHome:edit"><td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#policeHomeChildList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td></shiro:hasPermission>
				</tr>
				</script>
				<script type="text/javascript">
					var affairPoliceHomeChildRowIdx = 0,
							affairPoliceHomeChildTpl = $("#affairPoliceHomeChildTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
					function initData(data){
						$('#policeHomeChildList').html('')
						for (var i = 0; i < data.length; i++) {
							addRow('#policeHomeChildList', affairPoliceHomeChildRowIdx, affairPoliceHomeChildTpl, data[i]);
							affairPoliceHomeChildRowIdx = affairPoliceHomeChildRowIdx + 1;
						}
					}
					$(document).ready(function () {
						var data = ${fns:toJson(affairPoliceHome.policeHomeChildList)};
						initData(data);
					});
				</script>
			</div>
		</div>
		<%--

		修改为主子表样式
		查看时添加 此处不可添加多个
		<div class="control-group">
			<label class="control-label">所需设备：</label>
			<div class="controls">
				<form:input path="device" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建设数量：</label>
			<div class="controls">
				<form:input path="nums" id="num" htmlEscape="false" class="input-xlarge  digits required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单价（元）：</label>
			<div class="controls">
				<form:input path="price" id="price" htmlEscape="false" class="input-xlarge  number required"/>元
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">小计（元）：</label>
			<div class="controls">
				<form:input path="sum" id="sum" htmlEscape="false" class="input-xlarge  number" />元
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:input path="content" id="content" htmlEscape="false" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经办人：</label>
			<div class="controls">
				<form:input path="jingBan" id="content" htmlEscape="false" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申报单位审核人：</label>
			<div class="controls">
				<form:input path="unitShRen" id="content" htmlEscape="false" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处工会审核意见：</label>
			<div class="controls">
				<form:input path="chuShOpinion" id="content" htmlEscape="false" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">局工会审核意见：</label>
			<div class="controls">
				<form:input path="juShOpinion" id="content" htmlEscape="false" class="input-xlarge " />
			</div>
		</div>--%>
<%--		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filePath" type="files" uploadPath="affair/affairPoliceHome" selectMultiple="true"/>
			</div>
		</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairPoliceHome:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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