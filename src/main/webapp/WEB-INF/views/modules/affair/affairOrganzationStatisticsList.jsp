<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构部门学习统计管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairOrganzationStatistics/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairOrganzationStatistics/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairOrganzationStatistics/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairOrganzationStatistics/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairOrganzationStatistics", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairOrganzationStatistics"}});
			});
		});

		//添加，修改弹窗
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, " ",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairOrganzationStatistics"}
			});
		}

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairOrganzationStatistics/formDetail?id="+id;
			top.$.jBox.open(url, "",900,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}

		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="affairOrganzationStatistics" action="${ctx}/affair/affairOrganzationStatistics/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="机构部门学习统计表.xlsx"/>
		<ul class="ul-form">
			<li><label>机构：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairOrganzationStatistics.unitId}" labelName="unit" labelValue="${affairOrganzationStatistics.unit}"
								title="机构" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>课程名称：</label>
				<form:input path="className" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>课程类别：</label>
				<form:input path="classify" htmlEscape="false" class="input-medium"/>
			</li>
			<li>
				<label>学习时间：</label>
				<input name="beginTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairOrganzationStatistics.beginTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairOrganzationStatistics.endTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairOrganzationStatistics:edit">
				<li class="btns">
					<input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairOrganzationStatistics/form?id=${affairOrganzationStatistics.id}')"/>
				</li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
										onclick="deleteByIds('${ctx}/affair/affairOrganzationStatistics/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairOrganzationStatistics'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>机构</th>
				<th>用户总数</th>
				<th>学习总人数</th>
				<th>学习率</th>
				<th>学习总时长</th>
				<th>参加学习用户平均学习时长</th>
				<th>全体用户平均学习时长</th>
				<th>学习总次数</th>
				<th>参加用户平均次数</th>
				<th>全体用户平均学习次数</th>
				<th>课程通过总数</th>
				<th>人均通过课程</th>
				<shiro:hasPermission name="affair:affairOrganzationStatistics:edit"><th class="handleTd">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairOrganzationStatistics" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairOrganzationStatistics.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${affairOrganzationStatistics.unit}
				</td>
				<td>
						${affairOrganzationStatistics.peopleSum}
				</td>
				<td>
						${affairOrganzationStatistics.studySum}
				</td>
				<td>
						${affairOrganzationStatistics.studyRatio}
				</td>
				<td>
						${affairOrganzationStatistics.studyTimeSum}
				</td>
				<td>
						${affairOrganzationStatistics.studyTimeAvg}
				</td>
				<td>
						${affairOrganzationStatistics.studyTimeAvgAll}
				</td>
				<td>
						${affairOrganzationStatistics.studyNumber}
				</td>
				<td>
						${affairOrganzationStatistics.peopleAvg}
				</td>
				<td>
						${affairOrganzationStatistics.peopleAvgAll}
				</td>
				<td>
						${affairOrganzationStatistics.coursePassNumber}
				</td>
				<td>
						${affairOrganzationStatistics.coursePassPeople}
				</td>
				<td class="handleTd">
					<a onclick="openDetailDialog('${affairOrganzationStatistics.id}')">查看</a>
					<shiro:hasPermission name="affair:affairOrganzationStatistics:edit">
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairOrganzationStatistics/form?id=${affairOrganzationStatistics.id}')">修改</a>
						<a href="${ctx}/affair/affairOrganzationStatistics/delete?id=${affairOrganzationStatistics.id}" onclick="return confirmx('确认要删除该机构部门学习统计吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>