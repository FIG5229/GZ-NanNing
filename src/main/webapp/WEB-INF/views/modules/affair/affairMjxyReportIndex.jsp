<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民警休养申报管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairMjxyReport/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairMjxyReport/index");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairMjxyReport/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairMjxyReport/index");
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
					afterPrint:function(){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});
			$("[data-toggle='popover']").popover();
		});
		function excelImp(url){
			top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairMjxyReport", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href=url}});
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
		function openForm(url,rUrl,type,place,startDate,endDate) {
			top.$.jBox.open("iframe:"+url, "民警休养申报",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=rUrl+"&type="+type+"&place="+place+"&startDate="+startDate+"&endDate="+endDate}
			});
		}
		function openDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairMjxyReport/shenHeDialog?id="+id,"休养申报审核",800,420,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
				closed:function (){
					self.location.href="${ctx}/affair/affairMjxyReport/index?mType=3";
				}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairMjxyReport/formDetail?id="+id;
			top.$.jBox.open(url, "民警休养详情",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		function submitByIds() {
			if(null == $("#oneCheckId").val() || "" ==  $("#oneCheckId").val()){
				$.jBox.tip('请选择审核单位');
				return false;
			}
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				console.log($(this).val());
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
		$('#notPass').popover();
	</script>
</head>
<body>
<c:choose>
	<c:when test="${mType eq '3'}">
		<ul class="nav nav-tabs">
			<li class="active"><a href="${ctx}/affair/affairMjxyReport?mType=3">休养申报</a></li>
			<shiro:hasPermission name="affair:affairMjxyReportSum:view"><li ><a href="${ctx}/affair/affairMjxyReportSum/">汇总审核</a></li></shiro:hasPermission>
			<shiro:hasPermission name="affair:affairMjxyReportSum:view"><li><a href="${ctx}/affair/affairMjxyReport/affairMjxyReportListSum?type=1&sort=1">统计汇总</a></li></shiro:hasPermission>
	</ul>
		<form:form id="searchForm" modelAttribute="affairMjxyReport" action="${ctx}/affair/affairMjxyReport/" method="post" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<input id="mType" name="mType" type="hidden" value="${mType}"/>
			<input id="idNumber" name="idNumber" type="hidden" value="${affairMjxyReport.idNumber}"/>
			<input id="fileName" name="fileName" type="hidden" value="休养信息.xlsx"/>
			<ul class="ul-form">
				<li><label class="width120">休养开始时间：</label>
					<input name="beginStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${affairMjxyReport.beginStartDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					至
					<input name="finishStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${affairMjxyReport.finishStartDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</li>
				<li><label class="width120">休养结束时间：</label>
					<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${affairMjxyReport.beginDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					至
					<input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${affairMjxyReport.finishDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</li>
				<li><label>休养类型：</label>
					<form:select path="type" class="input-medium">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('affair_xiuyang')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</li>
				<li><label>单位：</label>
					<sys:treeselect id="unit" name="unitId" value="${affairMjxyReport.unitId}" labelName="unit" labelValue="${affairMjxyReport.unit}"
									title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
				</li>
				<li><label>姓名：</label>
					<form:input path="name" htmlEscape="false" class="input-medium"/>
				</li>
				<li><label>性别：</label>
					<form:select path="sex" class="input-medium">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</li>
			</ul>
			<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="affair:affairMjxyReport:edit">
					<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairMjxyReport/form?mType=3','${ctx}/affair/affairMjxyReport?mType=3')"/></li>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairMjxyReport/deleteByIds','checkAll','myCheckBox')"/></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="excelImp('${ctx}/affair/affairMjxyReport?mType=3')"/></li>
				</shiro:hasPermission>
				<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
				<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairMjxyReport?mType=3'"/></li>
				<li class="clearfix"></li>
			</ul>
		</form:form>
	</c:when>
	<c:when test="${mType eq '2'}"></c:when>
	<c:when test="${mType eq '1'}">
		<form:form id="searchForm" modelAttribute="affairMjxyReport"
				   action="${ctx}/affair/affairMjxyReport/index?mType=1&startDate=${affairMjxyReport.startDate}&endDate${affairMjxyReport.endDate}" method="post" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<input id="mType" name="mType" type="hidden" value="${mType}"/>
			<input id="idNumber" name="idNumber" type="hidden" value="${affairMjxyReport.idNumber}"/>
			<input id="fileName" name="fileName" type="hidden" value="休养信息.xlsx"/>


			<input id="type" name="type" type="hidden" value="${affairMjxyReport.type}"/>
			<input id="place" name="place" type="hidden" value="${affairMjxyReport.place}"/>
			<input id="beginStartDate" name="beginStartDate" type="hidden" value="<fmt:formatDate value="${affairMjxyReport.beginStartDate}" pattern="yyyy-MM-dd"/>"/>

			<input id="finishStartDate" name="finishStartDate" type="hidden" value="<fmt:formatDate value="${affairMjxyReport.finishStartDate}" pattern="yyyy-MM-dd"/>"/>
			<input id="beginDate" name="beginDate" type="hidden" value="<fmt:formatDate value="${affairMjxyReport.beginDate}" pattern="yyyy-MM-dd"/>"/>
			<input id="finishDate" name="finishDate" type="hidden" value="<fmt:formatDate value="${affairMjxyReport.finishDate}" pattern="yyyy-MM-dd"/>"/>

			<%--<ul class="ul-form">
				<li><label class="width140">休养开始时间：</label>
					<input name="beginStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${affairMjxyReport.beginStartDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					至
					<input name="finishStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${affairMjxyReport.finishStartDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</li>
				<li><label>休养类型：</label>
					<form:select path="type" class="input-medium">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('affair_xiuyang')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</li>
				<li><label>单位：</label>
					<sys:treeselect id="unit" name="unitId" value="${affairMjxyReport.unitId}" labelName="unit" labelValue="${affairMjxyReport.unit}"
									title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
				</li>
				<li><label class="width140">休养结束时间：</label>
					<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${affairMjxyReport.beginDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					至
					<input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${affairMjxyReport.finishDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</li>

				<li><label>性别：</label>
					<form:select path="sex" class="input-medium">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</li>
			</ul>--%>
			<ul class="ul-form2">
<%--				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>--%>
				<shiro:hasPermission name="affair:affairMjxyReport:edit">
					<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairMjxyReport/form?mType=1','${ctx}/affair/affairMjxyReport?mType=1')"/></li>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairMjxyReport/deleteByIds','checkAll','myCheckBox')"/></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="excelImp('${ctx}/affair/affairMjxyReport/imdex?mType=1')"/></li>
				</shiro:hasPermission>
				<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
				<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置"
										 onclick="window.location.href='${ctx}/affair/affairMjxyReport/index?mType=1&type=${affairMjxyReport.type}&place=${affairMjxyReport.place}&startDate=${affairMjxyReport.startDate}&endDate${affairMjxyReport.endDate}'"/></li>
				<li class="clearfix"></li>
			</ul>
		</form:form>
	</c:when>
	<c:otherwise>
		<form:form id="searchForm" modelAttribute="affairMjxyReport" action="${ctx}/affair/affairMjxyReport/" method="post" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<input id="mType" name="mType" type="hidden" value="${mType}"/>
			<input id="idNumber" name="idNumber" type="hidden" value="${affairMjxyReport.idNumber}"/>
			<input id="fileName" name="fileName" type="hidden" value="休养信息.xlsx"/>
			<ul class="ul-form">
				<li><label class="width140">休养开始时间：</label>
					<input name="beginStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${affairMjxyReport.beginStartDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					至
					<input name="finishStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${affairMjxyReport.finishStartDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</li>
				<li><label>休养类型：</label>
					<form:select path="type" class="input-medium">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('affair_xiuyang')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</li>
				<li><label class="width140">休养结束时间：</label>
					<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${affairMjxyReport.beginDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					至
					<input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${affairMjxyReport.finishDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</li>
				<li><label>性别：</label>
					<form:select path="sex" class="input-medium">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</li>
				<li><label>单位：</label>
					<sys:treeselect id="unit" name="unitId" value="${affairMjxyReport.unitId}" labelName="unit" labelValue="${affairMjxyReport.unit}"
									title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
				</li>
				<li><label>姓名：</label>
					<form:input path="name" htmlEscape="false" class="input-medium"/>
				</li>
			</ul>
			<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="affair:affairMjxyReport:edit">
					<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairMjxyReport/form?idNumber=${affairMjxyReport.idNumber}','${ctx}/affair/affairMjxyReport?idNumber=${affairMjxyReport.idNumber}')"/></li>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairMjxyReport/deleteByIds','checkAll','myCheckBox')"/></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="excelImp('${ctx}/affair/affairMjxyReport?idNumber=${affairMjxyReport.idNumber}')"/></li>
				</shiro:hasPermission>
				<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
				<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairMjxyReport?idNumber=${affairMjxyReport.idNumber}'"/></li>
				<li class="clearfix"></li>
			</ul>
		</form:form>
	</c:otherwise>
</c:choose>

	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<c:if test="${mType==null || mType eq '1' || mType eq '3'}">
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				</c:if>
				<th>序号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>单位</th>
				<th>休养类型</th>
				<th>休养地点</th>
				<th>身份证号</th>
				<th>休养开始时间</th>
				<th>休养结束时间</th>
				<th>审核状态</th>
				<c:if test="${mType==null || mType eq '1' || mType eq '3'}">
				<shiro:hasPermission name="affair:affairMjxyReport:edit"><th id="handleTh">操作</th></shiro:hasPermission>
				</c:if>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairMjxyReport" varStatus="status">
			<tr>
				<c:if test="${mType==null || mType eq '1' || mType eq '3'}">
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairMjxyReport.id}"/>
				</td>
				</c:if>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
				${affairMjxyReport.name}
				</td>
				<td>
						${fns:getDictLabel(affairMjxyReport.sex, 'sex', '')}
				</td>
				<td>
						${affairMjxyReport.unit}
				</td>
				<td>
						${fns:getDictLabel(affairMjxyReport.type, 'affair_xiuyang', '')}
				</td>
				<td>
						${affairMjxyReport.place}
				</td>
				<td>
						${affairMjxyReport.idNumber}
				</td>
				<td>
					<fmt:formatDate value="${affairMjxyReport.startDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairMjxyReport.endDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<c:choose>
						<c:when test="${affairMjxyReport.checkType == '5'}">
							<a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
							   data-content="${affairMjxyReport.opinion}"  style="cursor: pointer;color: red">审核未通过</a>
						</c:when>
						<c:otherwise>
							${fns:getDictLabel(affairMjxyReport.checkType, 'check_type', '')}
						</c:otherwise>
					</c:choose>
				</td>
				<c:choose>
					<c:when test="${mType eq '3'}">
						<td class="handleTd">
							<a onclick="openDetailDialog('${affairMjxyReport.id}')">查看</a>
							<c:if test="${affairMjxyReport.createBy.id == fns:getUser().id &&('0'.equals(affairMjxyReport.checkType)||'1'.equals(affairMjxyReport.checkType))}">
								<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairMjxyReport/form?id=${affairMjxyReport.id}','${ctx}/affair/affairMjxyReport?mType=3')">修改</a>
								<a href="${ctx}/affair/affairMjxyReport/delete?id=${affairMjxyReport.id}&mType=3" onclick="return confirmx('确认要删除该内容吗？', this.href)">删除</a>
							</c:if>
							<shiro:hasPermission name="affair:affairMjxyReport:manage">
								<%--<a href="${ctx}/affair/affairMjxyReport/delete?id=${affairMjxyReport.id}&mType=3" onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
								--%><%--<a onclick="openDialog('${affairMjxyReport.id}')">审核</a>--%>
							</shiro:hasPermission>
						</td>
					</c:when>
					<c:when test="${mType eq '2'}">
					</c:when>
					<c:when test="${mType eq '1'}">
						<td class="handleTd">
							<a onclick="openDetailDialog('${affairMjxyReport.id}')">查看</a>
							<c:if test="${'1' eq affairMjxyReport.checkType ||'2' eq affairMjxyReport.checkType ||'3' eq affairMjxyReport.checkType }">
								<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairMjxyReport/form?id=${affairMjxyReport.id}','${ctx}/affair/affairMjxyReport/index?mType=1',
										'${affairMjxyReport.type}','${affairMjxyReport.place}','<fmt:formatDate value="${affairMjxyReport.startDate}" pattern="yyyy-MM-dd"/>','<fmt:formatDate value="${affairMjxyReport.endDate}" pattern="yyyy-MM-dd"/>')">修改</a>
							<a href="${ctx}/affair/affairMjxyReport/delete?id=${affairMjxyReport.id}&mType=1" onclick="return confirmx('确认要删除该内容吗？', this.href)">删除</a>
							</c:if>
						</td>
					</c:when>
					<c:otherwise>
						<td class="handleTd">
							<a onclick="openDetailDialog('${affairMjxyReport.id}')">查看</a>
							<c:if test="${'1' eq affairMjxyReport.checkType ||'2' eq affairMjxyReport.checkType ||'3' eq affairMjxyReport.checkType }">
								<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairMjxyReport/form?id=${affairMjxyReport.id}','${ctx}/affair/affairMjxyReport?idNumber=${affairMjxyReport.idNumber}')">修改</a>
							<a href="${ctx}/affair/affairMjxyReport/delete?id=${affairMjxyReport.id}&idNumber=${affairMjxyReport.idNumber}" onclick="return confirmx('确认要删除该内容吗？', this.href)">删除</a>
							</c:if>
						</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
		</tbody>
	</table>
<%--	<div class="pagination">${page}</div>--%>
<form:form id="searchForm2" modelAttribute="affairMjxyReport" action="${ctx}/affair/affairMjxyReport/submitByIds" method="post" class="breadcrumb form-search">
	<ul class="ul-form" style="text-align: right">
		<font color="red">请选择审核单位：</font>
		<input type="hidden" name="ids" id="idsValue"/>
		<form:select id="oneCheckId" path="oneCheckId" class="input-xlarge required">
			<form:option value="" label=""/>
			<form:options items="${fns:getDictList('user_gonghui')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		</form:select>
		<input  class="btn btn-primary" type="button" value="提交送审" onclick="submitByIds()"/>
	</ul>
</form:form>
<script>
	if ("success" == "${saveResult}"){
		parent.$.jBox.close();
	}
</script>
</body>
</html>