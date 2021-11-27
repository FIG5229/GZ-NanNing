<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党组织概况管理</title>
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
		if("sucess"=="${saveResult}"){
			parent.$.jBox.close();
		}

		function verifySort() {
			var sort = $("#sort").val();
			if(sort==''){
				return true;
			}
			var reg="^[0-9-]+$";
			var regus = new RegExp(reg);
			if(regus.test(sort)){
				return true;
			}else{
				$.jBox.tip('请按照页面规则要求填写“排序”字段，请勿填写字母，汉字');
				return false;
			}

		}
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairGeneralSituation/">党组织概况列表</a></li>
		<li class="active"><a href="${ctx}/affair/affairGeneralSituation/form?id=${affairGeneralSituation.id}">党组织概况<shiro:hasPermission name="affair:affairGeneralSituation:edit">${not empty affairGeneralSituation.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairGeneralSituation:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairGeneralSituation" action="${ctx}/affair/affairGeneralSituation/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">党组织名称：</label>
			<div class="controls">
				<form:input path="partyOrganization" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--所属党组织去掉--%>
		<%--<div class="control-group">
			<label class="control-label">所属党组织：</label>
			<div class="controls">
				<sys:treeselect id="ofPartyOrganization" name="ofPartyOrgId" value="${affairGeneralSituation.ofPartyOrgId}" labelName="ofPartyOrganization" labelValue="${affairGeneralSituation.ofPartyOrganization}"
					title="所属党组织" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true" dataMsgRequired="必填信息"/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">党组织成立时间：</label>
			<div class="controls">
				<input name="foundDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairGeneralSituation.foundDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所在单位：</label>
			<div class="controls">
				<%--<sys:treeselect id="unit" name="unitId" value="${affairGeneralSituation.unitId}" labelName="unit" labelValue="${affairGeneralSituation.unit}"
					title="所在单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" dataMsgRequired="必填信息"/>--%>
					<form:select path="unitId" class="input-xlarge required">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('affair_g_s_unit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">党组织类型：</label>
			<div class="controls">
				<form:select path="type1" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_g_s_type1')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属党组织：</label>
			<div class="controls">
				<sys:treeselect id="ofPartyOrg" name="ofPartyOrgId" value="${affairGeneralSituation.ofPartyOrgId}" labelName="ofPartyOrganization" labelValue="${affairGeneralSituation.ofPartyOrganization}"
								title="所属党组织" url="/affair/affairGeneralSituation/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">党组织书记：</label>
			<div class="controls">
				<form:input path="shuji" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">党组织联系人：</label>
			<div class="controls">
				<form:input path="contactor" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人身份证号：</label>
			<div class="controls">
				<form:input path="contactorIdNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
	<%--	<div class="control-group">
			<label class="control-label">党组织书记身份证号：</label>
			<div class="controls">
				<form:input path="shujiIdNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="telephone" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">党组织类别：</label>
			<div class="controls">
				<form:select path="type2" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_g_s_type2')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="	help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">党组织党员数：</label>
			<div class="controls">
				<form:input path="num" htmlEscape="false" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">换届时间：</label>
			<div class="controls">
				<input name="hjDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${affairGeneralSituation.hjDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专兼职情况：</label>
			<div class="controls">
				<form:input path="zzSituation" htmlEscape="false" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">民警数：</label>
			<div class="controls">
				<form:input path="zgNum" htmlEscape="false" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所在地区：</label>
			<div class="controls">
				<form:input path="area" htmlEscape="false" class="input-xlarge"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">是否是高铁领域党支部：</label>
			<div class="controls">
				<form:select path="isGtly" class="input-xlarge">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">党组织简介：</label>
			<div class="controls">
				<form:textarea path="introduction" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" class="input-xlarge"/>
				<span class="help-inline"><font color="red">请按照1-1(公安局-单位序号),2-1-1(公安处-公安处序号-单位序号)，这种格式排序</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairGeneralSituation:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="return  verifySort()"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
</body>
</html>