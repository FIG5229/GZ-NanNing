<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活动风采管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
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
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairTixieActivityStyle/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTixieActivityStyle/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairTixieActivityStyle/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTixieActivityStyle/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairTixieActivityStyle", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairTixieActivityStyle"}});
			});

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "活动风采",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairTixieActivityStyle"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairTixieActivityStyle/formDetail?id="+id;
			top.$.jBox.open(url, "活动风采详情",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		$('#notPass').popover();
	</script>
</head>
<body>
	<ul class="nav nav-tabs"><!--
		<li><a href="${ctx}/affair/affairTixieConstitutionalSystem/">章程制度</a></li>-->
		<li class="active"><a href="${ctx}/affair/affairTixieActivityStyle/">活动风采</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairTixieActivityStyle" action="${ctx}/affair/affairTixieActivityStyle/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="体协活动风采.xlsx"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>类别：</label>
				<form:select path="category" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_txhuodong')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>单位：</label>
				<sys:treeselect id="ownGroupOrganization" name="ownGroupOrganizationId" value="${affairTixieActivityStyle.ownGroupOrganizationId}" labelName="ownGroupOrganization" labelValue="${affairTixieActivityStyle.ownGroupOrganization}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>发布时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairTixieActivityStyle.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairTixieActivityStyle.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairTixieActivityStyle:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairTixieActivityStyle/form?id=${affairTixieActivityStyle.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairTixieActivityStyle/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairTixieActivityStyle'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>标题</th>
				<th>单位</th>
				<th>发布时间</th>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTixieActivityStyle" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTixieActivityStyle.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairTixieActivityStyle.title}
				</td>
				<td>
					${affairTixieActivityStyle.ownGroupOrganization}
				</td>
				<td>
					<fmt:formatDate value="${affairTixieActivityStyle.releaseTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td class="handleTd">
					<a onclick="openDetailDialog('${affairTixieActivityStyle.id}')">查看</a>
						<%--<c:if test="${affairTixieActivityStyle.createBy.id == fns:getUser().id}">--%>
					<shiro:hasPermission name="affair:affairTixieActivityStyle:edit">
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairTixieActivityStyle/form?id=${affairTixieActivityStyle.id}')">修改</a>
						<a href="${ctx}/affair/affairTixieActivityStyle/delete?id=${affairTixieActivityStyle.id}" onclick="return confirmx('确认要删除该活动风采吗？', this.href)">删除</a>
					</shiro:hasPermission>
						<%--</c:if>--%>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>