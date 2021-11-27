<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript"  src="${ctxStatic}/js/export.js"></script>
<html>
<head>
	<title>月度领导考核评分管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'export') {
								var isChuLd = $("#isChuLD").val();
								console.log(isChuLd == 'isChuLD')
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
            top.$.jBox.open("iframe:${ctx}/exam/examLdScoreMonth/judgeGradesByIdBeta?id="+id,"绩效评定",1200,600,{
                buttons:{"关闭":true},
                loaded:function () {
                    $(".jbox-content",top.document).css("overflow-y","hidden");
                },closed:function () {self.location.href="${ctx}/exam/examWorkflowDatas/examBeta?examWorkflowId=${workflowId}&examType=${examType}&history=${history}"

                }
            });
        }
		function exportData() {
			dataExport("contentTable");
		}
	</script>
</head>
<body>
<%--<%@include file="/WEB-INF/views/modules/exam/step.jsp" %>--%>
<%--<%@include file="/WEB-INF/views/modules/exam/step.jsp" %>--%>
	<%--<ul class="nav nav-tabs">
		<li><a href="${ctx}/exam/examLdScore/">年度领导等次列表</a></li>
		<li class="active"><a href="${ctx}/exam/examLdScoreMonth">月度领导等次列表</a></li>
	</ul>--%>
	<form:form id="searchForm" modelAttribute="examLdScoreMonth" action="${ctx}/exam/examLdScoreMonth/exam" method="post" class="breadcrumb form-search"  cssStyle="text-align: right">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="workflowId" name="workflowId" type="hidden" value="${workflowId}"/>
		<input id="isChuLD" name="isChuLD"  type="hidden" value="${isChuLD}"/>
<%--		<ul class="ul-form">--%>
			<%--<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${examLdScoreMonth.unitId}" labelName="unit" labelValue="${examLdScoreMonth.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>--%>
<%--			<li><label>职务：</label>--%>
<%--				<form:input path="job" htmlEscape="false" class="input-medium"/>--%>
<%--			</li>--%>
<%--			<li><label>姓名：</label>--%>
<%--				<form:input path="name" htmlEscape="false" class="input-medium"/>--%>
<%--			</li>--%>
<%--			<li><label>等级：</label>--%>
<%--				<form:input path="grades" htmlEscape="false" class="input-medium"/>--%>
<%--			</li>--%>
<%--		</ul>--%>
		<%--<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnGrades" class="btn btn-primary" type="button" value="评定" onclick="window.location.href='${ctx}/exam/examLdScoreMonth/judgeGrades'" /></li>
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
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>单位</th>
				<th>姓名</th>
				<th>职务</th>
				<th>分数</th>
				<th>事项</th>
				<th>等级</th>
				<shiro:hasPermission name="exam:examLdScoreMonth:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examLdScoreMonth" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${examLdScoreMonth}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${examLdScoreMonth.unit}
				</td>
				<td>
					${examLdScoreMonth.name}
				</td>
				<td>
					${examLdScoreMonth.job}
				</td>
				<td>
					<a href="${ctx}/exam/examWorkflowDatas/examBeta?examWorkflowId=${workflowId}&objId=${examLdScoreMonth.personId}&publicDetail=true&fillPersonId=${examLdScoreMonth.personId}">${examLdScoreMonth.score}</a>
				</td>
				<td>
					${examLdScoreMonth.matter}
				</td>
				<td>
						${fns:getDictLabel(examLdScoreMonth.grades, 'performance_grade', '')}
				</td>
				<shiro:hasPermission name="exam:examLdScoreMonth:edit"><td>
<%--    				<a href="${ctx}/exam/examLdScoreMonth/form?id=${examLdScoreMonth.id}">修改</a>--%>
<!--    				<a href="javascript:;" onclick="openGradesDialog('${examLdScoreMonth.id}')">评定</a>-->
<%--					<a href="${ctx}/exam/examLdScoreMonth/delete?id=${examLdScoreMonth.id}" onclick="return confirmx('确认要删除该月度领导考核评分吗？', this.href)">删除</a>--%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
<%--	<div class="pagination">${page}</div>--%>
</body>
</html>