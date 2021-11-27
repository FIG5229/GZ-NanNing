<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>退休管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#shengcheng").click(function () {
				$("#searchForm").attr("action","${ctx}/affair/affairTuixiu/shengcheng");
				$("#searchForm").submit();
			});
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairTuixiu/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTuixiu/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairTuixiu/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTuixiu/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairTuixiu", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairTuixiu"}});
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
					header: null,
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
			top.$.jBox.open("iframe:"+url, "退休管理",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairTuixiu"}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<c:if test="${'1'.equals(fns:getUser().company.id)}">
			<li class="active"><a href="${ctx}/affair/affairTuixiu/list?tabFlag=1">公安局退休管理</a></li>
		</c:if>
		<c:if test="${'34'.equals(fns:getUser().company.id)}">
			<li class="active"><a href="${ctx}/affair/affairTuixiu/list?tabFlag=777">南宁公安处退休管理</a></li>
		</c:if>
		<c:if test="${'95'.equals(fns:getUser().company.id)}">
			<li class="active"><a href="${ctx}/affair/affairTuixiu/list?tabFlag=888">柳州公安处退休管理</a></li>
		</c:if>
		<c:if test="${'156'.equals(fns:getUser().company.id)}">
			<li class="active"><a href="${ctx}/affair/affairTuixiu/list?tabFlag=999">北海公安处退休管理</a></li>
		</c:if>
	</ul>
	<form:form id="searchForm" modelAttribute="affairTuixiu" action="${ctx}/affair/affairTuixiu/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="退休表.xlsx"/>
		<ul class="ul-form">

			<li><label>退休时间：</label>
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairTuixiu.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairTuixiu.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairTuixiu:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairTuixiu/form?id=${affairTuixiu.id}')"/></li>
				<li class="btns"><input id="shengcheng" class="btn btn-primary"  type="button" value="生成" /></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairTuixiu/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairTuixiu'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >退休人员信息统计表</td>
			</tr>
		</table>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="cbTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th style="text-align: center">序号</th>
				<th style="text-align: center">证书编号</th>
				<th style="text-align: center">发证日期</th>
				<th style="text-align: center">姓名</th>
				<th style="text-align: center">身份证号</th>
				<th style="text-align: center">性别</th>
				<th style="text-align: center">出生年月</th>
				<th style="text-align: center">民族</th>
				<th style="text-align: center">籍贯</th>
				<th style="text-align: center">参加工作日期</th>
				<th style="text-align: center">退休时间</th>
				<th style="text-align: center">退休时单位</th>
				<th style="text-align: center">退休时职务</th>
				<th style="text-align: center">批准退休单位</th>
				<th style="text-align: center">退休状态</th>
				<th style="text-align: center">备注</th>
				<shiro:hasPermission name="affair:affairTuixiu:edit"><th id="handleTh" style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTuixiu" varStatus="status">
			<tr>
				<td class="cbTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTuixiu.id}"/>
				</td>
				<td style="text-align: center">
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td style="text-align: center">
					${affairTuixiu.number}
				</td>
				<td style="text-align: center">
					<fmt:formatDate value="${affairTuixiu.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td style="text-align: center">
					${affairTuixiu.name}
				</td>
				<td style="text-align: center">
					${affairTuixiu.idNumber}
				</td>
				<td style="text-align: center">
						${fns:getDictLabel(affairTuixiu.sex, 'sex', '')}
				</td>
				<td style="text-align: center">
					<fmt:formatDate value="${affairTuixiu.birth}" pattern="yyyy-MM-dd"/>
				</td>
				<td style="text-align: center">
						${fns:getDictLabel(affairTuixiu.nation, 'nation', '')}
				</td>
				<td style="text-align: center">
					${affairTuixiu.hometown}
				</td>
				<td style="text-align: center">
					<fmt:formatDate value="${affairTuixiu.workDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td style="text-align: center">
					<fmt:formatDate value="${affairTuixiu.retirementTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td style="text-align: center">
					${affairTuixiu.unitName}
				</td>
				<td style="text-align: center">
					${affairTuixiu.retirementJob}
				</td>
				<td style="text-align: center">
					${affairTuixiu.unit}
				</td>
				<td style="text-align: center">
					${affairTuixiu.status}
				</td>
				<td style="text-align: center">
					${affairTuixiu.remark}
				</td>
				<shiro:hasPermission name="affair:affairTuixiu:edit"><td class="handleTd">
					<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairTuixiu/form?id=${affairTuixiu.id}')">修改</a>
					<a href="${ctx}/affair/affairTuixiu/queDing?id=${affairTuixiu.id}">确认</a>
					<a href="${ctx}/affair/affairTuixiu/delete?id=${affairTuixiu.id}" onclick="return confirmx('确认要删除该退休吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>