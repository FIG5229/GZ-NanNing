<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民警记实功能管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function () {
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action", "${ctx}/affair/affairWorkRecord/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action", "${ctx}/affair/affairWorkRecord/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action", "${ctx}/affair/affairWorkRecord/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action", "${ctx}/affair/affairWorkRecord/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairWorkRecord", "导入", 800, 520, {
					itle: "导入数据", buttons: {"关闭": true},
					bottomText: "导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！", closed: function () {
						self.location.href = "${ctx}/affair/affairWorkRecord/list"
					}
				});
			});
		});
		//添加，修改弹窗
		function openDialog(url) {
			top.$.jBox.open("iframe:" + url, "工作记实", 800, 500, {
				buttons: {"关闭": true},
				loaded: function (h) {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}, closed: function () {
					self.location.href = "${ctx}/affair/affairWorkRecord"
				}
			});
		};

		//负责人意见添加弹窗
		function openSaveUnit(url) {
			top.$.jBox.open("iframe:" + url, "负责人意见", 800, 500, {
				buttons: {"关闭": true},
				loaded: function (h) {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}, closed: function () {
					self.location.href = "${ctx}/affair/affairWorkRecord"
				}
			});
		};

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairWorkRecord/formDetail?id=" + id;
			top.$.jBox.open(url, "工作记实详情", 800, 500, {
				buttons: {"关闭": true},
				loaded: function (h) {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}
			});
		};
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

		//提交申报
		function submitByIds() {
			console.log($("#chuCheckManId").val())
			if(null == $("#chuCheckManId").val() || "" ==  $("#chuCheckManId").val()){
				$.jBox.tip('请选择审核用户');
				return false;
			}
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				//console.log($(this).val())
				ids.push($(this).val());
			});
			if (ids.length > 0) {
				var idsStr = ids.join(",");
				$("#idsValue").val(idsStr);
				$("#searchForm2").submit();
			} else {
				$.jBox.tip('请先选择要提交的内容');
			}
		}
		//打开审核弹窗
		function openShDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairWorkRecord/shenHeDialog?id="+id,"工作纪实评价",800,420,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
				closed:function (){
					self.location.href="${ctx}/affair/affairWorkRecord/list";
				}
			});
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairWorkRecord/">民警记实</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairWorkRecord" action="${ctx}/affair/affairWorkRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="民警纪实报表.xlsx"/>
		<ul class="ul-form">

			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>

			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairWorkRecord.unitId}" labelName="unit" labelValue="${affairWorkRecord.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>

			<li><label>年份：</label>
				<form:input path="years" htmlEscape="false" class="input-medium"/>
			</li>

			<li><label>周：</label>
				<form:input path="weeks" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>开始时间：</label>
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairWorkRecord.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>结束时间：</label>
				<input name="overDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairWorkRecord.overDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="clearfix"></li>
			<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="affair:affairWorkRecord:edit">
					<li class="btns"><input class="btn btn-primary" type="button" value="添加"
											onclick="openDialog('${ctx}/affair/affairWorkRecord/form')"/></li>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
											onclick="deleteByIds('${ctx}/affair/affairWorkRecord/deleteByIds','checkAll','myCheckBox')"/>
					</li>
					<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				</shiro:hasPermission>
				<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairWorkRecord'"/></li>
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
				<th>单位</th>
				<th>姓名</th>
				<th>职务</th>
				<th>年份</th>
				<th>周</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>岗位工作完成情况</th>
				<th>单位负责人意见</th>
				<th>审核状态</th>
				<th>评价</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairWorkRecord" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairWorkRecord.id}"/>
				</td>
				<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${affairWorkRecord.unit}
				</td>
				<td>
						${affairWorkRecord.name}
				</td>
				<td>
						${affairWorkRecord.postNape}
				</td>
				<td>
					${affairWorkRecord.years}
				</td>
				<td>
					${affairWorkRecord.weeks}
				</td>
				<td>
					<fmt:formatDate value="${affairWorkRecord.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${affairWorkRecord.overDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${affairWorkRecord.jobCompletionCondition}
				</td>
				<td>
					${affairWorkRecord.unitPrincipalOpinion}
				</td>
				<td>
						${fns:getDictLabel(affairWorkRecord.checkType, 'check_type', '') }
				</td>
				<td>
						${fns:getDictLabel(affairWorkRecord.evaluate, 'work_record_evaluate', '') }
				</td>
				<td>
					<a onclick="openDetailDialog('${affairWorkRecord.id}')" style="cursor: pointer">查看</a>
					<%--当前用户为系统管理员 或 当前用户为该条数据的审核人   且  该条数据状态为 审核中  或  转送上一级--%>
					<c:if test="${(fns:getUser().id eq '1' || affairWorkRecord.chuCheckId eq fns:getUser().id) && (affairWorkRecord.checkType eq '2' || affairWorkRecord.checkType eq '3')}">
						<a onclick="openShDialog('${affairWorkRecord.id}')" style="cursor: pointer">审核</a>
					</c:if>
					<shiro:hasPermission name="affair:affairWorkRecord:edit">
					<c:if test="${affairWorkRecord.checkType ne '2' && affairWorkRecord.checkType ne '3' && affairWorkRecord.checkType ne '4'}">
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairWorkRecord/form?id=${affairWorkRecord.id}')">修改</a>
					</c:if>
					<a href="${ctx}/affair/affairWorkRecord/delete?id=${affairWorkRecord.id}" onclick="return confirmx('确认要删除该民警记实功能吗？', this.href)">删除</a>
				</shiro:hasPermission>

				<%--<shiro:hasPermission name="affair:affairWorkRecord:fuzeren">
					<a href="javascript:void(0)" onclick="openSaveUnit('${ctx}/affair/affairWorkRecord/formSaveUnit?id=${affairWorkRecord.id}')">录入单位负责人意见</a>
				</shiro:hasPermission>--%>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<form:form id="searchForm2" modelAttribute="affairWorkRecord" action="${ctx}/affair/affairWorkRecord/submitByIds" method="post" class="breadcrumb form-search">
		<ul class="ul-form" style="text-align: right">
			<font color="red">请选择审核用户：</font>
			<input type="hidden" name="ids" id="idsValue"/>
			<sys:treeselect id="chuCheckMan" name="chuCheckId" value="${affairWorkRecord.chuCheckId}" labelName="chuCheckMan" labelValue="${affairWorkRecord.chuCheckMan}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true" dataMsgRequired="必填信息" isAll="true"/>
			<input  class="btn btn-primary" type="button" value="提交送审" onclick="submitByIds()"/>
		</ul>
	</form:form>
</body>
</html>