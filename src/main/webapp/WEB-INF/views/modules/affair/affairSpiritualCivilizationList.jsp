<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>精神文明单位</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$("[data-toggle='popover']").popover();
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
					afterPrint:function (){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});
		});

		//添加修改弹窗
		function openAddEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairSpiritualCivilization/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairSpiritualCivilization/form";
			}
			top.$.jBox.open(url, "精神文明单位录入",1000,650,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairSpiritualCivilization/formDetail?id="+id;
			top.$.jBox.open(url, "精神文明单位详情",1000,400,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<shiro:hasPermission name="affair:affairSpiritualCivilization:view">
			<li class="active"><a href="${ctx}/affair/affairSpiritualCivilization/">精神文明单位</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="affair:affairSpiritualTable:view">
			<li><a href="${ctx}/affair/affairSpiritualTable/">复查表</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="affair:affairSpiritualReview:view">
			<li><a href="${ctx}/affair/affairSpiritualReview/">复查情况报告</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="affair:affairCreateWork:view">
			<%--<c:choose>
				<c:when test="${userId=='66937439b2124f328d1521968fab06db' || userId == '8a6819768aef40968e8f289842613276'} ">
					<li><a href="${ctx}/affair/affairCreateWork/sign">创建工作</a></li>
				</c:when>
				<c:when test="${userId == 'e3ac8381fb3247e0b64fd6e3c48bddc1' || userId=='d30e324c8f73492d9b74103374fbc689'|| userId=='d154234ecb35470e84fb95e53726866b'}">
				<li><a href="${ctx}/affair/affairCreateWork/list">创建工作上报</a></li>
					<li><a href="${ctx}/affair/affairCreateWork/sign">创建工作审核</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="${ctx}/affair/affairCreateWork/list">创建工作</a></li>
					&lt;%&ndash;其他账号进行上报&ndash;%&gt;
				</c:otherwise>
			</c:choose>--%>
			<li><a href="${ctx}/affair/affairCreateWork/list">创建工作</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="affair:affairEvaluationCriteria:view">
			<li><a href="${ctx}/affair/affairEvaluationCriteria/">测评标准</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairSpiritualCivilization" action="${ctx}/affair/affairSpiritualCivilization/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairSpiritualCivilization.unitId}" labelName="unit" labelValue="${affairSpiritualCivilization.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false" isAll="true"/>
			</li>
			<li><label>年度：</label>
				<input name="annual" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairSpiritualCivilization.annual}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			</li>
			<shiro:hasPermission name="affair:affairSpiritualCivilization:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()" value="添加"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairSpiritualCivilization/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input class="btn btn-primary" type="button" value="重置"
									onclick="window.location.href='${ctx}/affair/affairSpiritualCivilization/list?treeId=${treeId}'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>单位</th>
				<th>年度</th>
				<th>批准层级</th>
				<th>批准时间</th>
				<shiro:hasPermission name="affair:affairSpiritualCivilization:edit"><th class="handleTd">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairSpiritualCivilization" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairSpiritualCivilization.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${affairSpiritualCivilization.unit}
				</td>
				<td>
						${affairSpiritualCivilization.annual}
				</td>
				<td>
						${fns:getDictLabel(affairSpiritualCivilization.approvalLevels, 'approval_levels', '')}
				</td>
				<td>
					<fmt:formatDate value="${affairSpiritualCivilization.approvalTime}" pattern="yyyy-MM-dd hh:mm "/>
				</td>
				<td class="handleTd">
					<shiro:hasPermission name="affair:affairSpiritualCivilization:edit">
						<a href="javascript:void(0)" onclick="openDetailDialog('${affairSpiritualCivilization.id}')">查看</a>
						<a href="javascript:void(0)" onclick="openAddEditDialog('${affairSpiritualCivilization.id}')">修改</a>
						<a href="${ctx}/affair/affairSpiritualCivilization/delete?id=${affairSpiritualCivilization.id}" onclick="return confirmx('确认要删除该精神文明单位管理吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>