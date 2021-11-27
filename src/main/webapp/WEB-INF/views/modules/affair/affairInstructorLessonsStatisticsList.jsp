<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>教官授课统计管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#print").click(function(){
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$('#checkTh').css('display', 'none');
				$('.checkTd').css('display', 'none');
				$('#headTitle').addClass("table table-striped table-bordered table-condensed");
				$('#headTitle').removeAttr('style');

				$("#contentTable").printThis({
					debug: false,
					importCSS: false,
					importStyle: false,
					printContainer: true,
					loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css"],
					pageTitle: "",
					removeInline: false,
					printDelay: 333,
					header: "",
					formValues: false,
					afterPrint:function (){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
						$('#checkTh').css('display', 'table-cell');
						$('.checkTd').css('display', 'table-cell');
						$('#headTitle').css('display', 'none');

					}
				});
			});

			//导出功能的JS
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairInstructorLessonsStatistics/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairInstructorLessonsStatistics/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairInstructorLessonsStatistics/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairInstructorLessonsStatistics/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);

			//导入功能的JS
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_instructorLessonsStatistics", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairInstructorLessonsStatistics"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//添加，修改弹窗
		function openDialog(url,type) {
			top.$.jBox.open("iframe:"+url, "教官授课统计"+type,800,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairInstructorLessonsStatistics"}
			});
		}
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairInstructorLessonsStatistics/">教官授课统计列表</a></li>
		<shiro:hasPermission name="affair:affairInstructorLessonsStatistics:edit"><li><a href="${ctx}/affair/affairInstructorLessonsStatistics/form">教官授课统计添加</a></li></shiro:hasPermission>
	</ul>--%>
	<form:form id="searchForm" modelAttribute="affairInstructorLessonsStatistics" action="${ctx}/affair/affairInstructorLessonsStatistics/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="教官授课统计报表.xlsx"/>
		<ul class="ul-form">
			<li><label>身份证号：</label>
				<form:input path="idNumber" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>教官姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>所属机构：</label>
				<sys:treeselect id="unitId" name="unitId" value="${affairInstructorLessonsStatistics.unitId}" labelName="unitName" labelValue="${affairInstructorLessonsStatistics.unitName}"
					title="所属机构" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairInstructorLessonsStatistics:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairInstructorLessonsStatistics/form','添加')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairInstructorLessonsStatistics/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairInstructorLessonsStatistics'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >南宁铁路公安处教官授课统计管理报表</td>
			</tr>
		</table>
		<table  class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="handleTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>身份证号</th>
				<th>教官姓名</th>
				<th>教官类型</th>
				<th>所属机构</th>
				<th>授课总数</th>
				<th>课时总数</th>
				<th>授课总时长(小时)</th>
				<shiro:hasPermission name="affair:affairInstructorLessonsStatistics:edit"><th id="checkTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairInstructorLessonsStatistics" varStatus="status">
			<tr>
				<td class="handleTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairInstructorLessonsStatistics.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairInstructorLessonsStatistics.idNumber}
				</td>
				<td>
					${affairInstructorLessonsStatistics.name}
				</td>
				<td>
					${affairInstructorLessonsStatistics.instructorType}
				</td>
				<td>
					${affairInstructorLessonsStatistics.unitName}
				</td>
				<td>
					${affairInstructorLessonsStatistics.giveLessonsTotal}
				</td>
				<td>
					${affairInstructorLessonsStatistics.classHourTotal}
				</td>
				<td>
					${affairInstructorLessonsStatistics.giveLessonsTotalHour}
				</td>
				<shiro:hasPermission name="affair:affairInstructorLessonsStatistics:edit">
					<td class="checkTd">
    					<a href="javascript:void(0);" onclick="openDialog('${ctx}/affair/affairInstructorLessonsStatistics/form?id=${affairInstructorLessonsStatistics.id}','修改')">修改</a>
						<a href="${ctx}/affair/affairInstructorLessonsStatistics/delete?id=${affairInstructorLessonsStatistics.id}" onclick="return confirmx('确认要删除该教官授课统计吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>