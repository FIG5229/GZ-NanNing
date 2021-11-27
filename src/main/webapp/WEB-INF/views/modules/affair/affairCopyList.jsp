<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>复印档案登记管理</title>
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
			top.$.jBox.open("iframe:"+url, "复印干部档案材料登记簿",800,650,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairCopy"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairCopy/formDetail?id="+id;
			top.$.jBox.open(url, "复印干部档案材料登记簿",900,500,{
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
				},closed:function (){self.location.href="${ctx}/affair/affairCopy"}
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
		<li><a href="${ctx}/affair/affairSecurityCheck/">安全检查</a></li>
		<li ><a href="${ctx}/affair/affairDestroyMeterial/">销毁清册</a></li>
		<li class="active"><a href="${ctx}/affair/affairCopy/">复印档案登记</a></li>
		<%--<shiro:hasPermission name="affair:affairCopy:edit"><li><a href="${ctx}/affair/affairCopy/form">复印档案登记添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairCopy" action="${ctx}/affair/affairCopy/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>复印时间：</label>
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairCopy.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairCopy.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label class="width120">被复印人姓名：</label>
				<form:input path="bCopyName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label class="width120">办理人员姓名：</label>
				<form:input path="handleName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label class="width120">办理人员单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairCopy.unitId}" labelName="unit" labelValue="${affairCopy.unit}"
					title="办理人员单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>复印原因：</label>
				<form:input path="reason" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairCopy:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairCopy/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairCopy/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="导入" /></li>
			</shiro:hasPermission>
			<li class="btns"><input  class="btn btn-primary" type="button" value="导出" onclick="openAccountExport('${ctx}/affair/affairLedgerInto/accountExport')"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairCopy'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>复印时间</th>
				<th>被复印人姓名</th>
				<th>办理人员姓名</th>
				<th>办理人员单位</th>
				<th>办理人员政治面貌</th>
				<th>复印原因</th>
				<th>备注</th>
				<shiro:hasPermission name="affair:affairCopy:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairCopy" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairCopy.id}"/></td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td><a onclick="openDetailDialog('${affairCopy.id}')">
					<fmt:formatDate value="${affairCopy.date}" pattern="yyyy-MM-dd"/>
				</a></td>
				<td>
					${affairCopy.bCopyName}
				</td>
				<td>
					${affairCopy.handleName}
				</td>
				<td>
					${affairCopy.unit}
				</td>
				<td>
					${fns:getDictLabel(affairCopy.face, 'zzmm', '')}
				</td>
				<td>
					${affairCopy.reason}
				</td>
				<td>
					${affairCopy.remark}
				</td>
				<shiro:hasPermission name="affair:affairCopy:edit"><td class="handleTd">
					<c:if test="${affairCopy.createBy.id == fns:getUser().id}">
						<a href="javascript:void(0)"
						   onclick="openForm('${ctx}/affair/affairCopy/form?id=${affairCopy.id}')">修改</a>
						<a href="${ctx}/affair/affairCopy/delete?id=${affairCopy.id}"
						   onclick="return confirmx('确认要删除该复印档案登记吗？', this.href)">删除</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>