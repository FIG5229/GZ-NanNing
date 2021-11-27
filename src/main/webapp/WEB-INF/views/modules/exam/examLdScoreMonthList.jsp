<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<html>
<head>
	<title>月度领导考核评分管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

        function openGradesDialog(id) {
            top.$.jBox.open("iframe:${ctx}/exam/examLdScoreMonth/judgeGradesById?id="+id,"绩效评定",1200,600,{
                buttons:{"关闭":true},
                loaded:function () {
                    $(".jbox-content",top.document).css("overflow-y","hidden");
                },closed:function () {self.location.href="${ctx}/exam/examLdScoreMonth"

                }
            });
        }
	</script>
</head>
<body>
<%--<%@include file="/WEB-INF/views/modules/exam/step.jsp" %>--%>
<%--<%@include file="/WEB-INF/views/modules/exam/step.jsp" %>--%>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/exam/examLdScore/">年度领导等次列表</a></li>
		<li class="active"><a href="${ctx}/exam/examLdScoreMonth">月度领导等次列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="examLdScoreMonth" action="${ctx}/exam/examLdScoreMonth/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${examLdScoreMonth.unitId}" labelName="unit" labelValue="${examLdScoreMonth.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
<%--			<li><label>职务：</label>--%>
<%--				<form:input path="job" htmlEscape="false" class="input-medium"/>--%>
<%--			</li>--%>
<%--			<li><label>姓名：</label>--%>
<%--				<form:input path="name" htmlEscape="false" class="input-medium"/>--%>
<%--			</li>--%>
<%--			<li><label>等级：</label>--%>
<%--				<form:input path="grades" htmlEscape="false" class="input-medium"/>--%>
<%--			</li>--%>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnGrades" class="btn btn-primary" type="button" value="评定" onclick="window.location.href='${ctx}/exam/examLdScoreMonth/judgeGrades'" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>单位</th>
				<th>职务</th>
				<th>姓名</th>
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
					${examLdScoreMonth.job}
				</td>
				<td>
					${examLdScoreMonth.name}
				</td>
				<td>
					<a href="${ctx}/exam/examWorkflowDatas/examBeta?examWorkflowId=${workflowId}&objId=${examLdScoreMonth.unitId}&publicDetail=true">${examLdScoreMonth.score}</a>
				</td>
				<td>
					${examLdScoreMonth.matter}
				</td>
				<td>
						${fns:getDictLabel(examLdScoreMonth.grades, 'performance_grade', '')}
				</td>
				<shiro:hasPermission name="exam:examLdScoreMonth:edit"><td>
    				<a href="${ctx}/exam/examLdScoreMonth/form?id=${examLdScoreMonth.id}">修改</a>
    				<a href="javascript:;" onclick="openGradesDialog('${examLdScoreMonth.id}')">评定</a>
					<a href="${ctx}/exam/examLdScoreMonth/delete?id=${examLdScoreMonth.id}" onclick="return confirmx('确认要删除该月度领导考核评分吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>