<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>警务技术任职资格管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//导入功能的JS
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairQualification", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairQualification"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "警务技术任职资格管理",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairQualification"}
			});
		}
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairQualification/formDetail?id="+id;
			top.$.jBox.open(url, "警务技术资格管理",800,500,{
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
<%--	这个页面及表与人员部分重叠已废弃--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairQualification" action="${ctx}/affair/affairQualification/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="警务技术任职资格.xlsx"/>
		<ul class="ul-form">
			<li><label>任职资格名称：</label>
				<form:input path="jobName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>取得资格日期：</label>
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairQualification.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairQualification.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>任职资格级别：</label>
				<form:select path="level" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('police_technical_qualification_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>专业方向：</label>
				<form:input path="direction" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairQualification:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairQualification/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairQualification/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairQualification'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>姓名</th>
				<th>任职资格名称</th>
				<th>取得资格日期</th>
				<th>任职资格级别</th>
				<th>准资格评定文件名称（文号）</th>
				<th>证书编号</th>
				<th>专业方向</th>
				<shiro:hasPermission name="affair:affairQualification:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairQualification" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairQualification.id}"/></td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td><a onclick="openDetailDialog('${affairQualification.id}')">
					${affairQualification.name}
				</a></td>
				<td>
					${affairQualification.jobName}
				</td>
				<td>
					<fmt:formatDate value="${affairQualification.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(affairQualification.level, 'police_technical_qualification_level', '')}
				</td>
				<td>
					${affairQualification.fileNo}
				</td>
				<td>
					${affairQualification.certificateNo}
				</td>
				<td>
					${affairQualification.direction}
				</td>
				<shiro:hasPermission name="affair:affairQualification:edit"><td>
					<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairQualification/form?id=${affairQualification.id}')">修改</a>
					<a href="${ctx}/affair/affairQualification/delete?id=${affairQualification.id}" onclick="return confirmx('确认要删除该警务技术任职资格管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>