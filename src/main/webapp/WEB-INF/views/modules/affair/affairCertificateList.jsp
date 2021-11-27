<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>证书管理</title>
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

			$("#btnExport").click(
					function () {
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action", "${ctx}/affair/affairCertificate/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action", "${ctx}/affair/affairCertificate/list?treeId");
							}
							if (v == 'part') {
								$("#searchForm").attr("action", "${ctx}/affair/affairCertificate/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action", "${ctx}/affair/affairCertificate/list");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, {
							buttons: {
								'导出全部数据': 'all',
								'导出当前页面数据': 'part',
								'取消': 'cancel'
							}
						});
					}
			);

			$("#btnImport").click(function () {
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairCertificate", "导入", 800, 520, {
					itle: "导入数据", buttons: {"关闭": true},
					bottomText: "导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！", closed: function () {
						self.location.href = "${ctx}/affair/affairCertificate/list"
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
			top.$.jBox.open("iframe:" + url, "证书", 1200, 600, {
				buttons: {"关闭": true},
				loaded: function () {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}, closed: function () {
					self.location.href = "${ctx}/affair/affairCertificate"
				}
			});
		}

		function openEditDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairCertificate/form?id=" + id, "证书编辑", 1200, 600, {
				buttons: {"关闭": true},
				loaded: function () {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}, closed: function () {
					self.location.href = "${ctx}/affair/affairCertificate"
				}
			});
		}

		function openDetailDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairCertificate/formDetail?id=" + id, "证书详情", 1200, 600, {
				buttons: {"关闭": true},
				loaded: function () {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}, closed: function () {
					self.location.href = "${ctx}/affair/affairCertificate"

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
		<li><a href="${ctx}/affair/affairCertificateIssue/">颁发证书</a></li>
		<li class="active"><a href="${ctx}/affair/affairCertificate/">证书列表</a></li>
		<li><a href="${ctx}/affair/affairCertificateManagement/">证书模板管理</a></li>
		<li><a href="${ctx}/affair/affairCertificateAdministrator/">证书管理员</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairCertificate" action="${ctx}/affair/affairCertificate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="证书详情表.xlsx"/>
		<ul class="ul-form">
			<li><label>证书编号：</label>
				<form:input path="number" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>用户姓名：</label>
				<form:input path="userName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>用户编号：</label>
				<form:input path="userNumber" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairCertificate.unitId}" labelName="unit" labelValue="${affairCertificate.unit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="affair:affairCertificate:edit">
					<li class="btns"><input class="btn btn-primary" type="button" value="添加"
											onclick="openAddForm('${ctx}/affair/affairCertificate/form')"/></li>
					<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
											onclick="deleteByIds('${ctx}/affair/affairCertificate/deleteByIds','checkAll','myCheckBox')"/>
					</li>
				</shiro:hasPermission>
				<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
				<li class="btns"><input id="btnPrint" class="btn btn-primary" type="button" value="打印"/></li>
				<li class="btns"><input class="btn btn-primary" type="button" value="重置"
										onclick="window.location.href='${ctx}/affair/affairCertificate'"/></li>
				<li class="clearfix"></li>
			</ul>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >南宁铁路公安处证书列表报表</td>
			</tr>
		</table>
		<table  class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll'
										onclick='chooseAll(this,"myCheckBox")'/>全选
				</th>
				<th>序号</th>
				<th>工号</th>
				<th>用户姓名</th>
				<th>机构全路径</th>
				<th>证书名称</th>
				<th>证书编码</th>
				<th>颁发日期</th>
				<shiro:hasPermission name="affair:affairCertificate:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairCertificate" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairCertificate.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${affairCertificate.jobNumber}
				</td>
				<td>
						${affairCertificate.userName}
				</td>
				<td>
						${affairCertificate.unit}
				</td>
				<td>
						${affairCertificate.certificateName}
				</td>
				<td>
						${affairCertificate.number}
				</td>
				<td>
					<fmt:formatDate value="${affairCertificate.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td class="handleTd">
				<a href="javascript:" onclick="openDetailDialog('${affairCertificate.id}')">查看</a>
				<shiro:hasPermission name="affair:affairCertificate:edit">
					<a href="javascript:" onclick="openEditDialog('${affairCertificate.id}')">修改</a>
					<a href="${ctx}/affair/affairCertificate/delete?id=${affairCertificate.id}"
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