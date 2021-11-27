<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党组织概况管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//导出
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairGeneralSituation/export?treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairGeneralSituation/export?flag=true&treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairGeneralSituation", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		};
		//添加修改弹窗
		function openAddEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairGeneralSituation/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairGeneralSituation/form";
			}
			top.$.jBox.open(url, "党组织概况",1000,700,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}";
				}
			});
		};
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairGeneralSituation/formDetail?id="+id;
			top.$.jBox.open(url, "党组织概况",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};

		//新建党小组
		//此处treeId为新建的党小组的父Id，点击党小组后根据父Id替换党组织，并且根据父Id查询数据
		function addPoliticalGroup(treeId) {
			top.$.jBox.open("iframe:${ctx}/affair/affairPoliticalGroup/form?parentId="+treeId, "党小组添加",800,500,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairGeneralSituation/list?treeId=${treeId}";}
			});
		};
		if("sucess"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
	<style>
		.form-search .ul-form li label {
			width: 104px;
			text-align: right;
		}
	</style>
</head>
<body>

<form:form id="searchForm" modelAttribute="affairGeneralSituation" action="${ctx}/affair/affairGeneralSituation/assessDoneDetail" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<input id="year" name="year" type="hidden" value="${affairGeneralSituation.year}"/>
	<input id="month" name="month" type="hidden" value="${affairGeneralSituation.month}"/>
	<input id="dateType" name="dateType" type="hidden" value="${affairGeneralSituation.dateType}"/>
	<input id="label" name="label" type="hidden" value="${affairGeneralSituation.label}"/>
	<input style="width: 140px" name="startDate" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
		   value="<fmt:formatDate value="${affairGeneralSituation.startDate}" pattern="yyyy-MM-dd"/>"
		   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
	<input style="width: 140px" name="endDate" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
		   value="<fmt:formatDate value="${affairGeneralSituation.endDate}" pattern="yyyy-MM-dd"/>"
		   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>

		<th>序号</th>
		<th>党组织名称</th>
		<%--<th>所属党组织</th>--%>
		<th>党组织成立时间</th>
		<th>所在单位</th>
		<th>党组织党员数</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="affairGeneralSituation" varStatus="status">
		<tr>

			<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
			</td>
			<td>
						${affairGeneralSituation.partyOrganization}
			</td>
				<%--	<td>
                        ${affairGeneralSituation.ofPartyOrganization}
                    </td>--%>
			<td>
				<fmt:formatDate value="${affairGeneralSituation.foundDate}" pattern="yyyy-MM-dd"/>
			</td>
			<td>
					${affairGeneralSituation.unit}
			</td>
			<td>
					${affairGeneralSituation.num}
			</td>

		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>