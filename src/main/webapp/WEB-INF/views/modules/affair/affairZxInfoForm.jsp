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
							return true;
						};
						top.$.jBox(html, { title: "输入",width: 600, height: 300, submit: submit });
					}else {
						$.jBox.tip('没有查询到该人名相关信息');
					}
				}
			})
		}
		//子表导出
		function exportChild(id) {
			if (id != null || id != undefined){
				window.location.href='${ctx}/affair/affairZxInfo/exportChild?id='+id;
			}
		}
		function importChild(id){
			let url = "iframe:${ctx}/affair/affairZxInfo/child/import?id="+id+"&fileName=金秋助学.xlsx";
			let refreshId = id;
			if (id == null || id == undefined || id ==""){
				url = "iframe:${ctx}/affair/affairZxInfo/child/import?id=${id}&fileName=金秋助学.xlsx";
				refreshId = '${id}';
			} else {
				url = "iframe:${ctx}/affair/affairZxInfo/child/import?id="+id+"&fileName=金秋助学.xlsx";
			}
			top.$.jBox.open(url,
					"导入",800,520,{title:"导入数据", buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){refreshData(refreshId)}});
		}
		function refreshData(id) {
			$.getJSON("${ctx}/affair/affairZxInfo/getChildById",{"id":id},function (result) {
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
	<form:form id="inputForm" modelAttribute="affairZxInfo" action="${ctx}/affair/affairZxInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">时间：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${affairZxInfo.date}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairZxInfo.unitId}" labelName="unit" labelValue="${affairZxInfo.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="false" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">顺号：</label>
			<div class="controls">
				<form:input id="shun" path="shun" htmlEscape="false" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">民警姓名：</label>
			<div class="controls">
				<form:input id="name" path="name" htmlEscape="false" class="input-xlarge " onkeydown="if(event.keyCode==13){setDefaults();return false;};"/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">医保号：</label>
			<div class="controls">
				<form:input id="medicaNumber" path="medicaNumber" htmlEscape="false" class="input-xlarge " />
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
			<label class="control-label">补助类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_zxtype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">子女姓名：</label>
			<div class="controls">
				<form:input path="childName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">子女性别：</label>
			<div class="controls">
				<form:select path="childSex" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">子女出生时间：</label>
			<div class="controls">
				<input name="childBirthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${affairZxInfo.childBirthday}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
<%--			9.3反馈更新 拆分为院校和专业--%>
		<div class="control-group">
			<label class="control-label">就读院校：</label>
			<div class="controls">
				<form:input path="childSchoolMajor" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所学专业：</label>
			<div class="controls">
				<form:input path="childMajor" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学校类别：</label>
			<div class="controls">
				<form:select path="childSchoolType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_zxctype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学年制：</label>
			<div class="controls">
				<form:select path="childYearSystem" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_zx_year_system')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">就读年级：</label>
			<div class="controls">
				<form:input path="childGrade" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">补助金额（元）：</label>
            <div class="controls">
                <form:input path="money" htmlEscape="false" class="input-xlarge  number"/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remarks" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div>
			<div class="controls">
				<a href="${ctx}/file/template/download?fileName=民警子女新入学一次性困难资助申请表.docx">《民警子女新入学一次性困难资助申请表（模板）》下载</a>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">《助学申报表》上传：</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filePath" type="files" uploadPath="affair/affairZxInfo" selectMultiple="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairZxInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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