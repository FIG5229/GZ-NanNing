<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>培训班学习统计管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairTrainingStatistics/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTrainingStatistics/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairTrainingStatistics/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTrainingStatistics/");
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
						$('.handleTd').css('display', 'table-cell');
						$('#headTitle').css('display', 'none');

					}
				});
			});
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairTrainingStatistics", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairTrainingStatistics"}});
			});
		});

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairTrainingStatistics/formDetail?id="+id;
			top.$.jBox.open(url, "",1000,400,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};

		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "",1000,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairTrainingStatistics"}
			});
		}


		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="affairTrainingStatistics" action="${ctx}/affair/affairTrainingStatistics/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="培训班学习统计表.xlsx"/>
		<ul class="ul-form">
			<li><label>培训班编号：</label>
				<form:input path="number" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>培训班名称：</label>
				<form:input path="className" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>培训方式：</label>
				<form:input path="trainingMethod" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>结项状态：</label>
				<form:input path="endState" htmlEscape="false" class="input-medium"/>
			</li>

			<li>
				<label>培训日期：</label>
				<input name="beginTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairTrainingStatistics.beginTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairTrainingStatistics.endTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairTrainingStatistics/form')"/></li>
			<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairTrainingStatistics/deleteByIds','checkAll','myCheckBox')"/></li>
			<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairTrainingStatistics'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >南宁铁路公安处培训班学习统计报表</td>
			</tr>
		</table>
		<table  class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>培训班编号</th>
				<th>培训班名称</th>
				<th>主办部门</th>
				<th>培训方式</th>
				<th>开始日期</th>
				<th>结束日期</th>
				<th>课程总数</th>
				<th>开班状态</th>
				<th>结项状态</th>
				<th>评估分数</th>
				<th>应参训人数</th>
				<th>实际参训人数</th>
				<th>参训率</th>
				<th>已通过人数</th>
				<th>未通过人数</th>
				<th>通过率</th>
				<th>师资费(万元)</th>
				<th>住宿费(万元)</th>
				<th>伙食费(万元)</th>
				<th>场地资料交通费(万元)</th>
				<th>其他费用(万元)</th>
				<th>费用总额(万元)</th>
				<th>学习总时长(小时)</th>
				<th>平均学习时长(小时)</th>
				<th>学习总次数</th>
				<th>平均学习次数</th>
				<shiro:hasPermission name="affair:affairTrainingStatistics:edit"><th class="handleTd">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTrainingStatistics" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTrainingStatistics.id}"/>
				</td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td>${affairTrainingStatistics.number}</td>
				<td>${affairTrainingStatistics.className}</td>
				<td>${affairTrainingStatistics.unit}</td>
				<td>${affairTrainingStatistics.trainingMethod}</td>
				<td><fmt:formatDate value="${affairTrainingStatistics.beginTime}" pattern="yyyy-MM-dd "/></td>
				<td><fmt:formatDate value="${affairTrainingStatistics.endTime}" pattern="yyyy-MM-dd "/></td>
				<td>${affairTrainingStatistics.classSum}</td>
                <td>${affairTrainingStatistics.beginState}</td>
                <td>${affairTrainingStatistics.endState}</td>
                <td>${affairTrainingStatistics.evaluationScore}</td>
                <td>${affairTrainingStatistics.shouldJoin}</td>
                <td>${affairTrainingStatistics.trueJoin}</td>
                <td>${affairTrainingStatistics.participationNumber}</td>
                <td>${affairTrainingStatistics.passedNumber}</td>
                <td>${affairTrainingStatistics.notPassedNumber}</td>
                <td>${affairTrainingStatistics.passRatio}</td>
                <td>${affairTrainingStatistics.teacherCost}</td>
                <td>${affairTrainingStatistics.accommodationCost}</td>
                <td>${affairTrainingStatistics.foodCost}</td>
                <td>${affairTrainingStatistics.siteCost}</td>
                <td>${affairTrainingStatistics.otherCost}</td>
                <td>${affairTrainingStatistics.costSum}</td>
                <td>${affairTrainingStatistics.learnTimeSum}</td>
                <td>${affairTrainingStatistics.learnTimeAvg}</td>
                <td>${affairTrainingStatistics.countSum}</td>
                <td>${affairTrainingStatistics.countAvg}</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairTrainingStatistics.id}')">查看</a>
					<shiro:hasPermission name="affair:affairTrainingStatistics:edit">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTrainingStatistics/form?id=${affairTrainingStatistics.id}')">修改</a>
						<a href="${ctx}/affair/affairTrainingStatistics/delete?id=${affairTrainingStatistics.id}" onclick="return confirmx('确认要删除该培训班学习统计吗？', this.href)">删除</a>
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