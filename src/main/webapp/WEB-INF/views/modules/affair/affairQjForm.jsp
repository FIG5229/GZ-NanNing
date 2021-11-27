<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>请假信息管理</title>
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
		function save() {
			$("#inputForm").submit();
		}
		/*勾选所有的复选框*/
		chooseAll = function (obj,checkBoxsName) {
			if (obj.checked) {
				var checkboxs = document.getElementsByName(checkBoxsName);
				for (var i = 0; i < checkboxs.length; i++) {
					checkboxs[i].checked = true;
				}
			} else {
				var checkboxs = document.getElementsByName(checkBoxsName);
				for (var i = 0; i < checkboxs.length; i++) {
					checkboxs[i].checked = false;
				}
			}
		};
		//得到被选中的checkbox的数据id
		function getIds(checkBoxsName) {
			var ids = [];
			$("input:checkbox[type=checkbox]:checked").each(function () {
				if ($(this).val() != "on"){
					ids.push($(this).val());
				}
			});
			console.log(ids);
			return ids;
        }
        function setDefaults() {
            //清空
            $('#idNumber').val('');
            $("#sex").val('');
            $('#sex').siblings('.select2-container').find('.select2-chosen').text('');
			$("#unitId").val('');
			$("#unitName").val('');
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
                    }else if(data.success==true && data.result.length>1){
                        var html = '<table id="contentTable" class="table table-striped table-bordered table-condensed">';
                        html += '<thead><tr><th></th><th>姓名</th><th>单位</th><th>身份证号</th></tr></thead>';
                        html += '<tbody>';
                        for(var i=0; i< data.result.length; i++) {
                            html += '<tr><td><input type="radio" name="selected" value="'+i+'"></td>';
                            html += '<td>'+data.result[i].name+'</td>';
                            html += '<td>'+data.result[i].workunitName+'</td>';
                            html += '<td>'+data.result[i].idNumber+'</td>';
                            html += '</tr>';
                        }

                        html +=	'</tbody>';
                        html +=	'</table>';
                        var submit = function (v, h, f) {
                            $('#idNumber').val(data.result[f.selected].idNumber);
                            $("#sex").val(data.result[f.selected].sex);
                            $('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
							$("#unitId").val(data.result[f.selected].actualUnitId);
							$("#unitName").val(data.result[f.selected].actualUnit);
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
	<br/>
	<form:form id="inputForm" modelAttribute="affairQj" action="${ctx}/affair/affairQj/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<%--<div class="control-group">
			<label class="control-label">审核单位：</label>
			<div class="controls">
				<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="0"/>人事部门
				<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="1"/>所在部门
				<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="2"/>局处领导
			</div>
		</div>--%>
        <div class="control-group">
            <label class="control-label">年份：</label>
            <div class="controls">
                <form:input path="year" htmlEscape="false" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">月份：</label>
            <div class="controls">
                <form:input path="month" htmlEscape="false" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required " onkeydown="if(event.keyCode==13){setDefaults();return false;}"/>
                <span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
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
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairQj.unitId}" labelName="unit" labelValue="${affairQj.unit}"
					title="部门" url="/affair/affairLaborOffice/treeData" cssClass="required" allowClear="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">部门：</label>
			<div class="controls">
				<form:input path="department" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">职务：</label>
			<div class="controls">
				<form:input path="job" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">请假日期：</label>
			<div class="controls">
				<input name="qjDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairQj.qjDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">假别：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personnel_xjtype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairQj.startTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairQj.endTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际休假天数：</label>
			<div class="controls">
				<form:input path="qjDay" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">请假备注：</label>
			<div class="controls">
				<form:textarea path="qjRemark" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>


		<div class="form-actions">
<%--			<shiro:hasPermission name="affair:affairElection:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="setStatus(0)"/>&nbsp;</shiro:hasPermission>--%>
			<shiro:hasPermission name="affair:affairQj:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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