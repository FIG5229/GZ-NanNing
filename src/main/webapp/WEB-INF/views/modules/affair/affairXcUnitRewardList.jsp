<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单位集体奖励表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairXcUnitReward/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairXcUnitReward/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairXcUnitReward/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairXcUnitReward/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);

			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairXcUnitReward", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairXcUnitReward"}});
			});

			$("#print").click(function(){
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$("#contentTable").printThis({
					debug: false,
					importCSS: false,
					importStyle: false,
					printContainer: true,
					loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css"],
					pageTitle: "打印",
					removeInline: false,
					printDelay: 333,
					header: null,
					formValues: false,
					afterPrint:function(){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "单位获奖",800,520,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairXcUnitReward/formDetail?id="+id;
			top.$.jBox.open(url, "单位获奖详情",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairXcUnitReward/">集体奖励</a></li>
		<li><a href="${ctx}/affair/affairPersonalReward/">个人奖励</a></li>
		<li><a href="${ctx}/affair/affairRewardInquire/">奖励查询</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairXcUnitReward" action="${ctx}/affair/affairXcUnitReward/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="4.1集体奖励.xlsx"/>
		<ul class="ul-form">
			<li><label>批准日期：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairXcUnitReward.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				-
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairXcUnitReward.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<%--<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairXcUnitReward.unitId}" labelName="unit" labelValue="${affairXcUnitReward.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
								affair_approval_unit
			</li>--%>

			<li class="control-group">
				<label>批准单位：</label>
				<form:select path="approvalUnit" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_approval_unit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>

			<li class="control-group">
				<label>奖励类别：</label>
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_org_reward_punish')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="control-group">
				<label>荣誉级别：</label>
				<form:select path="level" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_approval_unitLevel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<%--<li><label>批准单位：</label>
				<sys:treeselect id="approvalUnit" name="approvalUnitId" value="${affairXcUnitReward.approvalUnitId}" labelName="approvalUnit" labelValue="${affairXcUnitReward.approvalUnit}"
								title="批准单位" url="/sys/office/treeData?type=2" allowClear="true" notAllowSelectParent="false"/>			</li>
			<li><label>奖励名称:</label>
								title="批准单位" url="/sys/office/treeData?type=2" allowClear="true" notAllowSelectParent="false"/>
			</li>--%>

			<li><label>奖励名称:</label>
				<form:select path="nameCode" class="input-xlarge required" >
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_reward_code')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>

			<li><label>批准文号：</label>
				<form:input path="fileNo" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairXcUnitReward:add">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairXcUnitReward/form?id=${affairXcUnitReward.id}')"/></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairXcUnitReward:edit">
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairXcUnitReward:delete">
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairXcUnitReward/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairXcUnitReward'"/></li>
			<li class="clearfix"></li>
		</ul>
		<form:hidden path="rewardType"></form:hidden>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>批准时间</th>
				<th>受奖单位名称</th>
				<th>批准单位</th>
				<th>奖励名称</th>
				<th>批准文号</th>
				<th>推送状态</th>
				<shiro:hasPermission name="affair:affairXcUnitReward:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairXcUnitReward" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairXcUnitReward.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<fmt:formatDate value="${affairXcUnitReward.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairXcUnitReward.unit}
				</td>
				<td>
						${fns:getDictLabel(affairXcUnitReward.approvalUnit, 'affair_approval_unit', '')}
				</td>
				<%--<td>
						${affairXcUnitReward.name}
				</td>--%>
				<td>
						${fns:getDictLabel(affairXcUnitReward.nameCode, 'affair_reward_code', '')}
				</td>
				<td>
					${affairXcUnitReward.fileNo}
				</td>
				<td>
						${fns:getDictLabel(affairXcUnitReward.pushType, 'push_types', '')}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairXcUnitReward.id}')">查看</a>
				<shiro:hasPermission name="affair:affairXcUnitReward:edit">
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairXcUnitReward/form?id=${affairXcUnitReward.id}')">修改</a>
<%--
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/exam/examJcInfoPlus/affairXcUnitRewardform?id=${affairXcUnitReward.id}')">推送到奖励信息库</a>
--%>
				</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairXcUnitReward:delete">
						<a href="${ctx}/affair/affairXcUnitReward/delete?id=${affairXcUnitReward.id}" onclick="return confirmx('确认要删除该单位集体奖励吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>