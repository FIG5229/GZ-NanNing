
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>干部档案材料收集管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
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
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "干部档案材料收集登记簿",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairMaterial"}
			});
		}

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairMaterial/formDetail?id="+id;
			top.$.jBox.open(url, "干部档案材料收集登记簿",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		//批量导出弹窗
		function openAccountExport(url) {
			top.$.jBox.open("iframe:"+url, "批量导出",800,520,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairMaterial"}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li  ><a href="${ctx}/affair/affairLedgerInto/">转入</a></li>
		<li ><a href="${ctx}/affair/affairLedgerOut/">转出</a></li>
		<li ><a href="${ctx}/affair/affairConsult/">查阅</a></li>
		<li ><a href="${ctx}/affair/affairBorrow/">借阅</a></li>
		<li ><a href="${ctx}/affair/affairArchiveApproval/">查(借)阅审批列表</a></li>
		<li ><a href="${ctx}/affair/affairArchiveRegister/">在职</a></li>
		<li ><a href="${ctx}/affair/affairRetire/">离退</a></li>
		<li ><a href="${ctx}/affair/affairDeath/">死亡</a></li>
		<li class="active"><a href="${ctx}/affair/affairMaterial/">收集</a></li>
		<li ><a href="${ctx}/affair/affairTemHum/">温湿度</a></li>
		<li ><a href="${ctx}/affair/affairCheck/">核对</a></li>
		<li><a href="${ctx}/affair/affairSecurityCheck/">安全检查</a></li>
		<li ><a href="${ctx}/affair/affairDestroyMeterial/">销毁清册</a></li>
		<li ><a href="${ctx}/affair/affairCopy/">复印档案登记</a></li>

		<%--<shiro:hasPermission name="affair:affairMaterial:edit"><li><a href="${ctx}/affair/affairMaterial/form">干部档案材料收集添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairMaterial" action="${ctx}/affair/affairMaterial/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>身份证号：</label>
				<form:input path="idNumber" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>材料名称：</label>
				<form:input path="material" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label class="width120">形成材料单位：</label>
				<sys:treeselect id="materialUnit" name="materialUnitId" value="${affairMaterial.materialUnitId}" labelName="materialUnit" labelValue="${affairMaterial.materialUnit}"
					title="形成材料单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>移交时间：</label>
				<input name="beginTransferDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairMaterial.beginTransferDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endTransferDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairMaterial.endTransferDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>送交人：</label>
				<form:input path="deliver" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>接收人：</label>
				<form:input path="receiver" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairMaterial:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairMaterial/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairMaterial/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="导入" /></li>
			</shiro:hasPermission>
			<li class="btns"><input  class="btn btn-primary" type="button" value="导出" onclick="openAccountExport('${ctx}/affair/affairLedgerInto/accountExport')"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairMaterial'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>姓名</th>
				<th>身份证号</th>
				<th>材料名称</th>
				<th>材料份数</th>
				<th>形成材料单位</th>
				<th>形成材料时间</th>
				<th>移交时间</th>
				<th>送交人</th>
				<th>接收人</th>
				<shiro:hasPermission name="affair:affairMaterial:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairMaterial" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairMaterial.id}"/></td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td><a onclick="openDetailDialog('${affairMaterial.id}')">
					${affairMaterial.name}
				</a></td>
				<td>${affairMaterial.idNumber}</td>
				<td>
					${affairMaterial.material}
				</td>
				<td>
					${affairMaterial.num}
				</td>
				<td>
					${affairMaterial.materialUnit}
				</td>
				<td>
					<fmt:formatDate value="${affairMaterial.formDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairMaterial.transferDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairMaterial.deliver}
				</td>
				<td>
					${affairMaterial.receiver}
				</td>
				<shiro:hasPermission name="affair:affairMaterial:edit"><td class="handleTd">
					<c:if test="${affairMaterial.createBy.id == fns:getUser().id}">
						<a href="javascript:void(0)"
						   onclick="openForm('${ctx}/affair/affairMaterial/form?id=${affairMaterial.id}')">修改</a>
						<a href="${ctx}/affair/affairMaterial/delete?id=${affairMaterial.id}"
						   onclick="return confirmx('确认要删除该干部档案材料收集吗？', this.href)">删除</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
<font color="red">
	注：1.材料随时收集，随时登记，一月内入盒，两年装订一次。<br>

	3.对收集的材料首先鉴别是否符合存档要求，其学历、职称、党团、奖惩材料在归档之前要先交其干部任免权限部门核实后交档案部门；学历材料由主管干部培训的部门<br>

	或人员送交，本人送交的不收。 个人填写的履历、自传等材料要与组织认定的时间核对，不符的要求本人重填或在说明栏内说明。<br>

	4.同时收集的多人有同一名称的材料，可在“姓名”栏填写XXX等，其它有关栏填写后，另起一行通栏将人名列出。全局性工资令在“姓名”栏填写XXX等共XX人，<br>

	并填写其它有关栏，可不填写全体人员姓名。
</font>
</body>
</html>