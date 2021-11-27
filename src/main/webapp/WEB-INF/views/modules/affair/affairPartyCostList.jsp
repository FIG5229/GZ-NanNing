<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党费管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairPartyCost/export?treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPartyCost/list?treeId=${treeId}");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairPartyCost/export?flag=true&treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPartyCost/list?treeId=${treeId}");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairPartyCost", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairPartyCost/list?treeId=${treeId}"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
		}
		//添加修改弹窗
		function openAddEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairPartyCost/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairPartyCost/form";
			}
			top.$.jBox.open(url, "党费管理",1100,800,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairPartyCost/list?treeId=${treeId}";
				}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairPartyCost/formDetail?id="+id;
			top.$.jBox.open(url, "党费管理",1100,620,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//提取党员名册数据
		function getPMDatas() {
			var month = $('[name="month"]').val();
			var year = $('[name="year"]').val();
			$.ajax({
				type:"post",
				url:'${ctx}/affair/affairPartyCost/getPMDatas?treeId=${treeId}',
				data:{month:month,year:year},
				dataType:"json",
				success:function(data){
					location.reload();
					$.jBox.tip(data.message);
				}
			})
		}
	</script>
	<style>
		.tooltip {
			position: relative;
		}

		.tooltip .tooltiptext {
			visibility: hidden;
			width: 260px;
			background-color: black;
			color: #fff;
			text-align: center;
			border-radius: 6px;
			padding: 5px 0;

			/* 定位 */
			position: absolute;
			z-index: 1;
		}

		.tooltip:hover .tooltiptext {
			visibility: visible;
		}
	</style>
</head>
<body>
	<form:form id="searchForm" modelAttribute="affairPartyCost" action="${ctx}/affair/affairPartyCost/list?treeId=${treeId}" method="post" class="breadcrumb form-search">
<%--		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>--%>
<%--		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>--%>
		<input id="fileName" name="fileName" type="hidden" value="党费管理表.xlsx"/>
		<ul class="ul-form">
			<li><label>党员姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>缴费年度：</label>
				<input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="${affairPartyCost.year}"
					onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});" />
			</li>
			<li><label>缴费月度：</label>
				<input name="month" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairPartyCost.month}"
					   onclick="WdatePicker({dateFmt:'M',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns tooltip " style="opacity: 1;"><input id="pmBtn" class="btn btn-primary" type="button" onclick="getPMDatas()" value="提取党员名册数据"/><span class="tooltiptext">点击该按钮，自动提取党员名册数据，请耐心等待</span></li>
			<shiro:hasPermission name="affair:affairPartyCost:edit">
				<%--<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()" value="添加"/></li>--%>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairPartyCost/deleteByIds?treeId=${treeId}&year=${affairPartyCost.year}&month=${affairPartyCost.month}','checkAll','myCheckBox')"/></li>
				<%--<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>--%>
			</shiro:hasPermission>
<%--			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>--%>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairPartyCost/list?treeId=${treeId}'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed" >
		<thead>
				<tr>
					<th colspan="14"  style="text-align: center"><span>党员交纳党费报告单</span></th>
				</tr>
				<tr>
					<th colspan="14"><span style="float: left">党支部（或党小组）：${treeName}</span><span style="float: right">交纳党费日期：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月</span></th>
				</tr>
				<tr>
					<th colspan="14"><span>实有党员数：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>交纳人数：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>欠交人数：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>免交人数：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>金额：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;元</span></th>
				</tr>
				<tr>
					<th>序号</th>
					<th>姓名</th>
					<th>本年度1月职务工资</th>
					<th>本年度1月级别工资</th>
					<th>上年度工作性津贴平均数 </th>
					<th>上年度个人所得税平均数</th>
					<th>本年度1月养老金</th>
					<th>本年度1月医保金</th>
					<th>本年度1月职业年金</th>
					<th>本年度1月公积金</th>
					<th>计算基数</th>
					<th>应缴纳比例</th>
					<th>月实交党费</th>
					<shiro:hasPermission name="affair:affairPartyCost:edit"><th id="handleTh">操作</th></shiro:hasPermission>
				</tr>
<%--			<tr>--%>
<%--				<th>--%>
<%--					<input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>--%>
<%--				</th>--%>
<%--				<th>序号</th>--%>
<%--				<th>党员姓名</th>--%>
<%--				<th>党员身份证号</th>--%>
<%--				<th>所在党组织</th>--%>
<%--				<th>党员类别</th>--%>
<%--				<th>缴费年度</th>--%>
<%--				<th>缴费基数</th>--%>
<%--				<th>交纳党费比例</th>--%>
<%--				<th>应交党费</th>--%>
<%--				<th>实交党费</th>--%>
<%--				<shiro:hasPermission name="affair:affairPartyCost:edit"><th id="handleTh">操作</th></shiro:hasPermission>--%>
<%--			</tr>--%>
		</thead>
		<tbody>
		<c:forEach items="${page}" var="affairPartyCost" varStatus="status" >
			<tr>
				<td>
						${status.index+1}
				</td>
				<td>
					${affairPartyCost.name}
				</td>
				<td>
					${affairPartyCost.zwMoney}元
				</td>
				<td>
					${affairPartyCost.jbMoney}元
				</td>
				<td>
						${affairPartyCost.gzjtMoney}元
				</td>
				<td>
					${affairPartyCost.sdsMoney}元
				</td>
				<td>
					${affairPartyCost.ylMoney}元
				</td>
				<td>
					${affairPartyCost.ybMoney}元
				</td>
				<td>
					${affairPartyCost.zyMoney}元
				</td>
				<td>
					${affairPartyCost.gjjMoney}元
				</td>
				<td>
						${affairPartyCost.jishu}元
				</td>
				<td>
						${affairPartyCost.bili}%
				</td>
				<td>
						${affairPartyCost.cost2}元
				</td>
				<td class="handleTd">
					<a onclick="openDetailDialog('${affairPartyCost.id}')">查看</a>
				<shiro:hasPermission name="affair:affairPartyCost:edit">
    				<a onclick="openAddEditDialog('${affairPartyCost.id}')">修改</a>
					<a href="${ctx}/affair/affairPartyCost/delete?id=${affairPartyCost.id}&treeId=${treeId}&year=${affairPartyCost.year}&month=${affairPartyCost.month}" onclick="return confirmx('确认要删除该党费管理吗？', this.href)">删除</a>
			</shiro:hasPermission>
				</td>
			</tr>

		</c:forEach>
		<tr>
			<th colspan="14"><span style="float: right;">合计${sumMoney}</span></th>
		</tr>
		<tr>
			<th colspan="14"><span style="float: left">经办人（签字）：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>党支部书记（签字）：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>旁签人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></th>
		</tr>
		<tr>
			<th colspan="14"><span>党委（签字）：</span></th>
		</tr>
		<tr>
			<th colspan="14">说明：本表一式三份，报单位党组织一份，财务部门一份，党支部保留一份</th>
		</tr>
		</tbody>
	</table>
<%--	<div class="pagination">${page}</div>--%>
</body>
</html>