<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>档案台账转出管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairLedgerOut/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairLedgerOut/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairLedgerOut/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairLedgerOut/");
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
					afterPrint:function (){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairLedgerOut", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairLedgerOut"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "转出干部档案登记簿",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairLedgerOut"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairLedgerOut/formDetail?id="+id;
			top.$.jBox.open(url, "转出干部档案登记簿",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }
        //批量导出弹窗
		function openAccountExport(url) {
			top.$.jBox.open("iframe:"+url, "批量导出",800,520,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairLedgerOut"}
			});
		}
	</script>
</head>
<body>
<form:form id="searchForm" modelAttribute="affairLedgerOut" action="${ctx}/affair/affairLedgerOut/" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<input id="fileName" name="fileName" type="hidden" value="转出档案登记表.xlsx"/>
	<ul class="ul-form">
		<li><label>转往部门：</label>
			<form:input path="toDep" htmlEscape="false" class="input-medium"/>
		</li>
		<li><label>转出时间：</label>
			<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				   value="<fmt:formatDate value="${affairLedgerOut.startDate}" pattern="yyyy-MM-dd"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			-
			<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				   value="<fmt:formatDate value="${affairLedgerOut.endDate}" pattern="yyyy-MM-dd"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		</li>
		<li><label class="width120">转递通知单号：</label>
			<form:input path="noticeNo" htmlEscape="false" class="input-medium"/>
		</li>
	</ul>
	<ul class="ul-form2">
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
		<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairLedgerOut/form')"/></li>
		<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairLedgerOut/deleteByIds','checkAll','myCheckBox')"/></li>
		<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入" /></li>
		<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
		<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairLedgerOut'"/></li>
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
		<th>档案号</th>
		<th>原单位</th>
		<th>转往部门</th>
		<th>转出时间</th>
		<th>正本数量</th>
		<th>副本数量</th>
		<th>转递通知单号</th>
		<th id="handleTh">操作</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="affairLedgerOut" varStatus="status">
		<tr>
			<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairLedgerOut.id}"/></td>
			<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
			</td>
			<td>
					${affairLedgerOut.name}
			</td>
			<td>
					${affairLedgerOut.archiveNo}
			</td>
			<td>
					${affairLedgerOut.oldUnit}
			</td>
			<td>
					${affairLedgerOut.toDep}
			</td>
			<td>
				<fmt:formatDate value="${affairLedgerOut.date}" pattern="yyyy-MM-dd"/>
			</td>
			<td>
					${affairLedgerOut.getZNum()}
			</td>
			<td>
					${affairLedgerOut.getFNum()}
			</td>
			<td>
					${affairLedgerOut.noticeNo}
			</td>
			<td class="handleTd">
					<a href="javascript:void(0)"
					   onclick="openForm('${ctx}/affair/affairLedgerOut/form?id=${affairLedgerOut.id}')">修改</a>
					<a href="${ctx}/affair/affairLedgerOut/delete?id=${affairLedgerOut.id}"
					   onclick="return confirmx('确认要删除该档案台账转出吗？', this.href)">删除</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
	<%--<ul class="nav nav-tabs">
		<li  ><a href="${ctx}/affair/affairLedgerInto/">转入</a></li>
		<li class="active" ><a href="${ctx}/affair/affairLedgerOut/">转出</a></li>
&lt;%&ndash;		<li ><a href="${ctx}/affair/affairConsult/">查阅</a></li>&ndash;%&gt;
&lt;%&ndash;		<li ><a href="${ctx}/affair/affairBorrow/">借阅</a></li>&ndash;%&gt;
&lt;%&ndash;		<li ><a href="${ctx}/affair/affairArchiveApproval/">查(借)阅审批列表</a></li>&ndash;%&gt;
		<li ><a href="${ctx}/affair/affairArchiveRegister/">在职</a></li>
		<li ><a href="${ctx}/affair/affairRetire/">离退</a></li>
		<li ><a href="${ctx}/affair/affairDeath/">死亡</a></li>
&lt;%&ndash;		<li ><a href="${ctx}/affair/affairMaterial/">收集</a></li>&ndash;%&gt;
&lt;%&ndash;		<li ><a href="${ctx}/affair/affairTemHum/">温湿度</a></li>&ndash;%&gt;
&lt;%&ndash;		<li ><a href="${ctx}/affair/affairCheck/">核对</a></li>&ndash;%&gt;
&lt;%&ndash;		<li><a href="${ctx}/affair/affairSecurityCheck/">安全检查</a></li>&ndash;%&gt;
&lt;%&ndash;		<li ><a href="${ctx}/affair/affairDestroyMeterial/">销毁清册</a></li>&ndash;%&gt;
&lt;%&ndash;		<li ><a href="${ctx}/affair/affairCopy/">复印档案登记</a></li>&ndash;%&gt;
	&lt;%&ndash;	<shiro:hasPermission name="affair:affairLedgerOut:edit"><li><a href="${ctx}/affair/affairLedgerOut/form">档案台账转出添加</a></li></shiro:hasPermission>&ndash;%&gt;	</ul>
	<form:form id="searchForm" modelAttribute="affairLedgerOut" action="${ctx}/affair/affairLedgerOut/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="转出.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>原单位：</label>
				<sys:treeselect id="oldUnit" name="oldUnitId" value="${affairLedgerOut.oldUnitId}" labelName="oldUnit" labelValue="${affairLedgerOut.oldUnit}"
					title="原单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true"/>
			</li>
			<li><label>转往部门：</label>
				<form:input path="toDep" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>转出时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairLedgerOut.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				-
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairLedgerOut.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label class="width120">转递通知单号：</label>
				<form:input path="noticeNo" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairLedgerOut:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairLedgerOut/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairLedgerOut/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入" /></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
&lt;%&ndash;			<li class="btns"><input  class="btn btn-primary" type="button" value="导出" onclick="openAccountExport('${ctx}/affair/affairLedgerInto/accountExport')"/></li>&ndash;%&gt;
			<li class="btns"><input  id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairLedgerOut'"/></li>
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
				<th>档案号</th>
				<th>原单位</th>
				<th>转往部门</th>
				<th>转出时间</th>
				<th>正本数量</th>
				<th>副本数量</th>
				<th>转递通知单号</th>
				<shiro:hasPermission name="affair:affairLedgerOut:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairLedgerOut" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairLedgerOut.id}"/></td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td><a onclick="openDetailDialog('${affairLedgerOut.id}')">
					${affairLedgerOut.name}
				</a></td>
				<td>
					${affairLedgerOut.archiveNo}
				</td>
				<td>
					${affairLedgerOut.oldUnit}
				</td>
				<td>
					${affairLedgerOut.toDep}
				</td>
				<td>
					<fmt:formatDate value="${affairLedgerOut.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairLedgerOut.getZNum()}
				</td>
				<td>
					${affairLedgerOut.getFNum()}
				</td>
				<td>
					${affairLedgerOut.noticeNo}
				</td>
				<shiro:hasPermission name="affair:affairLedgerOut:edit"><td class="handleTd">
					<c:if test="${affairLedgerOut.createBy.id == fns:getUser().id}">
						<a href="javascript:void(0)"
						   onclick="openForm('${ctx}/affair/affairLedgerOut/form?id=${affairLedgerOut.id}')">修改</a>
						<a href="${ctx}/affair/affairLedgerOut/delete?id=${affairLedgerOut.id}"
						   onclick="return confirmx('确认要删除该档案台账转出吗？', this.href)">删除</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
		<font color="red">注：转出干部档案必须及时登记，并在《干部档案花名册》备注栏内注明转出原因及时间。</font>
</body>
</html>--%>