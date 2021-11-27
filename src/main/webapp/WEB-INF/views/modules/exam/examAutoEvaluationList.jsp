<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>自动考评管理</title>
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
			var url = "iframe:${ctx}/exam/examAutoEvaluation/formDetail?id=" + id;
			top.$.jBox.open(url, "详情", 1000, 500, {
				buttons: {"关闭": true},
				loaded: function (h) {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<c:choose>
			<c:when test="${mType==null}">
				<li><a href="${ctx}/exam/examCheck/">检查情况</a></li>
				<li><a href="${ctx}/exam/examCheckChild/">问题整改</a></li>
<%--				<shiro:hasPermission name="exam:examAutoEvaluation:view">--%>
					<li class="active"><a href="${ctx}/exam/examAutoEvaluation/">推送管理</a></li>
<%--				</shiro:hasPermission>--%>
				<li ><a href="${ctx}/exam/examOtherData/">其他数据</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="${ctx}/exam/examCheck?mType=1">检查情况</a></li>
				<shiro:hasPermission name="exam:examCheck:edit">
					<li ><a href="${ctx}/exam/examCheck/">检查情况管理</a></li>
				</shiro:hasPermission>
				<li><a href="${ctx}/exam/examCheckChild/">问题整改</a></li>
				<shiro:hasPermission name="exam:examCheck:edit">
					<li ><a href="${ctx}/exam/examAutoEvaluation/">推送管理</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="exam:examAutoEvaluation:view">
					<li class="active" ><a href="${ctx}/exam/examAutoEvaluation/">推送管理</a></li>
				</shiro:hasPermission>
				<li><a href="${ctx}/exam/examOtherData/">其他数据</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
	<form:form id="searchForm" modelAttribute="examAutoEvaluation" action="${ctx}/exam/examAutoEvaluation/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>来源：</label>
				<form:select path="flagType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('from_sys')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>周期：</label>
				<form:select path="period" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('exam_cycle')}" itemLabel="label" itemValue="value"
								  htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>类别：</label>
				<form:select path="evalType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('kp_type')}" itemLabel="label" itemValue="value"
								  htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>年份：</label>
				<%--<form:input path="year" htmlEscape="false" class="input-medium"/>--%>
				<input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="${examAutoEvaluation.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:true});"/>
			</li>
			<li><label>月份：</label>
			<%--<form:input path="month" htmlEscape="false" class="input-medium"/>--%>
				<input name="month" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="${examAutoEvaluation.month}"
					   onclick="WdatePicker({dateFmt:'MM',isShowClear:true});"/>
			</li>
			<li><label>被考评对象：</label>
				<sys:treeselect id="evaluationId" name="evaluationId" value="${examAutoEvaluation.evaluationId}" labelName="evaluation" labelValue="${examAutoEvaluation.evaluation}"
								title="人员" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>

			<li><label>详情：</label>
				<form:input path="details" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/exam/examAutoEvaluation'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
<%--				<th id="handleTh"><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>--%>
				<th>序号</th>
				<c:if test="${examAutoEvaluation.evalType == '5' || examAutoEvaluation.evalType == '6' || examAutoEvaluation.evalType == '7'}">
					<th>姓名</th>
				</c:if>
				<th>单位</th>
				<th>年份</th>
				<c:if test="${examAutoEvaluation.period == '1'}">
					<th>月份</th>
				</c:if>
				<th>模板</th>
				<th>考评标准</th>
				<th>详情</th>
				<th>被考评对象</th>
				<th>来源</th>
				<th>操作</th>
<%--				<shiro:hasPermission name="exam:examAutoEvaluation:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examAutoEvaluation" varStatus="status">
			<tr>
				<%--<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${examAutoEvaluation.id}"/>
				</td>--%>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<c:if test="${examAutoEvaluation.evalType == '5' ||examAutoEvaluation.evalType == '6' || examAutoEvaluation.evalType == '7'}">
					<td>
						${examAutoEvaluation.name}
					</td>
				</c:if>
                <td>
                        ${examAutoEvaluation.unit}
                </td>
				<td>
						${examAutoEvaluation.year}
				</td>
				<c:if test="${examAutoEvaluation.period == '1'}">
					<td>
							${examAutoEvaluation.month}
					</td>
				</c:if>
				<td>
						${examAutoEvaluation.model}
				</td>
				<td>
						${examAutoEvaluation.option}
				</td>
				<td>
						${examAutoEvaluation.details}
				</td>
				<td>
						${fns:getUserById(examAutoEvaluation.evaluationId).name}
				</td>
				<td>
						${examAutoEvaluation.fromSys}
				</td>
				<td>
					<a href="javascript:void(0)" onclick="openDetailDialog('${examAutoEvaluation.id}')" style="cursor: pointer">查看</a>
				</td>
				<%--<shiro:hasPermission name="exam:examAutoEvaluation:edit"><td>
    				<a href="${ctx}/exam/examAutoEvaluation/form?id=${examAutoEvaluation.id}">修改</a>
					<a href="${ctx}/exam/examAutoEvaluation/delete?id=${examAutoEvaluation.id}" onclick="return confirmx('确认要删除该自动考评吗？', this.href)">删除</a>
				</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>