<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>台账查阅表管理</title>
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
			top.$.jBox.open("iframe:"+url, "查阅干部档案登记簿",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairConsult"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairConsult/formDetail?id="+id;
			top.$.jBox.open(url, "查阅干部档案登记簿",800,500,{
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
				},closed:function (){self.location.href="${ctx}/affair/affairConsult"}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li  ><a href="${ctx}/affair/affairLedgerInto/">转入</a></li>
		<li ><a href="${ctx}/affair/affairLedgerOut/">转出</a></li>
		<li class="active"><a href="${ctx}/affair/affairConsult/">查阅</a></li>
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
		<li ><a href="${ctx}/affair/affairCopy/">复印档案登记</a></li>
		<%--<shiro:hasPermission name="affair:affairConsult:edit"><li><a href="${ctx}/affair/affairConsult/form">台账查阅表添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairConsult" action="${ctx}/affair/affairConsult/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>查阅人姓名：</label>
				<form:input path="cyrName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label class="width120">被查阅人姓名：</label>
				<form:input path="bcyrName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>查阅时间：</label>
				<input name="beginTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairConsult.beginTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairConsult.endTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>查阅人单位：</label>
				<sys:treeselect id="cyrUnit" name="cyrUnitId" value="${affairConsult.cyrUnitId}" labelName="cyrUnit" labelValue="${affairConsult.cyrUnit}"
					title="查阅人单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairConsult:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairConsult/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairConsult/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="导入" /></li>
			</shiro:hasPermission>
			<li class="btns"><input  class="btn btn-primary" type="button" value="导出" onclick="openAccountExport('${ctx}/affair/affairLedgerInto/accountExport')"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairConsult'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr> <th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>查阅人姓名</th>
				<th>被查阅人姓名</th>
				<th>查阅时间</th>
				<th>查阅人单位</th>
				<th>查阅人政治面貌</th>
				<th>查档目的</th>
				<shiro:hasPermission name="affair:affairConsult:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairConsult" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairConsult.id}"/></td>

				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td><a onclick="openDetailDialog('${affairConsult.id}')">
					${affairConsult.cyrName}
				</a></td>
				<td>
					${affairConsult.bcyrName}
				</td>
				<td>
					<fmt:formatDate value="${affairConsult.time}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairConsult.cyrUnit}
				</td>
				<td>
					${fns:getDictLabel(affairConsult.cyrFace, 'zzmm', '')}
				</td>
				<td>
					${affairConsult.purpose}
				</td>
				<shiro:hasPermission name="affair:affairConsult:edit"><td class="handleTd">
					<c:if test="${affairConsult.createBy.id == fns:getUser().id}">
						<a href="javascript:void(0)"
						   onclick="openForm('${ctx}/affair/affairConsult/form?id=${affairConsult.id}')">修改</a>
						<a href="${ctx}/affair/affairConsult/delete?id=${affairConsult.id}"
						   onclick="return confirmx('确认要删除该台账查阅表吗？', this.href)">删除</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
		<font color="red">注：1.局内查阅干部档案人员必须出具有查阅人单位主管领导签字、表中内容齐全、并加盖公章的《查（借）阅干部人事档案审批表》，持本人工作证,<br>

			并填写《查阅干部档案登记簿》，方可查阅。（本部门可只填《查阅干部档案登记簿》）。<br>

			2.查阅档案人员必须是中共党员。<br>

			3.任何人不得查阅本人及亲属的干部档案。</font>
</body>
</html>