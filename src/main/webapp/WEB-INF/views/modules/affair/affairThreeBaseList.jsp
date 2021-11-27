<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>三基综合管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairThreeBase/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairThreeBase/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairThreeBase/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairThreeBase/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairThreeBase", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairThreeBase/list?unitId=${unitId}&unit=${unit}"}});
			});
			});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "三基综合",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairThreeBase/list?unitId=${unitId}&unit=${unit}"}
			});
		}
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairThreeBase/formDetail?id="+id;
			top.$.jBox.open(url, "三基综合",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairBasicKnowledge/">基本知识</a></li>
		<li><a href="${ctx}/affair/affairBasicFitness/">基本体能</a></li>
		<li><a href="${ctx}/affair/affairBaseSkill/">基本技能</a></li>
		<li class="active"><a href="${ctx}/affair/affairThreeBase/">综合成绩</a></li>
	</ul>--%>
	<form:form id="searchForm" modelAttribute="affairThreeBase" action="${ctx}/affair/affairThreeBase/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="三基综合.xlsx"/>
		<ul class="ul-form">
			<li><label>时间：</label>
				<input id="yearMonth" name="yearMonth" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="${yearMonth}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width: 120px;"/>
			</li>
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairThreeBase.unitId}" labelName="unit" labelValue="${affairThreeBase.unit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>证件号：</label>
				<form:input path="idNumber" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>成绩总评：</label>
				<form:select path="overallScore" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pass_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairThreeBase:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairThreeBase/form?id=${affairThreeBase.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairThreeBase/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairThreeBase/list?unitId=${unitId}&unit=${unit}'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>单位</th>
				<th>姓名</th>
				<th>性别</th>
				<th>年龄</th>
				<th>职务</th>
				<th>基本知识</th>
				<th>基本体能</th>
				<th>基本技能</th>
				<th>成绩总评</th>
				<th>备注</th>
				<shiro:hasPermission name="affair:affairThreeBase:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairThreeBase" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairThreeBase.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
<%--								${affairThreeBase.number}--%>
				</td>
				<td>
					${affairThreeBase.unit}
				</td>
				<td>
					${affairThreeBase.name}
				</td>
				<td>
						${fns:getDictLabel(affairThreeBase.sex, 'sex', '')}
				</td>
				<td>
					${affairThreeBase.age}
				</td>
				<td>
					${affairThreeBase.job}
				</td>
				<td>
					${affairThreeBase.basicKnowledge}
				</td>
				<td>
					${affairThreeBase.basicFitness}
				</td>
				<td>
						${affairThreeBase.baseSkill}
				</td>
				<td>
<%--					${affairThreeBase.overallScore}--%>
							${fns:getDictLabel(affairThreeBase.overallScore, 'pass_status', '')}
				</td>
				<td>
					${affairThreeBase.remark}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairThreeBase.id}')">查看</a>
				<shiro:hasPermission name="affair:affairThreeBase:edit">
					<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairThreeBase/form?id=${affairThreeBase.id}')">修改</a>
					<a href="${ctx}/affair/affairThreeBase/delete?id=${affairThreeBase.id}" onclick="return confirmx('确认要删除该三基综合吗？', this.href)">删除</a>
				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>