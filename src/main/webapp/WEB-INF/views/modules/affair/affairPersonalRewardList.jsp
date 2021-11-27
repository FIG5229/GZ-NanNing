<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人奖励管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairPersonalReward/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPersonalReward/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairPersonalReward/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPersonalReward/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairPersonalReward", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairPersonalReward"}});
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
			top.$.jBox.open("iframe:"+url, "个人获奖",900,520,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairPersonalReward/formDetail?id="+id;
			top.$.jBox.open(url, "个人获奖明细",800,500,{
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
		<li><a href="${ctx}/affair/affairXcUnitReward">集体奖励</a></li>
		<li class="active"><a href="${ctx}/affair/affairPersonalReward">个人奖励</a></li>
		<li><a href="${ctx}/affair/affairRewardInquire/">奖励查询</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairPersonalReward" action="${ctx}/affair/affairPersonalReward/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="4.2个人奖励.xlsx"/>
		<ul class="ul-form">
				<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
			<c:choose>
				<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
					<li><label>单位：</label>
						<sys:treeselect id="unit" name="unitId" value="${affairPersonalReward.unitId}" labelName="unit" labelValue="${affairPersonalReward.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true" isAll="true"/>
					</li>
				</c:when>
				<c:otherwise>
					<li><label>单位：</label>
						<sys:treeselect id="unit" name="unitId" value="${affairPersonalReward.unitId}" labelName="unit" labelValue="${affairPersonalReward.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true" />
					</li>
				</c:otherwise>
			</c:choose>
			<%--<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairPersonalReward.unitId}" labelName="unit" labelValue="${affairPersonalReward.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true" />
			</li>--%>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<%--<li><label>性别：</label>
				<form:select path="sex" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>--%>

			<li><label>荣誉级别：</label>
				<form:select path="level" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_approval_unitLevel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					<%--12.18 调整为与批准机关层次字典一样--%>
					<%--<form:options items="${fns:getDictList('affair_chenghao_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
				</form:select>
			</li>
			<li><label class="width120">批准文件文号：</label>
				<form:input path="fileNo" htmlEscape="false" class="input-medium"/>
			</li>
			<%--<li><label>奖励类别：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_org_reward_punish')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>--%>
			<li><label>奖励名称：</label>
					<%--				<form:input path="rewardName" htmlEscape="false" class="input-medium"/>--%>
				<form:select path="rewardName" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_personnel_rewardCode')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>

			<li><label>批准日期：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairPersonalReward.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairPersonalReward.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairPersonalReward:add">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairPersonalReward/form?id=${affairPersonalReward.id}')"/></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairPersonalReward:edit">
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
<%--				<li class="btns"><input id="btnSubmit2" class="btn btn-primary" type="button" value="审核"/></li>--%>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairPersonalReward:delete">
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairPersonalReward/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairPersonalReward:view">
				<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>

			</shiro:hasPermission>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairPersonalReward'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>姓名</th>
				<%--<th>性别</th>--%>
				<th>单位</th>
				<th>批准时间</th>
				<th>批准机关名称</th>
				<th>奖励名称</th>
				<th>批准文件文号</th>
				<th>推送状态</th>
				<shiro:hasPermission name="affair:affairPersonalReward:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairPersonalReward" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairPersonalReward.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${affairPersonalReward.name}
				</td>
				<%--<td>
						${fns:getDictLabel(affairPersonalReward.sex, 'sex', '')}
				</td>--%>

				<td>
						${affairPersonalReward.approvalUnit}
				</td>
				<td>
					<fmt:formatDate value="${affairPersonalReward.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
<%--					${fns:getDictLabel(affairPersonalReward.unit, 'personal_reward_unit', '')}--%>
					${affairPersonalReward.unit}
				</td>
				<%--<td>
						${fns:getDictLabel(affairPersonalReward.nameCode, 'affair_personnel_rewardCode', '')}
				</td>--%>
				<td>
						${fns:getDictLabel(affairPersonalReward.rewardName, 'affair_personnel_rewardCode', '')}
				</td>
				<td>
					${affairPersonalReward.fileNo}
				</td>
				<td>
						${fns:getDictLabel(affairPersonalReward.pushType, 'push_types', '')}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairPersonalReward.id}')">查看</a>
				<shiro:hasPermission name="affair:affairPersonalReward:edit">
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairPersonalReward/form?id=${affairPersonalReward.id}')">修改</a>
<%--
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/exam/examJcInfoPlus/affairPersonalRewardForm?id=${affairPersonalReward.id}')">推送到奖励信息库</a>
--%>
				</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairPersonalReward:delete">
						<a href="${ctx}/affair/affairPersonalReward/delete?id=${affairPersonalReward.id}" onclick="return confirmx('确认要删除该个人奖励吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>