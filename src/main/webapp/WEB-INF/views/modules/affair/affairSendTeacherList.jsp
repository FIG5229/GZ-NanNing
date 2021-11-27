<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>送教上门管理</title>
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
								$("#searchForm").attr("action", "${ctx}/affair/affairSendTeacher/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action", "${ctx}/affair/affairSendTeacher/list?treeId");
							}
							if (v == 'part') {
								$("#searchForm").attr("action", "${ctx}/affair/affairSendTeacher/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action", "${ctx}/affair/affairSendTeacher/list");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairSendTeacher", "导入", 800, 520, {
					itle: "导入数据", buttons: {"关闭": true},
					bottomText: "导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！", closed: function () {
						self.location.href = "${ctx}/affair/affairSendTeacher/list?unitId=${unitId}&unit=${unit}&pageNo=${page.pageNo}&pageSize=${page.pageSize}";
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
			top.$.jBox.open("iframe:" + url, "送教上门", 1200, 600, {
				buttons: {"关闭": true},
				loaded: function () {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}, closed: function () {
					self.location.href = "${ctx}/affair/affairSendTeacher/list?unitId=${unitId}&unit=${unit}&pageNo=${page.pageNo}&pageSize=${page.pageSize}";
				}
			});
		}

		function openEditDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairSendTeacher/form?id=" + id, "送教上门编辑", 1200, 600, {
				buttons: {"关闭": true},
				loaded: function () {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}, closed: function () {
					self.location.href = "${ctx}/affair/affairSendTeacher/list?unitId=${unitId}&unit=${unit}&pageNo=${page.pageNo}&pageSize=${page.pageSize}";
				}
			});
		}

		function openDetailDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairSendTeacher/formDetail?id=" + id, "送教上门详情", 1200, 600, {
				buttons: {"关闭": true},
				loaded: function () {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}, closed: function () {
					self.location.href = "${ctx}/affair/affairSendTeacher/list?unitId=${unitId}&unit=${unit}&pageNo=${page.pageNo}&pageSize=${page.pageSize}";

				}
			});
		}

		if ("success" == "${saveResult}") {
			parent.$.jBox.close();
		}
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<shiro:hasPermission name="affair:affairTraining:view"><li><a href="${ctx}/affair/affairTraining/list">练兵比武</a></li></shiro:hasPermission>
&lt;%&ndash;
		<shiro:hasPermission name="affair:affairTrainingManage:view"><li><a href="${ctx}/affair/affairTrainingManage/list">成绩管理员练兵比武</a></li></shiro:hasPermission>
