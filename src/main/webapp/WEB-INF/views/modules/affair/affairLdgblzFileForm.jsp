<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>领导廉政干部档案管理</title>
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
			//身份证号输入框回车事件绑定
			/*$("#idNumber").keydown(function(e){
				if(e.keyCode == 13){
					$.ajax({
						url:"${ctx}/affair/affairLdgblzFile/findInfoByIdNumber",
						dataType:"json",
						async:true,//请求是否异步，默认为异步
						data:{"idNumber":$("#idNumber").val()},
						type:"POST",
						success:function(res){
							if (res.result != null){
								$("#name").val(res.result.name);
								$("#sex").val(res.result.sex);
								$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
								$("#unitId").val(res.result.workunitId);
								$('#unitName').val(res.result.workunitName)
								if(res.result.workunitName != undefined && res.result.jobAbbreviation != undefined){
									$("#workUnitJob").val(res.result.workunitName+" "+res.result.jobAbbreviation);
								}else if(res.result.workunitName != undefined && res.result.jobAbbreviation == undefined){
									$("#workUnitJob").val(res.result.workunitName);
								}else if(res.result.workunitName == undefined && res.result.jobAbbreviation != undefined){
									$("#workUnitJob").val(res.result.jobAbbreviation);
								}else{
                                    $("#workUnitJob").val('');
                                }
								$("#birthday").val(res.result.birthday.substring(0,7));
								$("#nativePlace").val(res.result.nativePlace);
							}
						}
					});
				}
			});*/
			//姓名输入框回车事件绑定
			$("#name").keydown(function(e){
				if(e.keyCode == 13){
				    //清空
                    $("#idNumber").val('');
                    $("#sex").val('');
                    $('#sex').siblings('.select2-container').find('.select2-chosen').text('');
                    $("#unitId").val('');
                    $('#unitName').val('')
                    $("#workUnitJob").val('');
                    $("#birthday").val('');
                    $("#nativePlace").val('');
                    $("#rdDate").val();
                    $("#education").val();
					$.ajax({
						type:"post",
						url:"${ctx}/personnel/personnelBase/getPersonByName",
						data:{name:$("#name").val()},
						dataType:"json",
						success:function(data){
							if(data.success == true && data.result.length == 1){
								$("#idNumber").val(data.result[0].idNumber);
								$("#sex").val(data.result[0].sex);
								$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
								$("#unitId").val(data.result[0].workunitId);
								$('#unitName').val(data.result[0].workunitName)
								if(data.result[0].workunitName != undefined && data.result[0].jobAbbreviation != undefined){
									$("#workUnitJob").val(data.result[0].workunitName+" "+data.result[0].jobAbbreviation);
								}else if(data.result[0].workunitName != undefined && data.result[0].jobAbbreviation == undefined){
									$("#workUnitJob").val(data.result[0].workunitName);
								}else if(data.result[0].workunitName == undefined && data.result[0].jobAbbreviation != undefined){
									$("#workUnitJob").val(data.result[0].jobAbbreviation);
								}else{
                                    $("#workUnitJob").val('');
                                }
								$("#birthday").val(data.result[0].birthday);
								$("#nativePlace").val(data.result[0].nativePlace);
								$("#education").val(data.result[0].education);
								$("#rdDate").val(data.result[0].organizationDate);
							}else if(data.success == true && data.result.length > 1){
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
									$("#unitId").val(data.result[f.selected].workunitId);
									$('#unitName').val(data.result[f.selected].workunitName)
									$("#workUnitJob").val(data.result[f.selected].workunitName+" "+data.result[f.selected].jobAbbreviation);
									$("#birthday").val(data.result[f.selected].birthday);
									$("#nativePlace").val(data.result[f.selected].nativePlace);
									$("#education").val(data.result[f.selected].education);
									$("#rdDate").val(data.result[f.selected].organizationDate);
									if(data.result[f.selected].workunitName != undefined && data.result[f.selected].jobAbbreviation != undefined){
										$("#workUnitJob").val(data.result[f.selected].workunitName+" "+data.result[f.selected].jobAbbreviation);
									}else if(data.result[0].workunitName != undefined && data.result[f.selected].jobAbbreviation == undefined){
										$("#workUnitJob").val(data.result[f.selected].workunitName);
									}else if(data.result[f.selected].workunitName == undefined && data.result[f.selected].jobAbbreviation != undefined){
										$("#workUnitJob").val(data.result[f.selected].jobAbbreviation);
									}else{
                                        $("#workUnitJob").val('');
                                    }
									return true;
								};
								top.$.jBox(html, { title: "输入",width: 600, height: 300, submit: submit });
							}else {
								$.jBox.tip('没有查询到该人名相关信息');
							}
						}
					})
				}
			});
		});
		if("sucess"=="${saveResult}"){
			parent.$.jBox.close();
		}
		function save() {
			$("#inputForm").submit();
		}
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="affairLdgblzFile" action="${ctx}/affair/affairLdgblzFile/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<div class="control-group">
				<label class="control-label">姓名：</label>
				<div class="controls">
					<form:input id="name" path="name" htmlEscape="false" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
				</div>
			</div>
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input id="idNumber" path="idNumber" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:select id="sex" path="sex" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairLdgblzFile.unitId}" labelName="unit" labelValue="${affairLdgblzFile.unit}"
								title="工作单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职务：</label>
			<div class="controls">
				<form:input id="workUnitJob" path="workUnitJob" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职务层次：</label>
			<div class="controls">
				<form:input id="jobLevel" path="jobLevel" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职级：</label>
			<div class="controls">
				<form:input path="level" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出生年月：</label>
			<div class="controls">
				<input id="birthday" name="birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairLdgblzFile.birthday}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">入党时间：</label>
			<div class="controls">
				<input id="rdDate" name="rdDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairLdgblzFile.rdDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学历：</label>
			<div class="controls">
				<form:input id="education" path="education" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">籍贯：</label>
			<div class="controls">
				<form:input id="nativePlace" path="nativePlace" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">惩处情况：</label>
			<div class="controls">
				<form:textarea path="chchqk" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">拒礼拒贿、上交礼金礼品情况：</label>
			<div class="controls">
				<form:textarea path="jlqk" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操办婚丧喜庆事宜情况：</label>
			<div class="controls">
				<form:textarea path="cbqk" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">个人事项报告情况：</label>
			<div class="controls">
				<form:textarea path="grqk" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学廉考廉情况：</label>
			<div class="controls">
				<form:textarea path="xlqk" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">落实党风廉政建设责任制考核情况：</label>
			<div class="controls">
				<form:textarea path="lshqk" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">述职述廉情况：</label>
			<div class="controls">
				<form:textarea path="shzhqk" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">民主评议和民主测评情况：</label>
			<div class="controls">
				<form:textarea path="mzhqk" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审计结论：</label>
			<div class="controls">
				<form:textarea path="shjjl" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">问题线索查核情况：</label>
			<div class="controls">
				<form:textarea path="wtqk" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">其他情况：</label>
			<div class="controls">
				<form:textarea path="qtqk" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="annex" path="annex" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="annex" type="files" uploadPath="affair/affairLdgblzFile" selectMultiple="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairLdgblzFile:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="save()"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>