<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>绩效等次管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/export.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'export') {
								var isChuLd = $("#isChuLD").val();
								if(isChuLd == 'isChuLD'){
									$("#searchForm").attr("action","${ctx}/exam/examWorkflow/chuLDExamExport?workflowId=${workflowId}");
								}else{
									$("#searchForm").attr("action","${ctx}/exam/examWorkflow/policemanExamExport?workflowId=${workflowId}");
								}
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/exam/examLdScoreMonth/exam");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出': 'export', '取消':'cancel'} });
					}
			);
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

		function openGradesDialog(id) {
			top.$.jBox.open("iframe:${ctx}/exam/examLdScore/judgeGradesById?id="+id,"绩效评定",1200,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				}/*,closed:function () {self.location.href="${ctx}/exam/examLdScoreMonth"

				}*/
			});
		}
		function exportData() {
			dataExport("contentTable");
		}
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/exam/examLdScore/">绩效等次列表</a></li>
		<shiro:hasPermission name="exam:examLdScore:edit"><li><a href="${ctx}/exam/examLdScore/form">绩效等次添加</a></li></shiro:hasPermission>
	</ul>--%>
	<form:form id="searchForm" modelAttribute="examLdScore" action="${ctx}/exam/examLdScore/exam" method="post" class="breadcrumb form-search"  cssStyle="text-align: right">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="workflowId" name="workflowId" type="hidden" value="${workflowId}"/>
		<input id="isChuLD" name="isChuLD" type="hidden" value="${isChuLD}"/>

		<%--<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>最终得分：</label>
				<form:input path="sumScore" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>--%>
		<input id="export" class="btn btn-primary" type="button" value="导出"/>
		<input id="back" class="btn" type="button" value="返回"  onclick="history.go(-1)"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<caption>
			<h3>${workflowName}</h3>
		</caption>
		<thead>
			<tr>
				<th>序号</th>
				<th>姓名</th>
				<th>最终得分</th>
				<%--<shiro:hasPermission name="exam:examLdScore:edit"><th>操作</th></shiro:hasPermission>--%>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examLdScore" varStatus="status">
			<tr>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${examLdScore.name}
				</td>
				<td>
					${examLdScore.sumScore}
				</td>
				<td>
				<a href="javascript:;" onclick="openGradesDialog('${examLdScore.id}')">评定</a>
<%--				<shiro:hasPermission name="exam:examLdScore:edit"><td>--%>
<%--    				<a href="${ctx}/exam/examLdScore/form?id=${examLdScore.id}">修改</a>--%>
<%--					<a href="${ctx}/exam/examLdScore/delete?id=${examLdScore.id}" onclick="return confirmx('确认要删除该绩效等次吗？', this.href)">删除</a>--%>
<%--				</shiro:hasPermission>--%>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
<%--	<div class="pagination">${page}</div>--%>
</body>
</html>