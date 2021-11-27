<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>伤亡信息查询</title>
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
		function openDialogByNum(id,unitName,typeName,type) {
			var url = "iframe:${ctx}/affair/affairCasualtyReport/findOneTypeByUnitId?unitId="+id+"&type="+type+"&typeName="+typeName;
			top.$.jBox.open(url, "伤亡信息-"+unitName+"-"+typeName,1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairCasualtyReport/">抚恤申报</a></li>
		<shiro:hasPermission name="affair:affairCasualtyReport:manage"><li ><a href="${ctx}/affair/affairCasualtyReport/manageList">抚恤审核</a></li></shiro:hasPermission>
		<li ><a href="${ctx}/personnel/personnelPensionRecord?mType=3">抚恤发放</a></li>
		<li class="active"><a href="${ctx}/affair/affairCasualtyReport/statistic">伤亡信息查询</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairCasualtyReport" action="${ctx}/affair/affairCasualtyReport/statistic" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
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
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairCasualtyReport/statistic'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
                <th>单位</th>
				<th>因公牺牲</th>
				<th>病故</th>
				<th>伤残</th>
				<th>伤亡</th>
				<th>合计</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${statistic}" var="affairCasualtyReportStatistic">
				<tr>
					<td>
						<a onclick="openDialogByUnitName('${affairCasualtyReportStatistic.unitId}','${affairCasualtyReportStatistic.unitName}')">${affairCasualtyReportStatistic.unitName}</a>
					</td>
					<td>
						<a onclick="openDialogByNum('${affairCasualtyReportStatistic.unitId}','${affairCasualtyReportStatistic.unitName}','因公牺牲',1)">${affairCasualtyReportStatistic.xiSheng}</a>
					</td>
					<td>
						<a onclick="openDialogByNum('${affairCasualtyReportStatistic.unitId}','${affairCasualtyReportStatistic.unitName}','病故'),2">${affairCasualtyReportStatistic.bingGu}</a>
					</td>
					<td>
						<a onclick="openDialogByNum('${affairCasualtyReportStatistic.unitId}','${affairCasualtyReportStatistic.unitName}','伤残',3)">${affairCasualtyReportStatistic.shangCan}</a>
					</td>
					<td>
						<a onclick="openDialogByNum('${affairCasualtyReportStatistic.unitId}','${affairCasualtyReportStatistic.unitName}','伤亡',4)">${affairCasualtyReportStatistic.shangWang}</a>
					</td>
					<td>
						<a onclick="openDialogByNum('${affairCasualtyReportStatistic.unitId}','${affairCasualtyReportStatistic.unitName}','合计')">${affairCasualtyReportStatistic.sum}</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>