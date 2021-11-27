<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>奖励查询管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
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

			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairRewardInquire/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairRewardInquire/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairRewardInquire/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairRewardInquire/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairRewardInquire", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairRewardInquire"}});
			});

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "奖励查询",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairRewardInquire"}
			});
		}
		//详情弹窗
		function openDetailDialog(urls) {
			var url = "iframe:"+urls;
			top.$.jBox.open(url, "详情",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairXcUnitReward">集体奖励</a></li>
		<li><a href="${ctx}/affair/affairPersonalReward">个人奖励</a></li>
		<li class="active"><a href="${ctx}/affair/affairRewardInquire/">奖励查询</a></li>
<%--		<shiro:hasPermission name="affair:affairRewardInquire:edit"><li><a href="${ctx}/affair/affairRewardInquire/form">奖励查询添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairRewardInquire" action="${ctx}/affair/affairRewardInquire/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="奖励查询表.xlsx"/>
		<ul class="ul-form">
			<li><label>奖励名称：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>荣誉级别：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_approval_unitLevel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairRewardInquire.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				-
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairRewardInquire.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairRewardInquire.unitId}" labelName="unit" labelValue="${affairRewardInquire.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false" isAll="true"/>
			</li>
			<li><label>奖励种类：</label>
				<form:select path="typeFlag" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('reward_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<%--<li><label>党组织：</label>
				<sys:treeselect id="org" name="orgId" value="${affairRewardInquire.orgId}" labelName="org" labelValue="${affairRewardInquire.org}"
								title="党组织名称" url="/affair/affairGeneralSituation/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>--%>
			<%--<li><label>范围：</label>
				<form:select path="range" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_range')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>--%>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairRewardInquire'"/></li>
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
				<th>单位</th>
				<th>奖励名称</th>
				<th>奖励文号</th>
				<th>批准单位</th>
				<th>奖励级别</th>
				<th>时间</th>
                <th class="handleTd">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairRewardInquire" varStatus="status">
			<>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairRewardInquire.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
			<td>
					${affairRewardInquire.name}
			</td>
			<td>
					${affairRewardInquire.unit}
			</td>
				<td>
					${affairRewardInquire.title}
				</td>
				<td>
					${affairRewardInquire.fileNo}
				</td>
				<td>
					${affairRewardInquire.approvalOrg}
				</td>
				<td>
					${fns:getDictLabel(affairRewardInquire.type, 'affair_approval_unitLevel', '')}
				</td>
			<td>
				<fmt:formatDate value="${affairRewardInquire.date}" pattern="yyyy-MM-dd"/>
			</td>
            <td class="handleTd">
                <c:choose>
                    <c:when test="${affairRewardInquire.flag == '1'}">
                        <a href="javascript:void(0)" onclick="openDetailDialog('${ctx}/affair/affairOrgRewardPunish/formDetail?id=${affairRewardInquire.id}')">查看</a>
                    </c:when>
                    <c:when test="${affairRewardInquire.flag == '2'}">
                        <a href="javascript:void(0)" onclick="openDetailDialog('${ctx}/affair/affairPartyRewardPunish/formDetail?id=${affairRewardInquire.id}')">查看</a>
                    </c:when>
                    <c:when test="${affairRewardInquire.flag == '3'}">
                        <a href="javascript:void(0)" onclick="openDetailDialog('${ctx}/affair/affairXcUnitReward/formDetail?id=${affairRewardInquire.id}')">查看</a>
                    </c:when>
                    <c:when test="${affairRewardInquire.flag == '4'}">
                        <a href="javascript:void(0)" onclick="openDetailDialog('${ctx}/affair/affairPersonalReward/formDetail?id=${affairRewardInquire.id}')">查看</a>
                    </c:when>
                    <c:when test="${affairRewardInquire.flag == '5'}">
                        <a href="javascript:void(0)" onclick="openDetailDialog('${ctx}/affair/affairCollectiveAward/formDetail?id=${affairRewardInquire.id}')">查看</a>
                    </c:when>
                    <c:when test="${affairRewardInquire.flag == '6'}">
                        <a href="javascript:void(0)" onclick="openDetailDialog('${ctx}/affair/affairPersonalAward/formDetail?id=${affairRewardInquire.id}')">查看</a>
                    </c:when>
                    <c:when test="${affairRewardInquire.flag == '7'}">
                        <a href="javascript:void(0)" onclick="openDetailDialog('${ctx}/affair/affairTwUnitAward/formDetail?id=${affairRewardInquire.id}')">查看</a>
                    </c:when>
                    <c:otherwise>
                        <a href="javascript:void(0)" onclick="openDetailDialog('${ctx}/affair/affairTwPersonalAward/formDetail?id=${affairRewardInquire.id}')">查看</a>
                    </c:otherwise>
                </c:choose>

            </td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>