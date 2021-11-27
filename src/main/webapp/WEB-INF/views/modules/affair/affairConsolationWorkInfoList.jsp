<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>一般慰问</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairConsolationWorkInfo/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairConsolationWorkInfo/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairConsolationWorkInfo/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairConsolationWorkInfo/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairConsolationWorkInfo", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairConsolationWorkInfo"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "慰问工作",1200,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairConsolationWorkInfo"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairConsolationWorkInfo/formDetail?id="+id;
			top.$.jBox.open(url, "慰问工作详情",900,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		$('#notPass').popover();
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairConsolationWorkInfo/">慰问工作</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairConsolationWorkInfo" action="${ctx}/affair/affairConsolationWorkInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="一般慰问.xlsx"/>
		<ul class="ul-form">
			<li><label>时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairConsolationWorkInfo.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairConsolationWorkInfo.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairConsolationWorkInfo.unitId}" labelName="unit" labelValue="${affairConsolationWorkInfo.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>慰问类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_ww')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		<%--	<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>--%>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairConsolationWorkInfo:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairConsolationWorkInfo/form?id=${affairConsolationWorkInfo.id}')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairConsolationWorkInfo/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairConsolationWorkInfo'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<%--9.3问题反馈 再次显示--%>
				<th>标题</th>
				<th>单位</th>
				<th>姓名</th>
				<th>身份证号</th>
				<th>慰问类型</th>
				<th>时间</th>
				<th>慰问金（慰问品）</th>
				<th>慰问原因</th>
				<th>内容</th>
				<th>备注</th>
				<shiro:hasPermission name="affair:affairConsolationWorkInfo:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairConsolationWorkInfo" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairConsolationWorkInfo.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairConsolationWorkInfo.title}
				</td>

				<td>
					${affairConsolationWorkInfo.unit}
				</td>
				<td>
					${affairConsolationWorkInfo.name}
				</td>
				<td>
					${affairConsolationWorkInfo.idNumber}
				</td>
				<td>
					${fns:getDictLabel(affairConsolationWorkInfo.type, 'affair_ww', '')}
				</td>
				<td>
					<fmt:formatDate value="${affairConsolationWorkInfo.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairConsolationWorkInfo.money}
				</td>
				<td>
					${affairConsolationWorkInfo.cause}
				</td>
				<td>
						${affairConsolationWorkInfo.content}
				</td>
				<td>
						${affairConsolationWorkInfo.remark}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairConsolationWorkInfo.id}')">查看</a>
					<shiro:hasPermission name="affair:affairConsolationWorkInfo:edit">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairConsolationWorkInfo/form?id=${affairConsolationWorkInfo.id}')">修改</a>
						<a href="${ctx}/affair/affairConsolationWorkInfo/delete?id=${affairConsolationWorkInfo.id}" onclick="return confirmx('确认要删除该慰问工作管理吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>