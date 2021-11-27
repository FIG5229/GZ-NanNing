<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>参公人员抚恤管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairCjCompassionate/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairCjCompassionate/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairCjCompassionate/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairCjCompassionate/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairCjCompassionate", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairCjCompassionate"}});
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
			top.$.jBox.open("iframe:"+url, "参公人员抚恤",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairCjCompassionate"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairCjCompassionate/formDetail?id="+id;
			top.$.jBox.open(url, "参公人员抚恤",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }
    </script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairCjCompassionate/">参公人员抚恤</a></li>
		<li><a href="${ctx}/affair/affairGwy/">公务员一次性抚恤</a></li>
		<li><a href="${ctx}/affair/affairSpecial/">特别抚恤金</a></li>
		<li><a href="${ctx}/affair/affairDeathMoney/">伤亡特殊补助金</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairCjCompassionate" action="${ctx}/affair/affairCjCompassionate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="参公人员抚恤表.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>死亡时间：</label>
				<input name="beginDeathDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairCjCompassionate.beginDeathDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endDeathDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairCjCompassionate.endDeathDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairCjCompassionate:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairCjCompassionate/form?id=${affairCjCompassionate.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairCjCompassionate/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairCjCompassionate'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >参公人员抚恤统计表</td>
			</tr>
		</table>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="cbTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th style="text-align: center">序号</th>
				<th style="text-align: center">姓名</th>
				<th style="text-align: center">身份证号</th>
				<th style="text-align: center">死亡时间</th>
				<th style="text-align: center">是否有遗属</th>
				<th style="text-align: center">核定的离退休费</th>
				<th style="text-align: center">基本离退费（含警衔和历年增加）</th>
				<th style="text-align: center">上一次全国城镇居民可支配收入</th>
				<th style="text-align: center">丧葬补助金</th>
				<th style="text-align: center">一次性困难补助</th>
				<th style="text-align: center">小计</th>
				<th style="text-align: center">上一次全国城镇居民可支配收入的2倍</th>
				<th style="text-align: center">40个月基本离退费</th>
				<th style="text-align: center">增发比例</th>
				<th style="text-align: center">增发金额</th>
				<th style="text-align: center">社保遗嘱救济费（月）</th>
				<th style="text-align: center">按【2014】101号遗属生活困难补助费（月）</th>
				<th style="text-align: center">至2017年8月差额</th>
				<th style="text-align: center">公务员丧葬补助按桂人社发【2011】186号</th>
				<th style="text-align: center">三个月工资按桂发【1911】31号</th>
				<th style="text-align: center">按民发【2014】101号计发合计</th>
				<th style="text-align: center">合计补差</th>
				<shiro:hasPermission name="affair:affairCjCompassionate:edit"><th id="handleTh" style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairCjCompassionate" varStatus="status">
			<tr>
				<td class="cbTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairCjCompassionate.id}"/>
				</td>
				<td style="text-align: center">
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td style="text-align: center">
					${affairCjCompassionate.name}
				</td>
				<td style="text-align: center">
					${affairCjCompassionate.idNumber}
				</td>
				<td style="text-align: center">
					<fmt:formatDate value="${affairCjCompassionate.deathDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td style="text-align: center">
					${fns:getDictLabel(affairCjCompassionate.isWill, 'yes_no', '')}
				</td>
				<td style="text-align: center">
					${affairCjCompassionate.fee}
				</td>
				<td style="text-align: center">
					${affairCjCompassionate.baseFee}
				</td>
				<td style="text-align: center">
					${affairCjCompassionate.lastFee}
				</td>
				<td style="text-align: center">
					${affairCjCompassionate.funeralGrant}
				</td>
				<td style="text-align: center">
					${affairCjCompassionate.hardshipAllowance}
				</td>
				<td style="text-align: center">
					${affairCjCompassionate.subtotal}
				</td>
				<td style="text-align: center">
					${affairCjCompassionate.doubleFee}
				</td>
				<td style="text-align: center">
					${affairCjCompassionate.fortyFee}
				</td>
				<td style="text-align: center">
					${affairCjCompassionate.issuanceRatio}
				</td>
				<td style="text-align: center">
					${affairCjCompassionate.additionalAmount}
				</td>
				<td style="text-align: center">
					${affairCjCompassionate.relief}
				</td>
				<td style="text-align: center">
					${affairCjCompassionate.hardFee}
				</td>
				<td style="text-align: center">
					${affairCjCompassionate.difference}
				</td>
				<td style="text-align: center">
					${affairCjCompassionate.gongWuYuan}
				</td>
				<td style="text-align: center">
					${affairCjCompassionate.threeMonth}
				</td>
				<td style="text-align: center">
					${affairCjCompassionate.people}
				</td>
				<td style="text-align: center">
					${affairCjCompassionate.sumDifference}
				</td>
				<td class="handleTd">
				<a href="javascript:void(0)" onclick="openDetailDialog('${affairCjCompassionate.id}')">查看</a>
				<shiro:hasPermission name="affair:affairCjCompassionate:edit">
					<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairCjCompassionate/form?id=${affairCjCompassionate.id}')">修改</a>
					<a href="${ctx}/affair/affairCjCompassionate/delete?id=${affairCjCompassionate.id}" onclick="return confirmx('确认要删除该参公人员抚恤吗？', this.href)">删除</a>
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