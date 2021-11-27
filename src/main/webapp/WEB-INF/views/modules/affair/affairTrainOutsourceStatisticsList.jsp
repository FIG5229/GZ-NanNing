<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>委外培训统计管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairTrainOutsourceStatistics/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTrainOutsourceStatistics/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all','取消':'cancel'} });
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairTrainOutsourceStatistics", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairTrainOutsourceStatistics"}});
			});
		});

		function openEditDialog(idNumber) {
			top.$.jBox.open("iframe:${ctx}/affair/affairTrainOutsourceStatistics/deputy?idNumber=" + idNumber, "", 1200, 600, {
				buttons: {"关闭": true},
				loaded: function () {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}, closed: function () {
					self.location.href = "${ctx}/affair/affairTrainOutsourceStatistics"
				}
			});
		}

		//添加，修改弹窗
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "委外培训统计",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairTrainOutsourceStatistics"}
			});
		}


		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairTrainOutsourceStatistics/formDetail?id="+id;
			top.$.jBox.open(url, "委外培训统计",1000,400,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};

		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="affairTrainOutsourceStatistics" action="${ctx}/affair/affairTrainOutsourceStatistics/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="委外培训统计表.xlsx"/>
		<ul class="ul-form">
			<li><label>机构：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairTrainOutsourceStatistics.unitId}" labelName="unit" labelValue="${affairTrainOutsourceStatistics.unit}"
								title="机构" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairTrainOutsourceStatistics:view">
				<li class="btns"><input  class="btn btn-primary" type="button" value="添加" onclick="openDialog('${ctx}/affair/affairTrainOutsourceStatistics/form?id=${affairTrainOutsourceStatistics.id}')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairTrainOutsourceStatistics:edit">
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairTrainOutsourceStatistics/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print"  class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairTrainOutsourceStatistics'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >南宁铁路公安处委外培训统计管理报表</td>
			</tr>
		</table>
		<table  class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th rowspan="4"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th rowspan="4">序号</th>
				<th rowspan="4">单位</th>
				<th rowspan="4">人数</th>
				<th colspan="3">培训形式</th>
				<th colspan="4">主办单位级别</th>
			</tr>
			<tr>
				<th rowspan="3">全脱产</th>
				<th rowspan="3">半脱产</th>
				<th rowspan="3">不脱产</th>
			</tr>
			<tr>
				<th rowspan="3">公安部</th>
				<th rowspan="3">部局（省）</th>
				<th rowspan="3">公安局（市）</th>
				<th rowspan="3">公安处（县）</th>
			</tr>
			<tr class="handleTd">
				<th rowspan="4">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTrainOutsourceStatistics" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTrainOutsourceStatistics.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					 ${affairTrainOutsourceStatistics.unit}
				</td>
				<td>
					 ${affairTrainOutsourceStatistics.peopleSum}
				</td>
				<td>
					 ${affairTrainOutsourceStatistics.offJob}
				</td>
				<td>
					 ${affairTrainOutsourceStatistics.halfJob}
				</td>
				<td>
					 ${affairTrainOutsourceStatistics.notJob}
				</td>
				<td>
					 ${affairTrainOutsourceStatistics.hq}
				</td>
				<td>
					 ${affairTrainOutsourceStatistics.provincial}
				</td>
				<td>
					 ${affairTrainOutsourceStatistics.city}
				</td>
				<td>
					 ${affairTrainOutsourceStatistics.county}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairTrainOutsourceStatistics.id}')">查看</a>
					<shiro:hasPermission name="affair:affairTrainOutsourceStatistics:edit">
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairTrainOutsourceStatistics/form?id=${affairTrainOutsourceStatistics.id}')">修改</a>
						<a href="${ctx}/affair/affairTrainOutsourceStatistics/delete?id=${affairTrainOutsourceStatistics.id}" onclick="return confirmx('确认要删除吗？', this.href)">删除</a>
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