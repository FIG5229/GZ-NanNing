<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>比武团体成绩管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairTeamPerformance/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTeamPerformance/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairTeamPerformance/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTeamPerformance/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairTeamPerformance", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairTeamPerformance"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "比武团体成绩",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairTeamPerformance"}
			});
		}
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairTeamPerformance/formDetail?id="+id;
			top.$.jBox.open(url, "比武团体成绩",800,500,{
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
		<li class="active"><a href="${ctx}/affair/affairTeamPerformance/">单位总成绩</a></li>
		<li><a href="${ctx}/affair/affairIndividualResults/">个人总成绩</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairTeamPerformance" action="${ctx}/affair/affairTeamPerformance/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="比武团体成绩.xlsx"/>
		<ul class="ul-form">
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairTeamPerformance.unitId}" labelName="unit" labelValue="${affairTeamPerformance.unit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>比武开始时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairTeamPerformance.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairTeamPerformance.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairTeamPerformance:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairTeamPerformance/form?id=${affairTeamPerformance.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairTeamPerformance/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairTeamPerformance'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>比武开始时间</th>
				<th>比武结束时间</th>
				<th>比武名称</th>
				<th>单位</th>
				<th>项目一</th>
				<th>项目二</th>
				<th>项目三</th>
				<th>项目四</th>
				<th>项目五</th>
				<th>项目六</th>
				<th>总成绩</th>
				<th>排名</th>
				<th>备注</th>
				<shiro:hasPermission name="affair:affairTeamPerformance:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTeamPerformance" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTeamPerformance.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
<%--								${affairTeamPerformance.number}--%>
				</td>
				<td>
					<fmt:formatDate value="${affairTeamPerformance.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairTeamPerformance.finishDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${affairTeamPerformance.name}
				</td>
				<td>
					${affairTeamPerformance.unit}
				</td>
				<td>
					${affairTeamPerformance.itemOne}
				</td>
				<td>
					${affairTeamPerformance.itemTwo}
				</td>
				<td>
					${affairTeamPerformance.itemThree}
				</td>
				<td>
					${affairTeamPerformance.itemFour}
				</td>
				<td>
					${affairTeamPerformance.itemFive}
				</td>
				<td>
					${affairTeamPerformance.itemSix}
				</td>
				<td>
					${affairTeamPerformance.score}
				</td>
				<td>
					${affairTeamPerformance.rank}
				</td>
				<td>
					${affairTeamPerformance.remark}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairTeamPerformance.id}')">查看</a>
				<shiro:hasPermission name="affair:affairTeamPerformance:edit">
					<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTeamPerformance/form?id=${affairTeamPerformance.id}')">修改</a>
					<a href="${ctx}/affair/affairTeamPerformance/delete?id=${affairTeamPerformance.id}" onclick="return confirmx('确认要删除该比武团体成绩吗？', this.href)">删除</a>
				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>