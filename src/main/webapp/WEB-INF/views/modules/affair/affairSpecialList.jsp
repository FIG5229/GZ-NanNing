<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>特别抚恤金管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairSpecial/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairSpecial/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairSpecial/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairSpecial/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairSpecial", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairSpecial"}});
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
			top.$.jBox.open("iframe:"+url, "特别抚恤金",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairSpecial"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairSpecial/formDetail?id="+id;
			top.$.jBox.open(url, "特别抚恤金",1200,600,{
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
		<li><a href="${ctx}/affair/affairCjCompassionate/">参公人员抚恤</a></li>
		<li><a href="${ctx}/affair/affairGwy/">公务员一次性抚恤</a></li>
		<li class="active"><a href="${ctx}/affair/affairSpecial/">特别抚恤金</a></li>
		<li><a href="${ctx}/affair/affairDeathMoney/">伤亡特殊补助金</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairSpecial" action="${ctx}/affair/affairSpecial/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="特别抚恤金表.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>参加工作时间：</label>
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairSpecial.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairSpecial.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>批准时间：</label>
				<input name="beginAprovalDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairSpecial.beginAprovalDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endAprovalDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairSpecial.endAprovalDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairSpecial:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairSpecial/form?id=${affairSpecial.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairSpecial/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairSpecial'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >特别抚恤金统计表</td>
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
				<th style="text-align: center">是否是党、团员</th>
				<th style="text-align: center">职务</th>
				<th style="text-align: center">级别</th>
				<th style="text-align: center">警衔</th>
				<th style="text-align: center">参加工作时间</th>
				<th style="text-align: center">种类</th>
				<th style="text-align: center">主要事迹</th>
				<th style="text-align: center">申报金额</th>
				<th style="text-align: center">批准时间</th>
				<shiro:hasPermission name="affair:affairSpecial:edit"><th id="handleTh" style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairSpecial" varStatus="status">
			<tr>
				<td class="cbTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairSpecial.id}"/>
				</td>
				<td style="text-align: center">
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td style="text-align: center">
					${affairSpecial.unit}
				</td>
				<td style="text-align: center">
					${affairSpecial.name}
				</td>
				<td style="text-align: center">
					${affairSpecial.idNumber}
				</td>
				<td style="text-align: center">
					${fns:getDictLabel(affairSpecial.personnelFlag, 'affair_is_dang', '')}
				</td>
				<td style="text-align: center">
					${affairSpecial.job}
				</td>
				<td style="text-align: center">
					${affairSpecial.level}
				</td>
				<td style="text-align: center">
					${affairSpecial.policeRank}
				</td>
				<td style="text-align: center">
					<fmt:formatDate value="${affairSpecial.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td style="text-align: center">
					${fns:getDictLabel(affairSpecial.type, 'affair_special_type', '')}
				</td>
				<td style="text-align: center">
					${affairSpecial.mainDeeds}
				</td>
				<td style="text-align: center">
					${affairSpecial.money}
				</td>
				<td style="text-align: center">
					<fmt:formatDate value="${affairSpecial.aprovalDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairSpecial.id}')">查看</a>
				<shiro:hasPermission name="affair:affairSpecial:edit">
					<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairSpecial/form?id=${affairSpecial.id}')">修改</a>
					<a href="${ctx}/affair/affairSpecial/delete?id=${affairSpecial.id}" onclick="return confirmx('确认要删除该特别抚恤金吗？', this.href)">删除</a>
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