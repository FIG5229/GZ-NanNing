<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>档案报送单管理管理</title>
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
			//导出功能的JS
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairSubmitForm/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairSubmitForm/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairSubmitForm/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairSubmitForm/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
			//导入功能的JS
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairSubmitForm", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairSubmitForm"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "干部档案报送单",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairSubmitForm"}
			});
		}
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairSubmitForm/formDetail?id="+id;
			top.$.jBox.open(url, "干部档案报送单",1000,600,{
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
				},closed:function (){self.location.href="${ctx}/affair/affairLedgerInto"}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairSubmitForm/">档案报送单</a></li>
		<li><a href="${ctx}/affair/affairTransmitForm/">档案传递单</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairSubmitForm" action="${ctx}/affair/affairSubmitForm/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="档案报送单.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairSubmitForm.unitId}" labelName="unit" labelValue="${affairSubmitForm.unit}"
					title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>职务：</label>
				<form:select path="job" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_job')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>档案整理人：</label>
				<form:input path="arranger" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>档案审核人：</label>
				<form:input path="checker" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label class="width140">报送单位签字领导：</label>
				<form:input path="leader" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairSubmitForm:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairSubmitForm/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairSubmitForm/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairSubmitForm'"/></li>
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
				<th>单位</th>
				<th>职务</th>
				<th>档案整理人</th>
				<th>档案审核人</th>
				<th>报送单位签字领导</th>
				<th>任职时间</th>
				<shiro:hasPermission name="affair:affairSubmitForm:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairSubmitForm" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairSubmitForm.id}"/></td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td><a onclick="openDetailDialog('${affairSubmitForm.id}')">
					${affairSubmitForm.name}
				</a></td>
				<td>
					${affairSubmitForm.unit}
				</td>
				<td>
					${fns:getDictLabel(affairSubmitForm.job, 'affair_job', '')}
				</td>
				<td>
					${affairSubmitForm.arranger}
				</td>
				<td>
					${affairSubmitForm.checker}
				</td>
				<td>
					${affairSubmitForm.leader}
				</td>
				<td>
					<fmt:formatDate value="${affairSubmitForm.startDate}" pattern="yyyy/MM/dd"/>--<fmt:formatDate value="${affairSubmitForm.endDate}" pattern="yyyy/MM/dd"/>
				</td>
				<shiro:hasPermission name="affair:affairSubmitForm:edit"><td class="handleTd">
					<c:if test="${affairSubmitForm.createBy.id == fns:getUser().id}">
						<a href="javascript:void(0)"
						   onclick="openForm('${ctx}/affair/affairSubmitForm/form?id=${affairSubmitForm.id}')">修改</a>
						<a href="${ctx}/affair/affairSubmitForm/delete?id=${affairSubmitForm.id}"
						   onclick="return confirmx('确认要删除该档案报送单管理吗？', this.href)">删除</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>