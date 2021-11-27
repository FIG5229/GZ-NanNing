<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>委外培训管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function () {
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
								$("#searchForm").attr("action", "${ctx}/affair/affairTrainOutsource/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action", "${ctx}/affair/affairTrainOutsource/?treeId");
							}
							if (v == 'part') {
								$("#searchForm").attr("action", "${ctx}/affair/affairTrainOutsource/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action", "${ctx}/affair/affairTrainOutsource/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairTrainOutsource", "导入", 800, 520, {
					itle: "导入数据", buttons: {"关闭": true},
					bottomText: "导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！", closed: function () {
						self.location.href = "${ctx}/affair/affairTrainOutsource/list?unitId=${unitId}&unit=${unit}&pageNo=${page.pageNo}&pageSize=${page.pageSize}";
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
			top.$.jBox.open("iframe:" + url, "委外培训", 1200, 600, {
				buttons: {"关闭": true},
				loaded: function () {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}, closed: function () {
					self.location.href = "${ctx}/affair/affairTrainOutsource/list?unitId=${unitId}&unit=${unit}&pageNo=${page.pageNo}&pageSize=${page.pageSize}";
				}
			});
		}

		function openEditDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairTrainOutsource/form?id=" + id, "委外培训编辑", 1200, 600, {
				buttons: {"关闭": true},
				loaded: function () {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}, closed: function () {
					self.location.href = "${ctx}/affair/affairTrainOutsource/list?unitId=${unitId}&unit=${unit}&pageNo=${page.pageNo}&pageSize=${page.pageSize}";
				}
			});
		}

		function openDetailDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairTrainOutsource/formDetail?id=" + id, "委外培训详情", 1200, 600, {
				buttons: {"关闭": true},
				loaded: function () {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}, closed: function () {
					self.location.href = "${ctx}/affair/affairTrainOutsource/list?unitId=${unitId}&unit=${unit}&pageNo=${page.pageNo}&pageSize=${page.pageSize}";

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
		<shiro:hasPermission name="affair:affairTrainOutsource:view"><li class="active"><a href="${ctx}/affair/affairTrainOutsource/">委外培训</a></li></shiro:hasPermission>
		<shiro:hasPermission name="affair:affairSendTeacher:view"><li><a href="${ctx}/affair/affairSendTeacher/">送教上门</a></li></shiro:hasPermission>
	</ul>--%>
	<form:form id="searchForm" modelAttribute="affairTrainOutsource" action="${ctx}/affair/affairTrainOutsource/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="委外培训表.xlsx"/>
		<ul class="ul-form">
			<li><label>用户名：</label>
				<form:input path="userName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>警号：</label>
				<form:input path="number" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
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
			<li><label class="width120">外部培训班名称：</label>
				<form:input path="externalName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label class="width120">外部培训班类别：</label>
				<form:select path="externalType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('external_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>主办单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairTrainOutsource.unitId}" labelName="unit"
								labelValue="${affairTrainOutsource.unit}"
								title="部门" url="/sys/office/treeData?type=2" allowClear="true"
								notAllowSelectParent="false"/>
			</li>
			<li><label>域：</label>
				<sys:treeselect id="region" name="regionId" value="${affairTrainOutsource.regionId}" labelName="region"
								labelValue="${affairTrainOutsource.region}"
								title="区域" url="/sys/area/treeData" cssClass="input-small" allowClear="true"
								notAllowSelectParent="true"/>
			</li>

			<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="affair:affairTrainOutsource:edit">
					<li class="btns"><input class="btn btn-primary" type="button" value="添加"
											onclick="openAddForm('${ctx}/affair/affairTrainOutsource/form')"/></li>
					<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
											onclick="deleteByIds('${ctx}/affair/affairTrainOutsource/deleteByIds','checkAll','myCheckBox')"/>
					</li>
				</shiro:hasPermission>
				<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
				<li class="btns"><input id="btnPrint" class="btn btn-primary" type="button" value="打印"/></li>
				<li class="btns"><input class="btn btn-primary" type="button" value="重置"
										onclick="window.location.href='${ctx}/affair/affairTrainOutsource/list?unitId=${unitId}&unit=${unit}'"/></li>
				<li class="clearfix"></li>
			</ul>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >南宁铁路公安处委外培训报表</td>
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
				<th>姓名</th>
				<th>外部培训班名称</th>
				<th>外部培训班类别</th>
				<th>培训完成情况</th>
				<th>主办单位机构代码</th>
				<th>主办单位</th>
				<th>主办单位级别</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>培训离岗状态</th>
				<th>承训机构名称</th>
				<th>培训地点</th>
				<th>证书编号</th>
				<th>培训时所在单位及职务</th>
				<th>创建人</th>
				<th>更新时间</th>
				<shiro:hasPermission name="affair:affairTrainOutsource:edit"><th class="handleTd">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTrainOutsourceinfo" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTrainOutsourceinfo.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairTrainOutsourceinfo.userName}
				</td>
				<td>
					${affairTrainOutsourceinfo.name}
				</td>
				<td>
					${affairTrainOutsourceinfo.externalName}
				</td>
				<td>
					${fns:getDictLabel(affairTrainOutsourceinfo.externalType, 'external_type', '')}
				</td>
				<td>
					${fns:getDictLabel(affairTrainOutsourceinfo.completion, 'train_completion', '')}
				</td>
				<td>
					${affairTrainOutsourceinfo.institutionCode}
				</td>
				<td>
					${affairTrainOutsourceinfo.unit}
				</td>
				<td>
					${fns:getDictLabel(affairTrainOutsourceinfo.unitLevel, 'unit_level', '')}
				</td>
				<td>
					<fmt:formatDate value="${affairTrainOutsourceinfo.beganDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairTrainOutsourceinfo.resultDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(affairTrainOutsourceinfo.quitStatus, 'quit_status', '')}
				</td>
				<td>
					${affairTrainOutsourceinfo.unitName}
				</td>
				<td>
					${affairTrainOutsourceinfo.trainSite}
				</td>
				<td>
					${affairTrainOutsourceinfo.certificateCode}
				</td>
				<td>
					${affairTrainOutsourceinfo.unitJob}
				</td>
				<td>
						${fns:getUserById(affairTrainOutsourceinfo.createBy).getName()}
				</td>
				<td>
					<fmt:formatDate value="${affairTrainOutsourceinfo.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td class="handleTd">
				<a href="javascript:" onclick="openDetailDialog('${affairTrainOutsourceinfo.id}')">查看</a>
				<shiro:hasPermission name="affair:affairTrainOutsource:edit">
					<a href="javascript:" onclick="openEditDialog('${affairTrainOutsourceinfo.id}')">修改</a>
					<a href="${ctx}/affair/affairTrainOutsource/delete?id=${affairTrainOutsourceinfo.id}&unitId=${affairTrainOutsource.unitId}"
					   onclick="return confirmx('确认要删除该委外培训吗？', this.href)">删除</a>
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