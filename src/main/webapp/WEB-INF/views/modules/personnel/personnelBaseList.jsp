<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人员基本情况信息管理</title>
	<meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmit").click(function () {
				//表单提交前处理
				$("#pageNo").val('1');
				$("#pageSize").val('10');
			});
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
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/list?type=${type}");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/renYuan?flag=2&fileNames=人员名册.xlsx");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelBase/list?type=${type}");
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

		function openDialog(id,ps) {
			var url = "iframe:${ctx}/personnel/personnelBase/index?id="+id+"&peopleStatus="+ps;
			top.$.jBox.open(url, "",1200,600,{
				persistent: true,  //设置点击窗口外不关闭的参数
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
				persistent: true,  //设置点击窗口外不关闭的参数
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
				},closed:function (){self.location.href="${ctx}/personnel/personnelBase?type=${type}"}
			});
		}

		if ("success" == "${saveResult}"){
			parent.$.jBox.close();
		}
		//打开复合查询弹窗
		function openComplexSelForm(){
			var url = "iframe:${ctx}/personnel/personnelBase/complexSelForm";
			top.$.jBox.open(url, "复合条件查询",1200,600,{
				persistent: true,  //设置点击窗口外不关闭的参数
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
				persistent: true,  //设置点击窗口外不关闭的参数
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
	<c:when test="${type eq '2'}">
		<form:form id="searchForm" modelAttribute="personnelBase" action="${ctx}/personnel/personnelBase/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="type" name="type" type="hidden" value="${type}"/>
		<input id="fileName" name="fileName" type="hidden" value="基本信息集.xlsx"/>

		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>出生日期：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${personnelBase.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${personnelBase.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li><label>警号：</label>
				<form:input path="policeIdNumber" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelBase?type=2'"/></li>
			<li class="clearfix"></li>
		</ul>
		</form:form>
	</c:when>
	<c:when test="${type eq '1'}">
		<form:form id="searchForm" modelAttribute="personnelBase" action="${ctx}/personnel/personnelBase/" method="post" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<input id="type" name="type" type="hidden" value="${type}"/>
			<input id="fileName" name="fileName" type="hidden" value="基本信息集.xlsx"/>

			<ul class="ul-form">
				<li><label>姓名：</label>
					<form:input path="name" htmlEscape="false" class="input-medium"/>
				</li>
				<li><label>出生日期：</label>
					<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${personnelBase.startDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
					至
					<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${personnelBase.endDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
				</li>
				<li><label>警号：</label>
					<form:input path="policeIdNumber" htmlEscape="false" class="input-medium"/>
				</li>
			</ul>
			<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<c:if test="${personnel == null}">

				<shiro:hasPermission name="personnel:personnelBase:edit">
					<%--<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelBase/form?type=1','${ctx}/personnel/personnelBase?type=1')"/></li>
					<li class="btns"><input class="btn btn-primary" type="button" value="单位调动"
											onclick="editUnitByIds('${ctx}/personnel/personnelBase/editUnitDialog','checkAll','myCheckBox')"/>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelBase/deleteByIds','checkAll','myCheckBox')"/></li>
					--%><li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				</shiro:hasPermission>
				</c:if>
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelBase/form?type=1','${ctx}/personnel/personnelBase?type=1')"/></li>
				<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
				<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelBase?type=1'"/></li>
				<li class="clearfix"></li>
			</ul>
		</form:form>
	</c:when>
	<c:otherwise>
		<form:form id="searchForm" modelAttribute="personnelBase" action="${ctx}/personnel/personnelBase/" method="post" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<input id="type" name="type" type="hidden" value="${type}"/>
			<input id="ps" name="ps" type="hidden" value="${ps}"/>
			<input id="fileName" name="fileName" type="hidden" value="基本信息集.xlsx"/>
			<input id="fileName" name="fileName" type="hidden" value="干部任免审批表.xlsx"/>
			<input id="fileName" name="fileName" type="hidden" value="民警卡片.xlsx"/>
			<ul class="ul-form">
				<li><label>姓名：</label>
					<form:input path="name" htmlEscape="false" class="input-medium"/>
				</li>
				<%--<li><label>出生日期：</label>
					<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${personnelBase.startDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					至
					<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${personnelBase.endDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</li>--%>
				<li><label>警号：</label>
					<form:input path="policeIdNumber" htmlEscape="false" class="input-medium"/>
				</li>
				<%--<li><label>单位：</label>
					<sys:treeselect id="workunitName" name="workunitId" value="${personnelBase.workunitId}" labelName="workunitName" labelValue="${personnelBase.workunitName}"
									title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
				</li>--%>
				<li><label>单位：</label>
					<sys:treeselect id="actualUnit" name="actualUnitId" value="${personnelBase.actualUnitId}" labelName="actualUnit" labelValue="${personnelBase.actualUnit}"
									title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
				</li>
				<li>
					<label>人员状态</label>
					<form:select path="peopleStatus" class="input-medium">
						<form:option value="1" label="在职"/>
						<form:option value="2" label="非在职"/>
					</form:select>
				</li>
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
<%--				<li class="btns"><input id="renyuan" class="btn btn-primary" type="button" value="人员名册" onclick="openRenYuan()"></li>--%>
				<li class="btns"><input id="renyuan" class="btn btn-primary" type="button" value="人员名册"></li>
				<li class="btns"><input id="appointmentRemoval" class="btn btn-primary" type="button" value="任免"></li>
				<li class="btns"><input id="policeCard" class="btn btn-primary" type="button" value="民警卡片"></li>
				<%--人员信息管理也显示添加，修改时，不显示 修改时身份证不为空--%>
				<c:if test="${personnel == null}">
				<shiro:hasPermission name="personnel:personnelBase:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelBase/form','${ctx}/personnel/personnelBase')"/></li>
					<li class="btns"><input class="btn btn-primary" type="button" value="单位调动"
											onclick="editUnitByIds('${ctx}/personnel/personnelBase/editUnitDialog','checkAll','myCheckBox')"/>
					</li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelBase/deleteByIds','checkAll','myCheckBox')"/></li>
						<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				</shiro:hasPermission>
				</c:if>
				<c:choose>
					<c:when test="${isComplexSelForm != null && isComplexSelForm!='' && isComplexSelForm == 'true'}">
						<li class="btns"><input id="complexSelexport" class="btn btn-primary" type="button" value="复合查询导出"/></li>
					</c:when>
					<c:when test="${isBatchSelTable != null && isBatchSelTable!='' && isBatchSelTable == 'true'}">
						<li class="btns"><input id="batchSelexport" class="btn btn-primary" type="button" value="批量查询导出"/></li>
					</c:when>
					<c:otherwise>
						<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					</c:otherwise>
				</c:choose>
				<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
				<li class="btns"><input  class="btn btn-primary"  type="button" value="复合条件查询" onclick="openComplexSelForm()"/></li>
				<li class="btns"><input  class="btn btn-primary"  type="button" value="批量查询" onclick="openBatchSelForm()"/></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelBase/list'"/></li>
				<li class="clearfix"></li>
			</ul>
		</form:form>
	</c:otherwise>
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
				<th>人事命令单位</th>
				<th>实际工作单位</th>
				<th>姓名</th>
				<th>身份证号</th>
				<th>警号</th>
				<th>职务简称</th>
				<th>人员状态</th>
				<%--<th>籍贯</th>--%>
				<th>备注</th>
				<c:if test="${isBatchSelTable ne 'true'}">
				<c:if test="${type eq '1' || type == null || type eq '2'}">
					<%--<shiro:hasPermission name="personnel:personnelBase:edit">--%>
					<th id="handleTh">操作</th>
					<%--</shiro:hasPermission>--%>
				</c:if>
				</c:if>
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
					${personnelBase.actualUnit}
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
				<c:if test="${isBatchSelTable ne 'true'}">
				<c:choose>
					<c:when test="${type eq '2'}">
						<td class="handleTd">
							<a href="javascript:void(0)" onclick="openDetailDialog('${personnelBase.id}')">查看</a>
							<a href="javaScript:void(0);"
							   onclick="openExamRecord('人员基本信息','${personnelBase.idNumber}','${personnelBase.name}')">考评档案</a>
						</td>
					</c:when>
					<c:when test="${type eq '1' }">
						<td class="handleTd">
							<a href="javascript:void(0)" onclick="openDetailDialog('${personnelBase.id}')">查看</a>
							<a href="javascript:void(0)" onclick="openForm('${ctx}/personnel/personnelBase/form?id=${personnelBase.id}','${ctx}/personnel/personnelBase?type=1')">修改</a>
							<%--<shiro:hasPermission name="personnel:personnelBase:edit">
								<c:if test="${personnelBase.createBy.id == fns:getUser().id}">
									<a href="javascript:void(0)" onclick="openForm('${ctx}/personnel/personnelBase/form?id=${personnelBase.id}','${ctx}/personnel/personnelBase?type=1')">修改</a>
									<a href="${ctx}/personnel/personnelBase/delete?id=${personnelBase.id}&type=1" onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a>
								</c:if>
							</shiro:hasPermission>--%>
						</td>
					</c:when>
					<c:otherwise>
						<td class="handleTd">
							<a href="javascript:void(0)" onclick="openFormDialog('${personnelBase.id}')">查看</a>
							<a href="javascript:void(0)" onclick="openDialog('${personnelBase.id}','${ps}')">修改</a>
							<a href="javascript:void(0)" onclick="openGenerationDialog('${personnelBase.id}')">导出</a>
							<a href="javaScript:void(0);"
							   onclick="openExamRecord('人员基本信息','${personnelBase.idNumber}','${personnelBase.name}')">考评档案</a>
							<a href="${ctx}/personnel/personnelBase/exportAppointRemoveCadre?id=${personnelBase.id}&fileName=干部任免审批表.xlsx">任免</a>
							<a href="${ctx}/personnel/personnelBase/policeCard?id=${personnelBase.id}&fileName=民警卡片.xlsx">民警卡片</a>
							<%--<shiro:hasPermission name="personnel:personnelBase:edit">
								<c:if test="${personnelBase.createBy.id == fns:getUser().id}">
									<a href="javascript:void(0)"
									   onclick="openForm('${ctx}/personnel/personnelBase/form?id=${personnelBase.id}','${ctx}/personnel/personnelBase?idNumber=${personnelBase.idNumber}')">修改</a>
									<a href="${ctx}/personnel/personnelBase/delete?id=${personnelBase.id}&idNumber=${personnelBase.idNumber}"
									   onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
								</c:if>
							</shiro:hasPermission>--%>
						</td>
					</c:otherwise>
				</c:choose>
				</c:if>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</c:otherwise>
</c:choose>
</body>
</html>
