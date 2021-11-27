<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>权重管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<style>
		.show_part {
			display: inline-block;
			*display: inline;
			*zoom: 1;
			width: 660px;
			height: 20px;
			line-height: 20px;
			font-size: 12px;
			overflow: hidden;
			-ms-text-overflow: ellipsis;
			text-overflow: ellipsis;
			white-space: nowrap;}
		.show_part:hover {height: auto;white-space: normal;}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#print").click(function(){
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
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
					formValues: false,
					afterPrint:function(){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "权重工作",1200,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/exam/examWeights"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/exam/examWeights/formDetail?id="+id;
			top.$.jBox.open(url, "权重工作详情",800,500,{
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
		<li class="active"><a href="${ctx}/exam/examWeights/">权重工作列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="examWeights" action="${ctx}/exam/examWeights/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>单位：</label>
				<sys:treeselect id="department" name="departmentId" value="${examWeights.departmentId}" labelName="department" labelValue="${examWeights.department}"
								title="所辖单位" url="/sys/office/treeData?type=2" allowClear="true" notAllowSelectParent="true" checked="true" />
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/exam/examWeights/form?id=${examWeights.id}')"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/exam/examWeights'"/></li>
			<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/exam/examWeights/deleteByIds','checkAll','myCheckBox')"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>名称</th>
				<th>考评类别</th>
				<th>单位</th>
				<shiro:hasPermission name="exam:examWeights:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examWeights" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${examWeights.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td><a href="#" onclick="openDetailDialog('${examWeights.id}')">
					${examWeights.name}
				</a></td>
				<td>
					${fns:getDictLabel(examWeights.kpType,'exam_weights_kpType', '')}
				</td>
				<td>
					<span class="show_part">${examWeights.department}</span>
						<%--${fn:substring(examWeights.department,0,100)}...--%>
				</td>
				<shiro:hasPermission name="exam:examWeights:edit"><td class="handleTd">
					<%--<c:if test="${examWeights.createBy.id == fns:getUser().id}">--%>
					<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examWeights/form?id=${examWeights.id}')">修改</a>
					<a href="${ctx}/exam/examWeights/delete?id=${examWeights.id}" onclick="return confirmx('确认要删除该权重吗？', this.href)">删除</a>
					<%--</c:if>--%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<div style="color: red;font-size: 12pt">--“局考处”考评类别 权重设置存在一条即可，请勿删除/重复添加 否则会导致分值计算错误 --</div>
	<div style="color: red;font-size: 12pt;margin-top: 5px">--“处考派出所”考评类别,各处允许存在两条：高铁/普铁(既有线) --</div>
	<div style="color: red;font-size: 12pt;margin-top: 5px">--“废弃”考评类别,表示不在使用该套权重 --</div>
	<div style="color: red;font-size: 12pt;margin-top: 5px">--建议：权重发生变化时，再原来基础上修改即可，无需将原来删除或将考评类别调整为废弃然后新增一套权重，废弃考评类别主要为了帮忙保存该套权重，防止再进行使用！！！--</div>
	<div style="color: red;font-size: 12pt;margin-top: 5px">--注意：录入的考评项目（考评标准）的项目名称/分类名称，一定要与该模块设置的工作名称保持一致，否则会导致工作项名称匹配失败导致权重分值设置错误！！！--</div>
</body>
</html>