<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>纪律处分管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		//计算影响期
		function calculateInfPeriod(){
			//计算行政处分影响期  （党纪处分影响期是手动填写）
			if ($("#disciplinaryType").val() == '1' && $('#xzSubOption').val() != null && $('#xzSubOption').val() != '' && $('#approvalDate').val() != '') {
				//计算影响期  行政处分：警告－6个月、记过－12个月、记大过－18个月、降级、撤职－24个月、开除
				$('#influencePeriod').val('');
				var apprDate = $('#approvalDate').val();
				var dateArr = apprDate.split("-");
				var year = parseInt(dateArr[0]);
				var month;
				if(dateArr[1].indexOf("0") == 0){
					month = parseInt(dateArr[1].substring(1));
				}else{
					month = parseInt(dateArr[1]);
				}
				var day = parseInt(dateArr[2]);
				var date1 = new Date(year,month -1,day);
				if($('#xzSubOption').val() == '1'){
					var infPeriod = date1.setMonth(date1.getMonth()+6);
					var date2 = new Date(infPeriod);
					var date3 = date2.setTime(date2.getTime()- 1*24*60*60*1000);
					var date4 = new Date(date3);
					$('#influencePeriod').val(dateToString(date4));
				}else if($('#xzSubOption').val() == '2'){
					var infPeriod = date1.setMonth(date1.getMonth()+12);
					var date2 = new Date(infPeriod);
					var date3 = date2.setTime(date2.getTime()- 1*24*60*60*1000);
					var date4 = new Date(date3);
					$('#influencePeriod').val(dateToString(date4));
				}else if($('#xzSubOption').val() == '3'){
					var infPeriod = date1.setMonth(date1.getMonth()+18);
					var date2 = new Date(infPeriod);
					var date3 = date2.setTime(date2.getTime()- 1*24*60*60*1000);
					var date4 = new Date(date3);
					$('#influencePeriod').val(dateToString(date4));
				}else if($('#xzSubOption').val() == '4'|| $('#xzSubOption').val() == '5'){
					var infPeriod = date1.setMonth(date1.getMonth()+24);
					var date2 = new Date(infPeriod);
					var date3 = date2.setTime(date2.getTime()- 1*24*60*60*1000);
					var date4 = new Date(date3);
					$('#influencePeriod').val(dateToString(date4));
				} else{
					$('#influencePeriod').val('');
				}
			}
		};
		//日期类型转成字符串
		function dateToString(date){
			var year = date.getFullYear();
			var month =(date.getMonth() + 1).toString();
			var day = (date.getDate()).toString();
			if (month.length == 1) {
				month = "0" + month;
			}
			if (day.length == 1) {
				day = "0" + day;
			}
			var dateTime = year + "-" + month + "-" + day;
			return dateTime;
		}
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
			//处分种类改变时触发事件，联动子选项
			$("#disciplinaryType").change(function(){
				showAndHide();
			});
			$("#djSubOption").change(function(){
				dangJi();
			});
			$("#xzSubOption").change(function () {
				calculateInfPeriod();
			});
			$("#approvalDate").change(function () {
				calculateInfPeriod();
			});

			//控制处分种类子选项下拉框的隐藏与显示
			function showAndHide(){
				if($("#disciplinaryType").val() == '1'){
					$('#s2id_defaultSubOption').css('display', 'none');
					$('#defaultSubOption').val("");
					$('#s2id_xzSubOption').css('display', 'inline-block');
					$('#xzSubOption').css('display', 'inline-block');
					$('#s2id_djSubOption').css('display', 'none');
					$('#djSubOption').val("");
					$('#s2id_rySubOption').css('display', 'none');
					$('#rySubOption').val("");
					$('#s2id_dzzSubOption').css('display', 'none');
					$('#dzzSubOption').val("");
					$('#xinxi').css('display', 'inline-block');
					$('#s2id_zzSubOption').css('display', 'none');
					$('#zzSubOption').val("");
					//党组织处理隐藏
					$('#chuliDiv').css('display', 'none');
					$('#chuliDiv').val("");
				}else if($("#disciplinaryType").val() == '2'){
					$('#s2id_defaultSubOption').css('display', 'none');
					$('#defaultSubOption').val("");
					$('#s2id_xzSubOption').css('display', 'none');
					$('#xzSubOption').val("");
					$('#s2id_djSubOption').css('display', 'inline-block');
					$('#djSubOption').css('display', 'inline-block');
					$('#xinxi').css('display', 'none');
					$('#xinxi').val("");
					$('#chuliDiv').css('display', 'inline-block');
					dangJi();
				}
			}
			function dangJi() {
				if($("#disciplinaryType").val() == '2'){
					$('#s2id_defaultSubOption').css('display', 'none');
					$('#defaultSubOption').val("");
					$('#s2id_xzSubOption').css('display', 'none');
					$('#xzSubOption').val("");
					$('#s2id_djSubOption').css('display', 'inline-block');
					$('#djSubOption').css('display', 'inline-block');
					if($("#djSubOption").val() == '1'){
						$('#s2id_zzSubOption').css('display', 'none');
						$('#zzSubOption').val("");
						$('#s2id_xzSubOption').css('display', 'none');
						$('#xzSubOption').val("");
						// $('#s2id_dzzSubOption').css('display', 'none');
						// $('#dzzSubOption').css('display', 'none');
						$('#s2id_rySubOption').css('display', 'inline-block');
						$('#rySubOption').css('display', 'inline-block');
						//党组织处理隐藏
						$('#chuliDiv').css('display', 'none');
						$('#chuliDiv').val("");
						$('#xinxi').css('display', 'inline-block');
					}
					/*else if($("#djSubOption").val() == '2'){
						$('#s2id_zzSubOption').css('display', 'none');
						$('#s2id_xzSubOption').css('display', 'none');
						$('#s2id_rySubOption').css('display', 'none');
						$('#rySubOption').css('display', 'none');
						$('#s2id_dzzSubOption').css('display', 'inline-block');
						$('#dzzSubOption').css('display', 'inline-block');
						$('#xinxi').css('display', 'none');
						$('#chuliDiv').css('display', 'inline-block');
					}*/
				}

			}
			//调用
			showAndHide();
			// dangJi();
		});
		//姓名输入框回车事件绑定
		function setDefaults(){
			//清空
			$("#siren").val('');
			$("#idNumber").val('');
			$("#sex").val('');
			$('#sex').siblings('.select2-container').find('.select2-chosen').text('');
			$("#job").val('');
			$("#zhengzhiFace").val('');
			$('#zhengzhiFace').siblings('.select2-container').find('.select2-chosen').text('');
			$("#unitId").val('');
			$("#unitName").val('');
			$.ajax({
				type:"post",
				url:"${ctx}/personnel/personnelBase/getPersonByName",
				data:{name:$("#name").val()},
				dataType:"json",
				success:function(data){
					if(data.success==true && data.result.length==1){
						$("#siren").val(data.result[0].policeIdNumber);
						$("#idNumber").val(data.result[0].idNumber);
						$("#sex").val(data.result[0].sex);
						$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
						$("#job").val(data.result[0].jobAbbreviation);
						$("#zhengzhiFace").val(data.result[0].sex);
						$('#zhengzhiFace').siblings('.select2-container').find('.select2-chosen').text($("#zhengzhiFace").find("option:selected").text());
						$("#unitId").val(data.result[0].actualUnitId);
						$("#unitName").val(data.result[0].actualUnit);
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
							$("#siren").val(data.result[f.selected].policeIdNumber);
							$("#idNumber").val(data.result[f.selected].idNumber);
							$("#sex").val(data.result[f.selected].sex);
							$('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
							$("#job").val(data.result[f.selected].jobAbbreviation);
							$("#zhengzhiFace").val(data.result[f.selected].sex);
							$('#zhengzhiFace').siblings('.select2-container').find('.select2-chosen').text($("#zhengzhiFace").find("option:selected").text());
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
		};
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="affairDisciplinaryAction" action="${ctx}/affair/affairDisciplinaryAction/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div id="chufen">
			<div class="control-group">
				<label class="control-label">处分相关基本信息 ：</label>
			</div>
			<div class="control-group">
				<label class="control-label">处分：</label>
				<div class="controls">
					<form:select id="disciplinaryType" path="disciplinaryType" class="input-xlarge required" cssStyle="width: 150px;">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('affair_xzchufen')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
						<%--默认下拉框--%>
					<form:select id="defaultSubOption" path="subOption" class="input-xlarge" cssStyle="width: 150px;">
						<form:option value="" label=""/>
					</form:select>
						<%--行政子选项下拉框--%>
					<form:select id="xzSubOption" path="xzSubOption" class="input-xlarge " cssStyle="display: none; width: 150px">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('affair_xz_sub_option')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
						<%--党纪选项下拉框--%>
					<form:select id="djSubOption" path="djSubOption" class="input-xlarge " cssStyle="display: none; width: 150px">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('affair_dj_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
						<%--默认下拉框--%>
					<form:select id="zzSubOption" path="zzSubOption" class="input-xlarge" cssStyle="width: 150px;">
						<form:option value="" label=""/>
					</form:select>
						<%--党纪人员子选项下拉框--%>
					<form:select id="rySubOption" path="rySubOption" class="input-xlarge " cssStyle="display: none; width: 150px">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('affair_dj_sub_option')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
						<%--党纪党组织子选项下拉框--%>
					<%--<form:select id="dzzSubOption" path="dzzSubOption" class="input-xlarge " cssStyle="display: none; width: 150px">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('affair_dj_sub')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>--%>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">处理方式 ：</label>
			</div>
			<div class="control-group">
				<label class="control-label">司法处理：</label>
				<div class="controls">
					<form:input path="sifa" htmlEscape="false" class="input-xlarge"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">组织处理：</label>
				<div class="controls">
					<form:input path="zuzhi" htmlEscape="false" class="input-xlarge"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">其他方式：</label>
				<div class="controls">
					<form:input path="other" htmlEscape="false" class="input-xlarge"/>
				</div>
			</div>
			<%--<div class="control-group">
				<label class="control-label">时间：</label>
				<div class="controls">
					<input id="date" name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${affairDisciplinaryAction.date}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" onchange="calculateInfPeriod()"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">党组织名称：</label>
				<div class="controls">
					<sys:treeselect id="org" name="orgId" value="${affairDisciplinaryAction.orgId}" labelName="org" labelValue="${affairDisciplinaryAction.org}"
									title="所在党支部" url="/affair/affairGeneralSituation/treeData"  allowClear="true" notAllowSelectParent="false" />
				</div>
			</div>--%>
			<div class="control-group">
				<label class="control-label">问题性质：</label>
				<div class="controls">
					<form:select path="nature" class="input-xlarge required">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('affair_wenti')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
            <div class="control-group">
                <label class="control-label">处分单位：</label>
                <div class="controls">
                    <form:select path="cfUnit" class="input-xlarge ">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('affair_cf_unit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </div>
            </div>
			<div class="control-group">
				<label class="control-label">文号：</label>
				<div class="controls">
					<form:input path="fileNum" htmlEscape="false" class="input-xlarge"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">处分决定时间：</label>
				<div class="controls">
					<input id="approvalDate" name="approvalDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
						   value="<fmt:formatDate value="${affairDisciplinaryAction.approvalDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" onchange="calculateInfPeriod()"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">处分期满时间：</label>
				<div class="controls">
					<input id="influencePeriod" name="influencePeriod" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
						   value="<fmt:formatDate value="${affairDisciplinaryAction.influencePeriod}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</div>
			</div>
			<div class="control-group" id="chuliDiv">
            <label class="control-label">党组织处理：</label>
            <div class="controls">
                <form:select path="chuli" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('affair_chuli')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
			<div class="control-group">
				<label class="control-label">发生时间：</label>
				<div class="controls">
					<input name="lrDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
						   value="<fmt:formatDate value="${affairDisciplinaryAction.lrDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">受理时间：</label>
				<div class="controls">
					<input name="chfDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${affairDisciplinaryAction.chfDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">简要问题：</label>
				<div class="controls">
					<form:textarea id="violations" htmlEscape="false" path="violations" rows="8" class="input-xlarge" cssStyle="width: 450px;"/>
				</div>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">是否立案：</label>
            <div class="controls">
                <form:select path="isLian" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
		<div id="xinxi" style="display: none">
			<div class="control-group">
				<label class="control-label">个人基本信息：</label>
			</div>
			<div class="control-group">
				<label class="control-label">姓名：</label>
				<div class="controls">
					<form:input id="name" path="name" htmlEscape="false" class="input-xlarge" onkeydown="if(event.keyCode==13){setDefaults();return false;};"/>
					<span class="help-inline"><font color="red">输入完后请务必按回车自动查询相关信息</font>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">警号：</label>
				<div class="controls">
					<form:input id="siren" path="siren" htmlEscape="false" class="input-xlarge "/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">身份证号：</label>
				<div class="controls">
					<form:input id="idNumber" path="idNumber" htmlEscape="false" class="input-xlarge "/>
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
                <label class="control-label">职务：</label>
                <div class="controls">
                    <form:input id="job" path="job" htmlEscape="false" class="input-xlarge "/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">职级：</label>
                <div class="controls">
                    <form:input path="jobLevel" htmlEscape="false" class="input-xlarge "/>
                </div>
            </div>
			<div class="control-group">
				<label class="control-label">政治面貌：</label>
				<div class="controls">
					<form:select id="zhengzhiFace" path="zhengzhiFace" class="input-xlarge ">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('political_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">单位：</label>
				<div class="controls">
					<sys:treeselect id="unit" name="unitId" value="${affairDisciplinaryAction.unitId}" labelName="unit" labelValue="${affairDisciplinaryAction.unit}"
									title="单位" url="/sys/office/treeData?type=2"  allowClear="true" notAllowSelectParent="false" />
				</div>
			</div>

		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="annex" path="annex" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="annex" type="files" uploadPath="affair/affairDisciplinaryAction" selectMultiple="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairDisciplinaryAction:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
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