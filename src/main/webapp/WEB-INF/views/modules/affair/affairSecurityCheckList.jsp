<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>安全检查管理</title>
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
					afterPrint:function (){
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
			top.$.jBox.open("iframe:"+url, "检查核对干部档案登记簿",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairSecurityCheck"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairSecurityCheck/formDetail?id="+id;
			top.$.jBox.open(url, "检查核对干部档案登记簿",800,500,{
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
				},closed:function (){self.location.href="${ctx}/affair/affairSecurityCheck"}
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
		<li ><a href="${ctx}/affair/affairMaterial/">收集</a></li>
		<li ><a href="${ctx}/affair/affairTemHum/">温湿度</a></li>
		<li ><a href="${ctx}/affair/affairCheck/">核对</a></li>
		<li class="active"><a href="${ctx}/affair/affairSecurityCheck/">安全检查</a></li>
		<li ><a href="${ctx}/affair/affairDestroyMeterial/">销毁清册</a></li>
		<li ><a href="${ctx}/affair/affairCopy/">复印档案登记</a></li>
	<%--	<shiro:hasPermission name="affair:affairSecurityCheck:edit"><li><a href="${ctx}/affair/affairSecurityCheck/form">安全检查添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairSecurityCheck" action="${ctx}/affair/affairSecurityCheck/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label class="width140">时间  年月日：</label>
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairSecurityCheck.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairSecurityCheck.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>检查内容：</label>
				<form:input path="content" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>检查情况：</label>
				<form:input path="situation" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>纠正措施：</label>
				<form:input path="measure" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairSecurityCheck:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairSecurityCheck/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairSecurityCheck/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="导入" /></li>
			</shiro:hasPermission>
			<li class="btns"><input  class="btn btn-primary" type="button" value="导出" onclick="openAccountExport('${ctx}/affair/affairLedgerInto/accountExport')"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairSecurityCheck'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this)'/>全选</th>
				<th>序号</th>
				<th>时间</th>
				<th>检查内容</th>
				<th>检查情况</th>
				<th>纠正措施</th>
				<th>备注</th>
				<shiro:hasPermission name="affair:affairSecurityCheck:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairSecurityCheck" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairSecurityCheck.id}"/></td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td><a onclick="openDetailDialog('${affairSecurityCheck.id}')">
					<fmt:formatDate value="${affairSecurityCheck.date}" pattern="yyyy-MM-dd"/>
				</a></td>
				<td>
					${affairSecurityCheck.content}
				</td>
				<td>
					${affairSecurityCheck.situation}
				</td>
				<td>
					${affairSecurityCheck.measure}
				</td>
				<td>
					${affairSecurityCheck.remark}
				</td>
				<shiro:hasPermission name="affair:affairSecurityCheck:edit"><td class="handleTd">
					<c:if test="${affairSecurityCheck.createBy.id == fns:getUser().id}">
						<a href="javascript:void(0)"
						   onclick="openForm('${ctx}/affair/affairSecurityCheck/form?id=${affairSecurityCheck.id}')">修改</a>
						<a href="${ctx}/affair/affairSecurityCheck/delete?id=${affairSecurityCheck.id}"
						   onclick="return confirmx('确认要删除该安全检查吗？', this.href)">删除</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
<font color="red">注：1.日常注意安全、保密；节假日对档案库房门窗上锁情况认真检查。<br>

	2.半年对干部档案库房的安全、保密、防火情况进行检查，并登记。</font>
</body>
</html>