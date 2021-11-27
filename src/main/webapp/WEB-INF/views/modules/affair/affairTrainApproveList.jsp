<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>培训计划审批管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function () {

			$("#btnPrint").click(function () {
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$('#checkTh').css('display', 'none');
				$('.checkTd').css('display', 'none');
				$("#contentTable").printThis({
					debug: false,
					importCSS: false,
					importStyle: false,
					printContainer: true,
					loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css", "${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css", "${ctxStatic}/common/jeesite.css"],
					pageTitle: "打印",
					removeInline: false,
					printDelay: 333,
					header: null,
					formValues: false,
					afterPrint: function () {
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
						$('#checkTh').css('display', 'table-cell');
						$('.checkTd').css('display', 'table-cell');
					}
				});
			});

			$("#btnExport").click(
					function () {
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action", "${ctx}/affair/affairTrainApprove/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action", "${ctx}/affair/affairTrainApprove/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action", "${ctx}/affair/affairTrainApprove/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action", "${ctx}/affair/affairTrainApprove/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairTrainApprove", "导入", 800, 520, {
					itle: "导入数据", buttons: {"关闭": true},
					bottomText: "导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！", closed: function () {
						self.location.href = "${ctx}/affair/affairTrainApprove/list"
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

		function openAddForm(url) {
			top.$.jBox.open("iframe:" + url, "培训计划审批", 1200, 600, {
				buttons: {"关闭": true},
				loaded: function () {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}, closed: function () {
					self.location.href = "${ctx}/affair/affairTrainApprove"
				}
			});
		}


		function openEditDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairTrainApprove/form?id=" + id, "培训计划审批修改", 1200, 600, {
				buttons: {"关闭": true},
				loaded: function () {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}, closed: function () {
					self.location.href = "${ctx}/affair/affairTrainApprove"
				}
			});
		}

		function openDetailDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairTrainApprove/formDetail?id=" + id, "培训计划审批详情", 1200, 600, {
				buttons: {"关闭": true},
				loaded: function () {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}, closed: function () {
					self.location.href = "${ctx}/affair/affairTrainApprove"
				}
			});
		}
		//添加审核弹窗
		function openDialog(myCheckBox) {
			var id = getIds(myCheckBox);
			if (id.length == 1) {
				top.$.jBox.open("iframe:${ctx}/affair/affairTrainApprove/shenHeDialog?id="+id, "审批",800,420,{
					buttons:{"关闭":true},
					loaded:function(){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					},closed:function (){
						self.location.href="${ctx}/affair/affairTrainApprove/";
					}
				});
			}else {
				$.jBox.tip('请先选择要审批的内容且只能单条审批');
			}
		}
		//批量审核
		function checkByIds(url, myCheckBox, checkBoxsName) {
			var editIds = getIds(checkBoxsName);
			if (editIds.length > 0) {
				var isStr = editIds.join(',');
				top.$.jBox.open("iframe:" + url + "?ids=" + isStr, "批量审批", 700, 520, {
					buttons: {"关闭": true},
					loaded: function () {
						$(".jbox-content", top.document).css("overflow-y", "hidden");
					}, closed: function () {
						self.location.href = '${ctx}/affair/affairTrainApprove/list';
					}
				});
			} else {
				$.jBox.tip('请先选择审批内容');
			}
		};

		if ("success" == "${saveResult}") {
			parent.$.jBox.close();
		}
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="affairTrainApprove" action="${ctx}/affair/affairTrainApprove/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="培训计划审批表.xlsx">
		<ul class="ul-form">
			<li>
				<label>培训年度：</label>
				<input name="trainYear" path="trainYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairTrainApprove.trainYear}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li><label>填报机构：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairTrainApprove.unitId}" labelName="unit" labelValue="${affairTrainApprove.unit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>审批状态：</label>
				<form:select path="approveStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('approve_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="affair:affairTrainApprove:edit">
					<li class="btns"><input class="btn btn-primary" type="button" value="添加"
											onclick="openAddForm('${ctx}/affair/affairTrainApprove/form')"/></li>
					<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
											onclick="deleteByIds('${ctx}/affair/affairTrainApprove/deleteByIds','checkAll','myCheckBox')"/>
					</li>
					<li class="btns"><input class="btn btn-primary" type="button" value="审核" onclick="checkByIds('${ctx}/affair/affairTrainApprove/shenHeDialog','checkAll','myCheckBox')"/></li>
				</shiro:hasPermission>
				<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
				<li class="btns"><input id="btnPrint" class="btn btn-primary" type="button" value="打印"/></li>
				<li class="btns"><input class="btn btn-primary" type="button" value="重置"
										onclick="window.location.href='${ctx}/affair/affairTrainApprove'"/></li>
				<li class="clearfix"></li>
			</ul>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>培训年度</th>
				<th>填报机构</th>
				<th>标题</th>
				<th>填报人</th>
				<th>提交审批日期</th>
				<th>审批层级</th>
				<th>审批状态</th>
				<th>备注</th>
				<th>更新时间</th>
				<shiro:hasPermission name="affair:affairTrainApprove:edit"><th class="handleTd">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTrainApprove" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTrainApprove.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairTrainApprove.trainYear}
				</td>
				<td>
					${affairTrainApprove.unit}
				</td>
				<td>
					${affairTrainApprove.title}
				</td>
				<td>
					${affairTrainApprove.informant}
				</td>
				<td>
					<fmt:formatDate value="${affairTrainApprove.approveDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(affairTrainApprove.approveLevel, 'approve_level', '')}
				</td>
				<td>
						<c:choose>
							<c:when test="${affairTrainApprove.approveStatus == '2'}">
								<a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
								   data-content="${affairTrainApprove.approveResult}"  style="cursor: pointer;color: red">审批未通过</a>
							</c:when>
							<c:otherwise>
								${fns:getDictLabel(affairTrainApprove.approveStatus, 'approve_status', '')}
							</c:otherwise>
						</c:choose>
				</td>
				<td>
					${affairTrainApprove.remarks}
				</td>
				<td>
					<fmt:formatDate value="${affairTrainApprove.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td class="handleTd">
					<a onclick="openDetailDialog('${affairTrainApprove.id}')">查看</a>
					<shiro:hasPermission name="affair:affairTrainApprove:edit">
						<a href="javascript:;" onclick="openEditDialog('${affairTrainApprove.id}')">修改</a>
						<a href="${ctx}/affair/affairTrainApprove/delete?id=${affairTrainApprove.id}"
						   onclick="return confirmx('确认要删除该教育训练培训模块吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>