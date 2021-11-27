<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人员基本情况信息管理</title>
	<meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
				function(){
					var submit = function (v, h, f) {
						if (v == 'all') {
							$("#searchForm").attr("action","${ctx}/personnel/personnelBase/export?peopleStatus=1");
							$("#searchForm").submit();
							$("#searchForm").attr("action","${ctx}/personnel/personnelBase/list?type=${type}");
						}
						if (v == 'part') {
							$("#searchForm").attr("action","${ctx}/personnel/personnelBase/export?flag=true&peopleStatus=1");
							$("#searchForm").submit();
							$("#searchForm").attr("action","${ctx}/personnel/personnelBase/list?type=${type}");
						}
						if (v == 'cancel') {
							$.jBox.tip('已取消');
						}
						return true;
					};
					$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
				}
			);

			$("#renyuan").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/renYuan?flag=1&fileNames=人员名册.xlsx");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/list?type=1");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/renYuan?flag=2&fileNames=人员名册.xlsx");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/list?type=1");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出人员名册？", "人员名册导出确认", submit, { buttons: { '在职': 'all', '非在职': 'part','取消':'cancel'} });
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=personnel_personnelBase", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/personnel/personnelBase/list?type=${type}"}});
			});

			//生成人员名册
		<%--	$("#renyuan").click(--%>
		<%--			function(){--%>
		<%--				$("#searchForm").attr("action","${ctx}/personnel/personnelBase/renYuan?fileNames=人员名册.xlsx");--%>
		<%--				$("#searchForm").submit();--%>
		<%--			}--%>
		<%--);--%>
			$("#appointmentRemoval").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/exportAppointRemoveBatch");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/list?type=${type}");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/exportAppointRemoveBatch?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/list?type=${type}");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
			$("#policeCard").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/exportPoliceCardBatch");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/list?type=${type}");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/exportPoliceCardBatch?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/list?type=${type}");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
			//复合查询导出
			$("#complexSelexport").click(
					function(){
						var pageNo = $("#pageNo").val();
						var pageSize = $("#pageSize").val();
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/complexSelexport");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/?isComplexSelForm=isComplexSelForm");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/complexSelexport?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/?isComplexSelForm=isComplexSelForm");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
			//批量查询导出
			$("#batchSelexport").click(
					function(){
						var pageNo = $("#pageNo").val();
						var pageSize = $("#pageSize").val();
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/batchSelexport");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/?isBatchSelForm=isBatchSelForm");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/batchSelexport?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/?isBatchSelForm=isBatchSelForm");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

		function openDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelBase/index?id="+id;
			top.$.jBox.open(url, "",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		function openGenerationDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelBase/excel?id="+id;
			top.$.jBox.open(url, "",500,300,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//添加function,用于打开form添加页面弹窗,需要传一个url
		function openForm(url,rUrl) {
			if (${personnel != null}){
				rUrl="${ctx}/personnel/personnelBase?idNumber=${personnel}"
			}
			top.$.jBox.open("iframe:"+url, "人员基本情况信息添加",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=rUrl}
			});
		}

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelBase/formDetail?id="+id;

			top.$.jBox.open(url, "",800,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//上方人员信息跳转人员信息详情页面
		function openFormDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelBase/generalFormDetail?id="+id;
			top.$.jBox.open(url, "",1000,610,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//批量修改单位
		function editUnitByIds(url, checkAllId, checkBoxsName) {
			var editIds = getIds(checkBoxsName);
			if (editIds.length > 0) {
				var isStr = editIds.join(',');
				top.$.jBox.open("iframe:" + url + "?ids=" + isStr, "修改工作单位", 700, 520, {
					buttons: {"关闭": true},
					loaded: function () {
						$(".jbox-content", top.document).css("overflow-y", "hidden");
					}, closed: function () {
						self.location.href = '${ctx}/personnel/personnelBase?';
					}
				});
			} else {
				$.jBox.tip('请先选择要修改的内容');
			}
		}
		//人员名册导出在职或非在职
		function openRenYuan() {
			var url = "iframe:${ctx}/personnel/personnelBase/renYuanChoose";
			top.$.jBox.open(url, "人员名册",400,200,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/personnel/personnelBase?type=3"}
			});
		}

		if ("success" == "${saveResult}"){
			parent.$.jBox.close();
		}
		//打开复合查询弹窗
		function openComplexSelForm(){
			var url = "iframe:${ctx}/personnel/personnelBase/complexSelForm";
			top.$.jBox.open(url, "复合条件查询",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
				closed:function (){
					loading('正在重新生成列表，请稍等...');
					self.location.href="${ctx}/personnel/personnelBase/?isComplexSelForm=isComplexSelForm";
				}
			});
		}
		//打开批量查询的弹窗
		function openBatchSelForm() {
			var url = "iframe:${ctx}/personnel/personnelBase/batchSelForm";
			top.$.jBox.open(url, "批量查询",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
				closed:function (){
					loading('正在重新生成列表，请稍等...');
					self.location.href="${ctx}/personnel/personnelBase/?isBatchSelForm=isBatchSelForm";
				}
			});
		}

		//复合查询跳转页面
		function complexSelPageChange(n,s,v){
			self.location.href="${ctx}/personnel/personnelBase/?isComplexSelForm=isComplexSelForm&pageNo="+n+"&pageSize="+s;
		}

		//批量查询跳转页面
		function batchSelPageChange(n,s,v){
			self.location.href="${ctx}/personnel/personnelBase/?isBatchSelForm=isBatchSelForm&pageNo="+n+"&pageSize="+s;
		}

		//打开考评档案
		function openExamRecord(personId, idNumber, name) {
			var url = '${ctx}/exam/examRecord/list?type=person&personId=' + personId + '&idNumber=' + idNumber + '&name=' + name;
			top.$.jBox.open("iframe:" + url, "考评档案", 1000, 600, {
				buttons: {"关闭": true},
				loaded: function () {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}
			});
		}
	</script>
</head>
<body>
	<c:choose>
		<c:when test="${type == 'age'}">
			<form:form id="searchForm" modelAttribute="personnelBase" action="${ctx}/personnel/personnelBase/personnelAgeList" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="label" name="label" type="hidden" value="${personnelBase.label}"/>
				<input id="unitId" name="unitId" type="hidden" value="${personnelBase.unitId}"/>

			</form:form>
		</c:when>
		<c:when test="${type == 'nation'}">
			<form:form id="searchForm" modelAttribute="personnelBase" action="${ctx}/personnel/personnelBase/chartsNationList" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="label" name="label" type="hidden" value="${personnelBase.label}"/>
				<input id="unitId" name="unitId" type="hidden" value="${personnelBase.unitId}"/>

			</form:form>
		</c:when>
		<c:when test="${type == 'category'}">
			<form:form id="searchForm" modelAttribute="personnelBase" action="${ctx}/personnel/personnelBase/chartsCategoryList" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="label" name="label" type="hidden" value="${personnelBase.label}"/>
				<input id="unitId" name="unitId" type="hidden" value="${personnelBase.unitId}"/>

			</form:form>
		</c:when>
		<c:when test="${type == 'workYear'}">
			<form:form id="searchForm" modelAttribute="personnelBase" action="${ctx}/personnel/personnelBase/chartsWorkYearList" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="label" name="label" type="hidden" value="${personnelBase.label}"/>
				<input id="unitId" name="unitId" type="hidden" value="${personnelBase.unitId}"/>

			</form:form>
		</c:when>
		<c:otherwise></c:otherwise>
	</c:choose>


	<sys:message content="${message}"/>
<c:choose>
<c:when test="${isComplexSelForm != null && isComplexSelForm!='' && isComplexSelForm == 'true'}">
	<%--复合条件查询结果table表格--%>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<c:forEach items="${columnTextList}" var="columnText" varStatus="status">
				<th>${columnText}</th>
			</c:forEach>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="mapList" varStatus="status">
			<tr>
				<c:forEach items="${columnTextList}" var="columnTextName" varStatus="columnstatus">
					<%--<c:forEach items="${mapList}" var="map" varStatus="mapStatus">
                        <c:if test="${map.key == columnTextName}">--%>
					<td>${mapList[columnTextName]}</td>
					<%--	</c:if>
                    </c:forEach>--%>
					<%--<td>${columnTextName}</td>--%>

				</c:forEach>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</c:when>
<c:otherwise>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
<%--				<c:if test="${type eq '1'}">--%>
					<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
<%--				</c:if>--%>
				<th>序号</th>
				<th>单位</th>
				<th>姓名</th>
				<th>身份证号</th>
				<th>警号</th>
				<th>职务简称</th>
				<th>人员状态</th>
				<%--<th>籍贯</th>--%>
				<th>备注</th>

			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelBase" varStatus="status">
			<tr>
<%--				<c:if test="${type eq '1'}">--%>
					<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelBase.id}"/></td>
<%--				</c:if>--%>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${personnelBase.workunitName}
				</td>
				<td>
					${personnelBase.name}
				</td>
				<td>${personnelBase.idNumber}</td>
				<td>
						${personnelBase.policeIdNumber}
				</td>
				<td>
					${personnelBase.jobAbbreviation}
				</td>
				<td>
						${fns:getDictLabel(personnelBase.status, 'personnel_status', '')}
				</td>
				<%--<td>
						${personnelBase.nativePlace}
				</td>--%>
				<td>
					${personnelBase.remarks}
				</td>

			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</c:otherwise>
</c:choose>
</body>
</html>