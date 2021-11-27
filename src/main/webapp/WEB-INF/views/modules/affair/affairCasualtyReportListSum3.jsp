<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>伤亡信息-单位-类别</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//这是打印功能的JS
			$("#print").click(function(){
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
					formValues: false
				});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//点单位名称出现的弹窗
		function openDialogByUnitName(id,name) {
			var url = "iframe:${ctx}/affair/affairCasualtyReport/findByUnitId?unitId="+id;
			top.$.jBox.open(url, name,1000,700,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		//点种类数量出现的弹窗
		function openDialogByNum(id,unitName,typeName) {
			var url = "iframe:${ctx}/affair/affairCasualtyReport/findByUnitId?unitId="+id;
			top.$.jBox.open(url, "伤亡信息-"+unitName+"-"+typeName,1000,700,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
	</script>
</head>
<body>
	<%--根据某一种伤亡种类统计的页面--%>
	<form:form id="searchForm" modelAttribute="affairCasualtyReport" action="${ctx}/affair/affairCasualtyReport/findOneTypeByUnitId" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="type" name="type" type="hidden" value="${type}"/>
		<input id="typeName" name="typeName" type="hidden" value="${typeName}"/>
		<input id="unitId" name="unitId" type="hidden" value="${unitId}"/>
		<ul class="ul-form">
			<li><label>伤亡时间：</label>
				<input name="beginCasualtyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairCasualtyReport.beginCasualtyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endCasualtyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairCasualtyReport.endCasualtyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>单位名称：</label>
				<sys:treeselect id="affirmDep" name="affirmDepId" value="${affairCasualtyReport.affirmDepId}" labelName="affirmDep" labelValue="${affairCasualtyReport.affirmDep}"
					title="单位名称" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairCasualtyReport:edit">
				<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			</shiro:hasPermission>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairCasualtyReport/findOneTypeByUnitId'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
                <th>单位</th>
				<c:forEach items="${statistic}" var="affairCasualtyReportStatistic">
					<th>${affairCasualtyReportStatistic.unitName}</th>
				</c:forEach>
				<th>合计</th>
			</tr>
		</thead>
		<tbody>
		<tr>
			<td>${typeName}</td>
			<c:forEach items="${statistic}" var="affairCasualtyReportStatistic">
				<td>${affairCasualtyReportStatistic.oneTypeNum}</td>
			</c:forEach>
			<td>${sum}</td>
		</tr>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>