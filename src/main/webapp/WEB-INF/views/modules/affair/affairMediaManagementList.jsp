<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团组织换届</title>
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
								$("#searchForm").attr("action","${ctx}/affair/affairMediaManagement/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairMediaManagement/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairMediaManagement/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairMediaManagement/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairMediaManagement", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairMediaManagement"}});
			});

			$("[data-toggle='popover']").popover();
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "团组织换届",1200,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairMediaManagement"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairMediaManagement/formDetail?id="+id;
			top.$.jBox.open(url, "团组织换届",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		//批量审核
		function checkByIds(url, myCheckBox, checkBoxsName) {
			var editIds = getIds(checkBoxsName);
			if (editIds.length > 0) {
				var isStr = editIds.join(',');
				top.$.jBox.open("iframe:" + url + "?ids=" + isStr, "批量审核", 700, 520, {
					buttons: {"关闭": true},
					loaded: function () {
						$(".jbox-content", top.document).css("overflow-y", "hidden");
					}, closed: function () {
						self.location.href = '${ctx}/affair/affairMediaManagement/list';
					}
				});
			} else {
				$.jBox.tip('请先选择审核内容');
			}
		};
		//审核
		function checkById(url, id) {
			top.$.jBox.open("iframe:" + url + "?ids=" + id, "审核", 700, 520, {
				buttons: {"关闭": true},
				loaded: function () {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}, closed: function () {
					self.location.href = '${ctx}/affair/affairMediaManagement/list';
				}
			});
		};
		$('#notPass').popover();
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairMediaManagement/">团组织换届</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairMediaManagement" action="${ctx}/affair/affairMediaManagement/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="换届工作.xlsx"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="headlines" htmlEscape="false" class="input-medium"/>
			</li>
			<%--
			<li><label>媒体名称：</label>
				<form:input path="mediaName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>栏目：</label>
				<form:input path="column1" htmlEscape="false" class="input-medium"/>
			</li>--%>
			<li><label>团组织：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairMediaManagement.unitId}" labelName="unit" labelValue="${affairMediaManagement.unit}"
					title="团组织" url="/affair/affairTwBase/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairMediaManagement.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairMediaManagement.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<!--
			<li><label>作者：</label>
				<form:input path="author" htmlEscape="false" class="input-medium"/>
			</li>
			-->
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairMediaManagement:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairMediaManagement/form?id=${affairMediaManagement.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairMediaManagement/deleteByIds','checkAll','myCheckBox')"/></li>
				<!--
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				<li class="btns"><input class="btn btn-primary" type="button" value="审核" onclick="checkByIds('${ctx}/affair/affairMediaManagement/shenHeDialog','checkAll','myCheckBox')"/></li>
				-->
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairMediaManagement'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>标题</th>
				<%--
				<th>媒体名称</th>
				<th>栏目</th>--%>
				<th>团组织</th>
				<%--
				<th>作者</th>
				<th>篇幅字数</th>--%>
				<th>时间</th>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairMediaManagement" varStatus="status">
			<tr>

				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairMediaManagement.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairMediaManagement.headlines}
				</td>
				<%--
				<td>
					${affairMediaManagement.mediaName}
				</td>
				<td>
					${affairMediaManagement.column1}
				</td>
				--%>
				<td>
						${affairMediaManagement.unit}
				</td>
				<%--
				<td>
					${affairMediaManagement.author}
				</td>
				<td>
					${affairMediaManagement.wordsNum}
				</td>
				--%>
				<td>
					<fmt:formatDate value="${affairMediaManagement.publicationTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairMediaManagement.id}')">查看</a>
					<%--<c:if test="${affairMediaManagement.createBy.id == fns:getUser().id}">--%>
						<shiro:hasPermission name="affair:affairMediaManagement:edit">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairMediaManagement/form?id=${affairMediaManagement.id}')">修改</a>
						<a href="${ctx}/affair/affairMediaManagement/delete?id=${affairMediaManagement.id}" onclick="return confirmx('确认要删除该团组织换届吗？', this.href)">删除</a>
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