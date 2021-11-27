<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活动情况管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {


			$("#btnPrint").click(function(){
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$('#checkTh').css('display', 'none');
				$('.checkTd').css('display', 'none');
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
						$('#checkTh').css('display', 'table-cell');
						$('.checkTd').css('display', 'table-cell');
					}
				});
			});

			$("#btnExport").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairActive/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairActive/list?treeId");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairActive/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairActive/list");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairActive", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairActive/list"}});
			});

		});


		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairActive/formDetail?id="+id;
			top.$.jBox.open(url, "",900,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}

		//添加，修改弹窗
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, " ",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairActive"}
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
	<form:form id="searchForm" modelAttribute="affairActive" action="${ctx}/affair/affairActive/readBookDetail" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="year" name="year" type="hidden" value="${affairActive.year}"/>
		<input id="month" name="month" type="hidden" value="${affairActive.month}"/>
		<input id="dateType" name="dateType" type="hidden" value="${affairActive.dateType}"/>
		<input id="label" name="label" type="hidden" value="${affairActive.label}"/>
		<input name="beginTime" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
			   value="<fmt:formatDate value="${affairActive.beginTime}" pattern="yyyy-MM-dd"/>"
			   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		<input name="endTime" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
			   value="<fmt:formatDate value="${affairActive.endTime}" pattern="yyyy-MM-dd"/>"
			   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>单位</th>
				<th>活动开始时间</th>
				<th>活动结束时间</th>
				<th>活动地点</th>
				<th>参加人员</th>
				<th>简要情况</th>
				<th>活动内容</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairActive" varStatus="status">
			<tr>

				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairActive.unit}
				</td>
				<td>
					<fmt:formatDate value="${affairActive.beginTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairActive.endTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${affairActive.place}
				</td>
				<td>
						${affairActive.people}
				</td>
				<td>
						${affairActive.description}
				</td>
				<td>
						${affairActive.content}
				</td>

			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>