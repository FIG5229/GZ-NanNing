<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>传递单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			/*//导出功能的JS
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairTransmitForm/export");
								$("#searchForm").submit();
							}
							if (v == 'part') {
								dataExport('contentTable');
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);*/
			//这是打印功能的JS
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
			top.$.jBox.open("iframe:"+url, "干部人事档案材料传递单",1200,800,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairTransmitForm"}
			});
		}
		//批量导出弹窗
		function openAccountExport(url) {
			top.$.jBox.open("iframe:"+url, "批量导出",800,520,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairLedgerInto"}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairSubmitForm/">档案报送单</a></li>
		<li class="active"><a href="${ctx}/affair/affairTransmitForm/">档案传递单</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairTransmitForm" action="${ctx}/affair/affairTransmitForm/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>经办人：</label>
				<form:input path="handler" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>经办日期：</label>
				<input name="handleDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairTransmitForm.handleDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>字：</label>
				<form:input path="handleZi" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>第号：</label>
				<form:input path="handleDh" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>接收人：</label>
				<form:input path="receiver" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>收件日期：</label>
				<input name="beginReceiveDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairTransmitForm.beginReceiveDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endReceiveDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairTransmitForm.endReceiveDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>收件机关：</label>
				<form:input path="receiveOrg" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
            <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
            <shiro:hasPermission name="affair:affairTransmitForm:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairTransmitForm/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairTransmitForm/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="导入" /></li>
			</shiro:hasPermission>
			<li class="btns"><input  class="btn btn-primary"  type="button" value="导出" /></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairTransmitForm'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr> <th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>经办人</th>
				<th>经办日期</th>
				<th>字</th>
				<th>第号</th>
				<th>接收人</th>
				<th>收件日期</th>
				<th>收件机关</th>
				<shiro:hasPermission name="affair:affairTransmitForm:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTransmitForm" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTransmitForm.id}"/></td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td>
					${affairTransmitForm.handler}
				</a></td>
				<td>
					<fmt:formatDate value="${affairTransmitForm.handleDate}" pattern="yyyy-MM-dd "/>
				</td>
				<td>
					${affairTransmitForm.handleZi}
				</td>
				<td>
					${affairTransmitForm.handleDh}
				</td>
				<td>
					${affairTransmitForm.receiver}
				</td>
				<td>
					<fmt:formatDate value="${affairTransmitForm.receiveDate}" pattern="yyyy-MM-dd "/>
				</td>
				<td>
					${affairTransmitForm.receiveOrg}
				</td>
				<shiro:hasPermission name="affair:affairTransmitForm:edit"><td class="handleTd">
					<c:if test="${affairTransmitForm.createBy.id == fns:getUser().id}">
						<a href="javascript:void(0)"
						   onclick="openForm('${ctx}/affair/affairTransmitForm/form?id=${affairTransmitForm.id}')">修改</a>
						<a href="${ctx}/affair/affairTransmitForm/delete?id=${affairTransmitForm.id}"
						   onclick="return confirmx('确认要删除该传递单吗？', this.href)">删除</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>