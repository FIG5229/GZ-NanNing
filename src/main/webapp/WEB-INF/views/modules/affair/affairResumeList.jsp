<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>履历信息管理管理</title>
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
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "履历信息管理",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairResume"}
			});
		}
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairResume/formDetail?id="+id;
			top.$.jBox.open(url, "",800,500,{
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
		<li ><a href="${ctx}/affair/affairLeaderCheck/">领导干部年度考核</a></li>
		<li ><a href="${ctx}/affair/personnelGoAbroad/">出国境管理</a></li>
		<li ><a href="${ctx}/affair/personnelPassport/">护照(通行证)管理</a></li>
		<li ><a href="${ctx}/affair/affairItemReport/">个人事项报告</a></li>
		<li ><a href="${ctx}/affair/affairTemporaryPolice/">临时抽调民警管理</a></li>
		<li ><a href="${ctx}/affair/affairQualification/">警务技术任职资格管理</a></li>
		<li class="active"><a href="${ctx}/affair/affairResume/">履历信息管理</a></li>

	</ul>
	<form:form id="searchForm" modelAttribute="affairResume" action="${ctx}/affair/affairResume/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>起始日期：</label>
				<input name="beginStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairResume.beginStartDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairResume.endStartDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>截止日期：</label>
				<input name="beginEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairResume.beginEndDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairResume.endEndDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>所在单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairResume.unitId}" labelName="unit" labelValue="${affairResume.unit}"
					title="所在单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairResume:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairResume/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairResume/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairResume'"/></li>
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
				<th>警号</th>
				<th>身份证号</th>
				<th>起始日期</th>
				<th>截止日期</th>
				<th>所在单位</th>
				<th>身份或职务说明</th>
				<th>履历类别</th>
				<th>基层工作的标志（是否）</th>
				<th>履历说明</th>
				<shiro:hasPermission name="affair:affairResume:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairResume" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairResume.id}"/>
				</td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td><a onclick="openDetailDialog('${affairResume.id}')">
					${affairResume.name}
				</a></td>
				<td>
					${affairResume.policeNo}
				</td>
				<td>
					${affairResume.idNumber}
				</td>
				<td>
					<fmt:formatDate value="${affairResume.startDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairResume.endDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairResume.unit}
				</td>
				<td>
					${affairResume.identityJobExplain}
				</td>
				<td>
					${fns:getDictLabel(affairResume.type, 'personnel_lltype', '')}
				</td>
				<td>
					${fns:getDictLabel(affairResume.sign, 'yes_no', '')}
				</td>
				<td>
					${affairResume.explain}
				</td>
				<shiro:hasPermission name="affair:affairResume:edit"><td>
					<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairResume/form?id=${affairResume.id}')">修改</a>
					<a href="${ctx}/affair/affairResume/delete?id=${affairResume.id}" onclick="return confirmx('确认要删除该履历信息管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>