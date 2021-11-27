<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党组织换届选举管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("[data-toggle='popover']").popover();
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
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		$('#notPass').popover();
		//添加修改弹窗
		function openAddEditDialog(id,flag) {
			var url = "iframe:${ctx}/affair/affairElection/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairElection/form";
			}
			top.$.jBox.open(url, "党组织换届选举",900,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href = "${ctx}/affair/affairElection/list?treeId=${treeId}";
				}
			});
		};
		//批量提交
		function submitByIds() {
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				console.log($(this).val())
				ids.push($(this).val());
			});
			if (ids.length > 0) {
				$.ajax({
					type:"post",
					url:"${ctx}/affair/affairElection/submitByIds",
					data:{ids:ids},
					dataType:"json",
					success:function(data){
						$.jBox.tip(data.message);
						resetCheckBox("checkAll","myCheckBox");
						location.reload();
					}
				})
			} else {
				$.jBox.tip('请先选择要提交的内容');
			}
		};
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairElection/formDetail?id="+id;
			top.$.jBox.open(url, "党组织换届选举",1000,350,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
	</script>
</head>
<body>

	<form:form id="searchForm" modelAttribute="affairElection"
			   action="${ctx}/affair/affairElection/electionDetail" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="year" name="year" type="hidden" value="${affairElection.year}"/>
		<input id="month" name="month" type="hidden" value="${affairElection.month}"/>
		<input id="dateType" name="dateType" type="hidden" value="${affairElection.dateType}"/>
		<input id="label" name="label" type="hidden" value="${affairElection.label}"/>
		<input name="jmStartDate" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
		value="<fmt:formatDate value="${affairElection.jmStartDate}" pattern="yyyy-MM-dd"/>"
		onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		<input name="jmEndDate" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
			   value="<fmt:formatDate value="${affairElection.jmEndDate}" pattern="yyyy-MM-dd"/>"
			   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>

				<th>序号</th>
				<th>党组织名称</th>
				<th>届次</th>
				<th>选举方式</th>
				<th>该届届满时间</th>
				<th>该届换届时间</th>
				<th>应到会人数</th>
				<th>实到会人数</th>
				<th>批准委员会名额</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairElection" varStatus="status">
			<tr>

				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>

						${affairElection.partyOrganizationName}

				</td>
				<td>
					${affairElection.jc}
				</td>
				<td>
					${fns:getDictLabel(affairElection.method, 'affair_xuanju_method', '')}
				</td>
				<td>
					<fmt:formatDate value="${affairElection.jmDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairElection.hjDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairElection.ydhNum}
				</td>
				<td>
					${affairElection.sdhNum}
				</td>
				<td>
					${affairElection.quota}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>