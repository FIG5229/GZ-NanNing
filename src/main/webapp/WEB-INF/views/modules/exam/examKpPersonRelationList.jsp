<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考评初审人员关系表管理</title>
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
		//添加function,用于打开form添加页面弹窗,需要传一个url
		function openForm(url,type) {
			top.$.jBox.open("iframe:"+url, "考评人员关系"+type,860,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/exam/examKpPersonRelation"}
			});
		}
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/exam/examKpPersonRelation/">考评初审人员关系表</a></li>
		&lt;%&ndash;<shiro:hasPermission name="exam:examKpPersonRelation:edit"><li><a href="${ctx}/exam/examKpPersonRelation/form">考评初审人员关系表添加</a></li></shiro:hasPermission>&ndash;%&gt;
	</ul>--%>

	<form:form id="searchForm" modelAttribute="examKpPersonRelation" action="${ctx}/exam/examKpPersonRelation/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>考评类别：</label>
				<form:select path="kpType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('kp_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<%--<li><label class="width110">考评对象单位：</label>
				<sys:treeselect id="unitId" name="unitId" value="${examKpPersonRelation.unitId}" labelName="" labelValue="${examKpPersonRelation.unitName}"
					title="选择单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>--%>
			<li><label class="width110">初审对象：</label>
				<sys:treeselect id="csUserId" name="csUserId" value="${examKpPersonRelation.csUserId}" labelName="csUserName" labelValue="${examKpPersonRelation.csUserName}"
					title="选择" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/exam/examKpPersonRelation/form','添加')"/></li>
			<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/exam/examKpPersonRelation/deleteByIds','checkAll','myCheckBox')"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/exam/examKpPersonRelation'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>考评类别</th>
				<%--<th>考评对象单位</th>--%>
				<th>考评对象</th>
				<%--<th>初审对象单位</th>--%>
				<th>初审对象</th>
				<shiro:hasPermission name="exam:examKpPersonRelation:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examKpPersonRelation" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${examKpPersonRelation.id}"/>
				</td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td>
					${fns:getDictLabel(examKpPersonRelation.kpType, 'kp_type', '')}
				</td>
				<%--<td>
					${examKpPersonRelation.unitName}
				</td>--%>
				<td>
					${examKpPersonRelation.kpUserName}
				</td>
				<%--<td>
					${examKpPersonRelation.csUnitName}
				</td>--%>
				<td>
					${examKpPersonRelation.csUserName}
				</td>
				<shiro:hasPermission name="exam:examKpPersonRelation:edit"><td>
    				<a href="javaScript:void(0)" onclick="openForm('${ctx}/exam/examKpPersonRelation/form?id=${examKpPersonRelation.id}','修改')">修改</a>
					<a href="${ctx}/exam/examKpPersonRelation/delete?id=${examKpPersonRelation.id}" onclick="return confirmx('确认要删除该考评初审人员关系表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
<span><font color="red" style="font-size: 13pt">-- 公安局考评公安处公共项考评对应关系维护表</font></span>
</body>
</html>