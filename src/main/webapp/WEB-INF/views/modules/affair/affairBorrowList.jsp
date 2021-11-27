<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>档案台账借阅表管理</title>
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
			top.$.jBox.open("iframe:"+url, "借阅干部档案登记簿",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairBorrow"}
			});
		}

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairBorrow/formDetail?id="+id;
			top.$.jBox.open(url, "借阅干部档案登记簿",800,500,{
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
				},closed:function (){self.location.href="${ctx}/affair/affairBorrow"}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li  ><a href="${ctx}/affair/affairLedgerInto/">转入</a></li>
		<li ><a href="${ctx}/affair/affairLedgerOut/">转出</a></li>
		<li ><a href="${ctx}/affair/affairConsult/">查阅</a></li>
		<li class="active"><a href="${ctx}/affair/affairBorrow/">借阅</a></li>
		<li ><a href="${ctx}/affair/affairArchiveApproval/">查(借)阅审批列表</a></li>
		<li ><a href="${ctx}/affair/affairArchiveRegister/">在职</a></li>
		<li ><a href="${ctx}/affair/affairRetire/">离退</a></li>
		<li ><a href="${ctx}/affair/affairDeath/">死亡</a></li>
		<li ><a href="${ctx}/affair/affairMaterial/">收集</a></li>
		<li ><a href="${ctx}/affair/affairTemHum/">温湿度</a></li>
		<li ><a href="${ctx}/affair/affairCheck/">核对</a></li>
		<li><a href="${ctx}/affair/affairSecurityCheck/">安全检查</a></li>
		<li ><a href="${ctx}/affair/affairDestroyMeterial/">销毁清册</a></li>
		<li ><a href="${ctx}/affair/affairCopy/">复印档案登记</a></li>
		<%--<shiro:hasPermission name="affair:affairBorrow:edit"><li><a href="${ctx}/affair/affairBorrow/form">档案台账借阅表添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairBorrow" action="${ctx}/affair/affairBorrow/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>借阅人姓名：</label>
				<form:input path="jyrName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label class="width120">借阅人身份证号：</label>
				<form:input path="jyrIdNumber" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label class="width120">被借阅人姓名：</label>
				<form:input path="bjyrName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label class="width140">被借阅人身份证号：</label>
				<form:input path="bjyrIdNumber" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>借阅人单位：</label>
				<sys:treeselect id="jyrUnit" name="jyrUnitId" value="${affairBorrow.jyrUnitId}" labelName="jyrUnit" labelValue="${affairBorrow.jyrUnit}"
								title="借阅人单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>借阅时间：</label>
				<input name="beginTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairBorrow.beginTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairBorrow.endTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>

			<li><label class="width140">借阅人政治面貌：</label>
				<form:select path="jyrFace" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('zzmm')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>批准人：</label>
				<form:input path="approver" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>经手人：</label>
				<form:input path="handler" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairBorrow:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairBorrow/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairBorrow/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="导入" /></li>
			</shiro:hasPermission>
			<li class="btns"><input  class="btn btn-primary" type="button" value="导出" onclick="openAccountExport('${ctx}/affair/affairLedgerInto/accountExport')"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairBorrow'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>借阅人姓名</th>
				<th>借阅人身份证号</th>
				<th>被借阅人姓名</th>
				<th>被借阅人身份证号</th>
				<th>借阅时间</th>
				<th>借阅人单位</th>
				<th>批准人</th>
				<th>归还日期</th>
				<th>经手人</th>
				<shiro:hasPermission name="affair:affairBorrow:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairBorrow" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairBorrow.id}"/></td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>

				<td><a onclick="openDetailDialog('${affairBorrow.id}')">
					${affairBorrow.jyrName}
				</a></td>
				<td>${affairBorrow.jyrIdNumber}</td>
				<td>
					${affairBorrow.bjyrName}
				</td>
				<td>${affairBorrow.bjyrIdNumber}</td>
				<td>
					<fmt:formatDate value="${affairBorrow.time}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairBorrow.jyrUnit}
				</td>
				<td>
					${affairBorrow.approver}
				</td>
				<td>
					<fmt:formatDate value="${affairBorrow.situation}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairBorrow.handler}
				</td>
				<shiro:hasPermission name="affair:affairBorrow:edit"><td class="handleTd">
					<c:if test="${affairBorrow.createBy.id == fns:getUser().id}">
						<a href="javascript:void(0)"
						   onclick="openForm('${ctx}/affair/affairBorrow/form?id=${affairBorrow.id}')">修改</a>
						<a href="${ctx}/affair/affairBorrow/delete?id=${affairBorrow.id}"
						   onclick="return confirmx('确认要删除该档案台账借阅表吗？', this.href)">删除</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
<font color="red">注：1.干部档案一般不外借，特殊情况外借时由主管部门领导批准。<br>

	2.局内借阅干部档案人员必须出具有借阅人单位主管领导签字、表中内容齐全、并加盖公章的《查（借）阅干部人事档案审批表》，持本人工作证，并填写《借阅干部档案登记簿》，<br>

	方可借阅。<br>

	3.借阅档案人员必须是中共党员     </font>
</body>
</html>