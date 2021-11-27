<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>信息采用管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairInformationAdoption/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairInformationAdoption/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairInformationAdoption/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairInformationAdoption/");
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
					afterPrint:function (){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairInformationAdoption", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairInformationAdoption"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "信息采用",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairInformationAdoption"}
			});
		}
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairInformationAdoption/formDetail?id="+id;
			top.$.jBox.open(url, "信息采用",800,500,{
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
		<li><a href="${ctx}/affair/affairInformationReport/">信息上报情况统计</a></li>
		<li class="active"><a href="${ctx}/affair/affairInformationAdoption/">信息采用情况统计</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairInformationAdoption" action="${ctx}/affair/affairInformationAdoption/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="信息采用.xlsx"/>
		<ul class="ul-form">
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairInformationAdoption.unitId}" labelName="unit" labelValue="${affairInformationAdoption.unit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairInformationAdoption.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairInformationAdoption.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairInformationAdoption:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairInformationAdoption/form?id=${affairInformationAdoption.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairInformationAdoption/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairInformationAdoption'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th rowspan="2"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th rowspan="2">序号</th>
				<th rowspan="2">单位</th>
				<th rowspan="2">时间</th>
				<th colspan="4">信息动态（篇）</th>
				<th colspan="4">工作简报（篇）</th>
				<th colspan="4">调研文章（篇）</th>
				<th colspan="4">领导批示</th>
				<th colspan="3">宣传报道</th>
				<th colspan="4">其他材料（篇）</th>
				<th rowspan="2">得分</th>
				<th rowspan="2">排名</th>
				<th rowspan="2">备注</th>
				<shiro:hasPermission name="affair:affairInformationAdoption:edit"><th id="handleTh" rowspan="2">操作</th></shiro:hasPermission>
			</tr>
			<tr>
				<th>公安处</th>
				<th>公安局</th>
				<th>部局</th>
				<th>公安部</th>
				<th>公安处</th>
				<th>公安局</th>
				<th>部局</th>
				<th>公安部</th>
				<th>公安处</th>
				<th>公安局</th>
				<th>部局</th>
				<th>公安部</th>
				<th>公安处</th>
				<th>公安局</th>
				<th>部局</th>
				<th>公安部</th>
				<th>地市级</th>
				<th>省部级</th>
				<th>中央级</th>
				<th>公安处</th>
				<th>公安局</th>
				<th>部局</th>
				<th>公安部</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairInformationAdoption" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairInformationAdoption.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
<%--								${affairInformationAdoption.number}--%>
				</td>
				<td>
					${affairInformationAdoption.unit}
				</td>
				<td>
					<fmt:formatDate value="${affairInformationAdoption.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairInformationAdoption.xxChu}
				</td>
				<td>
					${affairInformationAdoption.xxJu}
				</td>
				<td>
					${affairInformationAdoption.xxBuJu}
				</td>
				<td>
					${affairInformationAdoption.xxBu}
				</td>
				<td>
					${affairInformationAdoption.gzChu}
				</td>
				<td>
					${affairInformationAdoption.gzJu}
				</td>
				<td>
					${affairInformationAdoption.gzBuJu}
				</td>
				<td>
					${affairInformationAdoption.gzBu}
				</td>
				<td>
					${affairInformationAdoption.dyChu}
				</td>
				<td>
					${affairInformationAdoption.dyJu}
				</td>
				<td>
					${affairInformationAdoption.dyBuJu}
				</td>
				<td>
					${affairInformationAdoption.dyBu}
				</td>
				<td>
					${affairInformationAdoption.psChu}
				</td>
				<td>
					${affairInformationAdoption.psJu}
				</td>
				<td>
					${affairInformationAdoption.psBuJu}
				</td>
				<td>
					${affairInformationAdoption.psBu}
				</td>
				<td>
					${affairInformationAdoption.xcDs}
				</td>
				<td>
					${affairInformationAdoption.xcSb}
				</td>
				<td>
					${affairInformationAdoption.xcZy}
				</td>
				<td>
					${affairInformationAdoption.otherChu}
				</td>
				<td>
					${affairInformationAdoption.otherJu}
				</td>
				<td>
					${affairInformationAdoption.otherBuJu}
				</td>
				<td>
					${affairInformationAdoption.otherBu}
				</td>
				<td>
					${affairInformationAdoption.score}
				</td>
				<td>
					${affairInformationAdoption.rank}
				</td>
				<td>
					${affairInformationAdoption.remark}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairInformationAdoption.id}')">查看</a>
				<shiro:hasPermission name="affair:affairInformationAdoption:edit">
					<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairInformationAdoption/form?id=${affairInformationAdoption.id}')">修改</a>
					<a href="${ctx}/affair/affairInformationAdoption/delete?id=${affairInformationAdoption.id}" onclick="return confirmx('确认要删除该信息采用吗？', this.href)">删除</a>
				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>