<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>场地管理管理</title>
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
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairSitemAnagement/formDetailTwo?id="+id;
			top.$.jBox.open(url, "使用记录详情",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};


	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairSitemAnagement/">场地管理</a></li>
		<shiro:hasPermission name="affair:affairSitemAnagement:edit"><li><a href="${ctx}/affair/affairSitemAnagement/form">场地管理添加</a></li></shiro:hasPermission>
	</ul>--%>
<%--	<li class="active"><a href="${ctx}/affair/affairSitemAnagement/findAllaffairUsageRecordlist">使用记录</a></li>--%>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/></th>
				<th>序号</th>
				<th>场所</th>
				<th>培训项目</th>
				<th>培训层次</th>
				<th>班主任</th>
				<th>开始使用时间</th>
				<th>结束使用时间</th>
				<th>人均消费(吃)</th>
				<th>人均消费(住)</th>
				<th>场地消费</th>
				<th>使用评价</th>
				<th>联系人</th>
				<th>联系电话</th>
				<shiro:hasPermission name="affair:affairSitemAnagement:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${UsageRecordPage.list}" var="affairClassManage" varStatus="status">
			<tr>
				<td  class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairClassManage.id}"/>
				</td>
				<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>

					${affairClassManage.unit}
				</td>
				<td>
					${affairClassManage.name}
				</td>
				<td>
					${fns:getDictLabel(affairClassManage.level, 'affair_train_level','')}
				</td>
				<td>
					${affairClassManage.teacher}
				</td>
				<td>
					<fmt:formatDate value="${affairClassManage.beganDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${affairClassManage.resultDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${affairClassManage.boardWages}
				</td>
				<td>
					${affairClassManage.accommodationFees}
				</td>
				<td>
					${affairClassManage.siteFees}
				</td>
				<td>
					${affairClassManage.score}
				</td>
				<td>
					${affairClassManage.creator}
				</td>
				<td>
					${affairClassManage.phone}
				</td>
				<td>
					<a onclick="openDetailDialog('${affairClassManage.id}')" style="cursor: pointer">查看</a>
				</td>
			</tr>
		</c:forEach>

		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>