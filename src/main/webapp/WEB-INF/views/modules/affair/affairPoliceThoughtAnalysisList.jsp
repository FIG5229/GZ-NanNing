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
								$("#searchForm").attr("action","${ctx}/affair/affairPoliceThoughtAnalysis/export?treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPoliceThoughtAnalysis/list?treeId=${treeId}");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairPoliceThoughtAnalysis/export?flag=true&treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPoliceThoughtAnalysis/list?treeId=${treeId}");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairPoliceThoughtAnalysisJiCeng", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairPoliceThoughtAnalysis/list"}});
			});
			$("#btnAdd").click(function(){
				top.$.jBox.open("iframe:${ctx}/affair/affairPoliceThoughtAnalysis/form", "添加",800,520,{itle:"添加", buttons:{"关闭":true}

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

	<%--宣教的管理账号进行查看 签收--%>
		<%--list为签收页面 manage为新增上报页面--%>
	<c:choose>
		<%--公安局宣传教育管理		公安局政治部宣传教育处--%>
		<c:when test="${userId=='66937439b2124f328d1521968fab06db' || userId == '8a6819768aef40968e8f289842613276'}">
			<li class="active"><a href="${ctx}/affair/affairPoliceThoughtAnalysis/list">公安处党委</a></li>
		</c:when>
		<%--北海处宣传思想管理	柳州处宣传教育管理 	南宁处宣传思想管理--%>
		<c:when test="${userId == 'e3ac8381fb3247e0b64fd6e3c48bddc1' || userId=='d30e324c8f73492d9b74103374fbc689'|| userId=='d154234ecb35470e84fb95e53726866b'}">
			<li class="active"><a href="${ctx}/affair/affairPoliceThoughtAnalysis/list">基层党支部</a></li>
			<li><a href="${ctx}/affair/affairPoliceThoughtAnalysis/manage">公安处党委</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="${ctx}/affair/affairPoliceThoughtAnalysis/manage">基层党支部</a></li>
			<%--其他账号进行上报--%>
		</c:otherwise>
	</c:choose>

   <%-- <shiro:hasPermission name="affair:affairPoliceThoughtAnalysis:browse">
		<li class="active"><a href="${ctx}/affair/affairPoliceThoughtAnalysis/">
			<shiro:hasRole name="bureau_education">派出所上报</shiro:hasRole>
			<shiro:lacksRole name="bureau_education">思想分析</shiro:lacksRole>
		</a></li>
	</shiro:hasPermission>
    <shiro:hasPermission name="affair:affairPoliceThoughtAnalysis:manage">
		<li><a href="${ctx}/affair/affairPoliceThoughtAnalysis/manage">
           &lt;%&ndash; <c:choose>
                <c:when test="${id=='66937439b2124f328d1521968fab06db' || id == 'd154234ecb35470e84fb95e53726866b' ||
                id == '8a6819768aef40968e8f289842613276' || id=='d30e324c8f73492d9b74103374fbc689'|| id=='e3ac8381fb3247e0b64fd6e3c48bddc1'} ">
                    <shiro:hasPermission name="affair:affairPoliceThoughtAnalysis:browse">
                        <li class="active"><a href="${ctx}/affair/affairPoliceThoughtAnalysis/">
                            思想分析
                        </a></li>
                    </shiro:hasPermission>
                </c:when>
            </c:choose>&ndash;%&gt;
			<shiro:hasRole name="bureau_education">上报公安局</shiro:hasRole>
			<shiro:lacksRole name="bureau_education">思想分析</shiro:lacksRole>
			</a></li>
	</shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="affairPoliceThoughtAnalysis" action="${ctx}/affair/affairPoliceThoughtAnalysis/list" method="post" class="breadcrumb form-search">
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
		<c:if test="${userId == '66937439b2124f328d1521968fab06db' || userId == '8a6819768aef40968e8f289842613276' ||
					userId == 'a4e40825e3b34c37895ee2ed168f24a0' || userId=='94ccb0bdb21441f997c72268b4d882db' || userId=='82f8e9ba1cd7446b99c084d5b4b5c038'}">
			<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			<li class="btns"><input id="btnAdd" class="btn btn-primary" type="button" value="添加"/></li>
		</c:if>
        <li class="btns"><input id="btnRese" class="btn btn-primary" type="button" value="重置"
                                onclick="window.location.href='${ctx}/affair/affairPoliceThoughtAnalysis/list'"/></li>
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
		<th>签收时间</th>
		<th>签收状态</th>
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
			<td>
				<fmt:formatDate value="${affairPoliceThoughtAnalysis.signInTime}" pattern="yyyy-MM-dd"/>
			</td>
			<td>
					${fns:getDictLabel(affairPoliceThoughtAnalysis.reportStatus, 'thought_status', '')}
			</td>
			<td>
				${affairPoliceThoughtAnalysis.time}
			</td>
			<%--<td>
					${affairPoliceThoughtAnalysis.content}
			</td>--%>

<%--			<shiro:hasPermission name="affair:affairPoliceThoughtAnalysis:edit">--%>
                <td class="handleTd">
					<a href="javascript:" onclick="openDetailDialog('${affairPoliceThoughtAnalysis.id}')">查看</a>
					<c:if test="${ affairPoliceThoughtAnalysis.createBy.id != fns:getUser().id}">

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