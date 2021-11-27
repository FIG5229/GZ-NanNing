<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>档案台账转入管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairLedgerInto/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairLedgerInto/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairLedgerInto/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairLedgerInto/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairLedgerInto", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairLedgerInto"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "转入干部档案登记簿",800,520,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairLedgerInto"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairLedgerInto/formDetail?id="+id;
			top.$.jBox.open(url, "转入干部档案登记簿",800,500,{
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
				},closed:function (){self.location.href="${ctx}/affair/affairLedgerInto"}
			});
		}
	</script>
</head>
<body>
<form:form id="searchForm" modelAttribute="affairLedgerInto" action="${ctx}/affair/affairLedgerInto/" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<input id="fileName" name="fileName" type="hidden" value="转入档案登记表.xlsx"/>
	<ul class="ul-form">
		<li><label>档案号：</label>
			<form:input path="archiveNo" htmlEscape="false" class="input-medium"/>
		</li>
		<li><label>转入时间：</label>
			<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				   value="<fmt:formatDate value="${affairLedgerInto.startDate}" pattern="yyyy-MM-dd"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			-
			<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				   value="<fmt:formatDate value="${affairLedgerInto.endDate}" pattern="yyyy-MM-dd"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		</li>
	</ul>
	<ul class="ul-form2">
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
		<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairLedgerInto/form')"/></li>
		<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairLedgerInto/deleteByIds','checkAll','myCheckBox')"/></li>
		<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入" /></li>
		<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
		<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairLedgerInto'"/></li>
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
		<th>现单位</th>
		<th>接收人</th>
		<th>现职务</th>
		<th>转入原因</th>
		<th>正本数量</th>
		<th>副本数量</th>
		<th>转入时间</th>
		<th id="handleTh">操作</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="affairLedgerInto" varStatus="status">
		<tr>
			<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairLedgerInto.id}"/></td>
			<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
			<td>

					${affairLedgerInto.name}
			</td>
			<td>
					${affairLedgerInto.archiveNo}
			</td>
			<td>
					${affairLedgerInto.oldUnit}
			</td>
			<td>
					${affairLedgerInto.nowUnit}
			</td>
			<td>
					${affairLedgerInto.receiver}
			</td>
			<td>
					${affairLedgerInto.nowJob}
			</td>
			<td>
					${affairLedgerInto.reason}
			</td>
			<td>
					${affairLedgerInto.zhengbenNum}
			</td>
			<td>
					${affairLedgerInto.fubenNum}
			</td>
			<td>
				<fmt:formatDate value="${affairLedgerInto.date}" pattern="yyyy-MM-dd"/>
			</td>
			<td class="handleTd">
					<a href="javascript:void(0)"
					   onclick="openForm('${ctx}/affair/affairLedgerInto/form?id=${affairLedgerInto.id}')">修改</a>
					<a href="${ctx}/affair/affairLedgerInto/delete?id=${affairLedgerInto.id}"
					   onclick="return confirmx('确认要删除该档案台账转入吗？', this.href)">删除</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
	<%--<ul class="nav nav-tabs">
		<li class="active" ><a href="${ctx}/affair/affairLedgerInto/">转入</a></li>
		<li ><a href="${ctx}/affair/affairLedgerOut/">转出</a></li>
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
	</ul>
	<form:form id="searchForm" modelAttribute="affairLedgerInto" action="${ctx}/affair/affairLedgerInto/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="转入.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>档案号：</label>
				<form:input path="archiveNo" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>原单位：</label>
				<form:input path="oldUnit" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>现单位：</label>
				<sys:treeselect id="nowUnit" name="nowUnitId" value="${affairLedgerInto.nowUnitId}" labelName="nowUnit" labelValue="${affairLedgerInto.nowUnit}"
					title="现单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>转入时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairLedgerInto.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				-
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairLedgerInto.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairLedgerInto:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairLedgerInto/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairLedgerInto/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入" /></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
&lt;%&ndash;			<li class="btns"><input  class="btn btn-primary" type="button" value="导出" onclick="openAccountExport('${ctx}/affair/affairLedgerInto/accountExport')"/></li>&ndash;%&gt;
				<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairLedgerInto'"/></li>
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
				<th>现单位</th>
				<th>接收人</th>
				<th>现职务</th>
				<th>转入原因</th>
				<th>正本数量</th>
				<th>副本数量</th>
				<th>转入时间</th>
				<shiro:hasPermission name="affair:affairLedgerInto:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairLedgerInto" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairLedgerInto.id}"/></td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td><a onclick="openDetailDialog('${affairLedgerInto.id}')">

				${affairLedgerInto.name}
				</a></td>
				<td>
					${affairLedgerInto.archiveNo}
				</td>
				<td>
					${affairLedgerInto.oldUnit}
				</td>
				<td>
					${affairLedgerInto.nowUnit}
				</td>
				<td>
					${affairLedgerInto.receiver}
				</td>
				<td>
					${affairLedgerInto.nowJob}
				</td>
				<td>
						${affairLedgerInto.reason}
				</td>
				<td>
						${affairLedgerInto.zhengbenNum}
				</td>
				<td>
						${affairLedgerInto.fubenNum}
				</td>
				<td>
					<fmt:formatDate value="${affairLedgerInto.date}" pattern="yyyy-MM-dd"/>
				</td>
				<shiro:hasPermission name="affair:affairLedgerInto:edit"><td class="handleTd">
					<c:if test="${affairLedgerInto.createBy.id == fns:getUser().id}">
						<a href="javascript:void(0)"
						   onclick="openForm('${ctx}/affair/affairLedgerInto/form?id=${affairLedgerInto.id}')">修改</a>
						<a href="${ctx}/affair/affairLedgerInto/delete?id=${affairLedgerInto.id}"
						   onclick="return confirmx('确认要删除该档案台账转入吗？', this.href)">删除</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
		<font color="red">注：转入干部档案必须及时登记，并填写《干部档案花名册》各项目，在花名册备注栏内填写转入原因及时间。</font>

</body>
</html>--%>