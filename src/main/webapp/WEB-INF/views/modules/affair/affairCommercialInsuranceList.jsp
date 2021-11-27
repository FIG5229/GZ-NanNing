<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商业保险管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairCommercialInsurance/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairCommercialInsurance/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairCommercialInsurance/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairCommercialInsurance/");
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
					afterPrint:function (){
						$('#handleTh').css('display', 'table-cell');
						$('#cbTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
						$('.cbTd').css('display', 'table-cell');
						$('#headTitle').css('display', 'none');
					}
				});
			});

			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairCommercialInsurance", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairCommercialInsurance"}});
			});



		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairCommercialInsurance/formDetail?id="+id;
			top.$.jBox.open(url, "商业保险详情",1000,400,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }
        //添加，修改弹窗
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "商业保险",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairCommercialInsurance"}
			});
		}
	</script>
</head>
<body>

	<form:form id="searchForm" modelAttribute="affairCommercialInsurance" action="${ctx}/affair/affairCommercialInsurance/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="商业保险表.xlsx"/>
		<ul class="ul-form">
			<li>
				<label>年度：</label>
				<input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairCommercialInsurance.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li>
				<label>被保险人：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairCommercialInsurance:edit">
				<li class="btns">
					<input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairCommercialInsurance/form?mType=1','${ctx}/affair/affairCommercialInsurance?mType=1')"/>
				</li>
				<li class="btns">
					<input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairCommercialInsurance/deleteByIds','checkAll','myCheckBox')"/>
				</li>
				<li class="btns">
					<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>
				</li>
			</shiro:hasPermission>
			<li class="btns">
				<input id="export" class="btn btn-primary" type="button" value="导出"/>
			</li>
			<li class="btns">
				<input id="print" class="btn btn-primary" type="button" value="打印"/>
			</li>
			<li class="btns">
				<input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairCommercialInsurance'"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >商业保险表</td>
			</tr>
		</table>
		<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="cbTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th style="text-align: center">序号</th>
				<th style="text-align: center">年度</th>
				<th style="text-align: center">险种</th>
				<th style="text-align: center">被保险人</th>
				<th style="text-align: center">保单账号</th>
				<th style="text-align: center">身份证号</th>
				<th style="text-align: center">出险经过</th>
				<th style="text-align: center">申报材料</th>
				<th style="text-align: center">认定情况</th>
				<th style="text-align: center">赔付情况</th>
				<shiro:hasPermission name="affair:affairCommercialInsurance:edit"><th class="handleTd" style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairCommercialInsurance" varStatus="status">
			<tr>
				<td class="cbTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairCommercialInsurance.id}"/>
				</td>
				<td style="text-align: center">
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td style="text-align: center">
						${affairCommercialInsurance.year}
				</td>
				<td style="text-align: center">
						${affairCommercialInsurance.kind}
				</td>
				<td style="text-align: center">
						${affairCommercialInsurance.name}
				</td>
				<td style="text-align: center">
						${affairCommercialInsurance.accountNumber}
				</td>
				<td style="text-align: center">
						${affairCommercialInsurance.idNumber}
				</td>
				<td style="text-align: center">
						${affairCommercialInsurance.pass}
				</td>
				<td style="text-align: center">
						${affairCommercialInsurance.materials}
				</td>
				<td style="text-align: center">
						${affairCommercialInsurance.result}
				</td>
				<td style="text-align: center">
						${affairCommercialInsurance.compensate}
				</td>
				<td class="handleTd">
					<shiro:hasPermission name="affair:affairCommercialInsurance:view">
						<a href="javascript:void(0)" onclick="openDetailDialog('${affairCommercialInsurance.id}')">查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairCommercialInsurance:edit">
    					<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairCommercialInsurance/form?id=${affairCommercialInsurance.id}')">修改</a>
						<a href="${ctx}/affair/affairCommercialInsurance/delete?id=${affairCommercialInsurance.id}" onclick="return confirmx('确认要删除该商业保险吗？', this.href)">删除</a>
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