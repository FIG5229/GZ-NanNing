<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>助医管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairZyInfo/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairZyInfo/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairZyInfo/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairZyInfo/");
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
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairZyInfo", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairZyInfo?tabFlag=${tabFlag}"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		let height =  $(window).height()-50;
		let width =  $(window).width()-100;
		//添加function,用于打开form添加页面弹窗,需要传一个url
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "助医管理",width,height,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairZyInfo?tabFlag=${tabFlag}"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairZyInfo/formDetail?id="+id;

			top.$.jBox.open(url, "助医详情",width,height,{
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
		<li class="active"><a href="${ctx}/affair/affairZyInfo/list?tabFlag=${tabFlag}">助医管理</a></li>
		<c:choose>
			 <c:when test="${'1'.equals(fns:getUser().getCompany().getId())}">
				<li><a href="http://10.3.240.112:8086/User/User_Login.asp" target="_blank">公安局互济会</a></li>
				<li><a href="http://10.3.240.112:81/User/User_Login.asp" target="_blank">南宁公安处互济会</a></li>
				<li><a href="http://10.3.240.112:82/User/User_Login.asp" target="_blank">柳州公安处互济会</a></li>
				<li><a href="http://10.3.240.112:83/User/User_Login.asp" target="_blank">北海公安处互济会</a></li>
			</c:when>
			<c:when test="${'34'.equals(fns:getUser().getCompany().getId())}">
				<li><a href="http://10.3.240.112:8086/User/User_Login.asp" target="_blank">公安局互济会</a></li>
				<li><a href="http://10.3.240.112:81/User/User_Login.asp" target="_blank">南宁公安处互济会</a></li>
			</c:when>
			<c:when test="${'95'.equals(fns:getUser().getCompany().getId())}">
				<li><a href="http://10.3.240.112:8086/User/User_Login.asp" target="_blank">公安局互济会</a></li>
				<li><a href="http://10.3.240.112:82/User/User_Login.asp" target="_blank">柳州公安处互济会</a></li>
			</c:when>
			<c:when test="${'156'.equals(fns:getUser().getCompany().getId())}">
				<li><a href="http://10.3.240.112:8086/User/User_Login.asp" target="_blank">公安局互济会</a></li>
				<li><a href="http://10.3.240.112:83/User/User_Login.asp" target="_blank">北海公安处互济会</a></li>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
	</ul>
		<c:choose>
			<c:when test="${'4c40b4dd2aee459286a37538978e6261' eq fns:getUser().id}">
			<ul class="nav nav-tabs">
				<c:choose>
					<c:when test="${1 == tabFlag}">
						<li><a href="${ctx}/affair/affairZyInfo/list?tabFlag=34">南宁公安处</a></li>
						<li><a href="${ctx}/affair/affairZyInfo/list?tabFlag=95">柳州公安处</a></li>
						<li><a href="${ctx}/affair/affairZyInfo/list?tabFlag=156">北海公安处</a></li>
						<li class="active"><a href="${ctx}/affair/affairZyInfo/list?tabFlag=1">公安局机关</a></li>
					</c:when>
					<c:when test="${34 == tabFlag}">
						<li class="active"><a href="${ctx}/affair/affairZyInfo/list?tabFlag=34">南宁公安处</a></li>
						<li><a href="${ctx}/affair/affairZyInfo/list?tabFlag=95">柳州公安处</a></li>
						<li><a href="${ctx}/affair/affairZyInfo/list?tabFlag=156">北海公安处</a></li>
						<li><a href="${ctx}/affair/affairZyInfo/list?tabFlag=1">公安局机关</a></li>
					</c:when>
					<c:when test="${95 == tabFlag}">
						<li><a href="${ctx}/affair/affairZyInfo/list?tabFlag=34">南宁公安处</a></li>
						<li class="active"><a href="${ctx}/affair/affairZyInfo/list?tabFlag=95">柳州公安处</a></li>
						<li><a href="${ctx}/affair/affairZyInfo/list?tabFlag=156">北海公安处</a></li>
						<li><a href="${ctx}/affair/affairZyInfo/list?tabFlag=1">公安局机关</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${ctx}/affair/affairZyInfo/list?tabFlag=34">南宁公安处</a></li>
						<li><a href="${ctx}/affair/affairZyInfo/list?tabFlag=95">柳州公安处</a></li>
						<li class="active"><a href="${ctx}/affair/affairZyInfo/list?tabFlag=156">北海公安处</a></li>
						<li><a href="${ctx}/affair/affairZyInfo/list?tabFlag=1">公安局机关</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
			</c:when>
			<c:otherwise></c:otherwise>
		</c:choose>
	<form:form id="searchForm" modelAttribute="affairZyInfo" action="${ctx}/affair/affairZyInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="助医管理表.xlsx"/>
		<input id="tabFlag" name="tabFlag" type="hidden" value="${affairZyInfo.tabFlag}"/>
		<ul class="ul-form">
			<li><label>时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairZyInfo.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairZyInfo.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>补助类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_zybuzhu')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>补助机构：</label>
				<form:select path="bzJigou" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bz_jigou')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairZyInfo:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairZyInfo/form?id=${affairZyInfo.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairZyInfo/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairZyInfo:edit">
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairZyInfo/list?tabFlag=${affairZyInfo.tabFlag}'"/></li>
		</ul>
			<li class="clearfix"></li>

	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>时间</th>
				<th>单位</th>
				<th>姓名</th>
				<th>性别</th>
				<th>补助机构</th>
				<th>补助类型</th>
				<th>补助金额</th>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairZyInfo" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairZyInfo.id}"/></td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<fmt:formatDate value="${affairZyInfo.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairZyInfo.unit}
				</td>
				<td>
						${affairZyInfo.name}
				</td>
				<td>
						${fns:getDictLabel(affairZyInfo.sex, 'sex', '')}
				</td>
				<td>
						${fns:getDictLabel(affairZyInfo.bzJigou, 'bz_jigou', '')}
				</td>
				<td>
					${fns:getDictLabel(affairZyInfo.type, 'affair_zybuzhu', '')}
				</td>
				<td>
						${affairZyInfo.money}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairZyInfo.id}')">查看</a>
					<shiro:hasPermission name="affair:affairZyInfo:edit">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairZyInfo/form?id=${affairZyInfo.id}')">修改</a>
						<a href="${ctx}/affair/affairZyInfo/delete?id=${affairZyInfo.id}&tabFlag=${tabFlag}" onclick="return confirmx('确认要删除该助医管理吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">金额小计：</span><span class="modal-custom-info2-value">${sumMoney}</span></div>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>