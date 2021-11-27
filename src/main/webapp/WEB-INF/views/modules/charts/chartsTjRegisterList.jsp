<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团员名册管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairTjRegister/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTjRegister/list");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairTjRegister/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTjRegister/list");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);

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
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairTjRegister", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairTjRegister/list?partyBranchId=${affairTjRegister.partyBranchId}"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "团员名册",900,700,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairTjRegister/list?partyBranchId=${affairTjRegister.partyBranchId}"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairTjRegister/formDetail?id="+id;
			top.$.jBox.open(url, "团员名册详情",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		//批量修改工会组织
		function editUnitByIds(url, myCheckBox, checkBoxsName) {
			var editIds = getIds(checkBoxsName);
			if (editIds.length > 0) {
				var isStr = editIds.join(',');
				top.$.jBox.open("iframe:" + url + "?ids=" + isStr, "修改团组织", 700, 520, {
					buttons: {"关闭": true},
					loaded: function () {
						$(".jbox-content", top.document).css("overflow-y", "hidden");
					}, closed: function () {
						self.location.href = '${ctx}/affair/affairTjRegister/list?partyBranchId=${affairTjRegister.partyBranchId}';
					}
				});
			} else {
				$.jBox.tip('请先选择要修改的内容');
			}
		};

	</script>
</head>
<body>
<%--	<ul class="nav nav-tabs">--%>
<%--		<li class="active"><a href="${ctx}/affair/affairTjRegister/list?partyBranchId=${affairTjRegister.partyBranchId}">团员名册列表</a></li>--%>
<%--	</ul>--%>
	<form:form id="searchForm" modelAttribute="affairTjRegister" action="" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="团员名册表.xlsx"/>
		<%--<ul class="ul-form">
			<li><label>警号：</label>
				<form:input path="policeNo" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			&lt;%&ndash;<li><label>注册时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairTjRegister.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairTjRegister.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>&ndash;%&gt;
			<li><label>团内职务：</label>
				<form:select path="job" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_tnjob')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
            <li><label>所在团组织：</label>
                <sys:treeselect id="partyBranch" name="partyBranchId" value="${affairTjRegister.partyBranchId}"
                                labelName="partyBranch" labelValue="${affairTjRegister.partyBranch}"
                                title="所在团组织" url="/affair/affairTwBase/treeData" cssClass="input-small"
                                allowClear="true" disabled="disabled"/>
            </li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairTnActivityEnroll:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairTjRegister/form?id=${affairTjRegister.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairTjRegister/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				<li class="btns"><input class="btn btn-primary" type="button" value="团组织调动"
										onclick="editUnitByIds('${ctx}/affair/affairTjRegister/editTwBaseDialog','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairTjRegister/list?partyBranchId=${affairTjRegister.partyBranchId}'"/></li>
			<li class="clearfix"></li>
		</ul>--%>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
<%--				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>--%>
				<th>序号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>出生日期</th>
				<%--公用页面 type区分--%>
				<c:if test="${type == 1}">
					<th>学历</th>
				</c:if>
<%--				<th>注册时间</th>--%>
				<th>所在团组织</th>
				<th>备注</th>
<%--				<th id="handleTh">操作</th>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTjRegisterInfo" varStatus="status">
			<tr>
<%--				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTjRegisterInfo.id}"/></td>--%>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${affairTjRegisterInfo.name}
				</td>
				<td>
						${fns:getDictLabel(affairTjRegisterInfo.sex, 'sex', '')}
				</td>
				<td>
						<fmt:formatDate value="${affairTjRegisterInfo.birthday}" pattern="yyyy-MM-dd"/>
				</td>
				<c:if test="${type == 1}">
					<td>
						${affairTjRegisterInfo.education}
					</td>
				</c:if>
				<%--<td>
					<fmt:formatDate value="${affairTjRegister.date}" pattern="yyyy-MM-dd"/>
				</td>--%>
				<td>
					${affairTjRegisterInfo.partyBranch}
				</td>
				<td>
					${affairTjRegisterInfo.remark}
				</td>
				<%--<td class="handleTd">
					<a onclick="openDetailDialog('${affairTjRegisterInfo.id}')">查看</a>
					<shiro:hasPermission name="affair:affairTjRegister:edit">
					&lt;%&ndash;<c:if test="${affairTjRegister.createBy.id == fns:getUser().id}">&ndash;%&gt;
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTjRegister/form?id=${affairTjRegisterInfo.id}')">修改</a>
						<a href="${ctx}/affair/affairTjRegister/delete?id=${affairTjRegisterInfo.id}&partyBranchId=${affairTjRegister.partyBranchId}" onclick="return confirmx('确认要删除该团员名册吗？', this.href)">删除</a>
					&lt;%&ndash;</c:if>&ndash;%&gt;
					</shiro:hasPermission>
				</td>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>