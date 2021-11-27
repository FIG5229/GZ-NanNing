<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>医保关系变动管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairMedicareChange/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairMedicareChange/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairMedicareChange/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairMedicareChange/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);

			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairMedicareChange", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairMedicareChange"}});
			});

			$("#print").click(function(){
				$('#handleTh').css('display', 'none');
				$('#cbTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$('.cbTd').css('display', 'none');
				$('#headTitle').addClass("table table-striped table-bordered table-condensed");
				$('#headTitle').removeAttr('style');
				$("#contentTable").printThis({
					debug: false,
					importCSS: false,
					importStyle: false,
					printContainer: true,
					loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css"],
					pageTitle: "",
					removeInline: false,
					printDelay: 333,
					header: "",
					formValues: false,
					afterPrint:function(){
						$('#handleTh').css('display', 'table-cell');
						$('#cbTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
						$('.cbTd').css('display', 'table-cell');
						$('#headTitle').css('display', 'none');
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
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "医保关系变动",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairMedicareChange"}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairMedicalInsurance/">医疗保险</a></li>
		<li class="active"><a href="${ctx}/affair/affairMedicareChange/">医保关系变动</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairMedicareChange" action="${ctx}/affair/affairMedicareChange/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="医保关系变动表.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>变动时间：</label>
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairMedicareChange.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairMedicareChange.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairMedicareChange:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairMedicareChange/form?id=${affairMedicareChange.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairMedicareChange/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairMedicareChange'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >医保关系变动表</td>
			</tr>
		</table>
		<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="cbTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th style="text-align: center">序号</th>
				<th style="text-align: center">单位</th>
				<th style="text-align: center">姓名</th>
				<th style="text-align: center">身份证号</th>
				<th style="text-align: center">变动原因</th>
				<th style="text-align: center">变动时间</th>
				<th style="text-align: center">险种变动情况</th>
				<th style="text-align: center">医保缴费基数</th>
				<shiro:hasPermission name="affair:affairMedicareChange:edit"><th id="handleTh" style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairMedicareChange" varStatus="status">
			<tr>
				<td class="cbTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairMedicareChange.id}"/>
				</td>
				<td style="text-align: center">
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td style="text-align: center">
					${affairMedicareChange.unit}
				</td>
				<td style="text-align: center">
					${affairMedicareChange.name}
				</td>
				<td style="text-align: center">
					${affairMedicareChange.idNumber}
				</td>
				<td style="text-align: center">
					${affairMedicareChange.reason}
				</td>
				<td style="text-align: center">
					<fmt:formatDate value="${affairMedicareChange.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td style="text-align: center">
					${affairMedicareChange.situation}
				</td>
				<td style="text-align: center">
					${affairMedicareChange.cardinality}
				</td>
				<td class="handleTd">
				<shiro:hasPermission name="affair:affairMedicareChange:edit">
					<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairMedicareChange/form?id=${affairMedicareChange.id}')">修改</a>
					<a href="${ctx}/affair/affairMedicareChange/delete?id=${affairMedicareChange.id}" onclick="return confirmx('确认要删除该医保关系变动吗？', this.href)">删除</a>
				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>