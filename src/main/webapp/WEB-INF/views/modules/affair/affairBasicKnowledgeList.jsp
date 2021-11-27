<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>基本知识成绩管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairBasicKnowledge/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairBasicKnowledge/list");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairBasicKnowledge/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairBasicKnowledge/list");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairBasicKnowledge", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairBasicKnowledge/list?unitId=${unitId}&unit=${unit}"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "基本知识成绩",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairBasicKnowledge/list?unitId=${unitId}&unit=${unit}&pageNo=${page.pageNo}&pageSize=${page.pageSize}"}
			});
		}
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairBasicKnowledge/formDetail?id="+id;
			top.$.jBox.open(url, "基本知识成绩",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
	</script>
</head>
<body>

	<form:form id="searchForm" modelAttribute="affairBasicKnowledge" action="${ctx}/affair/affairBasicKnowledge/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="基本知识成绩.xlsx"/>
		<ul class="ul-form">
			<li><label>时间：</label>
				<input id="yearMonth" name="yearMonth" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="${yearMonth}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width: 120px;"/>
			</li>
			<li><label>考试：</label>
				<form:input path="exam" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>警号：</label>
				<form:input path="policeNo" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>机构：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairBasicKnowledge.unitId}" labelName="unit" labelValue="${affairBasicKnowledge.unit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="fasle"/>
			</li>
			<li><label>证件号：</label>
				<form:input path="idNumber" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>通过状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pass_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairBasicKnowledge:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairBasicKnowledge/form?id=${affairBasicKnowledge.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairBasicKnowledge/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairBasicKnowledge/list'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>考试</th>
				<th>警号</th>
				<th>姓名</th>
				<th>证件号码</th>
				<th>机构</th>
<%--				<th>进入考试时间</th>--%>
<%--				<th>交卷时间</th>--%>
<%--				<th>客观成绩</th>--%>
<%--				<th>主观成绩</th>--%>
				<th>最终成绩</th>
				<th>通过状态</th>
				<shiro:hasPermission name="affair:affairBasicKnowledge:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairBasicKnowledge" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairBasicKnowledge.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
<%--								${affairBasicKnowledge.number}--%>
				</td>
				<td>
					${affairBasicKnowledge.exam}
				</td>
				<td>
					${affairBasicKnowledge.policeNo}
				</td>
				<td>
					${affairBasicKnowledge.name}
				</td>
				<td>
					${affairBasicKnowledge.idNumber}
				</td>
				<td>
					${affairBasicKnowledge.unit}
				</td>
			<%--	<td>
					<fmt:formatDate value="${affairBasicKnowledge.enterTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>--%>
			<%--	<td>
					<fmt:formatDate value="${affairBasicKnowledge.handoverTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>--%>
				<%--<td>
					${affairBasicKnowledge.objectiveResults}
				</td>
				<td>
					${affairBasicKnowledge.subjectivePerformance}
				</td>--%>
				<td>
					${affairBasicKnowledge.finalResult}
				</td>
				<td>
					${fns:getDictLabel(affairBasicKnowledge.status, 'pass_status', '')}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairBasicKnowledge.id}')">查看</a>
				<shiro:hasPermission name="affair:affairBasicKnowledge:edit">
					<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairBasicKnowledge/form?id=${affairBasicKnowledge.id}')">修改</a>
					<a href="${ctx}/affair/affairBasicKnowledge/delete?id=${affairBasicKnowledge.id}" onclick="return confirmx('确认要删除该基本知识成绩吗？', this.href)">删除</a>
				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>