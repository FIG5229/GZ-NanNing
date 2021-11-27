<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>女警基本情况管理</title>
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
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairPolicewomanBase/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPolicewomanBase/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairPolicewomanBase/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPolicewomanBase/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairPolicewomanBase", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairPolicewomanBase"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//添加function,用于打开form添加页面弹窗,需要传一个url
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "女警基本情况",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairPolicewomanBase"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairPolicewomanBase/formDetail?id="+id;
			top.$.jBox.open(url, "女警基本情况",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};

		function openlDialog(idNumber) {
			var url = url = "iframe:${ctx}/personnel/personnelBase/nav?idNumber="+idNumber;
			top.$.jBox.open(url, "",1000,610,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		$('#notPass').popover();
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairPolicewomanBase/">女警工作管理</a></li>
		<%--	<li ><a href="${ctx}/affair/affairHardPolicewoman/">困难女民警申报</a></li>--%>
		<li ><a href="${ctx}/affair/affairPolicewomanTalent/">女警风采信息</a></li>
		<li ><a href="${ctx}/affair/affairChildSubsidy/">幼儿补助信息</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairPolicewomanBase" action="${ctx}/affair/affairPolicewomanBase/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="女警基本情况.xlsx"/>
		<ul class="ul-form">
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairPolicewomanBase.unitId}" labelName="unit" labelValue="${affairPolicewomanBase.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>学历：</label>
				<form:select path="xl" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_xueli')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>是否是党员：</label>
				<form:select path="isPartyMember" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>出生年月：</label>
				<input name="startBirthdayDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairPolicewomanBase.startBirthdayDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endBirthdayDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairPolicewomanBase.endBirthdayDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>技术职称：</label>
				<form:select path="jszc" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_jishu')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>录入时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairPolicewomanBase.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairPolicewomanBase.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairPolicewomanBase:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairPolicewomanBase/form?id=${affairPolicewomanBase.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairPolicewomanBase/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairPolicewomanBase'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>姓名</th>
				<th>单位</th>
				<th>学历</th>
				<th>出生年月</th>
				<th>是否党员</th>
				<th>技术职称</th>
				<shiro:hasPermission name="affair:affairPolicewomanBase:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairPolicewomanBase" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairPolicewomanBase.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td><a onclick="openDetailDialog('${affairPolicewomanBase.id}')">
						${affairPolicewomanBase.name}
				</a></td>
				<td>
						${affairPolicewomanBase.unit}
				</td>
				<td>
						${fns:getDictLabel(affairPolicewomanBase.xl, 'affair_xueli', '')}
				</td>
				<td>
					<fmt:formatDate value="${affairPolicewomanBase.birthday}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(affairPolicewomanBase.isPartyMember, 'yes_no', '')}
				</td>
				<td>
					${fns:getDictLabel(affairPolicewomanBase.jszc, 'affair_jishu', '')}
				</td>
				<shiro:hasPermission name="affair:affairPolicewomanBase:edit"><td class="handleTd">
					<%--<c:if test="${affairPolicewomanBase.createBy.id == fns:getUser().id}">--%>
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairPolicewomanBase/form?id=${affairPolicewomanBase.id}')">修改</a>
						<a href="${ctx}/affair/affairPolicewomanBase/delete?id=${affairPolicewomanBase.id}" onclick="return confirmx('确认要删除该女警基本情况吗？', this.href)">删除</a>
					<%--</c:if>--%>
					<a href="javascript:void(0)" onclick="openlDialog('${affairPolicewomanBase.idNumber}')">详细信息</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>