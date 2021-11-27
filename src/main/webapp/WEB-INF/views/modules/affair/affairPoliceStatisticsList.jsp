<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民警课程学习统计管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairPoliceStatistics/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPoliceStatistics/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairPoliceStatistics/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPoliceStatistics/");
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
					afterPrint:function(){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
						$('#headTitle').css('display', 'none');

					}
				});
			});
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairPoliceStatistics", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairPoliceStatistics"}});
			});

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairPoliceStatistics/formDetail?id="+id;
			top.$.jBox.open(url, "",1000,400,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }
        function openForm(url) {
			top.$.jBox.open("iframe:"+url, "",1200,800,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairPoliceStatistics"}
			});
		}
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="affairPoliceStatistics" action="${ctx}/affair/affairPoliceStatistics/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="民警课程学习统计表.xlsx"/>
		<ul class="ul-form">
			<li><label>用户名：</label>
				<form:input path="nickName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<%--<li><label>性别：</label>
				<form:input path="sex" htmlEscape="false" class="input-medium"/>
			</li>--%>
			<%--<li><label>年龄：</label>
				<form:input path="age" htmlEscape="false" class="input-medium"/>
			</li>--%>
			<li><label>警号：</label>
				<form:input path="alarm" htmlEscape="false" class="input-medium"/>
			</li>
			<%--<li><label>警种：</label>
				<form:input path="policeClassify" htmlEscape="false" class="input-medium"/>
			</li>--%>
			<li><label>课程名称：</label>
				<form:input path="className" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>课程类别：</label>
				<form:input path="classify" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairPoliceStatistics:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairPoliceStatistics/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairPoliceStatistics/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairPoliceStatistics'"/></li>

			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >南宁铁路公安处民警课程学习统计管理报表</td>
			</tr>
		</table>
		<table  class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>用户名</th>
				<th>姓名</th>
				<th>警号</th>
				<th>所属机构</th>
				<th>学习途径</th>
				<th>项目名称</th>
				<th>课程编号</th>
				<th>课程名称</th>
				<th>课程类别</th>
				<th>学时</th>
				<th>播放时长</th>
				<th>第一次学习时间</th>
				<th>最后一次学习时间</th>
				<th>学习进度</th>
				<th>学习时长</th>
				<th>是否通过</th>
				<shiro:hasPermission name="affair:affairPoliceStatistics:edit"><th class="handleTd">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairPoliceStatistics" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairPoliceStatistics.id}"/>
				</td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td>${affairPoliceStatistics.nickName}</td>
				<td>${affairPoliceStatistics.name}</td>
				<td>${affairPoliceStatistics.alarm}</td>
				<td>${affairPoliceStatistics.unit}</td>
				<td>${affairPoliceStatistics.way}</td>
				<td>${affairPoliceStatistics.projectName}</td>
				<td>${affairPoliceStatistics.classNumber}</td>
				<td>${affairPoliceStatistics.className}</td>
				<td>${affairPoliceStatistics.classify}</td>
				<td>${affairPoliceStatistics.learnTime}</td>
				<td>${affairPoliceStatistics.longTime}</td>
				<td><fmt:formatDate value="${affairPoliceStatistics.firstTime}" pattern="yyyy-MM-dd "/></td>
				<td><fmt:formatDate value="${affairPoliceStatistics.lastTime}" pattern="yyyy-MM-dd "/></td>
				<td>${affairPoliceStatistics.schedule}</td>
				<td>${affairPoliceStatistics.learnTimeLong}</td>
				<td>${affairPoliceStatistics.isPass}</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairPoliceStatistics.id}')">查看</a>
					<shiro:hasPermission name="affair:affairPoliceStatistics:edit">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairPoliceStatistics/form?id=${affairPoliceStatistics.id}')">修改</a>

						<a href="${ctx}/affair/affairPoliceStatistics/delete?id=${affairPoliceStatistics.id}" onclick="return confirmx('确认要删除该民警课程学习统计吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>