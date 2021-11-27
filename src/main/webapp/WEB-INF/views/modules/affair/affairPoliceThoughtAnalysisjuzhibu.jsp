<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民警思想动态分析管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#btnPrint").click(function(){
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$('#checkTh').css('display', 'none');
				$('.checkTd').css('display', 'none');
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
						$('#checkTh').css('display', 'table-cell');
						$('.checkTd').css('display', 'table-cell');
					}
				});
			});

			$("#btnExport").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairPoliceThoughtAnalysis/export?classify=2");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPoliceThoughtAnalysis/jiguanzhibu?treeId=${treeId}");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairPoliceThoughtAnalysis/export?flag=true&classify=2");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPoliceThoughtAnalysis/jiguanzhibu?treeId=${treeId}");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairPoliceThoughtAnalysisJiGuanZhiBu", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairPoliceThoughtAnalysis/jiguanzhibu"}});
			});
			$("#btnAdd").click(function(){
				top.$.jBox.open("iframe:${ctx}/affair/affairPoliceThoughtAnalysis/form?classify=2", "添加",800,520,{itle:"添加", buttons:{"关闭":true}

				});

			});

			if ("sucess" == "${saveResult}") {
				parent.$.jBox.close();
				closeLoading();
				top.$.jBox.closeTip();
			}

		});


		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
        }
        //详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairPoliceThoughtAnalysis/formDetail?id="+id;
			top.$.jBox.open(url, "民警动态思想分析详情",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }
        //编辑弹窗
		function openEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairPoliceThoughtAnalysis/form?id="+id;
			top.$.jBox.open(url, "思想分析",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }

    </script>
</head>
<body>
<ul class="nav nav-tabs">

	<%--只有公安局党委可查看此页面--%>
	<c:if test="${fns:getUser().id == '66937439b2124f328d1521968fab06db'||fns:getUser().id == '南宁局政治部宣传教育处'}">
		<li><a href="${ctx}/affair/affairPoliceThoughtAnalysis/ju">公安局党委</a></li>

	</c:if>
	<%--只有公安局党委和局机关党委可查看此页面--%>
	<c:if test="${fns:getUser().id == '66937439b2124f328d1521968fab06db'||fns:getUser().id == '8a6819768aef40968e8f289842613276'}">
		<%--||
						fns:getUser().id == 'a4e40825e3b34c37895ee2ed168f24a0'||fns:getUser().id == 'd154234ecb35470e84fb95e53726866b'||
						fns:getUser().id == 'e3ac8381fb3247e0b64fd6e3c48bddc1'||fns:getUser().id == '82f8e9ba1cd7446b99c084d5b4b5c038'||
						fns:getUser().id == '94ccb0bdb21441f997c72268b4d882db'||fns:getUser().id == 'd30e324c8f73492d9b74103374fbc689'--%>
		<li><a href="${ctx}/affair/affairPoliceThoughtAnalysis/jujiguan">公安局机关党委</a></li>

	</c:if>
	<%--只有公安局党委和公安处党委可查看此页面--%>
	<c:if test="${fns:getUser().id == '66937439b2124f328d1521968fab06db'||fns:getUser().id == '8a6819768aef40968e8f289842613276' ||
						fns:getUser().id == '南宁处政治处宣传教育室（民警训练基地）'||fns:getUser().id == 'd154234ecb35470e84fb95e53726866b'||
						fns:getUser().id == 'e3ac8381fb3247e0b64fd6e3c48bddc1'||fns:getUser().id == '北海处政治处宣传教育室（民警训练基地）'||
						fns:getUser().id == '柳州处政治处宣传教育室（民警训练基地）'||fns:getUser().id == 'd30e324c8f73492d9b74103374fbc689'}">
		<li><a href="${ctx}/affair/affairPoliceThoughtAnalysis/chu">公安处党委</a></li>

	</c:if>
	<%--只有公安局党委和局机关党支部可查看此页面--%>
	<c:if test="${fns:getUser().id == '66937439b2124f328d1521968fab06db'||fns:getUser().id == '8a6819768aef40968e8f289842613276' ||
			fns:getUser().company.id.equals('1') && !fns:getUser().office.id.equals('34')&&
			!fns:getUser().office.id.equals('95')&& !fns:getUser().office.id.equals('156') }">
		<li class="active"><a href="${ctx}/affair/affairPoliceThoughtAnalysis/jiguanzhibu">局机关党支部</a></li>

	</c:if>
	<%--只有基层可查看此页面--%>
	<c:if test="${fns:getUser().company.id =='34'||fns:getUser().company.id =='95'||fns:getUser().company.id =='156'}">
		<li><a href="${ctx}/affair/affairPoliceThoughtAnalysis/jiceng">基层党支部</a></li>

	</c:if>
