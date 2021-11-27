<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民警休养申报明细</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "民警休养申报",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairMjxyReport"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairMjxyReport/formDetail?id="+id;
			top.$.jBox.open(url, "民警休养详情",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="affairMjxyReport" action="${ctx}/affair/affairMjxyReport/mingXi" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="unitId" name="unitId" type="hidden" value="${affairMjxyReport.unitId}"/>
		<input id="type" name="type" type="hidden" value="${affairMjxyReport.type}"/>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>单位</th>
				<th>休养类型</th>
				<th>职务</th>
				<th>身份证号</th>
				<th>休养开始时间</th>
				<th>休养结束时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairMjxyReport" varStatus="status">
			<tr>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<a href="#" onclick="openDetailDialog('${affairMjxyReport.id}')">
						${affairMjxyReport.name}
					</a>
				</td>
				<td>
						${fns:getDictLabel(affairMjxyReport.sex, 'sex', '')}
				</td>
				<td>
						${affairMjxyReport.unit}
				</td>
				<td>
						${fns:getDictLabel(affairMjxyReport.type, 'affair_xiuyang', '')}
				</td>
				<td>
						${fns:getDictLabel(affairMjxyReport.job, 'affair_job', '')}
				</td>
				<td>
						${affairMjxyReport.idNumber}
				</td>
				<td>
					<fmt:formatDate value="${affairMjxyReport.startDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairMjxyReport.endDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>