<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>新闻宣传管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairNews/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairNews/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairNews/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairNews/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairNews", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairNews"}});
			});

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
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "新闻宣传",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairNews/formDetail?id="+id;
			top.$.jBox.open(url, "新闻宣传",1200,600,{
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
		<shiro:hasPermission name="affair:affairNews:view"><li class="active"><a href="${ctx}/affair/affairNews/">刊稿稿件</a></li></shiro:hasPermission>
		<li><a href="${ctx}/affair/affairNewsCount/countByUnit">刊稿排名</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairNews" action="${ctx}/affair/affairNews/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="6.1刊稿稿件 .xlsx"/>
		<ul class="ul-form">
			<li><label>新闻标题：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>媒体名称：</label>
				<form:input path="mediaName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>栏目：</label>
				<form:input path="lm" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>作者：</label>
				<form:input path="author" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>刊发时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairNews.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				value="<fmt:formatDate value="${affairNews.endDate}" pattern="yyyy-MM-dd"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>刊发类别：</label>
				<form:select path="typr" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_news')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>所属人：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
				<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
			<c:choose>
				<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
					<li><label>所属单位：</label>
						<sys:treeselect id="unitName" name="unitNameId" value="${affairNews.unitNameId}" labelName="unit" labelValue="${affairNews.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false" isAll="true"/>
					</li>
				</c:when>
				<c:otherwise>
					<li><label>所属单位：</label>
						<sys:treeselect id="unitName" name="unitNameId" value="${affairNews.unitNameId}" labelName="unit" labelValue="${affairNews.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
					</li>
				</c:otherwise>
			</c:choose>
			<%--<li><label>所属单位：</label>
				<sys:treeselect id="unitName" name="unitNameId" value="${affairNews.unitNameId}" labelName="unit" labelValue="${affairNews.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>--%>

		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairNews:add">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairNews/form?id=${affairNews.id}')"/></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairNews:delete">
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairNews/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairNews:edit">
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairNews'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>新闻标题</th>
				<th>媒体名称</th>
				<th>栏目</th>
				<th>作者</th>
				<th>单位</th>
				<%--<th>篇幅字数</th>--%>
				<th>刊发时间</th>
				<shiro:hasPermission name="affair:affairNews:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairNews" varStatus="status" >
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairNews.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairNews.title}
				</td>
				<td>
					${affairNews.mediaName}
				</td>
				<td>
					${affairNews.lm}
				</td>
				<td>
					${affairNews.author}
				</td>
				<td>
					${affairNews.unit}
				</td>

				<%--<td>
					${affairNews.wordNum}
				</td>--%>
				<td>
					<fmt:formatDate value="${affairNews.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td class="handleTd">
					<shiro:hasPermission name="affair:affairNews:edit">
							<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairNews/form?id=${affairNews.id}')">修改</a>
				</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairNews:delete">
						<a href="${ctx}/affair/affairNews/delete?id=${affairNews.id}" onclick="return confirmx('确认要删除该个人奖励吗？', this.href)">删除</a>
					</shiro:hasPermission>
				<a href="javascript:void(0)" onclick="openDetailDialog('${affairNews.id}')">查看</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>