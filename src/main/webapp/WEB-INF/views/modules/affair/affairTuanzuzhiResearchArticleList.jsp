<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>调研文章管理</title>
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
								$("#searchForm").attr("action","${ctx}/affair/affairTuanzuzhiResearchArticle/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTuanzuzhiResearchArticle/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairTuanzuzhiResearchArticle/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTuanzuzhiResearchArticle/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairTuanzuzhiResearchArticle", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairTuanzuzhiResearchArticle"}});
			});

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "调研文章",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairTuanzuzhiResearchArticle"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairTuanzuzhiResearchArticle/formDetail?id="+id;
			top.$.jBox.open(url, "调研文章详情",800,500,{
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
		<li ><a href="${ctx}/affair/affairThoughtAnalysis/">思想状况分析</a></li>
		<li ><a href="${ctx}/affair/affairDeedsBriefing/">事迹简报</a></li>
		<li class="active"><a href="${ctx}/affair/affairTuanzuzhiResearchArticle/">总结调研</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairTuanzuzhiResearchArticle" action="${ctx}/affair/affairTuanzuzhiResearchArticle/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="团组织调研文章.xlsx"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>所属团组织：</label>
				<sys:treeselect id="ownGroup" name="ownGroupId" value="${affairTuanzuzhiResearchArticle.ownGroupId}" labelName="ownGroup" labelValue="${affairTuanzuzhiResearchArticle.ownGroup}"
					title="所属团组织" url="/affair/affairTwBase/treeData" cssClass="input-small" allowClear="true"/>
			</li>
			<li><label>发布日期：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairTuanzuzhiResearchArticle.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairTuanzuzhiResearchArticle.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairTuanzuzhiResearchArticle:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairTuanzuzhiResearchArticle/form?id=${affairTuanzuzhiResearchArticle.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairTuanzuzhiResearchArticle/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairTuanzuzhiResearchArticle'"/></li>
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
				<th>单位</th>
				<th>发布时间</th>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTuanzuzhiResearchArticle" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTuanzuzhiResearchArticle.id}"/></td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairTuanzuzhiResearchArticle.title}
				</td>
				<td>
					${affairTuanzuzhiResearchArticle.ownGroup}
				</td>
				<td>
					<fmt:formatDate value="${affairTuanzuzhiResearchArticle.releaseTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairTuanzuzhiResearchArticle.id}')">查看</a>
					<shiro:hasPermission name="affair:affairTuanzuzhiResearchArticle:edit">
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairTuanzuzhiResearchArticle/form?id=${affairTuanzuzhiResearchArticle.id}')">修改</a>
						<a href="${ctx}/affair/affairTuanzuzhiResearchArticle/delete?id=${affairTuanzuzhiResearchArticle.id}" onclick="return confirmx('确认要删除该调研文章吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>