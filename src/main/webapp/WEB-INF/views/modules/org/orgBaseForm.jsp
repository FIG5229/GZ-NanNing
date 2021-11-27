<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构基本信息管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.order_box .myTab { width: 825px; clear: right; height: 27px; border-bottom: 2px solid #A10000; }
		.order_box .myTab .close { width: 80px; height: 18px; border-top: 1px solid #dedede; border-left: 1px solid #dedede; border-right: 1px solid #dedede;
			background: #f1f1f1; color: #000; text-align: center; float: left; margin-right: 5px; padding-top: 8px;font-size: 13px; }
		.order_box .myTab .open { width: 82px; height: 20px; background: #A10000; color: #fff; text-align: center; float: left; margin-right: 5px;
			padding-top: 8px; overflow: hidden; }
		.order_box ul li { cursor: pointer; display: list-item; list-style:none; }
		.cntorder{
			width: auto;
			height: auto;
		}
	</style>

	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#baseInfoForm").validate({
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
		//选项卡切换
		$(function () {
			$(".myTab li").click(function () {
				var index_tab = $(this).parent().children().index($(this)); //选项卡的索引值
				$(this).parent().find(".open").removeClass("open").addClass("close"); //选项卡背景处理
				$(this).removeClass("close").addClass("open");
				var content_obj = $(".cntorder") //内容节点
				content_obj.hide();
				content_obj.eq(index_tab).show();
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
        <li><a href="${ctx}/sys/office/list?id=${office.parent.id}&parentIds=${office.parentIds}">机构列表</a></li>
		<li class="active"><a href="${ctx}/sys/office/form?id=${office.id}&parent.id=${office.parent.id}">机构<shiro:hasPermission name="sys:office:edit">${not empty office.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:office:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<div class="order_box">
		<div class="myTab">
			<ul>
				<li class="open">基本信息</li>
				<li class="close">编制信息</li>
				<li class="close">职数信息</li>
				<li class="close">奖励信息</li>
				<li class="close">通讯信息</li>
			</ul>
		</div>
		<%--基本信息的内容--%>
		<div class="cntorder" >
			<br/>
			<form:form id="baseInfoForm" modelAttribute="office" action="${ctx}/sys/office/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="control-group">
					<label class="control-label">上级单位名称：</label>
					<div class="controls">
							<%--<sys:treeselect id="superUnitName" name="superUnitName" value="${office.superUnitName}" labelName="superUnitName" labelValue="${office.superUnitName}"
								title="上级单位名称" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>--%>
						<sys:treeselect id="office" name="parent.id" value="${office.parent.id}" labelName="parent.name" labelValue="${office.parent.name}"
							title="上级单位名称" url="/sys/office/treeData" extId="${office.id}" cssClass="required" allowClear="${office.currentUser.admin}" dataMsgRequired="必填信息"/>
								<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">单位全称：</label>
					<div class="controls">
						<form:input path="fullName" htmlEscape="false" class="input-xlarge required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">单位简称：</label>
					<div class="controls">
						<form:input path="abbreviation" htmlEscape="false" class="input-xlarge "/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">单位名称：</label>
					<div class="controls">
						<form:input path="name" htmlEscape="false" class="input-xlarge required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">单位隶属关系：</label>
					<div class="controls">
						<form:input path="relationship" htmlEscape="false" class="input-xlarge "/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">单位性质类别：</label>
					<div class="controls">
						<form:select path="characterType" class="input-xlarge ">
							<form:options items="${fns:getDictList('org_character_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">单位级别：</label>
					<div class="controls">
						<form:select path="level" class="input-xlarge ">
							<form:options items="${fns:getDictList('org_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">机构类型:</label>
					<div class="controls">
						<form:select path="type" class="input-medium">
							<form:options items="${fns:getDictList('sys_office_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">机构级别:</label>
					<div class="controls">
						<form:select path="grade" class="input-medium">
							<form:options items="${fns:getDictList('sys_office_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">是否可用:</label>
					<div class="controls">
						<form:select path="useable">
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">单位所在政区：</label>
					<div class="controls">
						<form:input path="unitArea" htmlEscape="false" class="input-xlarge "/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">单位所在层级：</label>
					<div class="controls">
						<form:select path="hierarchy1" class="input-xlarge ">
							<form:options items="${fns:getDictList('org_hierarchy1')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">上级单位代码：</label>
					<div class="controls">
						<form:input path="superUnitCode" htmlEscape="false" class="input-xlarge "/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">单位所属部门与警种：</label>
					<div class="controls">
						<form:input path="depPolic" htmlEscape="false" class="input-xlarge "/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">单位编码：</label>
					<div class="controls">
						<form:input path="code" htmlEscape="false" maxlength="100"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">单位类别：</label>
					<div class="controls">
						<form:select path="unitType" class="input-xlarge ">
							<form:options items="${fns:getDictList('org_unit_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">单位负责人：</label>
					<div class="controls">
						<form:input path="leader" htmlEscape="false" class="input-xlarge "/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">单位成立批准时间：</label>
					<div class="controls">
						<input name="foundDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							   value="<fmt:formatDate value="${office.foundDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">单位成立批准文号：</label>
					<div class="controls">
						<form:input path="approvalFileNo" htmlEscape="false" class="input-xlarge "/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">单位撤消批准时间：</label>
					<div class="controls">
						<input name="cancelDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							   value="<fmt:formatDate value="${office.cancelDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">单位撤消批准文号：</label>
					<div class="controls">
						<form:input path="cancelFileNo" htmlEscape="false" class="input-xlarge "/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">单位撤消批准机关名称：</label>
					<div class="controls">
						<form:input path="cancelOrg" htmlEscape="false" class="input-xlarge "/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">单位代码：</label>
					<div class="controls">
						<form:input path="unitDm" htmlEscape="false" class="input-xlarge "/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">撤销标识：</label>
					<div class="controls">
						<form:input path="cncelIdentification" htmlEscape="false" class="input-xlarge "/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">派出所类型：</label>
					<div class="controls">
						<form:input path="policeStationType" htmlEscape="false" class="input-xlarge "/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">派出所管理户数：</label>
					<div class="controls">
						<form:input path="huNum" htmlEscape="false" class="input-xlarge  digits"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">单位层次：</label>
					<div class="controls">
						<form:input path="hierarchy2" htmlEscape="false" class="input-xlarge "/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">虚单位：</label>
					<div class="controls">
						<form:input path="emptyUnit" htmlEscape="false" class="input-xlarge "/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">派出所人数：</label>
					<div class="controls">
						<form:input path="personNum" htmlEscape="false" class="input-xlarge  digits"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">公务员系统编码：</label>
					<div class="controls">
						<form:input path="sysCode" htmlEscape="false" class="input-xlarge "/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">法人单位标识：</label>
					<div class="controls">
						<form:input path="legalIdentification" htmlEscape="false" class="input-xlarge "/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">登记管理机关：</label>
					<div class="controls">
						<form:input path="registerOrg" htmlEscape="false" class="input-xlarge "/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">业务范围：</label>
					<div class="controls">
						<form:input path="scope" htmlEscape="false" class="input-xlarge "/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">经费来源：</label>
					<div class="controls">
						<form:input path="source" htmlEscape="false" class="input-xlarge "/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">单位成立批准机关名称：</label>
					<div class="controls">
						<form:input path="approvalOrg" htmlEscape="false" class="input-xlarge "/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">机构更新时间：</label>
					<div class="controls">
						<input name="orgUpdate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							   value="<fmt:formatDate value="${office.orgUpdate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">单位建立党组织情况：</label>
					<div class="controls">
						<form:select path="buildSituation" class="input-xlarge ">
							<form:options items="${fns:getDictList('org_build_situation')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-actions">
					<shiro:hasPermission name="sys:office:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
		</div>
		<%--编制信息显示的内容--%>
		<div class="cntorder" style="display: none;">
			<iframe id="orgBianzhiContent" src="${ctx}/org/orgBianzhi?orgId=${office.id}" width="100%" height="650px" frameborder="0"></iframe>
		</div>
        <%--职数信息显示的内容--%>
		<div class="cntorder" style="display: none;">
            <iframe id="orgJobNumberContent" src="${ctx}/org/orgJobNumber?orgId=${office.id}" width="100%" height="650px" frameborder="0"></iframe>
        </div>
		<%--奖励信息显示的内容--%>
		<div class="cntorder" style="display: none;">
			<iframe id="orgRewardContent" src="${ctx}/org/orgReward?orgId=${office.id}" width="100%" height="650px" frameborder="0"></iframe>
		</div>
        <%--通讯信息显示的内容--%>
		<div class="cntorder" style="display: none;">
            <iframe id="orgCommunicationContent" src="${ctx}/org/orgCommunication?orgId=${office.id}" width="100%" height="650px" frameborder="0"></iframe>
        </div>
	</div>


	<%--<form:form id="inputForm" modelAttribute="orgBase" action="${ctx}/org/orgBase/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">单位全称：</label>
			<div class="controls">
				<form:input path="fullName" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位简称：</label>
			<div class="controls">
				<form:input path="abbreviation" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位隶属关系：</label>
			<div class="controls">
				<form:input path="relationship" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位性质类别：</label>
			<div class="controls">
				<form:select path="characterType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('org_character_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位级别：</label>
			<div class="controls">
				<form:select path="level" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('org_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位所在政区：</label>
			<div class="controls">
				<form:input path="unitArea" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位所在层级：</label>
			<div class="controls">
				<form:select path="hierarchy1" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('org_hierarchy1')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上级单位名称：</label>
			<div class="controls">
				<sys:treeselect id="superUnitName" name="superUnitName" value="${orgBase.superUnitName}" labelName="superUnitName" labelValue="${orgBase.superUnitName}"
					title="上级单位名称" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
				&lt;%&ndash;<div class="controls">
					<sys:treeselect id="office" name="parent.id" value="${office.parent.id}" labelName="parent.name" labelValue="${office.parent.name}"
						title="机构" url="/sys/office/treeData" extId="${office.id}" cssClass="" allowClear="${office.currentUser.admin}"/>
				</div>&ndash;%&gt;
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上级单位代码：</label>
			<div class="controls">
				<form:input path="superUnitCode" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位所属部门与警种：</label>
			<div class="controls">
				<form:input path="depPolic" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位编码：</label>
			<div class="controls">
				<form:input path="unitCode1" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位类别：</label>
			<div class="controls">
				<form:select path="unitType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('org_unit_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位负责人：</label>
			<div class="controls">
				<form:input path="leader" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位成立批准时间：</label>
			<div class="controls">
				<input name="foundDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${orgBase.foundDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位成立批准文号：</label>
			<div class="controls">
				<form:input path="approvalFileNo" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位撤消批准时间：</label>
			<div class="controls">
				<input name="cancelDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${orgBase.cancelDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位撤消批准文号：</label>
			<div class="controls">
				<form:input path="cancelFileNo" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位撤消批准机关名称：</label>
			<div class="controls">
				<form:input path="cancelOrg" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册资金：</label>
			<div class="controls">
				<form:input path="fund" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位代码：</label>
			<div class="controls">
				<form:input path="unitCode2" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">撤销标识：</label>
			<div class="controls">
				<form:input path="cncelIdentification" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">派出所类型：</label>
			<div class="controls">
				<form:input path="policeStationType" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">派出所管理户数：</label>
			<div class="controls">
				<form:input path="huNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位层次：</label>
			<div class="controls">
				<form:input path="hierarchy2" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">虚单位：</label>
			<div class="controls">
				<form:input path="emptyUnit" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">派出所人数：</label>
			<div class="controls">
				<form:input path="personNum" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公务员系统编码：</label>
			<div class="controls">
				<form:input path="sysCode" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法人单位标识：</label>
			<div class="controls">
				<form:input path="legalIdentification" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登记管理机关：</label>
			<div class="controls">
				<form:input path="registerOrg" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务范围：</label>
			<div class="controls">
				<form:input path="scope" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经费来源：</label>
			<div class="controls">
				<form:input path="source" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位成立批准机关名称：</label>
			<div class="controls">
				<form:input path="approvalOrg" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构更新时间：</label>
			<div class="controls">
				<input name="orgUpdate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${orgBase.orgUpdate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位建立党组织情况：</label>
			<div class="controls">
				<form:select path="buildSituation" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('org_build_situation')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="org:orgBase:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>--%>
</body>
</html>