&ndash;%&gt;
		<shiro:hasPermission name="affair:affairJobTraining:view"><li><a href="${ctx}/affair/affairJobTraining/">岗位练兵</a></li></shiro:hasPermission>
		<shiro:hasPermission name="affair:affairTrainCombat:view"><li><a href="${ctx}/affair/affairTrainCombat/list">实弹训练</a></li></shiro:hasPermission>
		<shiro:hasPermission name="affair:affairSwapExercise:view"><li><a href="${ctx}/affair/affairSwapExercise/">交流锻炼</a></li></shiro:hasPermission>
		<shiro:hasPermission name="affair:affairTrainOutsource:view"><li><a href="${ctx}/affair/affairTrainOutsource/">委外培训</a></li></shiro:hasPermission>
		<shiro:hasPermission name="affair:affairSendTeacher:view"><li class="active"><a href="${ctx}/affair/affairSendTeacher/">送教上门</a></li></shiro:hasPermission>
	</ul>--%>
	<form:form id="searchForm" modelAttribute="affairSendTeacher" action="${ctx}/affair/affairSendTeacher/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="送教上门表.xlsx"/>
		<ul class="ul-form">
			<li><label>用户名：</label>
				<form:input path="userName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>警号：</label>
				<form:input path="number" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>受训民警：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>身份证号：</label>
				<form:input path="idNumber" htmlEscape="false" class="input-medium"/>
			</li>
			<%--<li><label>警种：</label>
				<form:select path="policeClassification" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('police_classification')}" itemLabel="label" itemValue="value"
								  htmlEscape="false"/>
				</form:select>
			</li>--%>
			<li><label>警衔：</label>
				<form:select path="policeRank" class="input-medium">
					<form:option value="" label=""/>
					<%--<form:options items="${fns:getDictList('police_rank')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
					<form:options items="${fns:getDictList('police_rank_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>人员类别：</label>
				<form:select path="personType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('person_type')}" itemLabel="label" itemValue="value"
								  htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>管理类别：</label>
				<form:select path="managementType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('management_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>行政职务：</label>
				<form:select path="post" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('administration_post')}" itemLabel="label" itemValue="value"
								  htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>职务级别：</label>
				<form:select path="postLevel" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('post_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairSendTeacher.unitId}" labelName="unit"
								labelValue="${affairSendTeacher.unit}"
								title="部门" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true"
								notAllowSelectParent="false" dataMsgRequired="必填信息"/>
			</li>
			<li><label>域：</label>
				<sys:treeselect id="region" name="regionId" value="${affairSendTeacher.regionId}" labelName="region"
								labelValue="${affairSendTeacher.region}"
								title="区域" url="/sys/area/treeData" cssClass="input-small" allowClear="true"
								notAllowSelectParent="true"/>
			</li>
			<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="affair:affairSendTeacher:edit">
					<li class="btns"><input class="btn btn-primary" type="button" value="添加"
											onclick="openAddForm('${ctx}/affair/affairSendTeacher/form')"/></li>
					<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
											onclick="deleteByIds('${ctx}/affair/affairSendTeacher/deleteByIds','checkAll','myCheckBox')"/>
					</li>
				</shiro:hasPermission>
				<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
				<li class="btns"><input id="btnPrint" class="btn btn-primary" type="button" value="打印"/></li>
				<li class="btns"><input class="btn btn-primary" type="button" value="重置"
										onclick="window.location.href='${ctx}/affair/affairSendTeacher/list?unitId=${unitId}&unit=${unit}'"/></li>
				<li class="clearfix"></li>
			</ul>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >南宁铁路公安处送教上门报表</td>
			</tr>
		</table>
		<table  class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll'
										onclick='chooseAll(this,"myCheckBox")'/>全选
				</th>
				<th>序号</th>
				<th>用户名</th>
				<%--<th>警号</th>
				<th>受训民警</th>--%>
				<th>组织单位</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>送教时长(天)</th>
				<%--<th>送教市场</th>--%>
				<th>送教场次</th>
				<%--<th>受训人数</th>--%>
				<th>送教内容</th>
				<th>受教单位</th>

				<shiro:hasPermission name="affair:affairSendTeacher:edit"><th class="handleTd">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairSendTeacherinfo" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairSendTeacherinfo.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairSendTeacherinfo.userName}
				</td>
				<%--<td>
					${affairSendTeacherinfo.number}
				</td>
				<td>
					${affairSendTeacherinfo.name}
				</td>--%>
				<td>
					${affairSendTeacherinfo.unit}
				</td>
				<td>
					<fmt:formatDate value="${affairSendTeacherinfo.beganDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairSendTeacherinfo.resultDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairSendTeacherinfo.sendDay}
				</td>
				<%--<td>
					${affairSendTeacherinfo.sendMarket}
				</td>--%>
				<td>
					${affairSendTeacherinfo.sendPeriod}
				</td>
				<%--<td>
					${affairSendTeacherinfo.count}
				</td>--%>
				<td>
						${affairSendTeacherinfo.sendContent}
				</td>
				<td>
					${affairSendTeacherinfo.sendUnit}
				</td>

				<td class="handleTd">
					<a href="javascript:" onclick="openDetailDialog('${affairSendTeacherinfo.id}')">查看</a>
					<shiro:hasPermission name="affair:affairSendTeacher:edit">
						<a href="javascript:" onclick="openEditDialog('${affairSendTeacherinfo.id}')">修改</a>
						<a href="${ctx}/affair/affairSendTeacher/delete?id=${affairSendTeacherinfo.id}&unitId=${affairSendTeacher.unitId}"
						   onclick="return confirmx('确认要删除该送教上门吗？', this.href)">删除</a>
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