<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>投诉举报管理</title>
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
			//处分种类改变时触发事件，联动子选项
			$("#forwardType").change(function(){
				showAndHide();
			});
			$("#ischeck").change(function () {
				isCheck();
			});
			$("#isdispose").change(function () {
				isDispose();
			});
			$("#noPunish").change(function () {
				noPunish();
			});
			$("#bjtype").change(function(){
				bjtypeChange();
			});

			//控制处分种类子选项下拉框的隐藏与显示
			function showAndHide(){
				if($("#forwardType").val() == '0'){
					$('#forwardUnit').css('display', 'none');
				}else if($("#forwardType").val() == '1'){
					$('#forwardUnit').css('display', 'inline-block');
				}
			}
			function isCheck() {
				if($("#ischeck").val() == '1'){
					$('#s2id_defaultSubOption').css('display', 'none');
					//$('#bjtype').css('display', 'inline-block');
					$('#s2id_bjtype').css('display', 'none');
					$('#isdisposeDiv').css('display', 'none');
					$('#resultDiv').css('display', 'none');
				}else if($("#ischeck").val() == '2'){
					$('#s2id_defaultSubOption').css('display', 'none');
					$('#s2id_bjtype').css('display', 'inline-block');
					$('#bjtype').css('display', 'inline-block');
					$('#isdisposeDiv').css('display', 'inline-block');
					if($("#bjtype").val() == '2'){
						$('#resultDiv').css('display', 'none');
					}else if($("#bjtype").val() == '1' || $("#bjtype").val() == '3'){
						$('#resultDiv').css('display', 'inline-block');
					}
				}else if($("#ischeck").val() == '3'){
					$('#s2id_defaultSubOption').css('display', 'none');
					$('#s2id_bjtype').css('display', 'none');
					$('#isdisposeDiv').css('display', 'none');
					$('#resultDiv').css('display', 'none');
				}
			}
			//控制是否纪律处分相关的部分隐藏与显示
			function isDispose() {
				if($("#isdispose").val() == '0'){
					$('#s2id_noPunish').css('display', 'inline-block');
					$('#noPunish').css('display', 'inline-block');
					if($("#noPunish").val() == '5'){//其他方式
						$('#otherMethod').css('display', 'inline-block');
					}
				}else{
					$('#s2id_noPunish').css('display', 'none');
					$('#otherMethod').css('display', 'none');
				}
			}
			//控制无纪律处分相关的部分隐藏与显示
			function noPunish(){
				if($("#noPunish").val() == '5'){//其他方式
					$('#otherMethod').css('display', 'inline-block');
				}else{
					$('#otherMethod').css('display', 'none');
				}
			}
			//查实和部分属实显示处理情况，查否不显示
			function bjtypeChange() {
				if($("#bjtype").val() == '2'){
					$('#resultDiv').css('display', 'none');
				}else if($("#bjtype").val() == '1' || $("#bjtype").val() == '3'){
					$('#resultDiv').css('display', 'inline-block');
				}
			}
			//调用
			showAndHide();
			isCheck();
			isDispose();
			noPunish();

			//问题性质切换
			$("#questionType").change(function () {
				console.log($("#questionType").val());
				switchType();
			});
			function switchType() {
				if ($("#questionType").val() == '1'){
					$("#s2id_zjType").css('display', 'inline-block');
					$("#s2id_sfType").css('display', 'none');
					$("#sfType").val("");
					$("#s2id_jjType").css('display', 'none');
					$("#jjType").val("");
				}else if ($("#questionType").val() == '2'){
					$("#s2id_zjType").css('display',  'none');
					$("#zjType").val("");
					$("#s2id_sfType").css('display', 'inline-block');
					$("#s2id_jjType").css('display', 'none');
					$("#jjType").val("");
				}else if ($("#questionType").val() == '3'){
					$("#s2id_zjType").css('display',  'none');
					$("#zjType").val("");
					$("#s2id_sfType").css('display', 'none');
					$("#sfType").val("");
					$("#s2id_jjType").css('display','inline-block');
				}
			}
			switchType();

		});
		//被反映人姓名输入框回车事件绑定
		function setDefaults(){
				//清空
				$("#repoterIdNumber").val('');
				$("#repoterUnitId").val('');
				$("#repoterUnitName").val('');
				$.ajax({
					type:"post",
					url:"${ctx}/personnel/personnelBase/getPersonByName",
					data:{name:$("#repoter").val()},
					dataType:"json",
					success:function(data){
						if(data.success==true && data.result.length==1){
							$("#repoterIdNumber").val(data.result[0].idNumber);
							$("#repoterUnitId").val(data.result[0].workunitId);
							$("#repoterUnitName").val(data.result[0].workunitName);
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
								$("#repoterIdNumber").val(data.result[f.selected].idNumber);
								$("#repoterUnitId").val(data.result[f.selected].workunitId);
								$("#repoterUnitName").val(data.result[f.selected].workunitName);
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
	<form:form id="inputForm" modelAttribute="affairTousujubaoguanli" action="${ctx}/affair/affairTousujubaoguanli/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">反映人：</label>
			<div class="controls">
				<form:input path="informer" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">反映人身份证号码：</label>
			<div class="controls">
				<form:input path="informerIdNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">反映人单位：</label>
			<div class="controls">
				<form:input path="informerUnit" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">来源渠道：</label>
			<div class="controls">
				<form:select path="source" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_source')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">媒介类型：</label>
			<div class="controls">
				<form:select path="complaintWay" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_tousu')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">线索形式：</label>
			<div class="controls">
				<form:select path="clue" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_clue')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">被反映人：</label>
			<div class="controls">
				<form:input id="repoter" path="repoter" htmlEscape="false" class="input-xlarge required" onkeydown="if(event.keyCode==13){setDefaults();return false;};"/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">被反映人身份证号码：</label>
			<div class="controls">
				<form:input id="repoterIdNumber" path="repoterIdNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">被反映人单位：</label>
			<div class="controls">
				<sys:treeselect id="repoterUnit" name="repoterUnitId" value="${affairTousujubaoguanli.repoterUnitId}" labelName="repoterUnit" labelValue="${affairTousujubaoguanli.repoterUnit}"
						title="被举报人单位" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="false" isAll="true"/>
<%--				&nbsp;&nbsp;其他单位：<form:input id="otherRepoterUnit" path="otherRepoterUnit" htmlEscape="false" class="input-xlarge"/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">其他单位：</label>
			<div class="controls">
				&nbsp;&nbsp;<form:input id="otherRepoterUnit" path="otherRepoterUnit" htmlEscape="false" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">问题性质：</label>
			<div class="controls">
				<form:select id="questionType" path="questionType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_qtType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<form:select id="zjType" path="zjType" class="input-xlarge " cssStyle="display: none">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_zjType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<form:select id="sfType" path="sfType" class="input-xlarge " cssStyle="display: none">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_sfType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<form:select id="jjType" path="jjType" class="input-xlarge " cssStyle="display: none">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_jjType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">简要情况：</label>
			<div class="controls">
				<form:textarea path="situation" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">批转单位：</label>
			<div class="controls">
				<form:select id="forwardType" path="forwardType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('approval_unit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
				<form:input id="forwardUnit" path="forwardUnit" htmlEscape="false" class="input-xlarge required" cssStyle="display: none;"/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">收到单位：</label>
			<div class="controls">
				<form:select path="sdUnit" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_xfjb_unit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">录入时间：</label>
			<div class="controls">
				<input name="entryTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairTousujubaoguanli.entryTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">收到时间：</label>
			<div class="controls">
				<input name="receiveTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${affairTousujubaoguanli.receiveTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否重复：</label>
			<div class="controls">
				<form:select path="isrepeat" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">查处单位：</label>
			<div class="controls">
				<form:select path="ccUnit" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_xfjb_unit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">查办状态：</label>
			<div class="controls">
				<form:select  id="ischeck" path="ischeck" class="input-xlarge required" cssStyle="width: 125px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_cbtype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
					<%--默认下拉框--%>
				<form:select id="defaultSubOption" path="subOption" class="input-xlarge" cssStyle="width: 125px;">
					<form:option value="" label=""/>
				</form:select>
					<%--办结子选项下拉框--%>
				<form:select id="bjtype" path="bjType" class="input-xlarge " cssStyle="display: none;width: 125px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_bjtype_sub')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<div id="resultDiv" style="display: none">&nbsp;&nbsp;处理情况：<form:input id="result" path="result" htmlEscape="false" class="input-xlarge"/></div>
			</div>
		</div>
		<div class="control-group" style="display: none;" id="isdisposeDiv">
			<label class="control-label">是否纪律处分：</label>
			<div class="controls">
				<form:select id="isdispose" path="isdispose" class="input-xlarge required" cssStyle="display: inline-block;width: 60px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
				<form:select id="noPunish" path="noPunish" class="input-xlarge required" cssStyle="display: none;width: 125px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_no_punish_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			&nbsp;&nbsp;<form:input id="otherMethod" path="otherMethod" htmlEscape="false" class="input-xlarge" cssStyle="display: none;"/>
			</div>
		</div>


		<%--<div class="control-group">
			<label class="control-label">审核人：</label>
			<div class="controls">
				<form:input path="revierwe" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核意见：</label>
			<div class="controls">
				<form:input path="auditOpinion" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<%--<div class="control-group" style="display: none">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairTousujubaoguanli.unitId}" labelName="unit" labelValue="${affairTousujubaoguanli.unit}"
								title="单位" url="/sys/office/treeData?type=2"  allowClear="true" notAllowSelectParent="true" />
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="annex" path="annex" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="annex" type="files" uploadPath="affair/affairTousujubaoguanli" selectMultiple="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairTousujubaoguanli:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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