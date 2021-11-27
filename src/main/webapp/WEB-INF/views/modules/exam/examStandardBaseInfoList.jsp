<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考评标准管理管理</title>
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
		function openForm(url) {
			var title = "考评标准添加";
			if(url.indexOf('id')!=-1){
				title = "考评标准修改";
			}
			top.$.jBox.open("iframe:"+url, title,800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					$("#searchForm").submit();
					<%--self.location.href="${ctx}/exam/examStandardBaseInfo/"--%>
				}
			});
		}
	</script>
</head>
<body>
<!--
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/exam/examStandardBaseInfo/">考评标准管理列表</a></li>
		<shiro:hasPermission name="exam:examStandardBaseInfo:edit"><li><a href="${ctx}/exam/examStandardBaseInfo/form">考评标准管理添加</a></li></shiro:hasPermission>
	</ul>-->

	<form:form id="searchForm" modelAttribute="examStandardBaseInfo" action="${ctx}/exam/examStandardBaseInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label class="width120">考评标准名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>

			<li><label class="width120">考评标准简称：</label>
				<form:input path="abbreviation" htmlEscape="false" class="input-medium"/>
			</li>

			<li><label class="width120">被考评对象类别：</label>
				<form:select path="objType" class="input-medium">
					<form:option value="" label=""/>
					<form:option value="" label=""/>

					<form:options items="${fns:getDictList('obj_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>考评类别：</label>
				<form:select path="kpType" class="input-medium">
					<form:option value="" label=""/>
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('kp_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>考评周期：</label>
				<form:select path="cycle" class="input-medium">
					<form:option value="" label=""/>
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('cycle')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>模板类型：</label>
				<form:select path="modelType" class="input-medium ">
					<form:option value="" label=""/>
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('model_types')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label class="control-label">创建人：</label>
					<sys:treeselect id="create" name="createBy.id" value="${examStandardBaseInfo.createBy.id}" labelName="createBy.name" labelValue="${examStandardBaseInfo.createBy.name}"
									title="创建人" url="/sys/office/treeData?type=3"  allowClear="true" notAllowSelectParent="true" isAll="true"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/exam/examStandardBaseInfo/form')"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>被考评对象类别</th>
				<th>考评类别</th>
				<th>考评周期</th>
				<th>考评标准名称</th>
				<th>考评标准简称</th>
				<th>所属单位</th>
				<th>是否启用</th>
				<th>创建者</th>
				<th>更新时间</th>
				<shiro:hasPermission name="exam:examStandardBaseInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examStandardBaseInfo">
			<tr>
				<td>
						${fns:getDictLabel(examStandardBaseInfo.objType, 'obj_type', '')}
				</td>
				<td>
						${fns:getDictLabel(examStandardBaseInfo.kpType, 'kp_type', '')}
				</td>
				<td>
						${fns:getDictLabel(examStandardBaseInfo.cycle, 'cycle', '')}
				</td>
				<td>
						${examStandardBaseInfo.name}
				</td>
				<td>
						${examStandardBaseInfo.abbreviation}
				</td>
				<td>
						${examStandardBaseInfo.unitName}
				</td>
				<td>
					${fns:getDictLabel(examStandardBaseInfo.isEnable, 'yes_no', '')}
				</td>
				<td>
					${examStandardBaseInfo.createName}
				</td>
				<td>
					<fmt:formatDate value="${examStandardBaseInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="exam:examStandardBaseInfo:edit"><td>

					<c:choose>
						<c:when test="${'1' == examStandardBaseInfo.isEnable}">
							<c:if test="${examStandardBaseInfo.createBy.id == fns:getUser().id}">
								<a href="${ctx}/exam/examStandardBaseInfo/updateStatus?id=${examStandardBaseInfo.id}&status=0" onclick="return confirmx('确认要禁用该考评流程吗？', this.href)">禁用</a>
							</c:if>
						</c:when>
						<c:otherwise>
							<c:if test="${examStandardBaseInfo.createBy.id == fns:getUser().id}">
								<a href="${ctx}/exam/examStandardBaseInfo/updateStatus?id=${examStandardBaseInfo.id}&status=1" onclick="return confirmx('确认要启用该考评流程吗？', this.href)">启用</a>
							</c:if>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${examStandardBaseInfo.createBy.id == fns:getUser().id}">
							<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examStandardBaseInfo/form?id=${examStandardBaseInfo.id}')">修改</a>
							<a href="${ctx}/exam/examStandardBaseInfo/delete?id=${examStandardBaseInfo.id}" onclick="return confirmx('确认要删除该考评标准管理吗？', this.href)">删除</a>
							<a href="${ctx}/exam/examStandardTemplateDefine/items?standard_id=${examStandardBaseInfo.id}&objType=${examStandardBaseInfo.objType}
							&kpType=${examStandardBaseInfo.kpType}&cycle=${examStandardBaseInfo.cycle}">考评项目</a>
						</c:when>
						<c:otherwise>
							<a href="${ctx}/exam/examStandardTemplateDefine/items?standard_id=${examStandardBaseInfo.id}&objType=${examStandardBaseInfo.objType}
							&kpType=${examStandardBaseInfo.kpType}&cycle=${examStandardBaseInfo.cycle}&operation=view">查看</a>
						</c:otherwise>
					</c:choose>
					<%--<c:if test="${examStandardBaseInfo.createBy.id == fns:getUser().id}">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examStandardBaseInfo/form?id=${examStandardBaseInfo.id}')">修改</a>
						<a href="${ctx}/exam/examStandardBaseInfo/delete?id=${examStandardBaseInfo.id}" onclick="return confirmx('确认要删除该考评标准管理吗？', this.href)">删除</a>
						<a href="${ctx}/exam/examStandardTemplateDefine/items?standard_id=${examStandardBaseInfo.id}&objType=${examStandardBaseInfo.objType}
							&kpType=${examStandardBaseInfo.kpType}&cycle=${examStandardBaseInfo.cycle}">考评项目</a>
					</c:if>--%>
				</td></shiro:hasPermission>
			</tr>


		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>