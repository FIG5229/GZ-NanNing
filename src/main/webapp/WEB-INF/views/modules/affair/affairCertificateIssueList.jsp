<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>颁发证书管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnPrint").click(function () {
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
					loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css", "${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css", "${ctxStatic}/common/jeesite.css"],
					pageTitle: "",
					removeInline: false,
					printDelay: 333,
					header: "",
					formValues: false,
					afterPrint: function () {
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
						$('#checkTh').css('display', 'table-cell');
						$('.checkTd').css('display', 'table-cell');
						$('#headTitle').css('display', 'none');

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
		function openAddForm(url) {
			top.$.jBox.open("iframe:" + url, "颁发证书", 1200, 600, {
				buttons: {"关闭": true},
				loaded: function () {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}, closed: function () {
					self.location.href = "${ctx}/affair/affairCertificateIssue"
				}
			});
		}

		function openEditDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairCertificateIssue/form?id=" + id, "颁发证书编辑", 1200, 600, {
				buttons: {"关闭": true},
				loaded: function () {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}, closed: function () {
					self.location.href = "${ctx}/affair/affairCertificateIssue"
				}
			});
		}

		function openDetailDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairCertificateIssue/formDetail?id=" + id, "颁发证书详情", 1200, 600, {
				buttons: {"关闭": true},
				loaded: function () {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}, closed: function () {
					self.location.href = "${ctx}/affair/affairCertificateIssue"

				}
			});
		}

		if ("success" == "${saveResult}") {
			parent.$.jBox.close();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairCertificateIssue/">颁发证书</a></li>
		<li><a href="${ctx}/affair/affairCertificate/">证书列表</a></li>
		<li><a href="${ctx}/affair/affairCertificateManagement/">证书模板管理</a></li>
		<li><a href="${ctx}/affair/affairCertificateAdministrator/">证书管理员</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairCertificateIssue" action="${ctx}/affair/affairCertificateIssue/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>证书：</label>
				<form:input path="certificate" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>颁发途径：</label>
				<form:select path="way" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('certificate_way')}" itemLabel="label" itemValue="value"
								  htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>颁发日期：</label>
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairCertificateIssue.date}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>编号：</label>
				<form:input path="number" htmlEscape="false" class="input-medium"/>
			</li>
			<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="affair:affairCertificateIssue:edit">
					<li class="btns"><input class="btn btn-primary" type="button" value="添加"
											onclick="openAddForm('${ctx}/affair/affairCertificateIssue/form')"/></li>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
											onclick="deleteByIds('${ctx}/affair/affairCertificateIssue/deleteByIds','checkAll','myCheckBox')"/>
					</li>
				</shiro:hasPermission>
				<li class="btns"><input id="btnPrint" class="btn btn-primary" type="button" value="打印"/></li>
				<li class="btns"><input class="btn btn-primary" type="button" value="重置"
										onclick="window.location.href='${ctx}/affair/affairCertificateIssue'"/></li>
				<li class="clearfix"></li>
			</ul>
		</ul>
	</form:form>
	<sys:message content="${message}"/>

	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >南宁铁路公安处颁发证书报表</td>
			</tr>
		</table>
		<table  class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选
				</th>
				<th>序号</th>
				<th>证书</th>
				<th>颁发途径</th>
				<th>说明</th>
				<th>颁发日期</th>
				<th>编号</th>
				<th>更新时间</th>
				<shiro:hasPermission name="affair:affairCertificateIssue:edit"><th class="handleTd">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairCertificateIssue">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairCertificateIssue.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairCertificateIssue.certificate}
				</td>
				<td>
					${fns:getDictLabel(affairCertificateIssue.way,'certificate_way','')}
				</td>
				<td>
					${affairCertificateIssue.explain}
				</td>
				<td>
					<fmt:formatDate value="${affairCertificateIssue.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairCertificateIssue.number}
				</td>
				<td>
					<fmt:formatDate value="${affairCertificateIssue.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td class="handleTd">
				<a href="javascript:;" onclick="openDetailDialog('${affairCertificateIssue.id}')">查看</a>
				<shiro:hasPermission name="affair:affairCertificateIssue:edit">
					<a href="javascript:;" onclick="openEditDialog('${affairCertificateIssue.id}')">修改</a>
					<a href="${ctx}/affair/affairCertificateIssue/delete?id=${affairCertificateIssue.id}"
					   onclick="return confirmx('确认要删除该证书吗？', this.href)">删除</a>
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