</ul>
<form:form id="searchForm" modelAttribute="affairPoliceThoughtAnalysis" action="${ctx}/affair/affairPoliceThoughtAnalysis/jiguanzhibu" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<input id="fileName" name="fileName" type="hidden" value="3.3思想分析.xlsx"/>

	<ul class="ul-form">
		<%--<li><label>时间：</label>
			<input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				   value="<fmt:formatDate value="${affairPoliceThoughtAnalysis.startTime}" pattern="yyyy-MM-dd"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			至
			<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				   value="<fmt:formatDate value="${affairPoliceThoughtAnalysis.endTime}" pattern="yyyy-MM-dd"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		</li>
		<li><label>单位：</label>
			<form:input path="unit" htmlEscape="false" class="input-medium"/>
		</li>
		<li>
			<label>报告类型：</label>
				<form:select path="reportType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('report_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
		</li>

		<li>
			<label>状态：</label>
			<form:select path="reportStatus" class="input-xlarge required">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('thought_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</li>--%>
			<li><label>时间：</label>
				<input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairPoliceThoughtAnalysis.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li class="control-group">
				<label>报告类型：</label>
				<form:select path="reportType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('report_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>

	</ul>
    <ul class="ul-form2">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
        <li class="btns"><input id="btnPrint" class="btn btn-primary" type="button" value="打印"/></li>
		<c:if test="${fns:getUser().id != '66937439b2124f328d1521968fab06db'&&fns:getUser().id != '8a6819768aef40968e8f289842613276' &&
			fns:getUser().company.id.equals('1') && !fns:getUser().office.id.equals('34')&&
			!fns:getUser().office.id.equals('95')&& !fns:getUser().office.id.equals('156') }">

		<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
		<li class="btns"><input id="btnAdd" class="btn btn-primary" type="button" value="添加"/></li>
		</c:if>

        <li class="btns"><input id="btnRese" class="btn btn-primary" type="button" value="重置"
                                onclick="window.location.href='${ctx}/affair/affairPoliceThoughtAnalysis/jiguanzhibu'"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/></th>
		<th>序号</th>
		<th>单位</th>
		<th>报告类型</th>
<%--		<th>签收时间</th>--%>
		<th>上报状态</th>
		<th>报告时间</th>
		<shiro:hasPermission name="affair:affairPoliceThoughtAnalysis:edit"><th id="handleTh">操作</th></shiro:hasPermission>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="affairPoliceThoughtAnalysis" varStatus="status">
		<tr>
			<td class="checkTd">
				<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairPoliceThoughtAnalysis.id}"/>
			</td>
			<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
			</td>
			<td>
					${affairPoliceThoughtAnalysis.unit}
			</td>
			<td>
					${fns:getDictLabel(affairPoliceThoughtAnalysis.reportType, 'report_type', '')}
			</td>
<%--			<td>--%>
<%--				<fmt:formatDate value="${affairPoliceThoughtAnalysis.signInTime}" pattern="yyyy-MM-dd"/>--%>
<%--			</td>--%>
			<td>
					${fns:getDictLabel(affairPoliceThoughtAnalysis.reportStatus, 'thought_status', '')}
			</td>
			<td>
				<fmt:formatDate value="${affairPoliceThoughtAnalysis.reportTime}" pattern="yyyy-MM-dd"/>
			</td>
			<%--<td>
					${affairPoliceThoughtAnalysis.content}
			</td>--%>

<%--			<shiro:hasPermission name="affair:affairPoliceThoughtAnalysis:edit">--%>
                <td class="handleTd">
					<a href="javascript:" onclick="openDetailDialog('${affairPoliceThoughtAnalysis.id}')">查看</a>
					<c:if test="${ affairPoliceThoughtAnalysis.createBy.id == fns:getUser().id}">

						<a href="javascript:" onclick="openEditDialog('${affairPoliceThoughtAnalysis.id}')">修改</a>
						<a href="${ctx}/affair/affairPoliceThoughtAnalysis/delete?id=${affairPoliceThoughtAnalysis.id}"
						   onclick="return confirmx('确认要删除该民警思想动态分析吗？', this.href)">删除</a>
						<a href="${ctx}/affair/affairPoliceThoughtAnalysis/report?id=${affairPoliceThoughtAnalysis.id}"
						   onclick="return confirmx('确认要上报该民警思想动态分析吗？', this.href)">上报</a>
					</c:if>
				</td>
<%--            </shiro:hasPermission>--%>
		</tr>
	</c:forEach>
	</tbody>
</table>
<%--<div class="pagination">${page}</div>--%>
</body>
</html>