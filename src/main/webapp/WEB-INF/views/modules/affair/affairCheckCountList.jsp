<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>档案管理统计台账管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairCheckCount/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairCheckCount/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairCheckCount/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairGz/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairCheckCount", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairCheckCount"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="affairCheckCount" action="${ctx}/affair/affairCheckCount/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="档案管理统计台帐.xlsx"/>
		<ul class="ul-form">
			<shiro:hasPermission name="affair:affairCheckCount:edit">
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th colspan="16" style="text-align: center">档案台账管理</th>
			</tr>
			<tr>
				<th></th>
				<th colspan="9" style="text-align: center">本级管理</th>
				<th colspan="5" style="text-align: center">上级管理</th>
				<th rowspan="2" style="text-align: center">备注</th>
			</tr>
			<tr>
				<th></th>
				<th>副局</th>
				<th>正处</th>
				<th>副处</th>
				<th>正科</th>
				<th>副科</th>
				<th>科员</th>
				<th>办事员</th>
				<th>见习</th>
				<th>小计</th>
				<th>副局</th>
				<th>正处</th>
				<th>副处</th>
				<th>正科</th>
				<th>小计</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairCheckCount">
			<tr>
				<td>${affairCheckCount.title}</td>
				<td>
					${affairCheckCount.bjFuju}
				</a></td>
				<td>
					${affairCheckCount.bjZhengchu}
				</td>
				<td>
					${affairCheckCount.bjFuchu}
				</td>
				<td>
					${affairCheckCount.bjZhengke}
				</td>
				<td>
					${affairCheckCount.bjFuke}
				</td>
				<td>
					${affairCheckCount.bjKeyuan}
				</td>
				<td>
					${affairCheckCount.bjBanshiyuan}
				</td>
				<td>
					${affairCheckCount.bjJianxi}
				</td>
				<td>
					${affairCheckCount.bjXiaoji}
				</td>
				<td>
					${affairCheckCount.sjFuju}
				</td>
				<td>
					${affairCheckCount.sjZhengchu}
				</td>
				<td>
					${affairCheckCount.sjFuchu}
				</td>
				<td>
					${affairCheckCount.sjZhengke}
				</td>
				<td>
					${affairCheckCount.sjXiaoji}
				</td>
				<td>
					${affairCheckCount.remark}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>