<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>线上考试管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairOnlineExam/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairOnlineExam/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairOnlineExam/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairOnlineExam/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairOnlineExam", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairOnlineExam"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "线上考试",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairOnlineExam"}
			});
		}
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairOnlineExam/formDetail?id="+id;
			top.$.jBox.open(url, "线上考试",800,500,{
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
		<li><a href="${ctx}/affair/affairOnlineLearning/">教育训练平台线上学习情况统计</a></li>
		<li class="active"><a href="${ctx}/affair/affairOnlineExam/">教育训练平台考试情况统计</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairOnlineExam" action="${ctx}/affair/affairOnlineExam/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="线上考试.xlsx"/>
		<ul class="ul-form">
			<li><label>考试名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>机构名称：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairOnlineExam.unitId}" labelName="unit" labelValue="${affairOnlineExam.unit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairOnlineExam:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairOnlineExam/form?id=${affairOnlineExam.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairOnlineExam/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairOnlineExam'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>考试名称</th>
				<th>考试时间</th>
				<th>机构名称</th>
				<th>应考人数</th>
				<th>实考人数</th>
				<th>出勤率</th>
				<th>及格人数</th>
				<th>及格率</th>
				<th>最高分</th>
				<th>最低分</th>
				<th>平均分</th>
				<th>备注</th>
				<shiro:hasPermission name="affair:affairOnlineExam:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairOnlineExam" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairOnlineExam.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
<%--								${affairOnlineExam.number}--%>
				</td>
				<td>
					${affairOnlineExam.name}
				</td>
				<td>
					<fmt:formatDate value="${affairOnlineExam.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairOnlineExam.unit}
				</td>
				<td>
					${affairOnlineExam.numberOfCandidates}
				</td>
				<td>
					${affairOnlineExam.numberOfActualTest}
				</td>
				<td>
					${affairOnlineExam.attendance}
				</td>
				<td>
					${affairOnlineExam.passNumber}
				</td>
				<td>
					${affairOnlineExam.passingRate}
				</td>
				<td>
					${affairOnlineExam.highestScore}
				</td>
				<td>
					${affairOnlineExam.lowestScore}
				</td>
				<td>
					${affairOnlineExam.averageScore}
				</td>
				<td>
					${affairOnlineExam.remark}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairOnlineExam.id}')">查看</a>
				<shiro:hasPermission name="affair:affairOnlineExam:edit">
					<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairOnlineExam/form?id=${affairOnlineExam.id}')">修改</a>
					<a href="${ctx}/affair/affairOnlineExam/delete?id=${affairOnlineExam.id}" onclick="return confirmx('确认要删除该线上考试吗？', this.href)">删除</a>
				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>