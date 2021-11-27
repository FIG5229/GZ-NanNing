<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>基本体能成绩管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairBasicFitness/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairBasicFitness/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairBasicFitness/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairBasicFitness/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairBasicFitness", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairBasicFitness/list?unitId=${unitId}&unit=${unit}"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "基本体能成绩",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairBasicFitness/list?unitId=${unitId}&unit=${unit}"}
			});
		}
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairBasicFitness/formDetail?id="+id;
			top.$.jBox.open(url, "基本体能成绩",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
	</script>
</head>
<body>

	<form:form id="searchForm" modelAttribute="affairBasicFitness" action="${ctx}/affair/affairBasicFitness/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="基本体能成绩.xlsx"/>
		<ul class="ul-form">
			<li><label>时间：</label>
				<input id="yearMonth" name="yearMonth" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="${yearMonth}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width: 120px;"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>证件号：</label>
				<form:input path="idNumber" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>性别：</label>
				<form:select path="sex" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairBasicFitness.unitId}" labelName="unit" labelValue="${affairBasicFitness.unit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>综合评定：</label>
				<form:select path="comprehensiveAssessment" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pass_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairBasicFitness:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairBasicFitness/form?id=${affairBasicFitness.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairBasicFitness/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairBasicFitness/list?unitId=${unitId}&unit=${unit}'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th rowspan="2" style="text-align: center"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th rowspan="2" style="text-align: center">序号</th>
				<th rowspan="2" style="text-align: center">姓名</th>
				<th rowspan="2" style="text-align: center">年龄</th>
				<th rowspan="2" style="text-align: center">性别</th>
				<th rowspan="2" style="text-align: center">单位</th>
				<th rowspan="2" style="text-align: center">身高（米）</th>
				<th rowspan="2" style="text-align: center">体重（kg）</th>
				<th colspan="2" style="text-align: center">身体质量指数（BMI）</th>
				<th colspan="2" style="text-align: center">50米跑</th>
				<th colspan="2" style="text-align: center">立定跳远</th>
				<th colspan="2" style="text-align: center">2000米跑</th>
				<th colspan="2" style="text-align: center">仰卧起坐</th>
				<th colspan="2" style="text-align: center">引体向上</th>
				<th colspan="2" style="text-align: center">俯卧撑</th>
				<th colspan="2" style="text-align: center">握力</th>
				<th colspan="2" style="text-align: center">坐位体前屈</th>
				<th rowspan="2" style="text-align: center">综合评定</th>
				<th rowspan="2" style="text-align: center">备注</th>
				<shiro:hasPermission name="affair:affairBasicFitness:edit"><th id="handleTh" rowspan="4" style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
			<tr>
				<th style="text-align: center">数值</th>
				<th style="text-align: center">评定</th>
				<th style="text-align: center">成绩（秒）</th>
				<th style="text-align: center">是否达标</th>
				<th style="text-align: center">成绩（米）</th>
				<th style="text-align: center">是否达标</th>
				<th style="text-align: center">成绩（分秒）</th>
				<th style="text-align: center">是否达标</th>
				<th style="text-align: center">成绩（个）</th>
				<th style="text-align: center">是否达标</th>
				<th style="text-align: center">成绩（个）</th>
				<th style="text-align: center">是否达标</th>
				<th style="text-align: center">成绩（个）</th>
				<th style="text-align: center">是否达标</th>
				<th style="text-align: center">成绩（kg）</th>
				<th style="text-align: center">是否达标</th>
				<th style="text-align: center">成绩（cm）</th>
				<th style="text-align: center">是否达标</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairBasicFitness" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairBasicFitness.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
<%--								${affairBasicFitness.number}--%>
				</td>
				<td>
					${affairBasicFitness.name}
				</td>
				<td>
					${affairBasicFitness.age}
				</td>
				<td>
						${fns:getDictLabel(affairBasicFitness.sex, 'sex', '')}
				</td>
				<td>
					${affairBasicFitness.unit}
				</td>
				<td>
					${affairBasicFitness.height}
				</td>
				<td>
						${affairBasicFitness.weight}
				</td>
				<td>
						${affairBasicFitness.value}
				</td>
				<td>
						${fns:getDictLabel(affairBasicFitness.assess, 'fitness_assess', '')}
				</td>
				<td>
					${affairBasicFitness.fiftyRunScore}
				</td>
				<td>
					${fns:getDictLabel(affairBasicFitness.fiftyRunStatus, 'yes_no', '')}
				</td>
				<td>
						${affairBasicFitness.jumpScore}
				</td>
				<td>
						${fns:getDictLabel(affairBasicFitness.jumpStatus, 'yes_no', '')}
				</td>
				<td>
						${affairBasicFitness.twokmRunScore}
				</td>
				<td>
						${fns:getDictLabel(affairBasicFitness.twokmRunStatus, 'yes_no', '')}
				</td>
				<td>
					${affairBasicFitness.nvSitUpsScore}
				</td>
				<td>
					${fns:getDictLabel(affairBasicFitness.nvSitUpsStatus, 'yes_no', '')}
				</td>
				<td>
					${affairBasicFitness.nanPullUpsScore}
				</td>
				<td>
					${fns:getDictLabel(affairBasicFitness.nanPullUpsStatus, 'yes_no', '')}
				</td>
				<td>
					${affairBasicFitness.nanPushUpsScore}
				</td>
				<td>
					${fns:getDictLabel(affairBasicFitness.nanPushIpsStatus, 'yes_no', '')}
				</td>
				<td>
					${affairBasicFitness.gripScore}
				</td>
				<td>
					${fns:getDictLabel(affairBasicFitness.gripStatus, 'yes_no', '')}
				</td>
				<td>
					${affairBasicFitness.sittingForwardBendingScore}
				</td>
				<td>
					${fns:getDictLabel(affairBasicFitness.sittingForwardBendingStatus, 'yes_no', '')}
				</td>
				<td>
						${fns:getDictLabel(affairBasicFitness.comprehensiveAssessment, 'pass_status', '')}
<%--					${affairBasicFitness.comprehensiveAssessment}--%>
				</td>
				<td>
					${affairBasicFitness.remark}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairBasicFitness.id}')">查看</a>
				<shiro:hasPermission name="affair:affairBasicFitness:edit">
					<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairBasicFitness/form?id=${affairBasicFitness.id}')">修改</a>
					<a href="${ctx}/affair/affairBasicFitness/delete?id=${affairBasicFitness.id}&unitId=${unitId}&unit=${unit}" onclick="return confirmx('确认要删除该基本体能成绩吗？', this.href)">删除</a>
				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>