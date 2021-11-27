<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>得分制工作项管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function () {
			$("#print").click(function () {
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$('#headTitle').addClass("table table-striped table-bordered table-condensed");
				$('#headTitle').removeAttr('style');
				$("#contentTable").printThis({
					debug: false,
					importCSS: false,
					importStyle: false,
					printContainer: true,
					loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css", "${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css", "${ctxStatic}/common/jeesite.css"],
					pageTitle: "",
					removeInline: false,
					printDelay: 333,
					header: "",
					formValues: false,
					afterPrint: function () {
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
						$('#headTitle').css('display', 'none');
					}
				});
			});


		});

		function page(n, s) {
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
		//添加，修改弹窗
		function openDialog(url) {
			top.$.jBox.open("iframe:" + url, "得分制工作项", 800, 500, {
				buttons: {"关闭": true},
				loaded: function (h) {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}, closed: function () {
					self.location.href = "${ctx}/exam/examScoreWorkItem/list";
				}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/exam/examScoreWorkItem/formDetail?id=" + id;
			top.$.jBox.open(url, "得分制工作项详情", 800, 500, {
				buttons: {"关闭": true},
				loaded: function (h) {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/exam/examScoreWorkItem/">得分制工作项管理列表</a></li>
<%--
		<shiro:hasPermission name="exam:examScoreWorkItem:edit"><li><a href="${ctx}/exam/examScoreWorkItem/form">得分制工作项管理添加</a></li></shiro:hasPermission>
--%>
	</ul>
	<form:form id="searchForm" modelAttribute="examScoreWorkItem" action="${ctx}/exam/examScoreWorkItem/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>类型：</label>
				<form:select path="examType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('exam_weights_kpType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>工作项：</label>
				<form:select path="workName" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('exam_weigths')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="clearfix"></li>

			<ul class="ul-form2">

				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>

				<shiro:hasPermission name="exam:examScoreWorkItem:edit">
					<li class="btns"><input class="btn btn-primary" type="button" value="添加"
											onclick="openDialog('${ctx}/exam/examScoreWorkItem/form')"/></li>

					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
											onclick="deleteByIds('${ctx}/exam/examScoreWorkItem/deleteByIds','checkAll','myCheckBox')"/>
					</li>

				</shiro:hasPermission>

				<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/exam/examScoreWorkItem/list'"/></li>

				<li class="clearfix">x</li>
			</ul>

		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll'
										onclick='chooseAll(this,"myCheckBox")'/></th>
				<th>序号</th>
				<th>类型</th>
				<th>工作项</th>
				<shiro:hasPermission name="exam:examScoreWorkItem:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examScoreWorkItem" varStatus="status" >
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${examScoreWorkItem.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${fns:getDictLabel(examScoreWorkItem.examType, 'exam_weights_kpType', '')}
				</td>
				<td>
					${fns:getDictLabel(examScoreWorkItem.workName, 'exam_weigths', '')}
				</td>
				<td>
				<a onclick="openDetailDialog('${examScoreWorkItem.id}')" style="cursor: pointer">查看</a>
				<shiro:hasPermission name="exam:examScoreWorkItem:edit">
					<a href="javascript:void(0)" onclick="openDialog('${ctx}/exam/examScoreWorkItem/form?id=${examScoreWorkItem.id}')">修改</a>
					<a href="${ctx}/exam/examScoreWorkItem/delete?id=${examScoreWorkItem.id}" onclick="return confirmx('确认要删除该得分制工作项管理吗？', this.href)">删除</a>
				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>