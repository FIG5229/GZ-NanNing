<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>幼儿补助管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
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
				$("#export").click(
						function(){
							var submit = function (v, h, f) {
								if (v == 'all') {
									$("#searchForm").attr("action","${ctx}/affair/affairChildSubsidy/export");
									$("#searchForm").submit();
									$("#searchForm").attr("action","${ctx}/affair/affairChildSubsidy/");
								}
								if (v == 'part') {
									$("#searchForm").attr("action","${ctx}/affair/affairChildSubsidy/export?flag=true");
									$("#searchForm").submit();
									$("#searchForm").attr("action","${ctx}/affair/affairChildSubsidy/");
								}
								if (v == 'cancel') {
									$.jBox.tip('已取消');
								}
								return true;
							};
							$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
						}
				);
				$("#btnImport").click(function(){
					top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairChildSubsidy", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairChildSubsidy"}});
				});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		let height =  $(window).height()-50;
		let width =  $(window).width()-50;
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "幼儿补助",width,height,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairChildSubsidy"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairChildSubsidy/formDetail?id="+id;
			top.$.jBox.open(url, "幼儿补助详情",width,height,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<shiro:lacksPermission name="affair:affairPolicewomanWork:manage">
			<li><a href="${ctx}/affair/affairPolicewomanWork/">女警工作管理</a></li>
		</shiro:lacksPermission>

		<shiro:hasPermission name="affair:affairPolicewomanWork:manage">
			<%--有管理权限时 跳转到管理页 无权限则是查询页 达到合并tab效果--%>
			<input type="hidden" value="hasPermission" id="permission">
			<li><a href="${ctx}/affair/affairPolicewomanWork/manageList">女警工作管理</a></li>
		</shiro:hasPermission>
		<li ><a href="${ctx}/affair/affairPolicewomanTalent/">女警风采信息</a></li>
		<li class="active"><a href="${ctx}/affair/affairChildSubsidy/">幼儿补助信息</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairChildSubsidy" action="${ctx}/affair/affairChildSubsidy/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="幼儿补助表.xlsx"/>
		<ul class="ul-form">
			<li><label>时间：</label>
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairChildSubsidy.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairChildSubsidy.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>单位：</label>
				<form:input path="unit" htmlEscape="false" class="input-xlarge "/>
			</li>
			<%--9.9 及时反馈改为输入框
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairChildSubsidy.unitId}" labelName="unit" labelValue="${affairChildSubsidy.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
			</li>--%>
			<li><label>民警姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>民警性别：</label>
				<form:select path="sex" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>补助金额：</label>
				<form:input path="beginMoney" htmlEscape="false" class="input-medium"/>
				至
				<form:input path="endMoney" htmlEscape="false" class="input-medium"/>元
			</li>
			<li><label>职务：</label>
				<form:input path="job" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairChildSubsidy:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairChildSubsidy/add?id=${affairChildSubsidy.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairChildSubsidy/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairChildSubsidy'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>时间</th>
				<th>单位</th>
				<th>民警姓名</th>
				<th>民警性别</th>
				<th>补助金额</th>
				<th>职务</th>
				<th>子女姓名</th>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairChildSubsidy" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairChildSubsidy.id}"/></td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<fmt:formatDate value="${affairChildSubsidy.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairChildSubsidy.unit}
				</td>
				<td>
					${affairChildSubsidy.name}
				</td>
				<td>
					${fns:getDictLabel(affairChildSubsidy.sex, 'sex', '')}
				</td>
				<td>
					${affairChildSubsidy.money}
				</td>
				<td>
						${affairChildSubsidy.job}
				</td>
				<td>
					${affairChildSubsidy.childName}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairChildSubsidy.id}')">查看</a>
					<shiro:hasPermission name="affair:affairChildSubsidy:edit">
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairChildSubsidy/form?id=${affairChildSubsidy.id}')">修改</a>
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairChildSubsidy/add?id=${affairChildSubsidy.id}&handleAdd=true')">添加子女</a>
						<a href="${ctx}/affair/affairChildSubsidy/delete?id=${affairChildSubsidy.id}" onclick="return confirmx('确认要删除该幼儿补助吗？', this.href)">删除</a>
				    </shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>