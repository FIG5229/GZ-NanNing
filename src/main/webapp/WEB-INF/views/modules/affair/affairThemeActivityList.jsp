<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党内主题实践活动管理</title>
	<meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#print").click(function(){
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
					formValues: false
				});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function addthemeActivity() {
			$.post('${ctx}/affair/affairThemeActivity/form',{},function (res) {

			})
		};
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairThemeActivity/formDetail?id="+id;
			top.$.jBox.open(url, "党内主题实践活动",1100,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairThemeActivity/">查询</a></li>
        <shiro:hasPermission name="affair:affairThemeActivity:edit"><li><a href="${ctx}/affair/affairThemeActivity/manageList">管理</a></li></shiro:hasPermission>
		<%--<shiro:hasPermission name="affair:affairThemeActivity:edit"><li><a href="${ctx}/affair/affairThemeActivity/form">党内主题实践活动添加</a></li></shiro:hasPermission>--%>
	</ul>
	<%--该页面废弃页，使用manage.jsp--%>
	<%--该页面废弃页，使用manage.jsp--%>
	<%--该页面废弃页，使用manage.jsp--%>
	<%--该页面废弃页，使用manage.jsp--%>
	<%--该页面废弃页，使用manage.jsp--%>
	<form:form id="searchForm" modelAttribute="affairThemeActivity" action="${ctx}/affair/affairThemeActivity/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>活动名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>活动时间：</label>
				<input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairThemeActivity.startTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				 --
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairThemeActivity.endTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>活动类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_theme_activity')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>活动地点：</label>
				<form:input path="place" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>参加人员：</label>
				<form:input path="joinPerson" htmlEscape="false" class="input-medium"/>
			</li>
			<%--暂时不用--%>
			<%--<li><label>党组织名称：</label>
				<sys:treeselect id="partyOrganizationName" name="partyOrganizationId" value="${affairThemeActivity.partyOrganizationId}" labelName="partyOrganizationName" labelValue="${affairThemeActivity.partyOrganizationName}"
					title="党组织名称" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>--%>
			<li><label>举办单位：</label>
				<sys:treeselect id="holdUnitName" name="holdUnitId" value="${affairThemeActivity.holdUnitId}" labelName="holdUnitName" labelValue="${affairThemeActivity.holdUnitName}"
					title="举办单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairThemeActivity/'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>
					<input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>
				</th>
				<th>序号</th>
				<th>活动名称</th>
				<th>活动类型</th>
				<th>活动地点</th>
				<th>举办单位</th>
				<th>活动时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairThemeActivity" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairThemeActivity.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
                    <a onclick="openDetailDialog('${affairThemeActivity.id}')">
					    ${affairThemeActivity.name}
				    </a>
                </td>
				<td>
					${fns:getDictLabel(affairThemeActivity.type, 'affair_theme_activity', '')}
				</td>
				<td>
					${affairThemeActivity.place}
				</td>
				<td>
					${affairThemeActivity.holdUnitName}
				</td>
				<td>
					<fmt:formatDate value="${affairThemeActivity.startTime}" pattern="yyyy-MM-dd HH:mm"/> 至 <fmt:formatDate value="${affairThemeActivity.endTime}" pattern="yyyy-MM-dd HH:mm"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>